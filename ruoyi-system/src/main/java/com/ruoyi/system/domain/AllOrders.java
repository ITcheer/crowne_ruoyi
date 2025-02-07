package com.ruoyi.system.domain;

import java.util.Date;

public class AllOrders {
    private String id;
    private String orderType;
    private String processingStatus;
    private Date date;
    private Object details; // 其他详细数据

    // 构造方法
    public AllOrders() {}

    public AllOrders(String id, String orderType, String processingStatus, Date date, Object details) {
        this.id = id;
        this.orderType = orderType;
        this.processingStatus = processingStatus;
        this.date = date;
        this.details = details;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    // ...其他字段的Getters和Setters...

    @Override
    public String toString() {
        return "AllOrders{" +
                "id='" + id + '\'' +
                ", orderType='" + orderType + '\'' +
                ", processingStatus='" + processingStatus + '\'' +
                ", date=" + date +
                ", details=" + details +
                // ...其他字段...
                '}';
    }
}
