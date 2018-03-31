package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
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

public class LoginActivity extends AppCompatActivity implements
        SuccessDialog.OnSuccessDialogListener,
        FailureDialog.OnFailureDialogListener {


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

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this, getString(R.string.please_wait), "Sit back & relax", false);
        successDialog = new SuccessDialog(this, getString(R.string.login_success), getString(R.string.yeah));
        failureDialog = new FailureDialog(this, getString(R.string.login_failed), getString(R.string.retry));

        validator = new Validator(this);

        validator.setValidationListener(new ValidationListener() {
            @Override
            public Context getContext() {
                return LoginActivity.this;
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
                        Toast.makeText(LoginActivity.this, "FAILED : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        failureDialog.showDialog();
                    }
                });
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

    @Override
    public void onFailureConfirmClick(MaterialDialog materialDialog) {
        materialDialog.dismiss();
        loginUser();
    }

    @Override
    public void onSuccessConfirmClick(MaterialDialog materialDialog) {
        materialDialog.dismiss();
        startActivity(MainActivity.newIntent(this));
    }
}
