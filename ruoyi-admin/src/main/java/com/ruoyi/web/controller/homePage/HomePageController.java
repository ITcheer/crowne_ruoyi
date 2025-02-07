package com.ruoyi.web.controller.homePage;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysMaintenanceOrder;
import com.ruoyi.system.domain.SysCleaningOrder;
import com.ruoyi.system.domain.SysFacilityOrder;
import com.ruoyi.system.domain.SysComplaintOrder;
import com.ruoyi.system.service.ISysMaintenanceOrderService;
import com.ruoyi.system.service.ISysCleaningOrderService;
import com.ruoyi.system.service.ISysFacilityOrderService;
import com.ruoyi.system.service.ISysComplaintOrderService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.system.domain.AllOrders;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/homePage")
public class HomePageController extends BaseController {

    @Autowired
    private ISysMaintenanceOrderService maintenanceOrderService;

    @Autowired
    private ISysCleaningOrderService cleaningOrderService;

    @Autowired
    private ISysFacilityOrderService facilityOrderService;

    @Autowired
    private ISysComplaintOrderService complaintOrderService;

    /**
     * 获取我的维修工单列表
     */
    @PreAuthorize("@ss.hasPermi('homePage:myOrder:list')")
    @GetMapping("/myMaintenanceOrder/list")
    public TableDataInfo listMyMaintenanceOrder(SysMaintenanceOrder order) {
        startPage();
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        Long userId = currentUser.getUserId();
        String roleName = currentUser.getRoles().get(0).getRoleName();

        if ("超级管理员".equals(roleName)||"GUIS管理员".equals(roleName)) {
            List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
            return getDataTable(list);
        } else if ("工程主管".equals(roleName)) {
            order.setFacilityGuyId(null);
            List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
            list.removeIf(o -> !userId.toString().equals(o.getFacilityGuyId()) && !"Undistributed".equals(o.getProcessingStatus()));
            return getDataTable(list);
        } else {
            order.setFacilityGuyId(userId.toString());
            List<SysMaintenanceOrder> list = maintenanceOrderService.selectMaintenanceOrderList(order);
            return getDataTable(list);
        }
    }

    /**
     * 获取我的清洁工单列表
     */
    @PreAuthorize("@ss.hasPermi('homePage:myOrder:list')")
    @GetMapping("/myCleaningOrder/list")
    public TableDataInfo listMyCleaningOrder(SysCleaningOrder order) {
        startPage();
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        Long userId = currentUser.getUserId();
        String roleName = currentUser.getRoles().get(0).getRoleName();

        if ("超级管理员".equals(roleName)||"GUIS管理员".equals(roleName)) {
            List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(order);
            return getDataTable(list);
        } else if ("工程主管".equals(roleName)) {
            order.setFacilityGuyId(null);
            List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(order);
            list.removeIf(o -> !userId.toString().equals(o.getFacilityGuyId()) && !"Undistributed".equals(o.getProcessingStatus()));
            return getDataTable(list);
        } else {
            order.setFacilityGuyId(userId.toString());
            List<SysCleaningOrder> list = cleaningOrderService.selectCleaningOrderList(order);
            return getDataTable(list);
        }
    }

    /**
     * 获取我的校工工单列表
     */
    @PreAuthorize("@ss.hasPermi('homePage:myOrder:list')")
    @GetMapping("/myFacilityOrder/list")
    public TableDataInfo listMyFacilityOrder(SysFacilityOrder order) {
        startPage();
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        Long userId = currentUser.getUserId();
        String roleName = currentUser.getRoles().get(0).getRoleName();

        if ("超级管理员".equals(roleName)||"GUIS管理员".equals(roleName)) {
            List<SysFacilityOrder> list = facilityOrderService.selectFacilityOrderList(order);
            return getDataTable(list);
        } else if ("工程主管".equals(roleName)) {
            order.setFacilityGuyId(null);
            List<SysFacilityOrder> list = facilityOrderService.selectFacilityOrderList(order);
            list.removeIf(o -> !userId.toString().equals(o.getFacilityGuyId()) && !"Undistributed".equals(o.getProcessingStatus()));
            return getDataTable(list);
        } else {
            order.setFacilityGuyId(userId.toString());
            List<SysFacilityOrder> list = facilityOrderService.selectFacilityOrderList(order);
            return getDataTable(list);
        }
    }

    /**
     * 获取我的投诉工单列表
     */
    @PreAuthorize("@ss.hasPermi('homePage:myOrder:list')")
    @GetMapping("/myComplaintOrder/list")
    public TableDataInfo listMyComplaintOrder(SysComplaintOrder order) {
        startPage();
        List<SysComplaintOrder> list = complaintOrderService.selectComplaintOrderList(order);
        return getDataTable(list);
    }

    /**
     * 获取全部工单列表
     */
    @PreAuthorize("@ss.hasPermi('homePage:allOrders:list')")
    @GetMapping("/allOrders/list")
    public TableDataInfo listAllOrders() {
        startPage();
        List<SysMaintenanceOrder> maintenanceOrders = maintenanceOrderService.selectMaintenanceOrderList(new SysMaintenanceOrder());
        List<SysCleaningOrder> cleaningOrders = cleaningOrderService.selectCleaningOrderList(new SysCleaningOrder());
        List<SysFacilityOrder> facilityOrders = facilityOrderService.selectFacilityOrderList(new SysFacilityOrder());
        List<SysComplaintOrder> complaintOrders = complaintOrderService.selectComplaintOrderList(new SysComplaintOrder());

        List<AllOrders> allOrders = new ArrayList<>();
        maintenanceOrders.forEach(order -> allOrders.add(new AllOrders(order.getIssueId(), "维修", order.getProcessingStatus(), order.getDate(), order)));
        cleaningOrders.forEach(order -> allOrders.add(new AllOrders(order.getIssueId(), "清洁", order.getProcessingStatus(), order.getDate(), order)));
        facilityOrders.forEach(order -> allOrders.add(new AllOrders(order.getIssueId(), "校工", order.getProcessingStatus(), order.getDate(), order)));
        complaintOrders.forEach(order -> allOrders.add(new AllOrders(order.getIssueId(), "投诉", order.getProcessingStatus(), order.getDate(), order)));

        return getDataTable(allOrders);
    }
}
