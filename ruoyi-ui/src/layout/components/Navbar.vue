<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>
    <ChatBox ref="chatbox" />
    <div class="right-menu">

      <el-tooltip :content="$t('navbar.language')" effect="dark" placement="bottom">
        <svg t="1738668255433" class="icon right-menu-item hover-effect" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="10619" width="32" height="32" @click="toggleLanguage">
          <path d="M128 576a21.333333 21.333333 0 0 1 20.992 17.493333L149.333333 597.333333v213.333334a64 64 0 0 0 57.856 63.701333L213.333333 874.666667h128a21.333333 21.333333 0 0 1 3.84 42.325333L341.333333 917.333333H213.333333a106.666667 106.666667 0 0 1-106.453333-99.669333L106.666667 810.666667v-213.333334a21.333333 21.333333 0 0 1 21.333333-21.333333z m682.666667-469.333333a106.666667 106.666667 0 0 1 106.453333 99.669333L917.333333 213.333333v213.333334a21.333333 21.333333 0 0 1-42.325333 3.84L874.666667 426.666667V213.333333a64 64 0 0 0-57.856-63.701333L810.666667 149.333333h-128a21.333333 21.333333 0 0 1-3.84-42.325333L682.666667 106.666667h128zM320 547.84c9.941333 0 16.768-2.218667 20.48-6.613333a26.666667 26.666667 0 0 0 5.546667-17.706667v-87.893333h105.386666c10.538667 0 18.986667-0.725333 25.386667-2.133334a30.72 30.72 0 0 0 14.933333-7.253333 25.685333 25.685333 0 0 0 7.04-14.293333c1.152-6.101333 1.706667-13.866667 1.706667-23.253334V259.413333c0-10.24-0.64-18.474667-1.92-24.746666a27.008 27.008 0 0 0-7.253333-14.506667 26.922667 26.922667 0 0 0-14.933334-6.613333 172.16 172.16 0 0 0-25.386666-1.493334H346.026667V158.293333c0-9.386667-2.133333-15.786667-6.4-19.2-4.266667-3.413333-10.794667-5.12-19.626667-5.12-9.386667 0-16.085333 1.706667-20.053333 5.12-3.968 3.413333-5.973333 9.813333-5.973334 19.2v53.76H191.146667c-10.24 0-18.56 0.64-24.96 1.92a29.354667 29.354667 0 0 0-14.933334 7.04 25.685333 25.685333 0 0 0-7.04 14.293334c-1.152 6.101333-1.706667 13.994667-1.706666 23.68v129.706666c0 9.088 0.554667 16.725333 1.706666 22.826667a26.88 26.88 0 0 0 7.04 14.506667c3.541333 3.541333 8.533333 6.058667 14.933334 7.466666 6.4 1.408 14.72 2.133333 24.96 2.133334h102.826666v87.466666c0 9.088 1.578667 15.488 4.693334 19.2 3.114667 3.712 10.24 5.546667 21.333333 5.546667z m116.906667-162.986667H346.026667V262.826667h87.466666c6.528 0 10.752 0.768 12.586667 2.346666 1.834667 1.578667 2.773333 5.034667 2.773333 10.453334v94.293333c0 6.528-0.554667 10.666667-1.706666 12.373333-1.152 1.706667-4.565333 2.56-10.24 2.56z m-142.933334 0H205.226667c-4.821333 0-7.893333-0.768-9.173334-2.346666-1.28-1.578667-1.92-5.76-1.92-12.586667V275.626667c0-5.674667 0.554667-9.258667 1.706667-10.666667 1.152-1.408 3.84-2.133333 8.106667-2.133333h90.026666v122.026666zM609.92 857.6c10.794667 0 18.474667-1.706667 23.04-5.12 4.565333-3.413333 6.826667-9.685333 6.826667-18.773333s-2.261333-15.36-6.826667-18.773334c-4.565333-3.413333-12.245333-5.12-23.04-5.12h-103.253333v-81.92h98.986666c9.088 0 15.658667-1.792 19.626667-5.333333 3.968-3.541333 5.973333-9.6 5.973333-18.133333 0-8.533333-2.005333-14.72-5.973333-18.56-3.968-3.84-10.538667-5.76-19.626667-5.76h-98.986666v-72.106667h103.253333c8.234667 0 14.634667-1.792 19.2-5.333333 4.565333-3.541333 6.698667-9.728 6.4-18.56-0.298667-8.832-2.474667-15.018667-6.613333-18.56-4.138667-3.541333-10.325333-5.333333-18.56-5.333334h-131.84c-9.088 0-15.914667 1.706667-20.48 5.12-4.565333 3.413333-6.826667 9.514667-6.826667 18.346667v250.453333c0 9.088 2.432 15.274667 7.253333 18.56 4.821333 3.285333 11.52 4.906667 20.053334 4.906667h131.413333z m240.64 4.266667c7.978667 0 14.293333-1.706667 18.986667-5.12a15.36 15.36 0 0 0 7.04-12.8v-139.52c0-13.653333-1.92-25.6-5.76-35.84a67.242667 67.242667 0 0 0-16.213334-25.386667 68.181333 68.181333 0 0 0-25.173333-15.146667 100.266667 100.266667 0 0 0-33.066667-5.12 58.794667 58.794667 0 0 0-39.04 13.866667 69.333333 69.333333 0 0 0-12.8 13.866667c-3.541333 5.12-6.613333 10.24-9.173333 15.36v-20.906667c0-5.12-2.474667-9.386667-7.466667-12.8a32.042667 32.042667 0 0 0-18.56-5.12 34.133333 34.133333 0 0 0-18.986666 5.12c-5.248 3.413333-7.893333 7.68-7.893334 12.8v198.826667c0 4.565333 2.474667 8.661333 7.466667 12.373333 4.992 3.712 11.306667 5.546667 18.986667 5.546667s13.994667-1.792 18.986666-5.333334c4.992-3.541333 7.466667-7.765333 7.466667-12.586666v-120.746667c0-10.794667 1.194667-19.712 3.626667-26.666667a39.253333 39.253333 0 0 1 9.813333-16.213333 32.938667 32.938667 0 0 1 14.933333-7.893333c5.845333-1.408 12.16-2.133333 18.986667-2.133334 8.234667 0 15.061333 1.152 20.48 3.413334a27.306667 27.306667 0 0 1 12.586667 10.24c2.986667 4.565333 5.034667 10.325333 6.186666 17.28 1.152 6.954667 1.706667 15.018667 1.706667 24.106666v118.613334c0 5.12 2.474667 9.386667 7.466667 12.8 4.992 3.413333 11.434667 5.12 19.413333 5.12z" fill="#515151" p-id="10620"></path>
        </svg>
      </el-tooltip>

      <search id="header-search" class="right-menu-item" v-if="device !== 'mobile'" />

      <!-- <el-tooltip content="源码地址" effect="dark" placement="bottom">
        <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />
      </el-tooltip>

      <el-tooltip content="文档地址" effect="dark" placement="bottom">
        <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />
      </el-tooltip> -->
      
      <screenfull id="screenfull" class="right-menu-item hover-effect" v-if="device !== 'mobile'"  />

      <el-tooltip content="Ai-ChatBot" effect="dark" placement="bottom">
        <svg t="1737366341576" class="icon right-menu-item hover-effect" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3208" width="32" height="32" @click="toggleChatbox(true)">
          <path d="M576 85.333333c0 18.944-8.234667 35.968-21.333333 47.701334V213.333333h213.333333a128 128 0 0 1 128 128v426.666667a128 128 0 0 1-128 128H256a128 128 0 0 1-128-128V341.333333a128 128 0 0 1 128-128h213.333333V133.034667A64 64 0 1 1 576 85.333333zM256 298.666667a42.666667 42.666667 0 0 0-42.666667 42.666666v426.666667a42.666667 42.666667 0 0 0 42.666667 42.666667h512a42.666667 42.666667 0 0 0 42.666667-42.666667V341.333333a42.666667 42.666667 0 0 0-42.666667-42.666666H256z m-170.666667 128H0v256h85.333333v-256z m853.333334 0h85.333333v256h-85.333333v-256zM384 618.666667a64 64 0 1 0 0-128 64 64 0 0 0 0 128z m256 0a64 64 0 1 0 0-128 64 64 0 0 0 0 128z" fill="#000000" p-id="3209"></path>
        </svg>
      </el-tooltip>

      <el-dropdown class="right-menu-item hover-effect" trigger="click">
        <el-tooltip :content="$t('navbar.personalMessages')" effect="dark" placement="bottom">
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
              {{ $t('navbar.more') }} <i class="el-icon-arrow-down"></i>
            </el-button>
          </div>
          <div v-if="showMore" style="text-align: center; margin-top: 10px;">
            <el-button type="text" @click="showLessNotifications" style="color: #409EFF; font-weight: bold;">
              {{ $t('navbar.collapse') }} <i class="el-icon-arrow-up"></i>
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
            <el-dropdown-item>{{ $t('navbar.personalCenter') }}</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>{{ $t('navbar.layoutSettings') }}</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>{{ $t('navbar.logout') }}</span>
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
import ChatBox from '@/components/ChatBox'
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
    RuoYiDoc,
    ChatBox
  },
  data() {
    return {
      hasUnread: false,
      userId: '',
      notifications: [],
      notificationInterval: null, // 定时器ID
      showMore: false,
      currentLanguage: this.$i18n.locale
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
      } else if (notification.notificationSource === '清洁工单模块') {
        this.$router.push('/cleaningOrder/my');
      } else if (notification.notificationSource === '校工工单模块') {
        this.$router.push('/facilityOrder/my');
      } else if (notification.notificationSource === '投诉工单模块') {
        this.$router.push('/ComplaintOrder/all');
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
    },
    toggleChatbox(visible) {
      this.$refs.chatbox.toggleChatbox(visible);
    },
    changeLanguage(lang) {
      this.$i18n.locale = lang;
    },
    toggleLanguage() {
      this.currentLanguage = this.currentLanguage === 'zh' ? 'en' : 'zh';
      this.$i18n.locale = this.currentLanguage;
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

.language-switch {
  align-items: center;
}
</style>
