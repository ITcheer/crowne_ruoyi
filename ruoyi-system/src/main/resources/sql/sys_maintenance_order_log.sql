CREATE TABLE sys_maintenance_order_log (
    log_id VARCHAR(64) PRIMARY KEY,
    issue_id VARCHAR(64) NOT NULL,
    action_type VARCHAR(64) NOT NULL,
    action_description VARCHAR(1024),
    action_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id VARCHAR(64),
    user_name VARCHAR(128),
    FOREIGN KEY (issue_id) REFERENCES sys_maintenance_order(ISSUE_Id)
);

-- 示例数据
INSERT INTO sys_maintenance_order_log (log_id, issue_id, action_type, action_description, action_time, user_id, user_name) VALUES ('1', '919171619384', '状态变化', '工单状态从未分配变为处理中', '2023-10-01 10:00:00', '26', '罗锟杨');
INSERT INTO sys_maintenance_order_log (log_id, issue_id, action_type, action_description, action_time, user_id, user_name) VALUES ('2', '919171619384', '沟通', '与发布者沟通，确认问题详情', '2023-10-01 11:00:00', '26', '罗锟杨');
INSERT INTO sys_maintenance_order_log (log_id, issue_id, action_type, action_description, action_time, user_id, user_name) VALUES ('3', '919171619384', '转单', '工单从罗锟杨转给林嘉玮', '2023-10-01 12:00:00', '26', '罗锟杨');
