<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        :default-active="path"
        router
        class="el-menu-vertical-demo">
      <el-submenu index="1">
        <template #title>
          <i class="el-icon-setting"></i>
          <span>系统管理</span>
        </template>
        <!--在这里v-if方式和v-show="show"的方式都会造成一小会的渲染，所有选用之间取消组件的v-if方式-->
        <div v-if="show">
          <el-menu-item index="/adminManage" >
            <template #title>管理员管理</template>
            <i class="el-icon-user"></i>
          </el-menu-item>
        </div>
        <el-menu-item index="/userManage">
          <template #title>用户管理</template>
          <i class="el-icon-user"></i>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="/adminQuestion">
        <i class="el-icon-collection"></i>
        <template #title>问题管理</template>
      </el-menu-item>
      <el-submenu index="3">
        <template #title>
          <i class="el-icon-folder-opened"></i>
          <span>文件管理</span>
        </template>
        <div v-if="show">
          <el-menu-item index="/adminFileLoad" >
            <template #title>文件上传</template>
            <i class="el-icon-upload2"></i>
          </el-menu-item>
        </div>
        <el-menu-item index="/adminFileList">
          <template #title>文件列表</template>
          <i class="el-icon-tickets"></i>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Aside",
  data() {
    return {
      /* 默认高亮的选项 */
      path: this.$route.path,
      show: true,
    }
  },
  created() {
    this.checkPower();
  },
  methods: {
    checkPower() {
      let adminJson = sessionStorage.getItem("admin");
      let adminData = adminJson.split(':')[1].split(',');
      let adminId = Number(adminData[0]);
      request.get("/admin/checkPower", {params: {"id": adminId}}).then(res => {
        if (!res.data) {
          this.show = false;
        }
      });
    },
  }
}
</script>

<style scoped>

</style>