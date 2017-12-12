package com.labelwall.mall.message;

/**
 * Created by Administrator on 2017-12-02.
 */
public enum UserResponseMessage {

    SUCCESS(10001, "登录成功"),
    FAIL(10002, "登陆失败"),
    USERNAME_NULL(10003, "用户名不存在"),
    PASSWORD_ERROR(10004, "密码错误"),
    NOT_LOGIN(10005, "用户未登录"),
    USERNAME_NOT(10006, "用户名已被占用"),
    EMAIL_NOT(10007, "邮箱已被占用"),
    VAILD_PASS(10008, "验证通过"),
    REGISTER_FAIL(10009, "注册失败"),
    REGISTER_SUCCESS(10010, "注册成功"),
    MODIFY_SUCCESS(10011, "修改信息成功"),
    MODIFY_FAIL(10012, "修改信息失败");

    private int code;
    private String value;

    UserResponseMessage(int code, String value) {
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
