package com.uyenpham.diploma.myenglish.fragment.vocabulary;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.adapter.WordAdapter;
import com.uyenpham.diploma.myenglish.customviews.ItemOffsetDecoration;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.interfaces.IRecyclerWordClick;
import com.uyenpham.diploma.myenglish.model.Vocabulary;
import com.uyenpham.diploma.myenglish.utils.Const;
import com.uyenpham.diploma.myenglish.utils.FragmentHelper;
import com.uyenpham.diploma.myenglish.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Ka on 4/7/2017.
 */

public class ListWordFragment extends BaseFragment implements IRecyclerWordClick {
    private String groupID;
    private ArrayList<Vocabulary> listWord;

    @BindView(R.id.rcv_group)
    RecyclerView rcvWord;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    protected void createView(View view) {
        getExtra();
        initView();
        initData();
    }

    private void getExtra() {
        Bundle bundle = getArguments();
        groupID = bundle.getString("groupID");
    }

    private void initView() {
        rcvWord.setLayoutManager(new LinearLayoutManager(mActivity));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(Utils.dpToPx
                (mActivity, 1));
        rcvWord.addItemDecoration(itemOffsetDecoration);
    }

    private void initData() {
        listWord = mActivity.mDatabase.getListVocalByGroupID(groupID);
        String json = new Gson().toJson(listWord);
        WordAdapter adapter = new WordAdapter(listWord, mActivity);
        adapter.setListener(this);
        rcvWord.setAdapter(adapter);
    }

    @Override
    public void onFavorite(int position) {

    }

    @Override
    public void onSpeaker(int position) {
        String word = listWord.get(position).getWord();
        String url = Const.GOOGLE_VOCABULARY.replace("hello", word);
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mActivity, Uri.parse(url));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        Vocabulary word = listWord.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_WORD, word);
        bundle.putParcelableArrayList(Const.KEY_LIST_WORD, listWord);
        WordDetailFragment wordDetailFragment = new WordDetailFragment();
        wordDetailFragment.setArguments(bundle);
        FragmentHelper.replaceFragmentAddToBackStack(wordDetailFragment,mActivity.getSupportFragmentManager());
    }
}
