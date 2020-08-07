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
    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable compositeDisposable;

    public BasePresenter(V view) {
        weakReference = new WeakReference<>(view);
        this.mView = weakReference.get();
    }


    /**
     * 解除view
     */
    public void detach() {
        mView = null;
        weakReference.clear();
        weakReference = null;
        // 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    /**
     * 将Disposable添加,在每次网络访问之前初始化时进行添加操作
     *
     * @param subscription
     */
    public void addDisposable(Disposable subscription) {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(subscription);
    }

}
