package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ShoppingDto;

import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
public interface IShoppingService {

    /**
     * 获取当前用户的所有配送地址
     *
     * @param id
     * @return
     */
    ResponseObject<List<ShoppingDto>> getShoppingByUserId(Integer id);

    /**
     * 添加新地址
     *
     * @param shoppingDto
     * @return
     */
    ResponseObject<ShoppingDto> addShopping(ShoppingDto shoppingDto);

    /**
     * 根据id获取指定的地址信息(防止横向越权)
     *
     * @param shoppingId
     * @return
     */
    ResponseObject<ShoppingDto> getShoppingById(Integer userId, Integer shoppingId);

    /**
     * 修改地址信息(防止横向越权)
     *
     * @param shoppingDto
     * @return
     */
    ResponseObject<ShoppingDto> updateShopping(ShoppingDto shoppingDto);

    /**
     * 删除选中的地址(防止横向越权)
     *
     * @param id
     * @param shoppingId
     * @return
     */
    ResponseObject removeShopping(Integer id, Integer shoppingId);

    /**
     * 修改默认的收货地址
     *
     * @param userId
     * @param shoppingId
     * @return
     */
    ResponseObject selectDefaultShopping(Integer userId, Integer shoppingId);
}
