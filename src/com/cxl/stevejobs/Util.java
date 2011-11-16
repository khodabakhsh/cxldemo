package com.cxl.stevejobs;

import android.content.Context;
import android.content.SharedPreferences;

public class Util {

	public static final String Preferences_File = "TxtBrowser";

	public static int getScrollY(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt("scrollY", 0);
	}

	public static int getTxtIndex(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt("txtName", 1);
	}

	public static void setScrollY(Context paramContext, int paramInt) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt("scrollY", paramInt);
		localEditor.commit();
	}

	public static void setTxtIndex(Context paramContext, int paramInt) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt("txtName", paramInt);
		localEditor.commit();
	}
}
