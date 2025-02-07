CREATE TABLE sys_complaint_order (
    ISSUE_Id VARCHAR(64) PRIMARY KEY,
    Issuer_Name VARCHAR(512),
    Isssuer_Email VARCHAR(512),
    Issue_details VARCHAR(512),
    Issuer_Phone VARCHAR(512),
    facility_guy_id VARCHAR(512),
    date datetime,
    facility_guys_email VARCHAR(256),
    facility_guys_name VARCHAR(128),
    facility_guy_mobile VARCHAR(20),
    facility_guy_superviosr VARCHAR(256),
    result_message VARCHAR(1024),
    processing_status VARCHAR(64),
    __PowerAppsId__ VARCHAR(64)
);

INSERT INTO sys_complaint_order (ISSUE_Id, Issuer_Name, Isssuer_Email, Issue_details, Issuer_Phone, facility_guy_id, date, facility_guys_email, facility_guys_name, facility_guy_mobile, facility_guy_superviosr, result_message, processing_status, __PowerAppsId__) VALUES ('1015142215912', '张三', 'zhangsan@example.com', '食堂饭菜质量差', '13800138000', '1', '2024-10-15 14:00:00', 'facility@example.com', '李四', '13900139000', 'supervisor@example.com', '已改进饭菜质量', 'Resolved', '');
INSERT INTO sys_complaint_order (ISSUE_Id, Issuer_Name, Isssuer_Email, Issue_details, Issuer_Phone, facility_guy_id, date, facility_guys_email, facility_guys_name, facility_guy_mobile, facility_guy_superviosr, result_message, processing_status, __PowerAppsId__) VALUES ('1015152215912', '李四', 'lisi@example.com', '宿舍卫生状况差', '13800138001', '2', '2024-10-16 10:00:00', 'facility@example.com', '王五', '13900139001', 'supervisor@example.com', '已安排清洁人员打扫', 'Resolved', '');
INSERT INTO sys_complaint_order (ISSUE_Id, Issuer_Name, Isssuer_Email, Issue_details, Issuer_Phone, facility_guy_id, date, facility_guys_email, facility_guys_name, facility_guy_mobile, facility_guy_superviosr, result_message, processing_status, __PowerAppsId__) VALUES ('1125154539247', '王五', 'wangwu@example.com', '教室空调不制冷', '13800138002', '3', '2024-11-25 15:00:00', 'facility@example.com', '赵六', '13900139002', 'supervisor@example.com', '已修复空调', 'Resolved', '');
