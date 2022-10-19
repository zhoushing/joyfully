<template>
  <div style="padding: 5px; width: 100%">
    <el-backtop/>
    <div style="margin: 5px 0; text-align: right; max-height: 500px">
      <el-button @click="load" type="primary" size="normal">刷新</el-button>
      &emsp;看看
      <el-radio-group v-model="optionNum">
        <el-radio :label=5 size="medium">5</el-radio>
        <el-radio :label=10 size="medium">10</el-radio>
      </el-radio-group>
      个
      &emsp;
      <swiper
          :direction="'vertical'"
          :modules="modules"
          :slides-per-view="1"
          :pagination="{ clickable: true }"
      >
        <swiper-slide v-for="(file, index) in fileList">
          <el-container>

            <el-header style="text-align: center; font-size: 16px; height: 120px; padding-top: 80px;">
              <el-row>
                <el-col :span="24">
                  <el-row>
                    <el-col :span="10">
                      <span>
                        <div>文件名</div>
                        {{ file.name }}
                      </span>
                    </el-col>
                    <el-col :span="5">
                        <span>
                          <div>文件类型</div>
                          {{ file.type }}
                        </span>
                    </el-col>
                    <el-divider direction="vertical" style="height: 40px"></el-divider>
                    <el-col :span="5">
                      <span>
                        <div>点赞</div>
                        {{ file.praiseCount }}
                      </span>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-header>

            <el-container>
              <el-main>
                <el-header style="height: 28vh">
                  <el-card style="font-size: x-large; font-weight: bolder; height: 100%">
                    <el-space size="large" direction="vertical" style="width: 100%" fill>
                      <div style="height: 95px">
                          <el-button @click="loadFilePreview(file)" >点击预览</el-button>
                      </div>
                      <div style="text-align: right">
                        <el-button type="" size="mini" @click="evaluate('file', file, false, false, '')">
                          赞
                        </el-button>
                        <el-button type="" size="mini" @click="evaluate('file', file, true, false, '')">
                          踩
                        </el-button>
                        <el-button type="danger" size="mini" @click="report('file', file)" >
                          举报
                        </el-button>
                      </div>
                    </el-space>
                  </el-card>
                </el-header>

                <el-divider />
              </el-main>
              <el-aside width="260px" style="padding-top: 60px; text-align: left">
                <el-card style="width: 230px;">
                  <div>
                    <el-row :gutter="20">
                      <el-col :span="8">
                        <el-avatar :src="file.fileOwnerInfo.avatar"></el-avatar>
                      </el-col>
                      <el-col :span="16">
                        <div style="font-size: medium">{{ file.fileOwnerInfo.nickname }}</div>
                        <div style="font-size: x-small">{{ file.fileOwnerInfo.introduction }}</div>
                      </el-col>
                    </el-row>
                  </div>

                  <el-divider />

                  <div>
                    <el-row :gutter="10" style="text-align: center">
                      <el-col :span="12">
                        <div>文件</div>
                        <div>{{ file.fileOwnerInfo.fileCount }}</div>
                      </el-col>
                      <el-col :span="12">
                        <el-button @click="sendMessage(file.fileOwnerInfo)">
                          <i class="el-icon-message"></i>
                          发私信
                        </el-button>
                      </el-col>
                    </el-row>
                  </div>

                </el-card>
              </el-aside>
            </el-container>
          </el-container>
        </swiper-slide>
      </swiper>

      <el-dialog center title="举报" v-model="reportVis" width="40%" >
        <template #default="scope">
          <el-form style="text-align: left">
            <el-form-item>
              举报目标内容：<br />
              <el-scrollbar max-height="200px">
                {{ reportObjShow }}
              </el-scrollbar>
            </el-form-item>
            <el-form-item>
              举报理由
              <el-input type="textarea" autosize v-model="reportReason" style="width: 98%"></el-input>
              <br />
            </el-form-item>
            <el-form-item>
              <el-button type="success" size="mini" @click="reportSubmit">提交</el-button>
            </el-form-item>
          </el-form>
        </template>
      </el-dialog>

    </div>
  </div>

</template>

<script>
import request from "../utils/request";

// import Swiper core and required modules
import { Pagination } from "swiper";
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from "swiper/vue/swiper-vue.js";
// Import Swiper styles
import "swiper/swiper.min.css";
import "swiper/modules/pagination/pagination.min.css";

import {Base64} from 'js-base64'

export default {
  components: {
    Swiper,
    SwiperSlide,
  },
  setup() {
    return {
      modules: [Pagination]
    };
  },
  name: "FileSquare",
  data() {
    return {
      fileList: [],
      activeName: -1,
      optionNum: 5,
      answerList: [],
      isEvaluate: false,
      addAnswerVis: false,
      answerContent: '',
      reportObj: {},
      reportObjShow: '',
      reportVis: false,
      reportReason: '',
      reportTarget: '',
      filePreviewVis: false,
      fileTarget: '/file/',
      fileUploadTarget: '/file/upload',
      fileDownLoadTarget: '/file/download/',
      fileDownLoadByFullNameTarget: '/file/downloadByFullName/',
      tencent_cos: window.server.tencent_cos + '/',
      kkFile: window.server.kk_file,
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/file/findByPraise", {params: {limit: this.optionNum}}).then(res => {
        if (res.code === '00000') {
          this.fileList = res.data
          console.log(this.fileList)
        }
        else {
          $this.message.message(res.msg);
        }
      });
    },
    acceptFile(e) {
      const allowHook = {
        video: '.mp4',
        audio: '.mp3,',
        file: '.doc, .docx, .pdf',
        excel: '.xlsx, .xls',
        img: '.jpg, .png, .gif'
      }
      if (e) {
        return allowHook[e];
      }
      let str = '';
      for (const k in allowHook) {
        str += allowHook[k] + " ,";
      }
      return str;
    },
    evaluate(targetObj, row, isBelittle, isReport, reportReason) {
      let evaluation = {}
      evaluation.target = targetObj + ","

      if (targetObj === 'answer') {
        evaluation.target = evaluation.target + row.userId + "_" + row.questionId
      }
      else if (targetObj === 'question'){
        evaluation.target = evaluation.target + row.id
      }
      else if (targetObj === 'file'){
        evaluation.target = evaluation.target + row.uuid
      }

      evaluation.belittle = isBelittle
      evaluation.report = isReport
      evaluation.reportReason = reportReason

      let action = isReport? "举报": (isBelittle? "差评": "好评");

      request.post("/evaluation", evaluation).then(res => {
        if (res.code === '00000') {
          this.$message({
            type: "success",
            message: action + "成功"
          });

          if (!isBelittle) {
            row.praiseCount += 1
          }
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    },
    report(reportTarget, row) {
      this.reportTarget = reportTarget
      this.reportObj = row

      if (reportTarget === 'question') {
        this.reportObjShow = row.issue
      }
      else if (reportTarget === 'answer') {
        this.reportObjShow = row.content
      }
      else if (reportTarget === 'file') {
        this.reportObjShow = row.name
      }

      this.reportVis = true
    },
    reportSubmit() {
      let temp = this.evaluate(this.reportTarget, this.reportObj, true, true, this.reportReason)
      this.reportTarget = ''
      this.reportObj = {}
      this.reportObjShow = ''
      this.reportReason = ''
      this.reportVis = false
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
    },
  }
}
</script>

<style scoped>
.swiper {
  height: 86vh;
}
.swiper-slide {
  height: 86vh;
  font-size: 22px;
  text-align: center;
}
</style>

