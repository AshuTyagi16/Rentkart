package com.sasuke.rentkart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.sasuke.rentkart.R;
import com.sasuke.rentkart.activity.CategoriesActivity;
import com.sasuke.rentkart.activity.MainActivity;
import com.sasuke.rentkart.customfonts.EditText_Roboto_Regular;
import com.sasuke.rentkart.dialog.FailureDialog;
import com.sasuke.rentkart.dialog.ProgressDialog;
import com.sasuke.rentkart.dialog.SuccessDialog;
import com.sasuke.rentkart.model.User;
import com.sasuke.rentkart.network.RentkartApi;
import com.sasuke.rentkart.util.ValidationListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 4/1/2018.
 */

public class LoginFragment extends BaseFragment {

    @NotEmpty
    @Email
    @BindView(R.id.et_email)
    EditText_Roboto_Regular etEmail;
    @NotEmpty
    @Password(min = 6)
    @BindView(R.id.et_password)
    EditText_Roboto_Regular etPassword;

    private Validator validator;

    private ProgressDialog progressDialog;
    private SuccessDialog successDialog;
    private FailureDialog failureDialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity(), getResources().getString(R.string.please_wait), "Sit back & relax", false);
        successDialog = new SuccessDialog(getActivity(), getResources().getString(R.string.login_success), getString(R.string.yeah));
        failureDialog = new FailureDialog(getActivity(), getResources().getString(R.string.login_failed), getString(R.string.retry));

        validator = new Validator(this);

        validator.setValidationListener(new ValidationListener() {
            @Override
            public Context getContext() {
                return getActivity();
            }

            @Override
            public void onValidationFailed(List<ValidationError> list) {
                super.onValidationFailed(list);
                progressDialog.dismissDialog();
            }

            @Override
            public void onValidationSucceeded() {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                RentkartApi.getInstance().loginUser(email, password).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressDialog.dismissDialog();
                        successDialog.showDialog();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        progressDialog.dismissDialog();
                        Toast.makeText(getActivity(), "FAILED : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        failureDialog.showDialog();
                    }
                });
            }
        });

        successDialog.getInstance().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                startActivity(CategoriesActivity.newIntent(getActivity()));
            }
        });

        failureDialog.getInstance().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.tv_signin)
    public void login() {
        loginUser();
    }

    private void loginUser() {
        progressDialog.showDialog();
        validator.validate();
    }
}
