/*
 * Ext JS Library 2.2 Copyright(c) 2006-2008, Ext JS, LLC. licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function() {

	Ext.QuickTips.init();

	var msg = function(title, msg) {
		Ext.Msg.show({
					title : title,
					msg : msg,
					minWidth : 200,
					modal : true,
					icon : Ext.Msg.INFO,
					buttons : Ext.Msg.OK
				});
	};

	var fp = new Ext.FormPanel({
		renderTo : 'fi-form',
		fileUpload : true,
		width : 500,
		frame : true,
		title : 'File Upload Form',
		autoHeight : true,
		bodyStyle : 'padding: 10px 10px 0 10px;',
		labelWidth : 50,
		defaults : {
			anchor : '95%',
			allowBlank : false,
			msgTarget : 'side'
		},
		items : [new Ext.ux.UploadFile({
					inputName : 'file'
				})],
		buttons : [{
			text : 'Save',
			handler : function() {
				if (fp.getForm().isValid()) {
					fp.getForm().submit({
						url : '/extdemo/demo/multiuploadfile',
						//waitMsg : 'Uploading your files...',
						clientValidation : true,
						params : {
							newStatus : 'delivered'
						},
						success : function(form, action) {
							Ext.Msg.alert('Success', action.result.msg);
						},
						failure : function(form, action) {
							switch (action.failureType) {
								case Ext.form.Action.CLIENT_INVALID :
									Ext.Msg
											.alert('Failure',
													'Form fields may not be submitted with invalid values');
									break;
								case Ext.form.Action.CONNECT_FAILURE :
									Ext.Msg.alert('Failure',
											'Ajax communication failed');
									break;
								case Ext.form.Action.SERVER_INVALID :
									Ext.Msg.alert('Failure', action.result.msg);
							}
						}
					});

				}
			}
		}]
	});

});