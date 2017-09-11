package com.guorenbao.taskmanager.utils;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.guorenbao.taskmanager.exception.BusinessException;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cronutils.model.CronType.QUARTZ;

public class CronUtil {

  public static final String commonTimeFormat = "yyyy-MM-dd HH:mm:ss";
  private static final CronParser quartzCronParser =
      new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));
  private static DateTimeFormatter commonTimeFormatter = DateTimeFormatter.ofPattern(commonTimeFormat);

  public static final String formatDate(Date date) {
    return new SimpleDateFormat(commonTimeFormat).format(date);
  }

  public static Cron check(String cronExpression) {
    try {
      return quartzCronParser.parse(cronExpression);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
  }

  public static Date lastExecutionTime(String cronExpression) {
    try {
      return new SimpleDateFormat(commonTimeFormat).parse(
          ExecutionTime.forCron(check(cronExpression))
                       .lastExecution(ZonedDateTime.now()).get()
                       .toLocalDateTime()
                       .format(commonTimeFormatter));
    } catch (ParseException e) {
      throw new BusinessException(e.getMessage());
    }
  }

  public static Date nextExecutionTime(String cronExpression) {
    try {
      return new SimpleDateFormat(commonTimeFormat).parse(
          ExecutionTime.forCron(check(cronExpression))
                       .nextExecution(ZonedDateTime.now()).get()
                       .toLocalDateTime()
                       .format(commonTimeFormatter));
    } catch (ParseException e) {
      throw new BusinessException(e.getMessage());
    }
  }


  public static void main(String[] args) throws Exception {
    System.out.println(lastExecutionTime("0 0/10 * * * ?"));
    System.out.println(nextExecutionTime("0 0/10 * * * ?"));
  }
}