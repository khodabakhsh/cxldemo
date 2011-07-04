Ext.override(Ext.form.CheckboxGroup, { // 在inputValue中找到定义的内容后，设置到items里的各个checkbox中
	setValue : function(value) {
		this.items.each(function(f) {
					if (value.indexOf(f.inputValue) != -1) {
						f.setValue(true);
					} else {
						f.setValue(false);
					}
				});
	},
	// 以value1,value2的形式拼接group内的值
	getValue : function() {
		var re = "";
		this.items.each(function(f) {
					if (f.getValue() == true) {
						re += f.inputValue + ",";
					}
				});
		return re.substr(0, re.length - 1);
	},
	// 在Field类中定义的getName方法不符合CheckBoxGroup中默认的定义，因此需要重写该方法使其可以被BasicForm找到
	getName : function() {
		return
		this.name;
	}
});

Ext.onReady(function() {

			Ext.QuickTips.init();

			// turn on validation errors beside the field globally
			Ext.form.Field.prototype.msgTarget = 'side';



			var fp = new Ext.FormPanel({
						frame : true,
						title : 'Check/Radio Groups',
						labelWidth : 110,
						width : 600,
						renderTo : 'form-ct',
						bodyStyle : 'padding:0 10px 0;',

						items : [ {
									xtype : 'radio',
									id : 'group',
									name : 'group',
									itemCls : 'float-left',
									clearCls : 'allow-float',
									fieldLabel : '请选择',
									inputValue : "true",
									boxLabel : '是'
								}, {
									xtype : 'radio',
									name : 'group',
									clearCls : 'stop-float',
									hideLabel : true,
									boxLabel : '否',
									inputValue : "false"
								}],

						buttons : [{
							text : 'values sent to the server',
							handler : function() {
								if (fp.getForm().isValid()) {
									Ext.Msg
											.alert(
													'Submitted Values',
													'The following will be sent to the server: <br />'
															+ fp
																	.getForm()
																	.getValues(true)
																	.replace(
																			/&/g,
																			', '));
								}
							}
						}, {
							text : '测试',
							handler : function() {
								if (fp.getForm().isValid()) {
									fp.getForm().submit({
												url : 'checkBoxGroup.jsp'
											});

								}
							}
						}, {
							text : 'Reset',
							handler : function() {
								fp.getForm().reset();
							}
						}]
					});
//
//			// for (var i = 0; i < cbg.items.length; i++) {
//			// if (cbg.items.itemAt(i).checked) {
//			// alert(cbg.items.itemAt(i).name);
//			// }
//			// }
//			cbg.setValue('1,3');
//			alert(cbg.getValue());

		});
