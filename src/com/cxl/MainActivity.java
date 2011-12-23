package com.cxl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cxl.qiyejiayulu.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "1、马云"));
		MENU_List.add(new KeyValue("2", "2、史玉柱"));
		MENU_List.add(new KeyValue("3", "3、史蒂夫・乔布斯"));
		MENU_List.add(new KeyValue("4", "4、张朝阳"));
		MENU_List.add(new KeyValue("5", "5、陈天桥"));
		MENU_List.add(new KeyValue("6", "6、周鸿伟"));
		MENU_List.add(new KeyValue("7", "7、李嘉诚"));
		MENU_List.add(new KeyValue("8", "8、牛根生"));
		MENU_List.add(new KeyValue("9", "9、李开复"));
		MENU_List.add(new KeyValue("10", "10、兰世立"));
		MENU_List.add(new KeyValue("11", "11、柳传志"));
		MENU_List.add(new KeyValue("12", "12、洛克菲勒"));
		MENU_List.add(new KeyValue("13", "13、稻盛和夫"));
		MENU_List.add(new KeyValue("14", "14、松下幸之助"));
		MENU_List.add(new KeyValue("15", "15、陈一舟"));
		MENU_List.add(new KeyValue("16", "16、比尔・盖茨"));
		MENU_List.add(new KeyValue("17", "17、沃伦・巴菲特"));
		MENU_List.add(new KeyValue("18", "18、李泽楷"));
		MENU_List.add(new KeyValue("19", "19、杨致远"));
		MENU_List.add(new KeyValue("20", "20、杰克・韦尔奇"));
		MENU_List.add(new KeyValue("21", "21、俞敏洪"));
		MENU_List.add(new KeyValue("22", "22、王石"));
		MENU_List.add(new KeyValue("23", "23、冯仑"));
		MENU_List.add(new KeyValue("24", "24、唐骏"));
		MENU_List.add(new KeyValue("25", "25、黄光裕"));
		MENU_List.add(new KeyValue("26", "26、孙正义"));
	}



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		menuList = (ListView) findViewById(R.id.menuList);
		menuAdapter = new ArrayAdapter<KeyValue>(this,
				R.layout.simple_list_layout, R.id.txtListItem, MENU_List);
		menuList.setAdapter(menuAdapter);
		menuList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				KeyValue menu = (KeyValue) arg0.getItemAtPosition(pos);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("menu", menu.getKey());
				bundle.putBoolean("startByMenu", true);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});
	}
}