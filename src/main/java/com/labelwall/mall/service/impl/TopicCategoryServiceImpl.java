package com.labelwall.mall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dao.TopicCategoryMapper;
import com.labelwall.mall.dto.TopicCategoryDto;
import com.labelwall.mall.entity.TopicCategory;
import com.labelwall.mall.service.ITopicCategoryService;
import com.labelwall.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017-12-05.
 */
@Service("topicCategoryService")
public class TopicCategoryServiceImpl implements ITopicCategoryService {

    @Autowired
    private TopicCategoryMapper topicCategoryMapper;

    @Override
    public ResponseObject<List<TopicCategoryDto>> getCategoryList(Integer categoryId) {
        List<TopicCategory> topicCategoryList = topicCategoryMapper.getCategory(categoryId);
        if (CollectionUtils.isEmpty(topicCategoryList)) {
            return ResponseObject.failStatusMessage("获取失败");
        }
        List<TopicCategoryDto> topicCategoryDtoList = Lists.newArrayList();
        Map<Integer, TopicCategoryDto> topicCategoryDtoMap = Maps.newHashMap();
        for (TopicCategory item : topicCategoryList) {
            TopicCategoryDto dto = new TopicCategoryDto();
            BeanUtils.copyProperties(item, dto);
            dto.setCreateTimeStr(DateTimeUtil.dateToStr(dto.getCreateTime()));
            dto.setUpdateTimeStr(DateTimeUtil.dateToStr(dto.getUpdateTime()));
            dto.setCreateTime(null);
            dto.setUpdateTime(null);
            if (item.getParentId() == 0) {
                topicCategoryDtoMap.put(item.getId(), dto);
            } else {
                topicCategoryDtoMap.get(item.getParentId()).getSubTopicCategoryDto().add(dto);
            }
        }
        for (Map.Entry<Integer, TopicCategoryDto> entry : topicCategoryDtoMap.entrySet()) {
            topicCategoryDtoList.add(entry.getValue());
        }
        return ResponseObject.successStautsData(topicCategoryDtoList);
    }

    @Override
    public ResponseObject<List<Integer>> getCategoryAndChildrenByCategoryId(Integer categoryId) {
        Set<TopicCategory> topicCategorySet = Sets.newHashSet();
        findChildrenCategoryList(topicCategorySet, categoryId);
        List<Integer> topicCategoryIdList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(topicCategorySet)) {
            for (TopicCategory item : topicCategorySet) {
                topicCategoryIdList.add(item.getId());
            }
            return ResponseObject.successStautsData(topicCategoryIdList);
        }
        return ResponseObject.failStatusMessage("获取失败");
    }

    private Set<TopicCategory> findChildrenCategoryList(Set<TopicCategory> topicCategorySet, Integer categoryId) {
        TopicCategory topicCategory = topicCategoryMapper.selectByPrimaryKey(categoryId);
        if (topicCategory != null) {
            topicCategorySet.add(topicCategory);
        }
        List<TopicCategory> topicCategoryList = topicCategoryMapper.getCategoryChildrenByParentId(categoryId);
        for (TopicCategory item : topicCategoryList) {
            findChildrenCategoryList(topicCategorySet, item.getId());
        }
        return topicCategorySet;
    }
}
