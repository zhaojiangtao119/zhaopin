package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dao.*;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.dto.ShopCartDto;
import com.labelwall.mall.entity.Order;
import com.labelwall.mall.entity.OrderItem;
import com.labelwall.mall.entity.Product;
import com.labelwall.mall.entity.Shopping;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.vo.OrderItemVo;
import com.labelwall.mall.vo.OrderProductVo;
import com.labelwall.mall.vo.OrderVo;
import com.labelwall.mall.vo.ShoppingVo;
import com.labelwall.util.BigDecimalUtil;
import com.labelwall.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017-12-08.
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ShopCartMapper shopCartMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ShoppingMapper shoppingMapper;

    @Override
    public ResponseObject<OrderVo> createOrder(Integer userId, Integer shoppingId) {
        //获取购物车中被选中的商品
        List<ShopCartDto> shopCartDtoList = shopCartMapper.selectCheckedCartByUserId(userId);
        //组装订单明细OrderItem
        ResponseObject response = this.getShopCartOrderItem(userId, shopCartDtoList);
        if (!response.isSuccess()) {
            return response;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) response.getData();
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ResponseObject.failStatusMessage("购物车为空");
        }
        //计算订单总额
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);
        Order order = this.assembleOrder(userId, shoppingId, payment);
        if (order == null) {
            return ResponseObject.failStatusMessage("订单生成失败");
        }
        //设置订单明细的订单号
        for (OrderItem item : orderItemList) {
            item.setOrderNo(order.getOrderNo());
        }
        //将订单明细插入数据库中
        orderItemMapper.batchInsert(orderItemList);
        //减少商品表的stock
        this.reduceProductStock(orderItemList);
        //清除购物车
        this.cleanCart(shopCartDtoList);
        //组装返回的展示的数据
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
        return ResponseObject.successStautsData(orderVo);
    }


    private ResponseObject<List<OrderItem>> getShopCartOrderItem(Integer userId, List<ShopCartDto> shopCartDtoList) {
        if (CollectionUtils.isEmpty(shopCartDtoList)) {
            return ResponseObject.failStatusMessage("购物车为空");
        }
        List<OrderItem> orderItemList = new ArrayList<>();
        //校验购物车中商品数据，商品的状态与数量
        for (ShopCartDto shopCartDtoItem : shopCartDtoList) {
            OrderItem orderItem = new OrderItem();
            ProductDto productDto = shopCartDtoItem.getProductDto();
            //商品状态
            if (productDto.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
                return ResponseObject.failStatusMessage("商品" + productDto.getName() + "不在售卖状态");
            }
            //商品数量
            if (shopCartDtoItem.getQuantity() > productDto.getStock()) {
                return ResponseObject.failStatusMessage("商品" + productDto.getName() + "库存不足");
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(productDto.getId());
            orderItem.setProductName(productDto.getName());
            orderItem.setProductImage(productDto.getMainImage());
            orderItem.setCurrentUnitPrice(productDto.getPrice());
            orderItem.setQuantity(shopCartDtoItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.multiply(productDto.getPrice().doubleValue(), shopCartDtoItem.getQuantity().doubleValue()));

            orderItemList.add(orderItem);
        }
        return ResponseObject.successStautsData(orderItemList);
    }

    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem item : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), item.getTotalPrice().doubleValue());
        }
        return payment;
    }

    private Order assembleOrder(Integer userId, Integer shoppingId, BigDecimal payment) {
        Order order = new Order();
        long orderNo = this.generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShoppingId(shoppingId);
        //创建订单
        int rowCount = orderMapper.insertSelective(order);
        if (rowCount > 0) {
            return order;
        }
        return null;
    }

    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }

    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem item : orderItemList) {
            ProductDto productDto = productMapper.selectByPrimaryKey(item.getProductId());
            Product product = new Product();
            product.setStock(productDto.getStock() - item.getQuantity());
            product.setId(productDto.getId());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    private void cleanCart(List<ShopCartDto> shopCartDtoList) {
        for (ShopCartDto item : shopCartDtoList) {
            shopCartMapper.deleteByPrimaryKey(item.getId());
        }
    }

    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());
        orderVo.setShoppingId(order.getShoppingId());
        Shopping shopping = shoppingMapper.selectByPrimaryKey(order.getUserId(), order.getShoppingId());
        if (shopping != null) {
            orderVo.setReceiverName(shopping.getReceiverName());
            orderVo.setShoppingVo(this.assembleShoppingVo(shopping));
        }
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        if (order.getPaymentTime() != null) {
            orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        }
        if (order.getSendTime() != null) {
            orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        }
        if (order.getEndTime() != null) {
            orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        }
        if (order.getCloseTime() != null) {
            orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        }
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = this.assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    private ShoppingVo assembleShoppingVo(Shopping shopping) {
        ShoppingVo shoppingVo = new ShoppingVo();
        shoppingVo.setReceiverName(shopping.getReceiverName());
        shoppingVo.setReceiverAddress(shopping.getReceiverAddress());
        shoppingVo.setReceiverProvince(shopping.getReceiverProvince());
        shoppingVo.setReceiverCity(shopping.getReceiverCity());
        shoppingVo.setReceiverCounty(shopping.getReceiverCounty());
        shoppingVo.setReceiverPhone(shopping.getReceiverPhone());
        shoppingVo.setReceiverMobile(shopping.getReceiverMobile());
        shoppingVo.setReceiverZip(shopping.getReceiverZip());
        return shoppingVo;
    }

    private OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }

    @Override
    public ResponseObject cancelOrder(Integer userId, Long orderNo) {
        if (orderNo == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        Order order = orderMapper.selectByUserIdOrderNo(userId, orderNo);
        if (order == null) {
            return ResponseObject.failStatusMessage("该订单不存在");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) {
            return ResponseObject.failStatusMessage("无法取消，订单已支付");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int rowCount = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (rowCount > 0) {
            return ResponseObject.successStatusMessage("取消成功");
        }
        return ResponseObject.failStatusMessage("取消失败");
    }

    @Override
    public ResponseObject<PageInfo> userOrderList(Integer userId, Integer pageNum, Integer pageSize) {
        List<Order> orderList = orderMapper.userOrderList(userId);
        PageHelper.startPage(pageNum, pageSize);
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList, userId);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    private List<OrderVo> assembleOrderVoList(List<Order> orderList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order item : orderList) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(item.getOrderNo(), userId);
            OrderVo orderVo = this.assembleOrderVo(item, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    @Override
    public ResponseObject<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
        if (orderNo == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        Order order = orderMapper.selectByUserIdOrderNo(userId, orderNo);
        if (order == null) {
            return ResponseObject.failStatusMessage("");
        }
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
        return ResponseObject.successStautsData(orderVo);
    }

    @Override
    public ResponseObject<OrderProductVo> getOrderCartProduct(Integer userId) {
        OrderProductVo orderProductVo = new OrderProductVo();
        List<ShopCartDto> shopCartDtoList = shopCartMapper.selectCheckedCartByUserId(userId);
        ResponseObject response = this.getShopCartOrderItem(userId, shopCartDtoList);
        if (!response.isSuccess()) {
            return response;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) response.getData();
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            OrderItemVo orderItemVo = this.assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setProductTotalPrice(payment);
        return ResponseObject.successStautsData(orderProductVo);
    }

    @Override
    public ResponseObject orderPay(Long orderNo, Integer id, String path) {
        Map<String, String> resultMap = Maps.newHashMap();
        return null;
    }
}
