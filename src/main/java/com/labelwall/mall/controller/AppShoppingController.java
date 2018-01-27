package com.labelwall.mall.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
