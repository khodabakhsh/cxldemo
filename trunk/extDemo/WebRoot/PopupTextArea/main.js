Ext.onReady(function() {
	// 初始化快速提示
	Ext.QuickTips.init();
	// 将提示显示在form属性旁边
	Ext.form.Field.prototype.msgTarget = 'under';
	// 得到body对象
	var bd = Ext.getBody();
	// 创建一个h2标签
	bd.createChild({
				tag : 'h2',
				html : '<a href="#">非常简单的From</a>'
			});

	var simple = new Ext.FormPanel({
		labelWidth : 75,
		frame : true,
		title : '简单Form',
		bodyStyle : 'padding : 5px 5px 0',
		width : 350,
		draggable : true,
		monitorValid : true,// 默认，可加可不加
		clientValidation : true,// 默认，可加可不加
		defaults : {
			width : 230
		},
		defaultType : 'textfield',
		items : [{xtype:'popupTextArea',
					id : 'name',
					fieldLabel : '姓名',
					name : 'username',
					allowBlank : false,
					blankText : '姓名不能为空',
					maxLength : 20,
					maxLengthText : '您输入的姓名太长'
				} ],

		buttons : [{
			text : '保存',
			handler : function() {

		alert(33);
			}
		}, {
			text : '取消',
			handler : function() {
				simple.getForm().getEl().dom.reset();
			}
		}]
	});
	// 渲染此form
	simple.render(document.body);
});