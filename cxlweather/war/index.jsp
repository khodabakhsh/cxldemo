<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="google-site-verification" content="xXYWS3e5fl1xrP2hWPOTexfG5ovwHF2whsRWdIFwcKs" />
<meta name="description"
	content="每天定时向指定邮箱发送天气预报邮件，借助手机邮箱（139邮箱，联通邮箱，189邮箱）提供的邮件到达手机短信提醒，实现免费天气预报短信定制" />
<link rel="stylesheet" type="text/css" href="css/core.css" />
<%
	UserService userService = UserServiceFactory.getUserService();
	boolean login = userService.isUserLoggedIn();
	if (login) {
%>
<link rel="stylesheet" type="text/css" href="css/flexigrid.css" />
<link rel="stylesheet" type="text/css" href="css/flexigrid-ext.css" />
<link rel="stylesheet" type="text/css" href="css/thickbox.css" />

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/flexigrid.pack.js"></script>
<script type="text/javascript" src="js/thickbox.js"></script>
<%
	}
%>

<title>免费天气预报邮件短信定制</title>
</head>
<body topmargin=3 marginheight=3>

<div id="msg"
	style="visibility: hidden; z-index: 1000; position: absolute; left: 300px;">
<table border='0' cellspacing='0' cellpadding='0'>
	<tr>
		<td style="padding: 4px; background-color: #fad163;"><font
			size="-1"><span id="msg_content"></span>&nbsp;<a href="#"
			onclick="clearMsg();return false;"><img id="close" alt="关闭"
			title="关闭" style="border: none;" src='images/close.gif' /></a></font></td>
	</tr>
</table>
</div>

<textarea id=csi style="display: none"></textarea>
<div id=gbar><b class=gb1>天气预报</b> <a
	href="http://fetion.xinghuo.org.ru/" target="_blank" class=gb1>网页飞信</a>
<a href="http://www.websms.org.ru/" target="_blank" class=gb1>GV网页短信</a>
<a href="http://xinghuo.org.ru/" target="_blank" class=gb1>我的博客</a></div>
<div id=guser width=100%><%=login ? userService.getCurrentUser().getEmail()
					+ " | " : ""%><a
	href="<%=login ? userService.createLogoutURL("/")
					: userService.createLoginURL("/")%>"><%=login ? "退出"
					: "<strong style='COLOR: blue'>登录</strong>"%></a></div>
<div class=gbh style="left: 0"></div>
<div class=gbh style="right: 0"></div>


<div class="bborderx">
<table id="flex1" style="display: none"></table>
</div>

<%
	if (login) {
%>
<script type="text/javascript" src="js/weather.js"></script>

<div style="display: none;"><input id="newSchedule"
	alt="#TB_inline?height=210&width=356&inlineId=hiddenModalContent&;modal=false"
	title="<b>新建天气预报定制</b>" class="thickbox" type="button" value="Show" /></div>

<div id="hiddenModalContent" style="display: none;">
<form id="scheduleForm">
<table border="0">
	<tr>
		<td colspan="2" align="center">&nbsp;<span id="message"
			style="display: none; color: red; font-weight: bold;"></span></td>
	</tr>
	<tr>
		<td style="width: 100px;">*发送时间：</td>
		<td align="left"><select name="sdate_hour" id="sdate_hour"
			style="width: 70px;">
			<%
				for (int i = 0; i < 24; i++) {
			%>
			<option <%=i == 20 ? "selected" : ""%>
				value="<%=i < 10 ? ("0" + i) : i%>"><%=i < 10 ? ("0" + i) : i%>
			时</option>
			<%
				}
			%>
		</select>&nbsp;&nbsp;<select name="sdate_minute" id="sdate_minute"
			style="width: 70px;">
			<%
				for (int i = 0; i < 12; i++) {
			%>
			<option value="<%=i < 2 ? ("0" + i * 5) : i * 5%>"><%=i < 2 ? ("0" + i * 5) : i * 5%>
			分</option>
			<%
				}
			%>
		</select></td>
	</tr>
	<tr>
		<td>*发送状态：</td>
		<td><select name="type" id="type" style="width: 148px;">
			<option value="1" selected="selected">天气内容放正文</option>
			<option value="2">天气内容放主题</option>
			<option value="0">暂时停用</option>
		</select></td>
	</tr>
	<tr>
		<td>*预报天数：</td>
		<td><select name="days" id="days" style="width: 148px;">
			<option value="0" selected="selected">[系统默认]</option>
			<option value="1">预报未来 1 天天气</option>
			<option value="2">预报未来 2 天天气</option>
			<option value="3">预报未来 3 天天气</option>
			<option value="4">预报未来 4 天天气</option>
			<option value="5">预报未来 5 天天气</option>
		</select></td>
	</tr>
	<tr>
		<td>*接收邮箱：</td>
		<td><input name="email" id="email" value="@139.com"
			style="width: 180px;" /></td>
	</tr>
	<tr>
		<td>*定制城市：</td>
		<td><input name="city" id="city" style="width: 180px;"
			maxlength="12" /></td>
	</tr>
	<tr>
		<td>备注：</td>
		<td><input name="remark" id="remark" maxlength="100"
			style="width: 180px;" /></td>
	</tr>
	<tr>
		<td colspan="2"><input type="hidden" id="sid" value="" /></td>
	</tr>
	<tr>
		<td width="100px;"></td>
		<td><input type="button" value="保存" id="scheduleSave"
			style="width: 60px;" />&nbsp;&nbsp;<input type="button" value="发送测试邮件" id="testEmail"
			style="width: 100px;" /></td>
	</tr>
</table>
</form>
</div>
<br />
<div style="font-weight: bold; line-height: 20px;">
<div>帐户信息：设置发送邮件时的昵称 <input type="text" id="nickname"
	maxlength="12" /> <input id="updateNickname" type="button" value="更改"
	style="width: 60px;" /></div>
<div>限额信息：您的账户定制限额为 <span id="slimit"
	style="color: blue; font-weight: bold;">10</span>，已定制数为 <span
	id="count" style="color: blue; font-weight: bold;">—</span>；本站总定制限额为 <span
	style="color: blue; font-weight: bold;">2000</span>，现有总定制数为 <span
	id="total" style="color: blue; font-weight: bold;">—</span>&nbsp;&nbsp;<input
	id="refreshCount" type="button" value="刷新" style="width: 60px;" disabled="disabled"/>&nbsp;&nbsp;<span
	id="count_loading" style="visibility: visible;"><img
	src="images/load.gif" /></span> <br />
本站总限额到达后，若您个人账户限额未满，也将无法添加新的定制；后续会考虑开设分站，有任何问题请到<a target='_blank'
	href='http://xinghuo.org.ru/2010/03/gae.html'>http://xinghuo.org.ru</a>留言或关注。
</div>

</div>
<br />
<br />
<%
	} else {
%>
<br />
<p align="center"><font size="5">天气预报邮件定制</font></p>
<font color='green'>提示：为了方便您今后管理和取消订阅等操作，需要用Google帐号<a
	href="<%=userService.createLoginURL("/")%>">登录</a>，Google仅提供登录授权，不会共享您的密码给本站</font>
<div style="font-weight: bold; line-height: 20px;">
<div>本站总定制限额为 <span
	style="color: blue; font-weight: bold;">2000</span>，现有总定制数为 <span
	id="nl_total" style="color: blue; font-weight: bold;">—</span>&nbsp;&nbsp;<input
	id="nl_refreshCount" type="button" value="刷新" style="width: 60px;" disabled="disabled" onclick="nl_refreshCount();"/>&nbsp;&nbsp;<span
	id="nl_count_loading" style="visibility: visible;"><img
	src="images/load.gif" /></span> <br />
本站总限额到达后，会考虑开设分站，有任何问题请到<a target='_blank'
	href='http://xinghuo.org.ru/2010/03/gae.html'>http://xinghuo.org.ru</a>留言或关注。
</div>
</div> 
	<script type="text/javascript" src="js/nl_weather.js"></script>
<%
	}
%>
<p align="left">
<font color="blue">2010-05-26 更新第二版：更换了更加权威的天气预报数据采集来源，天气预报更加准确，同时支持国外城市！</font><br/>
<strong><font size="4">功能：</font><br />
&nbsp; &nbsp; 每天定时向指定邮箱发送天气预报邮件，借助手机邮箱（<a href="http://mail.139.com/"
	target="_blank">139邮箱</a>，<a href="http://mail.wo.com.cn"
	target="_blank">联通邮箱</a>，<a href="http://www.189.cn/webmail/"
	target="_blank">189邮箱</a>）提供的邮件到达手机短信提醒，实现天气预报短信定制</strong><br />
<strong><font size="4">特点：<br />
</font></strong> &nbsp; &nbsp; 一个帐号可定制多个城市的天气预报或多个好友的邮箱<br />
&nbsp; &nbsp; 可自定义每天发送时间、可选择天气预报内容放入邮件正文或主题、可暂停预报<br />
&nbsp; &nbsp; 可自定义预报天气情况的天数，默认天气内容放主题3天，放正文5天<br />
&nbsp; &nbsp; 部署于GAE免费的服务器，定时期限无限长（只要Google App Engine没倒闭）<br />
<strong><font size="4">说明：<br />
</font></strong>&nbsp; &nbsp; 1.定时只能精确到5分钟（若遇到发送高峰也会相应延后）<br />
&nbsp; &nbsp; 2.暂时只能定制10条邮件天气预报列表，有更多需要可以联系我<br />
&nbsp; &nbsp; 3.<b>据统计，大部分网友选择在 <b style="color: blue">8:00</b> 
<b style="color: blue">18:00</b> <b style="color: blue">20:00</b> 等时间发送邮件，
发送列队众多会导致发送不及时，您也可以选择比较不常见的时间发送</b><br />
&nbsp; &nbsp; 4.<b>若使用139邮箱，会有一个“手机接收时间段”的设置，请 注意设置定制时间在该范围内</b><br />
&nbsp; &nbsp; 5.为了方便您今后管理和取消订阅，需要登录到您的Google帐号<br />
&nbsp; &nbsp; 6.新建或修改定制中的“备注”是为方便管理多个定制时的说明，并不会附加在邮件或短信内容中<br />
<br />
程序设计：<a href="http://xinghuo.org.ru/" target="_blank"><font
	color="#800080">Terry</font></a> <a
	href="http://code.google.com/p/terrycode/source/browse/#svn/trunk/weatherlib2"
	target="_blank"><font color="#800080">源代码</font></a> 企划：<a
	href="http://liming.net.ru/" target="_blank"><font color="#800080">黎明破晓</font></a><br />
其他作品： <a href="http://fetion.xinghuo.org.ru/" target="_blank"><font
	color="#800080">网页飞信</font></a> <a href="http://feix.org.ru/"
	target="_blank"><font color="#800080">网页飞信</font></a></p>
<br/>


<p align="center"><img
	src="http://code.google.com/appengine/images/appengine-silver-120x30.gif" /></p>
</body>
</html>
