var errorMsg = "对不起，程序错误，请稍候再试！";
var timeoutID = null;
function showMsg(type, msg) {
	if (!msg)
		return;
	if (type == "error")
		msg = "<img src=\"images/fail.png\"/>&nbsp;" + msg;
	else if (type = "pass")
		msg = "<img src=\"images/pass.png\"/>&nbsp;" + msg;
	else
		msg = "<img src=\"images/info.png\"/>&nbsp;" + msg;
	document.getElementById("msg_content").innerHTML = msg;
	var l = msg.length;
	if (msg.indexOf("<a") != -1)
		l = l - 47;
	document.getElementById("msg").style.left = (document.body.clientWidth - l * 10)
			/ 2 + "px";
	document.getElementById("msg").style.top = document.body.scrollTop +"px";
	document.getElementById("msg").style.visibility = "visible";
	if (timeoutID != null)
		clearTimeout(timeoutID);
	timeoutID = setTimeout("clearMsg()", 30000);
}
function clearMsg() {
	document.getElementById("msg").style.visibility = "hidden";
	document.getElementById("msg_content").innerHTML = "";
}
var pid=null;
var uuid = null;
var sadmin = "false";
$(function() {
	$("#commentSave")
			.click(
					function() {
						var nickname = $("#nickname").val();
						if (nickname == "") {
							showMsg("error","昵称不能为空！");
							return;
						}
						var email = $("#email").val();
						if (email != "" && !validateEmail(email)) {
							showMsg("error","Email格式不正确！");
							return;
						}
						var content = $("#ccontent").val();
						if (content == "") {
							showMsg("error","评论内容不能为空！");
							return;
						}
						$("#commentSave").attr("disabled", true).attr("value",
								"请稍候");
						$
								.ajax( {
									url : "photoManager",
									type : "POST",
									cache : false,
									data : {
										"action" : "saveComment",
										"email" : email,
										"nickname" : nickname,
										"content" : content,
										"pid" : pid
									},
									dataType : "json",
									success : function(data) {
										if (!data.result)
											showMsg("error",data.message);
										else {
											$("#ccontent").val("");
											var html = '<li id="li-'+data.cid+'"><p><span class="cnick">'
											+ data.nickname
											+ '</span>&nbsp;<span class="ccdate">'
											+ data.cdate
											+ '</span>&nbsp;说：</p><span class="ccont">'
											+ data.content
											+ '</span>';
											if(admin)
												html+="<a href=# onclick='deleteComment(\""+data.cid+"\");return false;'>[删除]</a>";
											html+='<br/></li>';
											$("#cc")
													.append(
															html)
																	
											showMsg("pass",data.message);
											if(sadmin=="false"){
											window.parent.document
													.getElementById("c-" + pid).innerHTML = "共有 <span class='commentcount'>"
													+ data.count + "</span> 条评论";
											}else{
												window.parent.setNeedReload();
											}
										}
									},
									complete : function(req) {
										$("#commentSave").attr("disabled",
												false).attr("value", "发布评论");
										var code = req.status;
										if (code < 200 || code > 299)
											showMsg("error",errorMsg);
									}
								});
					});
	$("#photoUpdate")
	.click(
			function() {
				var remark = $("#premark").val();
				if(remark.length>300){
					showMsg("error","照片说明不能超过300字");
					return;
				}
				var comment = $("#cancomment").attr("checked");
				$("#photoUpdate").attr("disabled", true).attr("value",
						"请稍候");
				$
						.ajax( {
							url : "photoManager",
							type : "POST",
							cache : false,
							data : {
								"action" : "updatePhoto",
								"remark" : remark,
								"comment" : comment,
								"pid" : pid
							},
							dataType : "json",
							success : function(data) {
								if (!data.result)
									showMsg("error",data.message);
								else {
									showMsg("pass",data.message);
									if(sadmin=="false"){
									window.parent.document
									.getElementById("c-" + pid).innerHTML = comment?"允许评论":"评论已关闭";
									window.parent.document
											.getElementById("r-" + pid).innerHTML = remark;
									}else{
										window.parent.setNeedReload();
									}
								}
							},
							complete : function(req) {
								$("#photoUpdate").attr("disabled",
										false).attr("value", "更新设置");
								var code = req.status;
								if (code < 200 || code > 299)
									showMsg("error",errorMsg);
							}
						});
			});
	$("#nickname").change(function(){
		$.cookie("nickname", $("#nickname").val(), {
			expires : 180
		});
	});
	var nickname = $.cookie("nickname");
	if (nickname != null && nickname != "null"&&nickname!="")
		$("#nickname").val(nickname);
	
	$("#email").change(function(){
		$.cookie("email", $("#email").val(), {
			expires : 180
		});
	});
	var email = $.cookie("email");
	if (email != null && email != "null"&&email!="")
		$("#email").val(email);
	if(admin){
		$("#cancomment").attr("checked",canComment);
		$("#premark").val(premark);
	}
	
	if(admin){
		$("#photoInputs").uploadify(
				{
					'uploader' : '../uploadify.swf',
					'script' : 'photoUpload?id='+uuid+'/'+pid,
					'cancelImg' : 'images/cancel.png',
					'fileDesc' : 'Image Files',
					'fileExt' : '*.jpg;*.jpeg;*.png;*.gif',
					'multi' : false,
					'queueID' : 'fileQueue',
					'sizeLimit' : 1048576,
					onError : function(event, queueID, fileObj, errorObj) {
						var msg;
						if (errorObj.status == 404) {
							msg = '无法找到上传程序';
						} else if (errorObj.type === "HTTP")
							msg = errorObj.type + ": " + errorObj.status;
						else if (errorObj.type === "File Size")
							msg = fileObj.name + '<br>' + '太大,不能超过1MB';
						else
							msg = errorObj.type + ": " + errorObj.text;
						showMsg("error",msg);
						return false;
					},
					onComplete : function(a, b, c, d, e) {
						var size = Math.round(c.size / 1024);
						var data = eval("(" + d + ")");
						if (data.result) {
							$("#thumbnail").attr("src","view?id="+pid+"&w=100&h=80&r="+Math.random());
							showMsg("pass",data.message);
						} else {
							showMsg("error",data.message);
							return false;
						}
					}

				});
	}
});

function validateEmail(input) {
	var email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (input.match(email)) {
		return true;
	} else {
		return false;
	}
}

function deleteComment(cid){
	if (confirm('您确定要删除这条评论吗？')) {
		$
		.ajax( {
			url : "photoManager",
			type : "POST",
			cache : false,
			data : {
				"action" : "deleteComment",
				"cid" : cid
			},
			dataType : "json",
			success : function(data) {
				if (!data.result)
					showMsg("error",data.message);
				else {
					showMsg("pass",data.message);
					$("#li-"+cid).remove();
					if(sadmin=="false"){
					window.parent.document
							.getElementById("c-" + pid).innerHTML = data.count==0?"暂无评论":("共有 <span class='commentcount'>"
							+ data.count + "</span> 条评论");
					}else{
						window.parent.setNeedReload();
					}
				}
			},
			complete : function(req) {
				var code = req.status;
				if (code < 200 || code > 299)
					showMsg("error",errorMsg);
			}
		});
	}
}
