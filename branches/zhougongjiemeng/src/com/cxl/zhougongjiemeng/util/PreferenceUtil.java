package com.cxl.zhougongjiemeng.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String Preferences_File = "MyApp";
	public static final String hasEnoughRequreReadPointPreferenceKey = "hasEnoughRequreReadPointPreferenceKey";
	public static final String hasEnoughRequreAdPointPreferenceKey = "hasEnoughRequreAdPointPreferenceKey";



	public static boolean getHasEnoughRequreReadPoint(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0)
				.getBoolean(hasEnoughRequreReadPointPreferenceKey, false);
	}

	public static void setHasEnoughRequreReadPoint(Context paramContext, boolean booleanValue) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putBoolean(hasEnoughRequreReadPointPreferenceKey, booleanValue);
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
