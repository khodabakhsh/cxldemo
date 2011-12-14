package com.cxl;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitMapUtil {

	public static final int In_Sample_Size = 4;
	public static final int Max_Size = 1000;

	public static Bitmap adujstSizeByRate(InputStream orgStream) {
		BitmapFactory.Options opts = new BitmapFactory.Options();

		opts.inSampleSize = In_Sample_Size;
		Bitmap bitmap = BitmapFactory.decodeStream(orgStream, null, opts);
		return bitmap;
	}

	public static Bitmap adujstSizeByMax(InputStream orgStream) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(orgStream, null, opts);
		opts.inSampleSize = computeSampleSize(opts, -1, Max_Size * Max_Size);
		opts.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeStream(orgStream, null, opts);
		return bitmap;
	}

	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
				Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

}
