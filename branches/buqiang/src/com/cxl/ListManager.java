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
		AllList.add("01、M16系列自动步枪");
		AllList.add("02、AWM");
		AllList.add("03、03式自动步枪");
		AllList.add("04、81式自动步枪");
		AllList.add("05、三八式步枪");
		AllList.add("06、卡宾枪");
		AllList.add("07、AK-47突击步枪");
		AllList.add("08、85式狙击步枪");
		AllList.add("09、SCARLIGHT突击步枪");
		AllList.add("10、56式半自动步枪");
		AllList.add("11、M200");
		AllList.add("12、SVD狙击步枪");
		AllList.add("13、毛瑟步枪");
		AllList.add("14、97式突击步枪");
		AllList.add("15、M14自动步枪");
		AllList.add("16、勃朗宁自动步枪");
		AllList.add("17、FAMAS自动步枪");
		AllList.add("18、M21狙击步枪");
		AllList.add("19、M1式加兰德步枪");
		AllList.add("20、巴雷特M82A1狙击步枪");
		AllList.add("21、M1卡宾枪");
		AllList.add("22、stg44突击步枪");
		AllList.add("23、AUG突击步枪");
		AllList.add("24、Kar98k毛瑟步枪");
		AllList.add("25、莫辛-纳甘步枪");
		AllList.add("26、SKS半自动步枪");
		AllList.add("27、大口径狙击步枪");
		AllList.add("28、QJY88式通用机枪");
		AllList.add("29、88式狙击步枪");
		AllList.add("30、M1941式约翰逊半自动步枪");
		AllList.add("31、AK-74突击步枪");
		AllList.add("32、M1式加兰德步枪");
		AllList.add("33、FN F2000");
		AllList.add("34、FN2000");
		AllList.add("35、99式步枪");
		AllList.add("36、G36自动步枪");
		AllList.add("37、AN94自动步枪");
		AllList.add("38、AK-103");
		AllList.add("39、李-恩菲尔德步枪");
		AllList.add("40、AN94自动步枪");
		AllList.add("41、G3/SG1狙击步枪");
		AllList.add("42、G43步枪");
		AllList.add("43、G36自动步枪");
		AllList.add("44、PSG-1狙击步枪");
		AllList.add("45、加利尔突击步枪");
		AllList.add("46、XM8轻型突击步枪");
		AllList.add("47、G22狙击步枪");
		AllList.add("48、XM109狙击步枪");
		AllList.add("49、M1917式步枪");
		AllList.add("50、马萨达战斗步枪");
	}
}
