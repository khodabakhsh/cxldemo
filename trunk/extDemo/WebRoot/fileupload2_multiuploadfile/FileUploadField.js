function roundValue(val) {
	val = parseFloat(val);
	val = Math.round(val * 100) / 100;
	val = val.toString();

	if (val.indexOf(".") < 0) {
		val += ".00"
	} else {
		var dec = val.substring(val.indexOf(".") + 1, val.length)
		if (dec.length > 2)
			val = val.substring(0, val.indexOf(".")) + "."
					+ dec.substring(0, 2)
		else if (dec.length == 1)
			val = val + "0"
	}
	return val;
}

/**
 * @copyright Copyright Intermesh 2007
 * @author Merijn Schering <mschering@intermesh.nl>
 * 
 * Based on the File Upload Widget of Ing. Jozef Sakalos
 * 
 * This file is part of Group-Office.
 * 
 * Group-Office is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * See file /LICENSE.GPL
 */
// TODO:增加文件类型验证，文件大小验证
Ext.namespace('Ext.ux');

/*
 * {attachedfilelist:[{id:1,fileName:'工作报告.doc'},{id:2,fileName:'使用手册.doc'}]}
 * attachFileId:为附件的主键ID，已写固定，不可改变 hidden:控制是否可以增加和删除附件
 */
Ext.ux.UploadFile = function(config) {
	this.inputs = new Ext.util.MixedCollection();
	this.uploadedFileList = new Ext.util.MixedCollection();
	Ext.ux.UploadFile.superclass.constructor.call(this, config);
};

Ext.extend(Ext.ux.UploadFile, Ext.form.Hidden, {
	maxNameLength : 80,
	// defaultAutoCreate : {tag: "div"},
	addText : '添加附件',
	addIconCls : 'x-uf-input-file-add',
	deleteIconCls : 'x-uf-input-file-delete',
	fileCls : 'file',
	name : 'attachedfilelist',
	id : 'attachedfilelist',
	fileListIdPrefix : 'filelist_',
	downloadAttachFileUrl : '#',
	hidden : false,
	autoHeight : true,
	maxFileSize : 0,

	initComponent : function() {
		Ext.ux.UploadFile.superclass.initComponent.call(this);
		this.addEvents(
				/**
		 * @event beforemovefile 删除页面文件之前的触发事件，将页面的显示的附件移除，但还没有删除数据库
		 * @param {Ext.ux.UploadFile}
		 *            Ext.ux.UploadFile对象
		 * @param {uploadFileId}
		 *            已经上传文件在数据库中的主键ID
		 */
				'beforemovefile',

				/**
				 * @event beforedeletefile 删除已经上传的文件之前的触发事件，会删除数据库
				 * @param {Ext.ux.UploadFile}
				 *            Ext.ux.UploadFile对象
				 * @param {uploadFileId}
				 *            已经上传文件在数据库中的主键ID
				 */
				'beforedeletefile');

	},
	onRender : function(ct, position) {

		Ext.ux.UploadFile.superclass.onRender.call(this, ct, position);
		this.id = Ext.id();

		this.uploadFileDivWrap = this.el.insertSibling({
					tag : 'div',
					id : this.id
				});
		this.uploadFileDivWrap.setHeight(this.height);
		this.uploadFileDivWrap.setStyle("overflow", "auto");

		if (!this.hidden) {
			this.createButtons();
			this.createUploadInput();
		}

	},

	setValue : function(v) {
		this.value = v;

		if (!this.rendered) {
			return;
		}

		if (v != null && v != undefined) {
			var fileListLength = v.length;
			this.createDisplayFileQueueListTable();
			for (var i = 0; i < fileListLength; i++) {
				var fileUploadObj = v[i];
				fileUploadObj.id = this.fileListIdPrefix + fileUploadObj.id;
				this.uploadedFileList.add(fileUploadObj);
				this.appendRow(fileUploadObj.fileName, fileUploadObj);
			}
		}
		this.el.dom.name = this.el.dom.name + '_hidden';
		this.el.dom.id = this.el.dom.id + '_hidden';

		/*
		 * var strValue = Ext.util.JSON.encode(v); this.value = strValue;
		 * if(this.rendered){ this.el.dom.value = (strValue === null || strValue
		 * === undefined ? '' : strValue); this.validate(); }
		 */
	},

	createUploadInput : function() {

		if (!this.inputName) {
			this.inputName = Ext.id();
		}

		var id = Ext.id();

		var inp = this.inputWrap.createChild({
					tag : 'input',
					type : 'file',
					cls : 'x-form-file',
					size : 1,
					id : id,
					name : this.inputName
				});
		inp.on('change', this.onFileAdded, this);
		this.inputs.add(inp);

		return inp;
	},

	createButtons : function() {

		// create containers sturcture
		// var buttonsDiv = this.el.insertSibling({tag: 'div', id: this.id},
		// 'after', true);

		this.buttonsWrap = this.uploadFileDivWrap.createChild({
					tag : 'div',
					cls : 'x-uf-buttons-ct',
					children : [{
								tag : 'div',
								cls : 'x-uf-input-ct',
								children : [{
											tag : 'div',
											cls : 'x-uf-bbtn-ct',
											children : [{
														tag : 'div',
														cls : 'x-uf-input-information',
														html : '提示：您能发送的附件总容量最大为50M'
													}]
										}]
							}

					]
				});

		// save containers for future use

		this.inputWrap = this.buttonsWrap.select('div.x-uf-bbtn-ct').item(0);
		this.addBtnCt = this.buttonsWrap.select('div.x-uf-input-ct').item(0);

		// add button
		var bbtnDiv = this.buttonsWrap.select('div.x-uf-bbtn-ct');
		var bbtnCt = bbtnDiv.item(0);
		this.browseBtn = new Ext.Button({
					id : 'btnFileUpload', // shappy
					renderTo : bbtnCt,
					text : this.addText,
					cls : 'x-btn-text-icon',
					iconCls : this.addIconCls
				});

		// Ext.DomHelper.append(this.addBtnCt,"<div
		// class='x-uf-show-msg'>ddddd</div>");
	},

	createDisplayFileQueueListTable : function() {
		// create table to hold the file queue list
		if (!this.table) {
			this.table = this.uploadFileDivWrap.createChild({
						tag : 'table',
						cls : 'x-uf-table',
						children : [{
									tag : 'tbody'
								}]
					});
			this.tbody = this.table.select('tbody').item(0);

			this.table.on({
						click : {
							scope : this,
							fn : this.onDeleteFile,
							delegate : '.deleteAttachFile'
						}
					});

		}
	},

	/**
	 * File added event handler
	 * 
	 * @param {Event}
	 *            e
	 * @param {Element}
	 *            inp Added input
	 */

	onFileAdded : function(e, inp) {
		// var inp = this.inputs.itemAt(this.inputs.getCount() - 1);
		// // 判断上传文件的大小，注意，访问地址必须放到受信任站点中！
		// var fso = new ActiveXObject("Scripting.FileSystemObject");
		// if (inp.getValue() != "" && fso) {
		// var fileSize = fso.GetFile(inp.getValue()).size;
		// if (fileSize + this.maxFileSize > 20971520) {
		// if (inp && inp.row) {
		// inp.row.remove();
		// }
		// if (inp) {
		// inp.remove();
		// }
		// this.inputs.removeKey(inp.id);
		// var id = Ext.id();
		// var otherInp = this.inputWrap.createChild({
		// tag : 'input',
		// type : 'file',
		// cls : 'x-form-file',
		// size : 1,
		// id : id,
		// name : this.inputName
		// });
		// otherInp.on('change', this.onFileAdded, this);
		// this.inputs.add(otherInp);
		// alert("上传文件大小超过限制！");
		// return false;
		// }
		// this.maxFileSize = this.maxFileSize + fileSize;
		//
		// // hide all previous inputs
		// this.inputs.each(function(i) {
		// i.setDisplayed(false);
		// });
		//
		// this.createDisplayFileQueueListTable();
		//
		// // add input to internal collection
		// var inp = this.inputs.itemAt(this.inputs.getCount() - 1);
		//
		// // uninstall event handler
		// inp.un('change', this.onFileAdded, this);
		//
		// // append input to display queue table
		// this.appendRow(inp.getValue(), inp);
		//
		// // create new input for future use
		// this.createUploadInput();

		// hide all previous inputs
		this.inputs.each(function(i) {
					i.setDisplayed(false);
				});

		this.createDisplayFileQueueListTable();

		// add input to internal collection
		var inp = this.inputs.itemAt(this.inputs.getCount() - 1);

		// uninstall event handler
		inp.un('change', this.onFileAdded, this);

		// append input to display queue table
		this.appendRow(inp.getValue(), inp);

		// create new input for future use
		this.createUploadInput();
	},
	/**
	 * Appends row to the queue table to display the file Override if you need
	 * another file display
	 * 
	 * @param {Element}
	 *            inp Input with file to display
	 */
	appendRow : function(fileName, inp) {
		// var fileName = inp.getValue();
		var attachFileSize = (inp.attachFileSize != null)
				? inp.attachFileSize
				: -1;

		var showAttachFileSizeStr = "";

		if (attachFileSize >= 0) {
			attachFileSize = roundValue(attachFileSize / 1024);
			showAttachFileSizeStr = "(" + attachFileSize + "KB)"
		}

		var o = {
			id : inp.id,
			fileCls : this.getFileCls(fileName),
			// fileName: Ext.util.Format.ellipsis(fileName.split(/[/]/).pop(),
			// this.maxNameLength),
			fileName : fileName.substr(fileName.lastIndexOf("\\") + 1),
			fileQtip : fileName
		}

		var deleteImgHref = "";
		if (!this.hidden) {
			deleteImgHref = '<a id="d-{id}" class="deleteAttachFile" href="#"><img class="'
					+ this.deleteIconCls
					+ '" src="'
					+ Ext.BLANK_IMAGE_URL
					+ '"></a>';
		}
		var downloadAttachFileHrefHTML = '{fileName}';
		if (inp.id && inp.id.indexOf(this.fileListIdPrefix) == 0) {
			var fileId = inp.id.substr(this.fileListIdPrefix.length);
			// shappy
			if (inp.url == null)
				downloadAttachFileHrefHTML = '<a href="'
						+ this.downloadAttachFileUrl + '?attachFileId='
						+ fileId + '" target="_blank">{fileName}</a>'
						+ showAttachFileSizeStr;
			else
				downloadAttachFileHrefHTML = '<a href="' + appPath + inp.url
						+ '" target="_blank">{fileName}</a>'
						+ showAttachFileSizeStr;
		}
		var t = new Ext.Template([
				'<tr id="r-{id}">',
				'<td class="x-unselectable x-tree-node-leaf">',
				'<img class="x-tree-node-icon {fileCls}" src="'
						+ Ext.BLANK_IMAGE_URL + '">',
				'<span class="x-uf-filename" unselectable="on" qtip="{fileQtip}">'
						+ downloadAttachFileHrefHTML + '</span>'
						+ deleteImgHref, '</td>', '</tr>']);

		// save row reference for future
		inp.row = t.append(this.tbody, o, true);

	},

	onDeleteFile : function(e, target) {
		this.removeFile(target.id.substr(2));
	},

	/**
	 * Removes file from the queue private
	 * 
	 * @param {String}
	 *            id Id of the file to remove (id is auto generated)
	 * @param {Boolean}
	 *            suppresEvent Set to true not to fire event
	 */
	removeFile : function(id) {
		if (this.uploading) {
			return;
		}

		if (id && id.indexOf(this.fileListIdPrefix) == 0) {
			var uploadedFile = this.uploadedFileList.get(id);
			var fileId = uploadedFile.id.substr(this.fileListIdPrefix.length);
			if (this.fireEvent('beforemovefile', this, fileId) !== false) {
				if (uploadedFile && uploadedFile.row) {
					uploadedFile.row.remove();
				}

				this.uploadedFileList.removeKey(id);

				if (this.fireEvent('beforedeletefile', this, fileId) !== false) {
					if (this.deleteAttachFileUrl
							&& this.deleteAttachFileUrl != ''
							&& this.deleteAttachFileUrl != undefined) {

						Ext.Ajax.request(Ext.apply(this.createCallback(), {
									url : this.deleteAttachFileUrl,
									method : 'GET',
									params : this
											.getDeleteAttachFileParams(fileId)
								}));
					}
				}
			}

		} else {
			var inp = this.inputs.get(id);
			if (inp && inp.row) {
				inp.row.remove();
			}
			if (inp) {
				inp.remove();
			}
			this.inputs.removeKey(id);
		}

	},

	getDeleteAttachFileParams : function(fileId) {
		this.deleteFileIdParam;
		var p = {
			attachFileId : fileId
		};
		return p;
	},

	createCallback : function() {
		return {
			success : this.success,
			failure : this.failure,
			scope : this
		};
	},
	/*
	 * 接口，回调成功后调用
	 */
	success : function(response) {
	},
	/*
	 * 接口，回调失败后调用
	 */
	failure : function(response) {
	},

	getFileCls : function(fileName) {
		var returnValue = 'x-uf-unknown-small';
		if (fileName != null && fileName != "") {
			var extensionName = fileName.substring(fileName.lastIndexOf(".")
					+ 1).toLowerCase();
			var css = '.x-uf-' + extensionName + '-small';
			if (Ext.isEmpty(Ext.util.CSS.getRule(css), true)) { // 判断样式是否存在
				returnValue = "x-uf-unknown-small";
			} else {
				returnValue = 'x-uf-' + extensionName + '-small';
			}
		}
		return returnValue;
	},
	clearQueue : function() {
		this.inputs.each(function(inp) {
					if (!inp.isVisible()) {
						this.removeFile(inp.id, true);
					}
				}, this);
	},
	clearFile : function(id) {
		if (this.uploading) {
			return;
		}
		if (id && id.indexOf(this.fileListIdPrefix) == 0) {
			var uploadedFile = this.uploadedFileList.get(id);
			var fileId = uploadedFile.id.substr(this.fileListIdPrefix.length);
			if (this.fireEvent('beforemovefile', this, fileId) !== false) {
				if (uploadedFile && uploadedFile.row) {
					uploadedFile.row.remove();
				}
				this.uploadedFileList.removeKey(id);
			}
		} else {
			var inp = this.inputs.get(id);
			if (inp && inp.row) {
				inp.row.remove();
			}
			if (inp) {
				inp.remove();
			}
			this.inputs.removeKey(id);
		}
	},

	cleanAllFile : function() {
		this.uploadedFileList.each(function(file) {
					this.clearFile(file.id, true);
				}, this);
		this.inputs.each(function(inp) {
					if (!inp.isVisible()) {
						this.clearFile(inp.id, true);
					}
				}, this);
	},

	/**
	 * Disables/Enables the whole form by masking/unmasking it
	 * 
	 * @param {Boolean}
	 *            disable true to disable, false to enable
	 * @param {Boolean}
	 *            alsoUpload true to disable also upload button
	 */
	setDisabled : function(disable) {

		if (disable) {
			this.addBtnCt.mask();
		} else {
			this.addBtnCt.unmask();
		}
	},

	setVisible : function(visible) {
		if (visible) {
			if (this.addBtnCt) {
				this.addBtnCt.show()
			}
		} else {
			if (this.addBtnCt) {
				this.addBtnCt.hide()
			}
		}
		for (var i = 0; i < this.uploadedFileList.getCount(); i++) {
			var inp = this.uploadedFileList.itemAt(i)
			var img = document.getElementById('d-' + inp.id)
			if (visible) {
				if (img) {
					img.style.visibility = 'visible';
				}
			} else {
				if (img) {
					img.style.visibility = 'hidden';
				}
			}
		}
	}
});
Ext.reg('multiuploadfile', Ext.ux.UploadFile);