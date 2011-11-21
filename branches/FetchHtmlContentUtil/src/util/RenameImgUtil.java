package util;

import java.io.File;
import java.io.FileFilter;

public class RenameImgUtil {
	private static int typeIndex = 0;
	private static int countIndex = 0;
	static File fileDirectory = new File("C:/Documents and Settings/caixl/桌面/com.drandxq.beautygirl007-1/res/drawable");

	static FileFilter filter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.getName().startsWith("mmpic") && pathname.getName().endsWith(".jpg");

		}
	};

	public static void renameFile(File parent, String oldFileName) {
		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), getNewImgName());
		oldFile.renameTo(newFile);
	}

	private static String getNewImgName() {
		if (countIndex == 20) {//每个类别最多20个
			typeIndex++;
			countIndex = 0;
		}
		return "img" + typeIndex + "_" + (countIndex++) + ".jpg";
	}

	public static void main(String[] args) {

		for (File file : fileDirectory.listFiles(filter)) {
			renameFile(fileDirectory, file.getName());
		}

	}
}
