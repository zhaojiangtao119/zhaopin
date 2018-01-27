package com.labelwall.mall.dto;

import com.labelwall.mall.entity.Shopping;

import java.util.Date;

/**
 * Created by Administrator on 2017-12-06.
 */
public class ShoppingDto extends Shopping {

    private String createTimeStr;
    private String updateTimeStr;

    public ShoppingDto(Integer id, Integer userId, String receiverName, String receiverPhone,
                       String receiverMobile, String receiverProvince, String receiverCity,
                       String receiverCounty, String receiverAddress, String receiverZip,
                       Date createTime, Date updateTime, String createTimeStr, String updateTimeStr, Integer selected) {
        super(id, userId, receiverName, receiverPhone, receiverMobile, receiverProvince,
                receiverCity, receiverCounty, receiverAddress, receiverZip, createTime, updateTime, selected);
        this.createTimeStr = createTimeStr;
        this.updateTimeStr = updateTimeStr;
    }

    public ShoppingDto() {
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
}
