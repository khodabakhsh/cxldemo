package com.cxl.stevejobs;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cxl.brainfun.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	ListView menuList;
	ArrayAdapter<String> menuAdapter;
	public static final ArrayList<String> MENU_List = new ArrayList<String>();
	static {
//		MENU_List.add("继续上次阅读");
		for (int i = 1; i <= 72; i++) {
			MENU_List.add("第" + i + "章");
		}

	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
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

		View contineView = ((LayoutInflater) MainActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.simple_list_layout, null);
		TextView textView = ((TextView) contineView.findViewById(R.id.txtListItem));
		textView.setTextColor(Color.parseColor("#FF69B4"));
		textView.setTextSize(22);
		textView.setPadding(3, 8, 3, 8);
		textView.setText("继续上次阅读");
		menuList.addHeaderView(contineView, "继续上次阅读", true);
		
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