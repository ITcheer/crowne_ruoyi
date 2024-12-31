package com.ruoyi.system.domain;

import com.ruoyi.common.utils.StringUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试信息
 * 
 * @author ruoyi
 */
public class SysTest
{
    /** 测试ID */
    private Long testId;

    /** 测试名称 */
    private String testName = "";

    /** 测试键名 */
    private String testKey = "";

    /** 测试内容 */
    private String testValue = "";

    /** 备注 */
    private String remark = "";

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updateBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 请求参数 */
    private Map<String, Object> params;

    /** 状态 */
    private String status;

    public SysTest()
    {

    }

    public SysTest(String testName, String remark)
    {
        this.testName = testName;
        this.remark = remark;
    }

    public SysTest(String testName, String testKey, String testValue)
    {
        this.testName = StringUtils.replace(testName, ":", "");
        this.testKey = StringUtils.replace(testKey, testName, "");
        this.testValue = testValue;
    }

    public Long getTestId()
    {
        return testId;
    }

    public void setTestId(Long testId)
    {
        this.testId = testId;
    }

    public String getTestName()
    {
        return testName;
    }

    public void setTestName(String testName)
    {
        this.testName = testName;
    }

    public String getTestKey()
    {
        return testKey;
    }

    public void setTestKey(String testKey)
    {
        this.testKey = testKey;
    }

    public String getTestValue()
    {
        return testValue;
    }

    public void setTestValue(String testValue)
    {
        this.testValue = testValue;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
