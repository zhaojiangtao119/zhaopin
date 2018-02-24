package com.labelwall.activity.dao;

import com.labelwall.activity.entity.ActivityAccountTradeHistory;

/**
 * Created by Administrator on 2018-02-23.
 */
public interface ActivityAccountTradeHistoryMapper {
    /**
     * 插入订单历史记录
     *
     * @param tradeHistory
     * @return
     */
    int insertSelective(ActivityAccountTradeHistory tradeHistory);
}
