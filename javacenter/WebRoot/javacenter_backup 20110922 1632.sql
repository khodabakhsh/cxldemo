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
 (1,'127.0.0.1',1316680244,-1);
/*!40000 ALTER TABLE `jchome_adminsession` ENABLE KEYS */;


--
-- Definition of table `jchome_album`
--

DROP TABLE IF EXISTS `jchome_album`;
CREATE TABLE `jchome_album` (
  `albumid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `albumname` varchar(50) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `updatetime` int(10) unsigned NOT NULL DEFAULT '0',
  `picnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pic` varchar(60) NOT NULL DEFAULT '',
  `picflag` tinyint(1) NOT NULL DEFAULT '0',
  `friend` tinyint(1) NOT NULL DEFAULT '0',
  `password` varchar(10) NOT NULL DEFAULT '',
  `target_ids` text NOT NULL,
  PRIMARY KEY (`albumid`),
  KEY `uid` (`uid`,`updatetime`),
  KEY `updatetime` (`updatetime`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_album`
--

/*!40000 ALTER TABLE `jchome_album` DISABLE KEYS */;
INSERT INTO `jchome_album` (`albumid`,`albumname`,`uid`,`username`,`dateline`,`updatetime`,`picnum`,`pic`,`picflag`,`friend`,`password`,`target_ids`) VALUES 
 (1,'想吃',2,'cxl',1316605340,1316605341,2,'201109/21/2_1316605341EBDF.jpg.thumb.jpg',1,0,'','');
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
  `bid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `blockname` varchar(40) NOT NULL DEFAULT '',
  `blocksql` text NOT NULL,
  `cachename` varchar(30) NOT NULL DEFAULT '',
  `cachetime` smallint(6) unsigned NOT NULL DEFAULT '0',
  `startnum` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `num` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `perpage` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `htmlcode` text NOT NULL,
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
  `subject` char(80) NOT NULL DEFAULT '',
  `classid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `viewnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `replynum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `pic` char(120) NOT NULL DEFAULT '',
  `picflag` tinyint(1) NOT NULL DEFAULT '0',
  `noreply` tinyint(1) NOT NULL DEFAULT '0',
  `friend` tinyint(1) NOT NULL DEFAULT '0',
  `password` char(10) NOT NULL DEFAULT '',
  `click_1` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_2` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_3` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_4` smallint(6) unsigned NOT NULL DEFAULT '0',
  `click_5` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`blogid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `topicid` (`topicid`,`dateline`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_blog`
--

/*!40000 ALTER TABLE `jchome_blog` DISABLE KEYS */;
INSERT INTO `jchome_blog` (`blogid`,`topicid`,`uid`,`username`,`subject`,`classid`,`viewnum`,`replynum`,`hot`,`dateline`,`pic`,`picflag`,`noreply`,`friend`,`password`,`click_1`,`click_2`,`click_3`,`click_4`,`click_5`) VALUES 
 (1,0,1,'admin','代码列表',1,3,2,1,1316584572,'',0,0,0,'',0,0,0,1,0),
 (2,0,1,'admin','压缩工具类',1,1,1,0,1316584674,'',0,0,0,'',0,0,0,0,0),
 (3,0,2,'cxl','gzip过滤器',2,0,0,0,1316607783,'',0,0,0,'',0,0,0,0,0),
 (4,0,1,'admin','缓存',3,0,0,0,1316676632,'',0,0,0,'',0,0,0,0,0);
/*!40000 ALTER TABLE `jchome_blog` ENABLE KEYS */;


--
-- Definition of table `jchome_blogfield`
--

DROP TABLE IF EXISTS `jchome_blogfield`;
CREATE TABLE `jchome_blogfield` (
  `blogid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tag` varchar(255) NOT NULL DEFAULT '',
  `message` mediumtext NOT NULL,
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
INSERT INTO `jchome_blogfield` (`blogid`,`uid`,`tag`,`message`,`postip`,`related`,`relatedtime`,`target_ids`,`hotuser`,`magiccolor`,`magicpaper`,`magiccall`) VALUES 
 (1,1,'a:1:{i:1;s:3:\"sns\";}','<br>package cn.jcenterhome.util;<br>import java.util.HashMap;<br>import java.util.Map;<br>import java.util.regex.Matcher;<br>import java.util.regex.Pattern;<br>public class JcHomeCode {<br>&nbsp;&nbsp;&nbsp; private Map&lt;String, Object&gt; jcHomeCode = null;<br>&nbsp;&nbsp;&nbsp; public JcHomeCode() {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; jcHomeCode = new HashMap&lt;String, Object&gt;();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; jcHomeCode.put(\"pcodecount\", -1);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; jcHomeCode.put(\"codecount\", 0);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; jcHomeCode.put(\"codehtml\", null);<br>&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; private String codeDisp(String code) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; jcHomeCode.put(\"pcodecount\", (Integer) jcHomeCode.get(\"pcodecount\") + 1);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; code = code.replaceAll(\"(?is)^[\\n\\r]*(.+?)[\\n\\r]*$\", \"$1\").replace(\"\\\\\\\"\", \"\\\"\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; Map temp = (Map) jcHomeCode.get(\"codehtml\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (temp == null) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; temp = new HashMap();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; temp.put(jcHomeCode.get(\"pcodecount\"), tplCodeDisp(code));<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; jcHomeCode.put(\"codehtml\", temp);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; jcHomeCode.put(\"codecount\", (Integer) jcHomeCode.get(\"codecount\") + 1);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return \"[\\tJCHOME_CODE_\" + jcHomeCode.get(\"pcodecount\") + \"\\t]\";<br>&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; public String complie(String message) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (message == null || message.length() == 0) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return message;<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; Matcher matcher = null;<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (message.indexOf(\"[/code]\") &gt;= 0) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher = Pattern.compile(\"(?is)\\\\s*\\\\[code\\\\](.+?)\\\\[\\\\/code\\\\]\\\\s*\").matcher(message);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; StringBuffer b = new StringBuffer();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; while (matcher.find()) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendReplacement(b, codeDisp(matcher.group(1)));<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendTail(b);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = b.toString();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (message.indexOf(\"[/url]\") &gt;= 0) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher = Pattern<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; .compile(<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"(?is)\\\\[url(=((https?|ftp|gopher|news|telnet|rtsp|mms|callto|bctp|ed2k|thunder|synacast){1}:\\\\/\\\\/|www\\\\.)([^\\\\[\\\\\\\"\']+?))?\\\\](.+?)\\\\[\\\\/url\\\\]\")<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; .matcher(message);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; StringBuffer b = new StringBuffer();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; while (matcher.find()) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendReplacement(b, parseURL(matcher.group(1), matcher.group(5)));<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendTail(b);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = b.toString();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (message.indexOf(\"[/email]\") &gt;= 0) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher = Pattern<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; .compile(<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"(?is)\\\\[email(=([a-z0-9\\\\-_.+]+)@([a-z0-9\\\\-_]+[.][a-z0-9\\\\-_.]+))?\\\\](.+?)\\\\[\\\\/email\\\\]\")<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; .matcher(message);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; StringBuffer b = new StringBuffer();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; while (matcher.find()) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendReplacement(b, parseEmail(matcher.group(1), matcher.group(4)));<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendTail(b);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = b.toString();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; String[] search = new String[] {\"[/color]\", \"[/size]\", \"[/font]\", \"[/align]\", \"[b]\", \"[/b]\", \"[i]\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"[/i]\", \"[u]\", \"[/u]\", \"[list]\", \"[list=1]\", \"[list=a]\", \"[list=A]\", \"[*]\", \"[/list]\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"[indent]\", \"[/indent]\", \"[/float]\"};<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; String[] replace = new String[] {\"&lt;/font&gt;\", \"&lt;/font&gt;\", \"&lt;/font&gt;\", \"&lt;/p&gt;\", \"&lt;strong&gt;\", \"&lt;/strong&gt;\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"&lt;i&gt;\", \"&lt;/i&gt;\", \"&lt;u&gt;\", \"&lt;/u&gt;\", \"&lt;ul&gt;\", \"&lt;ul type=\\\"1\\\"&gt;\", \"&lt;ul type=\\\"a\\\"&gt;\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"&lt;ul type=\\\"A\\\"&gt;\", \"&lt;li&gt;\", \"&lt;/ul&gt;\", \"&lt;blockquote&gt;\", \"&lt;/blockquote&gt;\", \"&lt;/span&gt;\"};<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; for (int i = 0; i &lt; replace.length; i++) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = message.replace(search[i], replace[i]);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; String[] search_exp = new String[] {\"\\\\[color=([#\\\\w]+?)\\\\](?i)\", \"\\\\[size=(\\\\d+?)\\\\](?i)\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"\\\\[size=(\\\\d+(\\\\.\\\\d+)?(px|pt|in|cm|mm|pc|em|ex|%)+?)\\\\](?i)\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"\\\\[font=([^\\\\[\\\\&lt;]+?)\\\\](?i)\", \"\\\\[align=(left|center|right)\\\\](?i)\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"\\\\[float=(left|right)\\\\](?i)\"};<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; String[] replace_exp = new String[] {\"&lt;font color=\\\"$1\\\"&gt;\", \"&lt;font size=\\\"$1\\\"&gt;\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"&lt;font style=\\\"font-size: $1\\\"&gt;\", \"&lt;font face=\\\"$1 \\\"&gt;\", \"&lt;p align=\\\"$1\\\"&gt;\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"&lt;span style=\\\"float: $1;\\\"&gt;\"};<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; for (int i = 0; i &lt; replace_exp.length; i++) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = message.replaceAll(search_exp[i], replace_exp[i]);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (message.indexOf(\"[/quote]\") &gt;= 0) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = message.replaceAll(\"\\\\s*\\\\[quote\\\\][\\\\n\\\\r]*(.+?)[\\\\n\\\\r]*\\\\[\\\\/quote\\\\]\\\\s*(?is)\",<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; tplQuote());<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (message.indexOf(\"[/img]\") &gt;= 0) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher = Pattern.compile(\"\\\\[img\\\\]\\\\s*([^\\\\[\\\\&lt;\\\\r\\\\n]+?)\\\\s*\\\\[/img\\\\](?is)\").matcher(message);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; StringBuffer b = new StringBuffer();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; while (matcher.find()) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendReplacement(b, bbcodeURL(matcher.group(1),<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"&lt;img src=\\\"%s\\\" border=\\\"0\\\" alt=\\\"\\\" /&gt;\"));<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendTail(b);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = b.toString();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher = Pattern.compile(<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"\\\\[img=(\\\\d{1,4})[x|\\\\,](\\\\d{1,4})\\\\]\\\\s*([^\\\\[\\\\&lt;\\\\r\\\\n]+?)\\\\s*\\\\[/img\\\\](?is)\")<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; .matcher(message);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; b = new StringBuffer();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; while (matcher.find()) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendReplacement(b, bbcodeURL(matcher.group(3), \"&lt;img width=\\\"\" + matcher.group(1)<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; + \"\\\" height=\\\"\" + matcher.group(2) + \"\\\" src=\\\"%s\\\" border=\\\"0\\\" alt=\\\"\\\" /&gt;\"));<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher.appendTail(b);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = b.toString();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; int pcodeCount = (Integer) jcHomeCode.get(\"pcodecount\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; Map codeHtmlMap = (Map) jcHomeCode.get(\"codehtml\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; for (int i = 0; i &lt;= pcodeCount; i++) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = message.replace(\"[\\tJCHOME_CODE_\" + i + \"\\t]\", (String) codeHtmlMap.get(i));<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = message.replace(\"\\t\", \"&amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; \");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = message.replace(\"&nbsp;&nbsp; \", \"&amp;nbsp; &amp;nbsp;\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; message = message.replace(\"&nbsp; \", \"&amp;nbsp;&amp;nbsp;\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return Common.nl2br(message);<br>&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; private String tplCodeDisp(String code) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return \"&lt;div class=\\\"blockcode\\\"&gt;&lt;code id=\\\"code\" + jcHomeCode.get(\"codecount\") + \"\\\"&gt;\" + code<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; + \"&lt;/code&gt;&lt;/div&gt;\";<br>&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; private String parseURL(String url, String text) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; Matcher matcher = null;<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (url == null) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; Pattern pattern = Pattern<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; .compile(\"((https?|ftp|gopher|news|telnet|rtsp|mms|callto|bctp|ed2k|thunder|synacast){1}:\\\\/\\\\/|www\\\\.)[^\\\\[\\\\\\\"\']+(?i)\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher = pattern.matcher(text.trim());<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (matcher != null &amp;&amp; matcher.find()) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; url = matcher.group(0);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; int length = 65;<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (Common.strlen(url) &gt; length) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; text = url.substring(0, (int) (length * 0.5)) + \" ... \"<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; + url.substring(url.length() - (int) (length * 0.3), url.length());<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return \"&lt;a href=\\\"\" + (url.substring(0, 4).toLowerCase().equals(\"www.\") ? \"http://\" + url : url)<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; + \"\\\" target=\\\"_blank\\\"&gt;\" + text + \"&lt;/a&gt;\";<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; } else {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (url == null) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; url = \"\";<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; } else {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; url = url.substring(1, url.length());<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (url.substring(0, 4).toLowerCase().equals(\"www.\")) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; url = \"http://\" + url;<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return \"&lt;a href=\\\"\" + url + \"\\\" target=\\\"_blank\\\"&gt;\" + text + \"&lt;/a&gt;\";<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; private String parseEmail(String email, String text) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; Matcher matcher = null;<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (email == null) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; Pattern pattern = Pattern<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; .compile(\"\\\\s*([a-z0-9\\\\-_.+]+)@([a-z0-9\\\\-_]+[.][a-z0-9\\\\-_.]+)\\\\s*(?i)\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; matcher = pattern.matcher(text);<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (matcher != null &amp;&amp; matcher.find()) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; email = matcher.group(0).trim();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return \"&lt;a href=\\\"mailto:\" + email + \"\\\"&gt;\" + email + \"&lt;/a&gt;\";<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; } else {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return \"&lt;a href=\\\"mailto:\" + (email == null ? \"\" : email.substring(1, email.length())) + \"\\\"&gt;\"<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; + text + \"&lt;/a&gt;\";<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; private String tplQuote() {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return \"&lt;div class=\\\"quote\\\"&gt;&lt;blockquote&gt;$1&lt;/blockquote&gt;&lt;/div&gt;\";<br>&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; private String bbcodeURL(String url, String tags) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (!url.matches(\"&lt;.+?&gt;(?s)\")) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; int urlLength = url.length() &gt;= 6 ? 6 : url.length();<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; if (!Common.in_array(new String[] {\"http:/\", \"https:\", \"ftp://\", \"rtsp:/\", \"mms://\"}, url<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; .substring(0, urlLength).toLowerCase())) {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; url = \"http://\" + url;<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return String.format(tags, url, Common.addSlashes(url)).replace(\"submit\", \"\").replace(<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; \"logging.jsp\", \"\");<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; } else {<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; return \"&amp;nbsp;\" + url;<br>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp; }<br>}<br><br>','127.0.0.1','N;',1316584572,'','2',0,0,0),
 (2,1,'a:1:{i:2;s:12:\"压缩工具\";}','<br>package cn.jcenterhome.util;<br>import java.io.BufferedInputStream;<br>import java.io.BufferedOutputStream;<br>import java.io.File;<br>import java.io.FileInputStream;<br>import java.io.FileOutputStream;<br>import java.io.IOException;<br>import java.util.Enumeration;<br>import java.util.zip.ZipEntry;<br>import java.util.zip.ZipFile;<br>import java.util.zip.ZipOutputStream;<br>public class ZipUtil {<br>&nbsp;&nbsp; &nbsp;private final int BUFFER = 2048;<br>&nbsp;&nbsp; &nbsp;private int decompress_count = 0; <br>&nbsp;&nbsp; &nbsp;private int decompress_file_count = 0; <br>&nbsp;&nbsp; &nbsp;private int decompress_folder_count = 0;<br>&nbsp;&nbsp; &nbsp;public boolean compress(String zipFileName, File sourceFile) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;FileOutputStream fos = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;ZipOutputStream zos = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;fos = new FileOutputStream(zipFileName);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos = new ZipOutputStream(fos);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (!zip(zos, sourceFile, sourceFile.getName())) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;throw new Exception();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (Exception e) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (fos != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;fos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (zos != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (IOException ioe) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;deleteDir(new File(zipFileName));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return false;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return true;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;public boolean compress(String zipFileName, File[] files) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;FileOutputStream fos = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;ZipOutputStream zos = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;fos = new FileOutputStream(zipFileName);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos = new ZipOutputStream(fos);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;for (File f : files) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (!zip(zos, f, f.getName())) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;throw new Exception();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (Exception e) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (fos != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;fos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (zos != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (IOException ioe) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;deleteDir(new File(zipFileName));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return false;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return true;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;@SuppressWarnings(\"unchecked\")<br>&nbsp;&nbsp; &nbsp;public boolean decompress(ZipFile zipFile, String toDir, String fileSuffix) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;decompress_count = 0;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;decompress_file_count = 0;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;decompress_folder_count = 0;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;BufferedInputStream bis = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;BufferedOutputStream bos = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;String separator = File.separator;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;Enumeration e = zipFile.entries();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;ZipEntry entry = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;byte[] datas = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;while (e.hasMoreElements()) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;entry = (ZipEntry) e.nextElement();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (fileSuffix != null &amp;&amp; !entry.getName().endsWith(fileSuffix)) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;continue;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (entry.isDirectory()) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;File dir = new File(toDir + separator + entry.getName());<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;dir.mkdirs();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;decompress_folder_count++;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} else {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;File file = new File(toDir + separator + entry.getName());<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis = new BufferedInputStream(zipFile.getInputStream(entry));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bos = new BufferedOutputStream(new FileOutputStream(file));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;int byteNum;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;datas = new byte[BUFFER];<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;while ((byteNum = bis.read(datas, 0, BUFFER)) != -1) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bos.write(datas, 0, byteNum);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bos.flush();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;decompress_file_count++;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;decompress_count++;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (Exception e) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (bos != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (bis != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (IOException ioe) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;deleteDir(new File(toDir));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return false;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return true;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;@SuppressWarnings(\"unchecked\")<br>&nbsp;&nbsp; &nbsp;public boolean decompress(ZipFile zipFile, String toDir) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return decompress(zipFile, toDir, null);<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;public void entryToFile(ZipFile zipFile, ZipEntry entry, File file) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;BufferedInputStream bis = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;BufferedOutputStream bos = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis = new BufferedInputStream(zipFile.getInputStream(entry));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bos = new BufferedOutputStream(new FileOutputStream(file));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;int byteNum;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;byte[] datas = new byte[BUFFER];<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;while ((byteNum = bis.read(datas, 0, BUFFER)) != -1) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bos.write(datas, 0, byteNum);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (IOException e) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;e.printStackTrace();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} finally {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (bos != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bos.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (bis != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (IOException ioe) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;public String getEntryContent(ZipFile zipFile, ZipEntry entry) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;BufferedInputStream bis = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis = new BufferedInputStream(zipFile.getInputStream(entry));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;StringBuffer strBuf = new StringBuffer();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;byte[] datas = new byte[BUFFER];<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;while (bis.read(datas, 0, BUFFER) != -1) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;strBuf.append(new String(datas));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return strBuf.toString();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (IOException e) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;e.printStackTrace();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} finally {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (bis != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (IOException e) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return null;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;@SuppressWarnings(\"unchecked\")<br>&nbsp;&nbsp; &nbsp;public ZipEntry getFirstEntry(ZipFile zipFile) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;Enumeration e = zipFile.entries();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (e.hasMoreElements()) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return (ZipEntry) e.nextElement();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return null;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;public int getDecompressCount() {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return decompress_count;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;public int getDecompressFileCount() {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return decompress_file_count;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;public int getDecompressFolderCount() {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return decompress_folder_count;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;private boolean zip(ZipOutputStream zos, File sourceFile, String entryPath) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (entryPath == null) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;entryPath = \"\";<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;BufferedInputStream bis = null;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (sourceFile.isDirectory()) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (entryPath.length() != 0) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;entryPath += \"/\";<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;ZipEntry entry = new ZipEntry(entryPath);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos.putNextEntry(entry);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;File[] folder = sourceFile.listFiles();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;for (File file : folder) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (!zip(zos, file, entryPath + file.getName())) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;throw new Exception();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} else {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;ZipEntry entry = new ZipEntry(entryPath);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos.putNextEntry(entry);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;FileInputStream fis = new FileInputStream(sourceFile);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis = new BufferedInputStream(fis, BUFFER);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;int byteNum;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;byte[] datas = new byte[BUFFER];<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;while ((byteNum = bis.read(datas, 0, BUFFER)) != -1) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;zos.write(datas, 0, byteNum);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (Exception e) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return false;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} finally {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (bis != null)<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;bis.close();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (IOException ioe) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return true;<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;private void deleteDir(File del) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (del != null &amp;&amp; del.exists()) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (del.isDirectory()) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;File[] files = del.listFiles();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;for (File f : files) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (f.isFile()) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;f.delete();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} else {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;deleteDir(f);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} else {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;del.delete();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;}<br>}<br><br>','127.0.0.1','N;',1316674268,'','',0,0,0),
 (3,2,'a:2:{i:3;s:4:\"gzip\";i:4;s:9:\"过滤器\";}','<br>package cn.jcenterhome.web.filter;<br>import java.io.IOException;<br>import javax.servlet.Filter;<br>import javax.servlet.FilterChain;<br>import javax.servlet.FilterConfig;<br>import javax.servlet.ServletException;<br>import javax.servlet.ServletRequest;<br>import javax.servlet.ServletResponse;<br>import javax.servlet.http.HttpServletRequest;<br>import javax.servlet.http.HttpServletResponse;<br>import cn.jcenterhome.util.Common;<br>import cn.jcenterhome.util.JavaCenterHome;<br>import cn.jcenterhome.web.servlet.GZIPResponseWrapper;<br>/**<br>&nbsp;* gzip压缩过滤器,如果config.properties的gzipCompress=1，就采用gzip压缩<br>&nbsp;* <br>&nbsp;* @author caixl , Sep 21, 2011<br>&nbsp;*<br>&nbsp;*/<br>public class GZIPFilter implements Filter {<br>&nbsp;&nbsp; &nbsp;public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;ServletException {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;int gzipCompress = Common.intval(JavaCenterHome.jchConfig.get(\"gzipCompress\"));<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (gzipCompress == 1 &amp;&amp; req instanceof HttpServletRequest) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;HttpServletRequest request = (HttpServletRequest) req;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;HttpServletResponse response = (HttpServletResponse) res;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;String ae = request.getHeader(\"accept-encoding\");<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (ae != null &amp;&amp; ae.indexOf(\"gzip\") != -1) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;chain.doFilter(req, wrappedResponse);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;wrappedResponse.finishResponse();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;chain.doFilter(req, res);<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;public void init(FilterConfig filterConfig) {<br>&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;public void destroy() {<br>&nbsp;&nbsp; &nbsp;}<br>}','127.0.0.1','N;',1316607783,'','',0,0,0),
 (4,1,'a:1:{i:5;s:6:\"缓存\";}','@SuppressWarnings(\"unchecked\")<br>&nbsp;&nbsp; &nbsp;public static Map getCacheDate(HttpServletRequest request, HttpServletResponse response, String path,<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;String var) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;Map cacheValue = (Map) request.getAttribute(var);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (cacheValue == null) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;try {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;String jchRoot = JavaCenterHome.jchRoot;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;File configFile = new File(jchRoot + path);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (!configFile.exists()) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;CacheService cacheService = (CacheService) BeanFactory.getBean(\"cacheService\");<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;cacheService.updateCache();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;HttpServletRequest myRequest = request;<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if (request instanceof MultipartRequestWrapper) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; myRequest = ((MultipartRequestWrapper) request).getRequest();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;request.getRequestDispatcher(path).include(myRequest, response);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;} catch (Exception e) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;e.printStackTrace();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;cacheValue = (Map) request.getAttribute(var);<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (cacheValue == null) {<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;cacheValue = new HashMap();<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return cacheValue;<br>&nbsp;&nbsp; &nbsp;}','127.0.0.1','N;',1316676632,'','',0,0,0);
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
  `classid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `classname` char(40) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`classid`),
  KEY `uid` (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_class`
--

/*!40000 ALTER TABLE `jchome_class` DISABLE KEYS */;
INSERT INTO `jchome_class` (`classid`,`classname`,`uid`,`dateline`) VALUES 
 (1,'java',1,1316584572),
 (2,'java',2,1316607783),
 (3,'缓存',1,1316676632);
/*!40000 ALTER TABLE `jchome_class` ENABLE KEYS */;


--
-- Definition of table `jchome_click`
--

DROP TABLE IF EXISTS `jchome_click`;
CREATE TABLE `jchome_click` (
  `clickid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `icon` varchar(100) NOT NULL DEFAULT '',
  `idtype` varchar(15) NOT NULL DEFAULT '',
  `displayorder` tinyint(6) unsigned NOT NULL DEFAULT '0',
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
INSERT INTO `jchome_clickuser` (`uid`,`username`,`id`,`idtype`,`clickid`,`dateline`) VALUES 
 (2,'cxl',1,'blogid',4,1316584952);
/*!40000 ALTER TABLE `jchome_clickuser` ENABLE KEYS */;


--
-- Definition of table `jchome_comment`
--

DROP TABLE IF EXISTS `jchome_comment`;
CREATE TABLE `jchome_comment` (
  `cid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `idtype` varchar(20) NOT NULL DEFAULT '',
  `authorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `author` varchar(15) NOT NULL DEFAULT '',
  `ip` varchar(20) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `magicflicker` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cid`),
  KEY `authorid` (`authorid`,`idtype`),
  KEY `id` (`id`,`idtype`,`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_comment`
--

/*!40000 ALTER TABLE `jchome_comment` DISABLE KEYS */;
INSERT INTO `jchome_comment` (`cid`,`uid`,`id`,`idtype`,`authorid`,`author`,`ip`,`dateline`,`message`,`magicflicker`) VALUES 
 (1,1,1,'blogid',1,'admin','127.0.0.1',1316584600,'<img src=\"image/face/3.gif\" class=\"face\">',0),
 (2,1,1,'blogid',2,'cxl','127.0.0.1',1316584940,'<img src=\"image/face/22.gif\" class=\"face\">',0),
 (3,1,2,'blogid',1,'admin','127.0.0.1',1316652916,'<img src=\"attachment/201109/22/1_1316652914NRP4.jpg\">',0),
 (4,2,2,'uid',1,'admin','127.0.0.1',1316666491,'踩踩',0),
 (5,2,2,'uid',1,'admin','127.0.0.1',1316666528,'<img src=\"attachment/201109/22/1_13166665265Ebx.jpg\">',0);
/*!40000 ALTER TABLE `jchome_comment` ENABLE KEYS */;


--
-- Definition of table `jchome_config`
--

DROP TABLE IF EXISTS `jchome_config`;
CREATE TABLE `jchome_config` (
  `var` varchar(30) NOT NULL DEFAULT '',
  `datavalue` text NOT NULL,
  PRIMARY KEY (`var`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_config`
--

/*!40000 ALTER TABLE `jchome_config` DISABLE KEYS */;
INSERT INTO `jchome_config` (`var`,`datavalue`) VALUES 
 ('sitename','IT'),
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
 ('cronnextrun','1316679900'),
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
 ('jchid','RoHglNe25672'),
 ('lastupdate','1316580180'),
 ('status','0'),
 ('sitekey','66e580Y7R5xn96PT'),
 ('licensed','0'),
 ('name_allowpoll','0'),
 ('domainroot',''),
 ('video_allowdoing','0'),
 ('video_allowpoll','0'),
 ('videophoto','0'),
 ('video_allowpoke','0'),
 ('video_allowwall','0'),
 ('closereason',''),
 ('video_allowalbum','0'),
 ('openim','0'),
 ('name_allowevent','0'),
 ('video_allowgift','0'),
 ('video_allowshare','0'),
 ('ftpurl',''),
 ('siteallurl',''),
 ('video_allowfriend','0'),
 ('video_allowthread','0'),
 ('video_allowcomment','0'),
 ('debuginfo','0'),
 ('video_allowblog','0'),
 ('video_allowviewphoto','0'),
 ('video_allowevent','0'),
 ('jc_dir',''),
 ('miibeian',''),
 ('openxmlrpc','0'),
 ('video_allowpost','0'),
 ('avatarreal','0'),
 ('defaultpoke','后台自动给你加cxl为好友哦'),
 ('defaultfusername','cxl');
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
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_creditlog`
--

/*!40000 ALTER TABLE `jchome_creditlog` DISABLE KEYS */;
INSERT INTO `jchome_creditlog` (`clid`,`uid`,`rid`,`total`,`cyclenum`,`credit`,`experience`,`starttime`,`info`,`user`,`app`,`dateline`) VALUES 
 (1,1,1,1,1,10,0,0,'','','',1316580173),
 (2,1,10,2,1,15,15,0,'','','',1316652874),
 (3,1,16,3,1,5,5,0,'','','',1316676632),
 (4,2,1,1,1,10,0,0,'','','',1316584832),
 (5,2,10,2,1,15,15,0,'','','',1316678661),
 (6,2,27,1,1,1,1,0,'blogid1','','',1316584940),
 (7,1,28,1,1,1,0,0,'blogid1','','',1316584940),
 (8,2,31,1,1,1,1,0,'blogid1','','',1316584952),
 (9,2,8,3,3,3,3,0,'','','',1316604851),
 (10,2,17,2,2,2,2,0,'','','',1316605341),
 (11,2,12,1,1,1,1,0,'','1','',1316605442),
 (12,1,11,2,1,1,1,0,'','2','',1316666495),
 (13,2,11,2,2,1,1,0,'','1,4','',1316610136),
 (14,1,8,1,1,3,3,0,'','','',1316606760),
 (15,1,12,1,1,1,1,0,'','2','',1316606791),
 (16,2,16,1,1,5,5,0,'','','',1316607783),
 (17,2,5,1,1,15,15,0,'','','',1316607905),
 (18,3,1,1,1,10,0,0,'','','',1316608553),
 (19,3,10,1,1,15,15,0,'','','',1316608553),
 (20,2,2,1,1,20,20,0,'','','',1316609726),
 (21,4,1,1,1,10,0,0,'','','',1316610077),
 (22,4,10,1,1,15,15,0,'','','',1316610077),
 (23,2,26,1,1,2,2,0,'','','',1316610193),
 (24,1,13,1,1,2,2,0,'','2','',1316666528),
 (25,2,14,1,1,1,0,0,'','1','',1316666528);
/*!40000 ALTER TABLE `jchome_creditlog` ENABLE KEYS */;


--
-- Definition of table `jchome_creditrule`
--

DROP TABLE IF EXISTS `jchome_creditrule`;
CREATE TABLE `jchome_creditrule` (
  `rid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `rulename` char(20) NOT NULL DEFAULT '',
  `action` char(20) NOT NULL DEFAULT '',
  `cycletype` tinyint(1) NOT NULL DEFAULT '0',
  `cycletime` int(10) NOT NULL DEFAULT '0',
  `rewardnum` tinyint(2) NOT NULL DEFAULT '1',
  `rewardtype` tinyint(1) NOT NULL DEFAULT '1',
  `norepeat` tinyint(1) NOT NULL DEFAULT '0',
  `credit` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `experience` mediumint(8) unsigned NOT NULL DEFAULT '0',
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
  `cronid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `type` enum('user','system') NOT NULL DEFAULT 'user',
  `name` char(50) NOT NULL DEFAULT '',
  `filename` char(50) NOT NULL DEFAULT '',
  `lastrun` int(10) unsigned NOT NULL DEFAULT '0',
  `nextrun` int(10) unsigned NOT NULL DEFAULT '0',
  `weekday` tinyint(1) NOT NULL DEFAULT '0',
  `day` tinyint(2) NOT NULL DEFAULT '0',
  `hour` tinyint(2) NOT NULL DEFAULT '0',
  `minute` char(36) NOT NULL DEFAULT '',
  PRIMARY KEY (`cronid`),
  KEY `nextrun` (`available`,`nextrun`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_cron`
--

/*!40000 ALTER TABLE `jchome_cron` DISABLE KEYS */;
INSERT INTO `jchome_cron` (`cronid`,`available`,`type`,`name`,`filename`,`lastrun`,`nextrun`,`weekday`,`day`,`hour`,`minute`) VALUES 
 (1,1,'system','更新浏览数统计','log.jsp',1316679606,1316679900,-1,-1,-1,'0	5	10	15	20	25	30	35	40	45	50	55'),
 (2,1,'system','清理过期feed','cleanfeed.jsp',1316652880,1316718240,-1,-1,3,'4'),
 (3,1,'system','清理个人通知','cleannotification.jsp',1316652954,1316725560,-1,-1,5,'6'),
 (4,1,'system','清理脚印和最新访客','cleantrace.jsp',1316652875,1316714580,-1,-1,2,'3');
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
 ('mail','a:8:{s:4:\"port\";s:1:\"0\";s:12:\"mailusername\";s:1:\"1\";s:13:\"maildelimiter\";s:1:\"0\";s:6:\"server\";s:0:\"\";s:13:\"auth_password\";s:0:\"\";s:4:\"from\";s:0:\"\";s:4:\"auth\";s:1:\"0\";s:13:\"auth_username\";s:0:\"\";}',1316609164),
 ('setting','a:14:{s:7:\"ftpuser\";s:0:\"\";s:14:\"maxthumbheight\";s:1:\"0\";s:6:\"ftpssl\";s:1:\"0\";s:7:\"ftppasv\";s:1:\"0\";s:11:\"thumbheight\";s:3:\"100\";s:7:\"ftphost\";s:0:\"\";s:10:\"thumbwidth\";s:3:\"100\";s:13:\"maxthumbwidth\";s:1:\"0\";s:13:\"watermarkfile\";s:0:\"\";s:10:\"ftptimeout\";s:1:\"0\";s:11:\"ftppassword\";s:0:\"\";s:6:\"ftpdir\";s:0:\"\";s:7:\"ftpport\";s:1:\"0\";s:12:\"watermarkpos\";s:1:\"4\";}',1316609164),
 ('network','a:6:{s:5:\"share\";a:1:{s:5:\"cache\";i:500;}s:4:\"poll\";a:1:{s:5:\"cache\";i:500;}s:5:\"event\";a:1:{s:5:\"cache\";i:900;}s:6:\"thread\";a:2:{s:5:\"cache\";i:800;s:4:\"hot1\";i:3;}s:3:\"pic\";a:2:{s:5:\"cache\";i:700;s:4:\"hot1\";i:3;}s:4:\"blog\";a:2:{s:5:\"cache\";i:600;s:4:\"hot1\";i:3;}}',1316580164),
 ('newspacelist','a:3:{i:0;a:6:{s:3:\"uid\";i:4;s:11:\"videostatus\";i:0;s:10:\"namestatus\";i:0;s:8:\"username\";s:3:\"foo\";s:8:\"dateline\";i:1316610077;s:4:\"name\";s:0:\"\";}i:1;a:6:{s:3:\"uid\";i:3;s:11:\"videostatus\";i:0;s:10:\"namestatus\";i:0;s:8:\"username\";s:4:\"test\";s:8:\"dateline\";i:1316608553;s:4:\"name\";s:0:\"\";}i:2;a:6:{s:3:\"uid\";i:2;s:11:\"videostatus\";i:0;s:10:\"namestatus\";i:1;s:8:\"username\";s:3:\"cxl\";s:8:\"dateline\";i:1316584832;s:4:\"name\";s:6:\"龙哦\";}}',1316610077),
 ('registerrule','',0),
 ('reason','',0),
 ('backupdir','g1PkE8',1316609874);
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
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `from` varchar(20) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `ip` varchar(20) NOT NULL DEFAULT '',
  `replynum` int(10) unsigned NOT NULL DEFAULT '0',
  `mood` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`doid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_doing`
--

/*!40000 ALTER TABLE `jchome_doing` DISABLE KEYS */;
INSERT INTO `jchome_doing` (`doid`,`uid`,`username`,`from`,`dateline`,`message`,`ip`,`replynum`,`mood`) VALUES 
 (1,2,'cxl','',1316604802,'<img src=\"image/face/10.gif\" class=\"face\">','127.0.0.1',0,10),
 (2,2,'cxl','',1316605749,'<img src=\"image/face/3.gif\" class=\"face\">','127.0.0.1',0,3),
 (3,1,'admin','',1316606760,'<img src=\"image/face/12.gif\" class=\"face\">','127.0.0.1',0,12);
/*!40000 ALTER TABLE `jchome_doing` ENABLE KEYS */;


--
-- Definition of table `jchome_event`
--

DROP TABLE IF EXISTS `jchome_event`;
CREATE TABLE `jchome_event` (
  `eventid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `title` varchar(80) NOT NULL DEFAULT '',
  `classid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `province` varchar(20) NOT NULL DEFAULT '',
  `city` varchar(20) NOT NULL DEFAULT '',
  `location` varchar(80) NOT NULL DEFAULT '',
  `poster` varchar(60) NOT NULL DEFAULT '',
  `thumb` tinyint(1) NOT NULL DEFAULT '0',
  `remote` tinyint(1) NOT NULL DEFAULT '0',
  `deadline` int(10) unsigned NOT NULL DEFAULT '0',
  `starttime` int(10) unsigned NOT NULL DEFAULT '0',
  `endtime` int(10) unsigned NOT NULL DEFAULT '0',
  `public` tinyint(3) NOT NULL DEFAULT '0',
  `membernum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `follownum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `viewnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `grade` tinyint(3) NOT NULL DEFAULT '0',
  `recommendtime` int(10) unsigned NOT NULL DEFAULT '0',
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `picnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `threadnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `updatetime` int(10) unsigned NOT NULL DEFAULT '0',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0',
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
  `classid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `classname` varchar(80) NOT NULL DEFAULT '',
  `poster` tinyint(1) NOT NULL DEFAULT '0',
  `template` text NOT NULL,
  `displayorder` mediumint(8) unsigned NOT NULL DEFAULT '0',
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
  `icon` varchar(30) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `friend` tinyint(1) NOT NULL DEFAULT '0',
  `hash_template` varchar(32) NOT NULL DEFAULT '',
  `hash_data` varchar(32) NOT NULL DEFAULT '',
  `title_template` text NOT NULL,
  `title_data` text NOT NULL,
  `body_template` text NOT NULL,
  `body_data` text NOT NULL,
  `body_general` text NOT NULL,
  `image_1` varchar(255) NOT NULL DEFAULT '',
  `image_1_link` varchar(255) NOT NULL DEFAULT '',
  `image_2` varchar(255) NOT NULL DEFAULT '',
  `image_2_link` varchar(255) NOT NULL DEFAULT '',
  `image_3` varchar(255) NOT NULL DEFAULT '',
  `image_3_link` varchar(255) NOT NULL DEFAULT '',
  `image_4` varchar(255) NOT NULL DEFAULT '',
  `image_4_link` varchar(255) NOT NULL DEFAULT '',
  `target_ids` text NOT NULL,
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `idtype` varchar(15) NOT NULL DEFAULT '',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`feedid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `dateline` (`dateline`),
  KEY `hot` (`hot`),
  KEY `id` (`id`,`idtype`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_feed`
--

/*!40000 ALTER TABLE `jchome_feed` DISABLE KEYS */;
INSERT INTO `jchome_feed` (`feedid`,`appid`,`icon`,`uid`,`username`,`dateline`,`friend`,`hash_template`,`hash_data`,`title_template`,`title_data`,`body_template`,`body_data`,`body_general`,`image_1`,`image_1_link`,`image_2`,`image_2_link`,`image_3`,`image_3_link`,`image_4`,`image_4_link`,`target_ids`,`id`,`idtype`,`hot`) VALUES 
 (1,0,'profile',1,'admin',1316580173,0,'3a7101a64ea7927f0b3f5179b7a457b3','ec7d775d9211880bca2ba1d401e3bcb9','{actor} 开通了自己的个人主页','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (2,0,'blog',1,'admin',1316584572,0,'2c24ba00fafd81b79f331389e04a26cb','62b1744370e78a14a65d2e51d5e09430','{actor} 发表了新日志','N;','<b>{subject}</b><br>{summary}','a:2:{s:7:\"summary\";s:149:\"package cn.jcenterhome.util; import java.util.HashMap; import java.util.Map; import java.util.regex.Matcher; import java.util.regex.Pattern; public c\";s:7:\"subject\";s:55:\"<a href=\"space.jsp?uid=1&do=blog&id=1\">代码列表</a>\";}','','','','','','','','','','',1,'blogid',1),
 (3,0,'blog',1,'admin',1316584674,0,'2c24ba00fafd81b79f331389e04a26cb','a29a0ea56b6a020d52155f854802846a','{actor} 发表了新日志','N;','<b>{subject}</b><br>{summary}','a:2:{s:7:\"summary\";s:149:\"package cn.jcenterhome.util; import java.io.BufferedInputStream; import java.io.BufferedOutputStream; import java.io.File; import java.io.FileInputSt\";s:7:\"subject\";s:58:\"<a href=\"space.jsp?uid=1&do=blog&id=2\">压缩工具类</a>\";}','','','','','','','','','','',2,'blogid',0),
 (4,0,'profile',2,'cxl',1316584832,0,'3a7101a64ea7927f0b3f5179b7a457b3','ec7d775d9211880bca2ba1d401e3bcb9','{actor} 开通了自己的个人主页','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (5,0,'comment',2,'cxl',1316584940,0,'7d93383c065dd586c9205fedb1c4fa25','a37ca0f63c102cc29707da0b51251b7c','{actor} 评论了 {touser} 的日志 {blog}','a:2:{s:6:\"touser\";s:35:\"<a href=\"space.jsp?uid=1\">admin</a>\";s:4:\"blog\";s:55:\"<a href=\"space.jsp?uid=1&do=blog&id=1\">代码列表</a>\";}','','a:0:{}','','','','','','','','','','',1,'blogid',0),
 (6,0,'click',2,'cxl',1316584952,0,'635b0d9c28c93db81825d2238e4fa6ae','08a1e221946a1571575c8b0c52272811','{actor} 送了一个“{click}”给 {touser} 的日志 {subject}','a:3:{s:7:\"subject\";s:55:\"<a href=\"space.jsp?uid=1&do=blog&id=1\">代码列表</a>\";s:5:\"click\";s:6:\"鲜花\";s:6:\"touser\";s:35:\"<a href=\"space.jsp?uid=1\">admin</a>\";}','','a:0:{}','','','','','','','','','','',1,'blogid',0),
 (7,0,'doing',2,'cxl',1316604802,0,'eea209e281f63b3ebc3304f1d4ae171b','10ca08d4ae5e7ac62d785f2f521a365c','cp_feed_doing_title','a:1:{s:7:\"message\";s:42:\"<img src=\"image/face/10.gif\" class=\"face\">\";}','','','','','','','','','','','','',1,'doid',0),
 (8,0,'album',2,'cxl',1316605341,0,'a3bb22490fb7a528e758a930e6675793','503cd2a72a44c35dfc03b03958eac1eb','{actor} cp_upload_album','N;','<b>{album}</b><br>cp_the_total_picture','a:2:{s:6:\"picnum\";i:2;s:5:\"album\";s:50:\"<a href=\"space.jsp?uid=2&do=album&id=1\">想吃</a>\";}','','attachment/201109/21/2_1316605341EBDF.jpg.thumb.jpg','space.jsp?uid=2&do=album&picid=2','attachment/201109/21/2_1316605340GNIz.jpg.thumb.jpg','space.jsp?uid=2&do=album&picid=1','','','','','',1,'albumid',0),
 (9,0,'friend',2,'cxl',1316605610,0,'30cbc640ac19aafc846cfcc33ac2055e','ec2c56fc642353d1a0dbfc509e2a8287','cp_feed_friend_title','a:1:{s:6:\"touser\";s:35:\"<a href=\"space.jsp?uid=1\">admin</a>\";}','','a:0:{}','','','','','','','','','','',0,'',0),
 (10,0,'doing',2,'cxl',1316605749,0,'eea209e281f63b3ebc3304f1d4ae171b','1e75d035fb9ffd0b09453fc6f3af5975','cp_feed_doing_title','a:1:{s:7:\"message\";s:41:\"<img src=\"image/face/3.gif\" class=\"face\">\";}','','','','','','','','','','','','',2,'doid',0),
 (11,0,'profile',2,'cxl',1316607938,0,'f985fb367f4f414af41cd6a87eebd714','20e980bce50c4df9b37256da19e03456','cp_feed_profile_update_base','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (12,0,'doing',1,'admin',1316606760,0,'eea209e281f63b3ebc3304f1d4ae171b','03555268b5aa6e454ccfb0e87e3fb910','cp_feed_doing_title','a:1:{s:7:\"message\";s:42:\"<img src=\"image/face/12.gif\" class=\"face\">\";}','','','','','','','','','','','','',3,'doid',0),
 (13,0,'mtag',2,'cxl',1316607049,0,'e0c15f92dcf1be8b62939572719186de','1dbc8ac52baa115723816ba88e0294cb','cp_feed_mtag_join','a:2:{s:5:\"field\";s:49:\"<a href=\"space.jsp?do=mtag&id=1\">自由联盟</a>\";s:4:\"mtag\";s:52:\"<a href=\"space.jsp?do=mtag&tagid=1\">灌篮高手</a>\";}','','a:0:{}','','','','','','','','','','',0,'',0),
 (29,0,'thunder',0,'admin',1316667903,0,'38cb145a2b4c3f329d1ddd242666da30','632fd6364b3d44f20c912d4a186f5558','<strong>{username} 发出了“雷鸣之声”</strong>','a:2:{s:3:\"uid\";i:1;s:8:\"username\";s:35:\"<a href=\"space.jsp?uid=1\">admin</a>\";}','大家好，我上线啦<br><a href=\"space.jsp?uid={uid}\" target=\"_blank\">欢迎来我家串个门</a>','a:2:{s:3:\"uid\";i:1;s:13:\"magic_thunder\";i:1;}','','data/avatar/noavatar_middle.gif','space.jsp?uid=1','','','','','','','',0,'',0),
 (15,0,'profile',2,'cxl',1316607482,0,'85e67470d663791550e55166ec48de6e','1fba40a620ca0c81dcfa364ab2959416','cp_feed_profile_update_edu','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (16,0,'profile',2,'cxl',1316607517,0,'2ec932639b236942199d6c072147aa69','9fd99649f3d6db3b8bd900755e54677c','cp_feed_profile_update_work','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (17,0,'profile',2,'cxl',1316607569,0,'efef5ebc78060fffc1aac5cea0549fd0','7c5a05c73c6949a3fc2b395497786a3f','cp_feed_profile_update_info','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (18,0,'task',2,'cxl',1316607730,0,'153261a3cf00a65483e2399662c4b500','db127050d58044f36c694959e699518e','cp_feed_task_credit','a:2:{s:4:\"task\";s:65:\"<a href=\"cp.jsp?ac=task&taskid=2\">将个人资料补充完整</a>\";s:6:\"credit\";i:20;}','','a:0:{}','','','','','','','','','','',0,'',0),
 (19,0,'blog',2,'cxl',1316607783,0,'b9a432477b56617733f9ab76666eec70','29386fd8321136b0fafe958c6b0758ac','cp_feed_blog','N;','<b>{subject}</b><br>{summary}','a:2:{s:7:\"summary\";s:149:\"package cn.jcenterhome.web.filter; import java.io.IOException; import javax.servlet.Filter; import javax.servlet.FilterChain; import javax.servlet.Fi\";s:7:\"subject\";s:56:\"<a href=\"space.jsp?uid=2&do=blog&id=3\">gzip过滤器</a>\";}','','','','','','','','','','',3,'blogid',0),
 (20,0,'task',2,'cxl',1316607790,0,'153261a3cf00a65483e2399662c4b500','039f01b285cf497d383783bfe3ffeebb','cp_feed_task_credit','a:2:{s:4:\"task\";s:68:\"<a href=\"cp.jsp?ac=task&taskid=3\">发表自己的第一篇日志</a>\";s:6:\"credit\";i:5;}','','a:0:{}','','','','','','','','','','',0,'',0),
 (21,0,'task',2,'cxl',1316607825,0,'153261a3cf00a65483e2399662c4b500','95226c74c12eaf4418d238be2875c795','cp_feed_task_credit','a:2:{s:4:\"task\";s:65:\"<a href=\"cp.jsp?ac=task&taskid=7\">领取每日访问大礼包</a>\";s:6:\"credit\";i:5;}','','a:0:{}','','','','','','','','','','',0,'',0),
 (22,0,'task',2,'cxl',1316607912,0,'153261a3cf00a65483e2399662c4b500','99ad8b40f8a03830dbbc7d288f2a3f8c','cp_feed_task_credit','a:2:{s:4:\"task\";s:65:\"<a href=\"cp.jsp?ac=task&taskid=1\">更新一下自己的头像</a>\";s:6:\"credit\";i:20;}','','a:0:{}','','','','','','','','','','',0,'',0),
 (23,0,'profile',3,'test',1316608553,0,'4f2fd0cd1449dc1414bacae6ee5a5ba2','692bad858aef200c730d12d0ec6b0a1e','cp_feed_space_open','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (24,0,'friend',2,'cxl',1316608605,0,'30cbc640ac19aafc846cfcc33ac2055e','6a916a2781077d145e9105581d553386','cp_feed_friend_title','a:1:{s:6:\"touser\";s:34:\"<a href=\"space.jsp?uid=3\">test</a>\";}','','a:0:{}','','','','','','','','','','',0,'',0),
 (25,0,'profile',4,'foo',1316610077,0,'4f2fd0cd1449dc1414bacae6ee5a5ba2','692bad858aef200c730d12d0ec6b0a1e','cp_feed_space_open','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (26,0,'share',2,'cxl',1316610193,0,'15560acef3dea7e280cdb9261e037223','fee15688a52ac216512fb48bdd4e4f2a','{actor} <a href=\"{url}\">cp_share_space</a>','a:1:{s:3:\"url\";s:29:\"space.jsp?uid=2&do=share&id=1\";}','<b>{username}</b><br>{reside}<br>{spacenote}','a:3:{s:8:\"username\";s:35:\"<a href=\"space.jsp?uid=1\">admin</a>\";s:6:\"reside\";s:0:\"\";s:9:\"spacenote\";s:42:\"<img src=\"image/face/12.gif\" class=\"face\">\";}','分享这个测试东东哦','data/avatar/noavatar_middle.gif','space.jsp?uid=1','','','','','','','',1,'sid',0),
 (27,0,'profile',1,'admin',1316665840,0,'24ffccd31b36d7ec6f4427c5e444fb25','0438762c64ab7c317879ef2e5629f4f7','{actor} 更新了自己的基本资料','a:0:{}','','a:0:{}','','','','','','','','','','',0,'',0),
 (28,0,'wall',1,'admin',1316666528,0,'cfea81bafbda82c436236af704a1cb5a','593a7575b133d294cdc426fded585308','{actor} 在 {touser} 的留言板留了言','a:1:{s:6:\"touser\";s:33:\"<a href=\"space.jsp?uid=2\">cxl</a>\";}','','a:0:{}','','','','','','','','','','',2,'uid',0),
 (30,0,'task',1,'admin',1316676596,0,'85e4383d5e3f680a4a31e302fb81530e','a87c533bbf2b36b59674e762d2fba29f','{actor} 完成了有奖任务 {task}，领取了 {credit} 个奖励积分','a:2:{s:4:\"task\";s:68:\"<a href=\"cp.jsp?ac=task&taskid=3\">发表自己的第一篇日志</a>\";s:6:\"credit\";i:5;}','','a:0:{}','','','','','','','','','','',0,'',0),
 (31,0,'blog',1,'admin',1316676632,0,'2c24ba00fafd81b79f331389e04a26cb','b87b7aa968941676d785e207efc6ba6d','{actor} 发表了新日志','N;','<b>{subject}</b><br>{summary}','a:2:{s:7:\"summary\";s:150:\"@SuppressWarnings(&quot;unchecked&quot;) &nbsp;&nbsp; &nbsp;public static Map getCacheDate(HttpServletRequest request, HttpServletResponse response, S\";s:7:\"subject\";s:49:\"<a href=\"space.jsp?uid=1&do=blog&id=4\">缓存</a>\";}','','','','','','','','','','',4,'blogid',0);
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
INSERT INTO `jchome_friend` (`uid`,`fuid`,`fusername`,`status`,`gid`,`note`,`num`,`dateline`) VALUES 
 (1,2,'cxl',1,0,'我是管理员哦',3,1316605610),
 (2,1,'admin',1,1,'',5,1316605610),
 (3,2,'cxl',1,0,'',0,1316608605),
 (2,3,'test',1,1,'',0,1316608605),
 (4,2,'cxl',1,0,'',0,1316610077),
 (2,4,'foo',1,1,'',1,1316610077);
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
INSERT INTO `jchome_friendlog` (`uid`,`fuid`,`action`,`dateline`) VALUES 
 (2,4,'add',1316610077);
/*!40000 ALTER TABLE `jchome_friendlog` ENABLE KEYS */;


--
-- Definition of table `jchome_gift`
--

DROP TABLE IF EXISTS `jchome_gift`;
CREATE TABLE `jchome_gift` (
  `giftid` int(4) NOT NULL AUTO_INCREMENT,
  `giftname` varchar(20) NOT NULL,
  `tips` varchar(20) NOT NULL,
  `price` int(4) NOT NULL,
  `buycount` int(4) NOT NULL,
  `icon` text NOT NULL,
  `addtime` int(10) NOT NULL,
  `typeid` varchar(20) NOT NULL,
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
  `mid` varchar(15) NOT NULL DEFAULT '',
  `name` varchar(30) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `forbiddengid` text NOT NULL,
  `charge` smallint(6) unsigned NOT NULL DEFAULT '0',
  `experience` smallint(6) unsigned NOT NULL DEFAULT '0',
  `provideperoid` int(10) unsigned NOT NULL DEFAULT '0',
  `providecount` smallint(6) unsigned NOT NULL DEFAULT '0',
  `useperoid` int(10) unsigned NOT NULL DEFAULT '0',
  `usecount` smallint(6) unsigned NOT NULL DEFAULT '0',
  `displayorder` smallint(6) unsigned NOT NULL DEFAULT '0',
  `custom` text NOT NULL,
  `close` tinyint(1) NOT NULL DEFAULT '0',
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
  `username` varchar(15) NOT NULL DEFAULT '',
  `mid` varchar(15) NOT NULL DEFAULT '',
  `count` smallint(6) unsigned NOT NULL DEFAULT '0',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `fromid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `credit` smallint(6) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`logid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `type` (`type`,`fromid`,`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_magicinlog`
--

/*!40000 ALTER TABLE `jchome_magicinlog` DISABLE KEYS */;
INSERT INTO `jchome_magicinlog` (`logid`,`uid`,`username`,`mid`,`count`,`type`,`fromid`,`credit`,`dateline`) VALUES 
 (1,2,'cxl','superstar',1,1,0,30,1316604859),
 (2,1,'admin','doodle',1,1,0,30,1316652900),
 (3,1,'admin','visit',1,1,0,20,1316666483),
 (4,1,'admin','doodle',1,1,0,30,1316666521),
 (5,1,'admin','thunder',5,1,0,2500,1316667902);
/*!40000 ALTER TABLE `jchome_magicinlog` ENABLE KEYS */;


--
-- Definition of table `jchome_magicstore`
--

DROP TABLE IF EXISTS `jchome_magicstore`;
CREATE TABLE `jchome_magicstore` (
  `mid` varchar(15) NOT NULL DEFAULT '',
  `storage` smallint(6) unsigned NOT NULL DEFAULT '0',
  `lastprovide` int(10) unsigned NOT NULL DEFAULT '0',
  `sellcount` int(8) unsigned NOT NULL DEFAULT '0',
  `sellcredit` int(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`mid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_magicstore`
--

/*!40000 ALTER TABLE `jchome_magicstore` DISABLE KEYS */;
INSERT INTO `jchome_magicstore` (`mid`,`storage`,`lastprovide`,`sellcount`,`sellcredit`) VALUES 
 ('superstar',998,1316580637,1,30),
 ('thunder',0,1316581121,5,2500),
 ('doodle',997,1316581717,2,60),
 ('anonymous',999,1316584606,0,0),
 ('gift',999,1316604818,0,0),
 ('invisible',10,1316605240,0,0),
 ('friendnum',999,1316605240,0,0),
 ('attachsize',999,1316605240,0,0),
 ('updateline',999,1316605240,0,0),
 ('downdateline',999,1316605240,0,0),
 ('color',999,1316605240,0,0),
 ('hot',999,1316605240,0,0),
 ('visit',998,1316605240,1,20),
 ('icon',999,1316605240,0,0),
 ('flicker',999,1316605240,0,0),
 ('viewmagiclog',999,1316605240,0,0),
 ('viewmagic',999,1316605240,0,0),
 ('viewvisitor',999,1316605240,0,0),
 ('call',999,1316605240,0,0),
 ('frame',999,1316605240,0,0),
 ('bgimage',999,1316605240,0,0),
 ('reveal',999,1316605240,0,0),
 ('license',999,1316605240,0,0),
 ('detector',999,1316605240,0,0);
/*!40000 ALTER TABLE `jchome_magicstore` ENABLE KEYS */;


--
-- Definition of table `jchome_magicuselog`
--

DROP TABLE IF EXISTS `jchome_magicuselog`;
CREATE TABLE `jchome_magicuselog` (
  `logid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `mid` varchar(15) NOT NULL DEFAULT '',
  `id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `idtype` varchar(20) NOT NULL DEFAULT '',
  `count` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `data` text NOT NULL,
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `expire` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`logid`),
  KEY `uid` (`uid`,`mid`),
  KEY `id` (`id`,`idtype`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_magicuselog`
--

/*!40000 ALTER TABLE `jchome_magicuselog` DISABLE KEYS */;
INSERT INTO `jchome_magicuselog` (`logid`,`uid`,`username`,`mid`,`id`,`idtype`,`count`,`data`,`dateline`,`expire`) VALUES 
 (1,2,'cxl','superstar',0,'',1,'',1316605833,1317210633),
 (2,1,'admin','doodle',0,'',2,'',1316666526,0),
 (3,1,'admin','visit',0,'',1,'',1316666491,0),
 (4,1,'admin','thunder',0,'',1,'',1316667903,0);
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
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_member`
--

/*!40000 ALTER TABLE `jchome_member` DISABLE KEYS */;
INSERT INTO `jchome_member` (`uid`,`username`,`password`,`blacklist`,`salt`) VALUES 
 (1,'admin','28c00ffa1c00044b8aeeedb5b91ed83f','','d1E9lN'),
 (2,'cxl','20ba47cb7a67bfd51f16876e4f120dcc','','yrNg3T'),
 (3,'test','2a8d98e3b7a453d56942e66170f4c619','','EYxp87'),
 (4,'foo','b90fe933eb73964cedeaa55b1aba0795','','cZtHgZ');
/*!40000 ALTER TABLE `jchome_member` ENABLE KEYS */;


--
-- Definition of table `jchome_mtag`
--

DROP TABLE IF EXISTS `jchome_mtag`;
CREATE TABLE `jchome_mtag` (
  `tagid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `tagname` varchar(40) NOT NULL DEFAULT '',
  `fieldid` smallint(6) NOT NULL DEFAULT '0',
  `membernum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `threadnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `postnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `close` tinyint(1) NOT NULL DEFAULT '0',
  `announcement` text NOT NULL,
  `pic` varchar(150) NOT NULL DEFAULT '',
  `closeapply` tinyint(1) NOT NULL DEFAULT '0',
  `joinperm` tinyint(1) NOT NULL DEFAULT '0',
  `viewperm` tinyint(1) NOT NULL DEFAULT '0',
  `threadperm` tinyint(1) NOT NULL DEFAULT '0',
  `postperm` tinyint(1) NOT NULL DEFAULT '0',
  `recommend` tinyint(1) NOT NULL DEFAULT '0',
  `moderator` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`tagid`),
  KEY `tagname` (`tagname`),
  KEY `threadnum` (`threadnum`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_mtag`
--

/*!40000 ALTER TABLE `jchome_mtag` DISABLE KEYS */;
INSERT INTO `jchome_mtag` (`tagid`,`tagname`,`fieldid`,`membernum`,`threadnum`,`postnum`,`close`,`announcement`,`pic`,`closeapply`,`joinperm`,`viewperm`,`threadperm`,`postperm`,`recommend`,`moderator`) VALUES 
 (1,'灌篮高手',1,2,0,0,0,'','',0,0,0,0,0,0,'');
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
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_notification`
--

/*!40000 ALTER TABLE `jchome_notification` DISABLE KEYS */;
INSERT INTO `jchome_notification` (`id`,`uid`,`type`,`new`,`authorid`,`author`,`note`,`dateline`) VALUES 
 (1,1,'blogcomment',0,2,'cxl','评论了你的日志 <a href=\"space.jsp?uid=1&do=blog&id=1&cid=2\" target=\"_blank\">代码列表</a>',1316584940),
 (2,1,'clickblog',0,2,'cxl','对你的日志 <a href=\"space.jsp?uid=1&do=blog&id=1\" target=\"_blank\">代码列表</a> 做了表态',1316584952),
 (3,1,'friend',0,2,'cxl','cp_note_friend_add',1316605610),
 (4,3,'friend',1,2,'cxl','cp_note_friend_add',1316608605),
 (5,1,'sharenotice',0,2,'cxl','cp_note_share_space',1316610193),
 (6,2,'comment',0,1,'admin','在留言板上给你<a href=\"space.jsp?uid=2&do=wall\" target=\"_blank\">留言</a>',1316666491),
 (7,2,'wall',0,1,'admin','在留言板上给你<a href=\"space.jsp?uid=2&do=wall&cid=5\" target=\"_blank\">留言</a>',1316666528);
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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_pic`
--

/*!40000 ALTER TABLE `jchome_pic` DISABLE KEYS */;
INSERT INTO `jchome_pic` (`picid`,`albumid`,`topicid`,`uid`,`username`,`dateline`,`postip`,`filename`,`title`,`type`,`size`,`filepath`,`thumb`,`remote`,`hot`,`click_6`,`click_7`,`click_8`,`click_9`,`click_10`,`magicframe`) VALUES 
 (1,1,0,2,'cxl',1316605340,'127.0.0.1','43315183f7f5cdbaf703a623.jpg','','jpeg',28467,'201109/21/2_1316605340GNIz.jpg',1,0,0,0,0,0,0,0,0),
 (2,1,0,2,'cxl',1316605341,'127.0.0.1','qq_7_e.jpg','','jpeg',47705,'201109/21/2_1316605341EBDF.jpg',1,0,0,0,0,0,0,0,0),
 (3,0,0,1,'admin',1316652914,'127.0.0.1','1_1316652914NRP4.jpg','','jpg',6738,'201109/22/1_1316652914NRP4.jpg',1,0,0,0,0,0,0,0,0),
 (4,0,0,1,'admin',1316666526,'127.0.0.1','1_13166665265Ebx.jpg','','jpg',7959,'201109/22/1_13166665265Ebx.jpg',1,0,0,0,0,0,0,0,0);
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
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `subject` char(80) NOT NULL DEFAULT '',
  `voternum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `replynum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `multiple` tinyint(1) NOT NULL DEFAULT '0',
  `maxchoice` tinyint(3) NOT NULL DEFAULT '0',
  `sex` tinyint(1) NOT NULL DEFAULT '0',
  `noreply` tinyint(1) NOT NULL DEFAULT '0',
  `credit` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `percredit` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `lastvote` int(10) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0',
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
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `ip` varchar(20) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `pic` varchar(255) NOT NULL DEFAULT '',
  `isthread` tinyint(1) NOT NULL DEFAULT '0',
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
  `title` varchar(80) NOT NULL DEFAULT '',
  `note` varchar(255) NOT NULL DEFAULT '',
  `formtype` varchar(20) NOT NULL DEFAULT '0',
  `inputnum` smallint(3) unsigned NOT NULL DEFAULT '0',
  `choice` text NOT NULL,
  `mtagminnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `manualmoderator` tinyint(1) NOT NULL DEFAULT '0',
  `manualmember` tinyint(1) NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) unsigned NOT NULL DEFAULT '0',
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
  `title` varchar(80) NOT NULL DEFAULT '',
  `note` varchar(255) NOT NULL DEFAULT '',
  `formtype` varchar(20) NOT NULL DEFAULT '0',
  `maxsize` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `required` tinyint(1) NOT NULL DEFAULT '0',
  `invisible` tinyint(1) NOT NULL DEFAULT '0',
  `allowsearch` tinyint(1) NOT NULL DEFAULT '0',
  `choice` text NOT NULL,
  `displayorder` tinyint(3) unsigned NOT NULL DEFAULT '0',
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
  `idtype` varchar(15) NOT NULL DEFAULT '',
  `new` tinyint(1) NOT NULL DEFAULT '0',
  `num` smallint(6) unsigned NOT NULL DEFAULT '0',
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
 (1,'admin','28c00ffa1c00044b8aeeedb5b91ed83f',1316678414,127000000,0),
 (2,'cxl','20ba47cb7a67bfd51f16876e4f120dcc',1316679636,127000000,0);
/*!40000 ALTER TABLE `jchome_session` ENABLE KEYS */;


--
-- Definition of table `jchome_share`
--

DROP TABLE IF EXISTS `jchome_share`;
CREATE TABLE `jchome_share` (
  `sid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `type` varchar(30) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `title_template` text NOT NULL,
  `body_template` text NOT NULL,
  `body_data` text NOT NULL,
  `body_general` text NOT NULL,
  `image` varchar(255) NOT NULL DEFAULT '',
  `image_link` varchar(255) NOT NULL DEFAULT '',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `hotuser` text NOT NULL,
  PRIMARY KEY (`sid`),
  KEY `uid` (`uid`,`dateline`),
  KEY `topicid` (`topicid`,`dateline`),
  KEY `hot` (`hot`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_share`
--

/*!40000 ALTER TABLE `jchome_share` DISABLE KEYS */;
INSERT INTO `jchome_share` (`sid`,`topicid`,`type`,`uid`,`username`,`dateline`,`title_template`,`body_template`,`body_data`,`body_general`,`image`,`image_link`,`hot`,`hotuser`) VALUES 
 (1,0,'space',2,'cxl',1316610193,'cp_share_space','<b>{username}</b><br>{reside}<br>{spacenote}','a:3:{s:8:\"username\";s:35:\"<a href=\"space.jsp?uid=1\">admin</a>\";s:6:\"reside\";s:0:\"\";s:9:\"spacenote\";s:42:\"<img src=\"image/face/12.gif\" class=\"face\">\";}','分享这个测试东东哦','data/avatar/noavatar_middle.gif','space.jsp?uid=1',0,'');
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
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `groupid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `credit` int(10) NOT NULL DEFAULT '0',
  `experience` int(10) NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `name` char(20) NOT NULL DEFAULT '',
  `namestatus` tinyint(1) NOT NULL DEFAULT '0',
  `videostatus` tinyint(1) NOT NULL DEFAULT '0',
  `domain` char(15) NOT NULL DEFAULT '',
  `friendnum` int(10) unsigned NOT NULL DEFAULT '0',
  `viewnum` int(10) unsigned NOT NULL DEFAULT '0',
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
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `updatetime` int(10) unsigned NOT NULL DEFAULT '0',
  `lastsearch` int(10) unsigned NOT NULL DEFAULT '0',
  `lastpost` int(10) unsigned NOT NULL DEFAULT '0',
  `lastlogin` int(10) unsigned NOT NULL DEFAULT '0',
  `lastsend` int(10) unsigned NOT NULL DEFAULT '0',
  `attachsize` int(10) unsigned NOT NULL DEFAULT '0',
  `addsize` int(10) unsigned NOT NULL DEFAULT '0',
  `addfriend` smallint(6) unsigned NOT NULL DEFAULT '0',
  `flag` tinyint(1) NOT NULL DEFAULT '0',
  `newpm` smallint(6) unsigned NOT NULL DEFAULT '0',
  `avatar` tinyint(1) NOT NULL DEFAULT '0',
  `regip` char(15) NOT NULL DEFAULT '',
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
 (1,1,5310,4838,'admin','',1,0,'',1,6,0,0,0,0,0,0,1,3,0,0,0,0,0,1316580173,1316676632,0,1316676632,1316665904,0,14697,0,0,1,0,0,'127.0.0.1',127000000,12,0,0,0),
 (2,6,859,588,'cxl','龙哦',1,0,'',3,4,0,0,0,0,0,0,2,1,1,0,0,0,1,1316584832,1316610193,0,1316610193,1316679634,0,76172,0,0,0,0,0,'127.0.0.1',127000000,3,0,0,0),
 (3,5,25,15,'test','',0,0,'',1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1316608553,0,0,0,1316608553,0,0,0,0,0,0,0,'127.0.0.1',127000000,0,0,0,0),
 (4,5,25,15,'foo','',0,0,'',1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1316610077,0,0,0,1316610077,0,0,0,0,0,0,0,'127.0.0.1',127000000,0,0,0,0);
/*!40000 ALTER TABLE `jchome_space` ENABLE KEYS */;


--
-- Definition of table `jchome_spacefield`
--

DROP TABLE IF EXISTS `jchome_spacefield`;
CREATE TABLE `jchome_spacefield` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `sex` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL DEFAULT '',
  `newemail` varchar(100) NOT NULL DEFAULT '',
  `emailcheck` tinyint(1) NOT NULL DEFAULT '0',
  `mobile` varchar(40) NOT NULL DEFAULT '',
  `qq` varchar(20) NOT NULL DEFAULT '',
  `msn` varchar(80) NOT NULL DEFAULT '',
  `msnrobot` varchar(15) NOT NULL DEFAULT '',
  `msncstatus` tinyint(1) NOT NULL DEFAULT '0',
  `videopic` varchar(32) NOT NULL DEFAULT '',
  `birthyear` smallint(6) unsigned NOT NULL DEFAULT '0',
  `birthmonth` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `birthday` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `blood` varchar(5) NOT NULL DEFAULT '',
  `marry` tinyint(1) NOT NULL DEFAULT '0',
  `birthprovince` varchar(20) NOT NULL DEFAULT '',
  `birthcity` varchar(20) NOT NULL DEFAULT '',
  `resideprovince` varchar(20) NOT NULL DEFAULT '',
  `residecity` varchar(20) NOT NULL DEFAULT '',
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
  `timeoffset` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_spacefield`
--

/*!40000 ALTER TABLE `jchome_spacefield` DISABLE KEYS */;
INSERT INTO `jchome_spacefield` (`uid`,`sex`,`email`,`newemail`,`emailcheck`,`mobile`,`qq`,`msn`,`msnrobot`,`msncstatus`,`videopic`,`birthyear`,`birthmonth`,`birthday`,`blood`,`marry`,`birthprovince`,`birthcity`,`resideprovince`,`residecity`,`note`,`spacenote`,`authstr`,`theme`,`nocss`,`menunum`,`css`,`privacy`,`friend`,`feedfriend`,`sendmail`,`magicstar`,`magicexpire`,`timeoffset`) VALUES 
 (1,1,'webmastor@yourdomain.com','',0,'','','','',0,'',2003,4,1,'B',2,'广东','珠海','广东','广州','<img src=\"image/face/12.gif\" class=\"face\">','<img src=\"image/face/12.gif\" class=\"face\">','','',0,0,'','a:3:{s:9:\"groupname\";a:1:{i:1;s:13:\"java的好友\";}s:4:\"feed\";a:21:{s:6:\"invite\";i:1;s:4:\"post\";i:1;s:4:\"task\";i:1;s:5:\"album\";i:1;s:5:\"click\";i:1;s:4:\"show\";i:1;s:4:\"join\";i:1;s:4:\"blog\";i:1;s:6:\"upload\";i:1;s:5:\"share\";i:1;s:4:\"poll\";i:1;s:9:\"spaceopen\";i:1;s:5:\"event\";i:1;s:6:\"thread\";i:1;s:4:\"mtag\";i:1;s:5:\"doing\";i:1;s:8:\"joinpoll\";i:1;s:6:\"credit\";i:1;s:7:\"comment\";i:1;s:6:\"friend\";i:1;s:7:\"profile\";i:1;}s:4:\"view\";a:12:{s:5:\"share\";i:0;s:5:\"index\";i:0;s:4:\"poll\";i:0;s:5:\"event\";i:0;s:5:\"album\";i:0;s:4:\"mtag\";i:0;s:5:\"doing\";i:0;s:4:\"feed\";i:0;s:4:\"blog\";i:0;s:4:\"wall\";i:0;s:6:\"friend\";i:0;s:7:\"profile\";i:0;}}','2','2','',0,0,''),
 (2,1,'469399609@qq.com','',0,'','','','',0,'',1998,1,1,'B',2,'广东','珠海','广东','广州','<img src=\"image/face/3.gif\" class=\"face\">','<img src=\"image/face/3.gif\" class=\"face\">','','',0,0,'','a:3:{s:9:\"groupname\";a:1:{i:1;s:10:\"java好友\";}s:4:\"feed\";a:21:{s:6:\"invite\";i:1;s:4:\"post\";i:1;s:4:\"task\";i:1;s:5:\"album\";i:1;s:5:\"click\";i:1;s:4:\"show\";i:1;s:4:\"join\";i:1;s:4:\"blog\";i:1;s:6:\"upload\";i:1;s:5:\"share\";i:1;s:4:\"poll\";i:1;s:9:\"spaceopen\";i:1;s:5:\"event\";i:1;s:6:\"thread\";i:1;s:4:\"mtag\";i:1;s:5:\"doing\";i:1;s:8:\"joinpoll\";i:1;s:6:\"credit\";i:1;s:7:\"comment\";i:1;s:6:\"friend\";i:1;s:7:\"profile\";i:1;}s:4:\"view\";a:12:{s:5:\"share\";i:0;s:5:\"index\";i:0;s:4:\"poll\";i:0;s:5:\"event\";i:0;s:5:\"album\";i:0;s:4:\"mtag\";i:0;s:5:\"doing\";i:0;s:4:\"feed\";i:0;s:4:\"blog\";i:0;s:4:\"wall\";i:0;s:6:\"friend\";i:0;s:7:\"profile\";i:0;}}','1,4,3','1,4,3','',1,1317210633,''),
 (3,0,'469399609@qq.com','',0,'','','','',0,'',0,0,0,'',0,'','','','','','','','',0,0,'','','2','2','',0,0,''),
 (4,0,'469399609@qq.com','',0,'','','','',0,'',0,0,0,'',0,'','','','','','','','',0,0,'','','2','2','',0,0,'');
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
) ENGINE=MyISAM AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_spaceinfo`
--

/*!40000 ALTER TABLE `jchome_spaceinfo` DISABLE KEYS */;
INSERT INTO `jchome_spaceinfo` (`infoid`,`uid`,`type`,`subtype`,`title`,`subtitle`,`friend`,`startyear`,`endyear`,`startmonth`,`endmonth`) VALUES 
 (49,2,'base','blood','','',0,0,0,0,0),
 (48,2,'base','residecity','','',0,0,0,0,0),
 (45,2,'base','birthcity','','',0,0,0,0,0),
 (47,2,'base','marry','','',0,0,0,0,0),
 (46,2,'base','birth','','',0,0,0,0,0),
 (26,2,'edu','','nb大学','理学',0,2011,0,0,0),
 (32,2,'work','','google','',0,2011,0,1,0),
 (33,2,'info','motto','','',0,0,0,0,0),
 (34,2,'info','sport','','',0,0,0,0,0),
 (35,2,'info','game','','',0,0,0,0,0),
 (36,2,'info','idol','','',0,0,0,0,0),
 (37,2,'info','tv','越狱','',0,0,0,0,0),
 (38,2,'info','music','','',0,0,0,0,0),
 (39,2,'info','wish','','',0,0,0,0,0),
 (40,2,'info','book','好书','',0,0,0,0,0),
 (41,2,'info','intro','code','',0,0,0,0,0),
 (42,2,'info','movie','科幻、悬疑、犯罪','',0,0,0,0,0),
 (43,2,'info','trainwith','美女','',0,0,0,0,0),
 (44,2,'info','interest','美女','',0,0,0,0,0),
 (50,1,'base','birthcity','','',0,0,0,0,0),
 (51,1,'base','birth','','',0,0,0,0,0),
 (52,1,'base','marry','','',0,0,0,0,0),
 (53,1,'base','residecity','','',0,0,0,0,0),
 (54,1,'base','blood','','',0,0,0,0,0);
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
 (20110921,4,4,0,0,5,3,2,0,0,1,0,0,2,0,0,0,0,0,0,0,0,2,1),
 (20110922,2,0,0,0,0,1,2,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0);
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
 (2,20110922,'login'),
 (1,20110922,'login');
/*!40000 ALTER TABLE `jchome_statuser` ENABLE KEYS */;


--
-- Definition of table `jchome_tag`
--

DROP TABLE IF EXISTS `jchome_tag`;
CREATE TABLE `jchome_tag` (
  `tagid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `tagname` char(30) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `blognum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `close` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tagid`),
  KEY `tagname` (`tagname`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_tag`
--

/*!40000 ALTER TABLE `jchome_tag` DISABLE KEYS */;
INSERT INTO `jchome_tag` (`tagid`,`tagname`,`uid`,`dateline`,`blognum`,`close`) VALUES 
 (1,'sns',1,1316584572,1,0),
 (2,'压缩工具',1,1316584674,1,0),
 (3,'gzip',2,1316607783,1,0),
 (4,'过滤器',2,1316607783,1,0),
 (5,'缓存',1,1316676632,1,0);
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
INSERT INTO `jchome_tagblog` (`tagid`,`blogid`) VALUES 
 (1,1),
 (2,2),
 (3,3),
 (4,3),
 (5,4);
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
INSERT INTO `jchome_tagspace` (`tagid`,`uid`,`username`,`grade`) VALUES 
 (1,2,'cxl',9),
 (1,1,'admin',0);
/*!40000 ALTER TABLE `jchome_tagspace` ENABLE KEYS */;


--
-- Definition of table `jchome_task`
--

DROP TABLE IF EXISTS `jchome_task`;
CREATE TABLE `jchome_task` (
  `taskid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL DEFAULT '',
  `note` text NOT NULL,
  `num` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `maxnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `image` varchar(150) NOT NULL DEFAULT '',
  `filename` varchar(50) NOT NULL DEFAULT '',
  `starttime` int(10) unsigned NOT NULL DEFAULT '0',
  `endtime` int(10) unsigned NOT NULL DEFAULT '0',
  `nexttime` int(10) unsigned NOT NULL DEFAULT '0',
  `nexttype` varchar(20) NOT NULL DEFAULT '',
  `credit` smallint(6) NOT NULL DEFAULT '0',
  `displayorder` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`taskid`),
  KEY `displayorder` (`displayorder`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_task`
--

/*!40000 ALTER TABLE `jchome_task` DISABLE KEYS */;
INSERT INTO `jchome_task` (`taskid`,`available`,`name`,`note`,`num`,`maxnum`,`image`,`filename`,`starttime`,`endtime`,`nexttime`,`nexttype`,`credit`,`displayorder`) VALUES 
 (1,1,'更新一下自己的头像','头像就是你在这里的个人形象。<br>设置自己的头像后，会让更多的朋友记住您。',1,0,'image/task/avatar.gif','avatar.jsp',0,0,0,'',20,1),
 (2,1,'将个人资料补充完整','把自己的个人资料填写完整吧。<br>这样您会被更多的朋友找到的，系统也会帮您找到朋友。',1,0,'image/task/profile.gif','profile.jsp',0,0,0,'2',20,0),
 (3,1,'发表自己的第一篇日志','现在，就写下自己的第一篇日志吧。<br>与大家一起分享自己的生活感悟。',2,0,'image/task/blog.gif','blog.jsp',0,0,0,'',5,3),
 (4,1,'寻找并添加五位好友','有了好友，您发的日志、图片等会被好友及时看到并传播出去；<br>您也会在首页方便及时的看到好友的最新动态。',0,0,'image/task/friend.gif','friend.jsp',0,0,0,'',50,4),
 (5,1,'验证激活自己的邮箱','填写自己真实的邮箱地址并验证通过。<br>您可以在忘记密码的时候使用该邮箱取回自己的密码；<br>还可以及时接受站内的好友通知等等。',0,0,'image/task/email.gif','email.jsp',0,0,0,'',10,5),
 (6,1,'邀请10个新朋友加入','邀请一下自己的QQ好友或者邮箱联系人，让亲朋好友一起来加入我们吧。',0,0,'image/task/friend.gif','invite.jsp',0,0,0,'',100,6),
 (7,1,'领取每日访问大礼包','每天登录访问自己的主页，就可领取大礼包。',1,0,'image/task/gift.gif','gift.jsp',0,0,0,'day',5,99);
/*!40000 ALTER TABLE `jchome_task` ENABLE KEYS */;


--
-- Definition of table `jchome_thread`
--

DROP TABLE IF EXISTS `jchome_thread`;
CREATE TABLE `jchome_thread` (
  `tid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `topicid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tagid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `eventid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `subject` char(80) NOT NULL DEFAULT '',
  `magiccolor` tinyint(6) unsigned NOT NULL DEFAULT '0',
  `magicegg` tinyint(6) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `viewnum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `replynum` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `lastpost` int(10) unsigned NOT NULL DEFAULT '0',
  `lastauthor` char(15) NOT NULL DEFAULT '',
  `lastauthorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `digest` tinyint(1) NOT NULL DEFAULT '0',
  `hot` mediumint(8) unsigned NOT NULL DEFAULT '0',
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
  `gid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `grouptitle` varchar(20) NOT NULL DEFAULT '',
  `system` tinyint(1) NOT NULL DEFAULT '0',
  `banvisit` tinyint(1) NOT NULL DEFAULT '0',
  `explower` int(10) NOT NULL DEFAULT '0',
  `maxfriendnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `maxattachsize` int(10) unsigned NOT NULL DEFAULT '0',
  `allowhtml` tinyint(1) NOT NULL DEFAULT '0',
  `allowcomment` tinyint(1) NOT NULL DEFAULT '0',
  `searchinterval` smallint(6) unsigned NOT NULL DEFAULT '0',
  `searchignore` tinyint(1) NOT NULL DEFAULT '0',
  `postinterval` smallint(6) unsigned NOT NULL DEFAULT '0',
  `spamignore` tinyint(1) NOT NULL DEFAULT '0',
  `videophotoignore` tinyint(1) NOT NULL DEFAULT '0',
  `allowblog` tinyint(1) NOT NULL DEFAULT '0',
  `allowdoing` tinyint(1) NOT NULL DEFAULT '0',
  `allowupload` tinyint(1) NOT NULL DEFAULT '0',
  `allowshare` tinyint(1) NOT NULL DEFAULT '0',
  `allowmtag` tinyint(1) NOT NULL DEFAULT '0',
  `allowthread` tinyint(1) NOT NULL DEFAULT '0',
  `allowpost` tinyint(1) NOT NULL DEFAULT '0',
  `allowcss` tinyint(1) NOT NULL DEFAULT '0',
  `allowpoke` tinyint(1) NOT NULL DEFAULT '0',
  `allowfriend` tinyint(1) NOT NULL DEFAULT '0',
  `allowpoll` tinyint(1) NOT NULL DEFAULT '0',
  `allowclick` tinyint(1) NOT NULL DEFAULT '0',
  `allowevent` tinyint(1) NOT NULL DEFAULT '0',
  `allowmagic` tinyint(1) NOT NULL DEFAULT '0',
  `allowpm` tinyint(1) NOT NULL DEFAULT '0',
  `allowviewvideopic` tinyint(1) NOT NULL DEFAULT '0',
  `allowmyop` tinyint(1) NOT NULL DEFAULT '0',
  `allowtopic` tinyint(1) NOT NULL DEFAULT '0',
  `allowstat` tinyint(1) NOT NULL DEFAULT '0',
  `magicdiscount` tinyint(1) NOT NULL DEFAULT '0',
  `verifyevent` tinyint(1) NOT NULL DEFAULT '0',
  `edittrail` tinyint(1) NOT NULL DEFAULT '0',
  `domainlength` smallint(6) unsigned NOT NULL DEFAULT '0',
  `closeignore` tinyint(1) NOT NULL DEFAULT '0',
  `seccode` tinyint(1) NOT NULL DEFAULT '0',
  `color` varchar(10) NOT NULL DEFAULT '',
  `icon` varchar(100) NOT NULL DEFAULT '',
  `manageconfig` tinyint(1) NOT NULL DEFAULT '0',
  `managenetwork` tinyint(1) NOT NULL DEFAULT '0',
  `manageprofilefield` tinyint(1) NOT NULL DEFAULT '0',
  `manageprofield` tinyint(1) NOT NULL DEFAULT '0',
  `manageusergroup` tinyint(1) NOT NULL DEFAULT '0',
  `managefeed` tinyint(1) NOT NULL DEFAULT '0',
  `manageshare` tinyint(1) NOT NULL DEFAULT '0',
  `managedoing` tinyint(1) NOT NULL DEFAULT '0',
  `manageblog` tinyint(1) NOT NULL DEFAULT '0',
  `managetag` tinyint(1) NOT NULL DEFAULT '0',
  `managetagtpl` tinyint(1) NOT NULL DEFAULT '0',
  `managealbum` tinyint(1) NOT NULL DEFAULT '0',
  `managecomment` tinyint(1) NOT NULL DEFAULT '0',
  `managemtag` tinyint(1) NOT NULL DEFAULT '0',
  `managethread` tinyint(1) NOT NULL DEFAULT '0',
  `manageevent` tinyint(1) NOT NULL DEFAULT '0',
  `manageeventclass` tinyint(1) NOT NULL DEFAULT '0',
  `managecensor` tinyint(1) NOT NULL DEFAULT '0',
  `managead` tinyint(1) NOT NULL DEFAULT '0',
  `managesitefeed` tinyint(1) NOT NULL DEFAULT '0',
  `managebackup` tinyint(1) NOT NULL DEFAULT '0',
  `manageblock` tinyint(1) NOT NULL DEFAULT '0',
  `managetemplate` tinyint(1) NOT NULL DEFAULT '0',
  `managestat` tinyint(1) NOT NULL DEFAULT '0',
  `managecache` tinyint(1) NOT NULL DEFAULT '0',
  `managecredit` tinyint(1) NOT NULL DEFAULT '0',
  `managecron` tinyint(1) NOT NULL DEFAULT '0',
  `managename` tinyint(1) NOT NULL DEFAULT '0',
  `manageapp` tinyint(1) NOT NULL DEFAULT '0',
  `managetask` tinyint(1) NOT NULL DEFAULT '0',
  `managereport` tinyint(1) NOT NULL DEFAULT '0',
  `managepoll` tinyint(1) NOT NULL DEFAULT '0',
  `manageclick` tinyint(1) NOT NULL DEFAULT '0',
  `managemagic` tinyint(1) NOT NULL DEFAULT '0',
  `managemagiclog` tinyint(1) NOT NULL DEFAULT '0',
  `managebatch` tinyint(1) NOT NULL DEFAULT '0',
  `managedelspace` tinyint(1) NOT NULL DEFAULT '0',
  `managetopic` tinyint(1) NOT NULL DEFAULT '0',
  `manageip` tinyint(1) NOT NULL DEFAULT '0',
  `managehotuser` tinyint(1) NOT NULL DEFAULT '0',
  `managedefaultuser` tinyint(1) NOT NULL DEFAULT '0',
  `managespacegroup` tinyint(1) NOT NULL DEFAULT '0',
  `managespaceinfo` tinyint(1) NOT NULL DEFAULT '0',
  `managespacecredit` tinyint(1) NOT NULL DEFAULT '0',
  `managespacenote` tinyint(1) NOT NULL DEFAULT '0',
  `managevideophoto` tinyint(1) NOT NULL DEFAULT '0',
  `managelog` tinyint(1) NOT NULL DEFAULT '0',
  `magicaward` text NOT NULL,
  `allowgift` tinyint(1) NOT NULL DEFAULT '0',
  `managegift` tinyint(1) NOT NULL DEFAULT '0',
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
  `username` char(15) NOT NULL DEFAULT '',
  `mid` varchar(15) NOT NULL DEFAULT '',
  `count` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`mid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jchome_usermagic`
--

/*!40000 ALTER TABLE `jchome_usermagic` DISABLE KEYS */;
INSERT INTO `jchome_usermagic` (`uid`,`username`,`mid`,`count`) VALUES 
 (2,'cxl','superstar',0),
 (1,'admin','doodle',0),
 (1,'admin','visit',0),
 (1,'admin','thunder',4);
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
INSERT INTO `jchome_usertask` (`uid`,`username`,`taskid`,`credit`,`dateline`,`isignore`) VALUES 
 (2,'cxl',5,10,1316605292,1),
 (2,'cxl',2,20,1316607730,0),
 (2,'cxl',3,5,1316607790,0),
 (2,'cxl',7,5,1316607825,0),
 (2,'cxl',1,20,1316607912,0),
 (1,'admin',3,5,1316676596,0);
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
INSERT INTO `jchome_visitor` (`uid`,`vuid`,`vusername`,`dateline`) VALUES 
 (2,1,'admin',1316666495),
 (1,2,'cxl',1316610179),
 (4,2,'cxl',1316610136);
/*!40000 ALTER TABLE `jchome_visitor` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
