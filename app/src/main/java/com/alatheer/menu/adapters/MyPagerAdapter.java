package com.alatheer.menu.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by elashry on 10/09/2018.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList;
    List<String> titleList;

    public MyPagerAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> titleList) {
        super(fm);
     this.fragmentList = fragmentList;
        this.titleList = titleList;
    }
    public void addFragment(Fragment fragment)
    {
        fragmentList.add(fragment);
    }

    public void addTitle(String title)
    {
        titleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}


