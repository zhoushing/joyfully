<template>
  <div style="width: 100%; height: 100vh; overflow: hidden;
  background-image: url('https://pool-1305119010.cos.ap-nanjing.myqcloud.com/project/joyfully/background/the_sunny.gif'); background-size:100% 100%">
    <div style="width: 360px; margin: 130px auto">
      <div style="color: #333333; font-size: 30px; text-align: center; padding: 30px 0" >Welcome to use Joyfully</div>
      <el-form ref="form" :model="form" :rules="rules" size="normal">
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
          <el-button style="" type="" @click="$router.push('/register')">注 册</el-button>
          <el-button style="" type="primary" @click="adminLogin">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";
import LoginValidCode from "../components/LoginValidCode";
import {activeRouter} from "../utils/permission";

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
          request.post("/user/login", this.form).then(res => {
            if (res.code === '00000') {
              this.$message({
                type: "success",
                message: "登录成功"
              });

              /* 缓存用户信息 */
              sessionStorage.setItem("user", JSON.stringify(res.data));

              // 配置权限路由
              activeRouter()

              /* 登陆成功之后进行跳转到主页 */
              this.$router.push("/");

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
}
</script>

<style scoped>

</style>







































