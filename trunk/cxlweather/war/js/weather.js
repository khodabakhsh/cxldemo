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
	document.getElementById("msg").style.visibility = "visible";
	if (timeoutID != null)
		clearTimeout(timeoutID);
	timeoutID = setTimeout("clearMsg()", 30000);
}
function clearMsg() {
	document.getElementById("msg").style.visibility = "hidden";
	document.getElementById("msg_content").innerHTML = "";
}

var slimit = 10;
var count = 0;
var total = 0;

var errorMsg = "对不起，服务器错误，请稍候再试";

$("#flex1")
		.flexigrid(
				{
					url : 'webManager?action=schedulesList',
					dataType : 'json',
					colModel : [ {
						display : '创建时间',
						name : 'cdate',
						width : 120,
						sortable : false,
						align : 'left'
					}, {
						display : '定制城市',
						name : 'city',
						width : 110,
						sortable : false,
						align : 'left'
					}, {
						display : '预报天数',
						name : 'days',
						width : 80,
						sortable : false,
						align : 'left'
					}, {
						display : '每天发送时间',
						name : 'sdate',
						width : 100,
						sortable : false,
						align : 'left'
					}, {
						display : '接收邮箱',
						name : 'email',
						width : 170,
						sortable : false,
						align : 'left'
					}, {
						display : '定制内容状态',
						name : 'type',
						width : 120,
						sortable : false,
						align : 'left'
					}, {
						display : '最近发送时间',
						name : 'adate',
						width : 120,
						sortable : false,
						align : 'left',
						hide : false
					}, {
						display : '备注',
						name : 'remark',
						width : 150,
						sortable : false,
						align : 'left'
					} ],
					buttons : [ {
						name : '新建',
						bclass : 'add',
						onpress : scheduleAction
					}, {
						name : '删除',
						bclass : 'delete',
						onpress : scheduleAction
					}, {
						name : '修改',
						bclass : 'modify',
						onpress : scheduleAction
					} ],
					title : '定制天气预报列表',
					usepager : true,
					rp : 10,
					useRp : false,
					pagestat : '显示 第 {from} 到 {to} 条 , 总共  {total} 条记录',
					procmsg : '加载中, 请稍候 ...',
					nomsg : '<strong>您还没有建立天气预报定制，现在就<a href="#" onclick="newSchedule();return false;">新建一个</a>，若要定制多个，请再点左上角“<img style="vertical-align:middle;" src="images/add.png" />新建”按钮</strong>',
					height : 278
				});
function scheduleAction(com, grid) {
	if (com == '删除') {
		var items = $('.trSelected', grid);
		if (items.length > 0) {
			if (confirm('确认删除选中的 ' + items.length + ' 条记录吗？' + '如果只是暂时不想收到邮件，只需设置状态为“暂时停用”即可')) {
				var itemlist = '';
				for (i = 0; i < items.length; i++) {
					itemlist += items[i].id.substr(3) + ",";
				}
				$.ajax( {
					type : "POST",
					cache : false,
					dataType : "json",
					url : "webManager",
					data : {
						"action" : "deleteSchedules",
						"ids" : itemlist
					},
					success : function(data) {
						showMsg(data.result ? "pass" : "error", data.message);
						if (data.result) {
							$("#flex1").flexReload();
							updateCountAndTotal(data);
						}
					},
					complete : function(req) {
						var code = req.status;
						if (code < 200 || code > 299)
							showMsg("error", errorMsg);
					}
				});
			}
		} else {
			showMsg("error", "请至少选中一行删除！");
		}
	} else if (com == '新建') {
		newSchedule();
		var selhour = Math.round(Math.random() * 12) + 9;
		var selmin = Math.round(Math.random() * 11) * 5;
		$("#sdate_hour").attr("value", (selhour < 10 ? "0" : "") + selhour);
		$("#sdate_minute").attr("value", (selmin < 10 ? "0" : "") + selmin);
	} else if (com == '修改') {
		if ($('.trSelected', grid).length == 1) {
			var cell = $('.trSelected', grid);
			var sdate = cell.find("td:eq(3)").eq(0).text();
			var hour = sdate.substring(0, 2);
			var minute = sdate.substring(3, 5);
			$("#sdate_hour").attr("value", hour);
			$("#sdate_minute").attr("value", minute);
			$("#email").val(cell.find("td:eq(4)").eq(0).text());
			$("#city").val(cell.find("td:eq(1)").eq(0).text());
			var remark = cell.find("td:eq(7)").eq(0).text();
			if (remark != "[无]")
				$("#remark").val(remark);
			var type = cell.find("td:eq(5)").eq(0).text();
			if (type == "天气内容放正文")
				$("#type").attr("value", "1");
			else if (type == "天气内容放主题")
				$("#type").attr("value", "2");
			else {
				$("#type").attr("value", "0");
				$("#test").attr("disabled", true);
			}
			var days = cell.find("td:eq(2)").eq(0).text();
			if(days.indexOf("系统默认")!=-1)
				$("#days").attr("value", "0");
			else
				$("#days").attr("value", days);
			$("#sid").val(cell[0].id.substr(3));
		} else {
			showMsg("error", "请选中一行修改！");
			return;
		}
		$("#message").html("").hide();
		$("#newSchedule").attr("title", "<b>修改天气预报定制</b>");
		$('#newSchedule').trigger("click");
	}
}

$(function() {
	getAccountInfo();
	$("#scheduleSave").click(function() {
		var email = $("#email").val();
		if (!validateEmail(email)) {
			$("#message").html("接收邮箱不是有效的Email格式！").show();
			resetForm();
			return;
		}
		var city = $("#city").val();
		if (city == "") {
			$("#message").html("定制城市不能为空！").show();
			return;
		}
		var remark = $("#remark").val();
		var sdate = $("#sdate_hour").val() + ":" + $("#sdate_minute").val();
		var type = $("#type").val();
		var days = $("#days").val();
		var sid = $("#sid").val();
		$("#message").html("").hide();
		$("#scheduleSave").attr("disabled", true).attr("value", "请稍候");
		$.ajax( {
			url : "webManager",
			type : "POST",
			cache : false,
			data : {
				"action" : "saveSchedule",
				"email" : email,
				"city" : city,
				"remark" : remark,
				"sdate" : sdate,
				"type" : type,
				"days" : days,
				"sid" : $("#sid").val()
			},
			dataType : "json",
			success : function(data) {
				if (!data.result)
					$("#message").html(data.message).show();
				else {
					tb_remove();
					resetForm();
					$("#flex1").flexReload();
					showMsg("pass", data.message);
					updateCountAndTotal(data);
				}
			},
			complete : function(req) {
				$("#scheduleSave").attr("disabled", false).attr("value", "保存");
				var code = req.status;
				if (code < 200 || code > 299)
					$("#message").html(errorMsg).show();
			}
		});
	});

	$("#testEmail").click(
			function() {
				var email = $("#email").val();
				if (!validateEmail(email)) {
					$("#message").html("接收邮箱不是有效的Email格式！").show();
					resetForm();
					return;
				}
				var city = $("#city").val();
				if (city == "") {
					$("#message").html("定制城市不能为空！").show();
					return;
				}
				var type = $("#type").val();
				var days = $("#days").val();
				$("#message").html("").hide();
				$("#testEmail").attr("disabled", true).attr("value", "请稍候");
				$.ajax( {
					url : "webManager",
					type : "POST",
					cache : false,
					data : {
						"action" : "testEmail",
						"email" : email,
						"city" : city,
						"type" : type,
						"days" : days
					},
					dataType : "json",
					success : function(data) {
						if (data.result) {
							$("#message").html(
									"<font color='blue'>" + data.message
											+ "</font>").show();
							$("#city").val(data.city);
						} else {
							$("#message").html(data.message).show();
						}
					},
					complete : function(req) {
						$("#testEmail").attr("disabled", false).attr("value",
								"发送测试邮件");
						var code = req.status;
						if (code < 200 || code > 299)
							$("#message").html(errorMsg).show();
					}
				});
			});

	$("#updateNickname").click(
			function() {
				var nickname = $("#nickname").val();
				if (nickname == "") {
					showMsg("error", "请输入昵称");
					return;
				}
				$("#updateNickname").attr("disabled", true)
						.attr("value", "请稍候");
				$.ajax( {
					url : "webManager",
					type : "POST",
					cache : false,
					data : {
						"action" : "updateNickname",
						"nickname" : nickname
					},
					dataType : "json",
					success : function(data) {
						showMsg(data.result ? "pass" : "error", data.message);
						updateCountAndTotal(data);
					},
					complete : function(req) {
						$("#updateNickname").attr("disabled", false).attr(
								"value", "更改");
						var code = req.status;
						if (code < 200 || code > 299)
							showMsg("error", errorMsg);
					}
				});
			});

	$("#refreshCount").click(function() {
		getTotalCount();
	});

	$("#type").change(function() {
		if ($("#type").val() == "0") {
			$("#test").attr("disabled", true).attr("checked", false);
		} else {
			$("#test").attr("disabled", false);
		}
	});

	$("#sdate_hour")
			.change(
					function() {
						var hour = $("#sdate_hour").val();
						if (hour.indexOf("0") == 0)
							hour = hour.substring(1, 2);
						var h = parseInt(hour);
						var is139 = $("#email").val().indexOf("@139.com") != -1;
						if (h <= 8) {
							if (is139)
								$("#message")
										.html(
												"<font color='orange'>定制时间挺早的，请确认139邮箱手机接收时间奥</font>")
										.show();
						} else if (h >= 22) {
							if (is139)
								$("#message")
										.html(
												"<font color='orange'>定制时间挺晚的，请确认139邮箱手机接收时间奥</font>")
										.show();
						} else
							$("#message").html("").hide();
					});

	$("#city")
			.focus(
					function() {
						var city = $("#city").val();
						if (city=="" || city.indexOf("省") != -1 || city.indexOf("市") != -1)
							$("#message")
									.html(
											"<font color='orange'>请输入城市名称或拼音，精确到城市，不要输入省份，如江苏省南京市，直接输入“南京”即可</font>")
									.show();
					});
	
	$("#remark")
	.focus(
			function() {
				if ($("#remark").val()=="")
					$("#message")
							.html(
									"<font color='orange'>若给您的好友定制天气预报，可在此添加备注，以方便下次方便查找、管理，此信息不会出现在天气预报正文</font>")
							.show();
			});

});

function newSchedule() {
	if (total >= 2000) {
		showMsg("error", "设置的定制数目已经达到上限:2000，后续会通过开分站的形式为您提供服务。");
		return;
	}
	if (count >= slimit) {
		showMsg("error", "设置的定制数目已经达到上限:" + slimit + "，请删除一些定制设置后再试，或联系站长");
		return;
	}
	$("#sid").val("");
	$("#message").html("<font color='orange'>请确保已在139或相关邮箱设置短信达到提醒</font>")
			.show();
	if ($("#newSchedule").attr("title").indexOf("新建") == -1) {
		$("#newSchedule").attr("title", "<b>新建天气预报定制</b>");
		resetForm();
	}
	$('#newSchedule').trigger("click");
}

function resetForm() {
	document.getElementById("scheduleForm").reset();
}

function getAccountInfo() {
	$("#refreshCount").attr("disabled", true).attr("value", "请稍候");
	$("#count_loading").show();
	$.ajax( {
		url : "webManager",
		type : "POST",
		cache : false,
		data : {
			"action" : "getAccountInfo"
		},
		dataType : "json",
		success : function(data) {
			if (data.result) {
				$("#nickname").val(data.nickname);
				slimit = data.slimit;
				$("#slimit").html(slimit);
				updateCountAndTotal(data);
			}
		},
		complete : function(req) {
			$("#refreshCount").attr("disabled", false).attr("value", "刷新");
			$("#count_loading").hide();
			var code = req.status;
			if (code < 200 || code > 299)
				showMsg("error", errorMsg);
		}
	});
}

function getTotalCount() {
	$("#refreshCount").attr("disabled", true).attr("value", "请稍候");
	$("#count_loading").show();
	$.ajax( {
		url : "webManager",
		type : "POST",
		cache : false,
		data : {
			"action" : "getTotalCount"
		},
		dataType : "json",
		success : function(data) {
			if (data.result) {
				total = data.total;
				$("#total").html(total);
			}
		},
		complete : function(req) {
			$("#refreshCount").attr("disabled", false).attr("value", "刷新");
			$("#count_loading").hide();
			var code = req.status;
			if (code < 200 || code > 299)
				showMsg("error", errorMsg);
		}
	});
}

function updateCountAndTotal(data) {
	total = data.total;
	count = data.count;
	$("#count").html(count);
	$("#total").html(total);
}

function validateEmail(input) {
	var email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (input.match(email)) {
		return true;
	} else {
		return false;
	}
}