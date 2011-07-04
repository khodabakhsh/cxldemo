Ext.override(Ext.form.CheckboxGroup, { // 在inputValue中找到定义的内容后，设置到items里的各个checkbox中
	setValue : function(value) {
		this.items.each(function(f) {
					if (value.indexOf(f.inputValue) != -1) {
						f.setValue(true);
					} else {
						f.setValue(false);
					}
				});
	}
	,
	// 以value1,value2的形式拼接group内的值
	getValue : function() {
		var re = "";
		this.items.each(function(f) {
					if (f.getValue() == true) {
						re += f.inputValue + ",";
					}
				});
		return re.substr(0, re.length - 1);
	}
//	,
//	// 在Field类中定义的getName方法不符合CheckBoxGroup中默认的定义，因此需要重写该方法使其可以被BasicForm找到
//	getName : function() {
//		return
//		this.name;
//	}
});

Ext.onReady(function() {

			Ext.QuickTips.init();

			// turn on validation errors beside the field globally
			Ext.form.Field.prototype.msgTarget = 'side';

			var cbg = new Ext.form.CheckboxGroup({

						xtype : 'checkboxgroup',
						fieldLabel : 'Multi-Column<br />(custom widths)',
//						name : 'group',
						id: 'group',
						columns : [100, 100],
						vertical : true,
						items : [{
									boxLabel : 'Item 1',
//									 name : 'group',
									inputValue : 1
								}, {
									boxLabel : 'Item 2',
//									 name : 'group',
									inputValue : 2
								}, {
									boxLabel : 'Item 3',
//									 name : 'group',
									inputValue : 3
//									,
//									checked : true
								}, {
									boxLabel : 'Item 4',
//									 name : 'group',
									inputValue : 4
								}, {
									boxLabel : 'Item 5',
//									 name : 'group',
									inputValue : 5
								}]
					});

			var fp = new Ext.FormPanel({
						frame : true,
						title : 'Check/Radio Groups',
						labelWidth : 110,
						width : 600,
						renderTo : 'form-ct',
						bodyStyle : 'padding:0 10px 0;',

						items : [cbg, {
									id : 'name',
									name : 'name',
									fieldLabel : '姓名',
									xtype : 'textfield'
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

			cbg.setValue('1,3');//选择checkboxgroup的1 3项
			alert(cbg.getValue());//选择checkboxgroup选择项，以逗号隔开
			fp.getForm().setValues({group:'2,5'});//选择checkboxgroup的2 5项,group为checkboxgroup的id

		});
