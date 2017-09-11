package com.guorenbao.taskmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.guorenbao.taskmanager.client.TaskManagerClient;
import com.guorenbao.taskmanager.client.param.BusinessResultTask;
import com.guorenbao.taskmanager.client.param.WorkingStatus;
import com.guorenbao.taskmanager.core.TaskManagerCore;
import com.guorenbao.taskmanager.domain.entity.TaskManagerList;
import com.guorenbao.taskmanager.service.TaskManagerListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(TaskManagerClient.path)
public class TaskManagerController implements TaskManagerClient {
  @Autowired
  TaskManagerCore taskManagerCore;
  @Autowired
  TaskManagerListService taskManagerListService;

  @Override
  @RequestMapping(value = "/isWorking", method = RequestMethod.GET)
  public BusinessResultTask<Boolean> isWorking() {
    return new BusinessResultTask<>(taskManagerCore.isWorking());
  }

  @Override
  @RequestMapping(value = "/workingStatus", method = RequestMethod.GET)
  public BusinessResultTask<WorkingStatus> workingStatus(
      @RequestParam("pageNo") Integer pageNo,
      @RequestParam("pageNo") Integer pageSize) {
    return new BusinessResultTask<>(taskManagerCore.workingStatus(pageNo, pageSize));
  }

  @Override
  @RequestMapping(value = "/setCanProcessTask", method = RequestMethod.GET)
  public BusinessResultTask setWorking(@RequestParam("can") Boolean canProcessTask) {
    taskManagerCore.setProcessTaskSwitch(canProcessTask);
    return new BusinessResultTask<>();
  }

  @Override
  @RequestMapping(value = "/exec", method = RequestMethod.POST)
  public BusinessResultTask exec(@RequestBody TaskManagerList task) {
    taskManagerCore.exec(task);
    return new BusinessResultTask<>();
  }

  @Override
  @RequestMapping(value = "/isRunning", method = RequestMethod.GET)
  public BusinessResultTask<Boolean> isRunning(@RequestParam("taskId") String taskId) {
    return new BusinessResultTask<>(taskManagerCore.isTaskRunningLocal(taskId));
  }

  @Override
  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public BusinessResultTask delete(@RequestParam("taskId") String taskId) {
    taskManagerListService.deleteByTaskId(taskId);
    return new BusinessResultTask<>();
  }

  @Override
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public BusinessResultTask create(@RequestBody TaskManagerList task) {
    taskManagerListService.create(task);
    return new BusinessResultTask<>();
  }

  @Override
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public BusinessResultTask update(@RequestBody TaskManagerList task) {
    taskManagerListService.update(task);
    return new BusinessResultTask<>();
  }

  @Override
  @RequestMapping(value = "/select", method = RequestMethod.GET)
  public BusinessResultTask<TaskManagerList> select(@RequestParam("taskId") String taskId) {
    return new BusinessResultTask<>(taskManagerListService.selectByTaskId(taskId));
  }

  @Override
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public BusinessResultTask<List<TaskManagerList>> list() {
    return new BusinessResultTask<>(
        taskManagerListService.getTaskList()
                              .stream()
                              .map(v -> JSONObject.parseObject(v.toString(), TaskManagerList.class))
                              .collect(Collectors.toList()));
  }
}
