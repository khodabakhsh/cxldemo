package com.cxl;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String Preferences_File = "MyApp";
	public static final String hasEnoughRequrePointPreferenceKey = "hasEnoughRequrePointPreferenceKey";

	public static boolean getHasEnoughRequrePoint(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0)
				.getBoolean(hasEnoughRequrePointPreferenceKey, false);
	}

	public static void setHasEnoughRequrePoint(Context paramContext, boolean booleanValue) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putBoolean(hasEnoughRequrePointPreferenceKey, booleanValue);
		localEditor.commit();
	}
}
