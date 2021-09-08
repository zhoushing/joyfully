<template>
  <div>
    <!--头部-->
    <Header/>
  </div>
  <!--主体-->
  <div style="display: flex">
    <!--侧边栏-->
    <Aside />
    <el-config-provider :locale="locale">
      <!--内容区域-->
      <router-view style="flex: 1"/>
    </el-config-provider>
  </div>
</template>

<script>
import Header from "@/components/UserHeader";
import Aside from "@/components/UserAside";

import {ElConfigProvider} from 'element-plus'
import zhCn from 'element-plus/lib/locale/lang/zh-cn'

export default {
  name: "Layout",
  components: {
    Header,
    Aside,
    [ElConfigProvider.name]:ElConfigProvider,//添加组件
  },
  data () {
    return {
      locale:zhCn,//给locale赋值
    }
  }
}
</script>

<style scoped>

</style>