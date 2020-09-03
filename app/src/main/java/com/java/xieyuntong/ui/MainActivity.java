package com.java.xieyuntong.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.java.xieyuntong.R;

public class MainActivity extends AppCompatActivity {
    private PagerSlidingTabStrip mPageSlidingTabStrip;
    private ViewPager mViewPager;
    private MainViewPagerAdapter mPagerAdapter;
    private String[] titles = {"1","2","3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPageSlidingTabStrip = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mPagerAdapter = new MainViewPagerAdapter(fragmentManager,titles);
        mViewPager.setAdapter(mPagerAdapter);
        mPageSlidingTabStrip.setViewPager(mViewPager);

    }
}