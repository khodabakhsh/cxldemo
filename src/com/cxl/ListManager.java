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
		AllList.add("01、剑齿虎");
		AllList.add("02、美洲剑齿虎");
		AllList.add("03、始祖鸟");
		AllList.add("04、雷兽");
		AllList.add("05、巨齿鲨");
		AllList.add("06、三叶虫");
		AllList.add("07、翼龙");
		AllList.add("08、猛犸");
		AllList.add("09、鹦鹉螺");
		AllList.add("10、蛇颈龙");
		AllList.add("11、沧龙");
		AllList.add("12、恐鳄");
		AllList.add("13、普鲁斯鳄");
		AllList.add("14、鱼龙");
		AllList.add("15、恐鸟");
		AllList.add("16、哈斯特鹰");
		AllList.add("17、滑齿龙");
		AllList.add("18、帝鳄");
		AllList.add("19、恐狼");
		AllList.add("20、大地懒");
		AllList.add("21、龙王鲸");
		AllList.add("22、利兹鱼");
		AllList.add("23、上龙");
		AllList.add("24、腔棘鱼");
		AllList.add("25、邓氏鱼");
		AllList.add("26、始熊猫");
		AllList.add("27、克柔龙");
		AllList.add("28、巨猿");
		AllList.add("29、霍氏不死虫");
		AllList.add("30、剑齿象");
		AllList.add("31、海蝎");
		AllList.add("32、长毛象");
		AllList.add("33、幻龙");
		AllList.add("34、海王龙");
		AllList.add("35、恐猫");
		AllList.add("36、袋狮");
		AllList.add("37、美洲拟狮");
		AllList.add("38、熊狗");
		AllList.add("39、泰坦鸟");
		AllList.add("40、奇虾");
		AllList.add("41、雕齿兽");
		AllList.add("42、巨犀");
		AllList.add("43、恐象");
		AllList.add("44、披毛犀");
		AllList.add("45、象鸟");
		AllList.add("46、短面熊");
		AllList.add("47、杯椎鱼龙");
		AllList.add("48、大洋龙");
		AllList.add("49、巨颏虎");
		AllList.add("50、板齿犀");

	}
}
