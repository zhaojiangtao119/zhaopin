package com.labelwall.mall.common;

/**
 * Created by Administrator on 2017-12-02.
 */
public enum ResponseStatus {

    SUCCESS(0, "SUCCESS"),
    FAIL(1, "FAIL"),
    ERROR(2, "ERROR"),
    ERROR_PARAM(3, "ERROR_PARAMS");

    private int code;
    private String value;

    ResponseStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
