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

import com.cxl.seeanybody.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	ListView menuList;
	ArrayAdapter<String> menuAdapter;
	public static final ArrayList<String> MENU_List = new ArrayList<String>();
	static {
		MENU_List.add("继续上次阅读");
		MENU_List.add("前言");
//		for (int i = 1; i <= 6; i++) {
//			MENU_List.add("第" + i + "章");
//		}
		MENU_List.add("第1章    这个人在隐瞒什么吗");
		MENU_List.add("第2章    赞成还是反对：他喜欢还是不喜欢");
		MENU_List.add("第3章    她是信心十足，还是仅仅在装酷");
		MENU_List.add("第4章    这些都是真的吗");
		MENU_List.add("第5章    测测兴趣等级：他是真的感兴趣，还是你在浪费时间");
		MENU_List.add("第6章    同伙还是敌人：她到底是哪一边的");

	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
//		AppConnect.getInstance(this).getPoints(this);
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
				R.layout.simple_list_layout, R.id.txtListItem, MENU_List);
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