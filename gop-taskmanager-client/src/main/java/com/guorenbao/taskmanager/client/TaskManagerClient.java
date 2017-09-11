package com.guorenbao.taskmanager.client;

import com.guorenbao.taskmanager.client.param.BusinessResultTask;
import com.guorenbao.taskmanager.client.param.WorkingStatus;
import com.guorenbao.taskmanager.domain.entity.TaskManagerList;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "gop-taskmanager-server", path = TaskManagerClient.path)
public interface TaskManagerClient {
  String path = "/task/manage";

  @RequestMapping(value = "/isWorking", method = RequestMethod.GET)
  BusinessResultTask<Boolean> isWorking();

  @RequestMapping(value = "/workingStatus", method = RequestMethod.GET)
  BusinessResultTask<WorkingStatus> workingStatus(
      @RequestParam("pageNo") Integer pageNo,
      @RequestParam("pageNo") Integer pageSize);

  @RequestMapping(value = "/setCanProcessTask", method = RequestMethod.GET)
  BusinessResultTask setWorking(@RequestParam("can") Boolean canProcessTask);

  @RequestMapping(value = "/exec", method = RequestMethod.POST)
  BusinessResultTask exec(@RequestBody TaskManagerList task);

  @RequestMapping(value = "/isRunning", method = RequestMethod.GET)
  BusinessResultTask<Boolean> isRunning(@RequestParam("taskId") String taskId);

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  BusinessResultTask delete(@RequestParam("taskId") String taskId);

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  BusinessResultTask create(@RequestBody TaskManagerList task);

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  BusinessResultTask update(@RequestBody TaskManagerList task);

  @RequestMapping(value = "/select", method = RequestMethod.GET)
  BusinessResultTask<TaskManagerList> select(@RequestParam("taskId") String taskId);

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  BusinessResultTask<List<TaskManagerList>> list();
}
