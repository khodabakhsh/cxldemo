/**
 * 大量文字输入控件，点击弹出文字输入窗口
 * 
 * @class App.widget.PopupTextArea
 * @extends Ext.ux.form.TriggerField
 * @author hongpf
 * @date 2009-5-26
 */
Ext.ns('App.widget');
App.widget.PopupTextArea = Ext.extend(Ext.ux.form.TriggerField, {

	trigger2Class : 'x-form-more-trigger',
	/**
	 * @cfg {Integer} winWidth 弹出框的宽度
	 */
	winWidth : null,
	/**
	 * @cfg {Integer} minWinWidth 弹出框最小宽度
	 */
	minWinWidth : 420,
	/**
	 * @cfg {Integer} winHeight 弹出框高度
	 */
	winHeight : null,
	/**
	 * @cfg {Integer} minWinHeight 弹出框最小高度
	 */
	minWinHeight : 300,

	winTitle : '输入内容',

	maxLength : 1000,

	maxLengthText : '内容长度不能超过500个字！',

	initComponent : function() {
		App.widget.PopupTextArea.superclass.initComponent.call(this);
		this.addEvents('select', 'cancel');
	},
	initWin : function() {
		var width = this.winWidth || this.minWinWidth;
		var height = this.winHeight || this.minWinHeight;

		var showFieldLabel = this.fieldLabel.replace(/<.*>/g, '');
		this.maxLengthText = showFieldLabel + '的最大长度是 ' + this.maxLength + ' 个字符，或 ' + this.maxLength / 2 + ' 个汉字！';
		if (!this.win) {
			if (!this.form) {
				var textAreafield = new Ext.form.TextArea({
					maxLength : this.maxLength,
					maxLengthText : this.maxLengthText,
					name : 'text'
				});
				this.form = new Ext.FormPanel({
					frame : false,
					header : false,
					bodyBorder : false,
					layout : 'fit',
					width : width - 20,
					height : height - 50,
					defaults : {
						width : width - 30
					},
					defaultType : 'textfield',
					items : [textAreafield]
				});
				this.form.textAreafield = textAreafield;
			}

			this.win = new Ext.Window({
				title : this.winTitle,
				width : this.winWidth || this.minWinWidth,
				height : this.winHeight || this.minWinHeight,
				autoScroll : true,
				bodyStyle : 'padding:5px;',
				layout : 'fit',
				closeAction : 'hide',
				items : [this.form],
				buttons : [{
					text : "确定",
					handler : this.confirm.createDelegate(this)
				}, {
					text : "取消",
					handler : this.cancel.createDelegate(this)
				}]
			});

			this.fireEvent("initWin", this);
		}
	},
	onRender : function(ct, position) {
		App.widget.PopupTextArea.superclass.onRender.call(this, ct, position);
		if (Ext.isGecko) {
			this.el.dom.setAttribute('autocomplete', 'off');
		}
		this.initWin();
	},
	confirm : function() {
		var hVal = Ext.getCmp(this.hiddenName);
		if (!this.form.getForm().isValid()) {
			Ext.Msg.alert('提示', this.maxLengthText);
			return;
		}
		var values = this.form.getForm().getValues();
		if (!values.text) {
			values.text = '';
		}
		var value = values.text;
		this.setValue(value);
		this.win.hide();
	},

	cancel : function() {
		this.win.hide();
	},
	setValue : function(v) {
		App.widget.PopupTextArea.superclass.setValue.call(this, v);
		this.onChange();
	},

	validateBlur : function() {
		return !this.win || !this.win.isVisible();
	},
	onDisable : function() {
		App.widget.PopupTextArea.superclass.onDisable.call(this);
		if (this.wrap) {
			// this.wrap.addClass(this.disabledClass);
			// this.el.removeClass(this.disabledClass);
		}
	},
	onDestroy : function() {
		if (this.win.el) {
			this.win.destroy();
		}
		App.widget.PopupTextArea.superclass.onDestroy.call(this);
	},
	onTriggerClick : function() {
		this.win.show();
		if (this.form.rendered) {
			this.form.textAreafield.setReadOnly(this.disabled || this.readOnly);
		}
		this.win.buttons[0].setDisabled(this.disabled || this.readOnly);
		this.onFocus();
		this.form.getForm().setValues({
			text : this.getValue()
		});
		this.form.find('name', 'text')[0].focus(false, 100);
	}
});
Ext.reg('popupTextArea', App.widget.PopupTextArea);