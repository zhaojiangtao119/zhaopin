package com.labelwall.util.storage;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017-12-05.
 */
public class QiniuStorage {

    //上传用户头像
    public static String uploadUserHead(byte[] buff) {
        String key = QiniuWrapper.upload(buff, QiniuKeyGenerator.generatorUserHeadKey(), false);
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return key;
    }

    //上传帖子的图片
    public static String uploadPostImage(byte[] buff) {
        String key = QiniuWrapper.upload(buff, QiniuKeyGenerator.generatorPostImageKey(), false);
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return key;
    }
    //上传回复的图片
    public static String uploadReplyImage(byte[] buff){
        String key = QiniuWrapper.upload(buff, QiniuKeyGenerator.generatorReplyImageKey(), false);
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return key;
    }

    //上传商品的富文本图片
    public static String uploadProductRichImage(byte[] buff){
        String key = QiniuWrapper.upload(buff, QiniuKeyGenerator.generatorProductRichImageKey(), false);
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return key;
    }

    //上传支付二维码图片
    public static String updateOrderPayImage(byte[] buff,String uploadKey){
        String key = QiniuWrapper.upload(buff, uploadKey, false);
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

    public static boolean fileIsExist(String key){
        return QiniuWrapper.fileIsExist(key);
    }
}
