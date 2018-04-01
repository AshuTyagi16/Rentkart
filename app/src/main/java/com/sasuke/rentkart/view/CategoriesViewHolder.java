package com.sasuke.rentkart.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopeer.shadow.ShadowView;
import com.sasuke.rentkart.R;
import com.sasuke.rentkart.activity.ItemsActivity;
import com.sasuke.rentkart.model.Category;
import com.squareup.picasso.Picasso;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 4/1/2018.
 */

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.shadow_view)
    ShadowView mSvContainer;
    @BindView(R.id.iv_category_thumbnail)
    ImageView mIvCategoryThumbnail;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_description)
    TextView mTvDescription;

    private Category mCategory;

    private int[] colors = {0xffff5722, 0xff1875f0, 0xff009688, 0xff123456, 0xFF635D4F};

    public CategoriesViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        int color = colors[getRandom()];
        mSvContainer.setShadowColor(color);
        mSvContainer.setForegroundColor(color);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCategory != null)
                    itemView.getContext().startActivity(ItemsActivity.newIntent(itemView.getContext(), mCategory.getCategoryId(), mCategory.getCategoryName()));
            }
        });
    }

    public void setCategory(Category category) {
        mCategory = category;
        Picasso.get()
                .load(category.getCategoryThumbnail())
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvCategoryThumbnail);
        mTvTitle.setText(category.getCategoryName());
        mTvDescription.setText(category.getCategoryDescription());
    }

    private int getRandom() {
        return new Random().nextInt(5);
    }
}
