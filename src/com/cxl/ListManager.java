package com.cxl;

import java.util.ArrayList;
import java.util.List;

public class ListManager {

	public static List<String> AllList = new ArrayList<String>();
	public static List<String> SearchList = new ArrayList<String>();

	public static List<String> getSearchList(String paramString) {
		if ("".equals(paramString)) {
			return AllList;
		}
		if (SearchList != null) {
			SearchList.clear();
		}
		for (int i = 0; i < AllList.size(); i++) {
			String content = AllList.get(i);
			if (content.indexOf(paramString) != -1) {
				SearchList.add(content);
			}
		}
		return SearchList;
	}

	static {
		AllList.add("00、中国古代兵器");
		AllList.add("01、钺");
		AllList.add("02、陌刀");
		AllList.add("03、双刃剑");
		AllList.add("04、唐十三铠");
		AllList.add("05、马铠");
		AllList.add("06、火龙枪");
		AllList.add("07、钩镰枪");
		AllList.add("08、斧");
		AllList.add("09、弩车");
		AllList.add("10、双刃斧");
		AllList.add("11、三尖刀");
		AllList.add("12、汉刀");
		AllList.add("13、突火枪");
		AllList.add("14、九节鞭");
		AllList.add("15、飞石索");
		AllList.add("16、武士刀");
		AllList.add("17、箭");
		AllList.add("18、暗器");
		AllList.add("19、梭镖");
		AllList.add("20、铠甲");
		AllList.add("21、羊头撞锤");
		AllList.add("22、戈");
		AllList.add("23、矛");
		AllList.add("24、弩");
		AllList.add("25、棍");
		AllList.add("26、锏");
		AllList.add("27、叉");
		AllList.add("28、戟");
		AllList.add("29、拒马枪");
		AllList.add("30、踏弩");
		AllList.add("31、太刀");
		AllList.add("32、铁火炮");
		AllList.add("33、苗刀");
		AllList.add("34、绳镖");
		AllList.add("35、流星锤");
		AllList.add("36、狼牙锤");
		AllList.add("37、飞爪");
		AllList.add("38、软鞭");
		AllList.add("39、大刀");
	}
}
