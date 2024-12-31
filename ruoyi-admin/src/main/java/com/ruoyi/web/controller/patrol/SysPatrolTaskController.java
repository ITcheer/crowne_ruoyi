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
import com.ruoyi.system.domain.SysPatrolTask;
import com.ruoyi.system.service.ISysPatrolTaskService;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;

/**
 * 秩序巡逻任务 控制器
 * 
 */
@RestController
@RequestMapping("/patrol/task")
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SysPatrolTaskController extends BaseController
{
    @Autowired
    private ISysPatrolTaskService patrolTaskService;

    /**
     * 获取巡逻任务列表
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPatrolTask task)
    {
        startPage();
        List<SysPatrolTask> list = patrolTaskService.selectPatrolTaskList(task);
        return getDataTable(list);
    }

    @Log(title = "巡逻任务管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('patrol:task:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPatrolTask task)
    {
        List<SysPatrolTask> list = patrolTaskService.selectPatrolTaskList(task);
        ExcelUtil<SysPatrolTask> util = new ExcelUtil<SysPatrolTask>(SysPatrolTask.class);
        util.exportExcel(response, list, "巡逻任务数据");
    }

    /**
     * 根据任务编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable String taskId)
    {
        return success(patrolTaskService.selectPatrolTaskById(taskId));
    }

    /**
     * 新增巡逻任务
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:add')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPatrolTask task)
    {
        return toAjax(patrolTaskService.insertPatrolTask(task));
    }

    /**
     * 修改巡逻任务
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:edit')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPatrolTask task)
    {
        return toAjax(patrolTaskService.updatePatrolTask(task));
    }

    /**
     * 删除巡逻任务
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:remove')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable String[] taskIds)
    {
        return toAjax(patrolTaskService.deletePatrolTaskByIds(taskIds));
    }

    /**
     * 上传图片到 Azure Blob
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:upload')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadImage")
    public AjaxResult uploadImage(@RequestPart("file") MultipartFile file) {
        if (file == null) {
            return AjaxResult.error("上传文件不能为空");
        }
        try {
            String fileName = AzureBlobUploadUtils.upload(file, "image/patrolTask");
            String relativePath = fileName.substring("image/".length());
            return AjaxResult.success("上传成功", relativePath);
        } catch (IOException e) {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }
}
