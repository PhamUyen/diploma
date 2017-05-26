package com.uyenpham.diploma.myenglish.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.customviews.CustomNavigationBar;
import com.uyenpham.diploma.myenglish.fragment.chat.ListChatFragment;
import com.uyenpham.diploma.myenglish.fragment.game.GameFragment;
import com.uyenpham.diploma.myenglish.fragment.vocabulary.GroupFragment;
import com.uyenpham.diploma.myenglish.interfaces.OnBackPressed;
import com.uyenpham.diploma.myenglish.utils.FragmentHelper;
import com.uyenpham.diploma.myenglish.utils.LogUtil;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    public static final int POSITION_PAGE_PROFILE = 0;
    public static final int POSITION_PAGE_FRIEND = 1;
    public static final int POSITION_PAGE_CHAT = 2;
    public static final int POSITION_PAGE_IMAGE = 3;
    public static final int POSITION_PAGE_VIDEO = 4;
    public static final int ID_MAIN_CONTENT = R.id.content;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    int tabPosition = POSITION_PAGE_PROFILE;
    public FragmentManager mFragmentManager;
    private CustomNavigationBar navigationBar;
    private OnBackPressed onBackPressed;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mFragmentManager = getSupportFragmentManager();
        setNavigationBar();
        switchTab(R.id.tab_profile);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                LogUtil.e("here click!");
                switchTab(tabId);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (onBackPressed != null) {
            onBackPressed.OnBack();
            return;
        }

        super.onBackPressed();
    }
    public void setOnBackPressed(OnBackPressed onBackPressed) {
        this.onBackPressed = onBackPressed;
    }

    public CustomNavigationBar getNavigationBar() {
        return navigationBar;
    }

    public void setNavigationBar() {
        navigationBar = (CustomNavigationBar) findViewById(R.id.nvBar);
    }

    /**
     * Switch fragment when click tabbar
     *
     * @param tabId This is id of button when click
     */
    public void switchTab(int tabId) {
        switch (tabId) {
            case R.id.tab_profile:
                GroupFragment groupFragment = new GroupFragment();
                FragmentHelper.replaceFragmentAddToBackStack(groupFragment,mFragmentManager);
                LogUtil.e("profile!");
                break;
            case R.id.tab_friend:
                GameFragment gameFragment = new GameFragment();
                FragmentHelper.replaceFragmentAddToBackStack(gameFragment,mFragmentManager);
                LogUtil.e("profile!");
                break;
            case R.id.tab_chat:
                ListChatFragment chatFragment = new ListChatFragment();
                FragmentHelper.replaceFragmentAddToBackStack(chatFragment,mFragmentManager);
                LogUtil.e("chat!");
                break;
            case R.id.tab_photo:
                LogUtil.e("photo!");
                break;
            default:
                // DO NOTHING
                break;
        }
    }

}
