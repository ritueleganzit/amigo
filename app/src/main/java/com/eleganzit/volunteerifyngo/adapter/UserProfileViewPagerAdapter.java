package com.eleganzit.volunteerifyngo.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class UserProfileViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> titles=new ArrayList<>();
    ArrayList<Fragment> fragments=new ArrayList<>();

    public UserProfileViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(String title,Fragment fragment)
    {
        fragments.add(fragment);
        titles.add(title);
    }

    @Override
    public Fragment getItem(int i) {

        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
