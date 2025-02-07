package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡逻任务记录
 * 
 */
public class SysPatrolTaskRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @Excel(name = "记录ID")
    private String id;

    /** 任务ID */
    @Excel(name = "任务ID")
    private String taskId;

    /** 打卡人ID */
    @Excel(name = "打卡人ID")
    private String participantId;

    /** 打卡信息 */
    @Excel(name = "打卡信息")
    private String checkInInfo;

    /** 打卡图片 */
    @Excel(name = "打卡图片")
    private String checkInImage;

    /** 打卡时间 */
    @Excel(name = "打卡时间")
    private Date checkInTime;

    /** 打卡人名称 */
    @Excel(name = "打卡人名称")
    private String participantName;

    /** 打卡地点 */
    @Excel(name = "打卡地点Id")
    private String checkInLocationId;

    /** 打卡地点名称 */
    @Excel(name = "打卡地点名称")
    private String checkInLocationName;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("participantId", getParticipantId())
            .append("checkInInfo", getCheckInInfo())
            .append("checkInImage", getCheckInImage())
            .append("checkInTime", getCheckInTime())
            .append("participantName", getParticipantName())
            .append("checkInLocation", getCheckInLocationId())
            .append("checkInLocationName", getCheckInLocationName())
            .toString();
    }
}
