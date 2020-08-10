package com.m.livedate.mvp.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：BasePresenter
 */
public abstract class BasePresenter<V extends BaseView> {
    protected V mView;
    private WeakReference<V> weakReference;

    public BasePresenter(V view) {
        weakReference = new WeakReference<>(view);
        this.mView = weakReference.get();
        init();
    }


    /**
     * 解除view
     */
    public void detach() {
        mView = null;
        weakReference.clear();
        weakReference = null;
    }

    /**
     * 用于初始化
     */
    protected abstract void init();
}
