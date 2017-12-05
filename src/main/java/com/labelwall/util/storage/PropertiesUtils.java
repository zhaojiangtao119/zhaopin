package com.labelwall.util.storage;

import com.labelwall.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017-12-04.获取七牛云存储的属性配置文件
 */
public class PropertiesUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private static final Map<String, Properties> propMap = new HashMap<>();

    private static final String DESAULT_PROPERTIES_FILE = "qiniu.properties";

    public static String getProperty(String key) {
        Properties properties = getDefaultProperties();//读取默认的配置文件
        if (properties != null && properties.get(key) != null) {
            return properties.getProperty(key);
        }
        return null;
    }

    public static Properties getDefaultProperties() {
        return getPropertys(DESAULT_PROPERTIES_FILE);
    }

    private static Properties getPropertys(String file) {
        try {
            if (propMap.get(file) == null) {
                Properties properties = new Properties();
                properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(file));
                propMap.put(file, properties);
                return properties;
            } else {
                return propMap.get(file);
            }
        } catch (IOException e) {
            logger.error("配置文件读取失败", e);
            return null;
        }
    }

    public static Object getProperty(String file, String key) {
        Properties properties = getPropertys(file);
        if (properties != null && properties.get(key) != null) {
            return properties.get(key);
        }
        return null;
    }
}
