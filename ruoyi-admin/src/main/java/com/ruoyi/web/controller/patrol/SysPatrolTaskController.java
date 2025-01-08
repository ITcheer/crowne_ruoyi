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
import com.ruoyi.system.domain.SysPatrolTaskLocation;
import com.ruoyi.system.domain.SysPatrolTaskParticipant;
import com.ruoyi.system.domain.SysPatrolLocation;
import com.ruoyi.system.service.ISysPatrolTaskService;
import com.ruoyi.system.service.ISysPatrolTaskLocationService;
import com.ruoyi.system.service.ISysPatrolTaskParticipantService;
import com.ruoyi.common.utils.file.AzureBlobUploadUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.ISysPatrolLocationService;
import com.ruoyi.common.core.domain.entity.SysUser;

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

    @Autowired
    private ISysPatrolTaskLocationService patrolTaskLocationService;

    @Autowired
    private ISysPatrolTaskParticipantService patrolTaskParticipantService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysPatrolLocationService locationService;

    /**
     * 获取巡逻任务列表
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPatrolTask task)
    {
        startPage();
        List<SysPatrolTask> list = patrolTaskService.selectPatrolTaskList(task);
        for (SysPatrolTask patrolTask : list) {
            SysPatrolTaskLocation locationQuery = new SysPatrolTaskLocation();
            locationQuery.setTaskId(patrolTask.getId());
            List<SysPatrolTaskLocation> locations = patrolTaskLocationService.selectPatrolTaskLocation(locationQuery);
            patrolTask.setLocations(locations);

            SysPatrolTaskParticipant participantQuery = new SysPatrolTaskParticipant();
            participantQuery.setTaskId(patrolTask.getId());
            List<SysPatrolTaskParticipant> participants = patrolTaskParticipantService.selectPatrolTaskParticipant(participantQuery);
            patrolTask.setParticipants(participants);

            // 查询地点详细信息
            for (SysPatrolTaskLocation location : locations) {
                SysPatrolLocation locationDetails = locationService.selectPatrolLocationById(location.getLocationId());
                if (locationDetails != null) {
                    location.setLocationDetails(locationDetails);
                }
            }

            // 查询参与人员详细信息
            for (SysPatrolTaskParticipant participant : participants) {
                SysUser userDetails = userService.selectUserById(Long.parseLong(participant.getParticipantId()));
                if (userDetails != null) {
                    participant.setUserDetails(userDetails);
                }
            }
        }
        return getDataTable(list);
    }

    @Log(title = "巡逻任务管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('patrol:task:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPatrolTask task)
    {
        List<SysPatrolTask> list = patrolTaskService.selectPatrolTaskList(task);
        for (SysPatrolTask patrolTask : list) {
            SysPatrolTaskLocation locationQuery = new SysPatrolTaskLocation();
            locationQuery.setTaskId(patrolTask.getId());
            List<SysPatrolTaskLocation> locations = patrolTaskLocationService.selectPatrolTaskLocation(locationQuery);
            patrolTask.setLocations(locations);

            SysPatrolTaskParticipant participantQuery = new SysPatrolTaskParticipant();
            participantQuery.setTaskId(patrolTask.getId());
            List<SysPatrolTaskParticipant> participants = patrolTaskParticipantService.selectPatrolTaskParticipant(participantQuery);
            patrolTask.setParticipants(participants);

            // 查询地点详细信息
            for (SysPatrolTaskLocation location : locations) {
                SysPatrolLocation locationDetails = locationService.selectPatrolLocationById(location.getLocationId());
                if (locationDetails != null) {
                    location.setLocationDetails(locationDetails);
                }
            }

            // 查询参与人员详细信息
            for (SysPatrolTaskParticipant participant : participants) {
                SysUser userDetails = userService.selectUserById(Long.parseLong(participant.getParticipantId()));
                if (userDetails != null) {
                    participant.setUserDetails(userDetails);
                }
            }
        }
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
        AjaxResult ajax = success(patrolTaskService.selectPatrolTaskById(taskId));
        
        SysPatrolTaskLocation locationQuery = new SysPatrolTaskLocation();
        locationQuery.setTaskId(taskId);
        List<SysPatrolTaskLocation> locations = patrolTaskLocationService.selectPatrolTaskLocation(locationQuery);
        ajax.put("locations", locations);

        SysPatrolTaskParticipant participantQuery = new SysPatrolTaskParticipant();
        participantQuery.setTaskId(taskId);
        List<SysPatrolTaskParticipant> participants = patrolTaskParticipantService.selectPatrolTaskParticipant(participantQuery);
        ajax.put("participants", participants);
        
        // 查询地点详细信息
        for (SysPatrolTaskLocation location : locations) {
            SysPatrolLocation locationDetails = locationService.selectPatrolLocationById(location.getLocationId());
            if (locationDetails != null) {
                location.setLocationDetails(locationDetails);
            }
        }

        // 查询参与人员详细信息并删除未知用户数据
        // 暂且 使用当点击修改按钮后就删除未知用户数据的方式
        participants.removeIf(participant -> {
            SysUser userDetails = userService.selectUserById(Long.parseLong(participant.getParticipantId()));
            if (userDetails != null) {
                participant.setUserDetails(userDetails);
                return false;
            } else {
                patrolTaskParticipantService.deletePatrolTaskParticipantByPatrolId(participant.getParticipantId());
                return true;
            }
        });

        return ajax;
    }

    /**
     * 新增巡逻任务
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:add')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPatrolTask task)
    {
        int result = patrolTaskService.insertPatrolTask(task);
        if (result > 0) {
            List<SysPatrolTaskLocation> locations = task.getLocations();
            if (locations != null && !locations.isEmpty()) {
                for (SysPatrolTaskLocation location : locations) {
                    location.setTaskId(task.getId());
                }
                patrolTaskLocationService.batchInsertPatrolTaskLocation(locations);
            }
            List<SysPatrolTaskParticipant> participants = task.getParticipants();
            if (participants != null && !participants.isEmpty()) {
                for (SysPatrolTaskParticipant participant : participants) {
                    participant.setTaskId(task.getId());
                }
                patrolTaskParticipantService.batchInsertPatrolTaskParticipant(participants);
            }
        }
        return toAjax(result);
    }

    /**
     * 修改巡逻任务
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:edit')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPatrolTask task)
    {
        int result = patrolTaskService.updatePatrolTask(task);
        if (result > 0) {
            patrolTaskLocationService.deletePatrolTaskLocationByTaskId(task.getId());
            List<SysPatrolTaskLocation> locations = task.getLocations();
            if (locations != null && !locations.isEmpty()) {
                for (SysPatrolTaskLocation location : locations) {
                    location.setTaskId(task.getId());
                }
                patrolTaskLocationService.batchInsertPatrolTaskLocation(locations);
            }
            patrolTaskParticipantService.deletePatrolTaskParticipantByTaskId(task.getId());
            List<SysPatrolTaskParticipant> participants = task.getParticipants();
            if (participants != null && !participants.isEmpty()) {
                for (SysPatrolTaskParticipant participant : participants) {
                    participant.setTaskId(task.getId());
                }
                patrolTaskParticipantService.batchInsertPatrolTaskParticipant(participants);
            }
        }
        return toAjax(result);
    }

    /**
     * 删除巡逻任务
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:remove')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable String[] taskIds)
    {
        for (String taskId : taskIds) {
            patrolTaskLocationService.deletePatrolTaskLocationByTaskId(taskId);
            patrolTaskParticipantService.deletePatrolTaskParticipantByTaskId(taskId);
        }
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

    /**
     * 获取所有地点
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:query')")
    @GetMapping("/location/list")
    public AjaxResult listLocations() {
        List<SysPatrolLocation> locations = locationService.selectPatrolLocationList(new SysPatrolLocation());
        return AjaxResult.success(locations);
    }

    /**
     * 获取所有人员
     */
    @PreAuthorize("@ss.hasPermi('patrol:task:query')")
    @GetMapping("/user/list")
    public AjaxResult listParticipants() {
        List<SysUser> users = userService.selectUserList(new SysUser());
        return AjaxResult.success(users);
    }
}
