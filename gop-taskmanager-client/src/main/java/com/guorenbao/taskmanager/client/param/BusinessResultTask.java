package com.guorenbao.taskmanager.client.param;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Sion on 17.4.5.
 */
@Data
@ToString
public class BusinessResultTask<T> implements Serializable {
  private static final long serialVersionUID = -1L;
  private T data;
  private Boolean isSuccess;
  private String msg;
  private ErrorCode errorCode;

  public BusinessResultTask() {
    this(null, true, null);
  }

  public BusinessResultTask(T data) {
    this(data, true, null);
  }

  public BusinessResultTask(T data, Boolean isSuccess, String msg) {
    this(data, isSuccess, msg, ErrorCode.DEFAULT);
  }

  public BusinessResultTask(T data, Boolean isSuccess, String msg, ErrorCode errorCode) {
    this.data = data;
    this.isSuccess = isSuccess;
    this.msg = msg;
    this.errorCode = errorCode;
  }

  public enum ErrorCode {
    DEFAULT,
  }
}