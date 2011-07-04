Usage example.
This is the code taken from upload-dialog.js. 

dialog = new Ext.ux.UploadDialog.Dialog({
  url: 'upload-dialog-request.php',
  reset_on_hide: false,
  allow_close_on_upload: true,
  upload_autostart: true
});
...
dialog.show('show-button');Configuration.
Most configuration options are inherited from Ext.Window (see ExtJs docs). The added ones are: 

url - the url where to post uploaded files. 
base_params - additional post params (default to {}). 
permitted_extensions - array of file extensions which are permitted to upload (default to []). 
reset_on_hide - whether to reset upload queue on dialog hide or not (default true). 
allow_close_on_upload - whether to allow hide/close dialog during upload process (default false). 
upload_autostart - whether to start upload automaticaly when file added or not (default false). 
post_var_name - uploaded data POST variable name (defaults to 'file'). 
Events.
filetest - fires before file is added into the queue, parameters:
dialog - reference to dialog
filename - file name
If handler returns false then file will not be queued. 
fileadd - fires when file is added into the queue, parameters:
dialog - reference to dialog
filename - file name 
fileremove - fires when file is removed from the queue, parameters:
dialog - reference to dialog
filename - file name
record - file record 
resetqueue - fires when upload queue is resetted, parameters:
dialog - reference to dialog

beforefileuploadstart - fires when file as about to start uploading:
dialog - reference to dialog
filename - uploaded file name
record - file record
If handler returns false then file upload will be canceled. 
fileuploadstart - fires when file has started uploading:
dialog - reference to dialog
filename - uploaded file name
record - file record 
uploadsuccess - fires when file is successfuly uploaded, parameters:
dialog - reference to dialog
filename - uploaded file name
data - js-object builded from json-data returned from upload handler response.
record - file record 
uploaderror - fires when file upload error occured, parameters:
dialog - reference to dialog
filename - uploaded file name
data - js-object builded from json-data returned from upload handler response.
record - file record 
uploadfailed - fires when file upload failed, parameters:
dialog - reference to dialog
filename - failed file name
record - file record 
uploadcanceled - fires when file upload canceled, parameters:
dialog - reference to dialog
filename - failed file name
record - file record 
uploadstart - fires when upload process starts, parameters:
dialog - reference to dialog 
uploadstop - fires when upload process stops, parameters:
dialog - reference to dialog 
uploadcomplete - fires when upload process complete (no files to upload left), parameters:
dialog - reference to dialog 
Public methods
Better go see the source. 

I18n.
The class is ready for i18n, override the Ext.ux.UploadDialog.Dialog.prototype.i18n object with your language strings, or just pass i18n object in config. 

Server side handler.
The files in the queue are posted one at a time, the file field name is 'file'. The handler should return json encoded object with following properties: 

{
  success: true|false, // required
  error: 'Error or success message' // optional, also can be named 'message'
}Internals.
The dialog is builded as finite state automata. The state diagram can be found in the dox folder. 

Download.
Ext.ux.UploadDialog.zip 

Licence.
No warranties, use it on your own risk, respectoware :D (if you like it and feels it's useful for you go to ExtJS forum find any of my posts (username MaximGB) and add to my reputation :))))) 

Author.
Maxim Bazhenov (aka MaximGB) 


--------------------------------------------------------------------------------

Bugs
Some users reported that add button works in IE6 only if one use non-packed version of upload-dialog.js I didn't test this. 

