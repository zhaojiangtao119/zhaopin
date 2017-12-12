package com.labelwall.mall.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labelwall.common.web.SessionUser;
import com.labelwall.mall.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017-12-02.
 */

public class UserDto extends User implements SessionUser {

    private String createTimeStr;
    private String updateTimeStr;
    private MultipartFile multipartFile;

    @JsonIgnore
    @Override
    public Integer getUserId() {
        return this.getId();
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

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
