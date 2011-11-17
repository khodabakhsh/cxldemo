package com.cxl;

import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cxl.coldjoke.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class MainActivity extends Activity  {
	Button btnPrevious;
	Button btnNext;
	String displayText;
	private WebView mWebView;
	boolean update_text = false;

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
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout2);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20
		setButtonVisible();
		super.onResume();
	}
private static String [] moodStrings= new String[]{"^_^","O(∩_∩)O~","(*^__^*) ……","(～ o ～)~zZ","(⊙o⊙)","(⊙v⊙)","( ⊙ o ⊙ )！","\\(^o^)/~"};
	private void setButtonVisible() {
		
		setTitle("第【" + Current_Page_Value + "】页         "+moodStrings[new Random().nextInt(moodStrings.length)]);
		if (Current_Page_Value == 1) {
			Toast.makeText(this, "第一页哦 ，开始愉快之旅 ！ \\(^o^)/~", Toast.LENGTH_LONG).show();
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Value == Page_Sum) {
			btnNext.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "最后一页了哦 ，祝您天天好心情哦 ！ (*^__^*) ……哈哈~", Toast.LENGTH_LONG).show();
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		SharedPreferences.Editor mEditor = mPerferences.edit();
		mEditor.putInt(Current_Page_Key, Current_Page_Value);
		mEditor.commit();
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
			
			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mWebView.loadUrl("file:///android_asset/" + (++Current_Page_Value) + ".html");
				setButtonVisible();
			
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

}