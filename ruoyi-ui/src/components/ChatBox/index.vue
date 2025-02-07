<template>
  <el-drawer
    size="400px"
    :visible.sync="visible"
    :with-header="false"
    :append-to-body="true"
    :show-close="false"
    custom-class="chatbox-drawer"
    @close="toggleChatbox(false)"
    @wrapper-click="handleWrapperClick"
  >
    <div class="chatbox" @click.stop>
      <div class="chatbox-header">
        <svg t="1737366341576" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3208" width="32" height="32">
          <path d="M576 85.333333c0 18.944-8.234667 35.968-21.333333 47.701334V213.333333h213.333333a128 128 0 0 1 128 128v426.666667a128 128 0 0 1-128 128H256a128 128 0 0 1-128-128V341.333333a128 128 0 0 1 128-128h213.333333V133.034667A64 64 0 1 1 576 85.333333zM256 298.666667a42.666667 42.666667 0 0 0-42.666667 42.666666v426.666667a42.666667 42.666667 0 0 0 42.666667 42.666667h512a42.666667 42.666667 0 0 0 42.666667-42.666667V341.333333a42.666667 42.666667 0 0 0-42.666667-42.666666H256z m-170.666667 128H0v256h85.333333v-256z m853.333334 0h85.333333v256h-85.333333v-256zM384 618.666667a64 64 0 1 0 0-128 64 64 0 0 0 0 128z m256 0a64 64 0 1 0 0-128 64 64 0 0 0 0 128z" fill="#ffffff" p-id="3209"></path>
        </svg>
        <span>Ai-ChatBot</span>
        <i class="el-icon-close chatbox-close" @click="toggleChatbox(false)"></i>
      </div>
      <div class="chatbox-content">
        <div v-for="(message, index) in messages" :key="index" class="message" :class="{'chatpdfAsk': message.user}">
          <div class="chatpdfContent">
            {{ message.text }}
          </div>
        </div>
      </div>
      <div class="chatbox-input chatpdfArea">
        <textarea v-model="input" @keyup.enter="sendMessage" placeholder="输入消息..."></textarea>
        <button type="button" @click="sendMessage">发送</button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      input: '',
      messages: [
        { text: '你好！我是你的智能助手，有什么我可以帮你的吗？', user: false },
        { text: '请问你能做什么？', user: true },
        { text: '我可以回答你的问题，提供信息，帮助你完成任务。比如查询工单状态、统计信息等。', user: false },
        { text: '目前有多少个工单正在处理中？', user: true },
        { text: '目前有 5 个工单正在处理中。这些工单主要涉及维修和安装任务。', user: false },
        { text: '有多少个工单已经解决了？', user: true },
        { text: '目前有 10 个工单已经解决了。如果你需要查看具体的工单列表，请告诉我。', user: false },
        { text: '请列出所有正在处理的工单。', user: true },
        { text: '以下是所有正在处理的工单：\n1. 工单 923175524349: 幼儿园雨棚堵水口铁皮脱落\n2. 工单 924091912610: 幼儿园岗亭门背丝扣坏需更换\n3. 工单 924092036743: 幼儿园铁门生锈\n4. 工单 924092209576: 体育馆靠近ULC成人女卫生间墙面破损\n5. 工单 924192821958: sw412门把手坏需维修。如果你需要查看某个工单的详细信息，请告诉我工单编号。', user: false },
        { text: '当前工单中维修工单占比多少？', user: true },
        { text: '当前工单中，维修工单占比 70%。如果你需要查看具体的维修工单列表，请告诉我。', user: false },
        { text: '当前我的工单中主要体现哪方面的问题，对此有什么建议？', user: true },
        { text: '根据当前的工单数据，主要问题集中在门把手损坏和铁门生锈。这些问题可能是由于使用频率高和维护不及时导致的。建议定期检查和维护这些设施，及时更换损坏的部件，以减少类似问题的发生。', user: false }
      ]
    };
  },
  methods: {
    toggleChatbox(visible) {
      this.visible = visible;
    },
    sendMessage() {
      if (this.input.trim() === '') return;
      this.messages.push({ text: this.input, user: true });
      this.input = '';
      setTimeout(() => {
        this.messages.push({ text: '机器人回复: ' + this.messages[this.messages.length - 1].text, user: false });
      }, 1000);
    },
    handleWrapperClick() {
      this.visible = false;
    }
  }
};
</script>

<style scoped>
.chatbox-drawer {
  .el-drawer__body {
    padding: 0;
  }
}
.chatbox {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: linear-gradient(to bottom right, #dbe6fb, #f3f4f8);
  background-size: cover;
  background-attachment: fixed;
}
.chatbox-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background: #409EFF;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  border-bottom: 1px solid #ebeef5;
}
.chatbox-close {
  cursor: pointer;
  font-size: 20px;
}
.chatbox-content {
  flex: 1;
  padding: 15px;
  overflow-y: auto;
  background: #fff;
  border-top: 1px solid #ebeef5;
  border-bottom: 1px solid #ebeef5;
}
.message {
  margin-bottom: 15px;
  display: flex;
}
.chatpdfAsk {
  justify-content: flex-end;
}
.chatpdfAsk .chatpdfContent {
  background: linear-gradient(90deg, #2870EA 10.79%, #1B4AEF 87.08%);
  color: #fff;
}
.chatpdfContent {
  display: inline-block;
  border-radius: 8px;
  padding: 6px 12px;
  max-width: 500px;
  background: rgba(255, 255, 255, 0.6);
  font-size: 14px;
  box-shadow: 0px 0.3px 0.9px rgba(0, 0, 0, 0.12), 0px 1.6px 3.6px rgba(0, 0, 0, 0.16);
}
.chatbox-input {
  display: flex;
  padding: 5px 10px;
  background: #fff;
  border-top: 1px solid #ebeef5;
}
.chatbox-input textarea {
  flex: 1;
  border-color: #d9d9d9;
  resize: none;
  outline: none;
  padding: 0px 5px;
  height: 35px;
  line-height: 30px;
  color: #404040;
  border-radius: 10px 0px 0px 10px;
  transition: all 0.3s, height 0s;
}
.chatbox-input textarea:hover {
  border-color: #4096ff;
}
.chatbox-input button {
  height: 35px;
  color: #fff;
  background: linear-gradient(90deg, #2870EA 10.79%, #1B4AEF 87.08%);
  box-shadow: 0 2px 0 rgba(5, 145, 255, 0.1);
  border: none;
  padding: 0 20px;
  border-radius: 0px 8px 8px 0px;
  cursor: pointer;
}
.chatbox-input button:hover {
  background-color: #388aff;
}
</style>
