CREATE TABLE `work_leave` (
  `id` varchar(36) NOT NULL,
  `msg` varchar(200) NOT NULL DEFAULT '' COMMENT '请假信息',
  `start_date` date NOT NULL COMMENT '请假开始日期',
  `end_date` date NOT NULL COMMENT '请假结速日期',
  `leave_day` int(1) NOT NULL DEFAULT '0' COMMENT '请假天数',
  `leave_user_id` varchar(200) NOT NULL DEFAULT '' COMMENT '请假者ID',
  `leave_user_name` varchar(200) NOT NULL DEFAULT '' COMMENT '请假者名称',
  `create_user_id` varchar(200) NOT NULL DEFAULT '' COMMENT '创建人 ID',
  `create_user_name` varchar(200) NOT NULL DEFAULT '' COMMENT '创建人 名称',
  `process_instance` varchar(36) NOT NULL DEFAULT '' COMMENT '流程实例ID',
  `remark` varchar(200) DEFAULT '' COMMENT '备注', 
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假流程详细信息';





