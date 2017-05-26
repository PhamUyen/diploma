package com.uyenpham.diploma.myenglish.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.interfaces.IDialogTalePhotoListener;


/**
 * Created by Ka on 12/9/2016.
 */

public class DialogUtils {
    /**
     * show dialog errors
     *
     * @param message  message to noti to user
     * @param activity activity show dialog
     * @param title    title of dialog
     */
    public static void showDialogError(String message, Activity activity, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showDialogTakePhoto(Context context, final IDialogTalePhotoListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_take_photo);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.setCancelable(true);

        dialog.findViewById(R.id.btn_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onGalleryClick();
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTakePhotoClick();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
