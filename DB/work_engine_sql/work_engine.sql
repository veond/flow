CREATE TABLE `work_model` (
  `id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL DEFAULT '' COMMENT '模型名称',
  `file_path` varchar(200) NOT NULL DEFAULT '' COMMENT '模型生成文件的路径', 
  `remark` varchar(200) DEFAULT '' COMMENT '备注', 
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作引擎模型';


CREATE TABLE `work_item` (
  `id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL DEFAULT '' COMMENT '模型名称',
  `work_model_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联工作模型ID',
  `item_type` int(1) NOT NULL DEFAULT '0' COMMENT '工作项的类型 0:开始  1:结束  2:连接线 3:手工活动',
  `condition` varchar(200) DEFAULT '' COMMENT '节点条件（变量）',
  `partake_user` varchar(200) NOT NULL DEFAULT '' COMMENT '参数者（变量）',
  `remark` varchar(200) DEFAULT '' COMMENT '备注', 
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作项信息';


CREATE TABLE `prepose_item` (
  `id` varchar(36) NOT NULL,
  `item_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联工作项ID',
  `prepose_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联前置节点的工作项ID', 
  `remark` varchar(200) DEFAULT '' COMMENT '备注', 
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作项前置节点关联表';


CREATE TABLE `postposition_item` (
  `id` varchar(36) NOT NULL,
  `item_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联工作项ID',
  `postposition_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联后置节点的工作项ID', 
  `remark` varchar(200) DEFAULT '' COMMENT '备注', 
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作项后置节点关联表';


CREATE TABLE `process_instance` (
  `id` varchar(36) NOT NULL,
  `work_model_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联工作模型ID',
  `work_item_id` varchar(36) NOT NULL DEFAULT '' COMMENT '当前流程所在节点ID',
  `remark` varchar(200) DEFAULT '' COMMENT '备注', 
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流， 流程信息';


CREATE TABLE `work_todo` (
  `id` varchar(36) NOT NULL,
  `work_model_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联工作模型ID',
  `work_item_id` varchar(36) NOT NULL DEFAULT '' COMMENT '工作项ID',
  `work_item_name` varchar(200) NOT NULL DEFAULT '' COMMENT '模型名称',
  `process_instance` varchar(36) NOT NULL DEFAULT '' COMMENT '流程实例ID',
  `partake_user` varchar(200) NOT NULL DEFAULT '' COMMENT '参数者ID',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '流程状态',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待办信息（记录需要处理的工作流）';


CREATE TABLE `work_have_todo` (
  `id` varchar(36) NOT NULL,
  `work_model_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联工作模型ID',
  `work_item_id` varchar(36) NOT NULL DEFAULT '' COMMENT '工作项ID',
  `work_item_name` varchar(200) NOT NULL DEFAULT '' COMMENT '模型名称',
  `process_instance` varchar(36) NOT NULL DEFAULT '' COMMENT '流程实例ID',
  `partake_user` varchar(200) NOT NULL DEFAULT '' COMMENT '参数者ID',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '流程状态',
  `handle_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '处理时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='已办信息（记录处理完成的流程）';


CREATE TABLE `work_from` (
  `id` varchar(36) NOT NULL,
  `url` varchar(200) NOT NULL DEFAULT '' COMMENT '处理表单的URL',
  `from_type` int(2) NOT NULL DEFAULT '1' COMMENT '表单URL的类型，1、模板页面直接展现，  2、跳转URL',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作项处理表单信息';







