package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GenXinLiJianYa {
	static FileFilter txtFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("txt");
		}
	};
	private static String directoryPath = "D:/cxl/my apk/__________others_________/世界100强企业/assets/txt";
	private static String gb2312 = "gb2312";
	private static String utf8 = "utf8";
	private static String txt_charset = gb2312;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getList();
		System.out.println("ok~~~~~~~~~~~~~~");
	}

	public static void getList() {
		File fileDir = new File(directoryPath);
			for (File file : fileDir.listFiles(txtFilter)) {
				FileInputStream fis = null;
				BufferedReader bufferedReader = null;
				InputStreamReader isReader = null;

				try {
					fis = new FileInputStream(file);
					bufferedReader = new BufferedReader(new InputStreamReader(fis, txt_charset));
					String contentString = "";
					while (null != (contentString = bufferedReader.readLine())) {
						String fileNameIndexString = file.getName().substring(0, file.getName().lastIndexOf(".txt"));
						System.out.println("MENU_List.add(new KeyValue(\"" + fileNameIndexString+ "\", \""
								+ fileNameIndexString + "、" + contentString + "\"));");
						break;
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

}
