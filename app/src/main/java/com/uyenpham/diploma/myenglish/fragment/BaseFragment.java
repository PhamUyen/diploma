package com.uyenpham.diploma.myenglish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uyenpham.diploma.myenglish.activity.BaseActivity;
import com.uyenpham.diploma.myenglish.utils.Const;
import com.uyenpham.diploma.myenglish.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;
    protected View mView;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
//        if(savedInstanceState!= null)
//        {
//            mFragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//
//            FragmentOne fragment = new FragmentOne();
//
//            fragmentTransaction.add(R.id.fragment_container, fragment);
//            fragmentTransaction.commit();
//        }
        setRetainInstance(true);
    }


    protected void showProgressDialog(String message) {
        mActivity.showProgressDialog(message);
    }

    protected void showProgressDialog(int messageId) {
        mActivity.showProgressDialog(messageId);
    }

    protected void hideProgressDialog() {
        mActivity.hideProgressDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String className = getClass().getName();
        if (mView == null) {
            LogUtil.d(className + " view null => init");
            mView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, mView);
            createView(mView);
        } else {
            LogUtil.d(className + " view not null");
        }
        return mView;
    }

    public void goToActivity(Class c) {
        Intent intent = new Intent(mActivity, c);
        startActivity(intent);
        mActivity.overridePendingTransition(0, 0);
    }

    public void goToActivity(Class c, Bundle bundle) {
        Intent intent = new Intent(mActivity, c);
        intent.putExtra(Const.KEY_EXTRA_DATA, bundle);
        startActivity(intent);
        mActivity.overridePendingTransition(0, 0);
    }

    protected abstract int getLayoutId();

    protected abstract void createView(View view);

}
