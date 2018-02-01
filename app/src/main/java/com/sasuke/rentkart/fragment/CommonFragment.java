package com.sasuke.rentkart.fragment;

/**
 * Created by abc on 2/1/2018.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasuke.rentkart.activity.DetailActivity;
import com.sasuke.rentkart.R;
import com.sasuke.rentkart.customview.DragLayout;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonFragment extends Fragment implements DragLayout.GotoDetailListener {

    @BindView(R.id.iv_category)
    ImageView mIvCategory;
    @BindView(R.id.tv_category_name)
    TextView mTvCategoryName;
    @BindView(R.id.tv_category_tagline)
    TextView mTvCategoryTagline;
    @BindView(R.id.tv_category_deal)
    TextView mTvCategoryDeal;
    @BindView(R.id.drag_layout)
    DragLayout mDragLayout;

    private static final String EXTRA_CATEGORY_IMAGE = "image";
    private static final String EXTRA_CATEGORY_NAME = "name";
    private static final String EXTRA_CATEGORY_TAGLINE = "tagline";
    private static final String EXTRA_CATEGORY_DEAL = "deal";

    private int image;
    private String categoryName;
    private String categoryDeal;
    private String categoryTagline;

    public static CommonFragment newInstance(int categoryImage, String categoryName, String categoryTagline, String categoryDeal) {
        CommonFragment fragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CATEGORY_IMAGE, categoryImage);
        bundle.putString(EXTRA_CATEGORY_NAME, categoryName);
        bundle.putString(EXTRA_CATEGORY_TAGLINE, categoryTagline);
        bundle.putString(EXTRA_CATEGORY_DEAL, categoryDeal);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image = getArguments().getInt(EXTRA_CATEGORY_IMAGE);
            categoryName = getArguments().getString(EXTRA_CATEGORY_NAME);
            categoryTagline = getArguments().getString(EXTRA_CATEGORY_TAGLINE);
            categoryDeal = getArguments().getString(EXTRA_CATEGORY_DEAL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupData();
        mDragLayout.setGotoDetailListener(this);
    }

    private void setupData() {
        Picasso.with(getContext()).load(image).into(mIvCategory);
        mTvCategoryName.setText(categoryName);
        mTvCategoryTagline.setText(categoryTagline);
        mTvCategoryDeal.setText(categoryDeal);
    }

    @Override
    public void gotoDetail() {
        ActivityCompat.startActivity(getActivity(), DetailActivity.newIntent(getActivity(), image, categoryName, categoryTagline, categoryDeal), null);
    }
}
