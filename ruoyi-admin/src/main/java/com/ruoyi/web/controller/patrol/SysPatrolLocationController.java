package com.ruoyi.web.controller.patrol;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPatrolLocation;
import com.ruoyi.system.service.ISysPatrolLocationService;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;

/**
 * 秩序巡逻地点 控制器
 * 
 */
@RestController
@RequestMapping("/patrol/location")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SysPatrolLocationController extends BaseController
{
    @Autowired
    private ISysPatrolLocationService patrolLocationService;

    /**
     * 获取巡逻地点列表
     */
    @PreAuthorize("@ss.hasPermi('patrol:location:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPatrolLocation location)
    {
        startPage();
        List<SysPatrolLocation> list = patrolLocationService.selectPatrolLocationList(location);
        return getDataTable(list);
    }

    @Log(title = "巡逻地点管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('patrol:location:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPatrolLocation location)
    {
        List<SysPatrolLocation> list = patrolLocationService.selectPatrolLocationList(location);
        ExcelUtil<SysPatrolLocation> util = new ExcelUtil<SysPatrolLocation>(SysPatrolLocation.class);
        util.exportExcel(response, list, "巡逻地点数据");
    }

    /**
     * 根据地点编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('patrol:location:query')")
    @GetMapping(value = "/{locationId}")
    public AjaxResult getInfo(@PathVariable String locationId)
    {
        return success(patrolLocationService.selectPatrolLocationById(locationId));
    }

    /**
     * 新增巡逻地点
     */
    @PreAuthorize("@ss.hasPermi('patrol:location:add')")
    @Log(title = "巡逻地点管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPatrolLocation location)
    {
        return toAjax(patrolLocationService.insertPatrolLocation(location));
    }

    /**
     * 修改巡逻地点
     */
    @PreAuthorize("@ss.hasPermi('patrol:location:edit')")
    @Log(title = "巡逻地点管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPatrolLocation location)
    {
        return toAjax(patrolLocationService.updatePatrolLocation(location));
    }

    /**
     * 删除巡逻地点
     */
    @PreAuthorize("@ss.hasPermi('patrol:location:remove')")
    @Log(title = "巡逻地点管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{locationIds}")
    public AjaxResult remove(@PathVariable String[] locationIds)
    {
        return toAjax(patrolLocationService.deletePatrolLocationByIds(locationIds));
    }

    /**
     * 上传图片到 Azure Blob
     */
    @PreAuthorize("@ss.hasPermi('patrol:location:upload')")
    @Log(title = "巡逻地点管理", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadImage")
    public AjaxResult uploadImage(@RequestPart("file") MultipartFile file) {
        if (file == null) {
            return AjaxResult.error("上传文件不能为空");
        }
        try {
            String fileName = AzureBlobUploadUtils.upload(file, "image/patrolLocation");
            String relativePath = fileName.substring("image/".length());
            return AjaxResult.success("上传成功", relativePath);
        } catch (IOException e) {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }
}
