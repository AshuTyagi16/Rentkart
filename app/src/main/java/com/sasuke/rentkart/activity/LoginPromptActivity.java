package com.sasuke.rentkart.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.sasuke.rentkart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPromptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prompt);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void openLogicActivity() {
        startActivity(LoginActivity.newIntent(this));
    }

    @OnClick(R.id.btn_sign_up)
    public void openSignupActivity() {
        startActivity(SignUpActivity.newIntent(this));
    }
}
