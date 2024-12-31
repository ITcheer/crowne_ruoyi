package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysPatrolLocation;
import com.ruoyi.system.mapper.SysPatrolLocationMapper;
import com.ruoyi.system.service.ISysPatrolLocationService;

/**
 * 巡逻地点 服务层实现
 * 
 */
@Service
public class SysPatrolLocationServiceImpl implements ISysPatrolLocationService
{
    @Autowired
    private SysPatrolLocationMapper locationMapper;

    @Override
    public SysPatrolLocation selectPatrolLocationById(String id)
    {
        return locationMapper.selectPatrolLocationById(id);
    }

    @Override
    public List<SysPatrolLocation> selectPatrolLocationList(SysPatrolLocation location)
    {
        return locationMapper.selectPatrolLocationList(location);
    }

    @Override
    public int insertPatrolLocation(SysPatrolLocation location)
    {
        return locationMapper.insertPatrolLocation(location);
    }

    @Override
    public int updatePatrolLocation(SysPatrolLocation location)
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