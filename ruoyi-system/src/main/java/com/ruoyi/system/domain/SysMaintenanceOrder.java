package com.ruoyi.system.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维护订单信息
 * 
 */
public class SysMaintenanceOrder extends BaseEntity
{

    private static final long serialVersionUID = 1L;
    /** 订单ID */
    @Excel(name = "订单ID")
    private String issueId;

    /** 发布者名称 */
    @Excel(name = "发布者名称")
    private String issuerName;

    /** 发布者照片 */
    @Excel(name = "发布者照片")
    private String issuePhoto;

    /** 发布者邮箱 */
    @Excel(name = "发布者邮箱")
    private String issuerEmail;

    /** 问题详情 */
    @Excel(name = "问题详情")
    private String issueDetails;

    /** 教室 */
    @Excel(name = "教室")
    private String classroom;

    /** 楼层 */
    @Excel(name = "楼层")
    private String floor;

    /** 维护类型 */
    @Excel(name = "维护类型")
    private String maintenanceType;

    /** 紧急程度 */
    @Excel(name = "紧急程度")
    private String urgencyLevel;

    /** 发布者手机 */
    @Excel(name = "发布者手机")
    private String issuerPhone;

    /** 设施人员ID */
    @Excel(name = "设施人员ID")
    private String facilityGuyId;

    /** 日期 */
    @Excel(name = "日期")
    private Timestamp date;

    /** 设施人员邮箱 */
    @Excel(name = "设施人员邮箱")
    private String facilityGuysEmail;

    /** 设施人员名称 */
    @Excel(name = "设施人员名称")
    private String facilityGuysName;

    /** 设施人员手机 */
    @Excel(name = "设施人员手机")
    private String facilityGuyMobile;

    /** 设施人员主管邮箱 */
    @Excel(name = "设施人员主管邮箱")
    private String facilityGuySupervisor;

    /** 结果图片路径 */
    @Excel(name = "结果图片路径")
    private String resultImgPath;

    /** 结果信息 */
    @Excel(name = "结果信息")
    private String resultMessage;

    /** 处理状态 */
    @Excel(name = "处理状态")
    private String processingStatus;

    /** PowerApps ID */
    @Excel(name = "PowerApps ID")
    private String powerAppsId;

    /** 请求参数 */
    private Map<String, Object> params;

    public SysMaintenanceOrder()
    {

    }

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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("issueId", getIssueId())
            .append("issuerName", getIssuerName())
            .append("issuePhoto", getIssuePhoto())
            .append("issuerEmail", getIssuerEmail())
            .append("issueDetails", getIssueDetails())
            .append("classroom", getClassroom())
            .append("floor", getFloor())
            .append("maintenanceType", getMaintenanceType())
            .append("urgencyLevel", getUrgencyLevel())
            .append("issuerPhone", getIssuerPhone())
            .append("facilityGuyId", getFacilityGuyId())
            .append("date", getDate())
            .append("facilityGuysEmail", getFacilityGuysEmail())
            .append("facilityGuysName", getFacilityGuysName())
            .append("facilityGuyMobile", getFacilityGuyMobile())
            .append("facilityGuySupervisor", getFacilityGuySupervisor())
            .append("resultImgPath", getResultImgPath())
            .append("resultMessage", getResultMessage())
            .append("processingStatus", getProcessingStatus())
            .append("powerAppsId", getPowerAppsId())
            .append("params", getParams())
            .toString();
    }
}
