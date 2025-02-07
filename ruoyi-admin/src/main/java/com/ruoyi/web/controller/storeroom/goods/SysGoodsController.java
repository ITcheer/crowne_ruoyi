package com.ruoyi.web.controller.storeroom.goods;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
import com.ruoyi.system.domain.SysGoods;
import com.ruoyi.system.domain.SysInventoryRecords;
import com.ruoyi.system.service.ISysGoodsService;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;
import com.ruoyi.system.service.ISysInventoryRecordsService;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 普通货品 控制器
 */
@RestController
@RequestMapping("/storeroom/goods")
public class SysGoodsController extends BaseController {
    @Autowired
    private ISysGoodsService goodsService;

    @Autowired
    private ISysInventoryRecordsService inventoryRecordsService;

    /**
     * 获取普通货品列表
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysGoods goods) {
        startPage();
        List<SysGoods> list = goodsService.selectGoodsList(goods);
        return getDataTable(list);
    }

    /**
     * 导出普通货品列表
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:export')")
    @Log(title = "普通货品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGoods goods) {
        List<SysGoods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<SysGoods> util = new ExcelUtil<SysGoods>(SysGoods.class);
        util.exportExcel(response, list, "普通货品数据");
    }

    /**
     * 根据批次ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:query')")
    @GetMapping(value = "/{batchId}")
    public AjaxResult getInfo(@PathVariable("batchId") String batchId) {
        return success(goodsService.selectGoodsById(batchId));
    }

    /**
     * 新增普通货品
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:add')")
    @Log(title = "普通货品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysGoods goods) {
        int result = goodsService.insertGoods(goods);
        if (result > 0) {
            SysInventoryRecords record = new SysInventoryRecords();
            record.setId(UUID.randomUUID().toString());
            record.setGoodsId(goods.getBatchId());
            record.setQuantity(goods.getQuantity());
            record.setRecordType("入库");
            record.setOperator(SecurityUtils.getUsername());
            record.setOperationTime(new Date());
            record.setDetails("新增货品入库");
            inventoryRecordsService.insertInventoryRecords(record);
        }
        return toAjax(result);
    }

    /**
     * 修改普通货品
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:edit')")
    @Log(title = "普通货品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysGoods goods) {
        return toAjax(goodsService.updateGoods(goods));
    }

    /**
     * 删除普通货品
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:remove')")
    @Log(title = "普通货品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{batchIds}")
    public AjaxResult remove(@PathVariable String[] batchIds) {
        return toAjax(goodsService.deleteGoodsByIds(batchIds));
    }

    /**
     * 上传图片到 Azure Blob
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:upload')")
    @Log(title = "普通货品", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadImage")
    public AjaxResult uploadImage(@RequestPart("file") MultipartFile file) {
        if (file == null) {
            return AjaxResult.error("上传文件不能为空");
        }
        try {
            String fileName = AzureBlobUploadUtils.upload(file, "image/goods");
            String relativePath = fileName.substring("image/".length());
            return AjaxResult.success("上传成功", relativePath);
        } catch (IOException e) {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 查询当前货品的出入库信息
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:query')")
    @GetMapping(value = "/inventoryRecords/{batchId}")
    public AjaxResult getInventoryRecords(@PathVariable("batchId") String batchId) {
        return success(inventoryRecordsService.selectInventoryRecordsListByBatchId(batchId));
    }

    /**
     * 出入库操作
     */
    @PreAuthorize("@ss.hasPermi('storeroom:goods:inventory')")
    @Log(title = "普通货品", businessType = BusinessType.UPDATE)
    @PostMapping("/inventory")
    public AjaxResult inventory(@Validated @RequestBody SysInventoryRecords record) {
        System.out.println(record.getGoodsId());
        SysGoods goods = goodsService.selectGoodsById(record.getGoodsId());
        if (goods == null) {
            return AjaxResult.error("货品不存在");
        }

        if ("出库".equals(record.getRecordType()) && goods.getQuantity() < record.getQuantity()) {
            return AjaxResult.error("出库数量不能超过存量");
        }

        if ("出库".equals(record.getRecordType())) {
            goods.setQuantity(goods.getQuantity() - record.getQuantity());
        } else if ("入库".equals(record.getRecordType())) {
            goods.setQuantity(goods.getQuantity() + record.getQuantity());
        }

        goodsService.updateGoods(goods);

        record.setId(UUID.randomUUID().toString());
        record.setOperator(SecurityUtils.getUsername());
        record.setOperationTime(new Date());
        inventoryRecordsService.insertInventoryRecords(record);

        return AjaxResult.success();
    }
}
