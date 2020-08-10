package com.m.livedate.mvp.base;

import com.m.livedate.mvp.base.net.ApiException;
import com.m.livedate.mvp.base.net.BaseBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * createDate:2020/8/10
 *
 * @author:spc
 * @describe：
 */
public abstract class MBasePresenter<V extends BaseView> extends BasePresenter<V> {
    public MBasePresenter(V view) {
        super(view);
    }
    /**
     * rx切换到主线程
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T, T> toMainThread() {
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


    /**
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<BaseBean<T>, T> filterData() {
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
                return upstream
                        .filter(new Predicate<BaseBean<T>>() {
                            @Override
                            public boolean test(BaseBean<T> bean) throws Exception {
                                if (bean.getCode().equals("200") && bean.isSuccess()) {
                                    if (null != bean.getResult()) {
                                        return true;
                                    } else {
                                        throw new ApiException("暂无数据");
                                    }
                                } else {
                                    throw new ApiException(bean.getMsg());
                                }
                            }
                        })
                        .map(new Function<BaseBean<T>, T>() {
                            @Override
                            public T apply(BaseBean<T> tBaseBean) throws Exception {
                                return tBaseBean.getResult();
                            }
                        });
            }
        };
    }
}
