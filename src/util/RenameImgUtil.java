package util;

import java.io.File;
import java.io.FileFilter;

public class RenameImgUtil {
	private static int typeIndex = 0;
	private static int countIndex = 0;
	static File fileDirectory = new File("C:/Documents and Settings/caixl/桌面/com.drandxq.gallery007-6/res/drawable");

	static FileFilter filter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(".jpg");

		}
	};

	public static void renameFile(File parent, String oldFileName) {
		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), getNewImgName());
		oldFile.renameTo(newFile);
	}

	private static String getNewImgName() {
		if (countIndex == 15) {//每个类别最多数目
			typeIndex++;
			countIndex = 0;
		}
		return "img" + typeIndex + "_" + (countIndex++) + ".jpg";
	}

	public static void main(String[] args) {
		File[] files = fileDirectory.listFiles(filter);
		for (File file : files) {
			renameFile(fileDirectory, file.getName());
		}
		System.out.println("ok");

	}
}
