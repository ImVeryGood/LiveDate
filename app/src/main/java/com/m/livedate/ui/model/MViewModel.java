package com.m.livedate.ui.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.m.livedate.base.BaseViewModel;
import com.m.livedate.retrofit.ApiResponse;
import com.m.livedate.retrofit.ListBean;

import java.util.List;

/**
 * date:2020/7/9
 * describe：
 */
public class MViewModel extends BaseViewModel {
    private LiveData<ApiResponse<List<ListBean.DataBean>>> mutableLiveData;
    private MutableLiveData<Boolean> trigger;

    public MViewModel(@NonNull Application application) {
        super(application);
        trigger = new MutableLiveData<>();
        mutableLiveData = map(mRequestImpl.checkVersion(trigger));
    }


    public void getData() {
        trigger.setValue(true);
    }

    public LiveData<ApiResponse<List<ListBean.DataBean>>> getListBeanData() {
        return mutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
