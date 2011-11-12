package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import fetch.CommonUtil;

public class SplitCarKnowledge {
	static FileFilter txtFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("txt");
		}
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File bookDir = new File(
				"C:/Documents and Settings/Administrator/桌面/汽车知识全攻略/assets/bookdir.txt");

		getCarKnowledgeMap(bookDir);

//		System.out.println(Car_Knowledge);
//		System.out.println("------------------------------------------------");
		genGamma();
	}

	public static void genGamma() {
		Map<String, List<KeyValue>> map = new HashMap<String, List<KeyValue>>();
		for (Map.Entry<String, List<KeyValue>> entry : Car_Knowledge.entrySet()) {
			List<KeyValue> carlist = Car_Knowledge.get(entry.getKey());
			System.out.println("list = new ArrayList<KeyValue>();");
			List<KeyValue> list = new ArrayList<KeyValue>();
			for (KeyValue keyValue : carlist) {
				list.add(new KeyValue(keyValue.getKey(), keyValue.getValue()));
				System.out.println("list.add(new KeyValue(\""+keyValue.getKey()+"\",\""+ keyValue.getValue()+"\"));");
			}
			System.out.println("map.put(\""+entry.getKey()+"\", list);");
			map.put(entry.getKey(), list);
		}
	}

	public static void getCarKnowledgeMap(File file) {
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(
					new FileInputStream(file), "GBK");
			bufferedReader = new BufferedReader(inputStreamReader);
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				String[] contentStrings = contentString.split(",");
				genMap(contentStrings[0], new File(file.getParent(),
						contentStrings[2]));
			}
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

	public static Map<String, List<KeyValue>> Car_Knowledge = new HashMap<String, List<KeyValue>>();

	public static void genMap(String menuName, File file) {
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		List<KeyValue> list = new ArrayList<SplitCarKnowledge.KeyValue>();
		try {
			inputStreamReader = new InputStreamReader(
					new FileInputStream(file), "GBK");
			bufferedReader = new BufferedReader(inputStreamReader);
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				int index = contentString.lastIndexOf(",");
				String subMenuName = contentString.substring(0, index);
				String subMenuFile = contentString.substring(index + 1);
				list.add(new KeyValue(subMenuFile, subMenuName));
			}
			Car_Knowledge.put(menuName, list);
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

	static class KeyValue {
		private String value;
		private String key;

		public KeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		@Override
		public String toString() {
			return "key -> " + key + " , value -> " + value;
		}
	}
	static{
		Map<String, List<KeyValue>> Menu_File_Map = new HashMap<String, List<KeyValue>>();
		List<KeyValue>list = new ArrayList<KeyValue>();
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("4-1.txt","1.谈谈火花塞"));
		list.add(new KeyValue("4-2.txt","2.如何正确使用和保养火花塞"));
		list.add(new KeyValue("4-3.txt","3.自动变速器(AT)的使用"));
		list.add(new KeyValue("4-4.txt","4.润滑系统对汽车引擎寿命的影响"));
		list.add(new KeyValue("4-5.txt","5.影响油耗的因素复杂 如何看汽车油耗"));
		list.add(new KeyValue("4-6.txt","6.电动玻璃升降器"));
		list.add(new KeyValue("4-7.txt","7.汽车油耗偏高-三项主要因素"));
		list.add(new KeyValue("4-8.txt","8.如何使用千斤顶"));
		list.add(new KeyValue("4-9.txt","9.刹车及轮胎的保养对行车安全影响"));
		list.add(new KeyValue("4-10.txt","10.自己动手换轮胎"));
		list.add(new KeyValue("4-11.txt","11.机油的分类及各类机油的不同特性"));
		list.add(new KeyValue("4-12.txt","12.机油常识"));
		list.add(new KeyValue("4-13.txt","13.学会看机油尺"));
		list.add(new KeyValue("4-14.txt","14.车窗的清洁"));
		list.add(new KeyValue("4-15.txt","15.汽车检测技术入门（1）"));
		list.add(new KeyValue("4-16.txt","16.汽车检测技术入门（2）"));
		list.add(new KeyValue("4-17.txt","17.汽车检测技术入门（3）"));
		list.add(new KeyValue("4-18.txt","18.汽车检测技术入门（4）"));
		list.add(new KeyValue("4-19.txt","19.原文就没有19，懒得改标号了"));
		list.add(new KeyValue("4-20.txt","20.车用燃油的使用的知识(1)"));
		list.add(new KeyValue("4-21.txt","21.车用燃油的使用知识（2）"));
		list.add(new KeyValue("4-22.txt","22.车用燃油的使用知识(3)"));
		list.add(new KeyValue("4-22.txt","23.汽车发动机供油系统的维护及燃烧室的清洗"));
		list.add(new KeyValue("4-24.txt","24.汽车的基本构造"));
		list.add(new KeyValue("4-25.txt","25.马力、扭力"));
		list.add(new KeyValue("4-26.txt","26.多气门发动机"));
		list.add(new KeyValue("4-27.txt","27.汽油喷射发动机"));
		list.add(new KeyValue("4-28.txt","28.轿车的悬架"));
		list.add(new KeyValue("4-29.txt","29.汽车轮胎"));
		list.add(new KeyValue("4-30.txt","30.汽车轮胎的使用知识"));
		list.add(new KeyValue("4-31.txt","31.如何延长配气机构的使用寿命"));
		list.add(new KeyValue("4-32.txt","32.轿车的仪表板总成"));
		list.add(new KeyValue("4-33.txt","33.认识汽车学英文"));
		list.add(new KeyValue("4-34.txt","34.ＳＯＨＣＤＯＨＣ到底有何分别"));
		list.add(new KeyValue("4-35.txt","35.ＶＤＣ、ＢＡＳ、ＥＢＤ是何方神圣"));
		list.add(new KeyValue("4-36.txt","36.ASR：驱动防滑系统"));
		list.add(new KeyValue("4-37.txt","37.正确使用蓄电池十法"));
		list.add(new KeyValue("4-38.txt","38.主机、喇叭、功放如何搭配 车载音响指南"));
		list.add(new KeyValue("4-39.txt","39.通过外观判断自动变速器工况"));
		list.add(new KeyValue("4-40.txt","40.机油添加剂的使用技巧"));
		list.add(new KeyValue("4-41.txt","41.使用、养护都有窍门"));
		list.add(new KeyValue("4-42.txt","42.汽车电气和电子部分的维护和保养(1)"));
		list.add(new KeyValue("4-43.txt","43.汽车电气和电子部分的维护和保养（2）"));
		list.add(new KeyValue("4-44.txt","44.根据水箱材质选择适用防冻液"));
		list.add(new KeyValue("4-45.txt","45.汽车漏雨的诊治和处理"));
		list.add(new KeyValue("4-46.txt","46.免维护蓄电池"));
		list.add(new KeyValue("4-47.txt","47.蓄电池使用速成"));
		list.add(new KeyValue("4-48.txt","48.如何应付蓄电池损坏"));
		list.add(new KeyValue("4-49.txt","49.汽车蓄电池时五大误区"));
		list.add(new KeyValue("4-50.txt","50.蓄电池使用注意什么"));
		list.add(new KeyValue("4-51.txt","51.行车在外如何处理蓄电池的损坏"));
		list.add(new KeyValue("4-52.txt","52.轿车的减噪措施"));
		list.add(new KeyValue("4-53.txt","53.汽车音响的使用及售后服务"));
		list.add(new KeyValue("4-54.txt","54.油路过脏影响动力"));
		list.add(new KeyValue("4-55.txt","55.轿车使用误区"));
		list.add(new KeyValue("4-56.txt","56.随车应该携带工具"));
		list.add(new KeyValue("4-57.txt","57.给车辆机件“降降温”"));
		list.add(new KeyValue("4-58.txt","58.车蜡的选择"));
		list.add(new KeyValue("4-59.txt","59.为什么传统蜡不适合现代蜡呢？"));
		list.add(new KeyValue("4-60.txt","60.打蜡的学问"));
		list.add(new KeyValue("4-61.txt","61.车蜡的分类"));
		list.add(new KeyValue("4-62.txt","62.高温季节下的车辆维护保养与安全行驶"));
		list.add(new KeyValue("4-63.txt","63.自动变速箱故障大多源自清洗不及时"));
		list.add(new KeyValue("4-64.txt","64.清洗车窗车身内饰的窍门"));
		Menu_File_Map.put("第四篇 技术知识", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("2-1.txt","1.保养内容周期"));
		list.add(new KeyValue("2-2.txt","2.汽车部件更换周期"));
		list.add(new KeyValue("2-3.txt","3.自己动手养护汽车步骤"));
		list.add(new KeyValue("2-4.txt","4.买了新车之后首次养护不能马虎"));
		list.add(new KeyValue("2-5.txt","5.汽车保养谨防落入误区"));
		list.add(new KeyValue("2-6.txt","6.汽车的一级保养 发动机离合器等7项"));
		list.add(new KeyValue("2-7.txt","7.汽车的二级保养 整车检验电气设备等7项"));
		list.add(new KeyValue("2-8.txt","8.汽车的三级保养 检查项目及规范"));
		list.add(new KeyValue("2-9.txt","9.维修站检测项目"));
		list.add(new KeyValue("2-10.txt","10.平时养车的方方面面"));
		list.add(new KeyValue("2-11.txt","11.车基本保养指南"));
		list.add(new KeyValue("2-12.txt","12.高级汽车保养常识"));
		list.add(new KeyValue("2-13.txt","13.两年必须更换制动液"));
		list.add(new KeyValue("2-14.txt","14.底盘封塑养护"));
		list.add(new KeyValue("2-15.txt","15.油路过脏影响动力 清洗油路免去维修之苦"));
		list.add(new KeyValue("2-16.txt","16.冬季汽车使用环境差 给爱车做次彻底清洁"));
		list.add(new KeyValue("2-17.txt","17.汽车磨合期保养"));
		list.add(new KeyValue("2-18.txt","18.别克汽车日常维护常识"));
		list.add(new KeyValue("2-19.txt","19.富康轿车的保养方法"));
		list.add(new KeyValue("2-20.txt","20.富康轿车的日常性维护"));
		list.add(new KeyValue("2-21.txt","21.进口、国产车维修保养费用对比"));
		list.add(new KeyValue("2-22.txt","22.自己动手为爱车做换季保养有八步"));
		list.add(new KeyValue("2-23.txt","23.自驾游行前车辆的准备工作"));
		list.add(new KeyValue("2-24.txt","24.出游归来话保修"));
		list.add(new KeyValue("2-25.txt","25.假日出行 自驾游归来应给爱车做全面保养"));
		list.add(new KeyValue("2-26.txt","26.雨季保养"));
		list.add(new KeyValue("2-27.txt","27.夏季爱车清凉三部曲"));
		list.add(new KeyValue("2-28.txt","28.盛夏日常保养"));
		list.add(new KeyValue("2-29.txt","29.夏日防开锅"));
		list.add(new KeyValue("2-30.txt","30.夏季，给汽车降温有四忌"));
		list.add(new KeyValue("2-31.txt","31.驾车行驶前、中、后 如何进行检查保养？"));
		list.add(new KeyValue("2-32.txt","32.内饰保养"));
		list.add(new KeyValue("2-33.txt","33.合金轮毂养护"));
		list.add(new KeyValue("2-34.txt","34.如何延长汽车发动机的寿命"));
		list.add(new KeyValue("2-35.txt","35.清洗发动机冷却系统水垢"));
		list.add(new KeyValue("2-36.txt","36.轿车发动机50万km无大修解秘"));
		list.add(new KeyValue("2-37.txt","37.让汽车心脏保持正常 别忽视发动机积炭"));
		list.add(new KeyValue("2-38.txt","38.汽车发动机保养的六大要点"));
		list.add(new KeyValue("2-39.txt","39.养护发动机防止机油污染"));
		list.add(new KeyValue("2-40.txt","40.润滑对汽车引擎寿命的影响"));
		list.add(new KeyValue("2-41.txt","41.按照要求更换制动液 保障汽车的行驶安全"));
		list.add(new KeyValue("2-42.txt","42.润滑系统保养"));
		list.add(new KeyValue("2-43.txt","43.两年必须更换制动液"));
		list.add(new KeyValue("2-44.txt","44.机油性能的简易检测法吸油点滴法"));
		list.add(new KeyValue("2-45.txt","45.正确维护自动变速器"));
		list.add(new KeyValue("2-46.txt","46.正确使用自动变速器油 保自动变速器长命"));
		list.add(new KeyValue("2-47.txt","47.自动变速箱换油不简单"));
		list.add(new KeyValue("2-48.txt","48.汽车例行养护时切记要勤查三水"));
		list.add(new KeyValue("2-49.txt","49.汽车三滤日常保养"));
		list.add(new KeyValue("2-50.txt","50.维护保养之空滤清洁篇"));
		list.add(new KeyValue("2-51.txt","51.燃油滤清器的原理和保养"));
		list.add(new KeyValue("2-52.txt","52.清洗油路可增加汽车动力"));
		list.add(new KeyValue("2-53.txt","53.维护保养之机油选择篇"));
		list.add(new KeyValue("2-54.txt","54.传动轴的保养"));
		list.add(new KeyValue("2-55.txt","55.刹车片更换周期"));
		list.add(new KeyValue("2-56.txt","56.刹车系统"));
		list.add(new KeyValue("2-57.txt","57.刹车系统日常养护"));
		list.add(new KeyValue("2-58.txt","58.汽车底盘的养护"));
		list.add(new KeyValue("2-59.txt","59.手刹的检测和养护"));
		list.add(new KeyValue("2-60.txt","60.汽车燃油箱清洗方法"));
		list.add(new KeyValue("2-61.txt","61.勤换机油保平安"));
		list.add(new KeyValue("2-62.txt","62.汽车油路和水路的保养"));
		list.add(new KeyValue("2-63.txt","63.汽车保养莫忘清洁油路"));
		list.add(new KeyValue("2-64.txt","64.电喷汽车进气系统应该定期进行清洗保养"));
		list.add(new KeyValue("2-65.txt","65.空调维护"));
		list.add(new KeyValue("2-66.txt","66.汽车空调：换季时必须清洗"));
		list.add(new KeyValue("2-67.txt","67.汽车外观的维护"));
		list.add(new KeyValue("2-68.txt","68.维护保养之打蜡篇"));
		list.add(new KeyValue("2-69.txt","69.汽车全面养护--如何防止汽车各部件老化"));
		list.add(new KeyValue("2-70.txt","70.快修划痕要确保质量 请关注五项补漆工艺"));
		list.add(new KeyValue("2-71.txt","71.如何快速修复划痕"));
		list.add(new KeyValue("2-72.txt","72.快速修复车身的划痕"));
		list.add(new KeyValue("2-73.txt","73.秋季爱车美容选封釉"));
		list.add(new KeyValue("2-74.txt","74.汽车打蜡有诀窍"));
		list.add(new KeyValue("2-75.txt","75.清洗车窗车身内饰的窍门"));
		list.add(new KeyValue("2-76.txt","76.让爱车保持干净 中控台的清洁要领"));
		list.add(new KeyValue("2-77.txt","77.汽车外观的维护"));
		list.add(new KeyValue("2-78.txt","78.车厢异味如何检查"));
		list.add(new KeyValue("2-79.txt","79.座椅地毯保养"));
		list.add(new KeyValue("2-80.txt","80.春季车内清洗"));
		list.add(new KeyValue("2-81.txt","81.沙尘过后的汽车清理"));
		list.add(new KeyValue("2-82.txt","82.换铝圈时要注意 以免威胁行车时的安全"));
		list.add(new KeyValue("2-83.txt","83.怎样给汽车的旧轮胎换件新衣裳"));
		list.add(new KeyValue("2-84.txt","84.电动车窗保养"));
		list.add(new KeyValue("2-85.txt","85.雨刮器定期检查"));
		list.add(new KeyValue("2-86.txt","86.你会洗车吗"));
		list.add(new KeyValue("2-87.txt","87.自己动手维护保养玻璃清洗器"));
		list.add(new KeyValue("2-88.txt","88.汽车停驶期间的养护措施"));
		Menu_File_Map.put("第二篇 保养", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("3-1.txt","1.怎样正确选购汽车零配件"));
		list.add(new KeyValue("3-2.txt","2.如何鉴别假冒伪劣汽车配件"));
		list.add(new KeyValue("3-3.txt","3.修车少花冤枉钱 车主要谨防四大骗招"));
		list.add(new KeyValue("3-4.txt","4.远离修车点时 汽车底盘机件损坏急救有方"));
		list.add(new KeyValue("3-5.txt","5.教你如何制服水温偏高"));
		list.add(new KeyValue("3-6.txt","6.汽车常见故障及预防解决措施"));
		list.add(new KeyValue("3-7.txt","7.千万不可轻视汽车悬挂系统"));
		list.add(new KeyValue("3-8.txt","8.汽车故障预测八法"));
		list.add(new KeyValue("3-9.txt","9.听、试、看、想 如何判断发动机异响"));
		list.add(new KeyValue("3-10.txt","10.汽车空调四种常见故障现象的检查和判断"));
		list.add(new KeyValue("3-11.txt","11.怎样从轮胎磨损情况判断汽车故障"));
		list.add(new KeyValue("3-12.txt","12.节温器要随车检查 切记不可将就使用"));
		list.add(new KeyValue("3-13.txt","13.行车在外如何处理蓄电池的损坏"));
		list.add(new KeyValue("3-14.txt","14.在修理汽车时对密封性要注意以下几点"));
		list.add(new KeyValue("3-15.txt","15.气压制动系统的故障诊断"));
		list.add(new KeyValue("3-16.txt","16.液压制动系统故障的诊断"));
		list.add(new KeyValue("3-17.txt","17.诊断蓄电池故障的简便方法"));
		list.add(new KeyValue("3-18.txt","18.借助仪表灯判断车辆故障"));
		Menu_File_Map.put("第三篇 维修", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("1-1.txt","1.汽车启动三步曲"));
		list.add(new KeyValue("1-2.txt","2.开车注意事项"));
		list.add(new KeyValue("1-3.txt","3.自动挡汽车的正确驾驶"));
		list.add(new KeyValue("1-4.txt","4.自动档中途熄火怎么办"));
		list.add(new KeyValue("1-5.txt","5.教你开车省油15招"));
		list.add(new KeyValue("1-6.txt","6.正确使用方向盘"));
		list.add(new KeyValue("1-7.txt","7.发动机油门使用要点"));
		list.add(new KeyValue("1-8.txt","8.夏季行车十防"));
		list.add(new KeyValue("1-9.txt","9.离合器正确使用要诀"));
		list.add(new KeyValue("1-10.txt","10.驾驶技巧"));
		list.add(new KeyValue("1-11.txt","11.轿车操作与节能"));
		list.add(new KeyValue("1-12.txt","12.车辆驾驶如何既省油又有利于环保"));
		list.add(new KeyValue("1-3.txt","13.驾驶方式不当是轮胎磨损主因"));
		Menu_File_Map.put("第一篇 驾驶篇", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("5-1.txt","1.保险如何赔"));
		list.add(new KeyValue("5-2.txt","2.汽车保险问答"));
		Menu_File_Map.put("第五篇、保险篇", list);
		list = new ArrayList<KeyValue>();
		list.add(new KeyValue("6-1.txt","1.新车验收"));
		list.add(new KeyValue("6-2.txt","2.保险技巧"));
		list.add(new KeyValue("6-3.txt","3.保险索赔"));
		Menu_File_Map.put("其他资料", list);

	}
}
