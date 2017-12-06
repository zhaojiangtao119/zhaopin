package com.labelwall.mall.service.impl;

import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dao.UserMapper;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.IUserService;
import com.labelwall.util.DateTimeUtil;
import com.labelwall.util.MD5Util;
import com.labelwall.util.PropertiesUtil;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Administrator on 2017-12-02.
 */
@SuppressWarnings("unused")
@Service("userService")
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseObject<UserDto> login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount = userMapper.checkUsername(username);
        if (rowCount == 0) {
            return ResponseObject.failStatusMessage(UserResponseMessage.USERNAME_NULL.getValue());
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.login(username, md5Password);
        if (user == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.PASSWORD_ERROR.getValue());
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setCreateTimeStr(DateTimeUtil.dateToStr(user.getCreateTime()));
        userDto.setUpdateTimeStr(DateTimeUtil.dateToStr(user.getUpdateTime()));
        userDto.setPassword(StringUtils.EMPTY);
        userDto.setCreateTime(null);
        userDto.setUpdateTime(null);
        if (StringUtils.isBlank(userDto.getHead())) {
            //如果用户头像为null设置一个默认的头像给用户getUserHeadUrl
            userDto.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
        } else {
            userDto.setHead(QiniuStorage.getUserHeadUrl(userDto.getHead()));
        }
        return ResponseObject.success(UserResponseMessage.SUCCESS.getValue(), userDto);
    }

    @Override
    public ResponseObject register(UserDto userDto) {
        //验证
        ResponseObject responseObject = this.checkUsernameEmail(userDto.getUsername(), Const.USERNAME);
        if (!responseObject.isSuccess()) {
            return responseObject;
        }
        responseObject = this.checkUsernameEmail(userDto.getEmail(), Const.EMAIL);
        if (!responseObject.isSuccess()) {
            return responseObject;
        }
        userDto.setRole(Const.Role.ROLE_CUSTOMER);
        userDto.setPassword(MD5Util.MD5EncodeUtf8(userDto.getPassword()));
        userDto.setHead(Const.DEFAULT_USER_HEAD);
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        int rowCount = userMapper.insert(user);
        if (rowCount == 0) {
            return ResponseObject.failStatusMessage(UserResponseMessage.REGISTER_FAIL.getValue());
        }
        return ResponseObject.successStatusMessage(UserResponseMessage.REGISTER_SUCCESS.getValue());
    }

    @Override
    public ResponseObject checkUsernameEmail(String str, String type) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(type)) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        if (Const.USERNAME.equals(type)) {
            int rowCount = userMapper.checkUsername(str);
            if (rowCount > 0) {//该用户名已被占用
                return ResponseObject.failStatusMessage(UserResponseMessage.USERNAME_NOT.getValue());
            }
        } else if (Const.EMAIL.equals(type)) {
            int rowCount = userMapper.checkEmail(str);
            if (rowCount > 0) {//该邮箱名已被占用
                return ResponseObject.failStatusMessage(UserResponseMessage.EMAIL_NOT.getValue());
            }
        } else {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        return ResponseObject.successStatusMessage(UserResponseMessage.VAILD_PASS.getValue());
    }

    @Override
    public ResponseObject modifyUserInfo(UserDto userDto) {
        int rowCountInsert = userMapper.checkEmail(userDto.getEmail());
        if (rowCountInsert > 0) {
            return ResponseObject.failStatusMessage(UserResponseMessage.EMAIL_NOT.getValue());
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        //判断用户是否修改了头像
        if (userDto.getMultipartFile() != null && userDto.getMultipartFile().getSize() > 0) {
            //TODO 用户修改了头像，将用户的新头像上传到七牛云存储，是否删除用户的旧头像，
            try {
                byte[] userhead = userDto.getMultipartFile().getBytes();
                String userheadKey = QiniuStorage.uploadUserHead(userhead);
                user.setHead(userheadKey);
            } catch (IOException e) {
                logger.error("用户头像解析图片失败", e);
                return ResponseObject.failStatusMessage("上传图片失败！");
            }
        }
        int rowCountUpdate = userMapper.updateByPrimaryKeySelective(user);
        if (rowCountUpdate == 0) {
            return ResponseObject.failStatusMessage(UserResponseMessage.MODIFY_FAIL.getValue());
        }
        if (userDto.getHead() != null) {
            userDto.setHead(QiniuStorage.getUserHeadUrl(userDto.getHead()));
        }
        return ResponseObject.success(UserResponseMessage.MODIFY_SUCCESS.getValue(), userDto);
    }

    @Override
    public ResponseObject restPassword(Integer id, String passwordOld, String passwordNew) {
        if(id == null || StringUtils.isBlank(passwordOld) || StringUtils.isBlank(passwordNew)){
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        //判断旧密码是正确
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        //对旧密码加密
        String md5PasswordOld = MD5Util.MD5EncodeUtf8(passwordOld);
        if(!md5PasswordOld.equals(user.getPassword())){
            return ResponseObject.failStatusMessage("旧密码不正确");
        }
        if(passwordOld.equals(passwordNew)){
            return ResponseObject.failStatusMessage("新密码与旧密码不能相同");
        }
        //将新密码设置进去
        String md5PasswordNew = MD5Util.MD5EncodeUtf8(passwordNew);
        int rowCount = userMapper.restPassword(id,md5PasswordNew);
        if(rowCount > 0){
            return ResponseObject.successStatusMessage("修改成功");
        }
        return ResponseObject.failStatusMessage("修改失败");
    }
}
