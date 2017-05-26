package com.uyenpham.diploma.myenglish.customviews.customviews.asymeticView;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Ka on 3/19/2017.
 */

public class LinearLayoutPoolObjectFactory implements PoolObjectFactory<LinearLayout> {

    private final Context context;

    public LinearLayoutPoolObjectFactory(final Context context) {
        this.context = context;
    }

    @Override
    public LinearLayout createObject() {
        return new LinearLayout(context, null);
    }
}
