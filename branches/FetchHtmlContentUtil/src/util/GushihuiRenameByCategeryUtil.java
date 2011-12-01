package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class GushihuiRenameByCategeryUtil {
	private static File fileDirectory = new File(
			"C:/Documents and Settings/caixl/桌面/gzczcm");

	private static FileFilter notTxtFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return !pathname.getName().endsWith(".txt");

		}
	};
	public static void renameFile(File parent, String oldFileName,String newFileName) {
		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), newFileName);
		oldFile.renameTo(newFile);
	}
	public static void main(String[] args) {
		File[] categoryFiles = fileDirectory.listFiles();
		int allFileCount = 1;
		for (File categoryFile : categoryFiles) {
			if (categoryFile.isDirectory()) {
				File[] gushiFiles = categoryFile.listFiles(notTxtFilter);
				int singleCategoryFileCount = 0;
				File indexFile = new File(categoryFile, "index.txt");
				Reader reader = null;
				BufferedReader bufferedReader = null;
				
				try {
					reader = new FileReader(indexFile);
					bufferedReader = new BufferedReader(reader);
					String contentString = "";
					while (null != (contentString = bufferedReader.readLine())) {
						renameFile(categoryFile,gushiFiles[singleCategoryFileCount++].getName(),String.valueOf(allFileCount));
						System.out.println("MENU_List.add(new KeyValue(\""+(allFileCount++)+"\", \""+categoryFile.getName()+"  "+contentString+"\"));");
						
					}
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
			}
		}

		System.out.println("ok");
		
	}
}
