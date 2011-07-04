// 获得根目录
function getRootPath() {
	var strFullPath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullPath.indexOf(strPath);
	var prePath = strFullPath.substring(0, pos);
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return (prePath + postPath);
}
var rootPath = getRootPath();

// 表情扩展开始
Ext.EmoteChooser = function(config) {
	Ext.EmoteChooser.superclass.constructor.call(this, config);
	this.addEvents("select");
	if (this.handler) {
		this.on("select", this.handler, this.scope, true);
	}
};
Ext.extend(Ext.EmoteChooser, Ext.Component, {
	itemCls : "emote-chooser",
	value : null,
	clickEvent : "click",
	ctype : "Ext.EmoteChooser",
	allowReselect : false,
	emotes : ["001", "002", "003", "004", "005", "006", "007", "008", "009",
			"010", "011", "012", "013", "014", "015", "016", "017", "018",
			"019", "020", "021", "022"],
	onRender : function(container, position) {
		var t = this.tpl
				|| new Ext.XTemplate("<tpl for=\".\"><a class=\"emote-{.}\" href=\"#\"><em><img src=\"emote/emote_{.}.gif\" alt=\"\" /></em></a></tpl>");// 套用模板，图片路径需自己修改
		var el = document.createElement("div");
		el.className = this.itemCls;
		t.overwrite(el, this.emotes);
		container.dom.insertBefore(el, position);
		this.el = Ext.get(el);
		this.el.on(this.clickEvent, this.handleClick, this, {
					delegate : "a"
				});
		if (this.clickEvent != "click") {
			this.el.on("click", Ext.emptyFn, this, {
						delegate : "a",
						preventDefault : true
					});
		}
	},
	afterRender : function() {
		Ext.EmoteChooser.superclass.afterRender.call(this);
		if (this.value) {
			var s = this.value;
			this.value = null;
			this.select(s);
		}
	},
	handleClick : function(e, t) {
		e.preventDefault();
		if (!this.disabled) {
			var c = t.className.match(/(?:^|\s)emote-(.{3})(?:\s|$)/)[1];
			this.select(c.toUpperCase());
		}
	},
	select : function(emote) {
		// emote = emote.replace("#", "");
		if (emote != this.value || this.allowReselect) {
			var el = this.el;
			if (this.value) {
				el.child("a.emote-" + this.value)
						.removeClass("emote-chooser-sel");
			}
			el.child("a.emote-" + emote).addClass("emote-chooser-sel");
			this.value = emote;
			this.fireEvent("select", this, emote);
		}
	}
});
Ext.reg("emotechooser", Ext.EmoteChooser);

Ext.menu.EmoteItem = function(config) {
	Ext.menu.EmoteItem.superclass.constructor.call(this,
			new Ext.EmoteChooser(config), config);
	this.chooser = this.component;
	this.relayEvents(this.chooser, ["select"]);
	if (this.selectHandler) {
		this.on("select", this.selectHandler, this.scope);
	}
};
Ext.extend(Ext.menu.EmoteItem, Ext.menu.Adapter);
Ext.menu.EmoteMenu = function(config) {
	Ext.menu.EmoteMenu.superclass.constructor.call(this, config);
	this.plain = true;
	var ci = new Ext.menu.EmoteItem(config);
	this.add(ci);
	this.chooser = ci.chooser;
	this.relayEvents(ci, ["select"]);
};
Ext.extend(Ext.menu.EmoteMenu, Ext.menu.Menu);

// 加入表情扩展和图片扩展
HTMLEditor = Ext.extend(Ext.form.HtmlEditor, {
	addImage : function() {
		var editor = this;
		var imgform = new Ext.FormPanel({
					region : 'center',
					labelWidth : 60,
					autoHeight : true,
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					autoScroll : true,
					border : false,
					items : [{
								xtype : 'textfield',
								fieldLabel : '图片网址',
								name : 'img',
								id : 'img',
								allowBlank : false,
								blankText : '网址不能为空',
								height : 25,
								anchor : '90%',
								regex : /^(http|https|ftp):\/\//i,
								regexText : '请输入正确网址'
							}],
					buttons : [{
								text : '插入',
								type : 'submit',
								handler : function() {
									if (!imgform.form.isValid()) {
										return;
									}
									var text = Ext.getCmp("img").getValue();
									var element = document.createElement("img");
									element.src = text;
									if (Ext.isIE) {
										editor
												.insertAtCursor(element.outerHTML);
									} else {
										var selection = editor.win
												.getSelection();
										if (!selection.isCollapsed) {
											selection.deleteFromDocument();
										}
										selection.getRangeAt(0)
												.insertNode(element);
									}
									win.close();
								}
							}, {
								text : '关闭',
								type : 'submit',
								handler : function() {
									win.close(this);
								}
							}]
				})

		var win = new Ext.Window({
					title : "插入图片",
					width : 400,
					autoheight : true,
					modal : true,
					border : false,
					layout : "fit",
					items : imgform

				});
		win.show();
	},
	createToolbar : function(editor) {
		HTMLEditor.superclass.createToolbar.call(this, editor);
		// 图片扩展
		this.tb.insertButton(16, {
					cls : "x-btn-icon",
					icon : rootPath + '/uxHtmlEditor/imgIcon.jpg',
					handler : this.addImage,
					// cls : 'x-html-editor-tip',
					scope : this
				});
		// 表情扩展
		this.getToolbar().add("-", {
			tooltip : "\u9009\u62e9\u8868\u60c5",
			icon : "emote/emote_016.gif",
			cls : "x-btn-icon",
			menu : new Ext.menu.EmoteMenu({
						allowReselect : true,
						focus : Ext.emptyFn,
						plain : true,
						selectHandler : function(cm, emote) {
							var emimg = "<img src=\"emote/emote_" + emote
									+ ".gif\" alt=\"\" />";
							editor.insertAtCursor(emimg);
						},
						scope : this,
						clickEvent : "mousedown"
					})
		});

	}
});
Ext.reg('ImgHtmleditor', HTMLEditor);