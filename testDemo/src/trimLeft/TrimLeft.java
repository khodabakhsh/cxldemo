package trimLeft;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * 去除源代码前面的两位行数字
 * @author Administrator
 *
 */
public class TrimLeft {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "c:/in.txt";
		trimLeft(filePath);

	}

	public static void trimLeft(String filePath) {
		FileReader fReader = null;
		BufferedReader bfReader = null;
		try {
			String outString = null;
			fReader = new FileReader(filePath);
			bfReader = new BufferedReader(fReader);
			while ((outString = bfReader.readLine()) != null) {
				if (!"".equals(outString)) {
					System.out.println(outString.substring(2));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bfReader.close();
				fReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
