package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import fetch.CommonUtil;

public class SplitCar {
	static FileFilter txtFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("txt");
		}
	};
	static FileFilter imageFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("jpg");
		}
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File fileDir = new File(
				"C:/Documents and Settings/caixl/桌面/165003_735fc96e-8855-4644-a9ba-e4bb155f5d1b/assets/txt/");
		if (fileDir.isDirectory()) {
			for (File file : fileDir.listFiles(txtFilter)) {
				getCarMap(file);

			}
		}
		File fileDir2 = new File(
				"C:/Documents and Settings/caixl/桌面/165003_735fc96e-8855-4644-a9ba-e4bb155f5d1b/assets/txt/新建文件夹");
		if (fileDir2.isDirectory()) {
			for (File file : fileDir2.listFiles(txtFilter)) {
				getCarImageMap(file);

			}
		}
	}

	
	public static void getCarImageMap(File file) {
		String[] names = file.getName().split("_");
		System.out.println("CarImageMap.put(\""+names[1].substring(0,names[1].lastIndexOf("."))+"\",\""+ names[0]+"\");");
	}
	
	public static void getCarMap(File file) {
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(file), "GBK");
			bufferedReader = new BufferedReader(inputStreamReader);
			String contentString = "";
			StringBuffer buffer = new StringBuffer();
			String carName = "";
			boolean hasGetName = false;
			while (null != (contentString = bufferedReader.readLine())) {
				buffer.append(contentString+"\\n");
				if(!hasGetName){
					hasGetName = true;
					carName = contentString;
				}
			}
			Map<String,String > map = new HashMap<String, String>();
			map.put(carName,buffer.toString());
			System.out.println("map.put(\""+carName+"\",\""+buffer.toString()+"\");");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	public void getMap (){
		Map<String,String > map = new HashMap<String, String>();
		map.put("奥迪汽车","奥迪汽车\n        奥迪是一个国际高品质汽车开发商和制造商。现为大众汽车公司的子公司，总部设在德国的英戈尔施塔特，主要产品有A1系列、A2系列、A3系列、A4系列、A5系列、A6系列、A8系列、Q7（SUV）、R系、敞篷车及运动车系列等。2002年，公司汽车销量达到74.2万辆，年收入约226亿欧元，全球雇员达到51,000多人。2006-2-22:  连续十年刷新销售纪录--奥迪公司2005年业绩再创新高。2006年2月22日，奥迪公司在年度新闻发布会中公布了上一年度的各项业绩，其中，在销售额、税前利润、产量及汽车销量等方面，奥迪再创新高。2005年，奥迪公司的销售额达266亿欧元，同比增长了8.5%；税前利润高达13.1亿欧元，提高了14.6%，创下企业历史最高水平；整车产量（包括兰博基尼）达811,522辆，增长了3.4%；全球范围向客户销售了829,109辆奥迪轿车，增长了6.4%，连续第十年刷新销量纪录。2006-1-24:  奥迪公司连续第十年创造汽车销售新纪录.奥迪公司日前宣布，其2005年共向全球终端用户交付了829,100辆汽车（2004年：779,441辆），增幅为6.4%，从而连续第十年创造了汽车销售的新纪录。同时，在全世界39个市场中，奥迪创造了历史最高销量纪录。至今奥迪仍然在不断的发展着其品牌与技术。\n        \n        奥迪是德国历史最悠久的汽车制造商之一。从1932年起，奥迪开始采用四环徽标，它象征着奥迪与小奇迹(DKW)、霍希(Horch)和漫游者(Wanderer)合并成的汽车联盟公司。在20世纪30年代，汽车联盟公司涵盖了德国汽车工业能够提供的所有乘用车领域，从摩托车到豪华轿车。\n        \n   2008年入选世界品牌价值实验室编制的《中国购买者满意度第一品牌》，排名第七。\n");
		map.put("阿尔法·罗密欧","阿尔法·罗密欧\n阿尔法·罗密欧a.l.f.a全称为anonima lombarda fabbrica di automobili,建于1910年，alfa公司是在废墟中建立起来的，1910年创厂的alfa汽车，以其强烈的运动气息以及独特外型，在车坛上一直有着其与众不同的定位，从创立开始，alfa的造车目标就是朝生产高性能跑车以及跑车化轿车而努力，而在其创厂至今的发展史上，alfa旗下的车款的确也不辱使命，从20年代参加mugello大赛、世界杯 gp、30年代第一部单座gp赛车p3树立了当时的赛车典型、50年代火红席卷世界的158/159赛车。\n");
		map.put("阿斯顿·马丁","阿斯顿·马丁\n阿斯顿·马丁（AstonMartin）原是英国豪华轿车、跑车生产厂。建于1913年，创始人是莱昂内尔·马丁和罗伯特·班福德。公司设在英国新港市，以生产敞蓬旅行车、赛车和限量生产的跑车而闻名世界。 参加车赛固然是发展轿车生产的重要手段，但耗资太大加上经营不善，1987年被美国福特公司收购。2007年，Prodrive老板大卫-理查兹以9.25亿美元的价格从福特手中购得阿斯顿-马丁。\n");
		map.put("奔驰","奔驰\n奔驰，德国汽车品牌，被认为是世界上最成功的高档汽车品牌之一，其完美的技术水平、过硬的质量标准、推陈出新的创新能力、以及一系列经典轿跑车款式令人称道。奔驰三叉星已成为世界上最著名的汽车及品牌标志之一。自1900年12月22日戴姆勒发动机工厂（Daimler-Motoren-Gesellschaft，DMG）向其客户献上了世界上第一辆以梅赛德斯（Mercedes）为品牌的轿车开始，奔驰汽车就成为汽车工业的楷模。100多年来，奔驰品牌一直是汽车技术创新的先驱者。\n");
		map.put("宝马","宝马\nBMW，全称为巴伐利亚机械制造厂股份公司（德文：Bayerische Motoren Werke AG），是德国一家世界知名的高档汽车和摩托车制造商，总部位于慕尼黑。BMW在中国大陆、香港与早年的台湾又常称为“宝马”。\n");
		map.put("宾利","宾利\n\n宾利（Bentley）汽车是一家发迹于英国的豪华房车和GT车的制造商，是由沃尔特·欧文·本特利（1888年–1971年）在1919年7月于英格兰创立的。宾利创办人早年是以在第一次世界大战中制造供应皇家空军飞机引擎而闻名。从1919年宾利的第一辆汽车的诞生之日起，近百年来，宾利的品牌虽历经时间的洗礼，却仍旧历久弥新，熠熠生辉。那个展翅腾飞的“B”字是宾利最强劲、永不妥协的标志，它是呈现给世人的永远是动力、尊贵、典雅、舒适与精工细做的最完美结合。\n");
		map.put("布加迪","布加迪\n布加迪是法国最具有特色的超级跑车车厂之一。布加迪以生产世界上最好的及最快的车闻名于世。最原始的布加迪品牌已经在第二次世界大战后消失。不过战后此品牌曾经有两度中兴，目前它是大众集团旗下的一个品牌。\n");
		map.put("保时捷","保时捷\n\n保时捷是世界知名汽车品牌，其生产的保时捷911是迄今为止世界上在赛车中最畅销的一款。保时捷一直努力将种种可能性与看似不太可能的东西相组合，而今对于跑车而言，“保时捷”无异于一个全球意义上的代名词。对保时捷来说，这最终的测试是要不惜一切，力求出色，哪怕是在技术层面上的大胆革新。保时捷把每一种想法都视为一种机遇，也正是这种先知先觉使保时捷的公司不断欣荣并引导保时捷至今。这也是保时捷品牌的核心所在：一个力求至臻完善的标准。\n");
		map.put("别克","别克\n1903年5月19日，大卫 ·邓巴· 别克在布里斯科兄弟的帮助下创建美国别克汽车公司，但不久公司就陷于了困境。1904年下半年，一个马车制造商William C. Durant看准了别克BUICK未来的巨大潜力，毅然买下了这家公司。他在1908年成立了通用汽车公司，并沿用别克BUICK品牌作为[1]开拓新公司的基石，公司才开始兴旺起来，并创造出汽车年产量居美国第一位的业绩。1908年它的产量达到8820辆，居美国第一位。同年，以别克汽车公司为中心，成立了美国通用汽车公司。当通用汽车公司扩大后，别克部成为通用汽车公司的第二大部门。它主要设计制造中档家庭轿车。别克汽车的销量在通用汽车公司内第三位。别克车具有大马力、个性化、实用性和成熟的特点。随着2004年奥兹莫比尔的淘汰，别克成为了唯一一家总部设在北美的入门级豪华轿车。别克是历史最悠久的美国汽车品牌之一，最近通用公司的财务危机导致人们猜测说别克品牌可能会被卖掉或者被废弃，但是，基于别克汽车在中国市场的完美表现和新推出的别克enclave型号的巨大成功，这传闻的可信度微乎其微。2009年7月，通用汽车完成重组，结束破产保护，别克等4个品牌保留，其他4个品牌出售。\n");
		map.put("本田","本田\n本田株式会社（ホンだ会社）是世界上最大的摩托车生产厂家，汽车产量和规模也名列世界十大汽车厂家之列。1948年创立，创始人是传奇式人物本田宗一郎。公司总部在东京，雇员总数达18万人左右。现在，本田公司已是一个跨国汽车、摩托车生产销售集团。它的产品除汽车摩托车外，还有发电机、农机等动力机械产品。\n");
		map.put("标致","标致\n标致汽车公司是欧洲老牌的汽车生产企业。然而制造汽车并不是标致的全部历史。标致曾涉足制造业的诸多领域，比如五金工具、家用器皿、裙撑、望远镜、夹鼻眼镜弹簧、锯条、外科手术器械、猎枪、收音机、缝纫机、不一而足。在汽车时代来临之前，标致自行车、摩托车和运输车辆的生产规模一度相当庞大。毕竟，标致的历史一直可以上溯到十五世纪，标致摩托车是欧洲摩托车第四大制造商之一。此外还有词语的意思。\n");
		map.put("比亚迪","比亚迪\n比亚迪股份有限公司（以下简称“比亚迪”）创立于1995年，是一家香港上市的高新技术民营企业。比亚迪在广东、北京、上海、长沙、宁波和西安等地区建有九大生产基地，总面积将近1,000万平方米，并在美国、欧洲、日本、韩国、印度、台湾、香港等地设有分公司或办事处，现员工总数已超过14万人。在最新公布的2009年中国企业500强中，比亚迪排名216位。\n");
		map.put("北汽","北汽\n北京汽车集团有限公司（简称“北汽集团”），是中国五大汽车集团之一，主要从事整车制造、零部件制造、汽车服务贸易、研发、教育和投融资等业务，是北京汽车工业的发展规划中心、资本运营中心、产品开发中心和人才中心。 2010年9月28日，北京汽车股份公司的成立，标志着北汽集团自主品牌乘用车进入全面提速阶段。\n");
		map.put("宝骏","宝骏\n上汽通用五菱2010年创建的自主汽车品牌。上汽通用五菱正式发布的新乘用车品牌“宝骏汽车”，标志这个中国微车领头羊开始正式进军方兴未艾的轿车市场。宝骏品牌源由：“骏”的本义是良驹，宝骏即人们最心爱的良驹。\n");
		map.put("宝腾","宝腾\n成立于１９８３年的宝腾公司，在科技方面先后与日本三菱公司和法国雪铁龙公司合作；１９９６年成功地收购了英国ＬＯＴＵＳ（莲花集团）国际公司，进一步加强了公司的实力；前不久收购底特律汽车设计中心，使宝腾公司具有独立完成从轿车开发到生产的能力，从单一的国内生产商发展成为产品款式多样、满足国内外不同需要的汽车生产商。\n");
		map.put("长城汽车","长城汽车\n长城汽车是长城汽车股份有限公司的简称，长城汽车的前身是长城工业公司，是一家集体所有制企业，成立于1984年，主要从事改装汽车业务。长城汽车是中国首家在香港H股上市的民营整车汽车企业、国内规模最大的皮卡SUV专业厂、跨国公司。公司下属控股子公司20余家，员工18000多人，目前拥有6个整车生产基地（皮卡、SUV、CUV，轿车MPV），2007年产能达到40万辆。具备发动机、前后桥等核心零部件自主配套能力。\n");
		map.put("道奇","道奇\n道奇牌轿车素以价廉和大众化称著, 颇受欢迎。轿车型号有: 蝰蛇(Viper), 无畏(Interpid), 隐形(Stealth), 小精灵(Spirit), 影子(Shadow), 霓虹(Neon)等。“道奇”文字商标采用道奇兄弟的姓氏“Dodge”，图形商标是在一个五边形中有一羊头形象，在汽车上使用小公羊、大公羊两个商标。该商标象征“道奇”车强壮剽悍，善于决斗，表示道奇部的产品朴实无华、美观大方。\n");
		map.put("福特","福特\n福特，亨利 · 福特(Henry Ford)创建的福特汽车公司，公司名称取自创始人亨利·福特(Henry Ford)的姓氏。同名的名人有美国往届总统福特和美国导演福特。\n");
		map.put("福特野马","福特野马\n1962年，福特汽车公司开始研发了野马的第一辆概念车——野马型车。它是一部发动机中置的两座跑车，为了纪念在二战中富有传奇色彩的北美P57型“野马战斗机，福特汽车将这辆跑车命名为“野马”。它的初次亮相是在1962年10月，赛车手丹.格尼(Dan Gurney)驾着它参加了在纽约举办的美国汽车大奖赛。\n");
		map.put("通用汽车","通用汽车\n\n通用汽车公司（GM）成立于1908年9月16日，自从威廉·杜兰特创建了美国通用汽车公司以来，先后联合或兼并了别克、凯迪拉克、雪佛兰、奥兹莫比尔、庞蒂克、克尔维特、悍马等公司，拥有铃木（Suzuki）3%股份。使原来的小公司成为它的分部。从1927年以来一直是全世界最大的汽车公司。\n");
		map.put("JEEP","JEEP\n\n吉普(JEEP)是一个品牌，而不是一种车型。世界上第一辆吉普车是1941年在二战中为满足美军军需生产的。戴姆勒克莱斯勒公司作为吉普的鼻祖，单独拥有这一注册商标。在中国，吉普品牌仅限北京吉普汽车有限公司使用。也就是说，除此之外所有的越野车，如三菱、丰田或其他公司生产的车，都不能叫吉普这个名字。\n");
		map.put("凯迪拉克","凯迪拉克\n凯迪拉克（香港译作“佳得利”）1902年诞生于被誉为美国汽车之城的底特律。百多年来，凯迪拉克在汽车行业创造了无数个第一，缔造了无数个豪华车的行业标准；可以说凯迪拉克的历史代表了美国豪华车的历史。在韦伯斯特大词典中，凯迪拉克被定义为“同类中最为出色、最具声望事物”的同义词；被一向以追求极致尊贵著称的伦敦皇家汽车俱乐部冠以“世界标准”的美誉。 凯迪拉克融汇了百年历史精华和一代代设计师的智慧才智，成为汽车工业的领导性品牌。\n");
		map.put("克莱斯勒","克莱斯勒\n克莱斯勒（香港译名：佳士拿），是美国著名汽车公司，同时也是美国三大汽车公司之一。该公司创始人为沃尔特·克莱斯勒（Walter Chrysler），1875年出生于美国衣阿华洲一个铁路技师的家庭。\n");
		map.put("林肯","林肯\n美国著名汽车企业福特旗下的一款豪华车，创立于1922年，创始人为亨利 马代恩 利兰。\n林肯：林肯大陆（LINCOLN CONTINENTAI）是林肯 · 默寇利部于1939年首推的名牌豪华车型。该车型显示林肯 · 默寇利部生产的高级轿车技术无懈可击，乃豪华车中的佼佼者，被称为福特汽车公司的传世佳作。 林肯（LINCOLN）轿车是以美国第16任总统的名字阿伯拉罕 · 林肯命名的汽车，借助林肯总统的名字来树立公司的形象，显示该公司生产的是顶级轿车。其商标是在一个矩形中含有一颗闪闪放光的星辰，表示林肯总统是美国联邦统一和废除奴隶制的启明星，也喻示福特 · 林肯牌轿车光辉灿烂。\n");
		map.put("奥兹莫比尔","奥兹莫比尔\n奥兹莫比尔由美国汽车业开创者之一兰索姆·奥兹建立于1897年，1908年并入通用公司，它的前身是1897年由兰塞姆·奥兹和弗兰克·克拉克创建的奥兹汽车公司，它是美国最老的小客车生产厂。奥兹莫比尔是美国第一个大量生产销售汽车的企业，以产中档车为主。主要产品有阿莱罗(Alero)、Aurora(曙光)、短剑(Cutlass)、激情(Intrigue)、88(Eightyeight)、摄政王(Regency)、剪影厢体车(Silhouette)等。\n");
		map.put("庞蒂亚克","庞蒂亚克\n庞蒂亚克是美国通用汽车公司旗下品牌之一，国内也称为庞蒂克（粤语译名：潘迪），其前身是1907年的奥克兰汽车公司（Oakland），1931年改为庞蒂亚克。2009年4月29日,通用正式宣布砍掉庞蒂亚克这个品牌,拥有102年历史的庞蒂亚克从此消失。此品牌于2010年10月31日倒闭。\n");
		map.put("Rossion","Rossion\nRossion汽车（罗森汽车）公司的总部设在佛罗里达州的庞帕诺海滩，是一家2000后的汽车公司。该公司创造了新型的Q1跑车。作为一家新型汽车公司，Rossion致力于在车辆的设计当中实现最纯粹的性能、速度和平衡处理形式。在2010年，Rossion汽车公司把Rossion品牌扩展到全球各地。Rossion汽车公司由两位合作伙伴伊安·格伦内斯和迪恩·罗森共同创立。\n");
		map.put("土星汽车","土星汽车\n1985年通用汽车公司决定新建土星分部，企图开发先进的土星牌轿车以抵御外国轿车大规模进入美国市场。分部设在田纳西州春山市，是通用公司唯一从内部建立起来的公司。主要产品分为豪华轿车SL、旅行轿车SW和跑车SC。\n");
		map.put("雪佛兰","雪佛兰\n雪佛兰汽车公司（Chevrolet），亦作雪佛莱或雪福兰，由美国人威廉·杜兰特与通用汽车大股东不合离开后与瑞士赛车手、工程师路易斯·雪佛兰于1911年在美国底特律创立，美国人常昵称雪佛兰为Chevy。\n");
		map.put("大众","大众\n\n大众，汽车品牌名，大众汽车公司是德国最年轻的、同时也是德国最大的汽车生产厂家。使大众公司扬名的产品是甲壳虫(Beetle)式轿车（由费迪南德·保时捷设计），该车在80年代初已生产了2000万辆。它启动了大众公司的第一班高速列车，紧随其后的高尔夫(Golf)、帕萨特(Passat)等也畅销全世界。从1984年大众汽车进入中国市场，大众汽车是第一批在中国开展业务的国际汽车制造商之一。大众汽车自进入中国市场以来，就一直保持着在中国轿车市场中的领先地位。另外在现代汉语中大众可以解释为：众多的人；泛指民众，群众等意思。\n");
		map.put("迈巴赫","迈巴赫\n迈巴赫（德文：Maybach）与迈巴赫引擎制造厂（德文：Maybach-Motorenbau GmbH）是一曾经在1921年到1940年间活跃于欧洲地区的德国超豪华汽车品牌与制造厂，车厂创始人卡尔·迈巴赫（Karl Maybach）的父亲威廉·迈巴赫（Wilhelm Maybach）曾担任戴姆勒发动机公司（今日戴姆勒·克莱斯勒集团前身）的首席技术总监，两厂渊源甚深。1997年戴姆勒·克莱斯勒集团在东京车展会场中展出一辆以Maybach为名的概念性超豪华四门轿车，正式让这个德国汽车品牌在销声匿迹多年后再次复活。\n");
		map.put("欧宝","欧宝\nOPEL在中国大陆称为欧宝、在台湾称为欧普。德国欧宝公司是美国通用汽车公司的子公司，是通用公司在欧洲的一个窗口。它由Adan Opel，即亚当·欧宝（欧普）所创立，至今已有百多年历史。\n");
		map.put("威兹曼","威兹曼\n或许有很多人不知道这个品牌，不过在当今汽车行业激烈的竞争环境下它确实表现的很另类，最显著的特征便是威兹曼的外型很复古，这与其他车商背道而驰。这点有点像卡特勒姆，不过威兹曼虽然一身复古经典的打扮，却有一颗年轻澎湃的\"心脏\"。因为它装有宝马赛车引擎，就像帕加尼的\"心脏\"是奔驰给的一个道理。\n　　\n1985年，马丁·威兹曼和弗莱德海姆·威兹曼两兄弟参观了埃森车展，参观了大量的各种老式和新式跑车后，兄弟俩突发奇想，为什么不制造一辆外观仿古而内部却极其现代化的超级跑车呢？制造汽车需要极大的投入，虽然威兹曼兄弟通过以前的经营有点积蓄，但和汽车制造需要的资金相比无异于杯水车薪。为了筹措资金，他们又开办了一家小公司专门制造给敞篷跑车配套的可拆卸硬顶。几年下来，公司的业务获得了长足的发展，兄弟俩不仅积累了资金，更如愿以偿地获得了宝马公司的许可，使用宝马的动力总成、底盘部件及部分电子系统。1993年，第一辆威兹曼跑车终于问世了。\n");
		map.put("本田","本田\n\n本田株式会社（ホンだ会社）是世界上最大的摩托车生产厂家，汽车产量和规模也名列世界十大汽车厂家之列。1948年创立，创始人是传奇式人物本田宗一郎。公司总部在东京，雇员总数达18万人左右。现在，本田公司已是一个跨国汽车、摩托车生产销售集团。它的产品除汽车摩托车外，还有发电机、农机等动力机械产品。\n");
		map.put("丰田","丰田\n\n丰田汽车公司（トヨタ自动车株式会社，Toyota Motor Corporation；）是一家总部设在日本爱知县丰田市和东京都文京区的汽车工业制造公司，隶属于日本三井财阀。丰田汽车公司自2008始逐渐取代通用汽车公司而成为全世界排行第一位的汽车生产厂商。其旗下品牌主要包括凌志、丰田等系列高中低端车型等。\n");
		map.put("光冈","光冈\n创建于1968年，主要是在日本市场销售欧美高级汽车，包括新车和二手车。旗下的五十多家销售店遍布日本各大主要城市。\n由“光冈汽车”开发的Coach Buider Car(改装车)，在汽车普及的欧美先进国家有几十年的销售历史和传统。顾客群是被称为男爵或公爵的贵族，或者是有经济实力的成功者和权威人士。\n");
		map.put("雷克萨斯","雷克萨斯\n雷克萨斯（Lexus）是日本丰田汽车公司旗下的豪华车品牌，它于1983年被首次提出，但仅用十几年的时间，自1999年起，在美国的销量超过奔驰、宝马，成为全美豪华车销量最大的品牌。过去，Lexus在国内的中文译名是凌志，2004年6月8日，丰田公司在北京宣布将Lexus的中文译名由“凌志”改为“雷克萨斯”，并开始在中国建立特许经销店，开始全面进军中国豪华车市。\n");
		map.put("铃木","铃木\n铃木是日本的姓氏。铃木公司成立于1920年，1952年开始生产摩托车，1955年开始生产汽车。 铃木汽车公司（SUZUKI）成立于1954年，以生产微型汽车为主。铃木也是丰田集团成员，同时通用持有铃木10％的股权。铃木汽车公司是最早与中国汽车公司合作成功的。铃木商标图案中的 “S”是 “SUZUKI”的第一个大写字母，它给人以无穷力量的感觉，象征无限发展的铃木汽车公司。铃木通过向全世界的客户提供优质产品，并且向使用铃木产品的客户提供优质服务，面向每位客户，以实现与客户建立终生信赖的关系为目标而不懈努力。\n");
		map.put("马自达","马自达\n\n马自达，全称马自达汽车有限公司（マツダ株式会社，MAZDA Motor Corporation，在香港又译为“万事得”），日本汽车生产商，总部位于广岛，主要销售市场包括亚洲、欧洲和北美洲。\n");
		map.put("日产","日产\n\n1914年，由田建治郎等人创建的 “快进社”，于1934年改为日产汽车公司。日产公司生产的轿车品牌很多，有总统、公子、桂冠、地平线、西尔维亚、羚羊、王子、南风、紫罗兰和小太阳等。“NISSAN’是日语 “日产”两个字的罗马音形式，是日本产业的简称，其含义是 “以人和汽车的明天为引目标”。\n");
		map.put("斯巴鲁","斯巴鲁\n斯巴鲁是富士重工业株式会社（ FHI ）旗下专业从事汽车制造的一家分公司，成立于1953年，最初主要生产汽车，同时也制造飞机和各种发动机，是生产多种类型、多用途运输设备的制造商。富士重工“斯巴鲁”汽车的标志采用六连星的形式。\n");
		map.put("三菱","三菱\n\n三菱这个名字来源于两部分：“mitsu”表示“三”而“bishi”表示“菱角”。三菱的标志是岩崎家族的家族标志“三段菱”和土佐藩主山内家族的家族标志“三柏菱”的结合，后来逐渐演变成今天的三菱标志。第一家三菱企业是岩崎弥太郎于1870年接手官方经营的长崎造船厂，1873年，造船厂更名为三菱商会。三菱开始涉足采矿、造船、银行、保险、仓储和贸易。随后，又经营纸、钢铁、玻璃、电气设备、飞机、石油和房地产。现在，三菱建立了一系列的企业，在日本工业现代化的过程中扮演着举足轻重的角色。\n");
		map.put("五十铃","五十铃\n五十铃（いすゞ自动车株式会社，Isuzu Jidōsha Kabushiki Kaisha）是一家日本的汽车制造公司，总公司位于日本东京，制造与组装则设厂于日本藤泽市、栃木县、及北海道，以生产商用车辆以及柴油内燃机著名。昭和9年(1934年)，根据商工省（现通商产业省）标准模式开始批量生产汽车，并以伊势神宫旁的五十铃河来命名成为“五十铃”这个商标。昭和24年商标和公司名统一以后就一直沿用“五十铃自动车株式会社”的公司名至今。会社标志作为今后国际化发展的象征，用罗马字母“ISUZU”经过现代化的设计成为现在的标志。\n");
		map.put("JAGUAR","JAGUAR\n捷豹（JAGUAR）是英国轿车的一种名牌产品，商标为一只正在跳跃前扑的\"美洲豹\"雕塑，矫健勇猛，形神兼备，具有时代感与视觉冲击力，它既代表了公司的名称，又表现出向前奔驰的力量与速度，象征该车如美洲豹一样驰骋于世界各地。\n");
		map.put("劳斯·莱斯","劳斯·莱斯\n\n劳斯莱斯(Rolls-Royce)是宝马公司旗下的品牌，于1906年在英国正式成立。劳斯莱斯以一个“贵族化”的汽车公司享誉全球，同时也是目前世界三大航空发动机生产商之一。2003年劳斯莱斯汽车公司归入宝马集团。\n");
		map.put("路虎","路虎\n\n路虎（Landrover），曾在中国大陆翻译成陆虎（香港地区称为“越野路华”），是世界著名的英国越野车品牌。今天，路虎公司是世界上唯一专门生产四驱车的公司。或许正是由于这一点，才使得路虎的价值--冒险、勇气和至尊，闪耀在其各款汽车中。\n");
		map.put("莲花","莲花\n莲花汽车公司成立于1952年1月1日，是世界上著名的运动汽车生产厂家，与法拉利、保时捷一起并称为世界三大跑车制造商，在世界上享有盛誉。莲花汽车拥有世界上顶级汽车设计技术，尤其在整车和动力总成方面，莲花具有丰富的经验和专业的技能。\n");
		map.put("mini","mini\nMini在国内翻译成“迷你”，意思是很小，很可爱，随着科技的进步，人们逐渐在追求着这样Mini的生活可以这样说，mini是一种时尚的生活，时尚的气息。Mini也是宝马旗下的一款车。1956年，欧洲汽油的供应紧张到了极点，英国政府开始强制执行燃油配额供给制度，这让BMC下定决心，开发一辆燃油效率最大化的小车。工程师Alec Issigonis接受了这个课题。在嘎那海滨酒店的一张餐巾纸上，Issigonis画下了Mini的第一张草图。Mini的传奇故事在1959年8月26日正式开始。\n");
		map.put("罗孚","罗孚\n罗孚ROVERRover--罗孚（香港译名：路华） 意思为流浪者或领航员。其命运就像名字一样，从贵为英女王的专用御驾声望并驾与劳斯莱斯，到落魄的被多次转手易主，但丝毫无损他深厚的文化底蕴。2005年7月22日罗孚资产托管人英国普华永道公司发表公告称，中国南京汽车集团正式成为英国百年老厂罗孚汽车公司和其发动机生产分部的买家。现今距他诞生已经整整一百零一年了，让我们翻阅罗孚的历史，翻开他当年的荣耀。\n");
		map.put("沃克斯豪尔","沃克斯豪尔\n\n沃克斯豪尔VAUXHALL汽车公司，1903年开始制造汽车，1925年被美国通用汽车公司收购，为通用的子公司。目前是英国产量较大的轿车厂商。\n");
		map.put("雪铁龙","雪铁龙\n\n雪铁龙（Citro&euml;n）汽车公司是法国第三大汽车公司，它创立于1915年，创始人是安德列·雪铁龙。主要产品是小客车和轻型载货车。雪铁龙公司总部设在法国巴黎。雇员总数为5万人左右，可年产汽车90万辆。\n");
		map.put("西亚特","西亚特\n	西亚特（Seat）是西班牙最大的汽车公司，1950年成立于巴塞罗那。现在属于德国大众汽车公司子公司。\n　　在西亚特汽车公司成立之初，他们以生产意大利菲亚特汽车公司的车型为主。当时在西班牙汽车市场的占有率达到了60%之多，有西班牙国民车之趋势，然而到70年代的时候，他们的市场占有率竟然下滑到了33%，亏损严重。\n　　1983年，德国大众汽车公司买下了西亚特的大部分股份，与西班牙政府合资共同经营西亚特汽车公司，因此西亚特就成了大众汽车公司的子公司。在西亚特归属大众麾下之后，他们得到了大众的资金和技术支持，他们的产品开始采用大众的零部件，有些车型的底盘、转向及悬挂系统也都由大众设计。之后的经营状态日趋好转，到二十世纪90年代初，西亚特汽车的年产量已到了36万辆以上，成为西班牙效益最好的汽车公司。然而在进入二十一世纪以后，西亚特的低生产率成为其痼疾，再加上受到全球金融风暴和美元贬值等因素的影响，西亚特出现了较大幅度的亏损，虽然在2007年之后出现好转，但已无能力在其国内进行扩张。西亚特总裁曾经表示：“受美元贬值等因素的影响，向南美出口整车的营销模式将变得意义不大，所以我们正考虑在西班牙以外的新兴市场建立生产基地。”\n");
		map.put("法拉利","法拉利\n法拉利(Ferrari)是一家意大利汽车生产商，1929年由恩佐·法拉利创办，主要制造一级方程式赛车、赛车及高性能跑车，法拉利生产的汽车大部分采用手工制造，年产量大约4300台。总部位于意大利摩德纳(Modena)附近的马拉内罗(Maranello)。早期的法拉利赞助赛车手及生产赛车，1947年独立生产汽车，其后变成今日的规模。现在菲亚特公司拥有法拉利50％股权，但法拉利却能独立于菲亚特公司运营。另有法拉利车队，以及手表、香水等周边产品。\n");
		map.put("菲亚特","菲亚特\n菲亚特（FIAT，NYSE：FIA ，台湾作飞雅特，港澳作快意，马新作飞霞），是意大利著名汽车制造公司，成立于1899年，总部位于意大利北部之都灵。菲亚特旗下的著名品牌包括：菲亚特、蓝旗亚、阿尔法·罗密欧和玛莎拉蒂。法拉利也是菲亚特的下属公司，但它是独立运作的。\n");
		map.put("兰博基尼","兰博基尼\n兰博基尼（Lamborghini），又译作林宝坚尼、蓝宝坚尼（台译）。兰博基尼汽车有限公司（Automobili Lamborghini S.p.A.）是一家坐落于意大利圣亚加塔·波隆尼（Sant'Agata Bolognese）的超级跑车制造公司。1963年，经由创业者费鲁齐欧·兰博基尼（Ferruccio Lamborghini）设立公司，早期曾因公司营运不善，数度易手经营权。兰博基尼的骨子里有一种唯我独尊的霸气，这种霸气使其在汽车界树立起了显赫的地位。\n");
		map.put("玛莎拉蒂","玛莎拉蒂\n玛莎拉蒂汽车公司具有悠久的历史，其家族四兄弟于1914年在意大利博洛尼亚成立了玛莎拉蒂公司，并于1926年生产了第一辆汽车Tipo26，创始人阿夫尔。玛莎拉蒂披甲上阵，亲自驾驶Tipo26型汽车参加了汽车比赛并赢得了奖项。玛莎拉蒂汽车公司是专门生产运动车的公司，在欧洲具有很高的知名度。玛莎拉蒂运动车在造型设计上，将自己的传统风格与流行款式相结合，在外观造型、机械性能、舒适安全性等各方面，在运动车中都是一流的。\n");
		map.put("帕加尼","帕加尼\n帕加尼（Pagani Automobili S.p.A.）是一家世界知名的超级跑车制造商，公司创始人为奥拉西欧·帕加尼（Horacio Pagani）。与大名鼎鼎的法拉利一样，诞生于素有超跑之乡美誉的意大利小镇摩德纳（Modena）。帕加尼所生产的超级跑车以极致的性能、大量采用纯手工打造的精湛工艺、昂贵的售价以及订单生产的稀有产量闻名于世。旗下代表车系有：1999年正式发布的帕加尼Zonda、2011年推出的第二个全新车系帕加尼Huayra。\n");
		map.put("大宇","大宇\n大宇——美国通用汽车公司旗下品牌之一。 韩国大宇汽车公司是韩国第二大汽车生产企业，年产汽车60多万辆。1967年，金宇中创建新韩公司，后改为新进公司，1983年改为大宇汽车公司。它是韩国大宇集团的骨干企业。\n");
		map.put("起亚","起亚\n起亚即起亚汽车公司，是韩国最早的汽车制造商，现在隶属于现代集团。拥有完善的乘用车和商用车生产流水线，330万平方米厂房的牙山湾工厂和79万平方米的所下里工厂，具有年产一百万辆汽车的生产力。通过在180多个国家的销售网络进行销售。\n");
		map.put("现代","现代\n1967年，郑周永创建现代汽车公司，经40多年的发展，它已成为韩国最大的汽车生产厂家，并进入世界著名汽车大公司行列。其商标是在椭圆中采用斜体字“H”，“H”是现代汽车公司英文名“HYUNDAI”的第一个大写字母。\n");
		

	}
}