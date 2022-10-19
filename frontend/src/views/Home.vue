<template>
  <div style="font-size: large; width: 100%">
    <el-header height="42vh" style="text-align: center">
      <el-row>
        <el-col :span="8">
          <p>优质问题</p>
          <el-carousel indicator-position="outside" :interval="4000" height="220px" v-loading="loading">
            <el-carousel-item v-for="(item, index) in questionList" :key="index">
              <el-card style="margin-top: 20px;margin-left: 24px; height: 200px; width: 92%">
                <div style="text-align: left; font-size: medium">
                  TOP&ensp;{{index + 1}}
                </div>
                <p style="height: 100px; padding-top: 10px">
                  {{ item.issue }}
                </p>
                <div style="text-align: right; font-size: medium" >
                  题主： {{ item.questionerInfo.nickname }}
                </div>
                <div style="text-align: right; font-size: medium">
                  点赞数： {{ item.praiseCount }}
                </div>
              </el-card>
            </el-carousel-item>
          </el-carousel>
        </el-col>
        <el-col :span="8">
          <p>优质资源</p>
          <el-carousel indicator-position="outside" :interval="4000" height="220px" v-loading="loading">
            <el-carousel-item v-for="(item, index) in fileList" :key="index">
              <el-card style="margin-top: 20px;margin-left: 24px; height: 200px; width: 92%">
                <div style="text-align: left; font-size: medium">
                  TOP&ensp;{{index + 1}}
                </div>
                <div style="padding-top: 10px;height: 100px">
                  <el-button size="larger" type="text" @click="loadFilePreview(item)" >{{ item.name + '.' + item.type }}</el-button>
                </div>
                <div style="text-align: right; font-size: medium" >
                  资源主： {{ item.fileOwnerInfo.nickname }}
                </div>
                <div style="text-align: right; font-size: medium">
                点赞数：{{ item.praiseCount }}
                </div>
              </el-card>
            </el-carousel-item>
          </el-carousel>
        </el-col>

        <el-col :span="8">
          <p>公告信息</p>
          <el-carousel indicator-position="outside" autoplay="false" :interval="3600000" height="220px" v-loading="loading">
            <el-carousel-item v-for="(item, index) in noticeList" :key="index">
              <el-card style="margin-top: 20px;margin-left: 24px; height: 200px; width: 92%">
                <p>
                {{ item.title }}
                </p>
                <p style="font-size: medium; height: 96px; padding-top: 10px">
                {{ item.content }}
                </p>
                <div style="text-align: right; font-size: small">
                  <p>
                    {{ item.date }}
                  </p>
                  <p>
                    {{ item.publisher }}
                  </p>
                </div>
              </el-card>
            </el-carousel-item>
          </el-carousel>
        </el-col>
      </el-row>
    </el-header>

    <el-divider/>

    <el-main style="text-align: center; height: 42vh">
      <el-row style="margin-top: -50px">
        <!--提问之星-->
        <el-col :span="2">
          <el-label>
            提
          </el-label>
          <br>
          <el-label>
            问
          </el-label>
          <br>
          <el-label>
            之
          </el-label>
          <br>
          <i class="el-icon-star-off"></i>
        </el-col>
        <el-col :span="6">
          <el-card style="width: 230px;">
            <div>
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-avatar :src="questioner.avatar"></el-avatar>
                </el-col>
                <el-col :span="16">
                  <div>{{ questioner.nickname }}</div>
                  <div>{{ questioner.introduction }}</div>
                </el-col>
              </el-row>
            </div>

            <el-divider />

            <div>
              <el-row :gutter="10" style="text-align: center">
                <el-col :span="12">
                  <div>提问数</div>
                  <div>{{ questioner.questionCount }}</div>
                </el-col>
                <el-col :span="12">
                  <el-button @click="sendMessage(questioner)">
                    <i class="el-icon-message"></i>
                    发私信
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-col>

        <!--回答之星-->
        <el-col :span="2">
          <el-label>
            回
          </el-label>
          <br>
          <el-label>
            答
          </el-label>
          <br>
          <el-label>
            之
          </el-label>
          <br>
          <i class="el-icon-star-off"></i>
        </el-col>
        <el-col :span="6">
          <el-card style="width: 230px;">
            <div>
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-avatar :src="responder.avatar"></el-avatar>
                </el-col>
                <el-col :span="16">
                  <div>{{ responder.nickname }}</div>
                  <div>{{ responder.introduction }}</div>
                </el-col>
              </el-row>
            </div>

            <el-divider />

            <div>
              <el-row :gutter="10" style="text-align: center">
                <el-col :span="12">
                  <div>回答数</div>
                  <div>{{ responder.answerCount }}</div>
                </el-col>
                <el-col :span="12">
                  <el-button @click="sendMessage(responder)">
                    <i class="el-icon-message"></i>
                    发私信
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-col>

        <!--资源之星-->
        <el-col :span="2">
          <el-label>
            资
          </el-label>
          <br>
          <el-label>
            源
          </el-label>
          <br>
          <el-label>
            之
          </el-label>
          <br>
          <i class="el-icon-star-off"></i>
        </el-col>
        <el-col :span="6">
          <el-card style="width: 230px;">
            <div>
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-avatar :src="fileOwner.avatar"></el-avatar>
                </el-col>
                <el-col :span="16">
                  <div>{{ fileOwner.nickname }}</div>
                  <div>{{ fileOwner.introduction }}</div>
                </el-col>
              </el-row>
            </div>

            <el-divider />

            <div>
              <el-row :gutter="10" style="text-align: center">
                <el-col :span="12">
                  <div>资源数</div>
                  <div>{{ fileOwner.fileCount }}</div>
                </el-col>
                <el-col :span="12">
                  <el-button @click="sendMessage(fileOwner)">
                    <i class="el-icon-message"></i>
                    发私信
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </div>
</template>

<script>

import request from '@/utils/request'
import {Base64} from 'js-base64'

export default {
  name: "Home",
  data() {
    return {
      questioner: {
        avatar: '',
        nickname: '',
        introduction: '',
        questionCount: '',
      },
      responder: {
        avatar: '',
        nickname: '',
        introduction: '',
        answerCount: '',
      },
      fileOwner: {
        avatar: '',
        nickname: '',
        introduction: '',
        fileCount: '',
      },
      questionList: [{
        questionerInfo: {
          nickname: '',
        }
      }],
      fileList: [{
        fileOwnerInfo: {
          nickname: '',
        }
      }],
      noticeList: [''],
      defaultLimit: 3,
      tencent_cos: window.server.tencent_cos + '/',
      kkFile: window.server.kk_file,
      loading: true,
      targetUser: {},
    }
  },
  created() {
    this.load();
  },
  methods: {
    async load() {
      request.get('/user/questioner').then(res => {
        if (res.code === '00000') {
          this.questioner = res.data[0]
        } else {
          this.$message.error(res.msg)
        }
      })
      request.get('/user/fileowner').then(res => {
        if (res.code === '00000') {
          this.fileOwner = res.data[0]
        } else {
          this.$message.error(res.msg)
        }
      })
      request.get('/user/responder').then(res => {
        if (res.code === '00000') {
          this.responder = res.data[0]
        } else {
          this.$message.error(res.msg)
        }
      })

      this.loading = true

      await request.get('/question/findByPraise', {params: {limit: this.defaultLimit}}).then(res => {
        if (res.code === '00000') {
          this.questionList = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
      await request.get('/file/findByPraise', {params: {limit: this.defaultLimit}}).then(res => {
        if (res.code === '00000') {
          this.fileList = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
      await request.get('/notice/recent', {params: {limit: this.defaultLimit}}).then(res => {
        if (res.code === '00000') {
          this.noticeList = res.data
        } else {
          this.$message.error(res.msg)
        }
      })

      this.loading = false
    },
    loadFilePreview(file) {
      let target = this.tencent_cos + file.uuid + '_' + file.name + '.' + file.type
      let url = this.kkFile + '/onlinePreview?url=' + encodeURIComponent(Base64.encode(target))
      window.open(url)
    },
    sendMessage(targetUser) {
      this.$router.push({
        name: "Message",
        query: {
          targetUser: JSON.stringify(targetUser),
        }
      })
    }
  }
}
</script>

<style scoped>
  .el-col {
    margin-top: 40px;
  }
  .el-card {}
</style>