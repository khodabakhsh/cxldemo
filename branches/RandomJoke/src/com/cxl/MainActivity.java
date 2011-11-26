package com.cxl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.cxl.randomjoke.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	private WebView myWebView;
	Button queryButton;
	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 60;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分;
	private ProgressDialog progressDialog = null;

	private void showDialog()
	{
		new AlertDialog.Builder(MainActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + MainActivity.currentPointTotal)
				.setMessage(
						"只要积分满足"
								+ MainActivity.requirePoint
								+ "，就可以消除本提示信息！！ 您当前的积分不足"
								+ MainActivity.requirePoint
								+ "，所以会有此 提示。\n\n【免费获得积分方法】：请点击【确认键】进入推荐下载列表 , 下载并安装软件获得相应积分，消除本提示！！")
				.setPositiveButton("确认", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialoginterface, int i)
					{
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(MainActivity.this).showOffers(
								MainActivity.this);
					}
				}).setNegativeButton("取消",
						new DialogInterface.OnClickListener()
						{

							public void onClick(DialogInterface dialog,
									int which)
							{
								// finish();
							}
						}).show();
	}

	final Handler mHandler = new Handler();

	// 创建一个线程
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			if (pointsTextView != null) {
				if (update_text) {
					pointsTextView.setText(displayText);
					update_text = false;
				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		AppConnect.getInstance(this).getPoints(this);
		super.onResume();
	}

	/**
	 * AppConnect.getPoints()方法的实现，必须实现
	 * 
	 * @param currencyName
	 *            虚拟货币名称.
	 * @param pointTotal
	 *            虚拟货币余额.
	 */
	public void getUpdatePoints(String currencyName, int pointTotal) {

		currentPointTotal = pointTotal;
		if (currentPointTotal >= requirePoint) {
			hasEnoughRequrePoint = true;
		}
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
		mHandler.post(mUpdateResults);
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		currentPointTotal = 0;
		update_text = true;
		displayText = error;
		mHandler.post(mUpdateResults);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		myWebView = (WebView) findViewById(R.id.myWebView);
		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多免费应用...");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(MainActivity.this).showMore(MainActivity.this);
			}
		});
		queryButton = (Button) findViewById(R.id.queryButton);
		queryButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				getRandomJoke();
				if (!hasEnoughRequrePoint) {// 没达到积分
					showDialog();
				}
			}
		});

		getRandomJoke();

	}

	private void getRandomJoke() {
		progressDialog = new ProgressDialog(MainActivity.this);
		progressDialog.setTitle("请稍等...");
		progressDialog.setMessage("获取数据中...");
		progressDialog.setIndeterminate(true);
		progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.wait));
		progressDialog.show();

		new Thread(new Runnable() {
			public void run() {
				SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
				int currentFileIndex = mPerferences.getInt("index", 1);
				SharedPreferences.Editor mEditor = mPerferences.edit();
				int newFileIndex = (currentFileIndex + 1) % 200;
				if (newFileIndex == 0) {
					newFileIndex = 200;
				}
				mEditor.putInt("index", newFileIndex);
				mEditor.commit();

				String fileName = "content_" + currentFileIndex;

				String fileContent = getFileContent(MainActivity.this,
						getResources().getIdentifier(fileName, "raw", getPackageName()));

				myWebView.loadDataWithBaseURL(null, fileContent, "text/html", "UTF-8", "");
				progressDialog.dismiss();
			}
		}).start();
	}

	public String getFileContent(Context context, int x) {// 规划了file参数、ID参数，方便多文件写入。
		InputStream in = null;
		BufferedReader bufferedReader = null;
		StringBuilder sBuffer = new StringBuilder("");
		try {
			in = context.getResources().openRawResource(x);

			bufferedReader = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return sBuffer.toString();
	}

}