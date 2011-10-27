package com.cxl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.yinghan.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	// 查询按钮申明
	private Button myButton01;
	// 清空按钮申明
	private Button myButton02;
	// 输入框申明
	private EditText mEditText1;
	// 加载数据的WebView申明
	private WebView mWebView1;

	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 70;// 要求积分
	private static boolean hasEnoughRequrePoint = false;// 是否达到积分
	private ProgressDialog progressDialog = null;
	final Handler mHandler = new Handler();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		// 获得布局的几个控件
		myButton01 = (Button) findViewById(R.id.myButton01);
		myButton02 = (Button) findViewById(R.id.myButton02);
		mEditText1 = (EditText) findViewById(R.id.myEditText1);
		mWebView1 = (WebView) findViewById(R.id.myWebView1);

		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(MainActivity.this).showMore(MainActivity.this);
			}
		});
		// 查询按钮添加事件
		myButton01.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				clickHandler();
			}
		});

		// 清空按钮添加事件，将EditText置空
		myButton02.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mEditText1.setText("");
			}
		});
	}
	private void clickHandler(){
		String keyword = (mEditText1.getText().toString());
		keyword = keyword.trim();
		// 如果查询内容为空提示
		if (keyword.length() == 0) {
			Toast.makeText(MainActivity.this, "查询内容不能为空!", Toast.LENGTH_LONG)
					.show();
		}
		// 否则则以参数的形式从http://dict.youdao.com/m取得数据，加载到WebView里.
		else {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setTitle("请稍等...");
			progressDialog.setMessage("获取数据中...");
			progressDialog.setIndeterminate(true);
			progressDialog.setIndeterminateDrawable(getResources()
					.getDrawable(R.drawable.wait));
			progressDialog.show();
			new Thread(new Runnable() {
				public void run() {
					mWebView1.loadDataWithBaseURL(
							DictionaryUtil.getDetailUrl, DictionaryUtil
									.getDetail(mEditText1.getText()
											.toString()), "text/html",
							DictionaryUtil.UTF8, "");
					progressDialog.dismiss();

				}
			}).start();

		}

		if (!hasEnoughRequrePoint) {// 没达到积分
//			showDialog();
		}
	}

	@Override
	protected void onPause() {
		progressDialog.dismiss();
		super.onPause();
	}
	private void showDialog() {
		new AlertDialog.Builder(MainActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + currentPointTotal)
				.setMessage(
						"只要积分满足"
								+ MainActivity.requirePoint
								+ "，就可以消除本提示信息！！ 您当前的积分不足"
								+ MainActivity.requirePoint
								+ "，所以会有此 提示。\n\n【免费获得积分方法】：请点击【确认键】进入推荐下载列表 , 下载并安装软件获得相应积分，消除本提示！！")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(MainActivity.this).showOffers(
								MainActivity.this);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// finish();
					}
				}).show();
	}

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
}