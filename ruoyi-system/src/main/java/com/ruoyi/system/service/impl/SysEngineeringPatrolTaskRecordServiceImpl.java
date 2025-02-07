
package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskRecord;
import com.ruoyi.system.mapper.SysEngineeringPatrolTaskRecordMapper;
import com.ruoyi.system.service.ISysEngineeringPatrolTaskRecordService;

/**
 * 工程巡逻任务记录 服务层实现
 */
@Service
public class SysEngineeringPatrolTaskRecordServiceImpl implements ISysEngineeringPatrolTaskRecordService
{
    @Autowired
    private SysEngineeringPatrolTaskRecordMapper taskRecordMapper;

    @Override
    public SysEngineeringPatrolTaskRecord selectPatrolTaskRecordById(String id)
    {
        return taskRecordMapper.selectPatrolTaskRecordById(id);
    }

    @Override
    public List<SysEngineeringPatrolTaskRecord> selectPatrolTaskRecordList(SysEngineeringPatrolTaskRecord taskRecord)
    {
        return taskRecordMapper.selectPatrolTaskRecordList(taskRecord);
    }

    @Override
    public List<SysEngineeringPatrolTaskRecord> selectPatrolTaskRecordListByTaskId(String taskId)
    {
        return taskRecordMapper.selectPatrolTaskRecordListByTaskId(taskId);
    }

    @Override
    public int insertPatrolTaskRecord(SysEngineeringPatrolTaskRecord taskRecord)
    {
        return taskRecordMapper.insertPatrolTaskRecord(taskRecord);
    }

    @Override
    public int updatePatrolTaskRecord(SysEngineeringPatrolTaskRecord taskRecord)
    {
        return taskRecordMapper.updatePatrolTaskRecord(taskRecord);
    }

    @Override
    public int deletePatrolTaskRecordById(String id)
    {
        return taskRecordMapper.deletePatrolTaskRecordById(id);
    }

    @Override
    public int deletePatrolTaskRecordByIds(String[] ids)
    {
        return taskRecordMapper.deletePatrolTaskRecordByIds(ids);
    }
}