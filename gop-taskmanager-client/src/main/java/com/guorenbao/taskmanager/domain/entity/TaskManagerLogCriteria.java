package com.guorenbao.taskmanager.domain.entity;

import com.guorenbao.taskmanager.domain.enums.TaskManagerLogRunType;
import com.guorenbao.taskmanager.domain.enums.TaskManagerLogStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManagerLogCriteria implements Serializable {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  protected Integer offSet;

  protected Integer pageSize;

  private static final long serialVersionUID = 1L;

  public TaskManagerLogCriteria() {
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
    protected List<Criterion> runTypeCriteria;

    protected List<Criterion> statusCriteria;

    protected List<Criterion> allCriteria;

    protected List<Criterion> criteria;

    private static final long serialVersionUID = 1L;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<Criterion>();
      runTypeCriteria = new ArrayList<Criterion>();
      statusCriteria = new ArrayList<Criterion>();
    }

    public List<Criterion> getRunTypeCriteria() {
      return runTypeCriteria;
    }

    protected void addRunTypeCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      runTypeCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
      allCriteria = null;
    }

    protected void addRunTypeCriterion(String condition, TaskManagerLogRunType value1, TaskManagerLogRunType value2,
                                       String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      runTypeCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
      allCriteria = null;
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

    protected void addStatusCriterion(String condition, TaskManagerLogStatus value1, TaskManagerLogStatus value2,
                                      String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      statusCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
      allCriteria = null;
    }

    public boolean isValid() {
      return criteria.size() > 0
             || runTypeCriteria.size() > 0
             || statusCriteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      if (allCriteria == null) {
        allCriteria = new ArrayList<Criterion>();
        allCriteria.addAll(criteria);
        allCriteria.addAll(runTypeCriteria);
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

    public Criteria andRunTypeIsNull() {
      addCriterion("run_type is null");
      return (Criteria) this;
    }

    public Criteria andRunTypeIsNotNull() {
      addCriterion("run_type is not null");
      return (Criteria) this;
    }

    public Criteria andRunTypeEqualTo(TaskManagerLogRunType value) {
      addRunTypeCriterion("run_type =", value, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeNotEqualTo(TaskManagerLogRunType value) {
      addRunTypeCriterion("run_type <>", value, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeGreaterThan(TaskManagerLogRunType value) {
      addRunTypeCriterion("run_type >", value, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeGreaterThanOrEqualTo(TaskManagerLogRunType value) {
      addRunTypeCriterion("run_type >=", value, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeLessThan(TaskManagerLogRunType value) {
      addRunTypeCriterion("run_type <", value, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeLessThanOrEqualTo(TaskManagerLogRunType value) {
      addRunTypeCriterion("run_type <=", value, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeIn(List<TaskManagerLogRunType> values) {
      addRunTypeCriterion("run_type in", values, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeNotIn(List<TaskManagerLogRunType> values) {
      addRunTypeCriterion("run_type not in", values, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeBetween(TaskManagerLogRunType value1, TaskManagerLogRunType value2) {
      addRunTypeCriterion("run_type between", value1, value2, "runType");
      return (Criteria) this;
    }

    public Criteria andRunTypeNotBetween(TaskManagerLogRunType value1, TaskManagerLogRunType value2) {
      addRunTypeCriterion("run_type not between", value1, value2, "runType");
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

    public Criteria andStatusEqualTo(TaskManagerLogStatus value) {
      addStatusCriterion("status =", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotEqualTo(TaskManagerLogStatus value) {
      addStatusCriterion("status <>", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThan(TaskManagerLogStatus value) {
      addStatusCriterion("status >", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThanOrEqualTo(TaskManagerLogStatus value) {
      addStatusCriterion("status >=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThan(TaskManagerLogStatus value) {
      addStatusCriterion("status <", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThanOrEqualTo(TaskManagerLogStatus value) {
      addStatusCriterion("status <=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusIn(List<TaskManagerLogStatus> values) {
      addStatusCriterion("status in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotIn(List<TaskManagerLogStatus> values) {
      addStatusCriterion("status not in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusBetween(TaskManagerLogStatus value1, TaskManagerLogStatus value2) {
      addStatusCriterion("status between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotBetween(TaskManagerLogStatus value1, TaskManagerLogStatus value2) {
      addStatusCriterion("status not between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andDurationIsNull() {
      addCriterion("duration is null");
      return (Criteria) this;
    }

    public Criteria andDurationIsNotNull() {
      addCriterion("duration is not null");
      return (Criteria) this;
    }

    public Criteria andDurationEqualTo(Long value) {
      addCriterion("duration =", value, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationNotEqualTo(Long value) {
      addCriterion("duration <>", value, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationGreaterThan(Long value) {
      addCriterion("duration >", value, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationGreaterThanOrEqualTo(Long value) {
      addCriterion("duration >=", value, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationLessThan(Long value) {
      addCriterion("duration <", value, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationLessThanOrEqualTo(Long value) {
      addCriterion("duration <=", value, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationIn(List<Long> values) {
      addCriterion("duration in", values, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationNotIn(List<Long> values) {
      addCriterion("duration not in", values, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationBetween(Long value1, Long value2) {
      addCriterion("duration between", value1, value2, "duration");
      return (Criteria) this;
    }

    public Criteria andDurationNotBetween(Long value1, Long value2) {
      addCriterion("duration not between", value1, value2, "duration");
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