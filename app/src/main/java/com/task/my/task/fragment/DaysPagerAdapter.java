package com.task.my.task.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.task.my.task.MainActivity;

import java.util.List;



public class DaysPagerAdapter extends FragmentStatePagerAdapter {

    private final List<FragmentDays> fragmentList;

    public DaysPagerAdapter(FragmentManager fm, List<FragmentDays> list) {
        super(fm);
        fragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainActivity.monthList.get(position);
    }
}
