package com.m.livedate.mvp.base.net;

public class BaseBean<T> {

    /**
     * code : u_01
     * msg : 用户名或密码错误
     * result : null
     * success : false
     */

    private String code;
    private String msg;
    private T result;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
