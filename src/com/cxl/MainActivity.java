package com.cxl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

import com.cxl.car.R;
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

	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 50;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分
	public static final String hasEnoughRequrePointPreferenceKey = "hasEnoughRequrePointPreferenceKey";
	public static boolean hasEnoughRequrePointPreferenceValue = false;// 保存在配置里

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
//		initRequrePointPreference();
		searchList = ListManager.getSearchList(txtSearch.getText().toString());
		matchListAdapter.notifyDataSetChanged();
		AppConnect.getInstance(this).getPoints(this);
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
		if (currentPointTotal >= requirePoint) {
			hasEnoughRequrePoint = true;
			if (!hasEnoughRequrePointPreferenceValue) {
				hasEnoughRequrePointPreferenceValue = true;
				SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
				SharedPreferences.Editor mEditor = mPerferences.edit();
				mEditor.putBoolean(hasEnoughRequrePointPreferenceKey, true);
				mEditor.commit();
			}
		}
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		currentPointTotal = 0;
	}

	private void initRequrePointPreference() {
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		hasEnoughRequrePointPreferenceValue = mPerferences.getBoolean(hasEnoughRequrePointPreferenceKey, false);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
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
		searchListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				String car = (String) arg0.getItemAtPosition(pos);
//				Toast.makeText(getApplicationContext(), "开始查看：" + car, Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("car", car);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		initFavorites();
		favoriteListView = (ListView) findViewById(R.id.favoriteList);
		favoriteListView.setAdapter(favoriteListAdapter);

		favoriteListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				String car = (String) arg0.getItemAtPosition(pos);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("car", car);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

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
		owns.setText("更多精品下载...");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
			}
		});
	

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
			final String carName = getItem(paramInt).toString();
			localTextView1.setText(carName);
			ImageView imageView = (ImageView) paramView.findViewById(R.id.flag_icon);

			ImageView carIcon = (ImageView) paramView.findViewById(R.id.carIcon);
			AssetManager assets = getAssets();
			try {
				// 打开指定资源对应的输入流  
				InputStream assetFile = assets.open("image/" + ListManager.CarImageMap.get(carName) + ".jpg");
				carIcon.setImageBitmap(BitmapFactory.decodeStream(assetFile));
			} catch (IOException e) {
				e.printStackTrace();
			}

			imageView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
					String myFavorite = mPerferences.getString(My_Favorite_Key, "");
					if (!exist(myFavorite, carName)) {
						favoriteList.add(carName);
					}
					favoriteListAdapter.notifyDataSetChanged();
					SharedPreferences.Editor mEditor = mPerferences.edit();
					mEditor.putString(My_Favorite_Key, add(myFavorite, carName));
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
			final String carName = getItem(paramInt).toString();
			localTextView1.setText(carName);

			ImageView carIcon = (ImageView) paramView.findViewById(R.id.carIcon);
			AssetManager assets = getAssets();
			try {
				// 打开指定资源对应的输入流  
				InputStream assetFile = assets.open("image/" + ListManager.CarImageMap.get(carName) + ".jpg");
				carIcon.setImageBitmap(BitmapFactory.decodeStream(assetFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	private void showDialog() {
		new AlertDialog.Builder(MainActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + MainActivity.currentPointTotal)
				.setMessage(
						"只要积分满足" + MainActivity.requirePoint + "，就可以使用收藏功能！！ 您当前的积分不足" + MainActivity.requirePoint
								+ "，暂时不能使用收藏功能。\n\n【免费获得积分方法】：请点击【确认键】进入推荐下载列表 , 【下载、安装并打开】软件获得相应积分，破解收藏功能！！")
				.setPositiveButton("【确认】", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
					}
				}).setNegativeButton("【取消】", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// finish();
					}
				}).show();
	}

}