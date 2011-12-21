package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GenCategoryInAFile {
	static FileFilter txtFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("txt");
		}
	};
	private static String categoryFile = "D:/my apk/________other__________/父母必知的100个教子智慧/assets/catalog.txt";
	private static String txt_charset = "utf8";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getList();
		System.out.println("ok~~~~~~~~~~~~~~");
	}

	public static void getList() {
		File file = new File(categoryFile);
		FileInputStream fis = null;
		BufferedReader bufferedReader = null;
		InputStreamReader isReader = null;
		int count = 0;
		try {
			fis = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(fis,
					txt_charset));
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				count++;
				System.out.println("MENU_List.add(new KeyValue(\"" + count
						+ "\", \"" + count + "、" + contentString + "\"));");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (fis != null) {
					fis.close();
				}
				if (isReader != null) {
					isReader.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
