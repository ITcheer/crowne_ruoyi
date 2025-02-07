package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskParticipant;
import com.ruoyi.system.mapper.SysEngineeringPatrolTaskParticipantMapper;
import com.ruoyi.system.service.ISysEngineeringPatrolTaskParticipantService;

/**
 * 工程巡逻任务参与者 服务层实现
 */
@Service
public class SysEngineeringPatrolTaskParticipantServiceImpl implements ISysEngineeringPatrolTaskParticipantService
{
    @Autowired
    private SysEngineeringPatrolTaskParticipantMapper taskParticipantMapper;

    @Override
    public SysEngineeringPatrolTaskParticipant selectPatrolTaskParticipantById(String taskId)
    {
        return taskParticipantMapper.selectPatrolTaskParticipantById(taskId);
    }

    @Override
    public List<SysEngineeringPatrolTaskParticipant> selectPatrolTaskParticipantList(SysEngineeringPatrolTaskParticipant taskParticipant)
    {
        return taskParticipantMapper.selectPatrolTaskParticipantList(taskParticipant);
    }

    @Override
    public int insertPatrolTaskParticipant(SysEngineeringPatrolTaskParticipant taskParticipant)
    {
        return taskParticipantMapper.insertPatrolTaskParticipant(taskParticipant);
    }

    @Override
    public int updatePatrolTaskParticipant(SysEngineeringPatrolTaskParticipant taskParticipant)
    {
        return taskParticipantMapper.updatePatrolTaskParticipant(taskParticipant);
    }

    @Override
    public int deletePatrolTaskParticipantById(String taskId)
    {
        return taskParticipantMapper.deletePatrolTaskParticipantById(taskId);
    }

    @Override
    public int deletePatrolTaskParticipantByIds(String[] taskIds)
    {
        return taskParticipantMapper.deletePatrolTaskParticipantByIds(taskIds);
    }

    @Override
    public int batchInsertPatrolTaskParticipant(List<SysEngineeringPatrolTaskParticipant> taskParticipants)
    {
        return taskParticipantMapper.batchInsertPatrolTaskParticipant(taskParticipants);
    }
}