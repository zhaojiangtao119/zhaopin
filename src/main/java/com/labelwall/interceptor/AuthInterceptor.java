package com.labelwall.interceptor;

import com.labelwall.common.Const;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.util.HttpUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017-12-12. 用户登录拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户是否登录
        UserDto userDto = (UserDto) request.getSession().getAttribute(Const.CURRENT_USER);
        if (userDto != null) {
            return true;
        }
        //跳转路径，提示用户未登录
        if (HttpUtil.isAjaxRequest(request)) {//是Ajax请求
            String basePath = request.getScheme() + "://" +
                    request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            response.setHeader("url", basePath + "/auth/noLogin");
        } else {
            request.getRequestDispatcher("/auth/noLogin").forward(request, response);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
