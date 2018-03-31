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

    private OnFailureDialogListener onFailureDialogListener;

    public FailureDialog(Context context, String title, String confirmText) {
        init(context, title, confirmText);
        onFailureDialogListener = (OnFailureDialogListener) context;
    }

    private void init(Context context, String title, String confirmText) {
        failureDialog = new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(ContextCompat.getColor(context, R.color.black))
                .positiveText(confirmText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onFailureDialogListener.onFailureConfirmClick(dialog);
                    }
                })
                .build();
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

    public interface OnFailureDialogListener {
        void onFailureConfirmClick(MaterialDialog sweetAlertDialog);
    }
}
