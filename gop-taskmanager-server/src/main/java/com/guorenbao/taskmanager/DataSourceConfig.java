package com.guorenbao.taskmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties
@EnableAutoConfiguration
public class DataSourceConfig {
  private String driverClassName = "com.mysql.jdbc.Driver";
  @Value("${mybatis.url}")
  private String url;
  @Value("${mybatis.username}")
  private String username;
  @Value("${mybatis.password}")
  private String password;
  private int maxActive = 100;
  private int maxIdle = 8;
  private int minIdle = 8;
  private int initialSize = 10;
  private String validationQuery;
  private boolean testOnBorrow = false;
  private boolean testOnReturn = false;
}