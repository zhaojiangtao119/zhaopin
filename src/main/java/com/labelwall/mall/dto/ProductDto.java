package com.labelwall.mall.dto;

import com.labelwall.mall.entity.Product;
import com.labelwall.mall.entity.Shop;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017-12-06.
 */
public class ProductDto extends Product {

    private String createTimeStr;
    private String updateTimeStr;
    private String keyword;
    private Shop shop;
    private Double minPrice;
    private Double maxPrice;
    private MultipartFile mainImg;
    private MultipartFile subImg;
    
    
    

    public MultipartFile getMainImg() {
		return mainImg;
	}

	public void setMainImg(MultipartFile mainImg) {
		this.mainImg = mainImg;
	}

	public MultipartFile getSubImg() {
		return subImg;
	}

	public void setSubImg(MultipartFile subImg) {
		this.subImg = subImg;
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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
