package com.labelwall.mall.controller;

import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2018-02-01.
 */
@RestController
@RequestMapping("/app/user/")
public class AppUserController {

    @Autowired
    private IUserService userService;

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "info/{userId}", method = RequestMethod.GET)
    public ResponseObject<UserDto> getUserInfo(@PathVariable("userId") Integer userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 修改用户信息
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public ResponseObject modifyUserInfo(UserDto userDto) {
        return userService.modifyUserInfo(userDto);
    }

    /**
     * 验证旧密码是否正确
     *
     * @param password
     * @param userId
     * @return
     */
    @RequestMapping(value = "password/validate", method = RequestMethod.POST)
    public ResponseObject validataOldPassword(String password, Integer userId) {
        return userService.validateOldPassword(password, userId);
    }

    /**
     * 登录后修改用户的密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "password/rest", method = RequestMethod.POST)
    public ResponseObject restPassword(Integer userId, String oldPassword, String newPassword) {
        return userService.restPassword(userId, oldPassword, newPassword);
    }
}
