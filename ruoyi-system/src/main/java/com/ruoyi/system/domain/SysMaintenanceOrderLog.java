package com.ruoyi.system.domain;

import java.sql.Timestamp;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维护订单日志信息
 */
public class SysMaintenanceOrderLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 日志ID */
    @Excel(name = "日志ID")
    private String logId;

    /** 工单ID */
    @Excel(name = "工单ID")
    private String issueId;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String actionType;

    /** 操作描述 */
    @Excel(name = "操作描述")
    private String actionDescription;

    /** 操作时间 */
    @Excel(name = "操作时间")
    private Timestamp actionTime;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    // Getters and Setters
    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "SysMaintenanceOrderLog{" +
                "logId='" + logId + '\'' +
                ", issueId='" + issueId + '\'' +
                ", actionType='" + actionType + '\'' +
                ", actionDescription='" + actionDescription + '\'' +
                ", actionTime=" + actionTime +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
