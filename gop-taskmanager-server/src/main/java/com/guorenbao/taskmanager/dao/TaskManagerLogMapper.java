package com.guorenbao.taskmanager.dao;

import com.guorenbao.common.mybatis.BaseMapper;
import com.guorenbao.common.mybatis.MybatisRepository;
import com.guorenbao.taskmanager.domain.entity.TaskManagerLog;
import com.guorenbao.taskmanager.domain.entity.TaskManagerLogCriteria;

@MybatisRepository
public interface TaskManagerLogMapper extends BaseMapper<Integer, TaskManagerLog, TaskManagerLogCriteria> {
}