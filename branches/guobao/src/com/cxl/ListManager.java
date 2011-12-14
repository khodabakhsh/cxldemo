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

		AllList.add("01、何尊");
		AllList.add("02、四羊方尊");
		AllList.add("03、针灸铜人");
		AllList.add("04、利簋");
		AllList.add("05、大玉戈");
		AllList.add("06、淳化大鼎");
		AllList.add("07、兽首玛瑙杯");
		AllList.add("08、虢季子白盘");
		AllList.add("09、大秦景教流行中国碑");
		AllList.add("10、长信宫灯");
		AllList.add("11、竹林七贤砖印模画");
		AllList.add("12、三星堆出土玉边璋");
		AllList.add("13、水晶杯");
		AllList.add("14、大克鼎");
		AllList.add("15、青花釉里红瓷仓");
		AllList.add("16、人物御龙帛画");
		AllList.add("17、人面鱼纹彩陶盆");
		AllList.add("18、摇钱树");
		AllList.add("19、茂陵石雕");
		AllList.add("20、曾侯乙编钟");
		AllList.add("21、大盂鼎");
		AllList.add("22、西夏文佛经《吉祥遍至口本续》纸本");
		AllList.add("23、司母戊铜鼎");
		AllList.add("24、铜车马");
		AllList.add("25、淅川出土铜禁");
		AllList.add("26、中山王铁足铜鼎");
		AllList.add("27、角形玉杯");
		AllList.add("28、马王堆一号墓木棺椁");
		AllList.add("29、曾侯乙青铜尊盘");
		AllList.add("30、水月观音菩萨像");
		AllList.add("31、玉兽面纹圭");
		AllList.add("32、玉琮王");
		AllList.add("33、刘胜金缕玉衣");
		AllList.add("34、朱然墓贵族生活图漆盘");
		AllList.add("35、马王堆一号墓T型帛画");
		AllList.add("36、五星出东方锦护膊");
		AllList.add("37、戴金面罩青铜人头像");
		AllList.add("38、莲鹤铜方壶");
		AllList.add("39、娄睿墓鞍马出行图壁画");
		AllList.add("40、铜错金银四龙四凤方案");
		AllList.add("41、铜屏风");
		AllList.add("42、直裾素纱禅衣");
		AllList.add("43、彩绘鹳鱼石斧图陶缸");
		AllList.add("44、虎形瓷枕");
		AllList.add("45、鸭形玻璃注");
		AllList.add("46、朱然墓出土漆木屐");
		AllList.add("47、铜浮屠");
		AllList.add("48、铸客大铜鼎");
		AllList.add("49、陶鹰鼎");
		AllList.add("50、人操蛇屏风铜托座");
		AllList.add("51、青铜神树");
		AllList.add("52、乳丁纹虑耳方鼎");
		AllList.add("53、鹿耳四足甗");
		AllList.add("54、曾侯乙墓外棺");
		AllList.add("55、太阳神鸟金饰");
		AllList.add("56、墙盘");
		AllList.add("57、嵌绿松石象牙杯");
		AllList.add("58、金翼善冠");
		AllList.add("59、三彩双龙耳瓶");
		AllList.add("60、太阳神纹石刻");
		AllList.add("61、常阳太尊石像");
		AllList.add("62、银花双轮十二环锡杖");
		AllList.add("63、青花釉里红楼阁式谷仓");
		AllList.add("64、牺首兽面纹铜尊");
		AllList.add("65、太保鼎");
		AllList.add("66、击鼓说唱陶俑");
		AllList.add("67、舞马衔杯仿皮囊式银壶");

	}
}
