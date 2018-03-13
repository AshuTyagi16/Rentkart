package com.sasuke.rentkart.dialog;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by abc on 3/13/2018.
 */

public class ProgressDialog {

    private SweetAlertDialog dialog;

    public ProgressDialog(Context context, String progressMessage) {
        init(context, progressMessage);
    }

    private void init(Context context, String message) {
        dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText(message);
        dialog.setCancelable(false);
    }

    public void showDialog() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
