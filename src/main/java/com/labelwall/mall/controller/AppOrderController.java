package com.labelwall.mall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.AlipayConfig;
import com.labelwall.common.AlipayTradeStatus;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018-01-24.
 */
@RestController
@RequestMapping("/app/order/")
public class AppOrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * APP创建订单，返回订单号
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseObject<OrderVo> createOrder(Integer userId) {
        ResponseObject<OrderVo> orderVoInfo = orderService.createAppOrder(userId);
        return orderVoInfo;
    }

    /**
     * 根据orderNo，生成订单签名，将签名返回给app客户端
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public ResponseObject<String> appCreateOrder(Long orderNo, Integer userId) {
        String signOrder = orderService.appOrderSign(orderNo, userId);
        return ResponseObject.successStautsData(signOrder);
    }

    /**
     * 支付宝支付完成后的回调接口  POST请求
     * 支付宝支付结果的通知，一个Map
     * 验签的过程
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/callbacks", method = RequestMethod.POST)
    public String callbacks(HttpServletRequest request) {
       /* //接受支付宝返回的请求参数
        Map<String, String[]> params = request.getParameterMap();
        JSONObject jsonParams = JSON.parseObject(JSON.toJSONString(params));

        //支付状态
        String trade_status = jsonParams.getString("trade_status")
                .substring(2, jsonParams.getString("trade_status").length() - 2);
        //订单号
        String out_trade_no = jsonParams.getString("out_trade_no")
                .substring(2, jsonParams.getString("out_trade_no").length() - 2);
        String notify_id = jsonParams.getString("notify_id")
                .substring(2, jsonParams.getString("notify_id").length() - 2);

        if (trade_status.equals("TRADE_SUCCESS")) {
            // 支付成功后的逻辑实现，改变订单状态

        } else {
            // 支付失败，???

        }*/

        Map<String, String> params = new HashMap<>();
        for (Iterator iterator = request.getParameterMap().keySet().iterator(); iterator.hasNext(); ) {
            String name = (String) iterator.next();
            String[] values = (String[]) request.getParameterMap().get(name);
            String valueStr = "";
            int size = values.length;
            for (int i = 0; i < size; i++) {
                valueStr =
                        (i == size - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //解决乱码
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
            try {
                boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
                if (flag) {
                   if(AlipayTradeStatus.TRADE_SUCCESS.equals("trade_status")){
                        //付款金额
                       String amount = params.get("buyer_pay_amount");
                       //商户订单号
                       String outTradeNo = params.get("out_trade_no");
                       //支付宝交易号
                       String tradeNo = params.get("trade_no");
                       //附加参数
                       String passbackParams = URLDecoder.decode(params.get("passback_params"));
                   }
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
        return "SUCCESS";
    }

    /**
     * APP取消订单
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cancel", method = RequestMethod.PUT)
    public ResponseObject cancelOrder(Integer userId, Long orderNo) {
        return orderService.cancelOrder(userId, orderNo);
    }

    /**
     * APP获取用户的所有订单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "user_order_list/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> userOrderList(Integer userId,
                                                  @PathVariable(value = "pageNum") Integer pageNum,
                                                  @PathVariable(value = "pageSize") Integer pageSize) {
        return orderService.getUserOrderList(userId, pageNum, pageSize);
    }

    /**
     * 获取订单详情
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResponseObject<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
        return orderService.getOrderDetail(userId, orderNo);
    }
}
