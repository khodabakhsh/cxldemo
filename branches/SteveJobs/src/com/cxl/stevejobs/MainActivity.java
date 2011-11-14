package com.cxl.stevejobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	ListView menuList;
	ArrayAdapter menuAdapter;
	public static final Map<String, String> MENU_FILE_MAP = new HashMap<String, String>();
	public static final ArrayList<String> MENU_List = new ArrayList<String>();
	static {
		MENU_FILE_MAP.put("前言", "0.dat");
		MENU_FILE_MAP.put("1 人生起步", "1.dat");
		MENU_FILE_MAP.put("2 公司诞生了", "2.dat");
		MENU_FILE_MAP.put("3 让我们当海盗吧", "3.dat");
		MENU_FILE_MAP.put("4 告别“苹果”", "4.dat");
		MENU_FILE_MAP.put("5 NTXE公司：乔布斯的“下一站”", "5.dat");
		MENU_FILE_MAP.put("6 进军好莱坞", "6.dat");
		MENU_FILE_MAP.put("7 携手迪士尼", "7.dat");
		MENU_FILE_MAP.put("8 重返“苹果”", "8.dat");
		MENU_FILE_MAP.put("9 亿万富翁", "9.dat");
		MENU_FILE_MAP.put("10 开创新领域", "10.dat");
		MENU_FILE_MAP.put("11 “我的地盘”iPod、 iTunes", "11.dat");
		MENU_FILE_MAP.put("12 巨人之争", "12.dat");
		MENU_FILE_MAP.put("13 辉煌时刻", "13.dat");
		MENU_FILE_MAP.put("尾声", "14.dat");

		MENU_List.add("前言");
		MENU_List.add("1 人生起步");
		MENU_List.add("2 公司诞生了");
		MENU_List.add("3 让我们当海盗吧");
		MENU_List.add("4 告别“苹果”");
		MENU_List.add("5 NTXE公司：乔布斯的“下一站”");
		MENU_List.add("6 进军好莱坞");
		MENU_List.add("7 携手迪士尼");
		MENU_List.add("8 重返“苹果”");
		MENU_List.add("9 亿万富翁");
		MENU_List.add("10 开创新领域");
		MENU_List.add("11 “我的地盘”iPod、 iTunes");
		MENU_List.add("12 巨人之争");
		MENU_List.add("13 辉煌时刻");
		MENU_List.add("尾声");

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

	/**
	 * AppConnect.getPoints()方法的实现，必须实现
	 * 
	 * @param currencyName
	 *            虚拟货币名称.
	 * @param pointTotal
	 *            虚拟货币余额.
	 */
	public void getUpdatePoints(String currencyName, int pointTotal) {

	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		menuList = (ListView) findViewById(R.id.menuList);
		menuAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_layout, R.id.txtListItem, MENU_List);
		menuList.setAdapter(menuAdapter);
		menuList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				String menu = (String) arg0.getItemAtPosition(pos);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("menu", menu);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}