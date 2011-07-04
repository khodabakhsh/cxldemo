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
            {name: 'id', mapping: 'id'},
            {name: 'title', mapping: 'title'},
            {name: 'author', mapping: 'author'},
            {name: 'postText', mapping: 'postText'}
        ]),

        baseParams: {limit:5, forumId: 4}
    });

    // Custom rendering Template for the View
    var resultTpl = new Ext.XTemplate(
        '<tpl for=".">',
        '<div class="search-item">',
            '<h3><span><br />by {author}</span>',
            '<a href="http://extjs.com/forum/showthread.php?p={id}" target="_blank">{title}</a></h3>',
            '<p>{postText}</p>',
        '</div></tpl>'
    );

    var panel = new Ext.Panel({
        applyTo: 'search-panel',
        title:'Forum Search',
        height:300,
        autoScroll:true,

        items: new Ext.DataView({
            tpl: resultTpl,
            store: ds,
            itemSelector: 'div.search-item'
        }),

        tbar: [
            'Search: ', ' ',
            new Ext.app.SearchField({
                store: ds,
                width:320
            })
        ],

        bbar: new Ext.PagingToolbar({
            store: ds,
            pageSize: 5,
            displayInfo: true,
            displayMsg: 'Topics {0} - {1} of {2}',
            emptyMsg: "No topics to display"
        })
    });

    ds.load({params:{start:0, limit:5, forumId: 4}});
});
