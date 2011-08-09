import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.tools.ant.filters.TokenFilter.FileTokenizer;

import tesseractOCR.ImageFilter;

/**
 * 灰度化
 * 灰度反转
 * 二值化
 * @author caixl
 *
 */

public class JustMain {

	public static void main(String[] args) throws Exception {
		String name = "d:/复件 2990.jpg";
		File file = new File(name);

		BufferedImage bi=ImageIO.read(file);
		ImageFilter filter = new ImageFilter(bi);
		BufferedImage nbi =null;
//		nbi = filter.lineGrey();
//		nbi = filter.changeGrey();
//		nbi = filter.sharp();
//		nbi = filter.grayFilter(); 
		 filter.grey();
		 filter.greyRevert();
//		 filter.greyRevert();
//		 filter.erZhi();
//		nbi = new ImageFilter(nbi).lineGrey();
//		nbi = new ImageFilter(bi).changeGrey();
//		nbi = new ImageFilter(bi).median();
//		nbi = new ImageFilter(nbi).sharp();
		nbi = new ImageFilter(bi).changeGrey();
		
		
		
		ImageIO.write(nbi, "jpg",new File( "d:/new"+file.getName()));  

		

}
	// 从URL中去掉"www"
	private static String removeWwwFromUrl(String url) {
		int index = url.indexOf("://www.");
		if (index != -1) {
			return url.substring(0, index + 3) + url.substring(index + 7);
		}

		return (url);
	}
}
