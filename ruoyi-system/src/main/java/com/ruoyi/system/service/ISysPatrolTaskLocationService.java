package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysPatrolTaskLocation;

/**
 * 巡逻任务地点对应关系 服务层
 * 
 */
public interface ISysPatrolTaskLocationService
{
    /**
     * 查询巡逻
     * 
     * 实体中两个属性，一个是任务ID，一个是地点ID
     * 都可以作为查询条件
     * 
     * @param taskId 任务ID
     * @return 巡逻任务地点对应关系
     */
    public List<SysPatrolTaskLocation> selectPatrolTaskLocation(SysPatrolTaskLocation taskLocation);

    /**
     * 新增巡逻任务地点对应关系
     * 
     * @param taskLocation 巡逻任务地点对应关系
     * @return 结果
     */
    public int insertPatrolTaskLocation(SysPatrolTaskLocation taskLocation);

    /**
     * 批量新增巡逻任务地点对应关系
     * 
     * @param taskLocations 巡逻任务地点对应关系列表
     * @return 结果
     */
    public int batchInsertPatrolTaskLocation(List<SysPatrolTaskLocation> taskLocations);

    /**
     * 删除巡逻任务地点对应关系
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    public int deletePatrolTaskLocationByTaskId(String taskId);
}
