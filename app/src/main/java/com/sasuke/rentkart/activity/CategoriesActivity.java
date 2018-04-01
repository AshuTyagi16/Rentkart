package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sasuke.rentkart.fragment.CategoriesFragment;

/**
 * Created by abc on 4/1/2018.
 */

public class CategoriesActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, CategoriesActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return CategoriesFragment.newInstance();
    }
}
