package com.uyenpham.diploma.myenglish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.customviews.asymeticView.AGVRecyclerViewAdapter;
import com.uyenpham.diploma.myenglish.customviews.asymeticView.AsymmetricItem;
import com.uyenpham.diploma.myenglish.interfaces.IRecyclerViewListener;
import com.uyenpham.diploma.myenglish.model.Group;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ka on 3/18/2017.
 */

public class PageAdapter extends AGVRecyclerViewAdapter<PageAdapter.PageViewHolder> {

    private ArrayList<Group> listGroup;
    private Context context;
    private IRecyclerViewListener listener;

    public PageAdapter(ArrayList<Group> listPage, Context context) {
        this.listGroup = listPage;
        this.context = context;
    }

    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent,
                false);
        return new PageAdapter.PageViewHolder(root);
    }


    @Override
    public void onBindViewHolder(PageViewHolder holder, final int position) {
        Group page = listGroup.get(position);
        holder.tvNamePage.setText(page.getName());
        Glide.with(context).load(page.getImage()).into(holder.imvCoverPage);
        holder.rltPageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickItem(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }

    @Override
    public AsymmetricItem getItem(int position) {
        return listGroup.get(position);
    }

    class PageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_page)
        TextView tvNamePage;
        @BindView(R.id.tv_categoty_page)
        TextView tvCategoryPage;
        @BindView(R.id.tv_fan_count)
        TextView tvFanCount;
        @BindView(R.id.imv_cover_page)
        ImageView imvCoverPage;
        @BindView(R.id.rlt_page_item)
        RelativeLayout rltPageItem;

        public PageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(IRecyclerViewListener listener) {
        this.listener = listener;
    }
}
