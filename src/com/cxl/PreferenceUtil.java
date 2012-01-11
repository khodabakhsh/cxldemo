package com.cxl;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String Preferences_File = "MyApp";
	public static final String hasEnoughRequreReadPointPreferenceKey = "hasEnoughRequreReadPointPreferenceKey";
	public static final String hasEnoughRequreAdPointPreferenceKey = "hasEnoughRequreAdPointPreferenceKey";



	public static int getScrollY(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt("scrollY", 0);
	}

	public static int getTxtIndex(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt("txtName", DetailActivity.Start_Page_Index);
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
