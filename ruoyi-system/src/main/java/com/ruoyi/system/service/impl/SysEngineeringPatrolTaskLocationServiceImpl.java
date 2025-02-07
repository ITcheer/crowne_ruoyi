package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskLocation;
import com.ruoyi.system.mapper.SysEngineeringPatrolTaskLocationMapper;
import com.ruoyi.system.service.ISysEngineeringPatrolTaskLocationService;

/**
 * 工程巡逻任务位置 服务层实现
 */
@Service
public class SysEngineeringPatrolTaskLocationServiceImpl implements ISysEngineeringPatrolTaskLocationService
{
    @Autowired
    private SysEngineeringPatrolTaskLocationMapper taskLocationMapper;

    @Override
    public SysEngineeringPatrolTaskLocation selectPatrolTaskLocationById(String taskId)
    {
        return taskLocationMapper.selectPatrolTaskLocationById(taskId);
    }

    @Override
    public List<SysEngineeringPatrolTaskLocation> selectPatrolTaskLocationList(SysEngineeringPatrolTaskLocation taskLocation)
    {
        return taskLocationMapper.selectPatrolTaskLocationList(taskLocation);
    }

    @Override
    public int insertPatrolTaskLocation(SysEngineeringPatrolTaskLocation taskLocation)
    {
        return taskLocationMapper.insertPatrolTaskLocation(taskLocation);
    }

    @Override
    public int updatePatrolTaskLocation(SysEngineeringPatrolTaskLocation taskLocation)
    {
        return taskLocationMapper.updatePatrolTaskLocation(taskLocation);
    }

    @Override
    public int deletePatrolTaskLocationById(String taskId)
    {
        return taskLocationMapper.deletePatrolTaskLocationById(taskId);
    }

    @Override
    public int deletePatrolTaskLocationByIds(String[] taskIds)
    {
        return taskLocationMapper.deletePatrolTaskLocationByIds(taskIds);
    }

    @Override
    public int batchInsertPatrolTaskLocation(List<SysEngineeringPatrolTaskLocation> taskLocations)
    {
        return taskLocationMapper.batchInsertPatrolTaskLocation(taskLocations);
    }
}
