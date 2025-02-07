package com.ruoyi.system.domain;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysInventoryRecords extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "记录ID")
    private String id;

    @Excel(name = "货品ID")
    private String goodsId;

    @Excel(name = "数量")
    private int quantity;

    @Excel(name = "记录类型")
    private String recordType;

    @Excel(name = "操作人")
    private String operator;

    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    @Excel(name = "详情")
    private String details;

    // 构造方法
    public SysInventoryRecords() {}

    public SysInventoryRecords(String id, String goodsId, int quantity, String recordType, String operator, Date operationTime, String details) {
        this.id = id;
        this.goodsId = goodsId;
        this.quantity = quantity;
        this.recordType = recordType;
        this.operator = operator;
        this.operationTime = operationTime;
        this.details = details;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "SysInventoryRecords{" +
                "id='" + id + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", quantity=" + quantity +
                ", recordType='" + recordType + '\'' +
                ", operator='" + operator + '\'' +
                ", operationTime=" + operationTime +
                ", details='" + details + '\'' +
                '}';
    }
}
