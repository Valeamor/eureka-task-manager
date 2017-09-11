package com.guorenbao.taskmanager;


import com.alibaba.druid.pool.DruidDataSource;
import com.guorenbao.common.mybatis.MybatisRepository;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataSourceConfig.class)
@MapperScan(annotationClass = MybatisRepository.class, basePackages = "com.guorenbao.taskmanager.dao")
public class MybatisDataSourceConfig {
  @Autowired
  DataSourceConfig dataSourceProperties;

  private DruidDataSource pool;

  @Bean
  public DataSource dataSource() {
    DataSourceConfig config = dataSourceProperties;
    System.out.println();
    System.out.println("************************************************");
    System.out.println("mybatis config db.url: " + config.getUrl() + ", db.username: " + config.getUsername());
    System.out.println("************************************************");
    System.out.println();
    this.pool = new DruidDataSource();
    this.pool.setDriverClassName(config.getDriverClassName());
    this.pool.setUrl(config.getUrl());
    if (config.getUsername() != null) {
      this.pool.setUsername(config.getUsername());
    }
    if (config.getPassword() != null) {
      this.pool.setPassword(config.getPassword());
    }
    this.pool.setInitialSize(config.getInitialSize());
    this.pool.setMaxActive(config.getMaxActive());
    this.pool.setMinIdle(config.getMinIdle());
    this.pool.setTestOnBorrow(config.isTestOnBorrow());
    this.pool.setTestOnReturn(config.isTestOnReturn());
    this.pool.setValidationQuery(config.getValidationQuery());
    return this.pool;
  }

  @PreDestroy
  public void close() {
    if (this.pool != null) {
      this.pool.close();
    }
  }

  @Bean
  public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource());
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    StringBuilder mapperLocations = new StringBuilder("classpath:/mybatis/*Mapper.xml");
    StringBuilder gMapperLocations = new StringBuilder("classpath:/mybatis/generated/*Mapper.xml");
    System.out.println("mapperLocations=" + mapperLocations);
    System.out.println("gMapperLocations=" + gMapperLocations);
    Resource[] resources = resolver.getResources(mapperLocations.toString());
    Resource[] gResources = resolver.getResources(gMapperLocations.toString());
    Resource[] temp = (Resource[]) ArrayUtils.addAll(resources, gResources);
    sqlSessionFactoryBean.setMapperLocations(temp);
    Arrays.stream(temp).forEach(resource -> System.out.println("load mapper: " + resource.getFilename()));
    System.out.println("load mappers count: " + resources.length);
    return sqlSessionFactoryBean.getObject();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }
}
