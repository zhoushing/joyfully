> 这是一个简单的 后端 系统，基于 SpringBoot 和 MyBatis-plus 搭建的。

## 目录结构

```bash
├── src.main                                    # 源代码
│   ├── java                            
│   │   └── com.joyfully.springboot             # 源代码
│   │       ├── common                          # 公用类
│   │       │   ├── config                      # 主要配置类
│   │       │   ├── AuthInterceptor             # 身份验证拦截类
│   │       │   ├── Result                      # 后端操作返回给前端的包装类
│   │       │   └── WebLogAspect                # 对 web 添加日志切面
│   │       ├── component                       # 组件类
│   │       │   └── WebSocketServer             # Socket 服务器类 
│   │       ├── controller                      # 控制器类
│   │       ├── entity                          # 实体类
│   │       ├── enums                           # 枚举类
│   │       ├── exception                       # 异常类
│   │       │   ├── GlobalExceptionHandler      # controller异常处理类
│   │       │   ├── CustomException             # 自定义异常类
│   │       │   └── XXXException                # 指定异常类，继承自CustomException
│   │       ├── mapper                          # 同 DAO 层, 也就是数据访问层
│   │       ├── service                         # 业务逻辑层
│   │       │   ├── impl                        # 具体实现类
│   │       │   └── xxxService                  # service 抽象接口
│   │       ├── util                            # 工具类
│   │       │   ├── datastructrue               # 工具数据结构
│   │       │   ├── IllegalWordMatcher          # 自定义异常类
│   │       │   └── XXXUtils                    # 指定工具类
│   │       └── BackendApplication              # SpringBoot 启动类
│   └── resources                               # 资源文件
│       ├── mapper                              # 自定义的 mapper, 定义了一些自定义方法  
│       ├── application.yml                     # SpringBoot 配置文件
│       └── logback-spring.xml                  # logback 日志配置文件
└── pom.xml                                     # maven 包管理配置
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
~~在解决问题 2 后, 因为自定义了查询方法, 造成任意条件筛选失效, 后续可能更改自定义查询方法~~（已解决，使用了MyBatisPlus的Wrapper）


## 待补充

预计添加技术或框架：
 - Spring Security 或者 Shiro 保证安全
 - Redis 实现统计同时在线人数或者作为缓存
 - 尝试在 Linux 上挂载

## 版本迭代

1.1.0版本

技术：

- 整合了Knife4j，提供了后端API文档，更加方便前后端协同（问题：代码耦合度较高）

设计：

- 新增审核员角色，用以对不合格资源以及问答进行审核和鉴别

数据库：

- 新建file文件表，将文件的记录转存数据库
- 新增evaluation评价表，用以记录对问题、回答、资源进行好坏评以及举报操作
- 新增notice公告表，存储公告信息
- 拆分表，将部分关系扩展为关系表
- 用户权限分配转化为RBAC认证模型，新增role角色、permission权限表，并对user表结构进行调整
- question、answer、file表新增好坏评字段，方便直接查询展示
- user新增avatar字段，存储用户头像地址
- user新增logout字段，删除用户时将此字段更改，实现伪删除
- user废弃address字段，转变为introduction字段，用以存储用户个人简介信息

后端：

- 根据数据库的设计进行了实体类和对应的三层设计
- 将三层的设计明确分开来
  - mapper层用于直接与数据库交互，返回操作影响条目
  - service层注重于业务逻辑，对一种或多种mapper的操作做出处理并对不合理操作抛出异常
  - controller层对service层的结果进行封装，亦或是对多个业务逻辑进行调用，对于不合理数据要第一时间进行处理（但部分应该放置到前端进行拦截）
- 抽象出各种操作的异常并对全局controller层操作进行异常拦截并处理
  - 定义多种异常方便记录日志，之后进行排错定位
  - 定义GlobalExceptionHandler类结合@ControllerAdvice注解来对全局controller层异常进行处理（分类讨论）
- 新建枚举类来存放默认密码以及角色类型等