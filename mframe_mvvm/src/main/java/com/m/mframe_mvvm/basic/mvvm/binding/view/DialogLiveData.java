package com.m.mframe_mvvm.basic.mvvm.binding.view;

import androidx.lifecycle.LiveData;


public final class DialogLiveData<T> extends LiveData<T> {

    private DialogBean bean = new DialogBean();

    public void setValue(boolean isShow) {
        bean.setShow(isShow);
        bean.setMsg("");
        postValue((T) bean);
    }
    public void setValue(boolean isShow, String msg) {
        bean.setShow(isShow);
        bean.setMsg(msg);
        postValue((T) bean);
    }
}