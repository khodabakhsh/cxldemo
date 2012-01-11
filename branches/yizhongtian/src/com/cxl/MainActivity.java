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

import com.cxl.yizhongtian.R;
import com.waps.AppConnect;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "1、（一）大江东去"));
		MENU_List.add(new KeyValue("2", "2、（二）真假曹操"));
		MENU_List.add(new KeyValue("3", "3、（三）奸雄之谜"));
		MENU_List.add(new KeyValue("4", "4、（四）能臣之路"));
		MENU_List.add(new KeyValue("5", "5、（五）何去何从"));
		MENU_List.add(new KeyValue("6", "6、（六）一错再错"));
		MENU_List.add(new KeyValue("7", "7、（七）深谋远虑"));
		MENU_List.add(new KeyValue("8", "8、（八）鬼使神差"));
		MENU_List.add(new KeyValue("9", "9、（九）一决雌雄"));
		MENU_List.add(new KeyValue("10", "10、（十）胜败有凭"));
		MENU_List.add(new KeyValue("11", "11、（十一）海纳百川"));
		MENU_List.add(new KeyValue("12", "12、（十二）天下归心"));
		MENU_List.add(new KeyValue("13", "13、（十三）青梅煮酒"));
		MENU_List.add(new KeyValue("14", "14、（十四）天生奇才"));
		MENU_List.add(new KeyValue("15", "15、（十五）慧眼所见"));
		MENU_List.add(new KeyValue("16", "16、（十六）三顾茅庐"));
		MENU_List.add(new KeyValue("17", "17、（十七）隆中对策"));
		MENU_List.add(new KeyValue("18", "18、（十八）江东基业"));
		MENU_List.add(new KeyValue("19", "19、（十九）必争之地"));
		MENU_List.add(new KeyValue("20", "20、（二十）兵临城下"));
		MENU_List.add(new KeyValue("21", "21、（二十一）临危受命"));
		MENU_List.add(new KeyValue("22", "22、（二十二）力挽狂澜"));
		MENU_List.add(new KeyValue("23", "23、（二十三）中流砥柱"));
		MENU_List.add(new KeyValue("24", "24、（二十四）赤壁疑云"));
		MENU_List.add(new KeyValue("25", "25、（二十五）半途而废"));
		MENU_List.add(new KeyValue("26", "26、（二十六）得寸进尺"));
		MENU_List.add(new KeyValue("27", "27、（二十七）进退失据"));
		MENU_List.add(new KeyValue("28", "28、（二十八）借刀杀人"));
		MENU_List.add(new KeyValue("29", "29、（二十九）命案真相"));
		MENU_List.add(new KeyValue("30", "30、（三十）夺嫡之争"));
		MENU_List.add(new KeyValue("31", "31、（三十一）乘虚而入"));
		MENU_List.add(new KeyValue("32", "32、（三十二）蜜月阴谋"));
		MENU_List.add(new KeyValue("33", "33、（三十三）白衣渡江"));
		MENU_List.add(new KeyValue("34", "34、（三十四）败走麦城"));
		MENU_List.add(new KeyValue("35", "35、（三十五）夷陵之战"));
		MENU_List.add(new KeyValue("36", "36、（三十六）永安托孤"));
		MENU_List.add(new KeyValue("37", "37、（三十七）非常君臣"));
		MENU_List.add(new KeyValue("38", "38、（三十八）难容水火"));
		MENU_List.add(new KeyValue("39", "39、（三十九）痛失臂膀"));
		MENU_List.add(new KeyValue("40", "40、（四十）祸起萧墙"));
		MENU_List.add(new KeyValue("41", "41、（四十一）以攻为守"));
		MENU_List.add(new KeyValue("42", "42、（四十二）无力回天"));
		MENU_List.add(new KeyValue("43", "43、（四十三）风云际会"));
		MENU_List.add(new KeyValue("44", "44、（四十四）坐断东南"));
		MENU_List.add(new KeyValue("45", "45、（四十五）情天恨海"));
		MENU_List.add(new KeyValue("46", "46、（四十六）冷暖人生"));
		MENU_List.add(new KeyValue("47", "47、（四十七）逆流而上"));
		MENU_List.add(new KeyValue("48", "48、（四十八）殊途同归"));
		MENU_List.add(new KeyValue("49", "49、（四十九）天下大势"));
		MENU_List.add(new KeyValue("50", "50、（五十）历史插曲"));
		MENU_List.add(new KeyValue("51", "51、（五十一）百年孤独"));
		MENU_List.add(new KeyValue("52", "52、（五十二）千古风流"));
		
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		AppConnect.getInstance(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		menuList = (ListView) findViewById(R.id.menuList);
		menuAdapter = new ArrayAdapter<KeyValue>(this, R.layout.simple_list_layout, R.id.txtListItem, MENU_List);
		menuList.setAdapter(menuAdapter);
		menuList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
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
		Bundle fromBundle = getIntent().getExtras();
		boolean showMenu = fromBundle==null?false:fromBundle.getBoolean("showMenu");
		
		if ((PreferenceUtil.getScrollY(MainActivity.this) != 0
				|| PreferenceUtil.getTxtIndex(MainActivity.this) != DetailActivity.Start_Page_Index)&&!showMenu) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, DetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putBoolean("startByMenu", false);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		}
	}
}