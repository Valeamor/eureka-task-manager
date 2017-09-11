package com.guorenbao.taskmanager.client.param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;
import lombok.Data;

@Data
public class WorkingStatus {
  private Boolean isWorking;
  private BigDecimal avgLocalTaskDuration;
  private List<Pair<Date, Long>> log;

  public WorkingStatus() {
  }

  public WorkingStatus(BigDecimal avgLocalTaskDuration, List<Pair<Date, Long>> log, boolean isWorking) {
    this.avgLocalTaskDuration = avgLocalTaskDuration;
    this.log = log;
    this.isWorking = isWorking;
  }
}
