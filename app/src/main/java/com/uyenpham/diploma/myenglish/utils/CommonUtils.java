package com.uyenpham.diploma.myenglish.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Random;

public class CommonUtils {
    private static final int MINUTE = 60;
    private static final int HOUR = 60 * MINUTE;

    public static final String FOLDER_STORAGE_IMG = "images";
    public static final String URL_STORAGE_REFERENCE ="gs://fir-casestudy.appspot.com";

    public static void initToast(Context c, String message){
        Toast.makeText(c,message,Toast.LENGTH_SHORT).show();
    }

    public  static boolean verificaConexao(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        conectado = conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
        return conectado;
    }

    /**
     * Check text is empty or null
     *
     * @param text This is text check empty
     * @return Flag check empty
     */
    public static Boolean checkEmpty(String text) {
        Boolean isEmpty = false;

        if (text == null || text.isEmpty()) {
            return true;
        }

        return isEmpty;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * random in array
     *
     * @param array array which want to random
     * @return return a index of member in array
     */
    public static int getRandom(int[] array) {
        return new Random().nextInt(array.length);
    }

    /**
     * Convert value dp to px
     *
     * @param dp
     * @param context
     * @return
     */
    public static int convertDpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    public static String getDeviceID(Context context) {
        return Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID);
    }
    //check device is tablet
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //get screen type (small, normal, large, xlarge)
    public static String getScreenType(Context context) {
        if ((context.getResources().getConfiguration().screenLayout & Configuration
                .SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "Large";
        } else if ((context.getResources().getConfiguration().screenLayout & Configuration
                .SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            return "Normal";
        } else if ((context.getResources().getConfiguration().screenLayout & Configuration
                .SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            return "Small";
        } else if ((context.getResources().getConfiguration().screenLayout & Configuration
                .SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            return "X-Large";
        } else return "Unknown";
    }

    public static int getScreenWidth(Activity context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static int getScreenHeight(Activity context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static String convertSecond(double seconds) {
        StringBuilder text = new StringBuilder("");
        if (seconds > HOUR) {
            int hours = (int) seconds / HOUR;
            seconds %= HOUR;
            if (hours < 10) {
                text.append(0).append(hours).append(":");
            } else {
                text.append(hours).append(":");
            }
        }
        int minute = (int) seconds / MINUTE;
        if (minute < 10) {
            text.append(0).append(minute).append(":");
        } else {
            text.append(minute).append(":");
        }
        seconds = Math.round(seconds %= MINUTE);
        if (seconds < 10) {
            text.append(0).append((int)seconds);
        } else {
            text.append((int)seconds);
        }

        return text.toString();
    }


}
