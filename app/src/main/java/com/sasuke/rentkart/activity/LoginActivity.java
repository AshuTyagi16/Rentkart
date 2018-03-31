package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sasuke.rentkart.fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }

}
