package com.guorenbao.taskmanager;

import com.guorenbao.taskmanager.core.TaskManagerCore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.guorenbao.*")
@EnableEurekaClient
@EnableFeignClients
public class Application {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args)
                     .getBean("taskManagerCore", TaskManagerCore.class)
                     .run();
  }
}
