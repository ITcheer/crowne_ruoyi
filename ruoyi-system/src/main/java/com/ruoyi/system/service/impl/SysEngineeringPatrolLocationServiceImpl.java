package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysEngineeringPatrolLocation;
import com.ruoyi.system.mapper.SysEngineeringPatrolLocationMapper;
import com.ruoyi.system.service.ISysEngineeringPatrolLocationService;

/**
 * 工程巡逻位置 服务层实现
 */
@Service
public class SysEngineeringPatrolLocationServiceImpl implements ISysEngineeringPatrolLocationService
{
    @Autowired
    private SysEngineeringPatrolLocationMapper locationMapper;

    @Override
    public SysEngineeringPatrolLocation selectPatrolLocationById(String id)
    {
        return locationMapper.selectPatrolLocationById(id);
    }

    @Override
    public List<SysEngineeringPatrolLocation> selectPatrolLocationList(SysEngineeringPatrolLocation location)
    {
        return locationMapper.selectPatrolLocationList(location);
    }

    @Override
    public int insertPatrolLocation(SysEngineeringPatrolLocation location)
    {
        return locationMapper.insertPatrolLocation(location);
    }

    @Override
    public int updatePatrolLocation(SysEngineeringPatrolLocation location)
    {
        return locationMapper.updatePatrolLocation(location);
    }

    @Override
    public int deletePatrolLocationById(String id)
    {
        return locationMapper.deletePatrolLocationById(id);
    }

    @Override
    public int deletePatrolLocationByIds(String[] ids)
    {
        return locationMapper.deletePatrolLocationByIds(ids);
    }
}
