package com.uyenpham.diploma.myenglish.customviews.customviews.asymeticView;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


/**
 * Created by Ka on 3/19/2017.
 */

public interface AGVBaseAdapter <T extends RecyclerView.ViewHolder> {
    int getActualItemCount();
    AsymmetricItem getItem(int position);
    void notifyDataSetChanged();
    int getItemViewType(int actualIndex);
    AsymmetricViewHolder<T> onCreateAsymmetricViewHolder(int position, ViewGroup parent, int
            viewType);
    void onBindAsymmetricViewHolder(AsymmetricViewHolder<T> holder, ViewGroup parent, int position);
}
