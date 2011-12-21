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
		AllList.add("01、猫");
		AllList.add("02、孟加拉猫");
		AllList.add("03、埃及猫");
		AllList.add("04、曼岛猫");
		AllList.add("05、新加坡猫");
		AllList.add("06、哈瓦那猫");
		AllList.add("07、缅甸猫");
		AllList.add("08、卷毛猫");
		AllList.add("09、暹罗猫");
		AllList.add("10、东方短毛猫");
		AllList.add("11、欧洲短毛猫");
		AllList.add("12、美国短毛猫");
		AllList.add("13、英国短毛猫");
		AllList.add("14、亚洲渐变色猫");
		AllList.add("15、亚洲烟色猫");
		AllList.add("16、亚洲单色猫");
		AllList.add("17、亚洲虎斑猫");
		AllList.add("18、俄罗斯蓝猫");
		AllList.add("19、孟买猫");
		AllList.add("20、阿比西尼亚猫");
		AllList.add("21、呵叻猫");
		AllList.add("22、日本短尾猫");
		AllList.add("23、加拿大无毛猫");
		AllList.add("24、加州闪亮猫");
		AllList.add("25、美国卷耳猫");
		AllList.add("26、苏格兰折耳猫");
		AllList.add("27、索马里猫");
		AllList.add("28、布偶猫");
		AllList.add("29、西伯利亚森林猫");
		AllList.add("30、挪威森林猫");
		AllList.add("31、土耳其梵猫");
		AllList.add("32、安哥拉猫");
		AllList.add("33、伯曼猫");
		AllList.add("34、缅因猫");
		AllList.add("35、喜马拉雅猫");
		AllList.add("36、金吉拉猫");
		AllList.add("37、波斯猫");


	}
}
