package com.sasuke.rentkart.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.rentkart.R;


/**
 * Created by abc on 4/1/2018.
 */

public class FailureDialog {

    private MaterialDialog failureDialog;

    public FailureDialog(Context context, String title, String content, String confirmText) {
        init(context, title, content, confirmText);
    }

    private void init(Context context, String title, String content, String confirmText) {
        failureDialog = new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(ContextCompat.getColor(context, R.color.black))
                .content(content)
                .contentColor(ContextCompat.getColor(context, R.color.notification_action_color_filter))
                .positiveText(confirmText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dismissDialog();
                    }
                })
                .build();
    }

    public MaterialDialog.Builder getInstance() {
        return failureDialog.getBuilder();
    }

    public void showDialog() {
        if (failureDialog != null && !failureDialog.isShowing()) {
            failureDialog.show();
        }
    }

    public void dismissDialog() {
        if (failureDialog != null && failureDialog.isShowing()) {
            failureDialog.dismiss();
        }
    }

}
