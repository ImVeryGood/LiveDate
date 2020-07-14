package com.m.livedate.base;

import android.app.Application;

/**
 * date:2020/7/13
 * describe：
 */
public class MAppLication extends Application {
    private static MAppLication mAppLication;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppLication = this;
    }

    public static MAppLication getMApplication() {
        if (mAppLication == null) {
            mAppLication = new MAppLication();
        }
        return mAppLication;
    }
}
