-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.51-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema javacenter
--

CREATE DATABASE IF NOT EXISTS javacenter;
USE javacenter;

--
-- Definition of table `jchome_ad`
--

DROP TABLE IF EXISTS `jchome_ad`;
CREATE TABLE `jchome_ad` (
  `adid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '1',
  `title` varchar(50) NOT NULL DEFAULT '',
  `pagetype` varchar(20) NOT NULL DEFAULT '',
  `adcode` text NOT NULL,
  `system` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`adid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_ad`
--

/*!40000 ALTER TABLE `jchome_ad` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_ad` ENABLE KEYS */;


--
-- Definition of table `jchome_adminsession`
--

DROP TABLE IF EXISTS `jchome_adminsession`;
CREATE TABLE `jchome_adminsession` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `ip` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `errorcount` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_adminsession`
--

/*!40000 ALTER TABLE `jchome_adminsession` DISABLE KEYS */;
INSERT INTO `jchome_adminsession` (`uid`,`ip`,`dateline`,`errorcount`) VALUES 
 (1,'127.0.0.1',1317354830,0);
/*!40000 ALTER TABLE `jchome_adminsession` ENABLE KEYS */;


--
-- Definition of table `jchome_album`
--

DROP TABLE IF EXISTS `jchome_album`;
CREATE TABLE `jchome_album` (
  `albumid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `albumname` varchar(50) NOT NULL DEFAULT '' COMMENT '相册名',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '所属用户id',
  `username` varchar(15) NOT NULL DEFAULT '' COMMENT '所属用户名',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `updatetime` int(10) unsigned NOT NULL DEFAULT '0',
  `picnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pic` varchar(60) NOT NULL DEFAULT '',
  `picflag` tinyint(1) NOT NULL DEFAULT '0',
  `friend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '权限，0(全站用户可见),1(全好友可见),2(仅指定的好友可见),3(仅自己可见),4(凭密码查看)',
  `password` varchar(10) NOT NULL DEFAULT '',
  `target_ids` text NOT NULL,
  PRIMARY KEY (`albumid`),
  KEY `uid` (`uid`,`updatetime`),
  KEY `updatetime` (`updatetime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_album`
--

/*!40000 ALTER TABLE `jchome_album` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_album` ENABLE KEYS */;


--
-- Definition of table `jchome_appcreditlog`
--

DROP TABLE IF EXISTS `jchome_appcreditlog`;
CREATE TABLE `jchome_appcreditlog` (
  `logid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `appid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `appname` varchar(60) NOT NULL DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `credit` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `note` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`logid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `appid` (`appid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_appcreditlog`
--

/*!40000 ALTER TABLE `jchome_appcreditlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_appcreditlog` ENABLE KEYS */;


--
-- Definition of table `jchome_blacklist`
--

DROP TABLE IF EXISTS `jchome_blacklist`;
CREATE TABLE `jchome_blacklist` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `buid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`buid`),
  KEY `uid` (`uid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_blacklist`
--

/*!40000 ALTER TABLE `jchome_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_blacklist` ENABLE KEYS */;


--
-- Definition of table `jchome_block`
--

DROP TABLE IF EXISTS `jchome_block`;
CREATE TABLE `jchome_block` (
  `bid` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `blockname` varchar(40) NOT NULL DEFAULT '' COMMENT '名称',
  `blocksql` text NOT NULL COMMENT '数据调用SQL',
  `cachename` varchar(30) NOT NULL DEFAULT '' COMMENT '变量名',
  `cachetime` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '缓存时间,设置一个缓存时间间隔，该模块数据将自动在指定的时间间隔内更新数据。缓存时间设置越大，对服务器的负载就越小，但数据的及时性就不够。设置为0，则不使用缓存，实时更新，这样会严重增加服务器负载。',
  `startnum` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '获取满足条件的数据的开始位置',
  `num` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '获取满足条件的数据的结束位置',
  `perpage` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '获取数目,0(获取满足条件的部分数据),其他（获取全部数据，分页显示）',
  `htmlcode` text NOT NULL COMMENT '数据显示HTML代码,用html语言，编写数据的显示样式。',
  PRIMARY KEY (`bid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_block`
--

/*!40000 ALTER TABLE `jchome_block` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_block` ENABLE KEYS */;


--
-- Definition of table `jchome_blog`
--

DROP TABLE IF EXISTS `jchome_blog`;
CREATE TABLE `jchome_blog` (
  `blogid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `subject` char(80) NOT NULL DEFAULT '' COMMENT '标题',
  `classid` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '分类',
  `viewnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `replynum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '热度',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `pic` char(120) NOT NULL DEFAULT '',
  `picflag` tinyint(1) NOT NULL DEFAULT '0',
  `noreply` tinyint(1) NOT NULL DEFAULT '0' COMMENT '不允许评论,1(不允许评论)',
  `friend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '隐私设置，0(全站用户可见),1(全好友可见),2(仅指定的好友可见),3(仅自己可见),4(凭密码查看)',
  `password` char(10) NOT NULL DEFAULT '' COMMENT '密码',
  `click_1` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_2` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_3` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_4` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_5` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`blogid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `topicid` (`topicid`,`dateline`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_blog`
--

/*!40000 ALTER TABLE `jchome_blog` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_blog` ENABLE KEYS */;


--
-- Definition of table `jchome_blogfield`
--

DROP TABLE IF EXISTS `jchome_blogfield`;
CREATE TABLE `jchome_blogfield` (
  `blogid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tag` varchar(255) NOT NULL DEFAULT '' COMMENT '标签',
  `message` mediumtext NOT NULL COMMENT '内容',
  `postip` varchar(20) NOT NULL DEFAULT '',
  `related` text NOT NULL,
  `relatedtime` int(10) unsigned NOT NULL DEFAULT '0',
  `target_ids` text NOT NULL,
  `hotuser` text NOT NULL,
  `magiccolor` tinyint(6) NOT NULL DEFAULT '0',
  `magicpaper` tinyint(6) NOT NULL DEFAULT '0',
  `magiccall` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`blogid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_blogfield`
--

/*!40000 ALTER TABLE `jchome_blogfield` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_blogfield` ENABLE KEYS */;


--
-- Definition of table `jchome_cache`
--

DROP TABLE IF EXISTS `jchome_cache`;
CREATE TABLE `jchome_cache` (
  `cachekey` varchar(16) NOT NULL DEFAULT '',
  `value` mediumtext NOT NULL,
  `mtime` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`cachekey`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_cache`
--

/*!40000 ALTER TABLE `jchome_cache` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_cache` ENABLE KEYS */;


--
-- Definition of table `jchome_class`
--

DROP TABLE IF EXISTS `jchome_class`;
CREATE TABLE `jchome_class` (
  `classid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `classname` char(40) NOT NULL DEFAULT '' COMMENT '名称',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '所属用户id',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`classid`),
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_class`
--

/*!40000 ALTER TABLE `jchome_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_class` ENABLE KEYS */;


--
-- Definition of table `jchome_click`
--

DROP TABLE IF EXISTS `jchome_click`;
CREATE TABLE `jchome_click` (
  `clickid` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '动作名称',
  `icon` varchar(100) NOT NULL DEFAULT '' COMMENT '动作图标',
  `idtype` varchar(15) NOT NULL DEFAULT '' COMMENT '系统类型,blogid(日志),picid(图片),tid(话题)',
  `displayorder` tinyint(6) unsigned NOT NULL DEFAULT '0' COMMENT '显示顺序',
  PRIMARY KEY (`clickid`),
  KEY `idtype` (`idtype`,`displayorder`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_click`
--

/*!40000 ALTER TABLE `jchome_click` DISABLE KEYS */;
INSERT INTO `jchome_click` (`clickid`,`name`,`icon`,`idtype`,`displayorder`) VALUES 
 (1,'路过','luguo.gif','blogid',0),
 (2,'雷人','leiren.gif','blogid',0),
 (3,'握手','woshou.gif','blogid',0),
 (4,'鲜花','xianhua.gif','blogid',0),
 (5,'鸡蛋','jidan.gif','blogid',0),
 (6,'漂亮','piaoliang.gif','picid',0),
 (7,'酷毙','kubi.gif','picid',0),
 (8,'雷人','leiren.gif','picid',0),
 (9,'鲜花','xianhua.gif','picid',0),
 (10,'鸡蛋','jidan.gif','picid',0),
 (11,'搞笑','gaoxiao.gif','tid',0),
 (12,'迷惑','mihuo.gif','tid',0),
 (13,'雷人','leiren.gif','tid',0),
 (14,'鲜花','xianhua.gif','tid',0),
 (15,'鸡蛋','jidan.gif','tid',0);
/*!40000 ALTER TABLE `jchome_click` ENABLE KEYS */;


--
-- Definition of table `jchome_clickuser`
--

DROP TABLE IF EXISTS `jchome_clickuser`;
CREATE TABLE `jchome_clickuser` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `idtype` varchar(15) NOT NULL DEFAULT '',
  `clickid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  KEY `id` (`id`,`idtype`,`dateline`),
  KEY `uid` (`uid`,`idtype`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_clickuser`
--

/*!40000 ALTER TABLE `jchome_clickuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_clickuser` ENABLE KEYS */;


--
-- Definition of table `jchome_comment`
--

DROP TABLE IF EXISTS `jchome_comment`;
CREATE TABLE `jchome_comment` (
  `cid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT 'ID',
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '评论对象ID',
  `idtype` varchar(20) NOT NULL DEFAULT '' COMMENT '评论对象类型,uid(留言),blogid(日志),picid(图片),eventid(活动),sid(分享)',
  `authorid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '作者id',
  `author` varchar(15) NOT NULL DEFAULT '' COMMENT '作者',
  `ip` varchar(20) NOT NULL DEFAULT '' COMMENT '评论ip',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发布时间',
  `message` text NOT NULL COMMENT '内容',
  `magicflicker` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cid`),
  KEY `authorid` (`authorid`,`idtype`),
  KEY `id` (`id`,`idtype`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_comment`
--

/*!40000 ALTER TABLE `jchome_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_comment` ENABLE KEYS */;


--
-- Definition of table `jchome_config`
--

DROP TABLE IF EXISTS `jchome_config`;
CREATE TABLE `jchome_config` (
  `var` varchar(30) NOT NULL DEFAULT '' COMMENT '键',
  `datavalue` text NOT NULL COMMENT '值',
  PRIMARY KEY (`var`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_config`
--

/*!40000 ALTER TABLE `jchome_config` DISABLE KEYS */;
INSERT INTO `jchome_config` (`var`,`datavalue`) VALUES 
 ('sitename','我的空间'),
 ('template','default'),
 ('adminemail','webmaster@localhost'),
 ('onlinehold','1800'),
 ('timeoffset','8'),
 ('maxpage','100'),
 ('starcredit','100'),
 ('starlevelnum','5'),
 ('cachemode','database'),
 ('cachegrade','0'),
 ('allowcache','1'),
 ('allowdomain','0'),
 ('headercharset','0'),
 ('allowrewrite','0'),
 ('allowwatermark','0'),
 ('allowftp','0'),
 ('holddomain','www|*blog*|*space*|x'),
 ('mtagminnum','5'),
 ('feedday','7'),
 ('feedmaxnum','100'),
 ('feedfilternum','10'),
 ('importnum','100'),
 ('maxreward','10'),
 ('singlesent','50'),
 ('groupnum','8'),
 ('closeregister','0'),
 ('closeinvite','0'),
 ('close','0'),
 ('networkpublic','1'),
 ('networkpage','1'),
 ('seccode_register','1'),
 ('jc_tagrelated','1'),
 ('manualmoderator','1'),
 ('linkguide','1'),
 ('showall','1'),
 ('sendmailday','0'),
 ('realname','0'),
 ('namecheck','0'),
 ('namechange','0'),
 ('videophotocheck','1'),
 ('videophotochange','0'),
 ('name_allowviewspace','1'),
 ('name_allowfriend','1'),
 ('name_allowpoke','1'),
 ('name_allowdoing','1'),
 ('name_allowblog','0'),
 ('name_allowalbum','0'),
 ('name_allowthread','0'),
 ('name_allowshare','0'),
 ('name_allowcomment','0'),
 ('name_allowpost','0'),
 ('name_allowgift','0'),
 ('showallfriendnum','10'),
 ('feedtargetblank','1'),
 ('feedread','1'),
 ('feedhotnum','3'),
 ('feedhotday','2'),
 ('feedhotmin','3'),
 ('feedhiddenicon','friend,profile,task,wall'),
 ('jc_tagrelatedtime','86400'),
 ('privacy','a:2:{s:4:\"feed\";a:21:{s:6:\"invite\";i:1;s:4:\"post\";i:1;s:4:\"task\";i:1;s:5:\"album\";i:1;s:5:\"click\";i:1;s:4:\"show\";i:1;s:4:\"join\";i:1;s:4:\"blog\";i:1;s:6:\"upload\";i:1;s:5:\"share\";i:1;s:4:\"poll\";i:1;s:9:\"spaceopen\";i:1;s:5:\"event\";i:1;s:6:\"thread\";i:1;s:4:\"mtag\";i:1;s:5:\"doing\";i:1;s:8:\"joinpoll\";i:1;s:6:\"credit\";i:1;s:7:\"comment\";i:1;s:6:\"friend\";i:1;s:7:\"profile\";i:1;}s:4:\"view\";a:12:{s:5:\"share\";i:0;s:5:\"index\";i:0;s:4:\"poll\";i:0;s:5:\"event\";i:0;s:5:\"album\";i:0;s:4:\"mtag\";i:0;s:5:\"doing\";i:0;s:4:\"feed\";i:0;s:4:\"blog\";i:0;s:4:\"wall\";i:0;s:6:\"friend\";i:0;s:7:\"profile\";i:0;}}'),
 ('cronnextrun','1317354713'),
 ('my_status','0'),
 ('uniqueemail','1'),
 ('updatestat','1'),
 ('my_showgift','1'),
 ('topcachetime','60'),
 ('newspacenum','3'),
 ('seccode_login','0'),
 ('questionmode','0'),
 ('checkemail','0'),
 ('regipdate','0'),
 ('jchid','tzT9tSN28240'),
 ('lastupdate','1317354832'),
 ('status','0');
/*!40000 ALTER TABLE `jchome_config` ENABLE KEYS */;


--
-- Definition of table `jchome_creditlog`
--

DROP TABLE IF EXISTS `jchome_creditlog`;
CREATE TABLE `jchome_creditlog` (
  `clid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `rid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `total` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cyclenum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `credit` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `experience` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `starttime` int(10) unsigned NOT NULL DEFAULT '0',
  `info` text NOT NULL,
  `user` text NOT NULL,
  `app` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`clid`),
  KEY `uid` (`uid`,`rid`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_creditlog`
--

/*!40000 ALTER TABLE `jchome_creditlog` DISABLE KEYS */;
INSERT INTO `jchome_creditlog` (`clid`,`uid`,`rid`,`total`,`cyclenum`,`credit`,`experience`,`starttime`,`info`,`user`,`app`,`dateline`) VALUES 
 (1,1,1,1,1,10,0,0,'','','',1317354722),
 (2,1,10,1,1,15,15,0,'','','',1317354722);
/*!40000 ALTER TABLE `jchome_creditlog` ENABLE KEYS */;


--
-- Definition of table `jchome_creditrule`
--

DROP TABLE IF EXISTS `jchome_creditrule`;
CREATE TABLE `jchome_creditrule` (
  `rid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `rulename` char(20) NOT NULL DEFAULT '' COMMENT '动作名称',
  `action` char(20) NOT NULL DEFAULT '',
  `cycletype` tinyint(1) NOT NULL DEFAULT '0' COMMENT '奖励周期 ,0(一次性),1(每天),2(整点),3(间隔分钟),4(不限周期)',
  `cycletime` int(10) NOT NULL DEFAULT '0',
  `rewardnum` tinyint(2) NOT NULL DEFAULT '1' COMMENT '奖励次数,0表示不限次数',
  `rewardtype` tinyint(1) NOT NULL DEFAULT '1' COMMENT '奖励规则 ,1是奖励加分，0是惩罚扣分',
  `norepeat` tinyint(1) NOT NULL DEFAULT '0',
  `credit` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '奖励/扣除积分',
  `experience` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '奖励/扣除经验值',
  PRIMARY KEY (`rid`),
  KEY `action` (`action`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_creditrule`
--

/*!40000 ALTER TABLE `jchome_creditrule` DISABLE KEYS */;
INSERT INTO `jchome_creditrule` (`rid`,`rulename`,`action`,`cycletype`,`cycletime`,`rewardnum`,`rewardtype`,`norepeat`,`credit`,`experience`) VALUES 
 (1,'开通空间','register',0,0,1,1,0,10,0),
 (2,'实名认证','realname',0,0,1,1,0,20,20),
 (3,'邮箱认证','realemail',0,0,1,1,0,40,40),
 (4,'成功邀请好友','invitefriend',4,0,20,1,0,10,10),
 (5,'设置头像','setavatar',0,0,1,1,0,15,15),
 (6,'视频认证','videophoto',0,0,1,1,0,40,40),
 (7,'成功举报','report',4,0,0,1,0,2,2),
 (8,'更新心情','updatemood',1,0,3,1,0,3,3),
 (9,'热点信息','hotinfo',4,0,0,1,0,10,10),
 (10,'每天登陆','daylogin',1,0,1,1,0,15,15),
 (11,'访问别人空间','visit',1,0,10,1,2,1,1),
 (12,'打招呼','poke',1,0,10,1,2,1,1),
 (13,'留言','guestbook',1,0,20,1,2,2,2),
 (14,'被留言','getguestbook',1,0,5,1,2,1,0),
 (15,'发表记录','doing',1,0,5,1,0,1,1),
 (16,'发表日志','publishblog',1,0,3,1,0,5,5),
 (17,'上传图片','uploadimage',1,0,10,1,0,2,2),
 (18,'拍大头贴','camera',1,0,5,1,0,3,3),
 (19,'发表话题','publishthread',1,0,5,1,0,5,5),
 (20,'回复话题','replythread',1,0,10,1,1,1,1),
 (21,'创建投票','createpoll',1,0,5,1,0,2,2),
 (22,'参与投票','joinpoll',1,0,10,1,1,1,1),
 (23,'发起活动','createevent',1,0,1,1,0,3,3),
 (24,'参与活动','joinevent',1,0,1,1,1,1,1),
 (25,'推荐活动','recommendevent',4,0,0,1,0,10,10),
 (26,'发起分享','createshare',1,0,3,1,0,2,2),
 (27,'评论','comment',1,0,40,1,1,1,1),
 (28,'被评论','getcomment',1,0,20,1,1,1,0),
 (29,'安装应用','installapp',4,0,0,1,3,5,5),
 (30,'使用应用','useapp',1,0,10,1,3,1,1),
 (31,'信息表态','click',1,0,10,1,1,1,1),
 (32,'修改实名','editrealname',0,0,1,0,0,5,0),
 (33,'更改邮箱认证','editrealemail',0,0,1,0,0,5,0),
 (34,'头像被删除','delavatar',0,0,1,0,0,10,10),
 (35,'获取邀请码','invitecode',0,0,1,0,0,0,0),
 (36,'搜索一次','search',0,0,1,0,0,1,0),
 (37,'日志导入','blogimport',0,0,1,0,0,10,0),
 (38,'修改域名','modifydomain',0,0,1,0,0,5,0),
 (39,'日志被删除','delblog',0,0,1,0,0,10,10),
 (40,'记录被删除','deldoing',0,0,1,0,0,2,2),
 (41,'图片被删除','delimage',0,0,1,0,0,4,4),
 (42,'投票被删除','delpoll',0,0,1,0,0,4,4),
 (43,'话题被删除','delthread',0,0,1,0,0,4,4),
 (44,'活动被删除','delevent',0,0,1,0,0,6,6),
 (45,'分享被删除','delshare',0,0,1,0,0,4,4),
 (46,'留言被删除','delguestbook',0,0,1,0,0,4,4),
 (47,'评论被删除','delcomment',0,0,1,0,0,2,2);
/*!40000 ALTER TABLE `jchome_creditrule` ENABLE KEYS */;


--
-- Definition of table `jchome_cron`
--

DROP TABLE IF EXISTS `jchome_cron`;
CREATE TABLE `jchome_cron` (
  `cronid` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `available` tinyint(1) NOT NULL DEFAULT '0' COMMENT '有效性,0(无效),1(有效)',
  `type` enum('user','system') NOT NULL DEFAULT 'user',
  `name` char(50) NOT NULL DEFAULT '' COMMENT '任务名',
  `filename` char(50) NOT NULL DEFAULT '' COMMENT '任务脚本',
  `lastrun` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上次执行时间',
  `nextrun` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '下次执行时间',
  `weekday` tinyint(1) NOT NULL DEFAULT '0' COMMENT '星期几,本设置会覆盖下面的“日”设定',
  `day` tinyint(2) NOT NULL DEFAULT '0' COMMENT '日期',
  `hour` tinyint(2) NOT NULL DEFAULT '0' COMMENT '小时',
  `minute` char(36) NOT NULL DEFAULT '' COMMENT '分钟',
  PRIMARY KEY (`cronid`),
  KEY `nextrun` (`available`,`nextrun`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_cron`
--

/*!40000 ALTER TABLE `jchome_cron` DISABLE KEYS */;
INSERT INTO `jchome_cron` (`cronid`,`available`,`type`,`name`,`filename`,`lastrun`,`nextrun`,`weekday`,`day`,`hour`,`minute`) VALUES 
 (1,1,'system','更新浏览数统计','log.jsp',1317354832,1317354900,-1,-1,-1,'0	5	10	15	20	25	30	35	40	45	50	55'),
 (2,1,'system','清理过期feed','cleanfeed.jsp',1317354713,1317354713,-1,-1,3,'4'),
 (3,1,'system','清理个人通知','cleannotification.jsp',1317354713,1317354713,-1,-1,5,'6'),
 (4,1,'system','清理脚印和最新访客','cleantrace.jsp',1317354713,1317354713,-1,-1,2,'3');
/*!40000 ALTER TABLE `jchome_cron` ENABLE KEYS */;


--
-- Definition of table `jchome_data`
--

DROP TABLE IF EXISTS `jchome_data`;
CREATE TABLE `jchome_data` (
  `var` varchar(20) NOT NULL DEFAULT '',
  `datavalue` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`var`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_data`
--

/*!40000 ALTER TABLE `jchome_data` DISABLE KEYS */;
INSERT INTO `jchome_data` (`var`,`datavalue`,`dateline`) VALUES 
 ('mail','a:8:{s:4:\"port\";i:0;s:12:\"mailusername\";i:1;s:13:\"maildelimiter\";i:0;s:6:\"server\";s:0:\"\";s:13:\"auth_password\";s:0:\"\";s:4:\"from\";s:0:\"\";s:4:\"auth\";i:0;s:13:\"auth_username\";s:0:\"\";}',1317354714),
 ('setting','a:6:{s:13:\"maxthumbwidth\";i:0;s:14:\"maxthumbheight\";i:0;s:11:\"thumbheight\";i:100;s:10:\"thumbwidth\";i:100;s:14:\"watermarktrans\";i:75;s:12:\"watermarkpos\";i:4;}',1317354714),
 ('network','a:6:{s:5:\"share\";a:1:{s:5:\"cache\";i:500;}s:4:\"poll\";a:1:{s:5:\"cache\";i:500;}s:5:\"event\";a:1:{s:5:\"cache\";i:900;}s:6:\"thread\";a:2:{s:5:\"cache\";i:800;s:4:\"hot1\";i:3;}s:3:\"pic\";a:2:{s:5:\"cache\";i:700;s:4:\"hot1\";i:3;}s:4:\"blog\";a:2:{s:5:\"cache\";i:600;s:4:\"hot1\";i:3;}}',1317354714),
 ('newspacelist','a:1:{i:0;a:6:{s:3:\"uid\";i:1;s:11:\"videostatus\";i:0;s:10:\"namestatus\";i:0;s:8:\"username\";s:5:\"admin\";s:8:\"dateline\";i:1317354722;s:4:\"name\";s:0:\"\";}}',1317354722);
/*!40000 ALTER TABLE `jchome_data` ENABLE KEYS */;


--
-- Definition of table `jchome_docomment`
--

DROP TABLE IF EXISTS `jchome_docomment`;
CREATE TABLE `jchome_docomment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `upid` int(10) unsigned NOT NULL DEFAULT '0',
  `doid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `ip` varchar(20) NOT NULL DEFAULT '',
  `grade` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `doid` (`doid`,`dateline`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_docomment`
--

/*!40000 ALTER TABLE `jchome_docomment` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_docomment` ENABLE KEYS */;


--
-- Definition of table `jchome_doing`
--

DROP TABLE IF EXISTS `jchome_doing`;
CREATE TABLE `jchome_doing` (
  `doid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '作者UID',
  `username` varchar(15) NOT NULL DEFAULT '' COMMENT '作者名',
  `from` varchar(20) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发布时间',
  `message` text NOT NULL COMMENT '内容',
  `ip` varchar(20) NOT NULL DEFAULT '' COMMENT '发布ip',
  `replynum` int(10) unsigned NOT NULL DEFAULT '0',
  `mood` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`doid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_doing`
--

/*!40000 ALTER TABLE `jchome_doing` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_doing` ENABLE KEYS */;


--
-- Definition of table `jchome_event`
--

DROP TABLE IF EXISTS `jchome_event`;
CREATE TABLE `jchome_event` (
  `eventid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '创建者UID',
  `username` varchar(15) NOT NULL DEFAULT '' COMMENT '创建者名',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `title` varchar(80) NOT NULL DEFAULT '' COMMENT '标题',
  `classid` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '活动分类id',
  `province` varchar(20) NOT NULL DEFAULT '',
  `city` varchar(20) NOT NULL DEFAULT '',
  `location` varchar(80) NOT NULL DEFAULT '',
  `poster` varchar(60) NOT NULL DEFAULT '',
  `thumb` tinyint(1) NOT NULL DEFAULT '0',
  `remote` tinyint(1) NOT NULL DEFAULT '0',
  `deadline` int(10) unsigned NOT NULL DEFAULT '0',
  `starttime` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '活动开始时间',
  `endtime` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '活动结束时间',
  `public` tinyint(3) NOT NULL DEFAULT '0' COMMENT '公开性质,0(私密),1(半公开),2(完全公开)',
  `membernum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `follownum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `viewnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `grade` tinyint(3) NOT NULL DEFAULT '0' COMMENT '活动状态,-2(已关闭),-1(未通过审核),0(待审核),1(通过审核),2(推荐)',
  `recommendtime` int(10) unsigned NOT NULL DEFAULT '0',
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `picnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `threadnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `updatetime` int(10) unsigned NOT NULL DEFAULT '0',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '热度',
  PRIMARY KEY (`eventid`),
  KEY `grade` (`grade`,`recommendtime`),
  KEY `membernum` (`membernum`),
  KEY `uid` (`uid`,`eventid`),
  KEY `tagid` (`tagid`,`eventid`),
  KEY `topicid` (`topicid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_event`
--

/*!40000 ALTER TABLE `jchome_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_event` ENABLE KEYS */;


--
-- Definition of table `jchome_eventclass`
--

DROP TABLE IF EXISTS `jchome_eventclass`;
CREATE TABLE `jchome_eventclass` (
  `classid` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `classname` varchar(80) NOT NULL DEFAULT '' COMMENT '名称',
  `poster` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认海报,活动发起者发起此类型的活动时如果没有上传海报则默认使用此海报',
  `template` text NOT NULL COMMENT '默认模板 ,建议活动发起者发起此类型的活动时按此内容模板填写活动介绍',
  `displayorder` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '显示顺序',
  PRIMARY KEY (`classid`),
  UNIQUE KEY `classname` (`classname`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_eventclass`
--

/*!40000 ALTER TABLE `jchome_eventclass` DISABLE KEYS */;
INSERT INTO `jchome_eventclass` (`classid`,`classname`,`poster`,`template`,`displayorder`) VALUES 
 (1,'生活/聚会',0,'费用说明：\r\n集合地点：\r\n着装要求：\r\n联系方式：\r\n注意事项：',1),
 (2,'出行/旅游',0,'路线说明:\r\n费用说明:\r\n装备要求:\r\n交通工具:\r\n集合地点:\r\n联系方式:\r\n注意事项:',2),
 (3,'比赛/运动',0,'费用说明：\r\n集合地点：\r\n着装要求：\r\n场地介绍：\r\n联系方式：\r\n注意事项：',4),
 (4,'电影/演出',0,'剧情介绍：\r\n费用说明：\r\n集合地点：\r\n联系方式：\r\n注意事项：',3),
 (5,'教育/讲座',0,'主办单位：\r\n活动主题：\r\n费用说明：\r\n集合地点：\r\n联系方式：\r\n注意事项：',5),
 (6,'其它',0,'',6);
/*!40000 ALTER TABLE `jchome_eventclass` ENABLE KEYS */;


--
-- Definition of table `jchome_eventfield`
--

DROP TABLE IF EXISTS `jchome_eventfield`;
CREATE TABLE `jchome_eventfield` (
  `eventid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `detail` text NOT NULL,
  `template` varchar(255) NOT NULL DEFAULT '',
  `limitnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `verify` tinyint(1) NOT NULL DEFAULT '0',
  `allowpic` tinyint(1) NOT NULL DEFAULT '0',
  `allowpost` tinyint(1) NOT NULL DEFAULT '0',
  `allowinvite` tinyint(1) NOT NULL DEFAULT '0',
  `allowfellow` tinyint(1) NOT NULL DEFAULT '0',
  `hotuser` text NOT NULL,
  PRIMARY KEY (`eventid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_eventfield`
--

/*!40000 ALTER TABLE `jchome_eventfield` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_eventfield` ENABLE KEYS */;


--
-- Definition of table `jchome_eventinvite`
--

DROP TABLE IF EXISTS `jchome_eventinvite`;
CREATE TABLE `jchome_eventinvite` (
  `eventid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `touid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tousername` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`eventid`,`touid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_eventinvite`
--

/*!40000 ALTER TABLE `jchome_eventinvite` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_eventinvite` ENABLE KEYS */;


--
-- Definition of table `jchome_eventpic`
--

DROP TABLE IF EXISTS `jchome_eventpic`;
CREATE TABLE `jchome_eventpic` (
  `picid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `eventid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`picid`),
  KEY `eventid` (`eventid`,`picid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_eventpic`
--

/*!40000 ALTER TABLE `jchome_eventpic` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_eventpic` ENABLE KEYS */;


--
-- Definition of table `jchome_feed`
--

DROP TABLE IF EXISTS `jchome_feed`;
CREATE TABLE `jchome_feed` (
  `feedid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `appid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `icon` varchar(30) NOT NULL DEFAULT '' COMMENT '类型',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '0表示全局动态',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发布时间，动态会在这个日期到来之前，一直显示在第一位。',
  `friend` tinyint(1) NOT NULL DEFAULT '0',
  `hash_template` varchar(32) NOT NULL DEFAULT '',
  `hash_data` varchar(32) NOT NULL DEFAULT '',
  `title_template` text NOT NULL COMMENT '标题模板，含占位符，支持html，例如：加粗 <b></b>，变色 <font color=red></font> ',
  `title_data` text NOT NULL,
  `body_template` text NOT NULL COMMENT '内容模板，含占位符',
  `body_data` text NOT NULL,
  `body_general` text NOT NULL COMMENT '备注',
  `image_1` varchar(255) NOT NULL DEFAULT '' COMMENT '第1张图片地址',
  `image_1_link` varchar(255) NOT NULL DEFAULT '' COMMENT '第1张图片链接',
  `image_2` varchar(255) NOT NULL DEFAULT '' COMMENT '第2张图片地址',
  `image_2_link` varchar(255) NOT NULL DEFAULT '' COMMENT '第2张图片链接',
  `image_3` varchar(255) NOT NULL DEFAULT '' COMMENT '第3张图片地址',
  `image_3_link` varchar(255) NOT NULL DEFAULT '' COMMENT '第3张图片链接',
  `image_4` varchar(255) NOT NULL DEFAULT '' COMMENT '第4张图片地址',
  `image_4_link` varchar(255) NOT NULL DEFAULT '' COMMENT '第4张图片链接',
  `target_ids` text NOT NULL,
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `idtype` varchar(15) NOT NULL DEFAULT '',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '热度',
  PRIMARY KEY (`feedid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `dateline` (`dateline`),
  KEY `hot` (`hot`),
  KEY `id` (`id`,`idtype`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_feed`
--

/*!40000 ALTER TABLE `jchome_feed` DISABLE KEYS */;
INSERT INTO `jchome_feed` (`feedid`,`appid`,`icon`,`uid`,`username`,`dateline`,`friend`,`hash_template`,`hash_data`,`title_template`,`title_data`,`body_template`,`body_data`,`body_general`,`image_1`,`image_1_link`,`image_2`,`image_2_link`,`image_3`,`image_3_link`,`image_4`,`image_4_link`,`target_ids`,`id`,`idtype`,`hot`) VALUES 
 (1,0,'profile',1,'admin',1317354722,0,'3a7101a64ea7927f0b3f5179b7a457b3','ec7d775d9211880bca2ba1d401e3bcb9','{actor} 开通了自己的个人主页','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0);
/*!40000 ALTER TABLE `jchome_feed` ENABLE KEYS */;


--
-- Definition of table `jchome_friend`
--

DROP TABLE IF EXISTS `jchome_friend`;
CREATE TABLE `jchome_friend` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fusername` varchar(15) NOT NULL DEFAULT '',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `gid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `note` varchar(50) NOT NULL DEFAULT '',
  `num` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`fuid`),
  KEY `fuid` (`fuid`),
  KEY `status` (`uid`,`status`,`num`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_friend`
--

/*!40000 ALTER TABLE `jchome_friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_friend` ENABLE KEYS */;


--
-- Definition of table `jchome_friendguide`
--

DROP TABLE IF EXISTS `jchome_friendguide`;
CREATE TABLE `jchome_friendguide` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fusername` char(15) NOT NULL DEFAULT '',
  `num` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`fuid`),
  KEY `uid` (`uid`,`num`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_friendguide`
--

/*!40000 ALTER TABLE `jchome_friendguide` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_friendguide` ENABLE KEYS */;


--
-- Definition of table `jchome_friendlog`
--

DROP TABLE IF EXISTS `jchome_friendlog`;
CREATE TABLE `jchome_friendlog` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `action` varchar(10) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`fuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_friendlog`
--

/*!40000 ALTER TABLE `jchome_friendlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_friendlog` ENABLE KEYS */;


--
-- Definition of table `jchome_gift`
--

DROP TABLE IF EXISTS `jchome_gift`;
CREATE TABLE `jchome_gift` (
  `giftid` int(4) NOT NULL AUTO_INCREMENT,
  `giftname` varchar(20) NOT NULL COMMENT '礼物名称',
  `tips` varchar(20) NOT NULL COMMENT '礼物提示,当用户鼠标移动到礼物图像时，显示的提示信息',
  `price` int(4) NOT NULL COMMENT '礼物价格，必填，值必须大于零',
  `buycount` int(4) NOT NULL COMMENT '购买次数',
  `icon` text NOT NULL COMMENT '礼物图像,必填',
  `addtime` int(10) NOT NULL COMMENT '添加日期',
  `typeid` varchar(20) NOT NULL COMMENT '所属分类id,必填，设置礼物所属分类',
  PRIMARY KEY (`giftid`)
) ENGINE=MyISAM AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_gift`
--

/*!40000 ALTER TABLE `jchome_gift` DISABLE KEYS */;
INSERT INTO `jchome_gift` (`giftid`,`giftname`,`tips`,`price`,`buycount`,`icon`,`addtime`,`typeid`) VALUES 
 (50,'玫瑰','一朵玫瑰',0,0,'image/gift/default/1275548853640_7KBa9p.gif',1275548853,'defGift'),
 (51,'生日蛋糕','生日蛋糕',0,0,'image/gift/default/1275548899437_H1fx1b.gif',1275548899,'defGift'),
 (52,'棒棒糖','棒棒糖',0,0,'image/gift/default/1275548925437_RyNFXz.gif',1275548925,'defGift'),
 (53,'红包','恭喜发财-红包',0,0,'image/gift/default/1275548965687_4hXFYg.gif',1275548965,'defGift'),
 (54,'元宝','金元宝',0,0,'image/gift/default/1275548996484_geO3Bd.gif',1275548996,'defGift'),
 (55,'熊宝宝','玩具熊宝宝',0,0,'image/gift/default/1275549026281_PXA293.gif',1275549026,'defGift'),
 (56,'啤酒','啤酒',0,0,'image/gift/default/1275549058015_mg7DN6.gif',1275549058,'defGift'),
 (57,'宝箱','蓝色宝箱',0,0,'image/gift/default/1275549081187_yzonc0.gif',1275549081,'defGift'),
 (58,'苹果','金苹果',0,0,'image/gift/default/1275549206843_AhQFuV.gif',1275549206,'defGift'),
 (59,'小天使','小天使',0,0,'image/gift/default/1275549265218_tg0pGk.gif',1275549265,'defGift'),
 (60,'钻石','钻石',0,0,'image/gift/default/1275549292171_pzXItW.gif',1275549292,'defGift'),
 (61,'手枪','手枪',0,0,'image/gift/advanced/1275549444312_z7tPfQ.gif',1275549444,'advGift'),
 (62,'搓衣板','回家用吧',0,0,'image/gift/advanced/1275549471609_2LsSOz.gif',1275549471,'advGift'),
 (63,'红肚兜','性感肚兜',0,0,'image/gift/advanced/1275549493593_bMSbkz.gif',1275549493,'advGift'),
 (64,'内裤','内裤',0,0,'image/gift/advanced/1275549524671_7kAhdI.gif',1275549524,'advGift'),
 (65,'红蜡烛','红蜡烛',0,0,'image/gift/advanced/1275549544312_7saP3Z.gif',1275549544,'advGift'),
 (66,'一束玫瑰','一束玫瑰',0,0,'image/gift/advanced/1275549566359_pidssZ.gif',1275549566,'advGift'),
 (67,'钻戒','粉色钻戒',0,0,'image/gift/advanced/1275549588921_GVGMbK.gif',1275549588,'advGift'),
 (68,'love香水','love香水',0,0,'image/gift/advanced/1275549606843_WDE6IT.gif',1275549606,'advGift'),
 (69,'Zippo','Zippo',0,0,'image/gift/advanced/1275549622593_yO4kf7.gif',1275549622,'advGift'),
 (70,'避孕套','避孕套',0,0,'image/gift/advanced/1275549642609_8FYmnF.gif',1275549642,'advGift'),
 (71,'饺子','饺子',0,0,'image/gift/advanced/1275549677296_Y8CQeg.gif',1275549677,'advGift'),
 (72,'冰棍','冰棍',0,0,'image/gift/advanced/1275549898515_XBL4XQ.gif',1275549898,'advGift'),
 (73,'创可贴','邦由创可贴',0,0,'image/gift/advanced/1275549917343_TdOM0Y.gif',1275549917,'advGift'),
 (74,'胸罩','胸罩',0,0,'image/gift/advanced/1275549930687_1ggT7u.gif',1275549930,'advGift'),
 (75,'脑白金','脑白金',0,0,'image/gift/advanced/1275549948562_8EBlaq.gif',1275549948,'advGift'),
 (76,'香蕉','香蕉',0,0,'image/gift/advanced/1275549962421_GGD6ny.gif',1275549962,'advGift'),
 (77,'高跟鞋','高跟鞋',0,0,'image/gift/advanced/1275549975390_K4WqIz.gif',1275549975,'advGift'),
 (78,'香烟','骆驼牌香烟',0,0,'image/gift/advanced/1275549995562_52QIW6.gif',1275549995,'advGift'),
 (79,'咖啡','咖啡',0,0,'image/gift/advanced/1275550025937_gMRcbq.gif',1275550025,'advGift'),
 (80,'奶瓶','奶瓶',0,0,'image/gift/advanced/1275550050468_R4kV3F.gif',1275550050,'advGift'),
 (81,'靠枕','靠枕',0,0,'image/gift/default/1275550116515_KLl7iy.gif',1275550116,'defGift');
/*!40000 ALTER TABLE `jchome_gift` ENABLE KEYS */;


--
-- Definition of table `jchome_giftreceived`
--

DROP TABLE IF EXISTS `jchome_giftreceived`;
CREATE TABLE `jchome_giftreceived` (
  `grid` int(4) NOT NULL AUTO_INCREMENT,
  `senderid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `sender` varchar(15) NOT NULL,
  `receiverid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `receiver` varchar(15) NOT NULL,
  `giftid` int(4) NOT NULL,
  `quiet` tinyint(1) NOT NULL,
  `anonymous` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `timed` tinyint(1) NOT NULL,
  `fee` tinyint(1) NOT NULL,
  `receipttime` int(10) NOT NULL,
  PRIMARY KEY (`grid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_giftreceived`
--

/*!40000 ALTER TABLE `jchome_giftreceived` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_giftreceived` ENABLE KEYS */;


--
-- Definition of table `jchome_giftsent`
--

DROP TABLE IF EXISTS `jchome_giftsent`;
CREATE TABLE `jchome_giftsent` (
  `gsid` int(4) NOT NULL AUTO_INCREMENT,
  `senderid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `sender` varchar(15) NOT NULL,
  `receiverid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `receiver` varchar(15) NOT NULL,
  `giftid` int(4) NOT NULL,
  `quiet` tinyint(1) NOT NULL DEFAULT '0',
  `anonymous` tinyint(1) NOT NULL DEFAULT '0',
  `timed` tinyint(1) NOT NULL DEFAULT '0',
  `fee` tinyint(1) NOT NULL DEFAULT '0',
  `sendtime` int(10) NOT NULL,
  PRIMARY KEY (`gsid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_giftsent`
--

/*!40000 ALTER TABLE `jchome_giftsent` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_giftsent` ENABLE KEYS */;


--
-- Definition of table `jchome_gifttype`
--

DROP TABLE IF EXISTS `jchome_gifttype`;
CREATE TABLE `jchome_gifttype` (
  `typeid` varchar(20) NOT NULL,
  `typename` varchar(20) NOT NULL,
  `fee` tinyint(1) NOT NULL,
  `order` tinyint(2) NOT NULL,
  PRIMARY KEY (`typeid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_gifttype`
--

/*!40000 ALTER TABLE `jchome_gifttype` DISABLE KEYS */;
INSERT INTO `jchome_gifttype` (`typeid`,`typename`,`fee`,`order`) VALUES 
 ('advGift','高级礼物',0,1),
 ('birthday','生日',1,1),
 ('card','贺卡',1,4),
 ('defGift','普通礼物',0,0),
 ('feeGift','收费礼物',0,2),
 ('love','爱情',1,2),
 ('magic','魔法',1,3),
 ('other','其他',1,5);
/*!40000 ALTER TABLE `jchome_gifttype` ENABLE KEYS */;


--
-- Definition of table `jchome_invite`
--

DROP TABLE IF EXISTS `jchome_invite`;
CREATE TABLE `jchome_invite` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `code` varchar(20) NOT NULL DEFAULT '',
  `fuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fusername` varchar(15) NOT NULL DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL DEFAULT '',
  `appid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_invite`
--

/*!40000 ALTER TABLE `jchome_invite` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_invite` ENABLE KEYS */;


--
-- Definition of table `jchome_log`
--

DROP TABLE IF EXISTS `jchome_log`;
CREATE TABLE `jchome_log` (
  `logid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `idtype` char(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`logid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_log`
--

/*!40000 ALTER TABLE `jchome_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_log` ENABLE KEYS */;


--
-- Definition of table `jchome_magic`
--

DROP TABLE IF EXISTS `jchome_magic`;
CREATE TABLE `jchome_magic` (
  `mid` varchar(15) NOT NULL DEFAULT '' COMMENT 'id',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
  `description` text NOT NULL COMMENT '说明描述',
  `forbiddengid` text NOT NULL,
  `charge` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '单价,购买时单个需要花费的积分值，需小于65535',
  `experience` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '经验成长,购买单个可以增长的经验值，需小于65535',
  `provideperoid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '补给周期,若道具商店中此道具已售光，在补给周期内自动补给一次.0(总是可以),3600(间隔1小时),86400(间隔24小时),604800(间隔7天)',
  `providecount` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '补给数目,若道具商店中此道具已售光，自动补给一次的数目，需小于65535',
  `useperoid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用周期,设定用户使用此道具的使用周期.0(总是可以),3600(间隔1小时),86400(间隔24小时),604800(间隔7天)',
  `usecount` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '使用数目,设定用户在使用周期内最多能使用此道具的个数，需小于65535.',
  `displayorder` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `custom` text NOT NULL,
  `close` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1(禁用道具),0(启用道具)',
  PRIMARY KEY (`mid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_magic`
--

/*!40000 ALTER TABLE `jchome_magic` DISABLE KEYS */;
INSERT INTO `jchome_magic` (`mid`,`name`,`description`,`forbiddengid`,`charge`,`experience`,`provideperoid`,`providecount`,`useperoid`,`usecount`,`displayorder`,`custom`,`close`) VALUES 
 ('invisible','隐身草','让自己隐身登录，不显示在线，24小时内有效','',50,5,86400,10,86400,1,0,'',0),
 ('friendnum','好友增容卡','在允许添加的最多好友数限制外，增加10个好友名额','',30,3,86400,999,0,1,0,'',0),
 ('attachsize','附件增容卡','使用一次，可以给自己增加 10M 的附件空间','',30,3,86400,999,0,1,0,'',0),
 ('thunder','雷鸣之声','发布一条全站信息，让大家知道自己上线了','',500,5,86400,5,86400,1,0,'',0),
 ('updateline','救生圈','把指定对象的发布时间更新为当前时间','',200,5,86400,999,0,1,0,'',0),
 ('downdateline','时空机','把指定对象的发布时间修改为过去的时间','',250,5,86400,999,0,1,0,'',0),
 ('color','彩色灯','把指定对象的标题变成彩色的','',50,5,86400,999,0,1,0,'',0),
 ('hot','热点灯','把指定对象的热度增加站点推荐的热点值','',50,5,86400,999,0,1,0,'',0),
 ('visit','互访卡','随机选择10个好友，向其打招呼、留言或访问空间','',20,2,86400,999,0,1,0,'',0),
 ('icon','彩虹蛋','给指定对象的标题前面增加图标（最多8个图标）','',20,2,86400,999,0,1,0,'',0),
 ('flicker','彩虹炫','让评论、留言的文字闪烁起来','',30,3,86400,999,0,1,0,'',0),
 ('gift','红包卡','在自己的空间埋下积分红包送给来访者','',20,2,86400,999,0,1,0,'',0),
 ('superstar','超级明星','在个人主页，给自己的头像增加超级明星标识','',30,3,86400,999,0,1,0,'',0),
 ('viewmagiclog','八卦镜','查看指定用户最近使用的道具记录','',100,5,86400,999,0,1,0,'',0),
 ('viewmagic','透视镜','查看指定用户当前持有的道具','',100,5,86400,999,0,1,0,'',0),
 ('viewvisitor','偷窥镜','查看指定用户最近访问过的10个空间','',100,5,86400,999,0,1,0,'',0),
 ('call','点名卡','发通知给自己的好友，让他们来查看指定的对象','',50,5,86400,999,0,1,0,'',0),
 ('coupon','代金券','购买道具时折换一定量的积分','',0,0,0,0,0,1,0,'',0),
 ('frame','相框','给自己的照片添上相框','',30,3,86400,999,0,1,0,'',0),
 ('bgimage','信纸','给指定的对象添加信纸背景','',30,3,86400,999,0,1,0,'',0),
 ('doodle','涂鸦板','允许在留言、评论等操作时使用涂鸦板','',30,3,86400,999,0,1,0,'',0),
 ('anonymous','匿名卡','在指定的地方，让自己的名字显示为匿名','',50,5,86400,999,0,1,0,'',0),
 ('reveal','照妖镜','可以查看一次匿名用户的真实身份','',100,5,86400,999,0,1,0,'',0),
 ('license','道具转让许可证','使用许可证，将道具赠送给指定好友','',10,1,3600,999,0,1,0,'',0),
 ('detector','探测器','探测埋了红包的空间','',10,1,86400,999,0,1,0,'',0);
/*!40000 ALTER TABLE `jchome_magic` ENABLE KEYS */;


--
-- Definition of table `jchome_magicinlog`
--

DROP TABLE IF EXISTS `jchome_magicinlog`;
CREATE TABLE `jchome_magicinlog` (
  `logid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '' COMMENT '用户',
  `mid` varchar(15) NOT NULL DEFAULT '' COMMENT '道具id',
  `count` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '道具数量',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '方式,2(获赠),3(升级用户组),其他(购买)',
  `fromid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `credit` smallint(6) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) NOT NULL DEFAULT '0' COMMENT '时间',
  PRIMARY KEY (`logid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `type` (`type`,`fromid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_magicinlog`
--

/*!40000 ALTER TABLE `jchome_magicinlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_magicinlog` ENABLE KEYS */;


--
-- Definition of table `jchome_magicstore`
--

DROP TABLE IF EXISTS `jchome_magicstore`;
CREATE TABLE `jchome_magicstore` (
  `mid` varchar(15) NOT NULL DEFAULT '',
  `storage` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '库存数量',
  `lastprovide` int(10) unsigned NOT NULL DEFAULT '0',
  `sellcount` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '售出数',
  `sellcredit` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '回收积分',
  PRIMARY KEY (`mid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_magicstore`
--

/*!40000 ALTER TABLE `jchome_magicstore` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_magicstore` ENABLE KEYS */;


--
-- Definition of table `jchome_magicuselog`
--

DROP TABLE IF EXISTS `jchome_magicuselog`;
CREATE TABLE `jchome_magicuselog` (
  `logid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '' COMMENT '用户',
  `mid` varchar(15) NOT NULL DEFAULT '' COMMENT '道具id',
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `idtype` varchar(20) NOT NULL DEFAULT '',
  `count` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `data` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '时间',
  `expire` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`logid`),
  KEY `uid` (`uid`,`mid`),
  KEY `id` (`id`,`idtype`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_magicuselog`
--

/*!40000 ALTER TABLE `jchome_magicuselog` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_magicuselog` ENABLE KEYS */;


--
-- Definition of table `jchome_mailcron`
--

DROP TABLE IF EXISTS `jchome_mailcron`;
CREATE TABLE `jchome_mailcron` (
  `cid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `touid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL DEFAULT '',
  `sendtime` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`cid`),
  KEY `sendtime` (`sendtime`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_mailcron`
--

/*!40000 ALTER TABLE `jchome_mailcron` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_mailcron` ENABLE KEYS */;


--
-- Definition of table `jchome_mailqueue`
--

DROP TABLE IF EXISTS `jchome_mailqueue`;
CREATE TABLE `jchome_mailqueue` (
  `qid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `cid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `subject` text NOT NULL,
  `message` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`qid`),
  KEY `mcid` (`cid`,`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_mailqueue`
--

/*!40000 ALTER TABLE `jchome_mailqueue` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_mailqueue` ENABLE KEYS */;


--
-- Definition of table `jchome_member`
--

DROP TABLE IF EXISTS `jchome_member`;
CREATE TABLE `jchome_member` (
  `uid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `username` char(15) NOT NULL DEFAULT '',
  `password` char(32) NOT NULL DEFAULT '',
  `blacklist` text NOT NULL,
  `salt` char(6) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_member`
--

/*!40000 ALTER TABLE `jchome_member` DISABLE KEYS */;
INSERT INTO `jchome_member` (`uid`,`username`,`password`,`blacklist`,`salt`) VALUES 
 (1,'admin','b375717fdc45665a4aba43df73d2a7d9','','7x7gCC');
/*!40000 ALTER TABLE `jchome_member` ENABLE KEYS */;


--
-- Definition of table `jchome_mtag`
--

DROP TABLE IF EXISTS `jchome_mtag`;
CREATE TABLE `jchome_mtag` (
  `tagid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `tagname` varchar(40) NOT NULL DEFAULT '' COMMENT '群组名',
  `fieldid` smallint(6) NOT NULL DEFAULT '0' COMMENT '归属栏目id',
  `membernum` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '用户数',
  `threadnum` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '话题数',
  `postnum` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '回帖数',
  `close` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定,1(是)',
  `announcement` text NOT NULL,
  `pic` varchar(150) NOT NULL DEFAULT '',
  `closeapply` tinyint(1) NOT NULL DEFAULT '0',
  `joinperm` tinyint(1) NOT NULL DEFAULT '0' COMMENT '加入权限,0(公开),1(审核),2(私密)',
  `viewperm` tinyint(1) NOT NULL DEFAULT '0' COMMENT '浏览权限,0(公开),1(封闭)',
  `threadperm` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发帖权限,0(仅成员可发话题),1(所有人可以发话题)',
  `postperm` tinyint(1) NOT NULL DEFAULT '0' COMMENT '回帖权限,0(仅成员可回帖),1(所有人可回帖)',
  `recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐,1(是)',
  `moderator` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`tagid`),
  KEY `tagname` (`tagname`),
  KEY `threadnum` (`threadnum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_mtag`
--

/*!40000 ALTER TABLE `jchome_mtag` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_mtag` ENABLE KEYS */;


--
-- Definition of table `jchome_mtaginvite`
--

DROP TABLE IF EXISTS `jchome_mtaginvite`;
CREATE TABLE `jchome_mtaginvite` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fromuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fromusername` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`tagid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_mtaginvite`
--

/*!40000 ALTER TABLE `jchome_mtaginvite` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_mtaginvite` ENABLE KEYS */;


--
-- Definition of table `jchome_myapp`
--

DROP TABLE IF EXISTS `jchome_myapp`;
CREATE TABLE `jchome_myapp` (
  `appid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `appname` varchar(60) NOT NULL DEFAULT '',
  `narrow` tinyint(1) NOT NULL DEFAULT '0',
  `flag` tinyint(1) NOT NULL DEFAULT '0',
  `version` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `displaymethod` tinyint(1) NOT NULL DEFAULT '0',
  `displayorder` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`appid`),
  KEY `flag` (`flag`,`displayorder`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_myapp`
--

/*!40000 ALTER TABLE `jchome_myapp` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_myapp` ENABLE KEYS */;


--
-- Definition of table `jchome_myinvite`
--

DROP TABLE IF EXISTS `jchome_myinvite`;
CREATE TABLE `jchome_myinvite` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `typename` varchar(100) NOT NULL DEFAULT '',
  `appid` mediumint(8) NOT NULL DEFAULT '0',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `fromuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `touid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `myml` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `hash` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `hash` (`hash`),
  KEY `uid` (`touid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_myinvite`
--

/*!40000 ALTER TABLE `jchome_myinvite` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_myinvite` ENABLE KEYS */;


--
-- Definition of table `jchome_newpm`
--

DROP TABLE IF EXISTS `jchome_newpm`;
CREATE TABLE `jchome_newpm` (
  `uid` mediumint(8) unsigned NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_newpm`
--

/*!40000 ALTER TABLE `jchome_newpm` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_newpm` ENABLE KEYS */;


--
-- Definition of table `jchome_notification`
--

DROP TABLE IF EXISTS `jchome_notification`;
CREATE TABLE `jchome_notification` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `type` varchar(20) NOT NULL DEFAULT '',
  `new` tinyint(1) NOT NULL DEFAULT '0',
  `authorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `author` varchar(15) NOT NULL DEFAULT '',
  `note` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`,`new`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_notification`
--

/*!40000 ALTER TABLE `jchome_notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_notification` ENABLE KEYS */;


--
-- Definition of table `jchome_pic`
--

DROP TABLE IF EXISTS `jchome_pic`;
CREATE TABLE `jchome_pic` (
  `picid` mediumint(8) NOT NULL AUTO_INCREMENT,
  `albumid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `postip` varchar(20) NOT NULL DEFAULT '',
  `filename` varchar(100) NOT NULL DEFAULT '',
  `title` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(20) NOT NULL DEFAULT '',
  `size` int(10) unsigned NOT NULL DEFAULT '0',
  `filepath` varchar(60) NOT NULL DEFAULT '',
  `thumb` tinyint(1) NOT NULL DEFAULT '0',
  `remote` tinyint(1) NOT NULL DEFAULT '0',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `click_6` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_7` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_8` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_9` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_10` smallint(6) unsigned NOT NULL DEFAULT '0',
  `magicframe` tinyint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`picid`),
  KEY `albumid` (`albumid`,`dateline`),
  KEY `topicid` (`topicid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_pic`
--

/*!40000 ALTER TABLE `jchome_pic` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_pic` ENABLE KEYS */;


--
-- Definition of table `jchome_picfield`
--

DROP TABLE IF EXISTS `jchome_picfield`;
CREATE TABLE `jchome_picfield` (
  `picid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `hotuser` text NOT NULL,
  PRIMARY KEY (`picid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_picfield`
--

/*!40000 ALTER TABLE `jchome_picfield` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_picfield` ENABLE KEYS */;


--
-- Definition of table `jchome_pms`
--

DROP TABLE IF EXISTS `jchome_pms`;
CREATE TABLE `jchome_pms` (
  `pmid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `msgfrom` varchar(15) NOT NULL DEFAULT '',
  `msgfromid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `msgtoid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `folder` enum('inbox','outbox') NOT NULL DEFAULT 'inbox',
  `new` tinyint(1) NOT NULL DEFAULT '0',
  `subject` varchar(75) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `delstatus` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `related` int(10) unsigned NOT NULL DEFAULT '0',
  `fromappid` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`pmid`),
  KEY `msgtoid` (`msgtoid`,`folder`,`dateline`),
  KEY `msgfromid` (`msgfromid`,`folder`,`dateline`),
  KEY `related` (`related`),
  KEY `getnum` (`msgtoid`,`folder`,`delstatus`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_pms`
--

/*!40000 ALTER TABLE `jchome_pms` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_pms` ENABLE KEYS */;


--
-- Definition of table `jchome_poke`
--

DROP TABLE IF EXISTS `jchome_poke`;
CREATE TABLE `jchome_poke` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fromuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fromusername` varchar(15) NOT NULL DEFAULT '',
  `note` varchar(255) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `iconid` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`fromuid`),
  KEY `uid` (`uid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_poke`
--

/*!40000 ALTER TABLE `jchome_poke` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_poke` ENABLE KEYS */;


--
-- Definition of table `jchome_poll`
--

DROP TABLE IF EXISTS `jchome_poll`;
CREATE TABLE `jchome_poll` (
  `pid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '作者UID',
  `username` char(15) NOT NULL DEFAULT '' COMMENT '作者名',
  `subject` char(80) NOT NULL DEFAULT '' COMMENT '标题',
  `voternum` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '参与人数',
  `replynum` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '评论数',
  `multiple` tinyint(1) NOT NULL DEFAULT '0',
  `maxchoice` tinyint(3) NOT NULL DEFAULT '0',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别限制，1(男),2(女)',
  `noreply` tinyint(1) NOT NULL DEFAULT '0' COMMENT '评论限制，0(全站用户可见),1(仅好友可评论)',
  `credit` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `percredit` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '悬赏积分',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '过期投票，1(未过期),2(已过期)',
  `lastvote` int(10) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发布时间',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '热度',
  PRIMARY KEY (`pid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `topicid` (`topicid`,`dateline`),
  KEY `voternum` (`voternum`),
  KEY `dateline` (`dateline`),
  KEY `lastvote` (`lastvote`),
  KEY `hot` (`hot`),
  KEY `percredit` (`percredit`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_poll`
--

/*!40000 ALTER TABLE `jchome_poll` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_poll` ENABLE KEYS */;


--
-- Definition of table `jchome_pollfield`
--

DROP TABLE IF EXISTS `jchome_pollfield`;
CREATE TABLE `jchome_pollfield` (
  `pid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `notify` tinyint(1) NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `summary` text NOT NULL,
  `option` text NOT NULL,
  `invite` text NOT NULL,
  `hotuser` text NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_pollfield`
--

/*!40000 ALTER TABLE `jchome_pollfield` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_pollfield` ENABLE KEYS */;


--
-- Definition of table `jchome_polloption`
--

DROP TABLE IF EXISTS `jchome_polloption`;
CREATE TABLE `jchome_polloption` (
  `oid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `pid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `votenum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `option` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`oid`),
  KEY `pid` (`pid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_polloption`
--

/*!40000 ALTER TABLE `jchome_polloption` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_polloption` ENABLE KEYS */;


--
-- Definition of table `jchome_polluser`
--

DROP TABLE IF EXISTS `jchome_polluser`;
CREATE TABLE `jchome_polluser` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `pid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `option` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`pid`),
  KEY `pid` (`pid`,`dateline`),
  KEY `uid` (`uid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_polluser`
--

/*!40000 ALTER TABLE `jchome_polluser` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_polluser` ENABLE KEYS */;


--
-- Definition of table `jchome_post`
--

DROP TABLE IF EXISTS `jchome_post`;
CREATE TABLE `jchome_post` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '群组ID',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '话题ID',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '作者UID',
  `username` varchar(15) NOT NULL DEFAULT '' COMMENT '作者名',
  `ip` varchar(20) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发布时间',
  `message` text NOT NULL COMMENT '内容',
  `pic` varchar(255) NOT NULL DEFAULT '',
  `isthread` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否主题帖,1(是)',
  `hotuser` text NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `tid` (`tid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_post`
--

/*!40000 ALTER TABLE `jchome_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_post` ENABLE KEYS */;


--
-- Definition of table `jchome_profield`
--

DROP TABLE IF EXISTS `jchome_profield`;
CREATE TABLE `jchome_profield` (
  `fieldid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(80) NOT NULL DEFAULT '' COMMENT '名称',
  `note` varchar(255) NOT NULL DEFAULT '' COMMENT '简单介绍',
  `formtype` varchar(20) NOT NULL DEFAULT '0' COMMENT '填写(表单)类型',
  `inputnum` smallint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户可加入群组最多个数，填写类型是多选类型或填写类型的才有',
  `choice` text NOT NULL COMMENT '可选值。填写类型是选择类型的才有',
  `mtagminnum` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '群组讨论区人数下限。当群组的成员数达到该数目时，才允许成员在群组内发话题和回帖',
  `manualmoderator` tinyint(1) NOT NULL DEFAULT '0' COMMENT '群组群主手工指定，1（手工），0（自动）。如果选择不手工指定，则系统会自动将第一次使用某个群组的用户作为群主。',
  `manualmember` tinyint(1) NOT NULL DEFAULT '0' COMMENT '群组成员可由群主控制，1（群主可控制），0（会员可自由加入）。群主可控制，则允许群主有权设置群组的会员加入方式，来控制加入群组的会员。',
  `displayorder` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '显示顺序',
  PRIMARY KEY (`fieldid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_profield`
--

/*!40000 ALTER TABLE `jchome_profield` DISABLE KEYS */;
INSERT INTO `jchome_profield` (`fieldid`,`title`,`note`,`formtype`,`inputnum`,`choice`,`mtagminnum`,`manualmoderator`,`manualmember`,`displayorder`) VALUES 
 (1,'自由联盟','','text',100,'',0,0,1,0),
 (2,'地区联盟','','text',100,'',0,0,1,0),
 (3,'兴趣联盟','','text',100,'',0,0,1,0);
/*!40000 ALTER TABLE `jchome_profield` ENABLE KEYS */;


--
-- Definition of table `jchome_profilefield`
--

DROP TABLE IF EXISTS `jchome_profilefield`;
CREATE TABLE `jchome_profilefield` (
  `fieldid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(80) NOT NULL DEFAULT '' COMMENT '名称',
  `note` varchar(255) NOT NULL DEFAULT '' COMMENT '简单介绍',
  `formtype` varchar(20) NOT NULL DEFAULT '0' COMMENT '表单类型,text(文本输入框),select(列表框)',
  `maxsize` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '可填写的最多字符',
  `required` tinyint(1) NOT NULL DEFAULT '0' COMMENT '必填,0(否),1(是)',
  `invisible` tinyint(1) NOT NULL DEFAULT '0' COMMENT '资料页面隐藏,0(否),1(是)',
  `allowsearch` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许搜索,0(否),1(是)',
  `choice` text NOT NULL COMMENT '可选值,表单类型是列表框的才有',
  `displayorder` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '显示顺序',
  PRIMARY KEY (`fieldid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_profilefield`
--

/*!40000 ALTER TABLE `jchome_profilefield` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_profilefield` ENABLE KEYS */;


--
-- Definition of table `jchome_report`
--

DROP TABLE IF EXISTS `jchome_report`;
CREATE TABLE `jchome_report` (
  `rid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `idtype` varchar(15) NOT NULL DEFAULT '' COMMENT '举报类型',
  `new` tinyint(1) NOT NULL DEFAULT '0',
  `num` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '举报次数',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `reason` text NOT NULL,
  `uids` text NOT NULL,
  PRIMARY KEY (`rid`),
  KEY `id` (`id`,`idtype`,`num`,`dateline`),
  KEY `new` (`new`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_report`
--

/*!40000 ALTER TABLE `jchome_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_report` ENABLE KEYS */;


--
-- Definition of table `jchome_session`
--

DROP TABLE IF EXISTS `jchome_session`;
CREATE TABLE `jchome_session` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `password` char(32) NOT NULL DEFAULT '',
  `lastactivity` int(10) unsigned NOT NULL DEFAULT '0',
  `ip` int(10) unsigned NOT NULL DEFAULT '0',
  `magichidden` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`),
  KEY `lastactivity` (`lastactivity`),
  KEY `ip` (`ip`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_session`
--

/*!40000 ALTER TABLE `jchome_session` DISABLE KEYS */;
INSERT INTO `jchome_session` (`uid`,`username`,`password`,`lastactivity`,`ip`,`magichidden`) VALUES 
 (1,'admin','b375717fdc45665a4aba43df73d2a7d9',1317354832,127000000,0);
/*!40000 ALTER TABLE `jchome_session` ENABLE KEYS */;


--
-- Definition of table `jchome_share`
--

DROP TABLE IF EXISTS `jchome_share`;
CREATE TABLE `jchome_share` (
  `sid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `type` varchar(30) NOT NULL DEFAULT '' COMMENT '事件类型',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '作者UID',
  `username` varchar(15) NOT NULL DEFAULT '' COMMENT '作者名',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发布时间',
  `title_template` text NOT NULL,
  `body_template` text NOT NULL,
  `body_data` text NOT NULL,
  `body_general` text NOT NULL,
  `image` varchar(255) NOT NULL DEFAULT '',
  `image_link` varchar(255) NOT NULL DEFAULT '',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '热度',
  `hotuser` text NOT NULL,
  PRIMARY KEY (`sid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `topicid` (`topicid`,`dateline`),
  KEY `hot` (`hot`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_share`
--

/*!40000 ALTER TABLE `jchome_share` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_share` ENABLE KEYS */;


--
-- Definition of table `jchome_show`
--

DROP TABLE IF EXISTS `jchome_show`;
CREATE TABLE `jchome_show` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `credit` int(10) unsigned NOT NULL DEFAULT '0',
  `note` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`uid`),
  KEY `credit` (`credit`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_show`
--

/*!40000 ALTER TABLE `jchome_show` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_show` ENABLE KEYS */;


--
-- Definition of table `jchome_space`
--

DROP TABLE IF EXISTS `jchome_space`;
CREATE TABLE `jchome_space` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '用户UID',
  `groupid` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '用户组id',
  `credit` int(10) NOT NULL DEFAULT '0' COMMENT '用户积分',
  `experience` int(10) NOT NULL DEFAULT '0' COMMENT '用户经验值',
  `username` char(15) NOT NULL DEFAULT '' COMMENT '用户名',
  `name` char(20) NOT NULL DEFAULT '' COMMENT '姓名',
  `namestatus` tinyint(1) NOT NULL DEFAULT '0' COMMENT '实名认证，0(未认证),1(已认证)',
  `videostatus` tinyint(1) NOT NULL DEFAULT '0' COMMENT '视频认证，0(视频未认证),1(视频已认证)',
  `domain` char(15) NOT NULL DEFAULT '',
  `friendnum` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '好友数',
  `viewnum` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '查看数',
  `notenum` int(10) unsigned NOT NULL DEFAULT '0',
  `addfriendnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `mtaginvitenum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `eventinvitenum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `myinvitenum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pokenum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `doingnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `blognum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `albumnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `threadnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pollnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `eventnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `sharenum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '空间创建时间',
  `updatetime` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  `lastsearch` int(10) unsigned NOT NULL DEFAULT '0',
  `lastpost` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后发信息时间',
  `lastlogin` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上次登录',
  `lastsend` int(10) unsigned NOT NULL DEFAULT '0',
  `attachsize` int(10) unsigned NOT NULL DEFAULT '0',
  `addsize` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '额外空间大小',
  `addfriend` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '额外好友数',
  `flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态,1(保护用户),0(普通用户),-1(锁定用户),保护状态下，该用户将不能够在JavaCenter、以及本应用中删除。',
  `newpm` smallint(6) unsigned NOT NULL DEFAULT '0',
  `avatar` tinyint(1) NOT NULL DEFAULT '0' COMMENT '头像,0(没有头像),1(上传头像)',
  `regip` char(15) NOT NULL DEFAULT '' COMMENT '注册IP',
  `ip` int(10) unsigned NOT NULL DEFAULT '0',
  `mood` smallint(6) unsigned NOT NULL DEFAULT '0',
  `advgiftcount` smallint(6) NOT NULL DEFAULT '0',
  `giftnum` smallint(6) NOT NULL DEFAULT '0',
  `showgiftlink` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`),
  KEY `username` (`username`),
  KEY `domain` (`domain`),
  KEY `ip` (`ip`),
  KEY `updatetime` (`updatetime`),
  KEY `mood` (`mood`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_space`
--

/*!40000 ALTER TABLE `jchome_space` DISABLE KEYS */;
INSERT INTO `jchome_space` (`uid`,`groupid`,`credit`,`experience`,`username`,`name`,`namestatus`,`videostatus`,`domain`,`friendnum`,`viewnum`,`notenum`,`addfriendnum`,`mtaginvitenum`,`eventinvitenum`,`myinvitenum`,`pokenum`,`doingnum`,`blognum`,`albumnum`,`threadnum`,`pollnum`,`eventnum`,`sharenum`,`dateline`,`updatetime`,`lastsearch`,`lastpost`,`lastlogin`,`lastsend`,`attachsize`,`addsize`,`addfriend`,`flag`,`newpm`,`avatar`,`regip`,`ip`,`mood`,`advgiftcount`,`giftnum`,`showgiftlink`) VALUES 
 (1,1,25,15,'admin','',0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1317354722,0,0,0,1317354722,0,0,0,0,1,0,0,'127.0.0.1',127000000,0,0,0,0);
/*!40000 ALTER TABLE `jchome_space` ENABLE KEYS */;


--
-- Definition of table `jchome_spacefield`
--

DROP TABLE IF EXISTS `jchome_spacefield`;
CREATE TABLE `jchome_spacefield` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别,1(男),2(女)',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '常用邮箱',
  `newemail` varchar(100) NOT NULL DEFAULT '',
  `emailcheck` tinyint(1) NOT NULL DEFAULT '0' COMMENT '邮箱验证，0(未激活),1(已经验证激活)',
  `mobile` varchar(40) NOT NULL DEFAULT '',
  `qq` varchar(20) NOT NULL DEFAULT '' COMMENT 'QQ',
  `msn` varchar(80) NOT NULL DEFAULT '' COMMENT 'MSN',
  `msnrobot` varchar(15) NOT NULL DEFAULT '',
  `msncstatus` tinyint(1) NOT NULL DEFAULT '0',
  `videopic` varchar(32) NOT NULL DEFAULT '',
  `birthyear` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '生日-年',
  `birthmonth` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '生日-月',
  `birthday` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '生日-日',
  `blood` varchar(5) NOT NULL DEFAULT '' COMMENT '血型',
  `marry` tinyint(1) NOT NULL DEFAULT '0' COMMENT '婚恋状态,1(单身),2(非单身)',
  `birthprovince` varchar(20) NOT NULL DEFAULT '' COMMENT '家乡(省)',
  `birthcity` varchar(20) NOT NULL DEFAULT '' COMMENT '家乡(市)',
  `resideprovince` varchar(20) NOT NULL DEFAULT '' COMMENT '居住地(省)',
  `residecity` varchar(20) NOT NULL DEFAULT '' COMMENT '居住地(市)',
  `note` text NOT NULL,
  `spacenote` text NOT NULL,
  `authstr` varchar(20) NOT NULL DEFAULT '',
  `theme` varchar(20) NOT NULL DEFAULT '',
  `nocss` tinyint(1) NOT NULL DEFAULT '0',
  `menunum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `css` text NOT NULL,
  `privacy` text NOT NULL,
  `friend` mediumtext NOT NULL,
  `feedfriend` mediumtext NOT NULL,
  `sendmail` text NOT NULL,
  `magicstar` tinyint(1) NOT NULL DEFAULT '0',
  `magicexpire` int(10) unsigned NOT NULL DEFAULT '0',
  `timeoffset` varchar(20) NOT NULL DEFAULT '' COMMENT '个人时区',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_spacefield`
--

/*!40000 ALTER TABLE `jchome_spacefield` DISABLE KEYS */;
INSERT INTO `jchome_spacefield` (`uid`,`sex`,`email`,`newemail`,`emailcheck`,`mobile`,`qq`,`msn`,`msnrobot`,`msncstatus`,`videopic`,`birthyear`,`birthmonth`,`birthday`,`blood`,`marry`,`birthprovince`,`birthcity`,`resideprovince`,`residecity`,`note`,`spacenote`,`authstr`,`theme`,`nocss`,`menunum`,`css`,`privacy`,`friend`,`feedfriend`,`sendmail`,`magicstar`,`magicexpire`,`timeoffset`) VALUES 
 (1,0,'webmastor@yourdomain.com','',0,'','','','',0,'',0,0,0,'',0,'','','','','','','','',0,0,'','','','','',0,0,'');
/*!40000 ALTER TABLE `jchome_spacefield` ENABLE KEYS */;


--
-- Definition of table `jchome_spaceinfo`
--

DROP TABLE IF EXISTS `jchome_spaceinfo`;
CREATE TABLE `jchome_spaceinfo` (
  `infoid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `type` varchar(20) NOT NULL DEFAULT '',
  `subtype` varchar(20) NOT NULL DEFAULT '',
  `title` text NOT NULL,
  `subtitle` varchar(255) NOT NULL DEFAULT '',
  `friend` tinyint(1) NOT NULL DEFAULT '0',
  `startyear` smallint(6) unsigned NOT NULL DEFAULT '0',
  `endyear` smallint(6) unsigned NOT NULL DEFAULT '0',
  `startmonth` smallint(6) unsigned NOT NULL DEFAULT '0',
  `endmonth` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`infoid`),
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_spaceinfo`
--

/*!40000 ALTER TABLE `jchome_spaceinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_spaceinfo` ENABLE KEYS */;


--
-- Definition of table `jchome_spacelog`
--

DROP TABLE IF EXISTS `jchome_spacelog`;
CREATE TABLE `jchome_spacelog` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `opuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `opusername` char(15) NOT NULL DEFAULT '',
  `flag` tinyint(1) NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`),
  KEY `flag` (`flag`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_spacelog`
--

/*!40000 ALTER TABLE `jchome_spacelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_spacelog` ENABLE KEYS */;


--
-- Definition of table `jchome_stat`
--

DROP TABLE IF EXISTS `jchome_stat`;
CREATE TABLE `jchome_stat` (
  `daytime` int(10) unsigned NOT NULL DEFAULT '0',
  `login` smallint(6) unsigned NOT NULL DEFAULT '0',
  `register` smallint(6) unsigned NOT NULL DEFAULT '0',
  `invite` smallint(6) unsigned NOT NULL DEFAULT '0',
  `appinvite` smallint(6) unsigned NOT NULL DEFAULT '0',
  `doing` smallint(6) unsigned NOT NULL DEFAULT '0',
  `blog` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pic` smallint(6) unsigned NOT NULL DEFAULT '0',
  `poll` smallint(6) unsigned NOT NULL DEFAULT '0',
  `event` smallint(6) unsigned NOT NULL DEFAULT '0',
  `share` smallint(6) unsigned NOT NULL DEFAULT '0',
  `thread` smallint(6) unsigned NOT NULL DEFAULT '0',
  `docomment` smallint(6) unsigned NOT NULL DEFAULT '0',
  `blogcomment` smallint(6) unsigned NOT NULL DEFAULT '0',
  `piccomment` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pollcomment` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pollvote` smallint(6) unsigned NOT NULL DEFAULT '0',
  `eventcomment` smallint(6) unsigned NOT NULL DEFAULT '0',
  `eventjoin` smallint(6) unsigned NOT NULL DEFAULT '0',
  `sharecomment` smallint(6) unsigned NOT NULL DEFAULT '0',
  `post` smallint(6) unsigned NOT NULL DEFAULT '0',
  `wall` smallint(6) unsigned NOT NULL DEFAULT '0',
  `poke` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`daytime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_stat`
--

/*!40000 ALTER TABLE `jchome_stat` DISABLE KEYS */;
INSERT INTO `jchome_stat` (`daytime`,`login`,`register`,`invite`,`appinvite`,`doing`,`blog`,`pic`,`poll`,`event`,`share`,`thread`,`docomment`,`blogcomment`,`piccomment`,`pollcomment`,`pollvote`,`eventcomment`,`eventjoin`,`sharecomment`,`post`,`wall`,`poke`,`click`) VALUES 
 (20110930,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `jchome_stat` ENABLE KEYS */;


--
-- Definition of table `jchome_statuser`
--

DROP TABLE IF EXISTS `jchome_statuser`;
CREATE TABLE `jchome_statuser` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `daytime` int(10) unsigned NOT NULL DEFAULT '0',
  `type` char(20) NOT NULL DEFAULT '',
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_statuser`
--

/*!40000 ALTER TABLE `jchome_statuser` DISABLE KEYS */;
INSERT INTO `jchome_statuser` (`uid`,`daytime`,`type`) VALUES 
 (1,20110930,'login');
/*!40000 ALTER TABLE `jchome_statuser` ENABLE KEYS */;


--
-- Definition of table `jchome_tag`
--

DROP TABLE IF EXISTS `jchome_tag`;
CREATE TABLE `jchome_tag` (
  `tagid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `tagname` char(30) NOT NULL DEFAULT '' COMMENT '标签名',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发布时间',
  `blognum` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '日志数',
  `close` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定,1(锁定)',
  PRIMARY KEY (`tagid`),
  KEY `tagname` (`tagname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_tag`
--

/*!40000 ALTER TABLE `jchome_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_tag` ENABLE KEYS */;


--
-- Definition of table `jchome_tagblog`
--

DROP TABLE IF EXISTS `jchome_tagblog`;
CREATE TABLE `jchome_tagblog` (
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `blogid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`tagid`,`blogid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_tagblog`
--

/*!40000 ALTER TABLE `jchome_tagblog` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_tagblog` ENABLE KEYS */;


--
-- Definition of table `jchome_tagspace`
--

DROP TABLE IF EXISTS `jchome_tagspace`;
CREATE TABLE `jchome_tagspace` (
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `grade` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tagid`,`uid`),
  KEY `grade` (`tagid`,`grade`),
  KEY `uid` (`uid`,`grade`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_tagspace`
--

/*!40000 ALTER TABLE `jchome_tagspace` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_tagspace` ENABLE KEYS */;


--
-- Definition of table `jchome_task`
--

DROP TABLE IF EXISTS `jchome_task`;
CREATE TABLE `jchome_task` (
  `taskid` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `available` tinyint(1) NOT NULL DEFAULT '0' COMMENT '有效性,0(无效),1(有效)',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `note` text NOT NULL COMMENT '任务说明',
  `num` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '该有奖任务已经完成的人次',
  `maxnum` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '完成数限制,设置该有奖任务最多可完成多少人次。超过该人次后，该有奖任务将自动失效。为0，则不限制',
  `image` varchar(150) NOT NULL DEFAULT '' COMMENT '任务图片',
  `filename` varchar(50) NOT NULL DEFAULT '' COMMENT '有奖任务处理JSP脚本文件',
  `starttime` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '开始日期',
  `endtime` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '结束日期',
  `nexttime` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '间隔时间，当nexttype（用户重复执行周期）是time(间隔指定时间)的时候才有的',
  `nexttype` varchar(20) NOT NULL DEFAULT '' COMMENT '用户重复执行周期,day(每天),hour(整点),time(间隔指定时间)',
  `credit` smallint(6) NOT NULL DEFAULT '0' COMMENT '奖励积分',
  `displayorder` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '优先顺序,数字越小，排序越靠前，优先级越高',
  PRIMARY KEY (`taskid`),
  KEY `displayorder` (`displayorder`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_task`
--

/*!40000 ALTER TABLE `jchome_task` DISABLE KEYS */;
INSERT INTO `jchome_task` (`taskid`,`available`,`name`,`note`,`num`,`maxnum`,`image`,`filename`,`starttime`,`endtime`,`nexttime`,`nexttype`,`credit`,`displayorder`) VALUES 
 (1,1,'更新一下自己的头像','头像就是你在这里的个人形象。<br>设置自己的头像后，会让更多的朋友记住您。',0,0,'image/task/avatar.gif','avatar.jsp',0,0,0,'',20,1),
 (2,1,'将个人资料补充完整','把自己的个人资料填写完整吧。<br>这样您会被更多的朋友找到的，系统也会帮您找到朋友。',0,0,'image/task/profile.gif','profile.jsp',0,0,0,'2',20,0),
 (3,1,'发表自己的第一篇日志','现在，就写下自己的第一篇日志吧。<br>与大家一起分享自己的生活感悟。',0,0,'image/task/blog.gif','blog.jsp',0,0,0,'',5,3),
 (4,1,'寻找并添加五位好友','有了好友，您发的日志、图片等会被好友及时看到并传播出去；<br>您也会在首页方便及时的看到好友的最新动态。',0,0,'image/task/friend.gif','friend.jsp',0,0,0,'',50,4),
 (5,1,'验证激活自己的邮箱','填写自己真实的邮箱地址并验证通过。<br>您可以在忘记密码的时候使用该邮箱取回自己的密码；<br>还可以及时接受站内的好友通知等等。',0,0,'image/task/email.gif','email.jsp',0,0,0,'',10,5),
 (6,1,'邀请10个新朋友加入','邀请一下自己的QQ好友或者邮箱联系人，让亲朋好友一起来加入我们吧。',0,0,'image/task/friend.gif','invite.jsp',0,0,0,'',100,6),
 (7,1,'领取每日访问大礼包','每天登录访问自己的主页，就可领取大礼包。',0,0,'image/task/gift.gif','gift.jsp',0,0,0,'day',5,99);
/*!40000 ALTER TABLE `jchome_task` ENABLE KEYS */;


--
-- Definition of table `jchome_thread`
--

DROP TABLE IF EXISTS `jchome_thread`;
CREATE TABLE `jchome_thread` (
  `tid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '指定话题ID',
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '群组ID',
  `eventid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `subject` char(80) NOT NULL DEFAULT '' COMMENT '标题',
  `magiccolor` tinyint(6) unsigned NOT NULL DEFAULT '0',
  `magicegg` tinyint(6) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '作者ID',
  `username` char(15) NOT NULL DEFAULT '' COMMENT '作者名',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `viewnum` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '查看数',
  `replynum` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '回复数',
  `lastpost` int(10) unsigned NOT NULL DEFAULT '0',
  `lastauthor` char(15) NOT NULL DEFAULT '',
  `lastauthorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `digest` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否精华，1(是),0(否)',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '热度',
  `click_11` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_12` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_13` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_14` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_15` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`tid`),
  KEY `tagid` (`tagid`,`displayorder`,`lastpost`),
  KEY `uid` (`uid`,`lastpost`),
  KEY `lastpost` (`lastpost`),
  KEY `topicid` (`topicid`,`dateline`),
  KEY `eventid` (`eventid`,`lastpost`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_thread`
--

/*!40000 ALTER TABLE `jchome_thread` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_thread` ENABLE KEYS */;


--
-- Definition of table `jchome_topic`
--

DROP TABLE IF EXISTS `jchome_topic`;
CREATE TABLE `jchome_topic` (
  `topicid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `subject` varchar(80) NOT NULL DEFAULT '',
  `message` mediumtext NOT NULL,
  `jointype` varchar(255) NOT NULL DEFAULT '',
  `joingid` varchar(255) NOT NULL DEFAULT '',
  `pic` varchar(100) NOT NULL DEFAULT '',
  `thumb` tinyint(1) NOT NULL DEFAULT '0',
  `remote` tinyint(1) NOT NULL DEFAULT '0',
  `joinnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `lastpost` int(10) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `endtime` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`topicid`),
  KEY `lastpost` (`lastpost`),
  KEY `joinnum` (`joinnum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_topic`
--

/*!40000 ALTER TABLE `jchome_topic` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_topic` ENABLE KEYS */;


--
-- Definition of table `jchome_topicuser`
--

DROP TABLE IF EXISTS `jchome_topicuser`;
CREATE TABLE `jchome_topicuser` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`,`dateline`),
  KEY `topicid` (`topicid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_topicuser`
--

/*!40000 ALTER TABLE `jchome_topicuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_topicuser` ENABLE KEYS */;


--
-- Definition of table `jchome_userapp`
--

DROP TABLE IF EXISTS `jchome_userapp`;
CREATE TABLE `jchome_userapp` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `appid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `appname` varchar(60) NOT NULL DEFAULT '',
  `privacy` tinyint(1) NOT NULL DEFAULT '0',
  `allowsidenav` tinyint(1) NOT NULL DEFAULT '0',
  `allowfeed` tinyint(1) NOT NULL DEFAULT '0',
  `allowprofilelink` tinyint(1) NOT NULL DEFAULT '0',
  `narrow` tinyint(1) NOT NULL DEFAULT '0',
  `menuorder` smallint(6) NOT NULL DEFAULT '0',
  `displayorder` smallint(6) NOT NULL DEFAULT '0',
  KEY `uid` (`uid`,`appid`),
  KEY `menuorder` (`uid`,`menuorder`),
  KEY `displayorder` (`uid`,`displayorder`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_userapp`
--

/*!40000 ALTER TABLE `jchome_userapp` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_userapp` ENABLE KEYS */;


--
-- Definition of table `jchome_userappfield`
--

DROP TABLE IF EXISTS `jchome_userappfield`;
CREATE TABLE `jchome_userappfield` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `appid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `profilelink` text NOT NULL,
  `myml` text NOT NULL,
  KEY `uid` (`uid`,`appid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_userappfield`
--

/*!40000 ALTER TABLE `jchome_userappfield` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_userappfield` ENABLE KEYS */;


--
-- Definition of table `jchome_userevent`
--

DROP TABLE IF EXISTS `jchome_userevent`;
CREATE TABLE `jchome_userevent` (
  `eventid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `fellow` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `template` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`eventid`,`uid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `eventid` (`eventid`,`status`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_userevent`
--

/*!40000 ALTER TABLE `jchome_userevent` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_userevent` ENABLE KEYS */;


--
-- Definition of table `jchome_usergroup`
--

DROP TABLE IF EXISTS `jchome_usergroup`;
CREATE TABLE `jchome_usergroup` (
  `gid` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '组的id',
  `grouptitle` varchar(20) NOT NULL DEFAULT '' COMMENT '组的名称',
  `system` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标示组别，-1(系统用户组),1(特殊用户组),0(普通用户组)',
  `banvisit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1(禁止访问),0(允许访问)',
  `explower` int(10) NOT NULL DEFAULT '0' COMMENT '组的经验值下限,受限组的是系统默认最低分 -999999999，不能修改,特殊组和系统组不受经验值限制',
  `maxfriendnum` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '最多好友数',
  `maxattachsize` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上传空间大小',
  `allowhtml` tinyint(1) NOT NULL DEFAULT '0' COMMENT '日志全HTML标签支持,谨慎允许，支持所有html标签可能会造成javascript脚本引起的不安全因素,1(是),0(否)',
  `allowcomment` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发表留言/评论,1(是),0(否)',
  `searchinterval` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '两次搜索操作间隔',
  `searchignore` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否免费搜索,1(是),0(否)',
  `postinterval` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '两次发布操作间隔',
  `spamignore` tinyint(1) NOT NULL DEFAULT '0' COMMENT '防灌水限制,1(不限制),0（受限制）',
  `videophotoignore` tinyint(1) NOT NULL DEFAULT '0' COMMENT '视频认证限制,1(不限制),0（受限制）',
  `allowblog` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发表日志,1(是),0(否)',
  `allowdoing` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发表记录,1(是),0(否)',
  `allowupload` tinyint(1) NOT NULL DEFAULT '0' COMMENT '上传图片,1(是),0(否)',
  `allowshare` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发布分享,1(是),0(否)',
  `allowmtag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '创建新群组,1(是),0(否)',
  `allowthread` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发表群组话题,1(是),0(否)',
  `allowpost` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发表群组回帖,1(是),0(否)',
  `allowcss` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许自定义CSS,1(是),0(否)',
  `allowpoke` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许打招呼,1(是),0(否)',
  `allowfriend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许加好友,1(是),0(否)',
  `allowpoll` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发起投票,1(是),0(否)',
  `allowclick` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许表态,1(是),0(否)',
  `allowevent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发布活动,1(是),0(否)',
  `allowmagic` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许使用道具,1(是),0(否)',
  `allowpm` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许发短消息,1(是),0(否)',
  `allowviewvideopic` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许查看视频认证,1(是),0(否)',
  `allowmyop` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许使用应用,1(是),0(否)',
  `allowtopic` tinyint(1) NOT NULL DEFAULT '0' COMMENT '添加新的热闹,1(是),0(否)',
  `allowstat` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许查看趋势统计,1(是),0(否)',
  `magicdiscount` tinyint(1) NOT NULL DEFAULT '0' COMMENT '购买道具折扣',
  `verifyevent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发表的活动需要审核,1(是),0(否)',
  `edittrail` tinyint(1) NOT NULL DEFAULT '0' COMMENT '编辑话题附加编辑记录,1(是),0(否)',
  `domainlength` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '二级域名最短长度,0为禁止使用二级域名，在站点开启二级域名时有效',
  `closeignore` tinyint(1) NOT NULL DEFAULT '0' COMMENT '站点关闭和IP屏蔽,1(不受站点关闭和IP屏蔽限制),0(受限制)',
  `seccode` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发布操作需填验证码,1(要),0(不需要)',
  `color` varchar(10) NOT NULL DEFAULT '' COMMENT '组名称的颜色',
  `icon` varchar(100) NOT NULL DEFAULT '' COMMENT '组的用户身份识别图标',
  `manageconfig` tinyint(1) NOT NULL DEFAULT '0' COMMENT '管理员身份,1(拥有管理员身份),0(禁止)',
  `managenetwork` tinyint(1) NOT NULL DEFAULT '0' COMMENT '随便看看,1(可管理),0(禁止)',
  `manageprofilefield` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户栏目,1(可管理),0(禁止)',
  `manageprofield` tinyint(1) NOT NULL DEFAULT '0' COMMENT '群组栏目,1(可管理),0(禁止)',
  `manageusergroup` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户组,1(可管理),0(禁止)',
  `managefeed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '动态(feed),1(可管理),0(禁止)',
  `manageshare` tinyint(1) NOT NULL DEFAULT '0' COMMENT '分享,1(可管理),0(禁止)',
  `managedoing` tinyint(1) NOT NULL DEFAULT '0' COMMENT '记录,1(可管理),0(禁止)',
  `manageblog` tinyint(1) NOT NULL DEFAULT '0' COMMENT '日志,1(可管理),0(禁止)',
  `managetag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标签,1(可管理),0(禁止)',
  `managetagtpl` tinyint(1) NOT NULL DEFAULT '0',
  `managealbum` tinyint(1) NOT NULL DEFAULT '0' COMMENT '相册,1(可管理),0(禁止)',
  `managecomment` tinyint(1) NOT NULL DEFAULT '0' COMMENT '评论,1(可管理),0(禁止)',
  `managemtag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '群组,1(可管理),0(禁止)',
  `managethread` tinyint(1) NOT NULL DEFAULT '0' COMMENT '话题,1(可管理),0(禁止)',
  `manageevent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '活动,1(可管理),0(禁止)',
  `manageeventclass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '活动分类,1(可管理),0(禁止)',
  `managecensor` tinyint(1) NOT NULL DEFAULT '0' COMMENT '词语屏蔽,1(可管理),0(禁止)',
  `managead` tinyint(1) NOT NULL DEFAULT '0' COMMENT '广告设置,1(可管理),0(禁止)',
  `managesitefeed` tinyint(1) NOT NULL DEFAULT '0',
  `managebackup` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据备份,1(可管理),0(禁止)',
  `manageblock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据调用,1(可管理),0(禁止)',
  `managetemplate` tinyint(1) NOT NULL DEFAULT '0' COMMENT '模板编辑,1(可管理),0(禁止)',
  `managestat` tinyint(1) NOT NULL DEFAULT '0' COMMENT '统计更新,1(可管理),0(禁止)',
  `managecache` tinyint(1) NOT NULL DEFAULT '0' COMMENT '缓存更新,1(可管理),0(禁止)',
  `managecredit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '积分规则,1(可管理),0(禁止)',
  `managecron` tinyint(1) NOT NULL DEFAULT '0' COMMENT '计划任务,1(可管理),0(禁止)',
  `managename` tinyint(1) NOT NULL DEFAULT '0' COMMENT '实名认证,1(可管理),0(禁止)',
  `manageapp` tinyint(1) NOT NULL DEFAULT '0' COMMENT '多产品/应用,1(可管理),0(禁止)',
  `managetask` tinyint(1) NOT NULL DEFAULT '0' COMMENT '有奖任务,1(可管理),0(禁止)',
  `managereport` tinyint(1) NOT NULL DEFAULT '0' COMMENT '举报管理,1(可管理),0(禁止)',
  `managepoll` tinyint(1) NOT NULL DEFAULT '0' COMMENT '投票,1(可管理),0(禁止)',
  `manageclick` tinyint(1) NOT NULL DEFAULT '0' COMMENT '表态动作设置,1(可管理),0(禁止)',
  `managemagic` tinyint(1) NOT NULL DEFAULT '0' COMMENT '道具设置,1(可管理),0(禁止)',
  `managemagiclog` tinyint(1) NOT NULL DEFAULT '0' COMMENT '道具记录,1(可管理),0(禁止)',
  `managebatch` tinyint(1) NOT NULL DEFAULT '0' COMMENT '批量管理操作,1(可管理),0(禁止)',
  `managedelspace` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除用户,1(可管理),0(禁止)',
  `managetopic` tinyint(1) NOT NULL DEFAULT '0' COMMENT '热闹管理,1(可管理),0(禁止)',
  `manageip` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'IP设,1(可管理),0(禁止)',
  `managehotuser` tinyint(1) NOT NULL DEFAULT '0' COMMENT '推荐成员设置,1(可管理),0(禁止)',
  `managedefaultuser` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认好友设置,1(可管理),0(禁止)',
  `managespacegroup` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户保护信息,1(可管理),0(禁止)',
  `managespaceinfo` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户基本信息,1(可管理),0(禁止)',
  `managespacecredit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户积分,1(可管理),0(禁止)',
  `managespacenote` tinyint(1) NOT NULL DEFAULT '0' COMMENT '向用户发通知,1(可管理),0(禁止)',
  `managevideophoto` tinyint(1) NOT NULL DEFAULT '0' COMMENT '视频认证,1(可管理),0(禁止)',
  `managelog` tinyint(1) NOT NULL DEFAULT '0' COMMENT '管理记录,1(可管理),0(禁止)',
  `magicaward` text NOT NULL COMMENT '升级奖励道具,字段类型为text',
  `allowgift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '允许发送礼物,1(是),0(否)',
  `managegift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '礼物,1(可管理),0(禁止)',
  PRIMARY KEY (`gid`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_usergroup`
--

/*!40000 ALTER TABLE `jchome_usergroup` DISABLE KEYS */;
INSERT INTO `jchome_usergroup` (`gid`,`grouptitle`,`system`,`banvisit`,`explower`,`maxfriendnum`,`maxattachsize`,`allowhtml`,`allowcomment`,`searchinterval`,`searchignore`,`postinterval`,`spamignore`,`videophotoignore`,`allowblog`,`allowdoing`,`allowupload`,`allowshare`,`allowmtag`,`allowthread`,`allowpost`,`allowcss`,`allowpoke`,`allowfriend`,`allowpoll`,`allowclick`,`allowevent`,`allowmagic`,`allowpm`,`allowviewvideopic`,`allowmyop`,`allowtopic`,`allowstat`,`magicdiscount`,`verifyevent`,`edittrail`,`domainlength`,`closeignore`,`seccode`,`color`,`icon`,`manageconfig`,`managenetwork`,`manageprofilefield`,`manageprofield`,`manageusergroup`,`managefeed`,`manageshare`,`managedoing`,`manageblog`,`managetag`,`managetagtpl`,`managealbum`,`managecomment`,`managemtag`,`managethread`,`manageevent`,`manageeventclass`,`managecensor`,`managead`,`managesitefeed`,`managebackup`,`manageblock`,`managetemplate`,`managestat`,`managecache`,`managecredit`,`managecron`,`managename`,`manageapp`,`managetask`,`managereport`,`managepoll`,`manageclick`,`managemagic`,`managemagiclog`,`managebatch`,`managedelspace`,`managetopic`,`manageip`,`managehotuser`,`managedefaultuser`,`managespacegroup`,`managespaceinfo`,`managespacecredit`,`managespacenote`,`managevideophoto`,`managelog`,`magicaward`,`allowgift`,`managegift`) VALUES 
 (1,'站点管理员',-1,0,0,0,0,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,0,'red','image/group/admin.gif',1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,'',1,0),
 (2,'信息管理员',-1,0,0,0,0,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,3,1,0,'blue','image/group/infor.gif',0,0,0,0,0,1,1,1,1,1,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',1,0),
 (3,'贵宾VIP',1,0,0,0,0,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,3,0,0,'green','image/group/vip.gif',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',1,0),
 (4,'受限会员',0,0,-999999999,10,10,0,0,600,0,300,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,'','',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',0,0),
 (5,'普通会员',0,0,0,100,20,0,1,60,0,60,0,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,'','',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',1,0),
 (6,'中级会员',0,0,100,200,50,0,1,30,0,30,0,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,0,0,0,0,5,0,0,'','',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',1,0),
 (7,'高级会员',0,0,1000,300,100,1,1,10,1,10,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,3,0,0,'','',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',1,0),
 (8,'禁止发言',-1,0,0,1,1,0,0,9999,0,9999,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,99,0,1,'','',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',0,0),
 (9,'禁止访问',-1,1,0,1,1,0,0,9999,0,9999,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,99,0,1,'','',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',0,0);
/*!40000 ALTER TABLE `jchome_usergroup` ENABLE KEYS */;


--
-- Definition of table `jchome_userlog`
--

DROP TABLE IF EXISTS `jchome_userlog`;
CREATE TABLE `jchome_userlog` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `action` char(10) NOT NULL DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_userlog`
--

/*!40000 ALTER TABLE `jchome_userlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_userlog` ENABLE KEYS */;


--
-- Definition of table `jchome_usermagic`
--

DROP TABLE IF EXISTS `jchome_usermagic`;
CREATE TABLE `jchome_usermagic` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '' COMMENT '用户',
  `mid` varchar(15) NOT NULL DEFAULT '' COMMENT '道具id',
  `count` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`uid`,`mid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_usermagic`
--

/*!40000 ALTER TABLE `jchome_usermagic` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_usermagic` ENABLE KEYS */;


--
-- Definition of table `jchome_usertask`
--

DROP TABLE IF EXISTS `jchome_usertask`;
CREATE TABLE `jchome_usertask` (
  `uid` mediumint(8) unsigned NOT NULL,
  `username` char(15) NOT NULL DEFAULT '',
  `taskid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `credit` smallint(6) NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `isignore` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`taskid`),
  KEY `isignore` (`isignore`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_usertask`
--

/*!40000 ALTER TABLE `jchome_usertask` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_usertask` ENABLE KEYS */;


--
-- Definition of table `jchome_visitor`
--

DROP TABLE IF EXISTS `jchome_visitor`;
CREATE TABLE `jchome_visitor` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `vuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `vusername` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`vuid`),
  KEY `dateline` (`uid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_visitor`
--

/*!40000 ALTER TABLE `jchome_visitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `jchome_visitor` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
