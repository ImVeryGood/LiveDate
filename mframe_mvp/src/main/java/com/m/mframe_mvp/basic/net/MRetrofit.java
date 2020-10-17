package com.m.mframe_mvp.basic.net;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * createDate:2020/10/15
 *
 * @author:spc
 * @describe：
 */
public class MRetrofit {

    public static ApiServer getInstance() {
        return RetrofitManage.createRetrofit().create(ApiServer.class);
    }

    /**
     * rx切换到主线程
     *
     * @param <T>
     * @return
     */
    public static  <T> ObservableTransformer<T, T> toMainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
} 