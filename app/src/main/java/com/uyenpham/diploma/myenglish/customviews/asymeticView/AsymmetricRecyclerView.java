package com.uyenpham.diploma.myenglish.customviews.asymeticView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.uyenpham.diploma.myenglish.interfaces.IRecyclerViewListener;


/**
 * Created by Ka on 3/19/2017.
 */

public class AsymmetricRecyclerView extends RecyclerView implements AsymmetricView {
    private final AsymmetricViewImpl viewImpl;
    private AsymmetricRecyclerViewAdapter<?> adapter;
    private IRecyclerViewListener listener;

    public AsymmetricRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewImpl = new AsymmetricViewImpl(context);
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        final ViewTreeObserver vto = getViewTreeObserver();
        if (vto != null) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //noinspection deprecation
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    viewImpl.determineColumns(getAvailableSpace());
                    if (adapter != null) {
                        adapter.recalculateItemsPerRow();
                    }
                }
            });
        }
    }

    @Override
    public void setAdapter(@NonNull RecyclerView.Adapter adapter) {
        if (!(adapter instanceof AsymmetricRecyclerViewAdapter)) {
            throw new UnsupportedOperationException(
                    "Adapter must be an instance of AsymmetricRecyclerViewAdapter");
        }

        this.adapter = (AsymmetricRecyclerViewAdapter<?>) adapter;
        super.setAdapter(adapter);

        this.adapter.recalculateItemsPerRow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewImpl.determineColumns(getAvailableSpace());
    }

    @Override
    public boolean isDebugging() {
        return viewImpl.isDebugging();
    }

    @Override
    public int getNumColumns() {
        return viewImpl.getNumColumns();
    }

    @Override
    public boolean isAllowReordering() {
        return viewImpl.isAllowReordering();
    }

    @Override
    public void fireOnItemClick(int index, View v) {
        if(listener != null){
            listener.onClickItem(v,index);
        }
    }

    @Override
    public boolean fireOnItemLongClick(int index, View v) {
        return false;
    }

    @Override
    public int getColumnWidth() {
        return viewImpl.getColumnWidth(getAvailableSpace());
    }

    private int getAvailableSpace() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    public int getDividerHeight() {
        return 0;
    }

    @Override
    public int getRequestedHorizontalSpacing() {
        return viewImpl.getRequestedHorizontalSpacing();
    }

    public void setRequestedColumnCount(int requestedColumnCount) {
        viewImpl.setRequestedColumnCount(requestedColumnCount);
    }

    public void setRequestedHorizontalSpacing(int spacing) {
        viewImpl.setRequestedHorizontalSpacing(spacing);
    }

    public void setDebugging(boolean debugging) {
        viewImpl.setDebugging(debugging);
    }

    public void setListener(IRecyclerViewListener listener) {
        this.listener = listener;
    }
}
