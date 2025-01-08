package com.ruoyi.system.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysPatrolTaskParticipant;
import com.ruoyi.system.mapper.SysPatrolTaskParticipantMapper;
import com.ruoyi.system.service.ISysPatrolTaskParticipantService;

/**
 * 巡逻任务参与人员对应关系 服务层实现
 * 
 */
@Service
public class SysPatrolTaskParticipantServiceImpl implements ISysPatrolTaskParticipantService
{
    @Autowired
    private SysPatrolTaskParticipantMapper taskParticipantMapper;

    @Override
    public List<SysPatrolTaskParticipant> selectPatrolTaskParticipant(SysPatrolTaskParticipant taskParticipant)
    {
        return taskParticipantMapper.selectPatrolTaskParticipant(taskParticipant);
    }

    @Override
    public int insertPatrolTaskParticipant(SysPatrolTaskParticipant taskParticipant)
    {
        return taskParticipantMapper.insertPatrolTaskParticipant(taskParticipant);
    }

    @Override
    public int batchInsertPatrolTaskParticipant(List<SysPatrolTaskParticipant> taskParticipants)
    {
        return taskParticipantMapper.batchInsertPatrolTaskParticipant(taskParticipants);
    }

    @Override
    public int deletePatrolTaskParticipantByTaskId(String taskId)
    {
        return taskParticipantMapper.deletePatrolTaskParticipantByTaskId(taskId);
    }

    @Override
    public int deletePatrolTaskParticipantByPatrolId(String participantId)
    {
        return taskParticipantMapper.deletePatrolTaskParticipantByPatrolId(participantId);
    }
}


