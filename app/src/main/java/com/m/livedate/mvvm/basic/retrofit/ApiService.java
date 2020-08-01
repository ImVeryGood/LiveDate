package com.m.livedate.mvvm.basic.retrofit;

import androidx.lifecycle.LiveData;

import com.m.livedate.mvvm.ui.bean.ListBean;
import com.m.livedate.mvvm.ui.bean.PagingBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * date:2020/7/9
 * describe：
 */
public interface ApiService {
    @GET("wxarticle/chapters/json")
    LiveData<ApiResponse<List<ListBean.DataBean>>> getListBeanData();

    @GET("article/list/{page}/json")
    LiveData<ApiResponse<PagingBean.DataBean>> getPageData(@Path("page") int page);
    @GET("article/list/{page}/json")
    List<ApiResponse<PagingBean.DataBean>> getPageDataList(@Path("page") int page);
}
