package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysFixedAssets;

/**
 * 固定资产Mapper接口
 * 
 */
public interface SysFixedAssetsMapper {
    /**
     * 查询固定资产
     * 
     * @param batchId 固定资产ID
     * @return 固定资产
     */
    public SysFixedAssets selectFixedAssetsById(String batchId);

    /**
     * 查询固定资产列表
     * 
     * @param fixedAssets 固定资产
     * @return 固定资产集合
     */
    public List<SysFixedAssets> selectFixedAssetsList(SysFixedAssets fixedAssets);

    /**
     * 新增固定资产
     * 
     * @param fixedAssets 固定资产
     * @return 结果
     */
    public int insertFixedAssets(SysFixedAssets fixedAssets);

    /**
     * 修改固定资产
     * 
     * @param fixedAssets 固定资产
     * @return 结果
     */
    public int updateFixedAssets(SysFixedAssets fixedAssets);

    /**
     * 删除固定资产
     * 
     * @param batchId 固定资产ID
     * @return 结果
     */
    public int deleteFixedAssetsById(String batchId);

    /**
     * 批量删除固定资产
     * 
     * @param batchIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteFixedAssetsByIds(String[] batchIds);
}
