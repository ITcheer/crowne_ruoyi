package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysFacilityOrder;

/**
 * 校工订单 服务层
 * 
 */
public interface ISysFacilityOrderService
{
    /**
     * 查询校工订单信息
     * 
     * @param issueId 订单ID
     * @return 校工订单信息
     */
    public SysFacilityOrder selectFacilityOrderById(String issueId);

    /**
     * 查询校工订单列表
     * 
     * @param order 校工订单信息
     * @return 校工订单集合
     */
    public List<SysFacilityOrder> selectFacilityOrderList(SysFacilityOrder order);

    /**
     * 新增校工订单
     * 
     * @param order 校工订单信息
     * @return 结果
     */
    public int insertFacilityOrder(SysFacilityOrder order);

    /**
     * 修改校工订单
     * 
     * @param order 校工订单信息
     * @return 结果
     */
    public int updateFacilityOrder(SysFacilityOrder order);

    /**
     * 批量删除校工订单
     * 
     * @param issueIds 需要删除的订单ID
     * @return 结果
     */
    public int deleteFacilityOrderByIds(String[] issueIds);
}
