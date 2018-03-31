package com.sasuke.rentkart.activity;

import android.support.v4.app.Fragment;
import com.sasuke.rentkart.fragment.LoginPromptFragment;


public class LoginPromptActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return LoginPromptFragment.newInstance();
    }
}
