package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysCleaningOrder;

/**
 * 清洁订单 数据层
 * 
 */
public interface SysCleaningOrderMapper
{
    /**
     * 查询清洁订单信息
     * 
     * @param issueId 订单ID
     * @return 清洁订单信息
     */
    public SysCleaningOrder selectCleaningOrderById(String issueId);

    /**
     * 查询清洁订单列表
     * 
     * @param order 清洁订单信息
     * @return 清洁订单集合
     */
    public List<SysCleaningOrder> selectCleaningOrderList(SysCleaningOrder order);

    /**
     * 新增清洁订单
     * 
     * @param order 清洁订单信息
     * @return 结果
     */
    public int insertCleaningOrder(SysCleaningOrder order);

    /**
     * 修改清洁订单
     * 
     * @param order 清洁订单信息
     * @return 结果
     */
    public int updateCleaningOrder(SysCleaningOrder order);

    /**
     * 删除清洁订单
     * 
     * @param issueId 订单ID
     * @return 结果
     */
    public int deleteCleaningOrderById(String issueId);

    /**
     * 批量删除清洁订单
     * 
     * @param issueIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCleaningOrderByIds(String[] issueIds);
}
