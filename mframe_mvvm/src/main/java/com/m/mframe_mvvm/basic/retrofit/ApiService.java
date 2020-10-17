package com.m.mframe_mvvm.basic.retrofit;

import androidx.lifecycle.LiveData;

import com.m.mframe_mvvm.demo.ListBean;

import java.util.List;

import retrofit2.http.GET;

/**
 * date:2020/7/9
 * describe：
 */
public interface ApiService {
    @GET("wxarticle/chapters/json")
    LiveData<ApiResponse<List<ListBean.DataBean>>> getListBeanData();
}
