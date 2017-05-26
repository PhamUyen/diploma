package com.uyenpham.diploma.myenglish.fragment.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.adapter.FriendAdapter;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.interfaces.IRecyclerViewListener;
import com.uyenpham.diploma.myenglish.model.chat.UserModel;
import com.uyenpham.diploma.myenglish.utils.FragmentHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Ka on 4/20/2017.
 */

public class ListChatFragment extends BaseFragment implements IRecyclerViewListener{
    @BindView(R.id.rcv_list_chat)
    RecyclerView rcvChat;
    private ArrayList<UserModel> listUser;
    FriendAdapter adapter;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private ArrayList<String> listUserID;
    UserModel me;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_chat;
    }

    @Override
    protected void createView(View view) {
        listUser = new ArrayList<>();
        rcvChat.setLayoutManager(new LinearLayoutManager(mActivity));
         adapter = new FriendAdapter(listUser,mActivity);
        adapter.setListener(this);
        rcvChat.setAdapter(adapter);
        mFirebaseAuth =  FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        me = new UserModel(mFirebaseUser.getDisplayName(), "", mFirebaseUser.getUid() );

        getListUserID();
        getListUserByID();

    }

    private void getListUserID(){
        listUserID = new ArrayList<>();
        DatabaseReference ref =mActivity.mFirebaseDatabaseReference.child("chat").child(me.getId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    UserModel userModel = data.getValue(UserModel.class);
//                    userModel.setId(data.getKey());
                    listUserID.add(data.getKey());
                }
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getListUserByID(){
        for(int i=0; i<listUserID.size(); i++){
            DatabaseReference ref =mActivity.mFirebaseDatabaseReference.child("user").child(listUserID.get(i));
            final int finalI = i;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    userModel.setId(listUserID.get(finalI));
                    listUser.add(userModel);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItem(View view, int postion) {
        UserModel userModel = listUser.get(postion);
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userModel",userModel);
        chatFragment.setArguments(bundle);
        FragmentHelper.replaceFragmentAddToBackStack(chatFragment,mActivity.getSupportFragmentManager() );
    }

    @Override
    public void onLongClickItem(View v, int position) {

    }
}
