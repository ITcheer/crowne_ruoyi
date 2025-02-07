package com.ruoyi.web.controller.order.clean;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysCleaningOrder;
import com.ruoyi.system.service.ISysCleaningOrderService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.common.core.domain.entity.SysUser;
import java.util.UUID;
import com.ruoyi.common.utils.AzureEmailUtils;

/**
 * 我的清洁工单 控制器
 * 
 */
@RestController
@RequestMapping("/cleaningOrder/my")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SysCleaningOrderMyController extends BaseController
{
    @Autowired
    private ISysCleaningOrderService cleaningOrderService;

    @Autowired
    private ISysUserService userService;

    /**
     * 获取我的清洁工单列表
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCleaningOrder order)
    {   
        startPage();
        // 获取当前登录用户
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        Long userId = currentUser.getUserId();
        String roleName = currentUser.getRoles().get(0).getRoleName();

        if ("超级管理员".equals(roleName)||"GUIS管理员".equals(roleName)) {
            // 超级管理员展示全部数据
            List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(order);
            return getDataTable(list);
        } else if ("工程主管".equals(roleName)) {
            // 工程主管展示全部状态为Undistributed的数据和该userid下的数据
            order.setFacilityGuyId(null);
            List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(order);
            list.removeIf(o -> !userId.toString().equals(o.getFacilityGuyId()) && !"Undistributed".equals(o.getProcessingStatus()));
            return getDataTable(list);
        } else {
            // 其他角色按userid展示
            order.setFacilityGuyId(userId.toString());
            List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(order);
            return getDataTable(list);
        }
    }

    /*
     * 导出清洁工单数据
     */
    @Log(title = "清洁工单管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCleaningOrder order)
    {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        order.setFacilityGuyId(userId.toString());
        List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(order);
        ExcelUtil<SysCleaningOrder> util = new ExcelUtil<SysCleaningOrder>(SysCleaningOrder.class);
        util.exportExcel(response, list, "清洁工单数据");
    }

    /**
     * 根据工单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable String issueId)
    {
        return success(cleaningOrderService.selectCleaningOrderById(issueId));
    }

    /**
     * 新增清洁工单
     */
    @Log(title = "清洁工单管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysCleaningOrder order)
    {
        return toAjax(cleaningOrderService.insertCleaningOrder(order));
    }

    /**
     * 修改清洁工单
     */
    @Log(title = "清洁工单管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysCleaningOrder order)
    {
        int result = cleaningOrderService.updateCleaningOrder(order);
        return toAjax(result);
    }

    /**
     * 删除清洁工单
     */
    @Log(title = "清洁工单管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:remove')")
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable String[] issueIds)
    {
        return toAjax(cleaningOrderService.deleteCleaningOrderByIds(issueIds));
    }

    /**
     * 上传图片到 Azure Blob
     */
    @Log(title = "清洁工单管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:upload')")
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
     * 发送清洁工单完成邮件
     */
    @Log(title = "清洁工单管理", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:sendCompletionEmail')")
    @PostMapping("/sendCompletionEmail/{issueId}")
    public AjaxResult sendCompletionEmail(@PathVariable String issueId) {
        SysCleaningOrder order = cleaningOrderService.selectCleaningOrderById(issueId);
        if (order == null) {
            return error("工单不存在");
        }

        String recipientEmail = order.getIssuerEmail();
        String subject = "您的清洁工单已完成";
        String workOrderUrl = "http://127.0.0.1:5000/query/clean/cn/" + order.getIssueId();

        AzureEmailUtils.sendHtmlEmail(
            recipientEmail,
            subject,
            AzureEmailUtils.EmailTemplate.ORDER_COMPLETED_NOTIFICATION,
            order.getIssuerName(),
            order.getIssuerEmail(),
            order.getIssuerPhone(),
            order.getClassroom(),
            order.getFloor(),
            "清洁",
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
     * 发送清洁工单未完成邮件
     */
    @Log(title = "清洁工单管理", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:sendIncompleteEmail')")
    @PostMapping("/sendIncompleteEmail/{issueId}")
    public AjaxResult sendIncompleteEmail(@PathVariable String issueId) {
        SysCleaningOrder order = cleaningOrderService.selectCleaningOrderById(issueId);
        if (order == null) {
            return error("工单不存在");
        }

        String recipientEmail = order.getIssuerEmail();
        String subject = "您的清洁工单未完成";
        String workOrderUrl = "http://127.0.0.1:5000/query/clean/cn/" + order.getIssueId();

        AzureEmailUtils.sendHtmlEmail(
            recipientEmail,
            subject,
            AzureEmailUtils.EmailTemplate.ORDER_INCOMPLETE_NOTIFICATION,
            order.getIssuerName(),
            order.getIssuerEmail(),
            order.getIssuerPhone(),
            order.getClassroom(),
            order.getFloor(),
            "清洁",
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
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:query')")
    @GetMapping("/profile")
    public AjaxResult getUserProfile() {
        SysUser user = userService.selectUserById(SecurityUtils.getLoginUser().getUser().getUserId());
        return AjaxResult.success(user);
    }

    /**
     * 根据部门id获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:query')")
    @GetMapping("/dept/{deptId}")
    public AjaxResult listUserByDeptId(@PathVariable Long deptId) {
        List<SysUser> list = userService.selectUserListByDeptId(deptId);
        return AjaxResult.success(list);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('cleaningOrder:my:query')")
    @GetMapping("/user/{userId}")
    public AjaxResult getUser(@PathVariable Long userId) {
        SysUser user = userService.selectUserById(userId);
        return AjaxResult.success(user);
    }
}
