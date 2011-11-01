package fetch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 香港、澳门、台湾 旅游景点
 * 
 */
public class TravelUtil {

	public static String getCityListUrl = "http://jingdian.supfree.net/";// 省份-城市列表
	public static String getTravelListUrl = "http://jingdian.supfree.net/south.asp";// 城市-景点列表
	public static String getDetailUrl = "http://jingdian.supfree.net/north.asp";// 景点详细页
	public static String Referer = "http://fengsu.supfree.net/";
	public static String Host = "fengsu.supfree.net";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";
	public static String GBK = "GBK";
	public static Map<String, List<KeyValue>> Province_City_Map = new HashMap<String, List<KeyValue>>();
	public static Map<String, List<KeyValue>> City_Tracvel_Map = new HashMap<String, List<KeyValue>>();

	static {
		// 取香港、澳门、台湾的好了，全部取太多了，收录不了
		List<KeyValue> list = new ArrayList<KeyValue>();
		list.add(new KeyValue("173", "香港"));
		Province_City_Map.put("香港", list);

		list.add(new KeyValue("174", "澳门"));
		Province_City_Map.put("澳门", list);

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

		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("172", "北京"));
		// Province_City_Map.put("北京", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("177", "重庆"));
		// Province_City_Map.put("重庆", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("72", "喀什"));
		// list.add(new KeyValue("73", "吐鲁番"));
		// list.add(new KeyValue("74", "乌鲁木齐"));
		// list.add(new KeyValue("75", "伊犁"));
		// list.add(new KeyValue("76", "哈密"));
		// list.add(new KeyValue("77", "阿克苏"));
		// list.add(new KeyValue("78", "巴音郭楞"));
		// list.add(new KeyValue("79", "昌吉"));
		// list.add(new KeyValue("80", "博尔塔拉"));
		// list.add(new KeyValue("81", "克拉玛依"));
		// list.add(new KeyValue("82", "克孜勒苏"));
		// list.add(new KeyValue("83", "石河子"));
		// Province_City_Map.put("新疆", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("224", "惠州"));
		// list.add(new KeyValue("225", "江门"));
		// list.add(new KeyValue("226", "揭阳"));
		// list.add(new KeyValue("227", "梅州"));
		// list.add(new KeyValue("228", "清远"));
		// list.add(new KeyValue("229", "汕头"));
		// list.add(new KeyValue("297", "潮州"));
		// list.add(new KeyValue("298", "东莞"));
		// list.add(new KeyValue("299", "佛山"));
		// list.add(new KeyValue("300", "广州"));
		// list.add(new KeyValue("301", "河源"));
		// list.add(new KeyValue("302", "汕尾"));
		// list.add(new KeyValue("303", "韶关"));
		// list.add(new KeyValue("304", "深圳"));
		// list.add(new KeyValue("305", "阳江"));
		// list.add(new KeyValue("306", "云浮"));
		// list.add(new KeyValue("307", "湛江"));
		// list.add(new KeyValue("308", "肇庆"));
		// list.add(new KeyValue("309", "中山"));
		// list.add(new KeyValue("310", "珠海"));
		// Province_City_Map.put("广东", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("218", "宁波"));
		// list.add(new KeyValue("219", "绍兴"));
		// list.add(new KeyValue("220", "台州"));
		// list.add(new KeyValue("221", "温州"));
		// list.add(new KeyValue("222", "舟山"));
		// list.add(new KeyValue("223", "衢州"));
		// list.add(new KeyValue("255", "杭州"));
		// list.add(new KeyValue("256", "湖州"));
		// list.add(new KeyValue("257", "嘉兴"));
		// list.add(new KeyValue("258", "金华"));
		// list.add(new KeyValue("259", "丽水"));
		// Province_City_Map.put("浙江", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("176", "天津"));
		// Province_City_Map.put("天津", list);
		// list = new ArrayList<KeyValue>();
		//
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("92", "崇左"));
		// list.add(new KeyValue("93", "桂林"));
		// list.add(new KeyValue("94", "贺州"));
		// list.add(new KeyValue("95", "来宾"));
		// list.add(new KeyValue("96", "柳州"));
		// list.add(new KeyValue("97", "南宁"));
		// list.add(new KeyValue("98", "钦州"));
		// list.add(new KeyValue("99", "梧州"));
		// list.add(new KeyValue("100", "玉林"));
		// list.add(new KeyValue("145", "百色"));
		// list.add(new KeyValue("146", "北海"));
		// Province_City_Map.put("广西", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("12", "阿拉善盟"));
		// list.add(new KeyValue("13", "巴彦淖尔"));
		// list.add(new KeyValue("14", "包头"));
		// list.add(new KeyValue("15", "赤峰"));
		// list.add(new KeyValue("16", "鄂尔多斯"));
		// list.add(new KeyValue("17", "呼和浩特"));
		// list.add(new KeyValue("18", "呼伦贝尔"));
		// list.add(new KeyValue("19", "通辽"));
		// list.add(new KeyValue("20", "乌海"));
		// list.add(new KeyValue("21", "乌兰察布"));
		// list.add(new KeyValue("22", "锡林郭勒盟"));
		// list.add(new KeyValue("23", "兴安盟"));
		// Province_City_Map.put("内蒙古", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("131", "固原"));
		// list.add(new KeyValue("132", "银川"));
		// Province_City_Map.put("宁夏", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("288", "抚州"));
		// list.add(new KeyValue("289", "赣州"));
		// list.add(new KeyValue("290", "吉安"));
		// list.add(new KeyValue("291", "景德镇"));
		// list.add(new KeyValue("292", "九江"));
		// list.add(new KeyValue("293", "南昌"));
		// list.add(new KeyValue("294", "上饶"));
		// list.add(new KeyValue("295", "宜春"));
		// list.add(new KeyValue("296", "鹰潭"));
		// Province_City_Map.put("江西", list);
		//
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("62", "毕节"));
		// list.add(new KeyValue("63", "贵阳"));
		// list.add(new KeyValue("64", "六盘水"));
		// list.add(new KeyValue("65", "黔东南"));
		// list.add(new KeyValue("66", "黔南"));
		// list.add(new KeyValue("67", "黔西南"));
		// list.add(new KeyValue("68", "铜仁"));
		// list.add(new KeyValue("69", "遵义"));
		// list.add(new KeyValue("91", "安顺"));
		// Province_City_Map.put("贵州", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("271", "黄山"));
		// list.add(new KeyValue("272", "安庆"));
		// list.add(new KeyValue("273", "蚌埠"));
		// list.add(new KeyValue("274", "巢湖"));
		// list.add(new KeyValue("275", "池州"));
		// list.add(new KeyValue("276", "滁州"));
		// list.add(new KeyValue("277", "阜阳"));
		// list.add(new KeyValue("278", "合肥"));
		// list.add(new KeyValue("279", "淮北"));
		// list.add(new KeyValue("280", "淮南"));
		// list.add(new KeyValue("281", "六安"));
		// list.add(new KeyValue("282", "马鞍山"));
		// list.add(new KeyValue("283", "宿州"));
		// list.add(new KeyValue("284", "铜陵"));
		// list.add(new KeyValue("285", "芜湖"));
		// list.add(new KeyValue("286", "宣城"));
		// list.add(new KeyValue("287", "亳州"));
		// Province_City_Map.put("安徽", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("53", "西安"));
		// list.add(new KeyValue("54", "安康"));
		// list.add(new KeyValue("55", "宝鸡"));
		// list.add(new KeyValue("57", "汉中"));
		// list.add(new KeyValue("58", "渭南"));
		// list.add(new KeyValue("59", "咸阳"));
		// list.add(new KeyValue("60", "延安"));
		// list.add(new KeyValue("61", "榆林"));
		// Province_City_Map.put("陕西", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("207", "鞍山"));
		// list.add(new KeyValue("208", "本溪"));
		// list.add(new KeyValue("209", "朝阳"));
		// list.add(new KeyValue("210", "大连"));
		// list.add(new KeyValue("211", "丹东"));
		// list.add(new KeyValue("212", "抚顺"));
		// list.add(new KeyValue("213", "葫芦岛"));
		// list.add(new KeyValue("214", "锦州"));
		// list.add(new KeyValue("215", "辽阳"));
		// list.add(new KeyValue("216", "沈阳"));
		// list.add(new KeyValue("217", "营口"));
		// list.add(new KeyValue("326", "阜新"));
		// list.add(new KeyValue("327", "铁岭"));
		// Province_City_Map.put("辽宁", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("260", "长治"));
		// list.add(new KeyValue("261", "大同"));
		// list.add(new KeyValue("262", "晋城"));
		// list.add(new KeyValue("263", "晋中"));
		// list.add(new KeyValue("264", "临汾"));
		// list.add(new KeyValue("265", "吕梁"));
		// list.add(new KeyValue("266", "朔州"));
		// list.add(new KeyValue("267", "太原"));
		// list.add(new KeyValue("268", "忻州"));
		// list.add(new KeyValue("269", "阳泉"));
		// list.add(new KeyValue("270", "运城"));
		// Province_City_Map.put("山西", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("311", "果洛"));
		// list.add(new KeyValue("312", "海北"));
		// list.add(new KeyValue("313", "海东"));
		// list.add(new KeyValue("314", "海西"));
		// list.add(new KeyValue("315", "黄南"));
		// list.add(new KeyValue("316", "西宁"));
		// list.add(new KeyValue("317", "玉树"));
		// Province_City_Map.put("青海", list);
		// list = new ArrayList<KeyValue>();
		//
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("133", "成都"));
		// list.add(new KeyValue("134", "阿坝"));
		// list.add(new KeyValue("135", "达州"));
		// list.add(new KeyValue("136", "德阳"));
		// list.add(new KeyValue("137", "甘孜"));
		// list.add(new KeyValue("138", "广安"));
		// list.add(new KeyValue("139", "广元"));
		// list.add(new KeyValue("140", "乐山"));
		// list.add(new KeyValue("141", "凉山"));
		// list.add(new KeyValue("142", "雅安"));
		// list.add(new KeyValue("143", "宜宾"));
		// list.add(new KeyValue("144", "自贡"));
		// list.add(new KeyValue("147", "眉山"));
		// list.add(new KeyValue("148", "绵阳"));
		// list.add(new KeyValue("149", "南充"));
		// list.add(new KeyValue("150", "内江"));
		// list.add(new KeyValue("151", "攀枝花"));
		// list.add(new KeyValue("152", "遂宁"));
		// Province_City_Map.put("四川", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("230", "常州"));
		// list.add(new KeyValue("231", "淮安"));
		// list.add(new KeyValue("232", "连云港"));
		// list.add(new KeyValue("233", "南京"));
		// list.add(new KeyValue("234", "南通"));
		// list.add(new KeyValue("235", "苏州"));
		// list.add(new KeyValue("236", "宿迁"));
		// list.add(new KeyValue("237", "泰州"));
		// list.add(new KeyValue("238", "无锡"));
		// list.add(new KeyValue("239", "徐州"));
		// list.add(new KeyValue("240", "盐城"));
		// list.add(new KeyValue("241", "扬州"));
		// list.add(new KeyValue("242", "镇江"));
		// Province_City_Map.put("江苏", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("178", "秦皇岛"));
		// list.add(new KeyValue("179", "承德"));
		// list.add(new KeyValue("180", "保定"));
		// list.add(new KeyValue("181", "沧州"));
		// list.add(new KeyValue("182", "邯郸"));
		// list.add(new KeyValue("183", "衡水"));
		// list.add(new KeyValue("184", "廊坊"));
		// list.add(new KeyValue("185", "石家庄"));
		// list.add(new KeyValue("186", "唐山"));
		// list.add(new KeyValue("187", "邢台"));
		// list.add(new KeyValue("188", "张家口"));
		// Province_City_Map.put("河北", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("108", "阿里"));
		// list.add(new KeyValue("109", "昌都"));
		// list.add(new KeyValue("110", "拉萨"));
		// list.add(new KeyValue("111", "林芝"));
		// list.add(new KeyValue("112", "那曲"));
		// list.add(new KeyValue("113", "日喀则"));
		// list.add(new KeyValue("114", "山南"));
		// Province_City_Map.put("西藏", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("44", "福州"));
		// list.add(new KeyValue("45", "厦门"));
		// list.add(new KeyValue("46", "龙岩"));
		// list.add(new KeyValue("47", "南平"));
		// list.add(new KeyValue("48", "宁德"));
		// list.add(new KeyValue("49", "莆田"));
		// list.add(new KeyValue("50", "泉州"));
		// list.add(new KeyValue("51", "三明"));
		// list.add(new KeyValue("52", "漳州"));
		// Province_City_Map.put("福建", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("200", "白城"));
		// list.add(new KeyValue("201", "白山"));
		// list.add(new KeyValue("202", "长春"));
		// list.add(new KeyValue("203", "四平"));
		// list.add(new KeyValue("204", "松原"));
		// list.add(new KeyValue("205", "通化"));
		// list.add(new KeyValue("206", "延边"));
		// list.add(new KeyValue("324", "吉林"));
		// Province_City_Map.put("吉林", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("32", "西双版纳"));
		// list.add(new KeyValue("33", "保山"));
		// list.add(new KeyValue("34", "楚雄"));
		// list.add(new KeyValue("35", "德宏"));
		// list.add(new KeyValue("36", "迪庆"));
		// list.add(new KeyValue("37", "红河"));
		// list.add(new KeyValue("38", "临沧"));
		// list.add(new KeyValue("39", "怒江"));
		// list.add(new KeyValue("40", "曲靖"));
		// list.add(new KeyValue("41", "文山"));
		// list.add(new KeyValue("42", "玉溪"));
		// list.add(new KeyValue("43", "昭通"));
		// list.add(new KeyValue("56", "丽江"));
		// list.add(new KeyValue("70", "昆明"));
		// list.add(new KeyValue("71", "大理"));
		// Province_City_Map.put("云南", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("243", "鄂州"));
		// list.add(new KeyValue("244", "恩施"));
		// list.add(new KeyValue("245", "黄冈"));
		// list.add(new KeyValue("246", "黄石"));
		// list.add(new KeyValue("247", "荆门"));
		// list.add(new KeyValue("248", "荆州"));
		// list.add(new KeyValue("249", "十堰"));
		// list.add(new KeyValue("250", "武汉"));
		// list.add(new KeyValue("251", "咸宁"));
		// list.add(new KeyValue("252", "襄樊"));
		// list.add(new KeyValue("253", "孝感"));
		// list.add(new KeyValue("254", "宜昌"));
		// Province_City_Map.put("湖北", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("101", "海口"));
		// list.add(new KeyValue("102", "三亚"));
		// list.add(new KeyValue("103", "澄迈"));
		// list.add(new KeyValue("104", "东方"));
		// list.add(new KeyValue("105", "琼海"));
		// list.add(new KeyValue("106", "儋州"));
		// list.add(new KeyValue("107", "万宁"));
		// Province_City_Map.put("海南", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("175", "上海"));
		// Province_City_Map.put("上海", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("84", "临夏"));
		// list.add(new KeyValue("85", "陇南"));
		// list.add(new KeyValue("86", "平凉"));
		// list.add(new KeyValue("87", "庆阳"));
		// list.add(new KeyValue("88", "天水"));
		// list.add(new KeyValue("89", "武威"));
		// list.add(new KeyValue("90", "张掖"));
		// list.add(new KeyValue("115", "白银"));
		// list.add(new KeyValue("116", "定西"));
		// list.add(new KeyValue("117", "甘南"));
		// list.add(new KeyValue("118", "嘉峪关"));
		// list.add(new KeyValue("119", "酒泉"));
		// list.add(new KeyValue("120", "金昌"));
		// list.add(new KeyValue("121", "兰州"));
		// Province_City_Map.put("甘肃", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("1", "常德"));
		// list.add(new KeyValue("153", "长沙"));
		// list.add(new KeyValue("154", "郴州"));
		// list.add(new KeyValue("155", "衡阳"));
		// list.add(new KeyValue("156", "张家界"));
		// list.add(new KeyValue("157", "怀化"));
		// list.add(new KeyValue("158", "娄底"));
		// list.add(new KeyValue("159", "邵阳"));
		// list.add(new KeyValue("160", "湘潭"));
		// list.add(new KeyValue("161", "吉首"));
		// list.add(new KeyValue("162", "益阳"));
		// list.add(new KeyValue("163", "永州"));
		// list.add(new KeyValue("170", "岳阳"));
		// list.add(new KeyValue("171", "株洲"));
		// Province_City_Map.put("湖南", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("164", "洛阳"));
		// list.add(new KeyValue("165", "南阳"));
		// list.add(new KeyValue("166", "平顶山"));
		// list.add(new KeyValue("167", "三门峡"));
		// list.add(new KeyValue("168", "商丘"));
		// list.add(new KeyValue("169", "新乡"));
		// list.add(new KeyValue("189", "安阳"));
		// list.add(new KeyValue("190", "鹤壁"));
		// list.add(new KeyValue("191", "济源"));
		// list.add(new KeyValue("192", "焦作"));
		// list.add(new KeyValue("193", "开封"));
		// list.add(new KeyValue("194", "信阳"));
		// list.add(new KeyValue("195", "许昌"));
		// list.add(new KeyValue("196", "郑州"));
		// list.add(new KeyValue("197", "周口"));
		// list.add(new KeyValue("198", "驻马店"));
		// list.add(new KeyValue("199", "濮阳"));
		// list.add(new KeyValue("325", "漯河"));
		// Province_City_Map.put("河南", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("122", "临沂"));
		// list.add(new KeyValue("123", "日照"));
		// list.add(new KeyValue("124", "泰安"));
		// list.add(new KeyValue("125", "威海"));
		// list.add(new KeyValue("126", "潍坊"));
		// list.add(new KeyValue("127", "烟台"));
		// list.add(new KeyValue("128", "枣庄"));
		// list.add(new KeyValue("129", "济宁"));
		// list.add(new KeyValue("130", "淄博"));
		// list.add(new KeyValue("318", "德州"));
		// list.add(new KeyValue("319", "济南"));
		// list.add(new KeyValue("320", "青岛"));
		// list.add(new KeyValue("321", "菏泽"));
		// list.add(new KeyValue("322", "莱芜"));
		// list.add(new KeyValue("323", "聊城"));
		// Province_City_Map.put("山东", list);
		// list = new ArrayList<KeyValue>();
		// list.add(new KeyValue("2", "大庆"));
		// list.add(new KeyValue("3", "大兴安岭"));
		// list.add(new KeyValue("4", "哈尔滨"));
		// list.add(new KeyValue("5", "黑河"));
		// list.add(new KeyValue("6", "鸡西"));
		// list.add(new KeyValue("7", "佳木斯"));
		// list.add(new KeyValue("8", "牡丹江"));
		// list.add(new KeyValue("9", "七台河"));
		// list.add(new KeyValue("10", "齐齐哈尔"));
		// list.add(new KeyValue("11", "伊春"));
		// Province_City_Map.put("黑龙江", list);

	}

	private static String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getDetailUrl);
		for (Map.Entry<String, String> entry : queryParams.entrySet())
			if (returnString.lastIndexOf("?") == -1) {
				returnString.append("?" + entry.getKey() + "="
						+ entry.getValue());
			} else {
				returnString.append("&" + entry.getKey() + "="
						+ entry.getValue());
			}
		return returnString.toString();
	}

	private static Document getDocument(String url) {
		try {
			// return addHeader(Jsoup.connect(url).timeout(5000)).get();
			return Jsoup
					.connect(url)
					.header("Referer", Referer)
					.header("Host", Host)
					.header("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
					.header("Accept", "text ml,application/xhtml+xml")
					.header("Accept-Language", "zh-cn,zh;q=0.5")
					.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
					.header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0")
					.header("Accept-Encoding", "gzip, deflate").get();
		} catch (IOException e) {

			e.printStackTrace();
			return getDocument(url);
		}
	}

	public static Map<String, List<KeyValue>> getList(String id) {
		List<KeyValue> list = new ArrayList<TravelUtil.KeyValue>();
		Document doc = null;
		try {
			doc = getDocument(getCityListUrl + "?id="
					+ URLEncoder.encode(id, GBK));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			getList(id);
		}
		Elements element = doc.select("table");
		Elements hrefsElements = element.get(0).select("a");
		int count = 0;// 总共抓取的话有1810，太多了。这里限制每个地区最多每个15条记录
		for (Element href : hrefsElements) {
			if (count == 15) {
				break;
			}
			list.add(new KeyValue(href.attr("href").substring(
					"up.asp?id=".length()), href.text()));
			count++;
		}
		Province_City_Map.put(id, list);
		return Province_City_Map;
	}

	public static Map<String, List<KeyValue>> getCityList() {
		List<KeyValue> list = new ArrayList<TravelUtil.KeyValue>();
		Document doc = null;
		try {
			doc = getDocument(getCityListUrl);
		} catch (Exception e) {
			e.printStackTrace();
			getCityList();
		}
		Elements elements = doc.select("div.entry");
		Element divElement = elements.get(0);
		Elements ulElements = divElement.select("ul");
		Elements pElements = divElement.select("p");
		for (int i = 0; i < pElements.size() - 1; i++) {// 最后一个<p>元素要去掉
			list = new ArrayList<TravelUtil.KeyValue>();
			Element pElement = pElements.get(i);
			Elements aElements = ulElements.get(i).select("a");
			for (int j = 0; j < aElements.size(); j++) {
				Element aElement = aElements.get(j);
				list.add(new KeyValue(aElement.attr("href").substring(
						"south.asp?id=".length()), aElement.text()));
			}
			Province_City_Map.put(pElement.text(), list);
		}

		return Province_City_Map;
	}

	public static Map<String, List<KeyValue>> getTravelListList(String cityId) {
		List<KeyValue> list = new ArrayList<TravelUtil.KeyValue>();
		Document doc = null;
		try {
			doc = getDocument(getTravelListUrl + "?id=" + cityId);
		} catch (Exception e) {
			e.printStackTrace();
			getTravelListList(cityId);
		}
		Elements aElements = doc.select("table").get(0).select("a");
		for (int j = 0; j < aElements.size(); j++) {
			Element aElement = aElements.get(j);
			list.add(new KeyValue(aElement.attr("href").substring(
					"north.asp?id=".length()), aElement.text()));
		}
		City_Tracvel_Map.put(cityId, list);

		return City_Tracvel_Map;
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
			return "key -> " + key + " , value -> " + value;
		}
	}

	public static String getDetail(String id) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("id", id);
		Document doc;
		try {
			doc = getDocument(addQueryParams(queryParams));
			Element element = doc.getElementById("table1").select("tr").get(1)
					.select("td").last();
			return element.outerHtml().replace("　　　　", "<br/>").replace("&#65533;","-").replace("。 ", "。<br/>");// 原来的格式太丑了！！
		} catch (Exception e) {
			e.printStackTrace();
			return getDetail(id);
		}

	}

	public static void main(String[] args) {
		// System.out.println(getCityList());
		// System.out.println(getTravelListList(cityId,travelId));

		for (Map.Entry<String, List<KeyValue>> entry : Province_City_Map
				.entrySet()) {
			List<KeyValue> list = entry.getValue();
			for (KeyValue city : list) {
				getTravelListList(city.getKey());
			}
		}
		// System.out.println(City_Tracvel_Map);
//		 System.out.println("Province_City_Map.put(\"" + entry.getKey()
//		 + "\",list);");
//		 }
//		for (Map.Entry<String, List<KeyValue>> entry : City_Tracvel_Map
//				.entrySet()) {
//			// System.out.println("area_custom_map.put(\""+entry.getKey()+"\"");
//			System.out.println("list = new ArrayList<KeyValue>();");
//			for (KeyValue keyValue : entry.getValue()) {
//				System.out.println("list.add(new KeyValue(\""
//						+ keyValue.getKey() + "\",\"" + keyValue.getValue()
//						+ "\"));");
//			}
//			System.out.println("City_Tracvel_Map.put(\"" + entry.getKey()
//					+ "\",list);");
//		}
		for (Map.Entry<String, List<KeyValue>> entry : City_Tracvel_Map
				.entrySet()) {
			for (final KeyValue keyValue : entry.getValue()) {
				new Thread() {
					public void run() {
						CommonUtil.WriteFile(
								CommonUtil.writeFileBasePath
										+ "\\香港、澳门、台湾旅游查询\\content_"
										+ keyValue.getKey() + ".txt",
								getDetail(keyValue.getKey()));
					};
				}.start();

			}
		}

	}
}