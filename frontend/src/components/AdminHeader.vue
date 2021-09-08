<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: cornflowerblue">后台管理</div>
    <div style="flex: 1"></div>
    <div style="width: 160px">
      <el-dropdown>
      <span class="el-dropdown-link">
        {{ admin.name }}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="changePwd">更改密码</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form :model = "form" label-width="120px">
          <el-form-item label="原密码：">
            <el-input v-model="form.sourcePwd" style="width: 80%" show-password></el-input>
          </el-form-item>
          <el-form-item label="更换密码：">
            <el-input v-model="form.pwd" style="width: 80%" show-password></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="savePwd">确 定</el-button>
        </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Header",
  data () {
    return {
      admin: {},
      form: {
        pwd: '',
      },
      dialogVisible: false,
    }
  },
  created() {
    let str = sessionStorage.getItem("admin") || "{}";
    this.admin = JSON.parse(str);
  },
  methods: {
    changePwd() {
      this.dialogVisible = true;
    },
    savePwd() {
      console.log(Number(this.admin.id));
      request.post("/admin/changePwd", {
        'id': Number(this.admin.id),
        'sourcePwd': this.form.sourcePwd,
        'pwd': this.form.pwd
      }).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "密码更改成功，请重新登录"
          });
          this.$router.push("/login");
          this.dialogVisible = false;
        }
        else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      });
      /* 清空form */
      this.form.sourcePwd = '';
      this.form.pwd = '';
    }
  }
}
</script>

<style scoped>

</style>