import { createRouter, createWebHistory } from 'vue-router'
import Login from "../views/common/Login";
import AdminMain from "../layout/AdminMain";
import Register from "../views/common/Register";
import UserManage from "../views/admin/UserManage";
import AdminQuestion from "../views/admin/AdminQuestion";
import UserInfo from "../views/user/UserInfo";
import AdminManage from "../views/admin/AdminManage";
import FilesLoad from "../views/common/FilesLoad";
import FilesList from "../views/common/FilesList";
import UserMain from "../layout/UserMain";
import UserQuestion from "../views/user/UserQuestion";
import UserPractice from "../views/user/UserPractice";
import OtherPractice from "../views/user/OtherPractice";
import ChatRoom from "../views/common/ChatRoom";

const routes = [
  {
    path: "/",
    name: "AdminMain",
    redirect: "/login",
    component: AdminMain,
    children: [
      {
        path: "/adminManage",
        name: "AdminManage",
        component: AdminManage
      },
      {
        path: "/userManage",
        name: "UserManage",
        component: UserManage
      },
      {
        path: "/adminQuestion",
        name: "AdminQuestion",
        component: AdminQuestion
      },
      {
        path: "/adminFileLoad",
        name: "AdminFileLoad",
        component: FilesLoad
      },
      {
        path: "/adminFileList",
        name: "AdminFileList",
        component: FilesList
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
  {
    path: "/userMain",
    name: "userMain",
    component: UserMain,
    children: [
      {
        path: "/userInfo",
        name: "UserInfo",
        component: UserInfo
      },
      {
        path: "/userQuestion",
        name: "UserQuestion",
        component: UserQuestion
      },
      {
        path: "/userPractice",
        name: "UserPractice",
        component: UserPractice
      },
      {
        path: "/otherPractice",
        name: "OtherPractice",
        component: OtherPractice
      },
      {
        path: "/userFileLoad",
        name: "UserFileLoad",
        component: FilesLoad
      },
      {
        path: "/userFileList",
        name: "UserFileList",
        component: FilesList
      },
      {
        path: '/chatRoom',
        name: 'ChatRoom',
        component: ChatRoom
      },

    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router
