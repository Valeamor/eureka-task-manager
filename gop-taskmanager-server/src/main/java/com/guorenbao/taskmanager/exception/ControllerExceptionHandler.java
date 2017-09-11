package com.guorenbao.taskmanager.exception;

import com.guorenbao.taskmanager.client.param.BusinessResultTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerExceptionHandler {
  private Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(value = Throwable.class)
  @ResponseBody
  public BusinessResultTask exceptionHandler(Throwable e) {
    logger.error("task server error", e);
    return new BusinessResultTask<>(null, false, "task server error");
  }

  @ExceptionHandler(value = BusinessException.class)
  @ResponseBody
  public BusinessResultTask clientExceptionHandler(BusinessException e) {
    logger.info("BusinessException:{}, {}", e.errorCode, e.getMessage());
    return new BusinessResultTask<>(null, e.isSuccess, e.getMessage(), e.errorCode);
  }
}
