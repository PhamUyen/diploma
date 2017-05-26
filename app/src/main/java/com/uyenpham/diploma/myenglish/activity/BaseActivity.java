package com.uyenpham.diploma.myenglish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uyenpham.diploma.myenglish.MyEnglishApplication;
import com.uyenpham.diploma.myenglish.utils.Const;
import com.uyenpham.diploma.myenglish.utils.database.DatabaseHelper;
import com.uyenpham.diploma.myenglish.utils.facebook.FacebookHelper;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    public FacebookHelper mFacebookHelper;
    public Firebase mFirebase;
    FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mFirebaseDatabaseReference;
    public DatabaseHelper mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        getHelper();
        getFirebase();
        initView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void getHelper() {
        mFacebookHelper = MyEnglishApplication.getInstance().getFacebookHelper();
        mDatabase = MyEnglishApplication.getInstance().getDatabase();
    }

    private void getFirebase() {
        mFirebase = MyEnglishApplication.getInstance().getFire();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void showProgressDialog(int messageId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(messageId));
        progressDialog.show();
    }


    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void makeToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show();
    }

    public void goToActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToActivity(Class c, Bundle bundle) {
        Intent intent = new Intent(this, c);
        intent.putExtra(Const.KEY_EXTRA_DATA, bundle);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookHelper.onActivityResult(requestCode, resultCode, data);
    }
}
