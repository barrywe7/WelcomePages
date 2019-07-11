package com.barryirvine.welcomepages.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.barryirvine.welcomepages.model.WelcomePage;
import com.barryirvine.welcomepages.ui.fragment.WelcomePageFragment;

public class WelcomePagerAdapter extends FragmentStatePagerAdapter {

    private static final int EXTRA_PAGES = 2;
    private final int mNumPages;

    public WelcomePagerAdapter(@NonNull final FragmentManager fm) {
        super(fm);
        mNumPages = WelcomePage.values().length + EXTRA_PAGES;
    }

    @Override
    public Fragment getItem(final int position) {
        if (position == mNumPages - 1) {
            //We've reached the end of the view pager so show the first page
            return WelcomePageFragment.newInstance(1);
        } else if (position == 0) {
            //We've reached the start of the view pager so show the last page
            return WelcomePageFragment.newInstance(mNumPages - EXTRA_PAGES);
        } else {
            return WelcomePageFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return mNumPages;
    }

    public int getExtraPages() {
        return EXTRA_PAGES;
    }

}
