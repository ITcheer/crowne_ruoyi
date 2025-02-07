package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysComplaintOrder;

/**
 * 投诉订单 数据层
 * 
 */
public interface SysComplaintOrderMapper
{
    /**
     * 查询投诉订单信息
     * 
     * @param issueId 订单ID
     * @return 投诉订单信息
     */
    public SysComplaintOrder selectComplaintOrderById(String issueId);

    /**
     * 查询投诉订单列表
     * 
     * @param order 投诉订单信息
     * @return 投诉订单集合
     */
    public List<SysComplaintOrder> selectComplaintOrderList(SysComplaintOrder order);

    /**
     * 新增投诉订单
     * 
     * @param order 投诉订单信息
     * @return 结果
     */
    public int insertComplaintOrder(SysComplaintOrder order);

    /**
     * 修改投诉订单
     * 
     * @param order 投诉订单信息
     * @return 结果
     */
    public int updateComplaintOrder(SysComplaintOrder order);

    /**
     * 删除投诉订单
     * 
     * @param issueId 订单ID
     * @return 结果
     */
    public int deleteComplaintOrderById(String issueId);

    /**
     * 批量删除投诉订单
     * 
     * @param issueIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteComplaintOrderByIds(String[] issueIds);
}
