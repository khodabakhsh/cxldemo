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

import com.cxl.gsh2011.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {

		MENU_List.add(new KeyValue("1", "第04期  《忘年交》等12则"));
		MENU_List.add(new KeyValue("2", "第04期  美丽的错误"));
		MENU_List.add(new KeyValue("3", "第04期  只怪自己嘴巴馋"));
		MENU_List.add(new KeyValue("4", "第04期  惊险的游戏"));
		MENU_List.add(new KeyValue("5", "第04期  忘了自己是谁"));
		MENU_List.add(new KeyValue("6", "第04期  救命的桂花树"));
		MENU_List.add(new KeyValue("7", "第04期  疯狂的房贷"));
		MENU_List.add(new KeyValue("8", "第04期  愚人节的玩笑"));
		MENU_List.add(new KeyValue("9", "第04期  拿你做模特"));
		MENU_List.add(new KeyValue("10", "第04期  偏爱臭脚"));
		MENU_List.add(new KeyValue("11", "第04期  来历不明的汇款"));
		MENU_List.add(new KeyValue("12", "第04期  遭遇骚扰"));
		MENU_List.add(new KeyValue("13", "第04期  白鸽含书奇缘"));
		MENU_List.add(new KeyValue("14", "第04期  漂亮的女房客"));
		MENU_List.add(new KeyValue("15", "第04期  一张信用卡"));
		MENU_List.add(new KeyValue("16", "第04期  《我们的宗旨》等30则"));
		MENU_List.add(new KeyValue("17", "第04期  鸭绿江边的香格里拉"));
		MENU_List.add(new KeyValue("18", "第04期  董卫华历险记"));
		MENU_List.add(new KeyValue("19", "第04期  作文里的真相"));
		MENU_List.add(new KeyValue("20", "第04期  遭遇中奖"));
		MENU_List.add(new KeyValue("21", "第04期  判官娶妻"));
		MENU_List.add(new KeyValue("22", "第04期  特殊招聘"));
		MENU_List.add(new KeyValue("23", "第04期  房契该写谁的名字"));
		MENU_List.add(new KeyValue("24", "第04期  新传说·遭遇哄抢"));
		MENU_List.add(new KeyValue("25", "第04期  东方夜谈·紫檀床"));
		MENU_List.add(new KeyValue("26", "第04期  阿P系列幽默故事·阿P出差"));
		MENU_List.add(new KeyValue("27", "第05期  故事会 爱情玫瑰糖"));
		MENU_List.add(new KeyValue("28", "第05期  故事会 同学聚会"));
		MENU_List.add(new KeyValue("29", "第05期  我的故事 雷管未响"));
		MENU_List.add(new KeyValue("30", "第05期  新传说 多出来的爹"));
		MENU_List.add(new KeyValue("31", "第05期  东方夜谈 这份爱金不换"));
		MENU_List.add(new KeyValue("32", "第05期  职场故事 活得给力"));
		MENU_List.add(new KeyValue("33", "第06期  阿P系列幽默故事 阿P当“富人”"));
		MENU_List.add(new KeyValue("34", "第06期  新传说 特殊治疗"));
		MENU_List.add(new KeyValue("35", "第06期  海外故事 最后一个安打"));
		MENU_List.add(new KeyValue("36", "第06期  职场故事 赚钱离婚"));
		MENU_List.add(new KeyValue("37", "第07期  新传说 谁来埋单"));
		MENU_List.add(new KeyValue("38", "第07期  阿P系列幽默故事 阿P回家"));
		MENU_List.add(new KeyValue("39", "第07期  中篇故事（精编版） 你必须道歉"));
		MENU_List.add(new KeyValue("40", "第07期  情感故事 借钱不能瞒老婆"));
		MENU_List.add(new KeyValue("41", "第08期  新传说 搭车那点事儿"));
		MENU_List.add(new KeyValue("42", "第08期  新传说 反穿衣服的怪老头"));
		MENU_List.add(new KeyValue("43", "第08期  传闻逸事 泥人王"));
		MENU_List.add(new KeyValue("44", "第08期  职场故事 代号和谐"));
		MENU_List.add(new KeyValue("45", "第09期  新传说 讲规矩"));
		MENU_List.add(new KeyValue("46", "第09期  新传说 天价理发"));
		MENU_List.add(new KeyValue("47", "第09期  幽默世界 孟子他妈"));
		MENU_List.add(new KeyValue("48", "第09期  民间故事金库 出家不容易"));
		MENU_List.add(new KeyValue("49", "第10期  儿子"));
		MENU_List.add(new KeyValue("50", "第10期  万里未归人"));
		MENU_List.add(new KeyValue("51", "第10期  不可当真的甜言蜜语"));
		MENU_List.add(new KeyValue("52", "第10期  老师是一个资深贫二代"));
		MENU_List.add(new KeyValue("53", "第10期  做一个幸福的职场人"));
		MENU_List.add(new KeyValue("54", "第10期  苏末丢失了林又南"));
		MENU_List.add(new KeyValue("55", "第10期  老情人博物馆"));
		MENU_List.add(new KeyValue("56", "第10期  缴械的父亲"));
		MENU_List.add(new KeyValue("57", "第10期  包子"));
		MENU_List.add(new KeyValue("58", "第10期  青春在一个暑假里速成"));
		MENU_List.add(new KeyValue("59", "第10期  不是我选择了此生"));
		MENU_List.add(new KeyValue("60", "第10期  西方作家的写作信条"));
		MENU_List.add(new KeyValue("61", "第10期  生活的一种"));
		MENU_List.add(new KeyValue("62", "第10期  脑残经济学"));

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