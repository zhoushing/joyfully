<template>
  <div style="width: 100%; height: 100vh; overflow: hidden;
  background-image: url('static/img/the_sunny.gif'); background-size:100% 100%">
    <div style="width: 360px; margin: 130px auto">
      <div style="color: #333333; font-size: 30px; text-align: center; padding: 30px 0" >欢迎登陆</div>
      <el-form ref="form" :model="form" :rules="rules" size="normal">
        <el-form-item style="text-align: center; ">
          <el-radio v-model="form.pick" label="user">普通用户</el-radio>
          <el-radio v-model="form.pick" label="admin">管理员</el-radio>
        </el-form-item>
        <el-form-item prop="name">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.name" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item prop="pwd">
          <el-input prefix-icon="el-icon-lock" v-model="form.pwd" show-password placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <div style="display: flex;">
            <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width: 50%;" placeholder="请输入验证码"></el-input>
            &emsp;
            <LoginValidCode @input="createValidCode" style=" background-color: #FFF;"/>
          </div>
        </el-form-item>
        <el-form-item style="text-align: center; ">
          <el-button style="" type="" @click="$router.push('/register')" :disabled=isAdmin>注 册</el-button>
          <el-button style="" type="primary" @click="adminLogin">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";
import LoginValidCode from "../../components/LoginValidCode";

export default {
  name: "Login",
  components: {
    LoginValidCode
  },
  data () {
    return {
      form: {
        name: '',
        pwd: '',
        pick: 'user'
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        pwd: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
      },
      validCode: '',
    }
  },
  created() {
    sessionStorage.removeItem("user");
    sessionStorage.removeItem("admin");
  },
  methods: {
    adminLogin() {
      this.$refs['form'].validate((valid) => {
        if (!this.form.validCode) {
          this.$message.error("验证码未填写");
          return;
        }
        if (this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
          this.$message.error("验证码错误");
          return;
        }

        if (valid) {
          request.post("/"+ this.form.pick +"/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              });

              console.log(res.data);

              /* 缓存用户信息 */
              sessionStorage.setItem(this.form.pick, JSON.stringify(res.data));

              /* 登陆成功之后进行跳转到主页 */
              if (this.form.pick === 'admin') {
                this.$router.push("/userManage");
              }
              else {
                this.$router.push("/userMain");
              }

            }
            else {
              this.$message({
                type: "error",
                message: res.msg
              });
            }
          });
        }
      });
    },
    createValidCode(data) {
      this.validCode = data;
    }
  },
  computed: {
    isAdmin: function () {
      return this.form.pick !== 'user'
    }
  }
}
</script>

<style scoped>

</style>







































