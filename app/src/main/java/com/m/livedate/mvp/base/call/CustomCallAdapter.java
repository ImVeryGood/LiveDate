package com.m.livedate.mvp.base.call;

import com.m.livedate.R;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import retrofit2.CallAdapter;

/**
 * createDate:2020/9/23
 *
 * @author:spc
 * @describe：
 */
public class CustomCallAdapter implements CallAdapter<R, CustomCall<R>> {
    private final Type responseType;

    public CustomCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    /**
     * 真正数据的类型，如Call<T> 中的 T，这个T会作为  Converter.Factory.responseConverter的第一个参数
     *
     * @return
     */
    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public CustomCall<R> adapt(retrofit2.Call<R> call) {
        return new CustomCall<>(call);
    }


}
