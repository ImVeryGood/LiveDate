package com.m.mframe_mvp;

import android.app.Application;
import android.content.Context;

import com.m.mframe_mvp.basic.net.NetConfig;
import com.m.mframe_mvp.basic.net.RetrofitManage;

/**
 * createDate:2020/10/15
 *
 * @author:spc
 * @describe：
 */
public class MApplication extends Application {
    private static MApplication mApplication;
    private String mBaseUrl ="https://wanandroid.com/";
    private static int mConnectTimeOut = 30;
    private static int mReadTimeOut = 30;
    private static int mWriteTimeOut = 30;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        init();
    }

    private void init() {
        RetrofitManage.setNetConfig(new NetConfig() {
            @Override
            public String mBaseUrl() {
                return mBaseUrl;
            }

            @Override
            public long configConnectTimeoutMills() {
                return mConnectTimeOut;
            }

            @Override
            public long configReadTimeoutMills() {
                return mReadTimeOut;
            }

            @Override
            public long configWriteTimeoutMills() {
                return mWriteTimeOut;
            }

            @Override
            public boolean configLogEnable() {
                return false;
            }
        });
    }

    public static MApplication getInstance() {
        if (mApplication == null) {
            mApplication = new MApplication();
        }
        return mApplication;
    }
}