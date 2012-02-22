package com.cxl;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.cxl.ListManager.KeyValue;
import com.cxl.tangshi.R;
import com.waps.AppConnect;

public class MainActivity extends TabActivity implements TabHost.OnTabChangeListener {
	TabHost tabHost;
	private ExpandableListView firstMenuListView;
	private ListView favoriteListView;
	private ArrayAdapter<KeyValue> favoriteListAdapter;
	public static List<KeyValue> favoriteList = new ArrayList<KeyValue>();

	private EditText txtSearch;
	private TextWatcher watcher = new MyTextWatcher();
	MatchListAdapter matchListAdapter = new MatchListAdapter();
	private ListView searchListView;

	class MyTextWatcher implements TextWatcher {
		public void afterTextChanged(Editable paramEditable) {
		}

		public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
		}

		public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
			ListManager.getSearchList(paramCharSequence.toString());
			matchListAdapter.notifyDataSetChanged();
		}
	}

	class MatchListAdapter extends BaseAdapter {
		private MatchListAdapter() {
		}

		public int getCount() {
			return ListManager.SearchList.size();
		}

		public Object getItem(int paramInt) {
			return ListManager.SearchList.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			if (paramView == null)
				paramView = ((LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.search_list_layout, null);
			String poemName = ((KeyValue) getItem(paramInt)).getValue();
			((TextView) paramView.findViewById(R.id.txtListItem)).setText(poemName);
			return paramView;
		}
	}

	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	protected void onResume() {
		favoriteListAdapter.notifyDataSetChanged();
		ListManager.getSearchList(txtSearch.getText().toString());
		matchListAdapter.notifyDataSetChanged();
		super.onResume();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		this.tabHost = getTabHost();
		FrameLayout localFrameLayout = this.tabHost.getTabContentView();
		LayoutInflater.from(this).inflate(R.layout.main, localFrameLayout, true);
		TabHost.TabSpec oneTabSpec = this.tabHost.newTabSpec("One")
				.setIndicator("", getResources().getDrawable(R.drawable.search)).setContent(R.id.widget_layout_sort);
		((TabHost) tabHost).addTab(oneTabSpec);
		TabHost.TabSpec twoTabSpec = this.tabHost.newTabSpec("Two")
				.setIndicator("", getResources().getDrawable(R.drawable.list)).setContent(R.id.widget_layout_search);
		((TabHost) tabHost).addTab(twoTabSpec);
		TabHost.TabSpec threeTabSpec = this.tabHost.newTabSpec("Three")
				.setIndicator("", getResources().getDrawable(R.drawable.favorite)).setContent(R.id.widget_layout_point);
		((TabHost) tabHost).addTab(threeTabSpec);
		this.tabHost.setOnTabChangedListener(this);
		tabHost.setCurrentTab(1);

		firstMenuListView = (ExpandableListView) findViewById(R.id.firstMenuListView);
		firstMenuListView.setAdapter(new MyExpandableListAdapter());
		firstMenuListView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView arg0, View arg1, final int groupPosition, int childPosition,
					long id) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SubMenuActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("menu", ListManager.children[groupPosition][childPosition].toString());
				intent.putExtras(bundle);
				startActivity(intent);

				return false;
			}
		});
		// firstMenuListView.expandGroup(0);

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

		txtSearch = (EditText) findViewById(R.id.txtSearch);
		txtSearch.addTextChangedListener(this.watcher);
		searchListView = (ListView) findViewById(R.id.searchListView);
		searchListView.setAdapter(matchListAdapter);
		searchListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {

				KeyValue selectItem = (KeyValue) arg0.getItemAtPosition(pos);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("fileName", selectItem.getKey());
				bundle.putString("subMenuName", selectItem.getValue());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

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
			favoriteList.add(new KeyValue(itemString.split(DetailActivity.Item_Key_Value_Split)[1], itemString
					.split(DetailActivity.Item_Key_Value_Split)[0]));
		}
	}

	// 自定义Adapter
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		AbsListView.LayoutParams lp;
		TextView textView;

		public Object getChild(int groupPosition, int childPosition) {
			return ListManager.children[groupPosition][childPosition];
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return ListManager.children[groupPosition].length;
		}

		// 取子列表中的某一项的 View

		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			return textView;
		}

		public Object getGroup(int groupPosition) {
			return ListManager.groups[groupPosition];
		}

		public int getGroupCount() {
			return ListManager.groups.length;
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		// 取父列表中的某一项的 View

		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setText(getGroup(groupPosition).toString());
			return textView;
		}

		public boolean hasStableIds() {
			return true;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		// 获取某一项的 View 的逻辑
		private TextView getGenericView() {
			lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
			textView = new TextView(MainActivity.this);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(18);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setPadding(52, 0, 0, 0);
			return textView;
		}
	}

}