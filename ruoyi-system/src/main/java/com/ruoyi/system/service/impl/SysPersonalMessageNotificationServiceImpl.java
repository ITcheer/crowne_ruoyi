package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysPersonalMessageNotification;
import com.ruoyi.system.mapper.SysPersonalMessageNotificationMapper;
import com.ruoyi.system.service.ISysPersonalMessageNotificationService;

/**
 * 个人消息通知 服务层实现
 * 
 */
@Service
public class SysPersonalMessageNotificationServiceImpl implements ISysPersonalMessageNotificationService {

    
    
    @Autowired
    private SysPersonalMessageNotificationMapper notificationMapper;

    @Override
    public SysPersonalMessageNotification selectPersonalMessageNotificationById(String notificationId) {
        return notificationMapper.selectPersonalMessageNotificationById(notificationId);
    }

    @Override
    public List<SysPersonalMessageNotification> selectPersonalMessageNotificationList(SysPersonalMessageNotification notification) {
        return notificationMapper.selectPersonalMessageNotificationList(notification);
    }

    @Override
    public int insertPersonalMessageNotification(SysPersonalMessageNotification notification) {
        return notificationMapper.insertPersonalMessageNotification(notification);
    }

    @Override
    public int updatePersonalMessageNotification(SysPersonalMessageNotification notification) {
        return notificationMapper.updatePersonalMessageNotification(notification);
    }

    @Override
    public int deletePersonalMessageNotificationById(String notificationId) {
        return notificationMapper.deletePersonalMessageNotificationById(notificationId);
    }

    @Override
    public int deletePersonalMessageNotificationByIds(String[] notificationIds) {
        return notificationMapper.deletePersonalMessageNotificationByIds(notificationIds);
    }

    @Override
    public List<SysPersonalMessageNotification> selectPersonalMessageNotificationByUserId(String userId) {
        return notificationMapper.selectPersonalMessageNotificationByUserId(userId);
    }
}
