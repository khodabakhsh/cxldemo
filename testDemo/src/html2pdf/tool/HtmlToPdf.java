package html2pdf.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

public class HtmlToPdf {
	@SuppressWarnings("deprecation")
	// 传入html页面流，产生pdf
	public void getPdfResponseByInputStream(InputStream is, String requestUrl,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String contentString = Html2Xhtml.doTidy(is);
		try {
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ "html2pdf.pdf" + "\"");
			String pageUrl = request.getRealPath("/")
					+ requestUrl.substring(0, requestUrl.lastIndexOf("?"));

			response.getOutputStream().write(
					genPdfFromHtmlString(addHtmlStyle(contentString), pageUrl)
							.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		is.close();
	}

	public void getPdfResponseByReader(Reader reader, String requestUrl,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String contentString = Html2Xhtml.doTidy(reader);
		try {
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ "html2pdf.pdf" + "\"");
			String pageUrl = request.getRealPath("/")
					+ requestUrl.substring(0, requestUrl.lastIndexOf("?"));

			response.getOutputStream().write(
					genPdfFromHtmlString(addHtmlStyle(contentString), pageUrl)
							.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		reader.close();
	}

	private ByteArrayOutputStream genPdfFromHtmlString(String contentString,
			String pageUrl) throws DocumentException, IOException {
		System.out.println(contentString);
		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(contentString);

		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("C:/Windows/Fonts/SIMSUN.TTC",
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		// 解决图片的相对路径问题
		renderer.getSharedContext().setBaseURL("file:/" + pageUrl);

		renderer.layout();
		ByteArrayOutputStream byteArrayOs = new ByteArrayOutputStream();
		renderer.createPDF(byteArrayOs);
		return byteArrayOs;
	}

	// 增加字体样式，支持中文
	private String addHtmlStyle(String contentString) {
		contentString.indexOf("<head>");
		String[] strs = contentString.split("<head>");
		if (strs.length == 2) {
			strs[1] = "<style type=\"text/css\">body {font-family: SimSun;	}</style>"
					+ strs[1];

			return strs[0] + "<head>" + strs[1];
		} else
			return "";
	}
}
