package util;

import java.io.File;
import java.io.FileFilter;
import java.util.UUID;

public class RenameImgUtil {
	private static int typeIndex = 0;
	private static int countIndex = 0;
	private static File fileDirectory = new File("C:/Documents and Settings/caixl/桌面/drawable");
	private final static int Max_Count = 15;

	private static FileFilter jpgFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(".jpg") && !pathname.getName().endsWith("_icon.jpg");

		}
	};

	public static void renameFile(File parent, String oldFileName) {
		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), getNewImgName());
		oldFile.renameTo(newFile);
	}

	public static void renameFileByRandom(File parent, String oldFileName) {

		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), UUID.randomUUID() + ".jpg");
		oldFile.renameTo(newFile);
	}

	private static String getNewImgName() {
		if (countIndex == Max_Count) {//每个类别最多数目
			typeIndex++;
			countIndex = 0;
		}
		return "img" + typeIndex + "_" + (countIndex++) + ".jpg";
	}

	private static String getIconImgName(int i) {
		return "img" + i + "_0.jpg";
	}

	public static void main(String[] args) {
		File[] files = fileDirectory.listFiles(jpgFilter);
		for (File file : files) {
			renameFileByRandom(fileDirectory, file.getName());//先随机命名一下。
		}
		files = fileDirectory.listFiles(jpgFilter);
		for (File file : files) {
			renameFile(fileDirectory, file.getName());
		}
		PicCompression ps = new PicCompression();
		for (int i = 0; i <= typeIndex; i++) {
			ps.proceJPG(fileDirectory.getAbsolutePath() + File.separator + getIconImgName(i), 0.5, 1, "_icon");
		}
		System.out.println("ok");

	}
}
