package util;

import java.io.File;
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
	private static String baseFilePath = "D:\\cxl\\my apk\\__________others_________\\中华上下五千年\\res\\xml";
	private static String titleFilePath = "D:\\cxl\\my apk\\__________others_________\\中华上下五千年\\res\\xml\\a00.xml";
	private static String genFilePath = "D:\\cxl\\my apk\\__________others_________\\中华上下五千年\\res\\xml_new";
	private static String suffix = ".txt";
	private static Map<String, Integer> map = new HashMap<String, Integer>();
	static {
		File genFile = new File(genFilePath);
		if (!genFile.exists()) {
			genFile.mkdirs();
		}

		map.put("前 　　言", 0);
		map.put("００１　开天辟地的神话", 1);
		map.put("００２　钻木取火的传说", 2);
		map.put("００３　黄帝战蚩尤", 3);
		map.put("００４　尧舜让位", 4);
		map.put("００５　大禹治水", 5);
		map.put("００６　神箭手后羿", 6);
		map.put("００７　商汤和伊尹", 7);
		map.put("００８　盘庚迁都", 8);
		map.put("００９　姜太公钓鱼", 9);
		map.put("０１０　奴隶倒戈", 10);
		map.put("０１１　周公辅成王", 11);
		map.put("０１２　国人暴动", 12);
		map.put("０１３　骊山上的烽火", 13);
		map.put("０１４　囚车里的人才", 14);
		map.put("０１５　曹刿抗击齐军", 15);
		map.put("０１６　齐桓公九合诸侯", 16);
		map.put("０１７　愚蠢的宋襄公", 17);
		map.put("０１８　流亡公子重耳", 18);
		map.put("０１９　晋文公退避三舍", 19);
		map.put("０２０　弦高智退秦军", 20);
		map.put("０２１　崤山大战", 21);
		map.put("０２２　一鸣惊人的楚庄王", 22);
		map.put("０２３　伍子胥过昭关", 23);
		map.put("０２４　孔子周游列国", 24);
		map.put("０２５　勾践卧薪尝胆", 25);
		map.put("０２６　范蠡和文种", 26);
		map.put("０２７　墨子破云梯", 27);
		map.put("０２８　三家瓜分晋国", 28);
		map.put("０２９　商鞅南门立木", 29);
		map.put("０３０　孙膑庞涓斗智", 30);
		map.put("０３１　张仪拆散联盟", 31);
		map.put("０３２　赵武灵王胡服骑射", 32);
		map.put("０３３　孟尝君的门客", 33);
		map.put("０３４　燕昭王求贤", 34);
		map.put("０３５　田单的火牛阵", 35);
		map.put("０３６　屈原沉江", 36);
		map.put("０３７　蔺相如完璧归赵", 37);
		map.put("０３８　廉颇负荆请罪", 38);
		map.put("０３９　范雎的远交近攻计", 39);
		map.put("０４０　纸上谈兵的赵括", 40);
		map.put("０４１　毛遂自荐", 41);
		map.put("０４２　信陵君救赵", 42);
		map.put("０４３　李斯谏逐客", 43);
		map.put("０４４　荆轲刺秦王", 44);
		map.put("０４５　秦王灭六国", 45);
		map.put("０４６　第一个皇帝――秦始皇", 46);
		map.put("０４７　博浪沙的铁椎", 47);
		map.put("０４８　沙丘的阴谋", 48);
		map.put("０４９　大泽乡起义", 49);
		map.put("０５０　刘邦和项羽", 50);
		map.put("０５１　巨鹿大战", 51);
		map.put("０５２　刘邦进咸阳", 52);
		map.put("０５３　鸿门宴", 53);
		map.put("０５４　萧何追韩信", 54);
		map.put("０５５　楚汉相争", 55);
		map.put("０５６　霸王乌江自刎", 56);
		map.put("０５７　大风歌", 57);
		map.put("０５８　白登被围", 58);
		map.put("０５９　白马盟", 59);
		map.put("０６０　萧曹两相国", 60);
		map.put("０６１　周勃夺军", 61);
		map.put("０６２　缇萦救父", 62);
		map.put("０６３　周亚夫的细柳营", 63);
		map.put("０６４　晁错削地", 64);
		map.put("０６５　马邑诱敌战", 65);
		map.put("０６６　飞将军李广", 66);
		map.put("０６７　卫青和霍去病", 67);
		map.put("０６８　张骞通西域", 68);
		map.put("０６９　苏武牧羊", 69);
		map.put("０７０　司马迁写《史记》", 70);
		map.put("０７１　霍光辅政", 71);
		map.put("０７２　王昭君出塞", 72);
		map.put("０７３　王莽复古改制", 73);
		map.put("０７４　绿林赤眉起义", 74);
		map.put("０７５　昆阳大战", 75);
		map.put("０７６　刘秀重建汉王朝", 76);
		map.put("０７７　硬脖子的洛阳令", 77);
		map.put("０７８　老当益壮的将军", 78);
		map.put("０７９　取经求佛像", 79);
		map.put("０８０　班超投笔从军", 80);
		map.put("０８１　张衡和地动仪", 81);
		map.put("０８２　跋扈将军梁冀", 82);
		map.put("０８３　“党锢”事件", 83);
		map.put("０８４　范滂进监狱", 84);
		map.put("０８５　黄巾军起义", 85);
		map.put("０８６　袁绍杀宦官", 86);
		map.put("０８７　曹操起兵", 87);
		map.put("０８８　王允计除董卓", 88);
		map.put("０８９　迁都许城", 89);
		map.put("０９０　衣带里的密诏", 90);
		map.put("０９１　官渡大战", 91);
		map.put("０９２　孙策占据江东", 92);
		map.put("０９３　诸葛亮隆中对策", 93);
		map.put("０９４　周瑜火攻赤壁", 94);
		map.put("０９５　华佗治病", 95);
		map.put("０９６　刘备进益州", 96);
		map.put("０９７　蔡文姬归汉", 97);
		map.put("０９８　关羽水淹七军", 98);
		map.put("０９９　吕蒙白衣渡江", 99);
		map.put("１００　曹植七步成诗", 100);
		map.put("１０１　陆逊烧连营", 101);
		map.put("１０２　七擒孟获", 102);
		map.put("１０３　马谡失街亭", 103);
		map.put("１０４　五丈原", 104);
		map.put("１０５　司马懿装病", 105);
		map.put("１０６　司马昭的野心", 106);
		map.put("１０７　邓艾偷渡剑阁", 107);
		map.put("１０８　扶不起的阿斗", 108);
		map.put("１０９　王?楼船破吴", 109);
		map.put("１１０　石崇王恺比富", 110);
		map.put("１１１　周处除“三害”", 111);
		map.put("１１２　白痴皇帝", 112);
		map.put("１１３　八王混战", 113);
		map.put("１１４　李特的流民大营", 114);
		map.put("１１５　匈奴人称汉帝", 115);
		map.put("１１６　闻鸡起舞", 116);
		map.put("１１７　王马共天下", 117);
		map.put("１１８　石勒读汉书", 118);
		map.put("１１９　祖逖中流击楫", 119);
		map.put("１２０　陶侃运砖头", 120);
		map.put("１２１　王羲之写字换鹅", 121);
		map.put("１２２　桓温北伐", 122);
		map.put("１２３　王猛扪虱谈天下", 123);
		map.put("１２４　一意孤行的苻坚", 124);
		map.put("１２５　谢安东山再起", 125);
		map.put("１２６　淝水之战", 126);
		map.put("１２７　陶渊明不折腰", 127);
		map.put("１２８　刘裕摆却月阵", 128);
		map.put("１２９　檀道济唱筹量沙", 129);
		map.put("１３０　说实话的高允", 130);
		map.put("１３１　大发明家祖冲之", 131);
		map.put("１３２　范缜反对迷信", 132);
		map.put("１３３　魏孝文帝改革风俗", 133);
		map.put("１３４　北魏的分裂", 134);
		map.put("１３５　梁武帝做和尚", 135);
		map.put("１３６　反复无常的侯景", 136);
		map.put("１３７　陈后主亡国", 137);
		map.put("１３８　赵绰依法办事", 138);
		map.put("１３９　隋炀帝游江都", 139);
		map.put("１４０　李密牛角挂书", 140);
		map.put("１４１　瓦岗军开仓分粮", 141);
		map.put("１４２　李渊太原起兵", 142);
		map.put("１４３　李世民取东都", 143);
		map.put("１４４　玄武门之变", 144);
		map.put("１４５　魏征直言敢谏", 145);
		map.put("１４６　李靖夜袭阴山", 146);
		map.put("１４７　玄奘和尚取经", 147);
		map.put("１４８　文成公主进吐蕃", 148);
		map.put("１４９　女皇帝武则天", 149);
		map.put("１５０　请君入瓮", 150);
		map.put("１５１　狄仁杰桃李满门", 151);
		map.put("１５２　张说不做伪证", 152);
		map.put("１５３　姚崇灭蝗", 153);
		map.put("１５４　口蜜腹剑的李林甫", 154);
		map.put("１５５　李白蔑视权贵", 155);
		map.put("１５６　安禄山叛乱", 156);
		map.put("１５７　颜杲卿骂贼", 157);
		map.put("１５８　马嵬驿兵变", 158);
		map.put("１５９　张巡草人借箭", 159);
		map.put("１６０　南霁云借兵", 160);
		map.put("１６１　李泌归山", 161);
		map.put("１６２　李光弼大破史思明", 162);
		map.put("１６３　杜甫写“诗史”", 163);
		map.put("１６４　段秀实不怕强暴", 164);
		map.put("１６５　郭子仪单骑退回纥", 165);
		map.put("１６６　颜真卿刚强不屈", 166);
		map.put("１６７　浑?和李晟", 167);
		map.put("１６８　东宫里的棋手", 168);
		map.put("１６９　刘禹锡游玄都观", 169);
		map.put("１７０　白居易进长安", 170);
		map.put("１７１　李?雪夜下蔡州", 171);
		map.put("１７２　韩愈反对迎佛骨", 172);
		map.put("１７３　甘露事件", 173);
		map.put("１７４　朋党的争吵", 174);
		map.put("１７５　冲天大将军黄巢", 175);
		map.put("１７６　唐王朝的末日", 176);
		map.put("１７７　“海龙王”钱?", 177);
		map.put("１７８　伶人做官", 178);
		map.put("１７９　“儿皇帝”石敬瑭", 179);
		map.put("１８０　周世宗斥冯道", 180);
		map.put("１８１　黄袍加身", 181);
		map.put("１８２　杯酒释兵权", 182);
		map.put("１８３　李后主亡国", 183);
		map.put("１８４　赵普收礼", 184);
		map.put("１８５　杨无敌", 185);
		map.put("１８６　王小波起义", 186);
		map.put("１８７　寇准抗辽", 187);
		map.put("１８８　元昊建立西夏", 188);
		map.put("１８９　狄青不怕出身低", 189);
		map.put("１９０　范仲淹实行新政", 190);
		map.put("１９１　欧阳修改革文风", 191);
		map.put("１９２　铁面无私的包拯", 192);
		map.put("１９３　王安石变法", 193);
		map.put("１９４　沈括出使", 194);
		map.put("１９５　司马光写《通鉴》", 195);
		map.put("１９６　苏东坡游赤壁", 196);
		map.put("１９７　花石纲", 197);
		map.put("１９８　方腊起义", 198);
		map.put("１９９　头鱼宴上的阿骨打", 199);
		map.put("２００　李纲守东京", 200);
		map.put("２０１　太学生请愿", 201);
		map.put("２０２　两个皇帝当俘虏", 202);
		map.put("２０３　宗泽三呼“过河”", 203);
		map.put("２０４　女词人李清照", 204);
		map.put("２０５　韩世忠阻击金兵", 205);
		map.put("２０６　岳家军大破兀术", 206);
		map.put("２０７　卖国贼秦桧", 207);
		map.put("２０８　“莫须有”冤狱", 208);
		map.put("２０９　钟相杨么起义", 209);
		map.put("２１０　虞允文书生退敌", 210);
		map.put("２１１　辛弃疾活捉叛徒", 211);
		map.put("２１２　陆游临终留诗", 212);
		map.put("２１３　成吉思汗统一蒙古", 213);
		map.put("２１４　贾似道误国", 214);
		map.put("２１５　文天祥起兵", 215);
		map.put("２１６　张世杰死守?山", 216);
		map.put("２１７　正气歌", 217);
		map.put("２１８　郭守敬修订历法", 218);
		map.put("２１９　欧洲来客马可?波罗", 219);
		map.put("２２０　《窦娥冤》感天动地", 220);
		map.put("２２１　一只眼的石人", 221);
		map.put("２２２　和尚当元帅", 222);
		map.put("２２３　鄱阳湖大战", 223);
		map.put("２２４　刘伯温求雨", 224);
		map.put("２２５　胡维庸案件", 225);
		map.put("２２６　燕王进南京", 226);
		map.put("２２７　三保太监下西洋", 227);
		map.put("２２８　土木堡的惨败", 228);
		map.put("２２９　于谦保卫北京", 229);
		map.put("２３０　杨一清计除刘瑾", 230);
		map.put("２３１　杨继盛冒死劾严嵩", 231);
		map.put("２３２　海瑞刚正不阿", 232);
		map.put("２３３　戚继光驱逐倭寇", 233);
		map.put("２３４　李时珍上山采药", 234);
		map.put("２３５　张居正辅政", 235);
		map.put("２３６　葛贤痛打税监", 236);
		map.put("２３７　努尔哈赤建立后金", 237);
		map.put("２３８　萨尔浒大战", 238);
		map.put("２３９　徐光启研究西学", 239);
		map.put("２４０　左光斗入狱", 240);
		map.put("２４１　五人墓", 241);
		map.put("２４２　袁崇焕大战宁远", 242);
		map.put("２４３　皇太极施反间计", 243);
		map.put("２４４　徐霞客远游探险", 244);
		map.put("２４５　闯王李自成", 245);
		map.put("２４６　卢象?战死巨鹿", 246);
		map.put("２４７　张献忠奇袭襄阳", 247);
		map.put("２４８　李岩和红娘子", 248);
		map.put("２４９　吴三桂借清兵", 249);
		map.put("２５０　史可法死守扬州", 250);
		map.put("２５１　夏完淳怒斥洪承畴", 251);
		map.put("２５２　郑成功收复台湾", 252);
		map.put("２５３　李定国转战西南", 253);
		map.put("２５４　康熙帝平定三藩", 254);
		map.put("２５５　雅克萨的胜利", 255);
		map.put("２５６　三征噶尔丹", 256);
		map.put("２５７　顾炎武著书立说", 257);
		map.put("２５８　文字狱", 258);
		map.put("２５９　乾隆帝禁书修书", 259);
		map.put("２６０　曹雪芹写《红楼梦》", 260);
		map.put("２６１　大贪官和?", 261);
		map.put("２６２　女英雄王聪儿", 262);

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
				// System.out.println(" map.put(\""+titleName+"\", "+i+");");
				System.out.println("MENU_List.add(new KeyValue(\""+i+"\", \""+i+"、"+titleName+"\"));");
				;
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
				// 前言在下面这句代码中不适用！
				// titleName=
				// titleName.substring(titleName.indexOf("　")+"　".length());
				// System.out.println(" map.put(\""+titleName+"\", "+i+");");
				System.out.println(element.getValue());
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
				genTitleKeyValue(titleFilePath);
//		File baseFile = new File(baseFilePath);
//		FileFilter filenameFilter = new FileFilter() {
//			public boolean accept(File pathname) {
//				return !pathname.equals("a00.xml");
//			}
//		};
//		File[] fileList = baseFile.listFiles(filenameFilter);
//		for (File file : fileList) {
//
//			parseContentXml(file);
//		}

	}
}