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
		AllList.add("01、F-18战机");
		AllList.add("02、歼-10战斗机");
		AllList.add("03、歼-20");
		AllList.add("04、飓风战机");
		AllList.add("05、歼-14战斗机");
		AllList.add("06、F-22战斗机");
		AllList.add("07、F-16战斗机");
		AllList.add("08、F-15战斗机");
		AllList.add("09、F-35战斗机");
		AllList.add("10、法国阵风战斗机");
		AllList.add("11、F-15战斗机");
		AllList.add("12、米格-29战斗机");
		AllList.add("13、歼-8战斗机");
		AllList.add("14、歼-11B战斗机");
		AllList.add("15、台风战斗机");
		AllList.add("16、美国SR-71“黑鸟”超音速侦察机");
		AllList.add("17、苏-33战斗机");
		AllList.add("18、苏-37战斗机");
		AllList.add("19、歼-11战斗机");
		AllList.add("20、苏-27战斗机");
		AllList.add("21、米格1.44战斗机");
		AllList.add("22、米格-21战斗机");
		AllList.add("23、F-14战斗机");
		AllList.add("24、F-35B联合攻击机");
		AllList.add("25、歼轰-7“飞豹”");
		AllList.add("26、苏-47战斗机");
		AllList.add("27、p-51野马战斗机");
		AllList.add("28、米格-29K");
		AllList.add("29、歼-9战斗机");
		AllList.add("30、FC-1枭龙战斗机");
		AllList.add("31、闪电战斗机");
		AllList.add("32、EA-18G电子战机");
		AllList.add("33、F-20虎鲨战斗机");
		AllList.add("34、俄罗斯T-50战斗机");
		AllList.add("35、p38战斗机");
		AllList.add("36、Bf-109");
		AllList.add("37、AV-8B鹞式攻击机　　  ");
		AllList.add("38、霍克-3型战斗机");
		AllList.add("39、JAS-39战斗机");
		AllList.add("40、苏-35战斗机");
		AllList.add("41、F-117A隐身攻击机");
		AllList.add("42、BF109");
		AllList.add("43、狂风战斗机");
		AllList.add("44、歼-7战斗机");
		AllList.add("45、F2战斗机");
		AllList.add("46、零式战机");
		AllList.add("47、P47雷电");
		AllList.add("48、F-4战斗机");
		AllList.add("49、歼-6战斗机");
		AllList.add("50、幻影2000战斗机");
	}
}
