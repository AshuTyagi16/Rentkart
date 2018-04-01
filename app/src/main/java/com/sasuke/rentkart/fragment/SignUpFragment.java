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
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.sasuke.rentkart.R;
import com.sasuke.rentkart.activity.LoginActivity;
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

public class SignUpFragment extends BaseFragment {

    @NotEmpty
    @BindView(R.id.et_name)
    EditText_Roboto_Regular etName;
    @NotEmpty
    @BindView(R.id.et_username)
    EditText_Roboto_Regular etUserName;
    @NotEmpty
    @Email
    @BindView(R.id.et_email)
    EditText_Roboto_Regular etEmail;
    @NotEmpty
    @BindView(R.id.et_phone_number)
    EditText_Roboto_Regular etPhoneNumber;
    @NotEmpty
    @Password(min = 6)
    @BindView(R.id.et_password)
    EditText_Roboto_Regular etPassword;
    @NotEmpty
    @ConfirmPassword
    @BindView(R.id.et_confirm_password)
    EditText_Roboto_Regular etConfirmPassword;

    private Validator validator;

    private ProgressDialog progressDialog;
    private SuccessDialog successDialog;
    private FailureDialog failureDialog;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext(), getString(R.string.please_wait), "Sit back & relax", false);
        successDialog = new SuccessDialog(getContext(), getString(R.string.register_success), getString(R.string.yeah));
        failureDialog = new FailureDialog(getContext(), getString(R.string.failed_to_register), getString(R.string.retry));

        validator = new Validator(this);

        validator.setValidationListener(new ValidationListener() {
            @Override
            public Context getContext() {
                return getContext();
            }

            @Override
            public void onValidationFailed(List<ValidationError> list) {
                super.onValidationFailed(list);
                progressDialog.dismissDialog();
            }

            @Override
            public void onValidationSucceeded() {

                String name = getStringFromEditText(etName);
                String username = getStringFromEditText(etUserName);
                String email = getStringFromEditText(etEmail);
                String phoneNumber = getStringFromEditText(etPhoneNumber);
                String password = getStringFromEditText(etPassword);

                RentkartApi.getInstance().registerUser(name, username, email, password, phoneNumber).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressDialog.dismissDialog();
                        successDialog.showDialog();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        progressDialog.dismissDialog();
                        Toast.makeText(getContext(), "FAILED : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        failureDialog.showDialog();
                    }
                });
            }
        });

        successDialog.getInstance().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                startActivity(LoginActivity.newIntent(getActivity()));
                if (getActivity() != null)
                    getActivity().finish();
            }
        });

        failureDialog.getInstance().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.tv_signup)
    public void register() {
        registerUser();
    }

    private void registerUser() {
        progressDialog.showDialog();
        validator.validate();
    }
}
