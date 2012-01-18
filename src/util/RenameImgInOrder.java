package util;

import java.io.File;
import java.io.FileFilter;
import java.util.UUID;

public class RenameImgInOrder {
	private static int countIndex = 1;
	private static File fileDirectory = new File("D:/cxl/my apk/e________________gao/download______neihan/9/assets/cartoon");
	private static String ImgPrefix = "mh";

	private static FileFilter jpgFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.getName().toLowerCase().endsWith(".jpg");

		}
	};

	public static void renameFile(File parent, String oldFileName) {
		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), ImgPrefix+oldFileName);
		oldFile.renameTo(newFile);
	}

	public static void renameFileByRandom(File parent, String oldFileName) {

		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), UUID.randomUUID() + ".jpg");
		oldFile.renameTo(newFile);
	}

	private static String getNewImgName() {
		
		return ImgPrefix +  (countIndex++) + ".jpg";
	}


	public static void main(String[] args) {
		File[] files = fileDirectory.listFiles(jpgFilter);
//		for (File file : files) {
//			renameFileByRandom(fileDirectory, file.getName());//
//		}
		files = fileDirectory.listFiles(jpgFilter);
		for (File file : files) {
			renameFile(fileDirectory, file.getName());
		}
		System.out.println("ok");

	}
}
