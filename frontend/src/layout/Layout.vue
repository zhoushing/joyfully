<template>
  <div>
    <!--头部-->
    <Header :key="isFresh"/>
  </div>
  <!--主体-->
  <div style="display: flex">
    <!--侧边栏-->
    <Aside />
    <el-config-provider :locale="locale">
      <!--内容区域-->
      <router-view style=""/>
    </el-config-provider>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";

import {ElConfigProvider} from 'element-plus'
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import request from "../utils/request";

export default {
  name: "Layout",
  components: {
    Header,
    Aside,
    // 添加组件
    [ElConfigProvider.name]: ElConfigProvider,
  },
  provide() {
    return {
      reloadLayout: this.reloadLayout
    }
  },
  data () {
    return {
      // 给locale赋值
      locale:zhCn,
      user: {},
      isFresh: true,
    }
  },
  created() {
    this.refreshUser();
  },
  methods: {
    refreshUser() {
      let userJson = sessionStorage.getItem("user");
      if (!userJson) {
        return
      }
      request.get("/user").then(res => {
        this.user = res.data
      })
    },
    reloadLayout() {
      this.isFresh = false
      this.$nextTick(()=>{
        this.isFresh = true
      })
    }
  },
}
</script>

<style scoped>

</style>