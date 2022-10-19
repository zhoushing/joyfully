<template>
  <div style="width: 100%">
    <el-card style="width: 40%; margin: 10px">
      <el-form ref="form" :model="form" label-width="auto" :rules="rules">
        <el-form-item label="原密码" prop="password">
          <el-input v-model="form.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="form.newPass" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPass">
          <el-input v-model="form.confirmPass" show-password></el-input>
        </el-form-item>
      </el-form>

      <div style="text-align: center">
        <el-button type="primary" @click="changePass">更改</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Password",
  data() {
    return {
      form: {
        password: '',
        newPass: '',
        confirmPass: ''
      },
      rules: {
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        newPass: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
        ],
        confirmPass: [
          {required: true, message: '请输入确认新密码', trigger: 'blur'},
        ],
      },
    }
  },
  methods: {
    changePass() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.newPass === this.form.confirmPass) {
            this.$message.error('2次输入新密码必须一致')
            return
          }

          request.put("/user/password", this.form).then(res => {
            if (res.code === '00000') {
              this.$message.success('修改成功，请重新登录')
              this.$router.push("/login")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
