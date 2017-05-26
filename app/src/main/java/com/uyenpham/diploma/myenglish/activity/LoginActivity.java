package com.uyenpham.diploma.myenglish.activity;


import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.utils.Const;
import com.uyenpham.diploma.myenglish.utils.DialogUtils;
import com.uyenpham.diploma.myenglish.utils.LogUtil;
import com.uyenpham.diploma.myenglish.utils.PreferenceUtils;

import butterknife.OnClick;

/**
 * Created by Ka on 2/6/2017.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_login_fb})
    void onClick() {
        loginFacebook();
    }

    private void loginFacebook() {
        mFacebookHelper.login(this, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LogUtil.e("success!");
                PreferenceUtils.saveBoolean(LoginActivity.this, Const.IS_LOGIN, true);
                goToActivity(MainActivity.class);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                LogUtil.e("error!");
                DialogUtils.showDialogError(error.getMessage(), LoginActivity.this, null);
            }
        });
    }
}
