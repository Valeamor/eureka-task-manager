package com.guorenbao.taskmanager.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sion on 17.4.21.
 */
public abstract class TaskBase {
  private Logger logger = LoggerFactory.getLogger(getClass());
  private long savedTime;
  private long startTime;
  private StringBuffer logs = new StringBuffer();

  protected void startLog() {
    logs.setLength(0);
    startTime = System.currentTimeMillis();
    savedTime = startTime;
  }

  protected void printLog() {
    savedTime = startTime;
    writeLogsWithTimeCost("Total");
    logger.info(logs.toString());
    logs.setLength(0);
  }

  protected void writeLogsWithTimeCost(String str) {
    long t = System.currentTimeMillis();
    logs.append("\n").append(str).append(": ").append(t - savedTime).append("ms ");
    savedTime = t;
  }

  public abstract void run() throws Exception;
}
