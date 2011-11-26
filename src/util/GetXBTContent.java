package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class GetXBTContent {
	static FileFilter filter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("html");
		}
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File v = new File(
				"C:/Documents and Settings/Administrator/桌面/新建 文本文档.txt");
		;
		getXBTContent(v);

	}

	public static Map<String, String> getXBTContent(File file) {
		Map<String, String> Title_Content_Map = new HashMap<String, String>();
		Reader reader = null;
		BufferedReader bufferedReader = null;
		int count = 0;
		StringBuffer oneJokeString = new StringBuffer();
		try {
			reader = new FileReader(file);
			bufferedReader = new BufferedReader(reader);
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				if (!"".equals(contentString)) {
					oneJokeString.append(contentString+"\n");
				} else {
					Title_Content_Map.put(String.valueOf(count++),
							oneJokeString.toString());
					System.out.println("Title_Content_Map.put(\""
							+ String.valueOf(count) + "\",\""
							+ oneJokeString.toString().replace("\"", "\\\"").replace("\n", "\\n") + "\");");
					oneJokeString = new StringBuffer();
				}
			}
			//最后一个
				Title_Content_Map.put(String.valueOf(count++),
						oneJokeString.toString());
				System.out.println("Title_Content_Map.put(\""
						+ String.valueOf(count) + "\",\""
						+ oneJokeString.toString().replace("\"", "\\\"").replace("\n", "\\n") + "\");");
				oneJokeString = new StringBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return Title_Content_Map;
	}

}
