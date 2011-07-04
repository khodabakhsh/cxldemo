Ext.onReady(function() {

			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

			function change(val) {
				if (val > 0) {
					return '<span style="color:green;">' + val
							+ '&nbsp;&nbsp;<img src="star.gif"/>' + '</span>';
				} else if (val < 0) {
					return '<span style="color:red;">' + val + '</span>';
				}
				return val;
			}

			function pctChange(val) {
				if (val > 0) {
					return '<span style="color:green;">' + val + '%</span>';
				} else if (val < 0) {
					return '<span style="color:red;">' + val + '%</span>';
				}
				return val;
			}

			// grid filters
			filters = new Ext.ux.grid.GridFilters({
						filters : [{
									type : 'string',
									dataIndex : 'title'
								}, {
									type : 'string',
									dataIndex : 'author'
								}, {
									type : 'date',
									dataIndex : 'lastPostTime'
								}]
					});

			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : '/extdemo/demo/getAllTopics'
								}),
						reader : new Ext.data.JsonReader({
									root : 'topics',
									totalProperty : 'totalCount',
									id : 'id'
								}, [{
											name : 'title',
											mapping : 'title'
										}, {
											name : 'author',
											mapping : 'author'
										}, {
											name : 'lastPostTime',
											mapping : 'lastPostTime',
											type : 'date',
											dateFormat : 'yyyy-MM-dd'
										}, {
											name : 'postText',
											mapping : 'postText'
										}])
					});
			store.load({
						params : {
							start : 0,
							limit : 10
						}
					});

			var grid = new Ext.grid.GridPanel({
						store : store,
						colModel : new Ext.grid.ColumnModel({
									defaults : {
										width : 120,
										sortable : true
									},
									columns : [{
												id : 'title',
												header : 'title',
												width : 200,
												sortable : true,
												dataIndex : 'title'
											}, {
												header : 'author',
												dataIndex : 'author'
											}, {
												header : 'lastPostTime',
												dataIndex : 'lastPostTime',
												renderer : Ext.util.Format
														.dateRenderer('Y-m-d')
											}, {
												header : 'postText',
												dataIndex : 'postText'
												// renderer : pctChange
										}]
								}),

						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
						width : 600,
						height : 300,
						frame : true,
						title : 'Framed with Row Selection and Horizontal Scrolling',
						iconCls : 'icon-grid',
						bbar : new Ext.PagingToolbar({
									store : store, // grid and PagingToolbar
									// using same store
									displayInfo : true,
									pageSize : 10,
									prependButtons : true,
									items : ['-', new Ext.Action({
														text : '我是按钮',
														iconCls : 'imgConfig',
														handler : function() {
															alert('you click me');
														}
													})]
								}),
						plugins : filters

					});
			grid.render('grid-example');
		});