package com.guorenbao.taskmanager.service;

import com.guorenbao.taskmanager.core.TaskBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskBeanService {
  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  ApplicationContext context;

  private final Map<String, TaskBase> taskBeans = new HashMap<>();

  public TaskBase getTaskBean(String taskId) {
    TaskBase taskBean = taskBeans.get(taskId);
    try {
      if (taskBean == null) {
        taskBean = context.getBean(taskId, TaskBase.class);
        taskBeans.put(taskId, taskBean);
      }
    } catch (Exception e) {
      logger.warn("获取任务实例失败 {}", e.getMessage());
    }
    return taskBean;
  }
}
