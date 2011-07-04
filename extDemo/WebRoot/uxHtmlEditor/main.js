Ext.onReady(function() {
			fs = new Ext.form.FormPanel({
						title : '回复帖子',
						renderTo : 'render',
						width : 600,
						height : 300,
						frame : true,
						layout : 'fit',
						items : {
							id : 'content',
							xtype : 'ImgHtmleditor',
							maxLength : 4000
							//			
						}
					});
		})