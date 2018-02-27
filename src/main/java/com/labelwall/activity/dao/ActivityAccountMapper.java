package com.labelwall.activity.dao;

import com.labelwall.activity.entity.ActivityAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-02-22.
 */
public interface ActivityAccountMapper {

    ActivityAccount getUserAccount(Integer userId);

    /**
     * 充值成功修改账户金豆余额
     *
     * @param accountId
     * @param jindouCount
     * @return
     */
    int updateAccountJindouNum(@Param("accountId") Integer accountId,
                               @Param("jindouCount") Integer jindouCount);

    int updateAccountSubJindouNum(@Param("accountId") Long accountId,
                                  @Param("jindouCount") Integer jindouCount);
}
