package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.fragment.CartFragment;
import com.sasuke.rentkart.fragment.CategoriesFragment;
import com.sasuke.rentkart.fragment.HomeFragment;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by abc on 4/1/2018.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private SlidingRootNav slidingRootNav;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        replaceFragment(HomeFragment.newInstance(), "Rentkart");

        slidingRootNav.getLayout().findViewById(R.id.tv_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                replaceFragment(HomeFragment.newInstance(), "Rentkart");
            }
        });

        slidingRootNav.getLayout().findViewById(R.id.tv_categories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                replaceFragment(CategoriesFragment.newInstance(), "Categories");
            }
        });

        slidingRootNav.getLayout().findViewById(R.id.tv_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                replaceFragment(CartFragment.newInstance(), "Cart");
            }
        });

    }

    private void replaceFragment(Fragment fragment, String name) {
        mTvTitle.setText(name);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f != null) {
                    ft.hide(f);
                }
            }
        }
        String tag = fragment.getClass().getName();
        Fragment f = fm.findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            ft.add(R.id.container, f, tag);
        } else {
            ft.show(f);
        }
        try {
            ft.commit();
        } catch (IllegalStateException e) {
        } catch (Exception e) {
        }
    }


    @Override
    public void onBackPressed() {
        if (!getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName()).isVisible())
            replaceFragment(HomeFragment.newInstance(), "Rentkart");
        else
            super.onBackPressed();
    }

    @OnClick(R.id.iv_cart)
    public void openCart() {
        replaceFragment(CartFragment.newInstance(), "Cart");
    }
}
