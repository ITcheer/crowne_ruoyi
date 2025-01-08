package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysPatrolTaskLocation;
import com.ruoyi.system.mapper.SysPatrolTaskLocationMapper;
import com.ruoyi.system.service.ISysPatrolTaskLocationService;

/**
 * 巡逻任务地点对应关系 服务层实现
 * 
 */
@Service
public class SysPatrolTaskLocationServiceImpl implements ISysPatrolTaskLocationService
{
    @Autowired
    private SysPatrolTaskLocationMapper taskLocationMapper;

    @Override
    public List<SysPatrolTaskLocation> selectPatrolTaskLocation(SysPatrolTaskLocation taskLocation)
    {
        return taskLocationMapper.selectPatrolTaskLocation(taskLocation);
    }

    @Override
    public int insertPatrolTaskLocation(SysPatrolTaskLocation taskLocation)
    {
        return taskLocationMapper.insertPatrolTaskLocation(taskLocation);
    }

    @Override
    public int batchInsertPatrolTaskLocation(List<SysPatrolTaskLocation> taskLocations)
    {
        return taskLocationMapper.batchInsertPatrolTaskLocation(taskLocations);
    }

    @Override
    public int deletePatrolTaskLocationByTaskId(String taskId)
    {
        return taskLocationMapper.deletePatrolTaskLocationByTaskId(taskId);
    }
}
