package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysCleaningOrder;
import com.ruoyi.system.mapper.SysCleaningOrderMapper;
import com.ruoyi.system.service.ISysCleaningOrderService;

/**
 * 清洁订单 服务层实现
 * 
 */
@Service
public class SysCleaningOrderServiceImpl implements ISysCleaningOrderService
{
    @Autowired
    private SysCleaningOrderMapper orderMapper;

    @Override
    public SysCleaningOrder selectCleaningOrderById(String issueId)
    {
        return orderMapper.selectCleaningOrderById(issueId);
    }

    @Override
    public List<SysCleaningOrder> selectCleaningOrderList(SysCleaningOrder order)
    {
        return orderMapper.selectCleaningOrderList(order);
    }

    @Override
    public int insertCleaningOrder(SysCleaningOrder order)
    {
        return orderMapper.insertCleaningOrder(order);
    }

    @Override
    public int updateCleaningOrder(SysCleaningOrder order)
    {
        return orderMapper.updateCleaningOrder(order);
    }

    @Override
    public int deleteCleaningOrderByIds(String[] issueIds)
    {
        return orderMapper.deleteCleaningOrderByIds(issueIds);
    }

}
