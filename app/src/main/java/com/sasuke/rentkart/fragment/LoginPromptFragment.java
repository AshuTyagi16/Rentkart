package com.sasuke.rentkart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.activity.HomeActivity;
import com.sasuke.rentkart.activity.LoginActivity;
import com.sasuke.rentkart.activity.SignUpActivity;
import com.sasuke.rentkart.manager.PreferenceManager;

import butterknife.OnClick;

/**
 * Created by abc on 4/1/2018.
 */

public class LoginPromptFragment extends BaseFragment {

    public static LoginPromptFragment newInstance() {
        return new LoginPromptFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login_prompt;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceManager.isUserLoggedIn(getActivity()))
            startActivity(HomeActivity.newIntent(getActivity()));
    }

    @OnClick(R.id.btn_login)
    public void openLogicActivity() {
        startActivity(LoginActivity.newIntent(getActivity()));
    }

    @OnClick(R.id.btn_sign_up)
    public void openSignupActivity() {
        startActivity(SignUpActivity.newIntent(getActivity()));
    }

}
