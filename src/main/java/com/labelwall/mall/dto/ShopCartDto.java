package com.labelwall.mall.dto;

import com.labelwall.mall.entity.ShopCart;

import java.util.Date;

/**
 * Created by Administrator on 2017-12-07.
 */
public class ShopCartDto extends ShopCart {

    private String createTimeStr;
    private String updateTimeStr;
    private ProductDto productDto;


    public ShopCartDto(Integer id, Integer userId, Integer productId, Integer quantity, Integer checked, Date createTime, Date updateTime, String createTimeStr, String updateTimeStr) {
        super(id, userId, productId, quantity, checked, createTime, updateTime);
        this.createTimeStr = createTimeStr;
        this.updateTimeStr = updateTimeStr;
    }

    public ShopCartDto() {
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }
}
