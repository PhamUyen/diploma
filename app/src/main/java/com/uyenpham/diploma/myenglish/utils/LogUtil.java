package com.uyenpham.diploma.myenglish.utils;

import android.util.Log;

public class LogUtil {

    private static final String TAG = "Memory Tracker";
    private static final Boolean LOG = true;

    /**
     * Custom log d no tag
     *
     * @param message This is messge show log
     */
    public static void d(String message) {
        if (LOG) Log.d(TAG, message);
    }

    /**
     * Custom log d with tag
     *
     * @param tag     This is tag show log
     * @param message This is messge show log
     */
    public static void d(String tag, String message) {
        if (LOG) Log.d(tag, message);
    }

    /**
     * Custom log e no tag
     *
     * @param message This is messge show log
     */
    public static void e(String message) {
        if (LOG) Log.e(TAG, message);
    }


    /**
     * Custom log e with tag
     *
     * @param tag     This is tag show log
     * @param message This is messge show log
     */
    public static void e(String tag, String message) {
        if (LOG) Log.e(tag, message);
    }
}
