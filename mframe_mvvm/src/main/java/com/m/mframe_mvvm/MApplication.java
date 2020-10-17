package com.m.mframe_mvvm;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

/**
 * createDate:2020/10/17
 *
 * @author:spc
 * @describe：
 */
public class MApplication extends Application {
    private static MApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static MApplication getInstance() {
        if (mApplication == null) {
            mApplication = new MApplication();
        }
        return mApplication;
    }
}