package com.labelwall.activity.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.google.common.collect.Lists;
import com.labelwall.activity.dao.ActivityAccountAddMapper;
import com.labelwall.activity.dao.ActivityAccountOrderMapper;
import com.labelwall.activity.dao.ActivityAccountTradeHistoryMapper;
import com.labelwall.activity.entity.ActivityAccountAdd;
import com.labelwall.activity.entity.ActivityAccountOrder;
import com.labelwall.activity.entity.ActivityAccountTradeHistory;
import com.labelwall.activity.entity.BaseBean;
import com.labelwall.activity.service.IActivityAccountOrderService;
import com.labelwall.activity.service.IActivityAccountService;
import com.labelwall.activity.vo.ActivityAccountAddVo;
import com.labelwall.activity.vo.ActivityAccountOrderVo;
import com.labelwall.common.*;
import com.labelwall.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018-02-22.
 */
@Service("activityAccountOrderService")
public class ActivityAccountOrderServiceImpl implements IActivityAccountOrderService {

    @Autowired
    private ActivityAccountOrderMapper activityAccountOrderMapper;
    @Autowired
    private ActivityAccountAddMapper activityAccountAddMapper;
    @Autowired
    private IActivityAccountService activityAccountService;
    @Autowired
    private ActivityAccountTradeHistoryMapper tradeHistoryMapper;

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
                addVo = changOrderStatus(addVo);
                addVoList.add(addVo);
            }
        }
        return addVoList;
    }

    private ActivityAccountAddVo changOrderStatus(ActivityAccountAddVo addVo) {
        if (addVo.getStatus() == Const.ActivityAddOrderStatus.NO_PAY.getCode()) {
            addVo.setStatusDesc(Const.ActivityAddOrderStatus.NO_PAY.getValue());
        } else if (addVo.getStatus() == Const.ActivityAddOrderStatus.PAID.getCode()) {
            addVo.setStatusDesc(Const.ActivityAddOrderStatus.PAID.getValue());
        } else if (addVo.getStatus() == Const.ActivityAddOrderStatus.FAIL_PAY.getCode()) {
            addVo.setStatusDesc(Const.ActivityAddOrderStatus.FAIL_PAY.getValue());
        }
        return addVo;
    }

    @Override
    public ResponseObject<ActivityAccountAddVo> createAccountAddOrder(ActivityAccountAdd activityAccountAdd) {
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
        activityAccountAdd.setTypeDesc("充值");
        activityAccountAdd.setStatus(1);
        activityAccountAdd.setOrderInfo("个人账户豆子充值");
        //生成订单号：
        String todayStr = DateTimeUtil.changeDateFormat(DateTimeUtil.dateToStr(new Date()));
        Integer orderCount = activityAccountAddMapper.getTodayOrderNum(todayStr);
        String orderNo = this.assembleOrderNo(orderCount);
        if (orderNo == null) {
            return ResponseObject.failStatusMessage("生成订单失败");
        }
        activityAccountAdd.setOrderNo(orderNo);
        //创建订单
        int rowCount = activityAccountAddMapper.createAccountAddOrder(activityAccountAdd);
        if (rowCount < 1) {
            return ResponseObject.failStatusMessage("生成订单失败");
        }
        ActivityAccountAddVo addVo = new ActivityAccountAddVo();
        BeanUtils.copyProperties(activityAccountAdd, addVo);
        addVo = changOrderStatus(addVo);
        return ResponseObject.successStautsData(addVo);
    }

    //生成订单号：年+月+日+时+分+秒+（当日已经生成的订单数+1）
    private String assembleOrderNo(Integer orderCount) {
        //获取今天的订单数
        String orderNum = String.valueOf(orderCount);
        final int size = 5 - orderNum.length();
        for (int i = 0; i < size; i++) {
            orderNum = "0" + orderNum;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmss");
        String orderNo = format.format(new Date()) + orderNum;
        return orderNo;
    }

    @Override
    public String activityAccountOrderSign(Long orderNo, Integer userId) {
        String signOrder = createSignOrder(orderNo, userId);
        return signOrder;
    }

    @SuppressWarnings("Duplicates")
    private String createSignOrder(Long orderNo, Integer userId) {
        //TODO 通过userId，orderNo获取该条订单信息，订单的付款金额，订单的Item详情，
        ActivityAccountAdd activityAccountAdd = this.getAccountOrderDetail(userId, orderNo);
        //需要支付的订单信息，将订单信息必要参数设置到AlipayTradeAppPayModel中，形成签名信息

        AlipayTradeAppPayResponse response = null;
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATE,
                AlipayConfig.APPID,
                AlipayConfig.APP_PRIVATE_KEY,
                "json",
                AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");//支付的内容
        model.setSubject("App支付测试Java");//支付的title
        model.setOutTradeNo(String.valueOf(orderNo));//订单号
        model.setTimeoutExpress("60m");//支付过期时间
        model.setTotalAmount("0.01");//支付金额
        model.setProductCode("QUICK_MSECU11RITY_PAY");
        request.setBizModel(model);
        //支付宝请求回调的服务端地址
        request.setNotifyUrl("http://dg4vnv.natappfree.cc/zhaopin/activity/account/trade/callbacks");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            response = alipayClient.sdkExecute(request);
            System.err.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ActivityAccountAdd getAccountOrderDetail(Integer userId, Long orderNo) {
        return activityAccountAddMapper.getAccountOrderDetail(userId, orderNo);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String activityAccountAlipayCallback(Map parameterMap) {
        boolean validateFlag = true;
        Map<String, String> params = new HashMap<>();
        for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext(); ) {
            String name = (String) iterator.next();
            String[] values = (String[]) parameterMap.get(name);
            String valueStr = "";
            int size = values.length;
            for (int i = 0; i < size; i++) {
                valueStr =
                        (i == size - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //解决乱码
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        try {
            //验证支付宝公钥
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            if (flag) {
                if (AlipayTradeStatus.TRADE_SUCCESS.equals(params.get("trade_status"))) {
                    //付款金额
                    String amount = params.get("buyer_pay_amount");
                    //商户订单号
                    String outTradeNo = params.get("out_trade_no");
                    //支付宝交易号
                    String tradeNo = params.get("trade_no");
                    //商户的AppId
                    String appId = params.get("app_id");
                    //附加参数
                    //String passbackParams = URLDecoder.decode(params.get("passback_params"));
                    validateFlag = validateOrderParam(outTradeNo, amount, appId, tradeNo);
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();

        }
        return "SUCCESS";
    }

    @SuppressWarnings("Duplicates")
    private boolean validateOrderParam(String outTradeNo, String amount, String appId, String tradeNo) {
        boolean validateFlag = false;
        if (StringUtils.isNotBlank(outTradeNo) && StringUtils.isNotBlank(tradeNo) &&
                StringUtils.isNotBlank(amount) && StringUtils.isNotBlank(appId)) {
            //通过支付宝返回的orderNo去查询该订单
            ActivityAccountAdd activityAccountAdd = activityAccountAddMapper.validateOrderParam(Long.valueOf(outTradeNo));
            if (activityAccountAdd == null) {
                return validateFlag;//不是该商城的订单
            }

            //TODO 测试金额为0.01
            activityAccountAdd.setOrderPrice(0.01);
            //验证订单金额
            if (!amount.equals(String.valueOf(activityAccountAdd.getOrderPrice()))) {
                return validateFlag;//支付金额错误
            } else if (!AlipayConfig.APPID.equals(appId)) {
                return validateFlag;//商户appid错误
            }
            //验证成功后修改修改订单信息（订单状态，已支付），
            activityAccountAddMapper.updateAddOrderStatus(outTradeNo, 2);
            //修改用户的额账户的金豆余额
            int rowCount = activityAccountService.
                    updateAccountJindouNum(activityAccountAdd.getAccountId(), activityAccountAdd.getJindouCount());
            //修改账户的充值记录
            updateUserAccountHistory(activityAccountAdd);
            validateFlag = true;
            return validateFlag;
        }
        return validateFlag;
    }

    private void updateUserAccountHistory(ActivityAccountAdd activityAccountAdd) {
        //需要的参数有：accountId,orderId,userId,jindouNum,tradeType,orderType,createTime
        ActivityAccountTradeHistory tradeHistory = new ActivityAccountTradeHistory();
        tradeHistory.setAccountId(activityAccountAdd.getAccountId());
        tradeHistory.setOrderId(activityAccountAdd.getId());
        tradeHistory.setUserId(activityAccountAdd.getUserId());
        tradeHistory.setJindouNum(activityAccountAdd.getJindouCount());
        tradeHistory.setTradeType(1);//正1表示充值/收入
        tradeHistory.setOrderType(0);//0表示用户对账户的充值记录，1是账户对活动的支出或收入
        int rowCount = tradeHistoryMapper.insertSelective(tradeHistory);
    }

    @Override
    public ResponseObject<ActivityAccountOrderVo> createAccountOrder(ActivityAccountOrder activityAccountOrder) {
        //需要前端传递的参数有，userId，金豆num，activityTitle，activityType
        if (activityAccountOrder.getUserId() == null ||
                activityAccountOrder.getOrderPrice() == null ||
                activityAccountOrder.getOrderInfo() == null ||
                activityAccountOrder.getType() == null) {
            return ResponseObject.
                    fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        //组装活动订单对象
        if (activityAccountOrder.getType() == 0) {
            activityAccountOrder.setTypeDesc("发起活动");
        } else if (activityAccountOrder.getType() == 1) {
            activityAccountOrder.setTypeDesc("参与活动");
        }
        //订单号
        String todayStr = DateTimeUtil.changeDateFormat(DateTimeUtil.dateToStr(new Date()));
        Integer orderCount = activityAccountOrderMapper.getTodayOrderNum(todayStr);
        String orderNo = this.assembleOrderNo(orderCount);
        if (orderNo == null) {
            return ResponseObject.failStatusMessage("生成订单失败");
        }
        activityAccountOrder.setOrderNo(orderNo);
        activityAccountOrder.setCreateTime(DateTimeUtil.dateToStr(new Date()));
        activityAccountOrder.setStatus(0);//订单未支付
        int rowCount = activityAccountOrderMapper.createAccountOrder(activityAccountOrder);
        if (rowCount < 1) {
            return ResponseObject.failStatusMessage("生成订单失败");
        }
        ActivityAccountOrderVo orderVo = new ActivityAccountOrderVo();
        BeanUtils.copyProperties(activityAccountOrder, orderVo);
        if (orderVo.getStatus() == Const.ActivityOrderStatus.NO_PAY.getCode()) {
            orderVo.setStatusDesc(Const.ActivityOrderStatus.NO_PAY.getValue());
        } else if (orderVo.getStatus() == Const.ActivityOrderStatus.PAID.getCode()) {
            orderVo.setStatusDesc(Const.ActivityOrderStatus.PAID.getValue());
        }
        return ResponseObject.successStautsData(orderVo);
    }
}
