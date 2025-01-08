<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="任务编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入任务编号"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务名称" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          placeholder="请输入任务名称"
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
          v-hasPermi="['patrolTask:add']"
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
          v-hasPermi="['patrolTask:edit']"
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
          v-hasPermi="['patrolTask:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['patrolTask:export']"
        >导出</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-setting"
          size="mini"
          @click="showColumnSettings = true"
        >字段设置</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patrolTaskList" @selection-change="handleSelectionChange" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('id')" label="任务编号" prop="id" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('taskName')" label="任务名称" prop="taskName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('taskContent')" label="任务内容" prop="taskContent" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('taskTimePeriod')" label="任务时间段" prop="taskTimePeriod" width="180" sortable />
      <el-table-column v-if="visibleColumns.includes('taskCycle')" label="任务周期" prop="taskCycle" width="180" sortable />
      <el-table-column v-if="visibleColumns.includes('taskStartDate')" label="任务开始日期" prop="taskStartDate" width="180" sortable>
        <template slot-scope="scope">
          {{ formatDate(scope.row.taskStartDate) }}
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('taskEndDate')" label="任务结束日期" prop="taskEndDate" width="180" sortable>
        <template slot-scope="scope">
          {{ formatDate(scope.row.taskEndDate) }}
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('updateTime')" label="更新时间" prop="updateTime" width="180" sortable>
        <template slot-scope="scope">
          {{ formatDate(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('locations')" label="任务地点" prop="locations" width="200">
        <template slot-scope="scope">
          <el-tag v-for="location in scope.row.locations" :key="location.id" type="info" class="mr8">{{ location.locationDetails ? location.locationDetails.locationName : '未知地点' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('participants')" label="任务参与人员" prop="participants" width="200">
        <template slot-scope="scope">
          <el-tag v-for="participant in scope.row.participants" :key="participant.id" type="success" class="mr8">{{ participant.userDetails ? participant.userDetails.userName : '未知用户' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['patrolTask:edit']">
                <i class="el-icon-edit"></i> 修改
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['patrolTask:remove']">
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

    <!-- 添加或修改巡逻任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '600px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="任务编号" prop="id">
          <el-input v-model="form.id" placeholder="任务编号" disabled />
        </el-form-item>
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="任务内容" prop="taskContent">
          <el-input v-model="form.taskContent" placeholder="请输入任务内容" />
        </el-form-item>
        <el-form-item label="任务时间段" prop="taskTimePeriod">
          <el-time-picker
            v-model="form.taskStartTime"
            placeholder="开始时间"
            :picker-options="{ selectableRange: '00:00:00 - 23:59:59' }"
            format="HH:mm"
            value-format="HH:mm"
            style="width: 45%; margin-right: 10%;"
          />
          <el-time-picker
            v-model="form.taskEndTime"
            placeholder="结束时间"
            :picker-options="{ selectableRange: '00:00:00 - 23:59:59' }"
            format="HH:mm"
            value-format="HH:mm"
            style="width: 45%;"
          />
        </el-form-item>
        <el-form-item label="任务周期" prop="taskCycleType">
          <el-select v-model="form.taskCycleType" placeholder="请选择任务周期" style="width: 100%;">
            <el-option label="每天" value="每天"></el-option>
            <el-option label="每周" value="每周"></el-option>
            <el-option label="每月" value="每月"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.taskCycleType === '每周'" label="选择星期" prop="taskCycle">
          <el-select v-model="form.taskCycle" placeholder="请选择星期" style="width: 100%;">
            <el-option label="周一" value="周一"></el-option>
            <el-option label="周二" value="周二"></el-option>
            <el-option label="周三" value="周三"></el-option>
            <el-option label="周四" value="周四"></el-option>
            <el-option label="周五" value="周五"></el-option>
            <el-option label="周六" value="周六"></el-option>
            <el-option label="周日" value="周日"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.taskCycleType === '每月'" label="选择日期" prop="taskCycle">
          <el-select v-model="form.taskCycle" placeholder="请选择日期" style="width: 100%;">
            <el-option v-for="day in 31" :key="day" :label="`${day}号`" :value="`${day}号`"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务开始日期" prop="taskStartDate">
          <el-date-picker v-model="form.taskStartDate" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="任务结束日期" prop="taskEndDate">
          <el-date-picker v-model="form.taskEndDate" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="任务地点" prop="locations">
          <el-select v-model="form.locationIds" multiple placeholder="请选择任务地点" style="width: 100%;">
            <el-option v-for="location in locationList" :key="location.id" :label="location.locationName" :value="location.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务参与人员" prop="participants">
          <el-select v-model="form.participantIds" multiple placeholder="请选择任务参与人员" style="width: 100%;">
            <el-option v-for="participant in participantList" :key="participant.userId" :label="participant.userName" :value="participant.userId"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="字段设置" :visible.sync="showColumnSettings" :width="isMobile ? '100%' : '300px'">
      <el-checkbox-group v-model="visibleColumns">
        <el-row>
          <el-col :span="12"><el-checkbox label="id">任务编号</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskName">任务名称</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskContent">任务内容</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskTimePeriod">任务时间段</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskCycle">任务周期</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskStartDate">任务开始日期</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="taskEndDate">任务结束日期</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="updateTime">更新时间</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="locations">任务地点</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="participants">任务参与人员</el-checkbox></el-col>
        </el-row>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showColumnSettings = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPatrolTask, getPatrolTask, delPatrolTask, addPatrolTask, updatePatrolTask, exportPatrolTask,listLocations,listParticipants } from "@/api/patrol/task/patrolTask";


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
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        taskContent: [
          { required: true, message: "任务内容不能为空", trigger: "blur" }
        ],
        taskTimePeriod: [
          { validator: this.validateTimePeriod, trigger: "change" }
        ],
        taskCycleType: [
          { required: true, message: "任务周期不能为空", trigger: "change" }
        ],
        taskCycle: [
          { required: true, message: "任务周期不能为空", trigger: "change" }
        ],
        taskStartDate: [
          { required: true, message: "任务开始日期不能为空", trigger: "change" },
          { validator: this.validateDateRange, trigger: "change" }
        ],
        taskEndDate: [
          { required: true, message: "任务结束日期不能为空", trigger: "change" },
          { validator: this.validateDateRange, trigger: "change" }
        ],
        locationIds: [
          { required: true, message: "任务地点不能为空", trigger: "change" }
        ],
        participantIds: [
          { required: true, message: "任务参与人员不能为空", trigger: "change" }
        ]
      },
      visibleColumns: ['taskName', 'taskContent', 'taskTimePeriod', 'taskCycle', 'taskStartDate', 'taskEndDate', 'locations', 'participants'],
      sortParams: {
        prop: 'id',
        order: 'ascending'
      },
      locationList: [],
      participantList: [],
      showColumnSettings: false
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
      this.title = "添加巡逻任务";
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
        this.title = "修改巡逻任务";
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
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPatrolTask(this.form).then(response => {
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
      return this.form.id && this.patrolTaskList.some(task => task.id === this.form.id);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除任务编号为"' + ids + '"的数据项？').then(function() {
        return delPatrolTask(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
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
        callback(new Error("任务时间段不能为空"));
      } else if (this.form.taskStartTime > this.form.taskEndTime) {
        callback(new Error("开始时间不能大于结束时间"));
      } else {
        callback();
      }
    },
    validateDateRange(rule, value, callback) {
      if (this.form.taskStartDate && this.form.taskEndDate && this.form.taskStartDate > this.form.taskEndDate) {
        callback(new Error("任务开始日期不能大于任务结束日期"));
      } else {
        callback();
      }
    }
  }
};
</script>

<style scoped>
/* 样式与 all 全部工单的 index 保持一致 */
</style>
