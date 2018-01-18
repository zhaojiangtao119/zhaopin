package com.labelwall.mall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.ShopCartMapper;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.dto.ShopCartDto;
import com.labelwall.mall.entity.ShopCart;
import com.labelwall.mall.service.IShopCartService;
import com.labelwall.mall.vo.CartProductVo;
import com.labelwall.mall.vo.CartVo;
import com.labelwall.util.BigDecimalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017-12-07.
 */
@Service("shopCartService")
public class ShopCartServiceImpl implements IShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;

    /**
     * 获取购物车的信息，
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseObject<CartVo> getCartList(Integer userId) {
        CartVo cartVo = this.getCartVoLimit(userId);
        return ResponseObject.successStautsData(cartVo);
    }

    @Override
    public ResponseObject<CartVo> addCart(ShopCartDto shopCartDto) {
        if (shopCartDto.getQuantity() == null || shopCartDto.getProductId() == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        //判断购物车中是否存在该商品
        ShopCart shopCart = shopCartMapper.selectCartByUserIdProductId(shopCartDto.getUserId(), shopCartDto.getProductId());
        if (shopCart == null) {
            //添加一条新纪录
            ShopCart shopCartNew = new ShopCart();
            BeanUtils.copyProperties(shopCartDto, shopCartNew);
            shopCartMapper.insertSelective(shopCartNew);
        } else {
            shopCart.setQuantity(shopCart.getQuantity() + shopCartDto.getQuantity());
            shopCartMapper.updateByPrimaryKeySelective(shopCart);
        }
        return this.getCartList(shopCartDto.getUserId());
    }

    @Override
    public ResponseObject<CartVo> updateQuantity(ShopCartDto shopCartDto) {
        if (shopCartDto.getQuantity() == null || shopCartDto.getProductId() == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        ShopCart shopCart = shopCartMapper.selectCartByUserIdProductId(shopCartDto.getUserId(), shopCartDto.getProductId());
        if (shopCart != null) {
            shopCart.setQuantity(shopCartDto.getQuantity());
        }
        shopCartMapper.updateByPrimaryKeySelective(shopCart);
        return this.getCartList(shopCartDto.getUserId());
    }

    @Override
    public ResponseObject<CartVo> removeCart(Integer userId, String productIds) {
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productIdList)) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        shopCartMapper.deleteByUserIdProductIds(userId, productIdList);
        return this.getCartList(userId);
    }

    @Override
    public ResponseObject<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked) {
        shopCartMapper.checkedOrUncheckedProduct(userId, productId, checked);
        return this.getCartList(userId);
    }

    @Override
    public ResponseObject<Integer> getCartProductCount(Integer userId) {
        if (userId == null) {
            return ResponseObject.successStautsData(0);
        }
        return ResponseObject.successStautsData(shopCartMapper.selectCartProductCount(userId));
    }

    private CartVo getCartVoLimit(Integer userId) {
        List<ShopCartDto> shopCartDtoList = shopCartMapper.getCartItemByUserId(userId);
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        //填充List<CartProductVo>
        if (CollectionUtils.isNotEmpty(shopCartDtoList)) {
            for (ShopCartDto shopCartDtoItem : shopCartDtoList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(shopCartDtoItem.getId());
                cartProductVo.setUserId(shopCartDtoItem.getUserId());
                cartProductVo.setProductId(shopCartDtoItem.getProductId());
                ProductDto productDto = shopCartDtoItem.getProductDto();
                if (productDto != null) {
                    cartProductVo.setProductMainImage(productDto.getMainImage());
                    cartProductVo.setProductName(productDto.getName());
                    cartProductVo.setProductSubtitle(productDto.getSubtitle());
                    cartProductVo.setStatus(productDto.getStatus());
                    cartProductVo.setProductPrice(productDto.getPrice());
                    cartProductVo.setProductStock(productDto.getStock());
                    //判断购买数量与商品表中库存字段的关系
                    int buyLimitCount = 0;
                    if (productDto.getStock() >= shopCartDtoItem.getQuantity()) {//
                        buyLimitCount = shopCartDtoItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        //购买数量超出了商品的stock
                        buyLimitCount = productDto.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //将商品的stock，即该商品所拥有的数量，设置到购物车中
                        ShopCart shopCart = new ShopCart();
                        shopCart.setId(shopCartDtoItem.getId());
                        shopCart.setQuantity(buyLimitCount);
                        shopCartMapper.updateByPrimaryKeySelective(shopCart);
                    }
                    //
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算该商品的总价
                    cartProductVo.setProductTotlaPrice(
                            BigDecimalUtil.multiply(productDto.getPrice().doubleValue(), shopCartDtoItem.getQuantity().doubleValue()));
                    cartProductVo.setProductChecked(shopCartDtoItem.getChecked());
                }
                if (shopCartDtoItem.getChecked() == Const.Cart.CHECKED) {
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotlaPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckStatus(userId));
        return cartVo;
    }

    private boolean getAllCheckStatus(Integer userId) {//判断当前用户的购物车商品是否全部选中
        if (userId == null) {
            return false;
        }
        return shopCartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
