Ext.onReady(function(){

var tabs = new Ext.TabPanel({
    renderTo: 'tab-div',
    enableTabScroll:true,
    border:false,
    frame: true,
    autoWidth : true,
    layoutOnTabChange: true,
    defaults:{autoScroll: true},
    height:500,
    plugins: [new Ext.ux.TabCloseMenu()] ,
    closable : true,
    defaults: {closable : true},
    items:[{
   title: "选项卡1",
   html: "第一季",
   bodyStyle:'padding : 10px'
 },{
   title: "选项卡2",
   html: "第二季",
   bodyStyle:'padding : 10px'
 },{
   title: "选项卡3",
   html: "第三季",
   bodyStyle:'padding : 10px'
 },{
   title: "选项卡3",
   html: "第四季",
   bodyStyle:'padding : 10px'
 }]
});

});