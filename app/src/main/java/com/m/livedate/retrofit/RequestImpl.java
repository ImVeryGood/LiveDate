package com.m.livedate.retrofit;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

/**
 * date:2020/7/14
 * describe：
 */
public class RequestImpl {
    private ApiService apiService;

    public RequestImpl() {
        RetrofitManager retrofitManager = new RetrofitManager();
        apiService = retrofitManager.getApiService();
    }

    private ApiService getApiService() {
        return apiService;
    }

    public LiveData<ApiResponse<List<ListBean.DataBean>>> checkVersion(LiveData trigger) {
        LiveData checkVersionLiveData = Transformations.switchMap(trigger, new Function<Object, LiveData<ApiResponse<List<ListBean.DataBean>>>>() {
            @Override
            public LiveData<ApiResponse<List<ListBean.DataBean>>> apply(Object input) {
                return getApiService().getListBeanData();
            }
        });
        return checkVersionLiveData;

    }
}
