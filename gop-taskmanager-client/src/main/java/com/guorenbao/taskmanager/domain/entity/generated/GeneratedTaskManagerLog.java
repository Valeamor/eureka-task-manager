package com.guorenbao.taskmanager.domain.entity.generated;

import com.guorenbao.taskmanager.domain.enums.TaskManagerLogRunType;
import com.guorenbao.taskmanager.domain.enums.TaskManagerLogStatus;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class GeneratedTaskManagerLog implements Serializable {
  /***/
  protected Integer id;

  /**
   * 任务id
   */
  protected String taskId;

  /**
   * 执行节点
   */
  protected String runNode;

  /**
   * 0-CRON(定时任务) 1-MANUAL(人工触发任务)
   */
  protected TaskManagerLogRunType runType;

  /**
   * 0-UNKNOWN 1-START 2-SUCCESS 3-FAILURE
   */
  protected TaskManagerLogStatus status;

  /**
   * 执行时间(ms)
   */
  protected Long duration;

  /**
   * 创建时间
   */
  protected Date createTime;

  private static final long serialVersionUID = 1L;
}