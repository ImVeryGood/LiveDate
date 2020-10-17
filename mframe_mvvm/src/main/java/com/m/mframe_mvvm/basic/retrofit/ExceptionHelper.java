package com.m.mframe_mvvm.basic.retrofit;

import android.net.ParseException;
import android.os.NetworkOnMainThreadException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * date:2019/1/2
 * android.os.NetworkOnMainThreadException
 */
public class ExceptionHelper {

    public static String handleException(Throwable e) {
        String error;
        if (e instanceof SocketTimeoutException) {
            //网络超时
            error = "网络连接异常";
        } else if (e instanceof ConnectException) {
            //均视为网络错误
            error = "网络连接异常";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //均视为解析错误
            error = "数据解析异常";
        } else if (e instanceof ApiException) {
            //服务器返回的错误信息
            error = e.getCause().getMessage();
        } else if (e instanceof UnknownHostException) {
            error = "网络连接异常";
        } else if (e instanceof IllegalArgumentException) {
            error = "非法参数异常";
        } else if (e instanceof HttpException) {
            error = "网络连接异常";
        } else if (e instanceof NullPointerException) {
            error = "空指针异常";
        } else if (e instanceof NetworkOnMainThreadException) {
            error = "主线程处理了耗时操作";
        } else {
            error = "错误";
        }
        return error;
    }


}