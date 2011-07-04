<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
		This is my JSP page.
		<br>
		now begin your demo
		<br>
		<br>
		<div>
			<ul>
				<li>
					<a href="fileupload/fileupload.html">fileUpload</a>
				</li>
				<li>
					<a href="fileUploadDialog/main.html">fileUploadDialog</a>
				</li>
				<li>
					<a href="fileupload2_multiuploadfile/fileupload.html">fileupload2_multiuploadfile</a>
				</li>
				<li>
					<a href="customSearchField/custom.html">custom-Search-Field</a>
				</li>
				<li>
					<a href="forumSearch/forum-search.html">forum-search</a>
				</li>
				<li>
					<a href="tree/checkboxTree.html">checkboxTree</a>
				</li>
				<li>
					<a href="tree/CRUDTree.html">CRUDTree</a>
				</li>
				<li>
					<a href="tree/AsyncTree.html">AsyncTree</a>
				</li>
				<li>
					<a href="closeMenuTab/closeMenuTab.html">closeMenuTab</a>
				</li>
				<li>
					<a href="viewport/viewport.html">viewport</a>
				</li>
				<li>
					<a href="formValidation/formValidation.html">formValidation</a>
				</li>
				<li>
					<a href="groupFilterGrid/groupFilterGrid.html">groupFilterGrid</a>
				</li>
				<li>
					<a href="SearchGrid/main.html">SearchGrid</a>
				</li>

				<li>
					<a href="PopupTextArea/main.html">PopupTextArea</a>
				</li>
				<li>
					<a href="uxHtmlEditor/main.html">uxHtmlEditor</a>
				</li>
				<li>
					<a href="linkCombo/main.html">linkCombo</a>
				</li>
				<li>
					<a href="popMessage/main.html">popMessage</a>
				</li>
				<li>
					<a href="scrollList/main.html">滚动新闻列表</a>
				</li>
				<li>
					<a href="checkbox_radio_group/checkBoxGroup.jsp">checkBoxGroup</a>
				</li>
			</ul>
		</div>
	</body>
</html>
