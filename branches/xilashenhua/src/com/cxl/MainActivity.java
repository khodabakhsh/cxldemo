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

import com.cxl.xilashenhua.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "01、创世纪"));
		MENU_List.add(new KeyValue("2", "02、盖亚"));
		MENU_List.add(new KeyValue("3", "03、乌拉诺斯是如何被废黜的"));
		MENU_List.add(new KeyValue("4", "04、克洛诺斯与瑞亚"));
		MENU_List.add(new KeyValue("5", "05、提坦们的反抗"));
		MENU_List.add(new KeyValue("6", "06、与巨人种族之战"));
		MENU_List.add(new KeyValue("7", "07、堤福俄斯"));
		MENU_List.add(new KeyValue("8", "08、普罗米修斯神话(1)"));
		MENU_List.add(new KeyValue("9", "09、普罗米修斯神话(2)"));
		MENU_List.add(new KeyValue("10", "10、潘多拉"));
		MENU_List.add(new KeyValue("11", "11、丢卡利翁与皮拉"));
		MENU_List.add(new KeyValue("12", "12、宙斯"));
		MENU_List.add(new KeyValue("13", "13、宙斯之出世"));
		MENU_List.add(new KeyValue("14", "14、克洛诺斯被废黜"));
		MENU_List.add(new KeyValue("15", "15、宙斯的爱情及其子女(1)"));
		MENU_List.add(new KeyValue("16", "16、宙斯的爱情及其子女(2)"));
		MENU_List.add(new KeyValue("17", "17、宙斯崇拜(1)"));
		MENU_List.add(new KeyValue("18", "18、宙斯崇拜(2)"));
		MENU_List.add(new KeyValue("19", "19、赫拉(1)"));
		MENU_List.add(new KeyValue("20", "20、赫拉(2)"));
		MENU_List.add(new KeyValue("21", "21、赫拉崇拜"));
		MENU_List.add(new KeyValue("22", "22、波塞冬(1)"));
		MENU_List.add(new KeyValue("23", "23、波塞冬(2)"));
		MENU_List.add(new KeyValue("24", "24、波塞冬(3)"));
		MENU_List.add(new KeyValue("25", "25、波塞冬(4)"));
		MENU_List.add(new KeyValue("26", "26、波塞冬(5)"));
		MENU_List.add(new KeyValue("27", "27、波塞冬(6)"));
		MENU_List.add(new KeyValue("28", "28、波塞冬崇拜"));
		MENU_List.add(new KeyValue("29", "29、雅典娜(1)"));
		MENU_List.add(new KeyValue("30", "30、雅典娜(2)"));
		MENU_List.add(new KeyValue("31", "31、古代雅典人眼中的雅典娜"));
		MENU_List.add(new KeyValue("32", "32、德墨忒耳(1)"));
		MENU_List.add(new KeyValue("33", "33、德墨忒耳(2)"));
		MENU_List.add(new KeyValue("34", "34、德墨忒耳(3)"));
		MENU_List.add(new KeyValue("35", "35、德墨忒耳-塞斯谟福若斯(1)"));
		MENU_List.add(new KeyValue("36", "36、德墨忒耳-塞斯谟福若斯(2)"));
		MENU_List.add(new KeyValue("37", "37、德墨忒耳-塞斯谟福若斯(3)"));
		MENU_List.add(new KeyValue("38", "38、德墨忒耳-塞斯谟福若斯(4)"));
		MENU_List.add(new KeyValue("39", "39、德墨忒耳-塞斯谟福若斯(5)"));
		MENU_List.add(new KeyValue("40", "40、阿芙洛狄忒崇拜(1)"));
		MENU_List.add(new KeyValue("41", "41、阿芙洛狄忒崇拜(2)"));
		MENU_List.add(new KeyValue("42", "42、阿芙洛狄忒崇拜(3)"));
		MENU_List.add(new KeyValue("43", "43、赫菲斯托斯(1)"));
		MENU_List.add(new KeyValue("44", "44、赫菲斯托斯(2)"));
		MENU_List.add(new KeyValue("45", "45、赫菲斯托斯(3)"));
		MENU_List.add(new KeyValue("46", "46、神话与象征符号"));
		MENU_List.add(new KeyValue("47", "47、阿瑞斯(1)"));
		MENU_List.add(new KeyValue("48", "48、阿瑞斯(2)"));
		MENU_List.add(new KeyValue("49", "49、阿瑞斯(3)"));
		MENU_List.add(new KeyValue("50", "50、阿瑞斯(4)"));
		MENU_List.add(new KeyValue("51", "51、阿波罗(1)"));
		MENU_List.add(new KeyValue("52", "52、阿波罗(2)"));
		MENU_List.add(new KeyValue("53", "53、阿波罗(3)"));
		MENU_List.add(new KeyValue("54", "54、阿波罗(4)"));
		MENU_List.add(new KeyValue("55", "55、阿波罗(5)"));
		MENU_List.add(new KeyValue("56", "56、阿波罗(6)"));
		MENU_List.add(new KeyValue("57", "57、特尔斐神谕所(1)"));
		MENU_List.add(new KeyValue("58", "58、特尔斐神谕所(2)"));
		MENU_List.add(new KeyValue("59", "59、赫耳墨斯(1)"));
		MENU_List.add(new KeyValue("60", "60、赫耳墨斯(2)"));
		MENU_List.add(new KeyValue("61", "61、赫耳墨斯(3)"));
		MENU_List.add(new KeyValue("62", "62、赫耳墨斯(4)"));
		MENU_List.add(new KeyValue("63", "63、哈德斯"));
		MENU_List.add(new KeyValue("64", "64、狄俄尼索斯(1)"));
		MENU_List.add(new KeyValue("65", "65、狄俄尼索斯(2)"));
		MENU_List.add(new KeyValue("66", "66、狄俄尼索斯(3)"));
		MENU_List.add(new KeyValue("67", "67、酒神崇拜与庆典(1)"));
		MENU_List.add(new KeyValue("68", "68、酒神崇拜与庆典(2)"));
		MENU_List.add(new KeyValue("69", "69、酒神崇拜与庆典(3)"));
		MENU_List.add(new KeyValue("70", "70、潘(1)"));
		MENU_List.add(new KeyValue("71", "71、潘(2)"));
		MENU_List.add(new KeyValue("72", "72、普里阿普斯(1)"));
		MENU_List.add(new KeyValue("73", "73、普里阿普斯(2)"));

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