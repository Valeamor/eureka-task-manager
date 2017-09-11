package com.guorenbao.taskmanager.dao;

import com.guorenbao.common.mybatis.BaseMapper;
import com.guorenbao.common.mybatis.MybatisRepository;
import com.guorenbao.taskmanager.domain.entity.TaskManagerList;
import com.guorenbao.taskmanager.domain.entity.TaskManagerListCriteria;

import java.util.List;

@MybatisRepository
public interface TaskManagerListMapper extends BaseMapper<Integer, TaskManagerList, TaskManagerListCriteria> {
  List<TaskManagerList> selectByExampleWithBLOBs(TaskManagerListCriteria criteria);
}