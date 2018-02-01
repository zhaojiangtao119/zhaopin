package com.labelwall.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.Data;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.labelwall.common.*;
import com.labelwall.mall.dao.*;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.dto.ShopCartDto;
import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.entity.*;
import com.labelwall.mall.message.OrderResponseMessage;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.service.IProductService;
import com.labelwall.mall.service.IShoppingService;
import com.labelwall.mall.vo.OrderItemVo;
import com.labelwall.mall.vo.OrderProductVo;
import com.labelwall.mall.vo.OrderVo;
import com.labelwall.mall.vo.ShoppingVo;
import com.labelwall.util.BigDecimalUtil;
import com.labelwall.util.CommonUtil;
import com.labelwall.util.DateTimeUtil;
import com.labelwall.util.PropertiesUtil;
import com.labelwall.util.storage.QiniuKeyGenerator;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Administrator on 2017-12-08.
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

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
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private IShoppingService shoppingService;

    private static AlipayTradeService tradeService;


    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Override
    public ResponseObject<OrderVo> createOrder(Integer userId) {
        List<Shopping> shoppingList = shoppingMapper.getShoppingByUserIdSelected(userId, 1);
        Integer shoppingId = null;
        if (CollectionUtils.isNotEmpty(shoppingList)) {
            shoppingId = shoppingList.get(0).getId();
        }
        //获取购物车中被选中的商品
        List<ShopCartDto> shopCartDtoList = shopCartMapper.selectCheckedCartByUserId(userId);
        //组装订单明细OrderItem
        ResponseObject response = this.getShopCartOrderItem(userId, shopCartDtoList);
        if (!response.isSuccess()) {
            return response;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) response.getData();
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ResponseObject.fail(OrderResponseMessage.SHOPCART_NULL.getCode(),
                    OrderResponseMessage.SHOPCART_NULL.getValue());
        }
        //计算订单总额
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);
        Order order = this.assembleOrder(userId, shoppingId, payment);
        if (order == null) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
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

    @Override
    public ResponseObject<OrderVo> buyProduct(Integer userId, Integer productId, Integer quantity) {
        if (productId == null || quantity == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        } else if (product.getStock() < quantity) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        //生成订单，
        //1.根据商品信息生成订单明细
        OrderItem orderItem = new OrderItem();
        orderItem.setUserId(userId);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        if (StringUtils.isNotBlank(product.getMainImage())) {
            product.setMainImage(QiniuStorage.getUrl(product.getMainImage()));
        }
        orderItem.setProductImage(product.getMainImage());
        orderItem.setQuantity(quantity);
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setTotalPrice(BigDecimalUtil.multiply(product.getPrice().doubleValue(), quantity.doubleValue()));
        List<OrderItem> orderItemList = Lists.newArrayList();
        orderItemList.add(orderItem);
        //2.计算订单总额
        BigDecimal payment = orderItem.getTotalPrice();
        //3.生成订单
        //查询用户默认选择的收货地址
        List<Shopping> shoppingList = shoppingMapper.getShoppingByUserIdSelected(userId, 1);
        Integer shoppingId = null;
        if (CollectionUtils.isNotEmpty(shoppingList)) {
            shoppingId = shoppingList.get(0).getId();
        }
        Order order = this.assembleOrder(userId, shoppingId, payment);
        if (order == null) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
        }
        //4.为订单明细设置订单号
        orderItem.setOrderNo(order.getOrderNo());
        //5.将订单明插入数据库
        orderItemMapper.insertSelective(orderItem);
        //6.修改商品表中商品的数量
        this.reduceProductStock(orderItemList);
        //7.组装返回的展示的数据
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
        return ResponseObject.successStautsData(orderVo);
    }

    private ResponseObject<List<OrderItem>> getShopCartOrderItem(Integer userId, List<ShopCartDto> shopCartDtoList) {
        if (CollectionUtils.isEmpty(shopCartDtoList)) {
            return ResponseObject.fail(OrderResponseMessage.SHOPCART_NULL.getCode(),
                    OrderResponseMessage.SHOPCART_NULL.getValue());
        }
        List<OrderItem> orderItemList = new ArrayList<>();
        //校验购物车中商品数据，商品的状态与数量
        for (ShopCartDto shopCartDtoItem : shopCartDtoList) {
            OrderItem orderItem = new OrderItem();
            ProductDto productDto = shopCartDtoItem.getProductDto();
            //商品状态
            if (productDto.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
                return ResponseObject.fail(OrderResponseMessage.PRODUCT_NOT_SALE.getCode(),
                        productDto.getName() + OrderResponseMessage.PRODUCT_NOT_SALE.getValue());
            }
            //商品数量
            if (shopCartDtoItem.getQuantity() > productDto.getStock()) {
                return ResponseObject.fail(OrderResponseMessage.PRODUCT_NUM_NOT.getCode(),
                        productDto.getName() + OrderResponseMessage.PRODUCT_NUM_NOT.getValue());
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(productDto.getId());
            orderItem.setProductName(productDto.getName());
            if (StringUtils.isNotBlank(productDto.getMainImage())) {
                productDto.setMainImage(QiniuStorage.getUrl(productDto.getMainImage()));
            }
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

    //TODO 订单号的生成，
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
            return ResponseObject.fail(OrderResponseMessage.ORDER_ISNULL.getCode(),
                    OrderResponseMessage.ORDER_ISNULL.getValue());
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) {
            return ResponseObject.fail(OrderResponseMessage.ORDER_PAY_NOT_CANCEL.getCode(),
                    OrderResponseMessage.ORDER_PAY_NOT_CANCEL.getValue());
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int rowCount = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (rowCount > 0) {
            return ResponseObject.successStatus();
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }

    @Override
    public ResponseObject<PageInfo> getUserOrderList(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.userOrderList(userId);
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
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        Order order = orderMapper.selectByUserIdOrderNo(userId, orderNo);
        if (order == null) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
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
    public ResponseObject orderPay(Long orderNo, Integer userId, String path) {
        Map<String, String> resultMap = Maps.newHashMap();
        //获取订单
        Order order = orderMapper.selectByUserIdOrderNo(userId, orderNo);
        if (order == null) {
            return ResponseObject.fail(OrderResponseMessage.ORDER_ISNULL.getCode(),
                    OrderResponseMessage.ORDER_ISNULL.getValue());
        }
        resultMap.put("orderNo", String.valueOf(order.getOrderNo()));

        // 点单号(必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // 订单标题(必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("happymmall扫码支付，订单号：").append(outTradeNo).toString();

        // 订单总金额(必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        //商品明细列表，须填写购买商品的详细信息
        List<GoodsDetail> goodsDetailList = new ArrayList<>();

        //获取要支付订单的明细，组装goodsDetailList
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
        for (OrderItem orderItem : orderItemList) {
            GoodsDetail goodsDetail = GoodsDetail.newInstance(String.valueOf(orderItem.getProductId()), orderItem.getProductName()
                    , BigDecimalUtil.multiply(orderItem.getCurrentUnitPrice().doubleValue(), new Double(100)).longValue()
                    , orderItem.getQuantity());
            goodsDetailList.add(goodsDetail);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //扫码付款成功后，支付宝服务器主动通知商户服务器里指定的页面http（接口）路径,根据需要设置，回调地址
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);

        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                //生成二维码图片
                File folder = new File(path);
                if (!folder.exists()) {//创建图片的临时存储路径
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                // 需要修改为运行机器上的路径，%s占位符，response.getOutTradeNo():此订单号
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());
                //二维码图片的名称qr-订单号
                String qrFileName = String.format(QiniuKeyGenerator.generatorOrderPayImageKey() + "-%s.png",
                        response.getOutTradeNo());
                //ZxingUtil生成二维码图片，存放在qrPath
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
                File targetFile = new File(qrPath);
                //获取图片名称，当做是存储空间中的key
                String key = qrFileName.substring(0, qrFileName.lastIndexOf("."));
                //将二维码存储到七牛上，判断图片是否存在，如果用户生成了支付二维码并没有支付，之后再进行支付会生成一个相同内容的二维码
                if (!QiniuStorage.fileIsExist(qrFileName)) {
                    String qrKey = QiniuStorage.updateOrderPayImage(CommonUtil.getFileByte(targetFile), key);
                    String url = QiniuStorage.getUrl(qrKey);
                    //将二维码的访问路径返回出去
                    resultMap.put("qrUrl", url);
                } else {
                    String url = QiniuStorage.getUrl(qrFileName);
                    resultMap.put("qrUrl", url);
                }
                //删除临时文件夹upload
                folder.delete();
                return ResponseObject.successStautsData(resultMap);
            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return ResponseObject.failStatusMessage("支付宝预下单失败!!!");
            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return ResponseObject.failStatusMessage("系统异常，预下单状态未知!!!");
            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return ResponseObject.failStatusMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    //简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

    @Override
    public ResponseObject aliCallback(Map<String, String> params) {
        //支付宝回调参数中获取订单号
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        //支付宝的交易号
        String tradeNo = params.get("trade_no");
        //交易状态
        String tradeStatus = params.get("trade_status");
        //获取该订单
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ResponseObject.failStatusMessage("非系统订单，忽略回调");
        }
        //判断订单的状态
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ResponseObject.successStatusMessage("支付宝重复调用");
        }
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            //交易成功修改订单状态
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }
        //添加账单详情，支付详情
        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);
        payInfoMapper.insertSelective(payInfo);
        return ResponseObject.successStatus();
    }

    @Override
    public ResponseObject queryOrderPayStatus(Integer userId, Long orderNo) {
        if (orderNo == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        Order order = orderMapper.selectByUserIdOrderNo(userId, orderNo);
        if (order == null) {
            return ResponseObject.fail(OrderResponseMessage.ORDER_ISNULL.getCode(),
                    OrderResponseMessage.ORDER_ISNULL.getValue());
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ResponseObject.successStatus();
        }
        return ResponseObject.fail(OrderResponseMessage.ORDER_NOT_PAY.getCode(),
                OrderResponseMessage.ORDER_NOT_PAY.getValue());
    }

    //-----------------------------APP---------------------------------------------------------
    @Override
    public String appOrderSign(Long orderNo, Integer userId) {
        String signOrder = createSignOrder(orderNo, userId);
        return signOrder;
    }

    private String createSignOrder(Long orderNo, Integer userId) {
        //TODO 通过userId，orderNo获取该条订单信息，订单的付款金额，订单的Item详情，
        ResponseObject<OrderVo> responseOrder = getOrderDetail(userId, orderNo);
        //需要支付的订单信息，将订单信息必要参数设置到AlipayTradeAppPayModel中，形成签名信息
        OrderVo orderVo = responseOrder.getData();

        AlipayTradeAppPayResponse response = null;
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATE,
                AlipayConfig.APPID,
                AlipayConfig.APP_PRIVATE_KEY,
                "json",
                AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");//支付的内容
        model.setSubject("App支付测试Java");//支付的title
        model.setOutTradeNo(String.valueOf(orderNo));//订单号
        model.setTimeoutExpress("30m");//过期时间
        model.setTotalAmount("0.01");//支付金额
        model.setProductCode("QUICK_MSECU11RITY_PAY");
        request.setBizModel(model);
        //支付宝请求回调的服务端地址
        request.setNotifyUrl("http://4hfax4.natappfree.cc/zhaopin/app/order/callbacks");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            response = alipayClient.sdkExecute(request);
            System.err.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String alipayCallback(Map parameterMap) {
        boolean validateFlag = true;
        Map<String, String> params = new HashMap<>();
        for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext(); ) {
            String name = (String) iterator.next();
            String[] values = (String[]) parameterMap.get(name);
            String valueStr = "";
            int size = values.length;
            for (int i = 0; i < size; i++) {
                valueStr =
                        (i == size - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //解决乱码
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        try {
            //验证支付宝公钥
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            if (flag) {
                if (AlipayTradeStatus.TRADE_SUCCESS.equals(params.get("trade_status"))) {
                    //付款金额
                    String amount = params.get("buyer_pay_amount");
                    //商户订单号
                    String outTradeNo = params.get("out_trade_no");
                    //支付宝交易号
                    String tradeNo = params.get("trade_no");
                    //商户的AppId
                    String appId = params.get("app_id");
                    //附加参数
                    //String passbackParams = URLDecoder.decode(params.get("passback_params"));
                    validateFlag = validateOrderParam(outTradeNo, amount, appId, tradeNo);
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();

        }
        return "SUCCESS";
    }

    private boolean validateOrderParam(String outTradeNo, String amount, String appId, String tradeNo) {
        boolean validateFlag = false;
        if (StringUtils.isNotBlank(outTradeNo) && StringUtils.isNotBlank(tradeNo) &&
                StringUtils.isNotBlank(amount) && StringUtils.isNotBlank(appId)) {
            //通过支付宝返回的orderNo去查询该订单
            Order order = orderMapper.validateOrderParam(Long.valueOf(outTradeNo));
            if (order == null) {
                return validateFlag;//不是该商城的订单
            }
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(), order.getUserId());
            OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
            //TODO 测试金额为0.01
            orderVo.setPayment(new BigDecimal("0.01"));
            //验证
            if (!amount.equals(String.valueOf(orderVo.getPayment()))) {
                return validateFlag;//支付金额错误
            } else if (!AlipayConfig.APPID.equals(appId)) {
                return validateFlag;//商户appid错误
            }
            //验证成功后修改修改订单信息（订单状态，支付时间，支付宝交易号），
            updateOrderInfo(order, tradeNo);
            validateFlag = true;
            return validateFlag;
        }
        return validateFlag;
    }

    private void updateOrderInfo(Order order, String tradeNo) {
        //支付结果验签成功后修改订单信息
        order.setStatus(Const.OrderStatusEnum.PAID.getCode());//订单状态
        order.setPaymentTime(new Date());//支付时间
        order.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        order.setPlatformOrderNo(tradeNo);
        int rowCount = orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public int updateOrderShopping(Long orderNo, Integer userId, Integer shoppingId) {
        return orderMapper.updateOrderShopping(orderNo, userId, shoppingId);
    }

    @Override
    public ResponseObject<OrderVo> createAppOrder(Integer userId) {
        return createOrder(userId);
    }
}
