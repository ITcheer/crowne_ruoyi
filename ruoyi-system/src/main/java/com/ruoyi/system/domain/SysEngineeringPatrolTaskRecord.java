package com.ruoyi.system.domain;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysEngineeringPatrolTaskRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "记录ID")
    private String id;

    @Excel(name = "任务ID")
    private String taskId;

    @Excel(name = "参与者ID")
    private String participantId;

    @Excel(name = "签到信息")
    private String checkInInfo;

    @Excel(name = "签到图片")
    private String checkInImage;

    @Excel(name = "签到时间")
    private Date checkInTime;

    @Excel(name = "相关参数")
    private String relatedParameter;

    @Excel(name = "打卡人名称")
    private String participantName;

    @Excel(name = "打卡地点ID")
    private String checkInLocationId;

    @Excel(name = "打卡地点名称")
    private String checkInLocationName;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCheckInInfo() {
        return checkInInfo;
    }

    public void setCheckInInfo(String checkInInfo) {
        this.checkInInfo = checkInInfo;
    }

    public String getCheckInImage() {
        return checkInImage;
    }

    public void setCheckInImage(String checkInImage) {
        this.checkInImage = checkInImage;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getRelatedParameter() {
        return relatedParameter;
    }

    public void setRelatedParameter(String relatedParameter) {
        this.relatedParameter = relatedParameter;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getCheckInLocationId() {
        return checkInLocationId;
    }

    public void setCheckInLocationId(String checkInLocation) {
        this.checkInLocationId = checkInLocation;
    }

    public String getCheckInLocationName() {
        return checkInLocationName;
    }

    public void setCheckInLocationName(String checkInLocationName) {
        this.checkInLocationName = checkInLocationName;
    }
}
