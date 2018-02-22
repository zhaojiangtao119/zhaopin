package com.labelwall.activity.dao;

import com.labelwall.activity.entity.ActivityAccountAdd;
import com.labelwall.activity.entity.ActivityAccountOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018-02-22.
 */
public interface ActivityAccountAddMapper {
    /**
     * 获取账户充值的交易记录
     *
     * @param userId
     * @param accountId
     * @return
     */
    List<ActivityAccountAdd> getUserAccountAddList(@Param("userId") Integer userId,
                                                   @Param("accountId") Integer accountId);
}
