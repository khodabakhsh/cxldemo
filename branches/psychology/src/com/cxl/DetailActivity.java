package com.cxl;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cxl.psychology.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class DetailActivity extends Activity {

	private WebView mWebView;

	private Button returnButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		Bundle bundle = getIntent().getExtras();
		mWebView = (WebView) findViewById(R.id.myWebView);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setScrollBarStyle(0);
		mWebView.loadUrl("file:///android_asset/" + bundle.getString("indexPage"));
		mWebView.setWebViewClient(new WebViewClient() {
			/*   
			此处能拦截超链接的url,即拦截href请求的内容.   
			*/
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mWebView.loadUrl( url);
				return true;
			}
		});

		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				if (mWebView.canGoBack()) {
					mWebView.goBack();
				} else {
					finish();
				}
			}
		});
		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多精品下载...");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
			}
		});
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if (paramInt == KeyEvent.KEYCODE_BACK) {
			boolean bool2 = this.mWebView.canGoBack();
			if (bool2) {
				this.mWebView.goBack();
			} else {
				finish();
			}
		}
		return true;
	}
}
