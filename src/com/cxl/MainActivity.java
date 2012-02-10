package com.cxl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.weibocoldjoke.R;
import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	Button btnPrevious;
	Button btnNext;
	private TextView contentTextView;
	private TextView titleTextView;
	private Button menuButton;
	private Button btnGetPoint;
	private LinearLayout adLinearLayout;

	Handler handler = new Handler();

	private int scrollY = 0;
	private Button shareButton;
	private ScrollView scrollView;
	public static int Current_Page_Value = 1;
	public static final int Start_Page_Value = 1;
	public static int Page_Sum = 0;

	public static boolean hasEnoughAdPointPreferenceValue = false;
	public static final int requireAdPoint = 80;
	public static boolean hasEnoughReadPointPreferenceValue = false;
	public static final int requireReadPoint = 50;
	public static final int Read_Requre_Point_Page_Index = 40;
	public static final int requireSharePoint = 60;
	public static boolean hasEnoughSharePointPreferenceValue = false;
	
	public static int dbFileRawId = R.raw.jokedata;

	public static int currentPointTotal = 0;

	private void initRequrePointPreference() {
		hasEnoughAdPointPreferenceValue = PreferenceUtil.getHasEnoughAdPoint(MainActivity.this);
		hasEnoughReadPointPreferenceValue = PreferenceUtil.getHasEnoughReadPoint(MainActivity.this);
		hasEnoughSharePointPreferenceValue = PreferenceUtil.getHasEnoughSharePoint(MainActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughAdPointPreferenceValue || !hasEnoughReadPointPreferenceValue
				|| !hasEnoughSharePointPreferenceValue) {
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
				}
			});
		}
		if (pointTotal >= requireReadPoint) {
			hasEnoughReadPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughReadPoint(MainActivity.this, true);
		}
		if (pointTotal >= requireSharePoint) {
			hasEnoughSharePointPreferenceValue = true;
			PreferenceUtil.setHasEnoughSharePoint(MainActivity.this, true);
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
		hasEnoughSharePointPreferenceValue = false;
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	private void setButtonVisible() {

		setTitle("微博冷笑话，第【" + Current_Page_Value + "】页    / 共" + Page_Sum + "页");
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
		PreferenceUtil.setScrollY(this, scrollView.getScrollY());
		PreferenceUtil.setCurrentPage(this, Current_Page_Value);
	}

	private boolean canView(int pageIndex) {
		if ((pageIndex >= Read_Requre_Point_Page_Index) && !hasEnoughReadPointPreferenceValue) {
			return false;
		} else {
			return true;
		}
	}

	private void initPageSume() {
		Cursor cursor = DBUtil.getDatabase(MainActivity.this, dbFileRawId).rawQuery("select count(*)   from joke",
				null);
		startManagingCursor(cursor);
		if (cursor.moveToNext()) {

			Page_Sum = Integer.parseInt(cursor.getString(0));
		}
	}

	private void setContent() {

		Cursor cursor = DBUtil.getDatabase(MainActivity.this,dbFileRawId).rawQuery(
				"select id,data from joke where id=?", new String[] { String.valueOf(Current_Page_Value) });
		startManagingCursor(cursor);
		if (cursor.moveToNext()) {

//			titleTextView.setText(cursor.getString(0) + "、" + cursor.getString(1));
			contentTextView.setText(cursor.getString(1).replaceAll("\\\\n", "\n"));
			scrollView.post(new Runnable() {
				public void run() {
					scrollView.scrollTo(0, scrollY);
				}
			});
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initPageSume();
		initRequrePointPreference();
		boolean canRead = true;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String menu = bundle.getString("menu");
			Current_Page_Value = Integer.parseInt(menu);
			if (!canView(Current_Page_Value)) {
				canRead = false;
				showGetPointDialog(
						"继续阅读 【" + MenuActivity.MENU_List.get(Read_Requre_Point_Page_Index - Start_Page_Value)
								+ "】 之后的内容哦!", requireReadPoint);
				Current_Page_Value = PreferenceUtil.getCurrentPage(MainActivity.this);
			}
			PreferenceUtil.setCurrentPage(MainActivity.this, Current_Page_Value);
		}

		initCurrentPagePreference();
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		shareButton = (Button) findViewById(R.id.shareButton);
		shareButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
//				if (!hasEnoughSharePointPreferenceValue) {
//					showGetPointDialog("使用【蓝牙、短信、微博】发送笑话分享给您的朋友!", requireSharePoint);
//
//				} else {

					Intent sendIntent = new Intent("android.intent.action.SEND");
					sendIntent.setType("text/plain");
					sendIntent.putExtra("android.intent.extra.SUBJECT", "分享");
					sendIntent.putExtra("android.intent.extra.TEXT", contentTextView.getText());
					Intent selectIntent = Intent.createChooser(sendIntent, "使用以下方式发送");
					startActivity(selectIntent);
//				}
			}
		});
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		contentTextView = (TextView) findViewById(R.id.contentTextView);

		contentTextView.setBackgroundResource(R.drawable.bg);
		scrollY = PreferenceUtil.getScrollY(MainActivity.this);
		setContent();

		btnPrevious = (Button) findViewById(R.id.previous);
		btnPrevious.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				--Current_Page_Value;
				scrollY = 0;
				setContent();
				setButtonVisible();

			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				if (canView(Current_Page_Value + 1)) {
					++Current_Page_Value;
					scrollY = 0;
					setContent();
					setButtonVisible();
				} else {
					showGetPointDialog(
							"继续阅读 【" + MenuActivity.MENU_List.get(Read_Requre_Point_Page_Index - Start_Page_Value)
									+ "】 之后的内容哦!", requireReadPoint);
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
		adLinearLayout = (LinearLayout) findViewById(R.id.AdLinearLayout);

		if (!hasEnoughAdPointPreferenceValue) {
			new AdView(this, adLinearLayout).DisplayAd(20);// 每20秒轮换一次广告；最少为20
		}
		if (canRead && !hasEnoughAdPointPreferenceValue) {

			
			new AlertDialog.Builder(MainActivity.this)
			.setIcon(R.drawable.happy2)
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
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		SubMenu menu = paramMenu.addSubMenu(0, 0, 0, "目录");
		menu.setIcon(R.drawable.menu);
		SubMenu menu2 = paramMenu.addSubMenu(0, 1, 0, "更多免费精品下载...");
		menu2.setIcon(R.drawable.more);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == 0) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MenuActivity.class);
			startActivity(intent);
			finish();
		} else if (paramMenuItem.getItemId() == 1) {
			// 显示推荐安装程序（Offer）.
			AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	private void showGetPointDialog(String type, int score) {
		new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.happy2).setTitle("当前积分：" + currentPointTotal)
				.setMessage("说明：只要积分满足" + score + "，就可以" + type)
				.setPositiveButton("获取积分", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
					}
				}).show();
	}

}