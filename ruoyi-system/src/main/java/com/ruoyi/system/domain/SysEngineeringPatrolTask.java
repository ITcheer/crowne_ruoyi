package com.ruoyi.system.domain;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysEngineeringPatrolTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "任务ID")
    private String id;

    @Excel(name = "任务名称")
    private String taskName;

    @Excel(name = "任务内容")
    private String taskContent;

    @Excel(name = "任务时间段")
    private String taskTimePeriod;

    @Excel(name = "任务周期")
    private String taskCycle;

    @Excel(name = "任务开始日期")
    private Date taskStartDate;

    @Excel(name = "任务结束日期")
    private Date taskEndDate;

    private Date updateTime;

    private List<SysEngineeringPatrolTaskLocation> locations;
    private List<SysEngineeringPatrolTaskParticipant> participants;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskTimePeriod() {
        return taskTimePeriod;
    }

    public void setTaskTimePeriod(String taskTimePeriod) {
        this.taskTimePeriod = taskTimePeriod;
    }

    public String getTaskCycle() {
        return taskCycle;
    }

    public void setTaskCycle(String taskCycle) {
        this.taskCycle = taskCycle;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<SysEngineeringPatrolTaskLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<SysEngineeringPatrolTaskLocation> locations) {
        this.locations = locations;
    }

    public List<SysEngineeringPatrolTaskParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<SysEngineeringPatrolTaskParticipant> participants) {
        this.participants = participants;
    }
}


