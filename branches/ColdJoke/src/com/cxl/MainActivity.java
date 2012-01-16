package com.cxl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cxl.coldjoke.R;
import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	Button btnPrevious;
	Button btnNext;
	private WebView mWebView;
	private Button menuButton;
	private Button btnGetPoint;
	private LinearLayout adLinearLayout;

	Handler handler = new Handler();
	Handler msgHandler = new Handler();

	private int scrollY = 0;
	public static int Current_Page_Value = 1;
	public static final int Start_Page_Value = 1;
	public static final int Page_Sum = 26;

	public static boolean hasEnoughAdPointPreferenceValue = false;
	public static final int requireAdPoint = 70;
	public static boolean hasEnoughReadPointPreferenceValue = false;
	public static final int requireReadPoint = 30;
	public static final int Read_Requre_Point_Page_Index = 15;

	public static int currentPointTotal = 0;

	private void initRequrePointPreference() {
		hasEnoughAdPointPreferenceValue = PreferenceUtil.getHasEnoughAdPoint(MainActivity.this);
		hasEnoughReadPointPreferenceValue = PreferenceUtil.getHasEnoughReadPoint(MainActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughAdPointPreferenceValue || !hasEnoughReadPointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
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
		if (pointTotal >= requireAdPoint) {
			hasEnoughAdPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughAdPoint(MainActivity.this, true);
			handler.post(new Runnable() {
				public void run() {
					adLinearLayout.setVisibility(View.GONE);
					btnGetPoint.setText("更多下载");
				}
			});
		}
		if (pointTotal >= requireReadPoint) {
			hasEnoughReadPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughReadPoint(MainActivity.this, true);
		}
        if(!hasEnoughAdPointPreferenceValue){
			
			msgHandler.post(new Runnable() {
				public void run() {
					new AlertDialog.Builder(MainActivity.this)
							.setTitle("感谢使用本程序")
							.setMessage(
									"说明：本程序的一切提示信息，在积分满足" + requireAdPoint
											+ "后，自动消除！\n\n可通过【免费赚积分】，获得积分。\n\n通过【更多应用】，可以下载各种好玩应用。\n\n当前积分："
											+ currentPointTotal)
							.setPositiveButton("更多应用", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {
									AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
								}
							}).setNeutralButton("免费赚积分", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {
									AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
								}
							}).setNegativeButton("继续", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {
								}
							}).show();
				}
			});
		}
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		hasEnoughAdPointPreferenceValue = false;
		hasEnoughReadPointPreferenceValue = false;
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	class MyPictureListener implements PictureListener {
		public void onNewPicture(WebView view, Picture arg1) {
			// put code here that needs to run when the page has finished
			// loading and
			// a new "picture" is on the webview.
			mWebView.scrollTo(0, scrollY);
		}
	}


	private void setButtonVisible() {

		setTitle("第【" + Current_Page_Value + "】页  / 共 "+Page_Sum+"页") ;
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
		saveState();
	}

	private void initCurrentPagePreference() {
		Current_Page_Value = PreferenceUtil.getCurrentPage(MainActivity.this);
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

	private boolean canView(int pageIndex) {
		if ((pageIndex >= Read_Requre_Point_Page_Index) && !hasEnoughReadPointPreferenceValue) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initRequrePointPreference();
		boolean canRead = true;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String menu = bundle.getString("menu");
			Current_Page_Value = Integer.parseInt(menu);
			if (!canView(Current_Page_Value)) {
				canRead = false;
				showGetPointDialog("继续阅读 【"
						+ MenuActivity.MENU_List.get(Read_Requre_Point_Page_Index - Start_Page_Value) + "】 之后的内容哦!");
				Current_Page_Value = PreferenceUtil.getCurrentPage(MainActivity.this);
			}
			PreferenceUtil.setCurrentPage(MainActivity.this, Current_Page_Value);
		}

		initCurrentPagePreference();
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		mWebView = (WebView) findViewById(R.id.myWebView);
		mWebView.setPictureListener(new MyPictureListener());

		mWebView.setBackgroundResource(R.drawable.bg);
		mWebView.getSettings().setDefaultFontSize(18);
		mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));

		mWebView.loadUrl("file:///android_asset/" + Current_Page_Value + ".html");
		scrollY = PreferenceUtil.getScrollY(MainActivity.this);

		btnPrevious = (Button) findViewById(R.id.previous);
		btnPrevious.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mWebView.loadUrl("file:///android_asset/" + (--Current_Page_Value) + ".html");
				scrollY = 0;
				setButtonVisible();

			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				if (canView(Current_Page_Value + 1)) {
					mWebView.loadUrl("file:///android_asset/" + (++Current_Page_Value) + ".html");
					scrollY = 0;
					setButtonVisible();
				} else {
					showGetPointDialog("继续阅读 【"
							+ MenuActivity.MENU_List.get(Read_Requre_Point_Page_Index - Start_Page_Value) + "】 之后的内容哦!");
				}
			}
		});
		menuButton = (Button) findViewById(R.id.menuButton);
		menuButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MenuActivity.class);
				startActivity(intent);
				finish();
			}
		});

		btnGetPoint = (Button) findViewById(R.id.OffersButton);
		btnGetPoint.setText("更多下载");
		btnGetPoint.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
			}
		});

		setButtonVisible();
		adLinearLayout = (LinearLayout) findViewById(R.id.AdLinearLayout2);

//		if (!hasEnoughAdPointPreferenceValue) {
			new AdView(this, adLinearLayout).DisplayAd(20);// 每20秒轮换一次广告；最少为20
//		}

	}

	private void showGetPointDialog(String type) {
		new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.happy2).setTitle("当前积分：" + currentPointTotal)
				.setMessage("只要积分满足" + requireReadPoint + "，就可以" + type)
				.setPositiveButton("免费获得积分", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
					}
				}).show();
	}

}