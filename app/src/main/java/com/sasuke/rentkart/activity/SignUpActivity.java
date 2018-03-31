package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sasuke.rentkart.fragment.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return SignUpFragment.newInstance();
    }
}
