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
		AllList.add("01、奥迪汽车");
		AllList.add("02、阿尔法・罗密欧");
		AllList.add("03、阿斯顿・马丁");
		AllList.add("04、奔驰");
		AllList.add("05、宝马");
		AllList.add("06、宾利");
		AllList.add("07、布加迪");
		AllList.add("08、保时捷");
		AllList.add("09、别克");
		AllList.add("10、本田");
		AllList.add("11、标致");
		AllList.add("12、比亚迪");
		AllList.add("13、北汽");
		AllList.add("14、宝骏");
		AllList.add("15、宝腾");
		AllList.add("16、长城汽车");
		AllList.add("17、道奇");
		AllList.add("18、福特");
		AllList.add("19、福特野马");
		AllList.add("20、通用汽车");
		AllList.add("21、JEEP");
		AllList.add("22、凯迪拉克");
		AllList.add("23、克莱斯勒");
		AllList.add("24、林肯");
		AllList.add("25、奥兹莫比尔");
		AllList.add("26、庞蒂亚克");
		AllList.add("27、Rossion");
		AllList.add("28、土星汽车");
		AllList.add("29、雪佛兰");
		AllList.add("30、大众");
		AllList.add("31、迈巴赫");
		AllList.add("32、欧宝");
		AllList.add("33、威兹曼");
		AllList.add("34、本田");
		AllList.add("35、丰田");
		AllList.add("36、光冈");
		AllList.add("37、雷克萨斯");
		AllList.add("38、铃木");
		AllList.add("39、马自达");
		AllList.add("40、日产");
		AllList.add("41、斯巴鲁");
		AllList.add("42、三菱");
		AllList.add("43、五十铃");
		AllList.add("44、JAGUAR");
		AllList.add("45、劳斯・莱斯");
		AllList.add("46、路虎");
		AllList.add("47、莲花");
		AllList.add("48、mini");
		AllList.add("49、罗孚");
		AllList.add("50、沃克斯豪尔");
		AllList.add("51、雪铁龙");
		AllList.add("52、西亚特");
		AllList.add("53、法拉利");
		AllList.add("54、菲亚特");
		AllList.add("55、兰博基尼");
		AllList.add("56、玛莎拉蒂");
		AllList.add("57、帕加尼");
		AllList.add("58、大宇");
		AllList.add("59、起亚");
		AllList.add("60、现代");

	}
}
