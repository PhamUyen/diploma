package com.uyenpham.diploma.myenglish.fragment.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.adapter.TypeQuestionAdapter;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.interfaces.IPagerClick;
import com.uyenpham.diploma.myenglish.model.Vocabulary;
import com.uyenpham.diploma.myenglish.utils.Const;
import com.uyenpham.diploma.myenglish.utils.FragmentHelper;
import com.uyenpham.diploma.myenglish.utils.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import me.crosswall.lib.coverflow.core.PagerContainer;

/**
 * Created by Ka on 4/8/2017.
 */

public class GameFragment extends BaseFragment {
    private static final int GAME_PICKWORD = 0;
    private static final int GAME_CARD = 1;
    private static final int GAME_MATCH = 2;

    ArrayList<Vocabulary> listAllWord;
    @BindView(R.id.viewpager_room)
    ViewPager viewPagerRoom;
    @BindView(R.id.pager_container)
    PagerContainer pagerContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void createView(View view) {
        listAllWord = mActivity.mDatabase.getAllWord();
        int[] images = {R.drawable.type_question_cauhoidovui, R.drawable
                .type_question_cauhoihieubiet, R.drawable.type_question_cauhoiiq};
        setupViewPager();
        TypeQuestionAdapter adapter = new TypeQuestionAdapter(mActivity, images);
        viewPagerRoom.setAdapter(adapter);
        viewPagerRoom.setClipChildren(false);
        viewPagerRoom.setOffscreenPageLimit(adapter.getCount());
        adapter.setListener(new IPagerClick() {
            @Override
            public void onPagerClick(int position) {
                LogUtil.d("page at number " + position);
                switch (position) {
                    case GAME_PICKWORD:
                        showGame(new GameCatchWordFragment());
                        break;
                    case GAME_CARD:
                        showGame(new GameCardFragment());
                        break;
                    case GAME_MATCH:
                        showGame(new MatchingGameFragment());
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void showGame(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Const.KEY_LIST_WORD, listAllWord);
        fragment.setArguments(bundle);
        FragmentHelper.replaceFragmentAddToBackStack(fragment,
                mActivity.getSupportFragmentManager());
    }

    private void setupViewPager(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        FrameLayout.LayoutParams sizeViewPager = (FrameLayout.LayoutParams) viewPagerRoom
                .getLayoutParams();
        sizeViewPager.width = (int) (85 * (width) / 100);
        sizeViewPager.height = (int) (85 * (width) / 100);
        viewPagerRoom.setLayoutParams(sizeViewPager);
        pagerContainer.setOverlapEnabled(true);
        viewPagerRoom = pagerContainer.getViewPager();
    }
}
