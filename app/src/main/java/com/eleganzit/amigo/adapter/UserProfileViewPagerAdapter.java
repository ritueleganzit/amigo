package com.eleganzit.amigo.adapter;



import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
