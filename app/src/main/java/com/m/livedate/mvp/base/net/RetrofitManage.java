package com.m.livedate.mvp.base.net;

import com.m.livedate.mvp.StaticQuality;
import com.m.livedate.mvp.base.call.CustomCallAdapterFactory;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：
 */
public class RetrofitManage {
    private Retrofit mRetrofit;
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private RetrofitManage() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.addInterceptor(new CreateInterceptor());
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(StaticQuality.BASE_URL)
                .build();
    }

    /**
     * 静态内部类单例模式
     *
     * @return
     */
    public static RetrofitManage getInstance() {
        return Inner.mRetrofitManage;
    }

    private static class Inner {
        private static final RetrofitManage mRetrofitManage = new RetrofitManage();
    }

    /**
     * 利用泛型传入接口class返回接口实例
     *
     * @param ser 类
     * @param <T> 类型
     * @return Observable
     */
    public <T> T createRs(Class<T> ser) {
        return mRetrofit.create(ser);
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