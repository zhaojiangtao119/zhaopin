package com.labelwall.common;

import com.labelwall.util.PropertiesUtil;

/**
 * Created by Administrator on 2017-12-04.
 */
public class Const {

    public static final String DEFAULT_USER_HEAD = PropertiesUtil.getProperty("userInfo.head");

    public static final String CURRENT_USER = "courrent_user";

    public static final String USERNAME = "username";

    public static final String EMAIL = "email";

    public interface Role {
        int ROLE_ADMIN = 0;
        int ROLE_CUSTOMER = 1;
    }

    public interface RedisCacheExTime{
        int REDIS_SESSION_EXTIME = 60 * 30;//设置登录session的在redis中保存的时间
    }

    public interface PostClickType {
        int LIKE_CLICK = 0;
        int DISLIKE_CLICK = 1;
    }

    //购物车商品是否勾选
    public interface Cart {
        int CHECKED = 1;
        int UN_CHECKED = 0;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    //商品状态
    public enum ProductStatusEnum {
        ON_SALE(1, "在售");

        private int code;
        private String value;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    //商品订单状态
    public enum OrderStatusEnum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭"),
        ORDER_FAILURE(70, "订单过期");

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    //支付方式
    public enum PaymentTypeEnum {
        ONLINE_PAY(1, "在线支付");

        private String value;
        private int code;

        PaymentTypeEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    //支付平台
    public enum PayPlatformEnum {
        ALIPAY(1, "支付宝"),
        WEICHAT(2, "微信");

        private int code;
        private String value;

        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    //支付宝回调的交易状态
    public interface AlipayCallback {
        String TRADE_STATUS_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    //订单过期限制
    public interface OrderFailureDate {
        long ORDER_NO_QR = 1440;
        long ORDER_QR = 120;
    }

    //活动订单状态
    public enum ActivityOrderStatus {
        NO_PAY(0, "未支付"),
        PAID(1, "已支付"),;

        private int code;
        private String value;

        ActivityOrderStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    //活动账户充值订单状态
    public enum ActivityAddOrderStatus {
        FAIL_PAY(0, "已过期"),
        PAID(1, "未支付"),
        NO_PAY(2,"已支付");

        private int code;
        private String value;

        ActivityAddOrderStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
}
