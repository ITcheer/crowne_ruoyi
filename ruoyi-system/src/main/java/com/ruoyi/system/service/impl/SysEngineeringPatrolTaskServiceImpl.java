package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysEngineeringPatrolTask;
import com.ruoyi.system.mapper.SysEngineeringPatrolTaskMapper;
import com.ruoyi.system.service.ISysEngineeringPatrolTaskService;

/**
 * 工程巡逻任务 服务层实现
 */
@Service
public class SysEngineeringPatrolTaskServiceImpl implements ISysEngineeringPatrolTaskService
{
    @Autowired
    private SysEngineeringPatrolTaskMapper taskMapper;

    @Override
    public SysEngineeringPatrolTask selectPatrolTaskById(String id)
    {
        return taskMapper.selectPatrolTaskById(id);
    }

    @Override
    public List<SysEngineeringPatrolTask> selectPatrolTaskList(SysEngineeringPatrolTask task)
    {
        return taskMapper.selectPatrolTaskList(task);
    }

    @Override
    public int insertPatrolTask(SysEngineeringPatrolTask task)
    {
        return taskMapper.insertPatrolTask(task);
    }

    @Override
    public int updatePatrolTask(SysEngineeringPatrolTask task)
    {
        return taskMapper.updatePatrolTask(task);
    }

    @Override
    public int deletePatrolTaskById(String id)
    {
        return taskMapper.deletePatrolTaskById(id);
    }

    @Override
    public int deletePatrolTaskByIds(String[] ids)
    {
        return taskMapper.deletePatrolTaskByIds(ids);
    }
}
