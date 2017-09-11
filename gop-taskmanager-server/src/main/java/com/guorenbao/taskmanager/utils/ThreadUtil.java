package com.guorenbao.taskmanager.utils;

public class ThreadUtil {
  public static void sleep(long t) {
    try {
      Thread.sleep(t);
    } catch (InterruptedException ignored) {
    }
  }
}
