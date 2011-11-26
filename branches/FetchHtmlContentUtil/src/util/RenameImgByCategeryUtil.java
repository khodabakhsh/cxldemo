package util;

import java.io.File;
import java.io.FileFilter;
import java.util.UUID;

public class RenameImgByCategeryUtil {
	private static File fileDirectory = new File(
			"C:/Documents and Settings/Administrator/桌面/gzczcm");

	private static FileFilter jpgFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(".jpg")
					&& !pathname.getName().endsWith("_icon.jpg");

		}
	};
	private static FileFilter iconJpgFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("_icon.jpg");

		}
	};

	public static void renameFile(File parent, String oldFileName,
			int typeIndex, int countIndex) {
		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), getNewImgName(typeIndex,
				countIndex));
		oldFile.renameTo(newFile);
	}

	public static void renameFileByRandom(File parent, String oldFileName) {

		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), UUID.randomUUID() + ".jpg");
		oldFile.renameTo(newFile);
	}

	private static String getNewImgName(int typeIndex, int countIndex) {
		return "img" + typeIndex + "_" + countIndex + ".jpg";
	}

	private static String getIconImgName(int i) {
		return "img" + i + "_0.jpg";
	}

	public static void main(String[] args) {
		File[] categoryFiles = fileDirectory.listFiles();
		int typeIndex = 0;
		int countIndex = 0;
		for (File categoryFile : categoryFiles) {
			if (categoryFile.isDirectory()) {
				File[] files = categoryFile.listFiles(iconJpgFilter);
				for (File file : files) {
					file.delete();
				}
				files = categoryFile.listFiles(jpgFilter);
				for (File file : files) {
					renameFileByRandom(categoryFile, file.getName());// 先随机命名一下。
				}
				files = categoryFile.listFiles(jpgFilter);
				for (File file : files) {
					renameFile(categoryFile, file.getName(), typeIndex,
							countIndex++);
				}
				PicCompression ps = new PicCompression();
				for (int i = 0; i <= typeIndex; i++) {
					ps.proceJPG(categoryFile.getAbsolutePath() + File.separator
							+ getIconImgName(i), 135, 180, 1, "_icon");
				}
				typeIndex++;
				countIndex = 0;
			}
		}

		System.out.println("ok");

	}
}
