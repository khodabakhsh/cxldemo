Ext.onReady(function() {

			var window = new Ext.Window({
						// contentEl : Ext.getBody(),
						width : 250,
						height : 150,
						shadow : false,
						html : "试试试试...",
						title : "温馨提示:"
					});
			function show() {
				this.el.alignTo(Ext.getBody(), 'br-br');
				this.el.slideIn('b', {
							easing : 'easeOut',
							callback : function() {
								this.close.defer(3000, this); // 定时关闭窗口
							},
							scope : this,
							duration : 1
						});

			}
			function hide() {
				if (this.isClose === true) { // 防止点击关闭和定时关闭处理
					return false;
				}
				this.isClose = true;
				this.el.slideOut('b', {
							easing : 'easeOut',
							callback : function() {
								this.un('beforeclose', hide, this);
								this.close();
							},
							scope : this,
							duration : 5
						});
				return false;
			}
			window.on('show', show, window);
			window.on('beforeclose', hide, window)
			window.show();
		})