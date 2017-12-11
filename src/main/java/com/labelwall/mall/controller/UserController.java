package com.labelwall.mall.controller;

import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-12-02.
 */
@RestController
@RequestMapping("/user/")
public class UserController {

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
                                         HttpSession session) {
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
     * 获取用户信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info", method = RequestMethod.GET)
    public ResponseObject<User> getUserInfo(HttpSession session) {
        //TODO 获取用户信息需要登录
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto != null) {
            return ResponseObject.successStautsData(userDto);
        }
        return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
    }

    /**
     * 注册
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseObject register(UserDto userDto) {
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
        if (userDtoOld == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
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
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return userService.restPassword(userDto.getId(), passwordOld, passwordNew);
    }
}
