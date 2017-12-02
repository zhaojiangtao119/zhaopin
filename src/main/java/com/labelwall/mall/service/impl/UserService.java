package com.labelwall.mall.service.impl;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dao.UserMapper;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.IUserService;
import com.labelwall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-12-02.
 */
@SuppressWarnings("unused")
@Service("userService")
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseObject<UserDto> login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount = userMapper.checkUsername(username);
        if(rowCount == 0){
            //return ResponseObject.failStatusMessage(UserResponseMessage.USERNAME_NULL.getValue());
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.login(username,md5Password);
        if(user == null){
            return ResponseObject.failStatusMessage(UserResponseMessage.PASSWORD_ERROR.getValue());
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        userDto.setPassword(StringUtils.EMPTY);
        return ResponseObject.success(UserResponseMessage.SUCCESS.getValue(),userDto);
    }
}
