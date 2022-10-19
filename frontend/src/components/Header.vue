<template>
  <div style="height: 50px; line-height: 30px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; ">
      <el-link style="font-size: larger; color: dodgerblue; padding-top: 10px" :underline="false" @click="$router.push('/')">JoyFully</el-link>
    </div>
    <div style="flex: 1"></div>
    <div style="width: 220px;">
<!--// todo 有无消息预览-->
      <el-badge class="item">
        <el-button size="mini" type="text" style="font-size: medium" @click="$router.push('/message')" >消息</el-button>
      </el-badge>
      <el-dropdown>
        <span class="el-dropdown-link" style="">
          <el-avatar :size="30" :src="user.avatar" style="position: relative; top: 10px;"></el-avatar>
           {{ user.nickname }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/password')">修改密码</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: ['user'],
  data() {
    return {
      user: {},
      hasMsg: true,

    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  }
}
</script>

<style scoped>
.item {
  margin-right: 20px;
}
</style>
