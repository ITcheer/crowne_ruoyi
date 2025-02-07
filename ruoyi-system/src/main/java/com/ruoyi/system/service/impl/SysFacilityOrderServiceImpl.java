package com.ruoyi.system.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysFacilityOrder;
import com.ruoyi.system.mapper.SysFacilityOrderMapper;
import com.ruoyi.system.service.ISysFacilityOrderService;

/**
 * 校工订单 服务层实现
 * 
 */
@Service
public class SysFacilityOrderServiceImpl implements ISysFacilityOrderService
{
    @Autowired
    private SysFacilityOrderMapper orderMapper;

    @Override
    public SysFacilityOrder selectFacilityOrderById(String issueId)
    {
        return orderMapper.selectFacilityOrderById(issueId);
    }

    @Override
    public List<SysFacilityOrder> selectFacilityOrderList(SysFacilityOrder order)
    {
        return orderMapper.selectFacilityOrderList(order);
    }

    @Override
    public int insertFacilityOrder(SysFacilityOrder order)
    {
        return orderMapper.insertFacilityOrder(order);
    }

    @Override
    public int updateFacilityOrder(SysFacilityOrder order)
    {
        return orderMapper.updateFacilityOrder(order);
    }

    @Override
    public int deleteFacilityOrderByIds(String[] issueIds)
    {
        return orderMapper.deleteFacilityOrderByIds(issueIds);
    }

}

