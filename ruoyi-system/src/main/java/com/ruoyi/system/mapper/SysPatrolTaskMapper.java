package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPatrolTask;

/**
 * 巡逻任务 数据层
 * 
 */
public interface SysPatrolTaskMapper
{
    /**
     * 查询巡逻任务信息
     * 
     * @param id 任务ID
     * @return 巡逻任务信息
     */
    public SysPatrolTask selectPatrolTaskById(String id);

    /**
     * 查询巡逻任务列表
     * 
     * @param task 巡逻任务信息
     * @return 巡逻任务集合
     */
    public List<SysPatrolTask> selectPatrolTaskList(SysPatrolTask task);

    /**
     * 新增巡逻任务
     * 
     * @param task 巡逻任务信息
     * @return 结果
     */
    public int insertPatrolTask(SysPatrolTask task);

    /**
     * 修改巡逻任务
     * 
     * @param task 巡逻任务信息
     * @return 结果
     */
    public int updatePatrolTask(SysPatrolTask task);

    /**
     * 删除巡逻任务
     * 
     * @param id 任务ID
     * @return 结果
     */
    public int deletePatrolTaskById(String id);

    /**
     * 批量删除巡逻任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePatrolTaskByIds(String[] ids);
}
