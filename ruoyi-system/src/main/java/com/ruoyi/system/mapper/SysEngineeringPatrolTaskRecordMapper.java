package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskRecord;

/**
 * 工程巡逻任务记录 数据层
 */
public interface SysEngineeringPatrolTaskRecordMapper
{
    /**
     * 查询工程巡逻任务记录
     * 
     * @param id 记录ID
     * @return 工程巡逻任务记录
     */
    public SysEngineeringPatrolTaskRecord selectPatrolTaskRecordById(String id);

    /**
     * 查询工程巡逻任务记录列表
     * 
     * @param taskRecord 工程巡逻任务记录
     * @return 工程巡逻任务记录集合
     */
    public List<SysEngineeringPatrolTaskRecord> selectPatrolTaskRecordList(SysEngineeringPatrolTaskRecord taskRecord);

    /**
     * 根据任务ID查询工程巡逻任务记录列表
     * 
     * @param taskId 任务ID
     * @return 工程巡逻任务记录集合
     */
    public List<SysEngineeringPatrolTaskRecord> selectPatrolTaskRecordListByTaskId(String taskId);

    /**
     * 新增工程巡逻任务记录
     * 
     * @param taskRecord 工程巡逻任务记录
     * @return 结果
     */
    public int insertPatrolTaskRecord(SysEngineeringPatrolTaskRecord taskRecord);

    /**
     * 修改工程巡逻任务记录
     * 
     * @param taskRecord 工程巡逻任务记录
     * @return 结果
     */
    public int updatePatrolTaskRecord(SysEngineeringPatrolTaskRecord taskRecord);

    /**
     * 删除工程巡逻任务记录
     * 
     * @param id 记录ID
     * @return 结果
     */
    public int deletePatrolTaskRecordById(String id);

    /**
     * 批量删除工程巡逻任务记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePatrolTaskRecordByIds(String[] ids);
}
