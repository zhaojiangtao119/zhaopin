package com.labelwall.common;

/**
 * Created by Administrator on 2018-01-24.
 */
public class AlipayConfig {
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    //合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String partner = "2088102172254060";

    //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
    public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzhunGQciRLBM9jruC6FGRdY3W1n7+mNIVw9qmoqZRxMgquM5laYuxsrvb/LzWyar8FTYMO23zPcVnpcY+FHp4HZVTNnqoPqvs3KzconmenSds+d9hlJxCSO1sybNfCZOiHlKU8g0TmyAlIAQwYSKUoI1ZRi+oQ5q6rmYf/28eQPd7VTyP6IftNI9Du+orjAVFM+ASk+ourUyjE4w8eqyDfRDk3OY8/KKj+cFuwjUh0pyJGruG84fWC3/ZXAlsFuJZIyEKghfo8ZSi7TEu5WPzfihlbOdDK7VZ93/j1kUcIPYGA1ikj9Z/amiwFz9Pj36gDqT3aTdbPWPhsntRRwYLAgMBAAECggEAXDM7uKBsW3JhZ0n4r2CVE7ll4SJEEol1y8vMbaw28uO3UYmcJqUwI1EB/hcs/LqR3+kc2udirnvY+ERBLfzicPHwkM337JRTQ9CJzr5666UWbHdf7AOgxMIjfa2RO4v1kFs2Wlx9rsYKmkZ5IEeitOZH8cOEEky2GI4c0O4kqUojwBp4zSk2xY+9n77q4aLjl/ZNEBhCxpxcVkUE86Cb1Mksba98tU6HyPyTXLeMI5UdFNFg8EyUb7ybvOVedwYSsuTTT9yFVSHh0aXGIvWbP8EcMTnDE3vt2Ywze3g3OiNd53kvAf02hHAPQ2FCBdRdH49sXYbF8H9PZCTkGzv0gQKBgQDi+MVb7R1Z9xKt3wQlOukOejdfSxF3vIsBzwZ3YgzckZ4GTUXjGvcHvMvFAbvUZXm8dvv8etNNZo5JywenI7sy5MDtyz8pJvNni2rHhn/1pE9QAF/v+gWHMELyKP0Z5yPT6rfs6QtTL9LE7JMFrNOHpvsXh1bXJLj1nHX0Fb5pqwKBgQDKfMO+Mvip1zuzfO5UUpJ3DXpjbsTTK9i8YUCUVraHa1xNNqU/XaCudDSxGiyhDti9rWYnCbX7sxE91T8TteT9URC5WXUfZIWpJd9b0xzuAJiNgRoD8p3XeYGWDaIoUGVhLwgDdi4ukYQcJ4urE8khhWLeGVNCJQtr4uesupE1IQKBgDYO3Cql0dzQjOFegYVAf6uUDkYjAfkLrevzW+3pK3kJMaL8mo2ZqYZtP/cfD5ZpyNHLYP7kXhlMOM14js0PJJHdi9nXE6cAgO6DdR4qeaWOydkbDb6BRPnK09cLTiTyOUVK3R2O78STWlbOpcGvRP5FFf6nnFZHh8nvxuQonZ5bAoGBALliCs/62KCedoE4svaudnva8h9QzZDMhuzJWRe2X/yRdXsXuajO7oppx8gRP6Tg89yMgYbiJCPh6Lokn1BLau/lJRuc5FcucBTRoLHwEX9xOjnrZccRfCNHkjWwG7MvD/Iu3fZVX6iw/zPLnNqvDajL9x2CMDDRu6g1oIgKc2EhAoGABYuulP8FGA/Jz32te8I9a6HUVXewkwOmXSSut9tjxHV4/ZaSoormhphYMhMkEGQ9+V9AEme2g9pTb0BWWoCgqnNsIzhgHT6nc8DVLj+QCeWUokT8xhOJqme9mG9WCaFGqHZLPLMeJEniX+zlkyyWspf4WcWkVJMB2OAFS05dLWs=";

    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzfA6vuOuzAgKysU8H41ypifG/gxLMsSt96jYWq86uJMiP3+2QPPnN1x7th98q0fU0SJv6Uxmhfnri2/xNXPGauDnL0S/D8Ou64R9oF5YTTV+nSv4p39mmbcvIFyStk5zTWqtShhfrWMkfIJDp09GPkjTC5oCi/zsS0Nn0zowGW+LQLK+zu3sPMu7eloYXJ0TB+CL3IXfsAybFhfzjnv1uaFLHdTovLplr8cL+ES6vaaTV4D1LsTimmpIwb4ZbcYLfbwdVdFEMLxs/NPvAPtRErAkPdtlLA3k0NKh81BQKUXO6jZVLTqDUA96ySqig/67PD/OqWA2449G2hZoDrwdzQIDAQAB";

    // 签名方式
    public static String sign_type = "RSA";

    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
    public static String log_path = "E://";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";

    // 接收通知的接口名
    public static String service = "http://60.***.***.00/callbacks";
    //public static String service = "mobile.securitypay.pay";

    //APPID
    public static String app_id = "2016082000296528";
}
