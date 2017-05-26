package com.uyenpham.diploma.myenglish.utils.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.AppInviteDialog;
import com.uyenpham.diploma.myenglish.utils.LogUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ka on 11/30/2016.
 */

public class FacebookHelper {
    private static FacebookHelper mFacebookHelper = new FacebookHelper();
    private static CallbackManager mCallbackManager;

    /**
     * get facebook hash key
     *
     * @param context
     */
    public void getKeyHash(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyhash = new String(Base64.encode(md.digest(), 0));
                LogUtil.e("hash key:", "" + keyhash);
            }
        } catch (PackageManager.NameNotFoundException e1) {

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * get instance of facebook helper class
     *
     * @param context
     * @return FacebookHelper
     */
    public static FacebookHelper getInstance(Context context) {
        FacebookSdk.sdkInitialize(context);
        mCallbackManager = CallbackManager.Factory.create();
        return mFacebookHelper;
    }

    /**
     * login facebook
     *
     * @param activity current activity when login
     * @param callback callback for login result
     */
    public void login(Activity activity, FacebookCallback<LoginResult> callback) {
        LoginManager loginManager = LoginManager.getInstance();
        List<String> permissions = new ArrayList<String>();
        permissions.add("public_profile");
        permissions.add("user_friends");
        permissions.add("user_posts");
        permissions.add("user_photos");
        permissions.add("user_likes");
        permissions.add("user_videos");
        permissions.add("user_hometown");
        loginManager.registerCallback(mCallbackManager, callback);
        loginManager.logInWithReadPermissions(activity, permissions);
    }

    /**
     * logout facebook
     *
     * @param callback callback for logout result
     */
    public void logoutFacebook(GraphRequest.Callback callback) {
        new GraphRequest(AccessToken.getCurrentAccessToken(),
                "/me/permissions/", null, HttpMethod.DELETE,
                callback).executeAsync();
    }

    /**
     * get friend list
     *
     * @param bundle   bundle to filter
     * @param callback callback for result
     */
    public void getFriendList(Bundle bundle, GraphRequest.Callback callback) {
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                "/me/friends",
                bundle,
                HttpMethod.GET,
                callback
        );
        request.executeAsync();
    }

    /**
     * invite friend install app
     *
     * @param activity        current activity when invite
     * @param appLinkUrl      link app to invite friend install
     * @param previewImageUrl prev image of app
     */
    public void inviteFriendInstallApp(Activity activity, String appLinkUrl, String
            previewImageUrl) {
        AppInviteContent content = new AppInviteContent.Builder()
                .setApplinkUrl(appLinkUrl)
                .setPreviewImageUrl(previewImageUrl)
                .build();
        LogUtil.d("content: " + content);
        AppInviteDialog.show(activity, content);
    }

    /**
     * get fb profile
     *
     * @param callback callback
     */
    public void getFacebookProfile(GraphRequest.GraphJSONObjectCallback callback) {
        Bundle params = new Bundle();
        params.putString("fields", "id, name, gender,picture,age_range,cover,context, " +
                "hometown");
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                callback);
        request.setParameters(params);
        request.executeAsync();
    }

    public void getAllPage(GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        params.putString("fields", "name,fan_count,id,cover,category");
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                "me/likes",
                params,
                HttpMethod.GET,
                callback);
        request.executeAsync();
    }

    /**
     * get post  of user
     *
     * @param callback
     */
    public void getPostOfUser(GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        params.putString("fields", "picture,story,created_time,name,message,object_id,comments" +
                ".limit(1000000000){id,message},likes.limit(1000000000){id,message}");
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                "/me/feed",
                params,
                HttpMethod.GET,
                callback
        );
        request.executeAsync();
    }

    public void loadMorePost(String nextPoint, GraphRequest.Callback callback) {
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                nextPoint,
                null,
                HttpMethod.GET,
                callback
        );
        request.executeAsync();
    }


    public void getAllPhoto(GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        params.putString("fields", "id, name,images ");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/photos?type=uploaded",
                params,
                HttpMethod.GET,
                callback
        ).executeAsync();
    }

    /**
     * check user logged in
     *
     * @return [true] if logged in [false] when not logged in
     */
    public boolean isLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLogin = false;
        if (accessToken != null) {
            isLogin = !accessToken.isExpired();
        }
        if (isLogin) {
            if (accessToken.getPermissions() != null && accessToken.getPermissions().contains
                    ("public_profile")) {
                isLogin = true;
            } else {
                isLogin = false;
            }
        }
        return isLogin;
    }

    /**
     * share bitmap to fb
     *
     * @param bitmap   bitmap to share
     * @param callback callback
     */
    public void shareBitmap(Bitmap bitmap, FacebookCallback<Sharer.Result> callback) {
        SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(bitmap).build();
        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder().addPhoto
                (sharePhoto).build();
        ShareApi.share(sharePhotoContent, callback);
    }

    public void getVideobyID(String id, GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        params.putString("fields", "videos{picture,length,source,description,title}");
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                "/" + id,
                params,
                HttpMethod.GET,
                callback
        );
        request.executeAsync();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
