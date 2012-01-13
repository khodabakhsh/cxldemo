package com.cxl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

public final class DBUtil {
	static final String dbName = "data.db";
	static SQLiteDatabase db = null;

	public static SQLiteDatabase getDatabase(Context paramContext, int rawId) {

		// 第一次运行应用程序时，加载数据库到data/data/<pkg_name>/database/<db_name>
		File dir = new File("data/data/" + paramContext.getPackageName()
				+ "/databases");
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		File file = new File(dir, dbName);

		if (!file.exists()) {
			loadDbFile(rawId, file, paramContext.getResources(),
					paramContext.getPackageName());
		}
		if (db == null) {
			db = SQLiteDatabase.openOrCreateDatabase(file, null);
		}
		return db;
	}

	public static void loadDbFile(int rawId, File file, Resources res,
			String pkgname) {
		InputStream dbInputStream = res.openRawResource(rawId);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = dbInputStream.read(bytes)) > 0) {
				fos.write(bytes, 0, length);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				dbInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}