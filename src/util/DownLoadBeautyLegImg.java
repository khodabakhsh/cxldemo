package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators.ParentIterator;

public class DownLoadBeautyLegImg {

	public static int downloadImg(File file, int typeIndex) {
		Reader reader = null;
		BufferedReader bufferedReader = null;
		int count = 0;
		try {
			reader = new FileReader(file);
			bufferedReader = new BufferedReader(reader);
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				if (contentString.indexOf("http://") != -1) {
					downLoad(getImgUrl(contentString), saveFileDir, genImgName(typeIndex, count++));
				}

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
		System.out.println(file.getAbsolutePath() + " 个数： " + count);
		return count;
	}

	private static String getImgUrl(String line) {
		return line.substring(line.indexOf("http:"), line.indexOf(".jpg") + ".jpg".length());
	}

	private static String genImgName(int typeIndex, int countIndex) {
		return "img" + typeIndex + "_" + countIndex + ".jpg";
	}

	private static final int TIME_OUT = 5000;
	private static String directoryPathString = "C:/Documents and Settings/caixl/桌面/com.hotty.legsshow-2/assets";
	private static String saveFileDir = "C:/Documents and Settings/caixl/桌面/save";

	public static void main(String[] args) {
		//		List<String> list = new ArrayList<String>();
		//		list.add("one.txt");
		//		list.add("two.txt");
		//		list.add("three.txt");
		//		list.add("four.txt");
		//		list.add("five.txt");
		//
		//		list.add("six.txt");
		//		list.add("seven.txt");
		//		list.add("eight.txt");
		//		list.add("nine.txt");
		//		list.add("ten.txt");
		//		for (int i = 0; i < list.size(); i++) {
		//			downloadImg(new File(directoryPathString, list.get(i)), i);
		//		}
		File fileDirectory = new File("C:/Documents and Settings/caixl/桌面/save_compress");
		for (File file : fileDirectory.listFiles()) {
			renameFile(fileDirectory, file.getName());
		}
		System.out.println("ok");
	}

	/**
	 * 去除压缩后的Table_前缀
	 */
	public static void renameFile(File parent, String oldFileName) {
		if (oldFileName.startsWith("Table_")) {
			File oldFile = new File(parent, oldFileName);
			File newFile = new File(oldFile.getParent(), oldFileName.substring("Table_".length()));
			oldFile.renameTo(newFile);
		}
	}

	public static void downLoad(String url, String saveFileDir, String saveFileName) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(url);
		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		BufferedReader bufferedReader = null;
		BufferedInputStream bufferedInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(saveFileDir, saveFileName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fileOutputStream = new FileOutputStream(file);
			HttpResponse response = client.execute(get);
			bufferedInputStream = new BufferedInputStream(response.getEntity().getContent());
			int size = 1024 * 1024;
			byte[] read = new byte[size];
			int readNumber;
			while ((readNumber = bufferedInputStream.read(read)) != -1) {
				fileOutputStream.write(read, 0, readNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
			downLoad(url, saveFileDir, saveFileName);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (bufferedInputStream != null)
					bufferedInputStream.close();
				if (fileOutputStream != null)
					fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
	}
}
