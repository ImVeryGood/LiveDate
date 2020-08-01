package com.m.livedate.mvvm.basic.retrofit;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * date:2020/7/9
 * describe：
 */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        //获取第一个泛型类型
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Type rawType = getRawType(observableType);
        if (rawType != ApiResponse.class){
            throw new IllegalArgumentException("type must be ApiResponse");
        }
        if (!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("resource must be Parameterized");
        }

        return new LiveDataCallAdapter<Type>(observableType);
    }
}
