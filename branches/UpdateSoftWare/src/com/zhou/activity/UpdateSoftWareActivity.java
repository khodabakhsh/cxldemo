package com.zhou.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;

public class UpdateSoftWareActivity extends Activity {
	Button update;
	TextView updateInfo;
	//远程下载用到的变量
	private String currentFilePath = "";
	private String currentTempFilePath = "";
	private String strURL = "";
	private String fileEx = "";
	private String fileNa = "";
	final String uriPath = "http://www.dubblogs.cc:8751/Android/Test/Apk/EX04_14.apk";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		update = (Button) this.findViewById(R.id.update);
		updateInfo = (TextView) this.findViewById(R.id.updateinfo);
		update.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(UpdateSoftWareActivity.this).setTitle("更新提示").setMessage("发现新版本，是否更新")
						.setPositiveButton("是", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								updateInfo.setText("下载中....");
								strURL = uriPath;
								/*取得欲安装程序之文件名称*/
								fileEx = strURL.substring(strURL.lastIndexOf(".") + 1, strURL.length()).toLowerCase();
								fileNa = strURL.substring(strURL.lastIndexOf("/") + 1, strURL.lastIndexOf("."));
								getFile(strURL);
							}
						}).setNegativeButton("否", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {

								finish();
							}
						}).show();
			}
		});
	}

	@Override
	protected void onPause() {
		updateInfo.setText("下载成功");
		super.onPause();
	}

	@Override
	protected void onResume() {
		delFile(currentTempFilePath);
		super.onResume();
	}

	/* 处理下载URL文件自定义函数 */
	private void getFile(final String strPath) {
		try {
			if (strPath.equals(currentFilePath)) {
				getDataSource(strPath);
			}
			currentFilePath = strPath;
			Runnable r = new Runnable() {
				public void run() {
					try {
						getDataSource(strPath);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			new Thread(r).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*取得远程文件*/
	private void getDataSource(String strPath) throws Exception {
		if (!URLUtil.isNetworkUrl(strPath)) {
			updateInfo.setText("错误的URL");
		} else {
			/*取得URL*/
			URL myURL = new URL(strPath);
			/*创建连接*/
			URLConnection conn = myURL.openConnection();
			conn.connect();
			/*InputStream 下载文件*/
			InputStream is = conn.getInputStream();
			if (is == null) {
				throw new RuntimeException("stream is null");
			}
			/*创建临时文件*/
			File myTempFile = File.createTempFile(fileNa, "." + fileEx);
			/*取得站存盘案路径*/
			currentTempFilePath = myTempFile.getAbsolutePath();
			/*将文件写入暂存盘*/
			FileOutputStream fos = new FileOutputStream(myTempFile);
			byte buf[] = new byte[128];
			do {
				int numread = is.read(buf);
				if (numread <= 0) {
					break;
				}
				fos.write(buf, 0, numread);
			} while (true);
			/*打开文件进行安装*/
			openFile(myTempFile);
			UpdateSoftWareActivity.this.finish();
			try {
				is.close();
			} catch (Exception ex) {

			}
		}
	}

	/* 在手机上打开文件的method */
	private void openFile(File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);

		/* 调用getMIMEType()来取得MimeType */
		String type = getMIMEType(f);
		/* 设置intent的file与MimeType */
		intent.setDataAndType(Uri.fromFile(f), type);
		startActivity(intent);
	}

	/* 判断文件MimeType的method */
	private String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		/* 取得扩展名 */
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

		/* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
				|| end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")
				|| end.equals("bmp")) {
			type = "image";
		} else if (end.equals("apk")) {
			/* android.permission.INSTALL_PACKAGES */
			type = "application/vnd.android.package-archive";
		} else {
			type = "*";
		}
		/*如果无法直接打开，就跳出软件列表给用户选择 */
		if (end.equals("apk")) {
		} else {
			type += "/*";
		}
		return type;
	}

	/*自定义删除文件方法*/
	private void delFile(String strFileName) {
		File myFile = new File(strFileName);
		if (myFile.exists()) {
			myFile.delete();
		}
	}
}