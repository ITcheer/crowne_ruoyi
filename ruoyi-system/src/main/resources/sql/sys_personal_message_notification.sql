CREATE TABLE sys_personal_message_notification (
    notification_id VARCHAR(64) PRIMARY KEY,
    user_id VARCHAR(64) NOT NULL,
    message_title VARCHAR(256) NOT NULL,
    message_content VARCHAR(1024),
    read_status BOOLEAN DEFAULT FALSE,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notification_type VARCHAR(64) NOT NULL,
    notification_source VARCHAR(64) NOT NULL
);

-- 示例数据
INSERT INTO sys_personal_message_notification (notification_id, user_id, message_title, message_content, read_status, create_time, notification_type, notification_source) VALUES ('1', '26', '系统更新通知', '系统将于2023-10-05进行更新，请提前保存工作。', FALSE, '2023-10-01 09:00:00', '系统通知', '系统');
INSERT INTO sys_personal_message_notification (notification_id, user_id, message_title, message_content, read_status, create_time, notification_type, notification_source) VALUES ('2', '27', '密码即将过期', '您的密码将于2023-10-10过期，请及时更新密码。', FALSE, '2023-10-02 10:00:00', '安全通知', '系统');
INSERT INTO sys_personal_message_notification (notification_id, user_id, message_title, message_content, read_status, create_time, notification_type, notification_source) VALUES ('3', '28', '新任务分配', '您有一个新的任务，请尽快查看并处理。', FALSE, '2023-10-03 11:00:00', '任务通知', '系统');
