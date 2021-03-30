package com.m.livedate.mvp.base.call;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * createDate:2020/9/23
 *
 * @author:spc
 * @describe：
 */
public class CustomCallAdapterFactory extends CallAdapter.Factory {

    public static CustomCallAdapterFactory create() {
        return new CustomCallAdapterFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        //获取原始类型
        Class<?> rawType = getRawType(returnType);

        //返回值必须是Custom并且带有泛型（参数类型），根据APIService接口中的方法返回值，确定returnType
        //如 CustomCall<String> getCategories();，那确定returnType就是CustomCall<String>
        if (rawType == CustomCall.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new CustomCallAdapter(callReturnType);
        }

        return null;
    }
}
