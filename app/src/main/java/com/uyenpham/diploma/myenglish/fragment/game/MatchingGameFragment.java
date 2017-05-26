package com.uyenpham.diploma.myenglish.fragment.game;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.customviews.imageview.RoundesImageView;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.model.Vocabulary;
import com.uyenpham.diploma.myenglish.utils.Const;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

/**
 * Created by Ka on 4/15/2017.
 */

public class MatchingGameFragment extends BaseFragment implements View.OnTouchListener {
    @BindView(R.id.tv_drag1)
    TextView tvDrag1;
    @BindView(R.id.tv_drag2)
    TextView tvDrag2;
    @BindView(R.id.tv_drag3)
    TextView tvDrag3;
    @BindView(R.id.tv_drag4)
    TextView tvDrag4;
    @BindView(R.id.image1)
    RoundesImageView image1;
    @BindView(R.id.image2)
    RoundesImageView image2;
    @BindView(R.id.image3)
    RoundesImageView image3;
    @BindView(R.id.image4)
    RoundesImageView image4;
    @BindView(R.id.tv_answer_1)
    TextView tvAnswer1;
    @BindView(R.id.tv_answer_2)
    TextView tvAnswer2;
    @BindView(R.id.tv_answer_3)
    TextView tvAnswer3;
    @BindView(R.id.tv_answer_4)
    TextView tvAnswer4;
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;

    private ArrayList<Vocabulary> listAllWord;
    private ArrayList<String> listWord;
    private ArrayList<String> listImage;
    private int xDelta;
    private int yDelta;
    private TextView tvMove;

    String fontPath;
    Typeface face;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_matching_game;
    }

    @Override
    protected void createView(View view) {
        initView();
        getExtra();
        initData();
        fillDataToView();
    }

    private void initView() {
        fontPath = mActivity.getString(R.string.Baloo_Regular);
        face = Typeface.createFromAsset(mActivity.getAssets(), fontPath);
        tvDrag1.setTypeface(face);
        tvDrag2.setTypeface(face);
        tvDrag3.setTypeface(face);
        tvDrag4.setTypeface(face);
        image1.setCornerRadiusDimen(R.dimen.corner_5dp);
        image2.setCornerRadiusDimen(R.dimen.corner_5dp);
        image3.setCornerRadiusDimen(R.dimen.corner_5dp);
        image4.setCornerRadiusDimen(R.dimen.corner_5dp);
        tvDrag1.setOnClickListener(moveClick);
        tvDrag2.setOnClickListener(moveClick);
        tvDrag3.setOnClickListener(moveClick);
        tvDrag4.setOnClickListener(moveClick);
        tvAnswer1.setOnClickListener(targetClick);
        tvAnswer2.setOnClickListener(targetClick);
        tvAnswer3.setOnClickListener(targetClick);
        tvAnswer4.setOnClickListener(targetClick);
    }

    private void getExtra() {
        Bundle bundle = getArguments();
        listAllWord = bundle.getParcelableArrayList(Const.KEY_LIST_WORD);
    }

    private void initData() {
        Collections.shuffle(listAllWord);
        listImage = new ArrayList<>();
        listWord = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Vocabulary word = listAllWord.get(i);
            listWord.add(word.getWord());
            listImage.add(word.getImage());
        }
        Collections.shuffle(listImage);
        Collections.shuffle(listWord);
    }

    private void fillDataToView() {
        ImageView[] imageView = {image1, image2, image3, image4};
        TextView[] textView = {tvDrag1, tvDrag2, tvDrag3, tvDrag4};
        for (int i = 0; i < listWord.size(); i++) {
            textView[i].setText(listWord.get(i));
            Glide.with(mActivity).load(listImage.get(i))
                    .dontAnimate()
                    .into(imageView[i]);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int x = (int) motionEvent.getRawX();
        final int y = (int) motionEvent.getRawY();

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams)
                        view.getLayoutParams();

                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                break;

            case MotionEvent.ACTION_UP:
                Toast.makeText(mActivity,
                        "thanks for new location!", Toast.LENGTH_SHORT)
                        .show();
                break;

            case MotionEvent.ACTION_MOVE:
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = x - xDelta;
                layoutParams.topMargin = y - yDelta;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                view.setLayoutParams(layoutParams);
                break;
        }
        mainLayout.invalidate();
        return true;
    }

    View.OnClickListener moveClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tvMove = (TextView) view;
        }
    };

    View.OnClickListener targetClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView tvTarget = (TextView) view;
            moveToAnswer(tvMove, tvTarget);
        }
    };

    /**
     * Move view to answer area.
     *
     * @param viewOne this view need to move.
     * @param viewTwo this view target that one moving to it.
     */
    private synchronized void moveToAnswer(final TextView viewOne, final TextView viewTwo) {
        final String letter = viewOne.getText().toString();
        TranslateAnimation animation = new TranslateAnimation(0, viewTwo.getX() - viewOne.getX(),
                0, viewTwo.getY() - viewOne.getY());
        animation.setRepeatMode(0);
        animation.setDuration(200);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewOne.setOnClickListener(null);
                viewOne.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewTwo.setText(letter);
                viewTwo.setBackgroundResource(R.drawable.backgroud_listview);
                viewTwo.setTypeface(face);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewOne.startAnimation(animation);
    }
}
