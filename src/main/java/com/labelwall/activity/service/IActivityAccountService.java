package com.labelwall.activity.service;

import com.labelwall.activity.vo.ActivityAccountVo;
import com.labelwall.common.ResponseObject;

/**
 * Created by Administrator on 2018-02-22.
 */
public interface IActivityAccountService {
    /**
     * 获取用户的账户信息
     *
     * @param userId
     * @return
     */
    ResponseObject<ActivityAccountVo> getUserAccount(Integer userId);
}
