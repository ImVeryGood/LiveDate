package com.m.livedate.retrofit;

/**
 * date:2020/7/9
 * describe：
 */
public class ApiResponse<T extends Object> {


    private int errorCode; //状态码
    private String errorMsg; //信息
    private T data; //数据

//    public ApiResponse(int code, String msg) {
//        this.code = code;
//        this.msg = msg;
//        this.data = null;
//    }
//
//    public ApiResponse(int code, String msg, T data) {
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}