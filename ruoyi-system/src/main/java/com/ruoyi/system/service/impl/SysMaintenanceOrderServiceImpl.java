package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysMaintenanceOrder;
import com.ruoyi.system.mapper.SysMaintenanceOrderMapper;
import com.ruoyi.system.service.ISysMaintenanceOrderService;

/**
 * 维护订单 服务层实现
 * 
 */
@Service
public class SysMaintenanceOrderServiceImpl implements ISysMaintenanceOrderService
{
    @Autowired
    private SysMaintenanceOrderMapper orderMapper;

    @Override
    public SysMaintenanceOrder selectMaintenanceOrderById(String issueId)
    {
        return orderMapper.selectMaintenanceOrderById(issueId);
    }

    @Override
    public List<SysMaintenanceOrder> selectMaintenanceOrderList(SysMaintenanceOrder order)
    {
        return orderMapper.selectMaintenanceOrderList(order);
    }

    @Override
    public int insertMaintenanceOrder(SysMaintenanceOrder order)
    {
        return orderMapper.insertMaintenanceOrder(order);
    }

    @Override
    public int updateMaintenanceOrder(SysMaintenanceOrder order)
    {
        return orderMapper.updateMaintenanceOrder(order);
    }

    @Override
    public int deleteMaintenanceOrderByIds(String[] issueIds)
    {
        return orderMapper.deleteMaintenanceOrderByIds(issueIds);
    }

}