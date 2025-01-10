<template>
  <el-dialog title="工单记录" :visible.sync="localVisible" width="800px" height="600px">
    <div class="chat-container">
      <div v-if="localLogList.length === 0" class="no-records">
        暂无记录
      </div>
      <div v-else>
        <div v-for="log in localLogList" :key="log.logId">
          <div v-if="log.actionType === '沟通'" :class="{'chat-message-left': log.userId === '-1', 'chat-message-right': log.userId !== '-1'}">
            <div class="chat-message">
              <div class="chat-message-content">
                <p>{{ log.actionDescription }}</p>
              </div>
              <div class="chat-message-info" :class="{'info-right': log.userId !== '-1'}">
                <span>{{ log.userName }}</span>
                <span style="margin-left: 10px;">{{ formatDate(log.actionTime) }}</span>
              </div>
            </div>
          </div>
          <div v-else class="chat-message-center">
            <div class="chat-message-content-center">
              <p>------ {{ log.actionDescription }} ------</p>
            </div>
            <div class="chat-message-info-center">
              <span>{{ log.userName }}</span>
              <span style="margin-left: 10px;">{{ formatDate(log.actionTime) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-input
        type="textarea"
        v-model="newMessage"
        placeholder="请输入消息"
        rows="2"
        @keyup.enter.native="handleSendMessage"
        class="rich-text-input"
      ></el-input>
      <el-button type="primary" @click="handleSendMessage" class="send-button">发送</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addMaintenanceOrderLog, listMaintenanceOrderLogs } from "@/api/maintenanceOrder/my/maintenanceOrderMy";
import { getUserProfile } from "@/api/maintenanceOrder/my/maintenanceOrderMy";

export default {
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    logList: {
      type: Array,
      required: true
    },
    issueId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      localVisible: this.visible,
      localLogList: [...this.logList],
      newMessage: "",
      currentUser: {
        userId: "",
        userName: ""
      }
    };
  },
  watch: {
    visible(val) {
      this.localVisible = val;
    },
    localVisible(val) {
      this.$emit('update:visible', val);
    },
    logList(val) {
      this.localLogList = [...val];
    }
  },
  computed: {
    isMobile() {
      return window.innerWidth <= 768;
    }
  },
  created() {
    this.getCurrentUser();
  },
  methods: {
    getCurrentUser() {
      getUserProfile().then(response => {
        this.currentUser.userId = response.data.userId;
        this.currentUser.userName = response.data.userName;
      });
    },
    formatDate(date) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit', weekday: 'short', hour: '2-digit', minute: '2-digit' };
      return new Date(date).toLocaleDateString('zh-CN', options).replace(/年|月/g, '/').replace('日', '');
    },
    handleSendMessage() {
      if (this.newMessage.trim() === "") {
        return;
      }
      const log = {
        issueId: this.issueId,
        actionType: "沟通",
        actionDescription: this.newMessage,
        userId: this.currentUser.userId,
        userName: this.currentUser.userName
      };
      addMaintenanceOrderLog(log).then(response => {
        if (response.code === 200) {
          this.refreshLogs();
          this.newMessage = "";
        } else {
          this.$message.error("发送消息失败");
        }
      });
    },
    refreshLogs() {
      listMaintenanceOrderLogs(this.issueId).then(response => {
        this.localLogList = response.data;
      });
    }
  }
};
</script>

<style scoped>
.chat-container {
  max-height: 400px;
  overflow-y: auto;
  padding: 10px;
}
.no-records {
  text-align: center;
  color: #888;
  font-size: 14px;
  margin-top: 20px;
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
.rich-text-input {
  width: calc(100% - 80px);
  margin-right: 10px;
}
.send-button {
  height: 100%;
  padding: 20px 20px;
}
</style>
