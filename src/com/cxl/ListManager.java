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

		AllList.add("01、高鼻羚羊");
		AllList.add("02、侏儒猫头鹰");
		AllList.add("03、小鸥");
		AllList.add("04、草原雕");
		AllList.add("05、西藏野驴");
		AllList.add("06、娃娃鱼");
		AllList.add("07、大天鹅");
		AllList.add("08、鳄蜥");
		AllList.add("09、大熊猫");
		AllList.add("10、长耳鸮");
		AllList.add("11、野牦牛");
		AllList.add("12、野骆驼");
		AllList.add("13、野马");
		AllList.add("14、蒙古野驴");
		AllList.add("15、四爪陆龟");
		AllList.add("16、丹顶鹤");
		AllList.add("17、长臂猿");
		AllList.add("18、巨松鼠");
		AllList.add("19、蜂猴");
		AllList.add("20、豚鹿");
		AllList.add("21、白颈长尾雉");
		AllList.add("22、穿山甲");
		AllList.add("23、朱鹭");
		AllList.add("24、褐马鸡");
		AllList.add("25、白暨豚");
		AllList.add("26、麋鹿");
		AllList.add("27、黑颈鹤");
		AllList.add("28、扬子鳄");
		AllList.add("29、红腹锦鸡");
		AllList.add("30、藏羚羊");
		AllList.add("31、华南虎");
		AllList.add("32、金丝猴");

	}
}
