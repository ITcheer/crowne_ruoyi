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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.io.IOException;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysMaintenanceOrder;
import com.ruoyi.system.service.ISysMaintenanceOrderService;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;

/**
 * 全部维护工单 控制器
 * 
 */
@RestController
@RequestMapping("/maintenanceOrder/all")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SysMaintenanceOrderAllController extends BaseController
{
    @Autowired
    private ISysMaintenanceOrderService maintenanceOrderService;

    /**
     * 获取维护工单列表
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysMaintenanceOrder order)
    {
        startPage();
        List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
        return getDataTable(list);
    }

    @Log(title = "维护工单管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysMaintenanceOrder order)
    {
        List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
        ExcelUtil<SysMaintenanceOrder> util = new ExcelUtil<SysMaintenanceOrder>(SysMaintenanceOrder.class);
        util.exportExcel(response, list, "维护工单数据");
    }

    /**
     * 根据工单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable String issueId)
    {
        return success(maintenanceOrderService.selectMaintenanceOrderById(issueId));
    }

    /**
     * 新增维护工单
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:add')")
    @Log(title = "维护工单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMaintenanceOrder order)
    {
        return toAjax(maintenanceOrderService.insertMaintenanceOrder(order));
    }

    /**
     * 修改维护工单
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:edit')")
    @Log(title = "维护工单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMaintenanceOrder order)
    {
        return toAjax(maintenanceOrderService.updateMaintenanceOrder(order));
    }

    /**
     * 删除维护工单
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:remove')")
    @Log(title = "维护工单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable String[] issueIds)
    {
        return toAjax(maintenanceOrderService.deleteMaintenanceOrderByIds(issueIds));
    }

    /**
     * 分配维护工单
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:assign')")
    @Log(title = "维护工单管理", businessType = BusinessType.UPDATE)
    @PatchMapping("/assign")
    public AjaxResult assign(@Validated @RequestBody SysMaintenanceOrder order) {
        SysMaintenanceOrder existingOrder = maintenanceOrderService.selectMaintenanceOrderById(order.getIssueId());
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
        return toAjax(maintenanceOrderService.updateMaintenanceOrder(existingOrder));
    }

    /**
     * 关闭维护工单
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:close')")
    @Log(title = "维护工单管理", businessType = BusinessType.UPDATE)
    @PatchMapping("/close")
    public AjaxResult close(@Validated @RequestBody SysMaintenanceOrder order) {
        SysMaintenanceOrder existingOrder = maintenanceOrderService.selectMaintenanceOrderById(order.getIssueId());
        if (existingOrder == null) {
            return error("工单不存在");
        }
        existingOrder.setProcessingStatus("Can not Resolve");
        existingOrder.setResultMessage("管理员手动关闭工单");
        return toAjax(maintenanceOrderService.updateMaintenanceOrder(existingOrder));
    }

    /**
     * 上传图片到 Azure Blob
     */
    @PreAuthorize("@ss.hasPermi('maintenanceOrder:all:upload')")
    @Log(title = "维护工单管理", businessType = BusinessType.UPDATE)
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
}
