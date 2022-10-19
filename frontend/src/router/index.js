import {createRouter, createWebHistory} from 'vue-router'
import Login from "../views/Login";
import Register from "../views/Register";
import Layout from "../layout/Layout";
import Home from "../views/Home";

const routes = [
  {
    path: "/",
    name: "Layout",
    redirect: "/home",
    component: Layout,
    children: [
      {
        path: "/home",
        name: "Home",
        component: Home
      },
    ]
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/register",
    name: "Register",
    component: Register
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

// 在刷新页面的时候重置当前路由
activeRouter()

function activeRouter() {
  const userStr = sessionStorage.getItem("user")
  if (userStr) {
    const user = JSON.parse(userStr)
    let root = {
      path: '/',
      name: 'Layout',
      component: Layout,
      redirect: "/home",
      children: []
    }
    user.permissionSet.forEach(p => {
      let obj = {
        path: p.path,
        name: p.name,
        component: () => import("@/views/" + p.name)
      };
      root.children.push(obj)
    })
    if (router) {
      router.addRoute(root)
    }
  }
}

router.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === '/register') {
    next()
    return
  }
  let user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
  if (!user.permissionSet || !user.permissionSet.length) {
    console.log('权限列表为空')
    next('/login')
  } else if (!user.permissionSet.find(p => p.path === to.path)) {
    console.log('权限不符')
    next('/login')
  } else {
    next()
  }
})

export default router
