package com.uyenpham.diploma.myenglish;

import android.app.Application;

import com.firebase.client.Firebase;
import com.uyenpham.diploma.myenglish.utils.database.DatabaseHelper;
import com.uyenpham.diploma.myenglish.utils.facebook.FacebookHelper;

import static com.uyenpham.diploma.myenglish.utils.Const.FIREBASE_URL;


public class MyEnglishApplication extends Application {
    private FacebookHelper facebookHelper;

    public String deviceId;
    public Firebase firebase;
    private DatabaseHelper myDatabase;

    public static MyEnglishApplication INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initSocialHelper();
        initFirebase();
        initDatabse();
    }

    private void initSocialHelper() {
        facebookHelper = FacebookHelper.getInstance(this);
    }

    public FacebookHelper getFacebookHelper() {
        return facebookHelper;
    }

    public void setFacebookHelper(FacebookHelper facebookHelper) {
        this.facebookHelper = facebookHelper;
    }

    public Firebase getFire() {
        return firebase;
    }

    public DatabaseHelper getDatabase() {
        return myDatabase;
    }

    public static MyEnglishApplication getInstance() {
        return INSTANCE;
    }


    public String getDeviceId() {
        return deviceId;
    }

    private void initFirebase(){
        Firebase.setAndroidContext(this);
        firebase = new Firebase(FIREBASE_URL);
    }
    private void initDatabse(){
         myDatabase= new DatabaseHelper(this);
    }
}
