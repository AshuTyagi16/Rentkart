package com.sasuke.rentkart.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.event.CartItemsUpdatedFromHomeEvent;
import com.sasuke.rentkart.manager.PreferenceManager;
import com.sasuke.rentkart.model.Item;
import com.sasuke.rentkart.network.RentkartApi;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by abc on 4/1/2018.
 */

public class ItemsViewHolder extends RecyclerView.ViewHolder
        implements RentkartApi.OnAddItemListener,
        RentkartApi.OnRemoveItemListener {

    @BindView(R.id.iv_category_thumbnail)
    ImageView mIvCategoryThumbnail;
    @BindView(R.id.iv_add_to_cart)
    ImageView mIvAddToCart;
    @BindView(R.id.pb_add_to_cart)
    ProgressBar mPbAddToCart;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;

    private Item mItem;

    public ItemsViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mIvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItem != null) {
                    mIvAddToCart.setVisibility(View.GONE);
                    mPbAddToCart.setVisibility(View.VISIBLE);
                    int userId = PreferenceManager.getUser(itemView.getContext()).getUserId();
                    if (!mItem.isAddedInCart())
                        RentkartApi.getInstance().addItemToCart(userId, mItem.getItemId(), mItem.getItemPrice(), ItemsViewHolder.this);
                    else
                        RentkartApi.getInstance().removeItemFromCart(userId, mItem.getItemId(), ItemsViewHolder.this);
                }
            }
        });
    }

    public void setItem(Item item) {
        mItem = item;
        Picasso.get()
                .load(item.getItemThumbnail())
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvCategoryThumbnail);
        mTvTitle.setText(item.getItemName());
        mTvPrice.setText("Price - " + String.valueOf(item.getItemPrice()));
        if (!mItem.isAddedInCart())
            Picasso.get().load(R.drawable.ic_add_to_cart).into(mIvAddToCart);
        else
            Picasso.get().load(R.drawable.ic_added_to_cart).into(mIvAddToCart);
    }

    @Override
    public void onAddItemSuccess(ResponseBody responseBody) {
        EventBus.getDefault().postSticky(new CartItemsUpdatedFromHomeEvent());
        mPbAddToCart.setVisibility(View.GONE);
        Picasso.get().load(R.drawable.ic_added_to_cart).into(mIvAddToCart);
        mIvAddToCart.setVisibility(View.VISIBLE);
        Toast.makeText(itemView.getContext(), "Item Added to cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddItemFailure(Throwable t) {
        Toast.makeText(itemView.getContext(), "FAILED : " + t.getMessage(), Toast.LENGTH_SHORT).show();
        mIvAddToCart.setVisibility(View.VISIBLE);
        mPbAddToCart.setVisibility(View.GONE);
    }

    @Override
    public void onRemoveItemSuccess(ResponseBody responseBody) {
        EventBus.getDefault().postSticky(new CartItemsUpdatedFromHomeEvent());
        mPbAddToCart.setVisibility(View.GONE);
        Picasso.get().load(R.drawable.ic_add_to_cart).into(mIvAddToCart);
        mIvAddToCart.setVisibility(View.VISIBLE);
        Toast.makeText(itemView.getContext(), "Item Removed from cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveItemFailure(Throwable t) {
        Toast.makeText(itemView.getContext(), "FAILED : " + t.getMessage(), Toast.LENGTH_SHORT).show();
        mIvAddToCart.setVisibility(View.VISIBLE);
        mPbAddToCart.setVisibility(View.GONE);
    }
}
