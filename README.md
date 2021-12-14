# Joyfully
<hr />
Joyfully 是一款开源的在线学习辅助系统，主要是针对部分人群个性化定制自己想要记录学习或者参考他人分享题目而做的。
Joyfully还支持文件上传，用来分享你的学习笔记或者记录。

## 主要功能
![Joyfully 功能结构图](http://assets.processon.com/chart_image/5fe68ffe63768932a286115b.png)
   
## 优势

- 简单易用：随想随记
- 操作简单：直白的界面，方便的操作
- 权限约束：可以限制管理员的权限是否传递
- 随机练题：可以通过别人分享的题目锻炼自己

## 技术栈

### 后端

**SpringBoot + MyBatisPlus**

### 前端

**Vue + Vue-Router + Axios + element-plus**

### 数据库

**MySQL**

## 开发环境

JDK： jdk1.8.0_281

mysql：mysql-5.7.33-Win64-x86_64

node：v14.15.1  

IDE：IntelliJ IDEA 2020、SQLyog

## 版本迭代
2021-12-14 版本 1.0.0

主要功能：
- 用户：
    - 对自己所创建的问题进行操作
    - 回顾问题
    - 上传笔记文件等
    - 聊天室询问其他在线人员
- 管理员：
    - 对用户、问题、文件统筹管理
    

主要问题：
1. 聊天室的用户无法得知对方是否收到消息
2. 用户界面未独立出来，和管理员共用一套页面布局
3. 管理界面进行用户管理时第一页显示不正确（具体表现为第一页总是只显示几行用户数据）
4. 界面还需优化，需要将用户和管理员剥离，并计划采用隐性验证，将登录界面的用户、管理员单选框去除


