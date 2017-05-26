package com.uyenpham.diploma.myenglish.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.uyenpham.diploma.myenglish.activity.MainActivity.ID_MAIN_CONTENT;


/**
 * Define fragment manager
 *
 * @author MinhNH
 */
public class FragmentHelper {
    /**
     * Repale fragment with contentTab and add fragment to backstack
     *
     * @param fragment        This is fragment need replace in layout contentTab
     * @param fragmentManager This is fragment manager
     */
    public static void replaceFragmentAddToBackStack(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(ID_MAIN_CONTENT, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Repale fragment with contentTab and add fragment to backstack
     *
     * @param fragment        This is fragment need replace in layout contentTab
     * @param fragmentManager This is fragment manager
     */
    public static void replaceFragmentAddToBackStackByTag(Fragment fragment, FragmentManager fragmentManager, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(ID_MAIN_CONTENT, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Repale fragment with contentTab
     *
     * @param fragment        This is fragment need replace in layout contentTab
     * @param fragmentManager This is fragment manager
     */
    public static void replaceFragment(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(ID_MAIN_CONTENT, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Repale fragment with contentMenu
     *
     * @param fragment        This is fragment need replace in layout contentMenu
     * @param fragmentManager This is fragment manager
     */
    public static void replaceMenuFragment(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(ID_MAIN_CONTENT, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Repale fragment with contentMenu and add fragment to backstack
     *
     * @param fragment        This is fragment need replace in layout contentMenu
     * @param fragmentManager This is fragment manager
     */
    public static void replaceMenuAddToBackStack(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(ID_MAIN_CONTENT, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
