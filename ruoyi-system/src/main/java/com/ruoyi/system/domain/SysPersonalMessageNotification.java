package com.ruoyi.system.domain;

import java.sql.Timestamp;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysPersonalMessageNotification extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 通知ID */
    @Excel(name = "通知ID")
    private String notificationId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 消息标题 */
    @Excel(name = "消息标题")
    private String messageTitle;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageContent;

    /** 阅读状态 */
    @Excel(name = "阅读状态")
    private Integer readStatus;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Timestamp createTime;

    /** 通知类型 */
    @Excel(name = "通知类型")
    private String notificationType;

    /** 通知来源 */
    @Excel(name = "通知来源")
    private String notificationSource;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationSource() {
        return notificationSource;
    }

    public void setNotificationSource(String notificationSource) {
        this.notificationSource = notificationSource;
    }

    @Override
    public String toString() {
        return "SysPersonalMessageNotification{" +
                "notificationId='" + notificationId + '\'' +
                ", userId='" + userId + '\'' +
                ", messageTitle='" + messageTitle + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", readStatus=" + readStatus +
                ", createTime=" + createTime +
                ", notificationType='" + notificationType + '\'' +
                ", notificationSource='" + notificationSource + '\'' +
                '}';
    }
}
