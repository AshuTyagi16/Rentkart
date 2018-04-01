package com.sasuke.rentkart.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.rentkart.R;
import com.sasuke.rentkart.adapter.ItemsAdapter;
import com.sasuke.rentkart.dialog.FailureDialog;
import com.sasuke.rentkart.dialog.ProgressDialog;
import com.sasuke.rentkart.model.Item;
import com.sasuke.rentkart.network.RentkartApi;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by abc on 4/1/2018.
 */

public class ItemsFragment extends BaseFragment implements RentkartApi.OnGetItemsForCategoryListener {

    @BindView(R.id.rv_items)
    RecyclerView mRvItems;

    private static final String EXTRA_CATEGORY_ID = "category_id";

    private int categoryId;
    private ItemsAdapter mAdapter;

    private ProgressDialog progressDialog;
    private FailureDialog failureDialog;

    public static ItemsFragment newInstance(int categoryId) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CATEGORY_ID, categoryId);
        ItemsFragment itemsFragment = new ItemsFragment();
        itemsFragment.setArguments(bundle);
        return itemsFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_items;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getActivity(), getResources().getString(R.string.please_wait), "Getting the menu for you..", false);

        if (getArguments() != null) {
            categoryId = getArguments().getInt(EXTRA_CATEGORY_ID);
            mRvItems.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mAdapter = new ItemsAdapter();
            mRvItems.setAdapter(mAdapter);
            getItems();
        }
    }

    private void getItems() {
        RentkartApi.getInstance().getItemsForCategory(categoryId, this);
        progressDialog.showDialog();
    }

    @Override
    public void onGetItemsForCategorySuccess(ArrayList<Item> list) {
        progressDialog.dismissDialog();
        mAdapter.setItems(list);
    }

    @Override
    public void onGetItemsForCategoryFailure(Throwable t) {
        progressDialog.dismissDialog();
        failureDialog = new FailureDialog(getActivity(), "FAILED : " + t.getMessage(), getString(R.string.retry));
        failureDialog.showDialog();

        failureDialog.getInstance().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                getItems();
            }
        });
    }
}
