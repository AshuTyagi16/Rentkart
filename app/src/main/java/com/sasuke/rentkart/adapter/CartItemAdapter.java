package com.sasuke.rentkart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.model.CartItem;
import com.sasuke.rentkart.view.CartItemViewHolder;

import java.util.ArrayList;

/**
 * Created by abc on 4/1/2018.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemViewHolder> {

    private ArrayList<CartItem> mList;

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_cart_item, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder holder, int position) {
        holder.setCartItem(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mList == null ? 0 : this.mList.size();
    }

    public void setCartItems(ArrayList<CartItem> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
