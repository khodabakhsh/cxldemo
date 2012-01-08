package com.cxl;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String Preferences_File = "MyApp";
	public static final String hasEnoughReadPointPreferenceKey = "hasEnoughReadPointPreferenceKey";
	public static final String hasEnoughAdPointPreferenceKey = "hasEnoughAdPointPreferenceKey";
	public static final String hasEnoughSharePointPreferenceKey = "hasEnoughSharePointPreferenceKey";
	public static final String Current_Page_Key = "Current_Page_Key";
	public static final String scrollY_Key = "scrollY";

	public static int getCurrentPage(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0)
				.getInt(Current_Page_Key, MainActivity.Start_Page_Value);
	}

	public static void setCurrentPage(Context paramContext, int page) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt(Current_Page_Key, page);
		localEditor.commit();
	}

	
	public static boolean getHasEnoughReadPoint(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0)
				.getBoolean(hasEnoughReadPointPreferenceKey, false);
	}

	public static void setHasEnoughReadPoint(Context paramContext, boolean booleanValue) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putBoolean(hasEnoughReadPointPreferenceKey, booleanValue);
		localEditor.commit();
	}

	public static boolean getHasEnoughAdPoint(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getBoolean(hasEnoughAdPointPreferenceKey, false);
	}

	public static void setHasEnoughAdPoint(Context paramContext, boolean booleanValue) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putBoolean(hasEnoughAdPointPreferenceKey, booleanValue);
		localEditor.commit();
	}
	public static boolean getHasEnoughSharePoint(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getBoolean(hasEnoughSharePointPreferenceKey, false);
	}

	public static void setHasEnoughSharePoint(Context paramContext, boolean booleanValue) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putBoolean(hasEnoughSharePointPreferenceKey, booleanValue);
		localEditor.commit();
	}
	public static int getScrollY(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt(scrollY_Key, 0);
	}

	public static void setScrollY(Context paramContext, int paramInt) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt(scrollY_Key, paramInt);
		localEditor.commit();
	}

}
