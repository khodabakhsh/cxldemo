package com.cxl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cxl.xiaochi.R;
import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class DetailActivity extends Activity implements UpdatePointsNotifier {

	private void showDialog() {
		new AlertDialog.Builder(DetailActivity.this).setIcon(R.drawable.happy2).setTitle("当前积分：" + currentPointTotal)
				.setMessage("只要积分满足" + requirePoint + "，就可以消除本提示信息！！\n 您当前的积分不足" + requirePoint + "，所以会有此 提示。")
				.setPositiveButton("免费获得积分", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// finish();
					}
				}).show();
	}

	public static boolean hasEnoughRequrePointPreferenceValue = false;// 保存在配置里
	public static final int requirePoint = 30;// 要求积分
	public static int currentPointTotal = 0;// 当前积分

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
		if (pointTotal >= requirePoint) {
			hasEnoughRequrePointPreferenceValue = true;
			PreferenceUtil.setHasEnoughRequrePoint(DetailActivity.this, true);
		}
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		hasEnoughRequrePointPreferenceValue = false;
	}

	protected void onDestroy() {
		mWebView.destroyDrawingCache();
		mWebView.destroy();
		super.onDestroy();
	}

	private WebView mWebView;

	private Button returnButton;

	private void initRequrePointPreference() {
		hasEnoughRequrePointPreferenceValue = PreferenceUtil.getHasEnoughRequrePoint(DetailActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughRequrePointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
		super.onResume();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initRequrePointPreference();
		setContentView(R.layout.detail);

		Bundle bundle = getIntent().getExtras();
		mWebView = (WebView) findViewById(R.id.myWebView);
		mWebView.getSettings().setDefaultFontSize(18);
		String fileName = "content_" + bundle.getString("id");

		String fileContent = getFileContent(DetailActivity.this,
				getResources().getIdentifier(fileName, "raw", getPackageName()));

		mWebView.loadDataWithBaseURL(null, fileContent, "text/html", "UTF8", "");

		if (!hasEnoughRequrePointPreferenceValue) {// 没达到积分
			showDialog();
		}

		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				finish();
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
