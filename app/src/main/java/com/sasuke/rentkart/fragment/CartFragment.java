package com.sasuke.rentkart.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.rentkart.R;
import com.sasuke.rentkart.adapter.CartItemAdapter;
import com.sasuke.rentkart.dialog.FailureDialog;
import com.sasuke.rentkart.dialog.ProgressDialog;
import com.sasuke.rentkart.event.CartItemsUpdatedFromCartEvent;
import com.sasuke.rentkart.event.CartItemsUpdatedFromHomeEvent;
import com.sasuke.rentkart.manager.PreferenceManager;
import com.sasuke.rentkart.model.CartItem;
import com.sasuke.rentkart.network.RentkartApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by abc on 4/1/2018.
 */

public class CartFragment extends BaseFragment implements RentkartApi.OnGetUserCartListener {

    @BindView(R.id.rv_cart_items)
    RecyclerView mRvItems;

    private CartItemAdapter mAdapter;

    private ProgressDialog progressDialog;
    private FailureDialog failureDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart_items;
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity(), getResources().getString(R.string.please_wait), "Getting the items in your cart..", false);

        mRvItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CartItemAdapter();
        mRvItems.setAdapter(mAdapter);
        getItems();
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

    private void getItems() {
        RentkartApi.getInstance().getUserCart(PreferenceManager.getUser(getActivity()).getUserId(), this);
        progressDialog.showDialog();
    }

    @Override
    public void onGetUserCartSuccess(ArrayList<CartItem> list) {
        progressDialog.dismissDialog();
        mAdapter.setCartItems(list);
    }

    @Override
    public void onGetUserCartFailure(Throwable t) {
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
    public void onCartUpdatedEvent(CartItemsUpdatedFromHomeEvent event) {
        getItems();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCartUpdatedEvent(CartItemsUpdatedFromCartEvent event) {
        getItems();
    }
}
