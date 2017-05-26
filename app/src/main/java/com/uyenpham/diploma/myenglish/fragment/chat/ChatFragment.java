package com.uyenpham.diploma.myenglish.fragment.chat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uyenpham.diploma.myenglish.BuildConfig;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.activity.MainActivity;
import com.uyenpham.diploma.myenglish.activity.SplashActivity;
import com.uyenpham.diploma.myenglish.adapter.ChatFirebaseAdapter;
import com.uyenpham.diploma.myenglish.adapter.ClickListenerChatFirebase;
import com.uyenpham.diploma.myenglish.customviews.CustomNavigationBar;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.model.chat.ChatModel;
import com.uyenpham.diploma.myenglish.model.chat.FileModel;
import com.uyenpham.diploma.myenglish.model.chat.MessageModel;
import com.uyenpham.diploma.myenglish.model.chat.UserModel;
import com.uyenpham.diploma.myenglish.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ka on 4/20/2017.
 */

public class ChatFragment extends BaseFragment implements View.OnClickListener, ClickListenerChatFirebase {
    private static final int IMAGE_GALLERY_REQUEST = 1;
    private static final int IMAGE_CAMERA_REQUEST = 2;

    static final String CHAT_REFERENCE = "chat";

    private static String TAG =ChatFragment.class.getSimpleName();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //CLass Model
    private UserModel me;
    private UserModel friend;
    private ChatModel chatModel;
    private ArrayList<MessageModel> listMessage;
    private ArrayList<ChatModel> listChat;
    private ArrayList<String> listIDChat;
    DatabaseReference ref = null;

    //Views UI
    private RecyclerView rvListMessage;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView btSendMessage,btEmoji;
    private EmojiconEditText edMessage;
    private View contentRoot;
    private EmojIconActions emojIcon;

    //File
    private File filePathImageCamera;
    private MainActivity main;
    private CustomNavigationBar navigationBar;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void createView(View view) {
        bindViews(view);
        getExtra();
        verificaUsuarioLogado();
        initNavigationBar();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.main = (MainActivity) activity;
    }

    private void getExtra(){
        listMessage = new ArrayList<>();
        Bundle bundle = getArguments();
        friend = (UserModel) bundle.getSerializable("userModel");
        chatModel = new ChatModel();
        chatModel.setIdUser(friend.getId());
        chatModel.setMessages(listMessage);
    }
    /**
     * Define base navigationbar
     */
    private void initNavigationBar() {
        navigationBar = main.getNavigationBar();
        navigationBar.reSetAll();
        navigationBar.hideImgright();
        navigationBar.setTitle(friend.getName());
        navigationBar.setBackgroudNavi(R.color.color_tab_video);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        StorageReference storageRef = storage.getReferenceFromUrl(CommonUtils.URL_STORAGE_REFERENCE).child(CommonUtils.FOLDER_STORAGE_IMG);
        if (requestCode == IMAGE_GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    sendFileFirebase(storageRef, selectedImageUri);
                } else {
                    //URI IS NULL
                }
            }
        } else if (requestCode == IMAGE_CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (filePathImageCamera != null && filePathImageCamera.exists()) {
                    StorageReference imageCameraRef = storageRef.child(filePathImageCamera.getName() + "_camera");
                    sendFileFirebase(imageCameraRef, filePathImageCamera);
                } else {
                    //IS NULL
                }
            }
        }
    }

    private void getAllChat(){
        listChat = new ArrayList<>();
        listIDChat = new ArrayList<>();
        DatabaseReference ref1 =mActivity.mFirebaseDatabaseReference.child("chat").child(me.getId());
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    ChatModel chatModel = data.getValue(ChatModel.class);
//                    chatModel.setId(data.getKey());
//                    listChat.add(chatModel);
                    listIDChat.add(data.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Verificar se usuario está logado
     */
    private void verificaUsuarioLogado(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null){
            startActivity(new Intent(mActivity, SplashActivity.class));
            mActivity.finish();
        }else{
            me = new UserModel(mFirebaseUser.getDisplayName(), "", mFirebaseUser.getUid() );
            lerMessagensFirebase();
        }
        getAllChat();
    }

    /**
     * Envia o arvquivo para o firebase
     */
    private void sendFileFirebase(StorageReference storageReference, final Uri file){
        if (storageReference != null){
            final String name = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
            StorageReference imageGalleryRef = storageReference.child(name+"_gallery");
            UploadTask uploadTask = imageGalleryRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"onFailure sendFileFirebase "+e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i(TAG,"onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FileModel fileModel = new FileModel("img",downloadUrl.toString(),name,"");
                    MessageModel chatModel = new MessageModel(me,"", Calendar.getInstance().getTime().getTime()+"",fileModel);
                    mActivity.mFirebaseDatabaseReference.child(CHAT_REFERENCE).push().setValue(chatModel);
                }
            });
        }else{
            //IS NULL
        }

    }
    /**
     * Envia o arvquivo para o firebase
     */
    private void sendFileFirebase(StorageReference storageReference, final File file){
        if (storageReference != null){
            Uri photoURI = FileProvider.getUriForFile(mActivity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
            UploadTask uploadTask = storageReference.putFile(photoURI);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"onFailure sendFileFirebase "+e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i(TAG,"onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FileModel fileModel = new FileModel("img",downloadUrl.toString(),file.getName(),file.length()+"");
                    MessageModel chatModel = new MessageModel(me,"",Calendar.getInstance().getTime().getTime()+"",fileModel);
                    mActivity.mFirebaseDatabaseReference.child(CHAT_REFERENCE).push().setValue(chatModel);
                }
            });
        }else{
            //IS NULL
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mActivity.getMenuInflater().inflate(R.menu.menu_chat, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sendPhoto:
                verifyStoragePermissions();
//                photoCameraIntent();
                break;
            case R.id.sendPhotoGallery:
                photoGalleryIntent();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Enviar foto tirada pela camera
     */
    private void photoCameraIntent(){
        String nomeFoto = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
        filePathImageCamera = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nomeFoto+"camera.jpg");
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoURI = FileProvider.getUriForFile(mActivity,
                BuildConfig.APPLICATION_ID + ".provider",
                filePathImageCamera);
        it.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
        startActivityForResult(it, IMAGE_CAMERA_REQUEST);
    }

    private void photoGalleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
    }

    private void sendMessageFirebase(){
        MessageModel model = new MessageModel(me,edMessage.getText().toString(), Calendar.getInstance().getTime().getTime()+"",null);
        mActivity.mFirebaseDatabaseReference.child("chat").child(me.getId()).child(friend.getId()).push().setValue(model);
        mActivity.mFirebaseDatabaseReference.child("chat").child(friend.getId()).child(me.getId()).push().setValue(model);
        edMessage.setText(null);
    }

    private void lerMessagensFirebase(){
        final ChatFirebaseAdapter firebaseAdapter = new ChatFirebaseAdapter(mActivity.mFirebaseDatabaseReference.child("chat").child(me.getId()).child(friend.getId()),me.getName(),this);
        firebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = firebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    rvListMessage.scrollToPosition(positionStart);
                }
            }
        });
        rvListMessage.setLayoutManager(mLinearLayoutManager);
        rvListMessage.setAdapter(firebaseAdapter);
    }
    private String getListChat(){
        int count  =0;
        String key ="";
        if(listIDChat.size()>0){
            for(int i =0; i<listChat.size(); i++){
                if(!friend.getId().equals(listIDChat.get(i))){
                    count++;
                }
            }
            if(count!= 0){
//                ChatModel chatModel = new ChatModel(friend.getId(),listMessage);
                mActivity.mFirebaseDatabaseReference.child("chat").push().setValue(friend.getId());
//                key =  mActivity.mFirebaseDatabaseReference.child("chat").push().getKey();
            }
        }else {
            mActivity.mFirebaseDatabaseReference.child("chat").push().setValue(friend.getId());
//            ChatModel chatModel = new ChatModel(friend.getId(),listMessage);
//            mActivity.mFirebaseDatabaseReference.child("chat").push().setValue(chatModel);
//            key =  mActivity.mFirebaseDatabaseReference.child("chat").push().getKey();
        }
        return key;
    }
    private void bindViews(View view){
        contentRoot = view.findViewById(R.id.contentRoot);
        edMessage = (EmojiconEditText)view.findViewById(R.id.editTextMessage);
        btSendMessage = (ImageView)view.findViewById(R.id.buttonMessage);
        btSendMessage.setOnClickListener(this);
        btEmoji = (ImageView)view.findViewById(R.id.buttonEmoji);
        emojIcon = new EmojIconActions(mActivity,contentRoot,edMessage,btEmoji);
        emojIcon.ShowEmojIcon();
        rvListMessage = (RecyclerView)view.findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mLinearLayoutManager.setStackFromEnd(true);
    }
    public void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(mActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    mActivity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }else{
            // we already have permission, lets go ahead and call camera intent
            photoCameraIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    photoCameraIntent();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonMessage:
                sendMessageFirebase();
                break;
        }
    }

    @Override
    public void clickImageChat(View view, int position, String nameUser, String urlPhotoUser,
                               String urlPhotoClick) {
    }

    @Override
    public void clickImageMapChat(View view, int position, String latitude, String longitude) {

    }
}
