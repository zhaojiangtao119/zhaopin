package com.labelwall.mall.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.labelwall.mall.entity.TopicCategory;

import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicCategoryDto extends TopicCategory {

    private String createTimeStr;
    private String updateTimeStr;

    private List<TopicCategoryDto> subTopicCategoryDto = Lists.newArrayList();

    public List<TopicCategoryDto> getSubTopicCategoryDto() {
        return subTopicCategoryDto;
    }

    public void setSubTopicCategoryDto(List<TopicCategoryDto> subTopicCategoryDto) {
        this.subTopicCategoryDto = subTopicCategoryDto;
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
