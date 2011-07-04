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
		<script type="text/javascript" src="radioGroup.js"> </script>

		<style>
.float-left {
	float: left;
}
.allow-float {
	clear: none !important;
}
</style>
	</head>

	<body>
		<%
			//	Map map = request.getParameterMap();
			String[] pStrings = request.getParameterValues("group");
			//System.out.println(request.getParameter("group"));
		//	System.out.println(request.getParameter("group"));
			if (pStrings != null) {
				for (int i = 0; i < pStrings.length; i++) {
					System.out.println(pStrings[i]);
				}
			}
		%>
		<div id="form-ct"></div>
	</body>
</html>
