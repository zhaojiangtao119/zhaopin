package com.labelwall.mall.controller;

import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
                                                       HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return shoppingService.getShoppingById(userDto.getId(), shoppingId);
    }

    /**
     * 获取用户的收货地址列表
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_shopping_by_user_id", method = RequestMethod.GET)
    public ResponseObject<List<ShoppingDto>> getShoppingByUserId(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
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
    public ResponseObject<ShoppingDto> addShopping(HttpSession session, ShoppingDto shoppingDto) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
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
    public ResponseObject<ShoppingDto> updateShopping(HttpSession session, ShoppingDto shoppingDto) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
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
    public ResponseObject removeShopping(HttpSession session,
                                         @PathVariable(value = "shoppingId") Integer shoppingId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return shoppingService.removeShopping(userDto.getId(), shoppingId);
    }
}
