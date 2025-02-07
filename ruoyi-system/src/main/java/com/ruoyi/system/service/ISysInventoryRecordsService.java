package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysInventoryRecords;

/**
 * 出入库记录Service接口
 * 
 */
public interface ISysInventoryRecordsService {
    /**
     * 查询出入库记录
     * 
     * @param id 出入库记录ID
     * @return 出入库记录
     */
    public SysInventoryRecords selectInventoryRecordsById(String id);

    /**
     * 查询出入库记录列表
     * 
     * @param records 出入库记录
     * @return 出入库记录集合
     */
    public List<SysInventoryRecords> selectInventoryRecordsList(SysInventoryRecords records);

    /**
     * 新增出入库记录
     * 
     * @param records 出入库记录
     * @return 结果
     */
    public int insertInventoryRecords(SysInventoryRecords records);

    /**
     * 修改出入库记录
     * 
     * @param records 出入库记录
     * @return 结果
     */
    public int updateInventoryRecords(SysInventoryRecords records);

    /**
     * 批量删除出入库记录
     * 
     * @param ids 需要删除的出入库记录ID
     * @return 结果
     */
    public int deleteInventoryRecordsByIds(String[] ids);

    /**
     * 删除出入库记录信息
     * 
     * @param id 出入库记录ID
     * @return 结果
     */
    public int deleteInventoryRecordsById(String id);

    /**
     * 根据批次ID查询出入库记录列表
     * 
     * @param batchId 批次ID
     * @return 出入库记录集合
     */
    public List<SysInventoryRecords> selectInventoryRecordsListByBatchId(String batchId);
}
