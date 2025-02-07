package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPatrolTaskRecord;

/**
 * 巡逻任务记录 数据层
 * 
 */
public interface SysPatrolTaskRecordMapper
{
    /**
     * 查询巡逻任务记录
     * 
     * @param id 记录ID
     * @return 巡逻任务记录
     */
    public SysPatrolTaskRecord selectPatrolTaskRecordById(String id);

    /**
     * 查询巡逻任务记录列表
     * 
     * @param taskRecord 巡逻任务记录
     * @return 巡逻任务记录集合
     */
    public List<SysPatrolTaskRecord> selectPatrolTaskRecordList(SysPatrolTaskRecord taskRecord);

    /**
     * 根据任务ID查询巡逻任务记录列表
     * 
     * @param taskId 任务ID
     * @return 巡逻任务记录集合
     */
    public List<SysPatrolTaskRecord> selectPatrolTaskRecordListByTaskId(String taskId);

    /**
     * 新增巡逻任务记录
     * 
     * @param taskRecord 巡逻任务记录
     * @return 结果
     */
    public int insertPatrolTaskRecord(SysPatrolTaskRecord taskRecord);

    /**
     * 修改巡逻任务记录
     * 
     * @param taskRecord 巡逻任务记录
     * @return 结果
     */
    public int updatePatrolTaskRecord(SysPatrolTaskRecord taskRecord);

    /**
     * 删除巡逻任务记录
     * 
     * @param id 记录ID
     * @return 结果
     */
    public int deletePatrolTaskRecordById(String id);

    /**
     * 批量删除巡逻任务记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePatrolTaskRecordByIds(String[] ids);
}
