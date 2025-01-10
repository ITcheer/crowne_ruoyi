-- 示例数据
INSERT INTO sys_maintenance_order_log (log_id, issue_id, action_type, action_description, action_time, user_id, user_name) 
SELECT 
  CONCAT(issue_id, '_1') AS log_id, 
  issue_id, 
  '新建工单' AS action_type, 
  '工单发起' AS action_description, 
  date AS action_time, 
  '-1' AS user_id, 
  issuer_name AS user_name 
FROM sys_maintenance_order;

INSERT INTO sys_maintenance_order_log (log_id, issue_id, action_type, action_description, action_time, user_id, user_name) 
SELECT 
  CONCAT(issue_id, '_2') AS log_id, 
  issue_id, 
  '沟通' AS action_type, 
  issue_details AS action_description, 
  date AS action_time, 
  '-1' AS user_id, 
  issuer_name AS user_name 
FROM sys_maintenance_order;
