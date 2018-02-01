package com.sasuke.rentkart.activity;

/**
 * Created by abc on 2/1/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sasuke.rentkart.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends FragmentActivity {

    private static final String EXTRA_CATEGORY_IMAGE = "image";
    private static final String EXTRA_CATEGORY_NAME = "name";
    private static final String EXTRA_CATEGORY_TAGLINE = "tagline";
    private static final String EXTRA_CATEGORY_DEAL = "deal";

    @BindView(R.id.iv_category)
    ImageView mIvCategory;
    @BindView(R.id.tv_category_name)
    TextView mTvCategoryName;
    @BindView(R.id.tv_category_tagline)
    TextView mTvCategoryTagline;
    @BindView(R.id.tv_category_deal)
    TextView mTvCategoryDeal;
    @BindView(R.id.detail_list_container)
    LinearLayout listContainer;

    private static final int[] imageIds = {R.drawable.head1, R.drawable.head2, R.drawable.head3, R.drawable.head4};

    private int image;
    private String categoryName;
    private String categoryDeal;
    private String categoryTagline;

    public static Intent newIntent(Context context, int image, String categoryName, String categoryTagline, String categoryDeal) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_CATEGORY_IMAGE, image);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        intent.putExtra(EXTRA_CATEGORY_TAGLINE, categoryTagline);
        intent.putExtra(EXTRA_CATEGORY_DEAL, categoryDeal);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setupWindow();

        if (getIntent() != null) {
            image = getIntent().getIntExtra(EXTRA_CATEGORY_IMAGE, 0);
            categoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
            categoryTagline = getIntent().getStringExtra(EXTRA_CATEGORY_TAGLINE);
            categoryDeal = getIntent().getStringExtra(EXTRA_CATEGORY_DEAL);
            setupData();
        }

        setupListView();
    }

    private void setupWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void setupData() {
        Picasso.with(this).load(image).into(mIvCategory);
        mTvCategoryName.setText(categoryName);
        mTvCategoryTagline.setText(categoryTagline);
        mTvCategoryDeal.setText(categoryDeal);
    }

    private void setupListView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < 20; i++) {
            View childView = layoutInflater.inflate(R.layout.detail_list_item, null);
            listContainer.addView(childView);
            ImageView headView = (ImageView) childView.findViewById(R.id.head);
            headView.setImageResource(imageIds[i % imageIds.length]);
        }
    }
}
