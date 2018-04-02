package com.sasuke.rentkart.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.rentkart.R;
import com.sasuke.rentkart.adapter.ItemsAdapter;
import com.sasuke.rentkart.dialog.FailureDialog;
import com.sasuke.rentkart.dialog.ProgressDialog;
import com.sasuke.rentkart.event.CartItemsUpdatedFromCartEvent;
import com.sasuke.rentkart.event.CartItemsUpdatedFromHomeEvent;
import com.sasuke.rentkart.manager.PreferenceManager;
import com.sasuke.rentkart.model.Item;
import com.sasuke.rentkart.network.RentkartApi;
import com.sasuke.rentkart.util.ItemDecorator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by abc on 4/1/2018.
 */

public class ItemsFragment extends BaseFragment implements RentkartApi.OnGetItemsForCategoryListener {

    @BindView(R.id.rv_items)
    RecyclerView mRvItems;
    @BindView(R.id.tv_category_name)
    TextView mTvCategoryName;

    private static final String EXTRA_CATEGORY_ID = "category_id";
    private static final String EXTRA_CATEGORY_NAME = "category_name";

    private int categoryId;
    private String categoryName;
    private ItemsAdapter mAdapter;

    private ProgressDialog progressDialog;
    private FailureDialog failureDialog;

    public static ItemsFragment newInstance(int categoryId, String categoryName) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CATEGORY_ID, categoryId);
        bundle.putString(EXTRA_CATEGORY_NAME, categoryName);
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
            categoryName = getArguments().getString(EXTRA_CATEGORY_NAME);
            mTvCategoryName.setText(categoryName);
            mRvItems.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRvItems.addItemDecoration(new ItemDecorator(
                    getResources().getDimensionPixelSize(R.dimen.item_list_spacing),
                    100));
            mAdapter = new ItemsAdapter();
            mRvItems.setAdapter(mAdapter);
            getItems();
        }
    }


    @OnClick(R.id.ll_back)
    public void finishActivity() {
        if (getActivity() != null)
            getActivity().finish();
    }

    private void getItems() {
        RentkartApi.getInstance().getItemsForCategory(PreferenceManager.getUser(getActivity()).getUserId(), categoryId, this);
        progressDialog.showDialog();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onGetItemsForCategorySuccess(ArrayList<Item> list) {
        progressDialog.dismissDialog();
        mAdapter.setItems(list);
    }

    @Override
    public void onGetItemsForCategoryFailure(Throwable t) {
        progressDialog.dismissDialog();
        failureDialog = new FailureDialog(getActivity(), "FAILED : " + t.getMessage(), "", getString(R.string.retry));
        failureDialog.showDialog();

        failureDialog.getInstance().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                getItems();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCartUpdatedEvent(CartItemsUpdatedFromCartEvent event) {
        getItems();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCartUpdatedEvent(CartItemsUpdatedFromHomeEvent event) {
        getItems();
    }
}
