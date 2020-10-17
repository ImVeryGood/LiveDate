package com.m.mframe_mvp.basic.mvp;

import java.lang.ref.WeakReference;

/**
 * createDate:2020/10/16
 *
 * @author:spc
 * @describe：
 */
public abstract class BasePresenter<V extends BaseView> {
    protected V mView;
    private WeakReference<V> weakReference;

    public BasePresenter(V mView) {
        weakReference = new WeakReference<>(mView);
        this.mView = weakReference.get();
    }

    public void detach() {
        mView = null;
        weakReference.clear();
        weakReference = null;
    }
}