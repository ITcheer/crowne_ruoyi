CREATE TABLE sys_patrol_location (
    ID VARCHAR(64) PRIMARY KEY,
    Location_Name VARCHAR(128) NOT NULL
);

ALTER TABLE sys_patrol_location ADD COLUMN Update_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

CREATE TABLE sys_patrol_task (
    ID VARCHAR(64) PRIMARY KEY,
    Task_Location_IDs VARCHAR(256) NOT NULL,
    Task_Name VARCHAR(128) NOT NULL,
    Task_Content VARCHAR(1024),
    Task_Time_Period VARCHAR(64) NOT NULL,
    Task_Cycle VARCHAR(64) NOT NULL,
    Task_Start_Date DATE NOT NULL,
    Task_End_Date DATE,
    Task_Participant_IDs VARCHAR(256) NOT NULL
);

ALTER TABLE sys_patrol_task ADD COLUMN Update_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

INSERT INTO sys_patrol_location (ID, Location_Name) VALUES ('1', '东门');
INSERT INTO sys_patrol_location (ID, Location_Name) VALUES ('2', '西门');
INSERT INTO sys_patrol_location (ID, Location_Name) VALUES ('3', '南门');
INSERT INTO sys_patrol_location (ID, Location_Name) VALUES ('4', '北门');

INSERT INTO sys_patrol_task (ID, Task_Location_IDs, Task_Name, Task_Content, Task_Time_Period, Task_Cycle, Task_Start_Date, Task_End_Date, Task_Participant_IDs) 
VALUES ('1', '1,2', '巡逻任务1', '检查门禁系统', '08:00-12:00', '每天', '2023-01-01', '2023-12-31', '1,2');
INSERT INTO sys_patrol_task (ID, Task_Location_IDs, Task_Name, Task_Content, Task_Time_Period, Task_Cycle, Task_Start_Date, Task_End_Date, Task_Participant_IDs) 
VALUES ('2', '3,4', '巡逻任务2', '检查消防设备', '14:00-18:00', '每周一', '2023-01-01', '2023-12-31', '3,4');
