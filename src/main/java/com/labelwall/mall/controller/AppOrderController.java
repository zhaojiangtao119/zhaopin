package com.labelwall.mall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.OrderItem;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        ResponseObject<OrderVo> orderVoInfo = orderService.createOrder(userId, 3);
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
     * 支付宝支付完成后的回调接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/callbacks", method = RequestMethod.POST)
    public String callbacks(HttpServletRequest request) {
        //接受支付宝返回的请求参数
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
            //TODO 支付成功后的逻辑实现，改变订单状态

        } else {
            //TODO 支付失败，???

        }
        return "SUCCESS";
    }
}
