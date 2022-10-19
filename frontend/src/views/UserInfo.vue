<template>
  <div style="text-align: center">
    <div style="width: 30%; margin-top: 100px; margin-left: 30%">
       <div style="margin-bottom: 20px; font-size: medium">个人信息修改</div>
      <el-form :model="form" label-width="100px">
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" style="width: 73%"></el-input>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker
              v-model="form.birthday"
              type="date"
              placeholder="选择日期"
              value-format="YYYY-MM-DD">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
          <el-radio v-model="form.sex" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input type="textarea" v-model="form.introduction" style="width: 73%"></el-input>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button @click="$router.push('/userMain')">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Person",
  data() {
    return {
      form: {},
      dialogVisible: false,
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}";
    this.form = JSON.parse(str);
  },
  methods: {
    saveUser() {
      /* 更新 */
      request.put("/user/update", this.form).then(res => {
        if (res.code === '00000') {
          this.$message({
            type: "success",
            message: "更新成功"
          });
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      });
      /* 缓存用户信息 */
      sessionStorage.setItem("user", JSON.stringify(this.form));
      this.dialogVisible = false;
      this.$router.push('/userMain');

    }
  }
}
</script>

<style scoped>

</style>