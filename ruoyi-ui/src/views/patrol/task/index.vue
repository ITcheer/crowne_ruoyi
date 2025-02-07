<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('patrolTask.taskId')" prop="id">
        <el-input
          v-model="queryParams.id"
          :placeholder="$t('patrolTask.enterTaskId')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('patrolTask.taskName')" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          :placeholder="$t('patrolTask.enterTaskName')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('patrolTask.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('patrolTask.reset') }}</el-button>
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
          v-hasPermi="['patrol:task:add']"
        >{{ $t('patrolTask.add') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['patrol:task:edit']"
        >{{ $t('patrolTask.edit') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['patrol:task:remove']"
        >{{ $t('patrolTask.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['patrol:task:export']"
        >{{ $t('patrolTask.export') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-setting"
          size="mini"
          @click="showColumnSettings = true"
        >{{ $t('patrolTask.columnSettings') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row v-if="isMobile" :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in patrolTaskList" :key="item.id">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <p><strong>{{ $t('patrolTask.taskId') }}:</strong> {{ item.id }}</p>
            <p><strong>{{ $t('patrolTask.taskName') }}:</strong> {{ item.taskName }}</p>
            <p><strong>{{ $t('patrolTask.taskContent') }}:</strong> {{ item.taskContent }}</p>
            <p><strong>{{ $t('patrolTask.taskTimePeriod') }}:</strong> {{ item.taskTimePeriod }}</p>
            <p><strong>{{ $t('patrolTask.taskCycle') }}:</strong> {{ item.taskCycle }}</p>
            <p><strong>{{ $t('patrolTask.taskStartDate') }}:</strong> {{ formatDate(item.taskStartDate) }}</p>
            <p><strong>{{ $t('patrolTask.taskEndDate') }}:</strong> {{ formatDate(item.taskEndDate) }}</p>
            <p><strong>{{ $t('patrolTask.updateTime') }}:</strong> {{ formatDate(item.updateTime) }}</p>
            <p>
              <strong>{{ $t('patrolTask.locations') }}:</strong>
              <el-tag v-for="location in item.locations" :key="location.id" type="info" class="mr8">{{ location.locationDetails ? location.locationDetails.locationName : '未知地点' }}</el-tag>
            </p>
            <p>
              <strong>{{ $t('patrolTask.participants') }}:</strong>
              <el-tag v-for="participant in item.participants" :key="participant.id" type="success" class="mr8">{{ participant.userDetails ? participant.userDetails.userName : '未知用户' }}</el-tag>
            </p>
            <el-button class="expand-button" type="text" @click="toggleDetails(item)">
              <i :class="item.showDetails ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
            </el-button>
            <div v-if="item.showDetails" class="button-container">
              <el-button type="success" size="mini" @click="handleUpdate(item)" v-hasPermi="['patrol:task:edit']">{{ $t('patrolTask.edit') }}</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(item)" v-hasPermi="['patrol:task:remove']">{{ $t('patrolTask.delete') }}</el-button>
              <el-button type="primary" size="mini" @click="handleViewRecords(item)" v-hasPermi="['patrol:task:query']">{{ $t('patrolTask.viewRecords') }}</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-table v-else v-loading="loading" :data="patrolTaskList" @selection-change="handleSelectionChange" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('id')" :label="$t('patrolTask.taskId')" prop="id" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('taskName')" :label="$t('patrolTask.taskName')" prop="taskName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('taskContent')" :label="$t('patrolTask.taskContent')" prop="taskContent" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('taskTimePeriod')" :label="$t('patrolTask.taskTimePeriod')" prop="taskTimePeriod" width="180" sortable />
      <el-table-column v-if="visibleColumns.includes('taskCycle')" :label="$t('patrolTask.taskCycle')" prop="taskCycle" width="180" sortable />
      <el-table-column v-if="visibleColumns.includes('taskStartDate')" :label="$t('patrolTask.taskStartDate')" prop="taskStartDate" width="180" sortable>
        <template slot-scope="scope">
          {{ formatDate(scope.row.taskStartDate) }}
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('taskEndDate')" :label="$t('patrolTask.taskEndDate')" prop="taskEndDate" width="180" sortable>
        <template slot-scope="scope">
          {{ formatDate(scope.row.taskEndDate) }}
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('updateTime')" :label="$t('patrolTask.updateTime')" prop="updateTime" width="180" sortable>
        <template slot-scope="scope">
          {{ formatDate(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('locations')" :label="$t('patrolTask.locations')" prop="locations" width="200">
        <template slot-scope="scope">
          <el-tag v-for="location in scope.row.locations" :key="location.id" type="info" class="mr8">{{ location.locationDetails ? location.locationDetails.locationName : '未知地点' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('participants')" :label="$t('patrolTask.participants')" prop="participants" width="200">
        <template slot-scope="scope">
          <el-tag v-for="participant in scope.row.participants" :key="participant.id" type="success" class="mr8">{{ participant.userDetails ? participant.userDetails.userName : '未知用户' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('patrolTask.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['patrol:task:edit']">
                <i class="el-icon-edit"></i> {{ $t('patrolTask.edit') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['patrol:task:remove']">
                <i class="el-icon-delete"></i> {{ $t('patrolTask.delete') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleViewRecords(scope.row)" v-hasPermi="['patrol:task:query']">
                <i class="el-icon-view"></i> {{ $t('patrolTask.viewRecords') }}
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

    <!-- 添加或修改巡逻任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '600px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('patrolTask.taskId')" prop="id">
          <el-input v-model="form.id" :placeholder="$t('patrolTask.taskId')" disabled />
        </el-form-item>
        <el-form-item :label="$t('patrolTask.taskName')" prop="taskName">
          <el-input v-model="form.taskName" :placeholder="$t('patrolTask.enterTaskName')" />
        </el-form-item>
        <el-form-item :label="$t('patrolTask.taskContent')" prop="taskContent">
          <el-input v-model="form.taskContent" :placeholder="$t('patrolTask.enterTaskContent')" />
        </el-form-item>
        <el-form-item :label="$t('patrolTask.taskTimePeriod')" prop="taskTimePeriod">
          <el-time-picker
            v-model="form.taskStartTime"
            :placeholder="$t('patrolTask.startTime')"
            :picker-options="{ selectableRange: '00:00:00 - 23:59:59' }"
            format="HH:mm"
            value-format="HH:mm"
            style="width: 45%; margin-right: 10%;"
          />
          <el-time-picker
            v-model="form.taskEndTime"
            :placeholder="$t('patrolTask.endTime')"
            :picker-options="{ selectableRange: '00:00:00 - 23:59:59' }"
            format="HH:mm"
            value-format="HH:mm"
            style="width: 45%;"
          />
        </el-form-item>
        <el-form-item :label="$t('patrolTask.taskCycleType')" prop="taskCycleType">
          <el-select v-model="form.taskCycleType" :placeholder="$t('patrolTask.selectTaskCycleType')" style="width: 100%;">
            <el-option :label="$t('patrolTask.daily')" value="每天"></el-option>
            <el-option :label="$t('patrolTask.weekly')" value="每周"></el-option>
            <el-option :label="$t('patrolTask.monthly')" value="每月"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.taskCycleType === '每周'" :label="$t('patrolTask.selectWeek')" prop="taskCycle">
          <el-select v-model="form.taskCycle" :placeholder="$t('patrolTask.selectWeek')" style="width: 100%;">
            <el-option :label="$t('patrolTask.monday')" value="周一"></el-option>
            <el-option :label="$t('patrolTask.tuesday')" value="周二"></el-option>
            <el-option :label="$t('patrolTask.wednesday')" value="周三"></el-option>
            <el-option :label="$t('patrolTask.thursday')" value="周四"></el-option>
            <el-option :label="$t('patrolTask.friday')" value="周五"></el-option>
            <el-option :label="$t('patrolTask.saturday')" value="周六"></el-option>
            <el-option :label="$t('patrolTask.sunday')" value="周日"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.taskCycleType === '每月'" :label="$t('patrolTask.selectDate')" prop="taskCycle">
          <el-select v-model="form.taskCycle" :placeholder="$t('patrolTask.selectDate')" style="width: 100%;">
            <el-option v-for="day in 31" :key="day" :label="`${day}号`" :value="`${day}号`"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('patrolTask.taskStartDate')" prop="taskStartDate">
          <el-date-picker v-model="form.taskStartDate" type="date" :placeholder="$t('patrolTask.selectDate')" style="width: 100%;" />
        </el-form-item>
        <el-form-item :label="$t('patrolTask.taskEndDate')" prop="taskEndDate">
          <el-date-picker v-model="form.taskEndDate" type="date" :placeholder="$t('patrolTask.selectDate')" style="width: 100%;" />
        </el-form-item>
        <el-form-item :label="$t('patrolTask.locations')" prop="locations">
          <el-select v-model="form.locationIds" multiple :placeholder="$t('patrolTask.selectLocations')" style="width: 100%;">
            <el-option v-for="location in locationList" :key="location.id" :label="location.locationName" :value="location.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('patrolTask.participants')" prop="participants">
          <el-select v-model="form.participantIds" multiple :placeholder="$t('patrolTask.selectParticipants')" style="width: 100%;">
            <el-option v-for="participant in participantList" :key="participant.userId" :label="participant.userName" :value="participant.userId"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('patrolTask.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('patrolTask.cancel') }}</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="$t('patrolTask.columnSettings')" :visible.sync="showColumnSettings" :width="isMobile ? '100%' : '300px'">
      <el-checkbox-group v-model="visibleColumns">
        <el-row>
          <el-col :span="12"><el-checkbox label="id">{{ $t('patrolTask.taskId') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskName">{{ $t('patrolTask.taskName') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskContent">{{ $t('patrolTask.taskContent') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskTimePeriod">{{ $t('patrolTask.taskTimePeriod') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskCycle">{{ $t('patrolTask.taskCycle') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskStartDate">{{ $t('patrolTask.taskStartDate') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskEndDate">{{ $t('patrolTask.taskEndDate') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="updateTime">{{ $t('patrolTask.updateTime') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="locations">{{ $t('patrolTask.locations') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="participants">{{ $t('patrolTask.participants') }}</el-checkbox></el-col>
        </el-row>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showColumnSettings = false">关闭</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="isMobile ? '打卡记录' : '打卡记录'" :visible.sync="recordDialogVisible" :width="isMobile ? '100%' : '800px'">
      <el-table :data="recordList" style="width: 100%">
        <el-table-column prop="checkInTime" :label="$t('patrolTask.checkInTime')" width="180" sortable>
          <template slot-scope="scope">
            {{ formatDate(scope.row.checkInTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="participantName" :label="$t('patrolTask.participantName')" />
        <el-table-column prop="checkInLocationName" :label="$t('patrolTask.checkInLocationName')" />
        <el-table-column prop="checkInInfo" :label="$t('patrolTask.checkInInfo')" />
        <el-table-column prop="checkInImage" :label="$t('patrolTask.checkInImage')">
          <template slot-scope="scope">
            <el-button type="text" :style="{ color: scope.row.checkInImage ? '' : 'gray' }" @click="scope.row.checkInImage ? viewImage(scope.row.checkInImage) : null">
              {{ scope.row.checkInImage ? '查看图片' : '暂无' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="recordDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="图片查看" :visible.sync="showImageDialog" :width="isMobile ? '100%' : '600px'">
      <img :src="imageSrc" alt="图片" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showImageDialog = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPatrolTask, getPatrolTask, delPatrolTask, addPatrolTask, updatePatrolTask, exportPatrolTask, listLocations, listParticipants, listPatrolTaskRecords } from "@/api/patrol/task/patrolTask";

export default {
  name: "PatrolTask",
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
      // 巡逻任务表格数据
      patrolTaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
        taskName: undefined,
        sortField: '',
        sortOrder: ''
      },
      // 表单参数
      form: {
        id: this.generateTaskId(),
        taskName: undefined,
        taskContent: undefined,
        taskStartTime: undefined,
        taskEndTime: undefined,
        taskCycleType: undefined,
        taskCycle: undefined,
        taskStartDate: undefined,
        taskEndDate: undefined,
        locationIds: [],
        participantIds: [],
        updateTime: new Date()
      },
      // 表单校验
      rules: {
        taskName: [
          { required: true, message: this.$t('patrolTask.enterTaskName'), trigger: "blur" }
        ],
        taskContent: [
          { required: true, message: this.$t('patrolTask.enterTaskContent'), trigger: "blur" }
        ],
        taskTimePeriod: [
          { validator: this.validateTimePeriod, trigger: "change" }
        ],
        taskCycleType: [
          { required: true, message: this.$t('patrolTask.selectTaskCycleType'), trigger: "change" }
        ],
        taskCycle: [
          { required: true, message: this.$t('patrolTask.selectTaskCycle'), trigger: "change" }
        ],
        taskStartDate: [
          { required: true, message: this.$t('patrolTask.selectDate'), trigger: "change" },
          { validator: this.validateDateRange, trigger: "change" }
        ],
        taskEndDate: [
          { required: true, message: this.$t('patrolTask.selectDate'), trigger: "change" },
          { validator: this.validateDateRange, trigger: "change" }
        ],
        locationIds: [
          { required: true, message: this.$t('patrolTask.selectLocations'), trigger: "change" }
        ],
        participantIds: [
          { required: true, message: this.$t('patrolTask.selectParticipants'), trigger: "change" }
        ]
      },
      visibleColumns: ['taskName', 'taskContent', 'taskTimePeriod', 'taskCycle', 'taskStartDate', 'taskEndDate', 'locations', 'participants'],
      sortParams: {
        prop: 'id',
        order: 'ascending'
      },
      locationList: [],
      participantList: [],
      showColumnSettings: false,
      recordDialogVisible: false,
      recordList: [],
      showImageDialog: false,
      imageSrc: ''
    };
  },
  computed: {
    isMobile() {
      return window.innerWidth <= 768;
    }
  },
  created() {
    this.getList();
    this.getLocations();
    this.getParticipants();
  },
  methods: {
    /** 查询巡逻任务列表 */
    getList() {
      this.loading = true;
      const params = {
        ...this.queryParams,
        sortField: this.sortParams.prop,
        sortOrder: this.sortParams.order
      };
      listPatrolTask(params).then(response => {
          this.patrolTaskList = response.rows;
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
        id: this.generateTaskId(),
        taskName: undefined,
        taskContent: undefined,
        taskStartTime: undefined,
        taskEndTime: undefined,
        taskCycleType: undefined,
        taskCycle: undefined,
        taskStartDate: undefined,
        taskEndDate: undefined,
        locationIds: [],
        participantIds: [],
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
        taskName: undefined,
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
      this.title = this.$t('patrolTask.add');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPatrolTask(id).then(response => {
        Object.keys(response.data).forEach(key => {
          this.$set(this.form, key, response.data[key]);
        });
        if (this.form.taskTimePeriod) {
          const [startTime, endTime] = this.form.taskTimePeriod.split('-');
          this.$set(this.form, 'taskStartTime', startTime);
          this.$set(this.form, 'taskEndTime', endTime);
        } else {
          this.$set(this.form, 'taskStartTime', undefined);
          this.$set(this.form, 'taskEndTime', undefined);
        }
        
        // 获取任务地点和参与人员
        this.$set(this.form, 'locationIds', response.locations ? response.locations.map(location => location.locationId) : []);
        this.$set(this.form, 'participantIds', response.participants ? response.participants.filter(participant => participant.userDetails).map(participant => participant.userDetails.userId) : []);
        
        // 使用正则表达式判断任务周期类型
        if (/^周[一二三四五六日](,周[一二三四五六日])*$/.test(this.form.taskCycle)) {
          this.$set(this.form, 'taskCycleType', '每周');
        } else if (/^\d+号(,\d+号)*$/.test(this.form.taskCycle)) {
          this.$set(this.form, 'taskCycleType', '每月');
        } else {
          this.$set(this.form, 'taskCycleType', '每天');
        }

        this.$nextTick(() => {
          this.$refs.form.clearValidate();
          this.$refs.form.validate();
        });

        this.open = true;
        this.title = this.$t('patrolTask.edit');
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.updateTime = new Date();
          this.form.taskTimePeriod = `${this.form.taskStartTime}-${this.form.taskEndTime}`;
          this.form.locations = this.form.locationIds.map(id => ({ taskId: this.form.id, locationId: id }));
          this.form.participants = this.form.participantIds.map(id => ({ taskId: this.form.id, participantId: id }));
          if (this.isUpdate()) {
            updatePatrolTask(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('patrolTask.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addPatrolTask(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('patrolTask.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 判断是否为修改操作 */
    isUpdate() {
      return this.form.id && this.patrolTaskList.some(task => task.id === this.form.id);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('patrolTask.deleteConfirm', { ids })).then(function() {
        return delPatrolTask(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('patrolTask.deleteSuccess'));
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('patrol/task/export', {
        ...this.queryParams
      }, `patrol_task_${new Date().getTime()}.xlsx`)
    },
    handleSortChange({ prop, order }) {
      this.sortParams.prop = prop;
      this.sortParams.order = order;
      this.queryParams.sortField = prop;
      this.queryParams.sortOrder = order;
      this.getList();
    },
    generateTaskId() {
      if (!this.patrolTaskList || this.patrolTaskList.length === 0) {
        return '1';
      }
      const maxId = Math.max(...this.patrolTaskList.map(task => parseInt(task.id, 10)));
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
    getLocations() {
      listLocations().then(response => {
        this.locationList = response.data;
      });
    },
    getParticipants() {
      listParticipants().then(response => {
        this.participantList = response.data;
      });
    },
    validateTimePeriod(rule, value, callback) {
      if (!this.form.taskStartTime || !this.form.taskEndTime) {
        callback(new Error(this.$t('patrolTask.taskTimePeriodRequired')));
      } else if (this.form.taskStartTime > this.form.taskEndTime) {
        callback(new Error(this.$t('patrolTask.startTimeGreaterThanEndTime')));
      } else {
        callback();
      }
    },
    validateDateRange(rule, value, callback) {
      if (this.form.taskStartDate && this.form.taskEndDate && this.form.taskStartDate > this.form.taskEndDate) {
        callback(new Error(this.$t('patrolTask.startDateGreaterThanEndDate')));
      } else {
        callback();
      }
    },
    /** 查看打卡记录 */
    handleViewRecords(row) {
      const taskId = row.id;
      listPatrolTaskRecords(taskId).then(response => {
        this.recordList = response.data;
        this.recordDialogVisible = true;
      });
    },
    /** 查看图片 */
    viewImage(src) {
      this.imageSrc = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${src}`;
      this.showImageDialog = true;
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
