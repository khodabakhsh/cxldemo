//TabPanel右击选项卡弹出菜单 插件，通过配置plugins 参数就可以使用。
Ext.ux.TabCloseMenu = function(){
   var tabs,menu,ctxItem;
   this.init = function(tp){//一个component组件配置了plugins 参数，就会调用该插件的inti方法。
       tabs=tp;
       tabs.on('contextmenu',onContextMenu);//TabPanel中右键单击选项卡触发contextmenu事件
   };
   function onContextMenu(ts,item,e){
      if(!menu){//create context menu on first right click
         menu = new Ext.menu.Menu([{
           id:tabs.id +'-close',
           text:'关闭标签',
           handler:function(){
                   tabs.remove(ctxItem);
           }
         },{
           id:tabs.id +'-close-others',
           text:'关闭其他标签',
           handler:function(){
                   tabs.items.each(function(item){
                       if(item.closable && item!=ctxItem){
                           tabs.remove(item);
                       }
                   });
            }
         },{                  
           id:tabs.id +'-close-all',
           text:'关闭全部标签',
           handler:function(){
                   tabs.items.each(function(item){
                       if(item.closable ){
                           tabs.remove(item);
                       }
                   });
            }            
         },"-",{
           id:tabs.id +'-fresh',
           text:'刷新',
           handler:function(){
                   ctxItem.getUpdater.update(ctxItem.autoLoad.url);
           }
        },{
           id:tabs.id +'-fresh-all',
           text:'刷新全部',
           handler:function(){
                  tabs.items.each(function(item){
                   item.getUpdater.update(item.autoLoad.url);  
                    });
            }
         }]);
      }
      
      ctxItem = item;
      var items = menu.items;
      
      //设置被单击的选项卡是否可以关闭，可以的话，就启用关闭标签按钮
      items.get(tabs.id + '-close').setDisabled(!item.closable);
      
      //设置是否有其他选项卡可以关闭，可以的话，就启用关闭其他标签按钮
      var disableOthers = true;
      tabs.items.each(function(){
          if(this!=item && this.closable){
               disableOthers = false;
               return false;
          }
      });
      items.get(tabs.id +'-close-others').setDisabled(disableOthers);
      
       //设置是否存在选项卡可以关闭，可以的话，就启用关闭全部其他标签按钮
      var disableAll = true;
      tabs.items.each(function(){
          if(this.closable){
               disableAll = false;
               return false;
          }
      });
       items.get(tabs.id +'-close-all').setDisabled(disableAll);   
       
       menu.showAt(e.getPoint());
  }
};             
      
      
      
      
      
      
      
      
      
                                   