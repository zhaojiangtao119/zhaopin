package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dao.TopicPostReplyMapper;
import com.labelwall.mall.dto.TopicPostReplyDto;
import com.labelwall.mall.entity.TopicPostReply;
import com.labelwall.mall.service.ITopicPostReplyService;
import com.labelwall.mall.service.ITopicPostService;
import com.labelwall.util.DateTimeUtil;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
@Service("topicPostReplyService")
public class TopicPostReplyServiceImpl implements ITopicPostReplyService {

    private static final Logger logger = LoggerFactory.getLogger(TopicPostReplyServiceImpl.class);

    @Autowired
    private TopicPostReplyMapper topicPostReplyMapper;
    @Autowired
    private ITopicPostService topicPostService;

    @Override
    public ResponseObject<PageInfo> getTopicReplyByPostId(Integer postId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (postId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        List<TopicPostReplyDto> topicPostReplyDtoList = topicPostReplyMapper.getTopicReplyByPostId(postId);
        if (CollectionUtils.isEmpty(topicPostReplyDtoList)) {
            return ResponseObject.successStatusMessage("该帖子暂没有回复！");
        }
        for (TopicPostReplyDto item : topicPostReplyDtoList) {
            item.setCreateTimeStr(DateTimeUtil.dateToStr(item.getCreateTime()));
            item.setUpdateTimeStr(DateTimeUtil.dateToStr(item.getUpdateTime()));
            item.setCreateTime(null);
            item.setUpdateTimeStr(null);
            //加载回复图片
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
        PageInfo pageInfo = new PageInfo(topicPostReplyDtoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<TopicPostReplyDto> pulishPostReply(TopicPostReplyDto topicPostReplyDto) {

        //是否上传了图片
        if (topicPostReplyDto.getMultipartFile() != null && topicPostReplyDto.getMultipartFile().getSize() > 0) {
            //上传回复的图片
            try {
                byte[] replyBuff = topicPostReplyDto.getMultipartFile().getBytes();
                String replyImageKey = QiniuStorage.uploadReplyImage(replyBuff);
                topicPostReplyDto.setImage(replyImageKey);
            } catch (IOException e) {
                logger.error("回复图片解析失败", e);
                return ResponseObject.failStatusMessage("回复失败");
            }
        }
        TopicPostReply topicPostReply = new TopicPostReply();
        BeanUtils.copyProperties(topicPostReplyDto, topicPostReply);
        int rowCount = topicPostReplyMapper.insertSelective(topicPostReply);
        if (rowCount > 0) {
            //回复成功，更改贴子的回复数量 TODO 是否需要判断回复数量修改成功与否
            topicPostService.updatePostRelpyNum(topicPostReply.getTopicPostId());

            TopicPostReplyDto topicPostReplyDtoNew = topicPostReplyMapper.selectByPrimaryKey(topicPostReply.getId());
            topicPostReplyDtoNew.setCreateTimeStr(DateTimeUtil.dateToStr(topicPostReplyDtoNew.getCreateTime()));
            topicPostReplyDtoNew.setUpdateTimeStr(DateTimeUtil.dateToStr(topicPostReplyDtoNew.getUpdateTime()));
            topicPostReplyDtoNew.setCreateTime(null);
            topicPostReplyDtoNew.setUpdateTimeStr(null);
            //加载回复图片
            if (topicPostReplyDtoNew.getImage() != null) {
                topicPostReplyDtoNew.setImage(QiniuStorage.getUrl(topicPostReplyDtoNew.getImage()));
            }
            //加载用户头像
            if (topicPostReplyDtoNew.getUserDto().getHead() != null) {
                topicPostReplyDtoNew.getUserDto().setHead(QiniuStorage.getUserHeadUrl(topicPostReplyDtoNew.getUserDto().getHead()));
            } else {
                topicPostReplyDtoNew.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            }
            return ResponseObject.successStautsData(topicPostReplyDtoNew);
        }
        return ResponseObject.failStatusMessage("回复失败");
    }

    @Override
    public ResponseObject updatePostReplyLikeDislike(Integer topicPostReplyId, Integer type) {
        if (topicPostReplyId == null || type == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount = 0;
        if (type == Const.PostClickType.LIKE_CLICK) {
            rowCount = topicPostReplyMapper.updatePostReplyLike(topicPostReplyId);
        } else if (type == Const.PostClickType.DISLIKE_CLICK) {
            rowCount = topicPostReplyMapper.updatePostReplyDislike(topicPostReplyId);
        } else {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        if (rowCount > 0) {
            return ResponseObject.successStatusMessage("操作成功");
        }
        return ResponseObject.failStatusMessage("操作失败");
    }
}
