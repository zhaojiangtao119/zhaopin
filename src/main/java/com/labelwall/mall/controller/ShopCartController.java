package com.labelwall.mall.controller;

import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dto.ShopCartDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.IShopCartService;
import com.labelwall.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ResponseObject<CartVo> getCartList(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shopCartService.getCartList(userDto.getId());
    }

    @RequestMapping(value = "add_cart", method = RequestMethod.POST)
    public ResponseObject<CartVo> addCart(HttpSession session, ShopCartDto shopCartDto) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        shopCartDto.setUserId(userDto.getId());
        return shopCartService.addCart(shopCartDto);
    }

    @RequestMapping(value = "update_quantity", method = RequestMethod.POST)
    public ResponseObject<CartVo> updateQuantity(HttpSession session, ShopCartDto shopCartDto) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        shopCartDto.setUserId(userDto.getId());
        return shopCartService.updateQuantity(shopCartDto);
    }

    @RequestMapping(value = "remove_cart", method = RequestMethod.POST)
    public ResponseObject<CartVo> removeCart(HttpSession session, String productIds) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shopCartService.removeCart(userDto.getId(), productIds);
    }

    /**
     * 商品全选
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "select_all", method = RequestMethod.GET)
    public ResponseObject<CartVo> selectAll(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shopCartService.selectOrUnSelect(userDto.getId(), null, Const.Cart.CHECKED);
    }

    /**
     * 商品全取消
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "un_select_all", method = RequestMethod.GET)
    public ResponseObject<CartVo> unSelectAll(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shopCartService.selectOrUnSelect(userDto.getId(), null, Const.Cart.UN_CHECKED);
    }

    /**
     * 单选
     *
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "select", method = RequestMethod.GET)
    public ResponseObject<CartVo> select(HttpSession session, Integer productId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shopCartService.selectOrUnSelect(userDto.getId(), productId, Const.Cart.CHECKED);
    }

    /**
     * 单反选
     *
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "un_select", method = RequestMethod.GET)
    public ResponseObject<CartVo> unSelect(HttpSession session, Integer productId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shopCartService.selectOrUnSelect(userDto.getId(), productId, Const.Cart.UN_CHECKED);
    }

    /**
     * 获取购物车中的商品数量
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_cart_product_num", method = RequestMethod.GET)
    public ResponseObject<Integer> getCartProductNum(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shopCartService.getCartProductCount(userDto.getId());
    }
}
