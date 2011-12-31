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

import com.cxl.zhuinvhai36ji.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "1、第一计：铁面计"));
		MENU_List.add(new KeyValue("2", "2、第二计：打折计"));
		MENU_List.add(new KeyValue("3", "3、第三计：秋千计"));
		MENU_List.add(new KeyValue("4", "4、第四计：吃草计"));
		MENU_List.add(new KeyValue("5", "5、第五计：高帽计"));
		MENU_List.add(new KeyValue("6", "6、第六计：黄花计"));
		MENU_List.add(new KeyValue("7", "7、第七计：叉饭计"));
		MENU_List.add(new KeyValue("8", "8、第八计：土豆计"));
		MENU_List.add(new KeyValue("9", "9、第九计：射雁计"));
		MENU_List.add(new KeyValue("10", "10、第十计：金钱计"));
		MENU_List.add(new KeyValue("11", "11、第十一计：咖啡计"));
		MENU_List.add(new KeyValue("12", "12、第十二计：走狗计"));
		MENU_List.add(new KeyValue("13", "13、第十三计：台球计"));
		MENU_List.add(new KeyValue("14", "14、第十四计：西游计"));
		MENU_List.add(new KeyValue("15", "15、第十五计：臭袜计"));
		MENU_List.add(new KeyValue("16", "16、第十六计：卖柑计"));
		MENU_List.add(new KeyValue("17", "17、第十七计：两面计"));
		MENU_List.add(new KeyValue("18", "18、第十八计：蝴蝶计"));
		MENU_List.add(new KeyValue("19", "19、第十九计：解牛计"));
		MENU_List.add(new KeyValue("20", "20、第二十计：画饼计"));
		MENU_List.add(new KeyValue("21", "21、第二一计：唐诗计"));
		MENU_List.add(new KeyValue("22", "22、第二二计：厕所计"));
		MENU_List.add(new KeyValue("23", "23、第二三计：苹果计"));
		MENU_List.add(new KeyValue("24", "24、第二四计：小鲜计"));
		MENU_List.add(new KeyValue("25", "25、第二五计：霸王计"));
		MENU_List.add(new KeyValue("26", "26、第二六计：睡觉计"));
		MENU_List.add(new KeyValue("27", "27、第二七计：开心计"));
		MENU_List.add(new KeyValue("28", "28、第二八计：牙婆计"));
		MENU_List.add(new KeyValue("29", "29、第二九计：秋鸿计"));
		MENU_List.add(new KeyValue("30", "30、第三十计：彩票计"));
		MENU_List.add(new KeyValue("31", "31、第三一计：醉酒计"));
		MENU_List.add(new KeyValue("32", "32、第三二计：克螂计"));
		MENU_List.add(new KeyValue("33", "33、第三三计：守拙计"));
		MENU_List.add(new KeyValue("34", "34、第三四计：撞钟计"));
		MENU_List.add(new KeyValue("35", "35、第三五计：存酒计"));
		MENU_List.add(new KeyValue("36", "36、第三六计：飞翼计"));

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