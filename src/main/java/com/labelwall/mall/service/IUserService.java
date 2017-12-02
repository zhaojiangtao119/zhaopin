package com.labelwall.mall.service;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;

/**
 * Created by Administrator on 2017-12-02.
 */
public interface IUserService {

    ResponseObject<UserDto> login(String username,String password);

}
