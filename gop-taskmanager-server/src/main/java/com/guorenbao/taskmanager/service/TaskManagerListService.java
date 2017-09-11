package com.guorenbao.taskmanager.service;

import com.alibaba.fastjson.JSONObject;
import com.guorenbao.taskmanager.client.TaskManagerClient;
import com.guorenbao.taskmanager.core.TaskManagerCore;
import com.guorenbao.taskmanager.dao.TaskManagerListMapper;
import com.guorenbao.taskmanager.dao.TaskManagerLogMapper;
import com.guorenbao.taskmanager.domain.entity.TaskManagerList;
import com.guorenbao.taskmanager.domain.entity.TaskManagerListCriteria;
import com.guorenbao.taskmanager.domain.entity.TaskManagerLog;
import com.guorenbao.taskmanager.domain.enums.TaskManagerListStatus;
import com.guorenbao.taskmanager.domain.enums.TaskManagerLogRunType;
import com.guorenbao.taskmanager.domain.enums.TaskManagerLogStatus;
import com.guorenbao.taskmanager.exception.BusinessException;
import com.guorenbao.taskmanager.utils.CronUtil;
import com.guorenbao.taskmanager.utils.TaskConfigurationUtil;
import com.guorenbao.taskmanager.utils.ThreadUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static com.guorenbao.taskmanager.service.RestCallService.Path.CHECK_TASK;
import static java.util.Optional.ofNullable;

@Service
public class TaskManagerListService {
  @Autowired
  TaskManagerListMapper taskManagerListMapper;
  @Autowired
  TaskManagerLogMapper taskManagerLogMapper;
  @Autowired
  RedisTemplate<String, String> redisTemplate;
  @Autowired
  ApplicationContext context;
  @Autowired
  RestCallService restCallService;
  @Autowired
  TaskManagerClient taskManagerClient;

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final String cacheKey = "taskCache";
  private final Map<String, Integer> checkRunningTaskFailureCounter = new ConcurrentHashMap<>();

  /**
   * ------------------------------- API -------------------------------
   */

  @Transactional
  public void create(TaskManagerList task) {
    task.setUpdateTime(new Date());
    taskManagerListMapper.insertSelective(task);
    setCache(task);
    logger.info("create task: {}", task);
  }

  @Transactional
  public void deleteByTaskId(String taskId) {
    TaskManagerListCriteria criteria = new TaskManagerListCriteria();
    criteria.createCriteria().andTaskIdEqualTo(taskId);
    List<TaskManagerList> taskList = taskManagerListMapper.selectByExampleWithBLOBs(criteria);
    if (taskList.size() == 0) {
      throw new BusinessException("任务不存在:" + taskId);
    }
    taskManagerListMapper.deleteByExample(criteria);
    deleteCache(taskId);
    logger.info("delete task: {}", taskList.get(0));
  }

  @Transactional
  public void update(TaskManagerList task) {
    task.setUpdateTime(new Date());
    taskManagerListMapper.updateByPrimaryKeySelective(task);
    setCache(task);
//    logger.info("update task: {}", task);
  }

  public TaskManagerList selectByTaskId(String taskId) {
    TaskManagerList task = getFromCache(taskId);
    if (task != null) {
      return task;
    }
    task = getFromDB(taskId);
    setCache(task);
    return task;
  }

  public List<Object> getTaskList() {
    List<Object> list = redisTemplate.opsForHash().values(cacheKey);
    if (list == null || list.size() == 0) {
      List<TaskManagerList> taskList = taskManagerListMapper.selectByExampleWithBLOBs(null);
      if (taskList.size() == 0) {
        return new ArrayList<>();
      }
      redisTemplate.opsForHash().putAll(
          cacheKey,
          taskList.stream().collect(Collectors.toMap(TaskManagerList::getTaskId, JSONObject::toJSONString)));
      return getTaskList();
    } else {
      return list;
    }
  }

  @Transactional
  public void processTask(TaskManagerList task, Date now, CountDownLatch countDownLatch) {
    try {
      switch (task.getStatus()) {
        case DEFAULT:
          processIdleTask(task, now);
          break;
        case RUNNING:
          processRunningTask(task, now);
          break;
        case INVALID:
          // do nothing here
          break;
      }
    } catch (Throwable e) {
      logger.error("执行任务失败\n{}", task, e);
      throw e;
    } finally {
      if (countDownLatch != null) {
        countDownLatch.countDown();
      }
    }
  }

  private void processIdleTask(TaskManagerList task, Date now) {
    Date nextRunTime = task.getNextRunTime();
    if (nextRunTime == null) {
      setNextRunTimeAndUpdate(task);
    } else if (nextRunTime.compareTo(now) <= 0) {
      // 通过 eureka feign client 分配任务
      taskManagerClient.exec(task);
    }
  }

  private void processRunningTask(TaskManagerList task, Date now) {
    // 判断任务是否执行时间过长(平均执行时间的两倍)
    if (now.getTime() - task.getUpdateTime().getTime() <
        ofNullable(task.getAvgRunDuration()).orElse(30_000L) * 2) {
      return;
    }
    try {
      if (!(Boolean) restCallService.call(task.getRunNode(), CHECK_TASK.withParams("taskId=" + task.getTaskId()))) {
        setTaskFailure(task);
        logger.info("向任务分配节点 {} 询问，任务 {} 并非执行状态，置为失败\n{}", task.getRunNode(), task.getTaskId(), task);
      }
    } catch (Exception e) {
      logger.error("查询执行中任务{}异常\n{}", task.getTaskId(), task, e);
      String taskId = task.getTaskId();
      Integer failCount = checkRunningTaskFailureCounter.get(taskId);
      if (failCount == null) {
        checkRunningTaskFailureCounter.put(taskId, 1);
      } else if (failCount > 10) {
        setTaskFailure(task);
        checkRunningTaskFailureCounter.remove(taskId);
      } else {
        checkRunningTaskFailureCounter.put(taskId, failCount + 1);
      }
    }
  }

  @Transactional
  public void setTaskRunning(TaskManagerList task) {
    TaskConfigurationUtil util = TaskConfigurationUtil.create(task);
    logTaskProcess(task, TaskManagerLogStatus.START, getRunType(util), null);
    task.setStatus(TaskManagerListStatus.RUNNING);
    task.setRunNode(TaskManagerCore.serverAddress);
    setNextRunTimeAndUpdate(task);
  }

  private void logTaskProcess(
      TaskManagerList task,
      TaskManagerLogStatus logStatus,
      TaskManagerLogRunType runType,
      Long duration) {
    TaskManagerLog log = new TaskManagerLog();
    log.setTaskId(task.getTaskId());
    log.setRunNode(TaskManagerCore.serverAddress);
    log.setRunType(runType);
    log.setStatus(logStatus);
    log.setDuration(duration);
    taskManagerLogMapper.insertSelective(log);
  }

  private TaskManagerLogRunType getRunType(TaskConfigurationUtil util) {
    if (util.isManualRun()) {
      // 手工触发任务下一次
      util.set(TaskConfigurationUtil.Configs.IS_MANUAL_RUN, false);
      return TaskManagerLogRunType.MANUAL;
    } else {
      return TaskManagerLogRunType.CRON;
    }
  }


  @Transactional
  public void setTaskSuccess(TaskManagerList task, long taskStartTime) {
    try {
      long runDuration = System.currentTimeMillis() - taskStartTime;
      logTaskProcess(task, TaskManagerLogStatus.SUCCESS, null, runDuration);
      int successSum = ofNullable(task.getSuccessSum()).orElse(0);
      task.setSuccessSum(successSum + 1); // 执行成功次数
      task.setLastRunDuration(runDuration); // 本次运行时间(ms)
      task.setAvgRunDuration((ofNullable(task.getAvgRunDuration()).orElse(0L) * successSum + runDuration)
                             / task.getSuccessSum()); // 平均运行时间(ms)
      TaskConfigurationUtil util = TaskConfigurationUtil.create(task);
      task.setStatus(util.isRunOnce() ? TaskManagerListStatus.INVALID : TaskManagerListStatus.DEFAULT);
      task.setLastSuccessTime(new Date());
      util.set(TaskConfigurationUtil.Configs.RETRY_TIMES_LEFT, null);
      setNextRunTimeAndUpdate(task);
    } catch (Exception e) {
      logSetTaskStatusError(task, "执行成功", e);
    }
  }

  @Transactional
  public void setTaskFailure(TaskManagerList task) {
    try {
      logTaskProcess(task, TaskManagerLogStatus.FAILURE, null, null);
      Date now = new Date();
      task.setStatus(TaskManagerListStatus.DEFAULT);
      task.setFailureSum(ofNullable(task.getFailureSum()).orElse(0) + 1); // 记录执行失败次数
      task.setLastFailureTime(now);
      TaskConfigurationUtil util = TaskConfigurationUtil.create(task);
      int retryTimesLeft = util.getRetryTimes();
      if (util.getRetryTimes() > 0) {
        task.setNextRunTime(now);
        util.set(TaskConfigurationUtil.Configs.RETRY_TIMES_LEFT, --retryTimesLeft);
        logger.warn("任务执行失败，采取重试策略\n{}", task);
      }
      update(task);
    } catch (Exception e) {
      logSetTaskStatusError(task, "执行失败", e);
    }
  }

  @Transactional
  public void setTaskInvalid(TaskManagerList task) {
    logTaskProcess(task, TaskManagerLogStatus.SET_INVLAID, null, null);
    task.setStatus(TaskManagerListStatus.INVALID);
    update(task);
  }

  private void logSetTaskStatusError(TaskManagerList task, String status, Exception e) {
    logger.error("更新任务状态为{}出错\n{}", status, task, e);
    ThreadUtil.sleep(1_000);
  }


  private void setNextRunTimeAndUpdate(TaskManagerList task) {
    task.setNextRunTime(CronUtil.nextExecutionTime(task.getCronExpression()));
    update(task);
  }


  /**
   * ------------------------------- redis caches -------------------------------
   */


  private TaskManagerList getFromDB(String taskId) {
    TaskManagerListCriteria criteria = new TaskManagerListCriteria();
    criteria.createCriteria().andTaskIdEqualTo(taskId);
    List<TaskManagerList> taskList = taskManagerListMapper.selectByExampleWithBLOBs(criteria);
    if (taskList.size() == 0) {
      throw new BusinessException("任务不存在:" + taskId);
    }
    return taskList.get(0);
  }

  private TaskManagerList getFromCache(String taskId) {
    String cache = redisTemplate.opsForHash().get(cacheKey, taskId).toString();
    if (cache == null) {
      return null;
    }
    return JSONObject.parseObject(cache, TaskManagerList.class);
  }

  private void setCache(TaskManagerList task) {
    redisTemplate.opsForHash().put(cacheKey, task.getTaskId(), JSONObject.toJSONString(task));
  }

  private void deleteCache(String taskId) {
    redisTemplate.opsForHash().delete(cacheKey, taskId);
  }

}
