package com.cxl;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.cxl.ListManager.KeyValue;
import com.cxl.tangshi.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends TabActivity implements
		TabHost.OnTabChangeListener, UpdatePointsNotifier {
	TabHost tabHost;
	private ExpandableListView firstMenuListView;
	private ListView favoriteListView;
	private ArrayAdapter<KeyValue> favoriteListAdapter;
	private ArrayAdapter<String> firstMenuAdapter;
	public static List<KeyValue> favoriteList = new ArrayList<KeyValue>();

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
		favoriteListAdapter.notifyDataSetChanged();
		initRequrePointPreference();
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
				SharedPreferences mPerferences = PreferenceManager
						.getDefaultSharedPreferences(MainActivity.this);
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
		SharedPreferences mPerferences = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);
		hasEnoughRequrePointPreferenceValue = mPerferences.getBoolean(
				hasEnoughRequrePointPreferenceKey, false);
	}

    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		this.tabHost = getTabHost();
		FrameLayout localFrameLayout = this.tabHost.getTabContentView();
		LayoutInflater.from(this)
				.inflate(R.layout.main, localFrameLayout, true);
		TabHost.TabSpec oneTabSpec = this.tabHost.newTabSpec("One")
				.setIndicator("", getResources().getDrawable(R.drawable.about))
				.setContent(R.id.widget_layout_sort);
		((TabHost) tabHost).addTab(oneTabSpec);
		TabHost.TabSpec twoTabSpec = this.tabHost
				.newTabSpec("Two")
				.setIndicator("", getResources().getDrawable(R.drawable.search))
				.setContent(R.id.widget_layout_search);
		((TabHost) tabHost).addTab(twoTabSpec);
		TabHost.TabSpec threeTabSpec = this.tabHost
				.newTabSpec("Three")
				.setIndicator("",
						getResources().getDrawable(R.drawable.favorite))
				.setContent(R.id.widget_layout_point);
		((TabHost) tabHost).addTab(threeTabSpec);
		this.tabHost.setOnTabChangedListener(this);
		tabHost.setCurrentTab(1);

		firstMenuListView = (ExpandableListView) findViewById(R.id.searchList);
//		firstMenuAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1,
//				ListManager.First_Menu_List);
		firstMenuListView.setAdapter(new MyExpandableListAdapter() );
		firstMenuListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition ,
					int childPosition, long id) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SubMenuActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("menu",  ListManager.children[groupPosition][childPosition].toString());
				intent.putExtras(bundle);
				startActivity(intent);
//				Toast.makeText(getApplicationContext(), "开始鉴赏： "+ListManager.children[groupPosition][childPosition].toString(), Toast.LENGTH_SHORT).show();  
				return false;
			}
		});
		firstMenuListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
//				String menu = (String) arg0.getItemAtPosition(pos);
//				Intent intent = new Intent();
//				intent.setClass(MainActivity.this, SubMenuActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putString("menu", menu);
//				intent.putExtras(bundle);
//				startActivity(intent);
			}
		});
//		firstMenuListView.expandGroup(0);
//		firstMenuListView.expandGroup(1);
//		firstMenuListView.expandGroup(2);

		initFavorites();
		favoriteListView = (ListView) findViewById(R.id.favoriteList);
		favoriteListAdapter = new ArrayAdapter<KeyValue>(this,
				android.R.layout.simple_list_item_1, favoriteList);
		favoriteListView.setAdapter(favoriteListAdapter);

		favoriteListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				KeyValue subMenuAndFileName = (KeyValue) arg0
						.getItemAtPosition(pos);
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
				new AlertDialog.Builder(MainActivity.this)
						.setMessage("确定清空收藏？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialoginterface,
											int i) {
										SharedPreferences mPerferences = PreferenceManager
												.getDefaultSharedPreferences(MainActivity.this);
										SharedPreferences.Editor mEditor = mPerferences
												.edit();
										mEditor.putString(
												DetailActivity.Favorite_Key, "");
										mEditor.commit();
										favoriteList.clear();
										favoriteListAdapter
												.notifyDataSetChanged();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}
		});

		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多免费应用...");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(MainActivity.this).showMore(
						MainActivity.this);
			}
		});
	}

	public void onTabChanged(String tabId) {
	}

	private void initFavorites() {
		SharedPreferences mPerferences = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);
		String myFavorite = mPerferences.getString(DetailActivity.Favorite_Key,
				"");
		if ("".equals(myFavorite)) {
			return;
		}
		String[] splitStrings = myFavorite
				.split(DetailActivity.Favorite_Item_Split);
		for (String itemString : splitStrings) {
			favoriteList.add(new KeyValue( itemString
					.split(DetailActivity.Item_Key_Value_Split)[1],itemString
					.split(DetailActivity.Item_Key_Value_Split)[0]));
		}
	}

	private void showDialog() {
		new AlertDialog.Builder(MainActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + MainActivity.currentPointTotal)
				.setMessage(
						"只要积分满足"
								+ MainActivity.requirePoint
								+ "，就可以使用收藏功能！！ 您当前的积分不足"
								+ MainActivity.requirePoint
								+ "，暂时不能使用收藏功能。\n\n【免费获得积分方法】：请点击【确认键】进入推荐下载列表 , 【下载、安装并打开】软件获得相应积分，破解收藏功能！！")
				.setPositiveButton("【确认】",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								// 显示推荐安装程序（Offer）.
								AppConnect.getInstance(MainActivity.this)
										.showOffers(MainActivity.this);
							}
						})
				.setNegativeButton("【取消】",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// finish();
							}
						}).show();
	}

	// 自定义Adapter
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return ListManager.children[groupPosition][childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return ListManager.children[groupPosition].length;
		}

		// 取子列表中的某一项的 View
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			return textView;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return ListManager.groups[groupPosition];
		}

		@Override
		public int getGroupCount() {
			return ListManager.groups.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		// 取父列表中的某一项的 View
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setText(getGroup(groupPosition).toString());
			return textView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		// 获取某一项的 View 的逻辑
		private TextView getGenericView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 64);
			TextView textView = new TextView(MainActivity.this);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setPadding(32, 0, 0, 0);
			return textView;
		}
	}

}