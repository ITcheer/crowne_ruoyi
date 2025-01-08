package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 巡逻任务参与人员对应关系
 * 
 */
public class SysPatrolTaskParticipant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @Excel(name = "任务ID")
    private String taskId;

    /** 参与人员ID */
    @Excel(name = "参与人员ID")
    private String participantId;

    /** 用户详细信息 */
    private SysUser userDetails;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public SysUser getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(SysUser userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("participantId", getParticipantId())
            .append("userDetails", getUserDetails())
            .toString();
    }
}
