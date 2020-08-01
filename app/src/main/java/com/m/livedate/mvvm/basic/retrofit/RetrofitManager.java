package com.m.livedate.mvvm.basic.retrofit;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * date:2020/7/9
 * describe：
 */
public class RetrofitManager {
    private static String mBaseUrl = UrlHelper.baseUrl;
    //    超时时长
    private static int mConnectTimeOut = 30;
    private static int readTimeout = 30;
    private static int writeTimeout = 30;
    //    用于存储retrofit
    private static ConcurrentHashMap<String, Retrofit> mRetrofitMap;
    private static OkHttpClient.Builder mOkhttpBuilder;
    private ApiService mApiService;
    private Retrofit mRetrofit;

    public RetrofitManager() {
        initOkHttp();
        createRetrofit();
    }

    public ApiService getApiService() {
        return mApiService;
    }

    /**
     * 设置okHttp
     */
    public void initOkHttp() {
        mOkhttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(mConnectTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                //只用HTTP1.x Http2会报REFUSED_STREAM
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
//                取消错误重连
                .retryOnConnectionFailure(false);
    }

    /**
     * 创建retrofit对象
     *
     * @return
     */

    public void createRetrofit() {
        mRetrofit = new Retrofit.Builder().baseUrl(mBaseUrl)
                .client(mOkhttpBuilder.build())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }
}
