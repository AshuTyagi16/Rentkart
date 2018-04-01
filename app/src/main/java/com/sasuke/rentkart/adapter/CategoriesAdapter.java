package com.sasuke.rentkart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.model.Category;
import com.sasuke.rentkart.view.CategoriesViewHolder;

import java.util.ArrayList;

/**
 * Created by abc on 4/1/2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    private ArrayList<Category> mList;

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_categories, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        holder.setCategory(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mList == null ? 0 : this.mList.size();
    }

    public void setCategories(ArrayList<Category> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
