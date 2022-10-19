<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        :default-active="$route.path"
        router
    >
<!-- ChatRoom需重新改动，暂时不显示  -->
      <div  v-for="m in user.permissionSet" :key="m.id">
        <el-menu-item :index="m.path"
                      v-if="m.name !== 'Person' && m.name !== 'Password'
                      && m.name !== 'Logout' && m.name !== 'Message'
                      && m.name !== 'ChatRoom'">
          <i :class="m.img"></i>  {{ m.remark }}
        </el-menu-item>
      </div>
    </el-menu>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Aside",
  data() {
    return {
      user: {}
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  }
}
</script>

<style scoped>

</style>
