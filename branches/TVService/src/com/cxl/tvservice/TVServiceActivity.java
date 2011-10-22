package com.cxl.tvservice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class TVServiceActivity extends TabActivity implements UpdatePointsNotifier {
	private static final String My_Favorite = "My_Favorite";//存放格式为：   337@#@广东新闻频道###334@#@广东体育频道
	private static final String Favorite_Item_Split = "###";
	private static final String ID_Name_Split = "@#@";

	private static final String Light_Blue = "#87CEEB";
	private static final String Black = "#000000";

	private int originFavoritePanelItemCount = 0;//记录收藏选项卡的原始控件数目

	private Set<String> favoriteChangeSet = new HashSet<String>();//每次点击新增加的收藏频道，都会记录在这里，用于选项卡切换时动态增加频道按钮

	LinearLayout favoritePanel;

	TextView TVdetails;
	TextView favoriteTVdetails;
	Button favoriteButton;
	Button deleteButton;

	Spinner areaSpinner;
	Spinner TVstationSpinner;
	Spinner TVchannelSpinner;

	List<KeyValuePair> TVstationSpinnerList;
	List<KeyValuePair> TVchannelSpinnerList;
	ArrayAdapter<KeyValuePair> TVstationaAdapter;
	ArrayAdapter<KeyValuePair> TVchannelAdapter;

	private void showDialog() {
		new AlertDialog.Builder(TVServiceActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + currentPointTotal)
				.setMessage(
						"只要积分满足" + requirePoint + "，收藏功能就可以使用！！ 您当前的积分不足" + requirePoint
								+ "，无法进行收藏。\n【免费获得积分方法】：请点击确认键进入推荐下载列表 , 下载并安装软件获得相应积分，解除收藏功能，定制您喜欢的频道预告！！")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(TVServiceActivity.this).showOffers(TVServiceActivity.this);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						//						finish();
					}
				}).show();
	}

	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	int currentPointTotal = 0;//当前积分
	public static final int requirePoint = 150;//要求积分
	private static boolean hasEnoughRequrePoint = false;//是否达到积分

	final Handler mHandler = new Handler();

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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/**
		 * 
		 */
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		Button owns1 = (Button) findViewById(R.id.OwnsButton1);
		owns1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(TVServiceActivity.this).showMore(TVServiceActivity.this);
			}
		});
		Button offers1 = (Button) findViewById(R.id.OffersButton1);
		offers1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(TVServiceActivity.this).showOffers(TVServiceActivity.this);
			}
		});

		TVdetails = (TextView) findViewById(R.id.TVdetails);

		areaSpinner = (Spinner) findViewById(R.id.area);
		List<KeyValuePair> lst = TVServiceHelper.getAreas();
		ArrayAdapter<KeyValuePair> myaAdapter = new ArrayAdapter<KeyValuePair>(this,
				android.R.layout.simple_spinner_item, lst);
		areaSpinner.setAdapter(myaAdapter);
		areaSpinner.setSelection(TVServiceHelper.currentAreaSpinnerIndex, true);
		areaSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedId = ((KeyValuePair) areaSpinner.getSelectedItem()).getKey();
				TVstationSpinnerList = TVServiceHelper.getTVstationString(selectedId);
				TVstationaAdapter = new ArrayAdapter<KeyValuePair>(TVServiceActivity.this,
						android.R.layout.simple_spinner_item, TVstationSpinnerList);
				TVstationSpinner.setAdapter(TVstationaAdapter);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		TVstationSpinner = (Spinner) findViewById(R.id.TVstation);
		//当前选择的AreaId
		String currentSelectAreaId = ((KeyValuePair) areaSpinner.getSelectedItem()).getKey();
		TVstationSpinnerList = TVServiceHelper.getTVstationString(currentSelectAreaId);
		TVstationaAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.simple_spinner_item,
				TVstationSpinnerList);
		TVstationSpinner.setAdapter(TVstationaAdapter);
		TVstationSpinner.setSelection(TVServiceHelper.currentTVstationSpinnerIndex, true);
		TVstationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedId = ((KeyValuePair) TVstationSpinner.getSelectedItem()).getKey();
				TVchannelSpinnerList = TVServiceHelper.getTVchannelString(selectedId);
				TVchannelAdapter = new ArrayAdapter<KeyValuePair>(TVServiceActivity.this,
						android.R.layout.simple_spinner_item, TVchannelSpinnerList);
				TVchannelSpinner.setAdapter(TVchannelAdapter);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		TVchannelSpinner = (Spinner) findViewById(R.id.TVchannel);
		//当前选择的TVstationId
		String currentSelectTVstationId = ((KeyValuePair) TVstationSpinner.getSelectedItem()).getKey();
		TVchannelSpinnerList = TVServiceHelper.getTVchannelString(currentSelectTVstationId);
		TVchannelAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.simple_spinner_item,
				TVchannelSpinnerList);
		TVchannelSpinner.setAdapter(TVchannelAdapter);
		TVchannelSpinner.setSelection(TVServiceHelper.currentTVchannelSpinnerIndex, true);

		TVchannelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedId = ((KeyValuePair) TVchannelSpinner.getSelectedItem()).getKey();
				TVdetails.setText(TVServiceHelper.getTVprogramDetail(selectedId, ""));
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		TVdetails.setBackgroundColor(Color.parseColor(Light_Blue));
		TVdetails.setTextColor(Color.parseColor(Black));
		TVdetails.setText(TVServiceHelper.getTVprogramDetail(
				((KeyValuePair) TVchannelSpinner.getSelectedItem()).getKey(), ""));

		favoriteButton = (Button) findViewById(R.id.favoriteButton);
		favoriteButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (!hasEnoughRequrePoint) {//没达到积分
					showDialog();
				} else {

					KeyValuePair selectedItem = (KeyValuePair) TVchannelSpinner.getSelectedItem();
					String selectedIdAndName = selectedItem.getKey() + ID_Name_Split + selectedItem.getValue();
					SharedPreferences mPerferences = PreferenceManager
							.getDefaultSharedPreferences(TVServiceActivity.this);
					String myFavorite = mPerferences.getString(My_Favorite, "");
					if (!exist(myFavorite, selectedIdAndName)) {
						favoriteChangeSet.add(selectedIdAndName);
					}
					SharedPreferences.Editor mEditor = mPerferences.edit();
					mEditor.putString(My_Favorite, add(myFavorite, selectedIdAndName));
					mEditor.commit();

					Toast.makeText(TVServiceActivity.this, "您收藏了" + selectedItem.getValue(), Toast.LENGTH_LONG).show();
				}
			}
		});
		deleteButton = (Button) findViewById(R.id.deleteButton);
		deleteButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(TVServiceActivity.this).setIcon(R.drawable.delete).setTitle("提示")
						.setMessage("确定要清空收藏的频道?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								SharedPreferences mPerferences = PreferenceManager
										.getDefaultSharedPreferences(TVServiceActivity.this);
								SharedPreferences.Editor mEditor = mPerferences.edit();
								mEditor.putString(My_Favorite, "");
								mEditor.commit();
								favoriteTVdetails.setText("");
								//减去原先的两个button
								favoritePanel.removeViews(0, favoritePanel.getChildCount()
										- originFavoritePanelItemCount);
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								//						finish();
							}
						}).show();
			}
		});
		TabHost mTabHost = getTabHost();
		mTabHost.addTab(mTabHost
				.newTabSpec("mainPanel")
				.setIndicator(getResources().getString(R.string.mainPanel),
						getResources().getDrawable(R.drawable.select)).setContent(R.id.mainPanel));
		favoritePanel = (LinearLayout) findViewById(R.id.favoritePanel);
		originFavoritePanelItemCount = favoritePanel.getChildCount();
		addFavoriteButtonsToFavoritePanel();
		mTabHost.addTab(mTabHost
				.newTabSpec("favoritePanel")
				.setIndicator(getResources().getString(R.string.favoritePanel),
						getResources().getDrawable(R.drawable.favorite)).setContent(R.id.favoritePanel));
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				if ("favoritePanel".equals(tabId) && favoriteChangeSet.size() > 0) {
					for (String itemString : favoriteChangeSet) {
						Button button = new Button(TVServiceActivity.this);
						button.setId(Integer.parseInt(itemString.split(ID_Name_Split)[0]));
						button.setText(itemString.split(ID_Name_Split)[1]);
						button.setOnClickListener(new Button.OnClickListener() {
							public void onClick(View v) {
								favoriteTVdetails.setText("【---------" + ((Button) v).getText() + "---------】\n"
										+ TVServiceHelper.getTVprogramDetail(String.valueOf(v.getId()), ""));
							}
						});
						favoritePanel.addView(button, 0);
					}
					//清空
					favoriteChangeSet.clear();
				}
			}
		});
		mTabHost.setCurrentTab(0);

	}

	private boolean exist(String orginString, String selectedIdAndName) {
		boolean exist = false;
		String[] splitStrings = orginString.split(Favorite_Item_Split);
		for (String itemString : splitStrings) {
			if (itemString.equals(selectedIdAndName)) {
				exist = true;
			}
		}
		return exist;
	}

	private String add(String orginString, String selectedIdAndName) {
		if ("".equals(orginString)) {
			return selectedIdAndName;
		}
		if (!exist(orginString, selectedIdAndName)) {
			orginString += Favorite_Item_Split + selectedIdAndName;
		}
		return orginString;

	}

	private void addFavoriteButtonsToFavoritePanel() {
		favoriteTVdetails = (TextView) findViewById(R.id.favoriteTVdetails);
		favoriteTVdetails.setBackgroundColor(Color.parseColor(Light_Blue));
		favoriteTVdetails.setTextColor(Color.parseColor(Black));
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(TVServiceActivity.this);
		String myFavorite = mPerferences.getString(My_Favorite, "");
		if ("".equals(myFavorite)) {
			return;
		}
		String[] splitStrings = myFavorite.split(Favorite_Item_Split);
		for (String itemString : splitStrings) {
			Button button = new Button(TVServiceActivity.this);
			button.setId(Integer.parseInt(itemString.split(ID_Name_Split)[0]));
			button.setText(itemString.split(ID_Name_Split)[1]);
			button.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					favoriteTVdetails.setText("【------------" + ((Button) v).getText() + "------------】\n"
							+ TVServiceHelper.getTVprogramDetail(String.valueOf(v.getId()), ""));
				}
			});
			favoritePanel.addView(button, 0);
		}
	}

}