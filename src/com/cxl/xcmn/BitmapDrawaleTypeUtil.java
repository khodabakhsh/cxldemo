package com.cxl.xcmn;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import com.cxl.qcmn.R;

public class BitmapDrawaleTypeUtil {
	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory()+"/";

	public static void saveFile(Bitmap bm, String parentPath,String fileName) {
		File dirFile = new File(ALBUM_PATH+parentPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File myCaptureFile = new File(ALBUM_PATH + parentPath + fileName);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Bitmap drawableToBitmap(Drawable paramDrawable) {
		int i = 0;
		int j = paramDrawable.getIntrinsicWidth();
		int k = paramDrawable.getIntrinsicHeight();
		int m = paramDrawable.getOpacity();
		if (m != -1)
			;
		for (Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;; localConfig = Bitmap.Config.RGB_565) {
			Bitmap localBitmap = Bitmap.createBitmap(j, k, localConfig);
			Canvas localCanvas = new Canvas(localBitmap);
			paramDrawable.setBounds(i, i, j, k);
			paramDrawable.draw(localCanvas);
			return localBitmap;
		}
	}

	public static byte[] getBitmapByte(Bitmap paramBitmap) {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		Bitmap.CompressFormat localCompressFormat = Bitmap.CompressFormat.JPEG;
		paramBitmap.compress(localCompressFormat, 100,
				localByteArrayOutputStream);
		try {
			localByteArrayOutputStream.flush();
			localByteArrayOutputStream.close();
			return localByteArrayOutputStream.toByteArray();
		} catch (IOException localIOException) {
			while (true)
				localIOException.printStackTrace();
		}
	}

	public static Bitmap getBitmapFromByte(byte[] paramArrayOfByte) {
		Bitmap localBitmap = null;
		if (paramArrayOfByte != null) {
			int i = paramArrayOfByte.length;
			localBitmap = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, i);
		}
		return localBitmap;
	}
}