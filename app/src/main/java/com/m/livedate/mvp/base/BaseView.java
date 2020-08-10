package com.m.livedate.mvp.base;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.annotations.NonNull;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：baseView
 */
public interface BaseView {
    /**
     * 绑定生命周期
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLifecycle();

    /**
     * 绑定指定生命周期
     *
     * @param event
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event);

    /**
     * 绑定生命周期
     *
     * @param event
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event);

    /**
     * 获取context
     *
     * @return
     */
    Context getContext();
}
