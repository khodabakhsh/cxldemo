<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.util.*"%>
<form action="online.jsp" method="post">
用户名：
<input type="text" name="name">
<input type="submit" value="登陆">
<a href="logout.jsp">注销</a>
</form>
<!-- 向session接收输入的用户名 -->
<%
	if(request.getParameter("name")!=null)
		session.setAttribute("uname",request.getParameter("name")) ;
%>
<h2>在线人员</h2>
<hr>
<%
	List l = (List)application.getAttribute("allUser");
	for(Object o : l)
		out.println(o);
%>
