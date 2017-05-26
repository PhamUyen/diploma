package com.uyenpham.diploma.myenglish.utils;

import android.content.Context;


public class PreferenceUtils {
    private static final String PREF_NAME = "diploma_project_pref";

    public static void saveBoolean(Context context, String key, boolean value) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    public static void saveInt(Context context, String key, int value) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putInt(key, value).commit();
    }

    public static void saveString(Context context, String key, String value) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    public static void saveFloat(Context context, String key, float value) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putFloat(key, value).commit();
    }


    public static boolean getBoolean(Context context, String key) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    public static int getInt(Context context, String key) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(key, 0);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(key, defaultValue);
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString(key, "");
    }

    public static float getFloat(Context context, String key) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getFloat(key, 0f);
    }

    public static void remove(Context context, String key) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().remove(key).commit();
    }

}
