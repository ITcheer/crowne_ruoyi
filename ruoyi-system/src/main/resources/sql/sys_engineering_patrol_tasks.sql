CREATE TABLE sys_engineering_patrol_location (
    ID VARCHAR(64) PRIMARY KEY,
    Location_Name VARCHAR(128) NOT NULL,
    Location_Description VARCHAR(256),
    Update_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE sys_engineering_patrol_task (
    ID VARCHAR(64) PRIMARY KEY,
    Task_Name VARCHAR(128) NOT NULL,
    Task_Content VARCHAR(1024),
    Task_Time_Period VARCHAR(64) NOT NULL,
    Task_Cycle VARCHAR(64) NOT NULL,
    Task_Start_Date DATE NOT NULL,
    Task_End_Date DATE,
    Update_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE sys_engineering_patrol_task_location (
    Task_ID VARCHAR(64),
    Location_ID VARCHAR(64),
    PRIMARY KEY (Task_ID, Location_ID),
    FOREIGN KEY (Task_ID) REFERENCES sys_engineering_patrol_task(ID),
    FOREIGN KEY (Location_ID) REFERENCES sys_engineering_patrol_location(ID)
);

CREATE TABLE sys_engineering_patrol_task_participant (
    Task_ID VARCHAR(64),
    Participant_ID VARCHAR(64),
    PRIMARY KEY (Task_ID, Participant_ID),
    FOREIGN KEY (Task_ID) REFERENCES sys_engineering_patrol_task(ID)
);

INSERT INTO sys_engineering_patrol_location (ID, Location_Name, Location_Description) VALUES ('1', '东门', '东门的描述');
INSERT INTO sys_engineering_patrol_location (ID, Location_Name, Location_Description) VALUES ('2', '西门', '西门的描述');
INSERT INTO sys_engineering_patrol_location (ID, Location_Name, Location_Description) VALUES ('3', '南门', '南门的描述');
INSERT INTO sys_engineering_patrol_location (ID, Location_Name, Location_Description) VALUES ('4', '北门', '北门的描述');

INSERT INTO sys_engineering_patrol_task (ID, Task_Name, Task_Content, Task_Time_Period, Task_Cycle, Task_Start_Date, Task_End_Date) 
VALUES ('1', '工程巡视任务1', '检查门禁系统', '08:00-12:00', '每天', '2023-01-01', '2023-12-31');
INSERT INTO sys_engineering_patrol_task (ID, Task_Name, Task_Content, Task_Time_Period, Task_Cycle, Task_Start_Date, Task_End_Date) 
VALUES ('2', '工程巡视任务2', '检查消防设备', '14:00-18:00', '每周一', '2023-01-01', '2023-12-31');

INSERT INTO sys_engineering_patrol_task_location (Task_ID, Location_ID) VALUES ('1', '1');
INSERT INTO sys_engineering_patrol_task_location (Task_ID, Location_ID) VALUES ('1', '2');
INSERT INTO sys_engineering_patrol_task_location (Task_ID, Location_ID) VALUES ('2', '3');
INSERT INTO sys_engineering_patrol_task_location (Task_ID, Location_ID) VALUES ('2', '4');

INSERT INTO sys_engineering_patrol_task_participant (Task_ID, Participant_ID) VALUES ('1', '1');
INSERT INTO sys_engineering_patrol_task_participant (Task_ID, Participant_ID) VALUES ('1', '2');
INSERT INTO sys_engineering_patrol_task_participant (Task_ID, Participant_ID) VALUES ('2', '3');
INSERT INTO sys_engineering_patrol_task_participant (Task_ID, Participant_ID) VALUES ('2', '4');

CREATE TABLE sys_engineering_patrol_task_record (
    ID VARCHAR(64) PRIMARY KEY,
    Task_ID VARCHAR(64) NOT NULL,
    Participant_ID VARCHAR(64) NOT NULL,
    Check_In_Info VARCHAR(256) NOT NULL,
    Check_In_Image VARCHAR(256) NOT NULL,
    Check_In_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Related_Parameter VARCHAR(256) NOT NULL
);

INSERT INTO sys_engineering_patrol_task_record (ID, Task_ID, Participant_ID, Check_In_Info, Check_In_Image, Related_Parameter) 
VALUES ('1', '1', '1', '东门巡视完成', 'image1.jpg', '参数1');
INSERT INTO sys_engineering_patrol_task_record (ID, Task_ID, Participant_ID, Check_In_Info, Check_In_Image, Related_Parameter) 
VALUES ('2', '1', '2', '西门巡视完成', 'image2.jpg', '参数2');
INSERT INTO sys_engineering_patrol_task_record (ID, Task_ID, Participant_ID, Check_In_Info, Check_In_Image, Related_Parameter) 
VALUES ('3', '2', '3', '南门巡视完成', 'image3.jpg', '参数3');
INSERT INTO sys_engineering_patrol_task_record (ID, Task_ID, Participant_ID, Check_In_Info, Check_In_Image, Related_Parameter) 
VALUES ('4', '2', '4', '北门巡视完成', 'image4.jpg', '参数4');
