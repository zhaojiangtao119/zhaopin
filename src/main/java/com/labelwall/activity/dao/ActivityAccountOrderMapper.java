package com.labelwall.activity.dao;

import com.labelwall.activity.entity.ActivityAccountOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018-02-22.
 */
public interface ActivityAccountOrderMapper {
    /**
     * 获取活动订单的列表
     *
     * @param userId
     * @return
     */
    List<ActivityAccountOrder> getUserActivityOrder(Integer userId);

}
