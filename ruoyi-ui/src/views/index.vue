<template>
  <div class="app-container home">
    <!-- <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          {{ $t('home.welcome') }}
        </blockquote>
      </el-col>
    </el-row> -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :sm="24" :lg="12">
        <el-card class="reduced-height">
          <div slot="header" class="clearfix">
            <span>{{ $t('home.myOrders') }}</span>
          </div>
          <el-tabs v-model="activeTab" @tab-click="handleTabClick" :tab-position="isMobile ? 'top' : 'left'">
            <el-tab-pane :label="$t('home.repairOrders')" name="repair">
              <div class="body reduced-height-body">
                <el-row :gutter="20">
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card unassigned" @click.native="navigateToMyOrders('Undistributed')">
                      <div class="body">
                        <p class="three-d-text">{{ unassignedCount }}</p>
                        <div class="work-order-text">{{ $t('home.unassigned') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-promotion"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card unprocessed" @click.native="navigateToMyOrders('On Process')">
                      <div class="body">
                        <p class="three-d-text">{{ unprocessedCount }}</p>
                        <div class="work-order-text">{{ $t('home.processing') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-tools"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card completed" @click.native="navigateToMyOrders('Resolved')">
                      <div class="body">
                        <p class="three-d-text">{{ completedCount }}</p>
                        <div class="work-order-text">{{ $t('home.completed') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-claim"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card incomplete" @click.native="navigateToMyOrders('Can not Resolve')">
                      <div class="body">
                        <p class="three-d-text">{{ incompleteCount }}</p>
                        <div class="work-order-text">{{ $t('home.incomplete') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-release"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
              </div>
            </el-tab-pane>
            <el-tab-pane :label="$t('home.cleaningOrders')" name="cleaning">
              <div class="body reduced-height-body">
                <el-row :gutter="20">
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card unassigned" @click.native="navigateToMyOrders('Undistributed', 'cleaning')">
                      <div class="body">
                        <p class="three-d-text">{{ unassignedCleaningCount }}</p>
                        <div class="work-order-text">{{ $t('home.unassigned') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-promotion"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card unprocessed" @click.native="navigateToMyOrders('On Process', 'cleaning')">
                      <div class="body">
                        <p class="three-d-text">{{ unprocessedCleaningCount }}</p>
                        <div class="work-order-text">{{ $t('home.processing') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-tools"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card completed" @click.native="navigateToMyOrders('Resolved', 'cleaning')">
                      <div class="body">
                        <p class="three-d-text">{{ completedCleaningCount }}</p>
                        <div class="work-order-text">{{ $t('home.completed') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-claim"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card incomplete" @click.native="navigateToMyOrders('Can not Resolve', 'cleaning')">
                      <div class="body">
                        <p class="three-d-text">{{ incompleteCleaningCount }}</p>
                        <div class="work-order-text">{{ $t('home.incomplete') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-release"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
              </div>
            </el-tab-pane>
            <el-tab-pane :label="$t('home.schoolOrders')" name="school">
              <div class="body reduced-height-body">
                <el-row :gutter="20">
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card unassigned" @click.native="navigateToMyOrders('Undistributed', 'school')">
                      <div class="body">
                        <p class="three-d-text">{{ unassignedSchoolCount }}</p>
                        <div class="work-order-text">{{ $t('home.unassigned') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-promotion"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card unprocessed" @click.native="navigateToMyOrders('On Process', 'school')">
                      <div class="body">
                        <p class="three-d-text">{{ unprocessedSchoolCount }}</p>
                        <div class="work-order-text">{{ $t('home.processing') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-tools"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card completed" @click.native="navigateToMyOrders('Resolved', 'school')">
                      <div class="body">
                        <p class="three-d-text">{{ completedSchoolCount }}</p>
                        <div class="work-order-text">{{ $t('home.completed') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-claim"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card incomplete" @click.native="navigateToMyOrders('Can not Resolve', 'school')">
                      <div class="body">
                        <p class="three-d-text">{{ incompleteSchoolCount }}</p>
                        <div class="work-order-text">{{ $t('home.incomplete') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-release"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
              </div>
            </el-tab-pane>
            <el-tab-pane :label="$t('home.complaintOrders')" name="complaint">
              <div class="body reduced-height-body">
                <el-row :gutter="20">
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card unassigned" @click.native="navigateToMyOrders('Untreated', 'complaint')">
                      <div class="body">
                        <p class="three-d-text">{{ unassignedComplaintCount }}</p>
                        <div class="work-order-text">{{ $t('home.unassigned') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-promotion"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card unprocessed" @click.native="navigateToMyOrders('On Process', 'complaint')">
                      <div class="body">
                        <p class="three-d-text">{{ unprocessedComplaintCount }}</p>
                        <div class="work-order-text">{{ $t('home.processing') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-tools"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card completed" @click.native="navigateToMyOrders('Resolved', 'complaint')">
                      <div class="body">
                        <p class="three-d-text">{{ completedComplaintCount }}</p>
                        <div class="work-order-text">{{ $t('home.completed') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-claim"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                    <el-card class="work-order-card incomplete" @click.native="navigateToMyOrders('Can not Resolve', 'complaint')">
                      <div class="body">
                        <p class="three-d-text">{{ incompleteComplaintCount }}</p>
                        <div class="work-order-text">{{ $t('home.incomplete') }}</div>
                        <el-icon style="font-size: 48px; color: white;">
                          <i class="el-icon-s-release"></i>
                        </el-icon>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-card>
        <el-row :gutter="20" style="margin-top: 20px;">
          <el-col :span="12">
            <el-card>
              <div slot="header" class="clearfix">
                <span>{{ $t('home.systemNotifications') }}</span>
              </div>
              <div class="body" style="text-align: center; font-size: 24px; padding: 10px;">
                <el-timeline>
                  <el-timeline-item v-for="item in notifications" :key="item.id" :timestamp="item.timestamp">
                    {{ $t(`messages.${item.content}`) }}
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
              <div slot="header" class="clearfix">
                <span>{{ $t('home.helpManual') }}</span>
              </div>
              <div class="body" style="text-align: center; font-size: 24px; padding: 10px;">
                <ul>
                  <li v-for="item in helpManuals" :key="item.id">
                    <el-link :href="item.link" target="_blank">{{ $t(`messages.${item.title}`) }}</el-link>
                  </li>
                </ul>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      <el-col :sm="24" :lg="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>{{ $t('home.orderStatistics') }}</span>
          </div>
          <div class="body" style="text-align: center; font-size: 24px; padding: 10px;">
            <el-row :gutter="20">
              <el-col :sm="24" :lg="12">
                <el-card>
                  <div slot="header" class="clearfix">
                    <span>{{ $t('home.totalOrders') }}</span>
                  </div>
                  <div class="body" style="text-align: center; font-size: 24px; color: #409EFF; padding: 10px;">
                    <el-icon style="font-size: 48px; color: #409EFF;">
                      <i class="el-icon-document"></i>
                    </el-icon>
                    <p class="three-d-text">{{ totalOrders }}</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :sm="24" :lg="12">
                <el-card>
                  <div slot="header" class="clearfix">
                    <span>{{ $t('home.newOrdersToday') }}</span>
                  </div>
                  <div class="body" style="text-align: center; font-size: 24px; color: #67C23A; padding: 10px;">
                    <el-icon style="font-size: 48px; color: #67C23A;">
                      <i class="el-icon-date"></i>
                    </el-icon>
                    <p class="three-d-text">{{ newOrdersToday }}</p>
                  </div>
                </el-card>
              </el-col>
            </el-row>
            <el-row :gutter="20" style="margin-top: 20px;">
              <el-col :sm="24" :lg="12">
                <el-card>
                  <div slot="header" class="clearfix">
                    <span>{{ $t('home.orderTypes') }}</span>
                  </div>
                  <div class="body">
                    <div ref="typeChart" style="height: 300px;"></div>
                  </div>
                </el-card>
              </el-col>
              <el-col :sm="24" :lg="12">
                <el-card>
                  <div slot="header" class="clearfix">
                    <span>{{ $t('home.orderStatus') }}</span>
                  </div>
                  <div class="body">
                    <div ref="statusChart" style="height: 300px;"></div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { listMyMaintenanceOrder, listMyCleaningOrder, listMyFacilityOrder, listMyComplaintOrder, listAllOrders } from "@/api/homePage/homePage";
import { useRouter } from 'vue-router';

export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.8",
      unassignedCount: 0,
      unprocessedCount: 0,
      completedCount: 0,
      incompleteCount: 0,
      totalOrders: 0,
      newOrdersToday: 0,
      notifications: [
        { id: 1, timestamp: '2023-10-01', content: 'systemMaintenance' },
        { id: 2, timestamp: '2023-10-02', content: 'newFeature' }
      ],
      helpManuals: [
        { id: 1, title: 'createOrder', link: 'https://example.com/help/create-ticket' },
        { id: 2, title: 'processOrder', link: 'https://example.com/help/process-ticket' },
        { id: 3, title: 'orderStatus', link: 'https://example.com/help/ticket-status' }
      ],
      activeTab: 'repair',
      unassignedCleaningCount: 0,
      unprocessedCleaningCount: 0,
      completedCleaningCount: 0,
      incompleteCleaningCount: 0,
      unassignedSchoolCount: 0,
      unprocessedSchoolCount: 0,
      completedSchoolCount: 0,
      incompleteSchoolCount: 0,
      unassignedComplaintCount: 0,
      unprocessedComplaintCount: 0,
      completedComplaintCount: 0,
      incompleteComplaintCount: 0,
      tabInterval: null,
      isMobile: window.innerWidth <= 768
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initCharts();
      this.getMyWorkOrderCounts();
      this.getAllWorkOrderCounts();
      this.startTabRotation();
    });
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize);
    this.stopTabRotation();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    getMyWorkOrderCounts() {
      const params = {
        pageNum: 1,
        pageSize: 1000 // 假设最大数量
      };
      listMyMaintenanceOrder(params).then(response => {
        const orders = response.rows;
        this.unassignedCount = orders.filter(order => order.processingStatus === 'Undistributed').length;
        this.unprocessedCount = orders.filter(order => order.processingStatus === 'On Process').length;
        this.completedCount = orders.filter(order => order.processingStatus === 'Resolved').length;
        this.incompleteCount = orders.filter(order => order.processingStatus === 'Can not Resolve').length;
      });

      listMyCleaningOrder(params).then(response => {
        const orders = response.rows;
        this.unassignedCleaningCount = orders.filter(order => order.processingStatus === 'Undistributed').length;
        this.unprocessedCleaningCount = orders.filter(order => order.processingStatus === 'On Process').length;
        this.completedCleaningCount = orders.filter(order => order.processingStatus === 'Resolved').length;
        this.incompleteCleaningCount = orders.filter(order => order.processingStatus === 'Can not Resolve').length;
      });

      listMyFacilityOrder(params).then(response => {
        const orders = response.rows;
        this.unassignedSchoolCount = orders.filter(order => order.processingStatus === 'Undistributed').length;
        this.unprocessedSchoolCount = orders.filter(order => order.processingStatus === 'On Process').length;
        this.completedSchoolCount = orders.filter(order => order.processingStatus === 'Resolved').length;
        this.incompleteSchoolCount = orders.filter(order => order.processingStatus === 'Can not Resolve').length;
      });

      listMyComplaintOrder(params).then(response => {
        const orders = response.rows;
        this.unassignedComplaintCount = orders.filter(order => order.processingStatus === 'Untreated').length;
        this.unprocessedComplaintCount = orders.filter(order => order.processingStatus === 'On Process').length;
        this.completedComplaintCount = orders.filter(order => order.processingStatus === 'Resolved').length;
        this.incompleteComplaintCount = orders.filter(order => order.processingStatus === 'Can not Resolve').length;
      });
    },
    getAllWorkOrderCounts() {
      const params = {
        pageNum: 1,
        pageSize: 1000 // 假设最大数量
      };
      listAllOrders(params).then(response => {
        const orders = response.rows;
        this.totalOrders = orders.length;
        const today = new Date().toISOString().slice(0, 10);
        this.newOrdersToday = orders.filter(order => order.date.slice(0, 10) === today).length;
        this.updateCharts(orders);
      });
    },
    updateCharts(orders) {
      const defaultStatuses = ['未分配', '处理中', '已完成', '未完成'];
      const defaultTypes = ['维修', '清洁', '校工', '投诉'];

      const statusData = orders.reduce((acc, order) => {
        const statusMap = {
          'Undistributed': '未分配',
          'On Process': '处理中',
          'Resolved': '已完成',
          'Can not Resolve': '未完成',
          'Untreated': '未处理'
        };
        const status = statusMap[order.processingStatus];
        const type = order.orderType;
        if (!acc[status]) {
          acc[status] = {};
        }
        if (acc[status][type]) {
          acc[status][type]++;
        } else {
          acc[status][type] = 1;
        }
        return acc;
      }, {});

      defaultStatuses.forEach(status => {
        if (!statusData[status]) {
          statusData[status] = {};
        }
        defaultTypes.forEach(type => {
          if (!statusData[status][type]) {
            statusData[status][type] = 0;
          }
        });
      });

      const statusChart = echarts.init(this.$refs.statusChart);
      const labelOption = {
        show: true,
        position: 'insideBottom',
        distance: 10,
        align: 'center',
        verticalAlign: 'middle',
        rotate: 0,
        formatter: '{c}',
        fontSize: 12,
        rich: {
          name: {}
        }
      };
      const statusChartOptions = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: defaultTypes
        },
        toolbox: {
          show: true,
          orient: 'vertical',
          left: 'right',
          top: 'center',
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar', 'stack'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: defaultStatuses,
            axisLabel: {
              fontSize: 10,
              rotate: 45
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: defaultTypes.map(type => ({
          name: type,
          type: 'bar',
          stack: 'total',
          barWidth: '30%',
          label: labelOption,
          emphasis: {
            focus: 'series'
          },
          data: defaultStatuses.map(status => statusData[status][type] || 0)
        }))
      };
      statusChart.setOption(statusChartOptions);

      const typeData = orders.reduce((acc, order) => {
        const type = order.orderType;
        if (acc[type]) {
          acc[type]++;
        } else {
          acc[type] = 1;
        }
        return acc;
      }, {});

      defaultTypes.forEach(type => {
        if (!typeData[type]) {
          typeData[type] = 0;
        }
      });

      const typeChart = echarts.init(this.$refs.typeChart);
      const typeChartOptions = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          right: '1%',
          top: '1%'
        },
        series: [
          {
            name: '工单类型',
            type: 'pie',
            radius: '50%',
            data: Object.keys(typeData).map(key => ({ value: typeData[key], name: key })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      typeChart.setOption(typeChartOptions);
    },
    initCharts() {
      const typeChart = echarts.init(this.$refs.typeChart);
      const statusChart = echarts.init(this.$refs.statusChart);

      const typeChartOptions = {
        // title: {
        //   text: '工单类型',
        //   left: 'center'
        // },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: this.$t('home.orderTypes'),
            type: 'pie',
            radius: '50%',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      const statusChartOptions = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: []
        },
        toolbox: {
          show: true,
          orient: 'vertical',
          left: 'right',
          top: 'center',
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar', 'stack'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: []
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: []
      };

      typeChart.setOption(typeChartOptions);
      statusChart.setOption(statusChartOptions);
    },
    navigateToMyOrders(status, type = 'repair') {
      let path = '';
      switch (type) {
        case 'repair':
          path = '/maintenanceOrder/my';
          break;
        case 'cleaning':
          path = '/cleaningOrder/my';
          break;
        case 'school':
          path = '/facilityOrder/my';
          break;
        case 'complaint':
          path = '/complaintOrder/all';
          break;
        default:
          path = '/maintenanceOrder/my';
      }
      this.$router.push({
        path: path,
        query: { status, type }
      });
    },
    handleResize() {
      this.isMobile = window.innerWidth <= 768;
    },
    startTabRotation() {
      this.tabInterval = setInterval(() => {
        const tabs = ['repair', 'cleaning', 'school', 'complaint'];
        const currentIndex = tabs.indexOf(this.activeTab);
        const nextIndex = (currentIndex + 1) % tabs.length;
        this.activeTab = tabs[nextIndex];
      }, 5000);
    },
    stopTabRotation() {
      if (this.tabInterval) {
        clearInterval(this.tabInterval);
        this.tabInterval = null;
      }
    },
    handleTabClick() {
      this.stopTabRotation();
      this.startTabRotation();
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
}

.three-d-text {
  font-size: 72px;
  color: white;
  text-shadow: 2px 2px 4px #000;
  cursor: pointer;
}

.work-order-text {
  font-size: 24px;
  color: white;
  text-shadow: 2px 2px 4px #000;
  margin-top: -50px;
}

.work-order-card {
  position: relative;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.5s ease;
}

.work-order-card:hover {
  transform: scale(1.05);
}

.work-order-card::after {
  content: '';
  position: absolute;
  bottom: 10px;
  right: 10px;
  width: 50%;
  height: 50%;
  background-size: contain;
  background-repeat: no-repeat;
  opacity: 0.5;
}

.work-order-card.unassigned {
  background-color: #bd24bd;
}

.work-order-card.unassigned::after {
  background-image: url('../assets/svg/unassigned-bg.svg');
}

.work-order-card.unprocessed {
  background-color: #fce148;
}

.work-order-card.unprocessed::after {
  background-image: url('../assets/svg/unprocessed-bg.svg');
}

.work-order-card.completed {
  background-color: #4aff4a;
}

.work-order-card.completed::after {
  background-image: url('../assets/svg/completed-bg.svg');
}

.work-order-card.incomplete {
  background-color: #f34242;
}

.work-order-card.incomplete::after {
  background-image: url('../assets/svg/incomplete-bg.svg');
}

.work-order-col {
  margin-bottom: 20px;
}

.reduced-height {
  height: 75%;
}

.reduced-height-body {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>

