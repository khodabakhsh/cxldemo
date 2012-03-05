package com.cxl;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String Preferences_File = "MyApp";
	public static final String hasEnoughRequrePointPreferenceKey = "hasEnoughRequrePointPreferenceKey";
	public static final String fontSizeKey = "fontSizeKey";
	
	public static final String modeKey = "modeKey";

	/**
	 * mode:1黑夜,0白天
	 *      
	 */
	public static int getMode(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt(modeKey, 0);
	}

	public static void setMode(Context paramContext, int paramInt) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt(modeKey, paramInt);
		localEditor.commit();
	}
	
	
	public static int getFontSize(Context paramContext) {
		return paramContext.getSharedPreferences(Preferences_File, 0).getInt(fontSizeKey, 16);
	}

	public static void setFontSize(Context paramContext, int paramInt) {
		SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(Preferences_File, 0).edit();
		localEditor.putInt(fontSizeKey, paramInt);
		localEditor.commit();
	}
	
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
