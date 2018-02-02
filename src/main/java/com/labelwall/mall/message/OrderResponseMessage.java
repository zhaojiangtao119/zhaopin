package com.labelwall.mall.message;

/**
 * Created by Administrator on 2017-12-30.
 */
public enum OrderResponseMessage {
    SHOPCART_NULL(20001,"购物车为空"),
    PRODUCT_NOT_SALE(20002,"商品不在售卖状态"),
    PRODUCT_NUM_NOT(20003,"商品库存不足"),
    ORDER_ISNULL(20004,"订单不存在"),
    ORDER_PAY_NOT_CANCEL(20005,"订单已支付无法取消"),
    ORDER_NOT_PAY(20006,"订单未支付"),
    ORDER_FAILURE(20007,"订单已过期")
    ;

    private Integer code;
    private String value;

    OrderResponseMessage(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
