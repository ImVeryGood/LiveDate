package com.m.livedate.basic.retrofit;

import android.accounts.NetworkErrorException;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
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
                            apiResponse = (ApiResponse) body;
                            apiResponse.setSuccess(true);
                        } else {
                            apiResponse.setSuccess(false);
                            int code = response.code();
                            apiResponse.setErrorCode(code);
                            String errorMsg = "请求失败!";
                            try {
                                String errorJson = response.errorBody().toString();
                                ApiResponse errorResponse = new Gson().fromJson(errorJson, ApiResponse.class);
                                errorMsg = errorResponse.getErrorMsg();
                                if (code == 404) {
                                    errorMsg = Const.ERROR_LINK_NOT_FOUND;
                                } else if (code == 500) {
                                    errorMsg = Const.ERROR_SERVER;
                                }
                            } catch (IllegalAccessError e) {
                                e.printStackTrace();
                            }
                            apiResponse.setErrorMsg(errorMsg);
                            apiResponse.setData(null);

                        }
                        postValue((T) apiResponse);
                    }

                    @Override
                    public void onFailure(Call<T> call, Throwable t) {
                        ApiResponse apiResponse = new ApiResponse();
                        apiResponse.setSuccess(false);
                        apiResponse.setErrorCode(-11111);
                        apiResponse.setData(null);
                        String errorMsg = t.getMessage();
                        if (t instanceof NetworkErrorException) {
                            errorMsg = Const.ERROR_NET;
                        } else if (t instanceof SocketException) {
                            SocketException socketException = (SocketException) t;
                            errorMsg = socketException.getMessage();
                        } else if (t instanceof ConnectException) {
                            errorMsg = Const.ERROR_LINK_SERVER;
                        } else if (t instanceof SocketTimeoutException) {
                            errorMsg = Const.ERROR_TIMEOUT;
                        } else if (t instanceof UnknownHostException) {
                            errorMsg = Const.ERROR_LINK_SERVER;
                        }
                        if (errorMsg == null) {
                            errorMsg = "";
                        }
                        apiResponse.setErrorMsg(errorMsg);
                        postValue((T) apiResponse);
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
