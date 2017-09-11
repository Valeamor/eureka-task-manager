package com.guorenbao.taskmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 *
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {
  @Value("${redis.ip}")
  private String host;
  @Value("${redis.port}")
  private int port;
  @Value("${redis.maxTotal}")
  private int maxTotal;
  @Value("${redis.maxIdle}")
  private int maxIdle;
  @Value("${redis.maxWaitMillis}")
  private int maxWaitMillis;

  @Bean
  public JedisPoolConfig getJedisPoolConfig() {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxIdle(maxIdle);
    config.setMaxTotal(maxTotal);
    config.setMaxWaitMillis(maxWaitMillis);
    config.setTestOnBorrow(true);
    config.setTestOnReturn(true);
    return config;
  }

  @Bean
  public JedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
    JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
    redisConnectionFactory.setHostName(host);
    redisConnectionFactory.setPort(port);
    redisConnectionFactory.setUsePool(true);
    System.out.println("*************************************************");
    System.out.println("redis config host: " + host + " port: " + port);
    System.out.println("*************************************************");
    return redisConnectionFactory;
  }

  @Bean()
  public RedisTemplate redisTemplate(JedisConnectionFactory factory) {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
