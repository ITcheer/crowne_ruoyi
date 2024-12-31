<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="发布者名称" prop="issuerName">
        <el-input
          v-model="queryParams.issuerName"
          placeholder="请输入发布者名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="问题详情" prop="issueDetails">
        <el-input
          v-model="queryParams.issueDetails"
          placeholder="请输入问题详情"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="紧急程度" prop="urgencyLevel">
        <el-select
          v-model="queryParams.urgencyLevel"
          placeholder="紧急程度"
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
      <el-form-item label="处理情况" prop="processingStatus">
        <el-select
          v-model="queryParams.processingStatus"
          placeholder="处理情况"
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
      <el-form-item label="日期">
        <template v-if="isMobile">
          <div style="display: flex; gap: 10px;">
            <el-date-picker
              v-model="startDate"
              style="flex: 1;"
              value-format="yyyyMMdd"
              type="date"
              placeholder="开始日期"
              :popper-class="isMobile ? 'mobile-date-picker' : ''"
              :append-to-body="isMobile"
              @change="handleStartDateChange"
            ></el-date-picker>
            <el-date-picker
              v-model="endDate"
              style="flex: 1;"
              value-format="yyyyMMdd"
              type="date"
              placeholder="结束日期"
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
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="showAdvancedSearch = true">高级搜索</el-button>
      </el-form-item>
    </el-form>

    <el-dialog title="高级搜索" :visible.sync="showAdvancedSearch" :width="isMobile ? '100%' : '800px'">
      <el-form :model="queryParams" ref="advancedSearchForm" size="small" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="发布者名称" prop="issuerName">
              <el-input v-model="queryParams.issuerName" placeholder="请输入发布者名称" clearable />
            </el-form-item>
            <el-form-item label="问题详情" prop="issueDetails">
              <el-input v-model="queryParams.issueDetails" placeholder="请输入问题详情" clearable />
            </el-form-item>
            <el-form-item label="紧急程度" prop="urgencyLevel">
              <el-select v-model="queryParams.urgencyLevel" placeholder="紧急程度" clearable>
                <el-option
                  v-for="dict in dict.type.sys_urgency_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="处理情况" prop="processingStatus">
              <el-select v-model="queryParams.processingStatus" placeholder="处理情况" clearable>
                <el-option
                  v-for="dict in dict.type.sys_maintenance_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="日期">
              <el-date-picker
                v-model="dateRange"
                style="width: 100%"
                value-format="yyyyMMdd"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="发布者邮箱" prop="issuerEmail">
              <el-input v-model="queryParams.issuerEmail" placeholder="请输入发布者邮箱" clearable />
            </el-form-item>
            <el-form-item label="教室" prop="classroom">
              <el-input v-model="queryParams.classroom" placeholder="请输入教室" clearable />
            </el-form-item>
            <el-form-item label="楼层" prop="floor">
              <el-input v-model="queryParams.floor" placeholder="请输入楼层" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维修类型" prop="maintenanceType">
              <el-select v-model="queryParams.maintenanceType" placeholder="维修类型" clearable>
                <el-option
                  v-for="dict in dict.type.sys_maintenance_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="发布者电话" prop="issuerPhone">
              <el-input v-model="queryParams.issuerPhone" placeholder="请输入发布者电话" clearable />
            </el-form-item>
            <el-form-item label="维修人员ID" prop="facilityGuyId">
              <el-input v-model="queryParams.facilityGuyId" placeholder="请输入维修人员ID" clearable />
            </el-form-item>
            <el-form-item label="维修人员邮箱" prop="facilityGuysEmail">
              <el-input v-model="queryParams.facilityGuysEmail" placeholder="请输入维修人员邮箱" clearable />
            </el-form-item>
            <el-form-item label="维修人员姓名" prop="facilityGuysName">
              <el-input v-model="queryParams.facilityGuysName" placeholder="请输入维修人员姓名" clearable />
            </el-form-item>
            <el-form-item label="维修人员电话" prop="facilityGuyMobile">
              <el-input v-model="queryParams.facilityGuyMobile" placeholder="请输入维修人员电话" clearable />
            </el-form-item>
            <el-form-item label="主管邮箱" prop="facilityGuySupervisor">
              <el-input v-model="queryParams.facilityGuySupervisor" placeholder="请输入主管邮箱" clearable />
            </el-form-item>
            <el-form-item label="结果信息" prop="resultMessage">
              <el-input v-model="queryParams.resultMessage" placeholder="请输入结果信息" clearable />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="showAdvancedSearch = false">关闭</el-button>
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
          v-hasPermi="['maintenanceOrder:all:add']"
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
          v-hasPermi="['maintenanceOrder:all:edit']"
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
          v-hasPermi="['maintenanceOrder:all:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['maintenanceOrder:all:export']"
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

    <el-row v-if="isMobile" :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in maintenanceOrderList" :key="item.issueId">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <p><strong>发布者名称:</strong> {{ item.issuerName }}</p>
            <p><strong>问题详情:</strong> {{ item.issueDetails }}</p>
            <p><strong>日期:</strong> {{ parseTime(item.date) }}</p>
            <p>
              <strong>教室:</strong> {{ item.classroom }} 
              <strong>楼层:</strong> {{ item.floor }}
            </p>
            <p class="inline-fields">
              <strong>维修类型:</strong> <dict-tag :options="dict.type.sys_maintenance_type" :value="item.maintenanceType"/>
              <strong>紧急程度:</strong> <dict-tag :options="dict.type.sys_urgency_level" :value="item.urgencyLevel"/>
              <strong>处理状态:</strong> <dict-tag :options="dict.type.sys_maintenance_status" :value="item.processingStatus"/>
            </p>
            <p>
              <strong>问题照片:</strong>
              <el-button type="text" :style="{ color: item.issuePhoto ? '' : 'gray' }" @click="item.issuePhoto ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + item.issuePhoto) : null">
                {{ item.issuePhoto ? '查看图片' : '暂无' }}
              </el-button>
            </p>
            <el-button class="expand-button" type="text" @click="item.showDetails = !item.showDetails">
              <i :class="item.showDetails ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
            </el-button>
            <div v-if="item.showDetails" class="button-container">
              <el-button type="success" size="mini" @click="handleUpdate(item)" v-hasPermi="['maintenanceOrder:all:edit']">修改</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(item)" v-hasPermi="['maintenanceOrder:all:remove']">删除</el-button>
              <el-button type="primary" size="mini" @click="handleAssign(item)" v-hasPermi="['maintenanceOrder:all:assign']" :disabled="item.processingStatus !== 'Undistributed' && item.processingStatus !== 'On Process'">分配</el-button>
              <el-button type="primary" size="mini" @click="handleSubmitOrUpdate(item)" v-hasPermi="['maintenanceOrder:all:submitOrUpdate']" :disabled="item.processingStatus === 'Undistributed'">{{ item.processingStatus === 'On Process' ? '提交' : '更新' }}</el-button>
              <!-- <el-button class="collapse-button" type="text" @click="item.showDetails = false">
                <i class="el-icon-arrow-up"></i>
              </el-button> -->
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-table v-else v-loading="loading" :data="maintenanceOrderList" @selection-change="handleSelectionChange" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('issueId')" label="工单编号" prop="issueId" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('issuerName')" label="发布者名称" prop="issuerName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issuePhoto')" label="问题照片" prop="issuePhoto" :show-overflow-tooltip="true" width="120">
        <template slot-scope="scope">
          <el-button type="text" :style="{ color: scope.row.issuePhoto ? '' : 'gray' }" @click="scope.row.issuePhoto ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + scope.row.issuePhoto) : null">
            {{ scope.row.issuePhoto ? '查看图片' : '暂无' }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('issuerEmail')" label="发布者邮箱" prop="issuerEmail" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issueDetails')" label="问题详情" prop="issueDetails" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('classroom')" label="教室" prop="classroom" :show-overflow-tooltip="true" width="100" sortable />
      <el-table-column v-if="visibleColumns.includes('floor')" label="楼层" prop="floor" :show-overflow-tooltip="true" width="80" sortable />
      <!-- <el-table-column v-if="visibleColumns.includes('maintenanceType')" label="维修类型" prop="maintenanceType" :show-overflow-tooltip="true" width="120" sortable /> -->
      <el-table-column v-if="visibleColumns.includes('maintenanceType')" label="维修类型" align="center" prop="maintenanceType" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_maintenance_type" :value="scope.row.maintenanceType"/>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('urgencyLevel')" label="紧急程度" align="center" prop="urgencyLevel" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_urgency_level" :value="scope.row.urgencyLevel"/>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('issuerPhone')" label="发布者电话" prop="issuerPhone" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuyId')" label="维修人员ID" prop="facilityGuyId" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('date')" label="日期" align="center" prop="date" width="180" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.date) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('facilityGuysEmail')" label="维修人员邮箱" prop="facilityGuysEmail" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuysName')" label="维修人员姓名" prop="facilityGuysName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuyMobile')" label="维修人员电话" prop="facilityGuyMobile" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuySupervisor')" label="主管邮箱" prop="facilityGuySupervisor" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('resultImgPath')" label="处理图片" prop="resultImgPath" :show-overflow-tooltip="true" width="120">
        <template slot-scope="scope">
          <el-button type="text" :style="{ color: scope.row.resultImgPath ? '' : 'gray' }" @click="scope.row.resultImgPath ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + scope.row.resultImgPath) : null">
            {{ scope.row.resultImgPath ? '查看图片' : '暂无' }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('resultMessage')" label="结果信息" prop="resultMessage" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('processingStatus')" label="处理状态" align="center" prop="processingStatus" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_maintenance_status" :value="scope.row.processingStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['maintenanceOrder:all:edit']">
                <i class="el-icon-edit"></i> 修改
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['maintenanceOrder:all:remove']">
                <i class="el-icon-delete"></i> 删除
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleAssign(scope.row)" v-hasPermi="['maintenanceOrder:all:assign']" :disabled="scope.row.processingStatus !== 'Undistributed' && scope.row.processingStatus !== 'On Process'">
                <i class="el-icon-s-tools"></i> 分配
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleSubmitOrUpdate(scope.row)" v-hasPermi="['maintenanceOrder:all:submitOrUpdate']" :disabled="scope.row.processingStatus === 'Undistributed'">
                <i class="el-icon-upload"></i> {{ scope.row.processingStatus === 'On Process' ? '提交' : '更新' }}
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

    <!-- 添加或修改维护工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="工单编号" prop="issueId">
          <el-input v-model="form.issueId" placeholder="工单编号" disabled />
        </el-form-item>
        <el-form-item label="发布者名称" prop="issuerName">
          <el-input v-model="form.issuerName" placeholder="请输入发布者名称" />
        </el-form-item>
        <el-form-item label="发布者邮箱" prop="issuerEmail">
          <el-input v-model="form.issuerEmail" placeholder="请输入发布者邮箱" />
        </el-form-item>
        <el-form-item label="发布者电话" prop="issuerPhone">
          <el-input v-model="form.issuerPhone" placeholder="请输入发布者电话" />
        </el-form-item>
        <el-form-item label="问题详情" prop="issueDetails">
          <el-input v-model="form.issueDetails" placeholder="请输入问题详情" />
        </el-form-item>
        <el-form-item label="紧急程度" prop="urgencyLevel">
          <el-radio-group v-model="form.urgencyLevel">
            <el-radio
              v-for="dict in dict.type.sys_urgency_level"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="维修类型" prop="maintenanceType">
          <el-select v-model="form.maintenanceType" placeholder="请选择维修类型">
            <el-option
              v-for="dict in dict.type.sys_maintenance_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="问题照片" prop="issuePhoto">
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
        <el-form-item label="楼层" prop="floor">
          <el-input v-model="form.floor" placeholder="请输入楼层" />
        </el-form-item>
        <el-form-item label="教室" prop="classroom">
          <el-input v-model="form.classroom" placeholder="请输入教室" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 分配维护工单对话框 -->
    <el-dialog :title="assignTitle" :visible.sync="assignOpen" :width="isMobile ? '100%' : '600px'" append-to-body>
      <el-form ref="assignForm" :model="assignForm" :rules="assignRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="维修人员姓名" prop="facilityGuysName">
              <el-select v-model="assignForm.facilityGuysName" placeholder="请选择维修人员姓名" @change="handleUserChange">
                <el-option
                  v-for="user in deptUsers"
                  :key="user.userId"
                  :label="user.userName"
                  :value="user.userName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="assignForm.facilityGuysName">
            <el-divider></el-divider>
          </el-col>
          <el-col :span="24" v-if="assignForm.facilityGuysName">
            <el-form-item label="维修人员电话" prop="facilityGuyMobile">
              <el-input v-model="assignForm.facilityGuyMobile" placeholder="请输入维修人员电话" />
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="assignForm.facilityGuysName">
            <el-form-item label="维修人员邮箱" prop="facilityGuysEmail">
              <el-input v-model="assignForm.facilityGuysEmail" placeholder="请输入维修人员邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button 
          type="primary" 
          :disabled="!assignForm.facilityGuysName || (assignForm.facilityGuysName === originalFacilityGuysName && assignForm.facilityGuyId)" 
          @click="submitAssignForm">
          {{ isUnassigned ? '指派' : '改派' }}
        </el-button>
        <el-button @click="cancelAssign">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="字段设置" :visible.sync="showColumnSettings" :width="isMobile ? '100%' : '300px'">
      <el-checkbox-group v-model="visibleColumns">
        <el-row>
          <el-col :span="12"><el-checkbox label="issueId">工单编号</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerName">发布者名称</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuePhoto">问题照片</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerEmail">发布者邮箱</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issueDetails">问题详情</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="classroom">教室</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="floor">楼层</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="maintenanceType">维修类型</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="urgencyLevel">紧急程度</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerPhone">发布者电话</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuyId">维修人员ID</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="date">日期</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuysEmail">维修人员邮箱</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuysName">维修人员姓名</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuyMobile">维修人员电话</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuySupervisor">主管邮箱</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="resultImgPath">处理图片</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="resultMessage">结果信息</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="processingStatus">处理状态</el-checkbox></el-col>
        </el-row>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showColumnSettings = false">关闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="图片查看" :visible.sync="showImageDialog" :width="isMobile ? '100%' : '600px'">
      <img :src="imageSrc" alt="图片" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showImageDialog = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 提交或更新维护工单对话框 -->
    <el-dialog :title="submitOrUpdateTitle" :visible.sync="submitOrUpdateOpen" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="submitOrUpdateForm" :model="submitOrUpdateForm" label-width="100px">
        <el-form-item label="维修人员姓名" prop="facilityGuysName" v-if="submitOrUpdateTitle === '更新维护工单'">
          <el-select v-model="submitOrUpdateForm.facilityGuysName" placeholder="请选择维修人员姓名" @change="handleUserChange">
            <el-option
              v-for="user in deptUsers"
              :key="user.userId"
              :label="user.userName"
              :value="user.userName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="维修人员电话" prop="facilityGuyMobile" v-if="submitOrUpdateTitle === '更新维护工单'">
          <el-input v-model="submitOrUpdateForm.facilityGuyMobile" placeholder="请输入维修人员电话" />
        </el-form-item>
        <el-form-item label="维修人员邮箱" prop="facilityGuysEmail" v-if="submitOrUpdateTitle === '更新维护工单'">
          <el-input v-model="submitOrUpdateForm.facilityGuysEmail" placeholder="请输入维修人员邮箱" />
        </el-form-item>
        <el-form-item label="结果信息" prop="resultMessage">
          <el-input v-model="submitOrUpdateForm.resultMessage" placeholder="请输入结果信息" />
        </el-form-item>
        <el-form-item label="处理图片" prop="resultImgPath">
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
        <el-form-item label="处理状态" prop="processingStatus">
          <el-select v-model="submitOrUpdateForm.processingStatus" placeholder="请选择处理状态">
            <el-option
              v-for="dict in dict.type.sys_maintenance_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
              v-if="submitOrUpdateTitle === '提交维护工单' ? (dict.value === 'Resolved' || dict.value === 'Can not Resolve') : true"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :disabled="!isFormChanged || !submitOrUpdateForm.processingStatus || (submitOrUpdateTitle === '提交维护工单' && submitOrUpdateForm.processingStatus === 'On Process')" @click="submitOrUpdateFormAction">确 定</el-button>
        <el-button @click="cancelSubmitOrUpdate">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMaintenanceOrder, getMaintenanceOrder, delMaintenanceOrder, addMaintenanceOrder, updateMaintenanceOrder, exportMaintenanceOrder, assignMaintenanceOrder, uploadImage, closeMaintenanceOrder } from "@/api/maintenanceOrder/all/maintenanceOrderAll";
import { getUserProfile, listUserByDeptId, getUser } from "@/api/maintenanceOrder/my/maintenanceOrderMy";

export default {
  name: "MaintenanceOrder",
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
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        issuerName: undefined,
        issueDetails: undefined,
        urgencyLevel: undefined,
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
        urgencyLevel: undefined,
        maintenanceType: undefined,
        date: new Date(),
        issuePhoto: undefined,
        floor: undefined,
        classroom: undefined,
        processingStatus: 'Undistributed'
      },
      // 表单校验
      rules: {
        issuerName: [
          { required: true, message: "发布者名称不能为空", trigger: "blur" }
        ],
        issuerEmail: [
          { required: true, message: "发布者邮箱不能为空", trigger: "blur" }
        ],
        issuerPhone: [
          { required: true, message: "发布者电话不能为空", trigger: "blur" }
        ],
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
      showImageDialog: false,
      imageSrc: '',
      showColumnSettings: false,
      visibleColumns: ['issuerName', 'issuePhoto', 'issueDetails', 'classroom', 'floor', 'maintenanceType', 'urgencyLevel', 'date', 'facilityGuysName', 'resultImgPath', 'resultMessage', 'processingStatus'],
      sortParams: {
        prop: 'date',
        order: 'descending'
      },
      showAdvancedSearch: false,
      assignOpen: false,
      assignTitle: "分配维护工单",
      assignForm: {
        issueId: undefined,
        facilityGuyId: undefined,
        facilityGuysName: undefined,
        facilityGuyMobile: undefined,
        facilityGuysEmail: undefined
      },
      assignRules: {
        facilityGuyId: [
          { required: true, message: "维修人员ID不能为空", trigger: "blur" }
        ],
        facilityGuysName: [
          { required: true, message: "维修人员姓名不能为空", trigger: "blur" }
        ],
        facilityGuysEmail: [
          { required: true, message: "维修人员邮箱不能为空", trigger: "blur" }
        ]
      },
      isUnassigned: true, // 是否为未分配工单
      originalFacilityGuysName: '', // 原始维修人员姓名
      deptUsers: [], // 部门用户列表
      fileList: [],
      previewVisible: false,
      previewImage: '',
      submitOrUpdateOpen: false,
      submitOrUpdateTitle: "",
      submitOrUpdateForm: {},
      originalSubmitOrUpdateForm: {},
      startDate: null,
      endDate: null
    };
  },
  computed: {
    isMobile() {
      return window.innerWidth <= 768;
    },
    isFormChanged() {
      return JSON.stringify(this.submitOrUpdateForm) !== JSON.stringify(this.originalSubmitOrUpdateForm);
    }
  },
  created() {
    this.getList();
    this.getDeptUsers(); // 获取部门id为200的用户信息
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
      listMaintenanceOrder(params).then(response => {
          this.maintenanceOrderList = response.rows.map(item => ({ ...item, showDetails: false }));
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 获取当前用户信息
    getUserProfile() {
      getUserProfile().then(response => {
        const user = response.data;
        if (this.title === "添加维护工单") {
          this.form.issuerName = user.userName;
          this.form.issuerEmail = user.email;
          this.form.issuerPhone = user.phonenumber;
        }
      });
    },
    // 获取部门id为200的用户信息 /**！！！！后续完善逻辑 */
    getDeptUsers() {
      listUserByDeptId(200).then(response => {
        this.deptUsers = response.data;
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
        issueId: this.generateIssueId(),
        issuerName: undefined,
        issuerEmail: undefined,
        issuerPhone: undefined,
        issueDetails: undefined,
        urgencyLevel: undefined,
        maintenanceType: undefined,
        date: new Date(),
        issuePhoto: undefined,
        floor: undefined,
        classroom: undefined,
        processingStatus: 'Undistributed'
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
      this.dateRange = [];
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        issuerName: undefined,
        issueDetails: undefined,
        urgencyLevel: undefined,
        processingStatus: undefined,
        issuerEmail: undefined,
        classroom: undefined,
        floor: undefined,
        maintenanceType: undefined,
        issuerPhone: undefined,
        facilityGuyId: undefined,
        facilityGuysEmail: undefined,
        facilityGuysName: undefined,
        facilityGuyMobile: undefined,
        facilityGuySupervisor: undefined,
        resultMessage: undefined,
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
      this.title = "添加维护工单";
      this.getUserProfile(); // 获取当前用户信息
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const issueId = row.issueId || this.ids;
      getMaintenanceOrder(issueId).then(response => {
        this.form = response.data;
        if (this.form.issuePhoto) {
          const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${this.form.issuePhoto}`;
          this.fileList = [{ name: this.form.issuePhoto, url: fullUrl }];
        } else {
          this.fileList = [];
        }
        this.open = true;
        this.title = "修改维护工单";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      if (this.fileList.some(file => file.status !== 'success')) {
        this.$message.error('请等待图片上传完成');
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.fileList.length === 0) {
            this.form.issuePhoto = '';
          }
          if (this.isUpdate()) {
            updateMaintenanceOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMaintenanceOrder(this.form).then(response => {
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
      return this.form.issueId && this.maintenanceOrderList.some(order => order.issueId === this.form.issueId);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const issueIds = row.issueId || this.ids;
      this.$modal.confirm('是否确认删除工单编号为"' + issueIds + '"的数据项？').then(function() {
        return delMaintenanceOrder(issueIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('maintenanceOrder/all/export', {
        ...this.queryParams
      }, `maintenanceOrder_all_${new Date().getTime()}.xlsx`)
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
    /** 分配按钮操作 */
    handleAssign(row) {
      const issueId = row.issueId;
      getMaintenanceOrder(issueId).then(response => {
        this.assignForm = { ...response.data, issueId };
        this.isUnassigned = !this.assignForm.facilityGuyId;
        if (this.assignForm.facilityGuyId) {
          getUser(this.assignForm.facilityGuyId).then(userResponse => {
            const user = userResponse.data;
            this.assignForm.facilityGuysName = user.userName;
            this.assignForm.facilityGuyMobile = user.phonenumber;
            this.assignForm.facilityGuysEmail = user.email;
            this.originalFacilityGuysName = user.userName;
          });
        } else {
          this.originalFacilityGuysName = '';
        }
        this.assignOpen = true;
      });
    },
    /** 提交分配表单 */
    submitAssignForm() {
      this.$refs["assignForm"].validate(valid => {
        if (valid) {
          assignMaintenanceOrder(this.assignForm).then(response => {
            this.$modal.msgSuccess("分配成功");
            this.assignOpen = false;
            this.getList();
          });
        }
      });
    },
    // 取消分配
    cancelAssign() {
      this.assignOpen = false;
      this.resetAssignForm();
    },
    // 重置分配表单
    resetAssignForm() {
      this.assignForm = {
        issueId: undefined,
        facilityGuyId: undefined,
        facilityGuysName: undefined,
        facilityGuyMobile: undefined,
        facilityGuysEmail: undefined
      };
      this.resetForm("assignForm");
    },
    // 选择维修人员后填充相应的信息
    handleUserChange(userName) {
      const selectedUser = this.deptUsers.find(user => user.userName === userName);
      if (selectedUser) {
        this.assignForm.facilityGuyId = selectedUser.userId;
        this.assignForm.facilityGuyMobile = selectedUser.phonenumber;
        this.assignForm.facilityGuysEmail = selectedUser.email;
        this.submitOrUpdateForm.facilityGuyId = selectedUser.userId;
        this.submitOrUpdateForm.facilityGuyMobile = selectedUser.phonenumber;
        this.submitOrUpdateForm.facilityGuysEmail = selectedUser.email;
      }
    },
    uploadImage({ file }) {
      const formData = new FormData();
      formData.append('file', file);
      uploadImage(formData).then(response => {
        const relativePath = response.data;
        const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${relativePath}`;
        this.form.issuePhoto = relativePath;
        this.submitOrUpdateForm.resultImgPath = relativePath;
        this.fileList.push({ name: file.name, url: fullUrl });
      }).catch(error => {
        this.$message.error('上传失败');
      });
    },
    handlePreview(file) {
      this.previewImage = file.url;
      this.previewVisible = true;
    },
    handleRemove(file) {
      
      this.fileList = this.fileList.filter(item => item.url !== file.url);
      this.fileList = this.fileList.filter(item => item.name !== file.name);
      if (this.form.issuePhoto === file.name || this.form.issuePhoto === file.url) {
        this.form.issuePhoto = '';
      }
      if (this.submitOrUpdateForm.resultImgPath === file.name || this.submitOrUpdateForm.resultImgPath === file.url) {
        this.submitOrUpdateForm.resultImgPath = '';
      }
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
      this.submitOrUpdateTitle = row.processingStatus === 'On Process' ? '提交维护工单' : '更新维护工单';
      this.submitOrUpdateOpen = true;
    },
    /** 提交或更新表单操作 */
    submitOrUpdateFormAction() {
      if (this.fileList.some(file => file.status !== 'success')) {
        this.$message.error('请等待图片上传完成');
        return;
      }
      if (this.fileList.length === 0) {
        this.submitOrUpdateForm.resultImgPath = '';
      } 
      if (this.submitOrUpdateTitle === '提交维护工单') {
        this.remindIssuer();
        this.updateMaintenanceOrderData();
      } else {
        this.$confirm('是否提醒发布人？', '提示', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          this.remindIssuer();
          this.updateMaintenanceOrderData();
        }).catch(() => {
          this.updateMaintenanceOrderData();
        });
      }
    },
    updateMaintenanceOrderData() {
      this.$refs["submitOrUpdateForm"].validate(valid => {
        if (valid) {
          if (this.fileList.length === 0) {
            this.submitOrUpdateForm.resultImgPath = '';
          } 
          updateMaintenanceOrder(this.submitOrUpdateForm).then(response => {
            this.$modal.msgSuccess("操作成功");
            this.submitOrUpdateOpen = false;
            this.getList();
          });
        }
      });
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
</style>
