package com.uyenpham.diploma.myenglish.model.chat;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by Ka on 4/20/2017.
 */

public class UserModel implements Serializable {
    private String userID;
    private String name;
    private String photo_profile;

    public UserModel() {
    }

    public UserModel(String name, String photo_profile, String id) {
        this.name = name;
        this.photo_profile = photo_profile;
        this.userID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_profile() {
        return photo_profile;
    }

    public void setPhoto_profile(String photo_profile) {
        this.photo_profile = photo_profile;
    }

    @Exclude
    public String getId() {
        return userID;
    }

    public void setId(String id) {
        this.userID = id;
    }
}
