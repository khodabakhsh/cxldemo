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

		AllList.add("01、吉萨金字塔");
		AllList.add("02、奥林匹亚宙斯巨像");
		AllList.add("03、阿尔忒弥斯神殿");
		AllList.add("04、摩索拉斯基陵墓");
		AllList.add("05、亚历山大灯塔");
		AllList.add("06、空中花园");
		AllList.add("07、罗德岛太阳神巨像");
		AllList.add("08、罗马斗兽场");
		AllList.add("09、亚历山大地下陵墓");
		AllList.add("10、万里长城");
		AllList.add("11、巨石阵");
		AllList.add("12、大报恩寺");
		AllList.add("13、比萨斜塔");
		AllList.add("14、索菲亚大教堂");
		AllList.add("15、科罗拉多大峡谷");
		AllList.add("16、维多利亚瀑布");
		AllList.add("17、阿拉斯加冰河湾");
		AllList.add("18、猛玛洞穴国家公园");
		AllList.add("19、珠穆朗玛峰");
		AllList.add("20、贝加尔湖");
		AllList.add("21、黄石国家公园");
		AllList.add("22、奇琴伊查库库尔坎金字塔");
		AllList.add("23、马丘比丘遗址");
		AllList.add("24、里约热内卢基督像");
		AllList.add("25、佩特拉");
	}
}
