package com.labelwall.mall.controller;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017-12-02.
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    //登录，参数：username,password 响应：userVo
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseObject<UserDto> login(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password) {
        //用户登录
        ResponseObject<UserDto> response = userService.login(username, password);
        if(response.isSuccess()){
            //TODO 登录成功来设置用户的session或者使用shiro
        }
        return response;
    }

}
