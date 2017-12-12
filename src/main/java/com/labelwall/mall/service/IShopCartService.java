package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ShopCartDto;
import com.labelwall.mall.vo.CartVo;

/**
 * Created by Administrator on 2017-12-07.
 */
public interface IShopCartService {
    /**
     * 获取当前用户的购物车
     *
     * @param userId
     * @return
     */
    ResponseObject<CartVo> getCartList(Integer userId);

    /**
     * 添加商品到购物车
     * @param shopCartDto
     * @return
     */
    ResponseObject<CartVo> addCart(ShopCartDto shopCartDto);

    /**
     * 修改购买商品的数量
     * @param shopCartDto
     * @return
     */
    ResponseObject<CartVo> updateQuantity(ShopCartDto shopCartDto);

    /**
     * 移除购物中的商品
     * @param userId
     * @param productIds
     * @return
     */
    ResponseObject<CartVo> removeCart(Integer userId,String productIds);

    /**
     * 单选，单反选，全选
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    ResponseObject<CartVo> selectOrUnSelect(Integer userId,Integer productId,Integer checked);

    /**
     * 获取购物车中商品的总数量
     * @param id
     * @return
     */
    ResponseObject<Integer> getCartProductCount(Integer id);
}
