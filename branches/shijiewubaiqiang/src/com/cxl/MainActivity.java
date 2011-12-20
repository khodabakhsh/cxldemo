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

import com.cxl.shijiewubaiqiang.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "1、沃尔玛"));
		MENU_List.add(new KeyValue("2", "2、皇家壳牌石油"));
		MENU_List.add(new KeyValue("3", "3、埃克森美孚"));
		MENU_List.add(new KeyValue("4", "4、英国石油"));
		MENU_List.add(new KeyValue("5", "5、中国石化"));
		MENU_List.add(new KeyValue("6", "6、中国石油天然气"));
		MENU_List.add(new KeyValue("7", "7、国家电网"));
		MENU_List.add(new KeyValue("8", "8、丰田汽车"));
		MENU_List.add(new KeyValue("9", "9、日本邮政"));
		MENU_List.add(new KeyValue("10", "10、雪佛龙"));
		MENU_List.add(new KeyValue("11", "11、道达尔"));
		MENU_List.add(new KeyValue("12", "12、康菲"));
		MENU_List.add(new KeyValue("13", "13、大众汽车"));
		MENU_List.add(new KeyValue("14", "14、安盛"));
		MENU_List.add(new KeyValue("15", "15、房利美"));
		MENU_List.add(new KeyValue("16", "16、通用电气"));
		MENU_List.add(new KeyValue("17", "17、荷兰国际集团"));
		MENU_List.add(new KeyValue("18", "18、嘉能可"));
		MENU_List.add(new KeyValue("19", "19、伯克希尔哈撒韦"));
		MENU_List.add(new KeyValue("20", "20、通用汽车"));
		MENU_List.add(new KeyValue("21", "21、美国银行"));
		MENU_List.add(new KeyValue("22", "22、三星电子"));
		MENU_List.add(new KeyValue("23", "23、埃尼"));
		MENU_List.add(new KeyValue("24", "24、戴姆勒"));
		MENU_List.add(new KeyValue("25", "25、福特汽车"));
		MENU_List.add(new KeyValue("26", "26、法国巴黎银行"));
		MENU_List.add(new KeyValue("27", "27、安联"));
		MENU_List.add(new KeyValue("28", "28、惠普"));
		MENU_List.add(new KeyValue("29", "29、意昂"));
		MENU_List.add(new KeyValue("30", "30、美国电话电报公司"));
		MENU_List.add(new KeyValue("31", "31、日本电报电话"));
		MENU_List.add(new KeyValue("32", "32、家乐福"));
		MENU_List.add(new KeyValue("33", "33、忠利保险"));
		MENU_List.add(new KeyValue("34", "34、巴西石油"));
		MENU_List.add(new KeyValue("35", "35、俄罗斯天然气工业"));
		MENU_List.add(new KeyValue("36", "36、摩根大通"));
		MENU_List.add(new KeyValue("37", "37、麦克森"));
		MENU_List.add(new KeyValue("38", "38、法国燃气-苏伊士集团"));
		MENU_List.add(new KeyValue("39", "39、花旗集团"));
		MENU_List.add(new KeyValue("40", "40、日立"));
		MENU_List.add(new KeyValue("41", "41、威瑞森电信"));
		MENU_List.add(new KeyValue("42", "42、雀巢"));
		MENU_List.add(new KeyValue("43", "43、农业信贷银行"));
		MENU_List.add(new KeyValue("44", "44、美国国际集团"));
		MENU_List.add(new KeyValue("45", "45、本田汽车"));
		MENU_List.add(new KeyValue("46", "46、汇丰控股"));
		MENU_List.add(new KeyValue("47", "47、西门子公司"));
		MENU_List.add(new KeyValue("48", "48、日产汽车公司"));
		MENU_List.add(new KeyValue("49", "49、墨西哥石油公司"));
		MENU_List.add(new KeyValue("50", "50、松下电器"));
		MENU_List.add(new KeyValue("51", "51、西班牙国际银行"));
		MENU_List.add(new KeyValue("52", "52、IBM公司"));
		MENU_List.add(new KeyValue("53", "53、美国卡地纳健康集团"));
		MENU_List.add(new KeyValue("54", "54、房地美公司"));
		MENU_List.add(new KeyValue("55", "55、现代汽车公司"));
		MENU_List.add(new KeyValue("56", "56、意大利国家电力公司"));
		MENU_List.add(new KeyValue("57", "57、CVS Caremark"));
		MENU_List.add(new KeyValue("58", "58、JX 控股"));
		MENU_List.add(new KeyValue("59", "59、劳埃德TSB集团"));
		MENU_List.add(new KeyValue("60", "60、鸿海精密"));
		MENU_List.add(new KeyValue("61", "61、英国特易购公司"));
		MENU_List.add(new KeyValue("62", "62、美国联合健康集团"));
		MENU_List.add(new KeyValue("63", "63、富国银行"));
		MENU_List.add(new KeyValue("64", "64、英杰华"));
		MENU_List.add(new KeyValue("65", "65、麦德龙"));
		MENU_List.add(new KeyValue("66", "66、委内瑞拉石油"));
		MENU_List.add(new KeyValue("67", "67、挪威国家石油"));
		MENU_List.add(new KeyValue("68", "68、法国电力"));
		MENU_List.add(new KeyValue("69", "69、卢克石油"));
		MENU_List.add(new KeyValue("70", "70、瓦莱罗能源"));
		MENU_List.add(new KeyValue("71", "71、巴斯夫"));
		MENU_List.add(new KeyValue("72", "72、法兴银行"));
		MENU_List.add(new KeyValue("73", "73、索尼　　"));
		MENU_List.add(new KeyValue("74", "74、阿塞洛米塔尔钢铁集团"));
		MENU_List.add(new KeyValue("75", "75、德国电信"));
		MENU_List.add(new KeyValue("76", "76、克罗格"));
		MENU_List.add(new KeyValue("77", "77、中国工商银行"));
		MENU_List.add(new KeyValue("78", "78、西班牙电信国际集团"));
		MENU_List.add(new KeyValue("79", "79、宝马"));
		MENU_List.add(new KeyValue("80", "80、宝洁"));
		MENU_List.add(new KeyValue("81", "81、日本生命"));
		MENU_List.add(new KeyValue("82", "82、韩国SK集团"));
		MENU_List.add(new KeyValue("83", "83、EXOR 集团"));
		MENU_List.add(new KeyValue("84", "84、美国美源伯根公司"));
		MENU_List.add(new KeyValue("85", "85、好市多"));
		MENU_List.add(new KeyValue("86", "86、马来西亚石油公司"));
		MENU_List.add(new KeyValue("87", "87、中国移动通信"));
		MENU_List.add(new KeyValue("88", "88、慕尼黑再保险"));
		MENU_List.add(new KeyValue("89", "89、东芝"));
		MENU_List.add(new KeyValue("90", "90、标致"));
		MENU_List.add(new KeyValue("91", "91、英国保诚集团"));
		MENU_List.add(new KeyValue("92", "92、沃达丰"));
		MENU_List.add(new KeyValue("93", "93、德国邮政"));
		MENU_List.add(new KeyValue("94", "94、雷普索尔YPF"));
		MENU_List.add(new KeyValue("95", "95、中国中铁"));
		MENU_List.add(new KeyValue("96", "96、德克夏银行"));
		MENU_List.add(new KeyValue("97", "97、法国BPCE银行集团"));
		MENU_List.add(new KeyValue("98", "98、印度石油"));
		MENU_List.add(new KeyValue("99", "99、马拉松石油"));
		MENU_List.add(new KeyValue("100", "100、苏格兰皇家银行"));
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