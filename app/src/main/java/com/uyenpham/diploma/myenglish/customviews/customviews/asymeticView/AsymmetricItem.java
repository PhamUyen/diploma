package com.uyenpham.diploma.myenglish.customviews.customviews.asymeticView;

import android.os.Parcelable;

/**
 * Created by Ka on 3/19/2017.
 */

public interface AsymmetricItem extends Parcelable {
    int getColumnSpan();
    int getRowSpan();
}
