package html2pdf.tool;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;

import org.w3c.tidy.Tidy;

public class Html2Xhtml {

	public static String doTidy(String urlStr) {
		URL url;
		BufferedInputStream sourceIn;
		ByteArrayOutputStream tidyOutStream;
		try {
			url = new URL(urlStr);
			
			sourceIn = new BufferedInputStream(url.openStream());
			tidyOutStream = new ByteArrayOutputStream();
			Tidy tidy = new Tidy();
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			tidy.setIndentContent(true);
			tidy.setSmartIndent(true);
			tidy.setIndentAttributes(false);
			tidy.setWraplen(1024);
			// 输出为 XHTML
			tidy.setXHTML(true);
			// tidy.setXmlOut(true);
			tidy.setErrout(new PrintWriter(System.out));
			tidy.setInputEncoding("UTF-8");
			tidy.setOutputEncoding("UTF-8");
			tidy.parse(sourceIn, tidyOutStream);

			System.out.println(tidyOutStream.toString());
			return tidyOutStream.toString();
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
		return null;
	}

	public static ByteArrayOutputStream doTidy(URL url) {

		BufferedInputStream sourceIn;
		ByteArrayOutputStream tidyOutStream;
		try {

			sourceIn = new BufferedInputStream(url.openStream());
			tidyOutStream = new ByteArrayOutputStream();
			Tidy tidy = new Tidy();
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			tidy.setIndentContent(true);
			tidy.setSmartIndent(true);
			tidy.setIndentAttributes(false);
			tidy.setWraplen(1024);
			// 输出为 XHTML
			tidy.setXHTML(true);
			// tidy.setXmlOut(true);
			tidy.setInputEncoding("UTF-8");
			tidy.setOutputEncoding("UTF-8");

			tidy.setErrout(new PrintWriter(System.out));
			tidy.parse(sourceIn, tidyOutStream);

			return tidyOutStream;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
		return null;
	}

	public static String doTidy(InputStream is) {

		ByteArrayOutputStream tidyOutStream = new ByteArrayOutputStream();
		;
		try {

			Tidy tidy = new Tidy();
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			tidy.setIndentContent(true);
			tidy.setSmartIndent(true);
			tidy.setIndentAttributes(false);
			tidy.setWraplen(1024);
			// 输出为 XHTML
			tidy.setXHTML(true);
			// tidy.setXmlOut(true);
			tidy.setErrout(new PrintWriter(System.out));

			tidy.setSpaces(0);

			tidy.setInputEncoding("gb2312");
			tidy.setOutputEncoding("gb2312");

			tidy.parse(is, tidyOutStream);

			System.out.println(tidyOutStream.toString());

			// FileOutputStream fos = new FileOutputStream("d://tidy2.htm");
			// tidyOutStream.writeTo(fos);

			return tidyOutStream.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public static String doTidy(Reader reader) {

		ByteArrayOutputStream tidyOutStream = new ByteArrayOutputStream();
		;
		try {

			Tidy tidy = new Tidy();
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			tidy.setIndentContent(true);
			tidy.setSmartIndent(true);
			tidy.setIndentAttributes(false);
			tidy.setWraplen(1024);
			// 输出为 XHTML
			tidy.setXHTML(true);
			// tidy.setXmlOut(true);
			tidy.setErrout(new PrintWriter(System.out));

			tidy.setSpaces(0);

			tidy.setInputEncoding("gb2312");
			tidy.setOutputEncoding("gb2312");

			tidy.parse(reader, tidyOutStream);

			System.out.println(tidyOutStream.toString());

			// FileOutputStream fos = new FileOutputStream("d://tidy2.htm");
			// tidyOutStream.writeTo(fos);

			return tidyOutStream.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}