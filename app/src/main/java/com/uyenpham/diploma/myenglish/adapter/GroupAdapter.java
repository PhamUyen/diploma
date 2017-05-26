package com.uyenpham.diploma.myenglish.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.customviews.imageview.CircleImageView;
import com.uyenpham.diploma.myenglish.interfaces.IRecyclerViewListener;
import com.uyenpham.diploma.myenglish.model.Group;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ka on 4/7/2017.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private ArrayList<Group> listGroup;
    private Context context;
    private IRecyclerViewListener listener;

    public GroupAdapter(ArrayList<Group> listGroup, Context context) {
        this.listGroup = listGroup;
        this.context = context;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent,
                false);
        return new GroupAdapter.GroupViewHolder(root);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, final int position) {
        Group group = listGroup.get(position);
        String fontPath = context.getString(R.string.Baloo_Regular);
        Typeface face = Typeface.createFromAsset(context.getAssets(), fontPath);
        holder.groupName.setTypeface(face);
        holder.groupNameVN.setTypeface(face);
        holder.groupName.setText(group.getName());
        holder.groupNameVN.setText(group.getMean());
        Glide.with(context).load(group.getImage())
                .into(holder.groupPhoto);
        holder.cvGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!= null){
                    listener.onClickItem(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView_group)
        RelativeLayout cvGroup;
        @BindView(R.id.group_name)
        TextView groupName;
        @BindView(R.id.group_photo)
        CircleImageView groupPhoto;
        @BindView(R.id.group_name_vn)
        TextView groupNameVN;

        public GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(IRecyclerViewListener listener) {
        this.listener = listener;
    }
}
