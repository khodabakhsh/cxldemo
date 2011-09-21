<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
//内置变量：task.get("done") (完成标识变量) task.get("result") (结果文字) task.get("guide") (向导文字)
Map<String,Object> task = (Map<String,Object>)request.getAttribute("task");

//判断用户是否完成了任务
boolean done = false;

//---------------------------------------------------
//	编写代码，判读用户是否完成任务要求 done = true;
//---------------------------------------------------

if(done){
	task.put("done", 1);//任务完成
	task.put("result", "......");//用户参与任务看到的文字说明。支持html代码
}else{
	task.put("guide", "......");//指导用户如何参与任务的文字说明。支持html代码
}
%>

