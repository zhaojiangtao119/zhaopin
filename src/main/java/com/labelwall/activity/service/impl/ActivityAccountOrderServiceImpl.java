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
import com.labelwall.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

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
        //验证订单的时效性，过期时间设置为1个小时
        for (ActivityAccountAdd activityAccountAdd : orderList) {
            if (activityAccountAdd.getStatus() == 1) {
                String createTime = activityAccountAdd.getCreateTime();
                long time = DateTimeUtil.dateInterval(DateTimeUtil.strToDate(createTime), new Date());
                if (time >= 60) {
                    //修改该订单的状态
                    activityAccountAddMapper.updateAddOrderStatus(activityAccountAdd.getOrderNo(), 0);
                    activityAccountAdd.setStatus(0);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (ActivityAccountAdd add : orderList) {
                ActivityAccountAddVo addVo = new ActivityAccountAddVo();
                BeanUtils.copyProperties(add, addVo);
                if (add.getStatus() == Const.ActivityAddOrderStatus.NO_PAY.getCode()) {
                    addVo.setStatusDesc(Const.ActivityAddOrderStatus.NO_PAY.getValue());
                } else if (add.getStatus() == Const.ActivityAddOrderStatus.PAID.getCode()) {
                    addVo.setStatusDesc(Const.ActivityAddOrderStatus.PAID.getValue());
                } else if (add.getStatus() == Const.ActivityAddOrderStatus.FAIL_PAY.getCode()) {
                    addVo.setStatusDesc(Const.ActivityAddOrderStatus.FAIL_PAY.getValue());
                }
                addVoList.add(addVo);
            }
        }
        return addVoList;
    }

    @Override
    public ResponseObject<ActivityAccountAdd> createAccountAddOrder(ActivityAccountAdd activityAccountAdd) {
        //校验数据 userId，account，price，jindouCount
        if (activityAccountAdd.getUserId() == null ||
                activityAccountAdd.getAccountId() == null ||
                activityAccountAdd.getOrderPrice() == null ||
                activityAccountAdd.getJindouCount() == null) {
            return ResponseObject.
                    fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        //构建订单对象
        activityAccountAdd.setCreateTime(DateTimeUtil.dateToStr(new Date()));
        activityAccountAdd.setType(1);
        activityAccountAdd.setTypeDesc("存入");
        activityAccountAdd.setStatus(1);
        activityAccountAdd.setOrderInfo("个人账户豆子充值");
        //生成订单号：
        String orderNo = this.assembleOrderNo();
        if (orderNo == null) {
            return ResponseObject.failStatusMessage("生成订单失败");
        }
        activityAccountAdd.setOrderNo(orderNo);
        //创建订单
        int rowCount = activityAccountAddMapper.createAccountAddOrder(activityAccountAdd);
        if (rowCount < 1) {
            return ResponseObject.failStatusMessage("生成订单失败");
        }
        return ResponseObject.successStautsData(activityAccountAdd);
    }

    //生成订单号：年+月+日+时+分+秒+（当日已经生成的订单数+1）
    private String assembleOrderNo() {
        //获取今天的订单数
        String todayStr = DateTimeUtil.changeDateFormat(DateTimeUtil.dateToStr(new Date()));
        Integer orderCount = activityAccountAddMapper.getTodayOrderNum(todayStr);
        String orderNum = String.valueOf(orderCount);
        final int size = 5 - orderNum.length();
        for (int i = 0; i < size; i++) {
            orderNum = "0" + orderNum;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmss");
        String orderNo = format.format(new Date()) + orderNum;
        return orderNo;
    }
}
