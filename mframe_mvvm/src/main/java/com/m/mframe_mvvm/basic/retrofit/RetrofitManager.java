package com.m.mframe_mvvm.basic.retrofit;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
                .addInterceptor(new CreateInterceptor())
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