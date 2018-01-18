package com.labelwall.exception;

/**
 * Created by Administrator on 2017-12-30.
 * 系统自定义的异常类，针对预期的异常，需要在程序中抛出此异常
 */
public class CustomException extends Exception {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
