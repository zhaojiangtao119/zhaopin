package com.labelwall.common;

/**
 * Created by Administrator on 2018-01-31.
 */
public class AlipayTradeStatus {

    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    public static final String TRADE_CLOSED = "TRADE_CLOSED";//未付款交易超时关闭，或支付完成后全额退款
    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";//交易创建等待买家付款
    public static final String TRADE_FINISHED = "TRADE_FINISHED";//交易结束，不可退款

}
