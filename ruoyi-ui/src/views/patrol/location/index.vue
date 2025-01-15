<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="地点编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入地点编号"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地点名称" prop="locationName">
        <el-input
          v-model="queryParams.locationName"
          placeholder="请输入地点名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['patrol:location:add']"
        >新增</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['patrol:location:edit']"
        >修改</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['patrol:location:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['patrol:location:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patrolLocationList" @selection-change="handleSelectionChange" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('id')" label="地点编号" prop="id" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('locationName')" label="地点名称" prop="locationName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('locationDescription')" label="地点描述" prop="locationDescription" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('updateTime')" label="更新时间" prop="updateTime" width="180" sortable>
        <template slot-scope="scope">
          {{ formatDate(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('qrCode')" label="打卡二维码" prop="qrCode">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="viewQRCode(scope.row.id)">查看二维码</el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('checkInLink')" label="打卡链接" prop="checkInLink">
        <template slot-scope="scope">
          <a :href="generateCheckInLink(scope.row.id)" target="_blank" class="check-in-link">{{ generateCheckInLink(scope.row.id) }}</a>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['patrol:location:edit']">
                <i class="el-icon-edit"></i> 修改
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['patrol:location:remove']">
                <i class="el-icon-delete"></i> 删除
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
        <el-form-item label="地点编号" prop="id">
          <el-input v-model="form.id" placeholder="地点编号" disabled />
        </el-form-item>
        <el-form-item label="地点名称" prop="locationName">
          <el-input v-model="form.locationName" placeholder="请输入地点名称" />
        </el-form-item>
        <el-form-item label="地点描述" prop="locationDescription">
          <el-input v-model="form.locationDescription" placeholder="请输入地点描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 二维码查看弹窗 -->
    <el-dialog title="打卡二维码" :visible.sync="showQRCodeDialog" :width="isMobile ? '100%' : '300px'">
      <img :src="qrCodeSrc" alt="二维码" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showQRCodeDialog = false">关闭</el-button>
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
          { required: true, message: "地点名称不能为空", trigger: "blur" }
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
          this.patrolLocationList = response.rows;
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
      this.title = "添加巡逻地点";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPatrolLocation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改巡逻地点";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.updateTime = new Date();
          if (this.isUpdate()) {
            updatePatrolLocation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPatrolLocation(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
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
      this.$modal.confirm('是否确认删除地点编号为"' + ids + '"的数据项？').then(function() {
        return delPatrolLocation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
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
</style>

