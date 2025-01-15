<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

    <div class="right-menu">
      <search id="header-search" class="right-menu-item" v-if="device !== 'mobile'" />

      <!-- <el-tooltip content="源码地址" effect="dark" placement="bottom">
        <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />
      </el-tooltip>

      <el-tooltip content="文档地址" effect="dark" placement="bottom">
        <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />
      </el-tooltip> -->

      <screenfull id="screenfull" class="right-menu-item hover-effect" v-if="device !== 'mobile'"  />

      <el-dropdown class="right-menu-item hover-effect" trigger="click">
        <el-tooltip content="个人消息" effect="dark" placement="bottom">
          <el-badge is-dot v-if="unreadNotifications.length > 0" class="badge-adjust">
            <i class="el-icon-bell" />
          </el-badge>
          <el-badge v-else class="badge-adjust">
            <i class="el-icon-bell" />
          </el-badge>
        </el-tooltip>
        <el-dropdown-menu slot="dropdown" class="notification-dropdown" style="width: 280px; max-height: 500px; overflow-y: auto;">
          <el-card v-for="(notification, index) in displayedNotifications" :key="notification.notificationId" class="notification-item" style="padding: 6px; border-bottom: 1px solid #f0f0f0; margin-bottom: 4px;" @click.native="navigateToModule(notification)">
            <div slot="header" class="notification-title" style="color: #666; font-weight: bold; font-size: 13px; margin-bottom: 2px; line-height: 1.2;">
              <i class="el-icon-message" style="margin-right: 5px;"></i>
              {{ notification.messageTitle }}
              <el-badge is-dot v-if="notification.readStatus === 0" class="title-badge"></el-badge>
            </div>
            <div class="notification-content" style="font-size: 12px; color: #666; margin-bottom: 2px;">{{ notification.messageContent }}</div>
            <div class="notification-time" style="font-size: 11px; color: #999; text-align: right;">{{ formatDate(notification.createTime) }}</div>
          </el-card>
          <div v-if="showMoreButton" style="text-align: center; margin-top: 10px;">
            <el-button type="text" @click="showMoreNotifications" style="color: #409EFF; font-weight: bold;">
              更多 <i class="el-icon-arrow-down"></i>
            </el-button>
          </div>
          <div v-if="showMore" style="text-align: center; margin-top: 10px;">
            <el-button type="text" @click="showLessNotifications" style="color: #409EFF; font-weight: bold;">
              收起 <i class="el-icon-arrow-up"></i>
            </el-button>
          </div>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYiGit from '@/components/RuoYi/Git'
import RuoYiDoc from '@/components/RuoYi/Doc'
import { listPersonalMessageNotificationByUserId, updatePersonalMessageNotification } from '@/api/notification/personalMessageNotification'
import { getUserProfile } from "@/api/system/user"

export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc
  },
  data() {
    return {
      hasUnread: false,
      userId: '',
      notifications: [],
      notificationInterval: null, // 定时器ID
      showMore: false
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    },
    unreadNotifications() {
      return this.notifications.filter(notification => notification.readStatus === 0);
    },
    sortedNotifications() {
      return this.notifications.slice().sort((a, b) => {
        if (a.readStatus === b.readStatus) {
          return new Date(b.createTime) - new Date(a.createTime);
        }
        return a.readStatus - b.readStatus;
      });
    },
    displayedNotifications() {
      return this.showMore ? this.sortedNotifications : this.sortedNotifications.slice(0, 3);
    },
    showMoreButton() {
      return this.sortedNotifications.length > 3 && !this.showMore;
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/index';
        })
      }).catch(() => {});
    },
    async fetchNotifications() {
      const response = await listPersonalMessageNotificationByUserId(this.userId)
      this.notifications = response.data
      this.hasUnread = this.notifications.some(notification => notification.readStatus === 0)
    },
    async fetchUserProfile() {
      const response = await getUserProfile()
      this.userId = response.data.userId
      this.fetchNotifications()
    },
    async navigateToModule(notification) {
      if (notification.readStatus === 0) {
        notification.readStatus = 1;
        await updatePersonalMessageNotification(notification);
      }
      if (notification.notificationSource === '维护工单模块') {
        this.$router.push('/maintenanceOrder/my');
      } else {
        // 其他模块的跳转逻辑留空待定
      }
    },
    startNotificationPolling() {
      // 每30秒轮询一次
      this.notificationInterval = setInterval(() => {
        this.fetchNotifications();
      }, 30000);
    },
    stopNotificationPolling() {
      // 清除定时器
      if (this.notificationInterval) {
        clearInterval(this.notificationInterval);
        this.notificationInterval = null;
      }
    },
    showMoreNotifications() {
      this.showMore = true;
    },
    showLessNotifications() {
      this.showMore = false;
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
    }
  },
  mounted() {
    this.fetchUserProfile();
    this.startNotificationPolling(); // 开始轮询
  },
  beforeDestroy() {
    this.stopNotificationPolling(); // 组件销毁前停止轮询
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }

    .badge-adjust {
      display: inline-block;
      position: relative;
      height: 45px; /* 调整高度 */

      .el-badge__content {
        position: absolute;
        top: 0;
        right: 0;
        transform: translate(50%, -50%);
        background-color: rgb(255, 0, 0);
        width: 8px;
        height: 8px;
        border-radius: 50%;
      }
    }

    .notification-dropdown {
      width: 300px;
      max-height: 450px; /* 增加高度 */
      overflow-y: auto;
    }

    .title-badge {
      position: absolute;
      top: 0;
      right: 0;
      transform: translate(50%, -50%);
      background-color: red;
      width: 8px;
      height: 8px;
      border-radius: 50%;
    }
  }
}
</style>
