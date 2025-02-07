package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;

public class SysEngineeringPatrolTaskParticipant extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "任务ID")
    private String taskId;

    @Excel(name = "参与者ID")
    private String participantId;

    private SysUser userDetails;

    // Getters and Setters
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
}