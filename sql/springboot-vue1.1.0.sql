/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.33-log : Database - springboot-vue
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springboot-vue` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `springboot-vue`;

/*Table structure for table `answer` */

DROP TABLE IF EXISTS `answer`;

CREATE TABLE `answer` (
  `user_id` int(11) NOT NULL COMMENT '答案所属用户ID',
  `question_id` int(11) NOT NULL COMMENT '答案所属问题ID',
  `content` varchar(512) DEFAULT '答案暂未填写' COMMENT '答案内容',
  `praise_count` int(11) NOT NULL DEFAULT '0' COMMENT '好评数',
  `belittle_count` int(11) NOT NULL DEFAULT '0' COMMENT '差评数',
  KEY `FK_answer_user_id` (`user_id`),
  KEY `FK_answer_question_id` (`question_id`),
  CONSTRAINT `FK_answer_question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
  CONSTRAINT `FK_answer_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `answer` */

insert  into `answer`(`user_id`,`question_id`,`content`,`praise_count`,`belittle_count`) values 
(1,3,'出息了',3,0),
(1,5,'SELECT * FROM `表名` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `表名`) - (SELECT MIN(id) FROM `表名`)) + (SELECT MIN(id) FROM `表名`)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id;',1,0),
(1,11,'徐年',0,0),
(1,12,'zhoushing.github.io',1,0),
(1,13,'leetcode-cn.com',1,0),
(1,14,'test',1,0),
(1,15,'1、&nbsp; 它叫不换行空格，它是按下space键产生的空格。该空格占据宽度受字体影响明显而强烈。2、&ensp; 其占据的宽度正好是1/2个中文宽度，而且基本上不受字体影响。3、&emsp;它叫“全角空格”，就是其占据的宽度正好是1个中文宽度，而且基本上不受字体影响。4、&thinsp; 它叫窄空格，它是em之六分之一宽。5、&zwnj; 它叫零宽不连字，是一个不打印字符，放在电子文本的两个字符之间。6、&zwj;它叫零宽连字，是一个不打印字符，放在某些需要复杂排版语言（如阿拉伯语、印地语）的两个字符之间。  ',1,0),
(1,16,'JWT全称JSON Web Token，是一个开放标准(RFC 7519)，它定义了一种紧凑的、自包含的方式，用于作为JSON对象在各方之间安全地传输信息。该信息可以被验证和信任，因为它是数字签名的。',0,0),
(1,17,'Authorization (授权) : 这是使用JWT的最常见场景。一旦用户登录，后续每个请求都将包含JWT，允许用户访问该令牌允许的路由、服务和资源。单点登录是现在广泛使用的JWT的一个特性，因为它的开销很小，并且可以轻松地跨域使用。   Information Exchange (信息交换) : 对于安全的在各方之间传输信息而言，JSON Web Tokens无疑是一种很好的方式。因为JWT可以被签名，例如，用公钥/私钥对，你可以确定发送人就是它们所说的那个人。另外，由于签名是使用头和有效负载计算的，您还可以验证内容没有被篡改。',1,0),
(1,18,'JSON Web Token由三部分组成，它们之间用圆点(.)连接。这三部分分别是：Header、Payload、Signature。所以一个token的通常形式为xxxxxx.yyyyyy.zzzzzz，其中：Header header典型的由两部分组成：token的类型（“JWT”）和算法名称（比如：HMAC SHA256或者RSA等等）。Payload JWT的第二部分是payload，它包含声明（要求）。声明是关于实体(通常是用户)和其他数据的声明。声明有三种类型: registered, public 和 private。Signature 签名是用于验证消息在传递过程中有没有被更改，并且，对于使用私钥签名的token，它还可以验证JWT的发送方是否为它所称的发送方。',1,0),
(1,1,'在CPU的寄存器中，指令寄存器对用户是完全透明的；在汇编语言程序中，程序员可以直接访问通用寄存器以存取数据，可以访问状态字寄存器以获取有关数据处理结果的相关信息，可以通过相对程序计数器进行寻址，但是不能访问指令寄存器。',1,0),
(2,18,'不知道，下一个',0,1);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
  `name` varchar(255) NOT NULL COMMENT '类别名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_CATEGORY_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`name`) values 
(3,'MySQL'),
(4,'作者相关'),
(5,'前端'),
(6,'后端'),
(7,'基础'),
(2,'计算机组成原理'),
(1,'默认');

/*Table structure for table `evaluation` */

DROP TABLE IF EXISTS `evaluation`;

CREATE TABLE `evaluation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评价id',
  `target` varchar(50) NOT NULL COMMENT '评价目标',
  `belittle` tinyint(1) NOT NULL COMMENT '是否为差评',
  `report` tinyint(1) NOT NULL COMMENT '是否为举报',
  `report_reason` varchar(255) NOT NULL DEFAULT '无' COMMENT '举报理由',
  `user_id` int(11) NOT NULL COMMENT '所属用户ID',
  `checked` tinyint(1) DEFAULT '0' COMMENT '是否已经审查',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `evaluation` */

insert  into `evaluation`(`id`,`target`,`belittle`,`report`,`report_reason`,`user_id`,`checked`) values 
(1,'answer,1_3',0,0,'无',1,0),
(2,'answer,1_3',0,0,'无',2,0),
(3,'answer,1_17',0,0,'无',1,0),
(4,'answer,1_12',0,0,'无',1,0),
(5,'answer,2_18',1,0,'无',1,0),
(6,'answer,1_18',0,0,'无',1,0),
(7,'answer,1_1',0,0,'无',1,0),
(8,'answer,1_15',0,0,'无',1,0),
(9,'answer,1_13',0,0,'无',1,0),
(10,'answer,1_5',0,0,'无',1,0),
(11,'answer,1_14',0,0,'无',1,0),
(12,'answer,1_3',0,0,'无',4,0),
(13,'question,3',0,0,'无',1,0),
(14,'question,15',0,0,'无',1,0),
(15,'answer,2_18',1,1,'答案太水了',4,0),
(16,'file,23bd012cf95f473da951e21c8a8fdf4c',0,0,'无',1,0),
(17,'file,6cef0883c3ed423ca219e8155e2461d1',0,0,'无',1,0);

/*Table structure for table `file` */

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
  `UUID` varchar(255) NOT NULL COMMENT '文件唯一标识',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `type` varchar(255) NOT NULL COMMENT '文件类型',
  `user_id` int(11) NOT NULL COMMENT '所属用户ID',
  `praise_count` int(11) NOT NULL DEFAULT '0' COMMENT '好评数',
  `belittle_count` int(11) NOT NULL DEFAULT '0' COMMENT '差评数',
  PRIMARY KEY (`UUID`),
  KEY `FK_file_user_id` (`user_id`),
  CONSTRAINT `FK_file_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `file` */

insert  into `file`(`UUID`,`name`,`type`,`user_id`,`praise_count`,`belittle_count`) values 
('23bd012cf95f473da951e21c8a8fdf4c','踩脚小人','gif',1,1,0),
('50ce2731f94f4a029c3e57d9bf85d106','大话','png',4,0,0),
('5412bd2ad1794a9cbe92e057c80f53d7','小怪物','png',1,0,0),
('6cef0883c3ed423ca219e8155e2461d1','李玲玉 - 粉红色的回忆','mp3',1,1,0),
('8a5e29ab6cbf44ed80b7dd226d9be77e','银魂1','jpg',2,0,0),
('8aa367d4380b4c7b853af358668754a9','足球','png',1,0,0),
('95e5d4df8c924818b4494b20f13edfcd','今天不学习，明天变垃圾','jpg',1,0,0),
('9db2dc6b2cd14bcebb8319a990418b58','ASCH','png',1,0,0),
('a1487f0ba03643d3936b0d00b79c8f7a','小可儿 - 念诗之王54秒版','mp3',1,0,0),
('aabbaa79560c46bdba2c03c57a8149ff','紫霞','gif',1,0,0),
('bfcb9b74d6fd49ebacc10784278ca030','狗子','jpg',1,0,0),
('cf614d2fbf7f4cf495422eab4771c0eb','骑上我心爱的小摩托 - 团团 (Cover：静sir)','mp3',1,0,0),
('d96e245fbc7741368a5455372c8c96e3','生日快乐-原声','mp3',1,0,0);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `from_id` int(11) NOT NULL COMMENT '发送人ID',
  `to_id` int(11) NOT NULL COMMENT '接收人ID',
  `content` varchar(255) NOT NULL COMMENT '消息内容',
  `conversation_id` varchar(32) NOT NULL COMMENT '会话ID，由发送和接收人ID按顺序拼接而成',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
  PRIMARY KEY (`id`),
  KEY `message_conversation_id_index` (`conversation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

insert  into `message`(`id`,`from_id`,`to_id`,`content`,`conversation_id`,`send_time`) values 
(1,2,1,'你好鸭','2_1','2022-05-03 15:45:43'),
(2,1,2,'你也好鸭','1_2','2022-05-03 15:45:58'),
(3,4,1,'滴滴滴','4_1','2022-05-03 15:48:57'),
(4,1,4,'在的','1_4','2022-05-03 15:49:14'),
(5,1,2,'最近有看到张三吗？','1_2','2022-05-03 22:29:00'),
(6,1,4,'有什么事吗？','1_4','2022-05-03 22:35:24'),
(7,3,1,'ddd','3_1','2022-05-06 19:07:53'),
(9,3,4,'在吗？','3_4','2022-05-06 19:29:38');

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(40) NOT NULL DEFAULT '无' COMMENT '公告标题',
  `content` varchar(200) NOT NULL DEFAULT '无' COMMENT '公告内容',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `publisher` varchar(20) NOT NULL COMMENT '发布人',
  `priority` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `notice` */

insert  into `notice`(`id`,`title`,`content`,`date`,`publisher`,`priority`) values 
(1,'特大喜讯','JoyFully网站即将发布上线，喜大普奔！','2022-04-22 15:01:43','系统管理员',5),
(5,'法外狂徒','我法外狂徒李四又回来了！','2022-04-25 23:10:53','法外狂徒李四',1);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  `path` varchar(30) NOT NULL COMMENT '资源路径',
  `remark` varchar(30) NOT NULL COMMENT '权限备注',
  `img` varchar(50) DEFAULT NULL COMMENT '权限图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`id`,`name`,`path`,`remark`,`img`) values 
(101,'Home','/home','主页','el-icon-house'),
(102,'Person','/person','个人信息',''),
(103,'Password','/password','更换密码',NULL),
(104,'Logout','/logout','注销账户',NULL),
(105,'Message','/message','消息',NULL),
(111,'UserManage','/userManage','用户管理','el-icon-user'),
(112,'QuestionManage','/questionManage','问题管理','el-icon-collection'),
(113,'FileManage','/fileManage','文件管理','el-icon-folder-opened'),
(114,'NoticeManage','/noticeManage','公告管理','el-icon-s-order'),
(121,'MyQuestion','/myQuestion','我的问题','el-icon-collection'),
(122,'QuestionSquare','/questionSquare','问题广场','el-icon-place'),
(123,'MyFile','/myFile','我的资源','el-icon-files'),
(124,'FileSquare','/fileSquare','资源广场','el-icon-folder-opened'),
(125,'ChatRoom','/chatRoom','聊天室','el-icon-chat-line-round'),
(131,'Audit','/audit','审核','el-icon-time');

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `issue` varchar(255) NOT NULL COMMENT '问题内容',
  `type` varchar(20) NOT NULL DEFAULT '问答题' COMMENT '问题题型',
  `share` tinyint(4) DEFAULT '0' COMMENT '是否分享题目',
  `user_id` int(11) NOT NULL COMMENT '所属用户ID',
  `praise_count` int(11) DEFAULT '0' COMMENT '好评数',
  `belittle_count` int(11) DEFAULT '0' COMMENT '差评数',
  `attention_count` int(11) DEFAULT '0' COMMENT '被用户关注数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `question` */

insert  into `question`(`id`,`issue`,`type`,`share`,`user_id`,`praise_count`,`belittle_count`,`attention_count`) values 
(1,'在CPU的寄存器中，（）对用户是完全透明的？\nA.程序计数器\nB.指令寄存器\nC.状态寄存器\nD.通用寄存器','选择题',0,1,0,0,1),
(3,'张三去哪了？','问答题',1,2,2,0,1),
(5,'mysql如何随机取数据','问答题',1,1,0,0,1),
(11,'作者笔名','问答题',1,1,0,0,1),
(12,'作者博客网站','问答题',1,1,0,0,1),
(13,'作者喜欢的刷题网站','问答题',1,1,0,0,1),
(14,'test','问答题',1,1,0,0,1),
(15,'HTML的5种空格表示','问答题',1,1,1,0,1),
(16,'JWT是什么','问答题',1,1,0,0,1),
(17,'什么时候应该用JSON Web Token（JWT）','问答题',1,1,0,0,1),
(18,'JSON Web Token的结构是什么样的','问答题',1,1,0,0,1);

/*Table structure for table `question_category` */

DROP TABLE IF EXISTS `question_category`;

CREATE TABLE `question_category` (
  `question_id` int(11) NOT NULL COMMENT '问题ID',
  `category_id` int(11) NOT NULL DEFAULT '1' COMMENT '问题对应的类别ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `question_category` */

insert  into `question_category`(`question_id`,`category_id`) values 
(3,1),
(5,3),
(11,4),
(12,4),
(13,4),
(14,1),
(15,5),
(16,6),
(17,6),
(18,6),
(1,2);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `description` varchar(255) NOT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`description`) values 
(1,'SUPER_ADMIN','超级管理员'),
(2,'NORMAL_USER','普通用户'),
(3,'AUDITOR','审核员');

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_permission` */

insert  into `role_permission`(`role_id`,`permission_id`) values 
(1,101),
(1,111),
(1,112),
(1,113),
(2,121),
(2,122),
(2,123),
(2,101),
(1,102),
(2,102),
(1,103),
(2,103),
(2,124),
(3,131),
(2,104),
(1,114),
(2,125),
(1,105),
(2,105),
(3,105),
(3,101),
(3,102),
(3,103);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '用户账号',
  `pwd` varchar(100) NOT NULL DEFAULT '$2a$10$CCYBzorVPJbn/oRtfZrlHuZrUpkZ6h/iimwIEsGWoNdFEHOjgC29i' COMMENT '密码',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `birthday` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '出生日期',
  `sex` varchar(4) NOT NULL DEFAULT '未知' COMMENT '性别',
  `introduction` varchar(40) NOT NULL DEFAULT '未填写' COMMENT '个人简介',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `logout` tinyint(1) NOT NULL DEFAULT '0' COMMENT '账户是否注销',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USER_NAME` (`name`) COMMENT '用户名唯一',
  UNIQUE KEY `UK_USER_NICKNAME` (`nickname`) COMMENT '昵称唯一'
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`pwd`,`nickname`,`birthday`,`sex`,`introduction`,`avatar`,`logout`) values 
(1,'root','$2a$10$CCYBzorVPJbn/oRtfZrlHuZrUpkZ6h/iimwIEsGWoNdFEHOjgC29i','系统管理员','2021-05-30 00:00:00','未知','root','https://pool-1305119010.cos.ap-nanjing.myqcloud.com//project/joyfully/files/5412bd2ad1794a9cbe92e057c80f53d7_小怪物.png',0),
(2,'lisi','$2a$10$8hOhB7OlfgAZhRzN5TTPbuXwgPB8LS9Q/FaFA0Xhbd7xXbz5cNUXm','法外狂徒李四','2021-08-01 00:00:00','男','法外狂徒','https://pool-1305119010.cos.ap-nanjing.myqcloud.com/project/joyfully/files/8a5e29ab6cbf44ed80b7dd226d9be77e_%E9%93%B6%E9%AD%821.jpg',0),
(3,'wangwu','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','法外狂徒3','2021-08-01 00:00:00','男','法外狂徒',NULL,0),
(4,'user','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','user','2021-07-26 00:00:00','男','用户体验第一','https://pool-1305119010.cos.ap-nanjing.myqcloud.com//project/joyfully/files/50ce2731f94f4a029c3e57d9bf85d106_大话.png',0),
(5,'22','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','22','2021-08-10 00:00:00','男','22',NULL,0),
(6,'delete','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','账号已注销','2021-01-01 00:00:06','未知','当前账户用以托管已注销用户的问题',NULL,0),
(8,'33','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','33','2021-08-11 00:00:00','未知','未填写',NULL,0),
(19,'444','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','user444','2021-08-04 00:00:00','未知','未填写',NULL,0),
(20,'555','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','user555','2021-08-04 18:50:35','未知','未填写',NULL,0),
(21,'666','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','user666','2021-08-04 00:00:00','女','未填写',NULL,0),
(22,'777','$2a$10$iy1yinJ9xt57la6vxrU4IOQXsOs.Q5KQZVcG0BBVTApeeOdvCbgoO','user777','2021-08-04 18:55:07','未知','未填写',NULL,0),
(26,'888','$2a$10$tuJ3jTZIqmYg9sa/Sys7/OIlJmEKCKatJhJixPG4YFt9YMApFZudm','user888','2022-02-26 14:38:51','未知','未填写',NULL,0),
(27,'test1','$2a$10$JIXFaVcUM1/.DK7mJaYQ2.yye/QpERvGYh/LW63i7Ne96xLpD6/uO','test_one','2022-04-30 00:00:00','男','无',NULL,0);

/*Table structure for table `user_question` */

DROP TABLE IF EXISTS `user_question`;

CREATE TABLE `user_question` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `question_id` int(11) NOT NULL COMMENT '问题ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_question` */

insert  into `user_question`(`user_id`,`question_id`) values 
(1,1),
(2,3),
(1,5),
(1,11),
(1,12),
(1,13),
(1,14),
(1,15),
(1,16),
(1,17),
(1,18),
(1,2),
(1,3),
(4,3);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL DEFAULT '2' COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`user_id`,`role_id`) values 
(1,1),
(2,1),
(3,2),
(4,2),
(5,2),
(8,2),
(17,2),
(19,2),
(20,2),
(21,2),
(22,2),
(1,2),
(26,2),
(1,3),
(2,3),
(3,3),
(27,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
