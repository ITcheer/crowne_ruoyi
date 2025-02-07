<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <!-- 搜索表单项 -->
      <el-form-item :label="$t('maintenanceOrder.issueDetails')" prop="issueDetails">
        <el-input
          v-model="queryParams.issueDetails"
          :placeholder="$t('maintenanceOrder.enterIssueDetails')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('maintenanceOrder.urgencyLevel')" prop="urgencyLevel">
        <el-select
          v-model="queryParams.urgencyLevel"
          :placeholder="$t('maintenanceOrder.urgencyLevel')"
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
      <el-form-item :label="$t('maintenanceOrder.processingStatus')" prop="processingStatus">
        <el-select
          v-model="queryParams.processingStatus"
          :placeholder="$t('maintenanceOrder.processingStatus')"
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
      <el-form-item :label="$t('maintenanceOrder.date')">
        <template v-if="isMobile">
          <div style="display: flex; gap: 10px;">
            <el-date-picker
              v-model="startDate"
              style="flex: 1;"
              value-format="yyyyMMdd"
              type="date"
              :placeholder="$t('maintenanceOrder.startDate')"
              :popper-class="isMobile ? 'mobile-date-picker' : ''"
              :append-to-body="isMobile"
              @change="handleStartDateChange"
            ></el-date-picker>
            <el-date-picker
              v-model="endDate"
              style="flex: 1;"
              value-format="yyyyMMdd"
              type="date"
              :placeholder="$t('maintenanceOrder.endDate')"
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
          :start-placeholder="$t('maintenanceOrder.startDate')"
          :end-placeholder="$t('maintenanceOrder.endDate')"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('maintenanceOrder.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('maintenanceOrder.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- 移除修改按钮 -->
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >{{ $t('maintenanceOrder.export') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-setting"
          size="mini"
          @click="showColumnSettings = true"
        >{{ $t('maintenanceOrder.columnSettings') }}</el-button>
      </el-col>
    </el-row>

    <el-row v-if="isMobile" :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in maintenanceOrderList" :key="item.issueId">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <p><strong>{{ $t('maintenanceOrder.issuerName') }}:</strong> {{ item.issuerName }}</p>
            <p><strong>{{ $t('maintenanceOrder.issueDetails') }}:</strong> {{ item.issueDetails }}</p>
            <p><strong>{{ $t('maintenanceOrder.date') }}:</strong> {{ parseTime(item.date) }}</p>
            <p>
              <strong>{{ $t('maintenanceOrder.classroom') }}:</strong> {{ item.classroom }} 
              <strong>{{ $t('maintenanceOrder.floor') }}:</strong> {{ item.floor }}
            </p>
            <p class="inline-fields">
              <strong>{{ $t('maintenanceOrder.maintenanceType') }}:</strong> <dict-tag :options="dict.type.sys_maintenance_type" :value="item.maintenanceType"/>
              <strong>{{ $t('maintenanceOrder.urgencyLevel') }}:</strong> <dict-tag :options="dict.type.sys_urgency_level" :value="item.urgencyLevel"/>
              <strong>{{ $t('maintenanceOrder.processingStatus') }}:</strong> <dict-tag :options="dict.type.sys_maintenance_status" :value="item.processingStatus"/>
            </p>
            <p>
              <strong>{{ $t('maintenanceOrder.issuePhoto') }}:</strong>
              <el-button type="text" :style="{ color: item.issuePhoto ? '' : 'gray' }" @click="item.issuePhoto ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + item.issuePhoto) : null">
                {{ item.issuePhoto ? $t('maintenanceOrder.viewImage') : $t('maintenanceOrder.noImage') }}
              </el-button>
            </p>
            <div class="button-container">
              <el-button :type="item.processingStatus === 'On Process' ? 'success' : 'primary'" size="mini" @click="handleSubmitOrUpdate(item)">
                {{ item.processingStatus === 'On Process' ? $t('maintenanceOrder.submit') : $t('maintenanceOrder.update') }}
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-table v-else v-loading="loading" :data="maintenanceOrderList" @selection-change="handleSelectionChange" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('issueId')" :label="$t('maintenanceOrder.issueId')" prop="issueId" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('issuerName')" :label="$t('maintenanceOrder.issuerName')" prop="issuerName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issuePhoto')" :label="$t('maintenanceOrder.issuePhoto')" prop="issuePhoto" :show-overflow-tooltip="true" width="120">
        <template slot-scope="scope">
          <el-button type="text" :style="{ color: scope.row.issuePhoto ? '' : 'gray' }" @click="scope.row.issuePhoto ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + scope.row.issuePhoto) : null">
            {{ scope.row.issuePhoto ? $t('maintenanceOrder.viewImage') : $t('maintenanceOrder.noImage') }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('issuerEmail')" :label="$t('maintenanceOrder.issuerEmail')" prop="issuerEmail" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issueDetails')" :label="$t('maintenanceOrder.issueDetails')" prop="issueDetails" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('classroom')" :label="$t('maintenanceOrder.classroom')" prop="classroom" :show-overflow-tooltip="true" width="100" sortable />
      <el-table-column v-if="visibleColumns.includes('floor')" :label="$t('maintenanceOrder.floor')" prop="floor" :show-overflow-tooltip="true" width="80" sortable />
      <el-table-column v-if="visibleColumns.includes('maintenanceType')" :label="$t('maintenanceOrder.maintenanceType')" align="center" prop="maintenanceType" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_maintenance_type" :value="scope.row.maintenanceType"/>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('urgencyLevel')" :label="$t('maintenanceOrder.urgencyLevel')" align="center" prop="urgencyLevel" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_urgency_level" :value="scope.row.urgencyLevel"/>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('issuerPhone')" :label="$t('maintenanceOrder.issuerPhone')" prop="issuerPhone" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuyId')" :label="$t('maintenanceOrder.facilityGuyId')" prop="facilityGuyId" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('date')" :label="$t('maintenanceOrder.date')" align="center" prop="date" width="180" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.date) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('facilityGuysEmail')" :label="$t('maintenanceOrder.facilityGuysEmail')" prop="facilityGuysEmail" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuysName')" :label="$t('maintenanceOrder.facilityGuysName')" prop="facilityGuysName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuyMobile')" :label="$t('maintenanceOrder.facilityGuyMobile')" prop="facilityGuyMobile" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuySupervisor')" :label="$t('maintenanceOrder.facilityGuySupervisor')" prop="facilityGuySupervisor" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('resultImgPath')" :label="$t('maintenanceOrder.resultImgPath')" prop="resultImgPath" :show-overflow-tooltip="true" width="120">
        <template slot-scope="scope">
          <el-button type="text" :style="{ color: scope.row.resultImgPath ? '' : 'gray' }" @click="scope.row.resultImgPath ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + scope.row.resultImgPath) : null">
            {{ scope.row.resultImgPath ? $t('maintenanceOrder.viewImage') : $t('maintenanceOrder.noImage') }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('resultMessage')" :label="$t('maintenanceOrder.resultMessage')" prop="resultMessage" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('processingStatus')" :label="$t('maintenanceOrder.processingStatus')" align="center" prop="processingStatus" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_maintenance_status" :value="scope.row.processingStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('maintenanceOrder.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleSubmit(scope.row)" v-if="scope.row.processingStatus === 'On Process'">
                <i class="el-icon-upload"></i> {{ $t('maintenanceOrder.submit') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-else>
                <i class="el-icon-edit"></i> {{ $t('maintenanceOrder.update') }}
              </el-dropdown-item>
              <!-- <el-dropdown-item @click.native="handleViewLogs(scope.row)">
                <i class="el-icon-document"></i> {{ $t('maintenanceOrder.viewLogs') }}
              </el-dropdown-item> -->
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

    <!-- 提交或更新维护工单对话框 -->
    <el-dialog :title="submitOrUpdateTitle" :visible.sync="submitOrUpdateOpen" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="submitOrUpdateForm" :model="submitOrUpdateForm" label-width="100px">
        <el-form-item :label="$t('maintenanceOrder.facilityGuysName')" prop="facilityGuysName" v-if="submitOrUpdateTitle === $t('maintenanceOrder.update')">
          <el-input v-model="submitOrUpdateForm.facilityGuysName" :placeholder="$t('maintenanceOrder.facilityGuysName')" disabled />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.facilityGuyMobile')" prop="facilityGuyMobile" v-if="submitOrUpdateTitle === $t('maintenanceOrder.update')">
          <el-input v-model="submitOrUpdateForm.facilityGuyMobile" :placeholder="$t('maintenanceOrder.facilityGuyMobile')" disabled />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.facilityGuysEmail')" prop="facilityGuysEmail" v-if="submitOrUpdateTitle === $t('maintenanceOrder.update')">
          <el-input v-model="submitOrUpdateForm.facilityGuysEmail" :placeholder="$t('maintenanceOrder.facilityGuysEmail')" disabled />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.resultMessage')" prop="resultMessage">
          <el-input v-model="submitOrUpdateForm.resultMessage" :placeholder="$t('maintenanceOrder.enterResultMessage')" />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.resultImgPath')" prop="resultImgPath">
          <el-upload
            class="upload-demo"
            action="#"
            :http-request="uploadImage"
            list-type="picture-card"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="previewVisible">
            <img width="100%" :src="previewImage" alt="" />
          </el-dialog>
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.processingStatus')" prop="processingStatus">
          <el-select v-model="submitOrUpdateForm.processingStatus" :placeholder="$t('maintenanceOrder.selectProcessingStatus')">
            <el-option
              v-for="dict in dict.type.sys_maintenance_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
              v-if="submitOrUpdateTitle === $t('maintenanceOrder.submit') ? (dict.value === 'Resolved' || dict.value === 'Can not Resolve') : true"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :disabled="!isFormChanged || !submitOrUpdateForm.processingStatus || (submitOrUpdateTitle === $t('maintenanceOrder.submit') && submitOrUpdateForm.processingStatus === 'On Process')" @click="submitOrUpdateFormAction">{{ $t('maintenanceOrder.confirm') }}</el-button>
        <el-button @click="cancelSubmitOrUpdate">{{ $t('maintenanceOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 字段设置对话框 -->
    <el-dialog :title="$t('maintenanceOrder.columnSettings')" :visible.sync="showColumnSettings" :width="isMobile ? '100%' : '300px'">
      <el-checkbox-group v-model="visibleColumns">
        <el-row>
          <el-col :span="12"><el-checkbox label="issueId">{{ $t('maintenanceOrder.issueId') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerName">{{ $t('maintenanceOrder.issuerName') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuePhoto">{{ $t('maintenanceOrder.issuePhoto') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerEmail">{{ $t('maintenanceOrder.issuerEmail') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issueDetails">{{ $t('maintenanceOrder.issueDetails') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="classroom">{{ $t('maintenanceOrder.classroom') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="floor">{{ $t('maintenanceOrder.floor') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="maintenanceType">{{ $t('maintenanceOrder.maintenanceType') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="urgencyLevel">{{ $t('maintenanceOrder.urgencyLevel') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerPhone">{{ $t('maintenanceOrder.issuerPhone') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuyId">{{ $t('maintenanceOrder.facilityGuyId') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="date">{{ $t('maintenanceOrder.date') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuysEmail">{{ $t('maintenanceOrder.facilityGuysEmail') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuysName">{{ $t('maintenanceOrder.facilityGuysName') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuyMobile">{{ $t('maintenanceOrder.facilityGuyMobile') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuySupervisor">{{ $t('maintenanceOrder.facilityGuySupervisor') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="resultImgPath">{{ $t('maintenanceOrder.resultImgPath') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="resultMessage">{{ $t('maintenanceOrder.resultMessage') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="processingStatus">{{ $t('maintenanceOrder.processingStatus') }}</el-checkbox></el-col>
        </el-row>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showColumnSettings = false">{{ $t('maintenanceOrder.close') }}</el-button>
      </div>
    </el-dialog>

    <!-- 图片查看对话框 -->
    <el-dialog :title="$t('maintenanceOrder.viewImage')" :visible.sync="showImageDialog" :width="isMobile ? '100%' : '600px'">
      <img :src="imageSrc" :alt="$t('maintenanceOrder.image')" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showImageDialog = false">{{ $t('maintenanceOrder.close') }}</el-button>
      </div>
    </el-dialog>

    <!-- 记录查看对话框 -->
    <log-dialog v-if="logDialogVisible" :visible.sync="logDialogVisible" :log-list="logList" :issue-id="currentIssueId" @send-message="sendMessage"></log-dialog>
  </div>
</template>

<script>
import { listMyMaintenanceOrder, getMaintenanceOrder, updateMaintenanceOrder, uploadImage, exportMaintenanceOrder, listUserByDeptId, listMaintenanceOrderLogs, addMaintenanceOrderLog, getUserProfile, sendCompletionEmail, sendIncompleteEmail } from "@/api/maintenanceOrder/my/maintenanceOrderMy";

export default {
  name: "MyMaintenanceOrder",
  dicts: ['sys_urgency_level', 'sys_maintenance_status', 'sys_maintenance_type'],
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
      // 维护订单表格数据
      maintenanceOrderList: [],
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
        sortOrder: 'descending'
      },
      // 表单参数
      submitOrUpdateForm: {},
      originalSubmitOrUpdateForm: {},
      // 表单校验
      rules: {
        issueDetails: [
          { required: true, message: "问题详情不能为空", trigger: "blur" }
        ],
        urgencyLevel: [
          { required: true, message: "紧急程度不能为空", trigger: "blur" }
        ],
        maintenanceType: [
          { required: true, message: "维修类型不能为空", trigger: "blur" }
        ],
        floor: [
          { required: true, message: "楼层不能为空", trigger: "blur" }
        ],
        classroom: [
          { required: true, message: "教室不能为空", trigger: "blur" }
        ]
      },
      fileList: [],
      previewVisible: false,
      previewImage: '',
      visibleColumns: ['issuerName', 'issuePhoto', 'issueDetails', 'classroom', 'floor', 'maintenanceType', 'urgencyLevel', 'date', 'resultImgPath', 'resultMessage', 'processingStatus'],
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
    /** 查询维护工单列表 */
    getList() {
      this.loading = true;
      const params = {
        ...this.addDateRange(this.queryParams, this.dateRange),
        sortField: this.sortParams.prop,
        sortOrder: this.sortParams.order
      };
      listMyMaintenanceOrder(params).then(response => {
          this.maintenanceOrderList = response.rows;
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
      if (this.submitOrUpdateForm.resultImgPath) {
        const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${this.submitOrUpdateForm.resultImgPath}`;
        this.fileList.push({ name: this.submitOrUpdateForm.resultImgPath, url: fullUrl });
      }
      this.submitOrUpdateTitle = row.processingStatus === 'On Process' ? this.$t('maintenanceOrder.submit') : this.$t('maintenanceOrder.update');
      this.submitOrUpdateOpen = true;
    },
    handleSubmit(row) {
      this.resetSubmitOrUpdateForm();
      this.submitOrUpdateForm = { ...row };
      this.originalSubmitOrUpdateForm = { ...row };
      if (this.submitOrUpdateForm.resultImgPath) {
        const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${this.submitOrUpdateForm.resultImgPath}`;
        this.fileList.push({ name: this.submitOrUpdateForm.resultImgPath, url: fullUrl });
      }
      this.submitOrUpdateTitle = this.$t('maintenanceOrder.submit');
      this.submitOrUpdateOpen = true;
    },
    handleUpdate(row) {
      this.resetSubmitOrUpdateForm();
      this.submitOrUpdateForm = { ...row };
      this.originalSubmitOrUpdateForm = { ...row };
      if (this.submitOrUpdateForm.resultImgPath) {
        const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${this.submitOrUpdateForm.resultImgPath}`;
        this.fileList.push({ name: this.submitOrUpdateForm.resultImgPath, url: fullUrl });
      }
      this.submitOrUpdateTitle = this.$t('maintenanceOrder.update');
      this.submitOrUpdateOpen = true;
    },
    /** 提交或更新表单操作 */
    submitOrUpdateFormAction() {
      if (this.fileList.some(file => file.status !== 'success')) {
        this.$message.error(this.$t('maintenanceOrder.uploadError'));
        return;
      }
      if (this.fileList.length === 0) {
        this.submitOrUpdateForm.resultImgPath = '';
      } 
      if (this.submitOrUpdateTitle === this.$t('maintenanceOrder.submit')) {
        this.updateMaintenanceOrderData(this.$t('maintenanceOrder.submit'));
      } else {
        this.updateMaintenanceOrderData(this.$t('maintenanceOrder.update'));
      }
    },
    updateMaintenanceOrderData(actionType) {
      this.$refs["submitOrUpdateForm"].validate(valid => {
        if (valid) {
          if (this.fileList.length === 0) {
            this.submitOrUpdateForm.resultImgPath = '';
          } 
          updateMaintenanceOrder(this.submitOrUpdateForm).then(response => {
            this.$modal.msgSuccess(this.$t('maintenanceOrder.actionSuccess'));
            this.submitOrUpdateOpen = false;
            this.getList();
            if (actionType === this.$t('maintenanceOrder.submit')) {
              const statusLabel = this.dict.type.sys_maintenance_status.find(item => item.value === this.submitOrUpdateForm.processingStatus)?.label || this.submitOrUpdateForm.processingStatus;
              // this.addLog(this.submitOrUpdateForm.issueId, actionType, `工单提交为 ${statusLabel}`);
              if (this.submitOrUpdateForm.processingStatus === 'Resolved') {
                sendCompletionEmail(this.submitOrUpdateForm.issueId);
              } else if (this.submitOrUpdateForm.processingStatus === 'Can not Resolve') {
                sendIncompleteEmail(this.submitOrUpdateForm.issueId);
              }
            } else {
              // this.addDetailedLog(this.submitOrUpdateForm.issueId, actionType, this.originalSubmitOrUpdateForm, this.submitOrUpdateForm);
            }
          });
        }
      });
    },
    addDetailedLog(issueId, actionType, originalData, updatedData) {
      const fieldNames = {
        issueDetails: "问题详情",
        urgencyLevel: "紧急程度",
        maintenanceType: "维修类型",
        floor: "楼层",
        classroom: "教室",
        resultMessage: "结果信息",
        resultImgPath: "处理图片",
        processingStatus: "处理状态",
        facilityGuysName: "维修人员姓名",
        facilityGuyMobile: "维修人员电话",
        facilityGuysEmail: "维修人员邮箱"
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
          changes.push(`${fieldName} 从 "${oldValue}" 更新为 "${newValue}"`);
        }
      }
      const actionDescription = changes.length > 0 ? changes.join(", ") : "无变化";
      const log = {
        issueId: issueId,
        actionType: actionType,
        actionDescription: actionDescription,
        userId: this.currentUser.userId,
        userName: this.currentUser.userName
      };
      addMaintenanceOrderLog(log).then(response => {
        if (response.code !== 200) {
          this.$message.error("日志记录失败");
        }
      });
    },
    /** 导出按钮操作 */
    // handleExport() {
    //   exportMaintenanceOrder(this.queryParams).then(response => {
    //     this.download(response, `maintenanceOrder_${new Date().getTime()}.xlsx`);
    //   });
    // },
    handleExport() {
      this.download('maintenanceOrder/my/export', {
        ...this.queryParams
      }, `maintenanceOrder_my_${new Date().getTime()}.xlsx`)
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
        this.$message.error(this.$t('maintenanceOrder.uploadFail'));
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
    resetSubmitOrUpdateForm() {
      this.submitOrUpdateForm = {};
      this.originalSubmitOrUpdateForm = {};
      this.fileList = [];
      this.resetForm("submitOrUpdateForm");
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
      // 假设部门ID为200
      listUserByDeptId(200).then(response => {
        this.deptUsers = response.data;
      });
    },
    handleViewLogs(row) {
      import('../all/LogDialog.vue').then(module => {
        this.$options.components.LogDialog = module.default;
        listMaintenanceOrderLogs(row.issueId).then(response => {
          this.logList = response.data;
          this.currentIssueId = row.issueId;
          this.logDialogVisible = true;
        });
      });
    },
    sendMessage(message) {
      if (message.trim() !== "") {
        const log = {
          issueId: this.currentIssueId,
          actionType: "沟通",
          actionDescription: message,
          userId: this.currentUser.userId,
          userName: this.currentUser.userName
        };
        addMaintenanceOrderLog(log).then(response => {
          if (response.code === 200) {
            this.refreshLogs();
            this.newMessage = "";
          } else {
            this.$message.error("发送消息失败");
          }
        });
      }
    },
    refreshLogs() {
      listMaintenanceOrderLogs(this.currentIssueId).then(response => {
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
      addMaintenanceOrderLog(log).then(response => {
        if (response.code !== 200) {
          this.$message.error("日志记录失败");
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
