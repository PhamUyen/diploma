package com.uyenpham.diploma.myenglish.adapter;

import android.view.View;

/**
 * Created by Ka on 4/20/2017.
 */

public interface ClickListenerChatFirebase {
    /**
     * Quando houver click na imagem do chat
     * @param view
     * @param position
     */
    void clickImageChat(View view, int position, String nameUser, String urlPhotoUser, String urlPhotoClick);

    /**
     * Quando houver click na imagem de mapa
     * @param view
     * @param position
     */
    void clickImageMapChat(View view, int position,String latitude,String longitude);
}
