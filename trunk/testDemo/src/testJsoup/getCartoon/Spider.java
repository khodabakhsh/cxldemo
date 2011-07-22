package testJsoup.getCartoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {

	/**
	 * http://i2534.iteye.com/blog/751830
	 * 魔兽世界的官方漫画<王者归来>看看,体会下刀疤男的复仇历程.google后发现uuu9上有中文版,
	 * 但是只能在线看,每次都要点击图片最大化看,很烦.于是想下载下来看.用firebug查看,发现页面写的还算规范.
	 * 就是用jsoup解析图片地址,然后用url下载下来.很简单,也不想用多线程了
	 */
	public static void main(String[] args) {
		File dir = new File("F:/国王归来");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Spider spider = new Spider();
		spider.catalog(dir, "http://wow.uuu9.com/2008/200812/187521.shtml");
		spider.prey(dir);
	}

	/**
	 * 分析目录
	 * 
	 * @param address
	 */
	private void catalog(File dir, String address) {
		try {
			URL url = new URL(address);
			Document doc = Jsoup.parse(url, 1000 * 3);
			Element body = doc.body();
			Element textworld = body.getElementsByClass("textworld").first();
			Element table = textworld.getElementsByTag("table").first();
			Elements hrefs = table.getElementsByTag("a");

			Map<File, String> map = new LinkedHashMap<File, String>();
			File catalog = new File(dir, "catalog.txt");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(catalog), "UTF-8"));
			for (Element a : hrefs) {
				if (a.children().size() == 0) {
					continue;
				}
				Element strong = a.child(0);
				if (strong != null
						&& strong.tagName().equalsIgnoreCase("strong")) {
					String title = strong.text()
							.replaceAll("[\\.\\-\\:：]", "_")
							.replaceAll("\\s", "");
					File f = new File(dir, title);
					if (!f.exists()) {
						f.mkdirs();
					}
					String href = a.attr("href");
					bw.write(title + "(" + href + ")\r\n");

					map.put(f, href);
				}
			}
			bw.close();

			for (Map.Entry<File, String> entry : map.entrySet()) {
				File f = entry.getKey();
				Set<String> set = new LinkedHashSet<String>();
				this.section(set, entry.getValue());
				OutputStreamWriter osw = new OutputStreamWriter(
						new FileOutputStream(new File(f, "catalog.txt")),
						"UTF-8");
				int i = 1;
				for (String src : set) {
					osw.write(i++ + "(" + src + ")\r\n");
				}
				osw.close();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分析章节
	 * 
	 * @param address
	 *            章节地址
	 */
	private void section(Set<String> set, String address) {
		try {
			URL url = new URL(address);
			Document doc = Jsoup.parse(url, 1000 * 10);
			Element body = doc.body();
			Element div = body.getElementsByClass("textworld").first();
			Element img = div.getElementsByTag("img").first();

			String src = img.attr("src");
			System.out.println(src);
			set.add(src);

			Element none = div.getElementById("pagecount");
			Element links = none.previousElementSibling();
			Element font = links.getElementsByTag("font").first();
			Element next = font.nextElementSibling();
			if (next.text().matches("\\[\\d+\\]")) {
				this.section(set, next.absUrl("href"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载图片
	 * 
	 * @param dir
	 */
	private void prey(File dir) {
		for (File f : dir.listFiles()) {
			if (!f.isDirectory()) {
				continue;
			}
			File catalog = new File(f, "catalog.txt");
			if (!catalog.exists()) {
				continue;
			}
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(catalog),
								"UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					String path = line.substring(line.indexOf("(") + 1, line
							.length() - 1);
					try {
						URL url = new URL(path);
						HttpURLConnection con = (HttpURLConnection) url
								.openConnection();
						InputStream is = con.getInputStream();
						OutputStream os = new FileOutputStream(new File(f, path
								.substring(path.lastIndexOf("/") + 1)));
						byte[] b = new byte[1024 * 4];
						int l = -1;
						while ((l = is.read(b)) != -1) {
							os.write(b, 0, l);
						}
						os.flush();
						os.close();
						con.disconnect();
						System.out.println(path + " download to "
								+ dir.getCanonicalPath() + " complete.");
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				reader.close();
				if (catalog.renameTo(new File(f, "catalog"))) {
					catalog.delete();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
