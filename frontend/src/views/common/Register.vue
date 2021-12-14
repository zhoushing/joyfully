<template>
  <div style="width: 100%; height: 100vh; overflow: hidden;
  background-image: url('static/img/the_sunny.gif'); background-size: 100% 100%">
    <div style="width: 400px; margin: 130px auto">
      <div style="color: #333333; font-size: 30px; text-align: center; padding: 30px 0" >欢迎注册</div>
      <el-form ref="form" :model="form" :rules="rules" size="normal">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input prefix-icon="el-icon-lock" v-model="form.confirmPassword" show-password></el-input>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button style="" type="primary" @click="register">注 册</el-button>
          <el-button @click="resetForm('form')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "Login",
  data () {
    var checkName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('用户名不能为空'));
      }
      setTimeout(() => {
        if (value.length < 2 || value.length > 14) {
            callback(new Error('用户名应该在2-14位之间！'));
        }
        else {
          callback();
        }
      }, 1000);
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      }
      else {
        if (this.form.confirmPassword !== '') {
          this.$refs.form.validateField('confirmPassword');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      }
      else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      }
      else {
        callback();
      }
    };

    return {
      form: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      confirmPassword: '',
      rules: {
        username: [
          { validator: checkName, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        confirmPassword: [
          { validator: validatePass2, trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    register() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/register", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功"
              });
              /* 登陆成功之后进行跳转到主页 */
              this.$router.push("/login")
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
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>

</style>







































