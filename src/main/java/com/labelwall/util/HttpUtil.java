package com.labelwall.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017-12-02. 网络请求工具类
 */
public class HttpUtil {

    public static boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-with");
        return !StringUtils.isBlank(header) && "XMLHttpRequest".equals(header);
    }
}
