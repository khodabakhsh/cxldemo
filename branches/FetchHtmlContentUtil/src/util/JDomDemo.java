package util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * JDOM 生成与解析XML文档 * 依赖jdom.jar
 */
public class JDomDemo {
	private static String baseFilePath = "D:\\cxl\\my apk\\__________others_________\\世界上下五千年\\res\\xml";
	private static String titleFilePath = "D:\\cxl\\my apk\\__________others_________\\世界上下五千年\\res\\xml\\a00.xml";
	private static String genFilePath = "D:\\cxl\\my apk\\__________others_________\\世界上下五千年\\res\\xml_new";
	private static String suffix = ".txt";
	private static Map<String, Integer> map = new HashMap<String, Integer>();
	static {
		File genFile = new File(genFilePath);
		if (!genFile.exists()) {
			genFile.mkdirs();
		}
		 map.put("前　　言", 0);
		 map.put("失踪的国家", 1);
		 map.put("说话的石碑", 2);
		 map.put("千古之谜金字塔", 3);
		 map.put("法老的诅咒", 4);
		 map.put("大战卡叠石", 5);
		 map.put("神奇古都底比斯", 6);
		 map.put("太阳之子埃赫那吞", 7);
		 map.put("太阳历和公历", 8);
		 map.put("让人神往的美索不达米亚", 9);
		 map.put("刻在泥版上的文字", 10);
		 map.put("汉谟拉比和刻在石柱上的法典", 11);
		 map.put("流浪的犹太民族", 12);
		 map.put("寻找金约柜和所罗门珍宝", 13);
		 map.put("紫红色的人", 14);
		 map.put("亚述帝国与血腥的狮穴", 15);
		 map.put("尼布甲尼撒二世", 16);
		 map.put("冒犯上帝的城市巴比伦", 17);
		 map.put("宇宙之王居鲁士", 18);
		 map.put("暴君冈比西斯", 19);
		 map.put("大流士改革", 20);
		 map.put("埋在死人之丘下的城市", 21);
		 map.put("古代印度的种姓制度", 22);
		 map.put("佛祖和佛教", 23);
		 map.put("伟大的阿育王", 24);
		 map.put("《摩诃婆罗多》", 25);
		 map.put("地下迷宫", 26);
		 map.put("荷　　马", 27);
		 map.put("木　马　计", 28);
		 map.put("奥林匹亚赛会", 29);
		 map.put("斯　巴　达", 30);
		 map.put("改革家梭伦", 31);
		 map.put("马　拉　松", 32);
		 map.put("血战温泉关", 33);
		 map.put("萨拉米湾海战", 34);
		 map.put("伯里克利与雅典", 35);
		 map.put("雅典的民主", 36);
		 map.put("希罗多德", 37);
		 map.put("苏格拉底", 38);
		 map.put("希波克拉底", 39);
		 map.put("柏　拉　图", 40);
		 map.put("西西里之战", 41);
		 map.put("亚里士多德", 42);
		 map.put("雄辩家德摩斯梯尼", 43);
		 map.put("伊索的寓言", 44);
		 map.put("亚历山大", 45);
		 map.put("格拉尼库斯河战役", 46);
		 map.put("罗马和母狼", 47);
		 map.put("法西斯笞棒", 48);
		 map.put("穆基乌斯", 49);
		 map.put("罗马与白鹅", 50);
		 map.put("向贵族宣战", 51);
		 map.put("坦克―战象", 52);
		 map.put("乌鸦式战舰", 53);
		 map.put("阿基米德", 54);
		 map.put("汉尼拔", 55);
		 map.put("康奈城激战", 56);
		 map.put("格拉古兄弟", 57);
		 map.put("斯巴达克起义", 58);
		 map.put("马略与苏拉", 59);
		 map.put("恺　　撒", 60);
		 map.put("庞　　培", 61);
		 map.put("克拉苏", 62);
		 map.put("奥古斯都", 63);
		 map.put("尼　　禄", 64);
		 map.put("耶　　稣", 65);
		 map.put("奥古斯丁", 66);
		 map.put("古城庞贝", 67);
		 map.put("罗马帝国的灭亡", 68);
		 map.put("查理大帝", 69);
		 map.put("查士丁尼", 70);
		 map.put("中世纪骑士", 71);
		 map.put("日本的大化改新", 72);
		 map.put("遣　唐　使", 73);
		 map.put("西天在哪里", 74);
		 map.put("十字军东侵", 75);
		 map.put("扎克雷起义", 76);
		 map.put("基辅罗斯的盛衰", 77);
		 map.put("君士坦丁堡的陷落", 78);
		 map.put("马丁?路德", 79);
		 map.put("勇敢的闵采尔", 80);
		 map.put("日内瓦的教皇加尔文", 81);
		 map.put("穆罕默德和伊斯兰教", 82);
		 map.put("《一千零一夜》", 83);
		 map.put("马可?波罗", 84);
		 map.put("阿　克　巴", 85);
		 map.put("横跨欧亚非的奥斯曼帝国", 86);
		 map.put("新航路的开辟", 87);
		 map.put("瓦斯科?达?伽马", 88);
		 map.put("麦　哲　伦", 89);
		 map.put("羊吃人的圈地运动", 90);
		 map.put("托马斯?阿奎那", 91);
		 map.put("文艺复兴", 92);
		 map.put("彼脱拉克", 93);
		 map.put("但丁与《神曲》", 94);
		 map.put("薄伽丘与《十日谈》", 95);
		 map.put("拉伯雷与《巨人传》", 96);
		 map.put("莎士比亚", 97);
		 map.put("大侠堂吉诃德", 98);
		 map.put("康帕内拉的《太阳城》", 99);
		 map.put("迦梨陀娑和《沙恭达罗》", 100);
		 map.put("米开朗基罗", 101);
		 map.put("达?芬奇", 102);
		 map.put("伽　利　略", 103);
		 map.put("哥　白　尼", 104);
		 map.put("培　　根", 105);
		 map.put("捷克胡司战争", 106);
		 map.put("掷出窗外事件", 107);
		 map.put("李舜臣和壬辰卫国战争", 108);
		 map.put("“乞丐”的国家", 109);
		 map.put("断头的国王", 110);
		 map.put("未戴王冠的英国国王――克伦威尔", 111);
		 map.put("“进口”的国王和“光荣革命”", 112);
		 map.put("俄罗斯帝国的创立者――彼得大帝", 113);
		 map.put("彼得大帝两次围攻纳尔瓦", 114);
		 map.put("蒸汽机的真正发明者", 115);
		 map.put("伟大的科学家牛顿", 116);
		 map.put("“海上马车夫”", 117);
		 map.put("美国独立战争的第一枪", 118);
		 map.put("美利坚合众国的缔造者――华盛顿", 119);
		 map.put("揭开雷电之谜的人――富兰克林", 120);
		 map.put("马背上的英雄――美国牛仔", 121);
		 map.put("谢司起义", 122);
		 map.put("启蒙运动的领袖――伏尔泰", 123);
		 map.put("攻占巴士底狱", 124);
		 map.put("罗伯斯比尔", 125);
		 map.put("刽子手?国王?断头台", 126);
		 map.put("青年拿破仑", 127);
		 map.put("“雾月政变”", 128);
		 map.put("教皇算什么！", 129);
		 map.put("会战马伦哥", 130);
		 map.put("乌耳姆战役", 131);
		 map.put("奥斯特里茨之战", 132);
		 map.put("拿破仑兵败俄国", 133);
		 map.put("滑铁卢战役", 134);
		 map.put("达尔文和他的“进化论”", 135);
		 map.put("世界音乐大师贝多芬", 136);
		 map.put("野心勃勃的女沙皇――叶卡杰琳娜二世", 137);
		 map.put("普加乔夫起义", 138);
		 map.put("俄国革命的先驱――１２月党人", 139);
		 map.put("雨果", 140);
		 map.put("“俄国文学的始祖”――普希金", 141);
		 map.put("文学史上的拿破仑", 142);
		 map.put("果戈理", 143);
		 map.put("“万古不朽”圣马丁", 144);
		 map.put("“解放者”玻利瓦尔", 145);
		 map.put("人类的耻辱――奴隶贸易", 146);
		 map.put("自由海地的诞生", 147);
		 map.put("电灯的发明", 148);
		 map.put("欧文和他的“新和谐公社”", 149);
		 map.put("青年马克思", 150);
		 map.put("恩格斯的青年时代", 151);
		 map.put("惊天动地的红色宣言", 152);
		 map.put("《资本论》的诞生", 153);
		 map.put("第一个国际工人的组织――第一国际", 154);
		 map.put("铁血宰相俾斯麦", 155);
		 map.put("色当战役", 156);
		 map.put("三月十八日武装起义", 157);
		 map.put("悲壮的“五月流血周”", 158);
		 map.put("民族英雄蒂博尼哥罗", 159);
		 map.put("巴布教徒的“正义王国”", 160);
		 map.put("东印度公司", 161);
		 map.put("章西女王葩依", 162);
		 map.put("德里反英大起义", 163);
		 map.put("约翰?布朗起义", 164);
		 map.put("林肯", 165);
		 map.put("唐纳尔逊堡大战", 166);
		 map.put("维克斯堡战役", 167);
		 map.put("血战葛底斯堡", 168);
		 map.put("弗吉尼亚大会战", 169);
		 map.put("陀思妥耶夫斯基", 170);
		 map.put("病菌和病毒的发现者――巴斯德", 171);
		 map.put("国际航道――苏伊士运河", 172);
		 map.put("马赫迪大破英军", 173);
		 map.put("“倒幕”运动与明治维新", 174);
		 map.put("飞机的发明者莱特兄弟", 175);
		 map.put("列宁的青年时代", 176);
		 map.put("“五一”国际劳动节", 177);
		 map.put("生理学无冕之王――巴甫洛夫", 178);
		 map.put("德雷福斯冤案", 179);
		 map.put("“Ｘ射线”是如何发现的", 180);
		 map.put("“缅因”号事件", 181);
		 map.put("星期日惨案", 182);
		 map.put("“诺贝尔奖金”的由来", 183);
		 map.put("居里夫人", 184);
		 map.put("日俄旅顺战役", 185);
		 map.put("爱因斯坦和他的“相对论”", 186);
		 map.put("震惊世界的“萨拉热窝枪声”", 187);
		 map.put("“史里芬计划”的破灭", 188);
		 map.put("德俄“坦仑堡战役”", 189);
		 map.put("人类历史上的第一次毒气战", 190);
		 map.put("“神秘之船”在行动", 191);
		 map.put("英德海上大决战", 192);
		 map.put("凡尔登战役", 193);
		 map.put("坦克的前身――“机枪破坏器”", 194);
		 map.put("“信使”彻尔?阿米", 195);
		 map.put("兰斯保卫战", 196);
		 map.put("攻占冬宫", 197);
		 map.put("德意志风云", 198);
		 map.put("巴黎和会", 199);
		 map.put("米骚动", 200);
		 map.put("新经济政策", 201);
		 map.put("青年人的任务", 202);
		 map.put("“星期六义务劳动”", 203);
		 map.put("列宁逝世", 204);
		 map.put("经济危机", 205);
		 map.put("从“流浪汉”到“第三帝国”总理", 206);
		 map.put("“国会纵火案”", 207);
		 map.put("“二?二六”暴乱", 208);
		 map.put("日军间谍与“九?一八”事变", 209);
		 map.put("埃塞俄比亚的抗战", 210);
		 map.put("保卫马德里", 211);
		 map.put("“和平鸽”的父亲", 212);
		 map.put("慕尼黑阴谋", 213);
		 map.put("“消灭褐色瘟疫”", 214);
		 map.put("电视的发明", 215);
		 map.put("“硬汉子”海明威", 216);
		 map.put("“圣雄”甘地", 217);
		 map.put("土耳其之父", 218);
		 map.put("巴基斯坦国的创建者", 219);
		 map.put("朝鲜“三?一”运动", 220);
		 map.put("自由人的将军桑地诺", 221);
		 map.put("揭开原子秘密的人", 222);
		 map.put("火箭发明家", 223);
		 map.put("青霉素的发现", 224);
		 map.put("第一颗原子弹", 225);
		 map.put("“白色方案”和“海狮计划”", 226);
		 map.put("敦刻尔克大撤退", 227);
		 map.put("“巴巴罗沙”计划", 228);
		 map.put("斯大林格勒保卫战", 229);
		 map.put("保卫列宁格勒", 230);
		 map.put("“红色间谍”", 231);
		 map.put("安妮?弗兰克日记", 232);
		 map.put("“杀人工厂”", 233);
		 map.put("绞刑架下的报告", 234);
		 map.put("地下救援组织", 235);
		 map.put("通往自由的隧道", 236);
		 map.put("伏击杀人魔王", 237);
		 map.put("红衣女谍", 238);
		 map.put("勇敢的“燕子”", 239);
		 map.put("运送十八亿英镑", 240);
		 map.put("围歼“俾斯麦”", 241);
		 map.put("勇敢的女船长", 242);
		 map.put("沙漠猎“狐”", 243);
		 map.put("“自由法国”运动", 244);
		 map.put("珍珠港怪客", 245);
		 map.put("偷袭珍珠港", 246);
		 map.put("中途岛海战", 247);
		 map.put("乔伊的故事", 248);
		 map.put("“第三战场”的无名勇士", 249);
		 map.put("挪威英雄", 250);
		 map.put("“魔术师”似的密码破译专家", 251);
		 map.put("希特勒的“敌后武工队”", 252);
		 map.put("背叛“祖国”的纳粹党创始人", 253);
		 map.put("“海狼”与海鸥", 254);
		 map.put("直布罗陀海底的“人鱼雷”", 255);
		 map.put("假电报瞒天过海日舰队偷袭成功", 256);
		 map.put("龙田丸示假隐真假水兵招摇过市", 257);
		 map.put("密写信中的间谍线索", 258);
		 map.put("来自背后的奇袭", 259);
		 map.put("走向死亡的行军", 260);
		 map.put("杜立德首炸东京", 261);
		 map.put("瓜岛之战中的“老鼠特快”", 262);
		 map.put("传单上的投降证", 263);
		 map.put("不到４分钟的“复仇行动”", 264);
		 map.put("引诱纳粹上钩的“肉馅”", 265);
		 map.put("诺曼底登陆", 266);
		 map.put("袭击美国的“飞象”", 267);
		 map.put("电子干扰制造的大骗局", 268);
		 map.put("普罗霍夫卡草原上的坦克大战", 269);
		 map.put("毁灭汉堡的“反射体”", 270);
		 map.put("夜袭佩内明德", 271);
		 map.put("穷凶极恶的纳粹别动队", 272);
		 map.put("海里捞起来的绝密公文包", 273);
		 map.put("日军惨败英帕尔", 274);
		 map.put("尼米兹的“奇袭行动”", 275);
		 map.put("马里亚纳的大规模火鸡射击战", 276);
		 map.put("垂死挣扎的“神风特攻”", 277);
		 map.put("静悄悄的瞬间奇袭", 278);
		 map.put("穿美军制服的德国兵", 279);
		 map.put("超级战列舰的覆灭", 280);
		 map.put("阿登山林的激战", 281);
		 map.put("“雷击”德累斯顿", 282);
		 map.put("血战硫黄岛", 283);
		 map.put("飞夺雷马根桥", 284);
		 map.put("夜空中燃烧的江户花", 285);
		 map.put("冲绳之战", 286);
		 map.put("击沉大和号", 287);
		 map.put("杰出的参谋长马歇尔", 288);
		 map.put("爱冒险的麦克阿瑟", 289);
		 map.put("血胆将军巴顿", 290);
		 map.put("临危受命不负众望的尼米兹", 291);
		 map.put("军人政治家外交家艾森豪威尔", 292);
		 map.put("一位工人和革命士兵――伏罗希洛夫", 293);
		 map.put("传奇英雄布琼尼", 294);
		 map.put("稳健持重的华西列夫斯基", 295);
		 map.put("最高副统帅朱可夫", 296);
		 map.put("“沙漠之鼠”蒙哥马利", 297);
		 map.put("德国陆军的元老伦德施泰特", 298);
		 map.put("富有创见的曼施泰因", 299);
		 map.put("“闪击英雄”古德里安", 300);
		 map.put("“沙漠之狐”隆美尔", 301);
		 map.put("纳粹之“鹰”凯塞林", 302);
		 map.put("战争恶魔东条英机", 303);
		 map.put("山本五十六", 304);
		 map.put("德黑兰会议", 305);
		 map.put("刺杀希特勒", 306);
		 map.put("拙劣的“跳马”", 307);
		 map.put("墨索里尼的末日", 308);
		 map.put("希特勒的末日", 309);
		 map.put("广岛“蘑菇云”", 310);
		 map.put("日本投降", 311);
		 map.put("正义的审判", 312);
		 map.put("联合国成立", 313);


	}

	
	public static void genTitleKeyValue(String filePath) {
		SAXBuilder builder = new SAXBuilder(false);
		File file = new File(filePath);
		try {
			Document document = builder.build(file);
			Element root = document.getRootElement();
			List<Element> titleList = root.getChildren("title");
			Element element;
			for (int i = 0; i < titleList.size(); i++) {
				element = titleList.get(i);
				String titleName = element.getAttributeValue("name");
				// 前言在下面这句代码中不适用！
				 titleName=
				 titleName.substring(titleName.indexOf("　")+"　".length());
				System.out.println("MENU_List.add(new KeyValue(\""+i+"\", \""+i+"、"+titleName+"\"));");
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void genTitleMap(String filePath) {
		SAXBuilder builder = new SAXBuilder(false);
		File file = new File(filePath);
		try {
			Document document = builder.build(file);
			Element root = document.getRootElement();
			List<Element> titleList = root.getChildren("title");
			Element element;
			for (int i = 0; i < titleList.size(); i++) {
				element = titleList.get(i);
				String titleName = element.getAttributeValue("name");
				 System.out.println(" map.put(\""+titleName+"\", "+i+");");
				;
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void parseContentXml(File orgFile) {
		SAXBuilder builder = new SAXBuilder(false);
		try {
			Document document = builder.build(orgFile);
			Element root = document.getRootElement();
			List<Element> titleList = root.getChildren("title");
			Element element;
			FileWriter writer = null;
			File file;
			for (int i = 0; i < titleList.size(); i++) {
				element = titleList.get(i);
				String titleName = element.getAttributeValue("name");
				file = new File(genFilePath, map.get(titleName) + suffix);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				if (!file.exists()) {
					file.createNewFile();
				}
				writer = new FileWriter(file);
				writer.write(element.getValue());
				writer.flush();
			}
			if (writer != null) {
				writer.close();
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
//				genTitleMap(titleFilePath);
//				genTitleKeyValue(titleFilePath);
		File baseFile = new File(baseFilePath);
		FileFilter filenameFilter = new FileFilter() {
			public boolean accept(File pathname) {
				return !pathname.equals("a00.xml");
			}
		};
		File[] fileList = baseFile.listFiles(filenameFilter);
		for (File file : fileList) {

			parseContentXml(file);
		}
//		

	}
}