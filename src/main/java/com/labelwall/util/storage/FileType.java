package com.labelwall.util.storage;

/**
 * Created by Administrator on 2017-12-04.上传文件（图片）的类型,用文件头来判断
 */
public enum  FileType {

    JPEG("FFD8FFE"),

    PNG("89504E47"),

    GIF("47494638");

    private String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
