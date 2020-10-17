package com.m.mframe_mvp.basic.net;

import com.m.mframe_mvp.demo.ArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * createDate:2020/10/15
 *
 * @author:spc
 * @describe：
 */
public interface ApiServer {

    @GET("article/listproject/0/json")
    Observable<ArticleBean> getData();
} 