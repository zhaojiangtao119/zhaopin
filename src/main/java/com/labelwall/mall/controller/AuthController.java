package com.labelwall.mall.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.message.UserResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017-12-12.
 */
@RestController
@RequestMapping("/auth/")
public class AuthController {

    @RequestMapping("noLogin")
    public ResponseObject noLogin() {
        return ResponseObject.fail(UserResponseMessage.NOT_LOGIN.getCode(), UserResponseMessage.NOT_LOGIN.getValue());
    }

}
