package com.m.mframe_mvp.basic.net;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * date:2019/1/2
 */
public abstract class MObserver<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        success(t);
    }

    @Override
    public void onError(Throwable e) {
        String exception = ExceptionHelper.handleException(e);
        error(exception);
    }

    @Override
    public void onComplete() {
    }

    protected abstract void success(T t);

    protected abstract void error(String error);


}
