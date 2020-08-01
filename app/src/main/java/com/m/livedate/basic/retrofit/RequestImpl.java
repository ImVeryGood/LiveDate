package com.m.livedate.basic.retrofit;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.m.livedate.basic.view.DialogBean;
import com.m.livedate.basic.view.DialogLiveData;
import com.m.livedate.basic.view.LoadingDialog;
import com.m.livedate.ui.bean.ListBean;
import com.m.livedate.ui.bean.PagingBean;

import java.util.List;

/**
 * date:2020/7/14
 * describe：
 */
public class RequestImpl {
    private static ApiService apiService;
    private DialogLiveData<DialogBean> showDialog;

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

    public LiveData<ApiResponse<PagingBean.DataBean>> pagingData(LiveData trigger) {
        LiveData getPagingData = Transformations.switchMap(trigger, new Function<Object, LiveData<ApiResponse<PagingBean.DataBean>>>() {
            @Override
            public LiveData<ApiResponse<PagingBean.DataBean>> apply(Object input) {
                return getApiService().getPageData((Integer) trigger.getValue());
            }
        });
        return getPagingData;
    }
}
