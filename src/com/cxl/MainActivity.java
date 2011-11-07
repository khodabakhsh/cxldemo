package com.cxl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.cxl.coldjoke.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	Button btnPrevious;
	Button btnNext;
	TextView page;
	String displayText;
	private WebView mWebView;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 50;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分
	public static final String hasEnoughRequrePointPreferenceKey = "hasEnoughRequrePointPreferenceKey";
	public static boolean hasEnoughRequrePointPreferenceValue = false;// 保存在配置里

	public static final String Current_Page_Key = "Current_Page_Key";
	public static int Current_Page_Value = 1;// 保存在配置里

	public static final int Page_Sum = 26;// 保存在配置里

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		initRequrePointPreference();
		AppConnect.getInstance(this).getPoints(this);
		setButtonVisible();

		super.onResume();
	}

	private void setButtonVisible() {
		page.setText("第【" + Current_Page_Value + "】页");
		if (Current_Page_Value == 1) {
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Value == Page_Sum) {
			btnNext.setVisibility(View.INVISIBLE);
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		SharedPreferences.Editor mEditor = mPerferences.edit();
		mEditor.putInt(Current_Page_Key, Current_Page_Value);
		mEditor.commit();
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
			if (!hasEnoughRequrePointPreferenceValue) {
				hasEnoughRequrePointPreferenceValue = true;
				SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
				SharedPreferences.Editor mEditor = mPerferences.edit();
				mEditor.putBoolean(hasEnoughRequrePointPreferenceKey, true);
				mEditor.commit();
			}
		}
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
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
	}

	private void initRequrePointPreference() {
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		hasEnoughRequrePointPreferenceValue = mPerferences.getBoolean(hasEnoughRequrePointPreferenceKey, false);
	}

	private void initCurrentPagePreference() {
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		Current_Page_Value = mPerferences.getInt(Current_Page_Key, 1);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initCurrentPagePreference();
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		page = (TextView) findViewById(R.id.page);
		mWebView = (WebView) findViewById(R.id.myWebView);
		mWebView.setBackgroundResource(R.drawable.bg);
		mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));

		mWebView.loadUrl("file:///android_asset/" + Current_Page_Value + ".html");

		btnPrevious = (Button) findViewById(R.id.previous);
		btnPrevious.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mWebView.getSettings().setJavaScriptEnabled(true);
				mWebView.setScrollBarStyle(0);
				mWebView.loadUrl("file:///android_asset/" + (--Current_Page_Value) + ".html");
				setButtonVisible();
				if (!MainActivity.hasEnoughRequrePointPreferenceValue && !MainActivity.hasEnoughRequrePoint) {// 没达到积分
					showDialog();
				}
			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mWebView.loadUrl("file:///android_asset/" + (++Current_Page_Value) + ".html");
				setButtonVisible();
				if (!MainActivity.hasEnoughRequrePointPreferenceValue && !MainActivity.hasEnoughRequrePoint) {// 没达到积分
					showDialog();
				}
			}
		});
		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多免费应用...");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(MainActivity.this).showMore(MainActivity.this);
			}
		});

	}

	private void showDialog() {
		new AlertDialog.Builder(MainActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + MainActivity.currentPointTotal)
				.setMessage(
						"只要积分满足" + MainActivity.requirePoint + "，就可以消除本提示信息！！ 您当前的积分不足" + MainActivity.requirePoint
								+ "，所以会有此 提示。\n\n【免费获得积分方法】：请点击【确认键】进入推荐下载列表 , 【下载、安装并打开】软件获得相应积分，消除本提示！！")
				.setPositiveButton("【确认】", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
					}
				}).setNegativeButton("【取消】", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// finish();
					}
				}).show();
	}

}