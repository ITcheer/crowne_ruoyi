package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysEngineeringPatrolTaskLocation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "任务ID")
    private String taskId;

    @Excel(name = "地点ID")
    private String locationId;

    private SysEngineeringPatrolLocation locationDetails;

    // Getters and Setters
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public SysEngineeringPatrolLocation getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(SysEngineeringPatrolLocation locationDetails) {
        this.locationDetails = locationDetails;
    }
}