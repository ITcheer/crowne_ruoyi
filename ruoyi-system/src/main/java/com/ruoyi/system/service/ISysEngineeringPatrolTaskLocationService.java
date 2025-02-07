package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskLocation;

/**
 * 工程巡逻任务位置 服务层
 */
public interface ISysEngineeringPatrolTaskLocationService
{
    /**
     * 查询工程巡逻任务位置
     * 
     * @param taskId 任务ID
     * @return 工程巡逻任务位置
     */
    public SysEngineeringPatrolTaskLocation selectPatrolTaskLocationById(String taskId);

    /**
     * 查询工程巡逻任务位置列表
     * 
     * @param taskLocation 工程巡逻任务位置
     * @return 工程巡逻任务位置集合
     */
    public List<SysEngineeringPatrolTaskLocation> selectPatrolTaskLocationList(SysEngineeringPatrolTaskLocation taskLocation);

    /**
     * 新增工程巡逻任务位置
     * 
     * @param taskLocation 工程巡逻任务位置
     * @return 结果
     */
    public int insertPatrolTaskLocation(SysEngineeringPatrolTaskLocation taskLocation);

    /**
     * 修改工程巡逻任务位置
     * 
     * @param taskLocation 工程巡逻任务位置
     * @return 结果
     */
    public int updatePatrolTaskLocation(SysEngineeringPatrolTaskLocation taskLocation);

    /**
     * 删除工程巡逻任务位置
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    public int deletePatrolTaskLocationById(String taskId);

    /**
     * 批量删除工程巡逻任务位置
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePatrolTaskLocationByIds(String[] taskIds);

    /**
     * 批量新增工程巡逻任务位置
     * 
     * @param taskLocations 工程巡逻任务位置列表
     * @return 结果
     */
    public int batchInsertPatrolTaskLocation(List<SysEngineeringPatrolTaskLocation> taskLocations);
}