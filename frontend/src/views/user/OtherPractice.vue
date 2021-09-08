<template>
  <div style="padding: 10px">
    <el-backtop />
    <div style="margin: 10px 0;">
      <el-button @click="load" type="primary" size="normal">刷新</el-button>
    </div>
    <el-collapse v-model="activeName" v-for="(random, index) in randomQuestions"
                 accordion style="padding: 20px">
      <el-collapse-item :title="random.issue" :name="index">
        答案：{{random.answer}}<br/>
        分类：{{random.category}}
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "OtherPractice",
  data() {
    return {
      randomQuestions: [],
      activeName: -1
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/question/findRandom", {params: {limit: 8}}).then(res => {
        this.randomQuestions = res.data;
      });
    }
  }
}
</script>

<style scoped>

</style>