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
import com.ruoyi.system.domain.SysEngineeringPatrolTask;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskLocation;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskParticipant;
import com.ruoyi.system.service.ISysEngineeringPatrolTaskService;
import com.ruoyi.system.service.ISysEngineeringPatrolLocationService;
import com.ruoyi.system.service.ISysEngineeringPatrolTaskLocationService;
import com.ruoyi.system.service.ISysEngineeringPatrolTaskParticipantService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.ISysPatrolLocationService;
import com.ruoyi.system.domain.SysPatrolLocation;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysEngineeringPatrolTaskRecordService;
import com.ruoyi.system.domain.SysEngineeringPatrolTaskRecord;

/**
 * 工程巡逻任务 控制器
 */
@RestController
@RequestMapping("/engineering/patrolTask")
public class SysEngineeringPatrolTaskController extends BaseController
{
    @Autowired
    private ISysEngineeringPatrolTaskService patrolTaskService;

    @Autowired
    private ISysEngineeringPatrolTaskLocationService patrolTaskLocationService;

    @Autowired
    private ISysEngineeringPatrolLocationService patrolLocationService;

    @Autowired
    private ISysEngineeringPatrolTaskParticipantService patrolTaskParticipantService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysEngineeringPatrolTaskRecordService patrolTaskRecordService;

    /**
     * 获取巡逻任务列表
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEngineeringPatrolTask task)
    {
        startPage();
        List<SysEngineeringPatrolTask> list = patrolTaskService.selectPatrolTaskList(task);
        for (SysEngineeringPatrolTask patrolTask : list) {
            SysEngineeringPatrolTaskLocation locationQuery = new SysEngineeringPatrolTaskLocation();
            locationQuery.setTaskId(patrolTask.getId());
            List<SysEngineeringPatrolTaskLocation> locations = patrolTaskLocationService.selectPatrolTaskLocationList(locationQuery);
            patrolTask.setLocations(locations);

            SysEngineeringPatrolTaskParticipant participantQuery = new SysEngineeringPatrolTaskParticipant();
            participantQuery.setTaskId(patrolTask.getId());
            List<SysEngineeringPatrolTaskParticipant> participants = patrolTaskParticipantService.selectPatrolTaskParticipantList(participantQuery);
            patrolTask.setParticipants(participants);

            // 查询地点详细信息
            for (SysEngineeringPatrolTaskLocation location : locations) {
                SysEngineeringPatrolLocation locationDetails = patrolLocationService.selectPatrolLocationById(location.getLocationId());
                if (locationDetails != null) {
                    location.setLocationDetails(locationDetails);
                }
            }

            // 查询参与人员详细信息
            for (SysEngineeringPatrolTaskParticipant participant : participants) {
                SysUser userDetails = userService.selectUserById(Long.parseLong(participant.getParticipantId()));
                if (userDetails != null) {
                    participant.setUserDetails(userDetails);
                }
                
            }
        }
        return getDataTable(list);
    }

    @Log(title = "巡逻任务管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysEngineeringPatrolTask task)
    {
        List<SysEngineeringPatrolTask> list = patrolTaskService.selectPatrolTaskList(task);
        ExcelUtil<SysEngineeringPatrolTask> util = new ExcelUtil<SysEngineeringPatrolTask>(SysEngineeringPatrolTask.class);
        util.exportExcel(response, list, "巡逻任务数据");
    }

    /**
     * 根据任务编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable String taskId)
    {
        AjaxResult ajax = success(patrolTaskService.selectPatrolTaskById(taskId));
        
        SysEngineeringPatrolTaskLocation locationQuery = new SysEngineeringPatrolTaskLocation();
        locationQuery.setTaskId(taskId);
        List<SysEngineeringPatrolTaskLocation> locations = patrolTaskLocationService.selectPatrolTaskLocationList(locationQuery);
        ajax.put("locations", locations);

        SysEngineeringPatrolTaskParticipant participantQuery = new SysEngineeringPatrolTaskParticipant();
        participantQuery.setTaskId(taskId);
        List<SysEngineeringPatrolTaskParticipant> participants = patrolTaskParticipantService.selectPatrolTaskParticipantList(participantQuery);
        ajax.put("participants", participants);

        // 查询地点详细信息
        for (SysEngineeringPatrolTaskLocation location : locations) {
            SysEngineeringPatrolLocation locationDetails = patrolLocationService.selectPatrolLocationById(location.getLocationId());
            if (locationDetails != null) {
                location.setLocationDetails(locationDetails);
            }
        }

        // 查询参与人员详细信息并删除未知用户数据
        participants.removeIf(participant -> {
            SysUser userDetails = userService.selectUserById(Long.parseLong(participant.getParticipantId()));
            if (userDetails != null) {
                participant.setUserDetails(userDetails);
                return false;
            } else {
                patrolTaskParticipantService.deletePatrolTaskParticipantById(participant.getParticipantId());
                return true;
            }
        });

        return ajax;
    }

    /**
     * 新增巡逻任务
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:add')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysEngineeringPatrolTask task)
    {
        int result = patrolTaskService.insertPatrolTask(task);
        if (result > 0) {
            List<SysEngineeringPatrolTaskLocation> locations = task.getLocations();
            if (locations != null && !locations.isEmpty()) {
                for (SysEngineeringPatrolTaskLocation location : locations) {
                    location.setTaskId(task.getId());
                }
                patrolTaskLocationService.batchInsertPatrolTaskLocation(locations);
            }
            List<SysEngineeringPatrolTaskParticipant> participants = task.getParticipants();
            if (participants != null && !participants.isEmpty()) {
                for (SysEngineeringPatrolTaskParticipant participant : participants) {
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
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:edit')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysEngineeringPatrolTask task)
    {
        int result = patrolTaskService.updatePatrolTask(task);
        if (result > 0) {
            patrolTaskLocationService.deletePatrolTaskLocationById(task.getId());
            List<SysEngineeringPatrolTaskLocation> locations = task.getLocations();
            if (locations != null && !locations.isEmpty()) {
                for (SysEngineeringPatrolTaskLocation location : locations) {
                    location.setTaskId(task.getId());
                }
                patrolTaskLocationService.batchInsertPatrolTaskLocation(locations);
            }
            patrolTaskParticipantService.deletePatrolTaskParticipantById(task.getId());
            List<SysEngineeringPatrolTaskParticipant> participants = task.getParticipants();
            if (participants != null && !participants.isEmpty()) {
                for (SysEngineeringPatrolTaskParticipant participant : participants) {
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
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:remove')")
    @Log(title = "巡逻任务管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable String[] taskIds)
    {
        for (String taskId : taskIds) {
            patrolTaskLocationService.deletePatrolTaskLocationById(taskId);
            patrolTaskParticipantService.deletePatrolTaskParticipantById(taskId);
        }
        return toAjax(patrolTaskService.deletePatrolTaskByIds(taskIds));
    }

    /**
     * 获取所有地点
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:query')")
    @GetMapping("/location/list")
    public AjaxResult listLocations() {
        List<SysEngineeringPatrolLocation> locations = patrolLocationService.selectPatrolLocationList(new SysEngineeringPatrolLocation());
        return AjaxResult.success(locations);
    }

    /**
     * 获取所有人员
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:query')")
    @GetMapping("/user/list")
    public AjaxResult listParticipants() {
        List<SysUser> users = userService.selectUserList(new SysUser());
        return AjaxResult.success(users);
    }

    /**
     * 获取工程巡逻任务打卡记录
     */
    @PreAuthorize("@ss.hasPermi('engineering:patrolTask:query')")
    @GetMapping("/{taskId}/records")
    public AjaxResult listRecords(@PathVariable String taskId) {
        List<SysEngineeringPatrolTaskRecord> records = patrolTaskRecordService.selectPatrolTaskRecordListByTaskId(taskId);
        for (SysEngineeringPatrolTaskRecord record : records) {
            SysUser userDetails = userService.selectUserById(Long.parseLong(record.getParticipantId()));
            if (userDetails != null) {
                record.setParticipantName(userDetails.getUserName());
            }
            SysEngineeringPatrolLocation locationDetails = patrolLocationService.selectPatrolLocationById(record.getCheckInLocationId());
            if (locationDetails != null) {
                record.setCheckInLocationName(locationDetails.getLocationName());
            }
        }
        return AjaxResult.success(records);
    }
}
