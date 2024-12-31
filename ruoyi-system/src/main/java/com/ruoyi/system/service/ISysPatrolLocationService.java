package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysPatrolLocation;

/**
 * 巡逻地点 服务层
 * 
 */
public interface ISysPatrolLocationService
{
    /**
     * 查询巡逻地点信息
     * 
     * @param id 地点ID
     * @return 巡逻地点信息
     */
    public SysPatrolLocation selectPatrolLocationById(String id);

    /**
     * 查询巡逻地点列表
     * 
     * @param location 巡逻地点信息
     * @return 巡逻地点集合
     */
    public List<SysPatrolLocation> selectPatrolLocationList(SysPatrolLocation location);

    /**
     * 新增巡逻地点
     * 
     * @param location 巡逻地点信息
     * @return 结果
     */
    public int insertPatrolLocation(SysPatrolLocation location);

    /**
     * 修改巡逻地点
     * 
     * @param location 巡逻地点信息
     * @return 结果
     */
    public int updatePatrolLocation(SysPatrolLocation location);

    /**
     * 删除巡逻地点
     * 
     * @param id 地点ID
     * @return 结果
     */
    public int deletePatrolLocationById(String id);

    /**
     * 批量删除巡逻地点
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePatrolLocationByIds(String[] ids);
}
