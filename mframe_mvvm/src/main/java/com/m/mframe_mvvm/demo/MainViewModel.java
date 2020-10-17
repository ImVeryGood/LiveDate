package com.m.mframe_mvvm.demo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.m.mframe_mvvm.basic.mvvm.butterknife.BaseViewModel;
import com.m.mframe_mvvm.basic.retrofit.ApiResponse;

import java.util.List;

/**
 * createDate:2020/10/17
 *
 * @author:spc
 * @describe：
 */
public class MainViewModel extends BaseViewModel {
    private LiveData<ApiResponse<List<ListBean.DataBean>>> mutableLiveData;
    private MutableLiveData<Boolean> trigger;

    public MainViewModel(@NonNull Application application) {
        super(application);
        trigger = new MutableLiveData<>();
        mutableLiveData = map(mRequestImpl.checkVersion(trigger));
    }

    public void getData() {
        trigger.setValue(true);
    }

    public LiveData<ApiResponse<List<ListBean.DataBean>>> getMutableLiveData() {
        return mutableLiveData;
    }
}