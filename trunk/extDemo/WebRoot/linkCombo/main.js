Ext.onReady(function() {
			var ParentStore = new Ext.data.JsonStore({
				fields : ['text', 'id'],
				url : '/extdemo/demo/getNode',
				baseParams : {
					mode : 'parent'
				},
				autoLoad : true
					// If data is not specified, and if autoLoad is true or an
					// Object,
					// this store's load method is automatically called after
					// creation. ' +
					// 'If the value of autoLoad is an Object, this Object will
					// be passed to the store's load method.
				});

			var parent = new Ext.form.ComboBox({
						allowBlank : false,
						hiddenName : 'Parent',
						triggerAction : 'all',
						mode : 'local',
						// 'remote' : Default
						// Automatically loads the store the first time the
						// trigger is clicked.
						// If you do not want the store to be automatically
						// loaded the first time the trigger is clicked,
						// set to 'local' and manually load the store. To force
						// a requery of the store every time the trigger is
						// clicked see lastQuery.
						width : 130,
						displayField : 'text',
						valueField : 'id',
						store : ParentStore,
						emptyText : '选择父类',
						renderTo : 'parent',
						editable : false
					});

			var ChildrenStore = new Ext.data.JsonStore({
						fields : ['text', 'id'],
						url : '/extdemo/demo/getNode'
					});

			parent.on('select', function(combo, record, index) {
						Ext.getCmp("c").setValue('');
						ChildrenStore.load({
									params : {
										mode : 'children',
										parent : record.data.id
									}
								});
					});

			var Children = new Ext.form.ComboBox({
						allowBlank : false,
						// hiddenName:
						// If specified, a hidden form field with this name is
						// dynamically generated to store the field's data value
						// (defaults to the underlying DOM element's name).
						// Required for the combo's value to automatically post
						// during a form submission
						hiddenName : 'Children',
						id : 'c',
						triggerAction : 'all',
						mode : 'local',
						width : 130,
						displayField : 'text',
						valueField : 'id',
						store : ChildrenStore,
						emptyText : '选择子类',
						renderTo : 'children',
						editable : false
					});

					/*
					 * 以下的combobox运用的是为本地的store，直接setValue可以成功，上面的两个store是服务器端的store，setValue要特殊处理的,可以写在监听store的load里面
					 */
			var localStore = new Ext.data.SimpleStore({
						fields : ['text', 'id'],
						data : [['本地text1', '1'], ['本地text0', '0']]
					});

			var local = new Ext.form.ComboBox({
						allowBlank : false,
						triggerAction : 'all',
						mode : 'local',
						value:'0',
						width : 130,
						displayField : 'text',
						valueField : 'id',
						store : localStore,
						emptyText : '选择',
						renderTo : 'local',
						editable : false
					});
			local.setValue(1);//
		})
