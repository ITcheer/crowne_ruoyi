package com.ruoyi.web.controller.storeroom.fixedAssets;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysFixedAssets;
import com.ruoyi.system.service.ISysFixedAssetsService;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;
import com.ruoyi.system.domain.SysInventoryRecords;
import com.ruoyi.system.service.ISysInventoryRecordsService;
import com.ruoyi.common.utils.SecurityUtils;

@RestController
@RequestMapping("/storeroom/fixedAssets")
public class SysFixedAssetsController extends BaseController {
    @Autowired
    private ISysFixedAssetsService sysFixedAssetsService;

    @Autowired
    private ISysInventoryRecordsService inventoryRecordsService;

    /**
     * 查询固定资产列表
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFixedAssets fixedAssets) {
        startPage();
        List<SysFixedAssets> list = sysFixedAssetsService.selectFixedAssetsList(fixedAssets);
        return getDataTable(list);
    }

    /**
     * 导出固定资产列表
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:export')")
    @Log(title = "固定资产", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFixedAssets fixedAssets) {
        List<SysFixedAssets> list = sysFixedAssetsService.selectFixedAssetsList(fixedAssets);
        ExcelUtil<SysFixedAssets> util = new ExcelUtil<SysFixedAssets>(SysFixedAssets.class);
        util.exportExcel(response, list, "固定资产数据");
    }

    /**
     * 获取固定资产详细信息
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:query')")
    @GetMapping(value = "/{batchId}")
    public AjaxResult getInfo(@PathVariable("batchId") String batchId) {
        return AjaxResult.success(sysFixedAssetsService.selectFixedAssetsById(batchId));
    }

    /**
     * 新增固定资产
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:add')")
    @Log(title = "固定资产", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysFixedAssets fixedAssets) {
        int result = sysFixedAssetsService.insertFixedAssets(fixedAssets);
        if (result > 0) {
            SysInventoryRecords record = new SysInventoryRecords();
            record.setId(UUID.randomUUID().toString());
            record.setGoodsId(fixedAssets.getBatchId());
            record.setQuantity(fixedAssets.getQuantity());
            record.setRecordType("入库");
            record.setOperator(SecurityUtils.getUsername());
            record.setOperationTime(new Date());
            record.setDetails("新增固定资产入库");
            inventoryRecordsService.insertInventoryRecords(record);
        }
        return toAjax(result);
    }

    /**
     * 修改固定资产
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:edit')")
    @Log(title = "固定资产", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysFixedAssets fixedAssets) {
        return toAjax(sysFixedAssetsService.updateFixedAssets(fixedAssets));
    }

    /**
     * 删除固定资产
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:remove')")
    @Log(title = "固定资产", businessType = BusinessType.DELETE)
    @DeleteMapping("/{batchIds}")
    public AjaxResult remove(@PathVariable String[] batchIds) {
        return toAjax(sysFixedAssetsService.deleteFixedAssetsByIds(batchIds));
    }

    /**
     * 上传固定资产图片到 Azure Blob
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:upload')")
    @Log(title = "固定资产", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadImage")
    public AjaxResult uploadImage(@RequestPart("file") MultipartFile file) {
        if (file == null) {
            return AjaxResult.error("上传文件不能为空");
        }
        try {
            String fileName = AzureBlobUploadUtils.upload(file, "image/fixedAssets");
            String relativePath = fileName.substring("image/".length());
            return AjaxResult.success("上传成功", relativePath);
        } catch (IOException e) {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 查询当前固定资产的出入库信息
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:query')")
    @GetMapping(value = "/inventoryRecords/{batchId}")
    public AjaxResult getInventoryRecords(@PathVariable("batchId") String batchId) {
        return success(inventoryRecordsService.selectInventoryRecordsListByBatchId(batchId));
    }

    /**
     * 出入库操作
     */
    @PreAuthorize("@ss.hasPermi('storeroom:fixedAssets:inventory')")
    @Log(title = "固定资产", businessType = BusinessType.UPDATE)
    @PostMapping("/inventory")
    public AjaxResult inventory(@Validated @RequestBody SysInventoryRecords record) {
        SysFixedAssets fixedAssets = sysFixedAssetsService.selectFixedAssetsById(record.getGoodsId());
        if (fixedAssets == null) {
            return AjaxResult.error("固定资产不存在");
        }

        if ("出库".equals(record.getRecordType()) && fixedAssets.getQuantity() < record.getQuantity()) {
            return AjaxResult.error("出库数量不能超过存量");
        }

        if ("出库".equals(record.getRecordType())) {
            fixedAssets.setQuantity(fixedAssets.getQuantity() - record.getQuantity());
        } else if ("入库".equals(record.getRecordType())) {
            fixedAssets.setQuantity(fixedAssets.getQuantity() + record.getQuantity());
        }

        sysFixedAssetsService.updateFixedAssets(fixedAssets);

        record.setId(UUID.randomUUID().toString());
        record.setOperator(SecurityUtils.getUsername());
        record.setOperationTime(new Date());
        inventoryRecordsService.insertInventoryRecords(record);

        return AjaxResult.success();
    }
}
