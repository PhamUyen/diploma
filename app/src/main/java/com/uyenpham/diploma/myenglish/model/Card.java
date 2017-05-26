package com.uyenpham.diploma.myenglish.model;

/**
 * Created by Ka on 4/9/2017.
 */

public class Card {
    private boolean isFliped;
    private int type;
    private String text;
    private int ID;

    public Card() {
    }

    public Card(boolean isFliped, int type, String text, int ID) {
        this.isFliped = isFliped;
        this.type = type;
        this.text = text;
        this.ID = ID;
    }

    public boolean isFliped() {
        return isFliped;
    }

    public void setFliped(boolean fliped) {
        isFliped = fliped;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
