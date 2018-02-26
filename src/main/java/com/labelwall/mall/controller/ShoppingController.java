package com.labelwall.mall.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IShoppingService;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;

/**
 * Created by Administrator on 2017-12-06.
 */
@RestController
@RequestMapping("/shopping/")
public class ShoppingController {

    @Autowired
    private IShoppingService shoppingService;

    /**
     * 获取指定的收货地址详情
     *
     * @param shoppingId
     * @param session
     * @return
     */
    @RequestMapping(value = "get_shopping_by_id/{shoppingId}", method = RequestMethod.GET)
    public ResponseObject<ShoppingDto> getShoppingById(@PathVariable(value = "shoppingId") Integer shoppingId,
                                                       HttpServletRequest request) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shoppingService.getShoppingById(userDto.getId(), shoppingId);
    }

    /**
     * 获取用户的收货地址列表
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_shopping_by_user_id", method = RequestMethod.GET)
    public ResponseObject<List<ShoppingDto>> getShoppingByUserId(HttpServletRequest request) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shoppingService.getShoppingByUserId(userDto.getId());
    }

    /**
     * 添加收货地址
     *
     * @param session
     * @param shoppingDto
     * @return
     */
    @RequestMapping(value = "add_shopping", method = RequestMethod.POST)
    public ResponseObject<ShoppingDto> addShopping(HttpServletRequest request, ShoppingDto shoppingDto) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        shoppingDto.setUserId(userDto.getId());
        return shoppingService.addShopping(shoppingDto);
    }

    /*
        如果是使用的是PUT方式，SpringMVC默认将不会辨认到请求体中的参数，
        或者也有人说是Spirng MVC默认不支持 PUT请求带参数.
        :将web.xml的HiddenHttpMethodFilter过滤器改为HttpPutFormContentFilter
    */

    /**
     * 修改收货地址
     *
     * @param session
     * @param shoppingDto
     * @return
     */
    @RequestMapping(value = "update_shopping", method = RequestMethod.PUT)
    public ResponseObject<ShoppingDto> updateShopping(HttpServletRequest request, ShoppingDto shoppingDto) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        shoppingDto.setUserId(userDto.getId());
        return shoppingService.updateShopping(shoppingDto);
    }

    /**
     * 删除收货地址
     *
     * @param session
     * @param shoppingId
     * @return
     */
    @RequestMapping(value = "remove_shopping/{shoppingId}", method = RequestMethod.DELETE)
    public ResponseObject removeShopping(HttpServletRequest request,
                                         @PathVariable(value = "shoppingId") Integer shoppingId) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shoppingService.removeShopping(userDto.getId(), shoppingId);
    }
}
