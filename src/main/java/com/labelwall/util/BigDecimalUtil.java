package com.labelwall.util;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-12-02.价格计算工具类，使用BigDecimal格式避免计算结果精度丢失
 */
public class BigDecimalUtil {

    public BigDecimalUtil() {

    }

    public static BigDecimal add(double v1, double v2) {
        //使用BigDecimal的字符串构造器
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static BigDecimal subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    public static BigDecimal multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal divide(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        //除法结果保留四舍五入两位小数
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }
}
