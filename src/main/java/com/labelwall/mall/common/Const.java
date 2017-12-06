package com.labelwall.mall.common;

import com.labelwall.util.PropertiesUtil;

/**
 * Created by Administrator on 2017-12-04.
 */
public class Const {

    public static final String DEFAULT_USER_HEAD = PropertiesUtil.getProperty("userInfo.head");

    public static final String CURRENT_USER = "courrent_user";

    public static final String USERNAME = "username";

    public static final String EMAIL = "email";

    public interface Role {
        int ROLE_ADMIN = 0;
        int ROLE_CUSTOMER = 1;
    }

    public interface PostClickType {
        Integer LIKE_CLICK = 0;
        Integer DISLIKE_CLICK = 1;
    }
}
