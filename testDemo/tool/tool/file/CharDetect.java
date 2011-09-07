package tool.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.compass.core.converter.extended.FileConverter;

public class CharDetect {
	private static FileFilter filter = new FileFilter() {
		public boolean accept(File pathname) {
//			// 只处理：目录 或是 .java文件
//			if (pathname.isDirectory()
//					|| (pathname.isFile() && pathname.getName()
//							.endsWith(".xml")))
				return true;
//			else
//				return false;
		}
	};

	public static void getFileCharacter(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile())
			get_charset(file);
		else {
			File[] tempList = file.listFiles(filter);
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				temp = tempList[i];
				if (temp.isFile()) {
					get_charset(temp);
				}
				if (temp.isDirectory()) {
					getFileCharacter(temp);
				}
			}
		}

	}

	public static String get_charset(File file) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();
			if (!checked) {
				// int len = 0;
				int loc = 0;

				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
				// System.out.println( loc + " " + Integer.toHexString( read )
				// );
			}

			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!charset.equals("UTF-8")) {
			System.out.println(file.getAbsolutePath() + "  【" + charset + "】");
			try {
				File targetFile = new File(outFile
						+ file.getAbsolutePath().substring(srcFile.length()));
				if (!targetFile.getParentFile().exists())
					targetFile.getParentFile().mkdirs();

				FileEncodeConverter.convert(file.getAbsolutePath(), outFile
						+ file.getAbsolutePath().substring(srcFile.length()),
						gb2312, utf8);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return charset;
	}

	private static String srcFile = "D:\\cxl_work\\jeebbs";
	private static String outFile = "d:\\new\\";
	// 输出文件编码
	private static String utf8 = "UTF-8";
	private static String ansi = "ANSI";
	private static String gb2312 = "gb2312";

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		getFileCharacter(new File(srcFile));
	}

}
