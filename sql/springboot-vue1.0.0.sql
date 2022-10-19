/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.33 : Database - springboot-vue
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

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `pwd` varchar(255) NOT NULL DEFAULT '123456' COMMENT '密码',
  `power` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有无创建管理员的权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`name`,`pwd`,`power`) values 
(1,'zhou','123456',1),
(2,'admin1','123456',1),
(3,'admin2','123456',0),
(4,'admin3','123456',0),
(5,'admin4','123456',0),
(6,'admin5','123456',0);

/*Table structure for table `answer` */

DROP TABLE IF EXISTS `answer`;

CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(512) DEFAULT NULL COMMENT '答案内容',
  `user_id` int(11) NOT NULL DEFAULT '1' COMMENT '答案所属用户ID',
  `question_id` int(11) NOT NULL COMMENT '问题ID',
  PRIMARY KEY (`id`),
  KEY `FK_answer_user_id` (`user_id`),
  KEY `FK_answer_question_id` (`question_id`),
  CONSTRAINT `FK_answer_question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
  CONSTRAINT `FK_answer_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `answer` */

insert  into `answer`(`id`,`content`,`user_id`,`question_id`) values 
(1,'(B)',1,1),
(2,'出息了',1,3),
(3,'SELECT * FROM `表名` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `表名`) - (SELECT MIN(id) FROM `表名`)) + (SELECT MIN(id) FROM `表名`)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id;',1,5),
(4,'徐年',1,11),
(5,'zhoushing.github.io',1,12),
(6,'leetcode-cn.com',1,13),
(7,'test',1,14),
(8,'1、&nbsp; 它叫不换行空格，它是按下space键产生的空格。该空格占据宽度受字体影响明显而强烈。2、&ensp; 其占据的宽度正好是1/2个中文宽度，而且基本上不受字体影响。3、&emsp;它叫“全角空格”，就是其占据的宽度正好是1个中文宽度，而且基本上不受字体影响。4、&thinsp; 它叫窄空格，它是em之六分之一宽。5、&zwnj; 它叫零宽不连字，是一个不打印字符，放在电子文本的两个字符之间。6、&zwj;它叫零宽连字，是一个不打印字符，放在某些需要复杂排版语言（如阿拉伯语、印地语）的两个字符之间。  ',1,15),
(9,'JWT全称JSON Web Token，是一个开放标准(RFC 7519)，它定义了一种紧凑的、自包含的方式，用于作为JSON对象在各方之间安全地传输信息。该信息可以被验证和信任，因为它是数字签名的。',1,16),
(10,'Authorization (授权) : 这是使用JWT的最常见场景。一旦用户登录，后续每个请求都将包含JWT，允许用户访问该令牌允许的路由、服务和资源。单点登录是现在广泛使用的JWT的一个特性，因为它的开销很小，并且可以轻松地跨域使用。   Information Exchange (信息交换) : 对于安全的在各方之间传输信息而言，JSON Web Tokens无疑是一种很好的方式。因为JWT可以被签名，例如，用公钥/私钥对，你可以确定发送人就是它们所说的那个人。另外，由于签名是使用头和有效负载计算的，您还可以验证内容没有被篡改。',1,17),
(11,'JSON Web Token由三部分组成，它们之间用圆点(.)连接。这三部分分别是：Header、Payload、Signature。所以一个token的通常形式为xxxxxx.yyyyyy.zzzzzz，其中：Header header典型的由两部分组成：token的类型（“JWT”）和算法名称（比如：HMAC SHA256或者RSA等等）。Payload JWT的第二部分是payload，它包含声明（要求）。声明是关于实体(通常是用户)和其他数据的声明。声明有三种类型: registered, public 和 private。Signature 签名是用于验证消息在传递过程中有没有被更改，并且，对于使用私钥签名的token，它还可以验证JWT的发送方是否为它所称的发送方。<img scr=\'https://sm.ms/image/uFvGLfogMaKjVqb\'>',1,18);

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `issue` varchar(255) NOT NULL COMMENT '问题内容',
  `category` varchar(20) DEFAULT '默认' COMMENT '问题类别',
  `type` varchar(20) NOT NULL DEFAULT '问答题' COMMENT '问题题型',
  `q_user_id` int(11) NOT NULL DEFAULT '1' COMMENT '所属用户id（默认是一号用户）',
  `share` tinyint(4) DEFAULT '0' COMMENT '是否分享题目',
  PRIMARY KEY (`id`),
  KEY `FK_question_user_id` (`q_user_id`),
  CONSTRAINT `FK_question_user_id` FOREIGN KEY (`q_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `question` */

insert  into `question`(`id`,`issue`,`category`,`type`,`q_user_id`,`share`) values 
(1,'在CPU的寄存器中，（）对用户是完全透明的？\nA.程序计数器\nB.指令寄存器\nC.状态寄存器\nD.通用寄存器','计算机组成原理','选择题',1,1),
(3,'张三去哪了？','默认','问答题',2,1),
(5,'mysql如何随机取数据','MySQL','问答题',1,1),
(11,'作者笔名','作者相关','问答题',1,1),
(12,'作者博客网站','作者相关','问答题',1,1),
(13,'作者喜欢的刷题网站','作者相关','问答题',1,1),
(14,'test','默认','问答题',1,1),
(15,'HTML的5种空格表示','前端','问答题',1,1),
(16,'JWT是什么','后端','问答题',1,1),
(17,'什么时候应该用JSON Web Token（JWT）','后端','问答题',1,1),
(18,'JSON Web Token的结构是什么样的','后端','问答题',1,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '用户账号',
  `pwd` varchar(255) DEFAULT '123456' COMMENT '密码',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `birthday` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '出生日期',
  `sex` varchar(4) DEFAULT '未知' COMMENT '性别',
  `address` varchar(255) DEFAULT '未填写' COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_NIKE_NAME` (`nick_name`),
  UNIQUE KEY `UNIQUE_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`pwd`,nickname,`birthday`,`sex`,`address`) values
(1,'root','123456','系统问题管理员','2021-05-30 00:00:00','男','保存默认问题，请勿删除'),
(2,'lisi','123456','法外狂徒2','2021-08-01 00:00:00','男','地球'),
(3,'wangwu','123456','法外狂徒3','2021-08-01 00:00:00','男','地球'),
(4,'11','123456','11','2021-07-26 00:00:00','男','11'),
(5,'22','123456','22','2021-08-10 00:00:00','男','22'),
(8,'33','123456','33','2021-08-11 00:00:00','未知','地球'),
(17,'333','123456','user333','2021-08-02 15:43:07','未知','未填写'),
(19,'444','123456','user444','2021-08-04 00:00:00','未知','未填写'),
(20,'555','123456','user555','2021-08-04 18:50:35','未知','未填写'),
(21,'666','123456','user666','2021-08-04 00:00:00','女','未填写'),
(22,'777','123456','user777','2021-08-04 18:55:07','未知','未填写'),
(23,'888','123456','user888','2021-08-04 18:55:18','未知','未填写'),
(24,'999','123456','user999','2021-08-04 18:55:31','未知','未填写'),
(25,'1111','123456','user1111','2021-08-04 18:56:49','未知','未填写'),
(26,'2222','123456','user2222','2021-08-04 18:56:59','未知','未填写'),
(27,'3333','123456','user3333','2021-08-04 18:57:06','未知','未填写'),
(28,'4444','123456','user4444','2021-08-04 00:00:00','未填写','未填写'),
(29,'5555','123456','user5555','2021-08-04 00:00:00','未填写','未填写'),
(30,'6666','123456','user6666','2021-08-04 00:00:00','未填写','未填写'),
(31,'7777','123456','user7777','2021-08-04 00:00:00','未填写','未填写'),
(33,'8888','123456','user8888','2021-08-04 00:00:00','未填写','未填写'),
(34,'uu','123456','useruu','2021-12-13 10:46:48','未知','11');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
