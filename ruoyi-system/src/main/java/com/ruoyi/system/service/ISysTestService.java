package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysTest;

/**
 * 测试 服务层
 * 
 * @author ruoyi
 */
public interface ISysTestService
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
     * 批量删除测试
     * 
     * @param testIds 需要删除的测试ID
     * @return 结果
     */
    public int deleteTestByIds(Long[] testIds);

    /**
     * 校验测试键名是否唯一
     * 
     * @param test 测试信息
     * @return 结果
     */
    public boolean checkTestKeyUnique(SysTest test);

    /**
     * 重置测试缓存
     */
    public void resetTestCache();
}
