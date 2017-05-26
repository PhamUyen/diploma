package com.uyenpham.diploma.myenglish.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ka on 4/7/2017.
 */

public class Vocabulary implements Parcelable{
    private String groupID;
    private int ID;
    private String word;
    private String mean;
    private String image;
    private String example;
    private String explain;
    private String pronunciation;
    private int like;

    public Vocabulary(String groupID, int ID, String word, String mean, int like, String image, String
            example, String explain, String pronunciation) {
        this.groupID = groupID;
        this.ID = ID;
        this.word = word;
        this.mean = mean;
        this.image = image;
        this.example = example;
        this.explain = explain;
        this.pronunciation = pronunciation;
        this.like = like;
    }

    public Vocabulary() {
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupID);
        dest.writeInt(this.ID);
        dest.writeString(this.word);
        dest.writeString(this.mean);
        dest.writeString(this.image);
        dest.writeString(this.example);
        dest.writeString(this.explain);
        dest.writeString(this.pronunciation);
        dest.writeInt(this.like);
    }

    protected Vocabulary(Parcel in) {
        this.groupID = in.readString();
        this.ID = in.readInt();
        this.word = in.readString();
        this.mean = in.readString();
        this.image = in.readString();
        this.example = in.readString();
        this.explain = in.readString();
        this.pronunciation = in.readString();
        this.like = in.readInt();
    }

    public static final Creator<Vocabulary> CREATOR = new Creator<Vocabulary>() {
        @Override
        public Vocabulary createFromParcel(Parcel source) {
            return new Vocabulary(source);
        }

        @Override
        public Vocabulary[] newArray(int size) {
            return new Vocabulary[size];
        }
    };
}
