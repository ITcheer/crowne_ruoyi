package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysEngineeringPatrolLocation;

/**
 * 工程巡逻位置 服务层
 */
public interface ISysEngineeringPatrolLocationService
{
    /**
     * 查询工程巡逻位置
     * 
     * @param id 位置ID
     * @return 工程巡逻位置
     */
    public SysEngineeringPatrolLocation selectPatrolLocationById(String id);

    /**
     * 查询工程巡逻位置列表
     * 
     * @param location 工程巡逻位置
     * @return 工程巡逻位置集合
     */
    public List<SysEngineeringPatrolLocation> selectPatrolLocationList(SysEngineeringPatrolLocation location);

    /**
     * 新增工程巡逻位置
     * 
     * @param location 工程巡逻位置
     * @return 结果
     */
    public int insertPatrolLocation(SysEngineeringPatrolLocation location);

    /**
     * 修改工程巡逻位置
     * 
     * @param location 工程巡逻位置
     * @return 结果
     */
    public int updatePatrolLocation(SysEngineeringPatrolLocation location);

    /**
     * 删除工程巡逻位置
     * 
     * @param id 位置ID
     * @return 结果
     */
    public int deletePatrolLocationById(String id);

    /**
     * 批量删除工程巡逻位置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePatrolLocationByIds(String[] ids);
}
