package com.uyenpham.diploma.myenglish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.interfaces.IRecyclerWordClick;
import com.uyenpham.diploma.myenglish.model.Vocabulary;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ka on 3/14/2017.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private ArrayList<Vocabulary> listWord;
    private Context context;
    private IRecyclerWordClick listener;

    public WordAdapter(ArrayList<Vocabulary> listWord, Context context) {
        this.listWord = listWord;
        this.context = context;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent,
                false);
        return new WordAdapter.WordViewHolder(root);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, final int position) {
        Vocabulary word= listWord.get(position);
        holder.tvWord.setText(word.getWord());
        holder.tvMean.setText(word.getMean());
        holder.iconFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!= null){
                    listener.onFavorite(position);
                }
            }
        });
        holder.iconSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!= null){
                    listener.onSpeaker(position);
                }
            }
        });
        holder.rltItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listWord.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_word)
        TextView tvWord;
        @BindView(R.id.tv_mean)
        TextView tvMean;
        @BindView(R.id.rlt_item_word)
        RelativeLayout rltItem;
        @BindView(R.id.icon_speaker)
        ImageView iconSpeaker;
        @BindView(R.id.icon_favorite)
        ImageView iconFavorite;

        public WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(IRecyclerWordClick listener) {
        this.listener = listener;
    }
}
