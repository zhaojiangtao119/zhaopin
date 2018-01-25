package com.labelwall.mall.controller;

import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ShopCartDto;
import com.labelwall.mall.service.IShopCartService;
import com.labelwall.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-01-23.
 */
@RestController
@RequestMapping("/app/shopcart/")
public class AppShopCartController {
    @Autowired
    private IShopCartService shopCartService;

    /**
     * APP获取购物车信息（商品列表，每种商品的总价，数量，购物车的总价，商品的是否选中）
     *
     * @return
     */
    @RequestMapping(value = "app_get_cart_list/{userId}", method = RequestMethod.GET)
    public ResponseObject<CartVo> appGetCartList(@PathVariable("userId") Integer userId) {
        return shopCartService.getCartList(userId);
    }

    /**
     * APP添加商品到购物车
     *
     * @param shopCartDto
     * @return
     */
    @RequestMapping(value = "app_add_cart", method = RequestMethod.POST)
    public ResponseObject<CartVo> appAddCart(ShopCartDto shopCartDto) {
        return shopCartService.addCart(shopCartDto);
    }

    /**
     * APP修改购物车中商品的数量
     *
     * @param shopCartDto
     * @return
     */
    @RequestMapping(value = "app_update_quantity", method = RequestMethod.PUT)
    public ResponseObject<CartVo> appUpdateQuantity(ShopCartDto shopCartDto) {
        return shopCartService.updateQuantity(shopCartDto);
    }

    /**
     * APP商品全选
     *
     * @return
     */
    @RequestMapping(value = "app_select_all", method = RequestMethod.PUT)
    public ResponseObject<CartVo> appSelectAll(Integer userId) {
        return shopCartService.selectOrUnSelect(userId, null, Const.Cart.CHECKED);
    }

    /**
     * APP商品全取消
     *
     * @return
     */
    @RequestMapping(value = "app_un_select_all", method = RequestMethod.PUT)
    public ResponseObject<CartVo> appUnSelectAll(Integer userId) {
        return shopCartService.selectOrUnSelect(userId, null, Const.Cart.UN_CHECKED);
    }

    /**
     * APP单选
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "app_select", method = RequestMethod.PUT)
    public ResponseObject<CartVo> appSelect(Integer userId, Integer productId) {
        return shopCartService.selectOrUnSelect(userId, productId, Const.Cart.CHECKED);
    }

    /**
     * APP单反选
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "app_un_select", method = RequestMethod.PUT)
    public ResponseObject<CartVo> appUnSelect(Integer userId, Integer productId) {
        return shopCartService.selectOrUnSelect(userId, productId, Const.Cart.UN_CHECKED);
    }


}
