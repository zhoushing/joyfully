<template>
  <div style="width: 100%; height: 100vh; overflow: hidden;
  background-image: url('https://pool-1305119010.cos.ap-nanjing.myqcloud.com/project/joyfully/background/the_sunny.gif'); background-size: 100% 100%">
    <div style="width: 360px; margin: 130px auto">
      <div style="color: #333333; font-size: 30px; text-align: center; padding: 30px 0" >Welcome to join Joyfully</div>
      <el-form ref="form" :model="form" :rules="rules" size="normal">
        <el-form-item prop="name">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.name" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="pwd">
          <el-input prefix-icon="el-icon-lock" v-model="form.pwd" show-password placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="confirmpwd">
          <el-input prefix-icon="el-icon-lock" v-model="form.confirmpwd" show-password placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button style="" type="primary" @click="register">注 册</el-button>
          <el-button @click="$router.push('/login')">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";

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
        if (this.form.confirmpwd !== '') {
          this.$refs.form.validateField('confirmpwd');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      }
      else if (value !== this.form.pwd) {
        callback(new Error('两次输入密码不一致!'));
      }
      else {
        callback();
      }
    };

    return {
      form: {
        name: '',
        pwd: '',
        confirmpwd: ''
      },
      confirmpwd: '',
      rules: {
        name: [
          { validator: checkName, trigger: 'blur' }
        ],
        pwd: [
          { validator: validatePass, trigger: 'blur' }
        ],
        confirmpwd: [
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
            if (res.code === '00000') {
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







































