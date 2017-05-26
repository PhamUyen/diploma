package com.uyenpham.diploma.myenglish.customviews.asymeticView;

import android.view.View;

/**
 * Created by Ka on 3/19/2017.
 */

public interface AsymmetricView {
    boolean isDebugging();
    int getNumColumns();
    boolean isAllowReordering();
    void fireOnItemClick(int index, View v);
    boolean fireOnItemLongClick(int index, View v);
    int getColumnWidth();
    int getDividerHeight();
    int getRequestedHorizontalSpacing();
}
