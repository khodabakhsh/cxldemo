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

import com.cxl.qiongrenqueshenme.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "1 、﻿穷人的饥饿思维 "));
		MENU_List.add(new KeyValue("2", "2 、穷人只有一个鸡蛋  "));
		MENU_List.add(new KeyValue("3", "3 、穷人占据不利地形  "));
		MENU_List.add(new KeyValue("4", "4 、穷人是永远的弱者  "));
		MENU_List.add(new KeyValue("5", "5 、穷人是社会的基础  "));
		MENU_List.add(new KeyValue("6", "6 、穷人是一种资源  "));
		MENU_List.add(new KeyValue("7", "7 、穷人被支配  "));
		MENU_List.add(new KeyValue("8", "8 、穷人不安全  "));
		MENU_List.add(new KeyValue("9", "9 、穷人容易上当  "));
		MENU_List.add(new KeyValue("10", "10 、穷人劳动不止  "));
		MENU_List.add(new KeyValue("11", "11 、穷人恩重如山  "));
		MENU_List.add(new KeyValue("12", "12 、穷人是颗螺丝钉  "));
		MENU_List.add(new KeyValue("13", "13 、穷人没法不志短  "));
		MENU_List.add(new KeyValue("14", "14 、穷人为富人输血  "));
		MENU_List.add(new KeyValue("15", "15 、穷人后富起来  "));
		MENU_List.add(new KeyValue("16", "16 、穷人不要感恩戴德  "));
		MENU_List.add(new KeyValue("17", "17 、穷人幻想现代化  "));
		MENU_List.add(new KeyValue("18", "18 、穷人要奋斗  "));
		MENU_List.add(new KeyValue("19", "19 、素养创造财富  "));
		MENU_List.add(new KeyValue("20", "20 、靠人推是走不远的  "));
		MENU_List.add(new KeyValue("21", "21 、穷人要有思想  "));
		MENU_List.add(new KeyValue("22", "22 、穷人要有激情  "));
		MENU_List.add(new KeyValue("23", "23 、你是穷人你怕什么  "));
		MENU_List.add(new KeyValue("24", "24 、穷人重视手艺  "));
		MENU_List.add(new KeyValue("25", "25 、穷人最有革命性  "));
		MENU_List.add(new KeyValue("26", "26 、把幻想变成理想  "));
		MENU_List.add(new KeyValue("27", "27 、始终保持斗志  "));
		MENU_List.add(new KeyValue("28", "28 、发财是件苦差事  "));
		MENU_List.add(new KeyValue("29", "29 、穷人混在穷人圈  "));
		MENU_List.add(new KeyValue("30", "30 、穷人靠单位  "));
		MENU_List.add(new KeyValue("31", "31 、穷人向往年薪  "));
		MENU_List.add(new KeyValue("32", "32 、穷人舍不得鸡肋  "));
		MENU_List.add(new KeyValue("33", "33 、穷人帮人创业  "));
		MENU_List.add(new KeyValue("34", "34 、穷人救济穷人  "));
		MENU_List.add(new KeyValue("35", "35 、穷人被选择  "));
		MENU_List.add(new KeyValue("36", "36 、穷人梦想当明星  "));
		MENU_List.add(new KeyValue("37", "37 、穷人知足常乐  "));
		MENU_List.add(new KeyValue("38", "38 、穷人只会跟进  "));
		MENU_List.add(new KeyValue("39", "39 、穷人太超前  "));
		MENU_List.add(new KeyValue("40", "40 、穷人不识时务  "));
		MENU_List.add(new KeyValue("41", "41 、穷人有投资冲动  "));
		MENU_List.add(new KeyValue("42", "42 、不要轻易借钱 "));
		MENU_List.add(new KeyValue("43", "43 、穷人疏于工计  "));
		MENU_List.add(new KeyValue("44", "44 、事业是最好的投资  "));
		MENU_List.add(new KeyValue("45", "45 、投资致富需要素质  "));
		MENU_List.add(new KeyValue("46", "46 、有计划地赚钱  "));
		MENU_List.add(new KeyValue("47", "47 、抓住支配权  "));
		MENU_List.add(new KeyValue("48", "48 、把一件事做透  "));
		MENU_List.add(new KeyValue("49", "49 、坚持就是胜利  "));
		MENU_List.add(new KeyValue("50", "50 、敢于做大事  "));
		MENU_List.add(new KeyValue("51", "51 、敢于反潮流  "));
		MENU_List.add(new KeyValue("52", "52 、成功靠日积月累 "));
		MENU_List.add(new KeyValue("53", "53 、放弃比抓住更难  "));
		MENU_List.add(new KeyValue("54", "54 、不要瞧不起小生意  "));
		MENU_List.add(new KeyValue("55", "55 、节约是种美德  "));
		MENU_List.add(new KeyValue("56", "56 、穷人不要显得穷  "));
		MENU_List.add(new KeyValue("57", "57 、穷人的弱点说不出口  "));
		MENU_List.add(new KeyValue("58", "58 、穷人要忍耐  "));
		MENU_List.add(new KeyValue("59", "59 、穷人想当大哥  "));
		MENU_List.add(new KeyValue("60", "60 、穷人衣锦还乡  "));
		MENU_List.add(new KeyValue("61", "61 、穷人的骨气  "));
		MENU_List.add(new KeyValue("62", "62 、穷人随便出汗  "));
		MENU_List.add(new KeyValue("63", "63 、穷人的贪婪  "));
		MENU_List.add(new KeyValue("64", "64 、穷人的野蛮  "));
		MENU_List.add(new KeyValue("65", "65 、穷人的粗糙  "));
		MENU_List.add(new KeyValue("66", "66 、穷人容易走极端  "));
		MENU_List.add(new KeyValue("67", "67 、穷人最恨不平等  "));
		MENU_List.add(new KeyValue("68", "68 、穷人想当暴发户  "));
		MENU_List.add(new KeyValue("69", "69 、穷人穷怕了  "));
		MENU_List.add(new KeyValue("70", "70 、穷人作秀富人做派  "));
		MENU_List.add(new KeyValue("71", "71 、穷人戴黄金  "));
		MENU_List.add(new KeyValue("72", "72 、穷人的虚荣  "));
		MENU_List.add(new KeyValue("73", "73 、穷人的节俭  "));
		MENU_List.add(new KeyValue("74", "74 、穷人的休闲  "));
		MENU_List.add(new KeyValue("75", "75 、穷人的名片  "));
		MENU_List.add(new KeyValue("76", "76 、穷人吃补品  "));
		MENU_List.add(new KeyValue("77", "77 、穷人看电视  "));
		MENU_List.add(new KeyValue("78", "78 、公平是相对的  "));
		MENU_List.add(new KeyValue("79", "79 、向机会不均开火  "));
		MENU_List.add(new KeyValue("80", "80 、经济学的立场  "));
		MENU_List.add(new KeyValue("81", "81 、财富的阴暗面  "));
		MENU_List.add(new KeyValue("82", "82 、富人应该多缴税  "));
		MENU_List.add(new KeyValue("83", "83 、富人的不义之财  "));
		MENU_List.add(new KeyValue("84", "84 、穷人买得起什么  "));
		MENU_List.add(new KeyValue("85", "85 、穷人不要生病  "));
		MENU_List.add(new KeyValue("86", "86 、穷人不要惹事  "));
		MENU_List.add(new KeyValue("87", "87 、规则大于道德  "));
		MENU_List.add(new KeyValue("88", "88 、劳心者的价植  "));
		MENU_List.add(new KeyValue("89", "89 、合理的按劳分配  "));
		MENU_List.add(new KeyValue("90", "90 、教育是最大的投资  "));
		MENU_List.add(new KeyValue("91", "91 、教育能够改变命运吗  "));
		MENU_List.add(new KeyValue("92", "92 、穷人害怕没饭碗  "));
		MENU_List.add(new KeyValue("93", "93 、机遇造就富人  "));
		MENU_List.add(new KeyValue("94", "94 、富人获益于自由  "));
		MENU_List.add(new KeyValue("95", "95 、跑步进入中产阶级  "));
		MENU_List.add(new KeyValue("96", "96 、没有长生不老的人  "));
		MENU_List.add(new KeyValue("97", "97 、赚钱是种享受  "));
		MENU_List.add(new KeyValue("98", "98 、工作是种生活方式  "));
		MENU_List.add(new KeyValue("99", "99 、幸福和金钱有关  "));
		MENU_List.add(new KeyValue("100", "100 、穷人不要被吓倒  "));

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