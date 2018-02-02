package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.UserMapper;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.*;
import com.labelwall.util.DateTimeUtil;
import com.labelwall.util.MD5Util;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017-12-02.
 */
@SuppressWarnings("unused")
@Service("userService")
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private ICountyService countyService;
    @Autowired
    private ISchoolService schoolService;


    @Override
    public ResponseObject<UserDto> login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = null;
        //判断用户使用登录名username,email
        if (validateEmail(username)) {
            //使用邮箱登录
            //1.判断邮箱是否存在
            int rowCount = userMapper.checkEmail(username);
            if (rowCount == 0) {
                return ResponseObject.fail(UserResponseMessage.EMAIL_NULL.getCode()
                        , UserResponseMessage.EMAIL_NULL.getValue());
            }
            user = userMapper.loginEmail(username, md5Password);
        } else {
            int rowCount = userMapper.checkUsername(username);
            if (rowCount == 0) {
                return ResponseObject.fail(UserResponseMessage.USERNAME_NULL.getCode()
                        , UserResponseMessage.USERNAME_NULL.getValue());
            }
            user = userMapper.login(username, md5Password);
        }

        if (user == null) {
            return ResponseObject.fail(UserResponseMessage.PASSWORD_ERROR.getCode(), UserResponseMessage.PASSWORD_ERROR.getValue());
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

    //用户登录是否使用邮箱地址
    private boolean validateEmail(String username) {
        if (username.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseObject<UserDto> register(UserDto userDto) {
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
            return ResponseObject.fail(UserResponseMessage.REGISTER_FAIL.getCode(),
                    UserResponseMessage.REGISTER_FAIL.getValue());
        }
        User userNew = userMapper.selectByPrimaryKey(user.getId());
        if (userNew != null) {
            UserDto userDtoNew = new UserDto();
            BeanUtils.copyProperties(userNew, userDtoNew);
            userDtoNew.setCreateTimeStr(DateTimeUtil.dateToStr(userNew.getCreateTime()));
            userDtoNew.setUpdateTimeStr(DateTimeUtil.dateToStr(userNew.getUpdateTime()));
            userDtoNew.setPassword(StringUtils.EMPTY);
            userDtoNew.setCreateTime(null);
            userDtoNew.setUpdateTime(null);
            if (StringUtils.isBlank(userDtoNew.getHead())) {
                //如果用户头像为null设置一个默认的头像给用户getUserHeadUrl
                userDtoNew.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            } else {
                userDtoNew.setHead(QiniuStorage.getUserHeadUrl(userDtoNew.getHead()));
            }
            return ResponseObject.successStautsData(userDtoNew);
        }
        return ResponseObject.successStautsData(null);
    }

    @Override
    public ResponseObject checkUsernameEmail(String str, String type) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(type)) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        if (Const.USERNAME.equals(type)) {
            int rowCount = userMapper.checkUsername(str);
            if (rowCount > 0) {//该用户名已被占用
                return ResponseObject.fail(UserResponseMessage.USERNAME_NOT.getCode(),
                        UserResponseMessage.USERNAME_NOT.getValue());
            }
        } else if (Const.EMAIL.equals(type)) {
            int rowCount = userMapper.checkEmail(str);
            if (rowCount > 0) {//该邮箱名已被占用
                return ResponseObject.fail(UserResponseMessage.EMAIL_NOT.getCode(),
                        UserResponseMessage.EMAIL_NOT.getValue());
            }
        } else {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        return ResponseObject.successStatus();
    }

    @Override
    public ResponseObject modifyUserInfo(UserDto userDto) {
        //TODO 判断用户是否修改了邮箱地址,是否允许修改邮箱地址
        /*int rowCountInsert = userMapper.checkEmail(userDto.getEmail());
        if (rowCountInsert > 0) {
            return ResponseObject.fail(UserResponseMessage.EMAIL_NOT.getCode(),
                    UserResponseMessage.EMAIL_NOT.getValue());
        }*/
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        //判断用户是否修改了头像
        if (userDto.getMultipartFile() != null && userDto.getMultipartFile().getSize() > 0) {
            //TODO 用户修改了头像，将用户的新头像上传到七牛云存储，是否删除用户的旧头像，
            try {
                byte[] userHead = userDto.getMultipartFile().getBytes();
                String userHeadKey = QiniuStorage.uploadUserHead(userHead);
                user.setHead(userHeadKey);
            } catch (IOException e) {
                logger.error("用户头像解析图片失败", e);
                return ResponseObject.fail(UserResponseMessage.USER_HEAD_ERROR.getCode(),
                        UserResponseMessage.USER_HEAD_ERROR.getValue());
            }
        }
        user = assembleUser(user);
        int rowCountUpdate = userMapper.updateByPrimaryKeySelective(user);
        if (rowCountUpdate == 0) {
            return ResponseObject.fail(UserResponseMessage.MODIFY_FAIL.getCode(),
                    UserResponseMessage.MODIFY_FAIL.getValue());
        }
        if (userDto.getHead() != null) {
            userDto.setHead(QiniuStorage.getUserHeadUrl(userDto.getHead()));
        }
        return ResponseObject.successStautsData(userDto);
    }

    private User assembleUser(User user) {
        if (StringUtils.isNotBlank(user.getLocationProvince())) {
            Integer provinceId = provinceService.findIdByProvinceName(user.getLocationProvince());
            if (provinceId != null) {
                user.setProvinceId(provinceId);
            }
            if (StringUtils.isNotBlank(user.getLocationCity())) {
                Integer cityId = cityService.findIdByCityName(user.getProvinceId(), user.getLocationCity());
                if (cityId != null) {
                    user.setCityId(cityId);
                }
                if (StringUtils.isNotBlank(user.getLocationCounty())) {
                    Integer countyId = countyService.findIdByCountyName(user.getCityId(), user.getLocationCounty());
                    if (countyId != null) {
                        user.setCountyId(countyId);
                    }
                }
            }
        }
        if (user.getSchoolId() != null) {
            String schoolName = schoolService.findNameBySchoolId(user.getSchoolId());
            if (schoolName != null) {
                user.setSchoolName(schoolName);
            }
        }
        return user;
    }

    @Override
    public ResponseObject modifyPassword(Integer id, String passwordOld, String passwordNew) {
        if (id == null || StringUtils.isBlank(passwordOld) || StringUtils.isBlank(passwordNew)) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        //判断旧密码是正确
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        //对旧密码加密
        String md5PasswordOld = MD5Util.MD5EncodeUtf8(passwordOld);
        if (!md5PasswordOld.equals(user.getPassword())) {
            return ResponseObject.fail(UserResponseMessage.OLD_PASSWORD_ERROR.getCode(),
                    UserResponseMessage.OLD_PASSWORD_ERROR.getValue());
        }
        if (passwordOld.equals(passwordNew)) {
            return ResponseObject.fail(UserResponseMessage.PASSWORD_SAME.getCode(),
                    UserResponseMessage.PASSWORD_SAME.getValue());
        }
        //新密码设置
        String md5PasswordNew = MD5Util.MD5EncodeUtf8(passwordNew);
        int rowCount = userMapper.restPassword(id, md5PasswordNew);
        if (rowCount > 0) {
            return ResponseObject.successStatus();
        }
        return ResponseObject.fail(UserResponseMessage.MODIFY_PASSWORD_FAIL.getCode(),
                UserResponseMessage.MODIFY_PASSWORD_FAIL.getValue());
    }

    @Override
    public UserDto selectByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        //加载用户的头像
        if (StringUtils.isNotBlank(user.getHead())) {
            user.setHead(QiniuStorage.getUserHeadUrl(user.getHead()));
        } else {
            user.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
        }
        user.setPassword(null);
        user.setEmail(null);
        user.setPhone(null);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Override
    public List<User> selectByUserIds(List<Integer> userIdList) {
        List<User> userList = userMapper.selectByUserIds(userIdList);
        for (User user : userList) {
            user.setPassword(null);
            user.setPhone(null);
            user.setEmail(null);
        }
        return userList;
    }

    @Override
    public ResponseObject<PageInfo> searchUser(UserDto userDto, Integer pageNum, Integer pageSize) {
        if (userDto != null) {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            List<User> userList = userMapper.selectUser(userDto);
            @SuppressWarnings("unchecked")
            PageInfo pageInfo = new PageInfo(userList);
            return ResponseObject.successStautsData(pageInfo);
        }
        return ResponseObject.successStautsData(null);
    }

    @Override
    public ResponseObject<UserDto> selectByUserId(Integer userId) {
        if (userId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        if (StringUtils.isBlank(user.getHead())) {
            //如果用户头像为null设置一个默认的头像给用户getUserHeadUrl
            user.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
        } else {
            user.setHead(QiniuStorage.getUserHeadUrl(user.getHead()));
        }
        UserDto userDto = new UserDto();
        user.setEmail(null);
        user.setPassword(null);
        user.setPassword(null);
        user.setPhone(null);
        BeanUtils.copyProperties(user, userDto);
        userDto.setCreateTimeStr(DateTimeUtil.dateToStr(user.getCreateTime()));
        userDto.setUpdateTimeStr(DateTimeUtil.dateToStr(user.getUpdateTime()));
        return ResponseObject.successStautsData(userDto);
    }

    @Override
    public ResponseObject<UserDto> getUserInfo(Integer userId) {
        if (userId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
        }
        if (StringUtils.isBlank(user.getHead())) {
            //如果用户头像为null设置一个默认的头像给用户getUserHeadUrl
            user.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
        } else {
            user.setHead(QiniuStorage.getUserHeadUrl(user.getHead()));
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return ResponseObject.successStautsData(userDto);
    }

    @Override
    public ResponseObject validateOldPassword(String password, Integer userId) {
        if (userId == null || StringUtils.isBlank(password)) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        User user = userMapper.selectByPrimaryKey(userId);
        //对旧密码加密
        String md5PasswordOld = MD5Util.MD5EncodeUtf8(password);
        if (user.getPassword().equals(md5PasswordOld)) {
            return ResponseObject.successStatus();
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }

    @Override
    public ResponseObject restPassword(Integer userId, String oldPassword, String newPassword) {
        //再验证一遍旧密码
        ResponseObject responseObject = this.validateOldPassword(oldPassword, userId);
        if (!responseObject.isSuccess()) {
            return responseObject;
        }
        //判断新旧密码是否一致
        if (oldPassword.equals(newPassword)) {
            return ResponseObject.failStatusMessage("新旧密码不能相同");
        }
        //新密码加密后修改
        String md5NewPassword = MD5Util.MD5EncodeUtf8(newPassword);
        User user = new User();
        user.setId(userId);
        user.setPassword(md5NewPassword);
        int rowCount = userMapper.updateByPrimaryKeySelective(user);
        if (rowCount == 1) {
            return ResponseObject.successStatus();
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }
}
