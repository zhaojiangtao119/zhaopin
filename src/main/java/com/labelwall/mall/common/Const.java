package com.labelwall.mall.common;

import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.util.PropertiesUtil;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-12-04.
 */
public class Const {

    public static final String DEFAULT_USER_HEAD = PropertiesUtil.getProperty("userInfo.head");

    public static final String CURRENT_USER = "courrent_user";

    public static final String USERNAME = "username";

    public static final String EMAIL = "email";

    public static ResponseObject<UserDto> isLogin(HttpSession session){
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if(userDto == null){
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return ResponseObject.successStautsData(userDto);
    }

    public interface Role {
        int ROLE_ADMIN = 0;
        int ROLE_CUSTOMER = 1;
    }

    public interface PostClickType {
        Integer LIKE_CLICK = 0;
        Integer DISLIKE_CLICK = 1;
    }
}
