CREATE TABLE `task_manager_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(32) NOT NULL COMMENT '任务id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-DEFAULT 1-RUNNING 2-INVALID',
  `run_node` varchar(32) DEFAULT NULL COMMENT '执行节点',
  `cron_expression` varchar(32) NOT NULL COMMENT 'cron表达式',
  `next_run_time` datetime DEFAULT NULL COMMENT '预计下次运行时间',
  `last_run_duration` bigint(20) DEFAULT NULL COMMENT '上次任务运行毫秒数',
  `avg_run_duration` bigint(20) DEFAULT NULL COMMENT '任务执行平均毫秒数',
  `last_success_time` datetime DEFAULT NULL COMMENT '上次成功时间',
  `success_sum` int(11) DEFAULT '0' COMMENT '成功次数',
  `last_failure_time` datetime DEFAULT NULL COMMENT '上次失败时间',
  `failure_sum` int(11) DEFAULT '0' COMMENT '失败次数',
  `configuration` text COMMENT '其它设置 ，json格式',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `task_id_index` (`task_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务表';


CREATE TABLE `task_manager_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(32) NOT NULL COMMENT '任务id',
  `run_node` varchar(32) NOT NULL COMMENT '执行节点',
  `run_type` tinyint(4) DEFAULT NULL COMMENT '0-CRON(定时任务) 1-MANUAL(人工触发任务)',
  `status` tinyint(4) NOT NULL COMMENT '0-UNKNOWN 1-START 2-SUCCESS 3-FAILURE',
  `duration` bigint(20) DEFAULT NULL COMMENT '执行时间(ms)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `task_id_index` (`task_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务表日志表';