CREATE TABLE `attachment` (
  `attachment_id` int(11) NOT NULL auto_increment,
  `post_id` int(11) NOT NULL,
  `name` varchar(100) default NULL COMMENT '名称',
  `description` varchar(255) default NULL COMMENT '描述',
  `file_path` varchar(100) default NULL COMMENT '路径',
  `file_name` varchar(100) default NULL COMMENT '文件名称',
  `file_size` int(11) default NULL COMMENT '大小',
  `is_pictrue` tinyint(1) default '0' COMMENT '是否是图片',
  `create_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`attachment_id`),
  KEY `FK_attachment_post` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `attachment` VALUES (1,30,'Winter.jpg',NULL,'/u/cms/www/201103/27154723aswp.jpg','Winter.jpg',105542,1,'2011-03-27 15:47:23');
CREATE TABLE `bbs_category` (
  `CATEGORY_ID` int(11) NOT NULL auto_increment,
  `SITE_ID` int(11) NOT NULL,
  `PATH` varchar(20) NOT NULL COMMENT '访问路径',
  `TITLE` varchar(100) NOT NULL COMMENT '标题',
  `PRIORITY` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `FORUM_COLS` int(11) NOT NULL default '1' COMMENT '板块列数',
  `moderators` varchar(100) default NULL,
  PRIMARY KEY  (`CATEGORY_ID`),
  KEY `FK_BBS_CTG_SITE` (`SITE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='论坛分区';
INSERT INTO `bbs_category` VALUES (1,1,'service','≡JEECMS产品服务区≡',1,1,'');
INSERT INTO `bbs_category` VALUES (2,1,'use','≡JEECMS使用交流区≡',2,1,'korven');
CREATE TABLE `bbs_category_user` (
  `CATEGORY_ID` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`CATEGORY_ID`,`user_id`),
  KEY `FK_BBS_CATEGORY_TO_USER` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分区版主';
CREATE TABLE `bbs_config` (
  `CONFIG_ID` bigint(20) NOT NULL,
  `DEF_AVATAR` varchar(100) NOT NULL default '' COMMENT '默认会员头像',
  `AVATAR_WIDTH` int(11) NOT NULL default '160' COMMENT '头像最大宽度',
  `AVATAR_HEIGHT` int(11) NOT NULL default '160' COMMENT '头像最大高度',
  `TOPIC_COUNT_PER_PAGE` int(11) NOT NULL default '20' COMMENT '每页主题数',
  `POST_COUNT_PER_PAGE` int(11) NOT NULL default '10' COMMENT '每页帖子数',
  `KEYWORDS` varchar(255) NOT NULL default '' COMMENT '首页关键字',
  `DESCRIPTION` varchar(255) NOT NULL default '' COMMENT '首页描述',
  `REGISTER_STATUS` smallint(6) NOT NULL default '1' COMMENT '注册状态（0：关闭，1：开放，2：邀请）',
  `REGISTER_GROUP_ID` int(11) NOT NULL default '1' COMMENT '注册会员组',
  `REGISTER_RULE` longtext COMMENT '注册协议',
  `CACHE_POST_TODAY` int(11) NOT NULL default '0' COMMENT '今日贴数',
  `CACHE_POST_YESTERDAY` int(11) NOT NULL default '0' COMMENT '昨日帖数',
  `CACHE_POST_MAX` int(11) NOT NULL default '0' COMMENT '最高帖数',
  `CACHE_POST_MAX_DATE` date NOT NULL COMMENT '最高帖数日',
  `CACHE_TOPIC_TOTAL` int(11) NOT NULL default '0' COMMENT '总主题',
  `CACHE_POST_TOTAL` int(11) NOT NULL default '0' COMMENT '总帖数',
  `CACHE_USER_TOTAL` int(11) NOT NULL default '0' COMMENT '总会员',
  `last_user_id` int(11) default NULL COMMENT '最新会员',
  `site_id` int(11) NOT NULL,
  `DEFAULT_GROUP_ID` bigint(20) NOT NULL default '1' COMMENT '默认会员组',
  `TOPIC_HOT_COUNT` int(11) NOT NULL default '10' COMMENT '热帖回复数量',
  `AUTO_REGISTER` tinyint(1) default '1' COMMENT '是否自动注册',
  PRIMARY KEY  (`CONFIG_ID`),
  KEY `FK_BBS_CONFIG_SITE` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛配置';
INSERT INTO `bbs_config` VALUES (1,'1',160,160,20,10,'JEEBBS','JEEBBS',1,1,'',0,0,0,'1999-12-30',0,0,0,NULL,1,1,10,1);
CREATE TABLE `bbs_forum` (
  `FORUM_ID` int(11) NOT NULL auto_increment,
  `CATEGORY_ID` int(11) NOT NULL COMMENT '分区ID',
  `SITE_ID` int(11) NOT NULL COMMENT '站点ID',
  `POST_ID` int(11) default NULL COMMENT '最后回帖',
  `replyer_id` int(11) default NULL COMMENT '最后回帖会员',
  `PATH` varchar(20) NOT NULL COMMENT '访问路径',
  `TITLE` varchar(150) NOT NULL COMMENT '标题',
  `DESCRIPTION` varchar(255) default NULL COMMENT '描述',
  `KEYWORDS` varchar(255) default NULL COMMENT 'meta-keywords',
  `FORUM_RULE` varchar(255) default NULL COMMENT '版规',
  `PRIORITY` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `TOPIC_TOTAL` int(11) NOT NULL default '0' COMMENT '主题总数',
  `POST_TOTAL` int(11) NOT NULL default '0' COMMENT '帖子总数',
  `POST_TODAY` int(11) NOT NULL default '0' COMMENT '今日新帖',
  `OUTER_URL` varchar(255) default NULL COMMENT '外部链接',
  `POINT_TOPIC` int(11) NOT NULL default '0' COMMENT '发贴加分',
  `POINT_REPLY` int(11) NOT NULL default '0' COMMENT '回帖加分',
  `POINT_PRIME` int(11) NOT NULL default '0' COMMENT '精华加分',
  `LAST_TIME` datetime default NULL COMMENT '最后发贴时间',
  `TOPIC_LOCK_LIMIT` int(11) NOT NULL default '0' COMMENT '锁定主题（天）',
  `moderators` varchar(100) default NULL COMMENT '版主',
  `group_views` varchar(100) default NULL COMMENT '访问会员组',
  `group_topics` varchar(100) default NULL COMMENT '发帖会员组',
  `group_replies` varchar(100) default NULL COMMENT '回复会员组',
  PRIMARY KEY  (`FORUM_ID`),
  KEY `FK_BBS_FORUM_CTG` (`CATEGORY_ID`),
  KEY `FK_BBS_FORUM_USER` (`replyer_id`),
  KEY `FK_BBS_FORUM_POST` (`POST_ID`),
  KEY `FK_BBS_FORUM_WEBSITE` (`SITE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='论坛板块';
INSERT INTO `bbs_forum` VALUES (1,1,1,24,6,'sqzx','系统售前咨询','提供JEECMS商业授权相关信息咨询','jsp cms,站群,开源','如果您需要使用JEECMS进行商业建站等商业性活动，例如：政府单位、教育机构、协会团体、企业等，请您购买我们的商业授权。如有需要，欢迎与我们联系。 QQ：811459917、48955621、122513110         电话：0791-6538070、13576281815    E-mail:jeecms@163.com',1,4,4,4,'',5,1,10,'2011-03-26 17:35:18',0,'korven',',14,1,2,3,4,5,6,7,8,9,10,11,12,13,',',1,2,3,4,5,6,7,8,9,10,11,12,13,',',1,2,3,4,5,6,7,8,9,10,11,12,13,');
INSERT INTO `bbs_forum` VALUES (2,2,1,33,6,'azsy','安装与使用','安装和使用JEECMS中遇到的问题大家可以在这里进行讨论','安装与使用','安装或使用中出现问题，请描述下您的系统使用环境，如操作系统，JDK版本，tocmat版本，mysql版本等',10,5,8,8,'',5,0,100,'2011-03-27 16:03:19',0,'korven',',14,1,2,3,4,5,6,7,8,9,10,11,12,13,',',1,2,3,4,5,6,7,8,9,10,11,12,13,',',1,2,3,4,5,6,7,8,9,10,11,12,13,');
CREATE TABLE `bbs_forum_group_reply` (
  `FORUM_ID` int(11) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  PRIMARY KEY  (`FORUM_ID`,`GROUP_ID`),
  KEY `FK_BBS_FORUM_GROUP_REPLY` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回复权限';
CREATE TABLE `bbs_forum_group_topic` (
  `FORUM_ID` int(11) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  PRIMARY KEY  (`FORUM_ID`,`GROUP_ID`),
  KEY `FK_BBS_FORUM_GROUP_TOPIC` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发贴权限';
CREATE TABLE `bbs_forum_group_view` (
  `GROUP_ID` int(11) NOT NULL,
  `FORUM_ID` int(11) NOT NULL,
  PRIMARY KEY  (`GROUP_ID`,`FORUM_ID`),
  KEY `FK_BBS_GROUP_FORUM_VIEW` (`FORUM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='浏览权限';
CREATE TABLE `bbs_forum_user` (
  `FORUM_ID` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`FORUM_ID`,`user_id`),
  KEY `FK_BBS_FORUM_TO_USER` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='版块版主';
CREATE TABLE `bbs_grade` (
  `GRADE_ID` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `POST_ID` int(11) NOT NULL,
  `SCORE` int(11) default NULL,
  `REASON` varchar(100) default NULL,
  `GRADE_TIME` datetime default NULL,
  PRIMARY KEY  (`GRADE_ID`),
  KEY `FK_MEMBER_GRADE` (`user_id`),
  KEY `FK_POST_GRADE` (`POST_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `bbs_grade` VALUES (1,5,6,2,'我很赞同','2011-03-18 14:36:34');
CREATE TABLE `bbs_operation` (
  `OPERATOR_ID` int(11) NOT NULL auto_increment,
  `SITE_ID` int(11) NOT NULL,
  `operater_id` int(11) NOT NULL COMMENT '操作会员',
  `REF_TYPE` char(4) NOT NULL COMMENT '引用类型',
  `REF_ID` int(11) NOT NULL default '0' COMMENT '引用ID',
  `OPT_NAME` varchar(100) NOT NULL COMMENT '操作名称',
  `OPT_REASON` varchar(255) default NULL COMMENT '操作原因',
  `OPT_TIME` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY  (`OPERATOR_ID`),
  KEY `FK_BBS_OPEATTION` (`SITE_ID`),
  KEY `FK_BBS_OPERATION_USER` (`operater_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='主题、帖子操作记录';
INSERT INTO `bbs_operation` VALUES (1,1,5,'POST',15,'修改主题',NULL,'2011-03-18 12:21:52');
INSERT INTO `bbs_operation` VALUES (2,1,5,'POST',15,'修改主题',NULL,'2011-03-18 12:37:49');
INSERT INTO `bbs_operation` VALUES (3,1,6,'TOPI',6,'精华','原创内容','2011-03-21 00:08:34');
INSERT INTO `bbs_operation` VALUES (4,1,6,'TOPI',6,'解除主题','违规内容','2011-03-21 11:03:46');
INSERT INTO `bbs_operation` VALUES (6,1,6,'TOPI',6,'解除主题','违规内容','2011-03-21 11:48:58');
INSERT INTO `bbs_operation` VALUES (7,1,6,'POST',3,'编辑',NULL,'2011-03-21 13:26:46');
INSERT INTO `bbs_operation` VALUES (8,1,6,'POST',2,'屏蔽',NULL,'2011-03-21 13:28:43');
INSERT INTO `bbs_operation` VALUES (9,1,6,'POST',3,'屏蔽',NULL,'2011-03-21 13:28:46');
INSERT INTO `bbs_operation` VALUES (10,1,6,'POST',27,'屏蔽',NULL,'2011-03-27 15:22:13');
INSERT INTO `bbs_operation` VALUES (11,1,6,'POST',29,'编辑',NULL,'2011-03-27 15:47:00');
INSERT INTO `bbs_operation` VALUES (12,1,6,'POST',33,'编辑',NULL,'2011-03-27 16:03:33');
INSERT INTO `bbs_operation` VALUES (13,1,6,'POST',33,'编辑',NULL,'2011-03-27 16:08:54');
INSERT INTO `bbs_operation` VALUES (14,1,6,'TOPI',9,'置顶','我很赞同','2011-03-27 23:13:45');
CREATE TABLE `bbs_permission` (
  `GROUP_ID` int(11) NOT NULL,
  `PERM_KEY` varchar(20) NOT NULL COMMENT '权限key',
  `PERM_VALUE` varchar(255) default NULL COMMENT '权限value',
  KEY `FK_BBS_PERMISSION_GROUP` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛权限';
INSERT INTO `bbs_permission` VALUES (1,'allow_attach','true');
INSERT INTO `bbs_permission` VALUES (1,'allow_reply','true');
INSERT INTO `bbs_permission` VALUES (1,'allow_topic','true');
INSERT INTO `bbs_permission` VALUES (1,'attach_max_size','0');
INSERT INTO `bbs_permission` VALUES (1,'attach_per_day','0');
INSERT INTO `bbs_permission` VALUES (1,'attach_type','');
INSERT INTO `bbs_permission` VALUES (1,'edit_limit_minute','0');
INSERT INTO `bbs_permission` VALUES (1,'favorite_count','0');
INSERT INTO `bbs_permission` VALUES (1,'msg_count','0');
INSERT INTO `bbs_permission` VALUES (1,'msg_interval','0');
INSERT INTO `bbs_permission` VALUES (1,'msg_per_day','100');
INSERT INTO `bbs_permission` VALUES (1,'post_interval','0');
INSERT INTO `bbs_permission` VALUES (1,'post_per_day','100');
INSERT INTO `bbs_permission` VALUES (12,'allow_reply','true');
INSERT INTO `bbs_permission` VALUES (12,'allow_topic','true');
INSERT INTO `bbs_permission` VALUES (12,'attach_max_size','0');
INSERT INTO `bbs_permission` VALUES (12,'attach_per_day','0');
INSERT INTO `bbs_permission` VALUES (12,'attach_type','');
INSERT INTO `bbs_permission` VALUES (12,'edit_limit_minute','0');
INSERT INTO `bbs_permission` VALUES (12,'favorite_count','0');
INSERT INTO `bbs_permission` VALUES (12,'member_prohibit','true');
INSERT INTO `bbs_permission` VALUES (12,'msg_count','0');
INSERT INTO `bbs_permission` VALUES (12,'msg_interval','0');
INSERT INTO `bbs_permission` VALUES (12,'msg_per_day','0');
INSERT INTO `bbs_permission` VALUES (12,'post_interval','0');
INSERT INTO `bbs_permission` VALUES (12,'post_limit','true');
INSERT INTO `bbs_permission` VALUES (12,'post_per_day','0');
INSERT INTO `bbs_permission` VALUES (12,'topic_delete','true');
INSERT INTO `bbs_permission` VALUES (12,'topic_edit','true');
INSERT INTO `bbs_permission` VALUES (12,'topic_manage','true');
INSERT INTO `bbs_permission` VALUES (12,'topic_shield','true');
INSERT INTO `bbs_permission` VALUES (12,'topic_top','3');
INSERT INTO `bbs_permission` VALUES (12,'view_ip','true');
CREATE TABLE `bbs_post` (
  `POST_ID` int(11) NOT NULL auto_increment,
  `TOPIC_ID` int(11) NOT NULL COMMENT '主题',
  `SITE_ID` int(11) NOT NULL COMMENT '站点',
  `CONFIG_ID` int(11) NOT NULL,
  `EDITER_ID` int(11) default NULL COMMENT '编辑器会员',
  `CREATER_ID` int(11) NOT NULL COMMENT '发贴会员',
  `CREATE_TIME` datetime NOT NULL COMMENT '发贴时间',
  `POSTER_IP` varchar(20) NOT NULL default '' COMMENT '发贴IP',
  `EDIT_TIME` datetime default NULL COMMENT '编辑时间',
  `EDITER_IP` varchar(20) default '' COMMENT '编辑者IP',
  `EDIT_COUNT` int(11) NOT NULL default '0' COMMENT '编辑次数',
  `INDEX_COUNT` int(11) NOT NULL default '1' COMMENT '第几楼',
  `STATUS` smallint(6) NOT NULL default '0' COMMENT '状态',
  `IS_AFFIX` tinyint(1) NOT NULL default '0' COMMENT '是否上传附件',
  `IS_HIDDEN` tinyint(1) default '0' COMMENT '是否有隐藏内容',
  PRIMARY KEY  (`POST_ID`),
  KEY `FK_BBS_POST_CREATER` (`CREATER_ID`),
  KEY `FK_BBS_POST_EDITOR` (`EDITER_ID`),
  KEY `FK_BBS_POST_TOPIC` (`TOPIC_ID`),
  KEY `FK_BBS_POST_WEBSITE` (`SITE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='论坛帖子';
INSERT INTO `bbs_post` VALUES (2,5,1,1,NULL,5,'2011-03-17 14:46:22','127.0.0.1',NULL,NULL,0,1,-1,0,0);
INSERT INTO `bbs_post` VALUES (3,5,1,1,6,5,'2011-03-17 15:22:51','127.0.0.1','2011-03-21 13:26:46','127.0.0.1',1,2,-1,0,0);
INSERT INTO `bbs_post` VALUES (4,5,1,1,NULL,5,'2011-03-17 15:24:51','127.0.0.1',NULL,NULL,0,3,0,0,0);
INSERT INTO `bbs_post` VALUES (5,5,1,1,NULL,5,'2011-03-17 15:35:58','127.0.0.1',NULL,NULL,0,4,-1,0,0);
INSERT INTO `bbs_post` VALUES (6,5,1,1,NULL,5,'2011-03-17 15:37:42','127.0.0.1',NULL,NULL,0,5,0,0,0);
INSERT INTO `bbs_post` VALUES (7,5,1,1,NULL,5,'2011-03-17 15:39:01','127.0.0.1',NULL,NULL,0,6,0,0,0);
INSERT INTO `bbs_post` VALUES (8,5,1,1,NULL,5,'2011-03-17 15:43:57','127.0.0.1',NULL,NULL,0,7,0,0,0);
INSERT INTO `bbs_post` VALUES (9,5,1,1,NULL,5,'2011-03-17 15:51:13','127.0.0.1',NULL,NULL,0,8,0,0,0);
INSERT INTO `bbs_post` VALUES (10,5,1,1,NULL,5,'2011-03-17 15:51:48','127.0.0.1',NULL,NULL,0,9,0,0,0);
INSERT INTO `bbs_post` VALUES (11,5,1,1,NULL,5,'2011-03-17 15:52:42','127.0.0.1',NULL,NULL,0,10,0,0,0);
INSERT INTO `bbs_post` VALUES (12,5,1,1,NULL,5,'2011-03-17 15:55:57','127.0.0.1',NULL,NULL,0,11,0,0,0);
INSERT INTO `bbs_post` VALUES (13,5,1,1,NULL,5,'2011-03-17 17:42:08','127.0.0.1',NULL,NULL,0,12,0,0,0);
INSERT INTO `bbs_post` VALUES (14,5,1,1,NULL,5,'2011-03-18 11:24:32','127.0.0.1',NULL,NULL,0,13,0,0,0);
INSERT INTO `bbs_post` VALUES (15,5,1,1,5,5,'2011-03-18 11:46:27','127.0.0.1','2011-03-18 12:37:49','127.0.0.1',2,14,0,0,0);
INSERT INTO `bbs_post` VALUES (16,6,1,1,NULL,6,'2011-03-19 14:32:15','127.0.0.1',NULL,NULL,0,1,0,0,0);
INSERT INTO `bbs_post` VALUES (17,7,1,1,NULL,6,'2011-03-19 18:02:10','127.0.0.1',NULL,NULL,0,1,0,0,0);
INSERT INTO `bbs_post` VALUES (18,8,1,1,NULL,6,'2011-03-21 11:49:08','127.0.0.1',NULL,NULL,0,1,0,0,0);
INSERT INTO `bbs_post` VALUES (19,5,1,1,NULL,6,'2011-03-21 13:33:02','127.0.0.1',NULL,NULL,0,15,0,0,0);
INSERT INTO `bbs_post` VALUES (20,9,1,1,NULL,6,'2011-03-26 16:33:55','127.0.0.1',NULL,NULL,0,1,0,0,0);
INSERT INTO `bbs_post` VALUES (22,11,1,1,NULL,6,'2011-03-26 17:29:55','127.0.0.1',NULL,NULL,0,1,0,0,0);
INSERT INTO `bbs_post` VALUES (23,12,1,1,NULL,6,'2011-03-26 17:34:54','127.0.0.1',NULL,NULL,0,1,0,0,0);
INSERT INTO `bbs_post` VALUES (24,12,1,1,NULL,6,'2011-03-26 17:35:18','127.0.0.1',NULL,NULL,0,2,0,0,0);
INSERT INTO `bbs_post` VALUES (26,14,1,1,NULL,6,'2011-03-27 15:20:53','127.0.0.1',NULL,NULL,0,1,0,0,0);
INSERT INTO `bbs_post` VALUES (27,15,1,1,NULL,6,'2011-03-27 15:21:30','127.0.0.1',NULL,NULL,0,1,-1,0,0);
INSERT INTO `bbs_post` VALUES (28,16,1,1,NULL,6,'2011-03-27 15:42:21','127.0.0.1',NULL,NULL,0,1,0,0,0);
INSERT INTO `bbs_post` VALUES (29,17,1,1,6,6,'2011-03-27 15:46:36','127.0.0.1','2011-03-27 15:47:00','127.0.0.1',1,1,0,0,0);
INSERT INTO `bbs_post` VALUES (30,18,1,1,NULL,6,'2011-03-27 15:47:22','127.0.0.1',NULL,NULL,0,1,0,1,0);
INSERT INTO `bbs_post` VALUES (31,18,1,1,NULL,6,'2011-03-27 15:50:01','127.0.0.1',NULL,NULL,0,2,0,0,0);
INSERT INTO `bbs_post` VALUES (32,18,1,1,NULL,6,'2011-03-27 15:51:43','127.0.0.1',NULL,NULL,0,3,0,0,0);
INSERT INTO `bbs_post` VALUES (33,18,1,1,6,6,'2011-03-27 16:03:19','127.0.0.1','2011-03-27 16:08:54','127.0.0.1',2,4,0,0,0);
CREATE TABLE `bbs_post_text` (
  `POST_ID` bigint(20) NOT NULL,
  `POST_TITLE` varchar(100) default NULL COMMENT '帖子标题',
  `POST_CONTENT` longtext COMMENT '帖子内容',
  PRIMARY KEY  (`POST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛帖子内容';
INSERT INTO `bbs_post_text` VALUES (2,'111111','111111');
INSERT INTO `bbs_post_text` VALUES (3,'222fff','22222fff');
INSERT INTO `bbs_post_text` VALUES (4,'33333333','33333333333');
INSERT INTO `bbs_post_text` VALUES (5,'4444444444444','44444444444444');
INSERT INTO `bbs_post_text` VALUES (6,'5555555','55555555555');
INSERT INTO `bbs_post_text` VALUES (7,'6666666','666666666');
INSERT INTO `bbs_post_text` VALUES (8,'777777777777','77777777777');
INSERT INTO `bbs_post_text` VALUES (9,'8888888888','88888888888888');
INSERT INTO `bbs_post_text` VALUES (10,'8888888888','88888888888888');
INSERT INTO `bbs_post_text` VALUES (11,'999999999999','999999999999');
INSERT INTO `bbs_post_text` VALUES (12,'aaaaaaaaaaaaa','aaaaaaaaaaa');
INSERT INTO `bbs_post_text` VALUES (13,'引用','[quote]44444444444444[/quote]\r\n\r\n11111111111111');
INSERT INTO `bbs_post_text` VALUES (14,'','[quote]33333333333[/quote]\r\n111111111111111111111');
INSERT INTO `bbs_post_text` VALUES (15,'','回复第13楼\r\n\r\n222222222222222222222');
INSERT INTO `bbs_post_text` VALUES (16,'发布新帖子','[smiley=13][smiley=13][smiley=13]\r\n\r\n[color=Red]瞧一瞧看一看了啊，刚出炉的孝子大甩卖了[/color][smiley=14]\r\n[localimg]1[/localimg]');
INSERT INTO `bbs_post_text` VALUES (17,'erreer','erererer');
INSERT INTO `bbs_post_text` VALUES (18,'rr','rr');
INSERT INTO `bbs_post_text` VALUES (19,'rrr','[quote]22222fff[/quote]\r\n            rrrrr');
INSERT INTO `bbs_post_text` VALUES (20,'表情测试','[smiley=10][smiley=11][smiley=12][smiley=13][smiley=17][smiley=16][smiley=15][smiley=14][smiley=19][smiley=19][smiley=20]');
INSERT INTO `bbs_post_text` VALUES (22,'为什么？','为什么要发帖？[smiley=6]');
INSERT INTO `bbs_post_text` VALUES (23,'111','111111');
INSERT INTO `bbs_post_text` VALUES (24,'11','22[smiley=12]');
INSERT INTO `bbs_post_text` VALUES (26,'JEEBBS如何安装？','[smiley=2]\r\n请问JEEBBS安装复杂吗？');
INSERT INTO `bbs_post_text` VALUES (27,'测试发帖','测试发帖[smiley=8]');
INSERT INTO `bbs_post_text` VALUES (28,'','');
INSERT INTO `bbs_post_text` VALUES (29,'测试插图','');
INSERT INTO `bbs_post_text` VALUES (30,'测试插图功能','[attachment]1[/attachment]测试插图功能\r\n');
INSERT INTO `bbs_post_text` VALUES (31,'淡淡的','[smiley=20]');
INSERT INTO `bbs_post_text` VALUES (32,'66','66发发发[smiley=20]');
INSERT INTO `bbs_post_text` VALUES (33,'发发发','[font=Impact]发发发[/font]');
CREATE TABLE `bbs_private_msg` (
  `PRIVMSG_ID` bigint(20) NOT NULL,
  `TO_USER` bigint(20) NOT NULL COMMENT '收信人',
  `FROM_USER` bigint(20) NOT NULL COMMENT '发信人',
  `MSG_TYPE` smallint(6) NOT NULL default '1' COMMENT '类型（2：已发，1：已阅，0：未阅）',
  `MSG_SUBJECT` varchar(255) default NULL COMMENT '主题',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `MSG_IP` varchar(20) NOT NULL default '' COMMENT 'IP地址',
  PRIMARY KEY  (`PRIVMSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人短消息';
CREATE TABLE `bbs_private_msg_text` (
  `PRIVMSG_ID` bigint(20) NOT NULL,
  `MSG_TEXT` longtext COMMENT '个人信息内容',
  PRIMARY KEY  (`PRIVMSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人消息内容';
CREATE TABLE `bbs_topic` (
  `TOPIC_ID` int(11) NOT NULL auto_increment,
  `FORUM_ID` int(11) NOT NULL COMMENT '板块',
  `LAST_POST_ID` int(11) default NULL COMMENT '最后帖',
  `FIRST_POST_ID` int(11) default NULL COMMENT '主题帖',
  `SITE_ID` int(11) NOT NULL COMMENT '站点',
  `CREATER_ID` int(11) NOT NULL COMMENT '发帖会员',
  `REPLYER_ID` int(11) NOT NULL COMMENT '最后回帖会员',
  `TITLE` varchar(100) NOT NULL COMMENT '标题',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `LAST_TIME` datetime NOT NULL COMMENT '最后回帖时间',
  `SORT_TIME` datetime NOT NULL COMMENT '用于排序',
  `VIEW_COUNT` bigint(20) NOT NULL default '0' COMMENT '浏览次数',
  `REPLY_COUNT` int(11) NOT NULL default '0' COMMENT '恢复次数',
  `TOP_LEVEL` smallint(6) NOT NULL default '0' COMMENT '固定级别',
  `PRIME_LEVEL` smallint(6) NOT NULL default '0' COMMENT '精华级别',
  `STATUS` smallint(6) NOT NULL default '0' COMMENT '状态',
  `OUTER_URL` varchar(255) default NULL COMMENT '外部链接',
  `STYLE_BOLD` tinyint(1) NOT NULL default '0' COMMENT '粗体',
  `STYLE_ITALIC` tinyint(1) NOT NULL default '0' COMMENT '斜体',
  `STYLE_COLOR` char(6) default NULL COMMENT '颜色',
  `STYLE_TIME` datetime default NULL COMMENT '样式有效时间',
  `IS_AFFIX` tinyint(1) NOT NULL default '0' COMMENT '是否上传附件',
  `HAVA_REPLY` longtext COMMENT '回复列表',
  `moderator_reply` tinyint(1) default '0' COMMENT '版主是否回复',
  PRIMARY KEY  (`TOPIC_ID`),
  KEY `BBS_SORT_TIME` (`SORT_TIME`),
  KEY `FK_BBS_FIRST_POST` (`FIRST_POST_ID`),
  KEY `FK_BBS_LAST_POST` (`LAST_POST_ID`),
  KEY `FK_BBS_TOPIC_FORUM` (`FORUM_ID`),
  KEY `FK_BBS_TOPIC_USER_CREATE` (`CREATER_ID`),
  KEY `FK_BBS_TOPIC_USER_LAST` (`REPLYER_ID`),
  KEY `FK_BBS_TOPIC_SITE` (`SITE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='论坛主题';
INSERT INTO `bbs_topic` VALUES (5,1,2,2,1,5,5,'111111','2011-03-17 14:46:22','2011-03-17 14:46:22','2011-03-17 14:46:22',0,1,0,0,0,NULL,0,0,NULL,NULL,0,',5,6,',0);
INSERT INTO `bbs_topic` VALUES (6,1,16,16,1,6,6,'发布新帖子','2011-03-19 14:32:15','2011-03-19 14:32:15','2011-03-19 14:32:15',0,0,0,1,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (7,1,17,17,1,6,6,'erreer','2011-03-19 18:02:10','2011-03-19 18:02:10','2011-03-19 18:02:10',0,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (8,1,NULL,18,1,6,6,'rr','2011-03-21 11:49:08','2011-03-21 11:49:08','2011-03-21 11:49:08',0,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (9,1,NULL,20,1,6,6,'表情测试','2011-03-26 16:33:55','2011-03-26 16:33:55','2011-03-26 16:33:55',0,0,1,0,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (11,1,NULL,22,1,6,6,'为什么？','2011-03-26 17:29:55','2011-03-26 17:29:55','2011-03-26 17:29:55',4,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (12,1,24,23,1,6,6,'111','2011-03-26 17:34:54','2011-03-26 17:35:18','2011-03-26 17:34:54',0,1,0,0,0,NULL,0,0,NULL,NULL,0,',6,',0);
INSERT INTO `bbs_topic` VALUES (14,2,NULL,26,1,6,6,'JEEBBS如何安装？','2011-03-27 15:20:53','2011-03-27 15:20:53','2011-03-27 15:20:53',0,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (15,2,NULL,27,1,6,6,'测试发帖','2011-03-27 15:21:30','2011-03-27 15:21:30','2011-03-27 15:21:30',0,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (16,2,NULL,28,1,6,6,'','2011-03-27 15:42:21','2011-03-27 15:42:21','2011-03-27 15:42:21',0,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (17,2,NULL,29,1,6,6,'测试插图','2011-03-27 15:46:36','2011-03-27 15:46:36','2011-03-27 15:46:36',0,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0);
INSERT INTO `bbs_topic` VALUES (18,2,33,30,1,6,6,'测试插图功能','2011-03-27 15:47:22','2011-03-27 16:03:19','2011-03-27 15:47:22',0,3,0,0,0,NULL,0,0,NULL,NULL,1,',6,',0);
CREATE TABLE `bbs_user_group` (
  `GROUP_ID` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `NAME` varchar(20) NOT NULL COMMENT '头衔',
  `GROUP_TYPE` smallint(6) NOT NULL default '0' COMMENT '组类别',
  `GROUP_IMG` varchar(100) default NULL COMMENT '图片',
  `GROUP_POINT` int(11) NOT NULL default '0' COMMENT '升级积分',
  `IS_DEFAULT` tinyint(1) NOT NULL default '0' COMMENT '是否默认组',
  `GRADE_NUM` int(11) default '0' COMMENT '评分',
  PRIMARY KEY  (`GROUP_ID`),
  KEY `FK_BBS_GROUP_SITE` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='论坛会员组';
INSERT INTO `bbs_user_group` VALUES (1,1,'白丁',1,'1',0,1,0);
INSERT INTO `bbs_user_group` VALUES (2,1,'童生',1,'2',10,0,0);
INSERT INTO `bbs_user_group` VALUES (3,1,'秀才',1,'3',50,0,0);
INSERT INTO `bbs_user_group` VALUES (4,1,'举人',1,'4',100,0,0);
INSERT INTO `bbs_user_group` VALUES (5,1,'解元',1,'5',200,0,0);
INSERT INTO `bbs_user_group` VALUES (6,1,'贡士',1,'6',400,0,0);
INSERT INTO `bbs_user_group` VALUES (7,1,'会元',1,'7',800,0,0);
INSERT INTO `bbs_user_group` VALUES (8,1,'进士',1,'8',1600,0,0);
INSERT INTO `bbs_user_group` VALUES (9,1,'探花',1,'9',3200,0,0);
INSERT INTO `bbs_user_group` VALUES (10,1,'榜眼',1,'10',6400,0,0);
INSERT INTO `bbs_user_group` VALUES (11,1,'状元',1,'11',12800,0,0);
INSERT INTO `bbs_user_group` VALUES (12,1,'版主',2,'21',0,0,0);
INSERT INTO `bbs_user_group` VALUES (13,1,'VIP会员',3,'10',0,0,0);
INSERT INTO `bbs_user_group` VALUES (14,1,'游客',0,'1',0,0,0);
CREATE TABLE `jb_user` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL default '0' COMMENT '登录次数',
  `upload_total` bigint(20) NOT NULL default '0' COMMENT '上传总大小',
  `upload_size` int(11) NOT NULL default '0' COMMENT '上传大小',
  `upload_date` date default NULL COMMENT '上传日期',
  `is_admin` tinyint(1) NOT NULL default '0' COMMENT '是否管理员',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  `PROHIBIT_POST` smallint(6) NOT NULL default '0' COMMENT '禁言(0:不禁言;1:永久禁言;2:定期禁言)',
  `PROHIBIT_TIME` datetime default NULL COMMENT '解禁时间',
  `GRADE_TODAY` int(11) default '0' COMMENT '今日评分',
  `UPLOAD_TODAY` int(11) default '0' COMMENT '今日上传',
  `POINT` bigint(20) default '0' COMMENT '积分',
  `INTRODUCTION` varchar(255) default NULL COMMENT '个人介绍',
  `SIGNED` varchar(255) default NULL COMMENT '个性签名',
  `AVATAR` varchar(100) default NULL COMMENT '个人头像',
  `AVATAR_TYPE` smallint(6) default '0' COMMENT '头像类型',
  `TOPIC_COUNT` int(11) default '0' COMMENT '主题总数',
  `REPLY_COUNT` int(11) default '0' COMMENT '回复总数',
  `PRIME_COUNT` int(11) default '0' COMMENT '精华总数',
  `POST_TODAY` int(11) default '0' COMMENT '今日发帖',
  `LAST_POST_TIME` datetime default NULL COMMENT '最后回帖时间',
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `ak_username` (`username`),
  KEY `FK_BBS_MEMBER_MEMBERGROUP` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BBS用户表';
INSERT INTO `jb_user` VALUES (5,1,'admin','wudily2010@163.com','2011-03-17 12:02:04','127.0.0.1','2011-04-11 00:49:41','127.0.0.1',28,0,0,'2011-03-17',1,0,0,NULL,NULL,0,0,NULL,NULL,NULL,0,0,0,0,0,NULL);
INSERT INTO `jb_user` VALUES (6,12,'korven','jeecms@163.com','2011-03-19 11:06:43','127.0.0.1','2011-04-11 00:37:51','127.0.0.1',46,0,0,'2011-03-19',0,0,0,NULL,NULL,0,41,NULL,'生命在于折腾~','08.gif',0,8,4,0,12,NULL);
INSERT INTO `jb_user` VALUES (7,1,'korven1','1231@123.com','2011-03-19 11:17:01','127.0.0.1','2011-03-19 11:17:01','127.0.0.1',0,0,0,'2011-03-19',0,0,0,NULL,NULL,0,0,NULL,NULL,NULL,0,0,0,0,0,NULL);
INSERT INTO `jb_user` VALUES (8,1,'rockect','rocket@163.com','2011-03-27 17:09:11','127.0.0.1','2011-03-27 17:11:49','127.0.0.1',1,0,0,'2011-03-27',0,0,0,NULL,NULL,0,0,NULL,NULL,NULL,0,0,0,0,0,NULL);
INSERT INTO `jb_user` VALUES (9,1,'test','123@123.com','2011-04-11 00:38:08','127.0.0.1','2011-04-11 00:49:12','127.0.0.1',3,0,0,'2011-04-11',0,0,0,NULL,NULL,0,0,NULL,'','none.gif',0,0,0,0,0,NULL);
CREATE TABLE `jb_user_ext` (
  `user_id` int(11) NOT NULL,
  `realname` varchar(100) default NULL COMMENT '真实姓名',
  `gender` tinyint(1) default NULL COMMENT '性别',
  `avatar` varchar(100) default NULL COMMENT '用户头像',
  `birthday` datetime default NULL COMMENT '出生日期',
  `intro` varchar(255) default NULL COMMENT '个人介绍',
  `comefrom` varchar(150) default NULL COMMENT '来自',
  `qq` varchar(100) default NULL COMMENT 'QQ',
  `msn` varchar(100) default NULL COMMENT 'MSN',
  `phone` varchar(50) default NULL COMMENT '电话',
  `moble` varchar(50) default NULL COMMENT '手机',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BBS用户扩展信息表';
INSERT INTO `jb_user_ext` VALUES (5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` VALUES (6,NULL,1,NULL,NULL,'这家伙很懒，什么都没留下~','江西南昌',NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` VALUES (7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` VALUES (8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` VALUES (9,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
CREATE TABLE `jc_config` (
  `config_id` int(11) NOT NULL,
  `context_path` varchar(20) default '' COMMENT '部署路径',
  `servlet_point` varchar(20) default NULL COMMENT 'Servlet挂载点',
  `port` int(11) default NULL COMMENT '端口',
  `db_file_uri` varchar(50) NOT NULL default '/dbfile.svl?n=' COMMENT '数据库附件访问地址',
  `is_upload_to_db` tinyint(1) NOT NULL default '0' COMMENT '上传附件至数据库',
  `def_img` varchar(255) NOT NULL default '/r/cms/www/blue/no_picture.gif' COMMENT '图片不存在时默认图片',
  `login_url` varchar(255) NOT NULL default '/login.jspx' COMMENT '登录地址',
  `process_url` varchar(255) default NULL COMMENT '登录后处理地址',
  `count_clear_time` date NOT NULL COMMENT '计数器清除时间',
  `count_copy_time` datetime NOT NULL COMMENT '计数器拷贝时间',
  `download_code` varchar(32) NOT NULL default 'jeecms' COMMENT '下载防盗链md5混淆码',
  `download_time` int(11) NOT NULL default '12' COMMENT '下载有效时间（小时）',
  PRIMARY KEY  (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS配置表';
INSERT INTO `jc_config` VALUES (1,'',NULL,80,'/dbfile.svl?n=',0,'/r/cms/www/blue/no_picture.gif','/login.jspx',NULL,'1999-12-30','1999-12-30 01:00:00','jeecms',12);
CREATE TABLE `jc_site` (
  `site_id` int(11) NOT NULL auto_increment,
  `config_id` int(11) NOT NULL COMMENT '配置ID',
  `ftp_upload_id` int(11) default NULL COMMENT '上传ftp',
  `domain` varchar(50) NOT NULL COMMENT '域名',
  `site_path` varchar(20) NOT NULL COMMENT '路径',
  `site_name` varchar(100) NOT NULL COMMENT '网站名称',
  `short_name` varchar(100) NOT NULL COMMENT '简短名称',
  `protocol` varchar(20) NOT NULL default 'http://' COMMENT '协议',
  `dynamic_suffix` varchar(10) NOT NULL default '.jhtml' COMMENT '动态页后缀',
  `static_suffix` varchar(10) NOT NULL default '.html' COMMENT '静态页后缀',
  `static_dir` varchar(50) default NULL COMMENT '静态页存放目录',
  `is_index_to_root` char(1) NOT NULL default '0' COMMENT '是否使用将首页放在根目录下',
  `is_static_index` char(1) NOT NULL default '0' COMMENT '是否静态化首页',
  `locale_admin` varchar(10) NOT NULL default 'zh_CN' COMMENT '后台本地化',
  `locale_front` varchar(10) NOT NULL default 'zh_CN' COMMENT '前台本地化',
  `tpl_solution` varchar(50) NOT NULL default 'default' COMMENT '模板方案',
  `final_step` tinyint(4) NOT NULL default '2' COMMENT '终审级别',
  `after_check` tinyint(4) NOT NULL default '2' COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `is_relative_path` char(1) NOT NULL default '1' COMMENT '是否使用相对路径',
  `is_recycle_on` char(1) NOT NULL default '1' COMMENT '是否开启回收站',
  `domain_alias` varchar(255) default NULL COMMENT '域名别名',
  `domain_redirect` varchar(255) default NULL COMMENT '域名重定向',
  PRIMARY KEY  (`site_id`),
  UNIQUE KEY `ak_domain` (`domain`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='站点表';
INSERT INTO `jc_site` VALUES (1,1,NULL,'localhost','www','JEEBBS论坛','jeebbs','http://','.jhtml','.html',NULL,'0','0','zh_CN','zh_CN','blue',2,2,'1','1','','');
CREATE TABLE `jo_authentication` (
  `authentication_id` char(32) NOT NULL COMMENT '认证ID',
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` varchar(50) NOT NULL COMMENT '登录ip',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY  (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='认证信息表';
INSERT INTO `jo_authentication` VALUES ('10dd9e72cf3449c2b9db2177192fb7c5',8,'rockect','rocket@163.com','2011-03-27 17:11:48','127.0.0.1','2011-03-27 17:13:49');
INSERT INTO `jo_authentication` VALUES ('9444a31e2bcd4a18b3c7e4cabf98fb97',6,'korven','jeecms@163.com','2011-03-27 15:40:50','127.0.0.1','2011-03-27 15:41:05');
INSERT INTO `jo_authentication` VALUES ('9a3b5de092d24070a707df149446f958',6,'korven','jeecms@163.com','2011-03-27 15:07:17','127.0.0.1','2011-03-27 16:08:54');
INSERT INTO `jo_authentication` VALUES ('c784c67c3d134b34b85512b61ee75dc4',5,'admin','wudily2010@163.com','2011-04-11 00:49:41','127.0.0.1','2011-04-11 00:49:41');
INSERT INTO `jo_authentication` VALUES ('eadd240452544de69d92c877f7f53345',6,'korven','jeecms@163.com','2011-03-27 23:04:01','127.0.0.1','2011-03-27 23:13:45');
CREATE TABLE `jo_ftp` (
  `ftp_id` int(11) NOT NULL auto_increment,
  `ftp_name` varchar(100) NOT NULL COMMENT '名称',
  `ip` varchar(50) NOT NULL COMMENT 'IP',
  `port` int(11) NOT NULL default '21' COMMENT '端口号',
  `username` varchar(100) default NULL COMMENT '登录名',
  `password` varchar(100) default NULL COMMENT '登陆密码',
  `encoding` varchar(20) NOT NULL default 'UTF-8' COMMENT '编码',
  `timeout` int(11) default NULL COMMENT '超时时间',
  `ftp_path` varchar(255) default NULL COMMENT '路径',
  `url` varchar(255) NOT NULL COMMENT '访问URL',
  PRIMARY KEY  (`ftp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FTP表';
CREATE TABLE `jo_template` (
  `tpl_name` varchar(150) NOT NULL COMMENT '模板名称',
  `tpl_source` longtext COMMENT '模板内容',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `is_directory` tinyint(1) NOT NULL default '0' COMMENT '是否目录',
  PRIMARY KEY  (`tpl_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板表';
CREATE TABLE `jo_upload` (
  `filename` varchar(150) NOT NULL COMMENT '文件名',
  `length` int(11) NOT NULL COMMENT '文件大小(字节)',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `content` longblob NOT NULL COMMENT '内容',
  PRIMARY KEY  (`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传附件表';
CREATE TABLE `jo_user` (
  `user_id` int(11) NOT NULL auto_increment COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '电子邮箱',
  `password` char(32) NOT NULL COMMENT '密码',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL default '0' COMMENT '登录次数',
  `reset_key` char(32) default NULL COMMENT '重置密码KEY',
  `reset_pwd` varchar(10) default NULL COMMENT '重置密码VALUE',
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `ak_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户表';
INSERT INTO `jo_user` VALUES (5,'admin','wudily2010@163.com','5f4dcc3b5aa765d61d8327deb882cf99','2011-03-17 12:02:04','127.0.0.1','2011-04-11 00:49:41','127.0.0.1',28,NULL,NULL);
INSERT INTO `jo_user` VALUES (6,'korven','jeecms@163.com','4297f44b13955235245b2497399d7a93','2011-03-19 11:06:43','127.0.0.1','2011-04-11 00:37:51','127.0.0.1',46,NULL,NULL);
INSERT INTO `jo_user` VALUES (7,'korven1','1231@123.com','4297f44b13955235245b2497399d7a93','2011-03-19 11:17:01','127.0.0.1','2011-03-19 11:17:01','127.0.0.1',0,NULL,NULL);
INSERT INTO `jo_user` VALUES (8,'rockect','rocket@163.com','4297f44b13955235245b2497399d7a93','2011-03-27 17:09:11','127.0.0.1','2011-03-27 17:11:48','127.0.0.1',1,NULL,NULL);
INSERT INTO `jo_user` VALUES (9,'test','123@123.com','5f4dcc3b5aa765d61d8327deb882cf99','2011-04-11 00:38:08','127.0.0.1','2011-04-11 00:49:12','127.0.0.1',3,NULL,NULL);

ALTER TABLE `attachment`
ADD CONSTRAINT `FK_attachment_post` FOREIGN KEY (`post_id`) REFERENCES `bbs_post` (`POST_ID`);

ALTER TABLE `bbs_category`
ADD CONSTRAINT `FK_BBS_CTG_SITE` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_category_user`
ADD CONSTRAINT `FK_BBS_CATEGORY_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`),
  ADD CONSTRAINT `FK_BBS_USER_TO_CATEGORY` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `bbs_category` (`CATEGORY_ID`);

ALTER TABLE `bbs_config`
ADD CONSTRAINT `FK_BBS_CONFIG_SITE` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_forum`
ADD CONSTRAINT `FK_BBS_FORUM_CTG` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `bbs_category` (`CATEGORY_ID`),
  ADD CONSTRAINT `FK_BBS_FORUM_POST` FOREIGN KEY (`POST_ID`) REFERENCES `bbs_post` (`POST_ID`),
  ADD CONSTRAINT `FK_BBS_FORUM_USER` FOREIGN KEY (`replyer_id`) REFERENCES `jb_user` (`user_id`),
  ADD CONSTRAINT `FK_BBS_FORUM_WEBSITE` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_forum_group_reply`
ADD CONSTRAINT `FK_BBS_FORUM_GROUP_REPLY` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`),
  ADD CONSTRAINT `FK_BBS_GROUP_FORUM_REPLY` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_forum_group_topic`
ADD CONSTRAINT `FK_BBS_FORUM_GROUP_TOPIC` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`),
  ADD CONSTRAINT `FK_BBS_GROUP_FORUM_TOPIC` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_forum_group_view`
ADD CONSTRAINT `FK_BBS_FORUM_GROUP_VIEW` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`),
  ADD CONSTRAINT `FK_BBS_GROUP_FORUM_VIEW` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_forum_user`
ADD CONSTRAINT `FK_BBS_FORUM_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`),
  ADD CONSTRAINT `FK_BBS_USER_TO_FORUM` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_grade`
ADD CONSTRAINT `FK_MEMBER_GRADE` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`),
  ADD CONSTRAINT `FK_POST_GRADE` FOREIGN KEY (`POST_ID`) REFERENCES `bbs_post` (`POST_ID`);

ALTER TABLE `bbs_operation`
ADD CONSTRAINT `FK_BBS_OPEATTION` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`),
  ADD CONSTRAINT `FK_BBS_OPERATION_USER` FOREIGN KEY (`operater_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_permission`
ADD CONSTRAINT `FK_BBS_PERMISSION_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`);

ALTER TABLE `bbs_post`
ADD CONSTRAINT `FK_BBS_POST_CREATER` FOREIGN KEY (`CREATER_ID`) REFERENCES `jb_user` (`user_id`),
  ADD CONSTRAINT `FK_BBS_POST_EDITOR` FOREIGN KEY (`EDITER_ID`) REFERENCES `jb_user` (`user_id`),
  ADD CONSTRAINT `FK_BBS_POST_TOPIC` FOREIGN KEY (`TOPIC_ID`) REFERENCES `bbs_topic` (`TOPIC_ID`),
  ADD CONSTRAINT `FK_BBS_POST_WEBSITE` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_topic`
ADD CONSTRAINT `FK_BBS_FIRST_POST` FOREIGN KEY (`FIRST_POST_ID`) REFERENCES `bbs_post` (`POST_ID`),
  ADD CONSTRAINT `FK_BBS_LAST_POST` FOREIGN KEY (`LAST_POST_ID`) REFERENCES `bbs_post` (`POST_ID`),
  ADD CONSTRAINT `FK_BBS_TOPIC_FORUM` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`),
  ADD CONSTRAINT `FK_BBS_TOPIC_SITE` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`),
  ADD CONSTRAINT `FK_BBS_TOPIC_USER_CREATE` FOREIGN KEY (`CREATER_ID`) REFERENCES `jb_user` (`user_id`),
  ADD CONSTRAINT `FK_BBS_TOPIC_USER_LAST` FOREIGN KEY (`REPLYER_ID`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_user_group`
ADD CONSTRAINT `FK_BBS_GROUP_SITE` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `jb_user`
ADD CONSTRAINT `FK_BBS_MEMBER_MEMBERGROUP` FOREIGN KEY (`group_id`) REFERENCES `bbs_user_group` (`GROUP_ID`);