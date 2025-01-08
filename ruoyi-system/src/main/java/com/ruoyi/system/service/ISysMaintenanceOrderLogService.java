package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysMaintenanceOrderLog;

/**
 * 维护订单日志 服务层
 */
public interface ISysMaintenanceOrderLogService {

    /**
     * 查询维护订单日志信息
     * 
     * @param logId 日志ID
     * @return 维护订单日志信息
     */
    public SysMaintenanceOrderLog selectMaintenanceOrderLogById(String logId);

    /**
     * 查询维护订单日志列表
     * 
     * @param log 维护订单日志信息
     * @return 维护订单日志集合
     */
    public List<SysMaintenanceOrderLog> selectMaintenanceOrderLogList(SysMaintenanceOrderLog log);

    /**
     * 新增维护订单日志
     * 
     * @param log 维护订单日志信息
     * @return 结果
     */
    public int insertMaintenanceOrderLog(SysMaintenanceOrderLog log);

    /**
     * 修改维护订单日志
     * 
     * @param log 维护订单日志信息
     * @return 结果
     */
    public int updateMaintenanceOrderLog(SysMaintenanceOrderLog log);

    /**
     * 批量删除维护订单日志
     * 
     * @param logIds 需要删除的日志ID
     * @return 结果
     */
    public int deleteMaintenanceOrderLogByIds(String[] logIds);
}
