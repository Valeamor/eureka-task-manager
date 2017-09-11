package com.guorenbao.taskmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.guorenbao.taskmanager.client.param.WorkingStatus;
import com.guorenbao.taskmanager.core.TaskManagerCore;
import com.guorenbao.taskmanager.domain.entity.TaskManagerList;
import com.guorenbao.taskmanager.domain.enums.TaskManagerListStatus;
import com.guorenbao.taskmanager.service.TaskManagerListService;
import com.guorenbao.taskmanager.utils.CronUtil;
import com.guorenbao.taskmanager.utils.TaskConfigurationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manual")
public class TaskManagerManualController {
  @Autowired
  TaskManagerCore taskManagerCore;
  @Autowired
  TaskManagerListService taskManagerListService;

  @RequestMapping(value = "/workingStatus", method = RequestMethod.GET)
  public String workingStatus(
      @RequestParam("pageNo") Integer pageNo,
      @RequestParam("pageNo") Integer pageSize) {
    return workingStatusToString(taskManagerCore.workingStatus(pageNo, pageSize));
  }

  private String workingStatusToString(WorkingStatus workingStatus) {
    StringBuilder sb = new StringBuilder();
    sb.append("\nisWorking:").append(workingStatus.getIsWorking())
      .append("\navgLocalTaskDuration:").append(workingStatus.getAvgLocalTaskDuration())
      .append("\nList:");
    workingStatus.getLog().forEach(
        line -> sb.append("\n")
                  .append(CronUtil.formatDate(line.getKey()))
                  .append(" - ")
                  .append(line.getValue())
                  .append("ms"));
    return sb.toString();
  }

  @RequestMapping(value = "/setCanProcessTask", method = RequestMethod.GET)
  public String setWorking(@RequestParam("can") Boolean canProcessTask) {
    taskManagerCore.setProcessTaskSwitch(canProcessTask);
    return "已将本节点强制置为slave";
  }

  @RequestMapping(value = "/exec", method = RequestMethod.POST)
  public String exec(@RequestParam("taskId") String taskId) {
    TaskManagerList task = taskManagerListService.selectByTaskId(taskId);
    if (task.getStatus().equals(TaskManagerListStatus.RUNNING)) {
      return "error: 任务运行中";
    }
    taskManagerListService.update(
        TaskConfigurationUtil.create(task).set(TaskConfigurationUtil.Configs.IS_MANUAL_RUN, true));
    return "设置任务状态为立刻手工执行";
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String setWorking(
      @RequestParam("taskId") String taskId,
      @RequestParam(value = "status", required = false) TaskManagerListStatus status,
      @RequestParam("cron_expression") String cronExpression,
      @RequestParam(value = "configuration", required = false) String configuration) {
    if (taskManagerListService.getTaskList()
                              .stream()
                              .anyMatch(t -> JSONObject.parseObject(t.toString(), TaskManagerList.class)
                                                       .getTaskId()
                                                       .equals(taskId))) {
      return "error: taskId重复";
    }
    if (!taskManagerCore.isTaskIdValid(taskId)) {
      return "error: taskId无对应实例";
    }
    CronUtil.check(cronExpression);
    TaskManagerList task = new TaskManagerList();
    task.setTaskId(taskId);
    task.setStatus(status);
    task.setCronExpression(cronExpression);
    task.setConfiguration(configuration);
    TaskConfigurationUtil.create(task).check();
    taskManagerListService.create(task);
    return "task创建成功";
  }
}
