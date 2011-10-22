package com.cxl;

import android.app.Activity;
import android.app.AlertDialog;
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

public class YouDao extends Activity implements UpdatePointsNotifier {
	//查询按钮申明
	private Button myButton01;
	//清空按钮申明
	private Button myButton02;
	//输入框申明
	private EditText mEditText1;
	//加载数据的WebView申明
	private WebView mWebView1;

	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	int currentPointTotal = 0;//当前积分
	public static final int requirePoint = 200;//要求积分
	private static boolean hasEnoughRequrePoint = false;//是否达到积分

	final Handler mHandler = new Handler();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		//先获取当前积分
		//		AppConnect.getInstance(YouDao.this).getPoints(YouDao.this);
		//		if (currentPointTotal < 200) {
		////			showDialog();
		//		}

		//获得布局的几个控件
		myButton01 = (Button) findViewById(R.id.myButton01);
		myButton02 = (Button) findViewById(R.id.myButton02);
		mEditText1 = (EditText) findViewById(R.id.myEditText1);
		mWebView1 = (WebView) findViewById(R.id.myWebView1);
		pointsTextView = (TextView) findViewById(R.id.PointsTextView);

		//		Button awardBurron = (Button) findViewById(R.id.awardButton);
		//		awardBurron.setOnClickListener(new Button.OnClickListener() {
		//			public void onClick(View arg0) {
		//				// 奖励虚拟货币
		//				AppConnect.getInstance(YouDao.this).awardPoints(10, YouDao.this);
		//			}
		//		});

		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(YouDao.this).showMore(YouDao.this);
			}
		});

		//		Button getPoints = (Button) findViewById(R.id.PointsButton);
		//		getPoints.setOnClickListener(new Button.OnClickListener() {
		//			public void onClick(View arg0) {
		//				// 从服务器端获取当前用户的积分.
		//				// 返回结果在回调函数getUpdatePoints(...)中处理
		//				AppConnect.getInstance(YouDao.this).getPoints(YouDao.this);
		//			}
		//		});

		Button offers = (Button) findViewById(R.id.OffersButton);
		offers.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(YouDao.this).showOffers(YouDao.this);
			}
		});

		//查询按钮添加事件
		myButton01.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if (!hasEnoughRequrePoint) {//没达到积分
						showDialog();
				}

				String strURI = (mEditText1.getText().toString());
				strURI = strURI.trim();
				//如果查询内容为空提示
				if (strURI.length() == 0) {
					Toast.makeText(YouDao.this, "查询内容不能为空!", Toast.LENGTH_LONG).show();
				}
				//否则则以参数的形式从http://dict.youdao.com/m取得数据，加载到WebView里.
				else {
					String strURL = "http://dict.youdao.com/m/search?keyfrom=dict.mindex&q=" + strURI;
					mWebView1.loadUrl(strURL);
				}
			}
		});

		//清空按钮添加事件，将EditText置空
		myButton02.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mEditText1.setText("");
			}
		});
	}

	private void showDialog() {
		new AlertDialog.Builder(YouDao.this)
				.setIcon(R.drawable.happy2)
				.setTitle("提示,当前的积分：" + currentPointTotal)
				.setMessage(
						"只要积分满足" + requirePoint + "，本程序就可以永久使用！！ 您当前的积分不足" + requirePoint
								+ "，无法进行查询。【免费获得积分方法】：请点击确认键进入推荐下载列表 , 下载并安装软件获得相应积分。")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(YouDao.this).showOffers(YouDao.this);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						//						finish();
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