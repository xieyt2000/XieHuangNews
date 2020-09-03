package com.java.xieyuntong.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;

    public MainViewPagerAdapter(@NonNull FragmentManager fm,String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String type = titles[position];
        return NewsFragment.newInstance(type);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
