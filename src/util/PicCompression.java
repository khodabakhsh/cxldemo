package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PicCompression {
	private int wideth;
	private int height;
	private String t = null;

	public void setT(String t) {
		this.t = t;
	}

	public void setWideth(int wideth) {
		// wideth=320;
		this.wideth = wideth;
	}

	public int getWideth() {
		return this.wideth;
	}

	public void setHeight(int height) {
		// height=240;
		this.height = height;
	}

	public int getHeight(int w, int h) // former images size
	{
		// int hhh;
		if (w > wideth) {
			float ww;
			ww = (float) w / (float) wideth;
			float hh = h / ww;
			return (int) hh;
		} else {
			this.setWideth(w);
			return h;
		}

	}

	public void proce(String fpath) throws Exception {
		File _file = new File(fpath);
		Image src = javax.imageio.ImageIO.read(_file);
		int wideth = src.getWidth(null);
		int height = src.getHeight(null);
		int h = this.getHeight(wideth, height);
		BufferedImage tag = new BufferedImage(this.getWideth(), h, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(src, 0, 0, this.getWideth(), h, null);
		if (t != null) {
			g.setColor(new Color(242, 242, 242));
			g.fillRect(this.getWideth() - 120, h - 18, 120, 18);
			g.setColor(new Color(180, 180, 180));
			g.drawRect(this.getWideth() - 120, h - 18, 119, 17);
			g.setColor(new Color(255, 102, 0));
			g.drawString(t, this.getWideth() - 100, h - 5);
		}
		FileOutputStream out = new FileOutputStream(fpath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		out.close();
	}

	/**
	* 压缩图片方法
	*
	* @param oldFile
	*            将要压缩的图片
	* @param width
	*            压缩宽
	* @param height
	*            压缩长
	* @param quality
	*            压缩清晰度 <b>建议为1.0</b>
	* @param affix
	*            压缩图片后,添加的后缀
	* @return
	*/
	public String proceJPG(String oldFile, int width, int height, float quality, String affix) {
		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			File file = new File(oldFile);
			if (!file.exists()) //文件不存在时
				return null;
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(file);
			// 为等比缩放计算输出的图片宽度及高度
			double rate1 = ((double) srcFile.getWidth(null)) / (double) width + 0.1;
			double rate2 = ((double) srcFile.getHeight(null)) / (double) height + 0.1;
			double rate = rate1 > rate2 ? rate1 : rate2;
			int new_w = (int) (((double) srcFile.getWidth(null)) / rate);
			int new_h = (int) (((double) srcFile.getHeight(null)) / rate);
			/** 宽,高设定 */
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
			String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
			/** 压缩后的文件名 */
			// newImage =smallIcon + filePrex
			// +oldFile.substring(filePrex.length());
			newImage = filePrex + affix + oldFile.substring(filePrex.length());
			// newImage = smallIcon;
			/** 压缩之后临时存放位置 */
			FileOutputStream out = new FileOutputStream(newImage);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/** 压缩质量 */
			jep.setQuality(quality, true);
			encoder.encode(tag, jep);

			out.close();
			srcFile.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newImage;
	}
	/**
	* 压缩图片方法
	*
	* @param oldFile
	*            将要压缩的图片
	* @param rate
	*            压缩比例，小于1
	* @param quality
	*            压缩清晰度 <b>建议为1.0</b>
	* @param affix
	*            压缩图片后,添加的后缀
	* @return
	*/
	public String proceJPG(String oldFile, double rate, float quality, String affix) {
		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			File file = new File(oldFile);
			if (!file.exists()) //文件不存在时
				return null;
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(file);
			// 为等比缩放计算输出的图片宽度及高度
			int new_w = (int) (((double) srcFile.getWidth(null)) * rate);
			int new_h = (int) (((double) srcFile.getHeight(null)) * rate);
			/** 宽,高设定 */
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
			String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
			/** 压缩后的文件名 */
			// newImage =smallIcon + filePrex
			// +oldFile.substring(filePrex.length());
			newImage = filePrex + affix + oldFile.substring(filePrex.length());
			// newImage = smallIcon;
			/** 压缩之后临时存放位置 */
			FileOutputStream out = new FileOutputStream(newImage);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/** 压缩质量 */
			jep.setQuality(quality, true);
			encoder.encode(tag, jep);

			out.close();
			srcFile.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newImage;
	}

	//
	public static void main(String str[]) {
		PicCompression ps = new PicCompression();
		try {
			System.out.println(ps.proceJPG("C:/Documents and Settings/caixl/桌面/screenshot/img0_0.jpg", 135, 180, 1,
					"_icon"));
			System.out.print("成功哦");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}