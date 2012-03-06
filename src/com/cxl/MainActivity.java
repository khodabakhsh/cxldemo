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

import com.cxl.qiuAiMiJi.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "1、肉麻情话宝典"));
		MENU_List.add(new KeyValue("2", "2、十二星座求爱秘籍"));
		MENU_List.add(new KeyValue("3", "3、不小心说出求爱的"));
		MENU_List.add(new KeyValue("4", "4、十二星座求爱秘籍"));
		MENU_List.add(new KeyValue("5", "5、女性弱点二十四式"));
		MENU_List.add(new KeyValue("6", "6、求爱技巧（初级）"));
		MENU_List.add(new KeyValue("7", "7、猜透女人心的三种"));
		MENU_List.add(new KeyValue("8", "8、追女孩秘诀"));
		MENU_List.add(new KeyValue("9", "9、泡妞大法(心理篇)"));
		MENU_List.add(new KeyValue("10", "10、男人拿什么打动女"));
		MENU_List.add(new KeyValue("11", "11、男人拿什么打动女"));
		MENU_List.add(new KeyValue("12", "12、换种方式说我爱你"));
		MENU_List.add(new KeyValue("13", "13、接吻技巧"));
		MENU_List.add(new KeyValue("14", "14、成为令女性心仪男"));
		MENU_List.add(new KeyValue("15", "15、情人节前的必修课"));
		MENU_List.add(new KeyValue("16", "16、求爱定律"));
		MENU_List.add(new KeyValue("17", "17、追女招数小全"));
		MENU_List.add(new KeyValue("18", "18、五十种表答爱的方"));
		MENU_List.add(new KeyValue("19", "19、身体接触的方法"));
		MENU_List.add(new KeyValue("20", "20、俘获MM大法(卑鄙篇"));
		MENU_List.add(new KeyValue("21", "21、校园幽默--情书模"));
		MENU_List.add(new KeyValue("22", "22、男孩必修五招术"));
		MENU_List.add(new KeyValue("23", "23、如何分手？"));
		MENU_List.add(new KeyValue("24", "24、女孩子喜欢的二十"));
		MENU_List.add(new KeyValue("25", "25、做个花心的女人"));
		MENU_List.add(new KeyValue("26", "26、怎样俘获男心"));
		MENU_List.add(new KeyValue("27", "27、怎样才能令女性对"));
		MENU_List.add(new KeyValue("28", "28、让男人恶心的30种"));
		MENU_List.add(new KeyValue("29", "29、闻香识女人"));
		MENU_List.add(new KeyValue("30", "30、女孩子“小动作”"));
		MENU_List.add(new KeyValue("31", "31、爱的告白的方法"));
		MENU_List.add(new KeyValue("32", "32、求爱技巧之探讨"));
		MENU_List.add(new KeyValue("33", "33、该分手时则分手"));
		MENU_List.add(new KeyValue("34", "34、爱情出师表"));
		MENU_List.add(new KeyValue("35", "35、光棍爱情技巧"));
		MENU_List.add(new KeyValue("36", "36、恋爱大忌十六宗罪"));
		MENU_List.add(new KeyValue("37", "37、如何追求自己的女"));
		MENU_List.add(new KeyValue("38", "38、了解男人再恋爱"));
		MENU_List.add(new KeyValue("39", "39、网络女孩上网聊天"));
		MENU_List.add(new KeyValue("40", "40、如何与陌生女孩交"));
		MENU_List.add(new KeyValue("41", "41、第一次约七大忌"));
		MENU_List.add(new KeyValue("42", "42、爱情游戏与规则"));
		MENU_List.add(new KeyValue("43", "43、该分手时则分手"));
		MENU_List.add(new KeyValue("44", "44、十种类型的女孩不"));
		MENU_List.add(new KeyValue("45", "45、接吻的定义"));
		MENU_List.add(new KeyValue("46", "46、男女间有没有纯友"));
		MENU_List.add(new KeyValue("47", "47、当男人不再爱女人"));
		MENU_List.add(new KeyValue("48", "48、女人要有女人味"));
		MENU_List.add(new KeyValue("49", "49、嗅觉在恋爱中的作"));
		MENU_List.add(new KeyValue("50", "50、爱情添加剂"));
		MENU_List.add(new KeyValue("51", "51、大学生不懂爱？"));
		MENU_List.add(new KeyValue("52", "52、心要慢慢给"));
		MENU_List.add(new KeyValue("53", "53、求爱实例分析"));
		MENU_List.add(new KeyValue("54", "54、怎样选择理想的男"));
		MENU_List.add(new KeyValue("55", "55、爱情长长久久――"));
		MENU_List.add(new KeyValue("56", "56、叁Ｓ政策"));
		MENU_List.add(new KeyValue("57", "57、冷视男人的诱惑"));
		MENU_List.add(new KeyValue("58", "58、女孩子“小动作”"));
		MENU_List.add(new KeyValue("59", "59、一个大学男生的爆"));
		MENU_List.add(new KeyValue("60", "60、一个猛男追一个乖"));

	

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