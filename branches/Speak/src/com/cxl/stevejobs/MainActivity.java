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

import com.cxl.speak.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	ListView menuList;
	ArrayAdapter<String> menuAdapter;
	public static final Map<String, String> MENU_FILE_MAP = new HashMap<String, String>();
	public static final ArrayList<String> MENU_List = new ArrayList<String>();
	static {
		MENU_FILE_MAP.put("第1章、 沟通源自内心", "1.txt");
		MENU_FILE_MAP.put("第2章、 你的价值观、信念、想法从哪儿来？", "2.txt");
		MENU_FILE_MAP.put("第3章、种瓜得瓜，种豆得豆", "3.txt");
		MENU_FILE_MAP.put("第4章、成功者的秘密", "4.txt");
		MENU_FILE_MAP.put("第5章、沟通的 6个要素", "5.txt");
		MENU_FILE_MAP.put("第6章、为自己的沟通负责", "6.txt");
		MENU_FILE_MAP.put("第7章、控制你的肢体语言", "7.txt");
		MENU_FILE_MAP.put("第8章、收集正确信息", "8.txt");
		MENU_FILE_MAP.put("第9章、给予正确信息", "9.txt");
		MENU_FILE_MAP.put("第10章、用别人的“语言”进行沟通", "10.txt");
		MENU_FILE_MAP.put("第11章、减少讨论的方法", "11.txt");
		MENU_FILE_MAP.put("第12章、让反馈为你服务", "12.txt");
		MENU_FILE_MAP.put("第13章、对付难对付的人", "13.txt");
		MENU_FILE_MAP.put("第14章、处理抱怨", "14.txt");
		MENU_FILE_MAP.put("第15章、需要避免的错误", "15.txt");
		MENU_FILE_MAP.put("第16章、说服你的读者", "16.txt");

		MENU_List.add("第1章、 沟通源自内心");
		MENU_List.add("第2章、 你的价值观、信念、想法从哪儿来？");
		MENU_List.add("第3章、种瓜得瓜，种豆得豆");
		MENU_List.add("第4章、成功者的秘密");
		MENU_List.add("第5章、沟通的 6个要素");
		MENU_List.add("第6章、为自己的沟通负责");
		MENU_List.add("第7章、控制你的肢体语言");
		MENU_List.add("第8章、收集正确信息");
		MENU_List.add("第9章、给予正确信息");
		MENU_List.add("第10章、用别人的“语言”进行沟通");
		MENU_List.add("第11章、减少讨论的方法");
		MENU_List.add("第12章、让反馈为你服务");
		MENU_List.add("第13章、对付难对付的人");
		MENU_List.add("第14章、处理抱怨");
		MENU_List.add("第15章、需要避免的错误");
		MENU_List.add("第16章、说服你的读者");

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
		menuAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, MENU_List);
		menuList.setAdapter(menuAdapter);
		menuList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
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