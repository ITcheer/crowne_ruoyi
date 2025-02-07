
package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysEngineeringPatrolTask;

/**
 * 工程巡逻任务 数据层
 */
public interface SysEngineeringPatrolTaskMapper
{
    /**
     * 查询工程巡逻任务
     * 
     * @param id 任务ID
     * @return 工程巡逻任务
     */
    public SysEngineeringPatrolTask selectPatrolTaskById(String id);

    /**
     * 查询工程巡逻任务列表
     * 
     * @param task 工程巡逻任务
     * @return 工程巡逻任务集合
     */
    public List<SysEngineeringPatrolTask> selectPatrolTaskList(SysEngineeringPatrolTask task);

    /**
     * 新增工程巡逻任务
     * 
     * @param task 工程巡逻任务
     * @return 结果
     */
    public int insertPatrolTask(SysEngineeringPatrolTask task);

    /**
     * 修改工程巡逻任务
     * 
     * @param task 工程巡逻任务
     * @return 结果
     */
    public int updatePatrolTask(SysEngineeringPatrolTask task);

    /**
     * 删除工程巡逻任务
     * 
     * @param id 任务ID
     * @return 结果
     */
    public int deletePatrolTaskById(String id);

    /**
     * 批量删除工程巡逻任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePatrolTaskByIds(String[] ids);
}


