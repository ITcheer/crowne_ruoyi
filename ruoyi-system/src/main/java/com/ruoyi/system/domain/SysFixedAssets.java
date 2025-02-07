package com.ruoyi.system.domain;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysFixedAssets extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "批次ID")
    private String batchId;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "数量")
    private int quantity;

    @Excel(name = "单价")
    private double unitPrice;

    @Excel(name = "折旧单价")
    private double depreciationPrice;

    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    @Excel(name = "使用年限(年)")
    private int serviceLife;

    @Excel(name = "照片")
    private String photo;

    @Excel(name = "描述")
    private String description;

    // 构造方法
    public SysFixedAssets() {}

    public SysFixedAssets(String batchId, String name, int quantity, double unitPrice, double depreciationPrice, Date productionDate, int serviceLife, String photo, String description) {
        this.batchId = batchId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.depreciationPrice = depreciationPrice;
        this.productionDate = productionDate;
        this.serviceLife = serviceLife;
        this.photo = photo;
        this.description = description;
    }

    // Getters and Setters
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDepreciationPrice() {
        return depreciationPrice;
    }

    public void setDepreciationPrice(double depreciationPrice) {
        this.depreciationPrice = depreciationPrice;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public int getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(int serviceLife) {
        this.serviceLife = serviceLife;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SysFixedAssets{" +
                "batchId='" + batchId + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", depreciationPrice=" + depreciationPrice +
                ", productionDate=" + productionDate +
                ", serviceLife=" + serviceLife +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
