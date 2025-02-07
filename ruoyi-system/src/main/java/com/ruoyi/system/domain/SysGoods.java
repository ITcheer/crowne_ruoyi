package com.ruoyi.system.domain;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysGoods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "批次ID")
    private String batchId;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "数量")
    private int quantity;

    @Excel(name = "单价")
    private double unitPrice;

    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    @Excel(name = "保质期(月)")
    private int shelfLife;

    @Excel(name = "照片")
    private String photo;

    @Excel(name = "更多描述")
    private String description;

    // 构造方法
    public SysGoods() {}

    public SysGoods(String batchId, String name, int quantity, double unitPrice, Date productionDate, int shelfLife, String photo, String description) {
        this.batchId = batchId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productionDate = productionDate;
        this.shelfLife = shelfLife;
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

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
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
        return "SysGoods{" +
                "batchId='" + batchId + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", productionDate=" + productionDate +
                ", shelfLife=" + shelfLife +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
