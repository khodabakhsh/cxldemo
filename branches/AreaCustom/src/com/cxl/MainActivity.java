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

import com.cxl.areacustom.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {

	private ArrayAdapter<String> areaAdapter;
	private ArrayAdapter<KeyValue> customAdapter;
	Button queryButton;
	Spinner area;
	Spinner custom;
	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 60;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分
	public static Map<String, String> ConstellationNameIdMap = new HashMap<String, String>();

	private static final String[] Area_Array = { "天津", "重庆", "上海", "河北", "山西", "山东", "河南", "陕西", "甘肃", "青海", "江苏",
			"浙江", "安徽", "湖北", "湖南", "云南", "贵州", "四川", "广东", "福建", "海南", "江西", "辽宁", "吉林", "黑龙江", "广西", "新疆", "西藏",
			"内蒙古", "宁夏", "台湾", "香港", "澳门" };

	public static Map<String, List<KeyValue>> area_custom_map = new HashMap<String, List<KeyValue>>();

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
		area = (Spinner) findViewById(R.id.area);

		areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Area_Array);
		//设置下拉列表的风格  
		areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		area.setAdapter(areaAdapter);
		area.setSelection(0);
		area.setPrompt("请选择地区");
		area.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Object selectedId = area.getSelectedItem();
				customAdapter = new ArrayAdapter<KeyValue>(MainActivity.this, android.R.layout.simple_spinner_item,
						area_custom_map.get(selectedId));
				custom.setAdapter(customAdapter);
				custom.setSelection(0);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		custom = (Spinner) findViewById(R.id.custom);
		custom.setPrompt("请选择风俗");
		customAdapter = new ArrayAdapter<KeyValue>(this, android.R.layout.simple_spinner_item, area_custom_map.get(area
				.getSelectedItem()));
		//设置下拉列表的风格  
		customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

		queryButton = (Button) findViewById(R.id.queryButton);
		queryButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				KeyValue selectedItem = (KeyValue) custom.getSelectedItem();

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", selectedItem.getKey());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		// queryButton.setBackgroundDrawable(d)

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

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
			return  value;
		}
	}

	static {
		List<KeyValue> list = null;
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16083", "撒面粉喝盐水――塔吉克族婚俗"));
		list.add(new KeyValue("16084", "闹洞房解衣扣――锡伯族的婚俗"));
		list.add(new KeyValue("16085", "结婚要五个阶段――维吾尔族婚俗"));
		list.add(new KeyValue("16086", "新疆人的见面礼"));
		list.add(new KeyValue("16087", "新疆的当地民俗"));
		list.add(new KeyValue("16088", "古尔邦节"));
		list.add(new KeyValue("16089", "哈萨克族传统节日"));
		list.add(new KeyValue("16090", "哈萨克族婚俗"));
		list.add(new KeyValue("16091", "新疆人的待客礼"));
		list.add(new KeyValue("16092", "维吾尔人待客有规矩"));
		list.add(new KeyValue("16093", "图瓦人节日"));
		list.add(new KeyValue("16094", "塔吉克族的剪发礼"));
		list.add(new KeyValue("16095", "塔吉克族油烛节"));
		list.add(new KeyValue("16096", "新疆人的出生礼"));
		list.add(new KeyValue("16097", "新疆的命名礼"));
		area_custom_map.put("新疆", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17069", "鬼城节日习俗"));
		list.add(new KeyValue("17070", "摆手舞"));
		list.add(new KeyValue("17071", "重庆的民俗、文化"));
		list.add(new KeyValue("17072", "重庆巴南少女裸浴风俗"));
		list.add(new KeyValue("17073", "重庆土家族节日"));
		list.add(new KeyValue("17074", "哭嫁"));
		list.add(new KeyValue("17075", "重庆民族风情"));
		list.add(new KeyValue("17076", "重庆节日风俗"));
		list.add(new KeyValue("17077", "山城棒棒军"));
		list.add(new KeyValue("17078", "重庆秀山花灯"));
		list.add(new KeyValue("17079", "重庆的赶年的习俗"));
		list.add(new KeyValue("17080", "重庆铜梁龙舞"));
		list.add(new KeyValue("17081", "重庆的特殊习俗"));
		list.add(new KeyValue("17082", "重庆民俗川剧"));
		list.add(new KeyValue("17083", "重庆八大特色民间艺术"));
		area_custom_map.put("重庆", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17109", "佛山生育禁忌"));
		list.add(new KeyValue("17110", "广州节日--荷花节"));
		list.add(new KeyValue("17111", "佛山禁忌俗"));
		list.add(new KeyValue("17112", "惠州春节舞龙、舞狮"));
		list.add(new KeyValue("17113", "早茶"));
		list.add(new KeyValue("17114", "龙川杂技"));
		list.add(new KeyValue("17115", "深圳节日欢海中"));
		list.add(new KeyValue("17116", "揭阳风俗民情"));
		list.add(new KeyValue("17117", "阳江山歌节"));
		list.add(new KeyValue("17118", "广州过年习俗"));
		list.add(new KeyValue("17119", "白城市汽车客运时刻表"));
		list.add(new KeyValue("17120", "广东梅州风俗文化"));
		list.add(new KeyValue("17121", "广州建筑特色"));
		list.add(new KeyValue("17122", "栽种四方竹的习俗"));
		list.add(new KeyValue("17123", "广州传统文化"));
		area_custom_map.put("广东", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16346", "三潭印月的传说"));
		list.add(new KeyValue("16347", "绍兴社戏"));
		list.add(new KeyValue("16348", "杭州的民情风俗"));
		list.add(new KeyValue("16349", "杨梅节"));
		list.add(new KeyValue("16350", "杭州旅游最新地方节日大全"));
		list.add(new KeyValue("16351", "轧蚕花"));
		list.add(new KeyValue("16352", "象山海鲜节"));
		list.add(new KeyValue("16353", "平湖西瓜灯节"));
		list.add(new KeyValue("16354", "放风筝"));
		list.add(new KeyValue("16355", "观湖节"));
		list.add(new KeyValue("16356", "中秋节"));
		list.add(new KeyValue("16357", "嘉兴粽子节"));
		list.add(new KeyValue("16358", "浙江柯岩风景区民俗活动简介"));
		list.add(new KeyValue("16359", "温州鼓词"));
		list.add(new KeyValue("16360", "梁祝婚俗节"));
		area_custom_map.put("浙江", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("15915", "天津杨柳青的传说"));
		list.add(new KeyValue("15916", "天津为何又叫算盘城"));
		list.add(new KeyValue("15917", "杨柳青年画――娃娃年画"));
		list.add(new KeyValue("15918", "天津民风三变"));
		list.add(new KeyValue("15919", "盐商文化与天津民俗"));
		list.add(new KeyValue("15920", "天津的岁时节俗"));
		list.add(new KeyValue("15921", "天津人过年礼品"));
		list.add(new KeyValue("15922", "打囤与填仓"));
		list.add(new KeyValue("15923", "天津独有的曲种-天津时调"));
		list.add(new KeyValue("15924", "天津的民俗事象-妈妈例儿"));
		list.add(new KeyValue("15925", "天津传统风俗：打囤与填仓"));
		area_custom_map.put("天津", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17669", "澳门名称的演变"));
		list.add(new KeyValue("17670", "居住在澳门的华人节日习俗"));
		area_custom_map.put("澳门", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17194", "壮族三月三"));
		list.add(new KeyValue("17195", "广西风雨桥的神话故事"));
		list.add(new KeyValue("17196", "漓江九十九道湾"));
		list.add(new KeyValue("17197", "瑶族赶鸟节"));
		list.add(new KeyValue("17198", "壮族牛魂节"));
		list.add(new KeyValue("17199", "隔街相望找情人――壮族婚俗"));
		list.add(new KeyValue("17200", "壮族三月三歌墟"));
		list.add(new KeyValue("17201", "踢沙折枝试情意――京族婚俗"));
		list.add(new KeyValue("17202", "串情人咬手背――瑶族婚俗"));
		list.add(new KeyValue("17203", "三月三歌墟"));
		list.add(new KeyValue("17204", "壮族婚俗"));
		list.add(new KeyValue("17205", "牛魂节"));
		list.add(new KeyValue("17206", "壮族民俗概述"));
		list.add(new KeyValue("17207", "舞麒麟"));
		list.add(new KeyValue("17208", "民族风情活动"));
		area_custom_map.put("广西", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16515", "驯鹿和神像――鄂温克族婚俗"));
		list.add(new KeyValue("16516", "蒙古族节日"));
		list.add(new KeyValue("16517", "蒙古族概况和旅游禁忌"));
		list.add(new KeyValue("16518", "达斡尔族食俗礼仪"));
		list.add(new KeyValue("16519", "鄂温克族的火神节"));
		list.add(new KeyValue("16520", "骑马迎亲与抱木枕头――蒙古族婚俗"));
		list.add(new KeyValue("16521", "鄂尔多斯的民俗"));
		list.add(new KeyValue("16522", "内蒙古民族礼仪"));
		list.add(new KeyValue("16523", "走进内蒙阿拉善草原 走进蒙古长调"));
		list.add(new KeyValue("16524", "鄂温克民俗"));
		list.add(new KeyValue("16525", "蒙古族献礼"));
		list.add(new KeyValue("16526", "蒙古族禁忌"));
		list.add(new KeyValue("16527", "蒙古族民歌：呼麦"));
		list.add(new KeyValue("16528", "内蒙古旅游――曲艺介绍"));
		list.add(new KeyValue("16529", "鄂温克族生活习俗"));
		area_custom_map.put("内蒙古", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17576", "回族粮食节"));
		list.add(new KeyValue("17577", "回族婚礼"));
		list.add(new KeyValue("17578", "宁夏回族开斋节"));
		list.add(new KeyValue("17579", "回族葬礼"));
		list.add(new KeyValue("17580", "宁夏回族的衣着"));
		list.add(new KeyValue("17581", "回族的生活习惯"));
		list.add(new KeyValue("17582", "回族的宗教"));
		list.add(new KeyValue("17583", "回族宗教信仰"));
		list.add(new KeyValue("17584", "宁夏旅游――歌舞介绍"));
		list.add(new KeyValue("17585", "回族的礼节"));
		list.add(new KeyValue("17586", "整洁清净的回民房舍"));
		list.add(new KeyValue("17587", "宁夏回族的饮食"));
		list.add(new KeyValue("17588", "回族的饮食习惯"));
		list.add(new KeyValue("17589", "回族的主要节日"));
		list.add(new KeyValue("17590", "回族的服饰和音乐"));
		area_custom_map.put("宁夏", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17372", "“口吃粽子撑竹排”端午佳节记铜鼓客家习俗"));
		list.add(new KeyValue("17373", "井冈山土籍人传统婚俗：招郎"));
		list.add(new KeyValue("17374", "江西地方小戏的总称:采茶戏"));
		list.add(new KeyValue("17375", "客籍民间居民:出嫁当天不吃饭"));
		list.add(new KeyValue("17376", "南昌人的钓鱼习俗:四季得鲜"));
		list.add(new KeyValue("17377", "庐山热闹的风俗民情"));
		list.add(new KeyValue("17378", "南昌人在茶馆聚会的习惯"));
		list.add(new KeyValue("17379", "井冈山土、客籍居民过年风俗"));
		list.add(new KeyValue("17380", "七巧节习俗"));
		list.add(new KeyValue("17381", "赣西北祭祖傩由来及其仪式活动"));
		list.add(new KeyValue("17382", "江西民族风情"));
		list.add(new KeyValue("17383", "新余"));
		list.add(new KeyValue("17384", "舞春牛"));
		list.add(new KeyValue("17385", "崇仁跳傩《跳八仙》简介"));
		list.add(new KeyValue("17386", "庙 会"));
		area_custom_map.put("江西", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17526", "台湾成年仪礼"));
		list.add(new KeyValue("17527", "台湾居住民俗"));
		list.add(new KeyValue("17528", "台湾民间婚礼"));
		list.add(new KeyValue("17529", "高山族的婚姻仪礼"));
		list.add(new KeyValue("17530", "台湾特殊祭仪"));
		list.add(new KeyValue("17531", "台湾拔毛风俗"));
		list.add(new KeyValue("17532", "台湾祖灵祭"));
		list.add(new KeyValue("17533", "台湾汉人的婚姻仪礼"));
		list.add(new KeyValue("17534", "台湾民间赠礼禁忌"));
		list.add(new KeyValue("17535", "台湾汉人的丧葬仪礼"));
		list.add(new KeyValue("17536", "台湾打猎祭"));
		list.add(new KeyValue("17537", "台湾诞生仪礼"));
		list.add(new KeyValue("17538", "台湾神明祭祀"));
		list.add(new KeyValue("17539", "台湾河川祭"));
		list.add(new KeyValue("17540", "高山族的丧葬仪礼"));
		area_custom_map.put("台湾", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16453", "黄山的来历"));
		list.add(new KeyValue("16454", "九华山得名的由来"));
		list.add(new KeyValue("16455", "徽州宅居习俗"));
		list.add(new KeyValue("16456", "淮南豆腐文化节"));
		list.add(new KeyValue("16457", "豆腐宴灯会"));
		list.add(new KeyValue("16458", "徽州人的节令习俗"));
		list.add(new KeyValue("16459", "徽州古民居民俗文化"));
		list.add(new KeyValue("16460", "徽州古牌坊民俗文化"));
		list.add(new KeyValue("16461", "胭脂井的传说"));
		list.add(new KeyValue("16462", "安徽习俗--归根与怀乡"));
		list.add(new KeyValue("16463", "建房与上梁习俗"));
		list.add(new KeyValue("16464", "滁州地方戏曲"));
		list.add(new KeyValue("16465", "安徽生产风俗"));
		list.add(new KeyValue("16466", "安徽习俗--踏青"));
		list.add(new KeyValue("16467", "徽州服饰"));
		area_custom_map.put("安徽", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16945", "苗族杀鱼节"));
		list.add(new KeyValue("16946", "贵州民族节日"));
		list.add(new KeyValue("16947", "苗族爬山节"));
		list.add(new KeyValue("16948", "盘郎和过门要换三双鞋――仡佬族婚俗"));
		list.add(new KeyValue("16949", "布依族的五色花饭"));
		list.add(new KeyValue("16950", "侗族林王节"));
		list.add(new KeyValue("16951", "苗家龙船节"));
		list.add(new KeyValue("16952", "仡佬族的吃虫节"));
		list.add(new KeyValue("16953", "布依族端午节"));
		list.add(new KeyValue("16954", "苗族除恶节的由来"));
		list.add(new KeyValue("16955", "苗族踩花山的来历"));
		list.add(new KeyValue("16956", "苗族盛会-----吃牯脏"));
		list.add(new KeyValue("16957", "踩脚后跟试情意和火把迎亲――侗族婚俗"));
		list.add(new KeyValue("16958", "贵州苗族节日"));
		list.add(new KeyValue("16959", "贵州布依族风俗"));
		area_custom_map.put("贵州", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16045", "陕西十大怪（即关中十大怪）"));
		list.add(new KeyValue("16046", "安塞腰鼓"));
		list.add(new KeyValue("16047", "社火"));
		list.add(new KeyValue("16048", "陕西旅游――民歌、乐器介绍"));
		list.add(new KeyValue("16049", "陕西婚俗介绍"));
		list.add(new KeyValue("16050", "陕西风俗一览：陕西十大怪"));
		list.add(new KeyValue("16051", "陕西民间过寿习俗"));
		list.add(new KeyValue("16052", "西安兵马俑石榴节"));
		list.add(new KeyValue("16053", "安塞剪纸"));
		list.add(new KeyValue("16054", "民间艺术：陕西榆林小曲"));
		list.add(new KeyValue("16055", "秦腔"));
		list.add(new KeyValue("16056", "面花"));
		list.add(new KeyValue("16057", "黄土坡上:历数黄河沿岸的剧种和民歌"));
		list.add(new KeyValue("16058", "秦腔艺术"));
		list.add(new KeyValue("16059", "木马勺脸谱"));
		area_custom_map.put("陕西", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17545", "满族概况和旅游禁忌"));
		list.add(new KeyValue("17546", "满婚中男方礼仪食俗"));
		list.add(new KeyValue("17547", "秤杆揭盖头――满族婚俗"));
		list.add(new KeyValue("17548", "阜新民俗文化大观"));
		list.add(new KeyValue("17549", "独特婚俗"));
		list.add(new KeyValue("17550", "满族礼俗"));
		list.add(new KeyValue("17551", "锦州回族风俗"));
		list.add(new KeyValue("17552", "大连民俗风情"));
		list.add(new KeyValue("17553", "辽宁省的曲艺"));
		list.add(new KeyValue("17554", "豪放的沈阳大秧歌"));
		list.add(new KeyValue("17555", "沈阳市民俗风情"));
		list.add(new KeyValue("17556", "独特的辽宁婚俗"));
		list.add(new KeyValue("17557", "独特的满族婚俗"));
		list.add(new KeyValue("17558", "锦州满族风俗"));
		list.add(new KeyValue("17559", "锦州蒙古族风俗"));
		area_custom_map.put("辽宁", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("15944", "歇龙石与五台山"));
		list.add(new KeyValue("15945", "自古恒山佳话多"));
		list.add(new KeyValue("15946", "晋剧"));
		list.add(new KeyValue("15947", "山西风俗简介"));
		list.add(new KeyValue("15948", "忻州婚俗"));
		list.add(new KeyValue("15949", "独特的山西风俗-怀仁旺火"));
		list.add(new KeyValue("15950", "山西的七夕节风俗"));
		list.add(new KeyValue("15951", "佛教开光"));
		list.add(new KeyValue("15952", "山西特有的习俗：平定元宵塔火"));
		list.add(new KeyValue("15953", "山西民间艺术：社火脸谱在葫芦上复苏"));
		list.add(new KeyValue("15954", "山西境内民间祭灶节"));
		list.add(new KeyValue("15955", "太原社火"));
		list.add(new KeyValue("15956", "运城婚俗：抹黑与抹红"));
		list.add(new KeyValue("15957", "关公文化节"));
		list.add(new KeyValue("15958", "大同旺火"));
		area_custom_map.put("山西", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16104", "青海日月山和倒淌河"));
		list.add(new KeyValue("16105", "青海民俗风情"));
		list.add(new KeyValue("16106", "热贡藏乡"));
		list.add(new KeyValue("16107", "玉树赛马节：十万人的集会"));
		list.add(new KeyValue("16108", "蒙古族节庆风俗"));
		list.add(new KeyValue("16109", "回族风俗"));
		list.add(new KeyValue("16110", "青海当地法规和禁忌"));
		list.add(new KeyValue("16111", "玉树藏族的风俗禁忌"));
		list.add(new KeyValue("16112", "撒拉族民俗"));
		list.add(new KeyValue("16113", "土族波波会"));
		list.add(new KeyValue("16114", "藏族民俗"));
		list.add(new KeyValue("16115", "回族的节日和禁忌"));
		list.add(new KeyValue("16116", "撒拉族的节日和禁忌"));
		list.add(new KeyValue("16117", "土族的节日与禁忌"));
		list.add(new KeyValue("16118", "藏族的节日和禁忌"));
		area_custom_map.put("青海", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17368", "香港旅游的“禁忌”"));
		list.add(new KeyValue("17369", "香港的盛事与节庆"));
		list.add(new KeyValue("17370", "香港民间习俗斩鸡头"));
		list.add(new KeyValue("17371", "香港中秋舞火龙"));
		list.add(new KeyValue("17662", "怎样注意赴香港旅游的“禁忌”"));
		list.add(new KeyValue("17663", "斩鸡头�香港民间习俗"));
		list.add(new KeyValue("17664", "九龙地名的由来"));
		list.add(new KeyValue("17665", "香港地名的由来"));
		list.add(new KeyValue("17666", "香港的盛事与节庆"));
		list.add(new KeyValue("17667", "香港的宗教信仰"));
		list.add(new KeyValue("17668", "香港中秋舞火龙"));
		area_custom_map.put("香港", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16985", "彝族火把节[农历六月二十四]"));
		list.add(new KeyValue("16986", "彝族火把节的传说"));
		list.add(new KeyValue("16987", "乐山大佛的来历"));
		list.add(new KeyValue("16988", "彝族插花节"));
		list.add(new KeyValue("16989", "泼水迎亲和摔跤婚礼――彝族婚俗"));
		list.add(new KeyValue("16990", "藏式建筑"));
		list.add(new KeyValue("16991", "藏羌风俗禁忌"));
		list.add(new KeyValue("16992", "藏羌歌舞"));
		list.add(new KeyValue("16993", "九寨人情"));
		list.add(new KeyValue("16994", "四川民居"));
		list.add(new KeyValue("16995", "川牌"));
		list.add(new KeyValue("16996", "藏羌节日"));
		list.add(new KeyValue("16997", "康巴民居管窥"));
		list.add(new KeyValue("16998", "祭祀大典-都江堰放水节"));
		list.add(new KeyValue("16999", "四川各族服饰"));
		area_custom_map.put("四川", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16320", "苏州狮子林为何叫狮子林"));
		list.add(new KeyValue("16321", "云龙山庙会"));
		list.add(new KeyValue("16322", "苏州特色节日"));
		list.add(new KeyValue("16323", "常州风土人情"));
		list.add(new KeyValue("16324", "连云港婚嫁习俗"));
		list.add(new KeyValue("16325", "吃“乌饭”"));
		list.add(new KeyValue("16326", "南通岁时节令"));
		list.add(new KeyValue("16327", "扬州的风情民俗"));
		list.add(new KeyValue("16328", "常州节庆"));
		list.add(new KeyValue("16329", "连云港生育习俗"));
		list.add(new KeyValue("16330", "立夏尝三新"));
		list.add(new KeyValue("16331", "京口旗营的婚丧习俗"));
		list.add(new KeyValue("16332", "苏州节令民俗"));
		list.add(new KeyValue("16333", "盐城民俗摸秋"));
		list.add(new KeyValue("16334", "盐城的民风民俗"));
		area_custom_map.put("江苏", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("15926", "鸡冠山下金鸡叫"));
		list.add(new KeyValue("15927", "邢台旱船和太平车"));
		list.add(new KeyValue("15928", "地方特色剧种：评剧"));
		list.add(new KeyValue("15929", "历史悠久的河北梆子"));
		list.add(new KeyValue("15930", "石家庄居住习俗"));
		list.add(new KeyValue("15931", "元霄节荡秋千"));
		list.add(new KeyValue("15932", "邢台龙灯和狮子舞"));
		list.add(new KeyValue("15933", "二月二乞巧节"));
		list.add(new KeyValue("15934", "河北传统节日介绍"));
		list.add(new KeyValue("15935", "五月十三送羊"));
		list.add(new KeyValue("15936", "西河大鼓"));
		list.add(new KeyValue("15937", "承德地区民俗民情"));
		list.add(new KeyValue("15938", "冀南扇鼓舞"));
		list.add(new KeyValue("15939", "六月初六姑姑节"));
		list.add(new KeyValue("15940", "张家口民风民俗"));
		area_custom_map.put("河北", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16122", "少数民族春节风俗"));
		list.add(new KeyValue("16123", "藏族沐浴节"));
		list.add(new KeyValue("16124", "赠烟盒、撒大米茶叶――德昂族婚俗"));
		list.add(new KeyValue("16125", "西藏旅游禁忌"));
		list.add(new KeyValue("16126", "藏族主要节日"));
		list.add(new KeyValue("16127", "藏族献哈达的礼仪"));
		list.add(new KeyValue("16128", "西藏的下毒习俗"));
		list.add(new KeyValue("16129", "藏族饮食习俗"));
		list.add(new KeyValue("16130", "西藏雪顿节"));
		list.add(new KeyValue("16131", "西藏三湖的传说"));
		list.add(new KeyValue("16132", "舅舅试新郎――门巴族婚俗"));
		list.add(new KeyValue("16133", "情卦和抢帽子――藏族婚俗"));
		list.add(new KeyValue("16134", "拉萨风俗特色/主要节庆活动介绍"));
		list.add(new KeyValue("16135", "藏风藏俗"));
		list.add(new KeyValue("16136", "康区藏族的一妻多夫家庭"));
		area_custom_map.put("西藏", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17243", "闽族崇蛇习俗"));
		list.add(new KeyValue("17244", "厦门风俗"));
		list.add(new KeyValue("17245", "畲族三月三"));
		list.add(new KeyValue("17246", "五指山的传说"));
		list.add(new KeyValue("17247", "酒坛峰、九曲溪和八仙的故事"));
		list.add(new KeyValue("17248", "福建平和县的三平村崇蛇习俗"));
		list.add(new KeyValue("17249", "惠女的奇特服饰"));
		list.add(new KeyValue("17250", "福建忌俗和彩俗"));
		list.add(new KeyValue("17251", "忌鸟粪落在人头上"));
		list.add(new KeyValue("17252", "泉州提线木偶戏"));
		list.add(new KeyValue("17253", "客家山歌"));
		list.add(new KeyValue("17254", "沙县肩膀戏"));
		list.add(new KeyValue("17255", "元宵观灯"));
		list.add(new KeyValue("17256", "独具特色的福州评话"));
		list.add(new KeyValue("17257", "中秋博状元"));
		area_custom_map.put("福建", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17601", "天池与七仙女"));
		list.add(new KeyValue("17602", "朝鲜族风俗"));
		list.add(new KeyValue("17603", "朝鲜族服饰"));
		list.add(new KeyValue("17604", "吉林的风俗禁忌"));
		list.add(new KeyValue("17605", "东北民俗：十七八的大姑娘叨烟袋"));
		list.add(new KeyValue("17606", "朝鲜族传统节日"));
		list.add(new KeyValue("17607", "60岁老人的生日宴席：花甲宴"));
		list.add(new KeyValue("17608", "37个少数民族的长春"));
		list.add(new KeyValue("17609", "吉林满族民间文学"));
		list.add(new KeyValue("17610", "长白山冬俗"));
		list.add(new KeyValue("17611", "东北大秧歌"));
		list.add(new KeyValue("17612", "那达慕：蒙古族的传统盛会"));
		list.add(new KeyValue("17613", "吉林“三灯”"));
		list.add(new KeyValue("17614", "满族民族工艺"));
		list.add(new KeyValue("17615", "刀舞"));
		area_custom_map.put("吉林", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16284", "上海集体婚礼"));
		list.add(new KeyValue("16285", "上海人的风俗习惯"));
		list.add(new KeyValue("16286", "上海习俗条礼"));
		list.add(new KeyValue("16287", "上海民俗"));
		list.add(new KeyValue("16288", "松江华亭老街民风"));
		list.add(new KeyValue("16289", "上海民俗节庆"));
		list.add(new KeyValue("16290", "上海之根-松江的习俗"));
		list.add(new KeyValue("16291", "朱家角风情"));
		list.add(new KeyValue("16292", "上海生活习俗"));
		list.add(new KeyValue("16293", "松江风土民情之社会风尚"));
		list.add(new KeyValue("16294", "上海消暑习俗"));
		list.add(new KeyValue("16295", "上海过年习俗"));
		list.add(new KeyValue("16296", "元宵灯节"));
		list.add(new KeyValue("16297", "上海消暑方法"));
		list.add(new KeyValue("16298", "松江照壁"));
		area_custom_map.put("上海", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16542", "吃粽子和赛龙舟"));
		list.add(new KeyValue("16543", "东湖梅花节"));
		list.add(new KeyValue("16544", "长阳巴山舞"));
		list.add(new KeyValue("16545", "楚剧"));
		list.add(new KeyValue("16546", "土家节日"));
		list.add(new KeyValue("16547", "汉川皮影戏"));
		list.add(new KeyValue("16548", "神农溪.土家民俗风情"));
		list.add(new KeyValue("16549", "武当国际旅游节"));
		list.add(new KeyValue("16550", "襄樊民俗 人生礼仪习俗"));
		list.add(new KeyValue("16551", "武汉夏夜街头的竹床阵"));
		list.add(new KeyValue("16552", "西兰卡普"));
		list.add(new KeyValue("16553", "士家族宗教信仰"));
		list.add(new KeyValue("16554", "道教服饰"));
		list.add(new KeyValue("16555", "英山的婚嫁习惯"));
		list.add(new KeyValue("16556", "丧俗"));
		area_custom_map.put("湖北", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16758", "傣族泼水节"));
		list.add(new KeyValue("16759", "傣族泼水节的来历"));
		list.add(new KeyValue("16760", "偷女婿和偷新娘――布朗族婚俗"));
		list.add(new KeyValue("16761", "饮茶定亲――独龙族婚俗"));
		list.add(new KeyValue("16762", "踩棚正喜回门――白族婚俗"));
		list.add(new KeyValue("16763", "白族饮食点滴"));
		list.add(new KeyValue("16764", "白族祭鸟节"));
		list.add(new KeyValue("16765", "摩梭人的猪膘肉"));
		list.add(new KeyValue("16766", "纳西族火把节"));
		list.add(new KeyValue("16767", "撑伞与新郎"));
		list.add(new KeyValue("16768", "白族饮食习俗"));
		list.add(new KeyValue("16769", "白族姑娘头戴凤凰帽的传说"));
		list.add(new KeyValue("16770", "哈尼族新米先喂狗的由来"));
		list.add(new KeyValue("16771", "卖鸡肉找对象――傣族婚俗"));
		list.add(new KeyValue("16772", "耳朵里插鲜花――基诺族婚俗"));
		area_custom_map.put("云南", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17345", "黎族三月三"));
		list.add(new KeyValue("17346", "黎族船形屋和竹竿舞"));
		list.add(new KeyValue("17347", "黎族跳竹竿"));
		list.add(new KeyValue("17348", "好看的黎苗婚礼"));
		list.add(new KeyValue("17349", "黎族的“三月三”"));
		list.add(new KeyValue("17350", "苗族风情"));
		list.add(new KeyValue("17351", "黎家婚礼喜“逗娘”"));
		list.add(new KeyValue("17352", "苗家姐妹节"));
		list.add(new KeyValue("17353", "正月十五换花节"));
		list.add(new KeyValue("17354", "苗族习俗"));
		list.add(new KeyValue("17355", "“清明节”与“菠菠稞”"));
		list.add(new KeyValue("17356", "龙舟节"));
		list.add(new KeyValue("17357", "三亚黎族风情"));
		list.add(new KeyValue("17358", "独具特色的民族风情"));
		list.add(new KeyValue("17359", "海南黎族风情"));
		area_custom_map.put("海南", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16067", "三天不吃男家的饭――保安族婚俗"));
		list.add(new KeyValue("16068", "马踏新房射新娘――裕固族婚俗"));
		list.add(new KeyValue("16069", "裕固族饮食习俗"));
		list.add(new KeyValue("16070", "甘肃民俗简介"));
		list.add(new KeyValue("16071", "高芯子、车社火和马社火"));
		list.add(new KeyValue("16072", "窑洞民居"));
		list.add(new KeyValue("16073", "甘肃环县道情皮影"));
		list.add(new KeyValue("16074", "裕固族文化和民俗风情"));
		list.add(new KeyValue("16075", "金锁、银锁、拴锁锁"));
		list.add(new KeyValue("16076", "甘肃岁时习俗"));
		list.add(new KeyValue("16077", "张掖婚俗"));
		list.add(new KeyValue("16078", "历史悠久的庆阳剪纸"));
		list.add(new KeyValue("16079", "甘肃地方戏曲"));
		list.add(new KeyValue("16080", "朝山会与打笳板"));
		list.add(new KeyValue("16081", "毛兰木大法会"));
		area_custom_map.put("甘肃", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16666", "草标密码和揉耳朵表情意――苗族婚俗"));
		list.add(new KeyValue("16667", "张家界和汉将张良"));
		list.add(new KeyValue("16668", "白族连理会的故事"));
		list.add(new KeyValue("16669", "端午习俗"));
		list.add(new KeyValue("16670", "江华瑶族风俗习惯"));
		list.add(new KeyValue("16671", "七月中元节习俗"));
		list.add(new KeyValue("16672", "长沙花鼓戏"));
		list.add(new KeyValue("16673", "凤凰苗族婚俗"));
		list.add(new KeyValue("16674", "邵阳回族习俗"));
		list.add(new KeyValue("16675", "湘西土家风情"));
		list.add(new KeyValue("16676", "神农的苗族节俗文化"));
		list.add(new KeyValue("16677", "瑶族婚俗趣无穷"));
		list.add(new KeyValue("16678", "湘西民俗风情"));
		list.add(new KeyValue("16679", "湘西凤凰土家婚俗"));
		list.add(new KeyValue("16680", "张家界的民风民俗"));
		area_custom_map.put("湖南", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("16043", "河南民俗"));
		list.add(new KeyValue("16044", "开封斗鸡"));
		list.add(new KeyValue("17671", "地方戏曲"));
		list.add(new KeyValue("17672", "曲艺之乡"));
		list.add(new KeyValue("17673", "曲剧"));
		list.add(new KeyValue("17674", "气功吐火"));
		list.add(new KeyValue("17675", "倒立民乐"));
		list.add(new KeyValue("17676", "钻刀山、火海"));
		list.add(new KeyValue("17677", "脑弹子"));
		list.add(new KeyValue("17678", "鞭技"));
		list.add(new KeyValue("17679", "东汉政治家陈蕃"));
		list.add(new KeyValue("17680", "盘古开天造人的传说"));
		list.add(new KeyValue("17681", "河南民间剪纸"));
		list.add(new KeyValue("17682", "周口方言汇萃"));
		list.add(new KeyValue("17683", "剪纸"));
		area_custom_map.put("河南", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("15992", "泰山顶上为何没有树"));
		list.add(new KeyValue("15993", "山东人的喝酒规矩"));
		list.add(new KeyValue("15994", "济南千佛山庙会"));
		list.add(new KeyValue("15995", "山东民间剪纸"));
		list.add(new KeyValue("15996", "山东蓬莱民俗--贴艾虎"));
		list.add(new KeyValue("15997", "胶东风俗--剪纸"));
		list.add(new KeyValue("15998", "吕剧"));
		list.add(new KeyValue("15999", "山东大鼓：几乎失传的瑰宝"));
		list.add(new KeyValue("16000", "山东民俗文化"));
		list.add(new KeyValue("16001", "聊城礼仪习俗"));
		list.add(new KeyValue("16002", "民俗风情：七月三十放河灯"));
		list.add(new KeyValue("16003", "七月三十放河灯"));
		list.add(new KeyValue("16004", "济南药市会"));
		list.add(new KeyValue("16005", "舞蹈中的俏花―山东秧歌"));
		list.add(new KeyValue("16006", "山东即墨渔民“祭海”风俗"));
		area_custom_map.put("山东", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("17626", "赫哲族的生食习俗"));
		list.add(new KeyValue("17627", "松花江的传说"));
		list.add(new KeyValue("17628", "婚礼上吃猪头猪尾巴――赫哲族婚俗"));
		list.add(new KeyValue("17629", "朝鲜族民俗风情"));
		list.add(new KeyValue("17630", "伊春鄂伦春族"));
		list.add(new KeyValue("17631", "黑龙江民族风情：“玩”在朝鲜族"));
		list.add(new KeyValue("17632", "北极村夏至节"));
		list.add(new KeyValue("17633", "哈尔滨民俗介绍"));
		list.add(new KeyValue("17634", "伊春朝鲜族"));
		list.add(new KeyValue("17635", "伊春满族"));
		list.add(new KeyValue("17636", "黑龙江民族风情：赫哲人的抓鼓"));
		list.add(new KeyValue("17637", "冰河上赶着三套车"));
		list.add(new KeyValue("17638", "黑龙江民族风情：鄂温克族的篝火舞"));
		list.add(new KeyValue("17639", "黑龙江民族风情：柯尔克孜“追姑娘”"));
		list.add(new KeyValue("17640", "满洲里名称的由来"));
		area_custom_map.put("黑龙江", list);

	}
}