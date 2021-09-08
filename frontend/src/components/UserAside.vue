<template>
  <div>
    <el-menu
        style=" min-height: calc(100vh - 50px)"
        :default-active="path"
        router
        class="el-menu-vertical-demo"
        :collapse="isCollapse">
      <el-menu-item>
        <template #title>
          <div v-if="isCollapse">
            <el-button @click="openAside" type="text" style="width: 100%; height: 100%" >
              <i class="el-icon-right"></i>
            </el-button>
          </div>
          <div v-if="!isCollapse">
            <el-button @click="closeAside" type="text" style="width: 100%" >
              <i class="el-icon-back"></i>
            </el-button>
          </div>
        </template>
      </el-menu-item>
      <el-submenu index="1">
        <template #title>
          <i class="el-icon-setting"></i>
          <span>问题广场</span>
        </template>
        <el-menu-item index="/otherPractice">
          <template #title>别处看看</template>
          <i class="el-icon-user"></i>
        </el-menu-item>
        <el-menu-item index="/userPractice">
          <template #title>随机练习</template>
          <i class="el-icon-user"></i>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="/userQuestion">
        <template #title>我的问题</template>
        <i class="el-icon-collection"></i>
      </el-menu-item>
      <el-submenu index="3">
        <template #title>
          <i class="el-icon-folder-opened"></i>
          <span>资源广场</span>
        </template>
        <div v-if="show">
          <el-menu-item index="/userFileLoad">
            <template #title>文件上传</template>
            <i class="el-icon-upload2"></i>
          </el-menu-item>
        </div>
        <el-menu-item index="/userFileList">
          <template #title>文件列表</template>
          <i class="el-icon-tickets"></i>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>

export default {
  name: "Aside",
  data() {
    return {
      /* 默认高亮的选项 */
      path: this.$route.path,
      show: true,
      isCollapse: false,
      showImage: 'el-icon-back'
    }
  },
  created() {
    this.checkPower();
  },
  methods: {
    checkPower() {
      let adminJson = sessionStorage.getItem("user");
      let admin = JSON.stringify(adminJson);
      let adminId = admin.id;
    },
    openAside() {
      this.isCollapse = false;
      this.showImage = 'el-icon-right';
    },
    closeAside() {
      this.isCollapse = true;
      this.showImage = 'el-icon-back';
    },
  }
}
</script>

<style scoped>

</style>