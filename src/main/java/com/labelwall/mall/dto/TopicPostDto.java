package com.labelwall.mall.dto;

import com.labelwall.mall.entity.TopicPost;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017-12-05.
 */
public class TopicPostDto extends TopicPost {

    private String createTimeStr;
    private String updateTimeStr;
    private String keyword;
    private MultipartFile multipartFile;

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

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
