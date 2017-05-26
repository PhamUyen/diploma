package com.uyenpham.diploma.myenglish.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.model.chat.UserModel;
import com.uyenpham.diploma.myenglish.utils.CommonUtils;
import com.uyenpham.diploma.myenglish.utils.DialogUtils;
import com.uyenpham.diploma.myenglish.utils.LogUtil;
import com.uyenpham.diploma.myenglish.utils.PreferenceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Ka on 12/17/2016.
 */

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.icon)
    ImageView iconApp;
    @BindView(R.id.ln_login)
    LinearLayout lnLogin;
    @BindView(R.id.ln_sign_up)
    LinearLayout lnSignUp;
    @BindView(R.id.ed_email)
    EditText edEmailReg;
    @BindView(R.id.ed_pass)
    EditText edPassReg;
    @BindView(R.id.ed_username)
    EditText edUsername;
    @BindView(R.id.ed_email_login)
    EditText edEmailLogin;
    @BindView(R.id.ed_pass_login)
    EditText edPassLogin;

    Handler handler = new Handler();

    FirebaseAuth fireAuth;
    UserModel userModel;
    String name = "";
    private ArrayList<UserModel> listUser;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mFacebookHelper.getKeyHash(this);
        fireAuth = FirebaseAuth.getInstance();
        fireAuthListener();
        if(!PreferenceUtils.getBoolean(SplashActivity.this,"IS_LOGIN")){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkLogin();
                }
            }, 1500);
            getListUser();
        }else {
            goToActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            fireAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @OnClick({R.id.btn_sign_in_fb})
    void onSignInFb() {
        loginFacebook();
    }

    @OnClick({R.id.btn_sign_in})
    void onSignIn() {
        String email = edEmailLogin.getText().toString().trim();
        String pass = edPassLogin.getText().toString().trim();
        signInWithEmailExist(email, pass);
    }

    @OnClick({R.id.btn_sign_up})
    void onSignUp() {
        name = edUsername.getText().toString();
        createAuthWithEmail();
    }

    @OnClick({R.id.tv_sign_up})
    void onReg() {
        lnLogin.clearAnimation();
        lnLogin.setVisibility(View.GONE);
        lnSignUp.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim
                .activity_scale_center);
        animation.setFillAfter(true);
        lnSignUp.startAnimation(animation);
    }

    private void createAuthWithEmail() {
        String email = edEmailReg.getText().toString().trim();
        String password = edPassReg.getText().toString().trim();
        fireAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SplashActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SplashActivity.this, "Authentication failed." + task
                                            .getException(),
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Authentication failed.:", task.getException().toString());
                        } else {
//                            lnSignUp.setVisibility(View.GONE);
                            lnSignUp.clearAnimation();
//                            lnLogin.setVisibility(View.VISIBLE);
//                            Animation loginAnimation = AnimationUtils.loadAnimation
//                                    (SplashActivity.this, R
//                                            .anim.activity_scale_center);
//                            loginAnimation.setFillAfter(true);
//                            lnLogin.startAnimation(loginAnimation);
                        }
                    }
                });
    }

    private void saveUserInfo(UserModel userModel) {
        mFirebaseDatabaseReference.child("user").child(userModel.getId()).setValue(userModel);
    }

    private void getListUser() {
        listUser = new ArrayList<>();
        DatabaseReference ref = this.mFirebaseDatabaseReference.child("user");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    UserModel userModel = data.getValue(UserModel.class);
                    userModel.setId(data.getKey());
                    listUser.add(userModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void signInWithEmailExist(String email, String password) {
        fireAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SplashActivity.this, "signInWithEmail:failed",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            goToActivity(MainActivity.class);
                        }

                        // ...
                    }
                });
    }

    private void loginFacebook() {
        mFacebookHelper.login(this, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LogUtil.e("success!");
//                PreferenceUtils.saveBoolean(SplashActivity.this, Const.IS_LOGIN, true);
//                goToActivity(MainActivity.class);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                LogUtil.e("error!");
                DialogUtils.showDialogError(error.getMessage(), SplashActivity.this, null);
            }
        });
    }

    private void fireAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    LogUtil.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    userModel = new UserModel();
                    userModel.setId(user.getUid());
                    if (CommonUtils.checkEmpty(name)) {
                        userModel.setName(user.getDisplayName());
                    } else {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();
                        user.updateProfile(profileUpdates);
                        userModel.setName(name);
                    }
                    String photo = "";
                    if (user.getPhotoUrl() != null) {
                        photo = user.getPhotoUrl().toString();
                    }
                    userModel.setPhoto_profile(photo);
                    saveUserInfo(userModel);
                    PreferenceUtils.saveBoolean(SplashActivity.this,"IS_LOGIN",true);
                    goToActivity(MainActivity.class);
                } else {
                    // User is signed out
                    LogUtil.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        fireAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void checkLogin() {
        fireAuth.addAuthStateListener(mAuthListener);
        final Animation logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim
                .zoom_in_and_translate);
        logoMoveAnimation.setFillAfter(true);
        iconApp.startAnimation(logoMoveAnimation);
        logoMoveAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lnLogin.setVisibility(View.VISIBLE);
                Animation loginAnimation = AnimationUtils.loadAnimation(SplashActivity.this, R
                        .anim.activity_scale_center);
                loginAnimation.setFillAfter(true);
                lnLogin.startAnimation(loginAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
