package com.m.livedate;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

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
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static MAppLication getMApplication() {
        if (mAppLication == null) {
            mAppLication = new MAppLication();
        }
        return mAppLication;
    }
}
