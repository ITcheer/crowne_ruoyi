<template>
  <div class="app-container home">
    <!-- <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          欢迎使用若依后台管理框架
        </blockquote>
      </el-col>
    </el-row> -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :sm="24" :lg="12">
        <el-card class="reduced-height">
          <div slot="header" class="clearfix">
            <span>我的工单</span>
          </div>
          <div class="body reduced-height-body">
            <el-row :gutter="20">
              <el-col :sm="24" :lg="12" class="work-order-col reduced-height">
                <el-card class="work-order-card unassigned" @click.native="navigateToMyOrders('Undistributed')">
                  <div class="body">
                    <p class="three-d-text">{{ unassignedCount }}</p>
                    <div class="work-order-text">未分配</div>
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
                    <div class="work-order-text">处理中</div>
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
                    <div class="work-order-text">已完成</div>
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
                    <div class="work-order-text">未完成</div>
                    <el-icon style="font-size: 48px; color: white;">
                      <i class="el-icon-s-release"></i>
                    </el-icon>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-card>
        <el-row :gutter="20" style="margin-top: 20px;">
          <el-col :span="12">
            <el-card>
              <div slot="header" class="clearfix">
                <span>系统通知</span>
              </div>
              <div class="body" style="text-align: center; font-size: 24px; padding: 10px;">
                <el-timeline>
                  <el-timeline-item v-for="item in notifications" :key="item.id" :timestamp="item.timestamp">
                    {{ item.content }}
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
              <div slot="header" class="clearfix">
                <span>帮助手册</span>
              </div>
              <div class="body" style="text-align: center; font-size: 24px; padding: 10px;">
                <ul>
                  <li v-for="item in helpManuals" :key="item.id">
                    <el-link :href="item.link" target="_blank">{{ item.title }}</el-link>
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
            <span>工单统计</span>
          </div>
          <div class="body" style="text-align: center; font-size: 24px; padding: 10px;">
            <el-row :gutter="20">
              <el-col :sm="24" :lg="12">
                <el-card>
                  <div slot="header" class="clearfix">
                    <span>总工单数</span>
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
                    <span>今日新增</span>
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
                    <span>工单类型</span>
                  </div>
                  <div class="body">
                    <div ref="typeChart" style="height: 300px;"></div>
                  </div>
                </el-card>
              </el-col>
              <el-col :sm="24" :lg="12">
                <el-card>
                  <div slot="header" class="clearfix">
                    <span>工单状态</span>
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
import { listMyMaintenanceOrder } from "@/api/maintenanceOrder/my/maintenanceOrderMy";
import { listMaintenanceOrder } from "@/api/maintenanceOrder/all/maintenanceOrderAll";
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
        { id: 1, timestamp: '2023-10-01', content: '系统维护通知' },
        { id: 2, timestamp: '2023-10-02', content: '新功能上线通知' }
      ],
      helpManuals: [
        { id: 1, title: '如何创建工单', link: 'https://example.com/help/create-ticket' },
        { id: 2, title: '如何处理工单', link: 'https://example.com/help/process-ticket' },
        { id: 3, title: '工单状态说明', link: 'https://example.com/help/ticket-status' }
      ]
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initCharts();
      this.getMyWorkOrderCounts();
      this.getAllWorkOrderCounts();
    });
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
    },
    getAllWorkOrderCounts() {
      const params = {
        pageNum: 1,
        pageSize: 1000 // 假设最大数量
      };
      listMaintenanceOrder(params).then(response => {
        const orders = response.rows;
        this.totalOrders = orders.length;
        const today = new Date().toISOString().slice(0, 10);
        this.newOrdersToday = orders.filter(order => order.date.slice(0, 10) === today).length;
        this.updateCharts(orders);
      });
    },
    updateCharts(orders) {
      const defaultStatuses = ['Undistributed', 'On Process', 'Resolved', 'Can not Resolve'];
      const defaultTypes = ['Maintain', 'Illumination', 'Install', 'Aircon'];

      const statusData = orders.reduce((acc, order) => {
        const status = order.processingStatus;
        const type = order.maintenanceType;
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
        const type = order.maintenanceType;
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
            name: '工单类型',
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
    navigateToMyOrders(status) {
      this.$router.push({
        path: '/maintenanceOrder/my',
        query: { status }
      });
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

