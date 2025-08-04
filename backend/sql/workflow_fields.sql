-- 工作流与业务数据通过工作流实例表中的business_key和business_type字段关联
-- 不在业务表中添加工作流字段，保持业务表的纯净性

-- 创建工作流实例表（如果使用自定义工作流存储的话）
CREATE TABLE IF NOT EXISTS workflow_instance (
  id VARCHAR(64) PRIMARY KEY COMMENT '实例ID',
  flow_code VARCHAR(32) NOT NULL COMMENT '流程编码',
  flow_name VARCHAR(100) NOT NULL COMMENT '流程名称', 
  current_node_code VARCHAR(64) COMMENT '当前节点编码',
  current_node_name VARCHAR(100) COMMENT '当前节点名称',
  status VARCHAR(20) DEFAULT 'RUNNING' COMMENT '实例状态：RUNNING-运行中, COMPLETED-已完成, TERMINATED-已终止',
  business_type VARCHAR(50) NOT NULL COMMENT '业务类型：PROJECT_DEMAND-项目需求, PROJECT-项目',
  business_key VARCHAR(100) NOT NULL COMMENT '业务数据ID，关联到具体业务表的主键',
  tenant_id VARCHAR(20) DEFAULT '000000' COMMENT '租户ID',
  variables JSON COMMENT '流程变量',
  create_by VARCHAR(64) COMMENT '创建者',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(64) COMMENT '更新者', 
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_flow_code (flow_code),
  INDEX idx_business_key (business_key),
  INDEX idx_business_type (business_type),
  INDEX idx_business_composite (business_type, business_key),
  INDEX idx_status (status),
  INDEX idx_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作流实例表';

-- 创建工作流任务表
CREATE TABLE IF NOT EXISTS workflow_task (
  id VARCHAR(64) PRIMARY KEY COMMENT '任务ID',
  instance_id VARCHAR(64) NOT NULL COMMENT '实例ID',
  node_code VARCHAR(64) NOT NULL COMMENT '节点编码', 
  node_name VARCHAR(100) NOT NULL COMMENT '节点名称',
  assignee VARCHAR(64) COMMENT '处理人',
  status VARCHAR(20) DEFAULT 'PENDING' COMMENT '任务状态：PENDING-待处理, COMPLETED-已完成, REJECTED-已驳回',
  start_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  end_time DATETIME COMMENT '结束时间',
  opinion TEXT COMMENT '处理意见',
  attachment VARCHAR(500) COMMENT '附件',
  create_by VARCHAR(64) COMMENT '创建者',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(64) COMMENT '更新者',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_instance_id (instance_id),
  INDEX idx_assignee (assignee),
  INDEX idx_status (status),
  FOREIGN KEY (instance_id) REFERENCES workflow_instance(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作流任务表';

-- 创建工作流历史表
CREATE TABLE IF NOT EXISTS workflow_history (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '历史ID',
  instance_id VARCHAR(64) NOT NULL COMMENT '实例ID',
  node_code VARCHAR(64) NOT NULL COMMENT '节点编码',
  node_name VARCHAR(100) NOT NULL COMMENT '节点名称', 
  handler VARCHAR(64) COMMENT '处理人',
  action VARCHAR(20) NOT NULL COMMENT '操作：SUBMIT-提交, APPROVE-通过, REJECT-驳回, COMPLETE-完成',
  opinion TEXT COMMENT '处理意见',
  attachment VARCHAR(500) COMMENT '附件',
  handle_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '处理时间',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX idx_instance_id (instance_id),
  INDEX idx_handler (handler),
  INDEX idx_handle_time (handle_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作流历史表';