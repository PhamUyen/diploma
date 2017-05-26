package com.uyenpham.diploma.myenglish.fragment.vocabulary;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.model.Vocabulary;
import com.uyenpham.diploma.myenglish.utils.CommonUtils;
import com.uyenpham.diploma.myenglish.utils.Const;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ka on 4/8/2017.
 */

public class WordDetailFragment extends BaseFragment {
    @BindView(R.id.conten_word)
    RelativeLayout contentWord;
    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.tv_pronun)
    TextView tvPronunciation;
    @BindView(R.id.tv_explain)
    TextView tvExPlain;
    @BindView(R.id.tv_mean)
    TextView tvMean;
    @BindView(R.id.imv_speaker)
    ImageView imvSpeaker;
    @BindView(R.id.imv_word_photo)
    ImageView imvWordPhoto;
    @BindView(R.id.tv_example)
    TextView tvExample;
    @BindView(R.id.imv_recorder)
    ImageView imvRecoder;
    @BindView(R.id.tv_notable)
    TextView tvnotable;
    @BindView(R.id.imv_next)
    ImageView imvNext;
    @BindView(R.id.imv_back)
    ImageView imvBack;

    private Vocabulary word;
    private ArrayList<Vocabulary> listWord;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_word_detail;
    }

    @Override
    protected void createView(View view) {
        getExtra();
        setInfoWord(word);
    }

    @OnClick({R.id.imv_next, R.id.imv_back,R.id.imv_speaker})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_next:
                int currentIndex = listWord.indexOf(word);
                word = listWord.get(currentIndex + 1);
                setInfoWord(word);
                Animation rightToLeft = AnimationUtils.loadAnimation(mActivity, R.anim
                        .slide_in_right);
                contentWord.startAnimation(rightToLeft);
                break;
            case R.id.imv_back:
                int currentIndex2 = listWord.indexOf(word);
                word = listWord.get(currentIndex2 - 1);
                setInfoWord(word);
                Animation leftToRight = AnimationUtils.loadAnimation(mActivity, R.anim
                        .slide_in_left);
                contentWord.startAnimation(leftToRight);
                break;
            case R.id.imv_speaker:
                String url = Const.GOOGLE_VOCABULARY.replace("hello", word.getWord());
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(mActivity, Uri.parse(url));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void getExtra() {
        Bundle bundle = getArguments();
        word = bundle.getParcelable(Const.KEY_WORD);
        listWord = bundle.getParcelableArrayList(Const.KEY_LIST_WORD);
    }

    private void setInfoWord(Vocabulary word) {
        String fontPath = mActivity.getString(R.string.Baloo_Regular);
        Typeface face = Typeface.createFromAsset(mActivity.getAssets(), fontPath);
        tvWord.setTypeface(face);
        tvWord.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tvWord.setText(word.getWord());
        tvPronunciation.setText(word.getPronunciation());
        if (!CommonUtils.checkEmpty(word.getExample())) {
            tvExample.setVisibility(View.VISIBLE);
            tvExample.setText(word.getExample());
        } else {
            tvExample.setVisibility(View.GONE);
        }
        tvExPlain.setText(word.getExplain());
        tvMean.setText(word.getMean());
        Glide.with(mActivity).load(word.getImage())
                .into(imvWordPhoto);
        if (listWord.indexOf(word) == listWord.size() - 1) {
            imvNext.setVisibility(View.GONE);
        } else {
            imvNext.setVisibility(View.VISIBLE);
        }

        if (listWord.indexOf(word) == 0) {
            imvBack.setVisibility(View.GONE);
        } else {
            imvBack.setVisibility(View.VISIBLE);
        }
    }
}
