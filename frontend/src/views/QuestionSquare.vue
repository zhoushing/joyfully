<template>
  <div style="padding: 5px; width: 96%">
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
        <swiper-slide v-for="(random, index) in randomQuestions">
          <el-container>
            <el-header style="text-align: center; font-size: 16px; height: 80px; padding-top: 40px;">
              <el-row>
                <el-col :span="12">
                  <div>分类</div>
                  <el-tag v-for="c in random.categoryList" :key="c.id" :type="'primary'">
                  {{c.name}}
                  </el-tag>
                </el-col>
                <el-col :span="12">
                  <el-row>
                    <el-col :span="6">
                      <span>
                        <div>关注</div>
                        {{ random.attentionCount }}
                      </span>
                    </el-col>
                    <el-divider direction="vertical" style="height: 40px"></el-divider>
                    <el-col :span="6">
                      <span>
                        <div>点赞</div>
                        {{ random.praiseCount }}
                      </span>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-header>

            <el-container>
              <el-main>
                <el-header style="height: 30vh">
                  <el-card style="font-size: x-large; font-weight: bolder; height: 100%">
                    <el-space size="large" direction="vertical" style="width: 100%" fill>
                      <div style="text-align: left; height: 4vh">
                        <el-button type="success" size="mini" @click="attentionQuestion(random)">关注问题</el-button>
                        <el-button type="" size="mini" plain @click="addAnswer(random)">
                          添加答案
                        </el-button>
                      </div>
                      <div style="height: 12vh">
                        <el-scrollbar max-height="12vh" style="height: 12vh">
                          <el-label>{{ random.issue }}</el-label>
                        </el-scrollbar>
                      </div>
                      <div style="text-align: right">
                        <el-button type="" size="mini" @click="evaluate('question', random, false, false, '')">
                          赞
                        </el-button>
                        <el-button type="" size="mini" @click="evaluate('question', random, true, false, '')">
                          踩
                        </el-button>
                        <el-button type="danger" size="mini" @click="report('question', random)" >
                          举报
                        </el-button>
                      </div>
                    </el-space>
                  </el-card>
                </el-header>

                <el-divider />

                <el-main style="">
                  <el-scrollbar max-height="24vh" style="height: 24vh">
                    <el-table
                        :data="random.answerList"

                        width="100%"
                        stripe>
<!--                      <el-table-column prop="userId" label="回答者ID"></el-table-column>-->
                      <el-table-column prop="userNickname" width="120px" label="回答者"></el-table-column>
                      <el-table-column prop="content" label="答案内容" width="530px" type="expand">
                        <template #default="props">
                          <p> {{ props.row.content }}</p>
                        </template>
                      </el-table-column>
                      <el-table-column prop="praiseCount" label="点赞数"></el-table-column>
                      <el-table-column label="操作" width="220px">
                        <template #default="scope">
                          <el-button type="" size="mini" @click="evaluate('answer', scope.row, false, false, '')">
                            赞
                          </el-button>
                          <el-button type="" size="mini" @click="evaluate('answer', scope.row, true, false, '')">
                            踩
                          </el-button>
                          <el-button type="danger" size="mini" @click="report('answer', scope.row)" >
                            举报
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-scrollbar>
                </el-main>
              </el-main>

              <el-aside width="260px" style="padding-top: 60px; text-align: left">
                <el-card style="width: 230px;">
                  <div>
                    <el-row :gutter="20">
                      <el-col :span="8">
                        <el-avatar :src="random.questionerInfo.avatar"></el-avatar>
                      </el-col>
                      <el-col :span="16">
                        <div style="font-size: medium">{{ random.questionerInfo.nickname }}</div>
                        <div style="font-size: x-small">{{ random.questionerInfo.introduction }}</div>
                      </el-col>
                    </el-row>
                  </div>

                  <el-divider />

                  <div>
                    <el-row :gutter="40" style="text-align: center">
                      <el-col :span="12">
                        <div>回答</div>
                        <div>{{ random.questionerInfo.questionCount }}</div>
                      </el-col>
                      <el-col :span="12">
                        <div>问题</div>
                        <div>{{ random.questionerInfo.answerCount }}</div>
                      </el-col>
                    </el-row>
                  </div>

                  <div style="text-align: center; margin-top: 10px">
                    <el-button @click="sendMessage(random.questionerInfo)">
                      <i class="el-icon-message"></i>
                      发私信
                    </el-button>
                  </div>

                </el-card>
              </el-aside>
            </el-container>
          </el-container>
        </swiper-slide>
      </swiper>

      <el-dialog center title="添加答案" v-model="addAnswerVis" width="40%" >
        <template #default="scope">
          <el-form style="text-align: left">
            <el-form-item>
              问题：<br />{{nowQuestion}}
            </el-form-item>
            <el-form-item>
              你的答案（已有的答案会被覆盖）：
              <el-input type="textarea" autosize v-model="answerContent" style="width: 98%"></el-input>
              <br />
            </el-form-item>
            <el-form-item>
              <el-button type="success" size="mini" @click="submitAnswer">添加</el-button>
            </el-form-item>
          </el-form>
        </template>
      </el-dialog>

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
  name: "QuestionSquare",
  data() {
    return {
      randomQuestions: [],
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
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/question/findByPraise", {params: {limit: this.optionNum}}).then(res => {
        this.randomQuestions = res.data;
      });
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
        evaluation.target = evaluation.target + row.UUID
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
    attentionQuestion(row) {
      let id = row.id
      request.post("/question/attention/" + id).then(res => {
        if (res.code === '00000') {
          this.$message({
            type: "success",
            message: "关注成功"
          });
          row.attentionCount += 1
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    },
    addAnswer(row) {
      this.nowQuestion = row.issue
      this.nowQuestionId = row.id

      this.addAnswerVis = true;
    },
    submitAnswer() {
      if (this.answerContent === '') {
        this.$message({
          type: "error",
          message: "答案不能为空"
        });
        return ;
      }

      let answer = {}

      answer.questionId = this.nowQuestionId
      answer.content = this.answerContent

      request.post("/answer/save", answer).then(res => {
        if (res.code === '00000') {
          this.$message({
            type: "success",
            message: "添加成功"
          });
          this.load()
        }
        else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      })

      this.answerContent = ''
      this.nowQuestion = ''
      this.nowQuestionId = ''
      this.addAnswerVis = false;

      this.load()
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