var center ;
var left;
var viewport;
Ext.onReady(function(){

 center = new Ext.TabPanel({
        region: 'center',
        items: {
            id: 'opt1',
            title:'default Tab',
            html: 'hello world again'
        },
        enableTabScroll :true,
        plugins: [new Ext.ux.TabCloseMenu()]
    });
 center.setActiveTab('opt1');
 
 left = new Ext.tree.TreePanel({
        region: 'west',
        collapsible: true,
        title: 'Navigation',
        width: 200,
        autoScroll: true,
        loader: new Ext.tree.TreeLoader( {baseParams:{a:1,b:2,c:3},//可以传参数过去，如a,b,c三个参数过去，
				             dataUrl:'../demo/asyncTree'   }),
        root: new Ext.tree.AsyncTreeNode({id:'0',text:'根节点',leaf: false}),
        rootVisible: true,
        listeners: {
            click: function(n) {
            var url = n.attributes.url;//本身TreeNode并没有这个属性，但是TreeNode的属性是可以自己加的，上面的树节点返回值中包含了url，因此可以应用
            
            
            var id  = n.attributes.id;//如果id没有定义，则自动生成一个唯一id
            var p = center.getItem(id);//获取节点id对应的面板
            if(url){
               if(p){//如果已经存在该选项卡,激活它
                   center.setActiveTab(p);
                  }
               else{//如果不存在该选项卡,创建并激活它
                   p = new Ext.Panel({
                       title: n.attributes.text,
                       autoLoad: {url : url},
                       closable: true,
                       id :id //这里必须设置
                   });
                    center.add(p);
                    center.setActiveTab(p);
                    }
            }
            else{Ext.MessageBox.show({
   title:'信息',
   msg: '此节点没有配置url信息',
   icon: Ext.MessageBox.INFO
});
}
        }
       }
     });   
viewport = new Ext.Viewport({
    layout: 'border',
    items: [{
        region: 'north',
        title: '标题',
        html: 'L O G O',
        height: 80,
        border: true,
        margins: '0 0 5 0'
    }, left,center , {
        region: 'south',
        title: 'Information',
        collapsible: true,
        html: 'Information goes here',
        height: 100,
        minHeight: 100
    }]
});
left.expandAll();

});