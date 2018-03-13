package com.sasuke.rentkart.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.sasuke.rentkart.R;
import com.sasuke.rentkart.fragment.CommonFragment;
import com.sasuke.rentkart.transformer.CustPagerTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.position_view)
    View positionView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.indicator_tv)
    TextView indicatorTv;

    private FragmentStatePagerAdapter adapter;
    private List<CommonFragment> fragments = new ArrayList<>();

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setipActionBar();
        setupStatusBar();
        fillViewPager();
    }

    private void setipActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    private void fillViewPager() {

        fragments.add(CommonFragment.newInstance(R.drawable.laptops, "Laptops", "Save upto 10% on laptop", "Starts from $2599"));
        fragments.add(CommonFragment.newInstance(R.drawable.ac, "Air Conditioners", "Upto 15% cashback on air conditioners", "Starts from $5499"));
        fragments.add(CommonFragment.newInstance(R.drawable.washing_machine, "Washing Machine", "Save upto 12% on washing machine", "Starts from $3000"));
        fragments.add(CommonFragment.newInstance(R.drawable.kitchen_appliances, "Kitchen Appliances", "Save upto 15% on kitchen appliances", "Starts from $250"));
        fragments.add(CommonFragment.newInstance(R.drawable.computer_accessories, "Computer Accessories", "Save upto 5% on computer accessories", "Starts from $100"));
        fragments.add(CommonFragment.newInstance(R.drawable.camera_lenses, "Camera Lenses", "Flat 10% cashback on all lenses", "Starts from $9999"));
        fragments.add(CommonFragment.newInstance(R.drawable.telivision, "Telivision", "Use code NEW10 to get 10% discount on telivisions", "Starts from $4999"));
        fragments.add(CommonFragment.newInstance(R.drawable.fridge, "Refrigerators", "Save upto 20% on refrigerators", "Starts from $3199"));

        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setPageTransformer(false, new CustPagerTransformer(this));
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateIndicatorTv();
    }

    private void updateIndicatorTv() {
        int totalNum = viewPager.getAdapter().getCount();
        int currentItem = viewPager.getCurrentItem() + 1;
        indicatorTv.setText(Html.fromHtml("<font color='#12edf0'>" + currentItem + "</font>  /  " + totalNum));
    }

    private void setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = statusBarHeight;
            positionView.setLayoutParams(lp);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

}
