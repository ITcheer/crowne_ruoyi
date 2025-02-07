package com.ruoyi.web.controller.engineering;

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
import com.ruoyi.system.domain.SysEngineeringPatrolLocation;
import com.ruoyi.system.service.ISysEngineeringPatrolLocationService;

/**
 * 工程巡逻位置 控制器
 */
@RestController
@RequestMapping("/engineering/patrolLocation")
public class SysEngineeringPatrolLocationController extends BaseController
{
    @Autowired
    private ISysEngineeringPatrolLocationService patrolLocationService;

    /**
     * 获取巡逻位置列表
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolLocation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEngineeringPatrolLocation location)
    {
        startPage();
        List<SysEngineeringPatrolLocation> list = patrolLocationService.selectPatrolLocationList(location);
        return getDataTable(list);
    }

    @Log(title = "巡逻位置管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('engineering:patrolLocation:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysEngineeringPatrolLocation location)
    {
        List<SysEngineeringPatrolLocation> list = patrolLocationService.selectPatrolLocationList(location);
        ExcelUtil<SysEngineeringPatrolLocation> util = new ExcelUtil<SysEngineeringPatrolLocation>(SysEngineeringPatrolLocation.class);
        util.exportExcel(response, list, "巡逻位置数据");
    }

    /**
     * 根据位置编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolLocation:query')")
    @GetMapping(value = "/{locationId}")
    public AjaxResult getInfo(@PathVariable String locationId)
    {
        return success(patrolLocationService.selectPatrolLocationById(locationId));
    }

    /**
     * 新增巡逻位置
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolLocation:add')")
    @Log(title = "巡逻位置管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysEngineeringPatrolLocation location)
    {
        return toAjax(patrolLocationService.insertPatrolLocation(location));
    }

    /**
     * 修改巡逻位置
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolLocation:edit')")
    @Log(title = "巡逻位置管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysEngineeringPatrolLocation location)
    {
        return toAjax(patrolLocationService.updatePatrolLocation(location));
    }

    /**
     * 删除巡逻位置
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolLocation:remove')")
    @Log(title = "巡逻位置管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{locationIds}")
    public AjaxResult remove(@PathVariable String[] locationIds)
    {
        return toAjax(patrolLocationService.deletePatrolLocationByIds(locationIds));
    }
}
