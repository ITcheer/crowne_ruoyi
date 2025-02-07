<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <!-- 搜索表单项 -->
      <el-form-item :label="$t('schoolOrder.issuerName')" prop="issuerName">
        <el-input
          v-model="queryParams.issuerName"
          :placeholder="$t('schoolOrder.enterIssuerName')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
        <el-button type="primary" icon="el-icon-search" size="mini" @click="showAdvancedSearch = true">{{ $t('schoolOrder.advancedSearch') }}</el-button>
      </el-form-item>
    </el-form>

    <el-dialog :title="$t('schoolOrder.advancedSearch')" :visible.sync="showAdvancedSearch" :width="isMobile ? '100%' : '800px'">
      <el-form :model="queryParams" ref="advancedSearchForm" size="small" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="$t('schoolOrder.issuerName')" prop="issuerName">
              <el-input v-model="queryParams.issuerName" :placeholder="$t('schoolOrder.enterIssuerName')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.issueDetails')" prop="issueDetails">
              <el-input v-model="queryParams.issueDetails" :placeholder="$t('schoolOrder.enterIssueDetails')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.urgencyLevel')" prop="urgencyLevel">
              <el-select v-model="queryParams.urgencyLevel" :placeholder="$t('schoolOrder.urgencyLevel')" clearable>
                <el-option
                  v-for="dict in dict.type.sys_urgency_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.processingStatus')" prop="processingStatus">
              <el-select v-model="queryParams.processingStatus" :placeholder="$t('schoolOrder.processingStatus')" clearable>
                <el-option
                  v-for="dict in dict.type.sys_maintenance_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.date')">
              <el-date-picker
                v-model="dateRange"
                style="width: 100%"
                value-format="yyyyMMdd"
                type="daterange"
                range-separator="-"
                :start-placeholder="$t('schoolOrder.startDate')"
                :end-placeholder="$t('schoolOrder.endDate')"
              ></el-date-picker>
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.issuerEmail')" prop="issuerEmail">
              <el-input v-model="queryParams.issuerEmail" :placeholder="$t('schoolOrder.enterIssuerEmail')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.classroom')" prop="classroom">
              <el-input v-model="queryParams.classroom" :placeholder="$t('schoolOrder.enterClassroom')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.floor')" prop="floor">
              <el-input v-model="queryParams.floor" :placeholder="$t('schoolOrder.enterFloor')" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('schoolOrder.issuerPhone')" prop="issuerPhone">
              <el-input v-model="queryParams.issuerPhone" :placeholder="$t('schoolOrder.enterIssuerPhone')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.facilityGuyId')" prop="facilityGuyId">
              <el-input v-model="queryParams.facilityGuyId" :placeholder="$t('schoolOrder.enterFacilityGuyId')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.facilityGuysEmail')" prop="facilityGuysEmail">
              <el-input v-model="queryParams.facilityGuysEmail" :placeholder="$t('schoolOrder.enterFacilityGuysEmail')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.facilityGuysName')" prop="facilityGuysName">
              <el-input v-model="queryParams.facilityGuysName" :placeholder="$t('schoolOrder.enterFacilityGuysName')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.facilityGuyMobile')" prop="facilityGuyMobile">
              <el-input v-model="queryParams.facilityGuyMobile" :placeholder="$t('schoolOrder.enterFacilityGuyMobile')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.facilityGuySupervisor')" prop="facilityGuySupervisor">
              <el-input v-model="queryParams.facilityGuySupervisor" :placeholder="$t('schoolOrder.enterFacilityGuySupervisor')" clearable />
            </el-form-item>
            <el-form-item :label="$t('schoolOrder.resultMessage')" prop="resultMessage">
              <el-input v-model="queryParams.resultMessage" :placeholder="$t('schoolOrder.enterResultMessage')" clearable />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleQuery">{{ $t('schoolOrder.search') }}</el-button>
        <el-button @click="showAdvancedSearch = false">{{ $t('schoolOrder.close') }}</el-button>
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
          v-hasPermi="['schoolOrder:all:add']"
        >{{ $t('schoolOrder.add') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['schoolOrder:all:edit']"
        >{{ $t('schoolOrder.edit') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['schoolOrder:all:remove']"
        >{{ $t('schoolOrder.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['schoolOrder:all:export']"
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row v-if="isMobile" :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in facilityOrderList" :key="item.issueId">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <p><strong>{{ $t('schoolOrder.issuerName') }}:</strong> {{ item.issuerName }}</p>
            <p><strong>{{ $t('schoolOrder.issueDetails') }}:</strong> {{ item.issueDetails }}</p>
            <p><strong>{{ $t('schoolOrder.appointmentTime') }}:</strong> {{ parseTime(item.appointmentTime) }}</p>
            <p>
              <strong>{{ $t('schoolOrder.classroom') }}:</strong> {{ item.classroom }} 
              <strong>{{ $t('schoolOrder.floor') }}:</strong> {{ item.floor }}
            </p>
            <p class="inline-fields">
              <strong>{{ $t('schoolOrder.urgencyLevel') }}:</strong> <dict-tag :options="dict.type.sys_urgency_level" :value="item.urgencyLevel"/>
              <strong>{{ $t('schoolOrder.processingStatus') }}:</strong> <dict-tag :options="dict.type.sys_maintenance_status" :value="item.processingStatus"/>
            </p>
            <el-button class="expand-button" type="text" @click="item.showDetails = !item.showDetails">
              <i :class="item.showDetails ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
            </el-button>
            <div v-if="item.showDetails" class="button-container">
              <el-button type="success" size="mini" @click="handleUpdate(item)" v-hasPermi="['facilityOrder:all:edit']">{{ $t('schoolOrder.edit') }}</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(item)" v-hasPermi="['facilityOrder:all:remove']">{{ $t('schoolOrder.delete') }}</el-button>
              <el-button type="primary" size="mini" @click="handleAssign(item)" v-hasPermi="['facilityOrder:all:assign']" :disabled="item.processingStatus !== 'Undistributed' && item.processingStatus !== 'On Process'">{{ $t('schoolOrder.assign') }}</el-button>
              <el-button type="primary" size="mini" @click="handleSubmitOrUpdate(item)" v-hasPermi="['facilityOrder:all:submitOrUpdate']" :disabled="item.processingStatus === 'Undistributed'">{{ item.processingStatus === 'On Process' ? $t('schoolOrder.submit') : $t('schoolOrder.update') }}</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-table v-else v-loading="loading" :data="facilityOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('issueId')" :label="$t('schoolOrder.issueId')" prop="issueId" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('issuerName')" :label="$t('schoolOrder.issuerName')" prop="issuerName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('appointmentTime')" :label="$t('schoolOrder.appointmentTime')" prop="appointmentTime" :show-overflow-tooltip="true" width="150" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.appointmentTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('timeSlot')" :label="$t('schoolOrder.timeSlot')" prop="timeSlot" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issueDetails')" :label="$t('schoolOrder.issueDetails')" prop="issueDetails" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('issuerEmail')" :label="$t('schoolOrder.issuerEmail')" prop="issuerEmail" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('classroom')" :label="$t('schoolOrder.classroom')" prop="classroom" :show-overflow-tooltip="true" width="100" sortable />
      <el-table-column v-if="visibleColumns.includes('floor')" :label="$t('schoolOrder.floor')" prop="floor" :show-overflow-tooltip="true" width="80" sortable />
      <el-table-column v-if="visibleColumns.includes('urgencyLevel')" :label="$t('schoolOrder.urgencyLevel')" align="center" prop="urgencyLevel" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_urgency_level || []" :value="scope.row.urgencyLevel"/>
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
          <dict-tag :options="dict.type.sys_maintenance_status || []" :value="scope.row.processingStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('schoolOrder.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['facilityOrder:all:edit']">
                <i class="el-icon-edit"></i> {{ $t('schoolOrder.edit') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['facilityOrder:all:remove']">
                <i class="el-icon-delete"></i> {{ $t('schoolOrder.delete') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleAssign(scope.row)" v-hasPermi="['facilityOrder:all:assign']" :disabled="scope.row.processingStatus !== 'Undistributed' && scope.row.processingStatus !== 'On Process'">
                <i class="el-icon-s-tools"></i> {{ $t('schoolOrder.assign') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleSubmitOrUpdate(scope.row)" v-hasPermi="['facilityOrder:all:submitOrUpdate']" :disabled="scope.row.processingStatus === 'Undistributed'">
                <i class="el-icon-upload"></i> {{ scope.row.processingStatus === 'On Process' ? $t('schoolOrder.submit') : $t('schoolOrder.update') }}
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

    <!-- 添加或修改校工订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('schoolOrder.issueId')" prop="issueId">
          <el-input v-model="form.issueId" :placeholder="$t('schoolOrder.issueId')" disabled />
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.issuerName')" prop="issuerName">
          <el-input v-model="form.issuerName" :placeholder="$t('schoolOrder.enterIssuerName')" />
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.issuerEmail')" prop="issuerEmail">
          <el-input v-model="form.issuerEmail" :placeholder="$t('schoolOrder.enterIssuerEmail')" />
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.issuerPhone')" prop="issuerPhone">
          <el-input v-model="form.issuerPhone" :placeholder="$t('schoolOrder.enterIssuerPhone')" />
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.urgencyLevel')" prop="urgencyLevel">
          <el-radio-group v-model="form.urgencyLevel">
            <el-radio
              v-for="dict in dict.type.sys_urgency_level"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.floor')" prop="floor">
          <el-input v-model="form.floor" :placeholder="$t('schoolOrder.enterFloor')" />
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.classroom')" prop="classroom">
          <el-input v-model="form.classroom" :placeholder="$t('schoolOrder.enterClassroom')" />
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.appointmentTime')" prop="appointmentTime">
          <el-date-picker
            v-model="form.appointmentTime"
            style="width: 100%"
            value-format="yyyy-MM-dd"
            type="date"
            :placeholder="$t('schoolOrder.selectAppointmentTime')"
          ></el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.timeSlot')" prop="timeSlot">
          <el-select v-model="form.timeSlot" :placeholder="$t('schoolOrder.selectTimeSlot')" clearable>
            <el-option label="08:00-10:00" value="08:00-10:00"></el-option>
            <el-option label="10:00-12:00" value="10:00-12:00"></el-option>
            <el-option label="14:00-16:00" value="14:00-16:00"></el-option>
            <el-option label="16:00-18:00" value="16:00-18:00"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.issueDetails')" prop="issueDetails">
          <el-input v-model="form.issueDetails" :placeholder="$t('schoolOrder.enterIssueDetails')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('schoolOrder.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('schoolOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 提交或更新校工订单对话框 -->
    <el-dialog :title="submitOrUpdateTitle" :visible.sync="submitOrUpdateOpen" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="submitOrUpdateForm" :model="submitOrUpdateForm" label-width="100px">
        <el-form-item :label="$t('schoolOrder.facilityGuysName')" prop="facilityGuysName" v-if="submitOrUpdateTitle === '更新校工订单'">
          <el-select v-model="submitOrUpdateForm.facilityGuysName" :placeholder="$t('schoolOrder.selectFacilityGuysName')" @change="handleUserChange">
            <el-option
              v-for="user in deptUsers"
              :key="user.userId"
              :label="user.userName"
              :value="user.userName"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.facilityGuyMobile')" prop="facilityGuyMobile" v-if="submitOrUpdateTitle === '更新校工订单'">
          <el-input v-model="submitOrUpdateForm.facilityGuyMobile" :placeholder="$t('schoolOrder.enterFacilityGuyMobile')" />
        </el-form-item>
        <el-form-item :label="$t('schoolOrder.facilityGuysEmail')" prop="facilityGuysEmail" v-if="submitOrUpdateTitle === '更新校工订单'">
          <el-input v-model="submitOrUpdateForm.facilityGuysEmail" :placeholder="$t('schoolOrder.enterFacilityGuysEmail')" />
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
              v-if="submitOrUpdateTitle === '提交校工订单' ? (dict.value === 'Resolved' || dict.value === 'Can not Resolve') : true"
            />
          </el-select>
        </el-form-item>
        
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :disabled="!isFormChanged || !submitOrUpdateForm.processingStatus || (submitOrUpdateTitle === '提交校工订单' && submitOrUpdateForm.processingStatus === 'On Process')" @click="submitOrUpdateFormAction">{{ $t('schoolOrder.confirm') }}</el-button>
        <el-button @click="cancelSubmitOrUpdate">{{ $t('schoolOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 分配校工订单对话框 -->
    <el-dialog :title="assignTitle" :visible.sync="assignOpen" :width="isMobile ? '100%' : '600px'" append-to-body>
      <el-form ref="assignForm" :model="assignForm" :rules="assignRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item :label="$t('schoolOrder.facilityGuysName')" prop="facilityGuysName">
              <el-select v-model="assignForm.facilityGuysName" :placeholder="$t('schoolOrder.selectFacilityGuysName')" @change="handleUserChange">
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
            <el-form-item :label="$t('schoolOrder.facilityGuyMobile')" prop="facilityGuyMobile">
              <el-input v-model="assignForm.facilityGuyMobile" :placeholder="$t('schoolOrder.enterFacilityGuyMobile')" />
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="assignForm.facilityGuysName">
            <el-form-item :label="$t('schoolOrder.facilityGuysEmail')" prop="facilityGuysEmail">
              <el-input v-model="assignForm.facilityGuysEmail" :placeholder="$t('schoolOrder.enterFacilityGuysEmail')" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button 
          type="primary" 
          :disabled="!assignForm.facilityGuysName || (assignForm.facilityGuysName === originalFacilityGuysName && assignForm.facilityGuyId)" 
          @click="submitAssignForm">
          {{ isUnassigned ? $t('schoolOrder.assign') : $t('schoolOrder.reassign') }}
        </el-button>
        <el-button @click="cancelAssign">{{ $t('schoolOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 字段设置对话框 -->
    <el-dialog :title="$t('schoolOrder.columnSettings')" :visible.sync="showColumnSettings" :width="isMobile ? '100%' : '300px'">
      <el-checkbox-group v-model="visibleColumns">
        <el-row>
          <el-col :span="12"><el-checkbox label="issueId">{{ $t('schoolOrder.issueId') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerName">{{ $t('schoolOrder.issuerName') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerEmail">{{ $t('schoolOrder.issuerEmail') }}</el-checkbox></el-col>
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
          <el-col :span="12"><el-checkbox label="issueDetails">{{ $t('schoolOrder.issueDetails') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="appointmentTime">{{ $t('schoolOrder.appointmentTime') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="timeSlot">{{ $t('schoolOrder.timeSlot') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="processingStatus">{{ $t('schoolOrder.processingStatus') }}</el-checkbox></el-col>
        </el-row>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showColumnSettings = false">{{ $t('schoolOrder.close') }}</el-button>
      </div>
    </el-dialog>

    <!-- 图片查看对话框 -->
    <el-dialog :title="$t('schoolOrder.viewImage')" :visible.sync="showImageDialog" :width="isMobile ? '100%' : '600px'">
      <img :src="imageSrc" :alt="$t('schoolOrder.image')" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showImageDialog = false">{{ $t('schoolOrder.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFacilityOrder, getFacilityOrder, delFacilityOrder, addFacilityOrder, updateFacilityOrder, exportFacilityOrder, uploadImage, assignFacilityOrder } from "@/api/facilityOrder/all/facilityOrderAll";
import { getUserProfile, listUserByDeptId } from "@/api/maintenanceOrder/my/maintenanceOrderMy";
import { sendCompletionEmail, sendIncompleteEmail } from "@/api/facilityOrder/my/facilityOrderMy";

export default {
  name: "FacilityOrder",
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
      // 校工订单表格数据
      facilityOrderList: [],
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
        urgencyLevel: undefined,
        processingStatus: undefined,
        issueDetails: undefined,
        sortField: '',
        sortOrder: ''
      },
      // 表单参数
      form: {
        issueId: this.generateIssueId(),
        issuerName: undefined,
        issuerEmail: undefined,
        issuerPhone: undefined,
        urgencyLevel: undefined,
        date: new Date(),
        floor: undefined,
        classroom: undefined,
        processingStatus: 'Undistributed',
        appointmentTime: undefined,
        timeSlot: undefined,
        issueDetails: undefined
      },
      // 表单校验
      rules: {
        issuerName: [
          { required: true, message: this.$t('schoolOrder.enterIssuerName'), trigger: "blur" }
        ],
        issuerEmail: [
          { required: true, message: this.$t('schoolOrder.enterIssuerEmail'), trigger: "blur" }
        ],
        issuerPhone: [
          { required: true, message: this.$t('schoolOrder.enterIssuerPhone'), trigger: "blur" }
        ],
        urgencyLevel: [
          { required: true, message: this.$t('schoolOrder.urgencyLevel'), trigger: "blur" }
        ],
        floor: [
          { required: true, message: this.$t('schoolOrder.enterFloor'), trigger: "blur" }
        ],
        classroom: [
          { required: true, message: this.$t('schoolOrder.enterClassroom'), trigger: "blur" }
        ],
        appointmentTime: [
          { required: true, message: this.$t('schoolOrder.selectAppointmentTime'), trigger: "blur" }
        ],
        timeSlot: [
          { required: true, message: this.$t('schoolOrder.selectTimeSlot'), trigger: "blur" }
        ]
      },
      showColumnSettings: false,
      visibleColumns: ['issuerName', 'classroom', 'floor', 'urgencyLevel', 'date', 'facilityGuysName', 'resultMessage', 'issueDetails', 'appointmentTime', 'timeSlot', 'processingStatus'],
      showAdvancedSearch: false,
      submitOrUpdateOpen: false,
      submitOrUpdateTitle: "",
      submitOrUpdateForm: {},
      originalSubmitOrUpdateForm: {},
      deptUsers: [], // 部门用户列表
      fileList: [],
      previewVisible: false,
      previewImage: '',
      showImageDialog: false,
      imageSrc: '',
      assignOpen: false,
      assignTitle: "分配校工订单",
      assignForm: {
        issueId: undefined,
        facilityGuyId: undefined,
        facilityGuysName: undefined,
        facilityGuyMobile: undefined,
        facilityGuysEmail: undefined
      },
      assignRules: {
        facilityGuyId: [
          { required: true, message: this.$t('schoolOrder.enterFacilityGuyId'), trigger: "blur" }
        ],
        facilityGuysName: [
          { required: true, message: this.$t('schoolOrder.enterFacilityGuysName'), trigger: "blur" }
        ],
        facilityGuysEmail: [
          { required: true, message: this.$t('schoolOrder.enterFacilityGuysEmail'), trigger: "blur" }
        ]
      },
      isUnassigned: true, // 是否为未分配工单
      originalFacilityGuysName: '', // 原始校工人员姓名
      startDate: null,
      endDate: null
    };
  },
  created() {
    this.getList();
    this.getDeptUsers(); // 获取部门id为201的用户信息
  },
  computed: {
    isMobile() {
      return window.innerWidth <= 768;
    },
    isFormChanged() {
      return JSON.stringify(this.submitOrUpdateForm) !== JSON.stringify(this.originalSubmitOrUpdateForm);
    }
  },
  methods: {
    /** 查询校工订单列表 */
    getList() {
      this.loading = true;
      const params = {
        ...this.addDateRange(this.queryParams, this.dateRange),
        sortField: this.queryParams.sortField,
        sortOrder: this.queryParams.sortOrder
      };
      listFacilityOrder(params).then(response => {
          this.facilityOrderList = response.rows.map(item => ({ ...item, showDetails: false }));
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
        urgencyLevel: undefined,
        processingStatus: undefined,
        issuerEmail: undefined,
        classroom: undefined,
        floor: undefined,
        issuerPhone: undefined,
        facilityGuyId: undefined,
        facilityGuysEmail: undefined,
        facilityGuysName: undefined,
        facilityGuyMobile: undefined,
        facilityGuySupervisor: undefined,
        resultMessage: undefined,
        appointmentTime: undefined,
        timeSlot: undefined,
        issueDetails: undefined,
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
      this.title = this.$t('schoolOrder.add');
      this.getUserProfile(); // 获取当前用户信息
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const issueId = row.issueId || this.ids;
      getFacilityOrder(issueId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('schoolOrder.edit');
        this.originalForm = { ...this.form }; // 保存原始数据
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isUpdate()) {
            updateFacilityOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('schoolOrder.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addFacilityOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('schoolOrder.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 判断是否为修改操作 */
    isUpdate() {
      return this.form.issueId && this.facilityOrderList.some(order => order.issueId === this.form.issueId);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const issueIds = row.issueId || this.ids;
      this.$modal.confirm(this.$t('schoolOrder.deleteConfirm', { issueIds })).then(() => {
        return delFacilityOrder(issueIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('schoolOrder.deleteSuccess'));
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('facilityOrder/all/export', {
        ...this.queryParams
      }, `facilityOrder_all_${new Date().getTime()}.xlsx`)
    },
    // 表单重置
    reset() {
      this.form = {
        issueId: this.generateIssueId(),
        issuerName: undefined,
        issuerEmail: undefined,
        issuerPhone: undefined,
        urgencyLevel: undefined,
        date: new Date(),
        floor: undefined,
        classroom: undefined,
        processingStatus: 'Undistributed',
        appointmentTime: undefined,
        timeSlot: undefined,
        issueDetails: undefined
      };
      this.resetForm("form");
    },
    generateIssueId() {
      return Math.random().toString().slice(2, 15);
    },
    remindIssuer() {
      // 提醒发布工单人操作
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleSubmitOrUpdate(row) {
      this.resetSubmitOrUpdateForm();
      this.submitOrUpdateForm = { ...row };
      this.originalSubmitOrUpdateForm = { ...row };
      this.submitOrUpdateTitle = row.processingStatus === 'On Process' ? this.$t('schoolOrder.submit') : this.$t('schoolOrder.update');
      this.submitOrUpdateOpen = true;
    },
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
    resetSubmitOrUpdateForm() {
      this.submitOrUpdateForm = {};
      this.originalSubmitOrUpdateForm = {};
      this.resetForm("submitOrUpdateForm");
    },
    uploadImage({ file }) {
      // 提交图片上传
      const formData = new FormData();
      formData.append('file', file.raw);
      uploadImage(formData).then(response => {
        if (response.code === 200) {
          this.submitOrUpdateForm.resultImgPath = response.data;
          this.$message.success(this.$t('schoolOrder.uploadSuccess'));
        } else {
          this.$message.error(this.$t('schoolOrder.uploadFail'));
        }
      });
    },
    handlePreview(file) {
      this.previewImage = file.url;
      this.previewVisible = true;
    },
    handleRemove(file) {
      const index = this.fileList.indexOf(file);
      if (index !== -1) {
        this.fileList.splice(index, 1);
      }
    },
    // 取消提交或更新
    cancelSubmitOrUpdate() {
      this.submitOrUpdateOpen = false;
      this.resetSubmitOrUpdateForm();
    },
    viewImage(src) {
      this.imageSrc = src;
      this.showImageDialog = true;
    },
    getUserProfile() {
      getUserProfile().then(response => {
        const user = response.data;
        if (this.title === this.$t('schoolOrder.add')) {
          this.form.issuerName = user.userName;
          this.form.issuerEmail = user.email;
          this.form.issuerPhone = user.phonenumber;
        }
      });
    },
    handleAssign(row) {
      this.resetAssignForm();
      this.assignForm.issueId = row.issueId;
      this.assignForm.facilityGuyId = row.facilityGuyId;
      this.assignForm.facilityGuysName = row.facilityGuysName;
      this.assignForm.facilityGuyMobile = row.facilityGuyMobile;
      this.assignForm.facilityGuysEmail = row.facilityGuysEmail;
      this.originalFacilityGuysName = row.facilityGuysName;
      this.isUnassigned = !row.facilityGuyId;
      this.assignTitle = this.isUnassigned ? this.$t('schoolOrder.assign') : this.$t('schoolOrder.reassign');
      this.assignOpen = true;
    },
    submitAssignForm() {
      this.$refs["assignForm"].validate(valid => {
        if (valid) {
          assignFacilityOrder(this.assignForm).then(response => {
            this.$modal.msgSuccess(this.$t('schoolOrder.assignSuccess'));
            this.assignOpen = false;
            this.getList();
          });
        }
      });
    },
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
    cancelAssign() {
      this.assignOpen = false;
      this.resetAssignForm();
    },
    handleUserChange(selectedUserName) {
      const selectedUser = this.deptUsers.find(user => user.userName === selectedUserName);
      if (selectedUser) {
        this.assignForm.facilityGuyId = selectedUser.userId;
        this.assignForm.facilityGuyMobile = selectedUser.phonenumber;
        this.assignForm.facilityGuysEmail = selectedUser.email;
      }
    },
    getDeptUsers() {
      listUserByDeptId(201).then(response => {
        this.deptUsers = response.data;
      });
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

