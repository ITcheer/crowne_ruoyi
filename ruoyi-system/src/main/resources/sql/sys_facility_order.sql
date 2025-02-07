CREATE TABLE sys_facility_order (
    ISSUE_Id VARCHAR(64) PRIMARY KEY,
    Issuer_Name VARCHAR(512),
    Issue_photo VARCHAR(512),
    Isssuer_Email VARCHAR(512),
    Issue_details VARCHAR(512),
    Classroom VARCHAR(512),
    Floor VARCHAR(512),
    Maintenance_Type VARCHAR(512),
    Urgency_Level VARCHAR(512),
    Issuer_Phone VARCHAR(512),
    facility_guy_id VARCHAR(512),
    date datetime,
    facility_guys_email VARCHAR(256),
    facility_guys_name VARCHAR(128),
    facility_guy_mobile VARCHAR(20),
    facility_guy_superviosr VARCHAR(256),
    result_img_path VARCHAR(256),
    result_message VARCHAR(1024),
    processing_status VARCHAR(64),
    __PowerAppsId__ VARCHAR(64),
    appointment_time date,
    time_slot VARCHAR(64)
);

INSERT INTO sys_facility_order (ISSUE_Id, Issuer_Name, Issue_photo, Isssuer_Email, Issue_details, Classroom, Floor, Maintenance_Type, Urgency_Level, Issuer_Phone, facility_guy_id, date, facility_guys_email, facility_guys_name, facility_guy_mobile, facility_guy_superviosr, result_img_path, result_message, processing_status, __PowerAppsId__, appointment_time, time_slot) VALUES ('1015142215912', 'Dannie', '', 'dddd@ulinkcollege.com', '测试', '教学楼', '1', 'repair', 'High', '', '1', '2024-10-15 14:00:00', '911787514@qq.com', '许悦', '13539773196', '123748665@qq.com', '', '', 'Pending', '', '2024-10-16', '14:00-17:30');
INSERT INTO sys_facility_order (ISSUE_Id, Issuer_Name, Issue_photo, Isssuer_Email, Issue_details, Classroom, Floor, Maintenance_Type, Urgency_Level, Issuer_Phone, facility_guy_id, date, facility_guys_email, facility_guys_name, facility_guy_mobile, facility_guy_superviosr, result_img_path, result_message, processing_status, __PowerAppsId__, appointment_time, time_slot) VALUES ('1015152215912', 'Reese Zhan', '', 'rzhan@ncpachina.org', '请在下午 4:30 分后，帮 SE612 修理灯具，谢谢', 'SE612', '6', 'repair', 'Medium', '', '1', '2024-10-15 15:00:00', '911787514@qq.com', '许悦', '13539773196', '123748665@qq.com', 'schoolMaintain/imageRepair/2024101508/1015165452103.png', '已完成', 'Resolved', '', '2024-10-16', '16:30-18:00');
INSERT INTO sys_facility_order (ISSUE_Id, Issuer_Name, Issue_photo, Isssuer_Email, Issue_details, Classroom, Floor, Maintenance_Type, Urgency_Level, Issuer_Phone, facility_guy_id, date, facility_guys_email, facility_guys_name, facility_guy_mobile, facility_guy_superviosr, result_img_path, result_message, processing_status, __PowerAppsId__, appointment_time, time_slot) VALUES ('1125154539247', 'Amber Chen', 'schoolMaintain/imageRepair/2024112515/1125154539247.png', 'achen@ncpachina.org', 'E201-4 的这个柜子需要麻烦啊姨修理一下，谢谢。', 'E201-4', '2', 'repair', 'Low', '15989215821', '1', '2024-11-25 15:00:00', '911787514@qq.com', '许悦', '13539773196', '123748665@qq.com', 'schoolMaintain/imageRepair/2024112603/1126110102203.png', '已完成', 'Resolved', '', '2024-11-26', '09:00-12:00');
