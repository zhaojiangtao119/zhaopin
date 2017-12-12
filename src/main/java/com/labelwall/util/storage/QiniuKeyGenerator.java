package com.labelwall.util.storage;

import com.labelwall.util.CommonUtil;

import java.text.MessageFormat;

/**
 * Created by Administrator on 2017-12-04.
 */
public class QiniuKeyGenerator {
    /*
        资源在七牛云存储的key生成器
        资源是七牛云存储服务中的逻辑存储单元，对于每一账号，该账号了皴法的每一个资源
        都有唯一的一对属主空间（bucket）与键名（key）作为标识。
    */
    //多图片可以按照：/表名/字段名/业务值/时间戳，进行处理
    private static final String key = "/{0}/{1}/{2}";

    public static String generatorUserHeadKey(){
        return MessageFormat.format(key,"user","head", CommonUtil.getUUID());
    }

    public static String generatorPostImageKey(){
        return MessageFormat.format(key,"topicPost","image",CommonUtil.getUUID());
    }

    public static String generatorReplyImageKey(){
        return MessageFormat.format(key,"topicPostReply","image",CommonUtil.getUUID());
    }

    public static String generatorProductRichImageKey(){
        return MessageFormat.format(key,"product","detail",CommonUtil.getUUID());
    }

    public static String generatorOrderPayImageKey(){
        return MessageFormat.format(key,"order","online","pay");
    }

}
