myFormPanel.getForm().submit({
	clientValidation : true,
	url : 'updateConsignment.php?param=' + 1,
	params : {
		newStatus : 'delivered'
	},
	success : function(form, action) {
		Ext.Msg.alert('Success', action.result.msg);
	},
	failure : function(form, action) {
		switch (action.failureType) {
			case Ext.form.Action.CLIENT_INVALID :
				Ext.Msg.alert('Failure',
						'Form fields may not be submitted with invalid values');
				break;
			case Ext.form.Action.CONNECT_FAILURE :
				Ext.Msg.alert('Failure', 'Ajax communication failed');
				break;
			case Ext.form.Action.SERVER_INVALID :
				Ext.Msg.alert('Failure', action.result.msg);
		}
	}
});

// would process the following server response for a successful submission:
//
// {
// "success":true, // note this is Boolean, not string
// "msg":"Consignment updated"
// }
//
// and the following server response for a failed submission:
//
// {
// "success":false, // note this is Boolean, not string
// "msg":"You do not have permission to perform this operation"
// }
