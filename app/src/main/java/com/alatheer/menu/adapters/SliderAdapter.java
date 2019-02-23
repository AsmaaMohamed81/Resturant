package com.alatheer.menu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elashry on 15/09/2018.
 */

public class SliderAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;
    public SliderAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
    }


    public void AddFragment(Fragment fragment)
    {
        fragmentList.add(fragment);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
