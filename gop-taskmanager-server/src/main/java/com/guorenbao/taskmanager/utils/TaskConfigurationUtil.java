package com.guorenbao.taskmanager.utils;

import com.alibaba.fastjson.JSONObject;
import com.guorenbao.taskmanager.domain.entity.TaskManagerList;
import com.guorenbao.taskmanager.exception.BusinessException;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.guorenbao.taskmanager.utils.TaskConfigurationUtil.Configs.IS_MANUAL_RUN;
import static com.guorenbao.taskmanager.utils.TaskConfigurationUtil.Configs.IS_RUN_ONCE;
import static com.guorenbao.taskmanager.utils.TaskConfigurationUtil.Configs.RETRY_STRATEGY;
import static com.guorenbao.taskmanager.utils.TaskConfigurationUtil.Configs.RETRY_TIMES_LEFT;

public class TaskConfigurationUtil {
  private JSONObject config;
  private TaskManagerList task;

  public static TaskConfigurationUtil create(TaskManagerList task) {
    return new TaskConfigurationUtil(task);
  }

  private TaskConfigurationUtil(TaskManagerList task) {
    this.task = task;
    this.config = Optional.ofNullable(JSONObject.parseObject(task.getConfiguration())).orElse(new JSONObject());
  }

  public TaskManagerList set(Configs key, Object value) {
    if (value == null) {
      config.remove(key.name());
    } else {
      key.checkConfig(value.toString());
      config.put(key.name(), value.toString());
    }
    return task;
  }

  public void check() {
    config.forEach((key, value) -> Configs.valueOf(key).checkConfig(value.toString()));
  }

  public boolean isManualRun() {
    return IS_MANUAL_RUN.equal(config, 1);
  }

  public boolean isRunOnce() {
    return IS_RUN_ONCE.equal(config, 1);
  }

  public Integer getRetryTimes() {
    Integer defaultRetryTimes = RETRY_STRATEGY.getInteger(config, 2, null);
    if (defaultRetryTimes != null) {
      return RETRY_TIMES_LEFT.getInteger(config, 0, defaultRetryTimes);
    } else {
      return RETRY_STRATEGY.equal(config, 1) ? 1 : 0;
    }
  }

  public enum Configs {
    IS_MANUAL_RUN("false", "true"),
    IS_RUN_ONCE("false", "true"),
    RETRY_STRATEGY("never"/*不重试，默认策略*/,
                   "forever"/*一直重试直到成功*/,
                   "limited\\:([1-9]\\d*)"/*有限次数重试*/),
    RETRY_TIMES_LEFT("(\\d+)");
    String[] args;

    Configs(String... args) {
      this.args = args;
    }

    public boolean equal(JSONObject config, int index) {
      return Pattern.compile(args[index]).matcher(config.getString(name())).matches();
    }

    public boolean match(JSONObject config, int index) {
      return Pattern.compile(args[index]).matcher(config.getString(name())).matches();
    }

    public Integer getInteger(JSONObject config, int index, Integer defaultValue) {
      String configStr = config.getString(name());
      if (configStr == null) {
        return defaultValue;
      }
      Matcher matcher = Pattern.compile(args[index]).matcher(configStr);
      return matcher.find() ? Integer.valueOf(matcher.group(1)) : defaultValue;
    }

    public void checkConfig(String configStr) {
      for (String arg : args) {
        if (configStr.matches(arg)) {
          return;
        }
      }
      throw new BusinessException("key:" + name() + "的值:" + configStr + "不在指定的值:" + Arrays.toString(args) + "中");
    }
  }
}
