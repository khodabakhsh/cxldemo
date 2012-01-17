package com.cxl;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String Preferences_File = "MyApp";
	public static final String hasEnoughReadPointPreferenceKey = "hasEnoughReadPointPreferenceKey";
	public static final String hasEnoughAdPointPreferenceKey = "hasEnoughAdPointPreferenceKey";

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
	public static int getScrollY(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt("scrollY", 0);
	}

	public static void setScrollY(Context paramContext, int paramInt) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt("scrollY", paramInt);
		localEditor.commit();
	}


}
