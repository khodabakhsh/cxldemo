package com.bincode.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * 基本工具类
 * 
 * @author bincode
 * @email	5235852@qq.com
 */
public class JavaUtils {
	public static String classpath = JavaUtils.class.getResource("/").getFile();
	
	/**
	 * 正则匹配
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static String[] match(String s, String pattern) {
		Matcher m = Pattern.compile(pattern).matcher(s);

		while (m.find()) {
			int n = m.groupCount();
			String[] ss = new String[n + 1];
			for (int i = 0; i <= n; i++) {
				ss[i] = m.group(i);
			}
			return ss;
		}
		return null;
	}

	/**
	 * 正则匹配
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static List<String[]> matchAll(String s, String pattern) {
		Matcher m = Pattern.compile(pattern).matcher(s);
		List<String[]> result = new ArrayList<String[]>();

		while (m.find()) {
			int n = m.groupCount();
			String[] ss = new String[n + 1];
			for (int i = 0; i <= n; i++) {
				ss[i] = m.group(i);
			}
			result.add(ss);
		}
		return result;
	}

	/**
	 * 正则匹配，指定开始位置
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static String[] firstMatch(String s, String pattern, int startIndex) {
		Matcher m = Pattern.compile(pattern).matcher(s);

		if (m.find(startIndex)) {
			int n = m.groupCount();
			String[] ss = new String[n + 1];
			for (int i = 0; i <= n; i++) {
				ss[i] = m.group(i);
			}
			return ss;
		}
		return null;
	}

	/**
	 * 正则匹配
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static boolean isMatch(String s, String pattern) {
		return s.matches(pattern);
	}

	public static boolean isAllMatch(String s, String pattern) {
		Matcher m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(s);
		while (m.find()) {
			return true;
		}
		return false;
	}


	/**
	 * 将String 解析成Documnet
	 * @param tempbodyStr
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(String tempbodyStr) throws Exception{
		String bodyStr = tempbodyStr.replace("　", " ").replaceAll("&nbsp;", " ");
		// 生成html parser
		DOMParser parser = new DOMParser();
		// 设置网页的默认编码
		
		parser.setFeature("http://xml.org/sax/features/namespaces", false);
		parser.setProperty(
				"http://cyberneko.org/html/properties/default-encoding",
				"GBK");
		java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(
				bodyStr.getBytes("GBK"));
		InputStreamReader fileIn = new InputStreamReader(bis, "GBK");
		BufferedReader in = new BufferedReader(fileIn);
		parser.parse(new InputSource(in));
		Document doc = parser.getDocument();
		return doc;
	}
	
	public static String XmlToString(Node node) {
		try {
			//org.apache.xml.serializer.TreeWalker;
			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString().replaceAll("<\\?.*\\?>", "");
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取结点的Text值
	 * @param node
	 * @return
	 */
	public static String getTextContent(Node node){
		if(node == null) return null;
		String textContent = node.getTextContent();
		if(textContent == null) return textContent;
		return textContent.trim();
	}
	
	/**
	 * 获取结点的属性值
	 * @param node
	 * @param attrName
	 * @return
	 */
	public  static String getNodeValue(Node node, String attrName){
		if(node == null || node.getAttributes()==null) return null;
		Node attrNode = node.getAttributes().getNamedItem(attrName);
		if(attrNode == null || attrNode.getNodeValue() == null) return null;
		return attrNode.getNodeValue().trim();
	}

	/**
	 * 获取结点的属性值
	 * @param node
	 * @param attrName
	 * @return
	 */
	public  static Node getNodeAttr(Node node, String attrName){
		if(node == null || node.getAttributes()==null) return null;
		Node attrNode = node.getAttributes().getNamedItem(attrName);
		return attrNode;
	}
	/**
	 * 获取结点的Tag (XML)
	 * @param node
	 * @return
	 */
	public static String getTagContent(Node node){
		return JavaUtils.XmlToString(node);
	}
	
	public static String toPostParam(Map<String,String> map) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		for(Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();){
			Entry<String, String> entry = it.next();
			String value = entry.getValue().trim();
			sb.append(entry.getKey().trim()+"="+value+"&");
		}
		return sb.substring(0, sb.length()-1);
	}
}