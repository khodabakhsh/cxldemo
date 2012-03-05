package com.cxl.oralenglish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends TabActivity implements TabHost.OnTabChangeListener, View.OnClickListener,
		UpdatePointsNotifier {
	TabHost tabHost;
	private ListView searchListView;
	private ListView favoriteListView;
	private EditText txtSearch;
	MatchListAdapter matchListAdapter = new MatchListAdapter();
	FavoriteListAdapter favoriteListAdapter = new FavoriteListAdapter();
	private TextWatcher watcher = new MyTextWatcher();
	public static List<String> searchList = ListManager.AllList;
	public static List<String> favoriteList = new ArrayList<String>();

	private static final String My_Favorite_Key = "My_Favorite";
	private static final String Favorite_Item_Split = "@@##";
	
	
	public static boolean hasEnoughRequrePointPreferenceValue = false;// 保存在配置里
	public static final int requirePoint = 70;// 要求积分
	public static int currentPointTotal = 0;// 当前积分
	private Handler msgHandler = new Handler();
	

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}
	private void initRequrePointPreference() {
		hasEnoughRequrePointPreferenceValue = PreferenceUtil.getHasEnoughRequrePoint(MainActivity.this);
	}

	@Override
	protected void onResume() {
//		initRequrePointPreference();
		searchList = ListManager.getSearchList(txtSearch.getText().toString());
		matchListAdapter.notifyDataSetChanged();
		if (!hasEnoughRequrePointPreferenceValue) {
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
		if (pointTotal >= requirePoint) {
			hasEnoughRequrePointPreferenceValue = true;
			PreferenceUtil.setHasEnoughRequrePoint(MainActivity.this, true);
		}
		if (!hasEnoughRequrePointPreferenceValue) {
			msgHandler.post(new Runnable() {
				public void run() {
					new AlertDialog.Builder(MainActivity.this)
							.setIcon(R.drawable.happy2)
							.setTitle("感谢使用本程序")
							.setMessage(
									"说明：本程序的一切提示信息，在积分满足" + requirePoint
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
		hasEnoughRequrePointPreferenceValue = false;
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

		searchListView = (ListView) findViewById(R.id.searchList);
		searchListView.setAdapter(matchListAdapter);

		initFavorites();
		favoriteListView = (ListView) findViewById(R.id.favoriteList);
		favoriteListView.setAdapter(favoriteListAdapter);

		txtSearch = (EditText) findViewById(R.id.txtSearch);
		txtSearch.addTextChangedListener(this.watcher);

		Button clearFavorite = (Button) findViewById(R.id.clearFavorite);
		clearFavorite.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				new AlertDialog.Builder(MainActivity.this).setMessage("确定清空收藏？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								SharedPreferences mPerferences = PreferenceManager
										.getDefaultSharedPreferences(MainActivity.this);
								SharedPreferences.Editor mEditor = mPerferences.edit();
								mEditor.putString(My_Favorite_Key, "");
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
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
			}
		});
		Button owns2 = (Button) findViewById(R.id.OwnsButton2);
		owns2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
			}
		});
		Button owns3 = (Button) findViewById(R.id.OwnsButton3);
		owns3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
			}
		});
		
		LinearLayout container2 = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container2).DisplayAd(20);// 每20秒轮换一次广告；最少为20

	}

	class MyTextWatcher implements TextWatcher {
		public void afterTextChanged(Editable paramEditable) {
		}

		public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
		}

		public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
			searchList = ListManager.getSearchList(paramCharSequence.toString());
			matchListAdapter.notifyDataSetChanged();
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void onTabChanged(String tabId) {
	}

	class MatchListAdapter extends BaseAdapter {
		private MatchListAdapter() {
		}

		public int getCount() {
			return searchList.size();
		}

		public Object getItem(int paramInt) {
			return searchList.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			if (paramView == null)
				paramView = ((LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.search_list_layout, null);
			TextView localTextView1 = (TextView) paramView.findViewById(R.id.txtSearchEnName);
			final String content = getItem(paramInt).toString();
			localTextView1.setText(content);
			ImageView imageView = (ImageView) paramView.findViewById(R.id.flag_icon);
			imageView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
						String id = content.substring(0, content.indexOf("."));

						SharedPreferences mPerferences = PreferenceManager
								.getDefaultSharedPreferences(MainActivity.this);
						String myFavorite = mPerferences.getString(My_Favorite_Key, "");
						if (!exist(myFavorite, id)) {
							favoriteList.add(id);
						}
						sortList(favoriteList);
						favoriteListAdapter.notifyDataSetChanged();
						SharedPreferences.Editor mEditor = mPerferences.edit();
						mEditor.putString(My_Favorite_Key, add(myFavorite, id));
						mEditor.commit();

						Toast.makeText(MainActivity.this, "收藏成功", Toast.LENGTH_LONG).show();

				}
			});
			return paramView;
		}
	}

	class FavoriteListAdapter extends BaseAdapter {
		private FavoriteListAdapter() {
		}

		public int getCount() {
			return favoriteList.size();
		}

		public Object getItem(int paramInt) {
			return favoriteList.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			if (paramView == null)
				paramView = ((LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.search_list_layout, null);
			TextView localTextView1 = (TextView) paramView.findViewById(R.id.txtSearchEnName);
			final String content = ListManager.AllList.get(Integer.valueOf(getItem(paramInt).toString()) - 1);
			localTextView1.setText(content);
			return paramView;
		}
	}

	private void initFavorites() {
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		String myFavorite = mPerferences.getString(My_Favorite_Key, "");
		if ("".equals(myFavorite)) {
			return;
		}
		String[] splitStrings = myFavorite.split(Favorite_Item_Split);
		favoriteList.clear();
		for (String itemString : splitStrings) {
			favoriteList.add(itemString);
		}
		sortList(favoriteList);
	}

	private void sortList(List<String> list) {
		Collections.sort(list, new Comparator<String>() {

			public int compare(String object1, String object2) {
				int resutl = Integer.valueOf(object1) - Integer.valueOf(object2);
				return resutl > 0 ? 1 : (resutl < 0 ? -1 : 0);
			}
		});
	}

	private boolean exist(String orginString, String id) {
		boolean exist = false;
		String[] splitStrings = orginString.split(Favorite_Item_Split);
		for (String itemString : splitStrings) {
			if (itemString.equals(id)) {
				exist = true;
			}
		}
		return exist;
	}

	private String add(String orginString, String id) {
		if ("".equals(orginString)) {
			return id;
		}
		if (!exist(orginString, id)) {
			orginString += Favorite_Item_Split + id;
		}
		return orginString;

	}


}