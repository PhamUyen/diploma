package com.uyenpham.diploma.myenglish.customviews.asymeticView;


/**
 * Created by Ka on 3/19/2017.
 */

final class RowItem {
    private final AsymmetricItem item;
    private final int index;

    RowItem(int index, AsymmetricItem item) {
        this.item = item;
        this.index = index;
    }

    AsymmetricItem getItem() {
        return item;
    }

    int getIndex() {
        return index;
    }
}
