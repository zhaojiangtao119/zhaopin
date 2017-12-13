package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;

/**
 * Created by Administrator on 2017-12-02.
 */
public interface IUserService {
    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    ResponseObject<UserDto> login(String username, String password);

    /**
     * 用户注册
     *
     * @param userDto
     * @return
     */
    ResponseObject register(UserDto userDto);

    /**
     * 校验用户名或邮箱的唯一性
     *
     * @param str
     * @param type
     * @return
     */
    ResponseObject checkUsernameEmail(String str, String type);

    /**
     * 修改用户信息
     *
     * @param userDto
     * @return
     */
    ResponseObject modifyUserInfo(UserDto userDto);

    /**
     * 登录后的重置密码
     *
     * @param id
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    ResponseObject restPassword(Integer id, String passwordOld, String passwordNew);

    /**
     * 根据用户名获取用户对象
     * @param username
     * @return
     */
    UserDto selectByUsername(String username);
}
