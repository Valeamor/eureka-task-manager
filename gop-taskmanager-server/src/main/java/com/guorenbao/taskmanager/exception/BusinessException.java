package com.guorenbao.taskmanager.exception;


import com.guorenbao.taskmanager.client.param.BusinessResultTask;

import static com.guorenbao.taskmanager.client.param.BusinessResultTask.ErrorCode.DEFAULT;

/**
 * Created by Sion on 17.3.29.
 */
public class BusinessException extends RuntimeException {
  public Boolean isSuccess;
  public BusinessResultTask.ErrorCode errorCode;

  public BusinessException(String msg) {
    this(msg, false, DEFAULT);
  }

  public BusinessException(String msg, Boolean isSuccess) {
    this(msg, isSuccess, DEFAULT);
  }

  public BusinessException(String msg, BusinessResultTask.ErrorCode errorCode) {
    this(msg, false, errorCode);
  }

  public BusinessException(String msg, Boolean isSuccess, BusinessResultTask.ErrorCode errorCode) {
    super(msg);
    this.isSuccess = isSuccess;
    this.errorCode = errorCode;
  }
}
