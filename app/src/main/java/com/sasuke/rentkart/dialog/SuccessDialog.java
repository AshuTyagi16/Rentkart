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

public class SuccessDialog {

    private MaterialDialog successDialog;

    private OnSuccessDialogListener onSuccessDialogListener;

    public SuccessDialog(Context context, String title, String confirmText) {
        init(context, title, confirmText);
        onSuccessDialogListener = (OnSuccessDialogListener) context;
    }

    private void init(Context context, String title, String confirmText) {
        successDialog = new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(ContextCompat.getColor(context, R.color.black))
                .positiveText(confirmText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onSuccessDialogListener.onSuccessConfirmClick(dialog);
                    }
                })
                .build();
    }

    public void showDialog() {
        if (successDialog != null && !successDialog.isShowing()) {
            successDialog.show();
        }
    }

    public void dismissDialog() {
        if (successDialog != null && successDialog.isShowing()) {
            successDialog.dismiss();
        }
    }

    public interface OnSuccessDialogListener {
        void onSuccessConfirmClick(MaterialDialog sweetAlertDialog);
    }
}
