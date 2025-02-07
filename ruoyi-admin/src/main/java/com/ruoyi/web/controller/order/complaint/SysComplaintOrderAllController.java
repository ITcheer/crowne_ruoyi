package com.ruoyi.web.controller.order.complaint;

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
import com.ruoyi.system.domain.SysComplaintOrder;
import com.ruoyi.system.service.ISysComplaintOrderService;
import com.ruoyi.system.domain.SysPersonalMessageNotification;
import com.ruoyi.system.service.ISysPersonalMessageNotificationService;
import com.ruoyi.common.utils.AzureEmailUtils;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

/**
 * 投诉订单 信息操作处理
 * 
 */
@RestController
@RequestMapping("/complaintOrder/all")
public class SysComplaintOrderAllController extends BaseController
{
    @Autowired
    private ISysComplaintOrderService complaintOrderService;

    @Autowired
    private ISysPersonalMessageNotificationService notificationService;

    /**
     * 查询投诉订单列表
     */
    @PreAuthorize("@ss.hasPermi('complaintOrder:all:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysComplaintOrder complaintOrder)
    {
        startPage();
        List<SysComplaintOrder> list = complaintOrderService.selectComplaintOrderList(complaintOrder);
        return getDataTable(list);
    }

    /**
     * 导出投诉订单列表
     */
    @PreAuthorize("@ss.hasPermi('complaintOrder:all:export')")
    @Log(title = "投诉订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysComplaintOrder complaintOrder)
    {
        List<SysComplaintOrder> list = complaintOrderService.selectComplaintOrderList(complaintOrder);
        ExcelUtil<SysComplaintOrder> util = new ExcelUtil<SysComplaintOrder>(SysComplaintOrder.class);
        util.exportExcel(response, list, "投诉订单数据");
    }

    /**
     * 获取投诉订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('complaintOrder:all:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable("issueId") String issueId)
    {
        return AjaxResult.success(complaintOrderService.selectComplaintOrderById(issueId));
    }

    /**
     * 新增投诉订单
     */
    @PreAuthorize("@ss.hasPermi('complaintOrder:all:add')")
    @Log(title = "投诉订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysComplaintOrder complaintOrder)
    {
        int result = complaintOrderService.insertComplaintOrder(complaintOrder);
        if (result > 0) {
            // 新增个人消息通知
            SysPersonalMessageNotification notification = new SysPersonalMessageNotification();
            notification.setNotificationId(UUID.randomUUID().toString());
            notification.setUserId("-1");
            notification.setMessageTitle("新增投诉工单");
            notification.setMessageContent("您有一个新的投诉工单，请及时处理。");
            notification.setNotificationType("工单通知");
            notification.setNotificationSource("投诉工单模块");
            notification.setReadStatus(0);
            notificationService.insertPersonalMessageNotification(notification);

            // 发送邮件通知给工单发起人
            String issuerEmail = complaintOrder.getIssuerEmail();
            String issuerSubject = "您的投诉工单已受理";
            String workOrderUrl = "http://127.0.0.1:5000/query/suggestions/cn/"+complaintOrder.getIssueId();
            AzureEmailUtils.sendHtmlEmail(
                issuerEmail, 
                issuerSubject, 
                AzureEmailUtils.EmailTemplate.COMPLAINT_ORDER_ACCEPTED_NOTIFICATION, 
                complaintOrder.getIssuerName(), 
                complaintOrder.getIssuerEmail(), 
                complaintOrder.getIssuerPhone(), 
                complaintOrder.getIssueDetails(), 
                workOrderUrl
            );
        }
        return toAjax(result);
    }

    /**
     * 修改投诉订单
     */
    @PreAuthorize("@ss.hasPermi('complaintOrder:all:edit')")
    @Log(title = "投诉订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysComplaintOrder complaintOrder)
    {
        int result = complaintOrderService.updateComplaintOrder(complaintOrder);
        if (result > 0 && "Resolved".equals(complaintOrder.getProcessingStatus())) {
            // 发送邮件通知给工单发起人
            String issuerEmail = complaintOrder.getIssuerEmail();
            String issuerSubject = "您的投诉工单已处理";
            String workOrderUrl = "http://127.0.0.1:5000/query/suggestions/cn/" + complaintOrder.getIssueId();
            AzureEmailUtils.sendHtmlEmail(
                issuerEmail, 
                issuerSubject, 
                AzureEmailUtils.EmailTemplate.COMPLAINT_ORDER_FEEDBACK_NOTIFICATION, 
                complaintOrder.getIssuerName(), 
                complaintOrder.getIssuerEmail(), 
                complaintOrder.getIssuerPhone(), 
                complaintOrder.getIssueDetails(), 
                complaintOrder.getFacilityGuysName(), 
                complaintOrder.getFacilityGuysEmail(), 
                complaintOrder.getFacilityGuyMobile(), 
                complaintOrder.getResultMessage(), 
                workOrderUrl
            );
        }
        return toAjax(result);
    }

    /**
     * 删除投诉订单
     */
    @PreAuthorize("@ss.hasPermi('complaintOrder:all:remove')")
    @Log(title = "投诉订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable String[] issueIds)
    {
        return toAjax(complaintOrderService.deleteComplaintOrderByIds(issueIds));
    }
}
