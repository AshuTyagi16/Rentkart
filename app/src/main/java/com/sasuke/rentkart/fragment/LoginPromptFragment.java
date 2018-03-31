package com.sasuke.rentkart.fragment;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.activity.LoginActivity;
import com.sasuke.rentkart.activity.SignUpActivity;

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

    @OnClick(R.id.btn_login)
    public void openLogicActivity() {
        startActivity(LoginActivity.newIntent(getActivity()));
    }

    @OnClick(R.id.btn_sign_up)
    public void openSignupActivity() {
        startActivity(SignUpActivity.newIntent(getActivity()));
    }

}
