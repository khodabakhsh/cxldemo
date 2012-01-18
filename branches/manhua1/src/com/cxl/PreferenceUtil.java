package com.cxl;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String Preferences_File = "MyApp";
	public static final String hasEnoughRequreAdPointPreferenceKey = "hasEnoughRequreAdPointPreferenceKey";
	public static final String Current_Page_Key = "Current_Page_Key";

	public static int getCurrentPage(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0)
				.getInt(Current_Page_Key,1);
	}

	public static void setCurrentPage(Context paramContext, int page) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt(Current_Page_Key, page);
		localEditor.commit();
	}

	public static int getScrollY(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt("scrollY", 0);
	}

	public static void setScrollY(Context paramContext, int paramInt) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt("scrollY", paramInt);
		localEditor.commit();
	}
	
	
	public static boolean getHasEnoughRequreAdPoint(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0)
				.getBoolean(hasEnoughRequreAdPointPreferenceKey, false);
	}

	public static void setHasEnoughRequreAdPoint(Context paramContext, boolean booleanValue) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putBoolean(hasEnoughRequreAdPointPreferenceKey, booleanValue);
		localEditor.commit();
	}


}
