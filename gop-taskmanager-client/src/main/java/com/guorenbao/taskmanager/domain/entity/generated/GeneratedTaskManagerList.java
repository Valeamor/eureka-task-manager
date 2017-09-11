package com.guorenbao.taskmanager.domain.entity.generated;

import com.guorenbao.taskmanager.domain.enums.TaskManagerListStatus;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class GeneratedTaskManagerList implements Serializable {
  /***/
  protected Integer id;

  /**
   * 任务id
   */
  protected String taskId;

  /**
   * 0-DEFAULT 1-RUNNING 2-INVALID
   */
  protected TaskManagerListStatus status;

  /**
   * 执行节点
   */
  protected String runNode;

  /**
   * cron表达式
   */
  protected String cronExpression;

  /**
   * 预计下次运行时间
   */
  protected Date nextRunTime;

  /**
   * 上次任务运行毫秒数
   */
  protected Long lastRunDuration;

  /**
   * 任务执行平均毫秒数
   */
  protected Long avgRunDuration;

  /**
   * 上次成功时间
   */
  protected Date lastSuccessTime;

  /**
   * 成功次数
   */
  protected Integer successSum;

  /**
   * 上次失败时间
   */
  protected Date lastFailureTime;

  /**
   * 失败次数
   */
  protected Integer failureSum;

  /**
   * 创建时间
   */
  protected Date createTime;

  /**
   * 更新时间
   */
  protected Date updateTime;

  /**
   * 其它设置 ，json格式
   */
  protected String configuration;

  private static final long serialVersionUID = 1L;
}