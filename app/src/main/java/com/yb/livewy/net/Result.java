package com.yb.livewy.net;

/**
 * create by liu
 * on 2020/4/22 4:06 PM
 **/
public class Result<T> {

    private String msg;
    private int code;
    private T data;

    public Result(String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public Result() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result:{" +
                "msg:'" + msg + '\'' +
                ", code:" + code +
                ", data:" + data.toString() +
                '}';
    }
}
