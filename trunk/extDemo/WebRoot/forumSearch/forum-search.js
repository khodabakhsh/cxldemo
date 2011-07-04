/*
 * Ext JS Library 2.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function(){

    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '/extdemo/demo/customSearchField'
        }),
        reader: new Ext.data.JsonReader({
            root: 'topics',
            totalProperty: 'totalCount',
            id: 'id'
        }, [
            {name: 'title', mapping: 'title'},
            {name: 'author', mapping: 'author'},
            {name: 'lastPostTime', mapping: 'lastPostTime', type: 'date', dateFormat: 'yyyy-MM-dd'},
            {name: 'postText', mapping: 'postText'}
        ])
    });

    // Custom rendering Template
    var resultTpl = new Ext.XTemplate(
        '<tpl for="."><div class="search-item">{title:this.catalog}',
            '<h3><span>{lastPostTime:date("Y年m月d日")}<br />by {author}</span>{title}</h3>',
            '{postText}',
        '</div></tpl>'
    );
    
    //为了演示作用，title为java的显示男生头像，其他显示女生头像
    resultTpl.catalog=function(value){
    return value=='java'?"<img src='user_female.gif'>":"<img src='user.gif'>";
    }
    var search = new Ext.form.ComboBox({
        store: ds,
        displayField:'title',
        typeAhead: false,
        loadingText: 'Searching...',
        width: 570,
        pageSize:5,
        hideTrigger:true,
        tpl: resultTpl,
        remote :true,
        applyTo: 'search',
        itemSelector: 'div.search-item',
        onSelect: function(record){ // override default onSelect to do redirect
            window.location =
                String.format('http://extjs.com/forum/showthread.php?t={0}&p={1}', record.data.title, record.id);
        }
    });
});