package com.ruoyi.web.controller.notification;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysPersonalMessageNotification;
import com.ruoyi.system.service.ISysPersonalMessageNotificationService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 个人消息通知 控制器
 */
@RestController
@RequestMapping("/notification/personal")
public class SysPersonalMessageNotificationController extends BaseController {
    @Autowired
    private ISysPersonalMessageNotificationService notificationService;

    /**
     * 获取个人消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('notification:personal:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPersonalMessageNotification notification) {
        startPage();
        List<SysPersonalMessageNotification> list = notificationService.selectPersonalMessageNotificationList(notification);
        return getDataTable(list);
    }

    /**
     * 根据通知ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('notification:personal:query')")
    @GetMapping(value = "/{notificationId}")
    public AjaxResult getInfo(@PathVariable String notificationId) {
        return success(notificationService.selectPersonalMessageNotificationById(notificationId));
    }

    /**
     * 新增个人消息通知
     */
    @PreAuthorize("@ss.hasPermi('notification:personal:add')")
    @Log(title = "个人消息通知管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPersonalMessageNotification notification) {
        notification.setNotificationId(UUID.randomUUID().toString());
        return toAjax(notificationService.insertPersonalMessageNotification(notification));
    }

    /**
     * 修改个人消息通知
     */
    @PreAuthorize("@ss.hasPermi('notification:personal:edit')")
    @Log(title = "个人消息通知管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPersonalMessageNotification notification) {
        return toAjax(notificationService.updatePersonalMessageNotification(notification));
    }

    /**
     * 删除个人消息通知
     */
    @PreAuthorize("@ss.hasPermi('notification:personal:remove')")
    @Log(title = "个人消息通知管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{notificationIds}")
    public AjaxResult remove(@PathVariable String[] notificationIds) {
        return toAjax(notificationService.deletePersonalMessageNotificationByIds(notificationIds));
    }

    /**
     * 根据用户ID获取个人消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('notification:personal:list')")
    @GetMapping("/user/{userId}")
    public AjaxResult listByUserId(@PathVariable String userId) {
        List<SysPersonalMessageNotification> list = notificationService.selectPersonalMessageNotificationByUserId(userId);

        // 获取当前登录用户角色
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        String roleName = currentUser.getRoles().get(0).getRoleName();

        // 如果用户角色为超级管理员或工程主管，展示 userId 为 -1 的个人消息
        if ("超级管理员".equals(roleName) || "工程主管".equals(roleName)) {
            List<SysPersonalMessageNotification> adminList = notificationService.selectPersonalMessageNotificationByUserId("-1");
            list.addAll(adminList);
        }

        return success(list);
    }
}
