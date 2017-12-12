package com.labelwall.util;

import com.labelwall.common.web.SessionUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017-12-02. 管理Session，此session是shiro中处理的session会话
 */
public class SessionUtil {

    private static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);

    //获取当前当前登录的用户
    public static SessionUser getCurrentUser() {
        SessionUser currentUser = (SessionUser) SecurityUtils.getSubject().getPrincipal();
        if (currentUser == null) {
            return currentUser;
        }
        return null;
    }

    public static Integer getUserId() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getUserId();
        }
        return null;
    }

    public static String getUsername() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getUsername();
        }
        return null;
    }

    //判断当前用户是否登录
    public static boolean isLogin() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null && currentUser.getPrincipal() != null) {
            return true;
        }
        return false;
    }

    //shiro退出登录
    public static boolean shiroLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
            return true;
        } catch (AuthenticationException e) {
            logger.error("shiro退出异常", e);
            return false;
        }
    }

    //操作HttpSession对象，获取属性，设置属性，删除属性
    public static Object getAttribute(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    public static void setAttribute(HttpServletRequest request, String key, String value) {
        request.getSession().setAttribute(key, value);
    }

    public static void removeAttribute(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

}
