package com.uyenpham.diploma.myenglish.fragment.vocabulary;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.adapter.GroupAdapter;
import com.uyenpham.diploma.myenglish.customviews.ItemOffsetDecoration;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.interfaces.IRecyclerViewListener;
import com.uyenpham.diploma.myenglish.model.Group;
import com.uyenpham.diploma.myenglish.utils.FragmentHelper;
import com.uyenpham.diploma.myenglish.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Ka on 4/7/2017.
 */

public class GroupFragment extends BaseFragment implements IRecyclerViewListener {
    private int[] imageGroup = {R.drawable.color_group, R.drawable.animal_group, R.drawable
            .transport_group
            , R.drawable.family_group, R.drawable.musical_instrument_group, R.drawable
            .weather_group, R.drawable.emotion_group,
            R.drawable.colose_group, R.drawable.kitchen_group, R.drawable.foosd_group, R.drawable
            .job_group,
            R.drawable.sport_group, R.drawable.healthy_group, R.drawable.country_group, R
            .drawable.music_group};

    @BindView(R.id.rcv_group)
    RecyclerView rcvGroup;

    GroupAdapter adapter;
    ArrayList<Group> listGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    protected void createView(View view) {
        rcvGroup.setLayoutManager(new GridLayoutManager(mActivity, 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(Utils.dpToPx(mActivity, 1));
        rcvGroup.addItemDecoration(itemOffsetDecoration);

        initData();
    }

    private void initData() {
        listGroup = new ArrayList<>();
        getlistGroup();
        adapter= new GroupAdapter(listGroup,mActivity);
        adapter.setListener(this);
        rcvGroup.setAdapter(adapter);
    }

    private void getlistGroup() {
        listGroup.addAll(mActivity.mDatabase.getAllGroup());
        for (int i = 0; i < listGroup.size(); i++) {
            listGroup.get(i).setImage(imageGroup[i]);
        }
    }

    @Override
    public void onClickItem(View view, int postion) {
      String groupID = listGroup.get(postion).getId();
        Bundle bundle = new Bundle();
        bundle.putString("groupID", groupID);
        ListWordFragment listWordFragment= new ListWordFragment();
        listWordFragment.setArguments(bundle);
        FragmentHelper.replaceFragmentAddToBackStack(listWordFragment, mActivity.getSupportFragmentManager());
    }

    @Override
    public void onLongClickItem(View v, int position) {

    }
}
