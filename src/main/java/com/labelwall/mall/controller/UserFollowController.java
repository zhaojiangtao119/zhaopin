package com.labelwall.mall.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IUserFollowsService;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;

/**
 * Created by Administrator on 2017-12-21.
 */
@RestController
@RequestMapping("/userfollows/")
public class UserFollowController {

    @Autowired
    private IUserFollowsService userFollowsService;

    /**
     * 判断用户是否关注了某个用户
     *
     * @param session
     * @param followsId
     * @return
     */
    @RequestMapping(value = "is_follows/{id}", method = RequestMethod.GET)
    public ResponseObject<Boolean> isFollows(HttpServletRequest request,
                                             @PathVariable("id") Integer followsId) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return userFollowsService.isFollows(userDto.getId(), followsId);
    }

    /**
     * 点击关注按钮之后执行的动作
     *
     * @param session
     * @param followsId
     * @return
     */
    @RequestMapping(value = "do_follows/{id}", method = RequestMethod.POST)
    public ResponseObject doFollows(HttpServletRequest request,
                                    @PathVariable("id") Integer followsId) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return userFollowsService.doFollows(userDto.getId(), followsId);
    }

    /**
     * 获取当前用户关注的用户列表
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_follows/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getFollows(HttpServletRequest request,
                                               @PathVariable("pageNum") Integer pageNum,
                                               @PathVariable("pageSize") Integer pageSize) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return userFollowsService.getFollows(userDto.getId(), pageNum, pageSize);
    }


}
