package com.guorenbao.taskmanager.tasks;

import com.guorenbao.taskmanager.core.TaskBase;

import org.springframework.stereotype.Component;

@Component("TestTask")
public class TestTask extends TaskBase {

  @Override
  public void run() throws Exception {
    startLog();
    writeLogsWithTimeCost("TestTask start");
    Thread.sleep(1000);
    writeLogsWithTimeCost("TestTask end");
    printLog();
  }
}