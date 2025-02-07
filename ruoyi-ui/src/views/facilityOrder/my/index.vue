<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <!-- 搜索表单项 -->
        <el-form-item :label="$t('schoolOrder.issueDetails')" prop="issueDetails">
          <el-input
            v-model="queryParams.issueDetails"
            :placeholder="$t('schoolOrder.enterIssueDetails')"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.urgencyLevel')" prop="urgencyLevel">
          <el-select
            v-model="queryParams.urgencyLevel"
            :placeholder="$t('schoolOrder.urgencyLevel')"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="dict in dict.type.sys_urgency_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.processingStatus')" prop="processingStatus">
          <el-select
            v-model="queryParams.processingStatus"
            :placeholder="$t('schoolOrder.processingStatus')"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="dict in dict.type.sys_maintenance_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.appointmentTime')" prop="appointmentTime">
          <el-date-picker
            v-model="queryParams.appointmentTime"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            :placeholder="$t('schoolOrder.selectAppointmentTime')"
          ></el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.timeSlot')" prop="timeSlot">
          <el-select v-model="queryParams.timeSlot" :placeholder="$t('schoolOrder.selectTimeSlot')" clearable style="width: 240px">
            <el-option label="08:00-10:00" value="08:00-10:00"></el-option>
            <el-option label="10:00-12:00" value="10:00-12:00"></el-option>
            <el-option label="14:00-16:00" value="14:00-16:00"></el-option>
            <el-option label="16:00-18:00" value="16:00-18:00"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.date')">
          <template v-if="isMobile">
            <div style="display: flex; gap: 10px;">
              <el-date-picker
                v-model="startDate"
                style="flex: 1;"
                value-format="yyyyMMdd"
                type="date"
                :placeholder="$t('schoolOrder.startDate')"
                :popper-class="isMobile ? 'mobile-date-picker' : ''"
                :append-to-body="isMobile"
                @change="handleStartDateChange"
              ></el-date-picker>
              <el-date-picker
                v-model="endDate"
                style="flex: 1;"
                value-format="yyyyMMdd"
                type="date"
                :placeholder="$t('schoolOrder.endDate')"
                :popper-class="isMobile ? 'mobile-date-picker' : ''"
                :append-to-body="isMobile"
                @change="handleEndDateChange"
              ></el-date-picker>
            </div>
          </template>
          <el-date-picker
            v-else
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyyMMdd"
            type="daterange"
            range-separator="-"
            :start-placeholder="$t('schoolOrder.startDate')"
            :end-placeholder="$t('schoolOrder.endDate')"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('schoolOrder.search') }}</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('schoolOrder.reset') }}</el-button>
        </el-form-item>
      </el-form>
  
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
          >{{ $t('schoolOrder.export') }}</el-button>
        </el-col>
        <el-col v-if="!isMobile" :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-setting"
            size="mini"
            @click="showColumnSettings = true"
          >{{ $t('schoolOrder.columnSettings') }}</el-button>
        </el-col>
      </el-row>
  
      <el-row v-if="isMobile" :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" v-for="item in facilityOrderList" :key="item.issueId">
          <el-card class="box-card" shadow="hover">
            <div class="card-content">
              <p><strong>{{ $t('schoolOrder.issuerName') }}:</strong> {{ item.issuerName }}</p>
              <p><strong>{{ $t('schoolOrder.issueDetails') }}:</strong> {{ item.issueDetails }}</p>
              <p><strong>{{ $t('schoolOrder.date') }}:</strong> {{ parseTime(item.date) }}</p>
              <p>
                <strong>{{ $t('schoolOrder.classroom') }}:</strong> {{ item.classroom }} 
                <strong>{{ $t('schoolOrder.floor') }}:</strong> {{ item.floor }}
              </p>
              <p class="inline-fields">
                <strong>{{ $t('schoolOrder.urgencyLevel') }}:</strong> <dict-tag :options="dict.type.sys_urgency_level" :value="item.urgencyLevel"/>
                <strong>{{ $t('schoolOrder.processingStatus') }}:</strong> <dict-tag :options="dict.type.sys_maintenance_status" :value="item.processingStatus"/>
              </p>
              <div class="button-container">
                <el-button type="primary" size="mini" @click="handleSubmitOrUpdate(item)">{{ item.processingStatus === 'On Process' ? $t('schoolOrder.submit') : $t('schoolOrder.update') }}</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-table v-else v-loading="loading" :data="facilityOrderList" @selection-change="handleSelectionChange" @sort-change="handleSortChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column v-if="visibleColumns.includes('issueId')" :label="$t('schoolOrder.issueId')" prop="issueId" width="120" sortable />
        <el-table-column v-if="visibleColumns.includes('issuerName')" :label="$t('schoolOrder.issuerName')" prop="issuerName" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('issuerEmail')" :label="$t('schoolOrder.issuerEmail')" prop="issuerEmail" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('issueDetails')" :label="$t('schoolOrder.issueDetails')" prop="issueDetails" :show-overflow-tooltip="true" width="200" sortable />
        <el-table-column v-if="visibleColumns.includes('classroom')" :label="$t('schoolOrder.classroom')" prop="classroom" :show-overflow-tooltip="true" width="100" sortable />
        <el-table-column v-if="visibleColumns.includes('floor')" :label="$t('schoolOrder.floor')" prop="floor" :show-overflow-tooltip="true" width="80" sortable />
        <el-table-column v-if="visibleColumns.includes('urgencyLevel')" :label="$t('schoolOrder.urgencyLevel')" align="center" prop="urgencyLevel" sortable>
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_urgency_level" :value="scope.row.urgencyLevel"/>
          </template>
        </el-table-column>
        <el-table-column v-if="visibleColumns.includes('issuerPhone')" :label="$t('schoolOrder.issuerPhone')" prop="issuerPhone" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('facilityGuyId')" :label="$t('schoolOrder.facilityGuyId')" prop="facilityGuyId" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('date')" :label="$t('schoolOrder.date')" align="center" prop="date" width="180" sortable>
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.date) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="visibleColumns.includes('facilityGuysEmail')" :label="$t('schoolOrder.facilityGuysEmail')" prop="facilityGuysEmail" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('facilityGuysName')" :label="$t('schoolOrder.facilityGuysName')" prop="facilityGuysName" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('facilityGuyMobile')" :label="$t('schoolOrder.facilityGuyMobile')" prop="facilityGuyMobile" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('facilityGuySupervisor')" :label="$t('schoolOrder.facilityGuySupervisor')" prop="facilityGuySupervisor" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('resultMessage')" :label="$t('schoolOrder.resultMessage')" prop="resultMessage" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column v-if="visibleColumns.includes('processingStatus')" :label="$t('schoolOrder.processingStatus')" align="center" prop="processingStatus" sortable>
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_maintenance_status" :value="scope.row.processingStatus"/>
          </template>
        </el-table-column>
        <el-table-column v-if="visibleColumns.includes('appointmentTime')" :label="$t('schoolOrder.appointmentTime')" align="center" prop="appointmentTime" width="180" sortable>
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.appointmentTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="visibleColumns.includes('timeSlot')" :label="$t('schoolOrder.timeSlot')" prop="timeSlot" :show-overflow-tooltip="true" width="150" sortable />
        <el-table-column :label="$t('schoolOrder.actions')" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-dropdown>
              <el-button type="text" size="mini">
                <i class="el-icon-more"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleSubmit(scope.row)" v-if="scope.row.processingStatus === 'On Process'">
                  <i class="el-icon-upload"></i> {{ $t('schoolOrder.submit') }}
                </el-dropdown-item>
                <el-dropdown-item @click.native="handleUpdate(scope.row)" v-else>
                  <i class="el-icon-edit"></i> {{ $t('schoolOrder.update') }}
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
  
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
  
      <!-- 提交或更新校工工单对话框 -->
      <el-dialog :title="submitOrUpdateTitle" :visible.sync="submitOrUpdateOpen" :width="isMobile ? '100%' : '500px'" append-to-body>
        <el-form ref="submitOrUpdateForm" :model="submitOrUpdateForm" label-width="100px">
          <el-form-item :label="$t('schoolOrder.facilityGuysName')" prop="facilityGuysName" v-if="submitOrUpdateTitle === '更新校工工单'">
            <el-input v-model="submitOrUpdateForm.facilityGuysName" :placeholder="$t('schoolOrder.enterFacilityGuysName')" disabled />
          </el-form-item>
          <el-form-item :label="$t('schoolOrder.facilityGuyMobile')" prop="facilityGuyMobile" v-if="submitOrUpdateTitle === '更新校工工单'">
            <el-input v-model="submitOrUpdateForm.facilityGuyMobile" :placeholder="$t('schoolOrder.enterFacilityGuyMobile')" disabled />
          </el-form-item>
          <el-form-item :label="$t('schoolOrder.facilityGuysEmail')" prop="facilityGuysEmail" v-if="submitOrUpdateTitle === '更新校工工单'">
            <el-input v-model="submitOrUpdateForm.facilityGuysEmail" :placeholder="$t('schoolOrder.enterFacilityGuysEmail')" disabled />
          </el-form-item>
          <el-form-item :label="$t('schoolOrder.resultMessage')" prop="resultMessage">
            <el-input v-model="submitOrUpdateForm.resultMessage" :placeholder="$t('schoolOrder.enterResultMessage')" />
          </el-form-item>
          <el-form-item :label="$t('schoolOrder.processingStatus')" prop="processingStatus">
            <el-select v-model="submitOrUpdateForm.processingStatus" :placeholder="$t('schoolOrder.selectProcessingStatus')">
              <el-option
                v-for="dict in dict.type.sys_maintenance_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
                v-if="submitOrUpdateTitle === '提交校工工单' ? (dict.value === 'Resolved' || dict.value === 'Can not Resolve') : true"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" :disabled="!isFormChanged || !submitOrUpdateForm.processingStatus || (submitOrUpdateTitle === '提交校工工单' && submitOrUpdateForm.processingStatus === 'On Process')" @click="submitOrUpdateFormAction">{{ $t('schoolOrder.confirm') }}</el-button>
          <el-button @click="cancelSubmitOrUpdate">{{ $t('schoolOrder.cancel') }}</el-button>
        </div>
      </el-dialog>
  
      <!-- 字段设置对话框 -->
      <el-dialog :title="$t('schoolOrder.columnSettings')" :visible.sync="showColumnSettings" :width="isMobile ? '100%' : '300px'">
        <el-checkbox-group v-model="visibleColumns">
          <el-row>
            <el-col :span="12"><el-checkbox label="issueId">{{ $t('schoolOrder.issueId') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="issuerName">{{ $t('schoolOrder.issuerName') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="issuerEmail">{{ $t('schoolOrder.issuerEmail') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="issueDetails">{{ $t('schoolOrder.issueDetails') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="classroom">{{ $t('schoolOrder.classroom') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="floor">{{ $t('schoolOrder.floor') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="urgencyLevel">{{ $t('schoolOrder.urgencyLevel') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="issuerPhone">{{ $t('schoolOrder.issuerPhone') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="facilityGuyId">{{ $t('schoolOrder.facilityGuyId') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="date">{{ $t('schoolOrder.date') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="facilityGuysEmail">{{ $t('schoolOrder.facilityGuysEmail') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="facilityGuysName">{{ $t('schoolOrder.facilityGuysName') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="facilityGuyMobile">{{ $t('schoolOrder.facilityGuyMobile') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="facilityGuySupervisor">{{ $t('schoolOrder.facilityGuySupervisor') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="resultMessage">{{ $t('schoolOrder.resultMessage') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="processingStatus">{{ $t('schoolOrder.processingStatus') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="appointmentTime">{{ $t('schoolOrder.appointmentTime') }}</el-checkbox></el-col>
            <el-col :span="12"><el-checkbox label="timeSlot">{{ $t('schoolOrder.timeSlot') }}</el-checkbox></el-col>
          </el-row>
        </el-checkbox-group>
        <div slot="footer" class="dialog-footer">
          <el-button @click="showColumnSettings = false">{{ $t('schoolOrder.close') }}</el-button>
        </div>
      </el-dialog>
  
      <!-- 图片查看对话框 -->
      <el-dialog :title="$t('schoolOrder.imageView')" :visible.sync="showImageDialog" :width="isMobile ? '100%' : '600px'">
        <img :src="imageSrc" alt="图片" style="width: 100%;" />
        <div slot="footer" class="dialog-footer">
          <el-button @click="showImageDialog = false">{{ $t('schoolOrder.close') }}</el-button>
        </div>
      </el-dialog>
  
      <!-- 记录查看对话框 -->
      <log-dialog v-if="logDialogVisible" :visible.sync="logDialogVisible" :log-list="logList" :issue-id="currentIssueId" @send-message="sendMessage"></log-dialog>
    </div>
  </template>
  
  <script>
  import { 
    listFacilityOrder, 
    getFacilityOrder, 
    updateFacilityOrder, 
    uploadImage, 
    exportFacilityOrder, 
    listUserByDeptId, 
    getUserProfile, 
    sendCompletionEmail, 
    sendIncompleteEmail 
  } from "@/api/facilityOrder/my/facilityOrderMy";
  
  export default {
    name: "MyFacilityOrder",
    dicts: ['sys_urgency_level', 'sys_maintenance_status'],
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 校工订单表格数据
        facilityOrderList: [],
        // 弹出层标题
        submitOrUpdateTitle: "",
        // 是否显示提交或更新弹出层
        submitOrUpdateOpen: false,
        // 日期范围
        dateRange: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          issueDetails: undefined,
          urgencyLevel: undefined,
          processingStatus: undefined,
          sortField: 'date',
          sortOrder: 'descending',
          appointmentTime: undefined,
          timeSlot: undefined
        },
        // 表单参数
        submitOrUpdateForm: {},
        originalSubmitOrUpdateForm: {},
        // 表单校验
        rules: {
          issueDetails: [
            { required: true, message: this.$t('schoolOrder.enterIssueDetails'), trigger: "blur" }
          ],
          urgencyLevel: [
            { required: true, message: this.$t('schoolOrder.urgencyLevel'), trigger: "blur" }
          ],
          floor: [
            { required: true, message: this.$t('schoolOrder.enterFloor'), trigger: "blur" }
          ],
          classroom: [
            { required: true, message: this.$t('schoolOrder.enterClassroom'), trigger: "blur" }
          ]
        },
        fileList: [],
        previewVisible: false,
        previewImage: '',
        visibleColumns: ['issuerName', 'issueDetails', 'classroom', 'floor', 'urgencyLevel', 'date', 'resultMessage', 'processingStatus', 'appointmentTime', 'timeSlot'],
        sortParams: {
          prop: 'date',
          order: 'descending'
        },
        showColumnSettings: false,
        showImageDialog: false,
        imageSrc: '',
        deptUsers: [],
        startDate: null,
        endDate: null,
        logDialogVisible: false,
        logList: [],
        currentIssueId: null,
        newMessage: "",
        currentUser: {
          userId: "",
          userName: ""
        }
      };
    },
    computed: {
      isFormChanged() {
        return JSON.stringify(this.submitOrUpdateForm) !== JSON.stringify(this.originalSubmitOrUpdateForm);
      },
      isMobile() {
        return window.innerWidth <= 768;
      }
    },
    created() {
      const status = this.$route.query.status;
      if (status) {
        this.queryParams.processingStatus = status;
      }
      this.getList();
      this.getDeptUsers(); // 获取部门用户信息
      this.getCurrentUser(); // 获取当前用户信息
    },
    methods: {
      /** 查询校工工单列表 */
      getList() {
        this.loading = true;
        const params = {
          ...this.addDateRange(this.queryParams, this.dateRange),
          sortField: this.sortParams.prop,
          sortOrder: this.sortParams.order
        };
        listFacilityOrder(params).then(response => {
            this.facilityOrderList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      // 取消按钮
      cancel() {
        this.submitOrUpdateOpen = false;
        this.resetSubmitOrUpdateForm();
      },
      // 表单重置
      resetSubmitOrUpdateForm() {
        this.submitOrUpdateForm = {};
        this.originalSubmitOrUpdateForm = {};
        this.fileList = [];
        this.resetForm("submitOrUpdateForm");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = [];
        this.startDate = null;
        this.endDate = null;
        this.queryParams = {
          pageNum: 1,
          pageSize: 10,
          issueDetails: undefined,
          urgencyLevel: undefined,
          processingStatus: undefined,
          sortField: '',
          sortOrder: ''
        };
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.issueId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      /** 提交或更新按钮操作 */
      handleSubmitOrUpdate(row) {
        this.resetSubmitOrUpdateForm();
        this.submitOrUpdateForm = { ...row };
        this.originalSubmitOrUpdateForm = { ...row };
        this.submitOrUpdateTitle = row.processingStatus === 'On Process' ? this.$t('schoolOrder.submit') : this.$t('schoolOrder.update');
        this.submitOrUpdateOpen = true;
      },
      handleSubmit(row) {
        this.resetSubmitOrUpdateForm();
        this.submitOrUpdateForm = { ...row };
        this.originalSubmitOrUpdateForm = { ...row };
        this.submitOrUpdateTitle = this.$t('schoolOrder.submit');
        this.submitOrUpdateOpen = true;
      },
      handleUpdate(row) {
        this.resetSubmitOrUpdateForm();
        this.submitOrUpdateForm = { ...row };
        this.originalSubmitOrUpdateForm = { ...row };
        this.submitOrUpdateTitle = this.$t('schoolOrder.update');
        this.submitOrUpdateOpen = true;
      },
      /** 提交或更新表单操作 */
      submitOrUpdateFormAction() {
        if (this.fileList.some(file => file.status !== 'success')) {
          this.$message.error(this.$t('schoolOrder.uploadError'));
          return;
        }
        if (this.fileList.length === 0) {
          this.submitOrUpdateForm.resultImgPath = '';
        } 
        if (this.submitOrUpdateTitle === this.$t('schoolOrder.submit')) {
          this.updateFacilityOrderData(this.$t('schoolOrder.submit'));
        } else {
          this.updateFacilityOrderData(this.$t('schoolOrder.update'));
        }
      },
      updateFacilityOrderData(actionType) {
        this.$refs["submitOrUpdateForm"].validate(valid => {
          if (valid) {
            if (this.fileList.length === 0) {
              this.submitOrUpdateForm.resultImgPath = '';
            } 
            updateFacilityOrder(this.submitOrUpdateForm).then(response => {
              this.$modal.msgSuccess(this.$t('schoolOrder.actionSuccess'));
              this.submitOrUpdateOpen = false;
              this.getList();
              if (actionType === this.$t('schoolOrder.submit')) {
                const statusLabel = this.dict.type.sys_maintenance_status.find(item => item.value === this.submitOrUpdateForm.processingStatus)?.label || this.submitOrUpdateForm.processingStatus;
                if (this.submitOrUpdateForm.processingStatus === 'Resolved') {
                  sendCompletionEmail(this.submitOrUpdateForm.issueId);
                } else if (this.submitOrUpdateForm.processingStatus === 'Can not Resolve') {
                  sendIncompleteEmail(this.submitOrUpdateForm.issueId);
                }
              }
            });
          }
        });
      },
      addDetailedLog(issueId, actionType, originalData, updatedData) {
        const fieldNames = {
          issueDetails: this.$t('schoolOrder.issueDetails'),
          urgencyLevel: this.$t('schoolOrder.urgencyLevel'),
          floor: this.$t('schoolOrder.floor'),
          classroom: this.$t('schoolOrder.classroom'),
          resultMessage: this.$t('schoolOrder.resultMessage'),
          processingStatus: this.$t('schoolOrder.processingStatus'),
          facilityGuysName: this.$t('schoolOrder.facilityGuysName'),
          facilityGuyMobile: this.$t('schoolOrder.facilityGuyMobile'),
          facilityGuysEmail: this.$t('schoolOrder.facilityGuysEmail')
        };
        const changes = [];
        for (const key in updatedData) {
          if (updatedData[key] !== originalData[key]) {
            const fieldName = fieldNames[key] || key;
            let oldValue = originalData[key];
            let newValue = updatedData[key];
            if (key === 'processingStatus') {
              oldValue = this.dict.type.sys_maintenance_status.find(item => item.value === originalData[key])?.label || originalData[key];
              newValue = this.dict.type.sys_maintenance_status.find(item => item.value === updatedData[key])?.label || updatedData[key];
            }
            changes.push(`${fieldName} ${this.$t('schoolOrder.from')} "${oldValue}" ${this.$t('schoolOrder.to')} "${newValue}"`);
          }
        }
        const actionDescription = changes.length > 0 ? changes.join(", ") : this.$t('schoolOrder.noChange');
        const log = {
          issueId: issueId,
          actionType: actionType,
          actionDescription: actionDescription,
          userId: this.currentUser.userId,
          userName: this.currentUser.userName
        };
        addCleaningOrderLog(log).then(response => {
          if (response.code !== 200) {
            this.$message.error(this.$t('schoolOrder.logFail'));
          }
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('facilityOrder/my/export', {
          ...this.queryParams
        }, `facilityOrder_my_${new Date().getTime()}.xlsx`)
      },
      viewImage(src) {
        this.imageSrc = src;
        this.showImageDialog = true;
      },
      handleSortChange({ prop, order }) {
        this.sortParams.prop = prop;
        this.sortParams.order = order;
        this.queryParams.sortField = prop;
        this.queryParams.sortOrder = order;
        this.getList();
      },
      generateIssueId() {
        return Math.random().toString().slice(2, 15);
      },
      uploadImage({ file }) {
        const formData = new FormData();
        formData.append('file', file);
        uploadImage(formData).then(response => {
          const relativePath = response.data;
          const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${relativePath}`;
          this.submitOrUpdateForm.resultImgPath = relativePath;
          this.fileList.push({ name: file.name, url: fullUrl });
        }).catch(error => {
          this.$message.error(this.$t('schoolOrder.uploadFail'));
        });
      },
      handlePreview(file) {
        this.previewImage = file.url;
        this.previewVisible = true;
      },
      handleRemove(file) {
        this.fileList = this.fileList.filter(item => item.url !== file.url);
        this.fileList = this.fileList.filter(item => item.name !== file.name);
        if (this.submitOrUpdateForm.resultImgPath === file.name || this.submitOrUpdateForm.resultImgPath === file.url) {
          this.submitOrUpdateForm.resultImgPath = '';
        }
      },
      // 取消提交或更新
      cancelSubmitOrUpdate() {
        this.submitOrUpdateOpen = false;
        this.resetSubmitOrUpdateForm();
      },
      remindIssuer() {
        // 提醒发布工单人操作
      },
      handleStartDateChange(value) {
        this.startDate = value;
        this.dateRange = [this.startDate, this.endDate];
      },
      handleEndDateChange(value) {
        this.endDate = value;
        this.dateRange = [this.startDate, this.endDate];
      },
      handleUserChange(userName) {
        const selectedUser = this.deptUsers.find(user => user.userName === userName);
        if (selectedUser) {
          this.submitOrUpdateForm.facilityGuyId = selectedUser.userId;
          this.submitOrUpdateForm.facilityGuyMobile = selectedUser.phonenumber;
          this.submitOrUpdateForm.facilityGuysEmail = selectedUser.email;
        }
      },
      getDeptUsers() {
        // 假设部门ID为201
        listUserByDeptId(201).then(response => {
          this.deptUsers = response.data;
        });
      },
      sendMessage(message) {
        if (message.trim() !== "") {
          const log = {
            issueId: this.currentIssueId,
            actionType: this.$t('schoolOrder.communication'),
            actionDescription: message,
            userId: this.currentUser.userId,
            userName: this.currentUser.userName
          };
          addCleaningOrderLog(log).then(response => {
            if (response.code === 200) {
              this.refreshLogs();
              this.newMessage = "";
            } else {
              this.$message.error(this.$t('schoolOrder.sendMessageFail'));
            }
          });
        }
      },
      refreshLogs() {
        listCleaningOrderLogs(this.currentIssueId).then(response => {
          this.logList = response.data;
        });
      },
      getCurrentUser() {
        getUserProfile().then(response => {
          this.currentUser.userId = response.data.userId;
          this.currentUser.userName = response.data.userName;
        });
      },
      addLog(issueId, actionType, actionDescription) {
        const log = {
          issueId: issueId,
          actionType: actionType,
          actionDescription: actionDescription,
          userId: this.currentUser.userId,
          userName: this.currentUser.userName
        };
        addCleaningOrderLog(log).then(response => {
          if (response.code !== 200) {
            this.$message.error(this.$t('schoolOrder.logFail'));
          }
        });
      }
    }
  };
  </script>
  
  <style scoped>
  .card-content {
    font-size: 12px;
    padding: 10px;
    position: relative;
    min-height: 200px; /* 调整卡片高度 */
  }
  .box-card {
    margin-bottom: 10px;
  }
  .inline-fields {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
  }
  .inline-fields > * {
    flex: 1;
  }
  .button-container {
    position: absolute;
    top: 10px;
    right: 10px;
  }
  .spacer {
    height: 20px; /* 调整按钮与底部的距离 */
  }
  .mobile-date-picker .el-picker-panel {
    left: 0 !important;
    right: 0 !important;
    margin: auto !important;
    width: 100% !important;
    max-width: 300px !important;
  }
  </style>
