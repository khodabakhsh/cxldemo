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

import com.cxl.baoxiao.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {

		MENU_List.add(new KeyValue("1", "1、美女的口号"));
		MENU_List.add(new KeyValue("2", "2、网上泡MM记"));
		MENU_List.add(new KeyValue("3", "3、新婚闹洞房大全(劲"));
		MENU_List.add(new KeyValue("4", "4、发疯方嫁"));
		MENU_List.add(new KeyValue("5", "5、老公的忏悔（看了"));
		MENU_List.add(new KeyValue("6", "6、甜死MM的几句话"));
		MENU_List.add(new KeyValue("7", "7、洞房花烛夜（电脑"));
		MENU_List.add(new KeyValue("8", "8、令男人“晕菜”的"));
		MENU_List.add(new KeyValue("9", "9、男女关系33个绝妙"));
		MENU_List.add(new KeyValue("10", "10、丑不是你的错（搞"));
		MENU_List.add(new KeyValue("11", "11、最尴尬的事男人篇"));
		MENU_List.add(new KeyValue("12", "12、消防队长的拒爱对"));
		MENU_List.add(new KeyValue("13", "13、洞房花烛夜（电脑"));
		MENU_List.add(new KeyValue("14", "14、第一次绝妙拒绝"));
		MENU_List.add(new KeyValue("15", "15、当男人和女人使用"));
		MENU_List.add(new KeyValue("16", "16、男女约会的十个尴"));
		MENU_List.add(new KeyValue("17", "17、衣柜里有一个男人"));
		MENU_List.add(new KeyValue("18", "18、畅想男人怀孕"));
		MENU_List.add(new KeyValue("19", "19、最狠的骂架"));
		MENU_List.add(new KeyValue("20", "20、女朋友生气时的场"));
		MENU_List.add(new KeyValue("21", "21、短裙的好处"));
		MENU_List.add(new KeyValue("22", "22、恋爱“三草”原则"));
		MENU_List.add(new KeyValue("23", "23、如何在公司骚扰别"));
		MENU_List.add(new KeyValue("24", "24、非常男女"));
		MENU_List.add(new KeyValue("25", "25、神啊，请让我丑一"));
		MENU_List.add(new KeyValue("26", "26、温柔男生和野蛮女"));
		MENU_List.add(new KeyValue("27", "27、都市男女的叹息"));
		MENU_List.add(new KeyValue("28", "28、娶个美女当老婆后"));
		MENU_List.add(new KeyValue("29", "29、大话陌生人"));
		MENU_List.add(new KeyValue("30", "30、坚决不能调戏良家"));
		MENU_List.add(new KeyValue("31", "31、10岁，20岁和30岁"));
		MENU_List.add(new KeyValue("32", "32、陈圆圆与吴三桂、"));
		MENU_List.add(new KeyValue("33", "33、不娶美女的十大理"));
		MENU_List.add(new KeyValue("34", "34、两个男人和一个女"));
		MENU_List.add(new KeyValue("35", "35、当老婆发现老公…"));
		MENU_List.add(new KeyValue("36", "36、浑身都是G点"));
		MENU_List.add(new KeyValue("37", "37、所谓老公老婆"));
		MENU_List.add(new KeyValue("38", "38、为什么拒绝未婚先"));
		MENU_List.add(new KeyValue("39", "39、女人的64个谜团"));
		MENU_List.add(new KeyValue("40", "40、搞笑解析征婚广告"));
		MENU_List.add(new KeyValue("41", "41、手机与女友"));
		MENU_List.add(new KeyValue("42", "42、怀孕的妻子"));
		MENU_List.add(new KeyValue("43", "43、人生中最不浪漫的"));
		MENU_List.add(new KeyValue("44", "44、寻找另一半的新条"));
		MENU_List.add(new KeyValue("45", "45、减肥秘诀"));
		MENU_List.add(new KeyValue("46", "46、婚姻物语：老公和"));
		MENU_List.add(new KeyValue("47", "47、寻找另一半的新条"));
		MENU_List.add(new KeyValue("48", "48、见青蛙跟见靓仔的"));
		MENU_List.add(new KeyValue("49", "49、男人的胸膛"));
		MENU_List.add(new KeyValue("50", "50、软件升级"));
		MENU_List.add(new KeyValue("51", "51、婚恋如股市"));
		MENU_List.add(new KeyValue("52", "52、公交车上的搞笑对"));
		MENU_List.add(new KeyValue("53", "53、我的那一半在下面"));
		MENU_List.add(new KeyValue("54", "54、另类MM想结婚"));
		MENU_List.add(new KeyValue("55", "55、被男友忽悠了回"));
		MENU_List.add(new KeyValue("56", "56、女友裸睡的十大爆"));
		MENU_List.add(new KeyValue("57", "57、我和美女护士臭事"));
		MENU_List.add(new KeyValue("58", "58、男生和女生在网吧"));
		MENU_List.add(new KeyValue("59", "59、一男一女内急"));
		MENU_List.add(new KeyValue("60", "60、我们结婚吧"));
		MENU_List.add(new KeyValue("61", "61、相亲是个漂亮女孩"));
		MENU_List.add(new KeyValue("62", "62、经典：网上泡MM记"));
		MENU_List.add(new KeyValue("63", "63、经典笑话：我的艳"));
		MENU_List.add(new KeyValue("64", "64、网吧里的一段QQ对"));
		MENU_List.add(new KeyValue("65", "65、我的电脑笑料"));
		MENU_List.add(new KeyValue("66", "66、帮她掀上去了...."));
		MENU_List.add(new KeyValue("67", "67、以免破财"));
		MENU_List.add(new KeyValue("68", "68、MM哄男友的招数"));
		MENU_List.add(new KeyValue("69", "69、90后女生的经典语"));
		MENU_List.add(new KeyValue("70", "70、男女对白"));
		MENU_List.add(new KeyValue("71", "71、七号电池"));
		MENU_List.add(new KeyValue("72", "72、男生有女友后的20"));
		MENU_List.add(new KeyValue("73", "73、女朋友要和我分手"));
		MENU_List.add(new KeyValue("74", "74、最勾魂的校园短信"));
		MENU_List.add(new KeyValue("75", "75、我 注 意 你 已 经"));
		MENU_List.add(new KeyValue("76", "76、女人眼中的男人?"));
		MENU_List.add(new KeyValue("77", "77、汽球怎么包装得这"));
		MENU_List.add(new KeyValue("78", "78、医生愕然？"));
		MENU_List.add(new KeyValue("79", "79、我有“胸”"));
		MENU_List.add(new KeyValue("80", "80、翻墙和翻枕头"));
		MENU_List.add(new KeyValue("81", "81、一个女生醉酒后不"));
		MENU_List.add(new KeyValue("82", "82、KFC里男孩表白"));
		MENU_List.add(new KeyValue("83", "83、女"));
		MENU_List.add(new KeyValue("84", "84、搭讪-很有缘"));
		MENU_List.add(new KeyValue("85", "85、一句话求婚语录…"));
		MENU_List.add(new KeyValue("86", "86、有种棒冰叫帅哥的"));
		MENU_List.add(new KeyValue("87", "87、搞笑80后征婚启事"));
		MENU_List.add(new KeyValue("88", "88、性骚扰的问题"));
		MENU_List.add(new KeyValue("89", "89、你要是借我10块"));
		MENU_List.add(new KeyValue("90", "90、售后服务"));
		MENU_List.add(new KeyValue("91", "91、指腹为婚"));
		MENU_List.add(new KeyValue("92", "92、女人和冰箱"));
		MENU_List.add(new KeyValue("93", "93、这玩意也有野生的"));
		MENU_List.add(new KeyValue("94", "94、成为将来主流的看"));
		MENU_List.add(new KeyValue("95", "95、都市男女的30声幽"));
		MENU_List.add(new KeyValue("96", "96、都市男女的30声幽"));
		MENU_List.add(new KeyValue("97", "97、都市男女的30声幽"));
		MENU_List.add(new KeyValue("98", "98、12个一句话，雷死"));
		MENU_List.add(new KeyValue("99", "99、女球盲答记者问"));
		MENU_List.add(new KeyValue("100", "100、近视眼"));
		MENU_List.add(new KeyValue("101", "101、热心的尴尬"));
		MENU_List.add(new KeyValue("102", "102、搭讪"));
		MENU_List.add(new KeyValue("103", "103、引线这么短"));
		MENU_List.add(new KeyValue("104", "104、最短“笑话”"));
		MENU_List.add(new KeyValue("105", "105、分工不同"));
		MENU_List.add(new KeyValue("106", "106、99%的人看完后当场"));
		MENU_List.add(new KeyValue("107", "107、她的胸到底有多大"));
		MENU_List.add(new KeyValue("108", "108、触电达高潮"));


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