package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysGoods;

/**
 * 普通货品Service接口
 * 
 */
public interface ISysGoodsService {
    /**
     * 查询普通货品
     * 
     * @param batchId 普通货品ID
     * @return 普通货品
     */
    public SysGoods selectGoodsById(String batchId);

    /**
     * 查询普通货品列表
     * 
     * @param goods 普通货品
     * @return 普通货品集合
     */
    public List<SysGoods> selectGoodsList(SysGoods goods);

    /**
     * 新增普通货品
     * 
     * @param goods 普通货品
     * @return 结果
     */
    public int insertGoods(SysGoods goods);

    /**
     * 修改普通货品
     * 
     * @param goods 普通货品
     * @return 结果
     */
    public int updateGoods(SysGoods goods);

    /**
     * 批量删除普通货品
     * 
     * @param batchIds 需要删除的普通货品ID
     * @return 结果
     */
    public int deleteGoodsByIds(String[] batchIds);

    /**
     * 删除普通货品信息
     * 
     * @param batchId 普通货品ID
     * @return 结果
     */
    public int deleteGoodsById(String batchId);
}
