package com.m.livedate.retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * date:2020/7/9
 * describe：
 */
public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<T>> {
    private Type mResponseType;

    public LiveDataCallAdapter(Type mResponseType) {
        this.mResponseType = mResponseType;
    }

    @Override
    public Type responseType() {
        return mResponseType;
    }

    @Override
    public LiveData<T> adapt(Call<T> call) {
        return new MLiveData<>(call);
    }

    private static class MLiveData<T> extends LiveData<T> {
        private AtomicBoolean stared = new AtomicBoolean(false);
        private final Call<T> call;
        public MLiveData(Call<T> call) {
            this.call = call;
            if (stared.compareAndSet(false, true)) {
                call.enqueue(new Callback<T>() {
                    @Override
                    public void onResponse(Call<T> call, Response<T> response) {
                        ApiResponse apiResponse = new ApiResponse();
                        if (response.isSuccessful()) {
                            T body = response.body();
                            apiResponse= (ApiResponse) body;
                        }else {
                            int code = response.code();
                            apiResponse.setErrorCode(code);
                            String errorMsg = "请求失败，请稍后重试";
                            String errorJson = response.errorBody().toString();
                            ApiResponse errorResponse = new Gson().fromJson(errorJson, ApiResponse.class);
                            errorMsg=errorResponse.getErrorMsg();
                        }
                        postValue((T) apiResponse);
                    }

                    @Override
                    public void onFailure(Call<T> call, Throwable t) {
                        Log.d("SSSSSSSSSSSS", "onFailure: "+t.getMessage());
                    }
                });
            }
        }

        @Override
        protected void onActive() {
            super.onActive();
        }
    }
}
