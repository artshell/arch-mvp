package com.artshell.arch.mvp.lazyload;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author artshell on 2018/5/29
 */
public class LazyLoadingPager extends FragmentStatePagerAdapter {
    String[] titles = {"Index", "Discover", "Article"};

    public LazyLoadingPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LazyIndexFragment.newInstance();
            case 1:
                return LazyDiscoverFragment.newInstance();
            default:
                return LazyArticleFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
