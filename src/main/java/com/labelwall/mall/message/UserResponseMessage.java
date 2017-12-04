package com.labelwall.mall.message;

/**
 * Created by Administrator on 2017-12-02.
 */
public enum UserResponseMessage {

    SUCCESS("登录成功"),
    FAIL("登陆失败"),
    USERNAME_NULL("用户名不存在"),
    PASSWORD_ERROR("密码错误"),
    NOT_LOGIN("用户未登录"),
    USERNAME_NOT("用户名已被占用"),
    EMAIL_NOT("邮箱已被占用"),
    VAILD_PASS("验证通过"),
    REGISTER_FAIL("注册失败"),
    REGISTER_SUCCESS("注册成功"),
    MODIFY_SUCCESS("修改信息成功"),
    MODIFY_FAIL("修改信息失败");


    private String value;

    UserResponseMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
