package com.uyenpham.diploma.myenglish.customviews.customviews.asymeticView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ka on 3/19/2017.
 */

public class AsymmetricViewHolder <VH extends RecyclerView.ViewHolder>
        extends RecyclerView.ViewHolder {
    final VH wrappedViewHolder;

    public AsymmetricViewHolder(VH wrappedViewHolder) {
        super(wrappedViewHolder.itemView);
        this.wrappedViewHolder = wrappedViewHolder;
    }

    public AsymmetricViewHolder(View view) {
        super(view);
        wrappedViewHolder = null;
    }
}
