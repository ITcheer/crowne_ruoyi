
package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.system.domain.SysFixedAssets;
import com.ruoyi.system.mapper.SysFixedAssetsMapper;
import com.ruoyi.system.service.ISysFixedAssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysFixedAssetsServiceImpl implements ISysFixedAssetsService {
    @Autowired
    private SysFixedAssetsMapper sysFixedAssetsMapper;

    @Override
    public SysFixedAssets selectFixedAssetsById(String batchId) {
        return sysFixedAssetsMapper.selectFixedAssetsById(batchId);
    }

    @Override
    public List<SysFixedAssets> selectFixedAssetsList(SysFixedAssets fixedAssets) {
        return sysFixedAssetsMapper.selectFixedAssetsList(fixedAssets);
    }

    @Override
    public int insertFixedAssets(SysFixedAssets fixedAssets) {
        return sysFixedAssetsMapper.insertFixedAssets(fixedAssets);
    }

    @Override
    public int updateFixedAssets(SysFixedAssets fixedAssets) {
        return sysFixedAssetsMapper.updateFixedAssets(fixedAssets);
    }

    @Override
    public int deleteFixedAssetsByIds(String[] batchIds) {
        return sysFixedAssetsMapper.deleteFixedAssetsByIds(batchIds);
    }

    @Override
    public int deleteFixedAssetsById(String batchId) {
        return sysFixedAssetsMapper.deleteFixedAssetsById(batchId);
    }
}