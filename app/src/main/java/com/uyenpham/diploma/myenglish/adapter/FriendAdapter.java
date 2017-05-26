package com.uyenpham.diploma.myenglish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.customviews.customviews.imageview.CircleImageView;
import com.uyenpham.diploma.myenglish.interfaces.IRecyclerViewListener;
import com.uyenpham.diploma.myenglish.model.chat.UserModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ka on 3/14/2017.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.WordViewHolder> {
    private ArrayList<UserModel> listUser;
    private Context context;
    private IRecyclerViewListener listener;

    public FriendAdapter(ArrayList<UserModel> listUser, Context context) {
        this.listUser = listUser;
        this.context = context;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chat, parent,
                false);
        return new FriendAdapter.WordViewHolder(root);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, final int position) {
        UserModel user= listUser.get(position);
        holder.tvName.setText(user.getName());
        Glide.with(context).load(user.getPhoto_profile())
                .dontAnimate()
                .placeholder(R.drawable.color_group)
                .into(holder.imvProfile);
        holder.contentItem.setOnClickListener(new View.OnClickListener() {
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
        return listUser.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.imv_profile)
        CircleImageView imvProfile;
        @BindView(R.id.content_item)
        LinearLayout contentItem;

        public WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(IRecyclerViewListener listener) {
        this.listener = listener;
    }
}
