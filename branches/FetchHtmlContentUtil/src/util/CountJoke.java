package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CountJoke {
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
		int sum = 0;
		File fileDir = new File("C:/Documents and Settings/caixl/桌面/冷笑话f/assets/OPS/");
		if (fileDir.isDirectory()) {
			for (File file : fileDir.listFiles(filter)) {
				sum += countJoke(file);

			}
		}
		System.out.println("总个数： " + sum);

	}

	public static int countJoke(File file) {
		Reader reader = null;
		BufferedReader bufferedReader = null;
		int count = 0;
		try {
			reader = new FileReader(file);
			bufferedReader = new BufferedReader(reader);
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				if ("<p>------------------------</p>".equals(contentString)) {
					count++;
				}
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
		int returnCount = (count == 0 ? 0 : count + 1);
		System.out.println(file.getAbsolutePath() + " 个数： " + returnCount);
		return returnCount;
	}

}
