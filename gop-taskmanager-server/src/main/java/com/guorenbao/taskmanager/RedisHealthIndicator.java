package com.guorenbao.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisHealthIndicator implements HealthIndicator {

  @Autowired
  RedisTemplate<String, String> redisTemplate;

  @Override
  public Health health() {
    try {
      redisTemplate.opsForValue().set("1", "1");
      redisTemplate.opsForValue().get("1");
      redisTemplate.delete("1");
      return Health.up().build();
    } catch (Exception e) {
      return Health.down().build();
    }
  }

}