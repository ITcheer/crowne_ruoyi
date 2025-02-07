<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('engineeringLocation.locationId')" prop="id">
        <el-input
          v-model="queryParams.id"
          :placeholder="$t('engineeringLocation.enterLocationName')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('engineeringLocation.locationName')" prop="locationName">
        <el-input
          v-model="queryParams.locationName"
          :placeholder="$t('engineeringLocation.enterLocationName')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('engineeringLocation.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('engineeringLocation.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['engineering:location:add']"
        >{{ $t('engineeringLocation.add') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['engineering:location:edit']"
        >{{ $t('engineeringLocation.edit') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['engineering:location:remove']"
        >{{ $t('engineeringLocation.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['engineering:location:export']"
        >{{ $t('engineeringLocation.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row v-if="isMobile" :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in patrolLocationList" :key="item.id">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <p><strong>{{ $t('engineeringLocation.locationId') }}:</strong> {{ item.id }}</p>
            <p><strong>{{ $t('engineeringLocation.locationName') }}:</strong> {{ item.locationName }}</p>
            <p><strong>{{ $t('engineeringLocation.updateTime') }}:</strong> {{ formatDate(item.updateTime) }}</p>
            <p><strong>{{ $t('engineeringLocation.locationDescription') }}:</strong> {{ item.locationDescription }}</p>
            <p>
              <strong>{{ $t('engineeringLocation.qrCode') }}:</strong>
              <el-button type="text" @click="viewQRCode(item.id)">{{ $t('engineeringLocation.viewQRCode') }}</el-button>
            </p>
            <p>
              <strong>{{ $t('engineeringLocation.checkInLink') }}:</strong>
              <a :href="generateCheckInLink(item.id)" target="_blank" class="check-in-link">{{ generateCheckInLink(item.id) }}</a>
            </p>
            <el-button class="expand-button" type="text" @click="item.showDetails = !item.showDetails">
              <i :class="item.showDetails ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
            </el-button>
            <div v-if="item.showDetails" class="button-container">
              <el-button type="success" size="mini" @click="handleUpdate(item)" v-hasPermi="['engineering:location:edit']">{{ $t('engineeringLocation.edit') }}</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(item)" v-hasPermi="['engineering:location:remove']">{{ $t('engineeringLocation.delete') }}</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-table v-else v-loading="loading" :data="patrolLocationList" @selection-change="handleSelectionChange" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('id')" :label="$t('engineeringLocation.locationId')" prop="id" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('locationName')" :label="$t('engineeringLocation.locationName')" prop="locationName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('locationDescription')" :label="$t('engineeringLocation.locationDescription')" prop="locationDescription" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('updateTime')" :label="$t('engineeringLocation.updateTime')" prop="updateTime" width="180" sortable>
        <template slot-scope="scope">
          {{ formatDate(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('qrCode')" :label="$t('engineeringLocation.qrCode')" prop="qrCode">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="viewQRCode(scope.row.id)">{{ $t('engineeringLocation.viewQRCode') }}</el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('checkInLink')" :label="$t('engineeringLocation.checkInLink')" prop="checkInLink">
        <template slot-scope="scope">
          <a :href="generateCheckInLink(scope.row.id)" target="_blank" class="check-in-link">{{ generateCheckInLink(scope.row.id) }}</a>
        </template>
      </el-table-column>
      <el-table-column :label="$t('engineeringLocation.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['engineering:location:edit']">
                <i class="el-icon-edit"></i> {{ $t('engineeringLocation.edit') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['engineering:location:remove']">
                <i class="el-icon-delete"></i> {{ $t('engineeringLocation.delete') }}
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

    <!-- 添加或修改巡逻地点对话框 -->
    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('engineeringLocation.locationId')" prop="id">
          <el-input v-model="form.id" :placeholder="$t('engineeringLocation.locationId')" disabled />
        </el-form-item>
        <el-form-item :label="$t('engineeringLocation.locationName')" prop="locationName">
          <el-input v-model="form.locationName" :placeholder="$t('engineeringLocation.enterLocationName')" />
        </el-form-item>
        <el-form-item :label="$t('engineeringLocation.locationDescription')" prop="locationDescription">
          <el-input v-model="form.locationDescription" :placeholder="$t('engineeringLocation.enterLocationDescription')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('engineeringLocation.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('engineeringLocation.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 二维码查看弹窗 -->
    <el-dialog :title="$t('engineeringLocation.qrCode')" :visible.sync="showQRCodeDialog" :width="isMobile ? '100%' : '300px'">
      <img :src="qrCodeSrc" alt="二维码" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showQRCodeDialog = false">{{ $t('engineeringLocation.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPatrolLocation, getPatrolLocation, delPatrolLocation, addPatrolLocation, updatePatrolLocation, exportPatrolLocation } from "@/api/patrol/location/patrolLocation";
import QRCode from 'qrcode';

export default {
  name: "PatrolLocation",
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
      // 巡逻地点表格数据
      patrolLocationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
        locationName: undefined,
        sortField: '',
        sortOrder: ''
      },
      // 表单参数
      form: {
        id: this.generateLocationId(),
        locationName: undefined,
        locationDescription: undefined,
        updateTime: new Date()
      },
      // 表单校验
      rules: {
        locationName: [
          { required: true, message: this.$t('engineeringLocation.enterLocationName'), trigger: "blur" }
        ]
      },
      visibleColumns: ['id', 'locationName', 'locationDescription', 'updateTime', 'qrCode', 'checkInLink'],
      sortParams: {
        prop: 'id',
        order: 'ascending'
      },
      showQRCodeDialog: false,
      qrCodeSrc: ''
    };
  },
  computed: {
    isMobile() {
      return window.innerWidth <= 768;
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询巡逻地点列表 */
    getList() {
      this.loading = true;
      const params = {
        ...this.queryParams,
        sortField: this.sortParams.prop,
        sortOrder: this.sortParams.order
      };
      listPatrolLocation(params).then(response => {
          this.patrolLocationList = response.rows.map(item => ({ ...item, showDetails: false }));
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: this.generateLocationId(),
        locationName: undefined,
        locationDescription: undefined,
        updateTime: new Date()
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
        locationName: undefined,
        sortField: '',
        sortOrder: ''
      };
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('engineeringLocation.add');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPatrolLocation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('engineeringLocation.edit');
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.updateTime = new Date();
          if (this.isUpdate()) {
            updatePatrolLocation(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('engineeringLocation.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addPatrolLocation(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('engineeringLocation.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 判断是否为修改操作 */
    isUpdate() {
      return this.form.id && this.patrolLocationList.some(location => location.id === this.form.id);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('engineeringLocation.deleteConfirm', { ids })).then(function() {
        return delPatrolLocation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('engineeringLocation.deleteSuccess'));
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('patrol/location/export', {
        ...this.queryParams
      }, `patrol_location_${new Date().getTime()}.xlsx`)
    },
    handleSortChange({ prop, order }) {
      this.sortParams.prop = prop;
      this.sortParams.order = order;
      this.queryParams.sortField = prop;
      this.queryParams.sortOrder = order;
      this.getList();
    },
    generateLocationId() {
      if (!this.patrolLocationList || this.patrolLocationList.length === 0) {
        return '1';
      }
      const maxId = Math.max(...this.patrolLocationList.map(location => parseInt(location.id, 10)));
      return (maxId + 1).toString();
    },
    formatDate(date) {
      const d = new Date(date);
      const year = d.getFullYear();
      const month = (d.getMonth() + 1).toString().padStart(2, '0');
      const day = d.getDate().toString().padStart(2, '0');
      const hours = d.getHours().toString().padStart(2, '0');
      const minutes = d.getMinutes().toString().padStart(2, '0');
      const seconds = d.getSeconds().toString().padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    viewQRCode(id) {
      const qrCodeUrl = `https://schoolmaintain-webapp.azurewebsites.net/cn/patrol_page/${id}`;
      QRCode.toDataURL(qrCodeUrl).then(url => {
        this.qrCodeSrc = url;
        this.showQRCodeDialog = true;
      });
    },
    generateQRCode(id) {
      const qrCodeUrl = `https://schoolmaintain-webapp.azurewebsites.net/cn/patrol_page/${id}`;
      return QRCode.toDataURL(qrCodeUrl);
    },
    generateCheckInLink(id) {
      return `https://schoolmaintain-webapp.azurewebsites.net/cn/patrol_page/${id}`;
    }
  }
};
</script>

<style scoped>
/* 样式与 all 全部工单的 index 保持一致 */
.el-table th, .el-table td {
  padding: 8px 10px;
}
.check-in-link {
  color: #409EFF;
  text-decoration: underline;
}
.card-content {
  font-size: 12px;
  padding: 10px;
  position: relative;
  min-height: 200px; /* 调整卡片高度 */
}
.box-card {
  margin-bottom: 10px;
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
</style>

