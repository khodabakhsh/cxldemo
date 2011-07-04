package html2pdf.servlet;

import html2pdf.tool.HtmlToPdf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class Jsp2pdf extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HtmlToPdf xhtmlToPdf = new HtmlToPdf();

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 通过以下两个方法都可以生成pdf
//		html2pdfByServletDemo(request, response);
		 html2pdfByUrlDemo(request, response);
	}

	/**
	 * 通过获取servlet输出流获得html内容
	 */
	public void html2pdfByServletDemo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		String requestUrl = request.getParameter("requestUrl");

		RequestDispatcher rd = sc.getRequestDispatcher(requestUrl);

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final ServletOutputStream stream = new ServletOutputStream() {
			public void write(byte[] data, int offset, int length) {
				os.write(data, offset, length);
			}

			public void write(int b) throws IOException {

				os.write(b);
			}
		};
		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
		HttpServletResponse rep = new HttpServletResponseWrapper(response) {
			public ServletOutputStream getOutputStream() {
				return stream;
			}

			public PrintWriter getWriter() {
				return pw;
			}
		};

		rd.include(request, rep);
		pw.flush();
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		xhtmlToPdf.getPdfResponseByInputStream(is, requestUrl, request,
				response);
	}

	/**
	 * 直接用url访问获得html内容，用url访问应该传入cookie的sessionid
	 */
	public void html2pdfByUrlDemo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requestUrl = "/OA/webReport/receivedoc.jsp?processId=3390";
		URL url = new URL(getBasePath(request) + requestUrl);
		HttpURLConnection httpurlconnection = (HttpURLConnection) url
				.openConnection();
		httpurlconnection.setRequestProperty("Cookie", "JSESSIONID="
				+ request.getSession().getId());

		InputStream urlStream = httpurlconnection.getInputStream();
		BufferedInputStream buff = new BufferedInputStream(urlStream);
		Reader reader = new InputStreamReader(buff, "utf-8");
		BufferedReader bufferReader = new BufferedReader(reader);
		xhtmlToPdf.getPdfResponseByReader(bufferReader, requestUrl, request,
				response);
	}

	private String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}
}
