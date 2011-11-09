package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SplitOralEnglish {
	public static List<String> list = new ArrayList<String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File fileDir = new File("C:/Documents and Settings/caixl/桌面/背完这999句,口语基本上没有问题啦！.txt");
		System.out.println(split(fileDir));

	}

	public static int split(File file) {
		Reader reader = null;
		BufferedReader bufferedReader = null;
		try {
			reader = new FileReader(file);
			bufferedReader = new BufferedReader(reader);
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				list.add(contentString);
				System.out.println("list.add(\""+contentString+"\");");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return list.size();
	}

}
