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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-12-08.
 */
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "create_order")
    public ResponseObject<OrderVo> createOrder(HttpSession session, @RequestParam("shoppingId") Integer shoppingId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if(userDto == null){
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.createOrder(userDto.getId(),shoppingId);
    }

    @RequestMapping(value = "cancel_order")
    public ResponseObject cancelOrder(HttpSession session,Long orderNo){
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if(userDto == null){
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.cancelOrder(userDto.getId(),orderNo);
    }

    @RequestMapping(value = "user_order_list")
    public ResponseObject<PageInfo> userOrderList(HttpSession session,
                                                  @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if(userDto == null){
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.userOrderList(userDto.getId(),pageNum,pageSize);
    }

    @RequestMapping(value = "get_order_detail")
    public ResponseObject<OrderVo> getOrderDetail(HttpSession session,Long orderNo){
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if(userDto == null){
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.getOrderDetail(userDto.getId(),orderNo);
    }

    @RequestMapping(value = "get_order_cart_product")
    public ResponseObject<OrderProductVo> getOrderCartProduct(HttpSession session){
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if(userDto == null){
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return orderService.getOrderCartProduct(userDto.getId());
    }
}
