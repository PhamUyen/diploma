package com.uyenpham.diploma.myenglish.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.interfaces.IPagerClick;


/**
 * Created by Ka on 20/12/2016.
 */

public class TypeQuestionAdapter extends PagerAdapter {

    private Context context;
    private int[] arr ;
    private IPagerClick listener;

    public TypeQuestionAdapter(Context context, int[] arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.item_layout_pager, container, false);
        ImageView imgDisplay = (ImageView) view.findViewById(R.id.img_display);

        imgDisplay.setImageResource(arr[position]);
        imgDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!= null){
                    listener.onPagerClick(position);
                }
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public void setListener(IPagerClick listener) {
        this.listener = listener;
    }
}
