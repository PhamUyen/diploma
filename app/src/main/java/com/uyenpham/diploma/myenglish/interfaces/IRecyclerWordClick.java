package com.uyenpham.diploma.myenglish.interfaces;

import android.view.View;

/**
 * Created by Ka on 4/7/2017.
 */

public interface IRecyclerWordClick {
    void onFavorite(int position);
    void onSpeaker(int position);
    void onItemClick(View v, int position);
}
