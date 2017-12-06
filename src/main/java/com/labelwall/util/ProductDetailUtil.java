package com.labelwall.util;

import com.google.common.collect.Maps;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017-12-06. 商品上传富文本类型的数据（图片(上传到七牛)+文字）
 */
public class ProductDetailUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProductDetailUtil.class);

    //上传富文本图片，上传成功将图片的url返回回去
    public static Map<String, Object> uploadProductRichTextImage(MultipartFile fileData) {
        Map<String, Object> resultMap = Maps.newHashMap();
        String key = null;
        try {
            byte[] productRichTextImage = fileData.getBytes();
            key = QiniuStorage.uploadProductRichImage(productRichTextImage);
            if (QiniuStorage.fileIsExist(key)) {
                String url = QiniuStorage.getUrl(key);
                if (StringUtils.isBlank(url)) {
                    resultMap.put("success", true);
                    resultMap.put("msg", "上传成功");
                    resultMap.put("file_path", url);
                    return resultMap;
                }
            }
        } catch (IOException e) {
            logger.error("解析富文本图片失败", e);
        }
        resultMap.put("success", false);
        resultMap.put("msg", "上传失败");
        return resultMap;
    }

}
