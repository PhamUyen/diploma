package com.uyenpham.diploma.myenglish.fragment;

import android.app.Activity;
import android.view.View;

import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.activity.MainActivity;
import com.uyenpham.diploma.myenglish.adapter.PageAdapter;
import com.uyenpham.diploma.myenglish.customviews.CustomNavigationBar;
import com.uyenpham.diploma.myenglish.customviews.ItemOffsetDecoration;
import com.uyenpham.diploma.myenglish.customviews.asymeticView.AsymmetricRecyclerView;
import com.uyenpham.diploma.myenglish.customviews.asymeticView.AsymmetricRecyclerViewAdapter;
import com.uyenpham.diploma.myenglish.customviews.catloading.CatLoadingView;
import com.uyenpham.diploma.myenglish.interfaces.IRecyclerViewListener;
import com.uyenpham.diploma.myenglish.model.Group;
import com.uyenpham.diploma.myenglish.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Ka on 3/12/2017.
 */

public class PageFragment extends BaseFragment implements IRecyclerViewListener {

    private MainActivity main;
    private CatLoadingView catLoadingView;
    private CustomNavigationBar navigationBar;

    private ArrayList<Group> listPage;
    PageAdapter adapter;
    AsymmetricRecyclerViewAdapter pageAdapter;

    @BindView(R.id.recyclerView_page)
    AsymmetricRecyclerView recyclerView;

    private int[] imageGroup = {R.drawable.color_group, R.drawable.animal_group, R.drawable
            .transport_group
            , R.drawable.family_group, R.drawable.musical_instrument_group, R.drawable
            .weather_group, R.drawable.emotion_group,
            R.drawable.colose_group, R.drawable.kitchen_group, R.drawable.foosd_group, R.drawable
            .job_group,
            R.drawable.sport_group, R.drawable.healthy_group, R.drawable.country_group, R
            .drawable.music_group};

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.main = (MainActivity) activity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page;
    }

    @Override
    protected void createView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        initNavigationBar();
        initData();
    }

    private void initData() {
        listPage = new ArrayList<>();
        catLoadingView = new CatLoadingView();
        getlistGroup();
        recyclerView.setRequestedColumnCount(2);
        recyclerView.setDebugging(true);
        adapter = new PageAdapter(listPage, mActivity);
        recyclerView.setRequestedHorizontalSpacing(Utils.dpToPx(main, 3));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(Utils.dpToPx(main, 1));
        recyclerView.addItemDecoration(itemOffsetDecoration);

        pageAdapter = new AsymmetricRecyclerViewAdapter<>(main, recyclerView,
                adapter);
        recyclerView.setAdapter(pageAdapter);
        recyclerView.setListener(this);
    }

    /**
     * Define base navigationbar
     */
    private void initNavigationBar() {
        navigationBar = main.getNavigationBar();
        navigationBar.reSetAll();
        navigationBar.hideImgright();
        navigationBar.hideImgLeft();
        navigationBar.setTitle(getString(R.string.txt_video));
        navigationBar.setBackgroudNavi(R.color.color_tab_video);
    }

    private void getlistGroup() {
        listPage.addAll(mActivity.mDatabase.getAllGroup());
        for (int i = 0; i < listPage.size(); i++) {
            listPage.get(i).setImage(imageGroup[i]);
        }
        setSpanCount(listPage);
    }

    private void setSpanCount(ArrayList<Group> list) {
        for (int i = 0; i < list.size(); i += 6) {
            list.get(i).setColumnSpan(1);
            list.get(i).setRowSpan(1);
            if (i + 1 < list.size()) {
                list.get(i + 1).setRowSpan(1);
                list.get(i + 1).setColumnSpan(1);
            }
            if (i + 2 < list.size()) {
                list.get(i + 2).setColumnSpan(2);
                list.get(i + 2).setRowSpan(1);
            }
            if (i + 3 < list.size()) {
                list.get(i + 3).setRowSpan(1);
                list.get(i + 3).setColumnSpan(1);
            }
            if (i + 4 < list.size()) {
                list.get(i + 4).setRowSpan(2);
                list.get(i + 4).setColumnSpan(1);
            }
            if (i + 5 < list.size()) {
                list.get(i + 5).setRowSpan(1);
                list.get(i + 5).setColumnSpan(1);
            }
        }
    }

    @Override
    public void onClickItem(View view, int postion) {
    }

    @Override
    public void onLongClickItem(View v, int position) {

    }
}
