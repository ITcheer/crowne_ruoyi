package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysTest;

/**
 * 测试 数据层
 * 
 * @author ruoyi
 */
public interface SysTestMapper
{
    /**
     * 查询测试信息
     * 
     * @param testId 测试ID
     * @return 测试信息
     */
    public SysTest selectTestById(Long testId);

    /**
     * 查询测试列表
     * 
     * @param test 测试信息
     * @return 测试集合
     */
    public List<SysTest> selectTestList(SysTest test);

    /**
     * 新增测试
     * 
     * @param test 测试信息
     * @return 结果
     */
    public int insertTest(SysTest test);

    /**
     * 修改测试
     * 
     * @param test 测试信息
     * @return 结果
     */
    public int updateTest(SysTest test);

    /**
     * 删除测试
     * 
     * @param testId 测试ID
     * @return 结果
     */
    public int deleteTestById(Long testId);

    /**
     * 批量删除测试
     * 
     * @param testIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestByIds(Long[] testIds);

    /**
     * 校验测试键名是否唯一
     * 
     * @param testKey 测试键名
     * @return 结果
     */
    public SysTest checkTestKeyUnique(String testKey);
}
