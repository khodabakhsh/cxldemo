<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>相册后台管理</title>
<link rel="stylesheet" href="css/admin.css" type="text/css" />
<link rel="stylesheet" href="css/flexigrid.css" type="text/css" />
<link rel="stylesheet" href="css/uploadify.jGrowl.css" type="text/css" />
<link rel="stylesheet" href="css/uploadify.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/thickbox.css" />

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/flexigrid.pack.js"></script>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/jquery.uploadify.v2.1.0.min.js"></script>
<script type="text/javascript" src="js/jquery.jgrowl_minimized.js"></script>
<script type="text/javascript" src="js/thickbox.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
</head>
<body>
<%
UserService userService = UserServiceFactory.getUserService();
%>
<script>
	uuid = "<%=request.getAttribute("uuid") %>";
</script>
<textarea id=csi style="display: none"></textarea>
<div id=gbar><b class=gb1><a href="/">相册首页</a></b></div>
<div id=guser width=100%><%=userService.getCurrentUser().getEmail()
					+ " | "%><a
	href="<%=userService.createLogoutURL("/")
					%>">退出</a></div>
<div class=gbh style="left: 0"></div>
<div class=gbh style="right: 0"></div>


<div class="bborderx">
<table id="flex1" style="display: none"></table>
</div>
<div id="flex1"></div>
<div style="display: none;"><input id="updatePhotosTrigger"
	alt="#TB_inline?height=300&width=500&inlineId=hiddenModalContent&;modal=false"
	title="<b>照片文件上传</b>" class="thickbox" type="button" value="Show" /></div>
<div id="hiddenModalContent" style="display: none;"><div align="center">
<p><strong>可按住ALT或SHIFT同时选择多个照片文件上传(IE或许有问题,请用Firefox或者Chrome)</strong></p>
<input id="photoInputs" type="file" name="file"></input>
<br /><br/>
<a href="javascript:$('#photoInputs').uploadifyUpload();"><font color="blue" style="font-weight: bold;">开始上传</font></a>
|
<a href="javascript:$('#photoInputs').uploadifyClearQueue();">清除列队</a>
<br/>
<div style="text-align:left" id="fileQueue"></div>
</div>
</div>
<jsp:include page="analytics.html"></jsp:include>
</body>
</html>