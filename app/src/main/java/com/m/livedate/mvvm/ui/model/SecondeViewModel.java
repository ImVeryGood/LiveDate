package com.m.livedate.mvvm.ui.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.m.livedate.mvvm.basic.base.BaseViewModel;
import com.m.livedate.mvvm.basic.retrofit.ApiResponse;
import com.m.livedate.mvvm.ui.bean.PagingBean;
import com.orhanobut.logger.Logger;

/**
 * createDate:2020/7/29
 *
 * @author:spc describe：
 */
public class SecondeViewModel extends BaseViewModel {
    private MutableLiveData<String> stringMutableLiveData;
    private LiveData<ApiResponse<PagingBean.DataBean>> pagLiveData;
    private MutableLiveData<Integer> trigger;
    private int mPage = 1;

    public SecondeViewModel(@NonNull Application application) {
        super(application);
        trigger = new MutableLiveData<>();
        stringMutableLiveData = new MutableLiveData<>();
        pagLiveData = map(mRequestImpl.pagingData(trigger));
    }

    public void getPageData(boolean isLoadMore, Context mContext) {
        showDialog.setValue(true);
        if (isLoadMore) {
            trigger.setValue(mPage++);
        } else {
            mPage = 1;
            trigger.setValue(mPage);
        }

        Logger.d(mPage);
    }

    public LiveData<ApiResponse<PagingBean.DataBean>> returnPageData() {
        return pagLiveData;
    }

    public void setNames(String name) {
        stringMutableLiveData.setValue(name);
    }

    public MutableLiveData<String> getString() {
        return stringMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
