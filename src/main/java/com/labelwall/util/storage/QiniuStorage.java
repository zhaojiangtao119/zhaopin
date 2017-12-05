package com.labelwall.util.storage;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017-12-05.
 */
public class QiniuStorage {

    //上传用户头像
    public static String uoloadUserHead(byte[] buff) {
        String key = QiniuWrapper.upload(buff, QiniuKeyGenerator.generatorUserHeadKey(), false);
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return key;
    }

    public static String getUserHeadUrl(String key){
        return QiniuWrapper.getUrl(key,ThumbModel.THUMB_64.getValue());
    }

    public static String getUrl(String key) {
        String url = QiniuWrapper.getUrl(key);
        if (StringUtils.isBlank(url)) {
            return null;
        }
        return url;
    }

    public static String getUrl(String key,ThumbModel model){
        return QiniuWrapper.getUrl(key,model.getValue());
    }

    public static boolean deleteFile(String key) {
        return QiniuWrapper.deleteFile(key);
    }
}
