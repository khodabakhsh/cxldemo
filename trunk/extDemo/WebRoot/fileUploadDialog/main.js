

Ext.onReady(function() {
			dialog = new Ext.ux.UploadDialog.Dialog({
						url : '/extdemo/demo/multiuploadfile',
						reset_on_hide : false,
						allow_close_on_upload : true,
						upload_autostart : false
					});
//			dialog.show('show-button');
						dialog.show();

		});