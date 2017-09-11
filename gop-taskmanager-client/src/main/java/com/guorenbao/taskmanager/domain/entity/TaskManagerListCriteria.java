package com.guorenbao.taskmanager.domain.entity;

import com.guorenbao.taskmanager.domain.enums.TaskManagerListStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManagerListCriteria implements Serializable {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  protected Integer offSet;

  protected Integer pageSize;

  private static final long serialVersionUID = 1L;

  public TaskManagerListCriteria() {
    oredCriteria = new ArrayList<Criteria>();
  }

  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  public String getOrderByClause() {
    return orderByClause;
  }

  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  public void setOffSet(Integer offSet) {
    this.offSet = offSet;
  }

  public Integer getOffSet() {
    return offSet;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  protected abstract static class GeneratedCriteria implements Serializable {
    protected List<Criterion> statusCriteria;

    protected List<Criterion> allCriteria;

    protected List<Criterion> criteria;

    private static final long serialVersionUID = 1L;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<Criterion>();
      statusCriteria = new ArrayList<Criterion>();
    }

    public List<Criterion> getStatusCriteria() {
      return statusCriteria;
    }

    protected void addStatusCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      statusCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
      allCriteria = null;
    }

    protected void addStatusCriterion(String condition, TaskManagerListStatus value1, TaskManagerListStatus value2,
                                      String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      statusCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
      allCriteria = null;
    }

    public boolean isValid() {
      return criteria.size() > 0
             || statusCriteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      if (allCriteria == null) {
        allCriteria = new ArrayList<Criterion>();
        allCriteria.addAll(criteria);
        allCriteria.addAll(statusCriteria);
      }
      return allCriteria;
    }

    public List<Criterion> getCriteria() {
      return criteria;
    }

    protected void addCriterion(String condition) {
      if (condition == null) {
        throw new RuntimeException("Value for condition cannot be null");
      }
      criteria.add(new Criterion(condition));
      allCriteria = null;
    }

    protected void addCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value));
      allCriteria = null;
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value1, value2));
      allCriteria = null;
    }

    public Criteria andIdIsNull() {
      addCriterion("id is null");
      return (Criteria) this;
    }

    public Criteria andIdIsNotNull() {
      addCriterion("id is not null");
      return (Criteria) this;
    }

    public Criteria andIdEqualTo(Integer value) {
      addCriterion("id =", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotEqualTo(Integer value) {
      addCriterion("id <>", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdGreaterThan(Integer value) {
      addCriterion("id >", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("id >=", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdLessThan(Integer value) {
      addCriterion("id <", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdLessThanOrEqualTo(Integer value) {
      addCriterion("id <=", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdIn(List<Integer> values) {
      addCriterion("id in", values, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotIn(List<Integer> values) {
      addCriterion("id not in", values, "id");
      return (Criteria) this;
    }

    public Criteria andIdBetween(Integer value1, Integer value2) {
      addCriterion("id between", value1, value2, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotBetween(Integer value1, Integer value2) {
      addCriterion("id not between", value1, value2, "id");
      return (Criteria) this;
    }

    public Criteria andTaskIdIsNull() {
      addCriterion("task_id is null");
      return (Criteria) this;
    }

    public Criteria andTaskIdIsNotNull() {
      addCriterion("task_id is not null");
      return (Criteria) this;
    }

    public Criteria andTaskIdEqualTo(String value) {
      addCriterion("task_id =", value, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdNotEqualTo(String value) {
      addCriterion("task_id <>", value, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdGreaterThan(String value) {
      addCriterion("task_id >", value, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdGreaterThanOrEqualTo(String value) {
      addCriterion("task_id >=", value, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdLessThan(String value) {
      addCriterion("task_id <", value, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdLessThanOrEqualTo(String value) {
      addCriterion("task_id <=", value, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdLike(String value) {
      addCriterion("task_id like", value, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdNotLike(String value) {
      addCriterion("task_id not like", value, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdIn(List<String> values) {
      addCriterion("task_id in", values, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdNotIn(List<String> values) {
      addCriterion("task_id not in", values, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdBetween(String value1, String value2) {
      addCriterion("task_id between", value1, value2, "taskId");
      return (Criteria) this;
    }

    public Criteria andTaskIdNotBetween(String value1, String value2) {
      addCriterion("task_id not between", value1, value2, "taskId");
      return (Criteria) this;
    }

    public Criteria andStatusIsNull() {
      addCriterion("status is null");
      return (Criteria) this;
    }

    public Criteria andStatusIsNotNull() {
      addCriterion("status is not null");
      return (Criteria) this;
    }

    public Criteria andStatusEqualTo(TaskManagerListStatus value) {
      addStatusCriterion("status =", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotEqualTo(TaskManagerListStatus value) {
      addStatusCriterion("status <>", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThan(TaskManagerListStatus value) {
      addStatusCriterion("status >", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThanOrEqualTo(TaskManagerListStatus value) {
      addStatusCriterion("status >=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThan(TaskManagerListStatus value) {
      addStatusCriterion("status <", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThanOrEqualTo(TaskManagerListStatus value) {
      addStatusCriterion("status <=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusIn(List<TaskManagerListStatus> values) {
      addStatusCriterion("status in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotIn(List<TaskManagerListStatus> values) {
      addStatusCriterion("status not in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusBetween(TaskManagerListStatus value1, TaskManagerListStatus value2) {
      addStatusCriterion("status between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotBetween(TaskManagerListStatus value1, TaskManagerListStatus value2) {
      addStatusCriterion("status not between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andRunNodeIsNull() {
      addCriterion("run_node is null");
      return (Criteria) this;
    }

    public Criteria andRunNodeIsNotNull() {
      addCriterion("run_node is not null");
      return (Criteria) this;
    }

    public Criteria andRunNodeEqualTo(String value) {
      addCriterion("run_node =", value, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeNotEqualTo(String value) {
      addCriterion("run_node <>", value, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeGreaterThan(String value) {
      addCriterion("run_node >", value, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeGreaterThanOrEqualTo(String value) {
      addCriterion("run_node >=", value, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeLessThan(String value) {
      addCriterion("run_node <", value, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeLessThanOrEqualTo(String value) {
      addCriterion("run_node <=", value, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeLike(String value) {
      addCriterion("run_node like", value, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeNotLike(String value) {
      addCriterion("run_node not like", value, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeIn(List<String> values) {
      addCriterion("run_node in", values, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeNotIn(List<String> values) {
      addCriterion("run_node not in", values, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeBetween(String value1, String value2) {
      addCriterion("run_node between", value1, value2, "runNode");
      return (Criteria) this;
    }

    public Criteria andRunNodeNotBetween(String value1, String value2) {
      addCriterion("run_node not between", value1, value2, "runNode");
      return (Criteria) this;
    }

    public Criteria andCronExpressionIsNull() {
      addCriterion("cron_expression is null");
      return (Criteria) this;
    }

    public Criteria andCronExpressionIsNotNull() {
      addCriterion("cron_expression is not null");
      return (Criteria) this;
    }

    public Criteria andCronExpressionEqualTo(String value) {
      addCriterion("cron_expression =", value, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionNotEqualTo(String value) {
      addCriterion("cron_expression <>", value, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionGreaterThan(String value) {
      addCriterion("cron_expression >", value, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionGreaterThanOrEqualTo(String value) {
      addCriterion("cron_expression >=", value, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionLessThan(String value) {
      addCriterion("cron_expression <", value, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionLessThanOrEqualTo(String value) {
      addCriterion("cron_expression <=", value, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionLike(String value) {
      addCriterion("cron_expression like", value, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionNotLike(String value) {
      addCriterion("cron_expression not like", value, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionIn(List<String> values) {
      addCriterion("cron_expression in", values, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionNotIn(List<String> values) {
      addCriterion("cron_expression not in", values, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionBetween(String value1, String value2) {
      addCriterion("cron_expression between", value1, value2, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andCronExpressionNotBetween(String value1, String value2) {
      addCriterion("cron_expression not between", value1, value2, "cronExpression");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeIsNull() {
      addCriterion("next_run_time is null");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeIsNotNull() {
      addCriterion("next_run_time is not null");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeEqualTo(Date value) {
      addCriterion("next_run_time =", value, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeNotEqualTo(Date value) {
      addCriterion("next_run_time <>", value, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeGreaterThan(Date value) {
      addCriterion("next_run_time >", value, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("next_run_time >=", value, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeLessThan(Date value) {
      addCriterion("next_run_time <", value, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeLessThanOrEqualTo(Date value) {
      addCriterion("next_run_time <=", value, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeIn(List<Date> values) {
      addCriterion("next_run_time in", values, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeNotIn(List<Date> values) {
      addCriterion("next_run_time not in", values, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeBetween(Date value1, Date value2) {
      addCriterion("next_run_time between", value1, value2, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andNextRunTimeNotBetween(Date value1, Date value2) {
      addCriterion("next_run_time not between", value1, value2, "nextRunTime");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationIsNull() {
      addCriterion("last_run_duration is null");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationIsNotNull() {
      addCriterion("last_run_duration is not null");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationEqualTo(Long value) {
      addCriterion("last_run_duration =", value, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationNotEqualTo(Long value) {
      addCriterion("last_run_duration <>", value, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationGreaterThan(Long value) {
      addCriterion("last_run_duration >", value, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationGreaterThanOrEqualTo(Long value) {
      addCriterion("last_run_duration >=", value, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationLessThan(Long value) {
      addCriterion("last_run_duration <", value, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationLessThanOrEqualTo(Long value) {
      addCriterion("last_run_duration <=", value, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationIn(List<Long> values) {
      addCriterion("last_run_duration in", values, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationNotIn(List<Long> values) {
      addCriterion("last_run_duration not in", values, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationBetween(Long value1, Long value2) {
      addCriterion("last_run_duration between", value1, value2, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastRunDurationNotBetween(Long value1, Long value2) {
      addCriterion("last_run_duration not between", value1, value2, "lastRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationIsNull() {
      addCriterion("avg_run_duration is null");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationIsNotNull() {
      addCriterion("avg_run_duration is not null");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationEqualTo(Long value) {
      addCriterion("avg_run_duration =", value, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationNotEqualTo(Long value) {
      addCriterion("avg_run_duration <>", value, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationGreaterThan(Long value) {
      addCriterion("avg_run_duration >", value, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationGreaterThanOrEqualTo(Long value) {
      addCriterion("avg_run_duration >=", value, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationLessThan(Long value) {
      addCriterion("avg_run_duration <", value, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationLessThanOrEqualTo(Long value) {
      addCriterion("avg_run_duration <=", value, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationIn(List<Long> values) {
      addCriterion("avg_run_duration in", values, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationNotIn(List<Long> values) {
      addCriterion("avg_run_duration not in", values, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationBetween(Long value1, Long value2) {
      addCriterion("avg_run_duration between", value1, value2, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andAvgRunDurationNotBetween(Long value1, Long value2) {
      addCriterion("avg_run_duration not between", value1, value2, "avgRunDuration");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeIsNull() {
      addCriterion("last_success_time is null");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeIsNotNull() {
      addCriterion("last_success_time is not null");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeEqualTo(Date value) {
      addCriterion("last_success_time =", value, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeNotEqualTo(Date value) {
      addCriterion("last_success_time <>", value, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeGreaterThan(Date value) {
      addCriterion("last_success_time >", value, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("last_success_time >=", value, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeLessThan(Date value) {
      addCriterion("last_success_time <", value, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeLessThanOrEqualTo(Date value) {
      addCriterion("last_success_time <=", value, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeIn(List<Date> values) {
      addCriterion("last_success_time in", values, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeNotIn(List<Date> values) {
      addCriterion("last_success_time not in", values, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeBetween(Date value1, Date value2) {
      addCriterion("last_success_time between", value1, value2, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andLastSuccessTimeNotBetween(Date value1, Date value2) {
      addCriterion("last_success_time not between", value1, value2, "lastSuccessTime");
      return (Criteria) this;
    }

    public Criteria andSuccessSumIsNull() {
      addCriterion("success_sum is null");
      return (Criteria) this;
    }

    public Criteria andSuccessSumIsNotNull() {
      addCriterion("success_sum is not null");
      return (Criteria) this;
    }

    public Criteria andSuccessSumEqualTo(Integer value) {
      addCriterion("success_sum =", value, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumNotEqualTo(Integer value) {
      addCriterion("success_sum <>", value, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumGreaterThan(Integer value) {
      addCriterion("success_sum >", value, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumGreaterThanOrEqualTo(Integer value) {
      addCriterion("success_sum >=", value, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumLessThan(Integer value) {
      addCriterion("success_sum <", value, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumLessThanOrEqualTo(Integer value) {
      addCriterion("success_sum <=", value, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumIn(List<Integer> values) {
      addCriterion("success_sum in", values, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumNotIn(List<Integer> values) {
      addCriterion("success_sum not in", values, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumBetween(Integer value1, Integer value2) {
      addCriterion("success_sum between", value1, value2, "successSum");
      return (Criteria) this;
    }

    public Criteria andSuccessSumNotBetween(Integer value1, Integer value2) {
      addCriterion("success_sum not between", value1, value2, "successSum");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeIsNull() {
      addCriterion("last_failure_time is null");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeIsNotNull() {
      addCriterion("last_failure_time is not null");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeEqualTo(Date value) {
      addCriterion("last_failure_time =", value, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeNotEqualTo(Date value) {
      addCriterion("last_failure_time <>", value, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeGreaterThan(Date value) {
      addCriterion("last_failure_time >", value, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("last_failure_time >=", value, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeLessThan(Date value) {
      addCriterion("last_failure_time <", value, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeLessThanOrEqualTo(Date value) {
      addCriterion("last_failure_time <=", value, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeIn(List<Date> values) {
      addCriterion("last_failure_time in", values, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeNotIn(List<Date> values) {
      addCriterion("last_failure_time not in", values, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeBetween(Date value1, Date value2) {
      addCriterion("last_failure_time between", value1, value2, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andLastFailureTimeNotBetween(Date value1, Date value2) {
      addCriterion("last_failure_time not between", value1, value2, "lastFailureTime");
      return (Criteria) this;
    }

    public Criteria andFailureSumIsNull() {
      addCriterion("failure_sum is null");
      return (Criteria) this;
    }

    public Criteria andFailureSumIsNotNull() {
      addCriterion("failure_sum is not null");
      return (Criteria) this;
    }

    public Criteria andFailureSumEqualTo(Integer value) {
      addCriterion("failure_sum =", value, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumNotEqualTo(Integer value) {
      addCriterion("failure_sum <>", value, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumGreaterThan(Integer value) {
      addCriterion("failure_sum >", value, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumGreaterThanOrEqualTo(Integer value) {
      addCriterion("failure_sum >=", value, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumLessThan(Integer value) {
      addCriterion("failure_sum <", value, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumLessThanOrEqualTo(Integer value) {
      addCriterion("failure_sum <=", value, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumIn(List<Integer> values) {
      addCriterion("failure_sum in", values, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumNotIn(List<Integer> values) {
      addCriterion("failure_sum not in", values, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumBetween(Integer value1, Integer value2) {
      addCriterion("failure_sum between", value1, value2, "failureSum");
      return (Criteria) this;
    }

    public Criteria andFailureSumNotBetween(Integer value1, Integer value2) {
      addCriterion("failure_sum not between", value1, value2, "failureSum");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNull() {
      addCriterion("create_time is null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNotNull() {
      addCriterion("create_time is not null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeEqualTo(Date value) {
      addCriterion("create_time =", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotEqualTo(Date value) {
      addCriterion("create_time <>", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThan(Date value) {
      addCriterion("create_time >", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("create_time >=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThan(Date value) {
      addCriterion("create_time <", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
      addCriterion("create_time <=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIn(List<Date> values) {
      addCriterion("create_time in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotIn(List<Date> values) {
      addCriterion("create_time not in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeBetween(Date value1, Date value2) {
      addCriterion("create_time between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
      addCriterion("create_time not between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIsNull() {
      addCriterion("update_time is null");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIsNotNull() {
      addCriterion("update_time is not null");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeEqualTo(Date value) {
      addCriterion("update_time =", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotEqualTo(Date value) {
      addCriterion("update_time <>", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeGreaterThan(Date value) {
      addCriterion("update_time >", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("update_time >=", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeLessThan(Date value) {
      addCriterion("update_time <", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
      addCriterion("update_time <=", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIn(List<Date> values) {
      addCriterion("update_time in", values, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotIn(List<Date> values) {
      addCriterion("update_time not in", values, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeBetween(Date value1, Date value2) {
      addCriterion("update_time between", value1, value2, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
      addCriterion("update_time not between", value1, value2, "updateTime");
      return (Criteria) this;
    }
  }

  public static class Criteria extends GeneratedCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Criteria() {
      super();
    }
  }

  public static class Criterion implements Serializable {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    private static final long serialVersionUID = 1L;

    public String getCondition() {
      return condition;
    }

    public Object getValue() {
      return value;
    }

    public Object getSecondValue() {
      return secondValue;
    }

    public boolean isNoValue() {
      return noValue;
    }

    public boolean isSingleValue() {
      return singleValue;
    }

    public boolean isBetweenValue() {
      return betweenValue;
    }

    public boolean isListValue() {
      return listValue;
    }

    public String getTypeHandler() {
      return typeHandler;
    }

    protected Criterion(String condition) {
      super();
      this.condition = condition;
      this.typeHandler = null;
      this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.typeHandler = typeHandler;
      if (value instanceof List<?>) {
        this.listValue = true;
      } else {
        this.singleValue = true;
      }
    }

    protected Criterion(String condition, Object value) {
      this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.secondValue = secondValue;
      this.typeHandler = typeHandler;
      this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
      this(condition, value, secondValue, null);
    }
  }
}