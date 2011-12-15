package com.cxl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cxl.gshcb.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("0", "0、名人效应"));
		MENU_List.add(new KeyValue("1", "1、成功的基石"));
		MENU_List.add(new KeyValue("2", "2、突破时间"));
		MENU_List.add(new KeyValue("3", "3、成功的脚印"));
		MENU_List.add(new KeyValue("4", "4、神秘之结"));
		MENU_List.add(new KeyValue("5", "5、跳出死胡同"));
		MENU_List.add(new KeyValue("6", "6、自信的理由"));
		MENU_List.add(new KeyValue("7", "7、一美元与八颗牙"));
		MENU_List.add(new KeyValue("8", "8、出路与退路"));
		MENU_List.add(new KeyValue("9", "9、感激冤家和对手"));
		MENU_List.add(new KeyValue("10", "10、妥协就会成功"));
		MENU_List.add(new KeyValue("11", "11、惟有成功不可复制"));
		MENU_List.add(new KeyValue("12", "12、哈佛憾事"));
		MENU_List.add(new KeyValue("13", "13、通用公司服务客户的故事"));
		MENU_List.add(new KeyValue("14", "14、低姿态"));
		MENU_List.add(new KeyValue("15", "15、肯德基的特殊顾客"));
		MENU_List.add(new KeyValue("16", "16、振奋过中国人的标语口号"));
		MENU_List.add(new KeyValue("17", "17、年终奖金"));
		MENU_List.add(new KeyValue("18", "18、目标的威力"));
		MENU_List.add(new KeyValue("19", "19、敲打吊着的铁球"));
		MENU_List.add(new KeyValue("20", "20、苍蝇逃生的启迪"));
		MENU_List.add(new KeyValue("21", "21、门就进去"));
		MENU_List.add(new KeyValue("22", "22、穷人最缺少的是什么"));
		MENU_List.add(new KeyValue("23", "23、要么竭尽全力，要么干脆放弃"));
		MENU_List.add(new KeyValue("24", "24、比目标远一点点"));
		MENU_List.add(new KeyValue("25", "25、“一念之间”的成功"));
		MENU_List.add(new KeyValue("26", "26、成功的真谛"));
		MENU_List.add(new KeyValue("27", "27、简单的道理"));
		MENU_List.add(new KeyValue("28", "28、花瓶与木桶"));
		MENU_List.add(new KeyValue("29", "29、不欺小节"));
		MENU_List.add(new KeyValue("30", "30、神奇的陪练"));
		MENU_List.add(new KeyValue("31", "31、偶然的成功"));
		MENU_List.add(new KeyValue("32", "32、一粒糖果的诱惑"));
		MENU_List.add(new KeyValue("33", "33、多坚持一刻"));
		MENU_List.add(new KeyValue("34", "34、等待第二个春天"));
		MENU_List.add(new KeyValue("35", "35、看你想去的地方"));
		MENU_List.add(new KeyValue("36", "36、如果你比对手更专注"));
		MENU_List.add(new KeyValue("37", "37、福特的“吝啬”"));
		MENU_List.add(new KeyValue("38", "38、启示"));
		MENU_List.add(new KeyValue("39", "39、花瓶与木桶"));
		MENU_List.add(new KeyValue("40", "40、不欺小节"));
		MENU_List.add(new KeyValue("41", "41、神奇的陪练"));
		MENU_List.add(new KeyValue("42", "42、偶然的成功"));
		MENU_List.add(new KeyValue("43", "43、一粒糖果的诱惑"));
		MENU_List.add(new KeyValue("44", "44、多坚持一刻"));
		MENU_List.add(new KeyValue("45", "45、纸和纸篓"));
		MENU_List.add(new KeyValue("46", "46、每秒摆一下"));
		MENU_List.add(new KeyValue("47", "47、过 河"));
		MENU_List.add(new KeyValue("48", "48、给糖哲学"));
		MENU_List.add(new KeyValue("49", "49、分段实现大目标"));
		MENU_List.add(new KeyValue("50", "50、做别人没有做过的事"));
		MENU_List.add(new KeyValue("51", "51、两个开发商"));
		MENU_List.add(new KeyValue("52", "52、时间和爱的故事"));
		MENU_List.add(new KeyValue("53", "53、不要等到比原来还少"));
		MENU_List.add(new KeyValue("54", "54、果 断"));
		MENU_List.add(new KeyValue("55", "55、每次被拒绝的收入"));
		MENU_List.add(new KeyValue("56", "56、青蛙托尔斯泰的飞行训练"));
		MENU_List.add(new KeyValue("57", "57、野鸭与苍鹰"));
		MENU_List.add(new KeyValue("58", "58、画虎"));
		MENU_List.add(new KeyValue("59", "59、热忱是种重要的力量"));
		MENU_List.add(new KeyValue("60", "60、好学不倦"));
		MENU_List.add(new KeyValue("61", "61、拐弯处的发现"));
		MENU_List.add(new KeyValue("62", "62、一张椅子"));
		MENU_List.add(new KeyValue("63", "63、自信"));
		MENU_List.add(new KeyValue("64", "64、人生的试金石"));
		MENU_List.add(new KeyValue("65", "65、目标"));
		MENU_List.add(new KeyValue("66", "66、需要胆识"));
		MENU_List.add(new KeyValue("67", "67、“回避”也是生活的艺术"));
		MENU_List.add(new KeyValue("68", "68、你知道你错过了什么吗"));
		MENU_List.add(new KeyValue("69", "69、寻宝"));
		MENU_List.add(new KeyValue("70", "70、别把困难在想象中放大"));
		MENU_List.add(new KeyValue("71", "71、想象人生"));
		MENU_List.add(new KeyValue("72", "72、通往成功的蛛丝马迹"));
		MENU_List.add(new KeyValue("73", "73、寻找机会"));
		MENU_List.add(new KeyValue("74", "74、两棵树的命运"));
		MENU_List.add(new KeyValue("75", "75、明确的目标"));
		MENU_List.add(new KeyValue("76", "76、野兔和猎狗"));
		MENU_List.add(new KeyValue("77", "77、狮子和羚羊的家教"));
		MENU_List.add(new KeyValue("78", "78、剑客"));
		MENU_List.add(new KeyValue("79", "79、优质的服务"));
		MENU_List.add(new KeyValue("80", "80、成功的秘密"));
		MENU_List.add(new KeyValue("81", "81、习惯"));
		MENU_List.add(new KeyValue("82", "82、自己先站起来"));
		MENU_List.add(new KeyValue("83", "83、大鱼吃小鱼"));
		MENU_List.add(new KeyValue("84", "84、分粥"));
		MENU_List.add(new KeyValue("85", "85、勇于冒险"));
		MENU_List.add(new KeyValue("86", "86、心中的顽石"));
		MENU_List.add(new KeyValue("87", "87、一只苹果"));
		MENU_List.add(new KeyValue("88", "88、选择方向"));
		MENU_List.add(new KeyValue("89", "89、将脑袋打开一毫米"));
		MENU_List.add(new KeyValue("90", "90、担当风险"));
		MENU_List.add(new KeyValue("91", "91、唯一可以依靠的就是你们"));
		MENU_List.add(new KeyValue("92", "92、享受成功的过程"));
		MENU_List.add(new KeyValue("93", "93、购买“设想”"));
		MENU_List.add(new KeyValue("94", "94、试穿的魅力"));
		MENU_List.add(new KeyValue("95", "95、成功的“本能”"));
		MENU_List.add(new KeyValue("96", "96、没有不受伤的船"));
		MENU_List.add(new KeyValue("97", "97、一条没有鱼鳔的鱼"));
		MENU_List.add(new KeyValue("98", "98、成功者的黑夜"));
		MENU_List.add(new KeyValue("99", "99、山谷的起点"));
		MENU_List.add(new KeyValue("100", "100、佛罗里达州的响尾蛇"));
		MENU_List.add(new KeyValue("101", "101、铃儿永远响叮当"));
		MENU_List.add(new KeyValue("102", "102、只剩一只眼可以眨"));
		MENU_List.add(new KeyValue("103", "103、渔王的儿子"));
		MENU_List.add(new KeyValue("104", "104、为了一个梦"));
		MENU_List.add(new KeyValue("105", "105、高尔夫球的凹点"));
		MENU_List.add(new KeyValue("106", "106、永远的一课"));
		MENU_List.add(new KeyValue("107", "107、驴的哲学"));
		MENU_List.add(new KeyValue("108", "108、从地下室爬上来的百万富翁"));
		MENU_List.add(new KeyValue("109", "109、劣势 优势"));
		MENU_List.add(new KeyValue("110", "110、把库存的商品热销出去"));
		MENU_List.add(new KeyValue("111", "111、向自己突围"));
		MENU_List.add(new KeyValue("112", "112、别在冬天里砍树"));
		MENU_List.add(new KeyValue("113", "113、沃尔曼试金石"));
		MENU_List.add(new KeyValue("114", "114、坦言失败就是成功的开始"));
		MENU_List.add(new KeyValue("115", "115、生命的脊梁"));
		MENU_List.add(new KeyValue("116", "116、美金改变两个人"));
		MENU_List.add(new KeyValue("117", "117、学习长颈鹿重新站起来"));
		MENU_List.add(new KeyValue("118", "118、大海里的船"));
		MENU_List.add(new KeyValue("119", "119、得与失"));
		MENU_List.add(new KeyValue("120", "120、不闭眼睛的青蛙"));
		MENU_List.add(new KeyValue("121", "121、神迹"));
		MENU_List.add(new KeyValue("122", "122、老鹰的再生"));
		MENU_List.add(new KeyValue("123", "123、非洲土人穿鞋"));
		MENU_List.add(new KeyValue("124", "124、弯曲"));
		MENU_List.add(new KeyValue("125", "125、危机"));
		MENU_List.add(new KeyValue("126", "126、在脚下多垫一块砖头"));
		MENU_List.add(new KeyValue("127", "127、这也会过去"));
		MENU_List.add(new KeyValue("128", "128、飞蛾的痛苦经历"));
		MENU_List.add(new KeyValue("129", "129、没有忘记爱美"));
		MENU_List.add(new KeyValue("130", "130、在绝境中遭遇的奇观"));
		MENU_List.add(new KeyValue("131", "131、困境即是赐予"));
		MENU_List.add(new KeyValue("132", "132、微不足道的第一步"));
		MENU_List.add(new KeyValue("133", "133、信念的力量"));
		MENU_List.add(new KeyValue("134", "134、人生没有放弃努力的借口"));
		MENU_List.add(new KeyValue("135", "135、为什么那只山羊爱吃那种红浆果……"));
		MENU_List.add(new KeyValue("136", "136、“感谢”羞辱"));
		MENU_List.add(new KeyValue("137", "137、跨越障碍"));
		MENU_List.add(new KeyValue("138", "138、厉司河卜者"));
		MENU_List.add(new KeyValue("139", "139、记住，你用不着跑在任何人后面"));
		MENU_List.add(new KeyValue("140", "140、成功是给能够坚持到最后的人的"));
		MENU_List.add(new KeyValue("141", "141、贼"));
		MENU_List.add(new KeyValue("142", "142、领导就是关注对方的感受？"));
		MENU_List.add(new KeyValue("143", "143、目标的重要性"));
		MENU_List.add(new KeyValue("144", "144、赚钱智慧只需一点点"));
		MENU_List.add(new KeyValue("145", "145、黑石头白石头"));
		MENU_List.add(new KeyValue("146", "146、上帝偏爱她，让她洗厕所"));
		MENU_List.add(new KeyValue("147", "147、何谓天堂"));
		MENU_List.add(new KeyValue("148", "148、谁在陪着你"));
		MENU_List.add(new KeyValue("149", "149、一家闻名全球的鱼铺"));
		MENU_List.add(new KeyValue("150", "150、你也能成为亿万富翁"));
		MENU_List.add(new KeyValue("151", "151、一只巴掌也能拍响"));
		MENU_List.add(new KeyValue("152", "152、你为什么不能成为未来的百万富翁？"));
		MENU_List.add(new KeyValue("153", "153、发现就是成功之门"));
		MENU_List.add(new KeyValue("154", "154、耕自己的田"));
		MENU_List.add(new KeyValue("155", "155、勇气是成功的前提"));
		MENU_List.add(new KeyValue("156", "156、成功的秘诀"));
		MENU_List.add(new KeyValue("157", "157、像邱吉尔说的那样去做"));
		MENU_List.add(new KeyValue("158", "158、成功一定有方法"));
		MENU_List.add(new KeyValue("159", "159、金子与大蒜――抢占先机是取胜的灵魂"));
		MENU_List.add(new KeyValue("160", "160、成功者永不放弃，放弃者决不成功"));
		MENU_List.add(new KeyValue("161", "161、成功的机遇就在身边"));
		MENU_List.add(new KeyValue("162", "162、危机"));
		MENU_List.add(new KeyValue("163", "163、弯曲"));
		MENU_List.add(new KeyValue("164", "164、用不寻常的视角去观察寻常的事物"));
		MENU_List.add(new KeyValue("165", "165、高原上的苹果"));
		MENU_List.add(new KeyValue("166", "166、骑马思维"));
		MENU_List.add(new KeyValue("167", "167、向右走"));
		MENU_List.add(new KeyValue("168", "168、你是千里马，但你还得叫"));
		MENU_List.add(new KeyValue("169", "169、新的生活才刚刚开始"));
		MENU_List.add(new KeyValue("170", "170、煮“石头汤”"));
		MENU_List.add(new KeyValue("171", "171、屡战屡败”与“屡败屡战”"));
		MENU_List.add(new KeyValue("172", "172、绕过壁垒，成为领跑者"));
		MENU_List.add(new KeyValue("173", "173、神奇的格言"));
		MENU_List.add(new KeyValue("174", "174、你能实现梦想"));
		MENU_List.add(new KeyValue("175", "175、没有什么不可能"));
		MENU_List.add(new KeyValue("176", "176、握手"));
		MENU_List.add(new KeyValue("177", "177、改变你的策略"));
		MENU_List.add(new KeyValue("178", "178、低姿态进入"));
		MENU_List.add(new KeyValue("179", "179、一毫米的自信"));
		MENU_List.add(new KeyValue("180", "180、敢于妄想"));
		MENU_List.add(new KeyValue("181", "181、困境中，不要羞于求助"));
		MENU_List.add(new KeyValue("182", "182、过不了河就摘一枚果子"));
		MENU_List.add(new KeyValue("183", "183、把专利卖给布什"));
		MENU_List.add(new KeyValue("184", "184、巴黎的画廊咖啡厅"));
		MENU_List.add(new KeyValue("185", "185、广告的效应"));
		MENU_List.add(new KeyValue("186", "186、千金一课"));
		MENU_List.add(new KeyValue("187", "187、改变生命的三个字"));
		MENU_List.add(new KeyValue("188", "188、车祸的启示"));
		MENU_List.add(new KeyValue("189", "189、明天照样会有报纸"));
		MENU_List.add(new KeyValue("190", "190、拼命的战场"));
		MENU_List.add(new KeyValue("191", "191、方向决定成败"));
		MENU_List.add(new KeyValue("192", "192、推销的秘诀"));
		MENU_List.add(new KeyValue("193", "193、蝴蝶与大陆"));
		MENU_List.add(new KeyValue("194", "194、帝国亡于细节"));
		MENU_List.add(new KeyValue("195", "195、朋友与财富"));
		MENU_List.add(new KeyValue("196", "196、知己知彼"));
		MENU_List.add(new KeyValue("197", "197、你吃的是第几只鸭？"));
		MENU_List.add(new KeyValue("198", "198、一次只做一件事"));
		MENU_List.add(new KeyValue("199", "199、我不是来推销的"));
		MENU_List.add(new KeyValue("200", "200、欲与之　先取之"));
		MENU_List.add(new KeyValue("201", "201、维他命的奇效"));
		MENU_List.add(new KeyValue("202", "202、排在第二也不错"));
		MENU_List.add(new KeyValue("203", "203、5年以后你在做什么"));
		MENU_List.add(new KeyValue("204", "204、惩罚"));
		MENU_List.add(new KeyValue("205", "205、我不爱跳伞"));
		MENU_List.add(new KeyValue("206", "206、使对方立即说“是”"));
		MENU_List.add(new KeyValue("207", "207、独特的眼光比知识更重要"));
		MENU_List.add(new KeyValue("208", "208、霍布森选择"));
		MENU_List.add(new KeyValue("209", "209、你一定要拒绝冰淇淋的美味"));
		MENU_List.add(new KeyValue("210", "210、每次都是初相识"));
		MENU_List.add(new KeyValue("211", "211、好运气缘何降临七次"));
		MENU_List.add(new KeyValue("212", "212、带着梦想起飞"));
		MENU_List.add(new KeyValue("213", "213、谁是盲人"));
		MENU_List.add(new KeyValue("214", "214、做变色龙还是做恐龙"));
		MENU_List.add(new KeyValue("215", "215、每日一得"));
		MENU_List.add(new KeyValue("216", "216、推销员的智慧"));
		MENU_List.add(new KeyValue("217", "217、乌鸦猎羊"));
		MENU_List.add(new KeyValue("218", "218、侥幸的几率"));
		MENU_List.add(new KeyValue("219", "219、你想要双门还是四门轿车？"));
		MENU_List.add(new KeyValue("220", "220、只有“付出”，才能“杰出”"));
		MENU_List.add(new KeyValue("221", "221、你的位置在哪里"));
		MENU_List.add(new KeyValue("222", "222、成功只是多说一句话"));
		MENU_List.add(new KeyValue("223", "223、突破心障"));
		MENU_List.add(new KeyValue("224", "224、天生我才必有用"));
		MENU_List.add(new KeyValue("225", "225、安然的总裁"));
		MENU_List.add(new KeyValue("226", "226、责任感创造奇迹"));
		MENU_List.add(new KeyValue("227", "227、不要往前后左右看"));
		MENU_List.add(new KeyValue("228", "228、右手比左手大４％"));
		MENU_List.add(new KeyValue("229", "229、穿针心理"));
		MENU_List.add(new KeyValue("230", "230、在赌场门口经营肠粉"));
		MENU_List.add(new KeyValue("231", "231、董事长的智慧"));
		MENU_List.add(new KeyValue("232", "232、凡事往好处想"));
		MENU_List.add(new KeyValue("233", "233、给芝麻加上糖"));
		MENU_List.add(new KeyValue("234", "234、一辈子只做一碗汤"));
		MENU_List.add(new KeyValue("235", "235、坚持是成功前的状态"));
		MENU_List.add(new KeyValue("236", "236、只赚一分钱"));
		MENU_List.add(new KeyValue("237", "237、天才不相信结局"));
		MENU_List.add(new KeyValue("238", "238、老天爱笨小孩"));
		MENU_List.add(new KeyValue("239", "239、茶杯上的专业"));
		MENU_List.add(new KeyValue("240", "240、真希望第七次再看到你"));
		MENU_List.add(new KeyValue("241", "241、“外卖”的世界"));
		MENU_List.add(new KeyValue("242", "242、垫脚石"));
		MENU_List.add(new KeyValue("243", "243、你不能失败"));
		MENU_List.add(new KeyValue("244", "244、竞标"));
		MENU_List.add(new KeyValue("245", "245、我不是乞丐"));
		MENU_List.add(new KeyValue("246", "246、把敌人变成朋友"));
		MENU_List.add(new KeyValue("247", "247、男人小便不要说话"));
		MENU_List.add(new KeyValue("248", "248、引水与引智"));
		MENU_List.add(new KeyValue("249", "249、从“无用”中发现“有用”的价值"));
		MENU_List.add(new KeyValue("250", "250、一百倍和一千倍"));
		MENU_List.add(new KeyValue("251", "251、意外的成功"));
		MENU_List.add(new KeyValue("252", "252、发明家的第一桶金"));
		MENU_List.add(new KeyValue("253", "253、富婆的诞生"));
		MENU_List.add(new KeyValue("254", "254、一枚钻戒"));
		MENU_List.add(new KeyValue("255", "255、这也会过去"));
		MENU_List.add(new KeyValue("256", "256、距离金子三英寸"));
		MENU_List.add(new KeyValue("257", "257、我要为你修剪草坪"));
		MENU_List.add(new KeyValue("258", "258、寻找圣人"));
		MENU_List.add(new KeyValue("259", "259、富翁的“炼金术”"));
		MENU_List.add(new KeyValue("260", "260、换个想法，便能换来一切"));
		MENU_List.add(new KeyValue("261", "261、一枚硬币改写命运"));
		MENU_List.add(new KeyValue("262", "262、你为什么是穷人"));
		MENU_List.add(new KeyValue("263", "263、“天价”促销术"));
		MENU_List.add(new KeyValue("264", "264、成功需要积累"));
		MENU_List.add(new KeyValue("265", "265、成功的公式"));
		MENU_List.add(new KeyValue("266", "266、在嘲笑中升值"));
		MENU_List.add(new KeyValue("267", "267、空间的价值"));
		MENU_List.add(new KeyValue("268", "268、“麦当劳”成功的启示"));
		MENU_List.add(new KeyValue("269", "269、迎接潮水"));
		MENU_List.add(new KeyValue("270", "270、起死回生的十二个字"));
		MENU_List.add(new KeyValue("271", "271、把人情味摆上货架"));
		MENU_List.add(new KeyValue("272", "272、把失败写在背面"));
		MENU_List.add(new KeyValue("273", "273、细节让你如此美丽"));
		MENU_List.add(new KeyValue("274", "274、时刻准备着"));
		MENU_List.add(new KeyValue("275", "275、猜猜哪位名人会来"));
		MENU_List.add(new KeyValue("276", "276、不做星星"));
		MENU_List.add(new KeyValue("277", "277、用你的心去跳舞"));
		MENU_List.add(new KeyValue("278", "278、无用之用"));
		MENU_List.add(new KeyValue("279", "279、笑迎挑战"));
		MENU_List.add(new KeyValue("280", "280、我不感到意外"));
		MENU_List.add(new KeyValue("281", "281、简单的办法"));
		MENU_List.add(new KeyValue("282", "282、全力以赴的弊端"));
		MENU_List.add(new KeyValue("283", "283、多努力一次"));
		MENU_List.add(new KeyValue("284", "284、成功需要多少时间"));
		MENU_List.add(new KeyValue("285", "285、你在为谁打工？"));
		MENU_List.add(new KeyValue("286", "286、成功缘于一杯酒"));
		MENU_List.add(new KeyValue("287", "287、找到适合你奔跑的那双鞋"));
		MENU_List.add(new KeyValue("288", "288、坦言失败的魅力"));
		MENU_List.add(new KeyValue("289", "289、为梦想打工"));
		MENU_List.add(new KeyValue("290", "290、一辈子的笑话"));
		MENU_List.add(new KeyValue("291", "291、意料之外的成功"));
		MENU_List.add(new KeyValue("292", "292、将军为什么输给了士兵"));
		MENU_List.add(new KeyValue("293", "293、卖色"));
		MENU_List.add(new KeyValue("294", "294、天使来信"));
		MENU_List.add(new KeyValue("295", "295、我谋杀了一条泥鳅"));
		MENU_List.add(new KeyValue("296", "296、最佳记者"));
		MENU_List.add(new KeyValue("297", "297、计划不如实践"));
		MENU_List.add(new KeyValue("298", "298、请别单独用餐"));
		MENU_List.add(new KeyValue("299", "299、兔子不背乌龟的壳"));
		MENU_List.add(new KeyValue("300", "300、天堂与地狱比邻"));
		MENU_List.add(new KeyValue("301", "301、预见未来"));
		MENU_List.add(new KeyValue("302", "302、信心的力量"));
		MENU_List.add(new KeyValue("303", "303、利用他人的思维"));
		MENU_List.add(new KeyValue("304", "304、骑单车的老板"));
		MENU_List.add(new KeyValue("305", "305、奇迹美分"));
		MENU_List.add(new KeyValue("306", "306、世界上又少了一对孤独的人"));
		MENU_List.add(new KeyValue("307", "307、锁住的冰柜"));
		MENU_List.add(new KeyValue("308", "308、在人造卫星上做广告"));
		MENU_List.add(new KeyValue("309", "309、坚定的后果"));
		MENU_List.add(new KeyValue("310", "310、灵光闪过之后"));
		MENU_List.add(new KeyValue("311", "311、错失的机会"));
		MENU_List.add(new KeyValue("312", "312、上半杯给别人"));
		MENU_List.add(new KeyValue("313", "313、在嘘声中唱完一首歌"));
		MENU_List.add(new KeyValue("314", "314、第三名是个旁听生"));
		MENU_List.add(new KeyValue("315", "315、沙漏"));
		MENU_List.add(new KeyValue("316", "316、抱一抱我"));
		MENU_List.add(new KeyValue("317", "317、勒比格勒的坏笑"));
		MENU_List.add(new KeyValue("318", "318、胆为先"));
		MENU_List.add(new KeyValue("319", "319、成功，从煮花生开始"));
		MENU_List.add(new KeyValue("320", "320、尽职的调度员"));
		MENU_List.add(new KeyValue("321", "321、错失良机"));
		MENU_List.add(new KeyValue("322", "322、但求破衣里面是人"));
		MENU_List.add(new KeyValue("323", "323、你不能只为别人鼓掌"));
		MENU_List.add(new KeyValue("324", "324、捉弄"));
		MENU_List.add(new KeyValue("325", "325、大玻璃与大塌方"));
		MENU_List.add(new KeyValue("326", "326、先敲门，再想怎么说"));
		MENU_List.add(new KeyValue("327", "327、小虫的启示"));
		MENU_List.add(new KeyValue("328", "328、别指出别人的错误"));
		MENU_List.add(new KeyValue("329", "329、籽麻糖的赚钱故事"));
		MENU_List.add(new KeyValue("330", "330、搬家"));
		MENU_List.add(new KeyValue("331", "331、职责有限"));
		MENU_List.add(new KeyValue("332", "332、你有那块石头吗"));
		MENU_List.add(new KeyValue("333", "333、思考的价值"));
		MENU_List.add(new KeyValue("334", "334、付出是痛苦的“解药”"));
		MENU_List.add(new KeyValue("335", "335、你的拇指就是很好的标尺"));
		MENU_List.add(new KeyValue("336", "336、你不能没有激情与勇气"));
		MENU_List.add(new KeyValue("337", "337、成功，就是多读一本书"));
		MENU_List.add(new KeyValue("338", "338、赚钱是什么意思"));
		MENU_List.add(new KeyValue("339", "339、成功的钥匙"));
		MENU_List.add(new KeyValue("340", "340、珍惜小成功"));
		MENU_List.add(new KeyValue("341", "341、领头羊是这样练成的"));
		MENU_List.add(new KeyValue("342", "342、请挫折“上一堂课”"));
		MENU_List.add(new KeyValue("343", "343、音乐世家"));
		MENU_List.add(new KeyValue("344", "344、敲动生命的大铁球"));
		MENU_List.add(new KeyValue("345", "345、成功从脱鞋开始"));
		MENU_List.add(new KeyValue("346", "346、没有靠背的椅子"));
		MENU_List.add(new KeyValue("347", "347、穷，也要站在富人堆里？！"));
		MENU_List.add(new KeyValue("348", "348、抓住梦想"));
		MENU_List.add(new KeyValue("349", "349、不接受拒绝"));
		MENU_List.add(new KeyValue("350", "350、倾斜的商机"));
		MENU_List.add(new KeyValue("351", "351、超值的赠品"));
		MENU_List.add(new KeyValue("352", "352、一只易拉罐改变人生"));
		MENU_List.add(new KeyValue("353", "353、没有人能使你倒下"));
		MENU_List.add(new KeyValue("354", "354、找到下一个说是的人"));
		MENU_List.add(new KeyValue("355", "355、哦，原来你不是卓别林"));
		MENU_List.add(new KeyValue("356", "356、钟点工"));
		MENU_List.add(new KeyValue("357", "357、简单的方法"));
		MENU_List.add(new KeyValue("358", "358、一支桨也可以遨游沧海"));
		MENU_List.add(new KeyValue("359", "359、每个人都有两张照片"));
		MENU_List.add(new KeyValue("360", "360、一幅新闻摄影作品的背后"));
		MENU_List.add(new KeyValue("361", "361、我的侏儒女儿"));
		MENU_List.add(new KeyValue("362", "362、一个人所能改变的……"));
		MENU_List.add(new KeyValue("363", "363、成功者的袜子"));
		MENU_List.add(new KeyValue("364", "364、祖母的智慧"));
		MENU_List.add(new KeyValue("365", "365、为文明的坚守"));
		MENU_List.add(new KeyValue("366", "366、当你被摔倒"));
		MENU_List.add(new KeyValue("367", "367、梦想的翅膀"));
		MENU_List.add(new KeyValue("368", "368、破碎的小提琴"));
		MENU_List.add(new KeyValue("369", "369、一秒钟"));
		MENU_List.add(new KeyValue("370", "370、认清脚下的位置"));
		MENU_List.add(new KeyValue("371", "371、难能未必可贵"));
		MENU_List.add(new KeyValue("372", "372、谢天"));
		MENU_List.add(new KeyValue("373", "373、赢"));
		MENU_List.add(new KeyValue("374", "374、找到你的人生使命"));
		MENU_List.add(new KeyValue("375", "375、不是不可能"));
		MENU_List.add(new KeyValue("376", "376、你想要一座什么样的房子"));
		MENU_List.add(new KeyValue("377", "377、赢的最高境界"));
		MENU_List.add(new KeyValue("378", "378、我是谁"));
		MENU_List.add(new KeyValue("379", "379、与比尔・盖茨拥有同等的机会"));
		MENU_List.add(new KeyValue("380", "380、陶器"));
		MENU_List.add(new KeyValue("381", "381、“小事”之大"));
		MENU_List.add(new KeyValue("382", "382、微笑的魔力"));
		MENU_List.add(new KeyValue("383", "383、共振和创业"));
		MENU_List.add(new KeyValue("384", "384、名片墙"));
		MENU_List.add(new KeyValue("385", "385、出租不出售"));
		MENU_List.add(new KeyValue("386", "386、被更改的通知"));
		MENU_List.add(new KeyValue("387", "387、不同的结果"));
		MENU_List.add(new KeyValue("388", "388、扳倒总统尼克松的女人"));
		MENU_List.add(new KeyValue("389", "389、截然相反的梦幻假期"));
		MENU_List.add(new KeyValue("390", "390、能耐，就是能够忍耐"));
		MENU_List.add(new KeyValue("391", "391、两个理由"));
		MENU_List.add(new KeyValue("392", "392、２２岁的骑师"));
		MENU_List.add(new KeyValue("393", "393、成果来自利用机会"));
		MENU_List.add(new KeyValue("394", "394、莫泽斯老奶奶的作品"));
		MENU_List.add(new KeyValue("395", "395、谁的命运都能改变"));
		MENU_List.add(new KeyValue("396", "396、带把手的瓶子"));
		MENU_List.add(new KeyValue("397", "397、最高哲学"));
		MENU_List.add(new KeyValue("398", "398、门面"));
		MENU_List.add(new KeyValue("399", "399、普雷瑟先生的成功之道"));
		MENU_List.add(new KeyValue("400", "400、管理的智慧"));
		MENU_List.add(new KeyValue("401", "401、三个字成就沃尔玛"));
		MENU_List.add(new KeyValue("402", "402、训练将军"));
		MENU_List.add(new KeyValue("403", "403、上上下下的启示"));
		MENU_List.add(new KeyValue("404", "404、淡化优势的成功哲学"));
		MENU_List.add(new KeyValue("405", "405、敲门的一刻"));
		MENU_List.add(new KeyValue("406", "406、爱就是要对你负责"));
		MENU_List.add(new KeyValue("407", "407、放下白菜"));
		MENU_List.add(new KeyValue("408", "408、关键时刻"));
		MENU_List.add(new KeyValue("409", "409、电梯里的１分２７秒"));
		MENU_List.add(new KeyValue("410", "410、演出就要开始"));
		MENU_List.add(new KeyValue("411", "411、玩捉迷藏的人"));
		MENU_List.add(new KeyValue("412", "412、生活就像自助餐厅"));
		MENU_List.add(new KeyValue("413", "413、限制法则"));
		MENU_List.add(new KeyValue("414", "414、一个帝国的诞生"));
		MENU_List.add(new KeyValue("415", "415、天才卡尔・克洛耶"));
		MENU_List.add(new KeyValue("416", "416、公正的力量"));
		MENU_List.add(new KeyValue("417", "417、跳到人生的擂台上"));
		MENU_List.add(new KeyValue("418", "418、批评的力量"));
		MENU_List.add(new KeyValue("419", "419、再“坏”一点，希望就会降临"));
		MENU_List.add(new KeyValue("420", "420、沃尔玛的胶带"));
		MENU_List.add(new KeyValue("421", "421、预测你头顶上的天空"));
		MENU_List.add(new KeyValue("422", "422、你能管理多少人"));
		MENU_List.add(new KeyValue("423", "423、让所有人都知道我"));
		MENU_List.add(new KeyValue("424", "424、成功的关键"));
		MENU_List.add(new KeyValue("425", "425、给我一刀"));
		MENU_List.add(new KeyValue("426", "426、定位决定成败"));
		MENU_List.add(new KeyValue("427", "427、天生一个面包师"));
		MENU_List.add(new KeyValue("428", "428、山羊除草"));
		MENU_List.add(new KeyValue("429", "429、降低变成秃头的几率"));
		MENU_List.add(new KeyValue("430", "430、乞丐也需要努力"));
		MENU_List.add(new KeyValue("431", "431、你有没有想要想疯了"));
		MENU_List.add(new KeyValue("432", "432、锁不住的灵魂"));
		MENU_List.add(new KeyValue("433", "433、成功是心底热烈的渴望"));
		MENU_List.add(new KeyValue("434", "434、抢占第二落点"));
		MENU_List.add(new KeyValue("435", "435、胆为先"));
		MENU_List.add(new KeyValue("436", "436、最早的奥运会邮票"));
		MENU_List.add(new KeyValue("437", "437、零增长政策下的一枚苦果"));
		MENU_List.add(new KeyValue("438", "438、保护动物"));
		MENU_List.add(new KeyValue("439", "439、滑铁卢战役的最大赢家"));
		MENU_List.add(new KeyValue("440", "440、红色鲑鱼推销员"));
		MENU_List.add(new KeyValue("441", "441、跟跑者的启示"));
		MENU_List.add(new KeyValue("442", "442、永恒的价值"));
		MENU_List.add(new KeyValue("443", "443、背着萨克斯走天涯"));
		MENU_List.add(new KeyValue("444", "444、人生的第二幕"));
		MENU_List.add(new KeyValue("445", "445、纸条上的命运"));
		MENU_List.add(new KeyValue("446", "446、在“羞辱”中奋起"));
		MENU_List.add(new KeyValue("447", "447、正视卑微"));
		MENU_List.add(new KeyValue("448", "448、不甘灭顶的西尔斯"));
		MENU_List.add(new KeyValue("449", "449、宽与窄"));
		MENU_List.add(new KeyValue("450", "450、成功的近道"));
		MENU_List.add(new KeyValue("451", "451、胜出的诀窍"));
		MENU_List.add(new KeyValue("452", "452、当年，我曾命悬一线"));
		MENU_List.add(new KeyValue("453", "453、自律"));
		MENU_List.add(new KeyValue("454", "454、手机放在咖啡店里卖"));
		MENU_List.add(new KeyValue("455", "455、手表和草帽"));
		MENU_List.add(new KeyValue("456", "456、带你３０００美元玩遍地球"));
		MENU_List.add(new KeyValue("457", "457、鱼和熊掌可以兼得"));
		MENU_List.add(new KeyValue("458", "458、百年誓约"));
		MENU_List.add(new KeyValue("459", "459、送你一只左鞋"));
		MENU_List.add(new KeyValue("460", "460、十年一图"));
		MENU_List.add(new KeyValue("461", "461、取舍的原则"));
		MENU_List.add(new KeyValue("462", "462、状告足球"));
		MENU_List.add(new KeyValue("463", "463、不让世界改变自己"));
		MENU_List.add(new KeyValue("464", "464、把咖啡磨成了金子"));
		MENU_List.add(new KeyValue("465", "465、二百五定律"));
		MENU_List.add(new KeyValue("466", "466、球场上的无私者"));
		MENU_List.add(new KeyValue("467", "467、让成功变成系列"));
		MENU_List.add(new KeyValue("468", "468、比较，让我们遗失了自我"));
		MENU_List.add(new KeyValue("469", "469、不知的境界"));
		MENU_List.add(new KeyValue("470", "470、两个伟人"));
		MENU_List.add(new KeyValue("471", "471、“智者”无惧"));
		MENU_List.add(new KeyValue("472", "472、让孩子结识校长"));
		MENU_List.add(new KeyValue("473", "473、输一时，赢一生"));
		MENU_List.add(new KeyValue("474", "474、１＋１＝１１"));
		MENU_List.add(new KeyValue("475", "475、成功之道"));
		MENU_List.add(new KeyValue("476", "476、你想换个鼻子吗？"));
		MENU_List.add(new KeyValue("477", "477、成功没有时间表"));
		MENU_List.add(new KeyValue("478", "478、让自己跌到谷底"));
		MENU_List.add(new KeyValue("479", "479、迈出“第三只脚”"));
		MENU_List.add(new KeyValue("480", "480、一个垃圾工的梦想"));
		MENU_List.add(new KeyValue("481", "481、抓心"));
		MENU_List.add(new KeyValue("482", "482、被机会打败"));
		MENU_List.add(new KeyValue("483", "483、一路风雪一路歌"));
		MENU_List.add(new KeyValue("484", "484、世界很吝啬"));
		MENU_List.add(new KeyValue("485", "485、苦难总有出口"));
		MENU_List.add(new KeyValue("486", "486、被馈赠的机会"));
		MENU_List.add(new KeyValue("487", "487、给微软白领上课的“的哥”"));
		MENU_List.add(new KeyValue("488", "488、沙漠也能出奇迹"));
		MENU_List.add(new KeyValue("489", "489、从未得到机会的女人"));
		MENU_List.add(new KeyValue("490", "490、麦当劳为什么不养牛"));
		MENU_List.add(new KeyValue("491", "491、打开梦想的盒子"));
		MENU_List.add(new KeyValue("492", "492、盲目的坚持到底"));
		MENU_List.add(new KeyValue("493", "493、“错”出来的成功"));
		MENU_List.add(new KeyValue("494", "494、做精一碗汤"));
		MENU_List.add(new KeyValue("495", "495、为梦想而歌"));
		MENU_List.add(new KeyValue("496", "496、弄“反”了的票价"));
		MENU_List.add(new KeyValue("497", "497、会跳舞的战马"));
		MENU_List.add(new KeyValue("498", "498、在苦闷中学会愉快"));
		MENU_List.add(new KeyValue("499", "499、一条路走到底"));
		MENU_List.add(new KeyValue("500", "500、3800块香口胶"));
		MENU_List.add(new KeyValue("501", "501、1650次拒绝"));
		MENU_List.add(new KeyValue("502", "502、享受的青蛙"));
		MENU_List.add(new KeyValue("503", "503、责任是成功的机会"));
		MENU_List.add(new KeyValue("504", "504、把拉面卖到一碗１０００美元"));
		MENU_List.add(new KeyValue("505", "505、抓住瞬间的灵感"));
		MENU_List.add(new KeyValue("506", "506、诚信与生命的抉择"));
		MENU_List.add(new KeyValue("507", "507、捷径＋苦干"));
		MENU_List.add(new KeyValue("508", "508、瓶盖上有几个齿？"));
		MENU_List.add(new KeyValue("509", "509、母亲教给我的"));
		MENU_List.add(new KeyValue("510", "510、宰相疏“水道”"));
		MENU_List.add(new KeyValue("511", "511、重奖跳槽者"));
		MENU_List.add(new KeyValue("512", "512、阿尔迪的简单"));
		MENU_List.add(new KeyValue("513", "513、一把属于自己的钥匙"));
		MENU_List.add(new KeyValue("514", "514、不是偶然"));
		MENU_List.add(new KeyValue("515", "515、最富争议的传奇"));
		MENU_List.add(new KeyValue("516", "516、一个黑脸一个白脸"));
		MENU_List.add(new KeyValue("517", "517、捕獭者说"));
		MENU_List.add(new KeyValue("518", "518、不比别人矮一头"));
		MENU_List.add(new KeyValue("519", "519、把“烂牌”打好"));
		MENU_List.add(new KeyValue("520", "520、如果伊安会说话"));
		MENU_List.add(new KeyValue("521", "521、一个逃兵"));
		MENU_List.add(new KeyValue("522", "522、我出售的是梦想"));
		MENU_List.add(new KeyValue("523", "523、把目标集中到一点"));
		MENU_List.add(new KeyValue("524", "524、一事无成"));
		MENU_List.add(new KeyValue("525", "525、智慧“推销”"));
		MENU_List.add(new KeyValue("526", "526、写好你一生的每一笔"));
		MENU_List.add(new KeyValue("527", "527、永远不能放弃"));
		MENU_List.add(new KeyValue("528", "528、聪明的珀金斯"));
		MENU_List.add(new KeyValue("529", "529、任务与结果"));
		MENU_List.add(new KeyValue("530", "530、飞越疯人院"));
		MENU_List.add(new KeyValue("531", "531、错出成功"));
		MENU_List.add(new KeyValue("532", "532、为人生修剪"));
		MENU_List.add(new KeyValue("533", "533、没有借口"));
		MENU_List.add(new KeyValue("534", "534、乐观的价值"));
		MENU_List.add(new KeyValue("535", "535、命运不可选择"));
		MENU_List.add(new KeyValue("536", "536、时间是朋友还是敌人？"));
		MENU_List.add(new KeyValue("537", "537、洗碗的故事"));
		MENU_List.add(new KeyValue("538", "538、你的成功你决定"));
		MENU_List.add(new KeyValue("539", "539、一根树枝改变命运"));
		MENU_List.add(new KeyValue("540", "540、千万别太在意“下下签”"));
		MENU_List.add(new KeyValue("541", "541、屈辱是一种力量"));
		MENU_List.add(new KeyValue("542", "542、苦难是财富，还是屈辱？"));
		MENU_List.add(new KeyValue("543", "543、成为富翁其实很简单"));
		MENU_List.add(new KeyValue("544", "544、取暖的声音"));
		MENU_List.add(new KeyValue("545", "545、给糖的诀窍"));
		MENU_List.add(new KeyValue("546", "546、沉默是金"));
		MENU_List.add(new KeyValue("547", "547、专机给总统运酒"));
		MENU_List.add(new KeyValue("548", "548、制造“抢劫”风波"));
		MENU_List.add(new KeyValue("549", "549、魔鬼的苹果"));
		MENU_List.add(new KeyValue("550", "550、汤凡新的“智慧”"));
	}



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		menuList = (ListView) findViewById(R.id.menuList);
		menuAdapter = new ArrayAdapter<KeyValue>(this,
				R.layout.simple_list_layout, R.id.txtListItem, MENU_List);
		menuList.setAdapter(menuAdapter);
		menuList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				KeyValue menu = (KeyValue) arg0.getItemAtPosition(pos);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("menu", menu.getKey());
				bundle.putBoolean("startByMenu", true);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});
	}
}