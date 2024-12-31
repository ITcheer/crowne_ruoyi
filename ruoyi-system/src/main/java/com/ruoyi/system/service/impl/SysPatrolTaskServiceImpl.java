
package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysPatrolTask;
import com.ruoyi.system.mapper.SysPatrolTaskMapper;
import com.ruoyi.system.service.ISysPatrolTaskService;

/**
 * 巡逻任务 服务层实现
 * 
 */
@Service
public class SysPatrolTaskServiceImpl implements ISysPatrolTaskService
{
    @Autowired
    private SysPatrolTaskMapper taskMapper;

    @Override
    public SysPatrolTask selectPatrolTaskById(String id)
    {
        return taskMapper.selectPatrolTaskById(id);
    }

    @Override
    public List<SysPatrolTask> selectPatrolTaskList(SysPatrolTask task)
    {
        return taskMapper.selectPatrolTaskList(task);
    }

    @Override
    public int insertPatrolTask(SysPatrolTask task)
    {
        return taskMapper.insertPatrolTask(task);
    }

    @Override
    public int updatePatrolTask(SysPatrolTask task)
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