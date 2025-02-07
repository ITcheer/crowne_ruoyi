package com.ruoyi.system.domain;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysCleaningOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "工单ID")
    private String issueId;

    @Excel(name = "发布人姓名")
    private String issuerName;

    @Excel(name = "问题照片")
    private String issuePhoto;

    @Excel(name = "发布人邮箱")
    private String issuerEmail;

    @Excel(name = "问题详情")
    private String issueDetails;

    @Excel(name = "教室")
    private String classroom;

    @Excel(name = "楼层")
    private String floor;

    @Excel(name = "清洁类型")
    private String maintenanceType;

    @Excel(name = "紧急程度")
    private String urgencyLevel;

    @Excel(name = "发布人电话")
    private String issuerPhone;

    @Excel(name = "清洁人员ID")
    private String facilityGuyId;

    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @Excel(name = "清洁人员邮箱")
    private String facilityGuysEmail;

    @Excel(name = "清洁人员姓名")
    private String facilityGuysName;

    @Excel(name = "清洁人员手机")
    private String facilityGuyMobile;

    @Excel(name = "清洁人员主管")
    private String facilityGuySupervisor;

    @Excel(name = "结果图片路径")
    private String resultImgPath;

    @Excel(name = "结果信息")
    private String resultMessage;

    @Excel(name = "处理状态")
    private String processingStatus;

    @Excel(name = "PowerApps ID")
    private String powerAppsId;

    // 构造方法
    public SysCleaningOrder() {}

    public SysCleaningOrder(String issueId, String issuerName, String issuePhoto, String issuerEmail, String issueDetails, String classroom, String floor, String maintenanceType, String urgencyLevel, String issuerPhone, String facilityGuyId, Date date, String facilityGuysEmail, String facilityGuysName, String facilityGuyMobile, String facilityGuySupervisor, String resultImgPath, String resultMessage, String processingStatus, String powerAppsId) {
        this.issueId = issueId;
        this.issuerName = issuerName;
        this.issuePhoto = issuePhoto;
        this.issuerEmail = issuerEmail;
        this.issueDetails = issueDetails;
        this.classroom = classroom;
        this.floor = floor;
        this.maintenanceType = maintenanceType;
        this.urgencyLevel = urgencyLevel;
        this.issuerPhone = issuerPhone;
        this.facilityGuyId = facilityGuyId;
        this.date = date;
        this.facilityGuysEmail = facilityGuysEmail;
        this.facilityGuysName = facilityGuysName;
        this.facilityGuyMobile = facilityGuyMobile;
        this.facilityGuySupervisor = facilityGuySupervisor;
        this.resultImgPath = resultImgPath;
        this.resultMessage = resultMessage;
        this.processingStatus = processingStatus;
        this.powerAppsId = powerAppsId;
    }

    // Getters and Setters
    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getIssuePhoto() {
        return issuePhoto;
    }

    public void setIssuePhoto(String issuePhoto) {
        this.issuePhoto = issuePhoto;
    }

    public String getIssuerEmail() {
        return issuerEmail;
    }

    public void setIssuerEmail(String issuerEmail) {
        this.issuerEmail = issuerEmail;
    }

    public String getIssueDetails() {
        return issueDetails;
    }

    public void setIssueDetails(String issueDetails) {
        this.issueDetails = issueDetails;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getIssuerPhone() {
        return issuerPhone;
    }

    public void setIssuerPhone(String issuerPhone) {
        this.issuerPhone = issuerPhone;
    }

    public String getFacilityGuyId() {
        return facilityGuyId;
    }

    public void setFacilityGuyId(String facilityGuyId) {
        this.facilityGuyId = facilityGuyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFacilityGuysEmail() {
        return facilityGuysEmail;
    }

    public void setFacilityGuysEmail(String facilityGuysEmail) {
        this.facilityGuysEmail = facilityGuysEmail;
    }

    public String getFacilityGuysName() {
        return facilityGuysName;
    }

    public void setFacilityGuysName(String facilityGuysName) {
        this.facilityGuysName = facilityGuysName;
    }

    public String getFacilityGuyMobile() {
        return facilityGuyMobile;
    }

    public void setFacilityGuyMobile(String facilityGuyMobile) {
        this.facilityGuyMobile = facilityGuyMobile;
    }

    public String getFacilityGuySupervisor() {
        return facilityGuySupervisor;
    }

    public void setFacilityGuySupervisor(String facilityGuySupervisor) {
        this.facilityGuySupervisor = facilityGuySupervisor;
    }

    public String getResultImgPath() {
        return resultImgPath;
    }

    public void setResultImgPath(String resultImgPath) {
        this.resultImgPath = resultImgPath;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }

    public String getPowerAppsId() {
        return powerAppsId;
    }

    public void setPowerAppsId(String powerAppsId) {
        this.powerAppsId = powerAppsId;
    }

    @Override
    public String toString() {
        return "SysCleaningOrder{" +
                "issueId='" + issueId + '\'' +
                ", issuerName='" + issuerName + '\'' +
                ", issuePhoto='" + issuePhoto + '\'' +
                ", issuerEmail='" + issuerEmail + '\'' +
                ", issueDetails='" + issueDetails + '\'' +
                ", classroom='" + classroom + '\'' +
                ", floor='" + floor + '\'' +
                ", maintenanceType='" + maintenanceType + '\'' +
                ", urgencyLevel='" + urgencyLevel + '\'' +
                ", issuerPhone='" + issuerPhone + '\'' +
                ", facilityGuyId='" + facilityGuyId + '\'' +
                ", date=" + date +
                ", facilityGuysEmail='" + facilityGuysEmail + '\'' +
                ", facilityGuysName='" + facilityGuysName + '\'' +
                ", facilityGuyMobile='" + facilityGuyMobile + '\'' +
                ", facilityGuySupervisor='" + facilityGuySupervisor + '\'' +
                ", resultImgPath='" + resultImgPath + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", processingStatus='" + processingStatus + '\'' +
                ", powerAppsId='" + powerAppsId + '\'' +
                '}';
    }
}
