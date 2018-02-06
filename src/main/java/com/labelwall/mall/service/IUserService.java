package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;

import java.util.List;

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
    ResponseObject<UserDto> register(UserDto userDto);

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
    ResponseObject modifyPassword(Integer id, String passwordOld, String passwordNew);

    /**
     * 根据用户名获取用户对象
     *
     * @param username
     * @return
     */
    UserDto selectByUsername(String username);

    /**
     * 根据id集合获取用户信息
     *
     * @param userIdList
     * @return
     */
    List<User> selectByUserIds(List<Integer> userIdList);

    /**
     * 用户搜索
     *
     * @param userDto
     * @return
     */
    ResponseObject<PageInfo> searchUser(UserDto userDto, Integer pageNum, Integer pageSize);

    /**
     * 根据主键获取用户信息
     *
     * @param userId
     * @return
     */
    ResponseObject<UserDto> selectByUserId(Integer userId);

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    ResponseObject<UserDto> getUserInfo(Integer userId);

    /**
     * 验证旧密码是否正确
     *
     * @param password
     * @return
     */
    ResponseObject validateOldPassword(String password, Integer userId);

    /**
     * 登录后修改密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    ResponseObject restPassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 获取用户信息
     *
     * @param activityId
     * @return
     */
    User selectByActivityId(Integer activityId);
}
