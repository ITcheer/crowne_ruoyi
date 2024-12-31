package com.ruoyi.system.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡逻地点信息
 * 
 */
public class SysPatrolLocation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地点ID */
    @Excel(name = "地点ID")
    private String id;

    /** 地点名称 */
    @Excel(name = "地点名称")
    private String locationName;

    /** 请求参数 */
    private Map<String, Object> params;

    /** 更新时间 */
    private Date updateTime;

    public SysPatrolLocation()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("locationName", getLocationName())
            .append("params", getParams())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}