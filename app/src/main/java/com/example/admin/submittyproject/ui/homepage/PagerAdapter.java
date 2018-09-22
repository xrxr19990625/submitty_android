package com.example.admin.submittyproject.ui.homepage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by admin on 2018/9/13.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> mFragments;
    private List<String> mTitles;
    public PagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<String> titles){
        super(fragmentManager);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position){
        return mFragments.get(position);
    }

    @Override
    public int getCount(){
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTitles.get(position);
    }
}
