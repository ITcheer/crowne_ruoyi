<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('goods.name')" prop="name">
        <el-input
          v-model="queryParams.name"
          :placeholder="$t('goods.enterName')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('goods.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('goods.reset') }}</el-button>
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
          v-hasPermi="['storeroom:goods:add']"
        >{{ $t('goods.add') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['storeroom:goods:edit']"
        >{{ $t('goods.edit') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['storeroom:goods:remove']"
        >{{ $t('goods.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['storeroom:goods:export']"
        >{{ $t('goods.export') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload"
          size="mini"
          :disabled="single"
          @click="handleInventory"
          v-hasPermi="['storeroom:goods:inventory']"
        >{{ $t('goods.inventory') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row v-if="isMobile" :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in goodsList" :key="item.batchId">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <p><strong>{{ $t('goods.batchId') }}:</strong> {{ item.batchId }}</p>
            <p><strong>{{ $t('goods.name') }}:</strong> {{ item.name }}</p>
            <p><strong>{{ $t('goods.quantity') }}:</strong> {{ item.quantity }}</p>
            <p><strong>{{ $t('goods.unitPrice') }}:</strong> {{ item.unitPrice }}</p>
            <p><strong>{{ $t('goods.productionDate') }}:</strong> {{ formatDate(item.productionDate) }}</p>
            <p><strong>{{ $t('goods.shelfLife') }}:</strong> {{ item.shelfLife }}</p>
            <p><strong>{{ $t('goods.photo') }}:</strong> <el-button type="text" @click="viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' +item.photo)">{{ $t('goods.viewImage') }}</el-button></p>
            <p><strong>{{ $t('goods.description') }}:</strong> {{ item.description }}</p>
            <el-button class="expand-button" type="text" @click="toggleDetails(item)">
              <i :class="item.showDetails ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
            </el-button>
            <div v-if="item.showDetails" class="button-container">
              <el-button type="success" size="mini" @click="handleUpdate(item)" v-hasPermi="['storeroom:goods:edit']">{{ $t('goods.edit') }}</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(item)" v-hasPermi="['storeroom:goods:remove']">{{ $t('goods.delete') }}</el-button>
              <el-button type="primary" size="mini" @click="handleViewInventoryRecords(item)" v-hasPermi="['storeroom:goods:query']">{{ $t('goods.inventoryRecords') }}</el-button>
              <el-button type="info" size="mini" @click="handleInventory(item)" v-hasPermi="['storeroom:goods:inventory']">{{ $t('goods.inventory') }}</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-table v-else v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('goods.batchId')" prop="batchId" width="150" sortable />
      <el-table-column :label="$t('goods.name')" prop="name" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column :label="$t('goods.quantity')" prop="quantity" width="100" sortable />
      <el-table-column :label="$t('goods.unitPrice')" prop="unitPrice" width="100" sortable />
      <el-table-column :label="$t('goods.productionDate')" prop="productionDate" width="180" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productionDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.shelfLife')" prop="shelfLife" width="120" sortable />
      <el-table-column :label="$t('goods.photo')" prop="photo" :show-overflow-tooltip="true" width="150">
        <template slot-scope="scope">
          <el-button type="text" :style="{ color: scope.row.photo ? '' : 'gray' }" @click="scope.row.photo ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + scope.row.photo) : null">
            {{ scope.row.photo ? $t('goods.viewImage') : $t('goods.noImage') }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.description')" prop="description" :show-overflow-tooltip="true" width="250" sortable />
      <el-table-column :label="$t('goods.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['storeroom:goods:edit']">
                <i class="el-icon-edit"></i> {{ $t('goods.edit') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['storeroom:goods:remove']">
                <i class="el-icon-delete"></i> {{ $t('goods.delete') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleViewInventoryRecords(scope.row)" v-hasPermi="['storeroom:goods:query']">
                <i class="el-icon-document"></i> {{ $t('goods.inventoryRecords') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleInventory(scope.row)" v-hasPermi="['storeroom:goods:inventory']">
                <i class="el-icon-upload"></i> {{ $t('goods.inventory') }}
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

    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('goods.batchId')" prop="batchId">
          <el-input v-model="form.batchId" :placeholder="$t('goods.batchId')" disabled />
        </el-form-item>
        <el-form-item :label="$t('goods.name')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('goods.enterName')" />
        </el-form-item>
        <el-form-item :label="$t('goods.quantity')" prop="quantity">
          <el-input v-model="form.quantity" :placeholder="$t('goods.enterQuantity')" />
        </el-form-item>
        <el-form-item :label="$t('goods.unitPrice')" prop="unitPrice">
          <el-input v-model="form.unitPrice" :placeholder="$t('goods.enterUnitPrice')" />
        </el-form-item>
        <el-form-item :label="$t('goods.productionDate')" prop="productionDate">
          <el-date-picker v-model="form.productionDate" type="date" :placeholder="$t('goods.selectProductionDate')" style="width: 100%;" />
        </el-form-item>
        <el-form-item :label="$t('goods.shelfLife')" prop="shelfLife">
          <el-input v-model="form.shelfLife" :placeholder="$t('goods.enterShelfLife')" />
        </el-form-item>
        <el-form-item :label="$t('goods.photo')" prop="photo">
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
        <el-form-item :label="$t('goods.description')" prop="description">
          <el-input v-model="form.description" :placeholder="$t('goods.enterDescription')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('goods.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('goods.cancel') }}</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="$t('goods.viewImage')" :visible.sync="showImageDialog" :width="isMobile ? '100%' : '600px'">
      <img :src="imageSrc" alt="图片" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showImageDialog = false">{{ $t('goods.close') }}</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="$t('goods.inventoryRecords')" :visible.sync="showInventoryRecordsDialog" :width="isMobile ? '100%' : '900px'" class="inventory-records-dialog">
      <el-table :data="inventoryRecords" v-loading="loadingInventoryRecords" border stripe :height="tableHeight">
        <el-table-column prop="quantity" :label="$t('goods.quantity')" width="100" />
        <el-table-column prop="recordType" :label="$t('goods.recordType')" width="100" />
        <el-table-column prop="operator" :label="$t('goods.operator')" width="150" />
        <el-table-column prop="operationTime" :label="$t('goods.operationTime')" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.operationTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="details" :label="$t('goods.details')" width="250" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showInventoryRecordsDialog = false">{{ $t('goods.close') }}</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="inventoryTitle" :visible.sync="inventoryDialogVisible" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="inventoryForm" :model="inventoryForm" :rules="inventoryRules" label-width="100px">
        <el-form-item :label="$t('goods.recordType')" prop="recordType">
          <el-select v-model="inventoryForm.recordType" :placeholder="$t('goods.selectRecordType')">
            <el-option :label="$t('goods.inbound')" value="入库"></el-option>
            <el-option :label="$t('goods.outbound')" value="出库"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('goods.quantity')" prop="quantity">
          <el-input v-model="inventoryForm.quantity" :placeholder="$t('goods.enterQuantity')" type="number" />
        </el-form-item>
        <el-form-item :label="$t('goods.details')" prop="details">
          <el-input v-model="inventoryForm.details" :placeholder="$t('goods.enterDetails')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitInventoryForm">{{ $t('goods.confirm') }}</el-button>
        <el-button @click="cancelInventory">{{ $t('goods.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGoods, getGoods, delGoods, addGoods, updateGoods, exportGoods, uploadImage, getInventoryRecords, inventory } from "@/api/storeroom/goods/goods";

export default {
  name: "Goods",
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
      // 货品表格数据
      goodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined
      },
      // 表单参数
      form: {
        batchId: this.generateBatchId(),
        name: undefined,
        quantity: undefined,
        unitPrice: undefined,
        productionDate: undefined,
        shelfLife: undefined,
        photo: undefined,
        description: undefined
      },
      // 表单校验
      rules: {
        name: [
          { required: true, message: this.$t('goods.nameRequired'), trigger: "blur" }
        ],
        quantity: [
          { required: true, message: this.$t('goods.quantityRequired'), trigger: "blur" }
        ],
        unitPrice: [
          { required: true, message: this.$t('goods.unitPriceRequired'), trigger: "blur" }
        ],
        productionDate: [
          { required: true, message: this.$t('goods.productionDateRequired'), trigger: "blur" }
        ],
        shelfLife: [
          { required: true, message: this.$t('goods.shelfLifeRequired'), trigger: "blur" }
        ]
      },
      showImageDialog: false,
      imageSrc: '',
      fileList: [],
      previewVisible: false,
      previewImage: '',
      showInventoryRecordsDialog: false,
      inventoryRecords: [],
      loadingInventoryRecords: false,
      tableHeight: 400,
      inventoryDialogVisible: false,
      inventoryTitle: "出入库操作",
      inventoryForm: {
        goodsId: "",
        recordType: "",
        quantity: undefined,
        details: ""
      },
      inventoryRules: {
        recordType: [
          { required: true, message: this.$t('goods.recordTypeRequired'), trigger: "blur" }
        ],
        quantity: [
          { required: true, message: this.$t('goods.quantityRequired'), trigger: "blur" },
          // { type: 'number', message: "数量必须为数字", trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    isMobile() {
      return window.innerWidth <= 768;
    }
  },
  watch: {
    showInventoryRecordsDialog(val) {
      if (val) {
        this.$nextTick(() => {
          this.tableHeight = this.$refs.inventoryRecordsDialog.$el.clientHeight - 100;
        });
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询货品列表 */
    getList() {
      this.loading = true;
      listGoods(this.queryParams).then(response => {
        this.goodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        batchId: this.generateBatchId(),
        name: undefined,
        quantity: undefined,
        unitPrice: undefined,
        productionDate: undefined,
        shelfLife: undefined,
        photo: undefined,
        description: undefined
      };
      this.fileList = [];
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
        name: undefined
      };
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.batchId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('goods.add');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const batchId = row.batchId || this.ids;
      getGoods(batchId).then(response => {
        this.form = response.data;
        if (this.form.photo) {
          this.fileList = [{ name: this.form.photo, url: 'https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + this.form.photo }];
        } else {
          this.fileList = [];
        }
        this.open = true;
        this.title = this.$t('goods.edit');
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      if (this.fileList.some(file => file.status !== 'success')) {
        this.$message.error(this.$t('goods.uploadError'));
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.fileList.length === 0) {
            this.form.photo = '';
          }
          if (this.isUpdate()) {
            updateGoods(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('goods.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addGoods(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('goods.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 判断是否为修改操作 */
    isUpdate() {
      return this.form.batchId && this.goodsList.some(goods => goods.batchId === this.form.batchId);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const batchIds = row.batchId || this.ids;
      this.$modal.confirm(this.$t('goods.deleteConfirm', { batchIds })).then(() => {
        return delGoods(batchIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('goods.deleteSuccess'));
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('storeroom/goods/export', {
        ...this.queryParams
      }, `goods_${new Date().getTime()}.xlsx`)
    },
    viewImage(src) {
      this.imageSrc = src;
      this.showImageDialog = true;
    },
    generateBatchId() {
      return Math.random().toString().slice(2, 15);
    },
    uploadImage({ file }) {
      const formData = new FormData();
      formData.append('file', file);
      uploadImage(formData).then(response => {
        const relativePath = response.data;
        const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${relativePath}`;
        this.form.issuePhoto = relativePath;
        this.form.photo = relativePath;
        this.fileList.push({ name: file.name, url: fullUrl });
      }).catch(error => {
        this.$message.error(error.message);
      });
    },
    handlePreview(file) {
      this.previewImage = file.url;
      this.previewVisible = true;
    },
    handleRemove(file) {
      this.fileList = this.fileList.filter(item => item.url !== file.url);
      if (this.form.photo === file.name || this.form.photo === file.url) {
        this.form.photo = '';
      }
    },
    handleViewInventoryRecords(row) {
      this.loadingInventoryRecords = true;
      getInventoryRecords(row.batchId).then(response => {
        this.inventoryRecords = response.data;
        this.loadingInventoryRecords = false;
        this.showInventoryRecordsDialog = true;
      });
    },
    handleInventory(row) {
      this.reset();
      const selectedGoods = row || this.goodsList.find(goods => goods.batchId === this.ids[0]);
      if (selectedGoods) {
        this.inventoryForm.goodsId = selectedGoods.batchId || this.ids[0];
        this.inventoryDialogVisible = true;
      }
    },
    submitInventoryForm() {
      this.$refs["inventoryForm"].validate(valid => {
        if (valid) {
          inventory(this.inventoryForm).then(response => {
            this.$modal.msgSuccess(this.$t('goods.actionSuccess'));
            this.inventoryDialogVisible = false;
            this.getList();
          }).catch(error => {
            this.$modal.msgError(error.message);
          });
        }
      });
    },
    cancelInventory() {
      this.inventoryDialogVisible = false;
      this.resetInventoryForm();
    },
    resetInventoryForm() {
      this.inventoryForm = {
        goodsId: "",
        recordType: "",
        quantity: undefined,
        details: ""
      };
      this.resetForm("inventoryForm");
    },
    toggleDetails(item) {
      this.$set(item, 'showDetails', !item.showDetails);
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
    handleSortChange({ prop, order }) {
      this.sortParams.prop = prop;
      this.sortParams.order = order;
      this.queryParams.sortField = prop;
      this.queryParams.sortOrder = order;
      this.getList();
    }
  }
};
</script>

<style scoped>
.upload-demo {
  display: flex;
  align-items: center;
  justify-content: center;
}

.inventory-records-dialog .el-table {
  margin-bottom: 20px;
}

.inventory-records-dialog .dialog-footer {
  text-align: right;
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
/* .collapse-button {
  position: absolute;
  top: 10px;
  right: 10px;
} */
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
