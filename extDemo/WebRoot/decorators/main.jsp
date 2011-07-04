<%-- 
    Document   : main
    Created on : 2008-3-21, 19:26:18
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<html debug="true">
    <head>
    <style type="text/css">
	.imgConfig {
	background-image: url(../img/cog_edit.png) !important;
}
	</style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>extDemo</title>
        <link rel="stylesheet" href="/extdemo/style/style.css" type="text/css" media="screen,projection" />
        <link rel="stylesheet" href="/extdemo/ext/resources/css/ext-all.css" type="text/css" media="screen,projection" />
        
        <script src="/extdemo/ext/adapter/ext/ext-base.js"></script>
        <script src="/extdemo/ext/ext-all-debug.js"></script>
        <script src="/extdemo/ext/ext-lang-zh_CN.js"></script>
      <!--  <script src="/extdemo/firebug-lite/build/firebug-lite.js"></script> --> 
        <script>
            Ext.BLANK_IMAGE_URL = '/extdemo/ext/resources/images/default/s.gif';
            Ext.QuickTips.init();
            Ext.form.Field.prototype.msgTarget = 'qtip';
        </script>
        
        <decorator:head />
    </head>
    <body>
        <decorator:body />
    </body>
</html>
