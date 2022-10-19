<template>
  <div style=" width: 100%">
<!--// todo 弹窗亦或抽屉都无法显示   -->

<!--    <el-header height="40px" style="">
      &lt;!&ndash;搜索区域&ndash;&gt;
      <div style="margin: 10px 0; ">
        <el-input v-model="search" placeholder="请输入要搜索的用户昵称" style="width: 240px" clearable></el-input>
        <el-button style="margin-left: 5px" type="primary" @click="findContact">
          查询
        </el-button>

        <el-drawer
            title="用户列表"
            :visible.sync="showSearch"
            :direction="rtl"
            :append-to-body="true">

          <el-table :data="userList">
            <el-table-column
                property="nickname"
                label="昵称"
            ></el-table-column>
          </el-table>

        </el-drawer>

      </div>
    </el-header>-->

    <el-main style="height: 84vh">
      <el-container style="height: 78vh">
        <el-aside style="border-left: 1px solid #ccc; border-right: 1px solid #ccc;">
          <div style="text-align: center; height: 40px; margin-top: 14px">消息列表</div>
          <el-menu style="">
            <div  v-for="c in contactList" :key="c.contactId">
              <el-menu-item @click="choose(c)" style="margin-top: 8px">
                <el-avatar :src="c.avatar"></el-avatar>
                {{ c.nickname }}
              </el-menu-item>
            </div>
          </el-menu>
        </el-aside>
        <el-main>
          <el-container style="">
            <el-main style="height: 54vh;">

              <el-scrollbar max-height="54vh" style="">

                <el-form v-for="(message, index) in nowContact.messageList">
                  <div style="margin-top: 10px">
                    <div style="text-align: center; color: #8c939d">
                      {{ message.sendTime }}
                    </div>
                    <div v-if="message.fromId === mine.id" style="text-align: right">
                      <el-tag effect="plain" style="font-size: medium">
                        {{ message.content }}
                      </el-tag>
                      <el-avatar :src="mine.avatar"></el-avatar>
                    </div>
                    <div v-else>
                      <el-avatar :src="nowContact.avatar"></el-avatar>
                      <el-tag effect="plain" style="font-size: medium">
                        {{ message.content }}
                      </el-tag>
                    </div>
                  </div>
                </el-form>

              </el-scrollbar>

            </el-main>

            <el-footer style="height: 18vh;" v-if="nowContact">
              <el-input type="textarea" v-model="sendContent" placeholder="" :rows="4" >
              </el-input>
              <div style="text-align: right; margin-top: 5px;">
                <el-button type="success" @click="send">
                  发送
                </el-button>
              </div>
            </el-footer>
          </el-container>
        </el-main>
      </el-container>
    </el-main>
  </div>
</template>

<script>
import request from "@/utils/request";
import { getNowTime } from "@/utils/util.js";

export default {
  name: "Message",
  data() {
    return {
      contactList: [],
      nowContact: {
        messageList: [],
      },
      mine: {},
      search: '',
      sendContent: '',
      userList: [],
      showSearch: false,
    }
  },
  created() {
    this.load()
  },
  methods: {
    async load() {
      await request.get("/message").then(res => {
        if (res.code === '00000') {
          this.contactList = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
      await request.get("/user").then(res => {
        if (res.code === '00000') {
          this.mine = res.data
        } else {
          this.$message.error(res.msg)
        }
      });

      let send = this.$route.query

      if (!send || !send.targetUser) {
        return
      }

      let newUser = JSON.parse(send.targetUser)

      /* 此联系人是自己 */
      if (newUser.userId === this.mine.id) {
        return
      }

      /* 此联系人在列表里已经存在 */
      for (let contact of this.contactList) {
        if (contact.contactId === newUser.userId) {
          return
        }
      }

      let newContact = {}

      newContact.contactId = newUser.userId
      newContact.nickname = newUser.nickname
      newContact.avatar = newUser.avatar
      this.contactList.push(newContact)

    },
    findContact() {
      if (this.showSearch) {
        this.showSearch = !this.showSearch
        return
      }

      if (this.search === '') {
        this.$message.error("要搜索的昵称不能为空")
        return
      }

      request.get("/user/nickname", {params: {nickname: this.search}}).then(res => {
        if (res.code === '00000') {
          this.userList = res.data
          this.showSearch = !this.showSearch
        }
        else {
          this.$message.error(res.msg)
        }
      })
    },
    send() {
      let temp = getNowTime()

      let msg = {
        toId: this.nowContact.contactId,
        fromId: this.mine.id,
        content: this.sendContent,
        sendTime: temp,
      }

      request.post("/message/send", msg).then(res => {
        if (res.code === '00000') {
          if (this.nowContact.messageList) {
            this.nowContact.messageList.push(msg)
          }
          else {
            this.nowContact.messageList = [ msg ]
          }
        }
        else {
          this.$message.error(res.msg)
        }
      })

      this.sendContent = ''
    },
    choose(someone) {
      this.nowContact = someone
    }
  },

}
</script>

<style scoped>

</style>