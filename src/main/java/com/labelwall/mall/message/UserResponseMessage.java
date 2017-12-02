package com.labelwall.mall.message;

/**
 * Created by Administrator on 2017-12-02.
 */
public enum UserResponseMessage {

    SUCCESS("登录成功"),
    FAIL("登陆失败"),
    USERNAME_NULL("用户名不存在"),
    PASSWORD_ERROR("密码错误");

    private String value;

    UserResponseMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
