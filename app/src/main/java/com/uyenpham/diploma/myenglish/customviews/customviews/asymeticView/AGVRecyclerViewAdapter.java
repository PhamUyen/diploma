package com.uyenpham.diploma.myenglish.customviews.customviews.asymeticView;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Ka on 3/19/2017.
 */

public abstract class AGVRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    public abstract AsymmetricItem getItem(int position);
}
