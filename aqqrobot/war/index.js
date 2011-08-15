

$(function() {
	$("#submit").click(function() {
		var checkCode = $("#checkCode").val();
//		var city = $("#city").val();
		if (checkCode == "") {
			alert("empty check code！");
			return;
		}
//		var remark = $("#remark").val();
//		var sdate = $("#sdate_hour").val() + ":" + $("#sdate_minute").val();
//		var type = $("#type").val();
//		var days = $("#days").val();
//		var sid = $("#sid").val();
//		$("#message").html("").hide();
//		$("#scheduleSave").attr("disabled", true).attr("value", "请稍候");
		$.ajax({
			url : "aqqrobot",
			type : "GET",
			cache : false,
			data : {
				"action" : "saveSchedule"
			},
			dataType : "json",
			success : function(data) {
			},
			complete : function(req) {
			
			}
		});
	});
});
