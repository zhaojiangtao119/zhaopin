package com.labelwall.mall.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-12-02. 服务端响应对象
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseObject<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ResponseObject(int status) {
        this.status = status;
    }

    private ResponseObject(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseObject(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ResponseObject(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseObject<T> success(String msg, T data) {
        return new ResponseObject<T>(ResponseStatus.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResponseObject<T> successStatus() {
        return new ResponseObject<T>(ResponseStatus.SUCCESS.getCode());
    }

    public static <T> ResponseObject<T> successStatusMessage(String msg) {
        return new ResponseObject<T>(ResponseStatus.SUCCESS.getCode(), msg);
    }

    public static <T> ResponseObject<T> successStautsData(T data) {
        return new ResponseObject<T>(ResponseStatus.SUCCESS.getCode(), data);
    }

    public static <T> ResponseObject<T> fail(int failStatus, String msg) {
        return new ResponseObject<T>(failStatus, msg);
    }

    public static <T> ResponseObject<T> failStatusMessage(String msg) {
        return new ResponseObject<T>(ResponseStatus.ERROR.getCode(), msg);
    }

    //不需要json序列化该对象
    @JsonIgnore
    public boolean isSuccess() {
        return this.getStatus() == ResponseStatus.SUCCESS.getCode();
    }
}
