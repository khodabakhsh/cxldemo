package com.cxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cxl.travel.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {

	private ArrayAdapter<String> provinceAdapter;
	private ArrayAdapter<KeyValue> cityAdapter;
	private ArrayAdapter<KeyValue> travelAdapter;
	Button queryButton;
	Spinner province;
	Spinner city;
	Spinner travel;
	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 60;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分
	private static final String[] Province_Array = { "香港", "澳门", "台湾" };

	public static Map<String, List<KeyValue>> Province_City_Map = new HashMap<String, List<KeyValue>>();
	public static Map<String, List<KeyValue>> City_Tracvel_Map = new HashMap<String, List<KeyValue>>();

	final Handler mHandler = new Handler();

	// 创建一个线程
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			if (pointsTextView != null) {
				if (update_text) {
					pointsTextView.setText(displayText);
					update_text = false;
				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		AppConnect.getInstance(this).getPoints(this);
		super.onResume();
	}

	/**
	 * AppConnect.getPoints()方法的实现，必须实现
	 * 
	 * @param currencyName
	 *            虚拟货币名称.
	 * @param pointTotal
	 *            虚拟货币余额.
	 */
	public void getUpdatePoints(String currencyName, int pointTotal) {

		currentPointTotal = pointTotal;
		if (currentPointTotal >= requirePoint) {
			hasEnoughRequrePoint = true;
		}
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
		mHandler.post(mUpdateResults);
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		currentPointTotal = 0;
		update_text = true;
		displayText = error;
		mHandler.post(mUpdateResults);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		province = (Spinner) findViewById(R.id.province);

		provinceAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, Province_Array);
		// 设置下拉列表的风格
		provinceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		province.setAdapter(provinceAdapter);
		province.setSelection(0);
		province.setPrompt("请选择香港、澳门或台湾");
		province.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedItem = (String) province.getSelectedItem();
				cityAdapter = new ArrayAdapter<KeyValue>(MainActivity.this,
						android.R.layout.simple_spinner_item, Province_City_Map
								.get(selectedItem));
				city.setAdapter(cityAdapter);
				city.setSelection(0);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		city = (Spinner) findViewById(R.id.city);
		city.setPrompt("请选择城市(台湾)");
		cityAdapter = new ArrayAdapter<KeyValue>(this,
				android.R.layout.simple_spinner_item,
				Province_City_Map.get(province.getSelectedItem()));
		// 设置下拉列表的风格
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		city.setAdapter(cityAdapter);
		city.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				KeyValue selectedItem = (KeyValue) city.getSelectedItem();
				travelAdapter = new ArrayAdapter<KeyValue>(MainActivity.this,
						android.R.layout.simple_spinner_item, City_Tracvel_Map
								.get(selectedItem.getKey()));
				travel.setAdapter(travelAdapter);
				travel.setSelection(0);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		city.setSelection(0);

		travel = (Spinner) findViewById(R.id.travel);
		travel.setPrompt("请选择旅游景点");
		travelAdapter = new ArrayAdapter<KeyValue>(this,
				android.R.layout.simple_spinner_item,
				City_Tracvel_Map.get(((KeyValue) city.getSelectedItem())
						.getKey()));
		travel.setAdapter(travelAdapter);
		// 设置下拉列表的风格
		travelAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);

		queryButton = (Button) findViewById(R.id.queryButton);
		queryButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				KeyValue selectedItem = (KeyValue) travel.getSelectedItem();

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", selectedItem.getKey());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}

	static class KeyValue {
		private String value;
		private String key;

		public KeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	static {
		List<KeyValue> list = new ArrayList<KeyValue>();
		list.add(new KeyValue("173", "香港"));
		Province_City_Map.put("香港", list);

		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("174", "澳门"));
		Province_City_Map.put("澳门", list);

		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("24", "高雄"));
		list.add(new KeyValue("25", "花莲"));
		list.add(new KeyValue("26", "基隆"));
		list.add(new KeyValue("27", "嘉义"));
		list.add(new KeyValue("28", "台北"));
		list.add(new KeyValue("29", "台东"));
		list.add(new KeyValue("30", "台南"));
		list.add(new KeyValue("31", "台中"));
		Province_City_Map.put("台湾", list);

		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("657", "台南旅游"));
		list.add(new KeyValue("658", "延平郡王祠"));
		list.add(new KeyValue("659", "台南孔庙"));
		list.add(new KeyValue("660", "小琉球"));
		list.add(new KeyValue("661", "鹅銮鼻公园"));
		list.add(new KeyValue("662", "港仔大沙漠"));
		list.add(new KeyValue("663", "关山"));
		list.add(new KeyValue("664", "国堡花园"));
		list.add(new KeyValue("665", "恒春出火"));
		list.add(new KeyValue("666", "恒春古镇"));
		list.add(new KeyValue("667", "垦丁公园"));
		list.add(new KeyValue("668", "石门古战场"));
		list.add(new KeyValue("669", "盐水蜂炮"));
		list.add(new KeyValue("670", "宝光寺"));
		list.add(new KeyValue("671", "走马濑农场"));
		list.add(new KeyValue("672", "悟智乐园"));
		list.add(new KeyValue("673", "大岗山温泉"));
		list.add(new KeyValue("674", "左镇化石博物馆"));
		list.add(new KeyValue("675", "关仔岭温泉"));
		list.add(new KeyValue("676", "安平古堡"));
		City_Tracvel_Map.put("30", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("5226", "澳门旅游"));
		list.add(new KeyValue("5227", "妈祖阁"));
		list.add(new KeyValue("5228", "大三巴牌坊"));
		list.add(new KeyValue("5229", "澳门赛马会"));
		list.add(new KeyValue("5230", "市政厅"));
		list.add(new KeyValue("5231", "玫瑰圣母堂"));
		list.add(new KeyValue("5232", "澳门国父纪念馆"));
		list.add(new KeyValue("5233", "大炮台"));
		list.add(new KeyValue("5234", "澳门海事博物馆"));
		list.add(new KeyValue("5235", "酒类博物馆"));
		list.add(new KeyValue("5236", "邮政局博物馆"));
		list.add(new KeyValue("5237", "白鸽巢公园"));
		list.add(new KeyValue("5238", "二龙喉公园"));
		list.add(new KeyValue("5239", "卢廉若公园"));
		list.add(new KeyValue("5240", "螺丝山公园"));
		list.add(new KeyValue("5241", "南湾公园"));
		list.add(new KeyValue("5242", "松山炮台"));
		list.add(new KeyValue("5243", "妈阁炮台"));
		list.add(new KeyValue("5244", "望厦炮台"));
		list.add(new KeyValue("5245", "嘉谟公园"));
		list.add(new KeyValue("5246", "普济禅院"));
		list.add(new KeyValue("5247", "圣安多尼堂"));
		list.add(new KeyValue("5248", "圣奥斯定堂"));
		list.add(new KeyValue("5249", "望厦圣方济各小堂"));
		list.add(new KeyValue("5250", "圣老楞佐堂"));
		list.add(new KeyValue("5251", "望德圣母堂"));
		list.add(new KeyValue("5252", "嘉谟圣母教堂"));
		list.add(new KeyValue("5253", "圣方济各圣堂"));
		list.add(new KeyValue("5254", "九澳七苦圣母小堂"));
		list.add(new KeyValue("5255", "住宅式博物馆"));
		list.add(new KeyValue("5256", "莲峰庙"));
		list.add(new KeyValue("5257", "莲溪庙"));
		list.add(new KeyValue("5258", "包公庙"));
		list.add(new KeyValue("5259", "菩提园"));
		list.add(new KeyValue("5260", "观音岩"));
		list.add(new KeyValue("5261", "谭公庙"));
		list.add(new KeyValue("5262", "大堂"));
		list.add(new KeyValue("5263", "圣母雪地殿教堂"));
		list.add(new KeyValue("5264", "圣雅各伯小堂"));
		list.add(new KeyValue("5265", "花地玛圣母堂"));
		list.add(new KeyValue("5266", "主教山小堂"));
		list.add(new KeyValue("5267", "竹湾海滩"));
		list.add(new KeyValue("5268", "黑沙海滩"));
		list.add(new KeyValue("5269", "关帝庙"));
		list.add(new KeyValue("5270", "基督教徒公墓"));
		list.add(new KeyValue("5271", "澳门博物馆"));
		list.add(new KeyValue("5272", "澳门文化中心"));
		list.add(new KeyValue("5273", "天主教艺术博物馆"));
		list.add(new KeyValue("5274", "得胜公园"));
		list.add(new KeyValue("5275", "风顺堂"));
		list.add(new KeyValue("5276", "格兰披士大赛车博物馆"));
		list.add(new KeyValue("5277", "观音像"));
		list.add(new KeyValue("5278", "花王堂"));
		list.add(new KeyValue("5279", "华士古达嘉马花园"));
		list.add(new KeyValue("5280", "加思栏炮台"));
		list.add(new KeyValue("5281", "康公庙"));
		list.add(new KeyValue("5282", "马礼逊教堂"));
		list.add(new KeyValue("5283", "南湾人工湖"));
		list.add(new KeyValue("5284", "葡京大酒店"));
		list.add(new KeyValue("5285", "融和门"));
		list.add(new KeyValue("5286", "圣宝禄教堂"));
		list.add(new KeyValue("5287", "圣弥额尔小堂"));
		list.add(new KeyValue("5288", "圣若瑟修院圣堂"));
		list.add(new KeyValue("5289", "松山"));
		list.add(new KeyValue("5290", "松山灯塔"));
		list.add(new KeyValue("5291", "孙中山市政纪念公园"));
		list.add(new KeyValue("5292", "香山公园"));
		list.add(new KeyValue("5293", "中央图书馆"));
		list.add(new KeyValue("5294", "澳凼大桥"));
		list.add(new KeyValue("5295", "大型浮雕及�望台"));
		list.add(new KeyValue("5296", "镜海长虹"));
		list.add(new KeyValue("5297", "路环圣方济各小堂"));
		list.add(new KeyValue("5298", "石排湾公园"));
		list.add(new KeyValue("5299", "澳门运动场"));
		list.add(new KeyValue("5300", "龙头环"));
		list.add(new KeyValue("5301", "澳门大学"));
		list.add(new KeyValue("5302", "�仔岛"));
		list.add(new KeyValue("5303", "三巴圣迹"));
		list.add(new KeyValue("5304", "渔人码头"));
		City_Tracvel_Map.put("174", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("677", "台中旅游"));
		list.add(new KeyValue("678", "郑成功庙"));
		list.add(new KeyValue("679", "南瑶宫"));
		list.add(new KeyValue("680", "益源大厝"));
		list.add(new KeyValue("681", "鹿港镇"));
		list.add(new KeyValue("682", "八卦山"));
		list.add(new KeyValue("683", "华山游憩区"));
		list.add(new KeyValue("684", "草 岭"));
		list.add(new KeyValue("685", "剑湖山世界"));
		list.add(new KeyValue("686", "樟湖风景区"));
		list.add(new KeyValue("687", "石壁森林游乐区"));
		list.add(new KeyValue("688", "日月潭"));
		list.add(new KeyValue("689", "惠荪农场"));
		list.add(new KeyValue("690", "泰雅渡假村"));
		list.add(new KeyValue("691", "合欢山"));
		list.add(new KeyValue("692", "玉山公园"));
		list.add(new KeyValue("693", "德基水库"));
		list.add(new KeyValue("694", "大雪山森林游乐区"));
		list.add(new KeyValue("695", "雪山坑"));
		list.add(new KeyValue("696", "龙谷天然乐园"));
		list.add(new KeyValue("697", "五福临门神木"));
		list.add(new KeyValue("698", "东势林场"));
		City_Tracvel_Map.put("31", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("5178", "香港旅游"));
		list.add(new KeyValue("5179", "浅水湾"));
		list.add(new KeyValue("5180", "太平山顶"));
		list.add(new KeyValue("5181", "香港海洋公园"));
		list.add(new KeyValue("5182", "西贡"));
		list.add(new KeyValue("5183", "香港仔"));
		list.add(new KeyValue("5184", "香港会议展览中心"));
		list.add(new KeyValue("5185", "维多利亚公园"));
		list.add(new KeyValue("5186", "跑马地赛马场"));
		list.add(new KeyValue("5187", "茶具博物馆"));
		list.add(new KeyValue("5188", "警队博物馆"));
		list.add(new KeyValue("5189", "香港公园"));
		list.add(new KeyValue("5190", "赤柱"));
		list.add(new KeyValue("5191", "文武庙"));
		list.add(new KeyValue("5192", "圣约翰大教堂"));
		list.add(new KeyValue("5193", "香港动植物公园"));
		list.add(new KeyValue("5194", "皇后广场"));
		list.add(new KeyValue("5195", "石阶街"));
		list.add(new KeyValue("5196", "青马大桥"));
		list.add(new KeyValue("5197", "黄大仙祠"));
		list.add(new KeyValue("5198", "万佛寺"));
		list.add(new KeyValue("5199", "圆玄学园"));
		list.add(new KeyValue("5200", "李郑屋古墓"));
		list.add(new KeyValue("5201", "香港历史博物馆"));
		list.add(new KeyValue("5202", "大澳"));
		list.add(new KeyValue("5203", "流浮山"));
		list.add(new KeyValue("5204", "米埔野生动物保护区"));
		list.add(new KeyValue("5205", "九龙清真寺"));
		list.add(new KeyValue("5206", "尖东海滨"));
		list.add(new KeyValue("5207", "三栋屋博物馆"));
		list.add(new KeyValue("5208", "吉庆围"));
		list.add(new KeyValue("5209", "新田大夫第"));
		list.add(new KeyValue("5210", "香港艺术馆"));
		list.add(new KeyValue("5211", "香港科学馆"));
		list.add(new KeyValue("5212", "太空馆"));
		list.add(new KeyValue("5213", "九龙城寨公园"));
		list.add(new KeyValue("5214", "九龙公园"));
		list.add(new KeyValue("5215", "油麻地/旺角"));
		list.add(new KeyValue("5216", "清水湾"));
		list.add(new KeyValue("5217", "香港天文台"));
		list.add(new KeyValue("5218", "钟楼"));
		list.add(new KeyValue("5219", "梅窝"));
		list.add(new KeyValue("5220", "东涌"));
		list.add(new KeyValue("5221", "洲际酒店观景台"));
		list.add(new KeyValue("5222", "榕树湾"));
		list.add(new KeyValue("5223", "索罟湾"));
		list.add(new KeyValue("5224", "宝莲寺"));
		list.add(new KeyValue("5225", "香港迪斯尼乐园"));
		City_Tracvel_Map.put("173", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("525", "高雄旅游"));
		list.add(new KeyValue("526", "爱河"));
		list.add(new KeyValue("527", "春秋阁"));
		list.add(new KeyValue("528", "高雄都会公园"));
		list.add(new KeyValue("529", "高雄孔庙"));
		list.add(new KeyValue("530", "莲池潭"));
		list.add(new KeyValue("531", "三凤宫"));
		list.add(new KeyValue("532", "西子湾"));
		list.add(new KeyValue("533", "少年溪风景区"));
		list.add(new KeyValue("534", "阿公店水库"));
		list.add(new KeyValue("535", "不老温泉"));
		list.add(new KeyValue("536", "澄清湖"));
		list.add(new KeyValue("537", "旗津半岛"));
		list.add(new KeyValue("538", "六龟彩蝶谷"));
		City_Tracvel_Map.put("24", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("539", "花莲旅游"));
		list.add(new KeyValue("540", "燕子口"));
		list.add(new KeyValue("541", "舞鹤"));
		list.add(new KeyValue("542", "天祥"));
		list.add(new KeyValue("543", "太鲁阁"));
		list.add(new KeyValue("544", "清水断崖"));
		list.add(new KeyValue("545", "鲤鱼潭"));
		list.add(new KeyValue("546", "长春祠"));
		list.add(new KeyValue("547", "布洛湾游憩区"));
		City_Tracvel_Map.put("25", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("548", "基隆旅游"));
		list.add(new KeyValue("549", "西台古堡"));
		list.add(new KeyValue("550", "通梁古榕"));
		list.add(new KeyValue("551", "鲸鱼洞"));
		list.add(new KeyValue("552", "吉贝屿"));
		list.add(new KeyValue("553", "观音亭"));
		list.add(new KeyValue("554", "莒光楼"));
		list.add(new KeyValue("555", "风柜涛声"));
		list.add(new KeyValue("556", "龟 岛"));
		list.add(new KeyValue("557", "北海坑道"));
		list.add(new KeyValue("558", "烈女义坑"));
		list.add(new KeyValue("559", "太武山"));
		list.add(new KeyValue("560", "太 湖"));
		list.add(new KeyValue("561", "水头村"));
		list.add(new KeyValue("562", "榕园"));
		list.add(new KeyValue("563", "李光前将军庙"));
		list.add(new KeyValue("564", "金门“国家”公园"));
		list.add(new KeyValue("565", "古宁头战史馆"));
		list.add(new KeyValue("566", "邱良功母节孝坊"));
		list.add(new KeyValue("567", "暖东峡谷"));
		list.add(new KeyValue("568", "和平岛"));
		list.add(new KeyValue("569", "海门天险"));
		list.add(new KeyValue("570", "中正公园"));
		list.add(new KeyValue("571", "赏鸟区"));
		list.add(new KeyValue("572", "泰安瀑布"));
		list.add(new KeyValue("573", "仙岩洞"));
		list.add(new KeyValue("574", "庙口小吃"));
		City_Tracvel_Map.put("26", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("575", "嘉义旅游"));
		list.add(new KeyValue("576", "曾文水库"));
		list.add(new KeyValue("577", "吴凤庙"));
		list.add(new KeyValue("578", "梅山公园"));
		list.add(new KeyValue("579", "丰山风景区"));
		list.add(new KeyValue("580", "阿里山森林游乐区"));
		list.add(new KeyValue("581", "竹崎公园"));
		list.add(new KeyValue("582", "兰潭"));
		list.add(new KeyValue("583", "瑞峰风景区"));
		City_Tracvel_Map.put("27", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("584", "台北旅游"));
		list.add(new KeyValue("585", "国父纪念馆"));
		list.add(new KeyValue("586", "北投温泉"));
		list.add(new KeyValue("587", "中正纪念堂"));
		list.add(new KeyValue("588", "台北故宫博物院"));
		list.add(new KeyValue("589", "国立历史博物馆"));
		list.add(new KeyValue("590", "台北海洋生活馆"));
		list.add(new KeyValue("591", "台北市立美术馆"));
		list.add(new KeyValue("592", "台北观音山"));
		list.add(new KeyValue("593", "台北木栅动物园"));
		list.add(new KeyValue("594", "台北龙山寺"));
		list.add(new KeyValue("595", "碧潭风景区"));
		list.add(new KeyValue("596", "迪化街"));
		list.add(new KeyValue("597", "台北孔庙"));
		list.add(new KeyValue("598", "赤嵌楼"));
		list.add(new KeyValue("599", "阳明山"));
		list.add(new KeyValue("600", "行天宫"));
		list.add(new KeyValue("601", "大安森林公园"));
		list.add(new KeyValue("602", "大湖公园"));
		list.add(new KeyValue("603", "安平古堡"));
		list.add(new KeyValue("604", "祀典武庙"));
		list.add(new KeyValue("605", "秋茂园"));
		list.add(new KeyValue("606", "通霄海水浴场"));
		list.add(new KeyValue("607", "明德水库"));
		list.add(new KeyValue("608", "马那邦山"));
		list.add(new KeyValue("609", "火炎山"));
		list.add(new KeyValue("610", "飞牛牧场"));
		list.add(new KeyValue("611", "大湖"));
		list.add(new KeyValue("612", "狮子博物馆"));
		list.add(new KeyValue("613", "冬山河亲水乐园"));
		list.add(new KeyValue("614", "福山植物园"));
		list.add(new KeyValue("615", "翠峰湖"));
		list.add(new KeyValue("616", "大溪渔港"));
		list.add(new KeyValue("617", "北关海滨公园"));
		list.add(new KeyValue("618", "龟山岛"));
		list.add(new KeyValue("619", "豆腐岬"));
		list.add(new KeyValue("620", "礁溪公园"));
		list.add(new KeyValue("621", "礁溪温泉"));
		list.add(new KeyValue("622", "金鸟海族乐园"));
		list.add(new KeyValue("623", "五峰渡假村"));
		list.add(new KeyValue("624", "万瑞森林游乐区"));
		list.add(new KeyValue("625", "慈湖"));
		list.add(new KeyValue("626", "虎头山公园"));
		list.add(new KeyValue("627", "东眼山森林游乐区"));
		list.add(new KeyValue("628", "大溪中正公园"));
		list.add(new KeyValue("629", "角板山公园"));
		list.add(new KeyValue("630", "龙 溪"));
		list.add(new KeyValue("631", "龙潭湖"));
		list.add(new KeyValue("632", "石门水库"));
		list.add(new KeyValue("633", "小乌来瀑布"));
		list.add(new KeyValue("634", "风力活动公园"));
		list.add(new KeyValue("635", "青草湖"));
		list.add(new KeyValue("636", "清华大学"));
		list.add(new KeyValue("637", "香山母圣宫"));
		list.add(new KeyValue("638", "迎曦门"));
		list.add(new KeyValue("639", "渔港环保公园"));
		list.add(new KeyValue("640", "关西田园"));
		list.add(new KeyValue("641", "二寮千年樟树"));
		list.add(new KeyValue("642", "大隘村矮人祭"));
		list.add(new KeyValue("643", "上新花园"));
		list.add(new KeyValue("644", "大圣渡假游乐世界"));
		list.add(new KeyValue("645", "大埔水库"));
		list.add(new KeyValue("646", "童话世界"));
		list.add(new KeyValue("647", "台北袖珍博物馆"));
		list.add(new KeyValue("648", "坪林茶业博物馆"));
		City_Tracvel_Map.put("28", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("649", "台东旅游"));
		list.add(new KeyValue("650", "知本森林区"));
		list.add(new KeyValue("651", "绿岛"));
		list.add(new KeyValue("652", "红叶温泉区"));
		list.add(new KeyValue("653", "三仙台"));
		list.add(new KeyValue("654", "初鹿牧场"));
		list.add(new KeyValue("655", "阿美族民俗中心"));
		list.add(new KeyValue("656", "雾鹿温泉"));
		City_Tracvel_Map.put("29", list);

	}
}