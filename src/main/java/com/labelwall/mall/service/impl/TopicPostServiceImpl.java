package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.TopicPostMapper;
import com.labelwall.mall.dto.TopicPostDto;
import com.labelwall.mall.entity.TopicPost;
import com.labelwall.mall.service.ITopicPostService;
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
        List<TopicPostDto> topicPostDtoList = topicPostMapper.getTopicPost(topicPostDto);
        for (TopicPostDto item : topicPostDtoList) {
           /* item.setCreateTimeStr(DateTimeUtil.dateToStr(item.getCreateTime()));
            item.setUpdateTimeStr(DateTimeUtil.dateToStr(item.getUpdateTime()));
            item.setCreateTime(null);
            item.setUpdateTime(null);*/
            //加载帖子图片
            if (item.getImage() != null) {
                item.setImage(QiniuStorage.getUrl(item.getImage()));
            }
            //加载用户头像
            if (item.getUserDto().getHead() != null) {
                item.getUserDto().setHead(QiniuStorage.getUserHeadUrl(item.getUserDto().getHead()));
            } else {
                item.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            }
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
            TopicPostDto topicPostDtoNew = topicPostMapper.selectByPrimaryKey(topicPost.getId());
            /*topicPostDtoNew.setCreateTimeStr(DateTimeUtil.dateToStr(topicPostDtoNew.getCreateTime()));
            topicPostDtoNew.setUpdateTimeStr(DateTimeUtil.dateToStr(topicPostDtoNew.getUpdateTime()));
            topicPostDtoNew.setCreateTime(null);
            topicPostDtoNew.setUpdateTime(null);*/
            //获取图片
            if (topicPostDtoNew.getImage() != null) {
                topicPostDtoNew.setImage(QiniuStorage.getUrl(topicPostDtoNew.getImage()));
            }
            //加载用户头像
            if (topicPostDtoNew.getUserDto().getHead() != null) {
                topicPostDtoNew.getUserDto().setHead(QiniuStorage.getUserHeadUrl(topicPostDtoNew.getUserDto().getHead()));
            } else {
                topicPostDtoNew.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            }
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

    @Override
    public ResponseObject<TopicPostDto> getTopicPostById(Integer topicPostId) {
        if (topicPostId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        TopicPostDto topicPostDto = topicPostMapper.selectByPrimaryKey(topicPostId);
        if (topicPostDto != null) {
            //加载帖子图片
            if (topicPostDto.getImage() != null) {
                topicPostDto.setImage(QiniuStorage.getUrl(topicPostDto.getImage()));
            }
            //加载用户头像
            if (topicPostDto.getUserDto().getHead() != null) {
                topicPostDto.getUserDto().setHead(QiniuStorage.getUserHeadUrl(topicPostDto.getUserDto().getHead()));
            } else {
                topicPostDto.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            }
            return ResponseObject.successStautsData(topicPostDto);
        }
        return ResponseObject.failStatusMessage("加载失败");
    }

    @Override
    public int updatePostRelpyNum(Integer topicPostId) {
        return topicPostMapper.updatePostRelpyNum(topicPostId);
    }
}
