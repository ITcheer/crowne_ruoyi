<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('complaintOrder.issuerName')" prop="issuerName">
        <el-input
          v-model="queryParams.issuerName"
          :placeholder="$t('complaintOrder.enterIssuerName')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('complaintOrder.issueDetails')" prop="issueDetails">
        <el-input
          v-model="queryParams.issueDetails"
          :placeholder="$t('complaintOrder.enterIssueDetails')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('complaintOrder.processingStatus')" prop="processingStatus">
        <el-select
          v-model="queryParams.processingStatus"
          :placeholder="$t('complaintOrder.processingStatus')"
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
      <el-form-item :label="$t('complaintOrder.date')">
        <template v-if="isMobile">
          <div style="display: flex; gap: 10px;">
            <el-date-picker
              v-model="startDate"
              style="flex: 1;"
              value-format="yyyyMMdd"
              type="date"
              :placeholder="$t('complaintOrder.startDate')"
              :popper-class="isMobile ? 'mobile-date-picker' : ''"
              :append-to-body="isMobile"
              @change="handleStartDateChange"
            ></el-date-picker>
            <el-date-picker
              v-model="endDate"
              style="flex: 1;"
              value-format="yyyyMMdd"
              type="date"
              :placeholder="$t('complaintOrder.endDate')"
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
          :start-placeholder="$t('complaintOrder.startDate')"
          :end-placeholder="$t('complaintOrder.endDate')"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('complaintOrder.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('complaintOrder.reset') }}</el-button>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="showAdvancedSearch = true">{{ $t('complaintOrder.advancedSearch') }}</el-button>
      </el-form-item>
    </el-form>

    <el-dialog :title="$t('complaintOrder.advancedSearch')" :visible.sync="showAdvancedSearch" :width="isMobile ? '100%' : '800px'">
      <el-form :model="queryParams" ref="advancedSearchForm" size="small" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="$t('complaintOrder.issuerName')" prop="issuerName">
              <el-input v-model="queryParams.issuerName" :placeholder="$t('complaintOrder.enterIssuerName')" clearable />
            </el-form-item>
            <el-form-item :label="$t('complaintOrder.issueDetails')" prop="issueDetails">
              <el-input v-model="queryParams.issueDetails" :placeholder="$t('complaintOrder.enterIssueDetails')" clearable />
            </el-form-item>
            <el-form-item :label="$t('complaintOrder.processingStatus')" prop="processingStatus">
              <el-select v-model="queryParams.processingStatus" :placeholder="$t('complaintOrder.processingStatus')" clearable>
                <el-option
                  v-for="dict in dict.type.sys_maintenance_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('complaintOrder.date')">
              <el-date-picker
                v-model="dateRange"
                style="width: 100%"
                value-format="yyyyMMdd"
                type="daterange"
                range-separator="-"
                :start-placeholder="$t('complaintOrder.startDate')"
                :end-placeholder="$t('complaintOrder.endDate')"
              ></el-date-picker>
            </el-form-item>
            <el-form-item :label="$t('complaintOrder.issuerEmail')" prop="issuerEmail">
              <el-input v-model="queryParams.issuerEmail" :placeholder="$t('complaintOrder.enterIssuerEmail')" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('complaintOrder.issuerPhone')" prop="issuerPhone">
              <el-input v-model="queryParams.issuerPhone" :placeholder="$t('complaintOrder.enterIssuerPhone')" clearable />
            </el-form-item>
            <el-form-item :label="$t('complaintOrder.facilityGuysName')" prop="facilityGuysName">
              <el-input v-model="queryParams.facilityGuysName" :placeholder="$t('complaintOrder.enterFacilityGuysName')" clearable />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleQuery">{{ $t('complaintOrder.search') }}</el-button>
        <el-button @click="showAdvancedSearch = false">{{ $t('complaintOrder.close') }}</el-button>
      </div>
    </el-dialog>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['complaintOrder:all:add']"
        >{{ $t('complaintOrder.add') }}</el-button>
      </el-col>
      <el-col  v-if="!isMobile" :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['complaintOrder:all:edit']"
        >{{ $t('complaintOrder.edit') }}</el-button>
      </el-col>
      <el-col  v-if="!isMobile" :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['complaintOrder:all:remove']"
        >{{ $t('complaintOrder.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['complaintOrder:all:export']"
        >{{ $t('complaintOrder.export') }}</el-button>
      </el-col>
      <el-col  v-if="!isMobile" :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-setting"
          size="mini"
          @click="showColumnSettings = true"
        >{{ $t('complaintOrder.columnSettings') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row v-if="isMobile" :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in complaintOrderList" :key="item.issueId">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <p><strong>{{ $t('complaintOrder.issuerName') }}:</strong> {{ item.issuerName }}</p>
            <p><strong>{{ $t('complaintOrder.issueDetails') }}:</strong> {{ item.issueDetails }}</p>
            <p><strong>{{ $t('complaintOrder.date') }}:</strong> {{ parseTime(item.date) }}</p>
            <p class="inline-fields">
              <strong>{{ $t('complaintOrder.processingStatus') }}:</strong> <dict-tag :options="dict.type.sys_maintenance_status" :value="item.processingStatus"/>
            </p>
            <el-button class="expand-button" type="text" @click="toggleDetails(item)">
              <i :class="item.showDetails ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
            </el-button>
            <div v-if="item.showDetails" class="button-container">
              <el-button type="success" size="mini" @click="handleUpdate(item)" v-hasPermi="['complaintOrder:all:edit']">{{ $t('complaintOrder.edit') }}</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(item)" v-hasPermi="['complaintOrder:all:remove']">{{ $t('complaintOrder.delete') }}</el-button>
              <el-button type="primary" size="mini" @click="handleProcess(item)" v-hasPermi="['complaintOrder:all:process']">{{ item.processingStatus === 'Untreated' ? $t('complaintOrder.process') : $t('complaintOrder.update') }}</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-table v-else v-loading="loading" :data="complaintOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('issueId')" :label="$t('complaintOrder.issueId')" prop="issueId" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('issuerName')" :label="$t('complaintOrder.issuerName')" prop="issuerName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issuerEmail')" :label="$t('complaintOrder.issuerEmail')" prop="issuerEmail" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issueDetails')" :label="$t('complaintOrder.issueDetails')" prop="issueDetails" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('issuerPhone')" :label="$t('complaintOrder.issuerPhone')" prop="issuerPhone" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuysName')" :label="$t('complaintOrder.facilityGuysName')" prop="facilityGuysName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('date')" :label="$t('complaintOrder.date')" align="center" prop="date" width="180" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.date) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('resultMessage')" :label="$t('complaintOrder.resultMessage')" prop="resultMessage" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('processingStatus')" :label="$t('complaintOrder.processingStatus')" align="center" prop="processingStatus" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_maintenance_status || []" :value="scope.row.processingStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('complaintOrder.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['complaintOrder:all:edit']">
                <i class="el-icon-edit"></i> {{ $t('complaintOrder.edit') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['complaintOrder:all:remove']">
                <i class="el-icon-delete"></i> {{ $t('complaintOrder.delete') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleProcess(scope.row)" v-hasPermi="['complaintOrder:all:process']">
                <i class="el-icon-check"></i> {{ scope.row.processingStatus === 'Untreated' ? $t('complaintOrder.process') : $t('complaintOrder.update') }}
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

    <!-- 添加或修改投诉订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('complaintOrder.issueId')" prop="issueId">
          <el-input v-model="form.issueId" :placeholder="$t('complaintOrder.issueId')" disabled />
        </el-form-item>
        <el-form-item :label="$t('complaintOrder.issuerName')" prop="issuerName">
          <el-input v-model="form.issuerName" :placeholder="$t('complaintOrder.enterIssuerName')" />
        </el-form-item>
        <el-form-item :label="$t('complaintOrder.issuerEmail')" prop="issuerEmail">
          <el-input v-model="form.issuerEmail" :placeholder="$t('complaintOrder.enterIssuerEmail')" />
        </el-form-item>
        <el-form-item :label="$t('complaintOrder.issuerPhone')" prop="issuerPhone">
          <el-input v-model="form.issuerPhone" :placeholder="$t('complaintOrder.enterIssuerPhone')" />
        </el-form-item>
        <el-form-item :label="$t('complaintOrder.issueDetails')" prop="issueDetails">
          <el-input v-model="form.issueDetails" :placeholder="$t('complaintOrder.enterIssueDetails')" />
        </el-form-item>
        <el-form-item :label="$t('complaintOrder.date')" prop="date">
          <el-date-picker v-model="form.date" type="date" :placeholder="$t('complaintOrder.selectDate')" style="width: 100%;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('complaintOrder.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('complaintOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 处理投诉订单对话框 -->
    <el-dialog :title="processTitle" :visible.sync="processOpen" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="processForm" :model="processForm" :rules="processRules" label-width="120px">
        <el-form-item :label="$t('complaintOrder.facilityGuysName')" prop="facilityGuysName">
          <el-input v-model="processForm.facilityGuysName" :placeholder="$t('complaintOrder.enterFacilityGuysName')" @focus="getUserProfile" />
        </el-form-item>
        <el-form-item :label="$t('complaintOrder.facilityGuysEmail')" prop="facilityGuysEmail">
          <el-input v-model="processForm.facilityGuysEmail" :placeholder="$t('complaintOrder.enterFacilityGuysEmail')" @focus="getUserProfile" />
        </el-form-item>
        <el-form-item :label="$t('complaintOrder.facilityGuyMobile')" prop="facilityGuyMobile">
          <el-input v-model="processForm.facilityGuyMobile" :placeholder="$t('complaintOrder.enterFacilityGuyMobile')" @focus="getUserProfile" />
        </el-form-item>
        <el-form-item :label="$t('complaintOrder.resultMessage')" prop="resultMessage">
          <el-input v-model="processForm.resultMessage" :placeholder="$t('complaintOrder.enterResultMessage')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitProcessForm">{{ $t('complaintOrder.confirm') }}</el-button>
        <el-button @click="cancelProcess">{{ $t('complaintOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 字段设置对话框 -->
    <el-dialog :title="$t('complaintOrder.columnSettings')" :visible.sync="showColumnSettings" :width="isMobile ? '100%' : '300px'">
      <el-checkbox-group v-model="visibleColumns">
        <el-row>
          <el-col :span="12"><el-checkbox label="issueId">{{ $t('complaintOrder.issueId') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerName">{{ $t('complaintOrder.issuerName') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerEmail">{{ $t('complaintOrder.issuerEmail') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issueDetails">{{ $t('complaintOrder.issueDetails') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerPhone">{{ $t('complaintOrder.issuerPhone') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuysName">{{ $t('complaintOrder.facilityGuysName') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="date">{{ $t('complaintOrder.date') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="resultMessage">{{ $t('complaintOrder.resultMessage') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="processingStatus">{{ $t('complaintOrder.processingStatus') }}</el-checkbox></el-col>
        </el-row>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showColumnSettings = false">{{ $t('complaintOrder.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listComplaintOrder, getComplaintOrder, delComplaintOrder, addComplaintOrder, updateComplaintOrder, exportComplaintOrder } from "@/api/complaintOrder/all/complaintOrderAll";
import { getUserProfile } from "@/api/maintenanceOrder/my/maintenanceOrderMy";

export default {
  name: "ComplaintOrder",
  dicts: ['sys_maintenance_status'],
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
      // 投诉订单表格数据
      complaintOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 处理弹出层标题
      processTitle: this.$t('complaintOrder.process'),
      // 是否显示处理弹出层
      processOpen: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        issuerName: undefined,
        issueDetails: undefined,
        processingStatus: undefined,
        sortField: '',
        sortOrder: ''
      },
      // 表单参数
      form: {
        issueId: this.generateIssueId(),
        issuerName: undefined,
        issuerEmail: undefined,
        issuerPhone: undefined,
        issueDetails: undefined,
        date: new Date(),
        processingStatus: 'Untreated'
      },
      // 处理表单参数
      processForm: {
        facilityGuysName: undefined,
        facilityGuysEmail: undefined,
        facilityGuyMobile: undefined,
        resultMessage: undefined,
        processingStatus: 'Resolved'
      },
      // 表单校验
      rules: {
        issuerName: [
          { required: true, message: this.$t('complaintOrder.enterIssuerName'), trigger: "blur" }
        ],
        issuerEmail: [
          { required: true, message: this.$t('complaintOrder.enterIssuerEmail'), trigger: "blur" }
        ],
        issuerPhone: [
          { required: true, message: this.$t('complaintOrder.enterIssuerPhone'), trigger: "blur" }
        ],
        issueDetails: [
          { required: true, message: this.$t('complaintOrder.enterIssueDetails'), trigger: "blur" }
        ]
      },
      // 处理表单校验
      processRules: {
        facilityGuysName: [
          { required: true, message: this.$t('complaintOrder.enterFacilityGuysName'), trigger: "blur" }
        ],
        facilityGuysEmail: [
          { required: true, message: this.$t('complaintOrder.enterFacilityGuysEmail'), trigger: "blur" }
        ],
        facilityGuyMobile: [
          { required: true, message: this.$t('complaintOrder.enterFacilityGuyMobile'), trigger: "blur" }
        ],
        resultMessage: [
          { required: true, message: this.$t('complaintOrder.enterResultMessage'), trigger: "blur" }
        ]
      },
      showColumnSettings: false,
      visibleColumns: ['issuerName', 'issueDetails', 'issuerEmail', 'issuerPhone', 'facilityGuysName', 'resultMessage', 'processingStatus'],
      showAdvancedSearch: false,
      startDate: null,
      endDate: null
    };
  },
  created() {
    const status = this.$route.query.status;
    if (status) {
      this.queryParams.processingStatus = status;
    }
    this.getList();
  },
  computed: {
    isMobile() {
      return window.innerWidth <= 768;
    }
  },
  methods: {
    /** 查询投诉订单列表 */
    getList() {
      this.loading = true;
      const params = {
        ...this.addDateRange(this.queryParams, this.dateRange),
        sortField: this.queryParams.sortField,
        sortOrder: this.queryParams.sortOrder
      };
      listComplaintOrder(params).then(response => {
          this.complaintOrderList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        issuerName: undefined,
        issueDetails: undefined,
        processingStatus: undefined,
        sortField: '',
        sortOrder: ''
      };
      this.resetForm("queryForm");
      this.resetForm("advancedSearchForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.issueId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('complaintOrder.add');
      this.getUserProfile(); // 获取当前用户信息
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const issueId = row.issueId || this.ids;
      getComplaintOrder(issueId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('complaintOrder.edit');
        this.originalForm = { ...this.form }; // 保存原始数据
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isUpdate()) {
            updateComplaintOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('complaintOrder.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addComplaintOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('complaintOrder.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 判断是否为修改操作 */
    isUpdate() {
      return this.form.issueId && this.complaintOrderList.some(order => order.issueId === this.form.issueId);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const issueIds = row.issueId || this.ids;
      this.$modal.confirm(this.$t('complaintOrder.deleteConfirm', { issueIds })).then(() => {
        return delComplaintOrder(issueIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('complaintOrder.deleteSuccess'));
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('complaintOrder/all/export', {
        ...this.queryParams
      }, `complaintOrder_all_${new Date().getTime()}.xlsx`)
    },
    // 表单重置
    reset() {
      this.form = {
        issueId: this.generateIssueId(),
        issuerName: undefined,
        issuerEmail: undefined,
        issuerPhone: undefined,
        issueDetails: undefined,
        date: new Date(),
        processingStatus: 'Untreated'
      };
      this.resetForm("form");
    },
    generateIssueId() {
      return Math.random().toString().slice(2, 15);
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 获取当前用户信息 */
    getUserProfile() {
      getUserProfile().then(response => {
        const { userName, email, phonenumber } = response.data;
        this.form.issuerName = userName;
        this.form.issuerEmail = email;
        this.form.issuerPhone = phonenumber;
        this.processForm.facilityGuysName = userName;
        this.processForm.facilityGuysEmail = email;
        this.processForm.facilityGuyMobile = phonenumber;
      });
    },
    /** 处理按钮操作 */
    handleProcess(row) {
      this.resetProcessForm();
      const issueId = row.issueId || this.ids;
      getComplaintOrder(issueId).then(response => {
        this.processForm = response.data;
        this.processOpen = true;
        this.processTitle = this.processForm.processingStatus === 'Untreated' ? this.$t('complaintOrder.process') : this.$t('complaintOrder.update');
        this.getUserProfile(); // 获取当前用户信息
      });
    },
    /** 提交处理表单 */
    submitProcessForm() {
      this.$refs["processForm"].validate(valid => {
        if (valid) {
          this.processForm.processingStatus = 'Resolved';
          updateComplaintOrder(this.processForm).then(response => {
            this.$modal.msgSuccess(this.$t('complaintOrder.editSuccess'));
            this.processOpen = false;
            this.getList();
          });
        }
      });
    },
    // 处理表单重置
    resetProcessForm() {
      this.processForm = {
        facilityGuysName: undefined,
        facilityGuysEmail: undefined,
        facilityGuyMobile: undefined,
        resultMessage: undefined,
        processingStatus: 'Resolved'
      };
      this.resetForm("processForm");
    },
    cancelProcess() {
      this.processOpen = false;
      this.resetProcessForm();
    },
    handleStartDateChange(value) {
      this.startDate = value;
      this.dateRange = [this.startDate, this.endDate];
    },
    handleEndDateChange(value) {
      this.endDate = value;
      this.dateRange = [this.startDate, this.endDate];
    },
    toggleDetails(item) {
      this.$set(item, 'showDetails', !item.showDetails);
    }
  }
};
</script>

<style scoped>
.card-content {
  font-size: 12px;
  padding: 10px;
  position: relative;
  min-height: 150px; /* 调整卡片高度 */
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
  right: 40px; /* 将全部操作按钮左移 */
}
.expand-button {
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
.chat-container {
  max-height: 400px;
  overflow-y: auto;
  padding: 10px;
}
.chat-message {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
}
.chat-message-left {
  align-items: flex-start;
}
.chat-message-right {
  align-items: flex-end;
}
.chat-message-center {
  align-items: center;
  text-align: center;
}
.chat-message-content {
  background-color: #f1f1f1;
  padding: 10px;
  border-radius: 5px;
  max-width: 60%;
  word-wrap: break-word;
  width: fit-content;
}
.chat-message-content-center {
  background-color: transparent;
  max-width: 100%;
  font-size: 12px;
  color: #888;
}
.chat-message-info {
  font-size: 10px;
  color: #888;
  margin-top: 5px;
}
.chat-message-right .chat-message-content {
  background-color: #d1ecf1;
  align-self: flex-end;
}
.chat-message-right .chat-message-info {
  text-align: right;
}
.chat-message-info-center {
  font-size: 10px;
  color: #888;
  margin-top: 2px;
  margin-bottom: 15px;
}
</style>


