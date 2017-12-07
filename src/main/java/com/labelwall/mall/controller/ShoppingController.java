package com.labelwall.mall.controller;

import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "get_shopping_by_id", method = RequestMethod.GET)
    public ResponseObject<ShoppingDto> getShoppingById(@RequestParam(value = "shoppingId") Integer shoppingId,
                                                       HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shoppingService.getShoppingById(userDto.getId(), shoppingId);
    }

    @RequestMapping(value = "get_shopping_by_user_id", method = RequestMethod.GET)
    public ResponseObject<List<ShoppingDto>> getShoppingByUserId(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shoppingService.getShoppingByUserId(userDto.getId());
    }

    @RequestMapping(value = "add_shopping", method = RequestMethod.POST)
    public ResponseObject<ShoppingDto> addShopping(HttpSession session, ShoppingDto shoppingDto) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        shoppingDto.setUserId(userDto.getId());
        return shoppingService.addShopping(shoppingDto);
    }

    @RequestMapping(value = "update_shopping", method = RequestMethod.POST)
    public ResponseObject<ShoppingDto> updateShopping(HttpSession session, ShoppingDto shoppingDto) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        shoppingDto.setUserId(userDto.getId());
        return shoppingService.updateShopping(shoppingDto);
    }

    @RequestMapping(value = "remove_shopping", method = RequestMethod.GET)
    public ResponseObject removeShopping(HttpSession session,
                                         @RequestParam(value = "shoppingId") Integer shoppingId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return shoppingService.removeShopping(userDto.getId(),shoppingId);
    }
}
