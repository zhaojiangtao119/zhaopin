package com.labelwall.mall.dto;

import com.labelwall.mall.entity.Product;
import com.labelwall.mall.entity.Shop;

/**
 * Created by Administrator on 2017-12-06.
 */
public class ProductDto extends Product {

    private String createTimeStr;
    private String updateTimeStr;
    private String keyword;
    private Shop shop;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}