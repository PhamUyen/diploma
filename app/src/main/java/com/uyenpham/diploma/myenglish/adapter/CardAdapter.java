package com.uyenpham.diploma.myenglish.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.customviews.FlipAnimation;
import com.uyenpham.diploma.myenglish.interfaces.ICardClick;
import com.uyenpham.diploma.myenglish.model.Card;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ka on 4/9/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    ArrayList<Card> listCard;
    ICardClick listener;
    Context context;

    public CardAdapter(ArrayList<Card> listCard, Context context) {
        this.listCard = listCard;
        this.context = context;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent,
                false);
        return new CardAdapter.CardViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        final Card card= listCard.get(position);
        if(card.isFliped()){
            holder.itemCard.setEnabled(false);
            holder.cardFront.setVisibility(View.GONE);
            holder.cardBack.setVisibility(View.VISIBLE);
        }else {
            holder.cardFront.setVisibility(View.VISIBLE);
            holder.cardBack.setVisibility(View.GONE);
            holder.itemCard.setEnabled(true);
        }
        if(card.getType() == 1){
            holder.cardText.setVisibility(View.VISIBLE);
            holder.cardImage.setVisibility(View.GONE);
            holder.cardText.setText(card.getText());
        }else {
            holder.cardImage.setVisibility(View.VISIBLE);
            holder.cardText.setVisibility(View.GONE);
            Glide.with(context).load(card.getText())
                    .into(holder.cardImage);
        }
         holder.itemCard.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 flipCard(holder.itemCard, holder.cardFront,holder.cardBack, view, position);
             }
         });
    }

    public ArrayList<Card> getListCard() {
        return listCard;
    }

    private void flipCard(final View rootLayout, final View cardFace, final View cardBack, final View view, final int position) {
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();

        }
        rootLayout.startAnimation(flipAnimation);
        flipAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(listener!= null){
                    listener.onClickCard(rootLayout,cardFace,cardBack,position);
                    rootLayout.setEnabled(false);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return listCard.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_card)
        RelativeLayout itemCard;
        @BindView(R.id.card_front)
        ImageView cardFront;
        @BindView(R.id.card_back)
        RelativeLayout cardBack;
        @BindView(R.id.card_image_type)
        ImageView cardImage;
        @BindView(R.id.card_text_type)
        TextView cardText;
        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            String fontPath = context.getString(R.string.Baloo_Regular);
            Typeface face = Typeface.createFromAsset(context.getAssets(), fontPath);
            cardText.setTypeface(face);
        }
    }

    public void setListener(ICardClick listener) {
        this.listener = listener;
    }
}
