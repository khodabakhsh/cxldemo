package com.cxl.stevejobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxl.seeanybody.R;
import com.waps.AdView;

public class DetailActivity extends Activity {

	private Button returnButton;
	private TextView textView;
	private String menu;
	public static final String GBK = "GBK";
	public static final String UTF8 = "UTF8";

	public static int Current_Page_Value = 0;// 保存在配置里
	TextView page;
	Button btnPrevious;
	Button btnNext;
	public static final int Page_Sum = 6;

	Handler mHandler = new Handler();
	private Runnable scrollViewRun = new Runnable() {
		public void run() {
			textView.scrollTo(0, 0);
		}
	};

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.getApplicationContext();
		setContentView(R.layout.detail);

		btnPrevious = (Button) findViewById(R.id.previous);
		btnPrevious.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				try {
					AssetManager assets = getAssets();
					InputStream assetFile;
					assetFile = assets.open((--Current_Page_Value) + ".txt");
					textView.setText(genFileContent(assetFile));
					mHandler.post(scrollViewRun);
				} catch (IOException e) {
					e.printStackTrace();
				}
				setButtonVisibleAndSaveState();
			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try {
					AssetManager assets = getAssets();
					InputStream assetFile;
					assetFile = assets.open((++Current_Page_Value) + ".txt");
					textView.setText(genFileContent(assetFile));
					mHandler.post(scrollViewRun);
				} catch (IOException e) {
					e.printStackTrace();
				}
				setButtonVisibleAndSaveState();
			}
		});

		Bundle bundle = getIntent().getExtras();
		menu = bundle.getString("menu");

		AssetManager assets = getAssets();
		// 打开指定资源对应的输入流
		InputStream assetFile;
		//是否继续阅读
		boolean continueRead = false;
		try {
			if ("前言".equals(menu)) {
				Current_Page_Value = 0;
			} else if ("继续上次阅读".equals(menu)) {
				Current_Page_Value = Util.getTxtIndex(this);
				continueRead = true;
			} else {
				Current_Page_Value = Integer.valueOf(menu.substring(1, menu.lastIndexOf("章")));
			}
			assetFile = assets.open(Current_Page_Value + ".txt");
			textView = (TextView) findViewById(R.id.TextView);
			textView.setMovementMethod(ScrollingMovementMethod.getInstance());
			textView.setText(genFileContent(assetFile));
			if (continueRead) {
				textView.scrollTo(0, Util.getScrollY(this));
			}
			setButtonVisibleAndSaveState();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				setButtonVisibleAndSaveState();
				finish();
			}
		});

		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20

	}

	protected void onPause() {
		saveState();
		super.onPause();
	}

	//保存当前页和滚动位置
	private void saveState() {
		Util.setScrollY(this, textView.getScrollY());
		Util.setTxtIndex(this, Current_Page_Value);
	}

	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			saveState();
		}
		return super.onKeyDown(keyCode, keyEvent);
	}

	private void setButtonVisibleAndSaveState() {
		saveState();
		setTitle(MainActivity.MENU_List.get(Current_Page_Value + 1));
		if (Current_Page_Value == 0) {
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Value == Page_Sum) {
			btnNext.setVisibility(View.INVISIBLE);
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
	}

	public static String genFileContent(InputStream inputStream) {
		StringBuffer returnString = new StringBuffer("");
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, GBK);
			bufferedReader = new BufferedReader(inputStreamReader);
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				returnString.append(contentString + "\n");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return returnString.toString();
	}

}
