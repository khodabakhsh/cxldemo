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

import com.cxl.xinlijianya.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("0", "0、什么是心理压力"));
		MENU_List.add(new KeyValue("1", "1、远离职场过劳死要学会10招"));
		MENU_List.add(new KeyValue("2", "2、5种方法做好自我心理调适"));
		MENU_List.add(new KeyValue("3", "3、压力男士减压法大集合"));
		MENU_List.add(new KeyValue("4", "4、职场白领轻松减压四大良方"));
		MENU_List.add(new KeyValue("5", "5、教你告别工作低潮的职场10招"));
		MENU_List.add(new KeyValue("6", "6、加班族必看11个减压小妙招"));
		MENU_List.add(new KeyValue("7", "7、白领周一压力大 嚼口香糖可减压"));
		MENU_List.add(new KeyValue("8", "8、4个心理策略让你享受工作压力"));
		MENU_List.add(new KeyValue("9", "9、对抗职场压力的心理安全指标"));
		MENU_List.add(new KeyValue("10", "10、职场人减压 多学学“彩虹族”"));
		MENU_List.add(new KeyValue("11", "11、赶走白领不良情绪的5个秘诀"));
		MENU_List.add(new KeyValue("12", "12、都市白领心理减压20个秘诀"));
		MENU_List.add(new KeyValue("13", "13、白领心理减压的八个潜规则"));
		MENU_List.add(new KeyValue("14", "14、学会职场宣泄 还你每天好心情"));
		MENU_List.add(new KeyValue("15", "15、缓解压力新秘诀：慢节奏生活"));
		MENU_List.add(new KeyValue("16", "16、白领解乏放松的方法"));
		MENU_List.add(new KeyValue("17", "17、现代白骨精10大时尚解压法"));
		MENU_List.add(new KeyValue("18", "18、N大措施助你缓解职场压力"));
		MENU_List.add(new KeyValue("19", "19、四个良方减轻心理压力"));
		MENU_List.add(new KeyValue("20", "20、多吃酸少吃盐利于减压"));
		MENU_List.add(new KeyValue("21", "21、“网络拖延症”为何惹上职场人"));
		MENU_List.add(new KeyValue("22", "22、十招助白领消除职场心理压力"));
		MENU_List.add(new KeyValue("23", "23、告别工作低潮十法则"));
		MENU_List.add(new KeyValue("24", "24、减压是一种浪漫心思"));
		MENU_List.add(new KeyValue("25", "25、白领要减压试试催眠法"));
		MENU_List.add(new KeyValue("26", "26、职场减压新尝试：调整生物节律"));
		MENU_List.add(new KeyValue("27", "27、美国心理专家教你减轻心理压力"));
		MENU_List.add(new KeyValue("28", "28、有效进行压力管理的八种策略"));
		MENU_List.add(new KeyValue("29", "29、20种心理减压法助你轻松抗压"));
		MENU_List.add(new KeyValue("30", "30、减压大比拼：哪种心理方更实用"));
		MENU_List.add(new KeyValue("31", "31、快乐的来源"));
		MENU_List.add(new KeyValue("32", "32、快乐获得的方法"));
		MENU_List.add(new KeyValue("33", "33、保持快乐最好的方法"));
		MENU_List.add(new KeyValue("34", "34、与快乐有关的名言"));
		MENU_List.add(new KeyValue("35", "35、缺乏幸福感的原因"));
		MENU_List.add(new KeyValue("36", "36、幸福的公式"));
		MENU_List.add(new KeyValue("37", "37、中国幸福学――幸福定律"));
		MENU_List.add(new KeyValue("38", "38、抑郁症简介"));
		MENU_List.add(new KeyValue("39", "39、抑郁症发病率及危害"));
		MENU_List.add(new KeyValue("40", "40、抑郁症的自我测试和断定方法"));
		MENU_List.add(new KeyValue("41", "41、抑郁症吃哪些对身体好?"));
		MENU_List.add(new KeyValue("42", "42、抑郁症不要吃哪些食物?"));
		MENU_List.add(new KeyValue("43", "43、抑郁症治疗常识"));
		MENU_List.add(new KeyValue("44", "44、焦虑症简介"));
		MENU_List.add(new KeyValue("45", "45、焦虑症症状有哪些？"));
		MENU_List.add(new KeyValue("46", "46、焦虑症吃什么好？"));
		MENU_List.add(new KeyValue("47", "47、焦虑症的自我治疗"));
		MENU_List.add(new KeyValue("48", "48、什么是强迫症"));
		MENU_List.add(new KeyValue("49", "49、强迫症的自我诊断"));
		MENU_List.add(new KeyValue("50", "50、强迫症的自救九法"));
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