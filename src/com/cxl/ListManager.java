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
		AllList.add("01、英国AWP狙击步枪");
		AllList.add("02、XM109狙击步枪");
		AllList.add("03、美国M21狙击步枪");
		AllList.add("04、美国M82A1狙击步枪");
		AllList.add("05、美国巴雷特M95反器材步枪");
		AllList.add("06、美国MK12狙击步枪");
		AllList.add("07、奥地利斯太尔SSG69狙击步枪");
		AllList.add("08、美国布朗精密战术步枪");
		AllList.add("09、奥地利SSG04狙击步枪");
		AllList.add("10、英国L115A3狙击枪");
		AllList.add("11、美国M24狙击步枪");
		AllList.add("12、美国M82A1M狙击步枪");
		AllList.add("13、巴雷特M98狙击步枪");
		AllList.add("14、德国MSG3狙击步枪");
		AllList.add("15、德国SR9半自动步枪");
		AllList.add("16、美国M110狙击步枪");
		AllList.add("17、苏联SVD狙击步枪");
		AllList.add("18、美国M40A1狙击步枪");
		AllList.add("19、美国M82A2狙击步枪");
		AllList.add("20、美国M99狙击步枪");
		AllList.add("21、德国MSG90狙击步枪");
		AllList.add("22、以色列SR99狙击步枪");
		AllList.add("23、美国XM109狙击步枪");
		AllList.add("24、中国79式狙击步枪");
		AllList.add("25、美国M40A3狙击步枪");
		AllList.add("26、美国巴雷特M90狙击步枪");
		AllList.add("27、美国MK11狙击步枪");
		AllList.add("28、德国PSG-1狙击步枪");
		AllList.add("29、雷明登SR8狙击步枪");
	}
}
