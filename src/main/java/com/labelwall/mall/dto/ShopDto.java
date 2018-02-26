package com.labelwall.mall.dto;

import com.labelwall.mall.entity.Shop;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017-12-06.
 */
public class ShopDto extends Shop {

    private String createTimeStr;
    private String updateTimeStr;
    private String keyword;
    private UserDto userDto;
    private Integer serviceId;
    private Integer productId;

    //上传图片
    private MultipartFile shopImg;
    

    public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public MultipartFile getShopImg() {
        return shopImg;
    }

    public void setShopImg(MultipartFile shopImg) {
        this.shopImg = shopImg;
    }
}
