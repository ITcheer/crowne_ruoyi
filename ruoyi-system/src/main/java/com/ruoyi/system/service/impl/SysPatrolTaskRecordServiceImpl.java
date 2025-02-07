
package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysPatrolTaskRecord;
import com.ruoyi.system.mapper.SysPatrolTaskRecordMapper;
import com.ruoyi.system.service.ISysPatrolTaskRecordService;

/**
 * 巡逻任务记录 服务层实现
 * 
 */
@Service
public class SysPatrolTaskRecordServiceImpl implements ISysPatrolTaskRecordService
{
    
    @Autowired
    private SysPatrolTaskRecordMapper taskRecordMapper;

    @Override
    public SysPatrolTaskRecord selectPatrolTaskRecordById(String id)
    {
        return taskRecordMapper.selectPatrolTaskRecordById(id);
    }

    @Override
    public List<SysPatrolTaskRecord> selectPatrolTaskRecordList(SysPatrolTaskRecord taskRecord)
    {
        return taskRecordMapper.selectPatrolTaskRecordList(taskRecord);
    }

    @Override
    public List<SysPatrolTaskRecord> selectPatrolTaskRecordListByTaskId(String taskId)
    {
        return taskRecordMapper.selectPatrolTaskRecordListByTaskId(taskId);
    }

    @Override
    public int insertPatrolTaskRecord(SysPatrolTaskRecord taskRecord)
    {
        return taskRecordMapper.insertPatrolTaskRecord(taskRecord);
    }

    @Override
    public int updatePatrolTaskRecord(SysPatrolTaskRecord taskRecord)
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