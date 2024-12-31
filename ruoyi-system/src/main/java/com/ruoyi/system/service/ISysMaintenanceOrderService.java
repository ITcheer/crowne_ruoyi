package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysMaintenanceOrder;

/**
 * 维护订单 服务层
 * 
 */
public interface ISysMaintenanceOrderService
{
    /**
     * 查询维护订单信息
     * 
     * @param issueId 订单ID
     * @return 维护订单信息
     */
    public SysMaintenanceOrder selectMaintenanceOrderById(String issueId);

    /**
     * 查询维护订单列表
     * 
     * @param order 维护订单信息
     * @return 维护订单集合
     */
    public List<SysMaintenanceOrder> selectMaintenanceOrderList(SysMaintenanceOrder order);

    /**
     * 新增维护订单
     * 
     * @param order 维护订单信息
     * @return 结果
     */
    public int insertMaintenanceOrder(SysMaintenanceOrder order);

    /**
     * 修改维护订单
     * 
     * @param order 维护订单信息
     * @return 结果
     */
    public int updateMaintenanceOrder(SysMaintenanceOrder order);

    /**
     * 批量删除维护订单
     * 
     * @param issueIds 需要删除的订单ID
     * @return 结果
     */
    public int deleteMaintenanceOrderByIds(String[] issueIds);
}