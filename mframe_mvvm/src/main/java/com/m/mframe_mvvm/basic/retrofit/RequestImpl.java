package com.m.mframe_mvvm.basic.retrofit;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.m.mframe_mvvm.demo.ListBean;

import java.util.List;


/**
 * date:2020/7/14
 * describe：
 */
public class RequestImpl {
    private static ApiService apiService;

    public RequestImpl() {
        RetrofitManager retrofitManager = new RetrofitManager();
        apiService = retrofitManager.getApiService();

    }

    public static ApiService getApiService() {
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
