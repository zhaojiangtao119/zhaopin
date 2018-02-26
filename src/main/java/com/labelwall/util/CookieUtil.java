package com.labelwall.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    private final static String COOKIE_DOMAIN = "labelwall.com";
    private final static String COOKIE_NAME = "labelwall_login_cookie";

    /**
     * 写入cookie
     *
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        //防止cookie信息泄露以及阻止部分脚本攻击
        cookie.setHttpOnly(true);
        //设置客户端cookie的保存时间，如果不设置，则cookie不写入硬盘而是保存在内存中，从而只对当前页面有效。-1表示永远
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);
    }

    /**
     * 读取cookie
     *
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie cookie : cks) {
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除cookie,也就是将客户端保存的cookie读取，设置时间为0 ，再写回客户端
     *
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie cookie : cks) {
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }
}
