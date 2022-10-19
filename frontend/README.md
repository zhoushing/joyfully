> 这是一个简单的 vue 项目，基于 vue 3 和 Element-plus 搭建的。

## 目录结构

```bash
├── public                     # 静态资源
│   │── static                 # 配置文件
│   │   │── img                # 图片
│   │   └── js/config.js       # 配置文件
│   │── favicon.ico            # favicon图标
│   └── index.html             # html模板
├── src                        # 源代码
│   ├── assets                 # 主题 字体等静态资源
│   ├── components             # 全局公用组件
│   ├── layout                 # 主要框架
│   ├── router                 # 路由
│   ├── store                  # 全局 store 管理
│   ├── utils                  # 全局公用方法
│   ├── views                  # views 所有页面
│   ├── App.vue                # 入口页面
│   └── main.js                # 入口文件 加载组件 初始化等
├── babel.config.js            # babel 配置
├── package.json               # package.json
├── package-lock.json          # package-lock.json
└── vue.config.js              # vue-cli 配置
```

## 构建步骤

```bash
# 克隆项目
git clone 项目地址

# 进入项目目录
cd XXX

# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 启动服务
npm run dev
```

## 访问地址
默认：127.0.0.1:8080