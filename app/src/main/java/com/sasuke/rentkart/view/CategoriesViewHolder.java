package com.sasuke.rentkart.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.model.Category;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 4/1/2018.
 */

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_category_thumbnail)
    ImageView mIvCategoryThumbnail;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_description)
    TextView mTvDescription;

    public CategoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setCategory(Category category) {
        Picasso.get()
                .load(category.getCategoryThumbnail())
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvCategoryThumbnail);
        mTvTitle.setText(category.getCategoryName());
        mTvDescription.setText(category.getCategoryDescription());
    }
}
