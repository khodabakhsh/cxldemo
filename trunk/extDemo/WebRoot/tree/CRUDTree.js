
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
									text : '节点信息',
									handler : function() {
									var selNode = tree.getSelectionModel().getSelectedNode();
									if(!selNode){
									Ext.Msg.alert("INFO","没选择任何节点");return;}
										var parentNode = selNode.parentNode;
										var firstChild = selNode.firstChild;
										var lastChild = selNode.lastChild;
										var previousSibling = selNode.previousSibling;
										var nextSibling = selNode.nextSibling;
										Ext.MessageBox.alert("INFO",(parentNode?"父节点: " +parentNode.text:"无父节点")
										+"<br>" + (firstChild?"第一个子节点: " +firstChild.text:"无第一个子节点")
										+"<br>" + (lastChild?"最后子节点: " +lastChild.text:"无最后子节点")
										+"<br>" + (previousSibling?"上一个兄弟节点: " +previousSibling.text:"无上一个兄弟节点")
										+"<br>" + (nextSibling?"下一个兄弟节点: " +nextSibling.text:"无下一个兄弟节点")
										+"<br>节点路径： " + selNode.getPath("text")
										);
									}
								}), new Ext.Action({
								    iconCls : 'imgConfig',
									text : '添加同级（前）',
									handler : function() {
									   var selNode = tree.getSelectionModel().getSelectedNode();
									     if(!selNode){
										    Ext.Msg.alert("INFO","请先选择一个参照节点");return;}
										 if(selNode.id == tree.getRootNode().id)
										    { Ext.Msg.alert("INFO","根节点不能添加同级节点");return;}
										Ext.Msg.prompt("输入","请输入新节点的名称: ",function(btn,txt){
										if(btn=='ok'){
										var newNode = new Ext.tree.TreeNode({text:txt});
										selNode.parentNode.insertBefore(newNode,selNode);
										 }
										})    
										   
									}
								}),{text:'添加同级（后）',
								    iconCls : 'imgConfig',
								     handler:function()
								     {
								      var selNode = tree.getSelectionModel().getSelectedNode();
									     if(!selNode){
										    Ext.Msg.alert("INFO","请先选择一个参照节点");return;}
										 if(selNode.id == tree.getRootNode().id)
										    { Ext.Msg.alert("INFO","根节点不能添加同级节点");return;}
										Ext.Msg.prompt("输入","请输入新节点的名称: ",function(btn,txt){
										if(btn=='ok'){
										var newNode = new Ext.tree.TreeNode({text:txt});
										selNode.parentNode.insertBefore(newNode,selNode.nextSibling);
										 }
										})   
								     }
								     },{text:'添加子节点',
								     iconCls : 'imgConfig',
								     handler:function()
								     {
								     var selNode = tree.getSelectionModel().getSelectedNode();
									     if(!selNode){
										    Ext.Msg.alert("INFO","请先选择一个参照节点");return;}
										Ext.Msg.prompt("输入","请输入新节点的名称: ",function(btn,txt){
										if(btn=='ok'){
										var newNode = new Ext.tree.TreeNode({text:txt});
										 selNode.appendChild(newNode);
										 selNode.expand();//新增的字节点不会自己展开，调用此方法
										 }
										})   
								    
								     }
								     },{text:'删除',
								     iconCls : 'imgConfig',
								     handler:function()
								     {
								      var selNode = tree.getSelectionModel().getSelectedNode();
									     if(!selNode){
										    Ext.Msg.alert("INFO","请先选择一个节点");return;}
										 if(selNode.id == tree.getRootNode().id)
										    { Ext.Msg.alert("INFO","根节点不能删除");return;}
										 selNode.remove();
										
								     }
								     }],
				root : new Ext.tree.TreeNode({
							id : 'root',
							text: 'i am root'
						}),
				id : '_MenuTree'
			});

	var first = new Ext.tree.TreeNode({
				text : '第一季',
				expanded : true,
				id : 'first'
			});
	var second = new Ext.tree.TreeNode({
				text : '第二季',
				expanded : true,
				id : 'second'
			});
			tree.getRootNode().appendChild(first);
			tree.getRootNode().appendChild(second);
	first.appendChild(new Ext.tree.TreeNode({
				text : '第一季第一',
				id : 'first_1'
			}));
			first.appendChild(new Ext.tree.TreeNode({
				text : '第一季第二',
				id : 'first_2'
			}));
			second.appendChild(new Ext.tree.TreeNode({
				text : '第二季第一',
				id : 'second_1'
			}));
			second.appendChild(new Ext.tree.TreeNode({
				text : '第二季第二',
				id : 'second_2'
			}));
tree.expandAll();

var editor = new Ext.tree.TreeEditor(tree,{allowBlank : false});//加入节点编辑功能，节点文本非空

//监听事件
editor.on("complete",function(editor,value,startValue){
    alert("原值： "+ startValue + ",新值：" +value);
    alert("被修改节点： "+ editor.editNode);
  });
  
  tree.on("insert",function(tree,parent,node,refNode){
    alert("inserted ");
  });
  
  tree.on("remove",function(tree,parent,node){
    alert("removed ");
  });
   tree.on("append",function(tree,parent,node){
    alert("appended ");
  });
  
});