package com.sasuke.rentkart.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.rentkart.R;


/**
 * Created by abc on 4/1/2018.
 */

public class FailureDialog {

    private MaterialDialog failureDialog;


    public FailureDialog(Context context, String title, String confirmText) {
        init(context, title, confirmText);
    }

    private void init(Context context, String title, String confirmText) {
        failureDialog = new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(ContextCompat.getColor(context, R.color.black))
                .positiveText(confirmText)
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
