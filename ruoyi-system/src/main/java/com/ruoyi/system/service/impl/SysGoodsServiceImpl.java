
package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysGoodsMapper;
import com.ruoyi.system.domain.SysGoods;
import com.ruoyi.system.service.ISysGoodsService;

/**
 * 普通货品Service业务层处理
 * 
 */
@Service
public class SysGoodsServiceImpl implements ISysGoodsService {
    @Autowired
    private SysGoodsMapper sysGoodsMapper;

    /**
     * 查询普通货品
     * 
     * @param batchId 普通货品ID
     * @return 普通货品
     */
    @Override
    public SysGoods selectGoodsById(String batchId) {
        return sysGoodsMapper.selectGoodsById(batchId);
    }

    /**
     * 查询普通货品列表
     * 
     * @param goods 普通货品
     * @return 普通货品
     */
    @Override
    public List<SysGoods> selectGoodsList(SysGoods goods) {
        return sysGoodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增普通货品
     * 
     * @param goods 普通货品
     * @return 结果
     */
    @Override
    public int insertGoods(SysGoods goods) {
        return sysGoodsMapper.insertGoods(goods);
    }

    /**
     * 修改普通货品
     * 
     * @param goods 普通货品
     * @return 结果
     */
    @Override
    public int updateGoods(SysGoods goods) {
        return sysGoodsMapper.updateGoods(goods);
    }

    /**
     * 批量删除普通货品
     * 
     * @param batchIds 需要删除的普通货品ID
     * @return 结果
     */
    @Override
    public int deleteGoodsByIds(String[] batchIds) {
        return sysGoodsMapper.deleteGoodsByIds(batchIds);
    }

    /**
     * 删除普通货品信息
     * 
     * @param batchId 普通货品ID
     * @return 结果
     */
    @Override
    public int deleteGoodsById(String batchId) {
        return sysGoodsMapper.deleteGoodsById(batchId);
    }
}