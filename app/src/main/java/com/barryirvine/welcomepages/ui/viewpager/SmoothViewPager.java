package com.barryirvine.welcomepages.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * The default view pager doesn't show the page transformations when you move between pages programmatically.
 * This overrides the startScroll so that the transformation takes place correctly.
 */
public class SmoothViewPager extends ViewPager {

    private static final int DEFAULT_SCROLL_DURATION = 200;
    private static final float OVERSHOOT_TENSION = 1.0f;

    public SmoothViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    private void setMyScroller() {
        try {
            final Class<?> viewpager = ViewPager.class;
            final Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (final Exception e) {
            //Noop
        }
    }

    private class MyScroller extends Scroller {
        MyScroller(final Context context) {
            //super(context, new AccelerateDecelerateInterpolator());
             super(context, new OvershootInterpolator(OVERSHOOT_TENSION));
        }

        @Override
        public final void startScroll(final int startX, final int startY, final int dx, final int dy, final int duration) {
            final int scrollDuration = duration == DEFAULT_SCROLL_DURATION ? (int) DateUtils.SECOND_IN_MILLIS : duration;
            super.startScroll(startX, startY, dx, dy, scrollDuration);
        }
    }
}