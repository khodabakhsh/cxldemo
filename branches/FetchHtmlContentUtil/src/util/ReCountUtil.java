package util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReCountUtil {
	private static File fileDirectory = new File("C:/Documents and Settings/caixl/桌面/gzczcm");

	private static FileFilter notTxtFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return !pathname.getName().endsWith(".txt");

		}
	};

	public static void renameFile(File parent, String oldFileName, String newFileName) {
		File oldFile = new File(parent, oldFileName);
		File newFile = new File(oldFile.getParent(), newFileName);
		oldFile.renameTo(newFile);
	}

	public static String prefix = "chapter";
	public static String suffix = ".html";

	public static List<File> getList(List<File> list) {
		List<File> listNew = new ArrayList<File>();
		listNew = list;
		if (!listNew.isEmpty()) {
			Collections.sort(listNew, new Comparator<File>() {
				public int compare(File object1, File object2) {

					return (Integer.valueOf(object1.getName().substring(prefix.length(),
							object1.getName().lastIndexOf(suffix)))).compareTo(Integer.valueOf(object2.getName()
							.substring(prefix.length(), object2.getName().lastIndexOf(suffix))));
				}
			});
		}
		return listNew;
	}

	public static void main(String[] args) {
		File[] categoryFiles = fileDirectory.listFiles();
		List<File> newList = getList(Arrays.asList(categoryFiles));
		File singleFile = null;
		for (int i = newList.size() - 1; i >= 0; i--) {
			singleFile = newList.get(i);
			renameFile(fileDirectory, singleFile.getName(), getNewFileName(getNewCount(singleFile)));
		}

		System.out.println("ok");

	}

	public static String getNewFileName(int count) {
		return prefix + count + suffix;
	}

	public static int getNewCount(File file) {
		return Integer.valueOf(file.getName().substring(prefix.length(), file.getName().lastIndexOf(suffix))) + 1;
	}
}
