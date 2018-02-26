package com.labelwall.mall.controller;

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
import com.labelwall.mall.dto.ShopCartDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IShopCartService;
import com.labelwall.mall.vo.CartVo;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;

/**
 * Created by Administrator on 2017-12-07.
 */
@RestController
@RequestMapping("/shopcart/")
public class ShopCartController {

    @Autowired
    private IShopCartService shopCartService;

    /**
     * 获取购物车信息（商品列表，每种商品的总价，数量，购物车的总价，商品的是否选中）
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_cart_list", method = RequestMethod.GET)
    public ResponseObject<CartVo> getCartList(HttpServletRequest request) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shopCartService.getCartList(userDto.getId());
    }

    /**
     * 添加商品到购物车
     *
     * @param session
     * @param shopCartDto
     * @return
     */
    @RequestMapping(value = "add_cart", method = RequestMethod.POST)
    public ResponseObject<CartVo> addCart(HttpServletRequest request, ShopCartDto shopCartDto) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        shopCartDto.setUserId(userDto.getId());
        return shopCartService.addCart(shopCartDto);
    }

    /**
     * 修改购物车中商品的数量
     *
     * @param session
     * @param shopCartDto
     * @return
     */
    @RequestMapping(value = "update_quantity", method = RequestMethod.PUT)
    public ResponseObject<CartVo> updateQuantity(HttpServletRequest request, ShopCartDto shopCartDto) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        shopCartDto.setUserId(userDto.getId());
        return shopCartService.updateQuantity(shopCartDto);
    }

    /**
     * 批量删除购物车中选中的商品
     *
     * @param session
     * @param productIds
     * @return
     */
    @RequestMapping(value = "remove_cart/{productIds}", method = RequestMethod.DELETE)
    public ResponseObject<CartVo> removeCart(HttpServletRequest request,
                                             @PathVariable("productIds") String productIds) {
        //TODO 批量删除的规则，可以使用 数组 来接收服务端传递的参数（商品的id），
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shopCartService.removeCart(userDto.getId(), productIds);
    }

    /**
     * 商品全选
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "select_all", method = RequestMethod.GET)
    public ResponseObject<CartVo> selectAll(HttpServletRequest request) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shopCartService.updateSelectOrUnSelect(userDto.getId(), null, Const.Cart.CHECKED);
    }



    /**
     * 商品全取消
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "un_select_all", method = RequestMethod.GET)
    public ResponseObject<CartVo> unSelectAll(HttpServletRequest request) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shopCartService.updateSelectOrUnSelect(userDto.getId(), null, Const.Cart.UN_CHECKED);
    }


    /**
     * 单选
     *
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "select/{productId}", method = RequestMethod.GET)
    public ResponseObject<CartVo> select(HttpServletRequest request,
                                         @PathVariable("productId") Integer productId) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shopCartService.updateSelectOrUnSelect(userDto.getId(), productId, Const.Cart.CHECKED);
    }


    /**
     * 单反选
     *
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "un_select/{productId}", method = RequestMethod.GET)
    public ResponseObject<CartVo> unSelect(HttpServletRequest request,
                                           @PathVariable("productId") Integer productId) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shopCartService.updateSelectOrUnSelect(userDto.getId(), productId, Const.Cart.UN_CHECKED);
    }


    /**
     * 获取购物车中的商品数量
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_cart_product_num", method = RequestMethod.GET)
    public ResponseObject<Integer> getCartProductNum(HttpServletRequest request) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return shopCartService.getCartProductCount(userDto.getId());
    }
}
