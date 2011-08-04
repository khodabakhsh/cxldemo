package tesseractOCR;

import java.io.File;

public class Test {

	/**
	 * http://blog.csdn.net/zhoushuyan/article/details/5948289#1567946
	 * 
	 */

	public static void main(String[] args) {

		String filepath = "d:/2990.jpg";

		try {

			String maybe = new OCR().recognizeText(new File(filepath), filepath
					.substring(filepath.lastIndexOf(".") + 1));

			System.out.println(maybe);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}