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

import com.cxl.yibaigezhihui.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "1、﻿前言 "));
		MENU_List.add(new KeyValue("2", "2、教育孩子不可主观行事 "));
		MENU_List.add(new KeyValue("3", "3、教育要以理服人 "));
		MENU_List.add(new KeyValue("4", "4、父母要做到有令必行 "));
		MENU_List.add(new KeyValue("5", "5、文明礼貌教育不可少 "));
		MENU_List.add(new KeyValue("6", "6、名流并不一定适合你孩子 "));
		MENU_List.add(new KeyValue("7", "7、压力过大对孩子的成长不利 "));
		MENU_List.add(new KeyValue("8", "8、让孩子集中学习注意力 "));
		MENU_List.add(new KeyValue("9", "9、不要忽视孩子的恶意撒谎 "));
		MENU_List.add(new KeyValue("10", "10、帮孩子制定可行性计划 "));
		MENU_List.add(new KeyValue("11", "11、培养孩子爱阅读的好习惯 "));
		MENU_List.add(new KeyValue("12", "12、注意尊重孩子的人格 "));
		MENU_List.add(new KeyValue("13", "13、家长的期望不一定适应孩子 "));
		MENU_List.add(new KeyValue("14", "14、教会孩子如何花钱 "));
		MENU_List.add(new KeyValue("15", "15、教会孩子不与人攀比 "));
		MENU_List.add(new KeyValue("16", "16、让孩子进行家务劳动 "));
		MENU_List.add(new KeyValue("17", "17、不尊重孩子的惩罚于教育无益 "));
		MENU_List.add(new KeyValue("18", "18、孩子需要平等对待 "));
		MENU_List.add(new KeyValue("19", "19、教会孩子要谦虚 "));
		MENU_List.add(new KeyValue("20", "20、给孩子自由述说的空间 "));
		MENU_List.add(new KeyValue("21", "21、孩子的事情由孩子做 "));
		MENU_List.add(new KeyValue("22", "22、鼓励孩子做事 "));
		MENU_List.add(new KeyValue("23", "23、培养孩子自己拿主意的习惯 "));
		MENU_List.add(new KeyValue("24", "24、让孩子丢掉虚荣心 "));
		MENU_List.add(new KeyValue("25", "25、鼓励孩子勇于面对挫折 "));
		MENU_List.add(new KeyValue("26", "26、用肯定的语气鼓励孩子 "));
		MENU_List.add(new KeyValue("27", "27、从小教育孩子要遵守纪律 "));
		MENU_List.add(new KeyValue("28", "28、孩子生存能力的培养 "));
		MENU_List.add(new KeyValue("29", "29、教孩子学会保护自己 "));
		MENU_List.add(new KeyValue("30", "30、发现孩子的闪光点 "));
		MENU_List.add(new KeyValue("31", "31、孩子的潜能 "));
		MENU_List.add(new KeyValue("32", "32、孩子的嫉妒行为 "));
		MENU_List.add(new KeyValue("33", "33、子反复暗示你的信任 "));
		MENU_List.add(new KeyValue("34", "34、创造性活动 "));
		MENU_List.add(new KeyValue("35", "35、要讲究艺术 "));
		MENU_List.add(new KeyValue("36", "36、入地理解孩子 "));
		MENU_List.add(new KeyValue("37", "37、孩子与人为善的品质 "));
		MENU_List.add(new KeyValue("38", "38、爱，娇是害 "));
		MENU_List.add(new KeyValue("39", "39、子从小学会吃苦 "));
		MENU_List.add(new KeyValue("40", "40、为孩子遮丑护短 "));
		MENU_List.add(new KeyValue("41", "41、对孩子说丧气话 "));
		MENU_List.add(new KeyValue("42", "42、孩子独立的能力 "));
		MENU_List.add(new KeyValue("43", "43、子心灵沟通 "));
		MENU_List.add(new KeyValue("44", "44、孩子“冒险” "));
		MENU_List.add(new KeyValue("45", "45、让孩子多动手，多探索 "));
		MENU_List.add(new KeyValue("46", "46、让孩子学会竞争 "));
		MENU_List.add(new KeyValue("47", "47、鼓励孩子的兴趣 "));
		MENU_List.add(new KeyValue("48", "48、孩子不是“橡皮泥” "));
		MENU_List.add(new KeyValue("49", "49、鼓励孩子与别人交往 "));
		MENU_List.add(new KeyValue("50", "50、不要为孩子“设限” "));
		MENU_List.add(new KeyValue("51", "51、根据孩子的特点进行教育 "));
		MENU_List.add(new KeyValue("52", "52、沟通能掌握孩子的变化 "));
		MENU_List.add(new KeyValue("53", "53、教育孩子不要在背后指责朋友 "));
		MENU_List.add(new KeyValue("54", "54、不要做白费力的事 "));
		MENU_List.add(new KeyValue("55", "55、不可对孩子失去信心 "));
		MENU_List.add(new KeyValue("56", "56、不要主观培养天才 "));
		MENU_List.add(new KeyValue("57", "57、给孩子改正错误的机会 "));
		MENU_List.add(new KeyValue("58", "58、查清孩子犯错的原因 "));
		MENU_List.add(new KeyValue("59", "59、消除孩子对老师的抵触情绪 "));
		MENU_List.add(new KeyValue("60", "60、允许孩子尝试 "));
		MENU_List.add(new KeyValue("61", "61、不要遗弃弱智的孩子 "));
		MENU_List.add(new KeyValue("62", "62、不要“拔苗助长” "));
		MENU_List.add(new KeyValue("63", "63、不要过分表现家长作风 "));
		MENU_List.add(new KeyValue("64", "64、做孩子竞争向上的榜样 "));
		MENU_List.add(new KeyValue("65", "65、不要给孩子加压 "));
		MENU_List.add(new KeyValue("66", "66、给孩子吃点苦 "));
		MENU_List.add(new KeyValue("67", "67、纠正孩子的挑食习惯 "));
		MENU_List.add(new KeyValue("68", "68、做好孩子的引路人 "));
		MENU_List.add(new KeyValue("69", "69、教给孩子应变的能力 "));
		MENU_List.add(new KeyValue("70", "70、恰如其分地教育 "));
		MENU_List.add(new KeyValue("71", "71、“捧”着孩子不好 "));
		MENU_List.add(new KeyValue("72", "72、教育孩子没有“不劳而获” "));
		MENU_List.add(new KeyValue("73", "73、家长的经验不一定是对的 "));
		MENU_List.add(new KeyValue("74", "74、看到孩子的长处就鼓励 "));
		MENU_List.add(new KeyValue("75", "75、让孩子走出“自卑”的阴影 "));
		MENU_List.add(new KeyValue("76", "76、从小培养孩子的责任心 "));
		MENU_List.add(new KeyValue("77", "77、尽早纠正孩子的错误 "));
		MENU_List.add(new KeyValue("78", "78、告诉孩子“天外有天”的道理 "));
		MENU_List.add(new KeyValue("79", "79、培育孩子的孝心 "));
		MENU_List.add(new KeyValue("80", "80、不可漠视孩子的依赖性 "));
		MENU_List.add(new KeyValue("81", "81、纠正孩子坐不住的习惯 "));
		MENU_List.add(new KeyValue("82", "82、不要给孩子“笨蛋”的暗示 "));
		MENU_List.add(new KeyValue("83", "83、不要主观的批评孩子 "));
		MENU_List.add(new KeyValue("84", "84、看到孩子交往的价值 "));
		MENU_List.add(new KeyValue("85", "85、不要让孩子过于看重自己 "));
		MENU_List.add(new KeyValue("86", "86、培养孩子的耐性 "));
		MENU_List.add(new KeyValue("87", "87、不拿孩子与别人攀比 "));
		MENU_List.add(new KeyValue("88", "88、你的语言传递着你的价值观念 "));
		MENU_List.add(new KeyValue("89", "89、不要为孩子当牛做马 "));
		MENU_List.add(new KeyValue("90", "90、让孩子远离“脏“话 "));
		MENU_List.add(new KeyValue("91", "91、指导孩子怎样玩 "));
		MENU_List.add(new KeyValue("92", "92、不要嘲笑孩子 "));
		MENU_List.add(new KeyValue("93", "93、强迫施教效果不好 "));
		MENU_List.add(new KeyValue("94", "94、早期识字讲究方法 "));
		MENU_List.add(new KeyValue("95", "95、不要常给孩子亮“黄牌” "));
		MENU_List.add(new KeyValue("96", "96、不可夸大孩子的短处 "));
		MENU_List.add(new KeyValue("97", "97、不要处处要求孩子听话 "));
		MENU_List.add(new KeyValue("98", "98、为子女积攒财富无益 "));
		MENU_List.add(new KeyValue("99", "99、不要随意触动孩子的隐私 "));
		MENU_List.add(new KeyValue("100", "100、推翻孩子身上的“大山” "));
		MENU_List.add(new KeyValue("101", "101、以民主的姿态增强孩子的自信 "));
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