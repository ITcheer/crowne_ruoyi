package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡逻任务地点对应关系
 * 
 */
public class SysPatrolTaskLocation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @Excel(name = "任务ID")
    private String taskId;

    /** 地点ID */
    @Excel(name = "地点ID")
    private String locationId;

    /** 地点详细信息 */
    private SysPatrolLocation locationDetails;

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

    public SysPatrolLocation getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(SysPatrolLocation locationDetails) {
        this.locationDetails = locationDetails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("locationId", getLocationId())
            .append("locationDetails", getLocationDetails())
            .toString();
    }
}
