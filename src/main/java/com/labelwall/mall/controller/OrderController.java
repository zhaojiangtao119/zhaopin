package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.vo.OrderProductVo;
import com.labelwall.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-12-08.
 */
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "create_order/{shoppingId}", method = RequestMethod.POST)
    public ResponseObject<OrderVo> createOrder(HttpSession session,
                                               @PathVariable("shoppingId") Integer shoppingId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.createOrder(userDto.getId(), shoppingId);
    }

    @RequestMapping(value = "cancel_order/{orderNo}", method = RequestMethod.PUT)
    public ResponseObject cancelOrder(HttpSession session,
                                      @PathVariable("orderNo") Long orderNo) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.cancelOrder(userDto.getId(), orderNo);
    }

    @RequestMapping(value = "user_order_list/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> userOrderList(HttpSession session,
                                                  @PathVariable(value = "pageNum") Integer pageNum,
                                                  @PathVariable(value = "pageSize") Integer pageSize) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.userOrderList(userDto.getId(), pageNum, pageSize);
    }

    @RequestMapping(value = "get_order_detail/{orderNo}", method = RequestMethod.GET)
    public ResponseObject<OrderVo> getOrderDetail(HttpSession session,
                                                  @PathVariable("orderNo") Long orderNo) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.getOrderDetail(userDto.getId(), orderNo);
    }

    /**
     * 获取购物车中已经选中的商品详情
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_order_cart_product", method = RequestMethod.GET)
    public ResponseObject<OrderProductVo> getOrderCartProduct(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.getOrderCartProduct(userDto.getId());
    }

    @RequestMapping(value = "order_pay")
    public ResponseObject orderPay(HttpSession session, HttpServletRequest request, Long orderNo){
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if(userDto == null){
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        String path = session.getServletContext().getRealPath("upload");
        return orderService.orderPay(orderNo,userDto.getId(),path);
    }
}
