package tesseractOCR;

import java.io.File;

public class Test {

	/**
	 * http://blog.csdn.net/zhoushuyan/article/details/5948289#1567946
	 * @param args
	 * 
	 */

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		OCR ocr = new OCR();

		try {

			String maybe = new OCR().recognizeText(
					new File("d://中文.jpg"), "jpg");

			System.out.println(maybe);

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

}