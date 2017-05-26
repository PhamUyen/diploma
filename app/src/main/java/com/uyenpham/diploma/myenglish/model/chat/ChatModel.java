package com.uyenpham.diploma.myenglish.model.chat;

import java.util.ArrayList;

/**
 * Created by Ka on 4/20/2017.
 */

public class ChatModel {
    private String id;
    private String idUser;
    private ArrayList<MessageModel> messages;

    public ChatModel() {
    }

    public ChatModel(String idUser, ArrayList<MessageModel> messages) {
        this.idUser = idUser;
        this.messages = messages;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public ArrayList<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageModel> messages) {
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
