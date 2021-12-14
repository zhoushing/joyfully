import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
import { Expand,ChatRound } from '@element-plus/icons'

import '@/assets/css/global.css'

createApp(App).use(store).use(router).use(ElementPlus, {size: 'small'})
    .component('expand',Expand)
    .component('chat-round',ChatRound)
    .mount('#app')
