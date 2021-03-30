package com.m.mvp_kotlin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * createDate:2021/3/30
 * @author:spc
 * @describe：
 */
class MAP : Application() {
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

    companion object {
        lateinit var mContext: Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}