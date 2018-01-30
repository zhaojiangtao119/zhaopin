package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.exception.CustomException;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-12-02.
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    /*
        返回json数据，在方法体内最好使用try catch快将方法体包裹起来，如果方法执行出现运行时异常，
        通过捕获来返回为止错误的json数据。
    */

    @Autowired
    private IUserService userService;

    /**
     * 登录，参数：username,password 响应：userVo
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseObject<UserDto> login(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password,
                                         HttpSession session) throws Exception {
        //用户登录
        ResponseObject<UserDto> response = userService.login(username, password);
        if (response.isSuccess()) {
            //TODO 登录成功来设置用户的session或者使用shiro
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 退出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseObject logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ResponseObject.successStatus();
    }

    /**
     * 获取用户信息(用户登录后获取个人信息)
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info", method = RequestMethod.GET)
    public ResponseObject<UserDto> getUserInfo(HttpSession session) {
        //TODO 获取用户信息需要登录
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return ResponseObject.successStautsData(userDto);
    }

    @RequestMapping(value = "get_user_info/{userId}", method = RequestMethod.GET)
    public ResponseObject<UserDto> getUserInfo(@PathVariable("userId") Integer userId) {
        return userService.selectByUserId(userId);
    }

    /**
     * 注册
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseObject<UserDto> register(UserDto userDto) {
        ResponseObject response = userService.register(userDto);
        return response;
    }

    /**
     * 检测用户名邮箱的唯一性
     *
     * @param vaildStr
     * @param type
     * @return
     */
    @RequestMapping(value = "check_username_email", method = RequestMethod.POST)
    public ResponseObject checkUsernameEmail(@RequestParam(value = "vaildStr") String vaildStr,
                                             @RequestParam(value = "type") String type) {
        ResponseObject response = userService.checkUsernameEmail(vaildStr, type);
        return response;
    }

    /**
     * 修改信息
     *
     * @param session
     * @param userDtoNew
     * @return
     */
    @RequestMapping(value = "modify_user_info", method = RequestMethod.PUT)
    public ResponseObject modifyUserInfo(HttpSession session, UserDto userDtoNew) {
        //TODO 修改信息,需要登录
        UserDto userDtoOld = (UserDto) session.getAttribute(Const.CURRENT_USER);
        userDtoNew.setId(userDtoOld.getId());
        userDtoNew.setUsername(userDtoOld.getUsername());//不允许用户修改用户名
        ResponseObject response = userService.modifyUserInfo(userDtoNew);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 登录后修改用户密码
     *
     * @param passwordOld
     * @param passwordNew
     * @param session
     * @return
     */
    @RequestMapping(value = "reset_password", method = RequestMethod.POST)
    public ResponseObject resetPassword(@RequestParam("passwordOld") String passwordOld,
                                        @RequestParam("passwordNew") String passwordNew,
                                        HttpSession session) {
        //TODO 修改密码判断用户是否登录
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return userService.modifyPassword(userDto.getId(), passwordOld, passwordNew);
    }

    /**
     * 用户搜索
     *
     * @param userDto  搜索条件，schoolId,provinceId,cityId,countyId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "search_user/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> searchUser(UserDto userDto,
                                               @PathVariable("pageNum") Integer pageNum,
                                               @PathVariable("pageSize") Integer pageSize) {
        return userService.searchUser(userDto, pageNum, pageSize);
    }
}
