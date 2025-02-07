package com.ruoyi.system.domain;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysComplaintOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "工单ID")
    private String issueId;

    @Excel(name = "发布人姓名")
    private String issuerName;

    @Excel(name = "发布人邮箱")
    private String issuerEmail;

    @Excel(name = "问题详情")
    private String issueDetails;

    @Excel(name = "发布人电话")
    private String issuerPhone;

    @Excel(name = "处理人员ID")
    private String facilityGuyId;

    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @Excel(name = "处理人员邮箱")
    private String facilityGuysEmail;

    @Excel(name = "处理人员姓名")
    private String facilityGuysName;

    @Excel(name = "处理人员手机")
    private String facilityGuyMobile;

    @Excel(name = "处理人员主管")
    private String facilityGuySupervisor;

    @Excel(name = "结果信息")
    private String resultMessage;

    @Excel(name = "处理状态")
    private String processingStatus;

    @Excel(name = "PowerApps ID")
    private String powerAppsId;

    // 构造方法
    public SysComplaintOrder() {}

    public SysComplaintOrder(String issueId, String issuerName, String issuerEmail, String issueDetails, String issuerPhone, String facilityGuyId, Date date, String facilityGuysEmail, String facilityGuysName, String facilityGuyMobile, String facilityGuySupervisor, String resultMessage, String processingStatus, String powerAppsId) {
        this.issueId = issueId;
        this.issuerName = issuerName;
        this.issuerEmail = issuerEmail;
        this.issueDetails = issueDetails;
        this.issuerPhone = issuerPhone;
        this.facilityGuyId = facilityGuyId;
        this.date = date;
        this.facilityGuysEmail = facilityGuysEmail;
        this.facilityGuysName = facilityGuysName;
        this.facilityGuyMobile = facilityGuyMobile;
        this.facilityGuySupervisor = facilityGuySupervisor;
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
        return "SysComplaintOrder{" +
                "issueId='" + issueId + '\'' +
                ", issuerName='" + issuerName + '\'' +
                ", issuerEmail='" + issuerEmail + '\'' +
                ", issueDetails='" + issueDetails + '\'' +
                ", issuerPhone='" + issuerPhone + '\'' +
                ", facilityGuyId='" + facilityGuyId + '\'' +
                ", date=" + date +
                ", facilityGuysEmail='" + facilityGuysEmail + '\'' +
                ", facilityGuysName='" + facilityGuysName + '\'' +
                ", facilityGuyMobile='" + facilityGuyMobile + '\'' +
                ", facilityGuySupervisor='" + facilityGuySupervisor + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", processingStatus='" + processingStatus + '\'' +
                ", powerAppsId='" + powerAppsId + '\'' +
                '}';
    }
}
