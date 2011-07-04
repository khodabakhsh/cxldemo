<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
	<head>

		<title>My JSP 'MyJsp.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="checkBoxGroup.js"> </script>

		<style>
.x-check-group-alt {
	background: #D1DDEF;
	border-top: 1px dotted #B5B8C8;
	border-bottom: 1px dotted #B5B8C8;
}
</style>
	</head>

	<body>
		<%
			
			
			System.out.println(request.getParameter("group"));
	
		%>
		<div id="form-ct"></div>
	</body>
</html>
