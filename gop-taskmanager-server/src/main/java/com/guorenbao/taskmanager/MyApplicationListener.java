package com.guorenbao.taskmanager;

import com.guorenbao.taskmanager.core.TaskManagerCore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ContextStartedEvent> {
  @Autowired
  TaskManagerCore taskManagerCore;

  public void onApplicationEvent(final ContextStartedEvent event) {
    System.out.println("1111111111111111111111111111111111111111111111111111111111111111111");
//    taskManagerCore.run();
  }

}