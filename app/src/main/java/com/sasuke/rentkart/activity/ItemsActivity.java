package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.sasuke.rentkart.fragment.ItemsFragment;

/**
 * Created by abc on 4/1/2018.
 */

public class ItemsActivity extends SingleFragmentActivity {

    private static final String EXTRA_CATEGORY_ID = "category_id";
    private static final String EXTRA_CATEGORY_NAME = "category_name";

    private int categoryId;
    private String categoryName;

    public static Intent newIntent(Context context, int categoryId, String categoryName) {
        Intent intent = new Intent(context, ItemsActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            categoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, 0);
            categoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return ItemsFragment.newInstance(categoryId, categoryName);
    }
}
