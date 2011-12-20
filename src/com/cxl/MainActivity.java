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

import com.cxl.shijieweijiezhimi.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("0", "0、玛雅人的2012世界末日预言"));
		MENU_List.add(new KeyValue("1", "1、神秘海底人鱼之谜"));
		MENU_List.add(new KeyValue("2", "2、揭开神秘怪物蹄印之谜"));
		MENU_List.add(new KeyValue("3", "3、有待破解的木乃伊书"));
		MENU_List.add(new KeyValue("4", "4、神秘的西夏王陵"));
		MENU_List.add(new KeyValue("5", "5、千年宝刹闹鬼之谜"));
		MENU_List.add(new KeyValue("6", "6、埃及金字塔内神秘能量之谜"));
		MENU_List.add(new KeyValue("7", "7、大西洋海底神秘的水下建筑"));
		MENU_List.add(new KeyValue("8", "8、希腊古代神话中的巨人族真的存在吗？"));
		MENU_List.add(new KeyValue("9", "9、神秘莫测的四度空间"));
		MENU_List.add(new KeyValue("10", "10、古印加帝国黄金藏匿地之谜"));
		MENU_List.add(new KeyValue("11", "11、古剑之谜--越王剑"));
		MENU_List.add(new KeyValue("12", "12、《圣经》诺亚方舟之谜"));
		MENU_List.add(new KeyValue("13", "13、被疑是外星物种 西班牙绿孩子的传说"));
		MENU_List.add(new KeyValue("14", "14、人类失踪谜案 “时空隧道” 是主谋？"));
		MENU_List.add(new KeyValue("15", "15、20亿年前核反应堆被发现 制造者成谜团?"));
		MENU_List.add(new KeyValue("16", "16、地球上存在多维世界和时间扭曲吗？"));
		MENU_List.add(new KeyValue("17", "17、揭秘神奇的宇宙生命信息"));
		MENU_List.add(new KeyValue("18", "18、西游记中沙和尚为何戴九颗骷髅项链？"));
		MENU_List.add(new KeyValue("19", "19、西汉蔡伦造纸：是革新技术还是发明?"));
		MENU_List.add(new KeyValue("20", "20、人类为什么是人类？Y染色体会消失吗?"));
		MENU_List.add(new KeyValue("21", "21、世界上第一位太空人加加林是死于谋杀？"));
		MENU_List.add(new KeyValue("22", "22、乾隆到底是不是海宁陈家的儿子？"));
		MENU_List.add(new KeyValue("23", "23、沈阳故宫暗藏四大谜团：清朝龙脉之谜"));
		MENU_List.add(new KeyValue("24", "24、文豪莎士比亚身份之谜：是一名女替身？"));
		MENU_List.add(new KeyValue("25", "25、佛教圣物舍利子形成之谜"));
		MENU_List.add(new KeyValue("26", "26、地球邻居的月球起源之谜"));
		MENU_List.add(new KeyValue("27", "27、中国古兵书《孙子兵法》如何走向世界？"));
		MENU_List.add(new KeyValue("28", "28、天狼星系外星人传授古多贡人天文知识？"));
		MENU_List.add(new KeyValue("29", "29、2600年前巴基斯坦卡拉奇木乃伊之谜团"));
		MENU_List.add(new KeyValue("30", "30、中国四川三星堆千年未解的五大谜团"));
		MENU_List.add(new KeyValue("31", "31、揭开亚马逊密林中“黄金”城之谜"));
		MENU_List.add(new KeyValue("32", "32、揭开维纳斯断臂之谜"));
		MENU_List.add(new KeyValue("33", "33、科学家揭密人体自燃 并非纯属无稽之谈"));
		MENU_List.add(new KeyValue("34", "34、恐怖的亚利桑纳州金矿"));
		MENU_List.add(new KeyValue("35", "35、古印度“战神之车”解密"));
		MENU_List.add(new KeyValue("36", "36、俄罗斯一数学家推测日历藏着外星人信函"));
		MENU_List.add(new KeyValue("37", "37、泰坦尼克号沉没之谜"));
		MENU_List.add(new KeyValue("38", "38、尼斯湖水怪之谜"));
		MENU_List.add(new KeyValue("39", "39、肯尼迪之死谜团"));
		MENU_List.add(new KeyValue("40", "40、UFO之谜"));
		MENU_List.add(new KeyValue("41", "41、鬼魂之谜"));
		MENU_List.add(new KeyValue("42", "42、埃及艳后克娄巴特拉的宫殿"));
		MENU_List.add(new KeyValue("43", "43、亚特兰蒂斯城在哪儿"));
		MENU_List.add(new KeyValue("44", "44、水晶头颅"));
		MENU_List.add(new KeyValue("45", "45、神秘的墓碑"));
		MENU_List.add(new KeyValue("46", "46、这是诺亚方舟吗"));
		MENU_List.add(new KeyValue("47", "47、星象盘"));
		MENU_List.add(new KeyValue("48", "48、恐龙陨石坑"));
		MENU_List.add(new KeyValue("49", "49、秦始皇曾接见外星人？"));
		MENU_List.add(new KeyValue("50", "50、匪夷所思人体被忽略的“第三眼”"));
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