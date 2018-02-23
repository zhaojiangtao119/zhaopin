package com.labelwall.activity.service;

import com.labelwall.activity.vo.ActivityAccountVo;
import com.labelwall.common.ResponseObject;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 充值成功修改账户的余额
     *
     * @param accountId
     * @param jindouCount
     * @return
     */
    int updateAccountJindouNum(Integer accountId, Integer jindouCount);
}
