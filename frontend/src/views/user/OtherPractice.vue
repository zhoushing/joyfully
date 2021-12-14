<template>
  <div style="padding: 10px; width: 100%">
    <el-backtop/>
    <div style="margin: 10px 0; text-align: right">
      <el-button @click="load" type="primary" size="normal">刷新</el-button>
      &emsp;请选择随机题目条数：
      <el-radio-group v-model="optionnum">
        <el-radio :label=5 size="medium">5</el-radio>
        <el-radio :label=10 size="medium">10</el-radio>
      </el-radio-group>
      &emsp;
    </div>
    <el-collapse v-model="activeName" v-for="(random, index) in randomQuestions"
                 accordion style="padding: 20px">
      <el-collapse-item :title="random.issue" :name="index">
        答案：
        <el-button size="mini" type="success" plain @click="showAnswer(random.answerList)">
          查看
        </el-button>
        <br/>
        分类：{{ random.category }}<br/>
        所属用户ID：{{ random.quserId }}
      </el-collapse-item>
    </el-collapse>
  </div>

  <div style="margin: 10px 0">
    <el-dialog title="答案列表" v-model="answerVis" width="80%">
      <el-table :data="answerList" stripe border>
        <el-table-column prop="id" label="答案ID"></el-table-column>
        <el-table-column prop="userId" label="所属用户ID"></el-table-column>
        <el-table-column prop="userName" label="所属用户昵称"></el-table-column>
        <el-table-column prop="content" label="答案内容" width="400"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "OtherPractice",
  data() {
    return {
      randomQuestions: [],
      activeName: -1,
      optionnum: 5,
      answerVis: false,
      answerList: [],
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/question/findRandom", {params: {limit: this.optionnum}}).then(res => {
        this.randomQuestions = res.data;
      });
    },
    showAnswer(answerList) {
      this.answerList = answerList
      this.answerVis = true
    },
  }
}
</script>

<style scoped>

</style>