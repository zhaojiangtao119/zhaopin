package com.labelwall.mall.dto;

import com.labelwall.mall.common.web.SessionUser;
import com.labelwall.mall.entity.User;

/**
 * Created by Administrator on 2017-12-02.
 */
public class UserDto extends User implements SessionUser {

    @Override
    public Integer getUserId() {
        return this.getId();
    }
}
