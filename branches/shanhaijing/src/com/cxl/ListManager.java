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
		AllList.add("01、山海经");
		AllList.add("02、烛龙");
		AllList.add("03、夸父逐日");
		AllList.add("04、女娲补天");
		AllList.add("05、后羿射日");
		AllList.add("06、衔烛之龙");
		AllList.add("07、毕方");
		AllList.add("08、英招");
		AllList.add("09、相柳");
		AllList.add("10、刑天舞干戚");
		AllList.add("11、玄鸟");
		AllList.add("12、腓腓");
		AllList.add("13、神荼");
		AllList.add("14、肥遗");
		AllList.add("15、蓬莱山");
		AllList.add("16、乘黄");
		AllList.add("17、三足鸟");
		AllList.add("18、羽山");
		AllList.add("19、延维");
		AllList.add("20、狌狌");
		AllList.add("21、祸斗");
		AllList.add("22、女娃");
		AllList.add("23、丹鸟");
		AllList.add("24、蛊雕");
		AllList.add("25、旋龟");
		AllList.add("26、羽灵");
		AllList.add("27、蝮虫");
		AllList.add("28、鱼妇");
		AllList.add("29、尚付");
		AllList.add("30、奇穷");
		AllList.add("31、长右");
		AllList.add("32、冉遗");
		AllList.add("33、橐蜚");
		AllList.add("34、驴鼠");
		AllList.add("35、瞿如");
		AllList.add("36、朋蛇");
		AllList.add("37、蠃鱼");
		AllList.add("38、翳鸟");
		AllList.add("39、天狗");
		AllList.add("40、狰 ");
		AllList.add("41、肥遗");
		AllList.add("42、凤凰");
		AllList.add("43、九尾狐 ");
		AllList.add("44、青龙");
		AllList.add("45、白虎");
		AllList.add("46、玄武");
		AllList.add("47、朱雀");
		AllList.add("48、梼杌");
		AllList.add("49、混沌");
		AllList.add("50、饕餮");
		AllList.add("51、水麒麟");
		AllList.add("52、白泽");
		AllList.add("53、獬豸");
		AllList.add("54、横公鱼");
		AllList.add("55、狴犴");
		AllList.add("56、夔");
		AllList.add("57、孰湖");
		AllList.add("58、当康");
	}
}
