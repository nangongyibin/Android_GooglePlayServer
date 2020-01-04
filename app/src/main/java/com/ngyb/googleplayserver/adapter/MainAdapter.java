package com.ngyb.googleplayserver.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ngyb.googleplayserver.factory.FragmentFactory;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/16 22:34
 */
public class MainAdapter extends FragmentPagerAdapter {
    public final String[] titles;

    public MainAdapter(@NonNull FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getFragment(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
