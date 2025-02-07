package com.ruoyi.web.controller.order.clean;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysCleaningOrder;
import com.ruoyi.system.service.ISysCleaningOrderService;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.ruoyi.system.domain.SysPersonalMessageNotification;
import com.ruoyi.system.service.ISysPersonalMessageNotificationService;
import com.ruoyi.common.utils.AzureEmailUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

/**
 * 清洁订单 信息操作处理
 * 
 */
@RestController
@RequestMapping("/cleaningOrder/all")
public class SysCleaningOrderAllController extends BaseController
{
    @Autowired
    private ISysCleaningOrderService cleaningOrderService;

    @Autowired
    private ISysPersonalMessageNotificationService notificationService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询清洁订单列表
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:all:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCleaningOrder cleaningOrder)
    {
        startPage();
        List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(cleaningOrder);
        return getDataTable(list);
    }

    /**
     * 导出清洁订单列表
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:all:export')")
    @Log(title = "清洁订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCleaningOrder cleaningOrder)
    {
        List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(cleaningOrder);
        ExcelUtil<SysCleaningOrder> util = new ExcelUtil<SysCleaningOrder>(SysCleaningOrder.class);
        util.exportExcel(response, list, "清洁订单数据");
    }

    /**
     * 获取清洁订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:all:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable("issueId") String issueId)
    {
        return AjaxResult.success(cleaningOrderService.selectCleaningOrderById(issueId));
    }

    /**
     * 新增清洁订单
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:all:add')")
    @Log(title = "清洁订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCleaningOrder cleaningOrder)
    {
        int result = cleaningOrderService.insertCleaningOrder(cleaningOrder);
        if (result > 0) {
            // 新增个人消息通知
            SysPersonalMessageNotification notification = new SysPersonalMessageNotification();
            notification.setNotificationId(UUID.randomUUID().toString());
            notification.setUserId("-1");
            notification.setMessageTitle("新增清洁工单");
            notification.setMessageContent("您有一个新的清洁工单，请及时处理。");
            notification.setNotificationType("工单通知");
            notification.setNotificationSource("清洁工单模块");
            notification.setReadStatus(0);
            notificationService.insertPersonalMessageNotification(notification);

            // 查询角色ID为103及角色为校工主管的用户的email
            List<SysUser> engineers = userService.selectUserListByRoleId(103L);
            String workOrderUrl = "https://wonderful-tree-0e2299d00.4.azurestaticapps.net/";

            for (SysUser engineer : engineers) {
                String recipientEmail = engineer.getEmail();
                String subject = "清洁工单发起";
                AzureEmailUtils.sendHtmlEmail(
                    recipientEmail, 
                    subject, 
                    AzureEmailUtils.EmailTemplate.NEW_ORDER_NOTIFICATION, 
                    cleaningOrder.getIssuerName(), 
                    cleaningOrder.getIssuerEmail(), 
                    cleaningOrder.getIssuerPhone(), 
                    cleaningOrder.getClassroom(), 
                    cleaningOrder.getFloor(), 
                    "清洁", 
                    cleaningOrder.getUrgencyLevel(), 
                    cleaningOrder.getIssueDetails(), 
                    workOrderUrl
                );
            }

            // 发送邮件通知给工单发起人
            String issuerEmail = cleaningOrder.getIssuerEmail();
            String issuerSubject = "您的清洁工单已提起";
            String issuerWorkOrderUrl = "http://127.0.0.1:5000/query/clean/cn/" + cleaningOrder.getIssueId();
            AzureEmailUtils.sendHtmlEmail(
                issuerEmail, 
                issuerSubject, 
                AzureEmailUtils.EmailTemplate.ORDER_INITIATED_NOTIFICATION, 
                cleaningOrder.getIssuerName(), 
                cleaningOrder.getIssuerEmail(), 
                cleaningOrder.getIssuerPhone(), 
                cleaningOrder.getClassroom(), 
                cleaningOrder.getFloor(), 
                "清洁", 
                cleaningOrder.getUrgencyLevel(), 
                cleaningOrder.getIssueDetails(), 
                issuerWorkOrderUrl
            );
        }
        return toAjax(result);
    }

    /**
     * 修改清洁订单
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:all:edit')")
    @Log(title = "清洁订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCleaningOrder cleaningOrder)
    {
        return toAjax(cleaningOrderService.updateCleaningOrder(cleaningOrder));
    }

    /**
     * 删除清洁订单
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:all:remove')")
    @Log(title = "清洁订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable String[] issueIds)
    {
        return toAjax(cleaningOrderService.deleteCleaningOrderByIds(issueIds));
    }

    /**
     * 上传图片到 Azure Blob
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:all:upload')")
    @Log(title = "清洁订单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadImage")
    public AjaxResult uploadImage(@RequestPart("file") MultipartFile file) {
        if (file == null) {
            return AjaxResult.error("上传文件不能为空");
        }
        try {
            String fileName = AzureBlobUploadUtils.upload(file, "image/cleaningOrder");
            String relativePath = fileName.substring("image/".length());
            return AjaxResult.success("上传成功", relativePath);
        } catch (IOException e) {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 分配清洁订单
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:all:assign')")
    @Log(title = "清洁订单管理", businessType = BusinessType.UPDATE)
    @PatchMapping("/assign")
    public AjaxResult assign(@Validated @RequestBody SysCleaningOrder order) {
        SysCleaningOrder existingOrder = cleaningOrderService.selectCleaningOrderById(order.getIssueId());
        if (existingOrder == null) {
            return error("工单不存在");
        }
        if ("Resolved".equals(existingOrder.getProcessingStatus()) || "Can not Resolve".equals(existingOrder.getProcessingStatus())) {
            return error("工单已解决或无法解决，不能分配");
        }
        existingOrder.setFacilityGuyId(order.getFacilityGuyId());
        existingOrder.setFacilityGuysName(order.getFacilityGuysName());
        existingOrder.setFacilityGuyMobile(order.getFacilityGuyMobile());
        existingOrder.setFacilityGuysEmail(order.getFacilityGuysEmail());
        if ("Undistributed".equals(existingOrder.getProcessingStatus())) {
            existingOrder.setProcessingStatus("On Process");
        }
        int result = cleaningOrderService.updateCleaningOrder(existingOrder);
        if (result > 0) {
            SysPersonalMessageNotification notification = new SysPersonalMessageNotification();
            notification.setNotificationId(UUID.randomUUID().toString());
            notification.setUserId(order.getFacilityGuyId());
            notification.setMessageTitle("清洁工单分配");
            notification.setMessageContent("您有一个新的清洁工单，请及时处理。");
            notification.setNotificationType("工单通知");
            notification.setNotificationSource("清洁工单模块");
            notification.setReadStatus(0);
            notificationService.insertPersonalMessageNotification(notification);

            // 发送邮件通知给处理人
            String recipientEmail = order.getFacilityGuysEmail();
            String subject = "清洁工单分配";
            String workOrderUrl = "https://wonderful-tree-0e2299d00.4.azurestaticapps.net/";
            AzureEmailUtils.sendHtmlEmail(
                recipientEmail, 
                subject, 
                AzureEmailUtils.EmailTemplate.ASSIGN_ORDER_NOTIFICATION, 
                existingOrder.getIssuerName(), 
                existingOrder.getIssuerEmail(), 
                existingOrder.getIssuerPhone(), 
                existingOrder.getClassroom(), 
                existingOrder.getFloor(), 
                // existingOrder.getMaintenanceType(), 
                "清洁",
                existingOrder.getUrgencyLevel(), 
                existingOrder.getIssueDetails(), 
                workOrderUrl
            );

            // 发送邮件通知给提工单的用户
            String issuerEmail = existingOrder.getIssuerEmail();
            String issuerSubject = "您的清洁工单已分配";
            String issuerWorkOrderUrl = "http://127.0.0.1:5000/query/clean/cn/" + existingOrder.getIssueId();
            AzureEmailUtils.sendHtmlEmail(
                issuerEmail, 
                issuerSubject, 
                AzureEmailUtils.EmailTemplate.ORDER_ASSIGNED_NOTIFICATION, 
                existingOrder.getIssuerName(), 
                existingOrder.getIssuerEmail(), 
                existingOrder.getIssuerPhone(), 
                existingOrder.getClassroom(), 
                existingOrder.getFloor(), 
                // existingOrder.getMaintenanceType(), 
                "清洁",
                existingOrder.getUrgencyLevel(), 
                existingOrder.getIssueDetails(), 
                order.getFacilityGuysName(), 
                order.getFacilityGuysEmail(), 
                order.getFacilityGuyMobile(), 
                issuerWorkOrderUrl
            );
        }
        return toAjax(result);
    }
}
