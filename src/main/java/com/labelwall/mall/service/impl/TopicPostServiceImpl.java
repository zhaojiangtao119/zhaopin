package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dao.TopicPostMapper;
import com.labelwall.mall.dto.TopicPostDto;
import com.labelwall.mall.entity.TopicPost;
import com.labelwall.mall.service.ITopicPostService;
import com.labelwall.util.DateTimeUtil;
import com.labelwall.util.storage.QiniuStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */
@Service("topicPostService")
public class TopicPostServiceImpl implements ITopicPostService {

    private static final Logger logger = LoggerFactory.getLogger(TopicPostServiceImpl.class);

    @Autowired
    private TopicPostMapper topicPostMapper;

    @Override
    public ResponseObject<PageInfo> getTopicPostList(TopicPostDto topicPostDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (topicPostDto == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        List<TopicPost> topicPostList = topicPostMapper.getTopicPost(topicPostDto);
        List<TopicPostDto> topicPostDtoList = Lists.newArrayList();
        for (TopicPost item : topicPostList) {
            TopicPostDto dto = new TopicPostDto();
            BeanUtils.copyProperties(item, dto);
            dto.setCreateTimeStr(DateTimeUtil.dateToStr(item.getCreateTime()));
            dto.setUpdateTimeStr(DateTimeUtil.dateToStr(item.getUpdateTime()));
            dto.setCreateTime(null);
            dto.setUpdateTime(null);
            topicPostDtoList.add(dto);
        }
        PageInfo pageInfo = new PageInfo(topicPostDtoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<TopicPostDto> publishPost(TopicPostDto topicPostDto) {
        //发表帖子
        //判断是否上传了图片
        if (topicPostDto.getMultipartFile() != null && topicPostDto.getMultipartFile().getSize() > 0) {
            try {
                byte[] postImage = topicPostDto.getMultipartFile().getBytes();
                String imageKey = QiniuStorage.uploadPostImage(postImage);
                topicPostDto.setImage(imageKey);
            } catch (IOException e) {
                logger.error("帖子图片解析失败", e);
                return ResponseObject.failStatusMessage("发表失败");
            }
        }
        TopicPost topicPost = new TopicPost();
        BeanUtils.copyProperties(topicPostDto, topicPost);
        int rowCount = topicPostMapper.insertSelective(topicPost);
        if (rowCount > 0) {
            //新增成功，获取新增的数据
            TopicPost topicPostNew = topicPostMapper.selectByPrimaryKey(topicPost.getId());
            TopicPostDto topicPostDtoNew = new TopicPostDto();
            BeanUtils.copyProperties(topicPostNew, topicPostDtoNew);
            topicPostDtoNew.setCreateTimeStr(DateTimeUtil.dateToStr(topicPostDtoNew.getCreateTime()));
            topicPostDtoNew.setUpdateTimeStr(DateTimeUtil.dateToStr(topicPostDtoNew.getUpdateTime()));
            topicPostDtoNew.setCreateTime(null);
            topicPostDtoNew.setUpdateTime(null);
            return ResponseObject.successStautsData(topicPostDtoNew);
        }
        return ResponseObject.failStatusMessage("发表失败");
    }

    @Override
    public ResponseObject updatePostLikeDislike(Integer topicPostId, Integer type) {
        if (topicPostId == null || type == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount = 0;
        if (type == Const.PostClickType.LIKE_CLICK) {
            rowCount = topicPostMapper.updatePostLike(topicPostId);
        } else if (type == Const.PostClickType.DISLIKE_CLICK) {
            rowCount = topicPostMapper.updatePostDislike(topicPostId);
        } else {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        if (rowCount > 0) {
            return ResponseObject.successStatusMessage("操作成功");
        }
        return ResponseObject.failStatusMessage("操作失败");
    }
}
