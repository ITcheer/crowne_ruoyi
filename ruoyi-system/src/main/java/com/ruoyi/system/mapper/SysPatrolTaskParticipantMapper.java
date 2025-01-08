package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPatrolTaskParticipant;

/**
 * 巡逻任务参与人员对应关系 数据层
 * 
 */
public interface SysPatrolTaskParticipantMapper
{
    /**
     * 查询巡逻任务
     * 
     * 实体中两个属性，一个是任务ID，一个是人员ID
     * 都可以作为查询条件
     * 
     * @param taskId 任务ID
     * @return 巡逻任务参与人员对应关系
     */
    public List<SysPatrolTaskParticipant> selectPatrolTaskParticipant(SysPatrolTaskParticipant taskParticipant);

    /**
     * 新增巡逻任务参与人员对应关系
     * 
     * @param taskParticipant 巡逻任务参与人员对应关系
     * @return 结果
     */
    public int insertPatrolTaskParticipant(SysPatrolTaskParticipant taskParticipant);

    /**
     * 批量新增巡逻任务参与人员对应关系
     * 
     * @param taskParticipants 巡逻任务参与人员对应关系列表
     * @return 结果
     */
    public int batchInsertPatrolTaskParticipant(List<SysPatrolTaskParticipant> taskParticipants);

    /**
     * 删除巡逻任务参与人员对应关系
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    public int deletePatrolTaskParticipantByTaskId(String taskId);

    /**
     * 根据参与人员ID删除巡逻任务参与人员对应关系
     * 
     * @param participantId 参与人员ID
     * @return 结果
     */
    public int deletePatrolTaskParticipantByPatrolId(String participantId);
}
