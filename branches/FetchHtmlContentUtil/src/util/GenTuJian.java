package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class GenTuJian {
	static FileFilter txtFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("txt");
		}
	};
	private static String directoryPath = "D:/cxl/my apk/__________others_________/著名国宝图鉴/assets/txt";
	private static String gb2312 = "gb2312";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getTuJianList();
		System.out.println("ok~~~~~~~~~~~~~~");
	}

	public static void getTuJianList() {
		File fileDir = new File(directoryPath);
		if (fileDir.isDirectory()) {
			for (File file : fileDir.listFiles(txtFilter)) {
				FileInputStream fis = null;
				BufferedReader bufferedReader = null;
				InputStreamReader isReader = null;

				try {
					fis = new FileInputStream(file);
					bufferedReader = new BufferedReader(new InputStreamReader(fis, gb2312));
					String contentString = "";
					while (null != (contentString = bufferedReader.readLine())) {
						System.out.println("list.add(\""
								+ file.getName().substring(0, file.getName().lastIndexOf(".txt")) + "、" + contentString
								+ "\");");
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

}
