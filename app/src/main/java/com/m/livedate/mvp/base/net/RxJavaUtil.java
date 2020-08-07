package com.m.livedate.mvp.base.net;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：
 */
public class RxJavaUtil {
    /**
     * 线程调度工作
     *
     * @param observable 被观察者
     * @param <T>        类型
     */
    public static <T> Observable toSubscribe(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
