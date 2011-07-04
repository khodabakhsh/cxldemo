
// vtype验证开始结束日期和密码，验证日期有bug存在，如选完开始，选结束，在回去选开始，提示信息有bug
Ext.apply(Ext.form.VTypes, {
	daterange : function(val, field) {
		var date = field.parseDate(val);

		if (!date) {
			return;
		}
		if (field.startDateField
				&& (!this.dateRangeMax || (date.getTime() != this.dateRangeMax
						.getTime()))) {
			var start = Ext.getCmp(field.startDateField);
			start.setMaxValue(date);
			start.validate();
			this.dateRangeMax = date;
		} else if (field.endDateField
				&& (!this.dateRangeMin || (date.getTime() != this.dateRangeMin
						.getTime()))) {
			var end = Ext.getCmp(field.endDateField);
			end.setMinValue(date);
			end.validate();
			this.dateRangeMin = date;
		}
		/*
		 * Always return true since we're only using this vtype to set the
		 * min/max allowed values (these are tested for after the vtype test)
		 */
		return true;
	},

	password : function(val, field) {
		if (field.initialPassField) {
			var pwd = Ext.getCmp(field.initialPassField);
			return (val == pwd.getValue());
		}
		return true;
	},

	passwordText : 'Passwords do not match',

	chinese : function(val, field) {
		var reg = /^[\u4e00-\u9fa5]+$/i;
		if (!reg.test(val)) {
			return false;
		}
		return true;
	},
	chineseText : '请输入中文',

	age : function(val, field) {
		try {
			if (parseInt(val) >= 18 && parseInt(val) <= 100)
				return true;
			return false;
		} catch (err) {
			return false;
		}
	},
	ageText : '年龄输入有误',

	photo : function(val, field) {
		var reg = /\.jpg$|\.jpeg$|\.gif$|\.png$/i;
		if (!reg.test(val)) {
			return false;
		}
		return true;
	},
	photoText : '图片格式不正确，只能上传以下图片格式 : jpg,jpeg,gif,png',

	alphanum : function(val, field) {
		try {
			if (!/\W/.test(val))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	alphanumText : '请输入英文字母或是数字,其它字符是不允许的.',

	url : function(val, field) {
		try {
			if (/^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i
					.test(val))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	urlText : '请输入有效的URL地址.',

	max : function(val, field) {
		try {
			if (parseFloat(val) <= parseFloat(field.max))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	maxText : '超过最大值',

	min : function(val, field) {
		try {
			if (parseFloat(val) >= parseFloat(field.min))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	minText : '小于最小值',

	datecn : function(val, field) {
		try {
			var regex = /^(\d{4})-(\d{2})-(\d{2})$/;
			if (!regex.test(val))
				return false;
			var d = new Date(val.replace(regex, '$1/$2/$3'));
			return (parseInt(RegExp.$2, 10) == (1 + d.getMonth()))
					&& (parseInt(RegExp.$3, 10) == d.getDate())
					&& (parseInt(RegExp.$1, 10) == d.getFullYear());
		} catch (e) {
			return false;
		}
	},
	datecnText : '请使用这样的日期格式: yyyy-mm-dd. 例如:2008-06-20.',

	integer : function(val, field) {
		try {
			if (/^[-+]?[\d]+$/.test(val))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	integerText : '请输入正确的整数',

	ip : function(val, field) {
		try {
			if ((/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
					.test(val)))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	ipText : '请输入正确的IP地址',

	phone : function(val, field) {
		try {
			if (/^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/.test(val))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	phoneText : '请输入正确的电话号码,如:0920-29392929',

	mobilephone : function(val, field) {
		try {
			if (/(^0?[1][35][0-9]{9}$)/.test(val))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	mobilephoneText : '请输入正确的手机号码',

	alpha : function(val, field) {
		try {
			if (/^[a-zA-Z]+$/.test(val))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	alphaText : '请输入英文字母',
	
	decimal : function(val, field) {
		try {
			if (/^[0-9]+?[\.]{0,1}[0-9]*$/.test(val))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	decimalText : '请输入小数'

});

//

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
		items : [{
					id : 'name',
					fieldLabel : '姓名',
					name : 'username',
					allowBlank : false,
					blankText : '姓名不能为空',
					maxLength : 20,
					maxLengthText : '您输入的姓名太长'
				}, {
					fieldLabel : 'phone',
					vtype : 'phone'
				}, {
					fieldLabel : 'chinese',
					vtype : 'chinese'
				}, {
					fieldLabel : 'age',
					vtype : 'age'
				}, {
					fieldLabel : 'alphanum',
					vtype : 'alphanum'
				}, {
					fieldLabel : 'max',
					vtype : 'max',
					max : 100
				}, {
					fieldLabel : 'min',
					vtype : 'min',
					min : 20
				}, {
					fieldLabel : 'datecn',
					vtype : 'datecn'
				}, {
					fieldLabel : 'integer',
					vtype : 'integer'
				}, {
					fieldLabel : 'ip',
					vtype : 'ip'
				}, {
					fieldLabel : 'mobilephone',
					vtype : 'mobilephone'
				}, {
					fieldLabel : 'alpha',
					vtype : 'alpha'
				}, {
					fieldLabel : 'Email',
					name : 'email',
					vtype : 'email',
					vtypeText : '您输入的邮箱格式不正确'
				},
				// 时间文本域
				new Ext.form.TimeField({
							fieldLabel : 'Time',
							name : 'time',
							minValue : '8:00am', // 最小时间
							maxValue : '6:00pm', // 最大时间
							increment : 1, // 间隔,单位为分钟,默认为15分钟
							invalidText : '您输入的时间格式不正确'
						}), {
					fieldLabel : 'Special Chars Only',
					msgTarget : 'qtip',
					stripCharsRe : /[a-zA-Z0-9]/ig
					// 过滤作用，设置要过滤掉的正则表达式
			}	, {
					fieldLabel : 'numbers Only',
					regex : /^[1-9]\d*$/,
					regexText : '只能输入正整数'
				}, {
					xtype : 'numberfield',
					fieldLabel : "numberfield",
					minValue : 0,
					maxValue : 100
				}, {
					fieldLabel : '开始日期',
					xtype : 'datefield',
					format : 'Y-m-d',
					id : 'startdt',
					name : 'startdt',
					width : 140,
					vtype : 'daterange',
					endDateField : 'enddt'// id of the 'To' date field
				}, {
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '结束日期',
					id : 'enddt',
					name : 'enddt',
					width : 140,
					vtype : 'daterange',
					startDateField : 'startdt'// id of the 'From' date field
				}, {
					xtype : 'textfield',
					fieldLabel : "密码",
					id : "pass1"
				}, {
					xtype : 'textfield',
					fieldLabel : "确认密码",
					id : "pass2",
					vtype : "password",// 自定义的验证类型
					vtypeText : "两次密码不一致！",
					initialPassField : "pass1"// 要比较的另外一个的组件的id
				}],

		buttons : [{
			text : '保存',
			handler : function() {

				// 此方法在Class
				// Ext.form.BasicForm里面,是FormPanel的父类
				if (simple.getForm().isValid()) {
					simple.getForm().getEl().dom.action = 'http://www.google.com.hk/';
					simple.getForm().getEl().dom.submit();

				} else {
					simple.getForm().markInvalid({
								add : 'yeye'
							});
				}
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
