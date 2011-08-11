<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.TimeZone"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="com.terry.weddingphoto.data.impl.PhotoDaoImpl"%>
<%@page import="com.terry.weddingphoto.data.intf.IPhotoDao"%>
<%@page import="java.util.List"%>
<%@page import="com.terry.weddingphoto.model.Comment"%>
<%@page import="com.terry.weddingphoto.model.Photo"%>
<%@page import="com.terry.weddingphoto.servlet.AdminServlet"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>照片测试</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/jquery.uploadify.v2.1.0.min.js"></script>
<script type="text/javascript" src="js/detail.js"></script>

<link rel="stylesheet" href="css/uploadify.css" type="text/css" />
<style type="text/css">
body {
	background: #444;
	color: white;
}

a:link,a:visited {
	color: #ddd !important;
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

.cnick {
	color : #f05d0c;
	min-width: 300px;
}

.ccdate {
	color : #ddd;
}

.ccont {
	color : #ee07fc;
	font-weight: bold;
}

.uploadifyQueueItem {
	color: #black;
}

</style>
</head>

<body>
<div id="msg"
	style="visibility: hidden; z-index: 1000; position: absolute; left: 300px;">
<table border='0' cellspacing='0' cellpadding='0'>
	<tr>
		<td style="padding: 4px; background-color: #fad163;color: black;"><font
			size="-1"><span id="msg_content"></span>&nbsp;<a href="#"
			onclick="clearMsg();return false;"><img id="close" alt="关闭"
			title="关闭" style="border: none;" src='images/close.gif' /></a></font></td>
	</tr>
</table>
</div>
<div align="right" style="font-size: 11px;">程序设计<a target="_blank"
	href="http://xinghuo.org.ru/">Terry</a>&nbsp;<a target="_blank"
	href="http://code.google.com/p/terrycode/source/browse/#svn/trunk/wedding-photo">源码</a>&nbsp;<a
	href="admin" target="_blank">后台管理</a></div>
<div id="cc">
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.CHINA);
	sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	UserService userService = UserServiceFactory.getUserService();
	boolean admin = userService.isUserLoggedIn()&&userService.isUserAdmin();
	String uuid = null;
	if(admin){
		uuid = AdminServlet.generateUUID();
	}
	String username=null;
	String email=null;
	if(userService.isUserLoggedIn()){
		username = userService.getCurrentUser().getNickname();
		email = userService.getCurrentUser().getEmail();
		if(username.indexOf("@")!=-1)
			username = username.substring(0,username.indexOf("@"));
	}
	IPhotoDao photoDao = new PhotoDaoImpl();
	String pid = request.getParameter("pid");
	Photo photo = photoDao.getPhotoById(pid);
	List<Comment> comments = photoDao.getCommentsByPhotoId(pid,0,0);
	for (Comment c : comments) {
		String cid = c.getId();
%>
<input type="hidden" value="<%=pid %>" id="pid"/> 
<li id="li-<%=cid %>"><p><span class="cnick"><%=c.getName()%></span>&nbsp;<span class="ccdate"><%=sdf.format(c.getCdate())%></span>&nbsp;说：</p><span class="ccont"><%=c.getContent()%></span>
	<%=admin?"<a href=# onclick='deleteComment(\""+cid+"\");return false;'>[删除]</a>":"" %>
<br/></li>
</div>
<%
	}
%>
<script type="text/javascript">
	var admin = <%=admin %>;
	pid = '<%=pid %>';
	uuid = '<%=uuid %>';
	var canComment = <%=photo.getComment()!=-1 %>;
	sadmin = '<%=request.getParameter("admin") %>';
	var premark = '<%=photo.getRemark() %>';
</script>
<%
if(photo.getComment()!=-1){
%>
<div>
<br/>
<fieldset>
    <legend>发布评论</legend>
<form>
<table>
	<tr>
		<td>*您的昵称</td>
		<td><input type="text" id="nickname" maxlength="12" value="<%=username==null?"":username %>"/></td>
		<td rowspan="2">
			<img src="view?id=<%=pid %>&w=100&h=80" id="thumbnail"/>
		</td>
	</tr>
	<tr>
		<td>Email(选填)</td>
		<td><input type="text" id="email" value="<%=email==null?"":email %>"/></td>
	</tr>
	<tr>
		<td>*评论内容</td>
		<td colspan="2"><textarea rows="6" cols="35" id="ccontent"></textarea> </td>
	</tr>
	<tr>
		<td></td>
		<td colspan="2"><input type="button" value="发布评论" id="commentSave"
			style="width: 80px;"/> </td>
	</tr>
</table>
</form>
</fieldset>
</div>

<%
}
	if(admin){
%>
<br/>
<fieldset>
    <legend>更新照片</legend>
<table>
	<tr>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td>选择照片</td>
		<td><input id="photoInputs" type="file" name="file"></input><div style="color:white;" id="fileQueue"></div></td>
	</tr>
	<tr>
		<td></td>
		<td><input type="button" value="开始更新" onclick="javascript:$('#photoInputs').uploadifyUpload();" style="width: 100px;"/> </td>
	</tr>
</table>
</fieldset>
<br/>
<fieldset>
    <legend>管理照片设置</legend>
<table>
	<tr>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td>照片简短说明</td>
		<td><textarea rows="4" cols="35" id="premark"></textarea></td>
	</tr>
	<tr>
		<td>是否允许评论</td>
		<td><input type="checkbox" value="cancomment" id="cancomment"/>允许评论</td>
	</tr>
	<tr>
		<td></td>
		<td><input type="button" value="更新设置" id="photoUpdate"
			style="width: 100px;"/> </td>
	</tr>
</table>
</fieldset>
<%
}
%>
<jsp:include page="analytics.html"></jsp:include>
</body>
</html>
