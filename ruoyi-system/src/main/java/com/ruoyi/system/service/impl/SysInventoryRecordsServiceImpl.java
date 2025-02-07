package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysInventoryRecordsMapper;
import com.ruoyi.system.domain.SysInventoryRecords;
import com.ruoyi.system.service.ISysInventoryRecordsService;

/**
 * 出入库记录Service业务层处理
 * 
 */
@Service
public class SysInventoryRecordsServiceImpl implements ISysInventoryRecordsService {
    @Autowired
    private SysInventoryRecordsMapper sysInventoryRecordsMapper;

    /**
     * 查询出入库记录
     * 
     * @param id 出入库记录ID
     * @return 出入库记录
     */
    @Override
    public SysInventoryRecords selectInventoryRecordsById(String id) {
        return sysInventoryRecordsMapper.selectInventoryRecordsById(id);
    }

    /**
     * 查询出入库记录列表
     * 
     * @param records 出入库记录
     * @return 出入库记录集合
     */
    @Override
    public List<SysInventoryRecords> selectInventoryRecordsList(SysInventoryRecords records) {
        return sysInventoryRecordsMapper.selectInventoryRecordsList(records);
    }

    /**
     * 根据批次ID查询出入库记录列表
     * 
     * @param batchId 批次ID
     * @return 出入库记录集合
     */
    @Override
    public List<SysInventoryRecords> selectInventoryRecordsListByBatchId(String batchId) {
        return sysInventoryRecordsMapper.selectInventoryRecordsListByBatchId(batchId);
    }

    /**
     * 新增出入库记录
     * 
     * @param records 出入库记录
     * @return 结果
     */
    @Override
    public int insertInventoryRecords(SysInventoryRecords records) {
        return sysInventoryRecordsMapper.insertInventoryRecords(records);
    }

    /**
     * 修改出入库记录
     * 
     * @param records 出入库记录
     * @return 结果
     */
    @Override
    public int updateInventoryRecords(SysInventoryRecords records) {
        return sysInventoryRecordsMapper.updateInventoryRecords(records);
    }

    /**
     * 批量删除出入库记录
     * 
     * @param ids 需要删除的出入库记录ID
     * @return 结果
     */
    @Override
    public int deleteInventoryRecordsByIds(String[] ids) {
        return sysInventoryRecordsMapper.deleteInventoryRecordsByIds(ids);
    }

    /**
     * 删除出入库记录信息
     * 
     * @param id 出入库记录ID
     * @return 结果
     */
    @Override
    public int deleteInventoryRecordsById(String id) {
        return sysInventoryRecordsMapper.deleteInventoryRecordsById(id);
    }
}