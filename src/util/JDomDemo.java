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
	private static String baseFilePath = "D:/cxl/my apk/__________others_________/中篇笑话精选1/assets/";
	//	private static String titleFilePath = "D:\\cxl\\my apk\\__________others_________\\世界上下五千年\\res\\xml\\a00.xml";
	private static String genFilePath = "D:/cxl/my apk/__________others_________/中篇笑话精选1/assets_new";
	private static String suffix = ".txt";
	private static Map<String, Integer> map = new HashMap<String, Integer>();
	static {
		File genFile = new File(genFilePath);
		if (!genFile.exists()) {
			genFile.mkdirs();
		}

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
				//				 titleName=
				//				 titleName.substring(titleName.indexOf("　")+"　".length());
				System.out.println("MENU_List.add(new KeyValue(\"" + i + "\", \"" + i + "、" + titleName + "\"));");
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
				System.out.println(" map.put(\"" + titleName + "\", " + i + ");");
				;
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static int genFilecount = 0;
	static int jokeIdcount = 0;
	static StringBuffer bf = new StringBuffer();

	

	public static void parseContentXml(File orgFile) {
		SAXBuilder builder = new SAXBuilder(false);
		try {
			Document document = builder.build(orgFile);
			Element root = document.getRootElement();
			List<Element> jokeList = root.getChildren("joke");
			Element element;
			FileWriter writer = null;
			File file;
			String titleName;
			String content;
			String id;

			for (int i = 0; i < jokeList.size(); i++) {
				element = jokeList.get(i);
//				id = element.getChildText("id");
				jokeIdcount++;
				titleName = element.getChildText("title");
				content = element.getChildText("text").trim();

				bf.append(jokeIdcount + "、" + titleName + "\n" + content + "\n\n\n");

				if (jokeIdcount % 100 == 0) {
					System.out.println(bf);
					file = new File(genFilePath, (++genFilecount) + suffix);
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					if (!file.exists()) {
						file.createNewFile();
					}
					writer = new FileWriter(file);
					writer.write(bf.toString());
					writer.flush();
					if (writer != null) {
						writer.close();
					}
					bf = new StringBuffer("");
				}
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static FileFilter xmlFileFilter = new FileFilter() {

		public boolean accept(File pathname) {
			return pathname.getName().endsWith(".xml");
		}
	};

	public static void main(String[] args) throws Exception {
		//				genTitleMap(titleFilePath);
		//				genTitleKeyValue(titleFilePath);
		//		File baseFile = new File(baseFilePath);
		//		FileFilter filenameFilter = new FileFilter() {
		//			public boolean accept(File pathname) {
		//				return !pathname.equals("a00.xml");
		//			}
		//		};
		File[] fileList = new File(baseFilePath).listFiles(xmlFileFilter);
		for (File file : fileList) {

			//			parseContentXml(file);
			//			break;
			parseContentXml(file);
			//			break;
		}

	}
}