<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.terry.weddingphoto.data.intf.IPhotoDao"%>
<%@page import="com.terry.weddingphoto.data.impl.PhotoDaoImpl"%>
<%@page import="java.util.List"%>

<%@page import="com.terry.weddingphoto.model.Photo"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.TimeZone"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>照片测试</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/wbox.css" />

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/jquery.galleryview-1.1.js"></script>
<script type="text/javascript" src="js/jquery.timers-1.1.2.js"></script>
<script type="text/javascript" src="js/wbox.js"></script>
<script type="text/javascript" src="js/index.js"></script>

<style type="text/css">
body {
	background: #444;
	color: white;
}

#gallery_wrap { /*width: 820px;
	height: 368px;
	padding: 25px;
	background: url(images/border.png) top left no-repeat;*/
	
}

a:link,a:visited {
	color: #ddd !important;
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

h3 {
	border-bottom-color: white;
}

.filmstripd {
	display: table-cell;
	vertical-align: middle;
	width: 100px;
	height: 80px;
}

.filmstripd img {
	vertical-align: middle;
	display: none;
}

.commentcount {
	color: red;
	font-weight: bold;
}
</style>
</head>

<body>
<div id="head">
<%
	UserService userService = UserServiceFactory.getUserService();
	String welcome = userService.isUserLoggedIn() ? userService
			.getCurrentUser().getEmail()
			+ " | <a href='"
			+ userService.createLogoutURL("/")
			+ "'>退出</a>" : "<a href='"
			+ userService.createLoginURL("/") + "'>登录</a>";
	welcome += "&nbsp;|&nbsp;<a href='admin'>后台管理</a>&nbsp;|&nbsp;";
%>
<h3><span style="color: pink">照片测试</span>
<div align="right" style="font-size: 11px;"><%=welcome%>程序设计:<a
	target="_blank" href="http://xinghuo.org.ru/">Terry</a>&nbsp;|&nbsp;<a
	target="_blank"
	href="http://code.google.com/p/terrycode/source/browse/#svn/trunk/wedding-photo">源码</a>&nbsp;<img
	src="images/slideup.png" class="control"
	style="cursor: pointer; vertical-align: bottom;"></img></div>
</h3>
</div>
<div id="gallery_wrap" align="center">
<div id='loading-div'><img src="images/loading.gif" />照片加载中,请稍候...</div>
<div id="photos" style="display: none;" class="galleryview">
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.CHINA);
	sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	IPhotoDao photoDao = new PhotoDaoImpl();
	List<Photo> photos = photoDao.getPhotos(0, 150);
	int iter = 0;
	for (Photo photo : photos) {
		String pid = photo.getId();
		String desc = "<span id='c-" + pid + "'>";
		int comment = photo.getComment();
		if (comment == -1) {
			desc += "评论已关闭";
		} else if (comment == 0)
			desc += "暂无评论";
		else if (comment > 0) {
			desc += "共有 <span class='commentcount'>" + comment
					+ "</span> 条评论";
		}
		desc += "</span>,&nbsp;<a class='wbox' href='detail.jsp?pid="
				+ pid
				+ "&admin=false'>评论|详情</a>&nbsp;<a class='wbox2' href=# pid='"
				+ pid + "'>查看原图</a>";
%>
<div class="panel"><img style="display: none; cursor: auto;"
	usemap="#photonav" border="none" pid="<%=pid%>" iter="<%=iter++%>" />
<div class="panel-overlay">
<h2 id="r-<%=pid%>"><%=photo.getRemark()%></h2>
<p><%=desc%></p>
</div>
</div>
<%
	}
%> <map name="photonav">
	<area id="photonav1" shape="rect" coords="0,0,100,100" alt="上一张"
		title="上一张" />
	<area id="photonav2" shape="rect" coords="100,0,200,100" alt="下一张"
		title="下一张" />
</map>
<ul class="filmstrip">
	<%
		iter = 0;
		for (Photo photo : photos) {
	%>
	<li iter="<%=iter%>">
	<div class="filmstripd"><img pid="<%=photo.getId()%>"
		id="i-<%=photo.getId()%>" iter="<%=iter++%>"
		alt="<%=photo.getFilename()%>"
		title="<%="".equals(photo.getRemark()) ? photo
						.getFilename() : photo.getRemark()%>" /></div>
	</li>
	<%
		}
	%>
</ul>
</div>
</div>
<%
	if (iter == 0) {
%>
相册中还没有图片,
<a href="admin">登录后台</a>
以上传图片!
<%
	}
%>
<div class="help" id='help-div'>小提示：您可以点击大图片的左、右部分、键盘方向键、空格键等
来向前或向后翻页 <a href='#' onclick="closeHelp();return false;"><img
	src='images/close.gif' alt='关闭' title='关闭' style='border: none;' /></a></div>
<jsp:include page="analytics.html"></jsp:include>
</body>
</html>
