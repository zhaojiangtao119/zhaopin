package com.labelwall.mall.controller.common;

import com.labelwall.common.Const;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 重置session类
 * @author Administrator
 *
 */
public class SessionExpireFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		String loginToken=CookieUtil.readLoginToken(request);
		if(StringUtils.isNotEmpty(loginToken)){
			String vlues=RedisPoolUtil.get(loginToken);
			UserDto user=JsonUtil.stringToObj(vlues, UserDto.class);
			if(user != null){
				RedisPoolUtil.expire(loginToken, Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
