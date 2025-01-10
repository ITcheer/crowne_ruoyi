package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPersonalMessageNotification;

public interface SysPersonalMessageNotificationMapper {

    /**
     * 根据通知ID查询通知
     * 
     * @param notificationId 通知ID
     * @return 通知信息
     */
    SysPersonalMessageNotification selectPersonalMessageNotificationById(String notificationId);

    /**
     * 查询通知列表
     * 
     * @param notification 通知信息
     * @return 通知集合
     */
    List<SysPersonalMessageNotification> selectPersonalMessageNotificationList(SysPersonalMessageNotification notification);

    /**
     * 新增通知
     * 
     * @param notification 通知信息
     * @return 结果
     */
    int insertPersonalMessageNotification(SysPersonalMessageNotification notification);

    /**
     * 修改通知
     * 
     * @param notification 通知信息
     * @return 结果
     */
    int updatePersonalMessageNotification(SysPersonalMessageNotification notification);

    /**
     * 删除通知
     * 
     * @param notificationId 通知ID
     * @return 结果
     */
    int deletePersonalMessageNotificationById(String notificationId);

    /**
     * 批量删除通知
     * 
     * @param notificationIds 需要删除的通知ID
     * @return 结果
     */
    int deletePersonalMessageNotificationByIds(String[] notificationIds);
}


