package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskParticipant;

/**
 * 工程巡逻任务参与者 服务层
 */
public interface ISysEngineeringPatrolTaskParticipantService
{
    /**
     * 查询工程巡逻任务参与者
     * 
     * @param taskId 任务ID
     * @return 工程巡逻任务参与者
     */
    public SysEngineeringPatrolTaskParticipant selectPatrolTaskParticipantById(String taskId);

    /**
     * 查询工程巡逻任务参与者列表
     * 
     * @param taskParticipant 工程巡逻任务参与者
     * @return 工程巡逻任务参与者集合
     */
    public List<SysEngineeringPatrolTaskParticipant> selectPatrolTaskParticipantList(SysEngineeringPatrolTaskParticipant taskParticipant);

    /**
     * 新增工程巡逻任务参与者
     * 
     * @param taskParticipant 工程巡逻任务参与者
     * @return 结果
     */
    public int insertPatrolTaskParticipant(SysEngineeringPatrolTaskParticipant taskParticipant);

    /**
     * 修改工程巡逻任务参与者
     * 
     * @param taskParticipant 工程巡逻任务参与者
     * @return 结果
     */
    public int updatePatrolTaskParticipant(SysEngineeringPatrolTaskParticipant taskParticipant);

    /**
     * 删除工程巡逻任务参与者
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    public int deletePatrolTaskParticipantById(String taskId);

    /**
     * 批量删除工程巡逻任务参与者
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePatrolTaskParticipantByIds(String[] taskIds);

    /**
     * 批量新增工程巡逻任务参与者
     * 
     * @param taskParticipants 工程巡逻任务参与者列表
     * @return 结果
     */
    public int batchInsertPatrolTaskParticipant(List<SysEngineeringPatrolTaskParticipant> taskParticipants);
}