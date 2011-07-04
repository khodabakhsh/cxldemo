package ldap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XMLParaReader {
	private SAXReader reader;
	Document document;
	private Node node;
	Element element;

	public XMLParaReader(String fileName) throws Exception {
		try {
			reader = new SAXReader();
			document = reader.read(Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(fileName));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("读取文件(" + fileName + ")失败,请检查文件路径");
		}
	}

	public void setRoot(String e) {
		element = this.document.getRootElement().element(e);

	}

	public String getTextValue(String nodeName) {
		Node n = this.element.element(nodeName);
		if (n != null)
			return n.getText();
		else
			return "";

	}

	public String getAttributeValue(String nodeName, String attribute) {
		Element e = this.element.element(nodeName);
		if (e != null)
			return e.attributeValue(attribute);
		else
			return "";

	}

}
