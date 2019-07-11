package com.barryirvine.welcomepages.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.barryirvine.welcomepages.R;

public enum WelcomePage {
    PAGE_1(0, R.string.welcome_page_1, R.color.purple),
    PAGE_2(0, R.string.welcome_page_2, R.color.orange),
    PAGE_3(0, R.string.welcome_page_3, R.color.blue);

    @DrawableRes
    private final int mDrawable;
    @StringRes
    private final int mMessage;

    public int getDrawable() {
        return mDrawable;
    }

    public int getMessage() {
        return mMessage;
    }

    public int getColor() {
        return mColor;
    }

    @ColorRes

    private final int mColor;

    WelcomePage(@DrawableRes final int drawable, @StringRes final int message, final int color) {
        mDrawable = drawable;
        mMessage = message;
        mColor = color;
    }
}
