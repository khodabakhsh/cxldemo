Ext.onReady(function() {

			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

			function star(val) {
				if (val) {
					return '<span style="color:green;">' + val
							+ '&nbsp;&nbsp;<img src="star.gif"/>' + '</span>';
				} else {
					return val;
				}

			}

			// grid filters
			filters = new Ext.ux.grid.GridFilters({
						filters : [{
									type : 'string',
									dataIndex : 'title'
								}, {
									type : 'date',
									dataIndex : 'lastPostTime'
								}, {
									type : 'list',
									dataIndex : 'author',
									options : ['jack', 'james', 'david'],
									phpMode : true
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
											dateFormat : 'Y-m-d'
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
										width : 200,
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
												dataIndex : 'postText',
												renderer : star

											}]
								}),

						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
						width : 800,
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
						plugins : [filters, new Ext.ux.grid.Search({
									width : 200,

									mode : 'remote'// 设为remote，服务端，设为local，本地

									,
									iconCls : false

									,
									dateFormat : 'm/d/Y'

									,
									minLength : 2

										// ,align:'right'

									})]
					});
			grid.render('grid-example');
		});