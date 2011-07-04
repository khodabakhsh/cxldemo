
var tree;
Ext.onReady(function(){
 tree = new Ext.tree.TreePanel({
				title : '树',
				renderTo: 'tree-div',
				hideMode : 'offsets',
				split : true,
				collapsed : false,
				collapsible : true,
				width : 500,
				minSize : 200,
				rootVisible : true,
				autoScroll : true,
				tbar : [new Ext.Action({
				                    iconCls : 'imgConfig',
									text : 'expand All',
									handler : function() {
										tree.expandAll();
									}
								}), new Ext.Action({ 
								    iconCls : 'imgConfig',
									text : 'collapse All',
									handler : function() {
										tree.collapseAll();
									}
								}),{text:'selected',
								    iconCls : 'imgConfig',
								     handler:function()
								     {
								      var nodes = tree.getChecked();
								      for(var i=0;i<nodes.length;i++)
								      {alert(nodes[i].text);}
								     }
								     },{text:'clear',
								       iconCls : 'imgConfig',
								     handler:function()
								     {
								      var nodes = tree.getChecked();
								      var selModel = tree.getSelectionModel();
								      for(var i=0;i<nodes.length;i++)
								      {nodes[i].ui.checkbox.checked = false;//取消选中节点
								      nodes[i].ui.onCheckChange();//与模型数据同步
								      }
								     }
								     },{text:'选中所有',
								      iconCls : 'imgConfig',
								     handler:function()
								     {
								     tree.iteratorCheck(tree.getRootNode(),true);
								     }
								     }],
				root : new Ext.tree.TreeNode({
							id : 'root',
							text: 'i am root',
							checked: false
						}),
				id : '_MenuTree'
			});

	var first = new Ext.tree.TreeNode({
	checked:false,
				text : '第一季',
				expanded : true,
				id : 'first'
			});
	var second = new Ext.tree.TreeNode({
	checked:false,
				text : '第二季',
				expanded : true,
				id : 'second'
			});
			tree.getRootNode().appendChild(first);
			tree.getRootNode().appendChild(second);
	first.appendChild(new Ext.tree.TreeNode({
	checked:false,
				text : '第一季第一',
				id : 'first_1'
			}));
			first.appendChild(new Ext.tree.TreeNode({
			checked:false,
				text : '第一季第二',
				id : 'first_2'
			}));
			second.appendChild(new Ext.tree.TreeNode({
			checked:false,
				text : '第二季第一',
				id : 'second_1'
			}));
			second.appendChild(new Ext.tree.TreeNode({
			checked:false,
				text : '第二季第二',
				id : 'second_2'
			}));
tree.expandAll();
tree.on("checkchange",function(node){
var children = node.childNodes;
	 for(var i=0;i<children.length;i++)
		{children[i].ui.checkbox.checked = node.ui.checkbox.checked;//取消选中节点,每个节点都有一个ui对应
		 children[i].ui.onCheckChange();//与模型数据同步
 }
 });
								      
 tree.iteratorCheck=function(node,checked){
 if(node.hasChildNodes())
 {
	node.eachChild(function(currentNode){
	                     tree.iteratorCheck(currentNode,checked);			   
});
}
node.ui.checkbox.checked = checked;//取消选中节点
node.ui.onCheckChange();//与模型数据同步
};
								      
	
});