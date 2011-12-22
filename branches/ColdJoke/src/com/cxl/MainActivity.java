package com.cxl;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cxl.coldjoke.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class MainActivity extends Activity {
	Button btnPrevious;
	Button btnNext;
	private WebView mWebView;
	private Button menuButton;

	private int scrollY = 0;
	public static int Current_Page_Value = 1;
	public static final int Start_Page_Value = 1;
	public static final int Page_Sum = 26;

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
	
		super.onResume();
	}
	class MyPictureListener implements PictureListener {
		public void onNewPicture(WebView view, Picture arg1) {
			// put code here that needs to run when the page has finished
			// loading and
			// a new "picture" is on the webview.
			mWebView.scrollTo(0, scrollY);
		}
	}
	private static String[] moodStrings = new String[] { "^_^", "O(∩_∩)O~",
			"(*^__^*) ……", "(～ o ～)~zZ", "(⊙o⊙)", "(⊙v⊙)", "( ⊙ o ⊙ )！",
			"\\(^o^)/~" };

	private void setButtonVisible() {

		setTitle("第【" + Current_Page_Value + "】页         "
				+ moodStrings[new Random().nextInt(moodStrings.length)]);
		if (Current_Page_Value == 1) {
			Toast.makeText(this, "第一页哦 ，开始愉快之旅 ！ \\(^o^)/~", Toast.LENGTH_LONG)
					.show();
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Value == Page_Sum) {
			btnNext.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "最后一页了哦 ，祝您天天好心情哦 ！ (*^__^*) ……哈哈~",
					Toast.LENGTH_LONG).show();
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
		saveState();
	}

	private void initCurrentPagePreference() {

		Current_Page_Value = PreferenceUtil.getCurrentPage(MainActivity.this);
		;
	}
	protected void onPause() {
		saveState();
		super.onPause();
	}

	// 保存当前页和滚动位置
	private void saveState() {
		PreferenceUtil.setScrollY(this, mWebView.getScrollY());
		PreferenceUtil.setCurrentPage(this, Current_Page_Value);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String menu = bundle.getString("menu");
			Current_Page_Value = Integer.parseInt(menu);
			PreferenceUtil
					.setCurrentPage(MainActivity.this, Current_Page_Value);
		}

		initCurrentPagePreference();
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		mWebView = (WebView) findViewById(R.id.myWebView);
		mWebView.setPictureListener(new MyPictureListener());

		mWebView.setBackgroundResource(R.drawable.bg);
		mWebView.getSettings().setDefaultFontSize(18);
		mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));

		mWebView.loadUrl("file:///android_asset/" + Current_Page_Value
				+ ".html");
		scrollY = 	PreferenceUtil.getScrollY(MainActivity.this);

		btnPrevious = (Button) findViewById(R.id.previous);
		btnPrevious.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mWebView.getSettings().setJavaScriptEnabled(true);
				mWebView.setScrollBarStyle(0);
				mWebView.loadUrl("file:///android_asset/"
						+ (--Current_Page_Value) + ".html");
				scrollY = 0;
				setButtonVisible();

			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mWebView.loadUrl("file:///android_asset/"
						+ (++Current_Page_Value) + ".html");
				scrollY = 0;
				setButtonVisible();

			}
		});
		menuButton =(Button)findViewById(R.id.menuButton);
		menuButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MenuActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		
		Button offers = (Button) findViewById(R.id.OffersButton);
		offers.setText("更多下载");
		offers.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(MainActivity.this).showOffers(
						MainActivity.this);
			}
		});
//		mWebView.post(new Runnable() {
//			public void run() {
		
//			}
//		});
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout2);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20
	}

}