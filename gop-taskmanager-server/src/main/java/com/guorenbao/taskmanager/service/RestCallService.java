package com.guorenbao.taskmanager.service;

import com.guorenbao.taskmanager.client.TaskManagerClient;
import com.guorenbao.taskmanager.client.param.BusinessResultTask;
import com.guorenbao.taskmanager.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestCallService {

  @Autowired
  RestTemplate restTemplate;

  public Object call(String ipPort, Path path) {
    BusinessResultTask result = restTemplate.getForObject(
        "http://" + ipPort + path.getPath(), BusinessResultTask.class);
    if (result.getIsSuccess()) {
      return result.getData();
    } else {
      throw new BusinessException(result.getMsg());
    }
  }

  public enum Path {
    CHECK_SERVICE(TaskManagerClient.path + "/isWorking"),
    CHECK_TASK(TaskManagerClient.path + "/isRunning"),;

    private String path;

    Path(String path) {
      this.path = path;
    }

    public String getPath() {
      return path;
    }

    public Path withParams(String... keyValue) {
      if (keyValue.length > 0) {
        StringBuilder stringBuilder = new StringBuilder(path);
        stringBuilder.append("?");
        for (int i = 0; i < keyValue.length; i++) {
          if (i != 0) {
            stringBuilder.append("&");
          }
          stringBuilder.append(keyValue[i]);
        }
        path = stringBuilder.toString();
      }
      return this;
    }
  }
}
