package com.sasuke.rentkart.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.model.Item;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 4/1/2018.
 */

public class ItemsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_category_thumbnail)
    ImageView mIvCategoryThumbnail;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_description)
    TextView mTvDescription;

    public ItemsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setItem(Item item) {
        Picasso.get()
                .load(item.getItemThumbnail())
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvCategoryThumbnail);
        mTvTitle.setText(item.getItemName());
        mTvDescription.setText(item.getItemDescription());
    }
}
