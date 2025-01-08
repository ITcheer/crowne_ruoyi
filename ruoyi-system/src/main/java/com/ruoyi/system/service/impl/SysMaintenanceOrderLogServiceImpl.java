package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysMaintenanceOrderLog;
import com.ruoyi.system.mapper.SysMaintenanceOrderLogMapper;
import com.ruoyi.system.service.ISysMaintenanceOrderLogService;

/**
 * 维护订单日志 服务层实现
 */
@Service
public class SysMaintenanceOrderLogServiceImpl implements ISysMaintenanceOrderLogService {

    @Autowired
    private SysMaintenanceOrderLogMapper logMapper;

    @Override
    public SysMaintenanceOrderLog selectMaintenanceOrderLogById(String logId) {
        return logMapper.selectMaintenanceOrderLogById(logId);
    }

    @Override
    public List<SysMaintenanceOrderLog> selectMaintenanceOrderLogList(SysMaintenanceOrderLog log) {
        return logMapper.selectMaintenanceOrderLogList(log);
    }

    @Override
    public int insertMaintenanceOrderLog(SysMaintenanceOrderLog log) {
        return logMapper.insertMaintenanceOrderLog(log);
    }

    @Override
    public int updateMaintenanceOrderLog(SysMaintenanceOrderLog log) {
        return logMapper.updateMaintenanceOrderLog(log);
    }

    @Override
    public int deleteMaintenanceOrderLogByIds(String[] logIds) {
        return logMapper.deleteMaintenanceOrderLogByIds(logIds);
    }
}
