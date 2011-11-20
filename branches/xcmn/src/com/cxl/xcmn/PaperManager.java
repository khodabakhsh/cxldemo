package com.cxl.xcmn;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.widget.Toast;

public class PaperManager {

	public static void setWallpaper(Context context, int imageId,int marginWidth ,int marginHeight ,int imageWidth,int imageHeight) {

		
		// Resources resources = context.getResources();
		// InputStream is = resources.openRawResource(R.drawable.oprea00);

		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				imageId);

		bitmap = resizeImage(bitmap, imageWidth, imageHeight);
		// String mstrTitle = "欢迎来到android世界";
		Bitmap mbmpTest = Bitmap.createBitmap(marginWidth, marginHeight, Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(mbmpTest);
		canvasTemp.drawColor(Color.WHITE);
		Paint p = new Paint();
		// String familyName = "宋体";
		// Typeface font = Typeface.create(familyName,Typeface.BOLD);
		// p.setColor(Color.RED);
		// p.setTypeface(font);
		// p.setTextSize(15);
		canvasTemp.drawBitmap(bitmap, (marginWidth - bitmap.getWidth()) / 2, (marginHeight - bitmap.getHeight()) / 2, p);
		// canvasTemp.drawText(mstrTitle,0,35,p);

		try {
			context.setWallpaper(mbmpTest);
			Toast.makeText(context, "设置壁纸成功", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {

		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;

		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scaleWidth, scaleHeight);

		// matrix.postSkew(0.5f, 0f, 0, 0);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);

		// recreate the new Bitmap
		// BitmapOrgg.
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);

		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return resizedBitmap;

	}

}