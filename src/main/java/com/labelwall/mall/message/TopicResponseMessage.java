package com.labelwall.mall.message;

/**
 * Created by Administrator on 2017-12-30.
 */
public enum TopicResponseMessage {
    SCHOOL_IS_NULL(30001, "学校信息为空"),;

    private Integer code;
    private String value;

    TopicResponseMessage(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
