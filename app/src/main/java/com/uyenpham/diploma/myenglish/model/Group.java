package com.uyenpham.diploma.myenglish.model;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.uyenpham.diploma.myenglish.customviews.asymeticView.AsymmetricItem;

/**
 * Created by Ka on 4/7/2017.
 */
@SuppressLint("ParcelCreator")
public class Group implements AsymmetricItem {
    private String id;
    private String name;
    private String mean;
    private int image;
    private int columnSpan;
    private int rowSpan;

    public Group() {
    }

    public Group(String id, String name, String mean) {
        this.id = id;
        this.name = name;
        this.mean = mean;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int getColumnSpan() {
        return 0;
    }

    @Override
    public int getRowSpan() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(columnSpan);
        parcel.writeInt(rowSpan);
    }
}
