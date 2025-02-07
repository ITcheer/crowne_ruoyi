<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('maintenanceOrder.issuerName')" prop="issuerName">
        <el-input
          v-model="queryParams.issuerName"
          :placeholder="$t('maintenanceOrder.enterIssuerName')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
        <el-button type="primary" icon="el-icon-search" size="mini" @click="showAdvancedSearch = true">{{ $t('maintenanceOrder.advancedSearch') }}</el-button>
      </el-form-item>
    </el-form>

    <el-dialog :title="$t('maintenanceOrder.advancedSearch')" :visible.sync="showAdvancedSearch" :width="isMobile ? '100%' : '800px'">
      <el-form :model="queryParams" ref="advancedSearchForm" size="small" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="$t('maintenanceOrder.issuerName')" prop="issuerName">
              <el-input v-model="queryParams.issuerName" :placeholder="$t('maintenanceOrder.enterIssuerName')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.issueDetails')" prop="issueDetails">
              <el-input v-model="queryParams.issueDetails" :placeholder="$t('maintenanceOrder.enterIssueDetails')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.urgencyLevel')" prop="urgencyLevel">
              <el-select v-model="queryParams.urgencyLevel" :placeholder="$t('maintenanceOrder.urgencyLevel')" clearable>
                <el-option
                  v-for="dict in dict.type.sys_urgency_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.processingStatus')" prop="processingStatus">
              <el-select v-model="queryParams.processingStatus" :placeholder="$t('maintenanceOrder.processingStatus')" clearable>
                <el-option
                  v-for="dict in dict.type.sys_maintenance_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.date')">
              <el-date-picker
                v-model="dateRange"
                style="width: 100%"
                value-format="yyyyMMdd"
                type="daterange"
                range-separator="-"
                :start-placeholder="$t('maintenanceOrder.startDate')"
                :end-placeholder="$t('maintenanceOrder.endDate')"
              ></el-date-picker>
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.issuerEmail')" prop="issuerEmail">
              <el-input v-model="queryParams.issuerEmail" :placeholder="$t('maintenanceOrder.enterIssuerEmail')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.classroom')" prop="classroom">
              <el-input v-model="queryParams.classroom" :placeholder="$t('maintenanceOrder.enterClassroom')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.floor')" prop="floor">
              <el-input v-model="queryParams.floor" :placeholder="$t('maintenanceOrder.enterFloor')" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('maintenanceOrder.maintenanceType')" prop="maintenanceType">
              <el-select v-model="queryParams.maintenanceType" :placeholder="$t('maintenanceOrder.maintenanceType')" clearable>
                <el-option
                  v-for="dict in dict.type.sys_maintenance_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.issuerPhone')" prop="issuerPhone">
              <el-input v-model="queryParams.issuerPhone" :placeholder="$t('maintenanceOrder.enterIssuerPhone')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.facilityGuyId')" prop="facilityGuyId">
              <el-input v-model="queryParams.facilityGuyId" :placeholder="$t('maintenanceOrder.enterFacilityGuyId')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.facilityGuysEmail')" prop="facilityGuysEmail">
              <el-input v-model="queryParams.facilityGuysEmail" :placeholder="$t('maintenanceOrder.enterFacilityGuysEmail')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.facilityGuysName')" prop="facilityGuysName">
              <el-input v-model="queryParams.facilityGuysName" :placeholder="$t('maintenanceOrder.enterFacilityGuysName')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.facilityGuyMobile')" prop="facilityGuyMobile">
              <el-input v-model="queryParams.facilityGuyMobile" :placeholder="$t('maintenanceOrder.enterFacilityGuyMobile')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.facilityGuySupervisor')" prop="facilityGuySupervisor">
              <el-input v-model="queryParams.facilityGuySupervisor" :placeholder="$t('maintenanceOrder.enterFacilityGuySupervisor')" clearable />
            </el-form-item>
            <el-form-item :label="$t('maintenanceOrder.resultMessage')" prop="resultMessage">
              <el-input v-model="queryParams.resultMessage" :placeholder="$t('maintenanceOrder.enterResultMessage')" clearable />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleQuery">{{ $t('maintenanceOrder.search') }}</el-button>
        <el-button @click="showAdvancedSearch = false">{{ $t('maintenanceOrder.close') }}</el-button>
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
        >{{ $t('maintenanceOrder.add') }}</el-button>
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
        >{{ $t('maintenanceOrder.edit') }}</el-button>
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
        >{{ $t('maintenanceOrder.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['maintenanceOrder:all:export']"
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
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
            <el-button class="expand-button" type="text" @click="item.showDetails = !item.showDetails">
              <i :class="item.showDetails ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
            </el-button>
            <div v-if="item.showDetails" class="button-container">
              <el-button type="success" size="mini" @click="handleUpdate(item)" v-hasPermi="['maintenanceOrder:all:edit']">{{ $t('maintenanceOrder.edit') }}</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(item)" v-hasPermi="['maintenanceOrder:all:remove']">{{ $t('maintenanceOrder.delete') }}</el-button>
              <el-button type="primary" size="mini" @click="handleAssign(item)" v-hasPermi="['maintenanceOrder:all:assign']" :disabled="item.processingStatus !== 'Undistributed' && item.processingStatus !== 'On Process'">{{ $t('maintenanceOrder.assign') }}</el-button>
              <el-button type="primary" size="mini" @click="handleSubmitOrUpdate(item)" v-hasPermi="['maintenanceOrder:all:submitOrUpdate']" :disabled="item.processingStatus === 'Undistributed'">{{ item.processingStatus === 'On Process' ? $t('maintenanceOrder.submit') : $t('maintenanceOrder.update') }}</el-button>
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
          <dict-tag :options="dict.type.sys_maintenance_type || []" :value="scope.row.maintenanceType"/>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('urgencyLevel')" :label="$t('maintenanceOrder.urgencyLevel')" align="center" prop="urgencyLevel" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_urgency_level || []" :value="scope.row.urgencyLevel"/>
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
          <dict-tag :options="dict.type.sys_maintenance_status || []" :value="scope.row.processingStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('maintenanceOrder.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['maintenanceOrder:all:edit']">
                <i class="el-icon-edit"></i> {{ $t('maintenanceOrder.edit') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['maintenanceOrder:all:remove']">
                <i class="el-icon-delete"></i> {{ $t('maintenanceOrder.delete') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleAssign(scope.row)" v-hasPermi="['maintenanceOrder:all:assign']" :disabled="scope.row.processingStatus !== 'Undistributed' && scope.row.processingStatus !== 'On Process'">
                <i class="el-icon-s-tools"></i> {{ $t('maintenanceOrder.assign') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleSubmitOrUpdate(scope.row)" v-hasPermi="['maintenanceOrder:all:submitOrUpdate']" :disabled="scope.row.processingStatus === 'Undistributed'">
                <i class="el-icon-upload"></i> {{ scope.row.processingStatus === 'On Process' ? $t('maintenanceOrder.submit') : $t('maintenanceOrder.update') }}
              </el-dropdown-item>
              <!-- <el-dropdown-item @click.native="handleViewLogs(scope.row)" v-hasPermi="['maintenanceOrder:all:viewLogs']">
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

    <!-- 添加或修改维护工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('maintenanceOrder.issueId')" prop="issueId">
          <el-input v-model="form.issueId" :placeholder="$t('maintenanceOrder.issueId')" disabled />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.issuerName')" prop="issuerName">
          <el-input v-model="form.issuerName" :placeholder="$t('maintenanceOrder.enterIssuerName')" />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.issuerEmail')" prop="issuerEmail">
          <el-input v-model="form.issuerEmail" :placeholder="$t('maintenanceOrder.enterIssuerEmail')" />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.issuerPhone')" prop="issuerPhone">
          <el-input v-model="form.issuerPhone" :placeholder="$t('maintenanceOrder.enterIssuerPhone')" />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.issueDetails')" prop="issueDetails">
          <el-input v-model="form.issueDetails" :placeholder="$t('maintenanceOrder.enterIssueDetails')" />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.urgencyLevel')" prop="urgencyLevel">
          <el-radio-group v-model="form.urgencyLevel">
            <el-radio
              v-for="dict in dict.type.sys_urgency_level"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.maintenanceType')" prop="maintenanceType">
          <el-select v-model="form.maintenanceType" :placeholder="$t('maintenanceOrder.maintenanceType')">
            <el-option
              v-for="dict in dict.type.sys_maintenance_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.issuePhoto')" prop="issuePhoto">
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
        <el-form-item :label="$t('maintenanceOrder.floor')" prop="floor">
          <el-input v-model="form.floor" :placeholder="$t('maintenanceOrder.enterFloor')" />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.classroom')" prop="classroom">
          <el-input v-model="form.classroom" :placeholder="$t('maintenanceOrder.enterClassroom')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('maintenanceOrder.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('maintenanceOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 分配维护工单对话框 -->
    <el-dialog :title="assignTitle" :visible.sync="assignOpen" :width="isMobile ? '100%' : '600px'" append-to-body>
      <el-form ref="assignForm" :model="assignForm" :rules="assignRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item :label="$t('maintenanceOrder.facilityGuysName')" prop="facilityGuysName">
              <el-select v-model="assignForm.facilityGuysName" :placeholder="$t('maintenanceOrder.selectFacilityGuysName')" @change="handleUserChange">
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
            <el-form-item :label="$t('maintenanceOrder.facilityGuyMobile')" prop="facilityGuyMobile">
              <el-input v-model="assignForm.facilityGuyMobile" :placeholder="$t('maintenanceOrder.enterFacilityGuyMobile')" />
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="assignForm.facilityGuysName">
            <el-form-item :label="$t('maintenanceOrder.facilityGuysEmail')" prop="facilityGuysEmail">
              <el-input v-model="assignForm.facilityGuysEmail" :placeholder="$t('maintenanceOrder.enterFacilityGuysEmail')" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button 
          type="primary" 
          :disabled="!assignForm.facilityGuysName || (assignForm.facilityGuysName === originalFacilityGuysName && assignForm.facilityGuyId)" 
          @click="submitAssignForm">
          {{ isUnassigned ? $t('maintenanceOrder.assign') : $t('maintenanceOrder.reassign') }}
        </el-button>
        <el-button @click="cancelAssign">{{ $t('maintenanceOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

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

    <el-dialog :title="$t('maintenanceOrder.viewImage')" :visible.sync="showImageDialog" :width="isMobile ? '100%' : '600px'">
      <img :src="imageSrc" :alt="$t('maintenanceOrder.image')" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showImageDialog = false">{{ $t('maintenanceOrder.close') }}</el-button>
      </div>
    </el-dialog>

    <!-- 提交或更新维护工单对话框 -->
    <el-dialog :title="submitOrUpdateTitle" :visible.sync="submitOrUpdateOpen" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="submitOrUpdateForm" :model="submitOrUpdateForm" label-width="100px">
        <el-form-item :label="$t('maintenanceOrder.facilityGuysName')" prop="facilityGuysName" v-if="submitOrUpdateTitle === '更新维护工单'">
          <el-select v-model="submitOrUpdateForm.facilityGuysName" :placeholder="$t('maintenanceOrder.selectFacilityGuysName')" @change="handleUserChange">
            <el-option
              v-for="user in deptUsers"
              :key="user.userId"
              :label="user.userName"
              :value="user.userName"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.facilityGuyMobile')" prop="facilityGuyMobile" v-if="submitOrUpdateTitle === '更新维护工单'">
          <el-input v-model="submitOrUpdateForm.facilityGuyMobile" :placeholder="$t('maintenanceOrder.enterFacilityGuyMobile')" />
        </el-form-item>
        <el-form-item :label="$t('maintenanceOrder.facilityGuysEmail')" prop="facilityGuysEmail" v-if="submitOrUpdateTitle === '更新维护工单'">
          <el-input v-model="submitOrUpdateForm.facilityGuysEmail" :placeholder="$t('maintenanceOrder.enterFacilityGuysEmail')" />
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
              v-if="submitOrUpdateTitle === '提交维护工单' ? (dict.value === 'Resolved' || dict.value === 'Can not Resolve') : true"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :disabled="!isFormChanged || !submitOrUpdateForm.processingStatus || (submitOrUpdateTitle === '提交维护工单' && submitOrUpdateForm.processingStatus === 'On Process')" @click="submitOrUpdateFormAction">{{ $t('maintenanceOrder.confirm') }}</el-button>
        <el-button @click="cancelSubmitOrUpdate">{{ $t('maintenanceOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 查看记录对话框 -->
    <log-dialog v-if="logDialogVisible" :visible.sync="logDialogVisible" :log-list="logList" :issue-id="currentIssueId" @send-message="sendMessage"></log-dialog>
  </div>
</template>

<script>
import { listMaintenanceOrder, getMaintenanceOrder, delMaintenanceOrder, addMaintenanceOrder, updateMaintenanceOrder, exportMaintenanceOrder, assignMaintenanceOrder, uploadImage, closeMaintenanceOrder } from "@/api/maintenanceOrder/all/maintenanceOrderAll";
import { getUserProfile, listUserByDeptId, getUser, listMaintenanceOrderLogs, addMaintenanceOrderLog, sendCompletionEmail, sendIncompleteEmail } from "@/api/maintenanceOrder/my/maintenanceOrderMy";

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
          { required: true, message: this.$t('maintenanceOrder.enterIssuerName'), trigger: "blur" }
        ],
        issuerEmail: [
          { required: true, message: this.$t('maintenanceOrder.enterIssuerEmail'), trigger: "blur" }
        ],
        issuerPhone: [
          { required: true, message: this.$t('maintenanceOrder.enterIssuerPhone'), trigger: "blur" }
        ],
        issueDetails: [
          { required: true, message: this.$t('maintenanceOrder.enterIssueDetails'), trigger: "blur" }
        ],
        urgencyLevel: [
          { required: true, message: this.$t('maintenanceOrder.urgencyLevel'), trigger: "blur" }
        ],
        maintenanceType: [
          { required: true, message: this.$t('maintenanceOrder.maintenanceType'), trigger: "blur" }
        ],
        floor: [
          { required: true, message: this.$t('maintenanceOrder.enterFloor'), trigger: "blur" }
        ],
        classroom: [
          { required: true, message: this.$t('maintenanceOrder.enterClassroom'), trigger: "blur" }
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
      assignTitle: this.$t('maintenanceOrder.assignTitle'),
      assignForm: {
        issueId: undefined,
        facilityGuyId: undefined,
        facilityGuysName: undefined,
        facilityGuyMobile: undefined,
        facilityGuysEmail: undefined
      },
      assignRules: {
        facilityGuyId: [
          { required: true, message: this.$t('maintenanceOrder.enterFacilityGuyId'), trigger: "blur" }
        ],
        facilityGuysName: [
          { required: true, message: this.$t('maintenanceOrder.enterFacilityGuysName'), trigger: "blur" }
        ],
        facilityGuysEmail: [
          { required: true, message: this.$t('maintenanceOrder.enterFacilityGuysEmail'), trigger: "blur" }
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
        if (this.title === this.$t('maintenanceOrder.add')) {
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
      this.title = this.$t('maintenanceOrder.add');
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
        this.title = this.$t('maintenanceOrder.edit');
        this.originalForm = { ...this.form }; // 保存原始数据
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      if (this.fileList.some(file => file.status !== 'success')) {
        this.$message.error(this.$t('maintenanceOrder.uploadError'));
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.fileList.length === 0) {
            this.form.issuePhoto = '';
          }
          if (this.isUpdate()) {
            updateMaintenanceOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('maintenanceOrder.editSuccess'));
              this.open = false;
              this.getList();
              // this.addDetailedLog(this.form.issueId, this.$t('maintenanceOrder.edit'), this.originalForm, this.form, this.$t('maintenanceOrder.edit'));
            });
          } else {
            addMaintenanceOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('maintenanceOrder.addSuccess'));
              this.open = false;
              this.getList();
              // this.addLog(this.form.issueId, this.$t('maintenanceOrder.add'), this.$t('maintenanceOrder.addLog'));
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
      this.$modal.confirm(this.$t('maintenanceOrder.deleteConfirm', { issueIds })).then(() => {
        return delMaintenanceOrder(issueIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('maintenanceOrder.deleteSuccess'));
        // this.addLog(issueIds, "删除工单", "工单已删除");
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
            this.$modal.msgSuccess(this.$t('maintenanceOrder.assignSuccess'));
            this.assignOpen = false;
            this.getList();
            // this.addLog(this.assignForm.issueId, this.$t('maintenanceOrder.assign'), this.$t('maintenanceOrder.assignLog', { name: this.assignForm.facilityGuysName }));
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
      this.submitOrUpdateTitle = row.processingStatus === 'On Process' ? this.$t('maintenanceOrder.submit') : this.$t('maintenanceOrder.update');
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
              // this.addLog(this.submitOrUpdateForm.issueId, actionType, this.$t('maintenanceOrder.submitLog', { status: statusLabel }));
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
    addDetailedLog(issueId, actionType, originalData, updatedData, operationType) {
      const fieldNames = {
        issueDetails: this.$t('maintenanceOrder.issueDetails'),
        urgencyLevel: this.$t('maintenanceOrder.urgencyLevel'),
        maintenanceType: this.$t('maintenanceOrder.maintenanceType'),
        floor: this.$t('maintenanceOrder.floor'),
        classroom: this.$t('maintenanceOrder.classroom'),
        resultMessage: this.$t('maintenanceOrder.resultMessage'),
        resultImgPath: this.$t('maintenanceOrder.resultImgPath'),
        processingStatus: this.$t('maintenanceOrder.processingStatus'),
        facilityGuysName: this.$t('maintenanceOrder.facilityGuysName'),
        facilityGuyMobile: this.$t('maintenanceOrder.facilityGuyMobile'),
        facilityGuysEmail: this.$t('maintenanceOrder.facilityGuysEmail')
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
          const changeText = operationType === this.$t('maintenanceOrder.edit') ? this.$t('maintenanceOrder.editTo') : this.$t('maintenanceOrder.updateTo');
          changes.push(`${fieldName} ${this.$t('maintenanceOrder.from')} "${oldValue}" ${changeText} "${newValue}"`);
        }
      }
      const actionDescription = changes.length > 0 ? changes.join(", ") : this.$t('maintenanceOrder.noChange');
      const log = {
        issueId: issueId,
        actionType: actionType,
        actionDescription: `${actionDescription}`,
        userId: this.currentUser.userId,
        userName: this.currentUser.userName
      };
      addMaintenanceOrderLog(log).then(response => {
        if (response.code !== 200) {
          this.$message.error(this.$t('maintenanceOrder.logFail'));
        }
      });
    },
    /** 添加工单日志记录 */
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
          this.$message.error(this.$t('maintenanceOrder.logFail'));
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
    },
    handleViewLogs(row) {
      import('./LogDialog.vue').then(module => {
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
        // 发送消息逻辑
        console.log(this.$t('maintenanceOrder.sendMessage'), message);
      }
    },
    getCurrentUser() {
      getUserProfile().then(response => {
        this.currentUser.userId = response.data.userId;
        this.currentUser.userName = response.data.userName;
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
