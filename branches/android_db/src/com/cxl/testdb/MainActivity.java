package com.cxl.testdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	private File file = null;
	private File dir = null;
	
	/**param
	 * */
	private static final String dbName = "jokedata";
	private static final int dbFileRawId = R.raw.jokedata;

	/***/
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 第一次运行应用程序时，加载数据库到data/data/<pkg_name>/database/<db_name>
		dir = new File("data/data/" + getPackageName() + "/databases");
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		file = new File(dir,dbName);

		if (!file.exists()) {
			FileUtils.loadDbFile(dbFileRawId, file, getResources(), getPackageName());
		}

		// 读取数据库

		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file, null);
		//SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
		Cursor cursor = db.query("joke", new String[] { "id", "data", "Fav"}, null, null, null, null,
				null);
		startManagingCursor((Cursor)cursor);

		while (cursor.moveToNext()) {
			Log.e("+++++++++++++++++++++++++++", cursor.getInt(0)+cursor.getString(1)+"  : "+cursor.getString(2));
//			System.out.println(cursor.getInt(0));
		}
		cursor.close();

	}

	static class FileUtils {

		public static void loadDbFile(int rawId, File file, Resources res, String pkgname) {
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
}