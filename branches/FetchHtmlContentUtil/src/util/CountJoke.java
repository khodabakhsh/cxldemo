package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;

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
		int count = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String contentString = "";
			while (null != (contentString = reader.readLine())) {
				if ("<p>------------------------</p>".equals(contentString)) {
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int returnCount = (count == 0 ? 0 : count + 1);
		System.out.println(file.getAbsolutePath() + " 个数： " + returnCount);
		return returnCount;
	}

}
