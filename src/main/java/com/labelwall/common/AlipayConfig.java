package com.labelwall.common;

/**
 * Created by Administrator on 2018-01-24.
 */
public class AlipayConfig {
   /* //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    //合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String partner = "2088821126570439";

    //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
    public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMQ9fGuJWmnZM6Mp\n" +
            "/A4EsXq0j5vNmki0XTq4t6E8C6ywI1Jsd+XGN289TjgnG03ARnqOY7tNripW/vo9\n" +
            "xLwYlPjU+Vn3lchlV4r0sWYyei8wCIackF78EEnErW0e5MenJ2/Zr1/I4WIbcWvA\n" +
            "RfJUPojEntU5zAfomkqq5rmQafqXAgMBAAECgYEAwnPa0gQsny4myMtVg96Krvg0\n" +
            "RUKaWui+aO2YqCADwX5XwdjRhdHAJG4vD91QZN7d95sYoAT9S98gkX6bb3c8RQek\n" +
            "3eqAe0/H/dXwlVYQ1bJ4kRk6QX6UXdiNLjHdpmpeah+7cB3JM6Zl83tRfnOYMxRS\n" +
            "7wchn42Uc4rugYeNAEECQQDzql5npq+zUhHPwdZs40BdDFFGqWS5JkJliIdXHpt9\n" +
            "Z45/+85ipehprf9jg6JVJiSMsBYN+PjK3II62BpbWSfpAkEAziyJz70+0oXWYA7J\n" +
            "MJ3vXO8bQ2iRPZO+D4yQYektYI6mPfFhdWV2AZOjOztH2w3U9eL4ZwAteTa86dgb\n" +
            "1Sb+fwJABnzl0Jq2mO65fK+rlvCiGRgn3drKzMdBA1UUTXSKLVehirTYtPW374Br\n" +
            "iWnK27uNQ5W4sJyNe1FNwmVYplAEaQJBAM13VV/SGWl0N9BC6/2nZ4QWRfVHoHbd\n" +
            "ZevZCYqJ9s7jWokgpR95ncC0gVfmHBYSEsl5CFSduLOz+VCSLiqeUcECQCZdcnJy\n" +
            "H16VDAS6Q85EwEwcGY2gxsiG/7RMrcRkcQG4FrDNO2SrOKguWbNgCIaFWzeyy+Ob\n" +
            "LhQK9AkKHmafqg8=";

    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtOgg+uBmkT9/06Oop77OqckLBXxaCKos3Engvibu8W1qRF+D0cTOozlDiej40s0q1k2k/l66dwlUK5n6Uwuz6naD72WjS93lMM0WInKFqhKulQpb60Gwh/wf4zMxIEv/QDSomHamPdOYUd+cqR734KV1uZqKLW/tCsltyc7fcN4UDKcq0752Nh0TQx/wSYSwuJwK2ySKL4uwOIWe0W3xJpq3CvzefmqVZ/XhfafTFxFTz+R0OFRoHXgut/c00jM/FFKSGmWH10zWCVES59mqkvc5vEF+5KQVg6ahhh6bajvHDD3LlbTjrvvuEBpi+bmu4xg7P+eLuCmmSOyFIKoLcwIDAQAB";

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
    public static String app_id = "2018010601630841";*/

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2018010601630841";

    /** 支付宝网关*/
    public static final String GATE = "https://openapi.alipay.com/gateway.do";

    /** 支付宝私钥*/
    public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCocFDx+6vlWCOWAAaYsOjNQnmV53liziC3Xb/SAw/MLqRdKVdrOTQ0/681+f5XNxB2dj9BOrjVbwUQjsNH26tD2deIpCC9o3iMJ6dvE9jDkl2l/2kakZjE/fLC3aHRlpkvY14qwjkYMzIVmSUc/5ijW9NyMCHHWEl1hAhdicmI44safKoFGBmLyn/2W+x2KA/q6c73C7hmmQK5biXGwQCAovvc50/m2ubTwurOlzRu880ul+sjv0aBXeZbwYBoA8p4iBHIEQ/jDXouiUeunQ7o+FMYJAT7bDIivrDeIGlcC5TWj7ifmhfk9uH+HoByArrfgiQbs8zJnwOYD3/xGksDAgMBAAECggEBAIHTOACbO02lFqCZ2nu792OltfMdm4jVWZAbmSq0o2t8E8GeiLujNqMW46QV0LzGO1EjKPQM38PaMywk7U0oeiPs9txhrL6eDd7w/XN2958d5EJ6zzbkO7yyA3fAveW+TsmgrEcGtlz5sMPG6z0joDFhKGOdx5tPaCdEofH+rrBreT5p5L2EkQpg8NTw3H4/gvPfDt42YpyD+21/G7Z997pvVRWwB/KKmrumZ2R7EWp20KLUW2T47/nY93KX5U9HCzLozyScu66pDuBQCC0edWfGtLpzsa4aktVhHpJPEabGQDzl91J4OeakmabEYeGLKGgtrZNir357zE3tapnKEUECgYEA2F8pF8+EVrjtYawmQW1EIjFwaKOOJs6i7tEI2WsytdlSVR9M/HnGhFm3TCCNqVIQI4tZBZALtfX9L1Ve1ZRKLC3SC3eQtDctEZpl0kH2L71n0gkvf9HrPC62/1gA02585UtbnVbBsNQ2+aNm7jsa6PisXJw1syT9L0qODxmBPKECgYEAx0nDD7N5lkgCvfppEhZBMGB+yIMiLacxUWPviDPf/zBg/OlqRd8Q+489NHhlLGTuBrVRdv3RkpFSplVK356bBTfXwxhssVgO55s0KlilLSfIfiyGWexGo3TfxeZymYg31IoXtZ5Dg1CsxPUjkSaLvA1FNzysufVWrppwdyK2YSMCgYEAvrBvKNrsiPmFy9TKClNf/rqPlMjAGRd3pP14zRSapoWW+AoT8VbpS+89icwOzTDTpF/E3xAqIf1fW33jjFuaWwEu4ohHcWxHPgJSxlD3xO8qNoN95yp8JJgPE07du3jefb5CSZISNeNxZ+VIa6CPgM7YKO5KrU1mDW2pcUlLCsECgYAxzASpz7b6DlRhFElc86Kma/ZCNEI+FiEErWwVJKE8KuKRT+H2Q58CvPAWRF9DkJTJqFUK3rGXZii412c2KXOjAikv3tKKHe7bgLSwQCZF+kLnBEkt/xRHk9S2prbMFnWc7kSuZTWJgocpT/frNS6FdutsugSPEvbt+jlQC6R28QKBgHQwg3IwPkShpfo5f+mDilU5F5cO94INm7lLaX9e6qFXMKjQ4cmJ3l6J38+x+PQFGtw+qAVxKb4S7qRQ42mOltbKJfUF5TsGnVwhLKEkipmtryC4V6kZJ05nFq+NHqh4O6VyYmyemJp14M2o+8oHU90zogi2//YbT/ciJ8vLP27R";

    /** 支付宝公钥 */
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtOgg+uBmkT9/06Oop77OqckLBXxaCKos3Engvibu8W1qRF+D0cTOozlDiej40s0q1k2k/l66dwlUK5n6Uwuz6naD72WjS93lMM0WInKFqhKulQpb60Gwh/wf4zMxIEv/QDSomHamPdOYUd+cqR734KV1uZqKLW/tCsltyc7fcN4UDKcq0752Nh0TQx/wSYSwuJwK2ySKL4uwOIWe0W3xJpq3CvzefmqVZ/XhfafTFxFTz+R0OFRoHXgut/c00jM/FFKSGmWH10zWCVES59mqkvc5vEF+5KQVg6ahhh6bajvHDD3LlbTjrvvuEBpi+bmu4xg7P+eLuCmmSOyFIKoLcwIDAQAB";

    /** 编码方式 */
    public static final String CHARSET = "utf-8";

    /*
        支付宝中创建应用，
        1.设置该应用的RSA(SHA256)的公钥与密钥，
        2.应用上线
        3.使用应用私钥进行请求参数的加签
        4.通过支付宝的公钥验证返回结果是否是支付宝返回的
    */
}
