package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sasuke.rentkart.fragment.ItemsFragment;

/**
 * Created by abc on 4/1/2018.
 */

public class ItemsActivity extends SingleFragmentActivity {

    private static final String EXTRA_CATEGORY_ID = "category_id";

    private int categoryId;

    public static Intent newIntent(Context context, int categoryId) {
        Intent intent = new Intent(context, ItemsActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().hasExtra(EXTRA_CATEGORY_ID))
            categoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, 0);
    }

    @Override
    protected Fragment createFragment() {
        return ItemsFragment.newInstance(categoryId);
    }
}
