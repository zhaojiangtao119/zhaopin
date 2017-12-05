package com.labelwall.mall.dto;

import com.labelwall.mall.entity.TopicPostReply;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017-12-05.
 */
public class TopicPostReplyDto extends TopicPostReply {

    private String createTimeStr;
    private String updateTimeStr;
    private MultipartFile multipartFile;
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

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
