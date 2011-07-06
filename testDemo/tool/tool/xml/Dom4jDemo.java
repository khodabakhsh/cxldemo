package tool.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * DOM4J生成和解析XML文档 依赖包：jaxen-1.0-FCS.jar saxpath-1.0-FCS.jar
 */
public class Dom4jDemo implements XmlDocument {
	public void createXml(String fileName) {
		Document document = DocumentHelper.createDocument();
		Element employees = document.addElement("employees");
		Element employee = employees.addElement("employee");
		Element name = employee.addElement("name");
		name.setText("ddvip");
		Element sex = employee.addElement("sex");
		sex.setText("m");
		Element age = employee.addElement("age");
		age.setText("29");
		try {
			Writer fileWriter = new FileWriter(fileName);
			XMLWriter xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void parserXml(String fileName) {
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			for (Iterator i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				for (Iterator j = employee.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					System.out.println(node.getName() + ":" + node.getText());
				}
			}
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("dom4j parserXml");
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		String file = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath()
				+ "/tool/xml/employees.xml";
		File inputXml = new File(file);
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputXml);
		Element root = document.getRootElement();
		List<Element> list = root.selectNodes("/employees/employee");
		for (int i = 0; i < list.size(); i++) {
			Element element = list.get(i);
			System.out.println("第" + i + "个 name is : "
					+ element.element("name").getText() + " ，特点：【"
					+ element.attributeValue("type")+"】");
		}
	}
}