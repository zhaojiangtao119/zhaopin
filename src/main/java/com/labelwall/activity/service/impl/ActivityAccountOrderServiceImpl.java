package com.labelwall.activity.service.impl;

import com.google.common.collect.Lists;
import com.labelwall.activity.dao.ActivityAccountAddMapper;
import com.labelwall.activity.dao.ActivityAccountOrderMapper;
import com.labelwall.activity.entity.ActivityAccountAdd;
import com.labelwall.activity.entity.ActivityAccountOrder;
import com.labelwall.activity.service.IActivityAccountOrderService;
import com.labelwall.activity.vo.ActivityAccountAddVo;
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
    @Autowired
    private ActivityAccountAddMapper activityAccountAddMapper;

    @Override
    public ResponseObject<List<ActivityAccountOrderVo>> getUserAcitivtyOrder(Integer userId) {
        if (userId == null) {
            return ResponseObject.
                    fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.name());
        }
        List<ActivityAccountOrder> orderList = activityAccountOrderMapper.getUserActivityOrder(userId);
        return ResponseObject.successStautsData(assembleActivityAccountOrderVo(orderList));
    }

    @Override
    public ResponseObject<List<ActivityAccountAddVo>> getUserAccountAddList(Integer userId, Integer accountId) {
        if (userId == null || accountId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        List<ActivityAccountAdd> addList = activityAccountAddMapper.getUserAccountAddList(userId, accountId);
        return ResponseObject.successStautsData(assembleActivityAccountAddVo(addList));
    }

    private List<ActivityAccountOrderVo> assembleActivityAccountOrderVo(List<ActivityAccountOrder> orderList) {
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
        return orderVoList;
    }

    private List<ActivityAccountAddVo> assembleActivityAccountAddVo(List<ActivityAccountAdd> orderList) {
        List<ActivityAccountAddVo> addVoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (ActivityAccountAdd add : orderList) {
                ActivityAccountAddVo addVo = new ActivityAccountAddVo();
                BeanUtils.copyProperties(add, addVo);
                if (add.getStatus() == Const.ActivityOrderStatus.NO_PAY.getCode()) {
                    addVo.setStatusDesc(Const.ActivityOrderStatus.NO_PAY.getValue());
                } else if (add.getStatus() == Const.ActivityOrderStatus.PAID.getCode()) {
                    addVo.setStatusDesc(Const.ActivityOrderStatus.PAID.getValue());
                }
                addVoList.add(addVo);
            }
        }
        return addVoList;
    }
}
