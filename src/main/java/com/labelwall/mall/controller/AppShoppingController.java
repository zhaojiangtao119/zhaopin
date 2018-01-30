package com.labelwall.mall.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018-01-27.
 */
@RestController
@RequestMapping("/app/shopping/")
public class AppShoppingController {

    @Autowired
    private IShoppingService shoppingService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseObject<List<ShoppingDto>> getShoppingListByUserId(Integer userId) {
        return shoppingService.getShoppingByUserId(userId);
    }

    /**
     * 删除收货地址
     *
     * @param userId
     * @param shoppingId
     * @return
     */
    @RequestMapping(value = "remove", method = RequestMethod.DELETE)
    public ResponseObject removeShopping(Integer userId, Integer shoppingId) {
        return shoppingService.removeShopping(userId, shoppingId);
    }

    /**
     * 修改选择的收货地址
     *
     * @param userId
     * @param shoppingId
     * @param orderNo    若不为null则修改订单的配送地址，若为null修改默认的配送地址
     * @return
     */
    @RequestMapping(value = "default", method = RequestMethod.PUT)
    public ResponseObject selectDefaultShopping(Integer userId, Integer shoppingId, Long orderNo) {
        return shoppingService.selectDefaultShopping(userId, shoppingId, orderNo);
    }

    /**
     * 添加收货地址
     *
     * @param shoppingDto
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseObject addShopping(ShoppingDto shoppingDto) {
        return shoppingService.addShopping(shoppingDto);
    }

    /**
     * 获取指定的配送地址
     *
     * @param userId
     * @param shoppingId
     * @return
     */
    @RequestMapping(value = "/{userId}/{shoppingId}", method = RequestMethod.GET)
    public ResponseObject<ShoppingDto> getShoppingById(@PathVariable("userId") Integer userId,
                                                       @PathVariable("shoppingId") Integer shoppingId) {
        return shoppingService.getShoppingById(userId, shoppingId);
    }

    /**
     * 修改配送信息
     *
     * @param shoppingDto
     * @return
     */
    @RequestMapping(value = "/modify/{shoppingId}", method = RequestMethod.POST)
    public ResponseObject updateShopping(@PathVariable("shoppingId") Integer shoppingId,
                                         ShoppingDto shoppingDto) {
        if (shoppingId != null) {
            shoppingDto.setId(shoppingId);
            return shoppingService.updateShopping(shoppingDto);
        }
        return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                ResponseStatus.ERROR_PARAM.getValue());
    }
}
