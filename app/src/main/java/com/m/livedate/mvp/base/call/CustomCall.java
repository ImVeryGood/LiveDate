package com.m.livedate.mvp.base.call;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * createDate:2020/9/23
 *
 * @author:spc
 * @describe：
 */
public class CustomCall<R> {

    public final Call<R> call;

    public CustomCall(Call<R> call) {
        this.call = call;
    }

    public R get() throws IOException {
//        call.enqueue(new Callback<R>() {
//            @Override
//            public void onResponse(Call<R> call, Response<R> response) {
//                Log.d("SSSSS", "onResponse: ");
//            }
//
//            @Override
//            public void onFailure(Call<R> call, Throwable t) {
//                Log.d("SSSSS", "onFailure: ");
//            }
//        });
        return call.execute().body();
    }
}