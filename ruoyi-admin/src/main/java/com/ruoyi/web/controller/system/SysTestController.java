package com.ruoyi.web.controller.system;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysTest;
import com.ruoyi.system.service.ISysTestService;

/**
 * 测试控制器
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/test")
public class SysTestController extends BaseController
{
    @Autowired
    private ISysTestService testService;

    /**
     * 获取测试列表
     */
    @PreAuthorize("@ss.hasPermi('system:test:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTest test)
    {
        startPage();
        List<SysTest> list = testService.selectTestList(test);
        return getDataTable(list);
    }

    @Log(title = "测试管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:test:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTest test)
    {
        List<SysTest> list = testService.selectTestList(test);
        ExcelUtil<SysTest> util = new ExcelUtil<SysTest>(SysTest.class);
        util.exportExcel(response, list, "测试数据");
    }

    /**
     * 根据测试编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:test:query')")
    @GetMapping(value = "/{testId}")
    public AjaxResult getInfo(@PathVariable Long testId)
    {
        return success(testService.selectTestById(testId));
    }

    /**
     * 新增测试
     */
    @PreAuthorize("@ss.hasPermi('system:test:add')")
    @Log(title = "测试管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysTest test)
    {
        if (!testService.checkTestKeyUnique(test))
        {
            return error("新增测试'" + test.getTestName() + "'失败，测试键名已存在");
        }
        test.setCreateBy(getUsername());
        return toAjax(testService.insertTest(test));
    }

    /**
     * 修改测试
     */
    @PreAuthorize("@ss.hasPermi('system:test:edit')")
    @Log(title = "测试管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysTest test)
    {
        if (!testService.checkTestKeyUnique(test))
        {
            return error("修改测试'" + test.getTestName() + "'失败，测试键名已存在");
        }
        test.setUpdateBy(getUsername());
        return toAjax(testService.updateTest(test));
    }

    /**
     * 删除测试
     */
    @PreAuthorize("@ss.hasPermi('system:test:remove')")
    @Log(title = "测试管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{testIds}")
    public AjaxResult remove(@PathVariable Long[] testIds)
    {
        testService.deleteTestByIds(testIds);
        return success();
    }

    /**
     * 刷新测试缓存
     */
    @PreAuthorize("@ss.hasPermi('system:test:remove')")
    @Log(title = "测试管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        testService.resetTestCache();
        return success();
    }

    /**
     * 获取所有测试数据
     */
    @PreAuthorize("@ss.hasPermi('system:test:list')")
    @GetMapping("/all")
    public AjaxResult getAllTests()
    {
        List<SysTest> list = testService.selectTestList(new SysTest());
        return success(list);
    }
}
