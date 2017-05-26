package com.uyenpham.diploma.myenglish.fragment.game;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.adapter.CardAdapter;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.interfaces.ICardClick;
import com.uyenpham.diploma.myenglish.model.Card;
import com.uyenpham.diploma.myenglish.model.Vocabulary;
import com.uyenpham.diploma.myenglish.utils.Const;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

/**
 * Created by Ka on 4/9/2017.
 */

public class GameCardFragment extends BaseFragment implements ICardClick {
    @BindView(R.id.rcv_card)
    RecyclerView rcvCard;

    ArrayList<Vocabulary> listAllWord;
    ArrayList<Card> listCard;
    CardAdapter adapter;
    int countFliped = 0;
    int previousIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_flip_card_game;
    }

    @Override
    protected void createView(View view) {
        getExtra();
        initData();
        initView();
    }

    private void initView() {
        rcvCard.setLayoutManager(new GridLayoutManager(mActivity, 3));
        adapter = new CardAdapter(listCard, mActivity);
        adapter.setListener(this);
        rcvCard.setAdapter(adapter);
    }

    private void getExtra() {
        Bundle bundle = getArguments();
        listAllWord = bundle.getParcelableArrayList(Const.KEY_LIST_WORD);
    }

    private void initData() {
        listCard = new ArrayList<>();
        Collections.shuffle(listAllWord);
        for (int i = 0; i < 12; i++) {
            Card card = new Card();
            if (i <= 5) {
                Vocabulary word = listAllWord.get(i);
                card.setType(1);
                card.setText(word.getWord());
                card.setID(word.getID());
            } else {
                Vocabulary word = listAllWord.get(i - 6);
                card.setType(2);
                card.setText(word.getImage());
                card.setID(word.getID());
            }
            listCard.add(card);
            Collections.shuffle(listCard);
        }
    }

    @Override
    public void onClickCard(View root, View front, View back, int position) {
        countFliped++;
        if (countFliped == 2 && previousIndex != -1) {
            if (adapter.getListCard().get(position).getID() == adapter.getListCard().get(previousIndex).getID()) {
                adapter.getListCard().get(position).setFliped(true);
            } else {
                adapter.getListCard().get(previousIndex).setFliped(false);
                adapter.getListCard().get(position).setFliped(false);
                adapter.notifyItemChanged(position);
                adapter.notifyItemChanged(previousIndex);
            }
            countFliped = 0;
            previousIndex = -1;

        }else {
            previousIndex =position;
        }
    }
}
