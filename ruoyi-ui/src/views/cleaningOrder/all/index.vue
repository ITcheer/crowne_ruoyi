<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('cleaningOrder.issuerName')" prop="issuerName">
        <el-input
          v-model="queryParams.issuerName"
          :placeholder="$t('cleaningOrder.enterIssuerName')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('cleaningOrder.issueDetails')" prop="issueDetails">
        <el-input
          v-model="queryParams.issueDetails"
          :placeholder="$t('cleaningOrder.enterIssueDetails')"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('cleaningOrder.urgencyLevel')" prop="urgencyLevel">
        <el-select
          v-model="queryParams.urgencyLevel"
          :placeholder="$t('cleaningOrder.urgencyLevel')"
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
      <el-form-item :label="$t('cleaningOrder.processingStatus')" prop="processingStatus">
        <el-select
          v-model="queryParams.processingStatus"
          :placeholder="$t('cleaningOrder.processingStatus')"
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
      <el-form-item :label="$t('cleaningOrder.date')">
        <template v-if="isMobile">
          <div style="display: flex; gap: 10px;">
            <el-date-picker
              v-model="startDate"
              style="flex: 1;"
              value-format="yyyyMMdd"
              type="date"
              :placeholder="$t('cleaningOrder.startDate')"
              :popper-class="isMobile ? 'mobile-date-picker' : ''"
              :append-to-body="isMobile"
              @change="handleStartDateChange"
            ></el-date-picker>
            <el-date-picker
              v-model="endDate"
              style="flex: 1;"
              value-format="yyyyMMdd"
              type="date"
              :placeholder="$t('cleaningOrder.endDate')"
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
          :start-placeholder="$t('cleaningOrder.startDate')"
          :end-placeholder="$t('cleaningOrder.endDate')"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('cleaningOrder.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('cleaningOrder.reset') }}</el-button>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="showAdvancedSearch = true">{{ $t('cleaningOrder.advancedSearch') }}</el-button>
      </el-form-item>
    </el-form>

    <el-dialog :title="$t('cleaningOrder.advancedSearch')" :visible.sync="showAdvancedSearch" :width="isMobile ? '100%' : '800px'">
      <el-form :model="queryParams" ref="advancedSearchForm" size="small" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="$t('cleaningOrder.issuerName')" prop="issuerName">
              <el-input v-model="queryParams.issuerName" :placeholder="$t('cleaningOrder.enterIssuerName')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.issueDetails')" prop="issueDetails">
              <el-input v-model="queryParams.issueDetails" :placeholder="$t('cleaningOrder.enterIssueDetails')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.urgencyLevel')" prop="urgencyLevel">
              <el-select v-model="queryParams.urgencyLevel" :placeholder="$t('cleaningOrder.urgencyLevel')" clearable>
                <el-option
                  v-for="dict in dict.type.sys_urgency_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.processingStatus')" prop="processingStatus">
              <el-select v-model="queryParams.processingStatus" :placeholder="$t('cleaningOrder.processingStatus')" clearable>
                <el-option
                  v-for="dict in dict.type.sys_maintenance_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.date')">
              <el-date-picker
                v-model="dateRange"
                style="width: 100%"
                value-format="yyyyMMdd"
                type="daterange"
                range-separator="-"
                :start-placeholder="$t('cleaningOrder.startDate')"
                :end-placeholder="$t('cleaningOrder.endDate')"
              ></el-date-picker>
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.issuerEmail')" prop="issuerEmail">
              <el-input v-model="queryParams.issuerEmail" :placeholder="$t('cleaningOrder.enterIssuerEmail')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.classroom')" prop="classroom">
              <el-input v-model="queryParams.classroom" :placeholder="$t('cleaningOrder.enterClassroom')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.floor')" prop="floor">
              <el-input v-model="queryParams.floor" :placeholder="$t('cleaningOrder.enterFloor')" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('cleaningOrder.issuerPhone')" prop="issuerPhone">
              <el-input v-model="queryParams.issuerPhone" :placeholder="$t('cleaningOrder.enterIssuerPhone')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.facilityGuyId')" prop="facilityGuyId">
              <el-input v-model="queryParams.facilityGuyId" :placeholder="$t('cleaningOrder.enterFacilityGuyId')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.facilityGuysEmail')" prop="facilityGuysEmail">
              <el-input v-model="queryParams.facilityGuysEmail" :placeholder="$t('cleaningOrder.enterFacilityGuysEmail')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.facilityGuysName')" prop="facilityGuysName">
              <el-input v-model="queryParams.facilityGuysName" :placeholder="$t('cleaningOrder.enterFacilityGuysName')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.facilityGuyMobile')" prop="facilityGuyMobile">
              <el-input v-model="queryParams.facilityGuyMobile" :placeholder="$t('cleaningOrder.enterFacilityGuyMobile')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.facilityGuySupervisor')" prop="facilityGuySupervisor">
              <el-input v-model="queryParams.facilityGuySupervisor" :placeholder="$t('cleaningOrder.enterFacilityGuySupervisor')" clearable />
            </el-form-item>
            <el-form-item :label="$t('cleaningOrder.resultMessage')" prop="resultMessage">
              <el-input v-model="queryParams.resultMessage" :placeholder="$t('cleaningOrder.enterResultMessage')" clearable />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleQuery">{{ $t('cleaningOrder.search') }}</el-button>
        <el-button @click="showAdvancedSearch = false">{{ $t('cleaningOrder.close') }}</el-button>
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
          v-hasPermi="['cleaningOrder:all:add']"
        >{{ $t('cleaningOrder.add') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['cleaningOrder:all:edit']"
        >{{ $t('cleaningOrder.edit') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['cleaningOrder:all:remove']"
        >{{ $t('cleaningOrder.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cleaningOrder:all:export']"
        >{{ $t('cleaningOrder.export') }}</el-button>
      </el-col>
      <el-col v-if="!isMobile" :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-setting"
          size="mini"
          @click="showColumnSettings = true"
        >{{ $t('cleaningOrder.columnSettings') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row v-if="isMobile" :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in cleaningOrderList" :key="item.issueId">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <p><strong>{{ $t('cleaningOrder.issuerName') }}:</strong> {{ item.issuerName }}</p>
            <p><strong>{{ $t('cleaningOrder.issuerEmail') }}:</strong> {{ item.issuerEmail }}</p>
            <p><strong>{{ $t('cleaningOrder.issuerPhone') }}:</strong> {{ item.issuerPhone }}</p>
            <p><strong>{{ $t('cleaningOrder.issueDetails') }}:</strong> {{ item.issueDetails }}</p>
            <p><strong>{{ $t('cleaningOrder.floor') }}:</strong> {{ item.floor }}</p>
            <p><strong>{{ $t('cleaningOrder.classroom') }}:</strong> {{ item.classroom }}</p>
            <p><strong>{{ $t('cleaningOrder.date') }}:</strong> {{ parseTime(item.date) }}</p>
            <p class="inline-fields">
              <strong>{{ $t('cleaningOrder.urgencyLevel') }}:</strong> <dict-tag :options="dict.type.sys_urgency_level" :value="item.urgencyLevel"/>
              <strong>{{ $t('cleaningOrder.processingStatus') }}:</strong> <dict-tag :options="dict.type.sys_maintenance_status" :value="item.processingStatus"/>
            </p>
            <p>
              <strong>{{ $t('cleaningOrder.issuePhoto') }}:</strong>
              <el-button type="text" :style="{ color: item.issuePhoto ? '' : 'gray' }" @click="item.issuePhoto ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + item.issuePhoto) : null">
                {{ item.issuePhoto ? $t('cleaningOrder.viewImage') : $t('cleaningOrder.noImage') }}
              </el-button>
            </p>
            <el-button class="expand-button" type="text" @click="toggleDetails(item)">
              <i :class="item.showDetails ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
            </el-button>
            <div v-if="item.showDetails" class="button-container">
              <el-button type="success" size="mini" @click="handleUpdate(item)" v-hasPermi="['cleaningOrder:all:edit']">{{ $t('cleaningOrder.edit') }}</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(item)" v-hasPermi="['cleaningOrder:all:remove']">{{ $t('cleaningOrder.delete') }}</el-button>
              <el-button type="primary" size="mini" @click="handleAssign(item)" v-hasPermi="['cleaningOrder:all:assign']" :disabled="item.processingStatus !== 'Undistributed' && item.processingStatus !== 'On Process'">{{ $t('cleaningOrder.assign') }}</el-button>
              <el-button type="primary" size="mini" @click="handleSubmitOrUpdate(item)" v-hasPermi="['cleaningOrder:all:submitOrUpdate']" :disabled="item.processingStatus === 'Undistributed'">{{ item.processingStatus === 'On Process' ? $t('cleaningOrder.submit') : $t('cleaningOrder.update') }}</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-table v-else v-loading="loading" :data="cleaningOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="visibleColumns.includes('issueId')" :label="$t('cleaningOrder.issueId')" prop="issueId" width="120" sortable />
      <el-table-column v-if="visibleColumns.includes('issuerName')" :label="$t('cleaningOrder.issuerName')" prop="issuerName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issuePhoto')" :label="$t('cleaningOrder.issuePhoto')" prop="issuePhoto" :show-overflow-tooltip="true" width="120">
        <template slot-scope="scope">
          <el-button type="text" :style="{ color: scope.row.issuePhoto ? '' : 'gray' }" @click="scope.row.issuePhoto ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + scope.row.issuePhoto) : null">
            {{ scope.row.issuePhoto ? $t('cleaningOrder.viewImage') : $t('cleaningOrder.noImage') }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('issuerEmail')" :label="$t('cleaningOrder.issuerEmail')" prop="issuerEmail" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('issueDetails')" :label="$t('cleaningOrder.issueDetails')" prop="issueDetails" :show-overflow-tooltip="true" width="200" sortable />
      <el-table-column v-if="visibleColumns.includes('classroom')" :label="$t('cleaningOrder.classroom')" prop="classroom" :show-overflow-tooltip="true" width="100" sortable />
      <el-table-column v-if="visibleColumns.includes('floor')" :label="$t('cleaningOrder.floor')" prop="floor" :show-overflow-tooltip="true" width="80" sortable />
      <el-table-column v-if="visibleColumns.includes('maintenanceType')" :label="$t('cleaningOrder.maintenanceType')" align="center" prop="maintenanceType" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_maintenance_type || []" :value="scope.row.maintenanceType"/>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('urgencyLevel')" :label="$t('cleaningOrder.urgencyLevel')" align="center" prop="urgencyLevel" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_urgency_level || []" :value="scope.row.urgencyLevel"/>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('issuerPhone')" :label="$t('cleaningOrder.issuerPhone')" prop="issuerPhone" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuyId')" :label="$t('cleaningOrder.facilityGuyId')" prop="facilityGuyId" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('date')" :label="$t('cleaningOrder.date')" align="center" prop="date" width="180" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.date) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('facilityGuysEmail')" :label="$t('cleaningOrder.facilityGuysEmail')" prop="facilityGuysEmail" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuysName')" :label="$t('cleaningOrder.facilityGuysName')" prop="facilityGuysName" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuyMobile')" :label="$t('cleaningOrder.facilityGuyMobile')" prop="facilityGuyMobile" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('facilityGuySupervisor')" :label="$t('cleaningOrder.facilityGuySupervisor')" prop="facilityGuySupervisor" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('resultImgPath')" :label="$t('cleaningOrder.resultImgPath')" prop="resultImgPath" :show-overflow-tooltip="true" width="120">
        <template slot-scope="scope">
          <el-button type="text" :style="{ color: scope.row.resultImgPath ? '' : 'gray' }" @click="scope.row.resultImgPath ? viewImage('https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/' + scope.row.resultImgPath) : null">
            {{ scope.row.resultImgPath ? $t('cleaningOrder.viewImage') : $t('cleaningOrder.noImage') }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="visibleColumns.includes('resultMessage')" :label="$t('cleaningOrder.resultMessage')" prop="resultMessage" :show-overflow-tooltip="true" width="150" sortable />
      <el-table-column v-if="visibleColumns.includes('processingStatus')" :label="$t('cleaningOrder.processingStatus')" align="center" prop="processingStatus" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_maintenance_status || []" :value="scope.row.processingStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('cleaningOrder.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="text" size="mini">
              <i class="el-icon-more"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleUpdate(scope.row)" v-hasPermi="['cleaningOrder:all:edit']">
                <i class="el-icon-edit"></i> {{ $t('cleaningOrder.edit') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.row)" v-hasPermi="['cleaningOrder:all:remove']">
                <i class="el-icon-delete"></i> {{ $t('cleaningOrder.delete') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleAssign(scope.row)" v-hasPermi="['cleaningOrder:all:assign']" :disabled="scope.row.processingStatus !== 'Undistributed' && scope.row.processingStatus !== 'On Process'">
                <i class="el-icon-s-tools"></i> {{ $t('cleaningOrder.assign') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleSubmitOrUpdate(scope.row)" v-hasPermi="['cleaningOrder:all:submitOrUpdate']" :disabled="scope.row.processingStatus === 'Undistributed'">
                <i class="el-icon-upload"></i> {{ scope.row.processingStatus === 'On Process' ? $t('cleaningOrder.submit') : $t('cleaningOrder.update') }}
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

    <!-- 添加或修改清洁订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('cleaningOrder.issueId')" prop="issueId">
          <el-input v-model="form.issueId" :placeholder="$t('cleaningOrder.issueId')" disabled />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.issuerName')" prop="issuerName">
          <el-input v-model="form.issuerName" :placeholder="$t('cleaningOrder.enterIssuerName')" />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.issuerEmail')" prop="issuerEmail">
          <el-input v-model="form.issuerEmail" :placeholder="$t('cleaningOrder.enterIssuerEmail')" />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.issuerPhone')" prop="issuerPhone">
          <el-input v-model="form.issuerPhone" :placeholder="$t('cleaningOrder.enterIssuerPhone')" />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.issueDetails')" prop="issueDetails">
          <el-input v-model="form.issueDetails" :placeholder="$t('cleaningOrder.enterIssueDetails')" />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.urgencyLevel')" prop="urgencyLevel">
          <el-radio-group v-model="form.urgencyLevel">
            <el-radio
              v-for="dict in dict.type.sys_urgency_level"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.issuePhoto')" prop="issuePhoto">
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
        <el-form-item :label="$t('cleaningOrder.floor')" prop="floor">
          <el-input v-model="form.floor" :placeholder="$t('cleaningOrder.enterFloor')" />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.classroom')" prop="classroom">
          <el-input v-model="form.classroom" :placeholder="$t('cleaningOrder.enterClassroom')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('cleaningOrder.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('cleaningOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 提交或更新清洁订单对话框 -->
    <el-dialog :title="submitOrUpdateTitle" :visible.sync="submitOrUpdateOpen" :width="isMobile ? '100%' : '500px'" append-to-body>
      <el-form ref="submitOrUpdateForm" :model="submitOrUpdateForm" label-width="100px">
        <el-form-item :label="$t('cleaningOrder.facilityGuysName')" prop="facilityGuysName" v-if="submitOrUpdateTitle === '更新清洁订单'">
          <el-select v-model="submitOrUpdateForm.facilityGuysName" :placeholder="$t('cleaningOrder.enterFacilityGuysName')" @change="handleUserChange">
            <el-option
              v-for="user in deptUsers"
              :key="user.userId"
              :label="user.userName"
              :value="user.userName"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.facilityGuyMobile')" prop="facilityGuyMobile" v-if="submitOrUpdateTitle === '更新清洁订单'">
          <el-input v-model="submitOrUpdateForm.facilityGuyMobile" :placeholder="$t('cleaningOrder.enterFacilityGuyMobile')" />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.facilityGuysEmail')" prop="facilityGuysEmail" v-if="submitOrUpdateTitle === '更新清洁订单'">
          <el-input v-model="submitOrUpdateForm.facilityGuysEmail" :placeholder="$t('cleaningOrder.enterFacilityGuysEmail')" />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.resultMessage')" prop="resultMessage">
          <el-input v-model="submitOrUpdateForm.resultMessage" :placeholder="$t('cleaningOrder.enterResultMessage')" />
        </el-form-item>
        <el-form-item :label="$t('cleaningOrder.resultImgPath')" prop="resultImgPath">
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
        <el-form-item :label="$t('cleaningOrder.processingStatus')" prop="processingStatus">
          <el-select v-model="submitOrUpdateForm.processingStatus" :placeholder="$t('cleaningOrder.enterProcessingStatus')">
            <el-option
              v-for="dict in dict.type.sys_maintenance_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
              v-if="submitOrUpdateTitle === '提交清洁订单' ? (dict.value === 'Resolved' || dict.value === 'Can not Resolve') : true"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :disabled="!isFormChanged || !submitOrUpdateForm.processingStatus || (submitOrUpdateTitle === '提交清洁订单' && submitOrUpdateForm.processingStatus === 'On Process')" @click="submitOrUpdateFormAction">{{ $t('cleaningOrder.confirm') }}</el-button>
        <el-button @click="cancelSubmitOrUpdate">{{ $t('cleaningOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 分配清洁订单对话框 -->
    <el-dialog :title="assignTitle" :visible.sync="assignOpen" :width="isMobile ? '100%' : '600px'" append-to-body>
      <el-form ref="assignForm" :model="assignForm" :rules="assignRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item :label="$t('cleaningOrder.facilityGuysName')" prop="facilityGuysName">
              <el-select v-model="assignForm.facilityGuysName" :placeholder="$t('cleaningOrder.enterFacilityGuysName')" @change="handleUserChange">
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
            <el-form-item :label="$t('cleaningOrder.facilityGuyMobile')" prop="facilityGuyMobile">
              <el-input v-model="assignForm.facilityGuyMobile" :placeholder="$t('cleaningOrder.enterFacilityGuyMobile')" />
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="assignForm.facilityGuysName">
            <el-form-item :label="$t('cleaningOrder.facilityGuysEmail')" prop="facilityGuysEmail">
              <el-input v-model="assignForm.facilityGuysEmail" :placeholder="$t('cleaningOrder.enterFacilityGuysEmail')" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button 
          type="primary" 
          :disabled="!assignForm.facilityGuysName || (assignForm.facilityGuysName === originalFacilityGuysName && assignForm.facilityGuyId)" 
          @click="submitAssignForm">
          {{ isUnassigned ? $t('cleaningOrder.assign') : $t('cleaningOrder.reassign') }}
        </el-button>
        <el-button @click="cancelAssign">{{ $t('cleaningOrder.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 字段设置对话框 -->
    <el-dialog :title="$t('cleaningOrder.columnSettings')" :visible.sync="showColumnSettings" :width="isMobile ? '100%' : '300px'">
      <el-checkbox-group v-model="visibleColumns">
        <el-row>
          <el-col :span="12"><el-checkbox label="issueId">{{ $t('cleaningOrder.issueId') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerName">{{ $t('cleaningOrder.issuerName') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuePhoto">{{ $t('cleaningOrder.issuePhoto') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerEmail">{{ $t('cleaningOrder.issuerEmail') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issueDetails">{{ $t('cleaningOrder.issueDetails') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="classroom">{{ $t('cleaningOrder.classroom') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="floor">{{ $t('cleaningOrder.floor') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="urgencyLevel">{{ $t('cleaningOrder.urgencyLevel') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="issuerPhone">{{ $t('cleaningOrder.issuerPhone') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuyId">{{ $t('cleaningOrder.facilityGuyId') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="date">{{ $t('cleaningOrder.date') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuysEmail">{{ $t('cleaningOrder.facilityGuysEmail') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuysName">{{ $t('cleaningOrder.facilityGuysName') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuyMobile">{{ $t('cleaningOrder.facilityGuyMobile') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="facilityGuySupervisor">{{ $t('cleaningOrder.facilityGuySupervisor') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="resultImgPath">{{ $t('cleaningOrder.resultImgPath') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="resultMessage">{{ $t('cleaningOrder.resultMessage') }}</el-checkbox></el-col>
          <el-col :span="12"><el-checkbox label="processingStatus">{{ $t('cleaningOrder.processingStatus') }}</el-checkbox></el-col>
        </el-row>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showColumnSettings = false">{{ $t('cleaningOrder.close') }}</el-button>
      </div>
    </el-dialog>

    <!-- 图片查看对话框 -->
    <el-dialog :title="$t('cleaningOrder.viewImage')" :visible.sync="showImageDialog" :width="isMobile ? '100%' : '600px'">
      <img :src="imageSrc" alt="图片" style="width: 100%;" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="showImageDialog = false">{{ $t('cleaningOrder.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCleaningOrder, getCleaningOrder, delCleaningOrder, addCleaningOrder, updateCleaningOrder, exportCleaningOrder, uploadImage, assignCleaningOrder } from "@/api/cleaningOrder/all/cleaningOrderAll";
import { getUserProfile, listUserByDeptId } from "@/api/maintenanceOrder/my/maintenanceOrderMy";
import { sendCompletionEmail, sendIncompleteEmail } from "@/api/cleaningOrder/my/cleaningOrderMy";

export default {
  name: "CleaningOrder",
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
      // 清洁订单表格数据
      cleaningOrderList: [],
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
          { required: true, message: this.$t('cleaningOrder.enterIssuerName'), trigger: "blur" }
        ],
        issuerEmail: [
          { required: true, message: this.$t('cleaningOrder.enterIssuerEmail'), trigger: "blur" }
        ],
        issuerPhone: [
          { required: true, message: this.$t('cleaningOrder.enterIssuerPhone'), trigger: "blur" }
        ],
        issueDetails: [
          { required: true, message: this.$t('cleaningOrder.enterIssueDetails'), trigger: "blur" }
        ],
        urgencyLevel: [
          { required: true, message: this.$t('cleaningOrder.urgencyLevel'), trigger: "blur" }
        ],
        maintenanceType: [
          { required: true, message: this.$t('cleaningOrder.maintenanceType'), trigger: "blur" }
        ],
        floor: [
          { required: true, message: this.$t('cleaningOrder.enterFloor'), trigger: "blur" }
        ],
        classroom: [
          { required: true, message: this.$t('cleaningOrder.enterClassroom'), trigger: "blur" }
        ]
      },
      showColumnSettings: false,
      visibleColumns: ['issuerName', 'issuePhoto', 'issueDetails', 'classroom', 'floor', 'urgencyLevel', 'date', 'facilityGuysName', 'resultImgPath', 'resultMessage', 'processingStatus'],
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
      assignTitle: this.$t('cleaningOrder.assignTitle'),
      assignForm: {
        issueId: undefined,
        facilityGuyId: undefined,
        facilityGuysName: undefined,
        facilityGuyMobile: undefined,
        facilityGuysEmail: undefined
      },
      assignRules: {
        facilityGuyId: [
          { required: true, message: "清洁人员ID不能为空", trigger: "blur" }
        ],
        facilityGuysName: [
          { required: true, message: "清洁人员姓名不能为空", trigger: "blur" }
        ],
        facilityGuysEmail: [
          { required: true, message: "清洁人员邮箱不能为空", trigger: "blur" }
        ]
      },
      isUnassigned: true, // 是否为未分配工单
      originalFacilityGuysName: '', // 原始清洁人员姓名
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
    /** 查询清洁订单列表 */
    getList() {
      this.loading = true;
      const params = {
        ...this.addDateRange(this.queryParams, this.dateRange),
        sortField: this.queryParams.sortField,
        sortOrder: this.queryParams.sortOrder
      };
      listCleaningOrder(params).then(response => {
          this.cleaningOrderList = response.rows;
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
      this.title = this.$t('cleaningOrder.add');
      this.getUserProfile(); // 获取当前用户信息
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const issueId = row.issueId || this.ids;
      getCleaningOrder(issueId).then(response => {
        this.form = response.data;
        if (this.form.issuePhoto) {
          const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${this.form.issuePhoto}`;
          this.fileList = [{ name: this.form.issuePhoto, url: fullUrl }];
        } else {
          this.fileList = [];
        }
        this.open = true;
        this.title = this.$t('cleaningOrder.edit');
        this.originalForm = { ...this.form }; // 保存原始数据
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      if (this.fileList.some(file => file.status !== 'success')) {
        this.$message.error(this.$t('cleaningOrder.uploadError'));
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.fileList.length === 0) {
            this.form.issuePhoto = '';
          }
          if (this.isUpdate()) {
            updateCleaningOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('cleaningOrder.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addCleaningOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('cleaningOrder.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 判断是否为修改操作 */
    isUpdate() {
      return this.form.issueId && this.cleaningOrderList.some(order => order.issueId === this.form.issueId);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const issueIds = row.issueId || this.ids;
      this.$modal.confirm(this.$t('cleaningOrder.deleteConfirm', { issueIds })).then(() => {
        return delCleaningOrder(issueIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('cleaningOrder.deleteSuccess'));
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cleaningOrder/my/export', {
        ...this.queryParams
      }, `cleaningOrder_all_${new Date().getTime()}.xlsx`)
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
      if (this.submitOrUpdateForm.resultImgPath) {
        const fullUrl = `https://schoolmaintenancestorage.blob.core.windows.net/schoolblodfiles/image/${this.submitOrUpdateForm.resultImgPath}`;
        this.fileList.push({ name: this.submitOrUpdateForm.resultImgPath, url: fullUrl });
      }
      this.submitOrUpdateTitle = row.processingStatus === 'On Process' ? this.$t('cleaningOrder.submit') : this.$t('cleaningOrder.update');
      this.submitOrUpdateOpen = true;
    },
    submitOrUpdateFormAction() {
      if (this.fileList.some(file => file.status !== 'success')) {
        this.$message.error(this.$t('cleaningOrder.uploadError'));
        return;
      }
      if (this.fileList.length === 0) {
        this.submitOrUpdateForm.resultImgPath = '';
      } 
      if (this.submitOrUpdateTitle === this.$t('cleaningOrder.submit')) {
        this.updateCleaningOrderData(this.$t('cleaningOrder.submit'));
      } else {
        this.updateCleaningOrderData(this.$t('cleaningOrder.update'));
      }
    },
    updateCleaningOrderData(actionType) {
      this.$refs["submitOrUpdateForm"].validate(valid => {
        if (valid) {
          if (this.fileList.length === 0) {
            this.submitOrUpdateForm.resultImgPath = '';
          } 
          updateCleaningOrder(this.submitOrUpdateForm).then(response => {
            this.$modal.msgSuccess(this.$t('cleaningOrder.actionSuccess'));
            this.submitOrUpdateOpen = false;
            this.getList();
            if (actionType === this.$t('cleaningOrder.submit')) {
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
      this.fileList = [];
      this.resetForm("submitOrUpdateForm");
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
        if (this.title === "添加清洁订单") {
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
      this.assignTitle = this.isUnassigned ? this.$t('cleaningOrder.assign') : this.$t('cleaningOrder.reassign');
      this.assignOpen = true;
    },
    submitAssignForm() {
      this.$refs["assignForm"].validate(valid => {
        if (valid) {
          assignCleaningOrder(this.assignForm).then(response => {
            this.$modal.msgSuccess(this.$t('cleaningOrder.assignSuccess'));
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

