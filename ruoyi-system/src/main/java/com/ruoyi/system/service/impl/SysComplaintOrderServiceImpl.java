package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysComplaintOrder;
import com.ruoyi.system.mapper.SysComplaintOrderMapper;
import com.ruoyi.system.service.ISysComplaintOrderService;

/**
 * 投诉订单 服务层实现
 * 
 */
@Service
public class SysComplaintOrderServiceImpl implements ISysComplaintOrderService
{
    @Autowired
    private SysComplaintOrderMapper orderMapper;

    @Override
    public SysComplaintOrder selectComplaintOrderById(String issueId)
    {
        return orderMapper.selectComplaintOrderById(issueId);
    }

    @Override
    public List<SysComplaintOrder> selectComplaintOrderList(SysComplaintOrder order)
    {
        return orderMapper.selectComplaintOrderList(order);
    }

    @Override
    public int insertComplaintOrder(SysComplaintOrder order)
    {
        return orderMapper.insertComplaintOrder(order);
    }

    @Override
    public int updateComplaintOrder(SysComplaintOrder order)
    {
        return orderMapper.updateComplaintOrder(order);
    }

    @Override
    public int deleteComplaintOrderByIds(String[] issueIds)
    {
        return orderMapper.deleteComplaintOrderByIds(issueIds);
    }

}

