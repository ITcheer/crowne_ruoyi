package com.ruoyi.web.controller.order.facility;

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
import com.ruoyi.system.domain.SysFacilityOrder;
import com.ruoyi.system.service.ISysFacilityOrderService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.common.core.domain.entity.SysUser;
import java.util.UUID;
import com.ruoyi.common.utils.AzureEmailUtils;

/**
 * 我的校工工单 控制器
 * 
 */
@RestController
@RequestMapping("/facilityOrder/my")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SysFacilityOrderMyController extends BaseController
{
    @Autowired
    private ISysFacilityOrderService facilityOrderService;

    @Autowired
    private ISysUserService userService;

    /**
     * 获取我的校工工单列表
     */
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFacilityOrder order)
    {   
        startPage();
        // 获取当前登录用户
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        Long userId = currentUser.getUserId();
        String roleName = currentUser.getRoles().get(0).getRoleName();

        if ("超级管理员".equals(roleName)||"GUIS管理员".equals(roleName)) {
            // 超级管理员展示全部数据
            List<SysFacilityOrder> list = facilityOrderService.selectFacilityOrderList(order);
            return getDataTable(list);
        } else if ("工程主管".equals(roleName)) {
            // 工程主管展示全部状态为Undistributed的数据和该userid下的数据
            order.setFacilityGuyId(null);
            List<SysFacilityOrder> list = facilityOrderService.selectFacilityOrderList(order);
            list.removeIf(o -> !userId.toString().equals(o.getFacilityGuyId()) && !"Undistributed".equals(o.getProcessingStatus()));
            return getDataTable(list);
        } else {
            // 其他角色按userid展示
            order.setFacilityGuyId(userId.toString());
            List<SysFacilityOrder> list = facilityOrderService.selectFacilityOrderList(order);
            return getDataTable(list);
        }
    }

    /*
     * 导出校工工单数据
     */
    @Log(title = "校工工单管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFacilityOrder order)
    {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        order.setFacilityGuyId(userId.toString());
        List<SysFacilityOrder> list = facilityOrderService.selectFacilityOrderList(order);
        ExcelUtil<SysFacilityOrder> util = new ExcelUtil<SysFacilityOrder>(SysFacilityOrder.class);
        util.exportExcel(response, list, "校工工单数据");
    }

    /**
     * 根据工单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable String issueId)
    {
        return success(facilityOrderService.selectFacilityOrderById(issueId));
    }

    /**
     * 新增校工工单
     */
    @Log(title = "校工工单管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysFacilityOrder order)
    {
        return toAjax(facilityOrderService.insertFacilityOrder(order));
    }

    /**
     * 修改校工工单
     */
    @Log(title = "校工工单管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysFacilityOrder order)
    {
        int result = facilityOrderService.updateFacilityOrder(order);
        return toAjax(result);
    }

    /**
     * 删除校工工单
     */
    @Log(title = "校工工单管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:remove')")
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable String[] issueIds)
    {
        return toAjax(facilityOrderService.deleteFacilityOrderByIds(issueIds));
    }

    /**
     * 上传图片到 Azure Blob
     */
    @Log(title = "校工工单管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:upload')")
    @PostMapping("/uploadImage")
    public AjaxResult uploadImage(@RequestPart("file") MultipartFile file) {
        if (file == null) {
            return AjaxResult.error("上传文件不能为空");
        }
        try {
            String fileName = AzureBlobUploadUtils.upload(file, "image/facilityOrder");
            String relativePath = fileName.substring("image/".length());
            return AjaxResult.success("上传成功", relativePath);
        } catch (IOException e) {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 发送校工工单完成邮件
     */
    @Log(title = "校工工单管理", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:sendCompletionEmail')")
    @PostMapping("/sendCompletionEmail/{issueId}")
    public AjaxResult sendCompletionEmail(@PathVariable String issueId) {
        SysFacilityOrder order = facilityOrderService.selectFacilityOrderById(issueId);
        if (order == null) {
            return error("工单不存在");
        }

        String recipientEmail = order.getIssuerEmail();
        String subject = "您的校工工单已完成";
        String workOrderUrl = "http://127.0.0.1:5000/query/schoolW/cn/" + order.getIssueId();

        AzureEmailUtils.sendHtmlEmail(
            recipientEmail,
            subject,
            AzureEmailUtils.EmailTemplate.ORDER_COMPLETED_NOTIFICATION,
            order.getIssuerName(),
            order.getIssuerEmail(),
            order.getIssuerPhone(),
            order.getClassroom(),
            order.getFloor(),
            "校工",
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
     * 发送校工工单未完成邮件
     */
    @Log(title = "校工工单管理", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:sendIncompleteEmail')")
    @PostMapping("/sendIncompleteEmail/{issueId}")
    public AjaxResult sendIncompleteEmail(@PathVariable String issueId) {
        SysFacilityOrder order = facilityOrderService.selectFacilityOrderById(issueId);
        if (order == null) {
            return error("工单不存在");
        }

        String recipientEmail = order.getIssuerEmail();
        String subject = "您的校工工单未完成";
        String workOrderUrl = "http://127.0.0.1:5000/query/schoolW/cn/" + order.getIssueId();

        AzureEmailUtils.sendHtmlEmail(
            recipientEmail,
            subject,
            AzureEmailUtils.EmailTemplate.ORDER_INCOMPLETE_NOTIFICATION,
            order.getIssuerName(),
            order.getIssuerEmail(),
            order.getIssuerPhone(),
            order.getClassroom(),
            order.getFloor(),
            "校工",
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
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:query')")
    @GetMapping("/profile")
    public AjaxResult getUserProfile() {
        SysUser user = userService.selectUserById(SecurityUtils.getLoginUser().getUser().getUserId());
        return AjaxResult.success(user);
    }

    /**
     * 根据部门id获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:query')")
    @GetMapping("/dept/{deptId}")
    public AjaxResult listUserByDeptId(@PathVariable Long deptId) {
        List<SysUser> list = userService.selectUserListByDeptId(deptId);
        return AjaxResult.success(list);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('facilityOrder:my:query')")
    @GetMapping("/user/{userId}")
    public AjaxResult getUser(@PathVariable Long userId) {
        SysUser user = userService.selectUserById(userId);
        return AjaxResult.success(user);
    }
}
