package com.m.mframe_mvp.basic.net;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * createDate:2020/10/15
 *
 * @author:spc
 * @describe： retrofit 网络请求
 */
public class RetrofitManage {

    private static OkHttpClient.Builder mOkHttpBuilder;
    private static Retrofit mRetrofit;
    private static RetrofitManage retrofitManage;
    private static NetConfig mConfig;


    public static synchronized Retrofit createRetrofit() {
        return getInstance().initRetrofit();
    }

    public static void setNetConfig(NetConfig netConfig) {
        RetrofitManage.mConfig = netConfig;
        retrofitManage = null;
    }

    private static synchronized RetrofitManage getInstance() {
        if (retrofitManage == null) {
            retrofitManage = new RetrofitManage();
        }
        return retrofitManage;
    }

    private OkHttpClient.Builder initOkHttp() {
        mOkHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(mConfig.configConnectTimeoutMills(), TimeUnit.SECONDS)
                .readTimeout(mConfig.configReadTimeoutMills(), TimeUnit.SECONDS)
                .writeTimeout(mConfig.configWriteTimeoutMills(), TimeUnit.SECONDS)
                .addInterceptor(new CreateInterceptor())
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .retryOnConnectionFailure(false);
        return mOkHttpBuilder;
    }

    private Retrofit initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mConfig.mBaseUrl())
                .client(initOkHttp().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }
}

class CreateInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        // 输出返回结果
        try {
            Charset charset;
            charset = Charset.forName("UTF-8");
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            Reader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
            BufferedReader reader = new BufferedReader(jsonReader);
            StringBuilder sbJson = new StringBuilder();
            String line = reader.readLine();
            do {
                sbJson.append(line);
                line = reader.readLine();
            } while (line != null);
//            可在此处处理code不为200的情况
            if (true) {
                Logger.d("url: " + request.url() + "\n"
                        + "response: " + sbJson.toString());
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(e.toString());
        }
        return response;
    }
}

