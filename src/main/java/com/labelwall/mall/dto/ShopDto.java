package com.labelwall.mall.dto;

import com.labelwall.mall.entity.Shop;

/**
 * Created by Administrator on 2017-12-06.
 */
public class ShopDto extends Shop {

    private String createTimeStr;
    private String updateTimeStr;
    private String keyword;
    private UserDto userDto;

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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
