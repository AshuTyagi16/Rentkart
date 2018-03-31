package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.sasuke.rentkart.R;
import com.sasuke.rentkart.customfonts.EditText_Roboto_Regular;
import com.sasuke.rentkart.dialog.FailureDialog;
import com.sasuke.rentkart.dialog.ProgressDialog;
import com.sasuke.rentkart.dialog.SuccessDialog;
import com.sasuke.rentkart.model.User;
import com.sasuke.rentkart.network.RentkartApi;
import com.sasuke.rentkart.util.ValidationListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements
        SuccessDialog.OnSuccessDialogListener,
        FailureDialog.OnFailureDialogListener {


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

    public static Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this, getString(R.string.please_wait), "Sit back & relax", false);
        successDialog = new SuccessDialog(this, getString(R.string.register_success), getString(R.string.yeah));
        failureDialog = new FailureDialog(this, getString(R.string.failed_to_register), getString(R.string.retry));

        validator = new Validator(this);

        validator.setValidationListener(new ValidationListener() {
            @Override
            public Context getContext() {
                return SignUpActivity.this;
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
                        Toast.makeText(SignUpActivity.this, "FAILED : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        failureDialog.showDialog();
                    }
                });


            }
        });
    }

    @OnClick(R.id.tv_signup)
    public void register() {
        registerUser();
    }

    private void registerUser(){
        progressDialog.showDialog();
        validator.validate();
    }

    private String getStringFromEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    @Override
    public void onFailureConfirmClick(MaterialDialog materialDialog) {
        materialDialog.dismiss();
    }

    @Override
    public void onSuccessConfirmClick(MaterialDialog materialDialog) {
        materialDialog.dismiss();
        startActivity(LoginActivity.newIntent(this));
        finish();
    }
}
