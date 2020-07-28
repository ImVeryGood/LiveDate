package com.m.livedate.basic.retrofit;

/**
 * date:2020/7/9
 * describe：
 */
public class ApiResponse<T extends Object> {


    private int errorCode; //状态码
    private String errorMsg; //信息
    private boolean isSuccess;
    private T data; //数据

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

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