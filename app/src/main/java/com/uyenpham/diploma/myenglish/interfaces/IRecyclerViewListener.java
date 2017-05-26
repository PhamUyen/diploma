package com.uyenpham.diploma.myenglish.interfaces;

import android.view.View;

/**
 * Created by Ka on 3/15/2017.
 */

public interface IRecyclerViewListener {
    void onClickItem(View view, int postion);

    void onLongClickItem(View v, int position);
}
