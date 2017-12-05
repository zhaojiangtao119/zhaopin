package com.labelwall.util.storage;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Created by Administrator on 2017-12-05.
 */
public class QiniuWrapper {

    private static final Logger logger = LoggerFactory.getLogger(QiniuWrapper.class);

    private static final String CONFIG_BUCKET = "qiniu.bucket";
    private static final String CONFIG_AK = "qiniu.accesskey";
    private static final String CONFIG_SK = "qiniu.secretkey";
    private static final String CONFIG_CDNS = "qiniu.cdns";

    private static final Auth auth;

    private static final UploadManager uploadManager;

    private static final BucketManager bucketManager;

    private static final String bucketName;

    private static final String domain;

    static {
        Properties properties = PropertiesUtils.getDefaultProperties();
        auth = Auth.create(properties.getProperty(CONFIG_AK), properties.getProperty(CONFIG_SK));
        Configuration cfg = new Configuration(Zone.zone1());
        //设置Config中的UP_HOST/RS_HOST，将默认的华东zone0空间设置为华北zone1

        uploadManager = new UploadManager(cfg);
        bucketManager = new BucketManager(auth, cfg);

        bucketName = properties.getProperty(CONFIG_BUCKET);
        domain = properties.getProperty(CONFIG_CDNS);
    }

    /**
     * @param data   上传的数据
     * @param key    数据存储的key
     * @param update 是否是更新数据
     * @return
     */
    public static String upload(byte[] data, String key, boolean update) {
        try {
            //1.获取上传凭证
            String token = update ? auth.uploadToken(bucketName, key) : auth.uploadToken(bucketName);
            //2.上传数据
            Response response = null;
            if (update) {
                response = uploadManager.put(data, key, token);
            } else {
                response = uploadManager.put(data, getFullKey(data, key), token);
            }
            //3.
            DefaultPutRet ret = response.jsonToObject(DefaultPutRet.class);
            //4.返回资源的key
            return ret.key;
        } catch (QiniuException e) {
            logger.error("上传失败", e);
            Response r = e.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    private static String getFullKey(byte[] data, String key) {
        FileType fileType = FileTypeHepler.getFileType(data);
        if (fileType != null) {
            return key + "." + fileType.name().toLowerCase();
        }
        return key;
    }

    public static String getUrl(String key) {
        try {
            String encodedFileName = URLEncoder.encode(key, "utf-8");
            String url = String.format("%s/%s", domain, encodedFileName);
            return url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUrl(String key, String model) {
        long expires = 3600l;
        if(StringUtils.isBlank(model)){
            return auth.privateDownloadUrl("http://"+domain+"/@"+key,expires);
        }else{
            return auth.privateDownloadUrl("http://"+domain+"/@"+key+"?"+model,expires);
        }
    }

    public static boolean deleteFile(String key) {
        boolean flag = false;
        try {
            Response response = bucketManager.delete(bucketName, key);
            if (response.statusCode == 200) {//删除成功
                flag = true;
                return flag;
            }
        } catch (QiniuException e) {
            logger.error("删除失败", e.response.toString());
        }
        return flag;
    }

}
