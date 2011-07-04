
var tree;
Ext.onReady(function(){
var root = new Ext.tree.AsyncTreeNode({id:'0',text:'根节点',leaf: false});
 tree = new Ext.tree.TreePanel({
				title : 'Async树',
				hideMode : 'offsets',
				split : true,
				collapsed : false,
				collapsible : true,
				width : 500,
				minSize : 200,
				autoScroll : true,
				//dataUrl:'',  配置了loader就可以不用这个了，也可以直接用这个
				loader : new Ext.tree.TreeLoader(
				            {baseParams:{a:1,b:2,c:3},//可以传参数过去，如a,b,c三个参数过去，
				             dataUrl:'../demo/asyncTree'   }),
				id : '_MenuTree'
			});

tree.setRootNode(root);
tree.render('tree-div');
tree.expandAll();
});