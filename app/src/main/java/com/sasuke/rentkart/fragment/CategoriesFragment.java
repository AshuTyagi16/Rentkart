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
import com.sasuke.rentkart.adapter.CategoriesAdapter;
import com.sasuke.rentkart.dialog.FailureDialog;
import com.sasuke.rentkart.dialog.ProgressDialog;
import com.sasuke.rentkart.model.Category;
import com.sasuke.rentkart.network.RentkartApi;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by abc on 4/1/2018.
 */

public class CategoriesFragment extends BaseFragment implements RentkartApi.OnGetCategoriesListener {

    @BindView(R.id.rv_categories)
    RecyclerView mRvCategories;

    private CategoriesAdapter mAdapter;

    private ProgressDialog progressDialog;
    private FailureDialog failureDialog;

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_categories;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getActivity(), getResources().getString(R.string.please_wait), "Getting the menu for you..", false);

        mRvCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CategoriesAdapter();
        mRvCategories.setAdapter(mAdapter);
        getCategories();
    }

    private void getCategories() {
        RentkartApi.getInstance().getCategories(this);
        progressDialog.showDialog();
    }

    @Override
    public void onGetCategoriesSuccess(ArrayList<Category> list) {
        progressDialog.dismissDialog();
        mAdapter.setCategories(list);
    }

    @Override
    public void onGetCategoriesFailure(Throwable t) {
        progressDialog.dismissDialog();
        failureDialog = new FailureDialog(getActivity(), "FAILED : " + t.getMessage(), getString(R.string.retry));
        failureDialog.showDialog();

        failureDialog.getInstance().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                getCategories();
            }
        });
    }
}
