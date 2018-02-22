package com.labelwall.activity.service.impl;

import com.google.common.collect.Lists;
import com.labelwall.activity.dao.ActivityAccountOrderMapper;
import com.labelwall.activity.entity.ActivityAccountOrder;
import com.labelwall.activity.service.IActivityAccountOrderService;
import com.labelwall.activity.vo.ActivityAccountOrderVo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018-02-22.
 */
@Service("activityAccountOrderService")
public class ActivityAccountOrderServiceImpl implements IActivityAccountOrderService {

    @Autowired
    private ActivityAccountOrderMapper activityAccountOrderMapper;

    @Override
    public ResponseObject<List<ActivityAccountOrderVo>> getUserAcitivtyOrder(Integer userId) {
        if (userId == null) {
            return ResponseObject.
                    fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.name());
        }
        List<ActivityAccountOrder> orderList = activityAccountOrderMapper.getUserActivityOrder(userId);
        List<ActivityAccountOrderVo> orderVoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (ActivityAccountOrder order : orderList) {
                ActivityAccountOrderVo orderVo = new ActivityAccountOrderVo();
                BeanUtils.copyProperties(order, orderVo);
                if (order.getStatus() == Const.ActivityOrderStatus.NO_PAY.getCode()) {
                    orderVo.setStatusDesc(Const.ActivityOrderStatus.NO_PAY.getValue());
                } else if (order.getStatus() == Const.ActivityOrderStatus.PAID.getCode()) {
                    orderVo.setStatusDesc(Const.ActivityOrderStatus.PAID.getValue());
                }
                orderVoList.add(orderVo);
            }
        }
        return ResponseObject.successStautsData(orderVoList);
    }
}
