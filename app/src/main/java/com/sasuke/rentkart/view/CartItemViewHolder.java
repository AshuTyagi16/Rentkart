package com.sasuke.rentkart.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.event.CartItemsUpdatedFromCartEvent;
import com.sasuke.rentkart.manager.PreferenceManager;
import com.sasuke.rentkart.model.CartItem;
import com.sasuke.rentkart.network.RentkartApi;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by abc on 4/1/2018.
 */

public class CartItemViewHolder extends RecyclerView.ViewHolder implements RentkartApi.OnRemoveItemListener {

    @BindView(R.id.iv_category_thumbnail)
    ImageView mIvThumbnail;
    @BindView(R.id.tv_title)
    TextView mTvName;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_quantity)
    TextView mTvQuantity;
    @BindView(R.id.iv_remove_item_from_cart)
    ImageView mIvRemove;
    @BindView(R.id.pb_remove_from_cart)
    ProgressBar mPbRemove;

    private CartItem cartItem;

    public CartItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mIvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItem != null) {
                    mIvRemove.setVisibility(View.GONE);
                    mPbRemove.setVisibility(View.VISIBLE);
                    RentkartApi.getInstance().removeItemFromCart(PreferenceManager.getUser(itemView.getContext()).getUserId(), cartItem.getItemId(), CartItemViewHolder.this);
                }
            }
        });
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
        Picasso.get()
                .load(cartItem.getItemThumbnail())
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvThumbnail);
        mTvName.setText(cartItem.getItemName());
        int quantity = cartItem.getQuantity();
        int totalPrice = quantity * cartItem.getPrice();
        mTvPrice.setText("Price - " + String.valueOf(totalPrice));
        mTvQuantity.setText("Quantity - " + String.valueOf(quantity));
    }

    @Override
    public void onRemoveItemSuccess(ResponseBody responseBody) {
        EventBus.getDefault().postSticky(new CartItemsUpdatedFromCartEvent());
        mIvRemove.setVisibility(View.VISIBLE);
        mPbRemove.setVisibility(View.GONE);
        Toast.makeText(itemView.getContext(), "Item Removed from cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveItemFailure(Throwable t) {
        Toast.makeText(itemView.getContext(), "FAILED : " + t.getMessage(), Toast.LENGTH_SHORT).show();
        mIvRemove.setVisibility(View.VISIBLE);
        mPbRemove.setVisibility(View.GONE);
    }
}
