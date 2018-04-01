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

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.fragment.CategoriesFragment;
import com.sasuke.rentkart.fragment.HomeFragment;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 4/1/2018.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

        replaceFragment(HomeFragment.newInstance(), R.id.container, "Rentkart");

        slidingRootNav.getLayout().findViewById(R.id.tv_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                replaceFragment(HomeFragment.newInstance(), R.id.container, "Rentkart");
            }
        });

        slidingRootNav.getLayout().findViewById(R.id.tv_categories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                replaceFragment(CategoriesFragment.newInstance(), R.id.container, "Categories");
            }
        });

    }

    private void replaceFragment(Fragment fragment, int containerId, String name) {
        toolbar.setTitle(name);
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
            ft.add(containerId, f, tag);
        } else {
            ft.show(f);
        }
        try {
            ft.commit();
        } catch (IllegalStateException e) {
        } catch (Exception e) {
        }
    }
}
