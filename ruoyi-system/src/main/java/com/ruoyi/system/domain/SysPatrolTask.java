package com.ruoyi.system.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡逻任务信息
 * 
 */
public class SysPatrolTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @Excel(name = "任务ID")
    private String id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务内容 */
    @Excel(name = "任务内容")
    private String taskContent;

    /** 任务时间段 */
    @Excel(name = "任务时间段")
    private String taskTimePeriod;

    /** 任务周期 */
    @Excel(name = "任务周期")
    private String taskCycle;

    /** 任务开始日期 */
    @Excel(name = "任务开始日期")
    private Date taskStartDate;

    /** 任务结束日期 */
    @Excel(name = "任务结束日期")
    private Date taskEndDate;

    /** 更新时间 */
    private Date updateTime;

    /** 请求参数 */
    private Map<String, Object> params;

    /** 任务地点列表 */
    private List<SysPatrolTaskLocation> locations;

    /** 任务参与人员列表 */
    private List<SysPatrolTaskParticipant> participants;

    public SysPatrolTask()
    {

    }

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

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

    public List<SysPatrolTaskLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<SysPatrolTaskLocation> locations) {
        this.locations = locations;
    }

    public List<SysPatrolTaskParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<SysPatrolTaskParticipant> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("taskContent", getTaskContent())
            .append("taskTimePeriod", getTaskTimePeriod())
            .append("taskCycle", getTaskCycle())
            .append("taskStartDate", getTaskStartDate())
            .append("taskEndDate", getTaskEndDate())
            .append("updateTime", getUpdateTime())
            .append("params", getParams())
            .append("locations", getLocations())
            .append("participants", getParticipants())
            .toString();
    }
}
