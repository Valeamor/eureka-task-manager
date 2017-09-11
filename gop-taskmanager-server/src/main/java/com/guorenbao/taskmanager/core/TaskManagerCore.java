package com.guorenbao.taskmanager.core;

import com.alibaba.fastjson.JSONObject;
import com.guorenbao.taskmanager.client.TaskManagerClient;
import com.guorenbao.taskmanager.client.param.WorkingStatus;
import com.guorenbao.taskmanager.domain.entity.TaskManagerList;
import com.guorenbao.taskmanager.service.RestCallService;
import com.guorenbao.taskmanager.service.TaskBeanService;
import com.guorenbao.taskmanager.service.TaskManagerListService;
import com.guorenbao.taskmanager.utils.ThreadUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javafx.util.Pair;

import static com.guorenbao.taskmanager.service.RestCallService.Path.CHECK_SERVICE;

@Service
public class TaskManagerCore {
  private Logger logger = LoggerFactory.getLogger(getClass());
  private static final long taskInterval = 256L; // 任务分发最小间隔(ms)
  private static final long heartBeatInterval = taskInterval * 4;
  private static final long deadInterval = taskInterval * 6;
  private static final long expireLockInterval = taskInterval * 10;
  private static final String lockKey = "grb.task.manager.lock";
  private static final String hostKey = "grb.task.manager.host";
  private static final int masterDeadCountLimit = 2;
  private static final ConcurrentHashMap<String, RunningTaskStatus> runningTaskList = new ConcurrentHashMap<>();
  private static final RunDurationLog runDurationLog = new RunDurationLog();

  public static String serverAddress;
  private static boolean canProcessTask = true;
  private static int masterDeadCount = 0;
  private static BigDecimal avgLocalTaskDuration = BigDecimal.ZERO;
  private static long localTaskRunCount = 0;

  @Autowired
  ApplicationContext context;
  @Value("${server.port}")
  String serverPort;
  @Value("${taskManager.LANipBeginWith}")
  String startIP;
  @Autowired
  RedisTemplate<String, String> redisTemplate;
  @Autowired
  TaskManagerListService taskManagerListService;
  @Autowired
  RestCallService restCallService;
  @Autowired
  TaskManagerClient taskManagerClient;
  @Autowired
  DiscoveryClient discoveryClient;
  @Autowired
  TaskBeanService taskBeanService;

  private static class RunDurationLog {
    static final int maxLength = 1000;
    List<Pair<Date, Long>> log;

    RunDurationLog() {
      log = new ArrayList<>();
    }

    void add(Date date, Long duration) {
      if (log.size() >= maxLength) {
        log.remove(0);
      }
      log.add(new Pair<>(date, duration));
    }

    List<Pair<Date, Long>> list(Integer offset, Integer pageSize) {
      return log.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
    }
  }

  private enum RunningTaskStatus {
    DEFAULT,
  }

  private final ExecutorService taskCheckPool =
      new ThreadPoolExecutor(
          2, 4,
          TimeUnit.MINUTES.toMinutes(10), TimeUnit.MILLISECONDS,
          new ArrayBlockingQueue<>(1024),
          (r, executor) -> {
            try {
              executor.getQueue().put(r);
            } catch (Exception e) {
              logger.error("error", e);
            }
          });

  private final ExecutorService taskRunPool =
      new ThreadPoolExecutor(
          4, 32,
          TimeUnit.MINUTES.toMinutes(1), TimeUnit.MILLISECONDS,
          new ArrayBlockingQueue<>(1024),
          (r, executor) -> {
            try {
              executor.getQueue().put(r);
            } catch (Exception e) {
              logger.error("error", e);
            }
          });

  @PostConstruct
  private void init() throws Exception {
    serverAddress = getLANAddress() + ":" + serverPort;
    lastHeartBeatTimestamp = System.currentTimeMillis();
  }

  @PreDestroy
  private void destroy() {
    releaseLock();
  }

  public void run() {
    logger.info("server start@{}", serverAddress);
    waitUntilServerRegistered();
    while (true) {
      try {
        if (canProcessTask && requireLock() && !isMasterNodeRunning()) {
          masterProc();
        } else {
          slaveProc();
        }
      } catch (Exception e) {
        logger.error("error", e);
      }
      ThreadUtil.sleep(deadInterval);
    }
  }


  private void waitUntilServerRegistered() {
    int i = 0;
    while (true) {
      try {
        Thread.sleep(1_000);
        if (++i % 5 == 1) {
          logger.info("eureka注册状态确认中...");
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("gop-taskmanager-server");
        if (instances.size() == 0) {
          continue;
        }
        for (ServiceInstance instance : instances) {
          if (serverAddress.equals(instance.getHost() + ":" + instance.getPort())) {
            Thread.sleep(1_000);
            taskManagerClient.isWorking();
            String[] activeProfiles = context.getEnvironment().getActiveProfiles();
            System.out.println("##################################################");
            System.out.println("start success: " + Arrays.toString(activeProfiles));
            System.out.println("##################################################");
            return;
          }
        }
      } catch (Exception e) {
        logger.info("eureka注册状态确认中: {}", e.getMessage());
      }
    }
  }

  private String getLANAddress() throws Exception {
    for (
        final Enumeration<NetworkInterface> interfaces =
        NetworkInterface.getNetworkInterfaces();
        interfaces.hasMoreElements();
        ) {
      final NetworkInterface cur = interfaces.nextElement();
      if (cur.isLoopback()) {
        continue;
      }
      for (final InterfaceAddress address : cur.getInterfaceAddresses()) {
        final InetAddress internalAddress = address.getAddress();
        if (internalAddress instanceof Inet4Address) {
          String ip = internalAddress.getHostAddress();
          if (ip.startsWith(startIP)) {
            return ip;
          }
        }
      }
    }
    throw new RuntimeException("获取局域网ip失败");
  }

  private Long lastHeartBeatTimestamp;

  public boolean isWorking() {
    return canProcessTask && System.currentTimeMillis() - lastHeartBeatTimestamp < deadInterval;
  }

  public WorkingStatus workingStatus(Integer offset, Integer pageSize) {
    return new WorkingStatus(avgLocalTaskDuration, runDurationLog.list(offset, pageSize), isWorking());
  }

  public void setProcessTaskSwitch(boolean canProcessTask) {
    TaskManagerCore.canProcessTask = canProcessTask;
  }

  @Transactional
  // 运行任务，只能被master主线程调用
  public void exec(TaskManagerList task) {
    long taskStartTime = System.currentTimeMillis();
    TaskBase taskInstance = taskBeanService.getTaskBean(task.getTaskId());
    if (taskInstance == null) {
      taskManagerListService.setTaskInvalid(task);
      logger.error("任务 {} 已置为无效状态 ============", task.getTaskId());
    } else {
      taskManagerListService.setTaskRunning(task);
      runningTaskList.put(task.getTaskId(), RunningTaskStatus.DEFAULT);
      taskRunPool.submit(() -> {
        try {
          taskInstance.run();
          taskManagerListService.setTaskSuccess(task, taskStartTime);
        } catch (Exception e) {
          logger.error("执行任务{}异常\n{}", task.getTaskId(), task, e);
          taskManagerListService.setTaskFailure(task);
        } finally {
          runningTaskList.remove(task.getTaskId());
        }
      });
    }
  }

  public Boolean isTaskIdValid(String taskId) {
    return taskBeanService.getTaskBean(taskId) != null;
  }


  public Boolean isTaskRunningLocal(String taskId) {
    return RunningTaskStatus.DEFAULT.equals(runningTaskList.get(taskId));
  }


  /**
   * master
   */
  private void masterProc() {
    setMeAsMasterNode();
    Thread thread = new Thread(this::mainThread);
    thread.start();
    while (true) {
      switch (thread.getState()) {
        case NEW:
        case RUNNABLE:
        case BLOCKED:
        case WAITING:
        case TIMED_WAITING:
          lastHeartBeatTimestamp = System.currentTimeMillis();
          expireLock();
          ThreadUtil.sleep(heartBeatInterval);
          break;
        case TERMINATED:
          logger.warn("主线程终止");
          return;
      }
    }
  }

  private void mainThread() {
    while (true) {
      if (canProcessTask && serverAddress.equals(getMasterNode())) {
        long startTime = System.currentTimeMillis();
        processTasksAsync();
        mainThreadSleep(startTime);
      } else {
        return;
      }
    }
  }

  private void mainThreadSleep(long startTime) {
    long taskDuration = System.currentTimeMillis() - startTime;
    runDurationLog.add(new Date(), taskDuration);
    avgLocalTaskDuration = avgLocalTaskDuration
        .multiply(BigDecimal.valueOf(localTaskRunCount))
        .add(BigDecimal.valueOf(taskDuration))
        .divide(BigDecimal.valueOf(++localTaskRunCount), 2, BigDecimal.ROUND_HALF_EVEN);
    long interval = taskInterval - taskDuration;
//    logger.info("本次任务分发处理时间:{}ms|avg:{}ms", taskDuration, avgLocalTaskDuration);
    if (interval > 0) {
      ThreadUtil.sleep(interval);
    }
  }

  /**
   * 主线程 -> 查询任务状态 分发任务 处理异常任务
   */
  // 异步执行
  private void processTasksAsync() {
    List<Object> taskList = taskManagerListService.getTaskList();
    int taskNum = taskList.size();
    if (taskNum <= 2) {
      processTaskList(taskList, null);
    } else {
      // 多于两个任务采用线程池执行
      CountDownLatch countDownLatch = new CountDownLatch(taskNum);
      processTaskList(taskList, countDownLatch);
      try {
        countDownLatch.await();
      } catch (InterruptedException ignored) {
      }
    }
  }

  private void processTaskList(List<Object> taskList, CountDownLatch countDownLatch) {
    Date now = new Date();
    taskList.stream()
            .map(task -> JSONObject.parseObject(task.toString(), TaskManagerList.class))
            .filter(task -> !isTaskRunningLocal(task.getTaskId()))
            .forEach(task -> taskCheckPool.submit(
                () -> taskManagerListService.processTask(task, now, countDownLatch)));
  }

  /**
   * slave
   */
  private void slaveProc() {
    logger.info("本节点 {} 现在是 slave 节点", serverAddress);
    while (true) {
      ThreadUtil.sleep(expireLockInterval);
      checkMastNodeStatus(false);
      if (masterDeadCount > masterDeadCountLimit) {
        return;
      } else if (masterDeadCount == masterDeadCountLimit) {
        expireLock();
        return;
      }
    }
  }

  private boolean isMasterNodeRunning() {
    checkMastNodeStatus(true);
    return masterDeadCount == 0;
  }


  private void checkMastNodeStatus(boolean isSilent) {
    String masterAddress = getMasterNode();
    if (masterAddress == null) {
      masterDeadCount++;
      return;
    }
    try {
      if ((Boolean) restCallService.call(masterAddress, CHECK_SERVICE)) {
        masterDeadCount = 0;
      } else {
        if (!isSilent) {
          logger.warn("master 节点 {} 异常 {}", masterAddress);
        }
        masterDeadCount++;
      }
    } catch (Exception e) {
      if (!isSilent) {
        logger.warn("master 节点异常 {}, {}", masterAddress, e.getMessage());
      }
      masterDeadCount++;
    }
  }

  private void setMeAsMasterNode() {
    redisTemplate.opsForValue().set(hostKey, serverAddress);
    logger.info("本节点 {} 现在是 master 节点", serverAddress);
  }

  private String getMasterNode() {
    return redisTemplate.opsForValue().get(hostKey);
  }

  private boolean requireLock() {
    return redisTemplate.opsForValue().increment(lockKey, 1L).compareTo(1L) == 0;
  }

  private void expireLock() {
    try {
      redisTemplate.expire(lockKey, expireLockInterval, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      logger.error("设置锁超时异常", e);
    }
  }

  private void releaseLock() {
    while (true) {
      try {
        redisTemplate.delete(lockKey);
        return;
      } catch (Exception e) {
        logger.error("释放锁异常", e);
      }
    }
  }
}
