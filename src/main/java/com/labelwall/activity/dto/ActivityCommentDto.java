package com.labelwall.activity.dto;

import com.labelwall.activity.entity.ActivityComment;

import java.util.Date;

/**
 * Created by Administrator on 2018-02-05.
 */
public class ActivityCommentDto extends ActivityComment{

    private String createTimeStr;
    private String updateTimeStr;

    public ActivityCommentDto(Integer id, Integer activityId, Integer userId, String username,
                              String content, String img, Date createTime, Date updateTime,
                              String createTimeStr, String updateTimeStr) {
        super(id, activityId, userId, username, content, img, createTime, updateTime);
        this.createTimeStr = createTimeStr;
        this.updateTimeStr = updateTimeStr;
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
