--
-- JavaCenterHome 数据库SQL
-- 生成日期: 2008 年 1 月 1 日 00:00
--

--
-- 数据库: 'jchome'
--

-- --------------------------------------------------------

--
-- 表的结构 'jchome_ad'
--

CREATE TABLE jchome_ad (
  adid smallint(6) unsigned NOT NULL auto_increment,
  available tinyint(1) NOT NULL default '1',
  title varchar(50) NOT NULL default '',
  pagetype varchar(20) NOT NULL default '',
  adcode text NOT NULL,
  system tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (adid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_adminsession'
--

CREATE TABLE jchome_adminsession (
  uid mediumint(8) unsigned NOT NULL default '0',
  ip char(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  errorcount tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (uid)
) ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_album'
-- 相册表

CREATE TABLE jchome_album (
  albumid mediumint(8) unsigned NOT NULL auto_increment,
  albumname varchar(50) NOT NULL default '' COMMENT '相册名',
  uid mediumint(8) unsigned NOT NULL default '0' COMMENT '所属用户id',
  username varchar(15) NOT NULL default '' COMMENT '所属用户名',
  dateline int(10) unsigned NOT NULL default '0',
  updatetime int(10) unsigned NOT NULL default '0',
  picnum smallint(6) unsigned NOT NULL default '0',
  pic varchar(60) NOT NULL default '',
  picflag tinyint(1) NOT NULL default '0',
  friend tinyint(1) NOT NULL default '0'  COMMENT '权限，0(全站用户可见),1(全好友可见),2(仅指定的好友可见),3(仅自己可见),4(凭密码查看)',
  `password` varchar(10) NOT NULL default '',
  target_ids text NOT NULL,
  PRIMARY KEY  (albumid),
  KEY uid (uid,updatetime),
  KEY updatetime (updatetime)
) ENGINE=MyISAM COMMENT ='相册表';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_appcreditlog'
--

CREATE TABLE jchome_appcreditlog (
  logid mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  appid mediumint(8) unsigned NOT NULL default '0',
  appname varchar(60) NOT NULL default '',
  `type` tinyint(1) NOT NULL default '0',
  credit mediumint(8) unsigned NOT NULL default '0',
  note text NOT NULL,
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (logid),
  KEY uid (uid,dateline),
  KEY appid (appid)
) ENGINE=MyISAM;
-- --------------------------------------------------------

--
-- 表的结构 'jchome_blacklist'
--

CREATE TABLE jchome_blacklist (
  uid mediumint(8) unsigned NOT NULL default '0',
  buid mediumint(8) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid,buid),
  KEY uid (uid,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_block'
--

CREATE TABLE jchome_block (
  bid smallint(6) unsigned NOT NULL auto_increment,
  blockname varchar(40) NOT NULL default '',
  blocksql text NOT NULL,
  cachename varchar(30) NOT NULL default '',
  cachetime smallint(6) unsigned NOT NULL default '0',
  startnum tinyint(3) unsigned NOT NULL default '0',
  num tinyint(3) unsigned NOT NULL default '0',
  perpage tinyint(3) unsigned NOT NULL default '0',
  htmlcode text NOT NULL,
  PRIMARY KEY  (bid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_blog'
--日志表

CREATE TABLE jchome_blog (
  blogid mediumint(8) unsigned NOT NULL auto_increment,
  topicid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  username char(15) NOT NULL default '',
  `subject` char(80) NOT NULL default '' COMMENT '标题',
  classid smallint(6) unsigned NOT NULL default '0' COMMENT '分类',
  viewnum mediumint(8) unsigned NOT NULL default '0',
  replynum mediumint(8) unsigned NOT NULL default '0',
  hot mediumint(8) unsigned NOT NULL default '0' COMMENT '热度',
  dateline int(10) unsigned NOT NULL default '0',
  pic char(120) NOT NULL default '',
  picflag tinyint(1) NOT NULL default '0',
  noreply tinyint(1) NOT NULL default '0' COMMENT '不允许评论,1(不允许评论)',
  friend tinyint(1) NOT NULL default '0' COMMENT '隐私设置，0(全站用户可见),1(全好友可见),2(仅指定的好友可见),3(仅自己可见),4(凭密码查看)',
  `password` char(10) NOT NULL default '' COMMENT '密码',
  click_1 smallint(6) unsigned NOT NULL default '0',
  click_2 smallint(6) unsigned NOT NULL default '0',
  click_3 smallint(6) unsigned NOT NULL default '0',
  click_4 smallint(6) unsigned NOT NULL default '0',
  click_5 smallint(6) unsigned NOT NULL default '0',
  PRIMARY KEY  (blogid),
  KEY uid (uid,dateline),
  KEY topicid (topicid,dateline),
  KEY dateline (dateline)
) ENGINE=MyISAM COMMENT='日志表';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_blogfield'
--日志字段

CREATE TABLE jchome_blogfield (
  blogid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  tag varchar(255) NOT NULL default '' COMMENT '标签',
  message mediumtext NOT NULL COMMENT '内容',
  postip varchar(20) NOT NULL default '',
  related text NOT NULL,
  relatedtime int(10) unsigned NOT NULL default '0',
  target_ids text NOT NULL,
  hotuser text NOT NULL,
  magiccolor tinyint(6) NOT NULL default '0',
  magicpaper tinyint(6) NOT NULL default '0',
  magiccall tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (blogid)
) ENGINE=MyISAM COMMENT='日志字段';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_cache'
--

CREATE TABLE jchome_cache (
  cachekey varchar(16) NOT NULL default '',
  `value` mediumtext NOT NULL,
  mtime int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (cachekey)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_class'
--日志分类表

CREATE TABLE jchome_class (
  classid mediumint(8) unsigned NOT NULL auto_increment COMMENT 'id',
  classname char(40) NOT NULL default '' COMMENT '名称',
  uid mediumint(8) unsigned NOT NULL default '0' COMMENT '所属用户id',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (classid),
  KEY uid (uid)
) ENGINE=MyISAM COMMENT ='日志分类表';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_click'
--

CREATE TABLE jchome_click (
  clickid smallint(6) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL default '',
  icon varchar(100) NOT NULL default '',
  idtype varchar(15) NOT NULL default '',
  displayorder tinyint(6) unsigned NOT NULL default '0',
  PRIMARY KEY  (clickid),
  KEY idtype (idtype,displayorder)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_clickuser'
--

CREATE TABLE jchome_clickuser (
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  id mediumint(8) unsigned NOT NULL default '0',
  idtype varchar(15) NOT NULL default '',
  clickid smallint(6) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  KEY id (id,idtype,dateline),
  KEY uid (uid,idtype,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_comment'
--评论、留言

CREATE TABLE jchome_comment (
  cid mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0'  COMMENT 'ID',
  id mediumint(8) unsigned NOT NULL default '0' COMMENT '评论对象ID',
  idtype varchar(20) NOT NULL default '' COMMENT '评论对象类型,uid(留言),blogid(日志),picid(图片),eventid(活动),sid(分享)',
  authorid mediumint(8) unsigned NOT NULL default '0' COMMENT '作者id',
  author varchar(15) NOT NULL default '' COMMENT '作者',
  ip varchar(20) NOT NULL default '' COMMENT '评论ip',
  dateline int(10) unsigned NOT NULL default '0'  COMMENT '发布时间',
  message text NOT NULL COMMENT '内容',
  magicflicker tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (cid),
  KEY authorid (authorid, idtype),
  KEY id (id, idtype, dateline)
) ENGINE=MyISAM COMMENT ='评论、留言';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_config'
--系统配置属性,存贮键值对
--已知的key-value如下：
--defaultpoke:默认好友 打招呼内容
--defaultfusername:默认好友,这些用户会自动将新注册用户添加为好友，并向其打个招呼。注意，指定的这几位用户浏览自己的首页时，可能会因其好友数众多而增加服务器负载。
--spacebarusername:推荐成员,这些用户将随机显示在随便看看页面的“站长推荐”栏目中。
--         ********防灌水设置*******
--login_action:自定义登录识别名,给登录页面设置个性名，可加大灌水机自动登录的难度，如果有必要，可以不定期更改一下
--register_action:自定义注册识别名,给注册页面设置个性名。注意，不能与登录识别名相同
--seccode_login:登录页面开启验证码功能,1(开启)
--seccode_register:注册页面开启验证码功能,1(开启)
--questionmode：验证码采用个性提问模式，1(使用我自定义的问答（推荐，效果好）)，0(使用系统验证码图片)
--need_email:强制新用户验证激活邮箱,1(是),0(否),选择是的话，用户必须验证激活自己的邮箱后，才可以进行发布操作。
--uniqueemail:验证激活邮箱唯一性,1(是),0(否),选择是的话，验证激活的邮箱将唯一性，不允许重复。
--newusertime:新用户见习时间,设置新注册用户必须等待多少小时后才可以发布操作。
--need_avatar:强制新用户上传头像,1(是),0(否),选择是的话，用户必须设置自己的头像后才能进行发布操作。
--need_friendnum:设置用户必须拥有多少个好友后，才可以进行发布操作。
--pmsendregdays:发短消息最少注册天数,注册天数少于次设置的，不允许发送短消息，0为不限制，此举为了限制机器人发广告。
--pmlimit1day:同一用户在 24 小时允许发送短消息的最大数目,同一用户在 24 小时内可以发送的短消息的极限，建议在 30 - 100 范围内取值，0 为不限制，此举为了限制通过机器批量发广告。
--pmfloodctrl:发短消息灌水预防,两次发短消息间隔小于此时间，单位秒，0 为不限制，此举为了限制通过机器批量发广告。
--         *******访问ip设置*********
--ipbanned:禁止访问的IP列表
--ipaccess:允许访问的IP列表
--         *******隐私设置***********
--networkpublic:游客开放浏览，1(是),0(否)。开放的话用户不需要登录也能浏览随便看看、未设置隐私的个人空间，同时，站内的信息也可以被搜索引擎收录。
--privacy:新用户默认隐私设置与默认动态发布设置,【序列化存储】
--         *******站点设置***********
--                ********1.基本设置********
--sitename:站点名称
--siteallurl:站点访问URL地址
--adminemail：站点联系邮箱
--licensed:显示授权信息链接,1(是),0(否)
--debuginfo:显示程序执行信息,1(是),0(否)
--miibeian:ICP/IP/域名备案
--headercharset:强制使用默认字符集,1(是),0(否)
--allowrewrite:URL Rewrite,URL 静态化可以提高搜索引擎抓取，开启本功能会轻微增加服务器负担。,1(是),0(否)
--onlinehold:记录在线时间(单位 秒)
--updatestat:开启站点概况统计,1(是),0(否),建议开启，记录站点每日的登录人数、发表数、互动数等重点参数，可为站点运营发展提供重要数据依据
--avatarreal:用户头像体系,1(使用头像体系2),其他(使用头像体系2)
--jc_dir:JavaCenter的物理路径.默认为空。如果本系统可以读取JavaCenter的程序目录，推荐填写该路径值。
--close:站点关闭访问,1(是),0(否)
--closereason:站点关闭说明
--               *******2.注册与显示*******
--closeregister：关闭新用户注册,1(是),0(否)
--closeinvite：关闭邀请注册功能,1(是),0(否)
--checkemail：一个邮箱只能注册一个账号,1(是),0(否)
--regipdate：同一IP注册时间间隔，限制同一个ip，在多长时间内只能注册一个账号
--registerrule：注册服务条款，用户注册的时候会显示并需要接受的服务条款。（支持html语言，换行使用<br>）
--maxpage：列表最大分页数，允许用户查看的最大分页数。当用户查看的分页数越大的时候，对服务器的负载压力就越大。
--feedday：动态保留天数，个人动态的保留天数。超过该天数的个人动态会被清理掉，从而可以保证MySQL的效率。建议不要设置太长。
--feedmaxnum：首页显示的好友动态将从这些数目的事件中进行合并显示。建议不要设置太多，从而可以保证MySQL的效率，但最少不能低于50。
--showallfriendnum:首页动态默认标签设置.好友数小于showallfriendnum的时候显示所有人的动态，超过这个数值显示好友动态,在日志、相册列表页面，该参数同样生效。
--feedhiddenicon:首页动态折叠设置,将某些动态折叠后，可以适当减少首页的动态噪音。
--feedhotnum:首页显示热点的数目,系统会自动获取10个热点，其中，排名第一的热点会固定显示，其余热点会随机显示。
--newspacenum:首页欢迎新成员数量
--newspaceavatar:已经上传头像(显示的新成员条件)
--newspacerealname：已经实名认证(显示的新成员条件)
--newspacevideophoto：已经视频认证(显示的新成员条件)
--feedhotday：热点推荐的天数范围，设置首页热点推荐选择的天数范围。
--feedhotmin：热点推荐的最小热度值，设置当发布的信息热度值超过多少后，才会显示在推荐里面。
--feedtargetblank：动态链接打开模式，1(新窗口打开),其他(同一窗口打开)
--feedread:记录动态阅读状态,1(是),0(否),如果选择是，该条动态被点击的时候，会变成灰色，以表示已经阅读。(注意，该功能对热点动态无效)
--networkpage:成员排行榜允许分页浏览,1(是),0(否),该设置只对排行榜中的竞价排行、在线成员、全部成员有效。
--topcachetime:成员排行榜缓存时间，用户排行榜中，除竞价排行、在线成员、全部成员外，其余排行榜是缓存处理的。
--linkguide：外部链接显示导航条,1(是),0(否)
--starcredit：星星经验阀值.当用户经验数到此阀值时，增加一个星星。最小为2，否则此功能无效，不显示星星数。
--starlevelnum:星星数在达到此阀值时，会升级为高级别的图标。最小为2，否则此项功能无效，始终以星星显示
--             ********3.系统功能*********
--groupnum：好友用户组个数。设置每个用户最多拥有的好友用户组个数。
--importnum:日志单次导入最大数，允许用户单次导入日志的条数
--maxreward:投票单次最高悬赏，允许用户创建悬赏投票时平均单次投票悬赏额度
--sendmailday：邮件通知更新天数单位：天，当用户多少天没有登陆站点的时候才会给其发送邮件通知，设置为0，则不启用邮件通知功能
--openxmlrpc：开启XMLPRC协议接口,1(是),0(否),用户可以通过本协议接口，使用客户端发布日志。但是，也可能会增加恶意灌水的可能。
--openim:开启即时消息,实现站内消息的即时提醒，无需刷新页面就能收到有新消息提示信息。注意: 本功能会严重加重服务器负担。此功能仅在Tomcat服务器下有效，此功能需要web服务器为Tomcat6.x，并需要配置Tomcat的server.xml，修改Connector节点的protocol属性值为"org.apache.coyote.http11.Http11NioProtocol"。
--jc_tagrelated：JavaCenter应用标签相关信息,1(是),0(否)，开启本功能，则系统会通过JavaCenter，获取到站内其他应用的标签相关信息
--jc_tagrelatedtime：缓存更新间隔，设置标签关联信息多长时间更新一次，建议设置1小时（3600秒）以上，减轻对服务器的压力。为0则不更新。
--allowcache：模块缓存,1(是),0(否)，推荐开启，这样在使用模块调用数据的时候，可以大幅度降低MYSQL服务器负载
--cachemode：缓存模式，file(存储到文本),database(存储到数据库)
--cachegrade:缓存分表等级,0(只用一个表(或目录)),1(分散到15个子表(或目录)),2(分散到225个子表(或目录)),分表(或目录)越多效率越高，但建立的数据表(或目录)越多
--allowdomain:二级域名功能,1(是),0(否),二级域名功能需要服务器配置支持
--holddomain:保留二级域名
--domainroot:二级域名根域名
--             ********4.实名认证设置********
--realname:实名认证设置,1(是),0(否),注意，开启实名认证会增加服务器负担。
--namecheck:实名需手工认证,1(是),0(否),设置为“是”，则用户填写的姓名只有在被管理者手工认证后才算有效。否则，则自动为认证有效。
--namechange:填写的姓名经认证有效后,1(是),0(否)，是否允许用户再次修改姓名。
--name_allowviewspace:允许查看实名用户主页,1(是),0(否)
--name_allowfriend:允许加好友,1(是),0(否)
--name_allowpoke:允许打招呼,1(是),0(否)
--name_allowdoing:允许发记录,1(是),0(否)
--name_allowblog:允许发日志,1(是),0(否)
--name_allowalbum:允许使用相册,1(是),0(否)
--name_allowthread:允许发话题,1(是),0(否)
--name_allowpoll:允许发投票,1(是),0(否)
--name_allowevent:允许发活动,1(是),0(否)
--name_allowshare:允许分享,1(是),0(否)
--name_allowgift:允许发送礼物,1(是),0(否)
--name_allowcomment:允许留言/评论,1(是),0(否)
--name_allowpost:允许群组回帖,1(是),0(否)
--          *********5.视频认证设置********
--videophoto:开启视频认证,1(是),0(否)
--videophotocheck:视频认证照片需手工认证,1(是),0(否),设置为“是”，则用户上传的认证照片只有在被管理者手工认证后才算有效。否则，则自动为认证有效。
--videophotochange:视频认证照片可再修改,1(是),0(否),上传的认证照片经认证有效后，是否允许用户再次修改认证照片。
--video_allowviewphoto:允许查看视频用户的照片,1(是),0(否)
--video_allowfriend:允许加视频用户为好友,1(是),0(否)
--video_allowpoke:允许向视频用户打招呼,1(是),0(否)
--video_allowwall:允许向视频用户留言,1(是),0(否)
--video_allowcomment:允许向视频用户评论,1(是),0(否)
--video_allowdoing:允许发记录,1(是),0(否)
--video_allowblog:允许发日志,1(是),0(否)
--video_allowalbum:允许使用相册,1(是),0(否)
--video_allowthread:允许发话题,1(是),0(否)
--video_allowpoll:允许发投票,1(是),0(否)
--video_allowevent:允许发活动,1(是),0(否)
--video_allowshare:允许分享,1(是),0(否)
--video_allowgift:允许发送礼物,1(是),0(否)
--video_allowpost:允许群组回帖,1(是),0(否)
--          *********6.上传图片设置********
--allowwatermark：是否启用图片水印,1(是),0(否)，注意，开启水印功能后，图片的EXIF信息将无法获取
--          *********7.远程上传设置********
--allowftp:启用远程附件,1(是),0(否)
--ftpssl:启用 SSL 连接,1(是),0(否)
--          *********8.邮件设置********

CREATE TABLE jchome_config (
  var varchar(30) NOT NULL default '' COMMENT '键',
  datavalue text NOT NULL COMMENT '值',
  PRIMARY KEY  (var)
) ENGINE=MyISAM COMMENT='系统配置属性,存贮键值对';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_cron'
--

CREATE TABLE jchome_cron (
  cronid smallint(6) unsigned NOT NULL auto_increment,
  available tinyint(1) NOT NULL default '0',
  `type` enum('user','system') NOT NULL default 'user',
  `name` char(50) NOT NULL default '',
  filename char(50) NOT NULL default '',
  lastrun int(10) unsigned NOT NULL default '0',
  nextrun int(10) unsigned NOT NULL default '0',
  weekday tinyint(1) NOT NULL default '0',
  `day` tinyint(2) NOT NULL default '0',
  `hour` tinyint(2) NOT NULL default '0',
  `minute` char(36) NOT NULL default '',
  PRIMARY KEY  (cronid),
  KEY nextrun (available,nextrun)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_creditrule'
-- 积分规则

CREATE TABLE jchome_creditrule (
  rid mediumint(8) unsigned NOT NULL auto_increment,
  rulename char(20) NOT NULL default ''  comment '动作名称',
  `action` char(20) NOT NULL default '',
  cycletype tinyint(1) NOT NULL default '0' comment '奖励周期 ,0(一次性),1(每天),2(整点),3(间隔分钟),4(不限周期)' ,
  cycletime int(10) NOT NULL default '0',
  rewardnum tinyint(2) NOT NULL default '1' comment '奖励次数,0表示不限次数',
  rewardtype tinyint(1) NOT NULL default '1' comment '奖励规则 ,1是奖励加分，0是惩罚扣分',
  norepeat tinyint(1) NOT NULL default '0',
  credit mediumint(8) unsigned NOT NULL default '0' comment '奖励/扣除积分',
  experience mediumint(8) unsigned NOT NULL default '0' comment '奖励/扣除经验值',
  PRIMARY KEY  (rid),
  KEY `action` (`action`)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_creditlog'
--

CREATE TABLE jchome_creditlog (
  clid mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  rid mediumint(8) unsigned NOT NULL default '0',
  total mediumint(8) unsigned NOT NULL default '0',
  cyclenum mediumint(8) unsigned NOT NULL default '0',
  credit mediumint(8) unsigned NOT NULL default '0',
  experience mediumint(8) unsigned NOT NULL default '0',
  starttime int(10) unsigned NOT NULL default '0',
  info text NOT NULL,
  `user` text NOT NULL,
  app text NOT NULL,
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (clid),
  KEY uid (uid, rid),
  KEY dateline (dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_data'
--配置数据
--已知的key-value如下：
--spam:个性问题和答案,【序列化存储】
--reason:举报可选理由
--setting:一、上传图片设置,预览缩略图宽高，图片最大宽高，水印图片地址，水印位置（1顶端居左，2顶端居右，3底端居左，4底端居右）。二、FTP连接信息（地址、用户名、密码等等）【序列化存储】
--mail：邮件设置【序列化存储】

CREATE TABLE jchome_data (
  var varchar(20) NOT NULL default '',
  datavalue text NOT NULL,
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (var)
) ENGINE=MyISAM COMMENT='配置数据';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_docomment'
--

CREATE TABLE jchome_docomment (
  id int(10) unsigned NOT NULL auto_increment,
  upid int(10) unsigned NOT NULL default '0',
  doid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  message text NOT NULL,
  ip varchar(20) NOT NULL default '',
  grade smallint(6) unsigned NOT NULL default '0',
  PRIMARY KEY (id),
  KEY doid (doid,dateline),
  KEY dateline (dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_doing'
--记录

CREATE TABLE jchome_doing (
  doid mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0' comment '作者UID',
  username varchar(15) NOT NULL default '' comment '作者名',
  `from` varchar(20) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0' comment '发布时间',
  message text NOT NULL comment '内容',
  ip varchar(20) NOT NULL default '' comment '发布ip',
  replynum int(10) unsigned NOT NULL default '0',
  mood smallint(6) NOT NULL default '0',
  PRIMARY KEY  (doid),
  KEY uid (uid,dateline),
  KEY dateline (dateline)
) ENGINE=MyISAM COMMENT='记录';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_event'
--

CREATE TABLE jchome_event (
  eventid mediumint(8) unsigned NOT NULL auto_increment,
  topicid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  title varchar(80) NOT NULL default '',
  classid smallint(6) unsigned NOT NULL default '0',
  province varchar(20) NOT NULL default '',
  city varchar(20) NOT NULL default '',
  location varchar(80) NOT NULL default '',
  poster varchar(60) NOT NULL default '',
  thumb tinyint(1) NOT NULL default '0',
  remote tinyint(1) NOT NULL default '0',
  deadline int(10) unsigned NOT NULL default '0',
  starttime int(10) unsigned NOT NULL default '0',
  endtime int(10) unsigned NOT NULL default '0',
  public tinyint(3) NOT NULL default '0',
  membernum mediumint(8) unsigned NOT NULL default '0',
  follownum mediumint(8) unsigned NOT NULL default '0',
  viewnum mediumint(8) unsigned NOT NULL default '0',
  grade tinyint(3) NOT NULL default '0',
  recommendtime int(10) unsigned NOT NULL default '0',
  tagid mediumint(8) unsigned NOT NULL default '0',
  picnum mediumint(8) unsigned NOT NULL default '0',
  threadnum mediumint(8) unsigned NOT NULL default '0',
  updatetime int(10) unsigned NOT NULL default '0',
  hot mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (eventid),
  KEY grade (grade,recommendtime),
  KEY membernum (membernum),
  KEY uid (uid,eventid),
  KEY tagid (tagid,eventid),
  KEY topicid (topicid,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_eventclass'
--

CREATE TABLE jchome_eventclass (
  classid smallint(6) unsigned NOT NULL auto_increment,
  classname varchar(80) NOT NULL default '',
  poster tinyint(1) NOT NULL default '0',
  template text NOT NULL,
  displayorder mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (classid),
  UNIQUE KEY classname (classname)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_eventfield'
--

CREATE TABLE jchome_eventfield (
  eventid mediumint(8) unsigned NOT NULL auto_increment,
  detail text NOT NULL,
  template varchar(255) NOT NULL default '',
  limitnum mediumint(8) unsigned NOT NULL default '0',
  verify tinyint(1) NOT NULL default '0',
  allowpic tinyint(1) NOT NULL default '0',
  allowpost tinyint(1) NOT NULL default '0',
  allowinvite tinyint(1) NOT NULL default '0',
  allowfellow tinyint(1) NOT NULL default '0',
  hotuser text NOT NULL,
  PRIMARY KEY  (eventid)
) ENGINE=MyISAM;


-- --------------------------------------------------------

--
-- 表的结构 'jchome_eventinvite'
--

CREATE TABLE jchome_eventinvite (
  eventid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  touid mediumint(8) unsigned NOT NULL default '0',
  tousername char(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (eventid,touid)
) ENGINE=MyISAM;


-- --------------------------------------------------------

--
-- 表的结构 'jchome_eventpic'
--

CREATE TABLE jchome_eventpic (
  picid mediumint(8) unsigned NOT NULL default '0',
  eventid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  username char(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (picid),
  KEY eventid (eventid,picid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_feed'
-- 动态(feed)

CREATE TABLE jchome_feed (
  feedid int(10) unsigned NOT NULL auto_increment,
  appid smallint(6) unsigned NOT NULL default '0',
  icon varchar(30) NOT NULL default '' COMMENT '类型',
  uid mediumint(8) unsigned NOT NULL default '0' COMMENT '0表示全局动态',
  username varchar(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0' COMMENT '发布时间，动态会在这个日期到来之前，一直显示在第一位。',
  friend tinyint(1) NOT NULL default '0',
  hash_template varchar(32) NOT NULL default '',
  hash_data varchar(32) NOT NULL default '',
  title_template text NOT NULL  COMMENT '标题模板，含占位符，支持html，例如：加粗 <b></b>，变色 <font color=red></font> ',
  title_data text NOT NULL,
  body_template text NOT NULL COMMENT '内容模板，含占位符',
  body_data text NOT NULL,
  body_general text NOT NULL  COMMENT '备注',
  image_1 varchar(255) NOT NULL default '' COMMENT '第1张图片地址',
  image_1_link varchar(255) NOT NULL default '' COMMENT '第1张图片链接',
  image_2 varchar(255) NOT NULL default ''  COMMENT '第2张图片地址',
  image_2_link varchar(255) NOT NULL default '' COMMENT '第2张图片链接',
  image_3 varchar(255) NOT NULL default ''  COMMENT '第3张图片地址',
  image_3_link varchar(255) NOT NULL default '' COMMENT '第3张图片链接',
  image_4 varchar(255) NOT NULL default ''  COMMENT '第4张图片地址',
  image_4_link varchar(255) NOT NULL default '' COMMENT '第4张图片链接',
  target_ids text NOT NULL,
  id mediumint(8) unsigned NOT NULL default '0',
  idtype varchar(15) NOT NULL default '',
  hot mediumint(8) unsigned NOT NULL default '0'  COMMENT '热度',
  PRIMARY KEY  (feedid),
  KEY uid (uid,dateline),
  KEY dateline (dateline),
  KEY hot (hot),
  KEY id (id,idtype)
) ENGINE=MyISAM COMMENT ='动态(feed)';

-- --------------------------------------------------------
--
-- 表的结构 'jchome_friend'
--

CREATE TABLE jchome_friend (
  uid mediumint(8) unsigned NOT NULL default '0',
  fuid mediumint(8) unsigned NOT NULL default '0',
  fusername varchar(15) NOT NULL default '',
  status tinyint(1) NOT NULL default '0',
  gid smallint(6) unsigned NOT NULL default '0',
  note varchar(50) NOT NULL default '',
  num mediumint(8) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid,fuid),
  KEY fuid (fuid),
  KEY status (uid, status, num, dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_friendguide'
--

CREATE TABLE jchome_friendguide (
  uid mediumint(8) unsigned NOT NULL default '0',
  fuid mediumint(8) unsigned NOT NULL default '0',
  fusername char(15) NOT NULL default '',
  num smallint(6) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid,fuid),
  KEY uid (uid,num)
) ENGINE=MyISAM;

-- --------------------------------------------------------
--
-- 表的结构 'jchome_friendlog'
--

CREATE TABLE jchome_friendlog (
  uid mediumint(8) unsigned NOT NULL default '0',
  fuid mediumint(8) unsigned NOT NULL default '0',
  action varchar(10) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid,fuid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_invite'
--

CREATE TABLE jchome_invite (
  id mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  code varchar(20) NOT NULL default '',
  fuid mediumint(8) unsigned NOT NULL default '0',
  fusername varchar(15) NOT NULL default '',
  `type` tinyint(1) NOT NULL default '0',
  email varchar(100) NOT NULL default '',
  appid mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (id),
  KEY uid (uid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_log'
--

CREATE TABLE jchome_log (
  logid mediumint(8) unsigned NOT NULL auto_increment,
  id mediumint(8) unsigned NOT NULL default '0',
  idtype char(20) NOT NULL default '',
  PRIMARY KEY  (logid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_magic'
-- 道具表

CREATE TABLE jchome_magic (
  mid varchar(15) NOT NULL default '' COMMENT 'id',
  `name` varchar(30) NOT NULL default ''  COMMENT '名称',
  description text NOT NULL COMMENT '说明描述',
  forbiddengid text NOT NULL,
  charge smallint(6) unsigned NOT NULL default '0' COMMENT '单价,购买时单个需要花费的积分值，需小于65535',
  experience smallint(6) unsigned NOT NULL default '0' COMMENT '经验成长,购买单个可以增长的经验值，需小于65535',
  provideperoid int(10) unsigned NOT NULL default '0' COMMENT '补给周期,若道具商店中此道具已售光，在补给周期内自动补给一次.0(总是可以),3600(间隔1小时),86400(间隔24小时),604800(间隔7天)',
  providecount smallint(6) unsigned NOT NULL default '0' COMMENT '补给数目,若道具商店中此道具已售光，自动补给一次的数目，需小于65535',
  useperoid int(10) unsigned NOT NULL default '0' COMMENT '使用周期,设定用户使用此道具的使用周期.0(总是可以),3600(间隔1小时),86400(间隔24小时),604800(间隔7天)',
  usecount smallint(6) unsigned NOT NULL default '0' COMMENT '使用数目,设定用户在使用周期内最多能使用此道具的个数，需小于65535.',
  displayorder smallint(6) unsigned NOT NULL default '0' COMMENT '显示顺序',
  custom text NOT NULL,
  `close` tinyint(1) NOT NULL default '0' COMMENT '1(禁用道具),0(启用道具)',
  PRIMARY KEY  (mid)
) ENGINE=MyISAM COMMENT= '道具表';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_magicinlog'
--道具获取记录表

CREATE TABLE jchome_magicinlog (
  logid mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '' COMMENT '用户',
  mid varchar(15) NOT NULL default '' COMMENT '道具id',
  count smallint(6) unsigned NOT NULL default '0' COMMENT '道具数量',
  `type` tinyint(3) unsigned NOT NULL default '0' COMMENT '方式,2(获赠),3(升级用户组),其他(购买)',
  fromid mediumint(8) unsigned NOT NULL default '0',
  credit smallint(6) unsigned NOT NULL default '0',
  dateline int(10) NOT NULL default '0' COMMENT '时间',
  PRIMARY KEY  (logid),
  KEY uid (uid,dateline),
  KEY `type` (`type`,fromid,dateline)
) ENGINE=MyISAM COMMENT= '道具获取记录表';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_magicstore'
--道具出售统计表

CREATE TABLE jchome_magicstore (
  mid varchar(15) NOT NULL default '',
  `storage` smallint(6) unsigned NOT NULL default '0' COMMENT  '库存数量',
  lastprovide int(10) unsigned NOT NULL default '0',
  sellcount int(8) unsigned NOT NULL default '0' COMMENT  '' COMMENT  '售出数',
  sellcredit int(8) unsigned NOT NULL default '0' COMMENT  '' COMMENT  '回收积分',
  PRIMARY KEY  (mid)
) ENGINE=MyISAM COMMENT= '道具出售统计表';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_magicuselog'
--道具使用记录表

CREATE TABLE jchome_magicuselog (
  logid mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '' COMMENT '用户',
  mid varchar(15) NOT NULL default '' COMMENT '道具id',
  id mediumint(8) unsigned NOT NULL default '0',
  idtype varchar(20) NOT NULL default '',
  count mediumint(8) unsigned NOT NULL default '0',
  `data` text NOT NULL,
  dateline int(10) unsigned NOT NULL default '0' COMMENT '时间',
  expire int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (logid),
  KEY uid (uid,mid),
  KEY id (id,idtype)
) ENGINE=MyISAM COMMENT= '道具使用记录表';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_mailqueue'
--

CREATE TABLE jchome_mailqueue (
  qid mediumint(8) unsigned NOT NULL auto_increment,
  cid mediumint(8) unsigned NOT NULL default '0',
  subject text NOT NULL,
  message text NOT NULL,
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (qid),
  KEY mcid (cid,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_mailcron'
--

CREATE TABLE jchome_mailcron (
  cid mediumint(8) unsigned NOT NULL auto_increment,
  touid mediumint(8) unsigned NOT NULL default '0',
  email varchar(100) NOT NULL default '',
  sendtime int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (cid),
  KEY sendtime (sendtime)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_member'
--

CREATE TABLE jchome_member (
  uid mediumint(8) unsigned NOT NULL auto_increment,
  username char(15) NOT NULL default '',
  `password` char(32) NOT NULL default '',
  blacklist text NOT NULL,
  salt char(6) NOT NULL,
  PRIMARY KEY  (uid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_mtag'
--群组

CREATE TABLE jchome_mtag (
  tagid mediumint(8) unsigned NOT NULL auto_increment,
  tagname varchar(40) NOT NULL default '' comment '群组名',
  fieldid smallint(6) NOT NULL default '0' comment '归属栏目id',
  membernum mediumint(8) unsigned NOT NULL default '0' comment '用户数',
  threadnum mediumint(8) unsigned NOT NULL default '0' comment '话题数',
  postnum mediumint(8) unsigned NOT NULL default '0' comment '回帖数',
  `close` tinyint(1) NOT NULL default '0'  comment '是否锁定,1(是)',
  announcement text NOT NULL,
  pic varchar(150) NOT NULL default '',
  closeapply tinyint(1) NOT NULL default '0',
  joinperm tinyint(1) NOT NULL default '0'  comment '加入权限,0(公开),1(审核),2(私密)',
  viewperm tinyint(1) NOT NULL default '0' comment '浏览权限,0(公开),1(封闭)',
  threadperm tinyint(1) NOT NULL default '0' comment '发帖权限,0(仅成员可发话题),1(所有人可以发话题)',
  postperm tinyint(1) NOT NULL default '0' comment '回帖权限,0(仅成员可回帖),1(所有人可回帖)',
  recommend tinyint(1) NOT NULL default '0' comment '是否推荐,1(是)',
  moderator varchar(255) NOT NULL default '',
  PRIMARY KEY  (tagid),
  KEY tagname (tagname),
  KEY threadnum (threadnum)
) ENGINE=MyISAM COMMENT='群组';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_mtaginvite'
--

CREATE TABLE jchome_mtaginvite (
  uid mediumint(8) unsigned NOT NULL default '0',
  tagid mediumint(8) unsigned NOT NULL default '0',
  fromuid mediumint(8) unsigned NOT NULL default '0',
  fromusername char(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid,tagid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_myapp'
--

CREATE TABLE jchome_myapp (
  appid mediumint(8) unsigned NOT NULL default '0',
  appname varchar(60) NOT NULL default '',
  narrow tinyint(1) NOT NULL default '0',
  flag tinyint(1) NOT NULL default '0',
  version mediumint(8) unsigned NOT NULL default '0',
  displaymethod tinyint(1) NOT NULL default '0',
  displayorder smallint(6) unsigned NOT NULL default '0',
  PRIMARY KEY (appid),
  KEY flag (flag, displayorder)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_myinvite'
--

CREATE TABLE jchome_myinvite (
  id mediumint(8) unsigned NOT NULL auto_increment,
  typename varchar(100) NOT NULL default '',
  appid mediumint(8) NOT NULL default '0',
  type tinyint(1) NOT NULL default '0',
  fromuid mediumint(8) unsigned NOT NULL default '0',
  touid mediumint(8) unsigned NOT NULL default '0',
  myml text NOT NULL,
  dateline int(10) unsigned NOT NULL default '0',
  hash int(10) unsigned NOT NULL default '0',
  PRIMARY KEY (id),
  KEY hash (hash),
  KEY uid (touid, dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_notification'
--

CREATE TABLE jchome_notification (
  id mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  `type` varchar(20) NOT NULL default '',
  `new` tinyint(1) NOT NULL default '0',
  authorid mediumint(8) unsigned NOT NULL default '0',
  author varchar(15) NOT NULL default '',
  note text NOT NULL,
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (id),
  KEY uid (uid,new,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_pic'
--

CREATE TABLE jchome_pic (
  picid mediumint(8) NOT NULL auto_increment,
  albumid mediumint(8) unsigned NOT NULL default '0',
  topicid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  postip varchar(20) NOT NULL default '',
  filename varchar(100) NOT NULL default '',
  title varchar(255) NOT NULL default '',
  `type` varchar(20) NOT NULL default '',
  size int(10) unsigned NOT NULL default '0',
  filepath varchar(60) NOT NULL default '',
  thumb tinyint(1) NOT NULL default '0',
  remote tinyint(1) NOT NULL default '0',
  hot mediumint(8) unsigned NOT NULL default '0',
  click_6 smallint(6) unsigned NOT NULL default '0',
  click_7 smallint(6) unsigned NOT NULL default '0',
  click_8 smallint(6) unsigned NOT NULL default '0',
  click_9 smallint(6) unsigned NOT NULL default '0',
  click_10 smallint(6) unsigned NOT NULL default '0',
  magicframe tinyint(6) NOT NULL default '0',
  PRIMARY KEY  (picid),
  KEY albumid (albumid,dateline),
  KEY topicid (topicid,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_picfield'
--

CREATE TABLE jchome_picfield (
  picid mediumint(8) unsigned NOT NULL default '0',
  hotuser text NOT NULL,
  PRIMARY KEY  (picid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_poke'
--

CREATE TABLE jchome_poke (
  uid mediumint(8) unsigned NOT NULL default '0',
  fromuid mediumint(8) unsigned NOT NULL default '0',
  fromusername varchar(15) NOT NULL default '',
  note varchar(255) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  iconid smallint(6) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid,fromuid),
  KEY uid (uid,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_poll'
--投票

CREATE TABLE jchome_poll (
  pid mediumint(8) unsigned NOT NULL auto_increment,
  topicid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0' comment '作者UID',
  username char(15) NOT NULL default ''  comment '作者名',
  subject char(80) NOT NULL default ''  comment '标题',
  voternum mediumint(8) unsigned NOT NULL default '0' comment '参与人数',
  replynum mediumint(8) unsigned NOT NULL default '0' comment '评论数',
  multiple tinyint(1) NOT NULL default '0',
  maxchoice tinyint(3) NOT NULL default '0',
  sex tinyint(1) NOT NULL default '0' comment '性别限制，1(男),2(女)',
  noreply tinyint(1) NOT NULL default '0'  comment '评论限制，0(全站用户可见),1(仅好友可评论)',
  credit mediumint(8) unsigned NOT NULL default '0',
  percredit mediumint(8) unsigned NOT NULL default '0'  comment '悬赏积分',
  expiration int(10) unsigned NOT NULL default '0'  comment '过期投票，1(未过期),2(已过期)',
  lastvote int(10) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0' comment '发布时间',
  hot mediumint(8) unsigned NOT NULL default '0' comment '热度',
  PRIMARY KEY  (pid),
  KEY uid (uid,dateline),
  KEY topicid (topicid,dateline),
  KEY voternum (voternum),
  KEY dateline (dateline),
  KEY lastvote (lastvote),
  KEY hot (hot),
  KEY percredit (percredit)
) ENGINE=MyISAM comment ='投票';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_pollfield'
--

CREATE TABLE jchome_pollfield (
  pid mediumint(8) unsigned NOT NULL default '0',
  notify tinyint(1) NOT NULL default '0',
  message text NOT NULL,
  summary text NOT NULL,
  `option` text NOT NULL,
  invite text NOT NULL,
  hotuser text NOT NULL,
  PRIMARY KEY  (pid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_polloption'
--

CREATE TABLE jchome_polloption (
  oid mediumint(8) unsigned NOT NULL auto_increment,
  pid mediumint(8) unsigned NOT NULL default '0',
  votenum mediumint(8) unsigned NOT NULL default '0',
  `option` varchar(100) NOT NULL default '',
  PRIMARY KEY (oid),
  KEY pid (pid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_polluser'
--

CREATE TABLE jchome_polluser (
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  pid mediumint(8) unsigned NOT NULL default '0',
  `option` text NOT NULL,
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY (uid, pid),
  KEY pid (pid, dateline),
  KEY uid (uid, dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_post'
--回帖

CREATE TABLE jchome_post (
  pid int(10) unsigned NOT NULL auto_increment,
  tagid mediumint(8) unsigned NOT NULL default '0' COMMENT '群组ID',
  tid mediumint(8) unsigned NOT NULL default '0' COMMENT '话题ID',
  uid mediumint(8) unsigned NOT NULL default '0' COMMENT '作者UID',
  username varchar(15) NOT NULL default '' COMMENT '作者名',
  ip varchar(20) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0' COMMENT '发布时间',
  message text NOT NULL COMMENT '内容',
  pic varchar(255) NOT NULL default '',
  isthread tinyint(1) NOT NULL default '0' COMMENT '是否主题帖,1(是)',
  hotuser text NOT NULL,
  PRIMARY KEY  (pid),
  KEY tid (tid,dateline)
) ENGINE=MyISAM COMMENT '回帖';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_profield'
--群组栏目

CREATE TABLE jchome_profield (
  fieldid smallint(6) unsigned NOT NULL auto_increment,
  title varchar(80) NOT NULL default '' comment '名称',
  note varchar(255) NOT NULL default '' comment '简单介绍',
  formtype varchar(20) NOT NULL default '0' comment '填写(表单)类型',
  inputnum smallint(3) unsigned NOT NULL default '0' comment '用户可加入群组最多个数.填写类型是多选类型或填写类型的才有',
  choice text NOT NULL comment '可选值。填写类型是选择类型的才有',
  mtagminnum smallint(6) unsigned NOT NULL default '0'  comment '群组讨论区人数下限。当群组的成员数达到该数目时，才允许成员在群组内发话题和回帖',
  manualmoderator tinyint(1) NOT NULL default '0'  comment '群组群主手工指定，1（手工），0（自动）。如果选择不手工指定，则系统会自动将第一次使用某个群组的用户作为群主。',
  manualmember tinyint(1) NOT NULL default '0' comment '群组成员可由群主控制，1（群主可控制），0（会员可自由加入）。群主可控制，则允许群主有权设置群组的会员加入方式，来控制加入群组的会员。',
  displayorder tinyint(3) unsigned NOT NULL default '0' comment '显示顺序',
  PRIMARY KEY  (fieldid)
) ENGINE=MyISAM COMMENT='群组栏目'; 


-- --------------------------------------------------------

--
-- 表的结构 'jchome_profilefield'
--

CREATE TABLE jchome_profilefield (
  fieldid smallint(6) unsigned NOT NULL auto_increment,
  title varchar(80) NOT NULL default '',
  note varchar(255) NOT NULL default '',
  formtype varchar(20) NOT NULL default '0',
  maxsize tinyint(3) unsigned NOT NULL default '0',
  required tinyint(1) NOT NULL default '0',
  invisible tinyint(1) NOT NULL default '0',
  allowsearch tinyint(1) NOT NULL default '0',
  choice text NOT NULL,
  displayorder tinyint(3) unsigned NOT NULL default '0',
  PRIMARY KEY  (fieldid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_report'
--

CREATE TABLE jchome_report (
  rid mediumint(8) unsigned NOT NULL auto_increment,
  id mediumint(8) unsigned NOT NULL default '0',
  idtype varchar(15) NOT NULL default '',
  `new` tinyint(1) NOT NULL default '0',
  num smallint(6) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  reason text NOT NULL,
  uids text NOT NULL,
  PRIMARY KEY  (rid),
  KEY id (id,idtype,num,dateline),
  KEY `new` (new,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_session',记住密码时保存session数据
--

CREATE TABLE jchome_session (
  uid mediumint(8) unsigned NOT NULL default '0',
  username char(15) NOT NULL default '',
  `password` char(32) NOT NULL default '',
  lastactivity int(10) unsigned NOT NULL default '0',
  ip int(10) unsigned NOT NULL default '0',
  magichidden tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (uid),
  KEY lastactivity (lastactivity),
  KEY ip (ip)
) ENGINE=MEMORY;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_share'
--分享,下面的`type`我改的

CREATE TABLE jchome_share (
  sid mediumint(8) unsigned NOT NULL auto_increment comment 'ID',
  topicid mediumint(8) unsigned NOT NULL default '0',
  `type` varchar(30) NOT NULL default '' comment '事件类型',
  uid mediumint(8) unsigned NOT NULL default '0' comment '作者UID',
  username varchar(15) NOT NULL default '' comment '作者名',
  dateline int(10) unsigned NOT NULL default '0'  comment '发布时间',
  title_template text NOT NULL,
  body_template text NOT NULL,
  body_data text NOT NULL,
  body_general text NOT NULL,
  image varchar(255) NOT NULL default '',
  image_link varchar(255) NOT NULL default '',
  hot mediumint(8) unsigned NOT NULL default '0' comment '热度',
  hotuser text NOT NULL,
  PRIMARY KEY  (sid),
  KEY uid (uid,dateline),
  KEY topicid (topicid,dateline),
  KEY hot (hot),
  KEY dateline (dateline)
) ENGINE=MyISAM comment='分享';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_show'
--

CREATE TABLE jchome_show (
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  credit int(10) unsigned NOT NULL default '0',
  note varchar(100) NOT NULL default '',
  PRIMARY KEY  (uid),
  KEY credit (credit)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_space'
--

CREATE TABLE jchome_space (
  uid mediumint(8) unsigned NOT NULL default '0',
  groupid smallint(6) unsigned NOT NULL default '0',
  credit int(10) NOT NULL default '0',
  experience int(10) NOT NULL default '0',
  username char(15) NOT NULL default '',
  `name` char(20) NOT NULL default '',
  namestatus tinyint(1) NOT NULL default '0',
  videostatus tinyint(1) NOT NULL default '0',
  domain char(15) NOT NULL default '',
  friendnum int(10) unsigned NOT NULL default '0',
  viewnum int(10) unsigned NOT NULL default '0',
  notenum int(10) unsigned NOT NULL default '0',
  addfriendnum smallint(6) unsigned NOT NULL default '0',
  mtaginvitenum smallint(6) unsigned NOT NULL default '0',
  eventinvitenum smallint(6) unsigned NOT NULL default '0',
  myinvitenum smallint(6) unsigned NOT NULL default '0',
  pokenum smallint(6) unsigned NOT NULL default '0',
  doingnum smallint(6) unsigned NOT NULL default '0',
  blognum smallint(6) unsigned NOT NULL default '0',
  albumnum smallint(6) unsigned NOT NULL default '0',
  threadnum smallint(6) unsigned NOT NULL default '0',
  pollnum smallint(6) unsigned NOT NULL default '0',
  eventnum smallint(6) unsigned NOT NULL default '0',
  sharenum smallint(6) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  updatetime int(10) unsigned NOT NULL default '0',
  lastsearch int(10) unsigned NOT NULL default '0',
  lastpost int(10) unsigned NOT NULL default '0',
  lastlogin int(10) unsigned NOT NULL default '0',
  lastsend int(10) unsigned NOT NULL default '0',
  attachsize int(10) unsigned NOT NULL default '0',
  addsize int(10) unsigned NOT NULL default '0',
  addfriend smallint(6) unsigned NOT NULL default '0',
  flag tinyint(1) NOT NULL default '0',
  newpm smallint(6) unsigned NOT NULL default '0',
  avatar tinyint(1) NOT NULL default '0',
  regip char(15) NOT NULL default '',
  ip int(10) unsigned NOT NULL default '0',
  mood smallint(6) unsigned NOT NULL default '0',
  advgiftcount smallint(6) NOT NULL DEFAULT '0',
  giftnum smallint(6) NOT NULL DEFAULT '0',
  showgiftlink tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY  (uid),
  KEY username (username),
  KEY domain (domain),
  KEY ip (ip),
  KEY updatetime (updatetime),
  KEY mood (mood)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_spacefield'
--

CREATE TABLE jchome_spacefield (
  uid mediumint(8) unsigned NOT NULL default '0',
  sex tinyint(1) NOT NULL default '0',
  email varchar(100) NOT NULL default '',
  newemail varchar(100) NOT NULL default '',
  emailcheck tinyint(1) NOT NULL default '0',
  mobile varchar(40) NOT NULL default '',
  qq varchar(20) NOT NULL default '',
  msn varchar(80) NOT NULL default '',
  msnrobot varchar(15) NOT NULL default '',
  msncstatus tinyint(1) NOT NULL default '0',
  videopic varchar(32) NOT NULL default '',
  birthyear smallint(6) unsigned NOT NULL default '0',
  birthmonth tinyint(3) unsigned NOT NULL default '0',
  birthday tinyint(3) unsigned NOT NULL default '0',
  blood varchar(5) NOT NULL default '',
  marry tinyint(1) NOT NULL default '0',
  birthprovince varchar(20) NOT NULL default '',
  birthcity varchar(20) NOT NULL default '',
  resideprovince varchar(20) NOT NULL default '',
  residecity varchar(20) NOT NULL default '',
  note text NOT NULL,
  spacenote text NOT NULL,
  authstr varchar(20) NOT NULL default '',
  theme varchar(20) NOT NULL default '',
  nocss tinyint(1) NOT NULL default '0',
  menunum smallint(6) unsigned NOT NULL default '0',
  css text NOT NULL,
  privacy text NOT NULL,
  friend mediumtext NOT NULL,
  feedfriend mediumtext NOT NULL,
  sendmail text NOT NULL,
  magicstar tinyint(1) NOT NULL default '0',
  magicexpire int(10) unsigned NOT NULL default '0',
  timeoffset varchar(20) NOT NULL default '',
  PRIMARY KEY  (uid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_spaceinfo'
--

CREATE TABLE jchome_spaceinfo (
  infoid mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  type varchar(20) NOT NULL default '',
  subtype varchar(20) NOT NULL default '',
  title text NOT NULL,
  subtitle varchar(255) NOT NULL default '',
  friend tinyint(1) NOT NULL default '0',
  startyear smallint(6) unsigned NOT NULL default '0',
  endyear smallint(6) unsigned NOT NULL default '0',
  startmonth smallint(6) unsigned NOT NULL default '0',
  endmonth smallint(6) unsigned NOT NULL default '0',
  PRIMARY KEY  (infoid),
  KEY uid (uid)
) ENGINE=MyISAM;;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_spacelog'
--

CREATE TABLE jchome_spacelog (
  uid mediumint(8) unsigned NOT NULL default '0',
  username char(15) NOT NULL default '',
  opuid mediumint(8) unsigned NOT NULL default '0',
  opusername char(15) NOT NULL default '',
  flag tinyint(1) NOT NULL default '0',
  expiration int(10) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid),
  KEY flag (flag)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_stat'
--

CREATE TABLE jchome_stat (
  daytime int(10) unsigned NOT NULL default '0',
  login smallint(6) unsigned NOT NULL default '0',
  register smallint(6) unsigned NOT NULL default '0',
  invite smallint(6) unsigned NOT NULL default '0',
  appinvite smallint(6) unsigned NOT NULL default '0',
  doing smallint(6) unsigned NOT NULL default '0',
  blog smallint(6) unsigned NOT NULL default '0',
  pic smallint(6) unsigned NOT NULL default '0',
  poll smallint(6) unsigned NOT NULL default '0',
  event smallint(6) unsigned NOT NULL default '0',
  `share` smallint(6) unsigned NOT NULL default '0',
  thread smallint(6) unsigned NOT NULL default '0',
  docomment smallint(6) unsigned NOT NULL default '0',
  blogcomment smallint(6) unsigned NOT NULL default '0',
  piccomment smallint(6) unsigned NOT NULL default '0',
  pollcomment smallint(6) unsigned NOT NULL default '0',
  pollvote smallint(6) unsigned NOT NULL default '0',
  eventcomment smallint(6) unsigned NOT NULL default '0',
  eventjoin smallint(6) unsigned NOT NULL default '0',
  sharecomment smallint(6) unsigned NOT NULL default '0',
  post smallint(6) unsigned NOT NULL default '0',
  wall smallint(6) unsigned NOT NULL default '0',
  poke smallint(6) unsigned NOT NULL default '0',
  click smallint(6) unsigned NOT NULL default '0',
  PRIMARY KEY  (daytime)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_statuser'
--

CREATE TABLE jchome_statuser (
  uid mediumint(8) unsigned NOT NULL default '0',
  daytime int(10) unsigned NOT NULL default '0',
  `type` char(20) NOT NULL default '',
  KEY uid (uid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_tag'
--标签

CREATE TABLE jchome_tag (
  tagid mediumint(8) unsigned NOT NULL auto_increment,
  tagname char(30) NOT NULL default '' comment '标签名',
  uid mediumint(8) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0' comment '发布时间',
  blognum smallint(6) unsigned NOT NULL default '0' comment '日志数',
  `close` tinyint(1) NOT NULL default '0' comment '是否锁定,1(锁定)',
  PRIMARY KEY  (tagid),
  KEY tagname (tagname)
) ENGINE=MyISAM COMMENT ='标签';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_tagblog'
--

CREATE TABLE jchome_tagblog (
  tagid mediumint(8) unsigned NOT NULL default '0',
  blogid mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (tagid,blogid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_tagspace'
--

CREATE TABLE jchome_tagspace (
  tagid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  username char(15) NOT NULL default '',
  grade smallint(6) NOT NULL default '0',
  PRIMARY KEY  (tagid,uid),
  KEY grade (tagid,grade),
  KEY uid (uid,grade)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_task'
--

CREATE TABLE jchome_task (
  taskid smallint(6) unsigned NOT NULL auto_increment,
  available tinyint(1) NOT NULL default '0',
  `name` varchar(50) NOT NULL default '',
  note text NOT NULL,
  num mediumint(8) unsigned NOT NULL default '0',
  maxnum mediumint(8) unsigned NOT NULL default '0',
  image varchar(150) NOT NULL default '',
  filename varchar(50) NOT NULL default '',
  starttime int(10) unsigned NOT NULL default '0',
  endtime int(10) unsigned NOT NULL default '0',
  nexttime int(10) unsigned NOT NULL default '0',
  nexttype varchar(20) NOT NULL default '',
  credit smallint(6) NOT NULL default '0',
  displayorder smallint(6) unsigned NOT NULL default 0,
  PRIMARY KEY  (taskid),
  KEY displayorder (displayorder)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_thread'
--话题

CREATE TABLE jchome_thread (
  tid mediumint(8) unsigned NOT NULL auto_increment COMMENT '指定话题ID',
  topicid mediumint(8) unsigned NOT NULL default '0',
  tagid mediumint(8) unsigned NOT NULL default '0' COMMENT '群组ID',
  eventid mediumint(8) unsigned NOT NULL default '0',
  `subject` char(80) NOT NULL default '' COMMENT '标题',
  magiccolor tinyint(6) unsigned NOT NULL default '0',
  magicegg tinyint(6) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0' COMMENT '作者ID',
  username char(15) NOT NULL default '' COMMENT '作者名',
  dateline int(10) unsigned NOT NULL default '0',
  viewnum mediumint(8) unsigned NOT NULL default '0' COMMENT '查看数',
  replynum mediumint(8) unsigned NOT NULL default '0' COMMENT '回复数',
  lastpost int(10) unsigned NOT NULL default '0',
  lastauthor char(15) NOT NULL default '',
  lastauthorid mediumint(8) unsigned NOT NULL default '0',
  displayorder tinyint(1) unsigned NOT NULL default '0',
  digest tinyint(1) NOT NULL default '0'  COMMENT '是否精华，1(是),0(否)',
  hot mediumint(8) unsigned NOT NULL default '0'  COMMENT '热度',
  click_11 smallint(6) unsigned NOT NULL default '0',
  click_12 smallint(6) unsigned NOT NULL default '0',
  click_13 smallint(6) unsigned NOT NULL default '0',
  click_14 smallint(6) unsigned NOT NULL default '0',
  click_15 smallint(6) unsigned NOT NULL default '0',
  PRIMARY KEY  (tid),
  KEY tagid (tagid,displayorder,lastpost),
  KEY uid (uid,lastpost),
  KEY lastpost (lastpost),
  KEY topicid (topicid,dateline),
  KEY eventid (eventid,lastpost)
) ENGINE=MyISAM COMMENT ='话题';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_topic'
--

CREATE TABLE jchome_topic (
  topicid mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  `subject` varchar(80) NOT NULL default '',
  message mediumtext NOT NULL,
  jointype varchar(255) NOT NULL default '',
  joingid varchar(255) NOT NULL default '',
  pic varchar(100) NOT NULL default '',
  thumb tinyint(1) NOT NULL default '0',
  remote tinyint(1) NOT NULL default '0',
  joinnum mediumint(8) unsigned NOT NULL default '0',
  lastpost int(10) unsigned NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  endtime int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (topicid),
  KEY lastpost (lastpost),
  KEY joinnum (joinnum)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_topicuser'
--

CREATE TABLE jchome_topicuser (
  id mediumint(8) unsigned NOT NULL auto_increment,
  uid mediumint(8) unsigned NOT NULL default '0',
  topicid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (id),
  KEY uid (uid,dateline),
  KEY topicid (topicid,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_userapp'
--

CREATE TABLE jchome_userapp (
  uid mediumint(8) unsigned NOT NULL default '0',
  appid mediumint(8) unsigned NOT NULL default '0',
  appname varchar(60) NOT NULL default '',
  privacy tinyint(1) NOT NULL default '0',
  allowsidenav tinyint(1) NOT NULL default '0',
  allowfeed tinyint(1) NOT NULL default '0',
  allowprofilelink tinyint(1) NOT NULL default '0',
  narrow tinyint(1) NOT NULL default '0',
  menuorder smallint(6) NOT NULL default '0',
  displayorder smallint(6) NOT NULL default '0',
  KEY uid (uid,appid),
  KEY menuorder (uid,menuorder),
  KEY displayorder (uid,displayorder)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_userappfield'
--

CREATE TABLE jchome_userappfield (
  uid mediumint(8) unsigned NOT NULL default '0',
  appid mediumint(8) unsigned NOT NULL default '0',
  profilelink text NOT NULL,
  myml text NOT NULL,
  KEY uid (uid,appid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_userevent'
--

CREATE TABLE jchome_userevent (
  eventid mediumint(8) unsigned NOT NULL default '0',
  uid mediumint(8) unsigned NOT NULL default '0',
  username varchar(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  status tinyint(4) NOT NULL default '0',
  fellow mediumint(8) unsigned NOT NULL default '0',
  template varchar(255) NOT NULL default '',
  PRIMARY KEY  (eventid,uid),
  KEY uid (uid,dateline),
  KEY eventid (eventid,status,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_usergroup'
--用户组,字段中manage开头的是管理权限，系统组和特殊组可见。

CREATE TABLE jchome_usergroup (
  gid smallint(6) unsigned NOT NULL auto_increment comment '组的id',
  grouptitle varchar(20) NOT NULL default '' comment '组的名称',
  system tinyint(1) NOT NULL default '0'  comment '标示组别，-1(系统用户组),1(特殊用户组),0(普通用户组)',
  banvisit tinyint(1) NOT NULL default '0'  comment '1(禁止访问),0(允许访问)',
  explower int(10) NOT NULL default '0' comment '组的经验值下限,受限组的是系统默认最低分 -999999999，不能修改,特殊组和系统组不受经验值限制',
  maxfriendnum smallint(6) unsigned NOT NULL default '0' comment '最多好友数',
  maxattachsize int(10) unsigned NOT NULL default '0' comment '上传空间大小',
  allowhtml tinyint(1) NOT NULL default '0' comment '日志全HTML标签支持,谨慎允许，支持所有html标签可能会造成javascript脚本引起的不安全因素,1(是),0(否)',
  allowcomment tinyint(1) NOT NULL default '0' comment '发表留言/评论,1(是),0(否)',
  searchinterval smallint(6) unsigned NOT NULL default '0' comment '两次搜索操作间隔',
  searchignore tinyint(1) NOT NULL default '0' comment '是否免费搜索,1(是),0(否)',
  postinterval smallint(6) unsigned NOT NULL default '0' comment '两次发布操作间隔',
  spamignore tinyint(1) NOT NULL default '0' comment '防灌水限制,1(不限制),0（受限制）',
  videophotoignore tinyint(1) NOT NULL default '0' comment '视频认证限制,1(不限制),0（受限制）',
  allowblog tinyint(1) NOT NULL default '0' comment '发表日志,1(是),0(否)',
  allowdoing tinyint(1) NOT NULL default '0' comment '发表记录,1(是),0(否)',
  allowupload tinyint(1) NOT NULL default '0' comment '上传图片,1(是),0(否)',
  allowshare tinyint(1) NOT NULL default '0' comment '发布分享,1(是),0(否)',
  allowmtag tinyint(1) NOT NULL default '0' comment '创建新群组,1(是),0(否)',
  allowthread tinyint(1) NOT NULL default '0' comment '发表群组话题,1(是),0(否)',
  allowpost tinyint(1) NOT NULL default '0' comment '发表群组回帖,1(是),0(否)',
  allowcss tinyint(1) NOT NULL default '0' comment '允许自定义CSS,1(是),0(否)',
  allowpoke tinyint(1) NOT NULL default '0' comment '允许打招呼,1(是),0(否)',
  allowfriend tinyint(1) NOT NULL default '0' comment '允许加好友,1(是),0(否)',
  allowpoll tinyint(1) NOT NULL default '0' comment '发起投票,1(是),0(否)',
  allowclick tinyint(1) NOT NULL default '0' comment '允许表态,1(是),0(否)',
  allowevent tinyint(1) NOT NULL default '0' comment '发布活动,1(是),0(否)',
  allowmagic tinyint(1) NOT NULL default '0' comment '允许使用道具,1(是),0(否)',
  allowpm tinyint(1) NOT NULL default '0' comment '允许发短消息,1(是),0(否)',
  allowviewvideopic tinyint(1) NOT NULL default '0' comment '允许查看视频认证,1(是),0(否)',
  allowmyop tinyint(1) NOT NULL default '0' comment '允许使用应用,1(是),0(否)',
  allowtopic tinyint(1) NOT NULL default '0' comment '添加新的热闹,1(是),0(否)',
  allowstat tinyint(1) NOT NULL default '0' comment '允许查看趋势统计,1(是),0(否)',
  magicdiscount tinyint(1) NOT NULL default '0' comment '购买道具折扣',
  verifyevent tinyint(1) NOT NULL default '0'  comment '发表的活动需要审核,1(是),0(否)',
  edittrail tinyint(1) NOT NULL default '0' comment '编辑话题附加编辑记录,1(是),0(否)',
  domainlength smallint(6) unsigned NOT NULL default '0' comment '二级域名最短长度,0为禁止使用二级域名，在站点开启二级域名时有效',
  closeignore tinyint(1) NOT NULL default '0' comment '站点关闭和IP屏蔽,1(不受站点关闭和IP屏蔽限制),0(受限制)',
  seccode tinyint(1) NOT NULL default '0' comment '发布操作需填验证码,1(要),0(不需要)',
  color varchar(10) NOT NULL default '' comment '组名称的颜色',
  icon varchar(100) NOT NULL default ''  comment '组的用户身份识别图标',
  manageconfig tinyint(1) NOT NULL default '0' comment '管理员身份,1(拥有管理员身份),0(禁止)',
  managenetwork tinyint(1) NOT NULL default '0' comment '随便看看,1(可管理),0(禁止)',
  manageprofilefield tinyint(1) NOT NULL default '0' comment '用户栏目,1(可管理),0(禁止)',
  manageprofield tinyint(1) NOT NULL default '0' comment '群组栏目,1(可管理),0(禁止)',
  manageusergroup tinyint(1) NOT NULL default '0' comment '用户组,1(可管理),0(禁止)',
  managefeed tinyint(1) NOT NULL default '0' comment '动态(feed),1(可管理),0(禁止)',
  manageshare tinyint(1) NOT NULL default '0' comment '分享,1(可管理),0(禁止)',
  managedoing tinyint(1) NOT NULL default '0' comment '记录,1(可管理),0(禁止)',
  manageblog tinyint(1) NOT NULL default '0' comment '日志,1(可管理),0(禁止)',
  managetag tinyint(1) NOT NULL default '0' comment '标签,1(可管理),0(禁止)',
  managetagtpl tinyint(1) NOT NULL default '0',
  managealbum tinyint(1) NOT NULL default '0' comment '相册,1(可管理),0(禁止)',
  managecomment tinyint(1) NOT NULL default '0' comment '评论,1(可管理),0(禁止)',
  managemtag tinyint(1) NOT NULL default '0' comment '群组,1(可管理),0(禁止)',
  managethread tinyint(1) NOT NULL default '0' comment '话题,1(可管理),0(禁止)',
  manageevent tinyint(1) NOT NULL default '0' comment '活动,1(可管理),0(禁止)',
  manageeventclass tinyint(1) NOT NULL default '0' comment '活动分类,1(可管理),0(禁止)',
  managecensor tinyint(1) NOT NULL default '0'  comment '词语屏蔽,1(可管理),0(禁止)',
  managead tinyint(1) NOT NULL default '0' comment '广告设置,1(可管理),0(禁止)',
  managesitefeed tinyint(1) NOT NULL default '0',
  managebackup tinyint(1) NOT NULL default '0' comment '数据备份,1(可管理),0(禁止)',
  manageblock tinyint(1) NOT NULL default '0' comment '数据调用,1(可管理),0(禁止)',
  managetemplate tinyint(1) NOT NULL default '0' comment '模板编辑,1(可管理),0(禁止)',
  managestat tinyint(1) NOT NULL default '0'  comment '统计更新,1(可管理),0(禁止)',
  managecache tinyint(1) NOT NULL default '0' comment '缓存更新,1(可管理),0(禁止)',
  managecredit tinyint(1) NOT NULL default '0' comment '积分规则,1(可管理),0(禁止)',
  managecron tinyint(1) NOT NULL default '0' comment '计划任务,1(可管理),0(禁止)',
  managename tinyint(1) NOT NULL default '0'  comment '实名认证,1(可管理),0(禁止)',
  manageapp tinyint(1) NOT NULL default '0' comment '多产品/应用,1(可管理),0(禁止)',
  managetask tinyint(1) NOT NULL default '0' comment '有奖任务,1(可管理),0(禁止)',
  managereport tinyint(1) NOT NULL default '0' comment '举报管理,1(可管理),0(禁止)',
  managepoll tinyint(1) NOT NULL default '0' comment '投票,1(可管理),0(禁止)',
  manageclick tinyint(1) NOT NULL default '0' comment '表态动作设置,1(可管理),0(禁止)',
  managemagic tinyint(1) NOT NULL default '0' comment '道具设置,1(可管理),0(禁止)',
  managemagiclog tinyint(1) NOT NULL default '0' comment '道具记录,1(可管理),0(禁止)',
  managebatch tinyint(1) NOT NULL default '0' comment '批量管理操作,1(可管理),0(禁止)',
  managedelspace tinyint(1) NOT NULL default '0' comment '删除用户,1(可管理),0(禁止)',
  managetopic tinyint(1) NOT NULL default '0' comment '热闹管理,1(可管理),0(禁止)',
  manageip tinyint(1) NOT NULL default '0' comment 'IP设,1(可管理),0(禁止)',
  managehotuser tinyint(1) NOT NULL default '0' comment '推荐成员设置,1(可管理),0(禁止)',
  managedefaultuser tinyint(1) NOT NULL default '0' comment '默认好友设置,1(可管理),0(禁止)',
  managespacegroup tinyint(1) NOT NULL default '0' comment '用户保护信息,1(可管理),0(禁止)',
  managespaceinfo tinyint(1) NOT NULL default '0' comment '用户基本信息,1(可管理),0(禁止)',
  managespacecredit tinyint(1) NOT NULL default '0' comment '用户积分,1(可管理),0(禁止)',
  managespacenote tinyint(1) NOT NULL default '0' comment '向用户发通知,1(可管理),0(禁止)',
  managevideophoto tinyint(1) NOT NULL default '0' comment '视频认证,1(可管理),0(禁止)',
  managelog tinyint(1) NOT NULL default '0' comment '管理记录,1(可管理),0(禁止)',
  magicaward text NOT NULL comment '升级奖励道具,字段类型为text',
  allowgift tinyint(1) NOT NULL DEFAULT '0' comment '允许发送礼物,1(是),0(否)',
  managegift tinyint(1) NOT NULL DEFAULT '0' comment '礼物,1(可管理),0(禁止)',
  PRIMARY KEY  (gid)
) ENGINE=MyISAM COMMENT='用户组,字段中manage开头的是管理权限，系统组和特殊组可见' ;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_userlog'
--

CREATE TABLE jchome_userlog (
  uid mediumint(8) unsigned NOT NULL default '0',
  action char(10) NOT NULL default '',
  `type` tinyint(1) NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_usermagic'
--道具持有记录

CREATE TABLE jchome_usermagic (
  uid mediumint(8) unsigned NOT NULL default '0',
  username char(15) NOT NULL default '' COMMENT '用户',
  mid varchar(15) NOT NULL default '' COMMENT '道具id',
  count smallint(6) unsigned NOT NULL default '0' COMMENT '数量',
  PRIMARY KEY  (uid,mid)
) ENGINE=MyISAM COMMENT='道具持有记录';

-- --------------------------------------------------------

--
-- 表的结构 'jchome_usertask'
--

CREATE TABLE jchome_usertask (
  uid mediumint(8) unsigned NOT NULL,
  username char(15) NOT NULL default '',
  taskid smallint(6) unsigned NOT NULL default '0',
  credit smallint(6) NOT NULL default '0',
  dateline int(10) unsigned NOT NULL default '0',
  isignore tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (uid,taskid),
  KEY isignore (isignore,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_visitor'
--

CREATE TABLE jchome_visitor (
  uid mediumint(8) unsigned NOT NULL default '0',
  vuid mediumint(8) unsigned NOT NULL default '0',
  vusername char(15) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (uid,vuid),
  KEY dateline (uid,dateline)
) ENGINE=MyISAM;

-- --------------------------------------------------------

--
-- 表的结构 'jchome_pms'
--
CREATE TABLE jchome_pms (
  pmid int(10) unsigned NOT NULL auto_increment,
  msgfrom varchar(15) NOT NULL default '',
  msgfromid mediumint(8) unsigned NOT NULL default '0',
  msgtoid mediumint(8) unsigned NOT NULL default '0',
  folder enum('inbox','outbox') NOT NULL default 'inbox',
  new tinyint(1) NOT NULL default '0',
  subject varchar(75) NOT NULL default '',
  dateline int(10) unsigned NOT NULL default '0',
  message text NOT NULL,
  delstatus tinyint(1) unsigned NOT NULL default '0',
  related int(10) unsigned NOT NULL default '0',
  fromappid SMALLINT(6) UNSIGNED NOT NULL DEFAULT '0',
  PRIMARY KEY(pmid),
  KEY msgtoid(msgtoid,folder,dateline),
  KEY msgfromid(msgfromid,folder,dateline),
  KEY related (related),
  KEY getnum (msgtoid,folder,delstatus)
) ENGINE=MyISAM;

-- --------------------------------------------------------
--
-- 表的结构 'jchome_newpm'
--
CREATE TABLE jchome_newpm (
  uid mediumint(8) unsigned NOT NULL,
  PRIMARY KEY  (uid)
) ENGINE=MyISAM;

-- --------------------------------------------------------
--
-- 表的结构 'jchome_gift'
--
CREATE TABLE jchome_gift (
  giftid int(4) NOT NULL AUTO_INCREMENT,
  giftname varchar(20) NOT NULL,
  tips varchar(20) NOT NULL,
  price int(4) NOT NULL,
  buycount int(4) NOT NULL,
  icon text NOT NULL,
  addtime int(10) NOT NULL,
  typeid varchar(20) NOT NULL,
  PRIMARY KEY (giftid)
) ENGINE=MyISAM;

-- --------------------------------------------------------
--
-- 表的结构 'jchome_giftreceived'
--
CREATE TABLE jchome_giftreceived (
  `grid` int(4) NOT NULL AUTO_INCREMENT,
  `senderid` mediumint(8) unsigned NOT NULL default '0',
  `sender` varchar(15) NOT NULL,
  `receiverid` mediumint(8) unsigned NOT NULL default '0',
  `receiver` varchar(15) NOT NULL,
  `giftid` int(4) NOT NULL,
  `quiet` tinyint(1) NOT NULL,
  `anonymous` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `timed` tinyint(1) NOT NULL,
  `fee` tinyint(1) NOT NULL,
  `receipttime` int(10) NOT NULL,
  PRIMARY KEY (`grid`)
) ENGINE=MyISAM;

-- --------------------------------------------------------
--
-- 表的结构 'jchome_giftsent'
--
CREATE TABLE jchome_giftsent (
  `gsid` int(4) NOT NULL AUTO_INCREMENT,
  `senderid` mediumint(8) unsigned NOT NULL default '0',
  `sender` varchar(15) NOT NULL,
  `receiverid` mediumint(8) unsigned NOT NULL default '0',
  `receiver` varchar(15) NOT NULL,
  `giftid` int(4) NOT NULL,
  `quiet` tinyint(1) NOT NULL DEFAULT '0',
  `anonymous` tinyint(1) NOT NULL DEFAULT '0',
  `timed` tinyint(1) NOT NULL DEFAULT '0',
  `fee` tinyint(1) NOT NULL DEFAULT '0',
  `sendtime` int(10) NOT NULL,
  PRIMARY KEY (`gsid`)
) ENGINE=MyISAM;

-- --------------------------------------------------------
--
-- 表的结构 'jchome_gifttype'
--
CREATE TABLE jchome_gifttype (
  `typeid` varchar(20) NOT NULL,
  `typename` varchar(20) NOT NULL,
  `fee` tinyint(1) NOT NULL,
  `order` tinyint(2) NOT NULL,
  PRIMARY KEY (`typeid`)
) ENGINE=MyISAM;