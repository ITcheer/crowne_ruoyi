package com.ruoyi.web.controller.order.maintenance;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestPart;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysMaintenanceOrder;
import com.ruoyi.system.service.ISysMaintenanceOrderService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysMaintenanceOrderLog;
import com.ruoyi.system.service.ISysMaintenanceOrderLogService;
import java.util.Comparator;
import java.sql.Timestamp;
import java.util.UUID;
import com.ruoyi.common.utils.AzureEmailUtils;

/**
 * 我的维修工单 控制器
 * 
 */
@RestController
@RequestMapping("/maintenanceOrder/my")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SysMaintenanceOrderMyController extends BaseController
{
    @Autowired
    private ISysMaintenanceOrderService maintenanceOrderService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysMaintenanceOrderLogService maintenanceOrderLogService;

    /**
     * 获取我的维修工单列表
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysMaintenanceOrder order)
    {   
        startPage();
        // 获取当前登录用户
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        Long userId = currentUser.getUserId();
        String roleName = currentUser.getRoles().get(0).getRoleName();

        if ("超级管理员".equals(roleName)||"GUIS管理员".equals(roleName)) {
            // 超级管理员展示全部数据
            List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
            return getDataTable(list);
        } else if ("工程主管".equals(roleName)) {
            // 工程主管展示全部状态为Undistributed的数据和该userid下的数据
            order.setFacilityGuyId(null);
            List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
            list.removeIf(o -> !userId.toString().equals(o.getFacilityGuyId()) && !"Undistributed".equals(o.getProcessingStatus()));
            return getDataTable(list);
        } else {
            // 其他角色按userid展示
            order.setFacilityGuyId(userId.toString());
            List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
            return getDataTable(list);
        }
    }

    /*
     * 导出维修工单数据
     */
    @Log(title = "维修工单管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysMaintenanceOrder order)
    {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        order.setFacilityGuyId(userId.toString());
        List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
        ExcelUtil<SysMaintenanceOrder> util = new ExcelUtil<SysMaintenanceOrder>(SysMaintenanceOrder.class);
        util.exportExcel(response, list, "维修工单数据");
    }

    /**
     * 根据工单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable String issueId)
    {
        return success(maintenanceOrderService.selectMaintenanceOrderById(issueId));
    }

    /**
     * 新增维修工单
     */
    @Log(title = "维修工单管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMaintenanceOrder order)
    {
        return toAjax(maintenanceOrderService.insertMaintenanceOrder(order));
    }

    /**
     * 修改维修工单
     */
    @Log(title = "维修工单管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMaintenanceOrder order)
    {
        int result = maintenanceOrderService.updateMaintenanceOrder(order);
        return toAjax(result);
    }

    /**
     * 删除维修工单
     */
    @Log(title = "维修工单管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:remove')")
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable String[] issueIds)
    {
        return toAjax(maintenanceOrderService.deleteMaintenanceOrderByIds(issueIds));
    }

    /**
     * 上传图片到 Azure Blob
     */
    @Log(title = "维修工单管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:upload')")
    @PostMapping("/uploadImage")
    public AjaxResult uploadImage(@RequestPart("file") MultipartFile file) {
        if (file == null) {
            return AjaxResult.error("上传文件不能为空");
        }
        try {
            String fileName = AzureBlobUploadUtils.upload(file, "image/maintenanceOrder");
            String relativePath = fileName.substring("image/".length());
            return AjaxResult.success("上传成功", relativePath);
        } catch (IOException e) {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 发送维修工单完成邮件
     */
    @Log(title = "维修工单管理", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:sendCompletionEmail')")
    @PostMapping("/sendCompletionEmail/{issueId}")
    public AjaxResult sendCompletionEmail(@PathVariable String issueId) {
        SysMaintenanceOrder order = maintenanceOrderService.selectMaintenanceOrderById(issueId);
        if (order == null) {
            return error("工单不存在");
        }

        String recipientEmail = order.getIssuerEmail();
        String subject = "您的维修工单已完成";
        // String workOrderUrl = "https://schoolmaintain-webapp.azurewebsites.net/query/cn/" + order.getIssueId();
        
        String workOrderUrl = "http://127.0.0.1:5000/query/cn/" + order.getIssueId();

        AzureEmailUtils.sendHtmlEmail(
            recipientEmail,
            subject,
            AzureEmailUtils.EmailTemplate.ORDER_COMPLETED_NOTIFICATION,
            order.getIssuerName(),
            order.getIssuerEmail(),
            order.getIssuerPhone(),
            order.getClassroom(),
            order.getFloor(),
            order.getMaintenanceType(),
            order.getUrgencyLevel(),
            order.getIssueDetails(),
            order.getFacilityGuysName(),
            order.getFacilityGuysEmail(),
            order.getFacilityGuyMobile(),
            workOrderUrl
        );

        return success("邮件发送成功");
    }

    /**
     * 发送维修工单未完成邮件
     */
    @Log(title = "维修工单管理", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:sendIncompleteEmail')")
    @PostMapping("/sendIncompleteEmail/{issueId}")
    public AjaxResult sendIncompleteEmail(@PathVariable String issueId) {
        SysMaintenanceOrder order = maintenanceOrderService.selectMaintenanceOrderById(issueId);
        if (order == null) {
            return error("工单不存在");
        }

        String recipientEmail = order.getIssuerEmail();
        String subject = "您的维修工单未完成";
        String workOrderUrl = "http://127.0.0.1:5000/query/cn/" + order.getIssueId();

        AzureEmailUtils.sendHtmlEmail(
            recipientEmail,
            subject,
            AzureEmailUtils.EmailTemplate.ORDER_INCOMPLETE_NOTIFICATION,
            order.getIssuerName(),
            order.getIssuerEmail(),
            order.getIssuerPhone(),
            order.getClassroom(),
            order.getFloor(),
            order.getMaintenanceType(),
            order.getUrgencyLevel(),
            order.getIssueDetails(),
            order.getFacilityGuysName(),
            order.getFacilityGuysEmail(),
            order.getFacilityGuyMobile(),
            workOrderUrl
        );

        return success("邮件发送成功");
    }

    /**
     * 获取当前用户信息
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:query')")
    @GetMapping("/profile")
    public AjaxResult getUserProfile() {
        SysUser user = userService.selectUserById(SecurityUtils.getLoginUser().getUser().getUserId());
        return AjaxResult.success(user);
    }

    /**
     * 根据部门id获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:query')")
    @GetMapping("/dept/{deptId}")
    public AjaxResult listUserByDeptId(@PathVariable Long deptId) {
        List<SysUser> list = userService.selectUserListByDeptId(deptId);
        return AjaxResult.success(list);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:query')")
    @GetMapping("/user/{userId}")
    public AjaxResult getUser(@PathVariable Long userId) {
        SysUser user = userService.selectUserById(userId);
        return AjaxResult.success(user);
    }

    /**
     * 获取工单日志记录
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:query')")
    @GetMapping("/logs/{issueId}")
    public AjaxResult getLogs(@PathVariable String issueId) {
        SysMaintenanceOrderLog log = new SysMaintenanceOrderLog();
        log.setIssueId(issueId);
        List<SysMaintenanceOrderLog> list = maintenanceOrderLogService.selectMaintenanceOrderLogList(log);
        list.sort(Comparator.comparing(SysMaintenanceOrderLog::getActionTime));
        return AjaxResult.success(list);
    }

    /**
     * 新增工单日志记录
     */
    @Log(title = "工单日志管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:my:add')")
    @PostMapping("/logs")
    public AjaxResult addLog(@Validated @RequestBody SysMaintenanceOrderLog log) {
        log.setLogId(UUID.randomUUID().toString());
        log.setActionTime(new Timestamp(System.currentTimeMillis()));
        return toAjax(maintenanceOrderLogService.insertMaintenanceOrderLog(log));
    }
}
