package com.barryirvine.welcomepages.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.widget.RadioGroup;

import com.barryirvine.welcomepages.R;
import com.barryirvine.welcomepages.ui.adapter.WelcomePagerAdapter;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final long AUTO_CAROUSEL_DURATION = DateUtils.SECOND_IN_MILLIS * 4;

    private ViewPager mViewPager;
    private WelcomePagerAdapter mAdapter;
    private int mCurrentPage = 1;
    private WelcomeHandler mHandler;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        setUpRadioButtons();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount() - 1);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(mCurrentPage);
        mHandler = new WelcomeHandler(this);
    }

    @Override
    public void onStop() {
        if (mHandler != null) {
            stopFlip();
        }
        mViewPager.clearOnPageChangeListeners();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mHandler != null) {
            startFlip();
        }
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
        if (position == mAdapter.getCount() - 1 && positionOffset == 0.0f) {
            mViewPager.setCurrentItem(1, false);
        } else if (position == 0 && positionOffset == 0.0f) {
            mViewPager.setCurrentItem(mAdapter.getCount() - mAdapter.getExtraPages(), false);
        }
    }

    @Override
    public void onPageSelected(final int position) {
        if (position == 0) {
            mCurrentPage = mAdapter.getCount() - mAdapter.getExtraPages();
        } else if (position < mAdapter.getCount() - 1) {
            mCurrentPage = position;
        } else {
            mCurrentPage = 1;
        }
        mRadioGroup.check(mRadioGroup.getChildAt(mCurrentPage - 1).getId());
        startFlip();
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                stopFlip();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                mViewPager.setCurrentItem(mCurrentPage);
                startFlip();
                break;
        }
    }

    private void startFlip() {
        if (mHandler != null) {
            mHandler.removeMessages(HandlerCode.FLIP);
            mHandler.sendEmptyMessageDelayed(HandlerCode.FLIP, AUTO_CAROUSEL_DURATION);
        }
    }

    private void stopFlip() {
        mHandler.removeMessages(HandlerCode.FLIP);
    }


    private void setUpRadioButtons() {
        for (int i = 0; i < mAdapter.getCount() - mAdapter.getExtraPages(); i++) {
            LayoutInflater.from(this).inflate(R.layout.indicator, mRadioGroup, true);
        }
    }

    private static class WelcomeHandler extends Handler {

        private final WeakReference<WelcomeActivity> mActivity;

        WelcomeHandler(@NonNull final WelcomeActivity welcomeActivity) {
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(welcomeActivity);
        }

        @Override
        public void handleMessage(final Message msg) {
            final WelcomeActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case HandlerCode.FLIP:
                        activity.mViewPager.setCurrentItem(++activity.mCurrentPage, true);
                        break;
                }
            }
        }
    }

    private static class HandlerCode {
        private static final int FLIP = 0;
    }

}
