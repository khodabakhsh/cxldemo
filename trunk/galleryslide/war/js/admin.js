var errorMsg = "对不起，程序错误，请稍候再试！";
var uuid = null;
var needReload = false;
$(function() {
	$("#flex1").flexigrid( {
		url : 'photoManager?action=photosList',
		dataType : 'json',
		colModel : [ {
			display : '上传时间',
			name : 'cdate',
			width : 120,
			sortable : false,
			align : 'left'
		}, {
			display : '文件名',
			name : 'filenam',
			width : 160,
			sortable : false,
			align : 'left'
		}, {
			display : '说明',
			name : 'remark',
			width : 260,
			sortable : false,
			align : 'left'
		}, {
			display : '评论数',
			name : 'count',
			width : 80,
			sortable : false,
			align : 'left'
		}, {
			display : '评论状态',
			name : 'status',
			width : 80,
			sortable : false,
			align : 'left'
		}, {
			display : '缩略图',
			name : 'thumb',
			width : 120,
			sortable : false,
			align : 'left'
		} ],
		buttons : [ {
			name : '上传新照片',
			bclass : 'add',
			onpress : photoAction
		}, {
			name : '删除照片',
			bclass : 'delete',
			onpress : photoAction
		}, {
			separator : true
		}, , {
			name : '刷新',
			bclass : 'refresh',
			onpress : photoAction
		} ],
		title : '照片管理列表(若需管理照片说明及评论，请单击文件名的链接)',
		usepager : true,
		rp : 10,
		useRp : false,
		pagestat : '显示 第 {from} 到 {to} 张, 总共  {total} 张照片',
		procmsg : '加载中, 请稍候 ...',
		nomsg: '<strong>您还没有上传照片到相册，现在就<a href="#" onclick="uploadPhoto();return false;">上传</a></strong>',
		onSuccess : function() {
			tb_init('a.thickbox')
		},
		height : document.documentElement.clientHeight-150
	});
	$("#photoInputs").uploadify(
			{
				'uploader' : '../uploadify.swf',
				'script' : 'photoUpload?id='+uuid,
				'checkScript' : 'photoCheck',
				'cancelImg' : 'images/cancel.png',
				'fileDesc' : 'Image Files',
				'fileExt' : '*.jpg;*.jpeg;*.png;*.gif',
				'multi' : true,
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
					$.jGrowl('<p></p>' + msg, {
						theme : 'error',
						header : '错误',
						sticky : true
					});
					$("#fileUploadgrowl" + queueID).fadeOut(250, function() {
						$("#fileUploadgrowl" + queueID).remove()
					});
					return false;
				},
				onCancel : function(a, b, c, d) {
					var msg = "取消上传: " + c.name;
					$.jGrowl('<p></p>' + msg, {
						theme : 'warning',
						header : '已取消上传',
						life : 4000,
						sticky : false
					});
				},
				onClearQueue : function(a, b) {
					var msg = "从列队中清除 " + b.fileCount + " 个图片文件";
					$.jGrowl('<p></p>' + msg, {
						theme : 'warning',
						header : '已清除列队',
						life : 4000,
						sticky : false
					});
				},
				onComplete : function(a, b, c, d, e) {
					var size = Math.round(c.size / 1024);
					var data = eval("(" + d + ")");
					if (data.result) {
						$.jGrowl('<p></p>' + data.message, {
							theme : 'success',
							header : '图片文件上传成功',
							life : 4000,
							sticky : false
						});
						setNeedReload();
						//tb_remove();
						//reloadGrid();
					} else {
						$.jGrowl('<p></p>' + data.message, {
							theme : 'error',
							header : '错误',
							sticky : true
						});
						return false;
					}
				}

			});
});

function photoAction(com, grid) {
	if (com == '删除照片') {
		var items = $('.trSelected', grid);
		if (items.length > 0) {
			if (confirm('确认删除选中的 ' + items.length + ' 张照片吗？')) {
				var itemlist = '';
				for (i = 0; i < items.length; i++) {
					itemlist += items[i].id.substr(3) + ",";
				}
				$.ajax( {
					type : "POST",
					cache : false,
					dataType : "json",
					url : "photoManager",
					data : {
						"action" : "deletePhotos",
						"ids" : itemlist
					},
					success : function(data) {
						var theme = 'success';
						if (data.total != data.count) {
							if (data.count == 0)
								theme = 'error';
							else
								theme = 'warning';
						}
						$.jGrowl('<p></p>' + data.message, {
							theme : theme,
							header : '删除报告',
							life : 4000,
							sticky : false
						});
						if (data.result) {
							reloadGrid();
						}
					},
					complete : function(req) {
						var code = req.status;
						if (code < 200 || code > 299)
							$.jGrowl('<p></p>' + errorMsg, {
								theme : 'error',
								header : '错误',
								life : 4000,
								sticky : false
							});
					}
				});
			}
		} else {
			$.jGrowl('<p></p>' + '请至少选中一行删除！', {
				theme : 'error',
				header : '错误',
				life : 4000,
				sticky : false
			});
		}
	} else if (com == '上传新照片') {
		uploadPhoto();
	} else {
		$("#flex1").flexReload();
	}
}

function uploadPhoto(){
	$('#updatePhotosTrigger').trigger("click");
}

function setNeedReload(){
	needReload = true;
}

function reloadGrid() {
	$("#flex1").flexReload();
	needReload= false;
}