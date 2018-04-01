package com.sasuke.rentkart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.model.Item;
import com.sasuke.rentkart.view.ItemsViewHolder;

import java.util.ArrayList;

/**
 * Created by abc on 4/1/2018.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    private ArrayList<Item> mList;

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_items, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        holder.setItem(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mList == null ? 0 : this.mList.size();
    }

    public void setItems(ArrayList<Item> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
