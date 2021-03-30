package com.m.mtrylifcle;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * createDate:2021/1/13
 *
 * @author:spc
 * @describe：
 */
public class MViewPager extends ViewPager {
    public MViewPager(@NonNull Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}