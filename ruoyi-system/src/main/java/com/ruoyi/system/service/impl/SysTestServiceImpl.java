package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysTest;
import com.ruoyi.system.mapper.SysTestMapper;
import com.ruoyi.system.service.ISysTestService;

/**
 * 测试 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class SysTestServiceImpl implements ISysTestService
{
    @Autowired
    private SysTestMapper testMapper;

    @Override
    public SysTest selectTestById(Long testId)
    {
        return testMapper.selectTestById(testId);
    }

    @Override
    public List<SysTest> selectTestList(SysTest test)
    {
        return testMapper.selectTestList(test);
    }

    @Override
    public int insertTest(SysTest test)
    {
        return testMapper.insertTest(test);
    }

    @Override
    public int updateTest(SysTest test)
    {
        return testMapper.updateTest(test);
    }

    @Override
    public int deleteTestByIds(Long[] testIds)
    {
        return testMapper.deleteTestByIds(testIds);
    }

    @Override
    public boolean checkTestKeyUnique(SysTest test)
    {
        Long testId = test.getTestId() == null ? -1L : test.getTestId();
        SysTest info = testMapper.checkTestKeyUnique(test.getTestKey());
        if (info != null && info.getTestId().longValue() != testId.longValue())
        {
            return false;
        }
        return true;
    }

    @Override
    public void resetTestCache()
    {
        // 实现缓存重置逻辑
    }
}
