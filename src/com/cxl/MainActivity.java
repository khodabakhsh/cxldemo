package com.cxl;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

import com.cxl.ListManager.KeyValue;
import com.cxl.carknowledge.R;
import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends TabActivity implements TabHost.OnTabChangeListener, UpdatePointsNotifier {
	TabHost tabHost;
	private ListView firstMenuListView;
	private ListView favoriteListView;
	private ArrayAdapter<KeyValue> favoriteListAdapter;
	private ArrayAdapter<String> firstMenuAdapter;
	public static List<KeyValue> favoriteList = new ArrayList<KeyValue>();

	Handler handler = new Handler();

	public static boolean hasEnoughReadPointPreferenceValue = false;
	public static final int requireReadPoint = 60;

	public static int currentPointTotal = 0;

	private void initRequrePointPreference() {
		hasEnoughReadPointPreferenceValue = PreferenceUtil.getHasEnoughReadPoint(MainActivity.this);
	}

	protected void onResume() {
		favoriteListAdapter.notifyDataSetChanged();
		if (!hasEnoughReadPointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
		super.onResume();
	}
	Handler msgHandler = new Handler();
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

		if (pointTotal >= requireReadPoint) {
			hasEnoughReadPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughReadPoint(MainActivity.this, true);
		}
		if (!hasEnoughReadPointPreferenceValue) {

			msgHandler.post(new Runnable() {
				public void run() {
					new AlertDialog.Builder(MainActivity.this)
							.setTitle("感谢使用本程序")
							.setMessage(
									"说明：本程序的一切提示信息，在积分满足" + requireReadPoint
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
		hasEnoughReadPointPreferenceValue = false;
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		initRequrePointPreference();

		this.tabHost = getTabHost();
		FrameLayout localFrameLayout = this.tabHost.getTabContentView();
		LayoutInflater.from(this).inflate(R.layout.main, localFrameLayout, true);
		TabHost.TabSpec oneTabSpec = this.tabHost.newTabSpec("One")
				.setIndicator("", getResources().getDrawable(R.drawable.about)).setContent(R.id.widget_layout_sort);
		((TabHost) tabHost).addTab(oneTabSpec);
		TabHost.TabSpec twoTabSpec = this.tabHost.newTabSpec("Two")
				.setIndicator("", getResources().getDrawable(R.drawable.search)).setContent(R.id.widget_layout_search);
		((TabHost) tabHost).addTab(twoTabSpec);
		TabHost.TabSpec threeTabSpec = this.tabHost.newTabSpec("Three")
				.setIndicator("", getResources().getDrawable(R.drawable.favorite)).setContent(R.id.widget_layout_point);
		((TabHost) tabHost).addTab(threeTabSpec);
		this.tabHost.setOnTabChangedListener(this);
		tabHost.setCurrentTab(1);

		firstMenuListView = (ListView) findViewById(R.id.searchList);
		firstMenuAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_layout, R.id.txtListItem,
				ListManager.First_Menu_List);
		firstMenuListView.setAdapter(firstMenuAdapter);
		firstMenuListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				final String menu = (String) arg0.getItemAtPosition(pos);
				if (("第五篇、保险".equals(menu)||"第六篇、其他补充资料".equals(menu))&&!hasEnoughReadPointPreferenceValue) {
					
					handler.post(new Runnable() {
						public void run() {
							showGetPointDialog("阅读【  "+menu+"】");
						}
					});
				} else {
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, SubMenuActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("menu", menu);
					intent.putExtras(bundle);
					startActivity(intent);
				}

			}
		});

		initFavorites();
		favoriteListView = (ListView) findViewById(R.id.favoriteList);
		favoriteListAdapter = new ArrayAdapter<KeyValue>(this, R.layout.simple_list_layout, R.id.txtListItem,
				favoriteList);
		favoriteListView.setAdapter(favoriteListAdapter);

		favoriteListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				KeyValue subMenuAndFileName = (KeyValue) arg0.getItemAtPosition(pos);
				Intent intent = new Intent();

				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("fileName", subMenuAndFileName.getKey());

				bundle.putString("subMenuName", subMenuAndFileName.getValue());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		Button clearFavorite = (Button) findViewById(R.id.clearFavorite);
		clearFavorite.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				new AlertDialog.Builder(MainActivity.this).setMessage("确定清空收藏？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								SharedPreferences mPerferences = PreferenceManager
										.getDefaultSharedPreferences(MainActivity.this);
								SharedPreferences.Editor mEditor = mPerferences.edit();
								mEditor.putString(DetailActivity.Favorite_Key, "");
								mEditor.commit();
								favoriteList.clear();
								favoriteListAdapter.notifyDataSetChanged();
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
							}
						}).show();
			}
		});

		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多精品下载...");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
			}
		});
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout3);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20
	}

	public void onTabChanged(String tabId) {
	}

	private void initFavorites() {
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		String myFavorite = mPerferences.getString(DetailActivity.Favorite_Key, "");
		if ("".equals(myFavorite)) {
			return;
		}
		String[] splitStrings = myFavorite.split(DetailActivity.Favorite_Item_Split);
		favoriteList.clear();
		for (String itemString : splitStrings) {
			favoriteList.add(new KeyValue(itemString.split(DetailActivity.Item_Key_Value_Split)[0], itemString
					.split(DetailActivity.Item_Key_Value_Split)[1]));
		}
	}

}