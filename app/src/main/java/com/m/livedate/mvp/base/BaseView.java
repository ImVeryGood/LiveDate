package com.m.livedate.mvp.base;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：baseView
 */
public interface BaseView {
    Context mContext();
    /**
     * 绑定生命周期
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLifecycle();
}
