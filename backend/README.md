> 这是一个简单的 后端 系统，基于 SpringBoot 和 MyBatis-plus 搭建的。

## 目录结构

```bash
├── src.main                            # 源代码
│   ├── java                            
│   │   └── com.joyfully.springboot     # 源代码
│   │       ├── common                  # 公用类
│   │       │   ├── CorsConfig          # 跨域配置类
│   │       │   ├── MybatisPlusConfig   # MyBatis-plus 分页配置
│   │       │   └── Result              # 后端操作返回给前端的包装类
│   │       ├── controller              # 控制器类
│   │       ├── entity                  # 实体类
│   │       ├── mapper                  # 同 DAO 层, 也就是数据访问层
│   │       ├── service                 # 业务逻辑层
│   │       │   ├── impl                # 具体实现类
│   │       │   └── xxxService          # service 抽象接口
│   │       └── BackendApplication      # SpringBoot 启动类
│   └── resources                       # 资源文件
│       ├── application.yml             # SpringBoot 配置文件
│       └── mapper                      # 自定义的 mapper, 定义了一些自定义查询方法 
└── pom.xml                             # maven 包管理配置
```

## 问题
 1. 前后端跨域问题
 2. 在前端用户列表中显示用户的问题列表
 3. 实现问题 2 之后无法对用户进行任意条件筛选了
 
### 已解决的问题
#### 前后端跨域问题
跨域: 
简单说就是只要协议、IP、http方法任意一个不同就是跨域, spring MVC 自从  4.2 开始添加了跨域的支持

解决方案:
在后端配置了 CorsConfig 类, 在当前项目中暂定为接收所有请求, 实际运行时再自定义指定接收地址和接口

#### 在前端用户列表中显示用户的问题列表

解决方案:
在 user 类中创建一个属性 questionList, 标记为 `@TableField(exist = false)`, 表示这个属性在表中不存在, 
再在 mapper 文件中自定义 resultMap 返回类型并编写 SQL 语句

### 未解决的问题
#### 无法对用户进行任意条件筛选了
在解决问题 2 后, 因为自定义了查询方法, 造成任意条件筛选失效, 后序可能更改自定义查询方法